/*******************************************************************************
 * Copyright (c) 2013 Ericsson and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.repository.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class DeleteReviewHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// IHandler
	// ------------------------------------------------------------------------

	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Check if we are closing down
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}

		// Fire the command
		Shell shell = window.getShell();
		MessageBox mb = new MessageBox(shell);
		mb.setText(this.getClass().getSimpleName());
		mb.setMessage("Not implemented yet"); //$NON-NLS-1$
		mb.open();

		return null;
	}

}
