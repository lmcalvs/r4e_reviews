/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the common setup for R4E JUnit UI Test suite 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests;

import junit.framework.TestCase;

import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;

public class R4ETestCase extends TestCase {

	// ------------------------------------------------------------------------
	// Attributes
	// ------------------------------------------------------------------------

	protected R4EUITestMain fTestMain = null;

	protected TestUtils fTestUtils = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	protected R4ETestCase(String suite) {
		super(suite);
	}

	// ------------------------------------------------------------------------
	// TestCase
	// ------------------------------------------------------------------------

	/**
	 * Sets up the R4E test environment.
	 */
	@Override
	protected void setUp() throws Exception {
		// Setup the test proxy
		fTestMain = new R4EUITestMain();

		// Initialize the suite's TestUtils
		fTestUtils = TestUtils.get(getClass().getSimpleName());
		fTestUtils.setupTestEnvironment();
		fTestUtils.setDefaultUser(fTestMain);
		fTestUtils.startNavigatorView();
	}

	/**
	 * Tears down R4E test setup.
	 */
	@Override
	protected void tearDown() throws Exception {
		fTestMain = null;
		fTestUtils.stopNavigatorView();
		fTestUtils.cleanupTestEnvironment();
	}

}
