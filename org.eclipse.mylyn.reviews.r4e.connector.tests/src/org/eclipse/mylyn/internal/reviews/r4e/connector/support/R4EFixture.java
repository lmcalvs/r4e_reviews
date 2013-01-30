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

package org.eclipse.mylyn.internal.reviews.r4e.connector.support;

import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnector;
import org.eclipse.mylyn.internal.reviews.r4e.connector.tests.R4ETestConstants;
import org.eclipse.mylyn.tests.util.TestFixture;

/**
 * @author Steffen Pingel
 * @author Miles Parker
 */
public class R4EFixture extends TestFixture {

	public static R4EFixture R4E_GIT_TEST = new R4EFixture(R4ETestConstants.TEST_REVIEW_GROUP_URI, "1.0.0", ""); //$NON-NLS-1$ //$NON-NLS-2$ 

	public static R4EFixture DEFAULT = R4E_GIT_TEST;

	private static R4EFixture current;

	public R4EFixture(String url, String version, String description) {
		super(R4EConnector.CONNECTOR_KIND, url);
		setInfo(url, version, description);
	}

	public static R4EFixture current() {
		if (current == null) {
			DEFAULT.activate();
		}
		return current;
	}

	@Override
	protected R4EFixture activate() {
		current = this;
		setUpFramework();
		return this;
	}

	@Override
	protected R4EFixture getDefault() {
		return DEFAULT;
	}

	public R4EHarness harness() {
		return new R4EHarness(this);
	}

	public boolean canAuthenticate() {
		return true;
	}
}
