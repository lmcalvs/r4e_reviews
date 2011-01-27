/**
 * Copyright (c) 2010 Ericsson
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
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.TestGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.Common.ResourceType;

/**
 * The class <code>CommonTest</code> contains tests for the class {@link
 * <code>Common</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 04/11/10 9:17 AM
 *
 * @author lmcalvs
 *
 * @version $Revision$
 */
public class CommonTest extends TestCase {

	R4EWriter	fixture	= SerializeFactory.getWriter();

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public CommonTest(String name) {
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
		// Add additional set up code here
		TestGeneral.activateTracer();
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

	public void testGetRootPath() {
		URI uri = URI.createURI("c:/folder/file.ext");

		uri = fixture.getFolderPath(uri);
		assertEquals("c:/folder", uri.toString());
	}

	/**
	 * Run the testToValidFileName test
	 */
	public void testToValidFileName() {
		String input = " ";
		String output;
		output = fixture.toValidFileName(input);
		assertEquals("_", output);

		input = "abc def/()_";
		output = fixture.toValidFileName(input);
		assertEquals("abc_def____", output);

		input = "";
		output = fixture.toValidFileName(input);
		assertEquals("", output);
	}

	public void testCreateReviewFileURI() {
		// review name or user id with special characters will be converted to a string usable for file and directory
		// names
		String name = "abc def \\/|z)l";
		// review.setName("normal Name");
		URI uriIn = URI.createURI("c:/folder");
		URI uriOut = null;

		// review folder added
		uriOut = fixture.createResourceURI(name, uriIn, ResourceType.REVIEW);
		assertEquals("c:/folder/abc_def____z_l/abc_def____z_l_review.xrer", uriOut.toString());

		// review folder does not need to be added
		uriOut = fixture.createResourceURI(name, uriIn, ResourceType.USER_COMMENT);
		assertEquals("c:/folder/abc_def____z_l_comments.xrer", uriOut.toString());
	}

	/**
	 * Run the List<URI> selectFiles(URI, Pattern) method test
	 */
	public void testSelectFiles() {
		// Prepare
		String path = "testSelectFiles";

		File folder = new File(path);
		folder.mkdir();

		List<File> fileList = new ArrayList<File>();
		fileList.add(new File(path + "/abd_124_group_reviews.xrer"));
		fileList.add(new File(path + "/abd_124_group_.xrer"));
		fileList.add(new File(path + "/abd_124_group_.xrer.xml"));
		try {
			for (File tfile : fileList) {
				tfile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Execute
		R4EReader fReader = new R4EReader();
		URI aFolder = URI.createFileURI(path);
		List<URI> result = fReader.selectUsrReviewGroupRes(aFolder);

		// Verify
		assertNotNull(result);
		assertEquals(1, result.size());
		String expected = URI.createFileURI(fileList.get(0).toString()).toString();
		assertEquals(expected, result.get(0).toString());

		// Clean up
		for (File rfile : fileList) {
			rfile.delete();
		}
		folder.delete();
	}

	public void testIsGroupURI() {
		// Prepare
		String path = "testSelectFiles";

		File folder = new File(path);
		folder.mkdir();

		List<File> fileList = new ArrayList<File>();
		fileList.add(new File(path + "/abd_124_group_reviews.xrer"));
		fileList.add(new File(path + "/abd_124_group_.xrer"));
		fileList.add(new File(path + "/abd_124__group_root.xrer.xml"));
		fileList.add(new File(path + "/abd_124__group_root.xrer"));
		try {
			for (File tfile : fileList) {
				tfile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Execute
		R4EReader fReader = new R4EReader();
		URI aFileUri = URI.createFileURI(fileList.get(0).toString());
		Boolean result = fReader.isGroupResourceUri(aFileUri);
		assertEquals(false, result.booleanValue());

		aFileUri = URI.createFileURI(fileList.get(1).toString());
		result = fReader.isGroupResourceUri(aFileUri);
		assertEquals(false, result.booleanValue());

		aFileUri = URI.createFileURI(fileList.get(2).toString());
		result = fReader.isGroupResourceUri(aFileUri);
		assertEquals(false, result.booleanValue());

		aFileUri = URI.createFileURI(fileList.get(3).toString());
		result = fReader.isGroupResourceUri(aFileUri);
		assertEquals(true, result.booleanValue());

		// Clean up
		for (File rfile : fileList) {
			rfile.delete();
		}
		folder.delete();
		// isGroupResourceUri
	}
}

/*$CPS$ This comment was generated by CodePro. Do not edit it.
 * patternId = com.instantiations.assist.eclipse.pattern.testCasePattern
 * strategyId = com.instantiations.assist.eclipse.pattern.testCasePattern.junitTestCase
 * additionalTestNames = testToValidFileName
 * assertTrue = false
 * callTestMethod = true
 * createMain = false
 * createSetUp = true
 * createTearDown = true
 * createTestFixture = false
 * createTestStubs = false
 * methods = 
 * package = org.eclipse.mylyn.reviews.r4e.core.model.serial.impl
 * package.sourceFolder = org.eclipse.mylyn.reviews.r4e.core.tests/src
 * superclassType = junit.framework.TestCase
 * testCase = CommonTest
 * testClassType = org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.Common
 */