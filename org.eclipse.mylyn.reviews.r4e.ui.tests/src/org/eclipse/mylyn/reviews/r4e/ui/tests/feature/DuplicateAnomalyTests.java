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
 * This class implements a JUnit Test Case for the anomaly store on the 
 * latest Review commit 
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
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
public class DuplicateAnomalyTests extends TestCase {

	// ------------------------------------------------------------------------
	// Constant
	// ------------------------------------------------------------------------

	private final static String FSECOND_COMMIT = "second Java Commit";

	private final static String FTHIRD_COMMIT = "third Java Commit";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

	private R4EUIReviewGroup fGroup = null;

	@SuppressWarnings("unused")
	private String fGroupName = null;

	private R4EUIReviewBasic fReview = null;

	private R4EUIReviewItem fItem = null;

	private R4EUIReviewItem fItem2 = null;

	private R4EUIReviewItem fItem3 = null;

	private R4EUIReviewItem fItem4 = null;

	private R4EUIAnomalyBasic fLinkedAnomaly1 = null;

	private R4EUIAnomalyBasic fLinkedAnomaly2 = null;

	@SuppressWarnings("unused")
	private final R4EUIAnomalyBasic fLinkedAnomaly3 = null;

	private R4EUIAnomalyBasic fExternalAnomaly = null;

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
		suite.addTestSuite(DuplicateAnomalyTests.class);
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
		if (((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).isHideDeltasFilterSet()) {
			fProxy.getCommandProxy().toggleHideDeltasFilter();
		}
		createReviews();
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
	 * Method testDuplicationAnomalyInAReviews
	 * 
	 * @throws CoreException
	 */
	public void testDuplicationAnomalyInAReviews() throws CoreException {
		TestUtils.waitForJobs();
		createReviewItems();
		createLinkedAnomalies();
		createExternalAnomalies();
		verifyAnomaly();
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
	 * Method createReviews
	 */
	private void createReviews() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviews");

		r4eAssert.setTest("Open Review Group");
		if (!fGroup.isOpen()) {
			fProxy.getCommandProxy().openElement(fGroup);
		}
		r4eAssert.assertTrue(fGroup.isOpen());

		r4eAssert.setTest("Create Review");
		fReview = fProxy.getReviewProxy().createReview(fGroup, TestConstants.REVIEW_TEST_TYPE_INFORMAL,
				TestConstants.REVIEW_DUPLICATE_NAME_INF, TestConstants.REVIEW_TEST_DESCRIPTION,
				TestConstants.REVIEW_TEST_DUE_DATE, TestConstants.REVIEW_TEST_PROJECT,
				TestConstants.REVIEW_TEST_COMPONENTS, TestConstants.REVIEW_TEST_ENTRY_CRITERIA,
				TestConstants.REVIEW_TEST_OBJECTIVES, TestConstants.REVIEW_TEST_REFERENCE_MATERIALS);
		r4eAssert.assertNotNull(fReview);
		r4eAssert.assertNotNull(fReview.getParticipantContainer());
		r4eAssert.assertNotNull(fReview.getAnomalyContainer());
		r4eAssert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, fReview.getReview().getType());
		r4eAssert.assertEquals(TestConstants.REVIEW_DUPLICATE_NAME_INF, fReview.getReview().getName());
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

	// ------------------------------------------------------------------------
	// Create Review Items
	// ------------------------------------------------------------------------

	/**
	 * Adjust the modified file from the last commit
	 * 
	 * @throws CoreException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static void amendInitialCommit() throws CoreException, IOException, URISyntaxException {

		//Update files ( Modify File 3 since last commit)
		TestUtils.FJavaFile3 = TestUtils.changeContentOfFile(TestUtils.FJavaFile3, TestUtils.JAVA_FILE3_EXT_MOD_PATH);

		//Commit modifications
		Collection<String> commitFileList = new ArrayList<String>();
		commitFileList.add(TestUtils.FJavaFile3.getProjectRelativePath().toOSString());

		TestUtils.commitAmendFiles(TestUtils.FJavaIProject, TestUtils.FJavaRepository, FTHIRD_COMMIT, commitFileList);
	}

	/**
	 * Method createReviewItems
	 */
	private void createReviewItems() throws CoreException {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewItems");

		r4eAssert.setTest("Create Commit Item 1");
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

		// Now validate
		r4eAssert.assertNotNull(fItem);
		r4eAssert.assertTrue(fItem.isOpen());
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fItem.getItem().getAddedById());
		r4eAssert.assertEquals("The.committer@some.com", fItem.getItem().getAuthorRep());
		r4eAssert.assertEquals(FSECOND_COMMIT, fItem.getItem().getDescription());
		r4eAssert.assertEquals(4, fItem.getChildren().length);
		for (int i = 0; i < fItem.getChildren().length; i++) {
			if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
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

		r4eAssert.setTest("Create Manual Tree Item 1");
		fItem2 = fProxy.getItemProxy().createManualTreeItem(TestUtils.FJavaFile3);
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

		r4eAssert.setTest("Create Manual Text Item 1");
		fItem3 = fProxy.getItemProxy().createManualTextItem(TestUtils.FJavaFile4, 50, 20);
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

		try {
			amendInitialCommit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		r4eAssert.setTest("Create Commit Item 2");
		fItem4 = fProxy.getItemProxy().createCommitItem(TestUtils.FJavaIProject, 0);

		//Validate the last Commit
		/************************************************************** */
		/*                                                              */
		/* This commit is an amended from the other commit              */
		/*   JAVA_FILE1_PROJ_NAME = same file                           */
		/*   JAVA_FILE2_PROJ_NAME = same deleted file test base id      */
		/*   JAVA_FILE3_PROJ_NAME = different file                      */
		/*   JAVA_FILE4_PROJ_NAME = same file                           */
		/*                                                              */
		/************************************************************** */
		r4eAssert.assertNotNull(fItem4);
		r4eAssert.assertTrue(fItem4.isOpen());
		r4eAssert.assertEquals(R4EUIModelController.getReviewer(), fItem4.getItem().getAddedById());
		r4eAssert.assertEquals("The.committer@some.com", fItem4.getItem().getAuthorRep());
		r4eAssert.assertEquals(FTHIRD_COMMIT, fItem4.getItem().getDescription());
		r4eAssert.assertEquals(4, fItem4.getChildren().length);
		for (int i = 0; i < fItem4.getChildren().length; i++) {
			if (((R4EUIFileContext) fItem4.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				String testfileVersion = null;
				R4EFileVersion origFileVersion = null;
				for (int j = 0; j < fItem.getChildren().length; j++) {
					if (((R4EUIFileContext) fItem.getChildren()[j]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
						origFileVersion = fItem.getItem().getFileContextList().get(j).getTarget();
						if (origFileVersion != null) {
							testfileVersion = origFileVersion.getLocalVersionID();
							break;
						}
					}

				}
				R4EFileVersion item4FileVersion = fItem4.getItem().getFileContextList().get(i).getTarget();
				if (item4FileVersion != null) {
					r4eAssert.assertEquals(testfileVersion, item4FileVersion.getLocalVersionID());
				}

				r4eAssert.assertEquals(TestUtils.JAVA_FILE1_PROJ_NAME, fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertEquals(606, ((R4ETextPosition) fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(25, ((R4ETextPosition) fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(0)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(665, ((R4ETextPosition) fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(63, ((R4ETextPosition) fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(1)
						.getTarget()
						.getLocation()).getLength());
				r4eAssert.assertEquals(733, ((R4ETextPosition) fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getStartPosition());
				r4eAssert.assertEquals(61, ((R4ETextPosition) fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getDeltas()
						.get(2)
						.getTarget()
						.getLocation()).getLength());
			} else if (((R4EUIFileContext) fItem4.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE4_PROJ_NAME)) {
				r4eAssert.assertNull(fItem4.getItem().getFileContextList().get(i).getBase());
				String testfileVersion = null;
				R4EFileVersion origFileVersion = null;
				for (int j = 0; j < fItem.getChildren().length; j++) {
					if (((R4EUIFileContext) fItem.getChildren()[j]).getName().equals(TestUtils.JAVA_FILE4_PROJ_NAME)) {
						origFileVersion = fItem.getItem().getFileContextList().get(j).getTarget();
						if (origFileVersion != null) {
							testfileVersion = origFileVersion.getLocalVersionID();
							break;
						}
					}

				}
				R4EFileVersion item4FileVersion = fItem4.getItem().getFileContextList().get(i).getTarget();
				if (item4FileVersion != null) {
					r4eAssert.assertEquals(testfileVersion, item4FileVersion.getLocalVersionID());
				}

				r4eAssert.assertEquals(TestUtils.JAVA_FILE4_PROJ_NAME, fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fItem4.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE3_PROJ_NAME)) {

				String testfileVersion = null;
				R4EFileVersion origFileVersion = null;
				for (int j = 0; j < fItem.getChildren().length; j++) {
					if (((R4EUIFileContext) fItem.getChildren()[j]).getName().equals(TestUtils.JAVA_FILE3_PROJ_NAME)) {
						origFileVersion = fItem.getItem().getFileContextList().get(j).getTarget();
						if (origFileVersion != null) {
							testfileVersion = origFileVersion.getLocalVersionID();
							break;
						}
					}

				}
				R4EFileVersion item4FileVersion = fItem4.getItem().getFileContextList().get(i).getTarget();
				if (item4FileVersion != null) {
					r4eAssert.assertNotSame(testfileVersion, item4FileVersion.getLocalVersionID());
				}

				r4eAssert.assertNull(fItem4.getItem().getFileContextList().get(i).getBase());
				r4eAssert.assertEquals(TestUtils.JAVA_FILE3_PROJ_NAME, fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getTarget()
						.getName());
			} else if (((R4EUIFileContext) fItem4.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
				String testfileVersion = null;
				R4EFileVersion origFileVersion = null;
				//This JAVA_FILE2_PROJ_NAME is deleted, so no target, we just verify the base instead
				for (int j = 0; j < fItem.getChildren().length; j++) {
					if (((R4EUIFileContext) fItem.getChildren()[j]).getName().equals(TestUtils.JAVA_FILE2_PROJ_NAME)) {
						origFileVersion = fItem.getItem().getFileContextList().get(j).getBase();
						if (origFileVersion != null) {
							testfileVersion = origFileVersion.getLocalVersionID();
							break;
						}
					}

				}
				R4EFileVersion item4FileVersion = fItem4.getItem().getFileContextList().get(i).getBase();
				if (item4FileVersion != null) {
					r4eAssert.assertEquals(testfileVersion, item4FileVersion.getLocalVersionID());
				}

				r4eAssert.assertEquals(TestUtils.JAVA_FILE2_PROJ_NAME, fItem4.getItem()
						.getFileContextList()
						.get(i)
						.getBase()
						.getName());
				r4eAssert.assertNull(fItem4.getItem().getFileContextList().get(i).getTarget());
			}
		}
	}

	// ------------------------------------------------------------------------
	// Create Linked Anomalies
	// ------------------------------------------------------------------------

	/**
	 * Method createLinkedAnomalies
	 */
	private void createLinkedAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createLinkedAnomalies");

		r4eAssert.setTest("Create Linked Anomaly 1");
		int sizeItem = fItem.getChildren().length;
		R4EUIContent content = null;
		for (int i = 0; i < sizeItem; i++) {
			if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				//Create an anomaly for JAVA_FILE1_PROJ_NAME 
				//Same file, so the anomaly should only show in the last commit
				r4eAssert.setTest("Create Linked Anomaly 1");
				content = fItem.getFileContexts().get(i).getContentsContainerElement().getContentsList().get(0);
				fLinkedAnomaly1 = fProxy.getAnomalyProxy().createLinkedAnomaly(content,
						TestConstants.LINKED_ANOMALY_TEST_TITLE, TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION,
						TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, TestConstants.ANOMALY_TEST_RANK_MAJOR,
						TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
				r4eAssert.assertNotNull(fLinkedAnomaly1);
				r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_TITLE, fLinkedAnomaly1.getAnomaly().getTitle());
				r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION, fLinkedAnomaly1.getAnomaly()
						.getDescription());
				r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
						((R4ECommentType) fLinkedAnomaly1.getAnomaly().getType()).getType());
				r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fLinkedAnomaly1.getAnomaly().getRank());
				r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fLinkedAnomaly1.getAnomaly().getDueDate());
				r4eAssert.assertEquals(
						((R4EUITextPosition) content.getPosition()).getOffset(),
						((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly1.getAnomaly().getLocations().get(0)).getLocation()).getStartPosition());
				r4eAssert.assertEquals(
						((R4EUITextPosition) content.getPosition()).getLength(),
						((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly1.getAnomaly().getLocations().get(0)).getLocation()).getLength());
				r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fLinkedAnomaly1.getAnomaly()
						.getAssignedTo()
						.get(0));
				//Add a second anomaly
				r4eAssert.setTest("Create Linked Anomaly 2");
				if (fItem.getFileContexts().get(i).getContentsContainerElement().getContentsList().size() >= 3) {
					content = fItem.getFileContexts().get(i).getContentsContainerElement().getContentsList().get(2);
					fLinkedAnomaly2 = fProxy.getAnomalyProxy().createLinkedAnomaly(content,
							TestConstants.LINKED_ANOMALY_TEST_TITLE, TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION,
							TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, TestConstants.ANOMALY_TEST_RANK_MAJOR,
							TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
					r4eAssert.assertNotNull(fLinkedAnomaly2);
					r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_TITLE, fLinkedAnomaly2.getAnomaly()
							.getTitle());
					r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION, fLinkedAnomaly2.getAnomaly()
							.getDescription());
					r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
							((R4ECommentType) fLinkedAnomaly2.getAnomaly().getType()).getType());
					r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fLinkedAnomaly2.getAnomaly()
							.getRank());
					r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fLinkedAnomaly2.getAnomaly()
							.getDueDate());
					r4eAssert.assertEquals(
							((R4EUITextPosition) content.getPosition()).getOffset(),
							((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly2.getAnomaly().getLocations().get(0)).getLocation()).getStartPosition());
					r4eAssert.assertEquals(
							((R4EUITextPosition) content.getPosition()).getLength(),
							((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly2.getAnomaly().getLocations().get(0)).getLocation()).getLength());
					r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fLinkedAnomaly2.getAnomaly()
							.getAssignedTo()
							.get(0));

				}
			}

			//Add anomaly on the modified file now
			//Create an anomaly with the file in the first commit and at the end verify the count

//			if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE3_PROJ_NAME)) {
//				//Create an anomaly for JAVA_FILE3_PROJ_NAME 
//				//Different file, so the anomaly should only show in the initial commit
//	//Crash contenmt null 			content = fItem.getFileContexts().get(i).getContentsContainerElement().getContentsList().get(0);
//				fLinkedAnomaly3 = fProxy.getAnomalyProxy().createLinkedAnomaly(content,
//						TestConstants.LINKED_ANOMALY_TEST_TITLE, TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION,
//						TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, TestConstants.ANOMALY_TEST_RANK_MAJOR,
//						TestConstants.ANOMALY_TEST_DUE_DATE, TestConstants.PARTICIPANT_ASSIGN_TO, null);
//				r4eAssert.assertNotNull(fLinkedAnomaly3);
//				r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_TITLE, fLinkedAnomaly3.getAnomaly().getTitle());
//				r4eAssert.assertEquals(TestConstants.LINKED_ANOMALY_TEST_DESCRIPTION, fLinkedAnomaly3.getAnomaly()
//						.getDescription());
//				r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT,
//						((R4ECommentType) fLinkedAnomaly3.getAnomaly().getType()).getType());
//				r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, fLinkedAnomaly3.getAnomaly().getRank());
//				r4eAssert.assertEquals(TestConstants.ANOMALY_TEST_DUE_DATE, fLinkedAnomaly3.getAnomaly().getDueDate());
//				r4eAssert.assertEquals(
//						((R4EUITextPosition) content.getPosition()).getOffset(),
//						((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly3.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
//				r4eAssert.assertEquals(
//						((R4EUITextPosition) content.getPosition()).getLength(),
//						((R4ETextPosition) ((R4ETextContent) fLinkedAnomaly3.getAnomaly().getLocation().get(0)).getLocation()).getLength());
//				r4eAssert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO, fLinkedAnomaly3.getAnomaly()
//						.getAssignedTo()
//						.get(0));
//			}

		}
//		fProxy.getCommandProxy().closeElement(fReview);
//		fProxy.getCommandProxy().openElement(fReview);
//		int test = 0;

	}

	// ------------------------------------------------------------------------
	// Create External Anomalies
	// ------------------------------------------------------------------------

	/**
	 * Method createExternalAnomalies
	 */
	private void createExternalAnomalies() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createExternalAnomalies");

		// Create an external anomaly from the last commit
		r4eAssert.setTest("Create External Anomaly");
		fExternalAnomaly = fProxy.getAnomalyProxy().createExternalAnomaly(TestUtils.FJavaFile3,
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
		r4eAssert.assertEquals(0, ((R4ETextPosition) ((R4ETextContent) fExternalAnomaly.getAnomaly()
				.getLocations()
				.get(0)).getLocation()).getStartPosition());
		r4eAssert.assertEquals(764, ((R4ETextPosition) ((R4ETextContent) fExternalAnomaly.getAnomaly()
				.getLocations()
				.get(0)).getLocation()).getLength());

	}

	// ------------------------------------------------------------------------
	// Verify Anomalies
	// ------------------------------------------------------------------------

	/**
	 * Look where the anomaly are stored now after re-opening the review
	 */
	private void verifyAnomaly() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("verifyAnomaly");

		r4eAssert.setTest("Verify Anomaly");
		String comit1 = fItem.getName();
		String commit2 = fItem4.getName();
		fProxy.getCommandProxy().closeElement(fReview);
		fProxy.getCommandProxy().openElement(fReview);
		for (IR4EUIModelElement elem : fReview.getChildren()) {
			if (elem.getName().equals(comit1)) {
				fItem = (R4EUIReviewItem) elem;
			} else if (elem.getName().equals(commit2)) {
				fItem4 = (R4EUIReviewItem) elem;
			}

		}

		int sizeItem = fItem.getChildren().length;
		int anomalyCount1 = 0;
		for (int i = 0; i < sizeItem; i++) {

			if (((R4EUIFileContext) fItem.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				anomalyCount1 = fItem.getFileContexts().get(i).getNumAnomalies();
			}
		}

		int anomalyCount4 = 0;
		for (int i = 0; i < fItem4.getChildren().length; i++) {
			if (((R4EUIFileContext) fItem4.getChildren()[i]).getName().equals(TestUtils.JAVA_FILE1_PROJ_NAME)) {
				anomalyCount4 = fItem4.getFileContexts().get(i).getNumAnomalies();
			}
		}
		//Verify the anomaly count under the appropriate file
		r4eAssert.assertEquals(0, anomalyCount1);
		r4eAssert.assertEquals(2, anomalyCount4);

	}

}
