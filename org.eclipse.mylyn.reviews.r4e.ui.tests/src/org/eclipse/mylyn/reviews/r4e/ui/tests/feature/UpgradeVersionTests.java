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
 * This class implements JUnit Test Cases for version upgrades
 * 
 * Contributors:
 *   Sebastien Dubois - Initial Contribution for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.feature;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIDelta;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleViolation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EDummyCompatibleUpgrader;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings("restriction")
public class UpgradeVersionTests extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	public static final String ROOT_TEST_DIR = "java.io.tmpdir"; //$NON-NLS-1$

	public static final String UPGRADE_TEST_DIR = "r4eUpgradeTest"; //$NON-NLS-1$

	public static final String COMPATIBLE_UPGRADE_TEST_NAME = "compatibleUpgradeTest"; //$NON-NLS-1$

	public static final String UPGRADE_TEST_PARENT_DIR_STR = "testFiles"; //$NON-NLS-1$

	public static final String COMPATIBLE_UPGRADE_GROUP_TEST_FILE_STR = "compatibleUpgradeTest_group_root.xrer"; //$NON-NLS-1$

	public static final String COMPATIBLE_UPGRADE_RULESET_TEST_FILE_STR = "compatibleUpgradeTest_rule_set.xrer"; //$NON-NLS-1$

	public static final URI COMPATIBLE_UPGRADE_GROUP_TEST_FILE_URI = URI.createFileURI(UPGRADE_TEST_PARENT_DIR_STR
			+ File.separator + COMPATIBLE_UPGRADE_TEST_NAME + File.separator + COMPATIBLE_UPGRADE_GROUP_TEST_FILE_STR);

	public static final URI COMPATIBLE_UPGRADE_RULESET_TEST_FILE_URI = URI.createFileURI(UPGRADE_TEST_PARENT_DIR_STR
			+ File.separator + COMPATIBLE_UPGRADE_TEST_NAME + File.separator + COMPATIBLE_UPGRADE_RULESET_TEST_FILE_STR);

	private static int UPGRADE_BUTTON_INDEX = 0;

	private static int COMPATIBLE_NO_UPGRADE_BUTTON_INDEX = 1;

	private static int COMPATIBLE_CANCEL_BUTTON_INDEX = 2;

	private static Date UPGRADE_REVIEW_DUE_DATE = new GregorianCalendar(2012, 11, 31).getTime();

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

	private R4EUIReviewGroup fReviewGroup = null;

	private R4EUIRuleSet fRuleSet = null;

	private R4EUIReview fReview = null;

	protected static File fRootTestDir = null;

	private URI fNonCompatibleReviewGroupFileURI;

	private URI fCompatibleReviewGroupFileURI;

	private URI fCompatibleRuleSetFileURI;

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
		suite.addTestSuite(UpgradeVersionTests.class);
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

		//Copy own files to target test directory
		String baseTargetDir = System.getProperty(ROOT_TEST_DIR);
		if (!baseTargetDir.endsWith(File.separator)) {
			baseTargetDir += File.separator;
		}

		fRootTestDir = new File(baseTargetDir + UPGRADE_TEST_DIR + File.separator + System.currentTimeMillis());

		//*** Compatible upgrade tests ***

		// Take the directory name to be used as copy destination
		File compatibleSourceTestDir = new File(URI.decode(COMPATIBLE_UPGRADE_GROUP_TEST_FILE_URI.trimSegments(1)
				.devicePath()));
		String compatibleTargetTestDirStr = fRootTestDir.toString() + compatibleSourceTestDir.getName();
		File compatibleTargetTestDir = new File(compatibleTargetTestDirStr);

		// Determine the location of the group file in the destination folder
		URI compatibleTargetTestRootURI = URI.createFileURI(compatibleTargetTestDir.getAbsolutePath());
		String compatibleTargetTestGroupFile = COMPATIBLE_UPGRADE_GROUP_TEST_FILE_URI.lastSegment();
		fCompatibleReviewGroupFileURI = compatibleTargetTestRootURI.appendSegment(compatibleTargetTestGroupFile);
		String compatibleTargetTestRuleSetFile = COMPATIBLE_UPGRADE_RULESET_TEST_FILE_URI.lastSegment();
		fCompatibleRuleSetFileURI = compatibleTargetTestRootURI.appendSegment(compatibleTargetTestRuleSetFile);

		// Copy source dir to test dir
		FileUtils.copyDirectory(compatibleSourceTestDir, compatibleTargetTestDir);

		//Create dummy compatible upgrader
		new R4EDummyCompatibleUpgrader();
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
		// remove test directory
		if (fRootTestDir != null && fRootTestDir.exists()) {
			FileUtils.deleteDirectory(fRootTestDir);
		}
	}

	/**
	 * Method testUpgrades
	 */
	public void testUpgrades() {
		TestUtils.waitForJobs();
		verifyCompatibleUpgradeCancelled();
		verifyCompatibleNoUpgrade();
		verifyCompatibleUpgradeOK();
	}

	/**
	 * Method verifyCompatibleUpgradeCancelled
	 */
	private void verifyCompatibleUpgradeCancelled() {
		addReviewGroupToPreferences(COMPATIBLE_UPGRADE_TEST_NAME, fCompatibleReviewGroupFileURI, true);
		openReviewGroup(COMPATIBLE_UPGRADE_TEST_NAME, fNonCompatibleReviewGroupFileURI, COMPATIBLE_CANCEL_BUTTON_INDEX,
				false, false);
		addRuleSetToPreferences(COMPATIBLE_UPGRADE_TEST_NAME, fCompatibleRuleSetFileURI, true);
		openReviewGroup(COMPATIBLE_UPGRADE_TEST_NAME, fNonCompatibleReviewGroupFileURI, COMPATIBLE_CANCEL_BUTTON_INDEX,
				false, false);
	}

	/**
	 * Method verifyCompatibleNoUpgrade
	 */
	private void verifyCompatibleNoUpgrade() {
		openReviewGroup(COMPATIBLE_UPGRADE_TEST_NAME, fCompatibleReviewGroupFileURI,
				COMPATIBLE_NO_UPGRADE_BUTTON_INDEX, true, true);
		openRuleSet(COMPATIBLE_UPGRADE_TEST_NAME, fCompatibleRuleSetFileURI, COMPATIBLE_NO_UPGRADE_BUTTON_INDEX, true,
				true);
		fProxy.getCommandProxy().closeElement(fReviewGroup);
		fProxy.getCommandProxy().closeElement(fRuleSet);
	}

	/**
	 * Method verifyCompatibleUpgradeOK
	 */
	private void verifyCompatibleUpgradeOK() {
		openReviewGroup(COMPATIBLE_UPGRADE_TEST_NAME, fCompatibleReviewGroupFileURI, UPGRADE_BUTTON_INDEX, true, false);
		openRuleSet(COMPATIBLE_UPGRADE_TEST_NAME, fCompatibleRuleSetFileURI, UPGRADE_BUTTON_INDEX, true, false);
		openReview(COMPATIBLE_NO_UPGRADE_BUTTON_INDEX, true, true);
		fProxy.getCommandProxy().closeElement(fReview);
		openReview(UPGRADE_BUTTON_INDEX, false, true);
	}

	/**
	 * Method addReviewGroupToPreferences
	 * 
	 * @param aGroupName
	 *            - String
	 * @param aReviewGroupUri
	 *            - URI
	 * @param aUpgradeDialogIndexButton
	 *            int
	 */
	private void addReviewGroupToPreferences(String aGroupName, URI aReviewGroupUri, boolean aGroupResolved) {
		R4EUIReviewGroup reviewGroupFound = null;
		fProxy.getPreferencesProxy().addGroupToPreferences(aReviewGroupUri.toFileString());
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getName().equals(aGroupName)) {
				reviewGroupFound = group;
				break;
			}
		}
		Assert.assertNotNull(reviewGroupFound);
		if (aGroupResolved) {
			Assert.assertNotNull(reviewGroupFound.getReviewGroup());
		} else {
			Assert.assertNull(reviewGroupFound.getReviewGroup());
		}
	}

	/**
	 * Method openReviewGroup
	 * 
	 * @param aGroupName
	 *            - String
	 * @param aReviewGroupUri
	 *            - URI
	 * @param aUpgradeDialogIndexButton
	 *            int
	 */
	private void openReviewGroup(String aGroupName, URI aReviewGroupUri, int aUpgradeDialogIndexButton,
			boolean aResolved, boolean aAssertReadOnly) {
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (elem instanceof R4EUIReviewGroup && aGroupName.equals(elem.getName())) {
				fReviewGroup = (R4EUIReviewGroup) elem;
			}
		}
		fProxy.getCommandProxy().openElementWithUpdate(fReviewGroup, aUpgradeDialogIndexButton);
		Assert.assertNotNull(fReviewGroup);

		if (aResolved) {
			Assert.assertTrue(fReviewGroup.isOpen());
			Assert.assertEquals(aAssertReadOnly, fReviewGroup.isReadOnly());
			Assert.assertEquals(aGroupName, fReviewGroup.getReviewGroup().getName());
			Assert.assertEquals(aReviewGroupUri.trimSegments(1).devicePath(), fReviewGroup.getReviewGroup().getFolder());
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, fReviewGroup.getReviewGroup()
					.getDescription());
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, fReviewGroup.getReviewGroup()
					.getDefaultEntryCriteria());
			for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
				Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i],
						fReviewGroup.getReviewGroup().getAvailableProjects().get(i));
			}
			for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
				Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i],
						fReviewGroup.getReviewGroup().getAvailableComponents().get(i));
			}
		} else {
			Assert.assertFalse(fReviewGroup.isOpen());
		}
	}

	/**
	 * Method addRuleSetToPreferences
	 * 
	 * @param aRuleSetName
	 *            - String
	 * @param aRuleSetUri
	 *            - URI
	 * @param aUpgradeDialogIndexButton
	 *            int
	 */
	private void addRuleSetToPreferences(String aRuleSetName, URI aRuleSetUri, boolean aRuleSetResolved) {
		R4EUIRuleSet ruleSetFound = null;
		fProxy.getPreferencesProxy().addRuleSetToPreferences(aRuleSetUri.toFileString());
		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getName().equals(aRuleSetName)) {
				ruleSetFound = ruleSet;
				break;
			}
		}
		Assert.assertNotNull(ruleSetFound);
		if (aRuleSetResolved) {
			Assert.assertNotNull(ruleSetFound.getRuleSet());
		} else {
			Assert.assertNull(ruleSetFound.getRuleSet());
		}
	}

	/**
	 * Method openRuleSet
	 * 
	 * @param aRuleSetName
	 *            - String
	 * @param aRuleSetUri
	 *            - URI
	 * @param aUpgradeDialogIndexButton
	 *            int
	 */
	private void openRuleSet(String aRuleSetName, URI aRuleSetUri, int aUpgradeDialogIndexButton, boolean aResolved,
			boolean aAssertReadOnly) {
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (elem instanceof R4EUIRuleSet && aRuleSetName.equals(elem.getName())) {
				fRuleSet = (R4EUIRuleSet) elem;
			}
		}
		fProxy.getCommandProxy().openElementWithUpdate(fRuleSet, aUpgradeDialogIndexButton);
		Assert.assertNotNull(fRuleSet);

		if (aResolved) {
			Assert.assertTrue(fRuleSet.isOpen());
			Assert.assertEquals(aAssertReadOnly, fRuleSet.isReadOnly());
			Assert.assertEquals(TestConstants.RULE_SET_TEST_VERSION, fRuleSet.getRuleSet().getVersion());
			Assert.assertEquals(aRuleSetUri.trimSegments(1).devicePath(), fRuleSet.getRuleSet().getFolder());
			Assert.assertEquals(aRuleSetName, fRuleSet.getRuleSet().getName());

			R4EUIRuleArea ruleArea = (R4EUIRuleArea) fRuleSet.getChildren()[0];
			Assert.assertNotNull(ruleArea);
			Assert.assertEquals(TestConstants.RULE_AREA_TEST_NAME, ruleArea.getArea().getName());

			R4EUIRuleViolation ruleViolation = (R4EUIRuleViolation) ruleArea.getChildren()[0];
			Assert.assertNotNull(ruleViolation);
			Assert.assertEquals(TestConstants.RULE_VIOLATION_TEST_NAME, ruleViolation.getViolation().getName());

			R4EUIRule rule = (R4EUIRule) ruleViolation.getChildren()[0];
			Assert.assertNotNull(rule);
			Assert.assertEquals(TestConstants.RULE_TEST_ID, rule.getRule().getId());
			Assert.assertEquals(TestConstants.RULE_TEST_TITLE, rule.getRule().getTitle());
			Assert.assertEquals(TestConstants.RULE_TEST_DESCRIPTION, rule.getRule().getDescription());
			Assert.assertEquals(UIUtils.getClassFromString(TestConstants.RULE_TEST_CLASS), rule.getRule().getClass_());
			Assert.assertEquals(UIUtils.getRankFromString(TestConstants.RULE_TEST_RANK), rule.getRule().getRank());
		} else {
			Assert.assertFalse(fRuleSet.isOpen());
		}
	}

	/**
	 * Method openReview
	 * 
	 * @param aUpgradeDialogIndexButton
	 *            - int
	 * @param aAssertReadOnly
	 *            - boolean
	 * @param aReviewOpen
	 *            - boolean
	 */
	private void openReview(int aUpgradeDialogIndexButton, boolean aAssertReadOnly, boolean aReviewOpen) {
		fReview = (R4EUIReview) fReviewGroup.getChildren()[0];
		fProxy.getCommandProxy().openElementWithUpdate(fReview, aUpgradeDialogIndexButton);
		fReview = (R4EUIReview) fReviewGroup.getChildren()[0]; //Need to recheck here after update
		Assert.assertNotNull(fReview);

		//Verify Review
		if (aReviewOpen) {
			R4EUIReviewBasic review = (R4EUIReviewBasic) fReview;
			Assert.assertTrue(review.isOpen());
			Assert.assertEquals(aAssertReadOnly, review.isReadOnly());
			Assert.assertEquals(TestConstants.REVIEW_TEST_TYPE_INFORMAL, review.getReview().getType());
			Assert.assertEquals(TestConstants.REVIEW_TEST_NAME_INF, review.getReview().getName());
			Assert.assertEquals(TestConstants.REVIEW_TEST_DESCRIPTION, review.getReview().getExtraNotes());
			Assert.assertEquals(UPGRADE_REVIEW_DUE_DATE, review.getReview().getDueDate());
			Assert.assertEquals(TestConstants.REVIEW_TEST_PROJECT, review.getReview().getProject());
			for (int i = 0; i < TestConstants.REVIEW_TEST_COMPONENTS.length; i++) {
				Assert.assertEquals(TestConstants.REVIEW_TEST_COMPONENTS[i], review.getReview().getComponents().get(i));
			}
			Assert.assertEquals(TestConstants.REVIEW_TEST_ENTRY_CRITERIA, review.getReview().getEntryCriteria());
			Assert.assertEquals(TestConstants.REVIEW_TEST_OBJECTIVES, review.getReview().getObjectives());
			Assert.assertEquals(TestConstants.REVIEW_TEST_REFERENCE_MATERIALS, review.getReview()
					.getReferenceMaterial());

			//Verify Participant
			Assert.assertNotNull(review.getParticipantContainer());
			R4EUIParticipant participant = (R4EUIParticipant) review.getParticipantContainer().getChildren()[0];
			Assert.assertNotNull(participant);
			Assert.assertEquals(aAssertReadOnly, participant.isReadOnly());
			Assert.assertEquals(TestConstants.PARTICIPANT_TEST_ID, participant.getParticipant().getId());
			Assert.assertEquals(TestConstants.PARTICIPANT_TEST_EMAIL, participant.getParticipant().getEmail());
			Assert.assertEquals(R4EUserRole.R4E_ROLE_REVIEWER, participant.getParticipant().getRoles().get(0));

			//Verify Global Anomaly
			Assert.assertNotNull(review.getAnomalyContainer());
			R4EUIAnomalyExtended anomaly = (R4EUIAnomalyExtended) review.getAnomalyContainer().getChildren()[0];
			Assert.assertNotNull(anomaly);
			Assert.assertEquals(aAssertReadOnly, anomaly.isReadOnly());
			Assert.assertEquals(TestConstants.GLOBAL_ANOMALY_TEST_TITLE, anomaly.getAnomaly().getTitle());
			Assert.assertEquals(TestConstants.GLOBAL_ANOMALY_TEST_DESCRIPTION, anomaly.getAnomaly().getDescription());
			Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_ERRONEOUS, ((R4ECommentType) anomaly.getAnomaly()
					.getType()).getType());
			Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MAJOR, anomaly.getAnomaly().getRank());
			Assert.assertEquals(UPGRADE_REVIEW_DUE_DATE, anomaly.getAnomaly().getDueDate());
			Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO2, anomaly.getAnomaly().getAssignedTo().get(0));

			//Verify Global Anomaly Comment
			R4EUIComment comment = (R4EUIComment) anomaly.getChildren()[0];
			Assert.assertNotNull(comment);
			Assert.assertEquals(aAssertReadOnly, comment.isReadOnly());
			Assert.assertEquals(TestConstants.COMMENT_TEST, comment.getComment().getDescription());

			//Verify Review Item
			R4EUIReviewItem item = review.getItems().get(0);
			Assert.assertEquals(aAssertReadOnly, item.isReadOnly());
			Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO2, item.getItem().getAddedById());
			Assert.assertEquals("gerrit@eclipse.org", item.getItem().getAuthorRep()); //$NON-NLS-1$
			Assert.assertEquals("Merge \"389468: Fixes for Add Due Date to EMF R4EReview EClass\"", item.getItem() //$NON-NLS-1$
					.getDescription());
			Assert.assertEquals(2, item.getChildren().length);

			//Verify Files
			R4EUIFileContext fileContext = null;
			for (IR4EUIModelElement element : item.getChildren()) {
				if ("ReviewProperties.java".equals(((R4EUIFileContext) element).getName())) { //$NON-NLS-1$
					Assert.assertEquals("1d1e73c49eeeed16cc50253d639bc0c31c65a84b", //$NON-NLS-1$
							((R4EUIFileContext) element).getFileContext().getBase().getVersionID());
					Assert.assertEquals("2308c471414399200e6abcf5ece263b71f7fa728", //$NON-NLS-1$
							((R4EUIFileContext) element).getFileContext().getTarget().getVersionID());
				}
				if ("R4EUIConstants.java".equals(((R4EUIFileContext) element).getName())) { //$NON-NLS-1$
					Assert.assertEquals("d2ee3692729eb40a43288c1dd52c4b0247d1684c", //$NON-NLS-1$
							((R4EUIFileContext) element).getFileContext().getBase().getVersionID());
					Assert.assertEquals("32a5c6bea359694424bd492dcd48c86d1c6794da", //$NON-NLS-1$
							((R4EUIFileContext) element).getFileContext().getTarget().getVersionID());
					fileContext = (R4EUIFileContext) element;
				}
				Assert.assertEquals(aAssertReadOnly, element.isReadOnly());
			}
			Assert.assertNotNull(fileContext);

			//Verify some Deltas
			Assert.assertEquals(2, fileContext.getContentsContainerElement().getChildren().length);
			for (IR4EUIModelElement element : fileContext.getContentsContainerElement().getChildren()) {
				if (71381 == ((R4EUITextPosition) ((R4EUIDelta) element).getPosition()).getOffset()) {
					Assert.assertEquals(87, ((R4EUITextPosition) ((R4EUIDelta) element).getPosition()).getLength());
				}
				if (71205 == ((R4EUITextPosition) ((R4EUIDelta) element).getPosition()).getOffset()) {
					Assert.assertEquals(79, ((R4EUITextPosition) ((R4EUIDelta) element).getPosition()).getLength());
				}
				Assert.assertEquals(aAssertReadOnly, element.isReadOnly());
			}

			//Verify Compare Editor Anomaly
			anomaly = (R4EUIAnomalyExtended) fileContext.getAnomalyContainerElement().getChildren()[0];
			Assert.assertNotNull(anomaly);
			Assert.assertEquals(aAssertReadOnly, anomaly.isReadOnly());
			Assert.assertEquals(TestConstants.COMPARE_EDITOR_ANOMALY_TEST_TITLE, anomaly.getAnomaly().getTitle());
			Assert.assertEquals(TestConstants.COMPARE_EDITOR_ANOMALY_TEST_DESCRIPTION, anomaly.getAnomaly()
					.getDescription());
			Assert.assertEquals(TestConstants.ANOMALY_TEST_CLASS_IMPROVEMENT, ((R4ECommentType) anomaly.getAnomaly()
					.getType()).getType());
			Assert.assertEquals(TestConstants.ANOMALY_TEST_RANK_MINOR, anomaly.getAnomaly().getRank());
			Assert.assertEquals(UPGRADE_REVIEW_DUE_DATE, anomaly.getAnomaly().getDueDate());
			Assert.assertEquals(TestConstants.PARTICIPANT_ASSIGN_TO2, anomaly.getAnomaly().getAssignedTo().get(0));
			Assert.assertEquals(
					71381,
					((R4ETextPosition) ((R4ETextContent) anomaly.getAnomaly().getLocation().get(0)).getLocation()).getStartPosition());
			Assert.assertEquals(
					87,
					((R4ETextPosition) ((R4ETextContent) anomaly.getAnomaly().getLocation().get(0)).getLocation()).getLength());
			//Assert.assertTrue(fProxy.getCommandProxy().verifyAnnotation(anomaly, true,
			//		R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID));

			//Verify Compare Editor Comment
			comment = (R4EUIComment) anomaly.getChildren()[0];
			Assert.assertNotNull(comment);
			Assert.assertEquals(aAssertReadOnly, comment.isReadOnly());
			Assert.assertEquals(TestConstants.COMMENT_TEST, comment.getComment().getDescription());

		} else {
			Assert.assertFalse(fReview.isOpen());
		}
	}
}
