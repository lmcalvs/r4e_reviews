// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the action used to open a review/review group in the UI model tree structure
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import java.io.FileNotFoundException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class OpenReviewAction extends Action {
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	
	/**
	 * Constructor for OpenReviewAction.
	 * @param aView ReviewNavigatorView
	 * @param aActionName String
	 * @param aActionTooltip String
	 * @param aImage ImageDescriptor
	 */
	public OpenReviewAction(ReviewNavigatorView aView, String aActionName, String aActionTooltip, ImageDescriptor aImage) {
		fView = aView;
		setText(aActionName);
		setToolTipText(aActionTooltip);
		setImageDescriptor(aImage);
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {	
		final IStructuredSelection selection = (IStructuredSelection) fView.getTreeViewer().getSelection();
		if (!selection.isEmpty()) {
			final IR4EUIModelElement element = (IR4EUIModelElement)selection.getFirstElement();
			try {
				if (element instanceof R4EUIReview) {					
					Activator.Tracer.traceInfo("Opening review " + element.getName());
					final R4EUIReview activeReview = R4EUIModelController.getActiveReview();
					if (null != activeReview ) activeReview.close();
					element.open();
					R4EUIModelController.setActiveReview((R4EUIReview)element);
				} else {
					element.open();	
				}

				//The action is only performed on the first element, so select it
				final StructuredSelection newSelection = new StructuredSelection(selection.getFirstElement());
				fView.getTreeViewer().setSelection(newSelection, true);
			} catch (ResourceHandlingException e) {
				Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while opening element",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			} catch (ReviewVersionsException e) {
				Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while opening element",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			} catch (FileNotFoundException e) {
				Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "File not found error detected while opening element",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			}
		}
	}
}
