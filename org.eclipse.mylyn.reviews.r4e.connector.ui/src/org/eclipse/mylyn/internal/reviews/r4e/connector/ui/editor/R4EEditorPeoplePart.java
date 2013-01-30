/*******************************************************************************
 * Copyright (c) 2013 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.reviews.r4e.connector.ui.editor;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnector;
import org.eclipse.mylyn.internal.tasks.ui.editors.EditorUtil;
import org.eclipse.mylyn.internal.tasks.ui.editors.MultiSelectionAttributeEditor;
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.ui.editors.AbstractAttributeEditor;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Supports custom display of review participants.
 * 
 * @author Steffen Pingel
 * @author Miles Parker
 */
@SuppressWarnings("restriction")
public class R4EEditorPeoplePart extends AbstractTaskEditorPart {

	private static final int COLUMN_MARGIN = 5;

	public R4EEditorPeoplePart() {
		setPartName("Reviewers");
	}

	private void addAttribute(Composite composite, FormToolkit toolkit, TaskAttribute attribute) {
		AbstractAttributeEditor editor = createAttributeEditor(attribute);
		if (editor != null) {
			editor.createLabelControl(composite, toolkit);
			GridDataFactory.defaultsFor(editor.getLabelControl())
					.indent(COLUMN_MARGIN, 0)
					.applyTo(editor.getLabelControl());
			editor.createControl(composite, toolkit);
			getTaskEditorPage().getAttributeEditorToolkit().adapt(editor);

			GridDataFactory gridDataFactory = GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.TOP);

			if (editor instanceof MultiSelectionAttributeEditor) {
				gridDataFactory.hint(SWT.DEFAULT, 95);
			}

			gridDataFactory.applyTo(editor.getControl());
		}
	}

	@Override
	public void createControl(Composite parent, FormToolkit toolkit) {
		Section section = createSection(parent, toolkit, true);

		Composite peopleComposite = toolkit.createComposite(section);
		GridLayout layout = EditorUtil.createSectionClientLayout();
		layout.numColumns = 2;
		peopleComposite.setLayout(layout);

		addAttribute(peopleComposite, toolkit,
				getTaskData().getRoot().getMappedAttribute(DefaultTaskSchema.getInstance().USER_ASSIGNED.getKey()));
		addAttribute(peopleComposite, toolkit, getTaskData().getRoot().getAttribute(R4EConnector.R4E_PARTICIPANTS_KEY));

		toolkit.paintBordersFor(peopleComposite);
		section.setClient(peopleComposite);
		setSection(toolkit, section);
	}
}
