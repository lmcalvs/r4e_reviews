// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the context-sensitive command to add an anomaly on 
 * a review item
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.R4EFindReviewItemsDialog;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FindReviewItemsHandler extends AbstractHandler {

	/**
	 * Field ADD_ANOMALY_DIALOG_TITLE.
	 * (value is ""Enter Anomaly details"")
	 */
	private static final String FIND_REVIEW_ITEMS_DIALOG_TITLE = "Find review items";

	/**
	 * Field ADD_ANOMALY_DIALOG_VALUE.
	 * (value is ""Enter the Anomaly title:"")
	 */
	private static final String FIND_REVIEW_ITEMS_DIALOG_VALUE = "Review item info (Last Commit)";

	/**
	 * Field ADD_COMMENT_DIALOG_VALUE.
	 * (value is ""Enter your comments for the new Anomaly:"")
	 */
	private static final String FIND_REVIEW_ITEMS_DESCRIPTION_DIALOG_VALUE = "Review item components (Last Commit)";
	
	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) {

		//Get project to use (use adapters if needed)
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final Object selectedElement = ((IStructuredSelection)selection).getFirstElement();
		IProject project = null;
		if (selectedElement instanceof IProject) { 
			project = (IProject) selectedElement;
		} else if (selectedElement instanceof IAdaptable) {
			final IAdaptable adaptableProject = (IAdaptable) selectedElement; 
			project = (IProject) adaptableProject.getAdapter(IProject.class); 
		} else {
			//Should never happen
			Activator.Tracer.traceError("Invalid selected element class " + selectedElement.getClass());
			Activator.getDefault().logError("Invalid selected element class " + selectedElement.getClass(), null);
		}
	
		//Fire up the find review items dialog to get the latest commit info for the first selection
		R4EUIModelController.setDialogOpen(true);

		final R4EFindReviewItemsDialog dialog = new R4EFindReviewItemsDialog(R4EUIModelController.getNavigatorView().
				getSite().getWorkbenchWindow().getShell(), FIND_REVIEW_ITEMS_DIALOG_TITLE, FIND_REVIEW_ITEMS_DIALOG_VALUE, 
				FIND_REVIEW_ITEMS_DESCRIPTION_DIALOG_VALUE, project);
    	dialog.open();
    	//Note the review item will be added to the review in the dialog if needed
		R4EUIModelController.setDialogOpen(false);
		return null;
	}
}
