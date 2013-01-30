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
package org.eclipse.mylyn.internal.reviews.r4e.connector.support;

import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EClient;
import org.eclipse.mylyn.tasks.core.TaskRepository;

/**
 * @author Miles Parker
 */
public class R4EHarness {

	private final R4EFixture fixture;

	public R4EHarness(R4EFixture fixture) {
		this.fixture = fixture;
	}

	public R4EClient client() {
		return new R4EClient(new TaskRepository(fixture.getConnectorKind(), fixture.getRepositoryUrl()));
	}

	public void dispose() {
	}
}
