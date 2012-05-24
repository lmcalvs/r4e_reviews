// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the command used to send an email or a notification to
 * other participants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ISendNotificationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class SendNotificationHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Sending Notification..."")
	 */
	private static final String COMMAND_MESSAGE = "Sending Notification...";

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

		final UIJob job = new UIJob(COMMAND_MESSAGE) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(COMMAND_MESSAGE, 1);

				ISelection source = HandlerUtil.getCurrentSelection(aEvent);

				//if the source is unique and is a Review element, all options are available.  Otherwise, only ask questions is supported
				final ISendNotificationInputDialog dialog = R4EUIDialogFactory.getInstance()
						.getSendNotificationInputDialog(source);
				dialog.create();
				final int result = dialog.open();
				if (result == Window.OK) {
					final int messageType = dialog.getMessageTypeValue();

					try {
						switch (messageType) {
						case R4EUIConstants.MESSAGE_TYPE_ITEMS_READY:
							//Send review items ready notification
							MailServicesProxy.sendItemsReadyNotification();
							break;
						case R4EUIConstants.MESSAGE_TYPE_PROGRESS:
							//Send progress notification
							MailServicesProxy.sendProgressNotification();
							break;
						case R4EUIConstants.MESSAGE_TYPE_COMPLETION:
							//Send completion notification
							MailServicesProxy.sendCompletionNotification();
							break;
						case R4EUIConstants.MESSAGE_TYPE_QUESTION:
							//Send question
							MailServicesProxy.sendQuestion(source);
							break;
						case R4EUIConstants.MESSAGE_TYPE_MEETING:
							//Send question
							MailServicesProxy.sendMeetingRequest();
							UIUtils.setNavigatorViewFocus(R4EUIModelController.getActiveReview(), 0);
							break;
						default:
							//Do nothing, should never happen
						}
					} catch (CoreException e) {
						UIUtils.displayCoreErrorDialog(e);
					} catch (ResourceHandlingException e) {
						UIUtils.displayResourceErrorDialog(e);
					} catch (OutOfSyncException e) {
						UIUtils.displaySyncErrorDialog(e);
					}
					monitor.worked(1);
					monitor.done();
					return Status.OK_STATUS;
				}
				monitor.done();
				return Status.CANCEL_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
