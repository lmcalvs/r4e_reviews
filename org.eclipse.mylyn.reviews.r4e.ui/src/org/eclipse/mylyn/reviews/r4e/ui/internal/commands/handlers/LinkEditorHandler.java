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
 * This class implements the navigator view toolbar command to link the editor view
 * to the review navigator
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.progress.UIJob;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class LinkEditorHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Changing Editor Link Status..."")
	 */
	private static final String COMMAND_MESSAGE = "Changing Editor Link Status...";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	//NOTE:  This code is currently not used, but should be re-introduced with full linking between the navigator view 
	//		 ans the editors eventually
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

				//Expand tree (all levels)
				//We need to preserve the expansion state and restore it afterwards
				final Object[] elements = R4EUIModelController.getNavigatorView().getTreeViewer().getExpandedElements();

				boolean oldValue;
				try {
					final Command command = aEvent.getCommand();
					oldValue = HandlerUtil.toggleCommandState(command);
				} catch (ExecutionException e) {
					monitor.done();
					return Status.CANCEL_STATUS;
				}

				if (!oldValue) {
					R4EUIPlugin.Ftracer.traceInfo("Linking Editor with ReviewNavigator"); //$NON-NLS-1$
				} else {
					R4EUIPlugin.Ftracer.traceInfo("Unlinking Editor with ReviewNavigator"); //$NON-NLS-1$
				}
				R4EUIModelController.getNavigatorView().setEditorLinked(!oldValue);
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

}
