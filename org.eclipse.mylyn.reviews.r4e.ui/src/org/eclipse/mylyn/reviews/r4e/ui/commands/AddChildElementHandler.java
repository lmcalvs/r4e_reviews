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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddChildElementHandler extends AbstractHandler {

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

		final IR4EUIModelElement element = getParentElement(event);
		IR4EUIModelElement newElement = null;
		final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();

		try {
			//Get data from user
			if (null != element) {
				final R4EReviewComponent tempModelComponent = element.createChildModelDataElement();
				if (null != tempModelComponent) {
					Activator.Ftracer.traceInfo("Adding child to element " + element.getName());

					//Create actual model element
					newElement = element.createChildren(tempModelComponent);	
					if (null != newElement) {
						//Set focus to newly created element and open it
						viewer.expandToLevel(newElement, AbstractTreeViewer.ALL_LEVELS);
						viewer.setSelection(new StructuredSelection(newElement), true);
					}
				}
			}
		} catch (ResourceHandlingException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while creating element ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			
			//Remove object if partially created
			if (null != element && null != newElement) element.removeChildren(newElement);
			
		} catch (OutOfSyncException e) {
			Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while adding element.  " +
					"Please refresh the review navigator view and try the command again",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			// TODO later we will want to do this automatically

			//Remove object if partially created
			if (null != element && null != newElement) element.removeChildren(newElement);		
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
	
	/**
	 * Method getParentElement.
	 * @param event ExecutionEvent
	 * @return IR4EUIModelElement 
	 */
	private IR4EUIModelElement getParentElement(ExecutionEvent event) {
		
		final Widget triggerObject = ((Event)event.getTrigger()).widget;
		IR4EUIModelElement element = null;
		
		if (triggerObject instanceof ToolItem) {
			//Add element to the root of the tree
			element = R4EUIModelController.getRootElement();
		} else {
			final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
			if (!selection.isEmpty()) {
			    element = (IR4EUIModelElement)selection.getFirstElement();
			} else {
				//Add element to the root of the tree
				element = R4EUIModelController.getRootElement();
			}
		}
		return element;
	}
}
