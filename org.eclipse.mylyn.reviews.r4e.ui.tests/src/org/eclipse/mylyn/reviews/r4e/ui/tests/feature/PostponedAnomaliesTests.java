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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
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
public class PostponedAnomaliesTests extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String ORIGINAL_REVIEW_TEST_NAME = "originalReview";

	private static final String ORIGINAL_REVIEW_TEST_DESCRIPTION = "postponed anomalies: original review";

	private static final String ORIGINAL_ANOMALY1_TEST_TITLE = "originalAnomaly1";

	private static final String ORIGINAL_ANOMALY1_TEST_DESCRIPTION = "Original postponed anomaly 1";

	private static final String ORIGINAL_ANOMALY2_TEST_TITLE = "originalAnomaly2";

	private static final String ORIGINAL_ANOMALY2_TEST_DESCRIPTION = "Original postponed anomaly 2";

	private static final String TARGET_REVIEW_TEST_NAME = "targetReview";

	private static final String TARGET_REVIEW_TEST_DESCRIPTION = "postponed anomalies: target review";

	private static final String ORIGINAL_GLOBAL_ANOMALY_TEST_TITLE = "originalGlobalAnomaly";

	private static final String ORIGINAL_GLOBAL_ANOMALY_TEST_DESCRIPTION = "Original Global Postponed Anomaly";

	private static final String TARGET_GLOBAL_REVIEW_TEST_NAME = "globalTargetReview";

	private static final String TARGET_GLOBAL_REVIEW_TEST_DESCRIPTION = "postponed global anomalies: target review";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

	private R4EUIReviewGroup fGroup = null;

	private String fGroupName = null;

	private R4EUIReviewBasic fOriginalReview = null;

	private R4EUIReviewItem fOriginalItem = null;

	private R4EUIReviewItem fOriginalItem2 = null;

	private R4EUIReviewItem fOriginalItem3 = null;

	private String fOriginalItemName = null;

	private R4EUIAnomalyBasic fOriginalAnomaly1 = null;

	private R4EUIAnomalyBasic fOriginalAnomaly2 = null;

	private R4EUIReviewBasic fTargetReview = null;

	private R4EUIReviewItem fTargetItem = null;

	private R4EUIReviewItem fTargetItem2 = null;

	private R4EUIReviewItem fTargetItem3 = null;

	private String fOriginalAnomaly1Title = null;

	private String fOriginalAnomaly2Title = null;

	private String fFile1Name = null;

	private String fFile1VersionID = null;

	private R4EUIPostponedAnomaly fPostponedAnomaly1 = null;;

	private R4EUIPostponedAnomaly fPostponedAnomaly2 = null;

	private int fOrigAnomalyFileIndex;

	private R4EUIAnomalyBasic fOriginalGlobalAnomaly = null;

	private R4EUIReviewBasic fGlobalTargetReview = null;

	private R4EUIPostponedAnomaly fGlobalPostponedAnomaly = null;

	private String fOriginalGlobalAnomalyTitle = null;

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
		suite.addTestSuite(PostponedAnomaliesTests.class);
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
		fProxy.getPreferencesProxy().setGlobalPostponedImport(true);
		createReviewGroups();
		createOriginalReview();
		createOriginalReviewItem();
		createParticipants(fOriginalReview);
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fProxy.getCommandProxy().toggleHideDeltasFilter();
		}
		createOriginalPostponedAnomalies();
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
	public void testPostponedAnomalies() throws CoreException {
		TestUtils.waitForJobs();

		//Global Anomalies
		createOriginalGlobalPostponedAnomalies();
		createGlobalTargetReview();
		importGlobalAnomalies();
		changeGlobalPostponedAnomaliesState();
		changeOriginalGlobalAnomaliesState();
		fixGlobalPostponedAnomalies();

		//Local Anomalies
		createTargetReview();
		createTargetReviewItem();
		createParticipants(fTargetReview);
		importAnomalies();
		changePostponedAnomaliesState();
		changeOriginalAnomaliesState();
		fixPostponedAnomalies();
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
	private void createOriginalReview() {
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

		fOriginalReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				ORIGINAL_REVIEW_TEST_NAME, ORIGINAL_REVIEW_TEST_DESCRIPTION, TestConstants.REVIEW_TEST_DUE_DATE,
				TestConstants.REVIEW_TEST_PROJECT, TestConstants.REVIEW_TEST_COMPONENTS,
				TestConstants.REVIEW_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_TEST_OBJECTIVES,
				TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		Assert.assertNotNull(fOriginalReview);
		Assert.assertNotNull(fOriginalReview.getParticipantContainer());
		Assert.assertNotNull(fOriginalReview.getAnomalyContainer());
		Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fOriginalReview.getReview().getType());
		Assert.assertEquals(ORIGINAL_REVIEW_TEST_NAME, fOriginalReview.getReview().getName());
		Assert.assertEquals(ORIGINAL_REVIEW_TEST_DESCRIPTION, fOriginalReview.getReview().getExtraNotes());
		Assert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fOriginalReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fOriginalReview.getReview()
					.getComponents()
					.get(i));
		}
		Assert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fOriginalReview.getReview().getEntryCriteria());
		Assert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fOriginalReview.getReview().getObjectives());
		Assert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fOriginalReview.getReview()
				.getReferenceMaterial());
		Assert.assertTrue(fOriginalReview.isOpen());
	}

	/**
	 * Method createOriginalReviewItem
	 */
	private void createOriginalReviewItem() throws CoreException {
		fOriginalItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		Assert.assertNotNull(fOriginalItem);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fOriginalItem.getItem().getAddedById());
		Assert.assertEquals("The.committer@some.com", fOriginalItem.getItem().getAuthorRep());
		Assert.assertEquals("second Java Commit", fOriginalItem.getItem().getDescription());
		Assert.assertEquals(4, fOriginalItem.getChildren().length);
		for (int i = 0; i < fOriginalItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				fOrigAnomalyFileIndex = i; //Used later to add anomalies
				Assert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				Assert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
				Assert.assertEquals(606, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(25, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
				Assert.assertEquals(665, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(63, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getLength());
				Assert.assertEquals(733, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(61, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getLength());
				fFile1Name = fOriginalItem.getFileContexts().get(i).getName();
				fFile1VersionID = fOriginalItem.getFileContexts().get(i).getTargetFileVersion().getVersionID();
			} else if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE4_PROJ_NAME)) {
				Assert.assertNull(fOriginalItem.getItem().getFileContextList().get(i).getBase());

				Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE3_PROJ_NAME)) {
				Assert.assertNull(fOriginalItem.getItem().getFileContextList().get(i).getBase());
				Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE2_PROJ_NAME)) {
				Assert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				Assert.assertNull(fOriginalItem.getItem().getFileContextList().get(i).getTarget());
			}
		}

		fOriginalItem2 = fProxy.getItemProxy().createManualTreeItem(TestUtils.FJavaFile3);
		Assert.assertNotNull(fOriginalItem2);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fOriginalItem2.getItem().getAddedById());
		Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		Assert.assertEquals(0,
				((R4ETextPosition) fOriginalItem2.getItem()
						.getFileContextList()
						.get(0)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
		Assert.assertEquals(755, ((R4ETextPosition) fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());

		fOriginalItem3 = fProxy.getItemProxy().createManualTextItem(TestUtils.FJavaFile4, 50, 20);
		Assert.assertNotNull(fOriginalItem3);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fOriginalItem3.getItem().getAddedById());
		Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		Assert.assertEquals(50, ((R4ETextPosition) fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		Assert.assertEquals(20, ((R4ETextPosition) fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());

		fOriginalItemName = fOriginalItem.getName();
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
		participants.add(participant);
		R4EUIParticipant uiParticipant = fProxy.getParticipantProxy().createParticipant(
				aReview.getParticipantContainer(), participants);
		Assert.assertNotNull(uiParticipant);
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, uiParticipant.getParticipant().getId());
		Assert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, uiParticipant.getParticipant().getEmail());
		Assert.assertEquals(R4EUserRole.R4E_ROLE_REVIEWER, uiParticipant.getParticipant().getRoles().get(0));
	}

	/**
	 * Method createOriginalPostponedAnomalies
	 */
	private void createOriginalPostponedAnomalies() {
		//Anomaly1
		R4EUIContent content1 = fOriginalItem.getFileContexts()
				.get(fOrigAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(0);
		fOriginalAnomaly1 = fProxy.getAnomalyProxy().createLinkedAnomaly(content1, ORIGINAL_ANOMALY1_TEST_TITLE,
				ORIGINAL_ANOMALY1_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fOriginalAnomaly1);
		Assert.assertEquals(ORIGINAL_ANOMALY1_TEST_TITLE, fOriginalAnomaly1.getAnomaly().getTitle());
		Assert.assertEquals(ORIGINAL_ANOMALY1_TEST_DESCRIPTION, fOriginalAnomaly1.getAnomaly().getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fOriginalAnomaly1.getAnomaly().getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fOriginalAnomaly1.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fOriginalAnomaly1.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fOriginalAnomaly1.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly1.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly1, TestConstants.ANOMALY_STATE_POSTPONED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly1.getAnomaly().getState());
		fOriginalAnomaly1Title = fOriginalAnomaly1.getAnomaly().getTitle();

		//Anomaly2
		R4EUIContent content2 = fOriginalItem.getFileContexts()
				.get(fOrigAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(1);
		fOriginalAnomaly2 = fProxy.getAnomalyProxy().createLinkedAnomaly(content2, ORIGINAL_ANOMALY2_TEST_TITLE,
				ORIGINAL_ANOMALY2_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		Assert.assertNotNull(fOriginalAnomaly2);
		Assert.assertEquals(ORIGINAL_ANOMALY2_TEST_TITLE, fOriginalAnomaly2.getAnomaly().getTitle());
		Assert.assertEquals(ORIGINAL_ANOMALY2_TEST_DESCRIPTION, fOriginalAnomaly2.getAnomaly().getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fOriginalAnomaly2.getAnomaly().getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fOriginalAnomaly2.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fOriginalAnomaly2.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fOriginalAnomaly2.getAnomaly().getAssignedTo().get(0));
		Assert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
		Assert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly2.getAnomaly().getLocation().get(0)).getLocation()).getLength());
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly2, TestConstants.ANOMALY_STATE_POSTPONED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly2.getAnomaly().getState());
		fOriginalAnomaly2Title = fOriginalAnomaly2.getAnomaly().getTitle();
	}

	/**
	 * Method createTargetReview
	 */
	private void createTargetReview() {
		fTargetReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				TARGET_REVIEW_TEST_NAME, TARGET_REVIEW_TEST_DESCRIPTION, TestConstants.REVIEW_TEST_DUE_DATE,
				TestConstants.REVIEW_TEST_PROJECT, TestConstants.REVIEW_TEST_COMPONENTS,
				TestConstants.REVIEW_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_TEST_OBJECTIVES,
				TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		Assert.assertNotNull(fTargetReview);
		Assert.assertNotNull(fTargetReview.getParticipantContainer());
		Assert.assertNotNull(fTargetReview.getAnomalyContainer());
		Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fTargetReview.getReview().getType());
		Assert.assertEquals(TARGET_REVIEW_TEST_NAME, fTargetReview.getReview().getName());
		Assert.assertEquals(TARGET_REVIEW_TEST_DESCRIPTION, fTargetReview.getReview().getExtraNotes());
		Assert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fTargetReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i],
					fTargetReview.getReview().getComponents().get(i));
		}
		Assert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fTargetReview.getReview().getEntryCriteria());
		Assert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fTargetReview.getReview().getObjectives());
		Assert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fTargetReview.getReview()
				.getReferenceMaterial());
		Assert.assertTrue(fTargetReview.isOpen());
	}

	/**
	 * Method createTargetReviewItem
	 */
	private void createTargetReviewItem() throws CoreException {
		fTargetItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		Assert.assertNotNull(fTargetItem);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fTargetItem.getItem().getAddedById());
		Assert.assertEquals("The.committer@some.com", fTargetItem.getItem().getAuthorRep());
		Assert.assertEquals("second Java Commit", fTargetItem.getItem().getDescription());
		Assert.assertEquals(4, fTargetItem.getChildren().length);
		for (int i = 0; i < fTargetItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {

				Assert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				Assert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
				Assert.assertEquals(606, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(25, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
				Assert.assertEquals(665, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(63, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getLength());
				Assert.assertEquals(733, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				Assert.assertEquals(61, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getLength());
				Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
						((R4EUIFileContext) fTargetItem.getChildren()[i]).getContentsContainerElement().getChildren(),
						true, R4EUIConstants.DELTA_ANNOTATION_ID));
			} else if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE4_PROJ_NAME)) {
				Assert.assertNull(fTargetItem.getItem().getFileContextList().get(i).getBase());
				Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE3_PROJ_NAME)) {
				Assert.assertNull(fTargetItem.getItem().getFileContextList().get(i).getBase());
				Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE2_PROJ_NAME)) {
				Assert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				Assert.assertNull(fTargetItem.getItem().getFileContextList().get(i).getTarget());
			}
		}

		fTargetItem2 = fProxy.getItemProxy().createManualTreeItem(TestUtils.FJavaFile3);
		Assert.assertNotNull(fTargetItem2);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fTargetItem2.getItem().getAddedById());
		Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		Assert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		Assert.assertEquals(0, ((R4ETextPosition) fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		Assert.assertEquals(755,
				((R4ETextPosition) fTargetItem2.getItem()
						.getFileContextList()
						.get(0)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fTargetItem2.getChildren()[0]).getContentsContainerElement().getChildren(), false,
				R4EUIConstants.SELECTION_ANNOTATION_ID));

		fTargetItem3 = fProxy.getItemProxy().createManualTextItem(TestUtils.FJavaFile4, 50, 20);
		Assert.assertNotNull(fTargetItem3);
		Assert.assertEquals(R4EUIModelController.getReviewer(), fTargetItem3.getItem().getAddedById());
		Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		Assert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		Assert.assertEquals(50, ((R4ETextPosition) fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		Assert.assertEquals(20, ((R4ETextPosition) fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fTargetItem3.getChildren()[0]).getContentsContainerElement().getChildren(), true,
				R4EUIConstants.SELECTION_ANNOTATION_ID));
	}

	/**
	 * Method importAnomalies
	 */
	private void importAnomalies() {
		fProxy.getCommandProxy().importPostponedAnomalies(fTargetReview);

		//Verify that the postponed anomalies were correctly imported
		R4EUIPostponedContainer postponedContainer = fTargetReview.getPostponedContainer();
		Assert.assertNotNull(postponedContainer);

		R4EUIPostponedFile postponedFile = (R4EUIPostponedFile) postponedContainer.getChildren()[0];
		Assert.assertNotNull(postponedFile);
		Assert.assertEquals(fFile1Name, postponedFile.getName());
		Assert.assertEquals(fFile1VersionID, postponedFile.getTargetFileVersion().getVersionID());

		fPostponedAnomaly1 = (R4EUIPostponedAnomaly) postponedFile.getChildren()[0];
		Assert.assertNotNull(fPostponedAnomaly1);
		Assert.assertEquals(fOriginalAnomaly1Title, fPostponedAnomaly1.getAnomaly().getTitle());
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fPostponedAnomaly1.getAnomaly().getState());
		Assert.assertEquals(fOriginalReview.getReview().getName(),
				fPostponedAnomaly1.getAnomaly().getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME));

		fPostponedAnomaly2 = (R4EUIPostponedAnomaly) postponedFile.getChildren()[1];
		Assert.assertNotNull(fPostponedAnomaly2);
		Assert.assertEquals(fOriginalAnomaly2Title, fPostponedAnomaly2.getAnomaly().getTitle());
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fPostponedAnomaly2.getAnomaly().getState());
		Assert.assertEquals(fOriginalReview.getReview().getName(),
				fPostponedAnomaly2.getAnomaly().getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME));

		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(fPostponedAnomaly2.getParent().getChildren(),
				false, R4EUIConstants.ANOMALY_CLOSED_ANNOTATION_ID));
	}

	/**
	 * Method changePostponedAnomaliesState
	 */
	private void changePostponedAnomaliesState() {

		//Change postponed anomalies states to ASSIGNED
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly1);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fPostponedAnomaly1.getAnomaly().getState());
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly2);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fPostponedAnomaly2.getAnomaly().getState());
		Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(fPostponedAnomaly2.getParent().getChildren(),
				false, R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));

		//Verify that the original anomalies are also updated
		fProxy.getCommandProxy().openElement(fOriginalReview);
		Assert.assertTrue(fOriginalReview.isOpen());
		Assert.assertFalse(fTargetReview.isOpen());

		for (IR4EUIModelElement elem : fOriginalReview.getChildren()) {
			if (elem.getName().equals(fOriginalItemName)) {
				fOriginalItem = (R4EUIReviewItem) elem;
			}
		}
		for (int i = 0; i < fOriginalItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				fOriginalAnomaly1 = (R4EUIAnomalyBasic) fOriginalItem.getFileContexts()
						.get(i)
						.getAnomalyContainerElement()
						.getChildren()[0];

				fOriginalAnomaly2 = (R4EUIAnomalyBasic) fOriginalItem.getFileContexts()
						.get(i)
						.getAnomalyContainerElement()
						.getChildren()[1];
			}
		}
		Assert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fOriginalAnomaly1.getAnomaly().getState());
		Assert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fOriginalAnomaly2.getAnomaly().getState());
	}

	/**
	 * Method changeOriginalAnomaliesState
	 */
	private void changeOriginalAnomaliesState() {

		//Change original anomalies states back to POSTPONED
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly1, TestConstants.ANOMALY_STATE_POSTPONED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly1.getAnomaly().getState());
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly2, TestConstants.ANOMALY_STATE_POSTPONED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly2.getAnomaly().getState());

		//Re-import of Target review and make sure the postponed anomalies are back
		fProxy.getCommandProxy().openElement(fTargetReview);
		Assert.assertTrue(fTargetReview.isOpen());
		Assert.assertFalse(fOriginalReview.isOpen());
		importAnomalies();
	}

	/**
	 * Method fixPostponedAnomalies
	 */
	private void fixPostponedAnomalies() {
		//Set postponed anomalies state to FIXED
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly1);
		fProxy.getAnomalyProxy().progressAnomaly(fPostponedAnomaly1, TestConstants.ANOMALY_STATE_FIXED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_FIXED, fPostponedAnomaly1.getAnomaly().getState());
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly2);
		fProxy.getAnomalyProxy().progressAnomaly(fPostponedAnomaly2, TestConstants.ANOMALY_STATE_FIXED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_FIXED, fPostponedAnomaly2.getAnomaly().getState());

		//Re-import anomalies and make sure they are disabled
		fProxy.getCommandProxy().importPostponedAnomalies(fTargetReview);
		Assert.assertEquals(0, fTargetReview.getPostponedContainer().getChildren().length);
	}

	/**
	 * Method createOriginalGlobalPostponedAnomalies
	 */
	private void createOriginalGlobalPostponedAnomalies() {
		//Anomaly1
		R4EUIAnomalyContainer cont1 = fOriginalReview.getAnomalyContainer();
		R4EUIAnomalyContainer cont2 = R4EUIModelController.getActiveReview().getAnomalyContainer();
		fOriginalGlobalAnomaly = fProxy.getAnomalyProxy().createGlobalAnomaly(fOriginalReview.getAnomalyContainer(),
				ORIGINAL_GLOBAL_ANOMALY_TEST_TITLE, ORIGINAL_GLOBAL_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, TestConstants.ANOMALY_TEST_RANK_MAJOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, null, TestConstants.PARTICIPANT_ASSIGN_TO);
		Assert.assertNotNull(fOriginalGlobalAnomaly);
		Assert.assertEquals(ORIGINAL_GLOBAL_ANOMALY_TEST_TITLE, fOriginalGlobalAnomaly.getAnomaly().getTitle());
		Assert.assertEquals(ORIGINAL_GLOBAL_ANOMALY_TEST_DESCRIPTION, fOriginalGlobalAnomaly.getAnomaly()
				.getDescription());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fOriginalGlobalAnomaly.getAnomaly().getType()).getType());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fOriginalGlobalAnomaly.getAnomaly().getRank());
		Assert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fOriginalGlobalAnomaly.getAnomaly().getDueDate());
		Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fOriginalGlobalAnomaly.getAnomaly()
				.getAssignedTo()
				.get(0));
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalGlobalAnomaly, TestConstants.ANOMALY_STATE_POSTPONED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalGlobalAnomaly.getAnomaly().getState());
		fOriginalGlobalAnomalyTitle = fOriginalGlobalAnomaly.getAnomaly().getTitle();
	}

	/**
	 * Method createGlobalTargetReview
	 */
	private void createGlobalTargetReview() {
		fGlobalTargetReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				TARGET_GLOBAL_REVIEW_TEST_NAME, TARGET_GLOBAL_REVIEW_TEST_DESCRIPTION,
				TestConstants.REVIEW_TEST_DUE_DATE, TestConstants.REVIEW_TEST_PROJECT,
				TestConstants.REVIEW_TEST_COMPONENTS, TestConstants.REVIEW_TEST_ENTRY_CRITERIA,
				TestConstants.REVIEW_TEST_OBJECTIVES, TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		Assert.assertNotNull(fGlobalTargetReview);
		Assert.assertNotNull(fGlobalTargetReview.getParticipantContainer());
		Assert.assertNotNull(fGlobalTargetReview.getAnomalyContainer());
		Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fGlobalTargetReview.getReview().getType());
		Assert.assertEquals(TARGET_GLOBAL_REVIEW_TEST_NAME, fGlobalTargetReview.getReview().getName());
		Assert.assertEquals(TARGET_GLOBAL_REVIEW_TEST_DESCRIPTION, fGlobalTargetReview.getReview().getExtraNotes());
		Assert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fGlobalTargetReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fGlobalTargetReview.getReview()
					.getComponents()
					.get(i));
		}
		Assert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fGlobalTargetReview.getReview()
				.getEntryCriteria());
		Assert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fGlobalTargetReview.getReview().getObjectives());
		Assert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fGlobalTargetReview.getReview()
				.getReferenceMaterial());
		Assert.assertTrue(fGlobalTargetReview.isOpen());
	}

	/**
	 * Method importGlobalAnomalies
	 */
	private void importGlobalAnomalies() {
		fProxy.getCommandProxy().importPostponedAnomalies(fGlobalTargetReview);

		//Verify that the global postponed anomalies were correctly imported
		R4EUIPostponedContainer postponedContainer = fGlobalTargetReview.getPostponedContainer();
		Assert.assertNotNull(postponedContainer);

		fGlobalPostponedAnomaly = (R4EUIPostponedAnomaly) postponedContainer.getAnomalyContainer().getChildren()[0];
		Assert.assertEquals(fOriginalReview.getReview().getName(), fGlobalPostponedAnomaly.getOriginalReviewName());
		Assert.assertNotNull(fGlobalPostponedAnomaly);
		Assert.assertEquals(fOriginalGlobalAnomalyTitle, fGlobalPostponedAnomaly.getAnomaly().getTitle());
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fGlobalPostponedAnomaly.getAnomaly().getState());
	}

	/**
	 * Method changeGlobalPostponedAnomaliesState
	 */
	private void changeGlobalPostponedAnomaliesState() {

		//Change postponed global anomaly states to ASSIGNED
		fProxy.getCommandProxy().regressElement(fGlobalPostponedAnomaly);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fGlobalPostponedAnomaly.getAnomaly().getState());

		//Verify that the original global anomaly is also updated
		fProxy.getCommandProxy().openElement(fOriginalReview);
		Assert.assertTrue(fOriginalReview.isOpen());
		Assert.assertFalse(fGlobalTargetReview.isOpen());
		fOriginalGlobalAnomaly = (R4EUIAnomalyBasic) fOriginalReview.getAnomalyContainer().getChildren()[0];
		Assert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fOriginalGlobalAnomaly.getAnomaly().getState());
	}

	/**
	 * Method changeOriginalGlobalAnomaliesState
	 */
	private void changeOriginalGlobalAnomaliesState() {

		//Change original global anomaly states back to POSTPONED
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalGlobalAnomaly, TestConstants.ANOMALY_STATE_POSTPONED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalGlobalAnomaly.getAnomaly().getState());

		//Re-import of Target review and make sure the postponed anomalies are back
		fProxy.getCommandProxy().openElement(fGlobalTargetReview);
		Assert.assertTrue(fGlobalTargetReview.isOpen());
		Assert.assertFalse(fOriginalReview.isOpen());
		importGlobalAnomalies();
	}

	/**
	 * Method fixGlobalPostponedAnomalies
	 */
	private void fixGlobalPostponedAnomalies() {
		//Set postponed global anomaly state to FIXED
		fProxy.getCommandProxy().regressElement(fGlobalPostponedAnomaly);
		fProxy.getAnomalyProxy().progressAnomaly(fGlobalPostponedAnomaly, TestConstants.ANOMALY_STATE_FIXED);
		Assert.assertEquals(TestConstants.ANOMALY_STATE_FIXED, fGlobalPostponedAnomaly.getAnomaly().getState());

		//Re-import anomalies and make sure they are disabled
		fProxy.getCommandProxy().importPostponedAnomalies(fGlobalTargetReview);
		Assert.assertEquals(0, fGlobalTargetReview.getPostponedContainer().getChildren().length);
	}
}
