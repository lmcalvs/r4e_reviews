/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Compare Editor menus command to navigate to the next
 * anomaly annotation
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class NextAnomalyAnnotationHandler extends AbstractHandler {

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
		final IEditorInput input = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getActiveEditor()
				.getEditorInput();
		if (input instanceof R4ECompareEditorInput) {
			((R4ECompareEditorInput) input).gotoNextAnnotation(R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID);
		}
		return null;
	}
}
