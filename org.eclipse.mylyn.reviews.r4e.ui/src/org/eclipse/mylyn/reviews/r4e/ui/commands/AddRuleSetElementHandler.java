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
 * This class implements the context-sensitive command to add a child element
 * to the navigator view/model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRootElement;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddRuleSetElementHandler extends AbstractHandler {

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

		final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
		IR4EUIModelElement element = R4EUIModelController.getRootElement();
		IR4EUIModelElement newElement = null;

		try {
			//Get data from user
				final ReviewComponent tempModelComponent = ((R4EUIRootElement)element).createRuleSetElement();
				if (null != tempModelComponent) {
					Activator.Ftracer.traceInfo("Adding Rule Set to the root element ");

					//Create actual model element
					newElement = element.createChildren(tempModelComponent);	
					if (null != newElement) {
						//Set focus to newly created element and open it
						viewer.expandToLevel(newElement, AbstractTreeViewer.ALL_LEVELS);
						viewer.setSelection(new StructuredSelection(newElement), true);
					}
				}
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);

			//Remove object if partially created
			try {
				element.removeChildren(newElement, true);
			} catch (ResourceHandlingException e1) {
				UIUtils.displayResourceErrorDialog(e1);
			} catch (OutOfSyncException e1) {
				UIUtils.displaySyncErrorDialog(e1);
			}

		} catch (OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);

			//Remove object if partially created
			try {
				element.removeChildren(newElement, true);
			} catch (ResourceHandlingException e1) {
				UIUtils.displayResourceErrorDialog(e1);
			} catch (OutOfSyncException e1) {
				UIUtils.displaySyncErrorDialog(e1);
			}		
		}
		
		try {
			final IEvaluationService evService = 
				(IEvaluationService) HandlerUtil.getActiveWorkbenchWindowChecked(event).getService(IEvaluationService.class);
			evService.requestEvaluation("org.eclipse.mylyn.reviews.r4e.ui.commands.dialogOpen");
		} catch (ExecutionException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
		return null;
	}
}
