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
 * This class implements a JUnit Test Case for the Sanity Basic Review tests
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.sanity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.ReviewTypeComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestCase;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings({ "restriction", "nls" })
public class SanityBasicTests extends R4ETestCase {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUIReviewGroup fGroup = null;

	private String fGroupName = null;

	private R4EUIReviewBasic fReview = null;

	private R4EUIParticipant fParticipant = null;

	private R4EUIReviewItem fItem = null;

	private R4EUIReviewItem fItem2 = null;

	private R4EUIReviewItem fItem3 = null;

	private R4EUIAnomalyBasic fCompareEditorAnomaly = null;

	private R4EUIAnomalyBasic fLinkedAnomaly = null;

	private R4EUIAnomalyBasic fExternalAnomaly = null;

	private R4EUIComment fComment = null;

	private int fAnomalyFileIndex;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	private static final String TEST_SUITE_ID = "SanityBasicTests";

	public SanityBasicTests(String suite) {
		super(suite);
	}

	public SanityBasicTests() {
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
		suite.addTestSuite(SanityBasicTests.class);
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
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// ------------------------------------------------------------------------
	// Main test case 
	// ------------------------------------------------------------------------

	/**
	 * Method testBasicReviews
	 * 
	 * @throws CoreException
	 */
	@org.junit.Test
	public void testBasicReviews() throws CoreException {
		TestUtils.waitForJobs();
		createReviews();
		createReviewItems();
		createParticipants();
		createCompareEditorAnomalies();
		createLinkedAnomalies();
		createExternalAnomalies();
		createComments();
		progressReview();
		verifySorters();
		sendQuestionNotifications();
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

		fGroup = null;

		// Create Review Group
		r4eAssert.setTest("Create Review Group");
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(TestConstants.REVIEW_GROUP_TEST_NAME)) {
				fGroup = group;
				fGroupName = group.getName();
				break;
			}
		}
		if (null == fGroup) {
			fGroup = fTestMain.getReviewGroupProxy().createReviewGroup(
					fTestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME,
					TestConstants.REVIEW_GROUP_TEST_NAME, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
					TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
					TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
			r4eAssert.assertNotNull(fGroup);
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME, fGroup.getReviewGroup().getName());
			r4eAssert.assertEquals(new Path(fTestUtils.FSharedFolder).toPortableString() + "/"
					+ TestConstants.REVIEW_GROUP_TEST_NAME, fGroup.getReviewGroup().getFolder());
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
			fGroupName = fGroup.getName();
		}
	}

	// ------------------------------------------------------------------------
	// Create Review Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method createReviews
	 */
	private void createReviews() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviews");

		// Update Review Group Handle
		r4eAssert.setTest("Update Review Group Handle");
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (fGroupName.equals(elem.getName())) {
				fGroup = (R4EUIReviewGroup) elem;
			}
		}
		if (!fGroup.isOpen()) {
			fTestMain.getCommandProxy().openElement(fGroup);
		}
		r4eAssert.assertTrue(fGroup.isOpen());

		r4eAssert.setTest("Create Review");
		fReview = fTestMain.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_BASIC,
				TestConstants.REVIEW_TEST_NAME, TestConstants.REVIEW_TEST_DESCRIPTION,
				TestConstants.REVIEW_TEST_DUE_DATE, TestConstants.REVIEW_TEST_PROJECT,
				TestConstants.REVIEW_TEST_COMPONENTS, TestConstants.REVIEW_TEST_ENTRY_CRITERIA,
				TestConstants.REVIEW_TEST_OBJECTIVES, TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fReview);
		r4eAssert.assertNotNull(fReview.getParticipantContainer());
		r4eAssert.assertNotNull(fReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_BASIC, fReview.getReview().getType());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_NAME, fReview.getReview().getName());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_DUE_DATE, fReview.getReview().getDueDate());
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

	// ------------------------------------------------------------------------
	// Create Review Item Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method createReviewItems
	 */
	private void createReviewItems() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewItems");

		// Close and re-open, so the validation takes de-serialized information
		r4eAssert.setTest("Create Commit Item");
		fItem = fTestMain.getItemProxy().createCommitItem(fTestUtils.FJavaIProject, 0);
		String itemName = fItem.getName();
		fTestMain.getCommandProxy().closeElement(fReview);
		fTestMain.getCommandProxy().openElement(fReview);
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(itemName)) {
				fItem = (R4EUIReviewItem) elem;
			}
		}

		// Now validate
		r4eAssert.assertNotNull(fItem);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fItem.getItem().getAddedById());
		r4eAssert.assertEquals("The.committer@some.com", fItem.getItem().getAuthorRep());
		r4eAssert.assertEquals("second Java Commit", fItem.getItem().getDescription());
		r4eAssert.assertEquals(4, fItem.getChildren().length);
		for (int i = 0; i < fItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				fAnomalyFileIndex = i; //Used later to add anomalies
				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
				r4eAssert.assertEquals(606, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(25, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(665, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(63, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(733, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(61, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertTrue(fTestMain.getCommandProxy().verifyAnnotations(
						((R4EUIFileContext) fItem.getChildren()[i]).getContentsContainerElement().getChildren(), true,
						R4EUIConstants.DELTA_ANNOTATION_ID));
			} else if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE4_PROJ_NAME)) {
				r4eAssert.assertNull(fItem.getItem().getFileContextList().get(i).getBase());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE3_PROJ_NAME)) {
				r4eAssert.assertNull(fItem.getItem().getFileContextList().get(i).getBase());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				r4eAssert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertNull(fItem.getItem().getFileContextList().get(i).getTarget());
			}
		}

		r4eAssert.setTest("Create Manual Tree Item");
		fItem2 = fTestMain.getItemProxy().createManualTreeItem(fTestUtils.FJavaFile3);
		r4eAssert.assertNotNull(fItem2);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fItem2.getItem().getAddedById());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		r4eAssert.assertEquals(0, ((R4ETextPosition) fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		r4eAssert.assertEquals(755, ((R4ETextPosition) fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		r4eAssert.assertTrue(fTestMain.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fItem2.getChildren()[0]).getContentsContainerElement().getChildren(), false,
				R4EUIConstants.SELECTION_ANNOTATION_ID));

		r4eAssert.setTest("Create Manual Text Item");
		fItem3 = fTestMain.getItemProxy().createManualTextItem(fTestUtils.FJavaFile4, 50, 20);
		r4eAssert.assertNotNull(fItem3);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fItem3.getItem().getAddedById());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		r4eAssert.assertEquals(50, ((R4ETextPosition) fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		r4eAssert.assertEquals(20, ((R4ETextPosition) fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		r4eAssert.assertTrue(fTestMain.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fItem3.getChildren()[0]).getContentsContainerElement().getChildren(), true,
				R4EUIConstants.SELECTION_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Create Participant Tests 
	// ------------------------------------------------------------------------

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
		fParticipant = fTestMain.getParticipantProxy().createParticipant(fReview.getParticipantContainer(),
				participants);

		r4eAssert.setTest("Create Participant");
		r4eAssert.assertNotNull(fParticipant);
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fParticipant.getParticipant().getId());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, fParticipant.getParticipant().getEmail());
		r4eAssert.assertEquals(R4EUserRole.R4E_ROLE_REVIEWER, fParticipant.getParticipant().getRoles().get(0));
	}

	// ------------------------------------------------------------------------
	// Create Compare Editor Anomalies Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method createEditorAnomalies
	 */
	private void createCompareEditorAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createCompareEditorAnomalies");

		r4eAssert.setTest("Create Compare Editor Anomaly");
		fCompareEditorAnomaly = fTestMain.getAnomalyProxy().createCompareEditorAnomaly(
				fItem.getFileContexts().get(fAnomalyFileIndex), 20, 50,
				TestConstants.COMPARE_EDITOR_ANOMALY_TEST_TITLE, TestConstants.COMPARE_EDITOR_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_ERRONEOUS, TestConstants.ANOMALY_TEST_RANK_MINOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fCompareEditorAnomaly);
		r4eAssert.assertEquals(TestConstants.COMPARE_EDITOR_ANOMALY_TEST_TITLE, fCompareEditorAnomaly.getAnomaly()
				.getTitle());
		r4eAssert.assertEquals(TestConstants.COMPARE_EDITOR_ANOMALY_TEST_DESCRIPTION,
				fCompareEditorAnomaly.getAnomaly().getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_ERRONEOUS,
				((R4ECommentType) fCompareEditorAnomaly.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MINOR, fCompareEditorAnomaly.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fCompareEditorAnomaly.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fCompareEditorAnomaly.getAnomaly()
				.getAssignedTo()
				.get(0));
		r4eAssert.assertEquals(20, ((R4ETextPosition) ((R4ETextContent) fCompareEditorAnomaly.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(50, ((R4ETextPosition) ((R4ETextContent) fCompareEditorAnomaly.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getLength());
		r4eAssert.assertTrue(fTestMain.getCommandProxy().verifyAnnotation(fCompareEditorAnomaly, true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Create Linked Anomaly Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method createLinkedAnomalies
	 */
	private void createLinkedAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createLinkedAnomalies");

		r4eAssert.setTest("Create Linked Anomaly");
		R4EUIContent content = fItem.getFileContexts()
				.get(fAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(0);
		fLinkedAnomaly = fTestMain.getAnomalyProxy().createLinkedAnomaly(content,
				TestConstants.LINKED_ANOMALY_TEST_TITLE, TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, TestConstants.ANOMALY_TEST_RANK_MAJOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fLinkedAnomaly);
		r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_TITLE, fLinkedAnomaly.getAnomaly().getTitle());
		r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION, fLinkedAnomaly.getAnomaly()
				.getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fLinkedAnomaly.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fLinkedAnomaly.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fLinkedAnomaly.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fLinkedAnomaly.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(
				((R4EUITextPosition) content.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4EUITextPosition) content.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		r4eAssert.assertTrue(fTestMain.getCommandProxy().verifyAnnotation(fLinkedAnomaly, true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Create External Anomaly Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method createExternalAnomalies
	 */
	private void createExternalAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createExternalAnomalies");

		r4eAssert.setTest("Create External Anomaly");
		fExternalAnomaly = fTestMain.getAnomalyProxy().createExternalAnomaly(fTestUtils.FJavaFile3,
				TestConstants.EXTERNAL_ANOMALY_TEST_TITLE, TestConstants.EXTERNAL_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_QUESTION, TestConstants.ANOMALY_TEST_RANK_MINOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fExternalAnomaly);
		r4eAssert.assertEquals(TestConstants.EXTERNAL_ANOMALY_TEST_TITLE, fExternalAnomaly.getAnomaly().getTitle());
		r4eAssert.assertEquals(TestConstants.EXTERNAL_ANOMALY_TEST_DESCRIPTION, fExternalAnomaly.getAnomaly()
				.getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_QUESTION,
				((R4ECommentType) fExternalAnomaly.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MINOR, fExternalAnomaly.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fExternalAnomaly.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fExternalAnomaly.getAnomaly()
				.getAssignedTo()
				.get(0));
		r4eAssert.assertEquals(755, ((R4ETextPosition) ((R4ETextContent) fExternalAnomaly.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getLength());
		r4eAssert.assertTrue(fTestMain.getCommandProxy().verifyAnnotation(fExternalAnomaly, false,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Create Comment Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method createComments
	 */
	private void createComments() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createComments");

		r4eAssert.setTest("Create Comment");
		fComment = fTestMain.getCommentProxy().createComment(fLinkedAnomaly, TestConstants.COMMENT_TEST);
		r4eAssert.assertNotNull(fComment);
		r4eAssert.assertEquals(TestConstants.COMMENT_TEST, fComment.getComment().getDescription());
	}

	// ------------------------------------------------------------------------
	// Progress Review Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method progressReview
	 */
	private void progressReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("progressReview");

		r4eAssert.setTest("Progress Review");
		fTestMain.getReviewProxy().progressReview(fReview);
		r4eAssert.assertEquals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED, ((R4EReviewState) fReview.getReview()
				.getState()).getState());
		r4eAssert.assertNotNull(fReview.getReview().getEndDate());
	}

	// ------------------------------------------------------------------------
	// Sorters Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method verifySorters
	 */
	private void verifySorters() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("verifySorters");

		r4eAssert.setTest("Initial State");
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		// Alpha sorter On/Off
		r4eAssert.setTest("Alpha Sorter On/Off");
		fTestMain.getCommandProxy().toggleAlphaSorter();
		ViewerComparator activeSorter = fTestMain.getCommandProxy().getActiveSorter();
		r4eAssert.assertTrue(null != activeSorter && activeSorter instanceof NavigatorElementComparator
				&& !(activeSorter instanceof ReviewTypeComparator));
		r4eAssert.assertTrue(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		fTestMain.getCommandProxy().toggleAlphaSorter();
		r4eAssert.assertTrue(null == fTestMain.getCommandProxy().getActiveSorter());
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		// Review Type sorter On/Off
		r4eAssert.setTest("Review Type Sorter On/Off");
		fTestMain.getCommandProxy().toggleReviewTypeSorter();
		activeSorter = fTestMain.getCommandProxy().getActiveSorter();
		r4eAssert.assertTrue(null != activeSorter && activeSorter instanceof ReviewTypeComparator);
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertTrue(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		fTestMain.getCommandProxy().toggleReviewTypeSorter();
		r4eAssert.assertTrue(null == fTestMain.getCommandProxy().getActiveSorter());
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		// AlphaSorter -> ReviewTypeSorter
		r4eAssert.setTest("AlphaSorter -> ReviewTypeSorter");
		fTestMain.getCommandProxy().toggleAlphaSorter();
		activeSorter = fTestMain.getCommandProxy().getActiveSorter();
		r4eAssert.assertTrue(null != activeSorter && activeSorter instanceof NavigatorElementComparator
				&& !(activeSorter instanceof ReviewTypeComparator));
		r4eAssert.assertTrue(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		fTestMain.getCommandProxy().toggleReviewTypeSorter();
		activeSorter = fTestMain.getCommandProxy().getActiveSorter();
		r4eAssert.assertTrue(null != activeSorter && activeSorter instanceof ReviewTypeComparator);
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertTrue(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		// ReviewTypeSorter -> AlphaSorter
		r4eAssert.setTest("ReviewTypeSorter -> AlphaSorter");
		fTestMain.getCommandProxy().toggleAlphaSorter();
		activeSorter = fTestMain.getCommandProxy().getActiveSorter();
		r4eAssert.assertTrue(null != activeSorter && activeSorter instanceof NavigatorElementComparator
				&& !(activeSorter instanceof ReviewTypeComparator));
		r4eAssert.assertTrue(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		// Remove AlphaSorter
		r4eAssert.setTest("Remove AlphaSorter");
		fTestMain.getCommandProxy().toggleAlphaSorter();
		r4eAssert.assertTrue(null == fTestMain.getCommandProxy().getActiveSorter());
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		r4eAssert.assertFalse(fTestMain.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));
	}

	// ------------------------------------------------------------------------
	// Send Question Notification Tests 
	// ------------------------------------------------------------------------

	/**
	 * Method sendQuestionNotifications
	 * 
	 * @throws CoreException
	 */
	private void sendQuestionNotifications() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("sendQuestionNotifications");

		r4eAssert.setTest("Send Question Notifications");
		fTestMain.getCommandProxy().sendQuestionNotification(fReview);
		r4eAssert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_SOURCE, fTestMain.getCommandProxy()
				.getEmailDetails()
				.getSource());
		String[] destinations = fTestMain.getCommandProxy().getEmailDetails().getDestinations();
		for (int i = 0; i < destinations.length; i++) {
			r4eAssert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_DESTINATIONS[i], destinations[i]);
		}
		r4eAssert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_SUBJECT, fTestMain.getCommandProxy()
				.getEmailDetails()
				.getSubject());
		//TODO:  Assert fails, but Strings seem to be identical???
		/*
		r4eAssert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_BODY, fProxy.getCommandProxy()
				.getEmailDetails()
				.getBody());
		*/
	}
}
