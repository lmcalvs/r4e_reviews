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
 * This class implements JUnit Test Cases for preferences 
 * 
 * Contributors:
 *   Sebastien Dubois - Initial Contribution for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings({ "restriction", "nls" })
public class PreferencesTests extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String PARENT_REVIEW_GROUP_NAME = "ParentReviewGroup";

	private static final String PARENT_REVIEW_GROUP_DESCRIPTION = "Parent Review Group Description";

	private static final String NONPARENT_REVIEW_GROUP_NAME = "NonParentReviewGroup";

	private static final String NONPARENT_REVIEW_GROUP_DESCRIPTION = "Non Parent Review Group Description";

	private static final String REVIEW_TEST_NAME = "ReviewTest";

	private static final String REVIEW_TEST_DESCRIPTION = "Review Test Description";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

	private R4EUIReviewGroup fParentGroup = null;

	private R4EUIReviewGroup fNonParentGroup = null;

	private R4EUIReviewBasic fReview = null;

	// ------------------------------------------------------------------------
	// Housekeeping
	// ------------------------------------------------------------------------

	/**
	 * Method suite - Sets up the global test environment, if not already done at the suite level.
	 * 
	 * @return Test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(PreferencesTests.class);
		return new R4ETestSetup(suite);
	}

	/**
	 * Method setUp - Sets up the fixture, for example, open a network connection. This method is called before a test
	 * is executed.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		fProxy = R4EUITestMain.getInstance();
		createReviewGroups();
		createReview();
	}

	/**
	 * Method tearDown
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		fProxy = null;
	}

	// ------------------------------------------------------------------------
	// Main test case
	// ------------------------------------------------------------------------

	/**
	 * Method testPreferences
	 */
	public void testPreferences() {
		TestUtils.waitForJobs();
		setReviewTreeTableDisplay();
		removeNonParentGroupFromPreferences();
		removeParentGroupFromPreferences();
	}

	// ------------------------------------------------------------------------
	// Helper functions
	// ------------------------------------------------------------------------

	/**
	 * Method createReviewGroups
	 */
	private void createReviewGroups() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewGroups");

		fParentGroup = null;
		fNonParentGroup = null;

		// Create Parent Review Group
		r4eAssert.setTest("Create Review Group");
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(PARENT_REVIEW_GROUP_NAME)) {
				fParentGroup = group;
				break;
			}
		}
		if (null == fParentGroup) {
			fParentGroup = fProxy.getReviewGroupProxy().createReviewGroup(
					TestUtils.FSharedFolder + File.separator + PARENT_REVIEW_GROUP_NAME, PARENT_REVIEW_GROUP_NAME,
					PARENT_REVIEW_GROUP_DESCRIPTION, "", new String[0], new String[0], new String[0]);
			r4eAssert.assertNotNull(fParentGroup);
			r4eAssert.assertEquals(PARENT_REVIEW_GROUP_NAME, fParentGroup.getReviewGroup().getName());
			r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
					+ PARENT_REVIEW_GROUP_NAME, fParentGroup.getReviewGroup().getFolder());
			r4eAssert.assertEquals(PARENT_REVIEW_GROUP_DESCRIPTION, fParentGroup.getReviewGroup().getDescription());
		}

		// Create Non-Parent Review Group
		r4eAssert.setTest("Create Non-Parent Review Group");
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(NONPARENT_REVIEW_GROUP_NAME)) {
				fNonParentGroup = group;
				break;
			}
		}
		if (null == fNonParentGroup) {
			fNonParentGroup = fProxy.getReviewGroupProxy().createReviewGroup(
					TestUtils.FSharedFolder + File.separator + NONPARENT_REVIEW_GROUP_NAME,
					NONPARENT_REVIEW_GROUP_NAME, NONPARENT_REVIEW_GROUP_DESCRIPTION, "", new String[0], new String[0],
					new String[0]);
			r4eAssert.assertNotNull(fNonParentGroup);
			r4eAssert.assertEquals(NONPARENT_REVIEW_GROUP_NAME, fNonParentGroup.getReviewGroup().getName());
			r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
					+ NONPARENT_REVIEW_GROUP_NAME, fNonParentGroup.getReviewGroup().getFolder());
			r4eAssert.assertEquals(NONPARENT_REVIEW_GROUP_DESCRIPTION, fNonParentGroup.getReviewGroup()
					.getDescription());
		}
	}

	/**
	 * Method createReview
	 */
	private void createReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReview");
		r4eAssert.setTest("Create Review");

		fReview = fProxy.getReviewProxy().createReview(fParentGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				REVIEW_TEST_NAME, REVIEW_TEST_DESCRIPTION, null, null, new String[0], null, null, null);
		r4eAssert.assertNotNull(fReview);
		r4eAssert.assertNotNull(fReview.getParticipantContainer());
		r4eAssert.assertNotNull(fReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		r4eAssert.assertEquals(REVIEW_TEST_NAME, fReview.getReview().getName());
		r4eAssert.assertEquals(REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		r4eAssert.assertTrue(fReview.isOpen());
	}

	// ------------------------------------------------------------------------
	// Set review tree table
	// ------------------------------------------------------------------------

	/**
	 * Method setReviewTreeTableDisplay
	 */
	private void setReviewTreeTableDisplay() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("setReviewTreeTableDisplay");
		r4eAssert.setTest("Change Display");

		fProxy.getCommandProxy().changeDisplay();
		r4eAssert.assertFalse(R4EUIModelController.getNavigatorView().isDefaultDisplay());
	}

	// ------------------------------------------------------------------------
	// Remove non-parent group tests
	// ------------------------------------------------------------------------

	/**
	 * Method removeNonParentGroupFromPreferences
	 */
	private void removeNonParentGroupFromPreferences() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("removeNonParentGroupFromPreferences");

		// Remove Review Group from preferences
		r4eAssert.setTest("Remove Review Group");
		String prefsGroup = fNonParentGroup.getReviewGroup().eResource().getURI().toFileString();
		fProxy.getPreferencesProxy().removeGroupFromPreferences(prefsGroup);
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(fNonParentGroup.getReviewGroup().getName())) {
				fail("Group " + prefsGroup + " should not be present since it was removed from preferences");
			}
		}
		r4eAssert.assertFalse(R4EUIModelController.getNavigatorView().isDefaultDisplay());
	}

	// ------------------------------------------------------------------------
	// Remove parent group tests
	// ------------------------------------------------------------------------

	/**
	 * Method removeParentGroupFromPreferences
	 */
	private void removeParentGroupFromPreferences() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("removeParentGroupFromPreferences");

		// Remove Review Group from preferences
		r4eAssert.setTest("Remove Review Group");
		String prefsGroup = fParentGroup.getReviewGroup().eResource().getURI().toFileString();
		fProxy.getPreferencesProxy().removeGroupFromPreferences(prefsGroup);
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(fParentGroup.getReviewGroup().getName())) {
				fail("Group " + prefsGroup + " should not be present since it was removed from preferences");
			}
		}
		r4eAssert.assertTrue(R4EUIModelController.getNavigatorView().isDefaultDisplay());
	}

}
