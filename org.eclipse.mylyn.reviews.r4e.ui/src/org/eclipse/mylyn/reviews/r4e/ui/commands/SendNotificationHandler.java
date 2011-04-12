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
 * This class implements the command used to send an email or a notification to
 * other participants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.SendNotificationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class SendNotificationHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object 
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) {
		
		Object source = ((EvaluationContext)event.getApplicationContext()).getDefaultVariable();
		if (source instanceof List) {
			source = ((List<?>)source).get(0);  //If this is a list, get first element
		}
		R4EUIModelController.setDialogOpen(true);
		//if the source is Review element, all options are available.  O(therwise, only ask questions is supported
		SendNotificationInputDialog dialog = new SendNotificationInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell(), source);
		dialog.create();
		final int result = dialog.open();
		if (result == Window.OK) {
			int messageType = dialog.getMessageTypeValue();

			try {
				switch (messageType) {
					case R4EUIConstants.MESSAGE_TYPE_ITEMS_READY:
						//Send review items ready notification
						MailServicesProxy.sendItemsReadyNotification();
						break;
					case R4EUIConstants.MESSAGE_TYPE_ITEMS_REMOVED:
						//Send review items ready notification
						MailServicesProxy.sendItemsRemovedNotification();
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
					default:
						//Do nothing, should never happen
				}
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceHandlingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  //else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);	
		return null;
	}
}
