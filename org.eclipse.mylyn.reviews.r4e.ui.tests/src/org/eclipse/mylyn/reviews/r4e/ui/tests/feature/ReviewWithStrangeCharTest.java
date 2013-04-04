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
 * This class implements a JUnit Test Case when creating a review with 
 * non-alpha-numeric char or spaces in the review group and review name.
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestCase;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
@SuppressWarnings({ "restriction", "nls" })
public class ReviewWithStrangeCharTest extends R4ETestCase {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUIReviewGroup fGroup = null;

	private R4EUIReviewBasic fReview = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	private static final String TEST_SUITE_ID = "ReviewWithStrangeCharTest";

	public ReviewWithStrangeCharTest(String suite) {
		super(suite);
	}

	public ReviewWithStrangeCharTest() {
		super(TEST_SUITE_ID);
	}

	// ------------------------------------------------------------------------
	// Housekeeping
	// ------------------------------------------------------------------------

	/**
	 * @return Test the test suite
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(ReviewWithStrangeCharTest.class);
		return suite;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		createReviewGroups();
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fTestMain.getCommandProxy().toggleHideDeltasFilter();
		}
		createReviews();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// ------------------------------------------------------------------------
	// Main test case (pretty light...)
	// ------------------------------------------------------------------------

	/**
	 * Method testUseStrangeCharInGroupReview
	 * 
	 * @throws CoreException
	 */
	@org.junit.Test
	public void testUseStrangeCharInGroupReview() throws CoreException {
		TestUtils.waitForJobs();
	}

	// ------------------------------------------------------------------------
	// helper functions
	// ------------------------------------------------------------------------

	/**
	 * Method createReviewGroups
	 */
	private void createReviewGroups() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewGroups");

		fGroup = null;

		// Create Review Group
		r4eAssert.setTest("Create Review Group");
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(TestConstants.REVIEW_GROUP_TEST_NAME_STRANGE)) {
				fGroup = group;
				group.getName();
				break;
			}
		}
		if (null == fGroup) {
			fGroup = fTestMain.getReviewGroupProxy().createReviewGroup(
					fTestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME_STRANGE,
					TestConstants.REVIEW_GROUP_TEST_NAME_STRANGE, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
					TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
					TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
			r4eAssert.assertNotNull(fGroup);
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME_STRANGE, fGroup.getReviewGroup().getName());
			r4eAssert.assertEquals(new Path(fTestUtils.FSharedFolder).toPortableString() + "/" //$NON-NLS-1$
					+ TestConstants.REVIEW_GROUP_TEST_NAME_STRANGE, fGroup.getReviewGroup().getFolder());
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, fGroup.getReviewGroup()
					.getDescription());
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, fGroup.getReviewGroup()
					.getDefaultEntryCriteria());
			for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
				r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i], fGroup.getReviewGroup()
						.getAvailableProjects()
						.get(i));
			}
			for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
				r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i], fGroup.getReviewGroup()
						.getAvailableComponents()
						.get(i));
			}
			fGroup.getName();
		}
	}

	/**
	 * Method createReviews
	 */
	private void createReviews() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviews");

		r4eAssert.setTest("Open Review Group");
		if (!fGroup.isOpen()) {
			fTestMain.getCommandProxy().openElement(fGroup);
		}
		r4eAssert.assertTrue(fGroup.isOpen());

		r4eAssert.setTest("Create Review");
		fReview = fTestMain.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				TestConstants.REVIEW_STRANGE_NAME_INF, TestConstants.REVIEW_TEST_DESCRIPTION,
				TestConstants.REVIEW_TEST_DUE_DATE, TestConstants.REVIEW_TEST_PROJECT,
				TestConstants.REVIEW_TEST_COMPONENTS, TestConstants.REVIEW_TEST_ENTRY_CRITERIA,
				TestConstants.REVIEW_TEST_OBJECTIVES, TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fReview);
		r4eAssert.assertNotNull(fReview.getParticipantContainer());
		r4eAssert.assertNotNull(fReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		r4eAssert.assertEquals(TestConstants.REVIEW_STRANGE_NAME_INF, fReview.getReview().getName());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fReview.getReview().getComponents().get(i));
		}
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fReview.getReview().getEntryCriteria());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fReview.getReview().getObjectives());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fReview.getReview()
				.getReferenceMaterial());
		r4eAssert.assertTrue(fReview.isOpen());
	}

}
