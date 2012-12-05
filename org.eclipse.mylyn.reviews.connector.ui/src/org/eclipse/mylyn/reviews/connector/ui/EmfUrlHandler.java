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
package org.eclipse.mylyn.reviews.connector.ui;

import org.eclipse.mylyn.commons.workbench.EditorHandle;
import org.eclipse.mylyn.commons.workbench.browser.AbstractUrlHandler;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.tasks.ui.TasksUi;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Provides basic url handling services. In simple case, just override returning actual connector implementation.
 * 
 * @author Miles Parker
 */
public abstract class EmfUrlHandler extends AbstractUrlHandler {

	@Override
	public EditorHandle openUrl(IWorkbenchPage page, String url, int customFlags) {
		AbstractEmfConnector connector = getEmfConnector();
		String taskId = connector.getTaskIdFromTaskUrl(url);
		String repositoryUrl = connector.getRepositoryUrlFromTaskUrl(url);
		for (TaskRepository repository : TasksUi.getRepositoryManager().getRepositories(connector.getConnectorKind())) {
			if (repository.getUrl().equals(repositoryUrl)) {
				return TasksUiUtil.openTaskWithResult(repository, taskId);
			}
		}
		return null;
	}

	protected abstract AbstractEmfConnector getEmfConnector();
}
