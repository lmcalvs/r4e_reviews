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
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings("restriction")
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
	// Methods
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
	@Override
	@Before
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
	@Override
	@After
	public void tearDown() throws Exception {
		fProxy = null;
	}

	/**
	 * Method testPreferences
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

	/**
	 * Method createReviewGroups
	 */
	private void createReviewGroups() {

		fReviewGroup = null;

		//Create Parent Review Group
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
			Assert.assertNotNull(fReviewGroup);
			Assert.assertEquals(PARENT_REVIEW_GROUP_NAME, fReviewGroup.getReviewGroup().getName());
			Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/" + PARENT_REVIEW_GROUP_NAME,
					fReviewGroup.getReviewGroup().getFolder());
			Assert.assertEquals(PARENT_REVIEW_GROUP_DESCRIPTION, fReviewGroup.getReviewGroup().getDescription());
		}
	}

	/**
	 * Method createReview
	 */
	private void createReview() {
		fReview = fProxy.getReviewProxy().createReview(fReviewGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				REVIEW_TEST_NAME, REVIEW_TEST_DESCRIPTION, null, null, new String[0], null, null, null);
		Assert.assertNotNull(fReview);
		Assert.assertNotNull(fReview.getParticipantContainer());
		Assert.assertNotNull(fReview.getAnomalyContainer());
		Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		Assert.assertEquals(REVIEW_TEST_NAME, fReview.getReview().getName());
		Assert.assertEquals(REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		Assert.assertTrue(fReview.isOpen());
	}

	/**
	 * Method createReviewItems
	 */
	private void createReviewItems() throws CoreException {
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

		//Now validate
		Assert.assertNotNull(fItem);
		Assert.assertNotNull(fItem2);
	}

	/**
	 * Method createParticipants
	 */
	private void createParticipants() {
		List<R4EParticipant> participants = new ArrayList<R4EParticipant>(1);
		R4EParticipant participant = RModelFactory.eINSTANCE.createR4EParticipant();
		participant.setId(TestConstants.PARTICIPANT_TEST_ID);
		participant.setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);
		participants.add(participant);
		fParticipant = fProxy.getParticipantProxy().createParticipant(fReview.getParticipantContainer(), participants);
		Assert.assertNotNull(fParticipant);
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fParticipant.getParticipant().getId());
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, fParticipant.getParticipant().getEmail());
	}

	/**
	 * Method setDelta
	 */
	private void setDelta() {

		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		//Mark first delta as reviewed
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				fProxy.getCommandProxy().changeReviewedState(delta);
				break;
			}
		}

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);
		Assert.assertFalse(fReview.isUserReviewed());
		Assert.assertFalse(fItem.isUserReviewed());
		Assert.assertFalse(file.isUserReviewed());
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				Assert.assertTrue(delta.isUserReviewed());
				Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(delta, true,
						R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
				break;
			}
		}
	}

	/**
	 * Method setAllDeltas
	 */
	private void setAllDeltas() {
		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		//Mark remaining deltas as reviewed
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			//Skip already marked delta
			if (delta.getName().equals(DELTA1_NAME)) {
				continue;
			}
			fProxy.getCommandProxy().changeReviewedState(delta);
		}

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);
		Assert.assertFalse(fReview.isUserReviewed());
		Assert.assertFalse(fItem.isUserReviewed());
		Assert.assertTrue(file.isUserReviewed());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(file.getContentsContainerElement().getChildren(),
				true, R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			Assert.assertTrue(delta.isUserReviewed());
		}
	}

	/**
	 * Method setFile
	 */
	private void setFile() {

		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		//Mark file as reviewed
		fProxy.getCommandProxy().changeReviewedState(file);

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);
		Assert.assertFalse(fReview.isUserReviewed());
		Assert.assertFalse(fItem.isUserReviewed());
		Assert.assertTrue(file.isUserReviewed());
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertTrue(file.isUserReviewed());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(file.getContentsContainerElement().getChildren(),
				true, R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			Assert.assertTrue(delta.isUserReviewed());
		}
	}

	/**
	 * Method setAllFiles
	 */
	private void setAllFiles() {
		for (IR4EUIModelElement file : fItem.getChildren()) {
			if (file.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)
					|| file.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				continue;
			}
			fProxy.getCommandProxy().changeReviewedState(file);
		}

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);
		Assert.assertFalse(fReview.isUserReviewed());
		Assert.assertTrue(fItem.isUserReviewed());
		for (IR4EUIModelElement file : fItem.getChildren()) {
			Assert.assertTrue(file.isUserReviewed());
			for (R4EUIContent delta : ((R4EUIFileContext) file).getContentsContainerElement().getContentsList()) {
				Assert.assertTrue(delta.isUserReviewed());
			}
		}
	}

	/**
	 * Method setReviewItem
	 */
	private void setReviewItem() {
		fItem2 = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
				break;
			}
		}
		fProxy.getCommandProxy().changeReviewedState(fItem2);

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		Assert.assertTrue(fReview.isUserReviewed());
		for (IR4EUIModelElement item : fReview.getChildren()) {
			if (item instanceof R4EUIReviewItem) {
				Assert.assertTrue(item.isUserReviewed());
				for (IR4EUIModelElement file : item.getChildren()) {
					Assert.assertTrue(file.isUserReviewed());
					for (R4EUIContent delta : ((R4EUIFileContext) file).getContentsContainerElement().getContentsList()) {
						Assert.assertTrue(delta.isUserReviewed());
					}
				}
			}
		}
	}

	/**
	 * Method addReviewItemFromOtherUser
	 * 
	 * @throws CoreException
	 */
	private void addReviewItemFromOtherUser() throws CoreException {
		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());

		//Change user
		String originalUser = fProxy.getPreferencesProxy().getUser();
		String originalEmail = fProxy.getPreferencesProxy().getEmail();
		fProxy.getPreferencesProxy().setUser(TestConstants.PARTICIPANT_TEST_ID);
		fProxy.getPreferencesProxy().setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);

		//New user adds new review item
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());
		fItem3 = fProxy.getItemProxy().createCommitItem(TestUtils.FTextIProject, 0);
		Assert.assertNotNull(fItem3);
		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());

		//Change back user to original one
		fProxy.getPreferencesProxy().setUser(originalUser);
		fProxy.getPreferencesProxy().setEmail(originalEmail);

		//Verify Reviewed State
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());
		Assert.assertFalse(fReview.isUserReviewed());
	}

	/**
	 * Method unsetDelta
	 */
	private void unsetDelta() {
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

		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		//Unmark first delta as reviewed
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				fProxy.getCommandProxy().changeReviewedState(delta);
				break;
			}
		}

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		Assert.assertFalse(fReview.isUserReviewed());
		Assert.assertFalse(fItem.isUserReviewed());
		Assert.assertFalse(file.isUserReviewed());
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			if (delta.getName().equals(DELTA1_NAME)) {
				Assert.assertFalse(delta.isUserReviewed());
				Assert.assertFalse(fProxy.getCommandProxy().verifyAnnotation(delta, true,
						R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
				Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(delta, true,
						R4EUIConstants.DELTA_ANNOTATION_ID));
				break;
			}
		}
	}

	/**
	 * Method unsetReviewItem
	 */
	private void unsetReviewItem() {
		fItem2 = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
				break;
			}
		}
		fProxy.getCommandProxy().changeReviewedState(fItem2);
		Assert.assertFalse(fItem2.isUserReviewed());

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		Assert.assertFalse(fReview.isUserReviewed());
		fItem2 = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName2)) {
				fItem2 = (R4EUIReviewItem) elem;
				break;
			}
		}
		for (IR4EUIModelElement file : fItem2.getChildren()) {
			Assert.assertFalse(file.isUserReviewed());
			for (R4EUIContent delta : ((R4EUIFileContext) file).getContentsContainerElement().getContentsList()) {
				Assert.assertFalse(delta.isUserReviewed());
			}
		}
	}

	/**
	 * Method unsetFile
	 */
	private void unsetFile() {
		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);

		R4EUIFileContext file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		//Mark file as reviewed
		fProxy.getCommandProxy().changeReviewedState(file);

		fProxy.getCommandProxy().closeElement(fReview);
		Assert.assertFalse(fReview.isOpen());
		fProxy.getCommandProxy().openElement(fReview);
		Assert.assertTrue(fReview.isOpen());

		fItem = null;
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(fItemName)) {
				fItem = (R4EUIReviewItem) elem;
				break;
			}
		}
		Assert.assertNotNull(fItem);
		file = null;
		for (IR4EUIModelElement elem : fItem.getChildren()) {
			if (elem.getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				file = (R4EUIFileContext) elem;
				break;
			}
		}
		Assert.assertNotNull(file);

		Assert.assertFalse(fReview.isUserReviewed());
		Assert.assertFalse(fItem.isUserReviewed());
		Assert.assertFalse(file.isUserReviewed());
		for (R4EUIContent delta : file.getContentsContainerElement().getContentsList()) {
			Assert.assertFalse(delta.isUserReviewed());
			Assert.assertFalse(fProxy.getCommandProxy().verifyAnnotation(delta, true,
					R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID));
			Assert.assertTrue(fProxy.getCommandProxy()
					.verifyAnnotation(delta, true, R4EUIConstants.DELTA_ANNOTATION_ID));
		}
	}
}
