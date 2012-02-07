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
 * This class implements the navigator view toolbar command to collapse all elements
 * of the review tree
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorTreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ChangeDisplayHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		final UIJob job = new UIJob("Changing Display...") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				if (null != R4EUIModelController.getActiveReview()) {
					R4EUIModelController.setJobInProgress(true);

					//Change Display Type (Global Tree / Review TreeTable)
					R4EUIPlugin.Ftracer.traceInfo("Changing Display Type");
					if (R4EUIModelController.getNavigatorView().isDefaultDisplay()) {
						((ReviewNavigatorTreeViewer) R4EUIModelController.getNavigatorView().getTreeViewer()).setViewTreeTable();
					} else {
						((ReviewNavigatorTreeViewer) R4EUIModelController.getNavigatorView().getTreeViewer()).setViewTree();
					}

					try {
						final IEvaluationService evService = (IEvaluationService) HandlerUtil.getActiveWorkbenchWindowChecked(
								aEvent)
								.getService(IEvaluationService.class);
						evService.requestEvaluation("org.eclipse.mylyn.reviews.r4e.ui.commands.NewChildElement");
						evService.requestEvaluation("org.eclipse.mylyn.reviews.r4e.ui.commands.NewRuleSetElement");
						evService.requestEvaluation("org.eclipse.mylyn.reviews.r4e.ui.commands.PreviousState");
						evService.requestEvaluation("org.eclipse.mylyn.reviews.r4e.ui.commands.NextState");
					} catch (ExecutionException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					}
					R4EUIModelController.setJobInProgress(false); //Enable commands in case of error
					final IStructuredSelection selection = (IStructuredSelection) R4EUIModelController.getNavigatorView()
							.getTreeViewer()
							.getSelection();
					if (null != selection) {
						final IR4EUIModelElement element = (IR4EUIModelElement) selection.getFirstElement();
						UIUtils.setNavigatorViewFocus(element, 0);
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
