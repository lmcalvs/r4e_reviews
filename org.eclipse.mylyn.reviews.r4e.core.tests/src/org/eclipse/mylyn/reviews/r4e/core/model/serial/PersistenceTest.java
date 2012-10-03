/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial API and Implementation
 *******************************************************************************/

/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.mylyn.reviews.r4e.core.TstGeneral;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.junit.After;
import org.junit.Before;

/**
 * @author Alvaro Sanchez-Leon
 * 
 */
public class PersistenceTest extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static final URI		fGoldenGroup	= TstGeneral.GOLDEN_GROUP_FILE;
	private static File				fTestDir;

	// ------------------------------------------------------------------------
	// Instance Variables
	// ------------------------------------------------------------------------
	private final RModelFactoryExt	fResFactory		= SerializeFactory.getModelExtension();
	private final ResourceUpdater	fUpdater		= SerializeFactory.getResourceSetUpdater();
	private R4EReviewGroup			fGroup			= null;
	private static File				fRootTestDir	= null;
	private final String			fSep			= File.separator;

	// ------------------------------------------------------------------------
	// RWCommon
	// ------------------------------------------------------------------------

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		String base = System.getProperty("java.io.tmpdir");
		if (!base.endsWith(File.separator)) {
			base = base + File.separator;
		}
		
		fRootTestDir = new File(base + "r4eTst");

		// Resolve golden group storage dir
		String rootFile = fGoldenGroup.lastSegment();
		File goldenDir = new File(URI.decode(fGoldenGroup.trimSegments(1).devicePath()));

		// Take the directory name to be used as copy destination
		String testDir = fRootTestDir.toString() + "OutL1" + fSep + "OutL2" + fSep + goldenDir.getName();
		fTestDir = new File(testDir);

		// Determine the location of the group file in the destination folder
		URI testRootURI = URI.createFileURI(fTestDir.getAbsolutePath());
		testRootURI = testRootURI.appendSegment(rootFile);

		// Copy golden dir to test dir
		FileUtils.copyDirectory(goldenDir, fTestDir);

		// Load
		try {
			fGroup = fResFactory.openR4EReviewGroup(testRootURI);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		// Tracing on
		TstGeneral.activateTracer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		fResFactory.closeR4EReviewGroup(fGroup);
		// remove test directory
		if (fRootTestDir != null && fRootTestDir.exists()) {
			FileUtils.deleteDirectory(fRootTestDir);
		}
	}

	// ------------------------------------------------------------------------
	// TEST METHODS
	// ------------------------------------------------------------------------
	public void testUpdate() {
		// extract test fixture
		R4EReview review = (R4EReview) fGroup.getReviews().get(0);
		String revName = review.getName();
		try {
			fResFactory.openR4EReview(fGroup, revName);
		} catch (ResourceHandlingException e1) {
			e1.printStackTrace();
			fail("Exception");
		} catch (CompatibilityException e) {
			e.printStackTrace();
			fail("Exception");
		}

		R4EUser user = (R4EUser) review.getUsersMap().values().iterator().next();
		// Check-out element before Persistent modification
		Long bookingNum = 0L;
		try {
			bookingNum = fUpdater.checkOut(review, user.getId());
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (OutOfSyncException e) {
			e.printStackTrace();
			fail("Exception");
		}


		// Modify element
		int spentTime = 100;
		R4EReviewDecision decision = RModelFactory.eINSTANCE.createR4EReviewDecision();
		decision.setSpentTime(spentTime);
		decision.setValue(R4EDecision.R4E_REVIEW_DECISION_ACCEPTED);
		review.setDecision(decision);

		// Check-In and Save
		try {
			fUpdater.checkIn(bookingNum);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// Reload persisted information before verification
		Resource groupRes = fGroup.eResource();
		URI groupURI = groupRes.getURI();
		fResFactory.closeR4EReviewGroup(fGroup);
		// Reload group
		try {
			fGroup = fResFactory.openR4EReviewGroup(groupURI);
			review = fResFactory.openR4EReview(fGroup, revName);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (CompatibilityException e) {
			e.printStackTrace();
			fail("Exception");
		}

		// Verify that change was persisted
		decision = review.getDecision();
		assertEquals(spentTime, decision.getSpentTime());
		assertEquals(R4EDecision.R4E_REVIEW_DECISION_ACCEPTED, decision.getValue());
	}

	/**
	 * Testing writing permissions
	 */
	public void testReadWritePermissions() {
		String dirName = fRootTestDir.toString() + "tFolder";
		File tfolder = new File(dirName);
		tfolder.mkdir();

		URI uri = URI.createURI(dirName);

		boolean result = false;
		try {
			result = fResFactory.testWritePermissions(uri);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertEquals(true, result);
		try {
			FileUtils.deleteDirectory(tfolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}