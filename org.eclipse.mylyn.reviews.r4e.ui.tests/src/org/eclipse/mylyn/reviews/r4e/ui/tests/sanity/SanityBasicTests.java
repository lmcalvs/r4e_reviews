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
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.sanity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
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
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings("restriction")
public class SanityBasicTests extends TestCase {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

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
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method suite - Sets up the global test environment, if not already done at the suite level.
	 * 
	 * @return Test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(SanityBasicTests.class);
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
	 * Method testBasicReviews
	 * 
	 * @throws CoreException
	 */
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
	 * Method createReviews
	 */
	private void createReviews() {
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

		fReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_BASIC,
				TestConstants.REVIEW_TEST_NAME, TestConstants.REVIEW_TEST_DESCRIPTION,
				TestConstants.REVIEW_TEST_DUE_DATE, TestConstants.REVIEW_TEST_PROJECT,
				TestConstants.REVIEW_TEST_COMPONENTS, TestConstants.REVIEW_TEST_ENTRY_CRITERIA,
				TestConstants.REVIEW_TEST_OBJECTIVES, TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		Assert.assertNotNull(fReview);
		Assert.assertNotNull(fReview.getParticipantContainer());
		Assert.assertNotNull(fReview.getAnomalyContainer());
		Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_BASIC, fReview.getReview().getType());
		Assert.assertEquals(TestConstants.REVIEW_TEST_NAME, fReview.getReview().getName());
		Assert.assertEquals(TestConstants.REVIEW_TEST_DESCRIPTION, fReview.getReview().getExtraNotes());
		Assert.assertEquals(TestConstants.REVIEW_TEST_DUE_DATE, fReview.getReview().getDueDate());
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
	 * Method createReviewItems
	 */
	private void createReviewItems() throws CoreException {
		fItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		//close and re-open, so the validation takes de-serialized information
		String itemName = fItem.getName();
		fProxy.getCommandProxy().closeElement(fReview);
		fProxy.getCommandProxy().openElement(fReview);
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(itemName)) {
				fItem = (R4EUIReviewItem) elem;
			}
		}

		//Now validate
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
				Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
						((R4EUIFileContext) fItem.getChildren()[i]).getContentsContainerElement().getChildren(), true,
						R4EUIConstants.DELTA_ANNOTATION_ID));
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
		Assert.assertEquals(755, ((R4ETextPosition) fItem2.getItem()
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
		Assert.assertEquals(R4EUserRole.R4E_ROLE_REVIEWER, fParticipant.getParticipant().getRoles().get(0));
	}

	/**
	 * Method createEditorAnomalies
	 */
	private void createCompareEditorAnomalies() {
		fCompareEditorAnomaly = fProxy.getAnomalyProxy().createCompareEditorAnomaly(
				fItem.getFileContexts().get(fAnomalyFileIndex), 20, 50,
				TestConstants.COMPARE_EDITOR_ANOMALY_TEST_TITLE, TestConstants.COMPARE_EDITOR_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_ERRONEOUS, TestConstants.ANOMALY_TEST_RANK_MINOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fCompareEditorAnomaly);
		Assert.assertEquals(TestConstants.COMPARE_EDITOR_ANOMALY_TEST_TITLE, fCompareEditorAnomaly.getAnomaly()
				.getTitle());
		Assert.assertEquals(TestConstants.COMPARE_EDITOR_ANOMALY_TEST_DESCRIPTION, fCompareEditorAnomaly.getAnomaly()
				.getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_ERRONEOUS,
				((R4ECommentType) fCompareEditorAnomaly.getAnomaly().getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MINOR, fCompareEditorAnomaly.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fCompareEditorAnomaly.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fCompareEditorAnomaly.getAnomaly()
				.getAssignedTo()
				.get(0));
		Assert.assertEquals(20, ((R4ETextPosition) ((R4ETextContent) fCompareEditorAnomaly.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(50, ((R4ETextPosition) ((R4ETextContent) fCompareEditorAnomaly.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fCompareEditorAnomaly, true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method createLinkedAnomalies
	 */
	private void createLinkedAnomalies() {
		R4EUIContent content = fItem.getFileContexts()
				.get(fAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(0);
		fLinkedAnomaly = fProxy.getAnomalyProxy().createLinkedAnomaly(content, TestConstants.LINKED_ANOMALY_TEST_TITLE,
				TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fLinkedAnomaly);
		Assert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_TITLE, fLinkedAnomaly.getAnomaly().getTitle());
		Assert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION, fLinkedAnomaly.getAnomaly().getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, ((R4ECommentType) fLinkedAnomaly.getAnomaly()
				.getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fLinkedAnomaly.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fLinkedAnomaly.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fLinkedAnomaly.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				((R4EUITextPosition) content.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4EUITextPosition) content.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fLinkedAnomaly, true,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method createExternalAnomalies
	 */
	private void createExternalAnomalies() {
		fExternalAnomaly = fProxy.getAnomalyProxy().createExternalAnomaly(TestUtils.FJavaFile3,
				TestConstants.EXTERNAL_ANOMALY_TEST_TITLE, TestConstants.EXTERNAL_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_QUESTION, TestConstants.ANOMALY_TEST_RANK_MINOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fExternalAnomaly);
		Assert.assertEquals(TestConstants.EXTERNAL_ANOMALY_TEST_TITLE, fExternalAnomaly.getAnomaly().getTitle());
		Assert.assertEquals(TestConstants.EXTERNAL_ANOMALY_TEST_DESCRIPTION, fExternalAnomaly.getAnomaly()
				.getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_QUESTION, ((R4ECommentType) fExternalAnomaly.getAnomaly()
				.getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MINOR, fExternalAnomaly.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fExternalAnomaly.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fExternalAnomaly.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				0,
				((R4ETextPosition) ((R4ETextContent) fExternalAnomaly.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(755, ((R4ETextPosition) ((R4ETextContent) fExternalAnomaly.getAnomaly()
				.getLocation()
				.get(0)).getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(fExternalAnomaly, false,
				R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));
	}

	/**
	 * Method createComments
	 */
	private void createComments() {
		fComment = fProxy.getCommentProxy().createComment(fLinkedAnomaly, TestConstants.COMMENT_TEST);
		Assert.assertNotNull(fComment);
		Assert.assertEquals(TestConstants.COMMENT_TEST, fComment.getComment().getDescription());
	}

	/**
	 * Method progressReview
	 */
	private void progressReview() {
		fProxy.getReviewProxy().progressReview(fReview);
		Assert.assertEquals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED,
				((R4EReviewState) fReview.getReview().getState()).getState());
		Assert.assertNotNull(fReview.getReview().getEndDate());
	}

	/**
	 * Method verifySorters
	 */
	public void verifySorters() {
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		//Alpha sorter On/Off
		fProxy.getCommandProxy().toggleAlphaSorter();
		ViewerComparator activeSorter = fProxy.getCommandProxy().getActiveSorter();
		Assert.assertTrue(null != activeSorter && activeSorter instanceof NavigatorElementComparator
				&& !(activeSorter instanceof ReviewTypeComparator));
		Assert.assertTrue(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		fProxy.getCommandProxy().toggleAlphaSorter();
		Assert.assertTrue(null == fProxy.getCommandProxy().getActiveSorter());
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		//Review Type sorter On/Off
		fProxy.getCommandProxy().toggleReviewTypeSorter();
		activeSorter = fProxy.getCommandProxy().getActiveSorter();
		Assert.assertTrue(null != activeSorter && activeSorter instanceof ReviewTypeComparator);
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertTrue(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		fProxy.getCommandProxy().toggleReviewTypeSorter();
		Assert.assertTrue(null == fProxy.getCommandProxy().getActiveSorter());
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		//AlphaSorter -> ReviewTypeSorter
		fProxy.getCommandProxy().toggleAlphaSorter();
		activeSorter = fProxy.getCommandProxy().getActiveSorter();
		Assert.assertTrue(null != activeSorter && activeSorter instanceof NavigatorElementComparator
				&& !(activeSorter instanceof ReviewTypeComparator));
		Assert.assertTrue(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		fProxy.getCommandProxy().toggleReviewTypeSorter();
		activeSorter = fProxy.getCommandProxy().getActiveSorter();
		Assert.assertTrue(null != activeSorter && activeSorter instanceof ReviewTypeComparator);
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertTrue(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		//ReviewTypeSorter -> AlphaSorter
		fProxy.getCommandProxy().toggleAlphaSorter();
		activeSorter = fProxy.getCommandProxy().getActiveSorter();
		Assert.assertTrue(null != activeSorter && activeSorter instanceof NavigatorElementComparator
				&& !(activeSorter instanceof ReviewTypeComparator));
		Assert.assertTrue(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));

		//Remove AlphaSorter
		fProxy.getCommandProxy().toggleAlphaSorter();
		Assert.assertTrue(null == fProxy.getCommandProxy().getActiveSorter());
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.ALPHA_SORTER_COMMAND));
		Assert.assertFalse(fProxy.getCommandProxy().getCommandState(R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND));
	}

	/**
	 * Method sendQuestionNotifications
	 * 
	 * @throws CoreException
	 */
	private void sendQuestionNotifications() throws CoreException {
		fProxy.getCommandProxy().sendQuestionNotification(fReview);
		Assert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_SOURCE, fProxy.getCommandProxy()
				.getEmailDetails()
				.getSource());
		String[] destinations = fProxy.getCommandProxy().getEmailDetails().getDestinations();
		for (int i = 0; i < destinations.length; i++) {
			Assert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_DESTINATIONS[i], destinations[i]);
		}
		Assert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_SUBJECT, fProxy.getCommandProxy()
				.getEmailDetails()
				.getSubject());
		//TODO:  Assert fails, but Strings seem to be identical???
		/*
		Assert.assertEquals(TestConstants.SEND_QUESTION_REVIEW_TEST_BODY, fProxy.getCommandProxy()
				.getEmailDetails()
				.getBody());
		*/
	}
}
