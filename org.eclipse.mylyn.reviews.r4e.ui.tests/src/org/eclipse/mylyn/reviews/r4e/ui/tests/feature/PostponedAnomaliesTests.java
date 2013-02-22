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
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings({ "restriction", "nls" })
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
	// Housekeeping
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
	@Before
	@Override
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
	@After
	@Override
	public void tearDown() throws Exception {
		fProxy = null;
	}

	// ------------------------------------------------------------------------
	// Main test case
	// ------------------------------------------------------------------------

	/**
	 * Method testPostponedAnomalies
	 * 
	 * @throws CoreException
	 */
	public void testPostponedAnomalies() throws CoreException {
		TestUtils.waitForJobs();

		// Global Anomalies
		createOriginalGlobalPostponedAnomalies();
		createGlobalTargetReview();
		importGlobalAnomalies();
		changeGlobalPostponedAnomaliesState();
		changeOriginalGlobalAnomaliesState();
		fixGlobalPostponedAnomalies();

		// Local Anomalies
		createTargetReview();
		createTargetReviewItem();
		createParticipants(fTargetReview);
		importAnomalies();
		changePostponedAnomaliesState();
		changeOriginalAnomaliesState();
		fixPostponedAnomalies();
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
			fGroup = fProxy.getReviewGroupProxy().createReviewGroup(
					TestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME,
					TestConstants.REVIEW_GROUP_TEST_NAME, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
					TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
					TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
			r4eAssert.assertNotNull(fGroup);
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME, fGroup.getReviewGroup().getName());
			r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
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
	 * Method createOriginalReview
	 */
	private void createOriginalReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createOriginalReview");

		// Update Review Group handle
		r4eAssert.setTest("Update Review Group Handle");
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (fGroupName.equals(elem.getName())) {
				fGroup = (R4EUIReviewGroup) elem;
			}
		}
		if (!fGroup.isOpen()) {
			fProxy.getCommandProxy().openElement(fGroup);
		}
		r4eAssert.assertTrue(fGroup.isOpen());

		// Create Review Group
		r4eAssert.setTest("Create Review Group");
		fOriginalReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				ORIGINAL_REVIEW_TEST_NAME, ORIGINAL_REVIEW_TEST_DESCRIPTION, TestConstants.REVIEW_TEST_DUE_DATE,
				TestConstants.REVIEW_TEST_PROJECT, TestConstants.REVIEW_TEST_COMPONENTS,
				TestConstants.REVIEW_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_TEST_OBJECTIVES,
				TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fOriginalReview);
		r4eAssert.assertNotNull(fOriginalReview.getParticipantContainer());
		r4eAssert.assertNotNull(fOriginalReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fOriginalReview.getReview().getType());
		r4eAssert.assertEquals(ORIGINAL_REVIEW_TEST_NAME, fOriginalReview.getReview().getName());
		r4eAssert.assertEquals(ORIGINAL_REVIEW_TEST_DESCRIPTION, fOriginalReview.getReview().getExtraNotes());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fOriginalReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fOriginalReview.getReview()
					.getComponents()
					.get(i));
		}
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fOriginalReview.getReview().getEntryCriteria());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fOriginalReview.getReview().getObjectives());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fOriginalReview.getReview()
				.getReferenceMaterial());
		r4eAssert.assertTrue(fOriginalReview.isOpen());
	}

	/**
	 * Method createOriginalReviewItem
	 */
	private void createOriginalReviewItem() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createOriginalReviewItem");

		r4eAssert.setTest("Create Commit Item");
		fOriginalItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		r4eAssert.assertNotNull(fOriginalItem);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fOriginalItem.getItem().getAddedById());
		r4eAssert.assertEquals("The.committer@some.com", fOriginalItem.getItem().getAuthorRep());
		r4eAssert.assertEquals("second Java Commit", fOriginalItem.getItem().getDescription());
		r4eAssert.assertEquals(4, fOriginalItem.getChildren().length);
		for (int i = 0; i < fOriginalItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				fOrigAnomalyFileIndex = i; //Used later to add anomalies
				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
				r4eAssert.assertEquals(606, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(25, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(665, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(63, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(733, ((R4ETextPosition) fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(61, ((R4ETextPosition) fOriginalItem.getItem()
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
				r4eAssert.assertNull(fOriginalItem.getItem().getFileContextList().get(i).getBase());

				r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE3_PROJ_NAME)) {
				r4eAssert.assertNull(fOriginalItem.getItem().getFileContextList().get(i).getBase());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fOriginalItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE2_PROJ_NAME)) {
				r4eAssert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fOriginalItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertNull(fOriginalItem.getItem().getFileContextList().get(i).getTarget());
			}
		}

		r4eAssert.setTest("Create Manual Tree Item");
		fOriginalItem2 = fProxy.getItemProxy().createManualTreeItem(TestUtils.FJavaFile3);
		r4eAssert.assertNotNull(fOriginalItem2);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fOriginalItem2.getItem().getAddedById());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		r4eAssert.assertEquals(0, ((R4ETextPosition) fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		r4eAssert.assertEquals(755, ((R4ETextPosition) fOriginalItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());

		r4eAssert.setTest("Create Manual Text Item");
		fOriginalItem3 = fProxy.getItemProxy().createManualTextItem(TestUtils.FJavaFile4, 50, 20);
		r4eAssert.assertNotNull(fOriginalItem3);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fOriginalItem3.getItem().getAddedById());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		r4eAssert.assertEquals(50, ((R4ETextPosition) fOriginalItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		r4eAssert.assertEquals(20, ((R4ETextPosition) fOriginalItem3.getItem()
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

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createParticipants");

		r4eAssert.setTest("Create Participant");
		List<R4EParticipant> participants = new ArrayList<R4EParticipant>(1);
		R4EParticipant participant = RModelFactory.eINSTANCE.createR4EParticipant();
		participant.setId(TestConstants.PARTICIPANT_TEST_ID);
		participant.setEmail(TestConstants.PARTICIPANT_TEST_EMAIL);
		participants.add(participant);
		R4EUIParticipant uiParticipant = fProxy.getParticipantProxy().createParticipant(
				aReview.getParticipantContainer(), participants);
		r4eAssert.assertNotNull(uiParticipant);
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, uiParticipant.getParticipant().getId());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, uiParticipant.getParticipant().getEmail());
		r4eAssert.assertEquals(R4EUserRole.REVIEWER, uiParticipant.getParticipant().getRoles().get(0));
	}

	// ------------------------------------------------------------------------
	// Create Original Postponed Anomalies
	// ------------------------------------------------------------------------

	/**
	 * Method createOriginalPostponedAnomalies
	 */
	private void createOriginalPostponedAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createOriginalPostponedAnomalies");

		// Anomaly1
		r4eAssert.setTest("Anomaly 1");
		R4EUIContent content1 = fOriginalItem.getFileContexts()
				.get(fOrigAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(0);
		fOriginalAnomaly1 = fProxy.getAnomalyProxy().createLinkedAnomaly(content1, ORIGINAL_ANOMALY1_TEST_TITLE,
				ORIGINAL_ANOMALY1_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fOriginalAnomaly1);
		r4eAssert.assertEquals(ORIGINAL_ANOMALY1_TEST_TITLE, fOriginalAnomaly1.getAnomaly().getTitle());
		r4eAssert.assertEquals(ORIGINAL_ANOMALY1_TEST_DESCRIPTION, fOriginalAnomaly1.getAnomaly().getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fOriginalAnomaly1.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fOriginalAnomaly1.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fOriginalAnomaly1.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO,
				fOriginalAnomaly1.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly1.getAnomaly().getLocations().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4EUITextPosition) content1.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly1.getAnomaly().getLocations().get(0)).getLocation()).getLength());
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly1, TestConstants.ANOMALY_STATE_POSTPONED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly1.getAnomaly().getState());
		fOriginalAnomaly1Title = fOriginalAnomaly1.getAnomaly().getTitle();

		// Anomaly2
		r4eAssert.setTest("Anomaly 2");
		R4EUIContent content2 = fOriginalItem.getFileContexts()
				.get(fOrigAnomalyFileIndex)
				.getContentsContainerElement()
				.getContentsList()
				.get(1);
		fOriginalAnomaly2 = fProxy.getAnomalyProxy().createLinkedAnomaly(content2, ORIGINAL_ANOMALY2_TEST_TITLE,
				ORIGINAL_ANOMALY2_TEST_DESCRIPTION, TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				TestConstants.ANOMALY_TEST_RANK_MAJOR, TestConstants.ANOMALY_TEST_DUE_DATE,
				TestConstants.PARTICIPANT_ASSIGN_TO, null);
		r4eAssert.assertNotNull(fOriginalAnomaly2);
		r4eAssert.assertEquals(ORIGINAL_ANOMALY2_TEST_TITLE, fOriginalAnomaly2.getAnomaly().getTitle());
		r4eAssert.assertEquals(ORIGINAL_ANOMALY2_TEST_DESCRIPTION, fOriginalAnomaly2.getAnomaly().getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fOriginalAnomaly2.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fOriginalAnomaly2.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fOriginalAnomaly2.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO,
				fOriginalAnomaly2.getAnomaly().getAssignedTo().get(0));
		r4eAssert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getOffset(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly2.getAnomaly().getLocations().get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(
				((R4EUITextPosition) content2.getPosition()).getLength(),
				((R4ETextPosition) ((R4ETextContent) fOriginalAnomaly2.getAnomaly().getLocations().get(0)).getLocation()).getLength());
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly2, TestConstants.ANOMALY_STATE_POSTPONED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly2.getAnomaly().getState());
		fOriginalAnomaly2Title = fOriginalAnomaly2.getAnomaly().getTitle();
	}

	// ------------------------------------------------------------------------
	// Create Target Review
	// ------------------------------------------------------------------------

	/**
	 * Method createTargetReview
	 */
	private void createTargetReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createTargetReview");

		r4eAssert.setTest("Create Review");
		fTargetReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				TARGET_REVIEW_TEST_NAME, TARGET_REVIEW_TEST_DESCRIPTION, TestConstants.REVIEW_TEST_DUE_DATE,
				TestConstants.REVIEW_TEST_PROJECT, TestConstants.REVIEW_TEST_COMPONENTS,
				TestConstants.REVIEW_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_TEST_OBJECTIVES,
				TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fTargetReview);
		r4eAssert.assertNotNull(fTargetReview.getParticipantContainer());
		r4eAssert.assertNotNull(fTargetReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fTargetReview.getReview().getType());
		r4eAssert.assertEquals(TARGET_REVIEW_TEST_NAME, fTargetReview.getReview().getName());
		r4eAssert.assertEquals(TARGET_REVIEW_TEST_DESCRIPTION, fTargetReview.getReview().getExtraNotes());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fTargetReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fTargetReview.getReview()
					.getComponents()
					.get(i));
		}
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fTargetReview.getReview().getEntryCriteria());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fTargetReview.getReview().getObjectives());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fTargetReview.getReview()
				.getReferenceMaterial());
		r4eAssert.assertTrue(fTargetReview.isOpen());
	}

	// ------------------------------------------------------------------------
	// Create Target Review Item
	// ------------------------------------------------------------------------

	/**
	 * Method createTargetReviewItem
	 */
	private void createTargetReviewItem() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createTargetReviewItem");

		r4eAssert.setTest("Create Commit Item");
		fTargetItem = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);
		r4eAssert.assertNotNull(fTargetItem);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fTargetItem.getItem().getAddedById());
		r4eAssert.assertEquals("The.committer@some.com", fTargetItem.getItem().getAuthorRep());
		r4eAssert.assertEquals("second Java Commit", fTargetItem.getItem().getDescription());
		r4eAssert.assertEquals(4, fTargetItem.getChildren().length);
		for (int i = 0; i < fTargetItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {

				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
				r4eAssert.assertEquals(606, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(25, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(665, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(63, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(733, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(61, ((R4ETextPosition) fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
						((R4EUIFileContext) fTargetItem.getChildren()[i]).getContentsContainerElement().getChildren(),
						true, R4EUIConstants.DELTA_ANNOTATION_ID));
			} else if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE4_PROJ_NAME)) {
				r4eAssert.assertNull(fTargetItem.getItem().getFileContextList().get(i).getBase());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE3_PROJ_NAME)) {
				r4eAssert.assertNull(fTargetItem.getItem().getFileContextList().get(i).getBase());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fTargetItem.getChildren()[i]).getName().equals(
					TestUtils.JAVA_FILE2_PROJ_NAME)) {
				r4eAssert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fTargetItem.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertNull(fTargetItem.getItem().getFileContextList().get(i).getTarget());
			}
		}

		r4eAssert.setTest("Create Manual Tree Item");
		fTargetItem2 = fProxy.getItemProxy().createManualTreeItem(TestUtils.FJavaFile3);
		r4eAssert.assertNotNull(fTargetItem2);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fTargetItem2.getItem().getAddedById());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		r4eAssert.assertEquals(0, ((R4ETextPosition) fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		r4eAssert.assertEquals(755, ((R4ETextPosition) fTargetItem2.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fTargetItem2.getChildren()[0]).getContentsContainerElement().getChildren(), false,
				R4EUIConstants.SELECTION_ANNOTATION_ID));

		r4eAssert.setTest("Create Manual Text Item");
		fTargetItem3 = fProxy.getItemProxy().createManualTextItem(TestUtils.FJavaFile4, 50, 20);
		r4eAssert.assertNotNull(fTargetItem3);
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fTargetItem3.getItem().getAddedById());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getBase()
				.getName());
		r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getTarget()
				.getName());
		r4eAssert.assertEquals(50, ((R4ETextPosition) fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getStartPosition());
		r4eAssert.assertEquals(20, ((R4ETextPosition) fTargetItem3.getItem()
				.getFileContextList()
				.get(0)
				.getDeltas()
				.get(0)
				.getTarget()
				.getLocation()).getLength());
		r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(
				((R4EUIFileContext) fTargetItem3.getChildren()[0]).getContentsContainerElement().getChildren(), true,
				R4EUIConstants.SELECTION_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Import Anomalies
	// ------------------------------------------------------------------------

	/**
	 * Method importAnomalies
	 */
	private void importAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("importAnomalies");

		r4eAssert.setTest("Import Postponed Anomaly");
		fProxy.getCommandProxy().importPostponedAnomalies(fTargetReview);

		// Verify that the postponed anomalies were correctly imported
		R4EUIPostponedContainer postponedContainer = fTargetReview.getPostponedContainer();
		r4eAssert.assertNotNull(postponedContainer);

		R4EUIPostponedFile postponedFile = (R4EUIPostponedFile) postponedContainer.getChildren()[0];
		r4eAssert.assertNotNull(postponedFile);
		r4eAssert.assertEquals(fFile1Name, postponedFile.getName());
		r4eAssert.assertEquals(fFile1VersionID, postponedFile.getTargetFileVersion().getVersionID());

		r4eAssert.setTest("Postponed Anomaly 1");
		fPostponedAnomaly1 = (R4EUIPostponedAnomaly) postponedFile.getChildren()[0];
		r4eAssert.assertNotNull(fPostponedAnomaly1);
		r4eAssert.assertEquals(fOriginalAnomaly1Title, fPostponedAnomaly1.getAnomaly().getTitle());
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fPostponedAnomaly1.getAnomaly().getState());
		r4eAssert.assertEquals(fOriginalReview.getReview().getName(),
				fPostponedAnomaly1.getAnomaly().getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME));

		r4eAssert.setTest("Postponed Anomaly 2");
		fPostponedAnomaly2 = (R4EUIPostponedAnomaly) postponedFile.getChildren()[1];
		r4eAssert.assertNotNull(fPostponedAnomaly2);
		r4eAssert.assertEquals(fOriginalAnomaly2Title, fPostponedAnomaly2.getAnomaly().getTitle());
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fPostponedAnomaly2.getAnomaly().getState());
		r4eAssert.assertEquals(fOriginalReview.getReview().getName(),
				fPostponedAnomaly2.getAnomaly().getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME));

		r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(fPostponedAnomaly2.getParent().getChildren(),
				false, R4EUIConstants.ANOMALY_CLOSED_ANNOTATION_ID));
	}

	// ------------------------------------------------------------------------
	// Change Postponed Anomaly State
	// ------------------------------------------------------------------------

	/**
	 * Method changePostponedAnomaliesState
	 */
	private void changePostponedAnomaliesState() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("changePostponedAnomaliesState");

		// Change postponed anomalies states to ASSIGNED
		r4eAssert.setTest("Assign Postponed Anomaly");
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly1);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fPostponedAnomaly1.getAnomaly().getState());
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly2);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fPostponedAnomaly2.getAnomaly().getState());
		r4eAssert.assertTrue(fProxy.getCommandProxy().verifyAnnotations(fPostponedAnomaly2.getParent().getChildren(),
				false, R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));

		// Verify that the original anomalies are also updated
		r4eAssert.setTest("Verify Original Anomaly");
		fProxy.getCommandProxy().openElement(fOriginalReview);
		r4eAssert.assertTrue(fOriginalReview.isOpen());
		r4eAssert.assertFalse(fTargetReview.isOpen());

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
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fOriginalAnomaly1.getAnomaly().getState());
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fOriginalAnomaly2.getAnomaly().getState());
	}

	// ------------------------------------------------------------------------
	// Change Original Anomaly State
	// ------------------------------------------------------------------------

	/**
	 * Method changeOriginalAnomaliesState
	 */
	private void changeOriginalAnomaliesState() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("changeOriginalAnomaliesState");

		// Change original anomalies states back to POSTPONED
		r4eAssert.setTest("Postpone Anomaly");
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly1, TestConstants.ANOMALY_STATE_POSTPONED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly1.getAnomaly().getState());
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalAnomaly2, TestConstants.ANOMALY_STATE_POSTPONED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalAnomaly2.getAnomaly().getState());

		// Re-import of Target review and make sure the postponed anomalies are back
		r4eAssert.setTest("Import Anomalies");
		fProxy.getCommandProxy().openElement(fTargetReview);
		r4eAssert.assertTrue(fTargetReview.isOpen());
		r4eAssert.assertFalse(fOriginalReview.isOpen());
		importAnomalies();
	}

	// ------------------------------------------------------------------------
	// Fix Postponed Anomaly
	// ------------------------------------------------------------------------

	/**
	 * Method fixPostponedAnomalies
	 */
	private void fixPostponedAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("fixPostponedAnomalies");

		// Set postponed anomalies state to FIXED
		r4eAssert.setTest("Fix Anomaly");
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly1);
		fProxy.getAnomalyProxy().progressAnomaly(fPostponedAnomaly1, TestConstants.ANOMALY_STATE_FIXED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_FIXED, fPostponedAnomaly1.getAnomaly().getState());
		fProxy.getCommandProxy().regressElement(fPostponedAnomaly2);
		fProxy.getAnomalyProxy().progressAnomaly(fPostponedAnomaly2, TestConstants.ANOMALY_STATE_FIXED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_FIXED, fPostponedAnomaly2.getAnomaly().getState());

		// Re-import anomalies and make sure they are disabled
		r4eAssert.setTest("Check Anomaly");
		fProxy.getCommandProxy().importPostponedAnomalies(fTargetReview);
		r4eAssert.assertEquals(0, fTargetReview.getPostponedContainer().getChildren().length);
	}

	// ------------------------------------------------------------------------
	// Create Original Global Postponed Anomaly
	// ------------------------------------------------------------------------

	/**
	 * Method createOriginalGlobalPostponedAnomalies
	 */
	private void createOriginalGlobalPostponedAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createOriginalGlobalPostponedAnomalies");

		// Anomaly1
		r4eAssert.setTest("Create Global Anomaly");
//		R4EUIAnomalyContainer cont1 = fOriginalReview.getAnomalyContainer();
//		R4EUIAnomalyContainer cont2 = R4EUIModelController.getActiveReview().getAnomalyContainer();
		fOriginalGlobalAnomaly = fProxy.getAnomalyProxy().createGlobalAnomaly(fOriginalReview.getAnomalyContainer(),
				ORIGINAL_GLOBAL_ANOMALY_TEST_TITLE, ORIGINAL_GLOBAL_ANOMALY_TEST_DESCRIPTION,
				TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, TestConstants.ANOMALY_TEST_RANK_MAJOR,
				TestConstants.ANOMALY_TEST_DUE_DATE, null, TestConstants.PARTICIPANT_ASSIGN_TO);
		r4eAssert.assertNotNull(fOriginalGlobalAnomaly);
		r4eAssert.assertEquals(ORIGINAL_GLOBAL_ANOMALY_TEST_TITLE, fOriginalGlobalAnomaly.getAnomaly().getTitle());
		r4eAssert.assertEquals(ORIGINAL_GLOBAL_ANOMALY_TEST_DESCRIPTION, fOriginalGlobalAnomaly.getAnomaly()
				.getDescription());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
				((R4ECommentType) fOriginalGlobalAnomaly.getAnomaly().getType()).getType());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fOriginalGlobalAnomaly.getAnomaly().getRank());
		r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fOriginalGlobalAnomaly.getAnomaly().getDueDate());
		r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fOriginalGlobalAnomaly.getAnomaly()
				.getAssignedTo()
				.get(0));
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalGlobalAnomaly, TestConstants.ANOMALY_STATE_POSTPONED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalGlobalAnomaly.getAnomaly().getState());
		fOriginalGlobalAnomalyTitle = fOriginalGlobalAnomaly.getAnomaly().getTitle();
	}

	// ------------------------------------------------------------------------
	// Create Global Target Review
	// ------------------------------------------------------------------------

	/**
	 * Method createGlobalTargetReview
	 */
	private void createGlobalTargetReview() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createGlobalTargetReview");

		r4eAssert.setTest("Create Review");
		fGlobalTargetReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				TARGET_GLOBAL_REVIEW_TEST_NAME, TARGET_GLOBAL_REVIEW_TEST_DESCRIPTION,
				TestConstants.REVIEW_TEST_DUE_DATE, TestConstants.REVIEW_TEST_PROJECT,
				TestConstants.REVIEW_TEST_COMPONENTS, TestConstants.REVIEW_TEST_ENTRY_CRITERIA,
				TestConstants.REVIEW_TEST_OBJECTIVES, TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fGlobalTargetReview);
		r4eAssert.assertNotNull(fGlobalTargetReview.getParticipantContainer());
		r4eAssert.assertNotNull(fGlobalTargetReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fGlobalTargetReview.getReview().getType());
		r4eAssert.assertEquals(TARGET_GLOBAL_REVIEW_TEST_NAME, fGlobalTargetReview.getReview().getName());
		r4eAssert.assertEquals(TARGET_GLOBAL_REVIEW_TEST_DESCRIPTION, fGlobalTargetReview.getReview().getExtraNotes());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, fGlobalTargetReview.getReview().getProject());
		for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], fGlobalTargetReview.getReview()
					.getComponents()
					.get(i));
		}
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, fGlobalTargetReview.getReview()
				.getEntryCriteria());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, fGlobalTargetReview.getReview().getObjectives());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, fGlobalTargetReview.getReview()
				.getReferenceMaterial());
		r4eAssert.assertTrue(fGlobalTargetReview.isOpen());
	}

	// ------------------------------------------------------------------------
	// Import Global Anomaly 
	// ------------------------------------------------------------------------

	/**
	 * Method importGlobalAnomalies
	 */
	private void importGlobalAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("importGlobalAnomalies");

		r4eAssert.setTest("Import Postponed Anomaly");
		fProxy.getCommandProxy().importPostponedAnomalies(fGlobalTargetReview);

		// Verify that the global postponed anomalies were correctly imported
		R4EUIPostponedContainer postponedContainer = fGlobalTargetReview.getPostponedContainer();
		r4eAssert.assertNotNull(postponedContainer);

		r4eAssert.setTest("Check Anomaly");
		fGlobalPostponedAnomaly = (R4EUIPostponedAnomaly) postponedContainer.getAnomalyContainer().getChildren()[0];
		r4eAssert.assertEquals(fOriginalReview.getReview().getName(), fGlobalPostponedAnomaly.getOriginalReviewName());
		r4eAssert.assertNotNull(fGlobalPostponedAnomaly);
		r4eAssert.assertEquals(fOriginalGlobalAnomalyTitle, fGlobalPostponedAnomaly.getAnomaly().getTitle());
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fGlobalPostponedAnomaly.getAnomaly().getState());
	}

	// ------------------------------------------------------------------------
	// Change Global Postponed Anomaly State 
	// ------------------------------------------------------------------------

	/**
	 * Method changeGlobalPostponedAnomaliesState
	 */
	private void changeGlobalPostponedAnomaliesState() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("changeGlobalPostponedAnomaliesState");

		// Change postponed global anomaly states to ASSIGNED
		r4eAssert.setTest("Change Postponed Anomaly");
		fProxy.getCommandProxy().regressElement(fGlobalPostponedAnomaly);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fGlobalPostponedAnomaly.getAnomaly().getState());

		// Verify that the original global anomaly is also updated
		r4eAssert.setTest("Verify Anomaly");
		fProxy.getCommandProxy().openElement(fOriginalReview);
		r4eAssert.assertTrue(fOriginalReview.isOpen());
		r4eAssert.assertFalse(fGlobalTargetReview.isOpen());
		fOriginalGlobalAnomaly = (R4EUIAnomalyBasic) fOriginalReview.getAnomalyContainer().getChildren()[0];
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_ASSIGNED, fOriginalGlobalAnomaly.getAnomaly().getState());
	}

	// ------------------------------------------------------------------------
	// Change Global Postponed Anomaly State 
	// ------------------------------------------------------------------------

	/**
	 * Method changeOriginalGlobalAnomaliesState
	 */
	private void changeOriginalGlobalAnomaliesState() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("changeOriginalGlobalAnomaliesState");

		// Change original global anomaly states back to POSTPONED
		r4eAssert.setTest("Change Global Anomaly");
		fProxy.getAnomalyProxy().progressAnomaly(fOriginalGlobalAnomaly, TestConstants.ANOMALY_STATE_POSTPONED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_POSTPONED, fOriginalGlobalAnomaly.getAnomaly().getState());

		// Re-import of Target review and make sure the postponed anomalies are back
		r4eAssert.setTest("Verify Anomaly");
		fProxy.getCommandProxy().openElement(fGlobalTargetReview);
		r4eAssert.assertTrue(fGlobalTargetReview.isOpen());
		r4eAssert.assertFalse(fOriginalReview.isOpen());
		importGlobalAnomalies();
	}

	// ------------------------------------------------------------------------
	// Fix Global Postponed Anomaly State 
	// ------------------------------------------------------------------------

	/**
	 * Method fixGlobalPostponedAnomalies
	 */
	private void fixGlobalPostponedAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("fixGlobalPostponedAnomalies");

		// Set postponed global anomaly state to FIXED
		r4eAssert.setTest("Change Global Anomaly");
		fProxy.getCommandProxy().regressElement(fGlobalPostponedAnomaly);
		fProxy.getAnomalyProxy().progressAnomaly(fGlobalPostponedAnomaly, TestConstants.ANOMALY_STATE_FIXED);
		r4eAssert.assertEquals(TestConstants.ANOMALY_STATE_FIXED, fGlobalPostponedAnomaly.getAnomaly().getState());

		// Re-import anomalies and make sure they are disabled
		r4eAssert.setTest("Verify Anomaly");
		fProxy.getCommandProxy().importPostponedAnomalies(fGlobalTargetReview);
		r4eAssert.assertEquals(0, fGlobalTargetReview.getPostponedContainer().getChildren().length);
	}
}
