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
 * reviewed elements filter
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.filters;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.ReviewedElemsFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;
import org.eclipse.ui.progress.UIJob;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewedElemsFilterHandler extends AbstractHandler implements IElementUpdater {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Applying My Assigned Elements Filter..."")
	 */
	private static final String COMMAND_MESSAGE = "Applying My Assigned Elements Filter...";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		final UIJob job = new UIJob(COMMAND_MESSAGE) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(COMMAND_MESSAGE, 1);

				//We need to preserve the expansion state and restore it afterwards
				final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
				final Object[] elements = viewer.getExpandedElements();

				final ReviewedElemsFilter filter = ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView()
						.getActionSet()).getReviewedElemsFilter();

				boolean oldValue;
				try {
					oldValue = HandlerUtil.toggleCommandState(aEvent.getCommand());
				} catch (ExecutionException e) {
					monitor.done();
					return Status.CANCEL_STATUS;
				}
				if (!oldValue) {
					R4EUIPlugin.Ftracer.traceInfo("Apply Hide Reviewed Elements filter to ReviewNavigator"); //$NON-NLS-1$
					viewer.addFilter(filter);
				} else {
					R4EUIPlugin.Ftracer.traceInfo("Remove Hide Reviewed Elements filter from ReviewNavigator"); //$NON-NLS-1$
					viewer.removeFilter(filter);
				}
				R4EUIModelController.getNavigatorView().getTreeViewer().setExpandedElements(elements);

				monitor.worked(1);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Method updateElement.
	 * 
	 * @param element
	 *            UIElement
	 * @param parameters
	 *            Map
	 * @see org.eclipse.ui.commands.IElementUpdater#updateElement(UIElement, Map)
	 */
	public void updateElement(UIElement element, Map parameters) {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = commandService.getCommand(R4EUIConstants.REVIEWED_ELEMS_FILTER_COMMAND);
		element.setChecked((Boolean) command.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).getValue());
	}
}
