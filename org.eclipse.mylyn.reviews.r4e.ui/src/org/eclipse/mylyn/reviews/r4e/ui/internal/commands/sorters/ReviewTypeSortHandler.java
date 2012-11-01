/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the navigator view toolbar command to apply the 
 * review type sorter
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.sorters;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewTypeSortHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Applying Review Type Sorter..."")
	 */
	private static final String COMMAND_MESSAGE = "Applying Review Type Sorter...";

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

		final ISelection selection = R4EUIModelController.getNavigatorView().getTreeViewer().getSelection();

		final UIJob job = new UIJob(COMMAND_MESSAGE) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(COMMAND_MESSAGE, 1);

				//We need to preserve the expansion state and restore it afterwards
				final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
				final Object[] elements = viewer.getExpandedElements();

				//First check if there is already another sorter applied, if so, remove if
				ViewerComparator oldSorter = viewer.getComparator();
				if (null != oldSorter && oldSorter instanceof NavigatorElementComparator) {
					((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).resetAlphaSorterCommand();
				}

				//Then apply the new Comparator

				final ViewerComparator sorter = ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView()
						.getActionSet()).getReviewTypeSorter();

				boolean oldValue;
				try {
					oldValue = HandlerUtil.toggleCommandState(aEvent.getCommand());
				} catch (ExecutionException e) {
					monitor.done();
					return Status.CANCEL_STATUS;
				}

				if (!oldValue) {
					R4EUIPlugin.Ftracer.traceInfo("Apply Review Type sorter to ReviewNavigator"); //$NON-NLS-1$
					viewer.setComparator(sorter);
				} else {
					R4EUIPlugin.Ftracer.traceInfo("Remove Review Type sorter from ReviewNavigator"); //$NON-NLS-1$
					viewer.setComparator(null);
				}
				R4EUIModelController.getNavigatorView().getTreeViewer().setExpandedElements(elements);

				try {
					final IEvaluationService evService = (IEvaluationService) HandlerUtil.getActiveWorkbenchWindowChecked(
							aEvent)
							.getService(IEvaluationService.class);
					evService.requestEvaluation(R4EUIConstants.ALPHA_SORTER_COMMAND);
					evService.requestEvaluation(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND);
				} catch (ExecutionException e) {
					R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
				//Refresh view if possible
				if (selection instanceof IStructuredSelection) {
					final Object element = ((IStructuredSelection) selection).getFirstElement();
					if (element instanceof IR4EUIModelElement) {
						UIUtils.setNavigatorViewFocus((IR4EUIModelElement) element, 0);
					}
				}
				monitor.worked(1);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
