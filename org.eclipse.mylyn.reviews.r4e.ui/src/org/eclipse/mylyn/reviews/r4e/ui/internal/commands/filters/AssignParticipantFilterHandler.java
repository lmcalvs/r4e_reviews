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
 * Assign participant filter
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.AssignParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AssignParticipantFilterHandler extends AbstractHandler {

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
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {

		//We need to preserve the expansion state and restore it afterwards
		final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
		final AssignParticipantFilter filter = ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView()
				.getActionSet()).getAssignedParticipantFilter();

		if (filter.getParticipant().equals("")) { //$NON-NLS-1$
			final String participant = UIUtils.getParticipantFilterInputDialog();
			if (participant.equals("")) { //$NON-NLS-1$
				return null;
			}
			filter.setParticipant(participant);
		}

		boolean oldValue = HandlerUtil.toggleCommandState(aEvent.getCommand());
		if (!oldValue) {
			R4EUIPlugin.Ftracer.traceInfo("Apply assigned filter for participant " + filter.getParticipant() //$NON-NLS-1$
					+ " to ReviewNavigator"); //$NON-NLS-1$
			viewer.addFilter(filter);
		} else {
			R4EUIPlugin.Ftracer.traceInfo("Remove assigned filter from ReviewNavigator"); //$NON-NLS-1$
			viewer.removeFilter(filter);
			filter.setParticipant(""); //$NON-NLS-1$
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isAssignedMyFilterSet()
				|| ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isUnassignedFilterSet()) {
			return false;
		}
		return true;
	}
}
