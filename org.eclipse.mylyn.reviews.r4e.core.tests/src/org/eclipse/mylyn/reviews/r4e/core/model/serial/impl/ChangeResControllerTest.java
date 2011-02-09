/*******************************************************************************
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
 *   Alvaro Sanchez-Leon  - First API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;

//import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.

/**
 * The class <code>ChangeResControllerTest</code> contains tests for the class
 * {@link <code>ChangeResController</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 22/11/10 8:47 AM
 *
 * @author lmcalvs
 *
 * @version $Revision$
 */
public class ChangeResControllerTest extends TestCase {

	/**
	 * The object that is being tested.
	 *
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ChangeResController
	 */
	private ChangeResController resCont = new ChangeResController();
	private RModelFactoryExt	fFactory	= SerializeFactory.getModelExtension();
	private final static String	TEST_DIR	= "dir";
	private final static String	USER1		= "userX";
	private final static String	USER2		= "userY";
	private final static String	GROUP_NAME	= "Test Group";

	//
	private Long				fBookingNumber		= 0L;
	private R4EReviewGroup		fResourceElement	= null;

	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public ChangeResControllerTest(String name) {
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
		// Activate Traces
		TstGeneral.activateTracer();

		// Create resource and initiate check-out
		try {
			fResourceElement = fFactory.createR4EReviewGroup(URI.createURI(TEST_DIR), GROUP_NAME);
		} catch (ResourceHandlingException e1) {
			e1.printStackTrace();
		}

		try {
			fBookingNumber = resCont.checkOut(fResourceElement, USER1);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}
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

		// Clean up test directory
		File dir = new File(TEST_DIR);
		if (dir.exists()) {
			FileUtils.deleteDirectory(dir);
		}
	}

	/**
	 * Run the Long checkOut(EObject, String) method test
	 */
	public void testCheckOut() {
		// check checkout status
		assertNotSame(0L, fBookingNumber);

		// Checking it out by same user should return with same result
		Long newBookingNum = 0L;
		try {
			newBookingNum = resCont.checkOut(fResourceElement, USER1);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		// The old and new booking numbers shall match since the checkout comes from the same user
		assertEquals(fBookingNumber, newBookingNum);

	}

	/**
	 * Attempt to checkout a resource already checkout by a different user shall not got through and shal be reported to
	 * the ui
	 */
	public void testAlreadyCheckedOut() {
		// Checking it out by same user should return with same result
		String message = null;
		try {
			resCont.checkOut(fResourceElement, USER2);
		} catch (ResourceHandlingException e) {
			message = e.getMessage();
		}

		assertNotNull(message);
		assertTrue(message.contains("is locked"));
	}

	/**
	 * Run the void checkIn(Long) method test
	 */
	public void testCheckIn() {
		String newGroupName = "new group name";
		// Modify the resource
		fResourceElement.setName(newGroupName);

		// attempt checking in with an invalid booking number, no exception generated
		try {
			resCont.checkIn(10000L);
		} catch (ResourceHandlingException e) {
			// Unexpected exception
			e.printStackTrace();
		}

		// Successful checkIn expected
		try {
			resCont.checkIn(fBookingNumber);
		} catch (ResourceHandlingException e) {
			// Unexpected exception
			e.printStackTrace();
			fail("Exception");
		}

		// Load the resource to validate the update
		R4EReviewGroup group = null;
		try {
			group = fFactory.openR4EReviewGroup(fResourceElement.eResource().getURI());
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// validate updated value read from disk
		assertEquals(newGroupName, group.getName());

		// Validate that the file can be checkout once more i.e. lock is being removed
		try {
			Long bookingNum = resCont.checkOut(fResourceElement, USER2);
			resCont.checkIn(bookingNum);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	/**
	 * Run the void undoCheckOut(Long) method test
	 */
	public void testUndoCheckOut() {
		// attempt undo checkout of invalid booking
		Long aBookingNumber = 1000L;
		try {
			resCont.undoCheckOut(aBookingNumber);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// Undo checkout with real booking number
		try {
			resCont.undoCheckOut(fBookingNumber);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// Validate that the file can be checkout once more i.e. lock is being removed
		try {
			Long bookingNum = resCont.checkOut(fResourceElement, USER2);
			resCont.checkIn(bookingNum);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}
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
 * createTestStubs = true
 * methods = 
 * package = org.eclipse.mylyn.reviews.r4e.core.model.serial.impl
 * package.sourceFolder = org.eclipse.mylyn.reviews.r4e.core/src
 * superclassType = junit.framework.TestCase
 * testCase = ChangeResControllerTest
 * testClassType = ChangeResController
 */