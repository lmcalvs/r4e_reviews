/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the navigator view toolbar command to apply the 
 * Unassign filter
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.filters;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.UnassignParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class UnassignFilterHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param event
	 *            ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		//We need to preserve the expansion state and restore it afterwards
		final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
		final UnassignParticipantFilter filter = ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView()
				.getActionSet()).getUnassignedFilter();

		final Object[] elements = viewer.getExpandedElements();
		boolean oldValue = HandlerUtil.toggleCommandState(event.getCommand());

		if (!oldValue) {
			R4EUIPlugin.Ftracer.traceInfo("Apply unassigned filter to ReviewNavigator");
			viewer.addFilter(filter);
		} else {
			R4EUIPlugin.Ftracer.traceInfo("Remove unassigned filter from ReviewNavigator");
			viewer.removeFilter(filter);
		}
		R4EUIModelController.getNavigatorView().getTreeViewer().setExpandedElements(elements);
		return null;
	}

	@Override
	public boolean isEnabled() {
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isAssignedMyFilterSet()
				|| ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isAssignedParticipantFilterSet()) {
			return false;
		}
		return true;
	}
}
