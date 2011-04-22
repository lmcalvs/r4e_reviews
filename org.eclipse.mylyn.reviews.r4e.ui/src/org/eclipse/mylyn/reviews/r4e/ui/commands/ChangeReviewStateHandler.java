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
package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.SendNotificationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ChangeReviewStateHandler extends AbstractHandler {

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

		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty()) {
			IR4EUIModelElement element = null;
			for (final Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
				try {
					element = (IR4EUIModelElement) iterator.next();
					Activator.Ftracer.traceInfo("Changing review state for element " + element.getName());
					element.setReviewed(!(element.isReviewed()));
					
					//If we just completed the review, prompt user for mail sending
					if (element instanceof R4EUIReviewBasic && element.isReviewed()) {
						
						Object source = ((EvaluationContext)event.getApplicationContext()).getDefaultVariable();
						if (source instanceof List) {
							source = ((List<?>)source).get(0);  //If this is a list, get first element
						}
						R4EUIModelController.setDialogOpen(true);
						
						final SendNotificationInputDialog dialog = new SendNotificationInputDialog(R4EUIModelController.getNavigatorView().
								getSite().getWorkbenchWindow().getShell(), source);
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
						R4EUIModelController.setDialogOpen(false);	
					}
				} catch (ResourceHandlingException e) {
					UIUtils.displayResourceErrorDialog(e);
		
				} catch (OutOfSyncException e) {
					UIUtils.displaySyncErrorDialog(e);

				}
			}
		}
		return null;
	}
}
