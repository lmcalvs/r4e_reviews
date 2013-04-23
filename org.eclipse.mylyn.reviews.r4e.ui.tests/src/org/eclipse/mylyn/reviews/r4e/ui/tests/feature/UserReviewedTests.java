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
 * This class implements JUnit Test Cases for R4E User Reviewed feature
 * 
 * Contributors:
 *   Sebastien Dubois - Initial Contribution for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings({ "restriction", "nls" })
public class UserReviewedTests extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String PARENT_REVIEW_GROUP_NAME = "ParentReviewGroup";

	private static final String PARENT_REVIEW_GROUP_DESCRIPTION = "Parent Review Group Description";

	private static final String REVIEW_TEST_NAME = "ReviewTest";

	private static final String REVIEW_TEST_DESCRIPTION = "Review Test Description";

	private static final String DELTA1_NAME = "Line 18";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

	private R4EUIReviewGroup fReviewGroup = null;

	private R4EUIReviewBasic fReview = null;

	private R4EUIReviewItem fItem = null;

	private R4EUIReviewItem fItem2 = null;

	private R4EUIReviewItem fItem3 = null;

	private R4EUIParticipant fParticipant = null;

	private String fItemName = null;

	private String fItemName2 = null;

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
		suite.addTestSuite(UserReviewedTests.class);
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
		createReviewItems();
		createParticipants();
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fProxy.getCommandProxy().toggleHideDeltasFilter();
		}
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
	 * Method testUserReviewed
	 * 
	 * @throws CoreException
	 */
	public void testUserReviewed() throws CoreException {
		TestUtils.waitForJobs();
		setDelta();
		setAllDeltas();
		setFile();
		setAllFiles();
		setReviewItem();
		addReviewItemFromOtherUser();
		unsetDelta();
		unsetReviewItem();
		unsetFile();
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

		fReviewGroup = null;

		// Create Parent Review Group
		r4eAssert.setTest("Create Review Group");
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(PARENT_REVIEW_GROUP_NAME)) {
				fReviewGroup = group;
				break;
			}
		}
		if (null == fReviewGroup) {
			fReviewGroup = fProxy.getReviewGroupProxy().createReviewGroup(
					TestUtils.FSharedFolder + File.separator + PARENT_REVIEW_GROUP_NAME, PARENT_REVIEW_GROUP_NAME,
					PARENT_REVIEW_GROUP_DESCRIPTION, "", new String[0], new String[0], new String[0]);
			r4eAssert.assertNotNull(fReviewGroup);
			r4eAssert.assertEquals(PARENT_REVIEW_GROUP_NAME, fReviewGroup.getReviewGroup().getName());
			r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
					+ PARENT_REVIEW_GROUP_NAME, fReviewGroup.getReviewGroup().getFolder());
			r4eAssert.assertEquals(PARENT_REVIEW_GROUP_DESCRIPTION, fReviewGroup.getReviewGroup().getDescription());
		}
	}

	/**
	 * Method createReview
	 */
	private void createReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReview");

		r4eAssert.setTest("Create Review");
		fReview = fProxy.getReviewProxy().createReview(fReviewGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				REVIEW_TEST_NAME, REVIEW_TEST_DESCRIPTION, null, null, new String[0], null, null, null);
		r4eAssert.assertNotNull(fReview);
		r4eAssert.assertNotNull(fReview.getParticipantContainer());
		r4eAssert.assertNotNull(fReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		r4eAssert.assertEquals(REVIEW_TEST_NAME, fReview.getReview().getName());
		r4eAssert.assertEquals(REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		r4eAssert.assertTrue(fReview.isOpen());
	}

	/**
	 * Method createReviewItems
	 */
	private void createReviewItems() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewItems");

		r4eAssert.setTest("Create Commit Item");
		fItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		fItem2 = fProxy.getItemProxy().createCommitItem(TestUtils.FCIProject, 0);
		fItemName = fItem.getName();
		fItemName2 = fItem2.getName();

		fProxy.getCommandProxy().closeElement(fReview);
		fProxy.getCommandProxy().openElement(fReview);
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
			}
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
			}
		}

		// Now validate
		r4eAssert.assertNotNull(fItem);
		r4eAssert.assertNotNull(fItem2);
	}

	/**
	 * Method createParticipants
	 */
	private void createParticipants() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createParticipants");

		r4eAssert.setTest("Create Participant");
		List<R4EParticipant> participants = new ArrayList<R4EParticipant>(1);
		R4EParticipant participant = RModelFactory.eINSTANCE.createR4EParticipant();
		participant.setId(TestConstants.PARTICIPANT_TEST_ID);
		participant.setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);
		participants.add(participant);
		fParticipant = fProxy.getParticipantProxy().createParticipant(fReview.getParticipantContainer(), participants);
		r4eAssert.assertNotNull(fParticipant);
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fParticipant.getParticipant().getId());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, fParticipant.getParticipant().getEmail());
	}

	// ------------------------------------------------------------------------
	// Set Delta tests
	// ------------------------------------------------------------------------

	/**
	 * Method setDelta
	 */
	@SuppressWarnings("null")
	private void setDelta() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("setDelta");

		r4eAssert.setTest("Get File");
		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		// Mark first delta as reviewed
		r4eAssert.setTest("Mark Delta");
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				fProxy.getCommandProxy().changeReviewedState(delta);
				break;
			}
		}

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Review Item");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);
		r4eAssert.assertFalse(fReview.isUserReviewed());
		r4eAssert.assertFalse(fItem.isUserReviewed());
		r4eAssert.assertFalse(file.isUserReviewed());
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				r4eAssert.assertTrue(delta.isUserReviewed());
				//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(delta, true,
				//		R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
				break;
			}
		}
	}

	// ------------------------------------------------------------------------
	// Set All Delta tests
	// ------------------------------------------------------------------------

	/**
	 * Method setAllDeltas
	 */
	@SuppressWarnings("null")
	private void setAllDeltas() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("setAllDeltas");

		r4eAssert.setTest("Get File");
		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		// Mark remaining deltas as reviewed
		r4eAssert.setTest("Marks Deltas As Reviewed");
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			//Skip already marked delta
			if (delta.getName().equals(DELTA1_NAME)) {
				continue;
			}
			fProxy.getCommandProxy().changeReviewedState(delta);
		}

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Review Items");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);
		r4eAssert.assertFalse(fReview.isUserReviewed());
		r4eAssert.assertFalse(fItem.isUserReviewed());
		r4eAssert.assertTrue(file.isUserReviewed());
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
		//		file.getContentsContainerElement().getChildren(), true, R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			r4eAssert.assertTrue(delta.isUserReviewed());
		}
	}

	// ------------------------------------------------------------------------
	// Set File tests
	// ------------------------------------------------------------------------

	/**
	 * Method setFile
	 */
	@SuppressWarnings("null")
	private void setFile() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("setFile");

		r4eAssert.setTest("Get File");
		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		// Mark file as reviewed
		r4eAssert.setTest("Mark File as Reviewed");
		fProxy.getCommandProxy().changeReviewedState(file);

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check File");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);
		r4eAssert.assertFalse(fReview.isUserReviewed());
		r4eAssert.assertFalse(fItem.isUserReviewed());
		r4eAssert.assertTrue(file.isUserReviewed());
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertTrue(file.isUserReviewed());
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
		//		file.getContentsContainerElement().getChildren(), true, R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			r4eAssert.assertTrue(delta.isUserReviewed());
		}
	}

	// ------------------------------------------------------------------------
	// Set All Files tests
	// ------------------------------------------------------------------------

	/**
	 * Method setAllFiles
	 */
	private void setAllFiles() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("setAllFiles");

		r4eAssert.setTest("Mark All Files as Reviewed");
		for (IR4EUIModelElement file : fItem.getChildren()) {
			if (file.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)
					|| file.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				continue;
			}
			fProxy.getCommandProxy().changeReviewedState(file);
		}

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Files");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);
		r4eAssert.assertFalse(fReview.isUserReviewed());
		r4eAssert.assertTrue(fItem.isUserReviewed());
		for (IR4EUIModelElement file : fItem.getChildren()) {
			r4eAssert.assertTrue(file.isUserReviewed());
			for (R4EUIContent delta : ((R4EUIFileContext) file).getContentsContainerElement().getContentsList()) {
				r4eAssert.assertTrue(delta.isUserReviewed());
			}
		}
	}

	// ------------------------------------------------------------------------
	// Set Review Item tests
	// ------------------------------------------------------------------------

	/**
	 * Method setReviewItem
	 */
	private void setReviewItem() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("setReviewItem");

		r4eAssert.setTest("Setup Review State");
		fItem2 = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
				break;
			}
		}
		fProxy.getCommandProxy().changeReviewedState(fItem2);

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Review State");
		r4eAssert.assertTrue(fReview.isUserReviewed());
		for (IR4EUIModelElement item : fReview.getChildren()) {
			if (item instanceof R4EUIReviewItem) {
				r4eAssert.assertTrue(item.isUserReviewed());
				for (IR4EUIModelElement file : item.getChildren()) {
					r4eAssert.assertTrue(file.isUserReviewed());
					for (R4EUIContent delta : ((R4EUIFileContext) file).getContentsContainerElement().getContentsList()) {
						r4eAssert.assertTrue(delta.isUserReviewed());
					}
				}
			}
		}
	}

	// ------------------------------------------------------------------------
	// Add Review Item tests
	// ------------------------------------------------------------------------

	/**
	 * Method addReviewItemFromOtherUser
	 * 
	 * @throws CoreException
	 */
	private void addReviewItemFromOtherUser() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("addReviewItemFromOtherUser");

		r4eAssert.setTest("Close Element");
		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());

		// Change user
		r4eAssert.setTest("Change User");
		String originalUser = fProxy.getPreferencesProxy().getUser();
		String originalEmail = fProxy.getPreferencesProxy().getEmail();
		fProxy.getPreferencesProxy().setUser(TestConstants.PARTICIPANT_TEST_ID);
		fProxy.getPreferencesProxy().setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);

		// New user adds new review item
		r4eAssert.setTest("New User Adds New Review Item");
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());
		fItem3 = fProxy.getItemProxy().createCommitItem(TestUtils.FTextIProject, 0);
		r4eAssert.assertNotNull(fItem3);
		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());

		// Change back user to original one
		r4eAssert.setTest("Change back to Original User");
		fProxy.getPreferencesProxy().setUser(originalUser);
		fProxy.getPreferencesProxy().setEmail(originalEmail);

		// Verify Reviewed State
		r4eAssert.setTest("Verify State");
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());
		r4eAssert.assertFalse(fReview.isUserReviewed());
	}

	// ------------------------------------------------------------------------
	// Unset Delta tests
	// ------------------------------------------------------------------------

	/**
	 * Method unsetDelta
	 */
	@SuppressWarnings("null")
	private void unsetDelta() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("unsetDelta");

		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fProxy.getCommandProxy().toggleHideDeltasFilter();
		}
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}

		r4eAssert.setTest("Get File");
		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		// Unmark first delta as reviewed
		r4eAssert.setTest("Setup Review State");
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				fProxy.getCommandProxy().changeReviewedState(delta);
				break;
			}
		}

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Review State");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		r4eAssert.assertFalse(fReview.isUserReviewed());
		r4eAssert.assertFalse(fItem.isUserReviewed());
		r4eAssert.assertFalse(file.isUserReviewed());
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				r4eAssert.assertFalse(delta.isUserReviewed());
				//r4eAssert.assertFalse(fProxy.getCommandProxy().verifyAnnotation(delta, true,
				//		R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
				//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(delta, true,
				//		R4EUIConstants.DELTA_ANNOTATION_ID));
				break;
			}
		}
	}

	// ------------------------------------------------------------------------
	// Unset Review Item tests
	// ------------------------------------------------------------------------

	/**
	 * Method unsetReviewItem
	 */
	private void unsetReviewItem() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("unsetReviewItem");

		r4eAssert.setTest("Setup Review State");
		fItem2 = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
				break;
			}
		}
		fProxy.getCommandProxy().changeReviewedState(fItem2);
		r4eAssert.assertFalse(fItem2.isUserReviewed());

		r4eAssert.setTest("Close/Open Review Element");
		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Review State");
		r4eAssert.assertFalse(fReview.isUserReviewed());
		fItem2 = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
				break;
			}
		}
		for (IR4EUIModelElement file : fItem2.getChildren()) {
			r4eAssert.assertFalse(file.isUserReviewed());
			for (R4EUIContent delta : ((R4EUIFileContext) file).getContentsContainerElement().getContentsList()) {
				r4eAssert.assertFalse(delta.isUserReviewed());
			}
		}
	}

	// ------------------------------------------------------------------------
	// Unset File tests
	// ------------------------------------------------------------------------

	/**
	 * Method unsetFile
	 */
	@SuppressWarnings("null")
	private void unsetFile() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("unsetFile");

		r4eAssert.setTest("Get File");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);

		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		// Mark file as reviewed
		r4eAssert.setTest("Setup Review State");
		fProxy.getCommandProxy().changeReviewedState(file);

		fProxy.getCommandProxy().closeElement(fReview);
		r4eAssert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		r4eAssert.assertTrue(fReview.isOpen());

		r4eAssert.setTest("Check Review State");
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		r4eAssert.assertNotNull(file);

		r4eAssert.assertFalse(fReview.isUserReviewed());
		r4eAssert.assertFalse(fItem.isUserReviewed());
		r4eAssert.assertFalse(file.isUserReviewed());
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			r4eAssert.assertFalse(delta.isUserReviewed());
			//r4eAssert.assertFalse(fProxy.getCommandProxy().verifyAnnotation(delta, true,
			//		R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
			//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(delta, true,
			//		R4EUIConstants.DELTA_ANNOTATION_ID));
		}
	}
}
