// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the action used to add a children node to the UI 
 * model tree structure
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddChildNodeAction extends Action {
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Field fIsRoot.
	 */
	private boolean fIsRoot = false;
	
	/**
	 * Constructor for AddChildNodeAction.
	 * @param aView ReviewNavigatorView
	 * @param aActionName String
	 * @param aActionTooltip String
	 * @param aImage ImageDescriptor
	 * @param aIsRoot boolean
	 */
	public AddChildNodeAction(ReviewNavigatorView aView, String aActionName, String aActionTooltip, ImageDescriptor aImage, boolean aIsRoot) {
		fView = aView;
		fIsRoot = aIsRoot;
		setText(aActionName);
		setToolTipText(aActionTooltip);
		setImageDescriptor(aImage);
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {	 // $codepro.audit.disable cyclomaticComplexity
		IR4EUIModelElement element = null;
		IR4EUIModelElement newElement = null;
		
		try {
			if (fIsRoot) {
				//Add element to the root of the tree
				element = R4EUIModelController.getRootElement();
			} else {
				final IStructuredSelection selection = (IStructuredSelection) fView.getTreeViewer().getSelection();
				if (!selection.isEmpty()) {
				    element = (IR4EUIModelElement)selection.getFirstElement();
				}
			}
			
			//Get data from user
			if (null == element) return;   //Failed to get parent element, errors are shown in the model classes
			final R4EReviewComponent tempModelComponent = element.createChildModelDataElement();
			
			if (null == tempModelComponent) return;  //User cancelled the action
			Activator.Tracer.traceInfo("Adding child to element " + element.getName());
			
			//Create actual model element
			newElement = element.createChildren(tempModelComponent);	
			if (null == newElement) return;   //Failed creation, errors are shown in the model classes
			
			//Set focus to newly created element and open it
			fView.getTreeViewer().expandToLevel(newElement, AbstractTreeViewer.ALL_LEVELS);
			fView.getTreeViewer().setSelection(new StructuredSelection(newElement), true);		
		} catch (ResourceHandlingException e) {
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while creating element ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			
			//Remove object if partially created
			if (null != element && null != newElement) element.removeChildren(newElement);
			
		} catch (OutOfSyncException e) {
			Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while adding element.  " +
					"Please refresh the review navigator view and try the command again",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			// TODO later we will want to do this automatically

			//Remove object if partially created
			if (null != element && null != newElement) element.removeChildren(newElement);
			
		} 
	}
}
