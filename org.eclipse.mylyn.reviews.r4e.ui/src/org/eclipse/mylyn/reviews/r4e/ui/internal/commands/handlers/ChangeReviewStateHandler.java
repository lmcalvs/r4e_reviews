// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the context-sensitive command to set an element
 * as reviewed/not reviewed
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ISendNotificationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ChangeReviewStateHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Changing Reviewed State..."")
	 */
	private static final String COMMAND_MESSAGE = "Changing Reviewed State...";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param event
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) {

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				final ISelection selection = HandlerUtil.getCurrentSelection(event);
				if (selection instanceof IStructuredSelection) {
					if (!selection.isEmpty()) {
						monitor.beginTask(COMMAND_MESSAGE, ((IStructuredSelection) selection).size());
						R4EUIModelController.setJobInProgress(true);

						Object element = null;
						Resource resource = null;
						try {
							//Lock the resource to the participant to avoid parallel updates from other users
							final R4EUIReviewBasic review = R4EUIModelController.getActiveReview();
							final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), true);
							final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user,
									R4EUIModelController.getReviewer());

							//Prevent serialization for each individual child element within the particpant's EMF Resource and serialize in bulk at the end
							resource = user.eResource();
							R4EUIModelController.stopSerialization(resource);

							//Loop through all selected elements
							for (final Iterator<?> iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();) {
								element = iterator.next();
								if (!(element instanceof IR4EUIModelElement)) {
									monitor.worked(1);
									continue;
								}
								R4EUIPlugin.Ftracer.traceInfo("Changing Reviewed State for Element " //$NON-NLS-1$
										+ ((IR4EUIModelElement) element).getName());
								boolean newValue = !(((IR4EUIModelElement) element).isUserReviewed());

								//Here if we set the element and its children.
								((IR4EUIModelElement) element).setUserReviewed(newValue, true, true);

								monitor.worked(1);
								if (monitor.isCanceled()) {
									R4EUIModelController.setJobInProgress(false);
									UIUtils.setNavigatorViewFocus((IR4EUIModelElement) element, 0);
									//make sure serialization is back to normal							
									R4EUIModelController.startSerialization(resource);
									return Status.CANCEL_STATUS;
								}
							}

							//Enable serialization for this resource
							R4EUIModelController.startSerialization(resource);
							//Serialize changes
							R4EUIModelController.FResourceUpdater.checkIn(bookNum);

							//If user marked all elements as reviewed, prompt user for mail sending
							if (R4EUIModelController.getActiveReview().isUserReviewed()) {
								promptCompletionNotification();
							}
						} catch (ResourceHandlingException e) {
							UIUtils.displayResourceErrorDialog(e);
						} catch (OutOfSyncException ex) {
							UIUtils.displaySyncErrorDialog(ex);
						} finally {
							//make sure serialization is back to normal							
							R4EUIModelController.startSerialization(resource);
						}

						R4EUIModelController.setJobInProgress(false);
						UIUtils.setNavigatorViewFocus((IR4EUIModelElement) element, 0);
					}
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Method promptCompletionNotification.
	 */
	private void promptCompletionNotification() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				ISelection selection = new StructuredSelection(R4EUIModelController.getActiveReview());
				final ISendNotificationInputDialog dialog = R4EUIDialogFactory.getInstance()
						.getSendNotificationInputDialog(selection);
				dialog.create();
				final int result = dialog.open();
				if (result == Window.OK) {
					final int messageType = dialog.getMessageTypeValue();
					try {
						switch (messageType) {
						case R4EUIConstants.MESSAGE_TYPE_COMPLETION:
							//Send completion notification
							MailServicesProxy.sendCompletionNotification();
							break;
						default:
							//Do nothing, should never happen
						}
					} catch (CoreException e) {
						UIUtils.displayCoreErrorDialog(e);
					} catch (ResourceHandlingException e) {
						UIUtils.displayResourceErrorDialog(e);
					}
				}
			}
		});
	}

}
