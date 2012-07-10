// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the context-sensitive command used 
 * to remove the currently selected element form the model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class RemoveElementHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Removing Elements..."")
	 */
	private static final String COMMAND_MESSAGE = "Removing Elements...";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		final ISelection selection = R4EUIModelController.getNavigatorView().getTreeViewer().getSelection();

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (selection instanceof IStructuredSelection) {
					if (!selection.isEmpty()) {
						monitor.beginTask(COMMAND_MESSAGE, ((IStructuredSelection) selection).size());
						R4EUIModelController.setJobInProgress(true);

						boolean askConfirmation = true;
						Object element = null;
						R4EReview review = null;
						if (null != R4EUIModelController.getActiveReview()) {
							review = R4EUIModelController.getActiveReview().getReview();
						}
						final List<R4EReviewComponent> removedItems = new ArrayList<R4EReviewComponent>();
						for (final Iterator<?> iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();) {
							element = iterator.next();
							if (!(element instanceof IR4EUIModelElement)) {
								monitor.worked(1);
								continue;
							}
							monitor.subTask("Disabling element " + ((IR4EUIModelElement) element).getName());
							R4EUIPlugin.Ftracer.traceInfo("Disabling element " + ((IR4EUIModelElement) element).getName()); //$NON-NLS-1$
							final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
							final boolean[] fileRemove = new boolean[1];
							final String elementName = ((IR4EUIModelElement) element).getName();
							if (askConfirmation) {
								Display.getDefault().syncExec(new Runnable() {
									public void run() {
										MessageDialogWithToggle dialog = MessageDialogWithToggle.openOkCancelConfirm(
												null, "Disable element", "Do you really want to disable element "
														+ elementName + "?", "Don't ask again", false, null, null);
										result[0] = dialog.getReturnCode();
										fileRemove[0] = !dialog.getToggleState();
									}
								});
							}
							if (result[0] == Window.OK) {
								askConfirmation = fileRemove[0];
								try {
									if (element instanceof R4EUIReviewItem) {
										removedItems.add(((R4EUIReviewItem) element).getItem());
									} else if (element instanceof R4EUIContent) {
										removedItems.add(((R4EUIContent) element).getContent());
									}

									if (((IR4EUIModelElement) element).isOpen()) {
										((IR4EUIModelElement) element).close();
										for (IR4EUIModelElement childElement : ((IR4EUIModelElement) element).getChildren()) {
											if (null != childElement && childElement.isOpen()) {
												childElement.close();
												break;
											}
										}
									}
									((IR4EUIModelElement) element).getParent().removeChildren(
											(IR4EUIModelElement) element, false);
								} catch (ResourceHandlingException e) {
									UIUtils.displayResourceErrorDialog(e);
								} catch (OutOfSyncException e) {
									UIUtils.displaySyncErrorDialog(e);
								} catch (CompatibilityException e) {
									UIUtils.displayCompatibilityErrorDialog(e);
								}
							}
							monitor.worked(1);
							if (monitor.isCanceled()) {
								R4EUIModelController.setJobInProgress(false);
								UIUtils.setNavigatorViewFocus((IR4EUIModelElement) element, 0);
								return Status.CANCEL_STATUS;
							}
						}

						//Send email notification if needed
						if (null != review) {
							if (0 < removedItems.size()
									&& review.getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
								if (((R4EFormalReview) review).getCurrent()
										.getType()
										.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
									try {
										MailServicesProxy.sendItemsRemovedNotification(removedItems);
									} catch (CoreException e) {
										UIUtils.displayCoreErrorDialog(e);
									} catch (ResourceHandlingException e) {
										UIUtils.displayResourceErrorDialog(e);
									}
								}
							}
						}
						if (null != element && element instanceof IR4EUIModelElement) {
							R4EUIModelController.setJobInProgress(false);
							UIUtils.setNavigatorViewFocus((IR4EUIModelElement) element, 0);
						}
					}
				}
				R4EUIModelController.setJobInProgress(false);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
