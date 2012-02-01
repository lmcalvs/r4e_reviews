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
 * This class implements the navigator view toolbar command to apply the 
 * review participant filter
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.filters;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.AssignParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.swt.widgets.Display;
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
	 * @param event
	 *            ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {

		//We need to preserve the expansion state and restore it afterwards
		final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
		final AssignParticipantFilter filter = ((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView()
				.getActionSet()).getAssignedParticipantFilter();

		if (filter.getParticipant().equals("")) {
			final String participant = getParticipantDialog();
			if (participant.equals("")) {
				return null;
			}
			filter.setParticipant(participant);
		}

		final Object[] elements = viewer.getExpandedElements();
		boolean oldValue = HandlerUtil.toggleCommandState(event.getCommand());

		if (!oldValue) {
			R4EUIPlugin.Ftracer.traceInfo("Apply assigned filter for participant " + filter.getParticipant()
					+ " to ReviewNavigator");
			viewer.addFilter(filter);
		} else {
			R4EUIPlugin.Ftracer.traceInfo("Remove assigned filter from ReviewNavigator");
			viewer.removeFilter(filter);
			filter.setParticipant("");
		}
		R4EUIModelController.getNavigatorView().getTreeViewer().setExpandedElements(elements);
		return null;
	}

	/**
	 * Display the dialog used by the user to enter the participant to use as filter criteria
	 * 
	 * @return String
	 */
	public String getParticipantDialog() {
		final InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(), "Set user name",
				"Enter user name to filter on", null, null);
		if (dlg.open() == Window.OK) {
			return dlg.getValue();
		}
		return "";
	}
}
