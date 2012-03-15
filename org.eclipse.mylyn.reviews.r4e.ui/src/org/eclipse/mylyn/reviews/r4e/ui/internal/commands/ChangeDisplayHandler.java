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
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorTreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
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
					final Command command = aEvent.getCommand();
					try {
						HandlerUtil.toggleCommandState(command);
						if (R4EUIModelController.getNavigatorView().isDefaultDisplay()) {
							((ReviewNavigatorTreeViewer) R4EUIModelController.getNavigatorView().getTreeViewer()).setViewTreeTable();
						} else {
							((ReviewNavigatorTreeViewer) R4EUIModelController.getNavigatorView().getTreeViewer()).setViewTree();
						}
						R4EUIModelController.setJobInProgress(false); //Enable commands in case of error

						final IEvaluationService evService = (IEvaluationService) HandlerUtil.getActiveWorkbenchWindowChecked(
								aEvent)
								.getService(IEvaluationService.class);
						evService.requestEvaluation("org.eclipse.mylyn.reviews.r4e.ui.commands.display.defaultDisplay");
						evService.requestEvaluation(R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND);
						evService.requestEvaluation(R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND);
						evService.requestEvaluation(R4EUIConstants.ALPHA_SORTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.REVIEWS_ONLY_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.REVIEWS_PARTICIPANT_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.ANOMALIES_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.ANOMALIES_MY_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.REVIEWS_COMPLETED_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.HIDE_RULE_SETS_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.HIDE_DELTAS_FILTER_COMMAND);
						evService.requestEvaluation(R4EUIConstants.REMOVE_ALL_FILTER_COMMAND);
					} catch (ExecutionException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
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
