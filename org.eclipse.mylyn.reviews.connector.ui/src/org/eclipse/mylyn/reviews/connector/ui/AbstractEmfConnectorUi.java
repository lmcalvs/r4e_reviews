/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.reviews.connector.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.ui.wizards.NewTaskWizard;
import org.eclipse.swt.widgets.Shell;

/**
 * UI support needed for all EMF connector implementations.
 * 
 * @author Miles Parker
 */
public abstract class AbstractEmfConnectorUi extends AbstractRepositoryConnectorUi {

	@Override
	public IWizard getNewTaskWizard(TaskRepository taskRepository, ITaskMapping taskSelection) {
		return new NewTaskWizard(taskRepository, taskSelection);
	}

	/**
	 * Override to manage the process of creating a new repository and return the appropriate result. Typically this
	 * involves displaying a modified version of the EMF generated [Model]ModelWizard.
	 * 
	 * @param parent
	 *            TODO
	 * @return the full path to the new resource if user selected one, null if user cancelled.
	 */
	public abstract IPath createNewRepository(Shell parent);

	@Override
	public boolean hasSearchPage() {
		return true;
	}

	@Override
	public String getTaskKindLabel(ITask task) {
		return "Task";
	}

	@Override
	public ImageDescriptor getTaskKindOverlay(ITask task) {
		return EmfImages.ECORE_OVERLAY;
	}

	public abstract AbstractEmfConnector getConnector();

	/**
	 * Override to provide custom file extensions.
	 * 
	 * @return
	 */
	public abstract String[] getFileNameExtensions();

	@Override
	public String getConnectorKind() {
		return getConnector().getConnectorKind();
	}

}
