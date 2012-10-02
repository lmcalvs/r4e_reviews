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
 * This class implements a Compare Editor menus command to navigate to the previous
 * anomaly annotation
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class PreviousOpenAnomalyAnnotationHandler extends AbstractHandler {

	/**
	 * Field fEditor.
	 */
	ITextEditor fEditor = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {
		final IEditorInput input = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getActiveEditor()
				.getEditorInput();
		if (input instanceof R4ECompareEditorInput) {
			final R4EAnnotation previousAnnotation = ((R4ECompareEditorInput) input).gotoPreviousAnnotation(R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID);
			if (null != previousAnnotation) {
				final IR4EUIModelElement element = previousAnnotation.getSourceElement();
				if (null != element) {
					R4EUIModelController.getNavigatorView().updateView(element, 0, false);
				}
			}
		}
		return null;
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isEnabled() {
		IEditorInput editorInput = null;
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (null != window) {
			final IWorkbenchPage page = window.getActivePage();
			if ((null != page) && (null != page.getActiveEditor())) {
				editorInput = page.getActiveEditor().getEditorInput();
				if (editorInput instanceof R4ECompareEditorInput) {
					return ((R4ECompareEditorInput) editorInput).isAnnotationsAvailable(R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID);
				}
			}
		}
		return false;
	}
}
