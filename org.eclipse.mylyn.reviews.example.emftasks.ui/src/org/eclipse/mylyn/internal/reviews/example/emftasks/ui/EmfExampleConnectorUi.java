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
package org.eclipse.mylyn.internal.reviews.example.emftasks.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleCorePlugin;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.ui.AbstractEmfConnectorUi;
import org.eclipse.mylyn.reviews.connector.ui.EmfRepositorySettingsPage;
import org.eclipse.mylyn.reviews.connector.ui.wizards.EmfRepositoryQueryPage;
import org.eclipse.mylyn.reviews.example.emftasks.presentation.EmfTasksModelWizard;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskSearchPage;
import org.eclipse.mylyn.tasks.ui.wizards.RepositoryQueryWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

/**
 * Connector specific UI for R4E.
 * 
 * @author Miles Parker
 */
public class EmfExampleConnectorUi extends AbstractEmfConnectorUi {

	public static final String[] FILE_NAME_EXTENSIONS = new String[] { "*.emftasks" };

	class InnerEmfRepositorySettingsPage extends EmfRepositorySettingsPage {

		public InnerEmfRepositorySettingsPage(TaskRepository taskRepository) {
			super("Emf Example Repository Settings", "Connect to a Repository File", taskRepository);
		}

		@Override
		public AbstractEmfConnectorUi getConnectorUi() {
			return EmfExampleConnectorUi.this;
		}

	}

	class InnerQueryPage extends EmfRepositoryQueryPage {

		public InnerQueryPage(TaskRepository repository, IRepositoryQuery query) {
			super(repository, "Emf Example Query", query);
		}

		@Override
		public AbstractEmfConnector getConnector() {
			return EmfExampleConnectorUi.this.getConnector();
		}

	}

	@Override
	public ITaskRepositoryPage getSettingsPage(TaskRepository taskRepository) {
		return new InnerEmfRepositorySettingsPage(taskRepository);
	}

	@Override
	public IWizard getQueryWizard(TaskRepository repository, IRepositoryQuery query) {
		RepositoryQueryWizard wizard = new RepositoryQueryWizard(repository);
		wizard.addPage(new InnerQueryPage(repository, query));
		return wizard;
	}

	@Override
	public ITaskSearchPage getSearchPage(TaskRepository repository, IStructuredSelection selection) {
		return new InnerQueryPage(repository, null);
	}

	@Override
	public ImageDescriptor getTaskKindOverlay(ITask task) {
		return EmfExampleImages.ECORE_OVERLAY;
	}

	@Override
	public AbstractEmfConnector getConnector() {
		return EmfExampleCorePlugin.getDefault().getConnector();
	}

	@Override
	public String[] getFileNameExtensions() {
		return FILE_NAME_EXTENSIONS;
	}

	@Override
	public IPath createNewRepository(Shell parent) {
		EmfTasksModelWizard wizard = new EmfTasksModelWizard();
		WizardDialog dialog = new WizardDialog(parent, wizard);
		int open = dialog.open();
		if (open != SWT.CANCEL && wizard.getModelFile() != null) {
			return wizard.getModelFile().getFullPath();
		}
		return null;
	}
}
