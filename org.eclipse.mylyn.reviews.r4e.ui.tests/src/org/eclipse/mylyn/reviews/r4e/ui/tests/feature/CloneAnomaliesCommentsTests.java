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
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings("restriction")
public class CloneAnomaliesCommentsTests extends TestCase {

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

	private R4EUITestMain fProxy = null;

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

	private final R4EUIAnomalyBasic fClonedAnomaly3 = null;

	private R4EUIAnomalyBasic fClonedAnomaly4 = null;

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
		suite.addTestSuite(CloneAnomaliesCommentsTests.class);
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
		createReviewItem();
		createParticipants(fReview);
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fProxy.getCommandProxy().toggleHideDeltasFilter();
		}
		createAnomalies();
		createComments();
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
	 * Method testBasicReviews
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

	/**
	 * Method createReviewGroups
	 */
	private void createReviewGroups() {

		fGroup = null;

		//Create Review Group
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(TestConstants.REVIEW_GROUP_TEST_NAME)) {
				fGroup = group;
				fGroupName = group.getName();
				break;
			}
		}
		if (null == fGroup) {
			fGroup = fProxy.getReviewGroupProxy().createReviewGroup(
					TestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME,
					TestConstants.REVIEW_GROUP_TEST_NAME, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
					TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
					TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
			Assert.assertNotNull(fGroup);
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME, fGroup.getReviewGroup().getName());
			Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
					+ TestConstants.REVIEW_GROUP_TEST_NAME, fGroup.getReviewGroup().getFolder());
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, fGroup.getReviewGroup().getDescription());
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, fGroup.getReviewGroup()
					.getDefaultEntryCriteria());
			for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
				Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i], fGroup.getReviewGroup()
						.getAvailableProjects()
						.get(i));
			}
			for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
				Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i], fGroup.getReviewGroup()
						.getAvailableComponents()
						.get(i));
			}
			fGroupName = fGroup.getName();
		}
	}

	/**
	 * Method createOriginalReview
	 */
	private void createReview() {
		//Update Review Group handle
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (fGroupName.equals(elem.getName())) {
				fGroup = (R4EUIReviewGroup) elem;
			}
		}
		if (!fGroup.isOpen()) {
			fProxy.getCommandProxy().openElement(fGroup);
		}
		Assert.assertTrue(fGroup.isOpen());

		fReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				REVIEW_TEST_NAME, REVIEW_TEST_DESCRIPTION, TestConstants.REVIEW_TEST_DUE_DATE,
				TestConstants.REVIEW_TEST_PROJECT, TestConstants.REVIEW_TEST_COMPONENTS,
				TestConstants.REVIEW_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_TEST_OBJECTIVES,
				TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		Assert.assertNotNull(fReview);
		Assert.assertNotNull(fReview.getParticipantContainer());
		Assert.assertNotNull(fReview.getAnomalyContainer());
		Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		Assert.assertEquals(REVIEW_TEST_NAME, fReview.getReview().getName());
		Assert.assertEquals(REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		Assert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fReview.getReview().getComponents().get(i));
		}
		Assert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fReview.getReview().getEntryCriteria());
		Assert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fReview.getReview().getObjectives());
		Assert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fReview.getReview().getReferenceMaterial());
		Assert.assertTrue(fReview.isOpen());
	}

	/**
	 * Method createOriginalReviewItem
	 */
	private void createReviewItem() throws CoreException {
		fItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		Assert.assertNotNull(fItem);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fItem.getItem().getAddedById());
		Assert.assertEquals("The.committer@some.com", fItem.getItem().getAuthorRep());
		Assert.assertEquals("second Java Commit", fItem.getItem().getDescription());
		Assert.assertEquals(4, fItem.getChildren().length);
		for (int i = 0; i < fItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				fAnomalyFileIndex = i; //Used later to add anomalies
				Assert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				Assert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
				Assert.assertEquals(606, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(25,
						((R4ETextPosition) fItem.getItem()
								.getFileContextList()
								.get(i)
								.getDeltas()
								.get(0)
								.getTarget()
								.getLocation()).getLength());
				Assert.assertEquals(665, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(63,
						((R4ETextPosition) fItem.getItem()
								.getFileContextList()
								.get(i)
								.getDeltas()
								.get(1)
								.getTarget()
								.getLocation()).getLength());
				Assert.assertEquals(733, ((R4ETextPosition) fItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(61,
						((R4ETextPosition) fItem.getItem()
								.getFileContextList()
								.get(i)
								.getDeltas()
								.get(2)
								.getTarget()
								.getLocation()).getLength());
				//Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				//		((R4EUIFileContext) fItem.getChildren()[i]).getContentsContainerElement().getChildren(), true,
				//		R4EUIConstants.DELTA_ANNOTATION_ID));
			} else if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE4_PROJ_NAME)) {
				Assert.assertNull(fItem.getItem().getFileContextList().get(i).getBase());

				Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE3_PROJ_NAME)) {
				Assert.assertNull(fItem.getItem().getFileContextList().get(i).getBase());
				Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				Assert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				Assert.assertNull(fItem.getItem().getFileContextList().get(i).getTarget());
			}
		}

		fItem2 = fProxy.getItemProxy().createManualTreeItem(TestUtils.FJavaFile3);
		Assert.assertNotNull(fItem2);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fItem2.getItem().getAddedById());
		Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		Assert.assertEquals(0, ((R4ETextPosition) fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		Assert.assertEquals(781, ((R4ETextPosition) fItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fItem2.getChildren()[0]).getContentsContainerElement().getChildren(), false,
				R4EUIConstants.SELECTION_ANNOTATION_ID));

		fItem3 = fProxy.getItemProxy().createManualTextItem(TestUtils.FJavaFile4, 50, 20);
		Assert.assertNotNull(fItem3);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fItem3.getItem().getAddedById());
		Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		Assert.assertEquals(50, ((R4ETextPosition) fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		Assert.assertEquals(20, ((R4ETextPosition) fItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fItem3.getChildren()[0]).getContentsContainerElement().getChildren(), true,
				R4EUIConstants.SELECTION_ANNOTATION_ID));
	}

	/**
	 * Method createParticipants
	 * 
	 * @param aReview
	 */
	private void createParticipants(R4EUIReviewBasic aReview) {
		List<R4EParticipant> participants = new ArrayList<R4EParticipant>(1);
		R4EParticipant participant = RModelFactory.eINSTANCE.createR4EParticipant();
		participant.setId(TestConstants.PARTICIPANT_TEST_ID);
		participant.setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);
		for (R4EUserRole role : TestConstants.PARTICIPANT_TEST_ROLES) {
			participant.getRoles().add(role);
		}
		participant.setFocusArea(TestConstants.PARTICIPANT_TEST_FOCUS_AREA);
		participants.add(participant);
		R4EUIParticipant uiParticipant = fProxy.getParticipantProxy().createParticipant(
				aReview.getParticipantContainer(), participants);
		Assert.assertNotNull(uiParticipant);
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, uiParticipant.getParticipant().getId());
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, uiParticipant.getParticipant().getEmail());
		for (int i = 0; i < TestConstants.PARTICIPANT_TEST_ROLES.length; i++) {
			Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ROLES[i],
					uiParticipant.getParticipant().getRoles().get(i));
		}
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_FOCUS_AREA, uiParticipant.getParticipant().getFocusArea());
	}

	/**
	 * Method createAnomalies
	 */
	private void createAnomalies() {
		//Anomaly1
		R4EUIContent content1 = fItem.getFileContexts()
				.get(fAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(0);
		fAnomaly1 = fProxy.getAnomalyProxy().createLinkedAnomaly(content1, ANOMALY1_TEST_TITLE,
				ANOMALY1_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fAnomaly1);
		Assert.assertEquals(ANOMALY1_TEST_TITLE, fAnomaly1.getAnomaly().getTitle());
		Assert.assertEquals(ANOMALY1_TEST_DESCRIPTION, fAnomaly1.getAnomaly().getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, ((R4ECommentType) fAnomaly1.getAnomaly()
				.getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fAnomaly1.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fAnomaly1.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fAnomaly1.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getLength());

		//Anomaly2
		R4EUIContent content2 = fItem.getFileContexts()
				.get(fAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(1);
		fAnomaly2 = fProxy.getAnomalyProxy().createLinkedAnomaly(content2, ANOMALY2_TEST_TITLE,
				ANOMALY2_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fAnomaly2);
		Assert.assertEquals(ANOMALY2_TEST_TITLE, fAnomaly2.getAnomaly().getTitle());
		Assert.assertEquals(ANOMALY2_TEST_DESCRIPTION, fAnomaly2.getAnomaly().getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, ((R4ECommentType) fAnomaly2.getAnomaly()
				.getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fAnomaly2.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fAnomaly2.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fAnomaly2.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getLength());

		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(fAnomaly2.getParent().getChildren(), true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method createComments
	 */
	private void createComments() {
		fAnomaly1Comment1 = fProxy.getCommentProxy().createComment(fAnomaly1, ANOMALY1_COMMENT1_TEST);
		Assert.assertNotNull(fAnomaly1Comment1);
		Assert.assertEquals(ANOMALY1_COMMENT1_TEST, fAnomaly1Comment1.getComment().getDescription());

		fAnomaly1Comment2 = fProxy.getCommentProxy().createComment(fAnomaly1, ANOMALY1_COMMENT2_TEST);
		Assert.assertNotNull(fAnomaly1Comment2);
		Assert.assertEquals(ANOMALY1_COMMENT2_TEST, fAnomaly1Comment2.getComment().getDescription());

		fAnomaly2Comment1 = fProxy.getCommentProxy().createComment(fAnomaly2, ANOMALY2_COMMENT1_TEST);
		Assert.assertNotNull(fAnomaly2Comment1);
		Assert.assertEquals(ANOMALY2_COMMENT1_TEST, fAnomaly2Comment1.getComment().getDescription());

		fAnomaly2Comment2 = fProxy.getCommentProxy().createComment(fAnomaly2, ANOMALY2_COMMENT2_TEST);
		Assert.assertNotNull(fAnomaly2Comment2);
		Assert.assertEquals(ANOMALY2_COMMENT2_TEST, fAnomaly2Comment2.getComment().getDescription());
	}

	/**
	 * Method cloneAnomaliesFromEditor
	 */
	private void cloneAnomaliesFromEditor() {
		fClonedAnomaly1 = fProxy.getAnomalyProxy().cloneEditorAnomaly(fItem.getFileContexts().get(fAnomalyFileIndex),
				20, 50, fAnomaly1, TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_TEST_ID);

		Assert.assertNotNull(fClonedAnomaly1);
		Assert.assertEquals(fAnomaly1.getAnomaly().getTitle(), fClonedAnomaly1.getAnomaly().getTitle());
		Assert.assertEquals(fAnomaly1.getAnomaly().getDescription(), fClonedAnomaly1.getAnomaly().getDescription());
		Assert.assertEquals(((R4ECommentType) fAnomaly1.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly1.getAnomaly().getType()).getType());
		Assert.assertEquals(fAnomaly1.getAnomaly().getRank(), fClonedAnomaly1.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fClonedAnomaly1.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fClonedAnomaly1.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				20,
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				50,
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fClonedAnomaly1, true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method cloneAnomaliesFromExternal
	 */
	private void cloneAnomaliesFromExternal() {
		fClonedAnomaly2 = fProxy.getAnomalyProxy().cloneExternalAnomaly(TestUtils.FJavaFile3, fAnomaly2,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_TEST_ID);

		Assert.assertNotNull(fClonedAnomaly2);
		Assert.assertEquals(fAnomaly2.getAnomaly().getTitle(), fClonedAnomaly2.getAnomaly().getTitle());
		Assert.assertEquals(fAnomaly2.getAnomaly().getDescription(), fClonedAnomaly2.getAnomaly().getDescription());
		Assert.assertEquals(((R4ECommentType) fAnomaly2.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly2.getAnomaly().getType()).getType());
		Assert.assertEquals(fAnomaly2.getAnomaly().getRank(), fClonedAnomaly2.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fClonedAnomaly2.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, fClonedAnomaly2.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				0,
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				781,
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fClonedAnomaly2, false,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method cloneAnomaliesDragDrop
	 */
	private void cloneAnomaliesDragDrop() {
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
		Assert.assertNotNull(fClonedAnomaly3);
		Assert.assertEquals(fAnomaly1.getAnomaly().getTitle(), fClonedAnomaly3.getAnomaly().getTitle());
		Assert.assertEquals(fAnomaly1.getAnomaly().getDescription(), fClonedAnomaly3.getAnomaly().getDescription());
		Assert.assertEquals(((R4ECommentType) fAnomaly1.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly3.getAnomaly().getType()).getType());
		Assert.assertEquals(fAnomaly1.getAnomaly().getRank(), fClonedAnomaly3.getAnomaly().getRank());
		Assert.assertEquals(null, fClonedAnomaly3.getAnomaly().getDueDate());
		Assert.assertEquals(0, fClonedAnomaly3.getAnomaly().getAssignedTo().size());
		Assert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getStartPosition(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly3.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly3.getAnomaly().getLocation().get(0)).getLocation()).getLength());
				*/
	}

	/**
	 * Method cloneAnomaliesCopyPaste
	 */
	private void cloneAnomaliesCopyPaste() {
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly1);
		fProxy.getCommandProxy().copyElements(elementsCopied);
		R4EUISelection targetContent = (R4EUISelection) fItem3.getFileContexts()
				.get(0)
				.getContentsContainerElement()
				.getChildren()[0];
		List<IR4EUIModelElement> elementsPasted = fProxy.getCommandProxy().pasteElements(targetContent);
		fClonedAnomaly4 = (R4EUIAnomalyBasic) elementsPasted.get(0);
		Assert.assertNotNull(fClonedAnomaly4);
		Assert.assertEquals(fAnomaly1.getAnomaly().getTitle(), fClonedAnomaly4.getAnomaly().getTitle());
		Assert.assertEquals(fAnomaly1.getAnomaly().getDescription(), fClonedAnomaly4.getAnomaly().getDescription());
		Assert.assertEquals(((R4ECommentType) fAnomaly1.getAnomaly().getType()).getType(),
				((R4ECommentType) fClonedAnomaly4.getAnomaly().getType()).getType());
		Assert.assertEquals(fAnomaly1.getAnomaly().getRank(), fClonedAnomaly4.getAnomaly().getRank());
		Assert.assertEquals(null, fClonedAnomaly4.getAnomaly().getDueDate());
		Assert.assertEquals(0, fClonedAnomaly4.getAnomaly().getAssignedTo().size());
		Assert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getStartPosition(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly4.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4ETextPosition) targetContent.getContent().getTarget().getLocation()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fClonedAnomaly4.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fClonedAnomaly4, true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method cloneCommentsDragDrop
	 */
	private void cloneCommentsDragDrop() {
		/* TODO drag & drop does not work
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly1Comment1);
		elementsCopied.add(fAnomaly1Comment2);
		List<IR4EUIModelElement> elementsDropped = fProxy.getCommandProxy().dragDropElements(elementsCopied,
				fClonedAnomaly1);
		//NOTE:  For some reason, only the second comment is selected after the paste, so we use another way of verifying the results
		Assert.assertNotNull(fClonedAnomaly1.getChildren()[0]);
		Assert.assertEquals(fAnomaly1Comment1.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[0]).getComment().getDescription());
		Assert.assertNotNull(fClonedAnomaly1.getChildren()[1]);
		Assert.assertEquals(fAnomaly1Comment2.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[1]).getComment().getDescription());
				*/
	}

	/**
	 * Method cloneCommentsCopyPaste
	 */
	private void cloneCommentsCopyPaste() {
		List<IR4EUIModelElement> elementsCopied = new ArrayList<IR4EUIModelElement>();
		elementsCopied.add(fAnomaly2Comment1);
		elementsCopied.add(fAnomaly2Comment2);
		fProxy.getCommandProxy().copyElements(elementsCopied);
		@SuppressWarnings("unused")
		List<IR4EUIModelElement> elementsPasted = fProxy.getCommandProxy().pasteElements(fClonedAnomaly1);
		//NOTE:  For some reason, only the second comment is selected after the paste, so we use another way of verifying the results
		Assert.assertNotNull(fClonedAnomaly1.getChildren()[0]);
		Assert.assertEquals(fAnomaly2Comment1.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[0]).getComment().getDescription());
		Assert.assertNotNull(fClonedAnomaly1.getChildren()[1]);
		Assert.assertEquals(fAnomaly2Comment2.getComment().getDescription(),
				((R4EUIComment) fClonedAnomaly1.getChildren()[1]).getComment().getDescription());
	}
}
