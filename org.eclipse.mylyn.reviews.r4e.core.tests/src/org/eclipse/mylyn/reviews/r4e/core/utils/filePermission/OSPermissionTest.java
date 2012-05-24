/**
 * Copyright (c) 2010, 2012 Ericsson
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * Alvaro Sanchez-Leon  - Initial implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.utils.filePermission;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;

/**
 * The class <code>WindowPermissionTest</code> contains tests for the class
 * {@link <code>WindowPermission</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 18/11/10 8:22 AM
 *
 * @author Alvaro Sanchez-Leon
 *
 * @version $Revision$
 */
public class OSPermissionTest extends TestCase {

	/**
	 * The object that is being tested.
	 *
	 * @see org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.WindowPermission
	 */
	private IFileSupportCommand perm = FileSupportCommandFactory.getInstance();

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public OSPermissionTest(String name) {
		super(name);
	}

	/**
	 * Perform pre-test initialization
	 *
	 * @throws Exception
	 *
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		TstGeneral.activateTracer();
	}

	/**
	 * Perform post-test clean up
	 *
	 * @throws Exception
	 *
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		// Add additional tear down code here
	}

	/**
	 * Run the boolean changePermission(String) method test
	 */
	public void testChangePermission() {
		String base = System.getProperty("java.io.tmpdir");
		if (!base.endsWith(File.separator)) {
			base = base + File.separator;
		}
		
		String dirStr = base + "PermDirTest" + File.separator;
		File dir = new File(dirStr);
		dir.mkdir();

		try {
			assertTrue(perm.grantWritePermission(dirStr));
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}

		try {
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}
	
	/**
	 * Run the boolean changePermission(String) method test
	 */
	public void testChangePermissionFailed() {
		// add test code here
		String base =  System.getProperty("java.io.tmpdir");
		if (!base.endsWith(File.separator)) {
			base = base + File.separator;
		}
		String dirStr = base + "PermDirTest" + File.separator;
		File dir = new File(dirStr);
		dir.mkdir();

		try {
			assertFalse(perm.grantWritePermission(dirStr + "1"));
		} catch (IOException e) {
			Activator.fTracer.traceDebug("IOException received as expected, the directory does not exist");
		}

		try {
			FileUtils.deleteDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	// /**
	// * File exist but not allowed to change it
	// */
	// public void testChangePermissionFailed2() {
	// String dirStr = "Y:" + File.separator + "solaris";
	//
	// try {
	// // pointing to a read only file/folder, permission denied
	// assertFalse(perm.grantWritePermission(dirStr));
	// // assertTrue(perm.grantWritePermission(dirStr));
	// } catch (IOException e) {
	// // no exception expected
	// e.printStackTrace();
	// fail("Exception");
	// }
	// }
}

/*$CPS$ This comment was generated by CodePro. Do not edit it.
 * patternId = com.instantiations.assist.eclipse.pattern.testCasePattern
 * strategyId = com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = 
 * assertTrue = false
 * callTestMethod = true
 * createMain = false
 * createSetUp = true
 * createTearDown = true
 * createTestFixture = true
 * createTestStubs = false
 * methods = changePermission(QString;)
 * package = org.eclipse.mylyn.reviews.r4e.core.utils.filePermission
 * package.sourceFolder = org.eclipse.mylyn.reviews.r4e.core.tests/src
 * superclassType = junit.framework.TestCase
 * testCase = WindowPermissionTest
 * testClassType = org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.WindowPermission
 */