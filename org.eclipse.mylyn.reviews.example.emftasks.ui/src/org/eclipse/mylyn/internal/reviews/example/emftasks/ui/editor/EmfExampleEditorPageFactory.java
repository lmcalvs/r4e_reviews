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
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.example.emftasks.ui.editor;

import org.eclipse.mylyn.commons.ui.CommonImages;
import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleConnector;
import org.eclipse.mylyn.tasks.ui.TasksUiImages;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPageFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorInput;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.editor.IFormPage;

/**
 * @author Miles Parker
 */
public class EmfExampleEditorPageFactory extends AbstractTaskEditorPageFactory {

	@Override
	public boolean canCreatePageFor(TaskEditorInput input) {
		return (input.getTask().getConnectorKind().equals(EmfExampleConnector.CONNECTOR_KIND))
				|| (TasksUiUtil.isOutgoingNewTask(input.getTask(), EmfExampleConnector.CONNECTOR_KIND));
	}

	@Override
	public IFormPage createPage(TaskEditor parentEditor) {
		return new EmfTaskEditorPage(parentEditor);
	}

	@Override
	public Image getPageImage() {
		return CommonImages.getImage(TasksUiImages.REPOSITORY_SMALL);
	}

	@Override
	public String getPageText() {
		return "Emf Task";
	}

	@Override
	public int getPriority() {
		return PRIORITY_TASK;
	}
}
