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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class RestoreElementHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Restoring Elements..."")
	 */
	private static final String COMMAND_MESSAGE = "Restoring Elements...";

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

		final List<IR4EUIModelElement> selectedElements = UIUtils.getCommandUIElements();

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (!selectedElements.isEmpty()) {
					monitor.beginTask(COMMAND_MESSAGE, selectedElements.size());
					R4EUIModelController.setJobInProgress(true);

					R4EReview review = null;
					if (null != R4EUIModelController.getActiveReview()) {
						review = R4EUIModelController.getActiveReview().getReview();
					}
					final List<R4EReviewComponent> addedItems = new ArrayList<R4EReviewComponent>();
					for (IR4EUIModelElement element : selectedElements) {
						try {
							monitor.subTask("Restoring element " + element.getName());
							R4EUIPlugin.Ftracer.traceInfo("Restoring element " + element.getName()); //$NON-NLS-1$
							element.restore();
							if (element instanceof R4EUIReviewBasic) {
								if (null != R4EUIModelController.getActiveReview()) {
									R4EUIModelController.getActiveReview().close(); //Only one review open at any given time
								}
							}
							element.open();
							if (element instanceof R4EUIReviewItem) {
								addedItems.add(((R4EUIReviewItem) element).getItem());
							} else if (element instanceof R4EUIContent) {
								addedItems.add(((R4EUIContent) element).getContent());
							}

						} catch (ResourceHandlingException e) {
							UIUtils.displayResourceErrorDialog(e);
						} catch (OutOfSyncException e) {
							UIUtils.displaySyncErrorDialog(e);
						} catch (FileNotFoundException e) {
							R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() //$NON-NLS-1$ //$NON-NLS-2$
									+ ")"); //$NON-NLS-1$
							R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
						} catch (CompatibilityException e) {
							UIUtils.displayCompatibilityErrorDialog(e);
						}

						monitor.worked(1);
						if (monitor.isCanceled()) {
							R4EUIModelController.setJobInProgress(false);
							UIUtils.setNavigatorViewFocus(element, 0);
							return Status.CANCEL_STATUS;
						}
					}

					//Send email notification if needed
					if (null != review) {
						if (0 < addedItems.size() && review.getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
							if (((R4EFormalReview) review).getCurrent()
									.getType()
									.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
								try {
									MailServicesProxy.sendItemsAddedNotification(addedItems);
								} catch (CoreException e) {
									UIUtils.displayCoreErrorDialog(e);
								} catch (ResourceHandlingException e) {
									UIUtils.displayResourceErrorDialog(e);
								}
							}
						}
					}
					R4EUIModelController.setJobInProgress(false);
					UIUtils.setNavigatorViewFocus(selectedElements.get(0), 1);
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
