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
 * This class implements the navigator view toolbar command to show the UI legend dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.R4EUILegendDialog;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ShowUiLegendHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Showing UI Legend..."")
	 */
	private static final String COMMAND_MESSAGE = "Showing UI Legend...";

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
	public Object execute(ExecutionEvent aEvent) {

		final UIJob job = new UIJob(COMMAND_MESSAGE) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(COMMAND_MESSAGE, 1);

				//Show UI Legend Dialog
				R4EUIPlugin.Ftracer.traceInfo("Showing UI Legend"); //$NON-NLS-1$
				final R4EUILegendDialog uiLegendDialog = new R4EUILegendDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow()
						.getShell());
				uiLegendDialog.open();

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
