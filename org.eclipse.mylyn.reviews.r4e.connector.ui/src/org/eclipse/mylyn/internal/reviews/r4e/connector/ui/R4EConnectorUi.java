/*******************************************************************************
 * Copyright (c) 2013 Ericsson
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
package org.eclipse.mylyn.internal.reviews.r4e.connector.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnectorPlugin;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.ui.AbstractEmfConnectorUi;
import org.eclipse.mylyn.reviews.connector.ui.wizards.EmfRepositoryQueryPage;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskRepositoryPage;
import org.eclipse.mylyn.tasks.ui.wizards.ITaskSearchPage;
import org.eclipse.mylyn.tasks.ui.wizards.NewTaskWizard;
import org.eclipse.mylyn.tasks.ui.wizards.RepositoryQueryWizard;
import org.eclipse.swt.widgets.Shell;

/**
 * Connector specific UI for R4E.
 * 
 * @author Miles Parker
 */
public class R4EConnectorUi extends AbstractEmfConnectorUi {

	class InnerQueryPage extends EmfRepositoryQueryPage {

		public InnerQueryPage(TaskRepository repository, IRepositoryQuery query) {
			super(repository, "R4E Query", query);
		}

		@Override
		public AbstractEmfConnector getConnector() {
			return R4EConnectorUi.this.getConnector();
		}

	}

	public R4EConnectorUi() {
		if (R4EUiPlugin.getDefault() != null) {
			R4EUiPlugin.getDefault().setConnectorUi(this);
		}
	}

	@Override
	public ITaskRepositoryPage getSettingsPage(TaskRepository taskRepository) {
		return new R4ERepositorySettingsPage(taskRepository);
	}

	@Override
	public IWizard getQueryWizard(TaskRepository repository, IRepositoryQuery query) {
		RepositoryQueryWizard wizard = new RepositoryQueryWizard(repository);
		wizard.addPage(new InnerQueryPage(repository, query));
		return wizard;
	}

	@Override
	public IWizard getNewTaskWizard(TaskRepository taskRepository, ITaskMapping taskSelection) {
		return new NewTaskWizard(taskRepository, taskSelection);
	}

	@Override
	public ITaskSearchPage getSearchPage(TaskRepository repository, IStructuredSelection selection) {
		return new InnerQueryPage(repository, null);
	}

	@Override
	public boolean hasSearchPage() {
		return true;
	}

	@Override
	public String getTaskKindLabel(ITask task) {
		return "Review"; //$NON-NLS-1$
	}

	@Override
	public ImageDescriptor getTaskKindOverlay(ITask task) {
		return R4EImages.OVERLAY_REVIEW;
	}

	@Override
	public AbstractEmfConnector getConnector() {
		return R4EConnectorPlugin.getDefault().getConnector();
	}

	@Override
	public String[] getFileNameExtensions() {
		return new String[] { "*.xrer" }; //$NON-NLS-1$
	}

	@Override
	public IPath createNewRepository(Shell parent) {
		// ignore
		return null;
	}
}
