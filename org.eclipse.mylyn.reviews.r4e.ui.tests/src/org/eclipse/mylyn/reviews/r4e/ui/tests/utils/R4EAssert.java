/*******************************************************************************
 * Copyright (c) 2013 Ericsson Research Canada and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.utils;

import junit.framework.Assert;

/**
 * R4E utility to provide some additional location information to tests.
 * 
 * @author lmcfrch
 */
public class R4EAssert {

	// --------------------------------------------------------------------
	// The test information data
	// --------------------------------------------------------------------

	private final String fSuite;

	private String fTest;

	private int fStep;

	// --------------------------------------------------------------------
	// Constructor 
	// --------------------------------------------------------------------

	public R4EAssert(String suite) {
		fSuite = suite;
		fTest = ""; //$NON-NLS-1$
		fStep = 0;
	}

	// --------------------------------------------------------------------
	// Operations 
	// --------------------------------------------------------------------

	public void setTest(String test) {
		fTest = test;
		fStep = 1;
	}

	// --------------------------------------------------------------------
	// Helper functions 
	// --------------------------------------------------------------------

	@SuppressWarnings("nls")
	private String fmtId() {
		return "[" + fSuite + ":" + fTest + ":" + fStep++ + "]";
	}

	// --------------------------------------------------------------------
	// Asserts with step increment - directly mapped on JUnit's Assert
	// --------------------------------------------------------------------

	public void assertTrue(boolean condition) {
		Assert.assertTrue(fmtId(), condition);
	}

	public void assertFalse(boolean condition) {
		Assert.assertFalse(fmtId(), condition);
	}

	public void assertNull(Object object) {
		Assert.assertNull(fmtId(), object);
	}

	public void assertNotNull(Object object) {
		Assert.assertNotNull(fmtId(), object);
	}

	public void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(fmtId(), expected, actual);
	}

	public void assertNotSame(Object expected, Object actual) {
		Assert.assertNotSame(fmtId(), expected, actual);
	}

}
