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

import org.eclipse.mylyn.reviews.connector.ui.AbstractEmfConnectorUi;
import org.eclipse.mylyn.reviews.connector.ui.EmfRepositorySettingsPage;
import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * Wizard page to specify R4E connection details.
 * 
 * @author Miles Parker
 */
public class R4ERepositorySettingsPage extends EmfRepositorySettingsPage {

	public R4ERepositorySettingsPage(TaskRepository taskRepository) {
		super("R4E Repository Settings", "Connect to a Mylyn Reviews R4E Review Group.", taskRepository);
	}

	@Override
	public AbstractEmfConnectorUi getConnectorUi() {
		return R4EUiPlugin.getDefault().getConnectorUi();
	}

	/**
	 * Supports R4E naming issue. Otherwise, not intended for extension.
	 * 
	 * @param browseResult
	 * @return
	 */
	@Override
	protected String getQualifiedName(String browseResult) {
		return browseResult + "_group_root";
	}
}
