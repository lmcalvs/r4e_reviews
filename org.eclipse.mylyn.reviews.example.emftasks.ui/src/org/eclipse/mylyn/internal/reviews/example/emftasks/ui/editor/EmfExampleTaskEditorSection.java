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

import org.eclipse.mylyn.internal.tasks.ui.editors.AbstractTaskEditorSection;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * @author Miles Parker
 */
public class EmfExampleTaskEditorSection extends AbstractTaskEditorSection {

	private Composite composite;

	public EmfExampleTaskEditorSection() {
		setPartName("Review");
	}

	@Override
	protected Control createContent(FormToolkit toolkit, Composite parent) {
		composite = toolkit.createComposite(parent);
		return composite;
	}

	@Override
	protected boolean shouldExpandOnCreate() {
		return true;
	}

	protected Shell getShell() {
		return getTaskEditorPage().getSite().getShell();
	}

	protected ITask getTask() {
		return getTaskEditorPage().getTask();
	}
}
