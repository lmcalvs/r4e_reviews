// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the review navigator view toolbar command used 
 * to refresh (i.e. sychronize with serialization model) the review/review group data 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewGroup;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class RefreshHandler extends AbstractHandler  {

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

		try {
			final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
			if (!selection.isEmpty()) {

				final IR4EUIModelElement element = (IR4EUIModelElement) selection.getFirstElement();
				if (element instanceof R4EUIReviewGroup) {
					//Refresh whole Review Group
					((R4EUIReviewGroup)element).close();
					R4EUIModelController.setActiveReview(null);
					((R4EUIReviewGroup)element).open();
				} else {
					//Refresh Review
					refreshReview(element);
				}		
			} else {
				//No selection refresh all open review groups
				final R4EUIReviewGroup[] groups = (R4EUIReviewGroup[]) R4EUIModelController.getRootElement().getChildren();
				for (R4EUIReviewGroup group : groups) {
					if (group.isOpen()) {
						group.close();
						group.open();
					}
				}
				R4EUIModelController.setActiveReview(null);
			}
		} catch (ResourceHandlingException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while refreshing view ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		}
		return null;
	}
	
	/**
	 * Method refreshReview.
	 * @param element IR4EUIModelElement
	 */
	private void refreshReview(IR4EUIModelElement element) {
		IR4EUIModelElement refreshElement = element;
		
		try {
			while (null != refreshElement && !(refreshElement instanceof R4EUIReview)) {
				refreshElement = refreshElement.getParent();
			}
			
			if (null != refreshElement && refreshElement.isOpen()) {
				refreshElement.close();
				refreshElement.open();
			}
		} catch (ResourceHandlingException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while refreshing ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		} catch (ReviewVersionsException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while refreshing review item ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		} catch (FileNotFoundException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "File not found error detected while refreshing review item ",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		}
	}
}
