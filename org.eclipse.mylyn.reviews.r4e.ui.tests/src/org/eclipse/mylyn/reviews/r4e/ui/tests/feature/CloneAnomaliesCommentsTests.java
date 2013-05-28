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
 * This class implements JUnit Test Cases for postponed anomalies 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestCase;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings({ "restriction", "nls" })
public class CloneAnomaliesCommentsTests extends R4ETestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String REVIEW_TEST_NAME = "test Review";

	private static final String REVIEW_TEST_DESCRIPTION = "review description";

	private static final String ANOMALY1_TEST_TITLE = "test Anomaly1";

	private static final String ANOMALY1_TEST_DESCRIPTION = "Anomaly 1 description";

	private static final String ANOMALY2_TEST_TITLE = " test Anomaly2";

	private static final String ANOMALY2_TEST_DESCRIPTION = "Anomaly 2 description";

	private static final String ANOMALY1_COMMENT1_TEST = "Anomaly 1, Comment 1";

	private static final String ANOMALY1_COMMENT2_TEST = "Anomaly 1, Comment 2";

	private static final String ANOMALY2_COMMENT1_TEST = "Anomaly 2, Comment 1";

	private static final String ANOMALY2_COMMENT2_TEST = "Anomaly 2, Comment 2";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUIReviewGroup fGroup = null;

	private String fGroupName = null;

	private R4EUIReviewBasic fReview = null;

	private R4EUIReviewItem fItem = null;

	private R4EUIReviewItem fItem2 = null;

	private R4EUIReviewItem fItem3 = null;

	private R4EUIAnomalyBasic fAnomaly1 = null;

	private R4EUIAnomalyBasic fAnomaly2 = null;

	private int fAnomalyFileIndex;

	private R4EUIComment fAnomaly1Comment1 = null;

	private R4EUIComment fAnomaly1Comment2 = null;

	private R4EUIComment fAnomaly2Comment1 = null;

	private R4EUIComment fAnomaly2Comment2 = null;

	private R4EUIAnomalyBasic fClonedAnomaly1 = null;

	private R4EUIAnomalyBasic fClonedAnomaly2 = null;

	@SuppressWarnings("unused")
	private final R4EUIAnomalyBasic fClonedAnomaly3 = null;

	private R4EUIAnomalyBasic fClonedAnomaly4 = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	private static final String TEST_SUITE_ID = "CloneAnomaliesCommentsTests";

	public CloneAnomaliesCommentsTests(String suite) {
		super(suite);
	}

	public CloneAnomaliesCommentsTests() {
		super(TEST_SUITE_ID);
	}

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
		suite.addTestSuite(CloneAnomaliesCommentsTests.class);
		return suite;
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
		super.setUp();
		createReviewGroups();
		createReview();
		createReviewItem();
		createParticipants(fReview);
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fTestMain.getCommandProxy().toggleHideDeltasFilter();
		}
		createAnomalies();
		createComments();
	}

	/**
	 * Method tearDown
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// ------------------------------------------------------------------------
	// Main test case
	// ------------------------------------------------------------------------

	/**
	 * Method testCloning
	 * 
	 * @throws CoreException
	 */
	public void testCloning() throws CoreException {
		TestUtils.waitForJobs();
		cloneAnomaliesFromEditor();
		cloneAnomaliesFromExternal();
		cloneAnomaliesDragDrop();
		cloneAnomaliesCopyPaste();
		cloneCommentsDragDrop();
		cloneCommentsCopyPaste();
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

	/**
	 * Method createReviewItem
	 */
	private void createReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReview");

		// Update Review Group handle
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
		fReview = fTestMain.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				REVIEW_TEST_NAME, REVIEW_TEST_DESCRIPTION, TestConstants.REVIEW_TEST_DUE_DATE,
				TestConstants.REVIEW_TEST_PROJECT, TestConstants.REVIEW_TEST_COMPONENTS,
				TestConstants.REVIEW_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_TEST_OBJECTIVES,
				TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fReview);
		r4eAssert.assertNotNull(fReview.getParticipantContainer());
		r4eAssert.assertNotNull(fReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		r4eAssert.assertEquals(REVIEW_TEST_NAME, fReview.getReview().getName());
		r4eAssert.assertEquals(REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
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

	/**
	 * Method createReviewItem
	 */
	private void createReviewItem() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewItem");

		r4eAssert.setTest("Create Commit Item");
		fItem = fTestMain.getItemProxy().createCommitItem(fTestUtils.FJavaIProject, 0);
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
				//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				//		((R4EUIFileContext) fItem.getChildren()[i]).getContentsContainerElement().getChildren(), true,
				//		R4EUIConstants.DELTA_ANNOTATION_ID));
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
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
		//		((R4EUIFileContext) fItem2.getChildren()[0]).getContentsContainerElement().getChildren(), false,
		//		R4EUIConstants.SELECTION_ANNOTATION_ID));

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
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
		//		((R4EUIFileContext) fItem3.getChildren()[0]).getContentsContainerElement().getChildren(), true,
		//		R4EUIConstants.SELECTION_ANNOTATION_ID));
	}

	/**
	 * Method createParticipants
	 * 
	 * @param aReview
	 */
	private void createParticipants(R4EUIReviewBasic aReview) {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createParticipants");

		r4eAssert.setTest("Create Participant");
		List<R4EParticipant> participants = new ArrayList<R4EParticipant>(1);
		R4EParticipant participant = RModelFactory.eINSTANCE.createR4EParticipant();
		participant.setId(TestConstants.PARTICIPANT_TEST_ID);
		participant.setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);
		participants.add(participant);
		R4EUIParticipant uiParticipant = fTestMain.getParticipantProxy().createParticipant(
				aReview.getParticipantContainer(), participants);
		r4eAssert.assertNotNull(uiParticipant);
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, uiParticipant.getParticipant().getId());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, uiParticipant.getParticipant().getEmail());
		r4eAssert.assertEquals(R4EUserRole.R4E_ROLE_REVIEWER, uiParticipant.getParticipant().getRoles().get(0));
	}

	/**
	 * Method createAnomalies
	 */
	private void createAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createAnomalies");

		// Anomaly1
		r4eAssert.setTest("Anomaly 1");
		R4EUIContent content1 = fItem.getFileContexts()
				.get(fAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(0);
		fAnomaly1 = fTestMain.getAnomalyProxy().createLinkedAnomaly(content1, ANOMALY1_TEST_TITLE,
				ANOMALY1_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fAnomaly1);
		r4eAssert.assertEquals(ANOMALY1_TEST_TITLE, fAnomaly1.getAnomaly().getTitle());
		r4eAssert.assertEquals(ANOMALY1_TEST_DESCRIPTION, fAnomaly1.getAnomaly().getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, ((R4ECommentType) fAnomaly1.getAnomaly()
				.getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fAnomaly1.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fAnomaly1.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fAnomaly1.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getLength());

		// Anomaly2
		r4eAssert.setTest("Anomaly 2");
		R4EUIContent content2 = fItem.getFileContexts()
				.get(fAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(1);
		fAnomaly2 = fTestMain.getAnomalyProxy().createLinkedAnomaly(content2, ANOMALY2_TEST_TITLE,
				ANOMALY2_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fAnomaly2);
		r4eAssert.assertEquals(ANOMALY2_TEST_TITLE, fAnomaly2.getAnomaly().getTitle());
		r4eAssert.assertEquals(ANOMALY2_TEST_DESCRIPTION, fAnomaly2.getAnomaly().getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, ((R4ECommentType) fAnomaly2.getAnomaly()
				.getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fAnomaly2.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fAnomaly2.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fAnomaly2.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getLength());

		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(fAnomaly2.getParent().getChildren(), true,
		//		R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method createComments
	 */
	private void createComments() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createComments");

		r4eAssert.setTest("Anomaly 1 Comment 1");
		fAnomaly1Comment1 = fTestMain.getCommentProxy().createComment(fAnomaly1, ANOMALY1_COMMENT1_TEST);
		r4eAssert.assertNotNull(fAnomaly1Comment1);
		r4eAssert.assertEquals(ANOMALY1_COMMENT1_TEST, fAnomaly1Comment1.getComment().getDescription());

		r4eAssert.setTest("Anomaly 1 Comment 2");
		fAnomaly1Comment2 = fTestMain.getCommentProxy().createComment(fAnomaly1, ANOMALY1_COMMENT2_TEST);
		r4eAssert.assertNotNull(fAnomaly1Comment2);
		r4eAssert.assertEquals(ANOMALY1_COMMENT2_TEST, fAnomaly1Comment2.getComment().getDescription());

		r4eAssert.setTest("Anomaly 2 Comment 1");
		fAnomaly2Comment1 = fTestMain.getCommentProxy().createComment(fAnomaly2, ANOMALY2_COMMENT1_TEST);
		r4eAssert.assertNotNull(fAnomaly2Comment1);
		r4eAssert.assertEquals(ANOMALY2_COMMENT1_TEST, fAnomaly2Comment1.getComment().getDescription());

		r4eAssert.setTest("Anomaly 2 Comment 2");
		fAnomaly2Comment2 = fTestMain.getCommentProxy().createComment(fAnomaly2, ANOMALY2_COMMENT2_TEST);
		r4eAssert.assertNotNull(fAnomaly2Comment2);
		r4eAssert.assertEquals(ANOMALY2_COMMENT2_TEST, fAnomaly2Comment2.getComment().getDescription());
	}

	// ------------------------------------------------------------------------
	// Create Anomalies from Editor
	// ------------------------------------------------------------------------

	/**
	 * Method cloneAnomaliesFromEditor
	 */
	private void cloneAnomaliesFromEditor() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("cloneAnomaliesFromEditor");

		r4eAssert.setTest("Clone Anomaly");
		fClonedAnomaly1 = fTestMain.getAnomalyProxy().cloneEditorAnomaly(
				fItem.getFileContexts().get(fAnomalyFileIndex), 20, 50, fAnomaly1, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_TEST_ID);

		r4eAssert.assertNotNull(fClonedAnomaly1);
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getTitle(), fClonedAnomaly1.getAnomaly().getTitle());
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getDescription(), fClonedAnomaly1.getAnomaly().getDescription());
		r4eAssert.assertEquals(((R4ECommentType) fAnomaly1.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly1.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getRank(), fClonedAnomaly1.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fClonedAnomaly1.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fClonedAnomaly1.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(
				20,
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				50,
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fClonedAnomaly1, true,
		//		R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Create Anomalies from External
	// ------------------------------------------------------------------------

	/**
	 * Method cloneAnomaliesFromExternal
	 */
	private void cloneAnomaliesFromExternal() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("cloneAnomaliesFromExternal");

		r4eAssert.setTest("Clone Anomaly");
		fClonedAnomaly2 = fTestMain.getAnomalyProxy().cloneExternalAnomaly(fTestUtils.FJavaFile3, fAnomaly2,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_TEST_ID);

		r4eAssert.assertNotNull(fClonedAnomaly2);
		r4eAssert.assertEquals(fAnomaly2.getAnomaly().getTitle(), fClonedAnomaly2.getAnomaly().getTitle());
		r4eAssert.assertEquals(fAnomaly2.getAnomaly().getDescription(), fClonedAnomaly2.getAnomaly().getDescription());
		r4eAssert.assertEquals(((R4ECommentType) fAnomaly2.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly2.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(fAnomaly2.getAnomaly().getRank(), fClonedAnomaly2.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fClonedAnomaly2.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fClonedAnomaly2.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(0, ((R4ETextPosition) ((R4ETextContent) fClonedAnomaly2.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(755, ((R4ETextPosition) ((R4ETextContent) fClonedAnomaly2.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getLength());
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fClonedAnomaly2, false,
		//		R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Create Anomalies from DragDrop
	// ------------------------------------------------------------------------

	/**
	 * Method cloneAnomaliesDragDrop
	 */
	private void cloneAnomaliesDragDrop() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("cloneAnomaliesDragDrop");

		r4eAssert.setTest("Drag and Drop");
		/* TODO drag & drop does not work
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly1);
		R4EUISelection targetContent = (R4EUISelection) fItem2.getFileContexts()
				.get(0)
				.getContentsContainerElement()
				.getChildren()[0];
		List<IR4EUIModelElement> elementsDropped = fProxy.getCommandProxy().dragDropElements(elementsCopied,
				targetContent);

		fClonedAnomaly3 = (R4EUIAnomalyBasic) elementsDropped.get(0);
		r4eAssert.assertNotNull(fClonedAnomaly3);
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getTitle(), fClonedAnomaly3.getAnomaly().getTitle());
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getDescription(), fClonedAnomaly3.getAnomaly().getDescription());
		r4eAssert.assertEquals(((R4ECommentType) fAnomaly1.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly3.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getRank(), fClonedAnomaly3.getAnomaly().getRank());
		r4eAssert.assertEquals(null, fClonedAnomaly3.getAnomaly().getDueDate());
		r4eAssert.assertEquals(0, fClonedAnomaly3.getAnomaly().getAssignedTo().size());
		r4eAssert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getStartPosition(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly3.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly3.getAnomaly().getLocation().get(0)).getLocation()).getLength());
				*/
	}

	// ------------------------------------------------------------------------
	// Create Anomalies CopyPaste
	// ------------------------------------------------------------------------

	/**
	 * Method cloneAnomaliesCopyPaste
	 */
	private void cloneAnomaliesCopyPaste() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("cloneAnomaliesCopyPaste");

		r4eAssert.setTest("Copy Elements");
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly1);
		fTestMain.getCommandProxy().copyElements(elementsCopied);
		R4EUISelection targetContent = (R4EUISelection) fItem3.getFileContexts()
				.get(0)
				.getContentsContainerElement()
				.getChildren()[0];

		r4eAssert.setTest("Paste Elements");
		List<IR4EUIModelElement> elementsPasted = fTestMain.getCommandProxy().pasteElements(targetContent);
		fClonedAnomaly4 = (R4EUIAnomalyBasic) elementsPasted.get(0);
		r4eAssert.assertNotNull(fClonedAnomaly4);
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getTitle(), fClonedAnomaly4.getAnomaly().getTitle());
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getDescription(), fClonedAnomaly4.getAnomaly().getDescription());
		r4eAssert.assertEquals(((R4ECommentType) fAnomaly1.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly4.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(fAnomaly1.getAnomaly().getRank(), fClonedAnomaly4.getAnomaly().getRank());
		r4eAssert.assertEquals(null, fClonedAnomaly4.getAnomaly().getDueDate());
		r4eAssert.assertEquals(0, fClonedAnomaly4.getAnomaly().getAssignedTo().size());
		r4eAssert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getStartPosition(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly4.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly4.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		//r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fClonedAnomaly4, true,
		//		R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Clone Comment DragDrop
	// ------------------------------------------------------------------------

	/**
	 * Method cloneCommentsDragDrop
	 */
	private void cloneCommentsDragDrop() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("cloneCommentsDragDrop");

		r4eAssert.setTest("Drag and Drop");
		/* TODO drag & drop does not work
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly1Comment1);
		elementsCopied.add(fAnomaly1Comment2);
		List<IR4EUIModelElement> elementsDropped = fProxy.getCommandProxy().dragDropElements(elementsCopied,
				fClonedAnomaly1);
		//NOTE:  For some reason, only the second comment is selected after the paste, so we use another way of verifying the results
		r4eAssert.assertNotNull(fClonedAnomaly1.getChildren()[0]);
		r4eAssert.assertEquals(fAnomaly1Comment1.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[0]).getComment().getDescription());
		r4eAssert.assertNotNull(fClonedAnomaly1.getChildren()[1]);
		r4eAssert.assertEquals(fAnomaly1Comment2.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[1]).getComment().getDescription());
				*/
	}

	// ------------------------------------------------------------------------
	// Clone Comment CopyPaste
	// ------------------------------------------------------------------------

	/**
	 * Method cloneCommentsCopyPaste
	 */
	private void cloneCommentsCopyPaste() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("cloneCommentsCopyPaste");

		r4eAssert.setTest("Copy Elements");
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly2Comment1);
		elementsCopied.add(fAnomaly2Comment2);
		fTestMain.getCommandProxy().copyElements(elementsCopied);

		r4eAssert.setTest("Paste Elements");
		@SuppressWarnings("unused")
		List<IR4EUIModelElement> elementsPasted = fTestMain.getCommandProxy().pasteElements(fClonedAnomaly1);
		//NOTE:  For some reason, only the second comment is selected after the paste, so we use another way of verifying the results
		r4eAssert.assertNotNull(fClonedAnomaly1.getChildren()[0]);
		r4eAssert.assertEquals(fAnomaly2Comment1.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[0]).getComment().getDescription());
		r4eAssert.assertNotNull(fClonedAnomaly1.getChildren()[1]);
		r4eAssert.assertEquals(fAnomaly2Comment2.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[1]).getComment().getDescription());
	}
}
