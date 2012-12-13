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

import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleConnector;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.ui.editors.AbstractAttributeEditor;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.AttributeEditorFactory;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;

/**
 * @author Miles Parker
 */
public class EmfTaskEditorPage extends AbstractTaskEditorPage {

	public EmfTaskEditorPage(TaskEditor editor) {
		super(editor, EmfExampleConnector.CONNECTOR_KIND);
		setNeedsPrivateSection(true);
	}

	@Override
	protected AttributeEditorFactory createAttributeEditorFactory() {
		return new AttributeEditorFactory(getModel(), getTaskRepository(), getEditorSite()) {
			@Override
			public AbstractAttributeEditor createEditor(String type, TaskAttribute taskAttribute) {
				try {
					return super.createEditor(type, taskAttribute);
				} catch (IllegalArgumentException e) {
					//ignore
				}
				return null;
			}
		};
	}
}
