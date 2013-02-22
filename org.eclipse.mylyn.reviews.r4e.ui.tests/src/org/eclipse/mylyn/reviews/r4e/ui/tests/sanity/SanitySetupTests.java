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
 * This class implements a JUnit Test Case for the Sanity test setup
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Francois Chouinard - Add identifying message to each assert
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.sanity;

import java.io.File;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleViolation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.r4e.ui.tests.R4ETestSetup;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.R4EAssert;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings({ "restriction", "nls" })
public class SanitySetupTests extends TestCase {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

	// ------------------------------------------------------------------------
	// Housekeeping
	// ------------------------------------------------------------------------

	/**
	 * Sets up the global test environment, if not already done at the suite level.
	 * 
	 * @return Test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(SanitySetupTests.class);
		return new R4ETestSetup(suite);
	}

	/**
	 * Sets up the fixture, for example, open a network connection. This method is called before a test is executed.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		fProxy = R4EUITestMain.getInstance();
	}

	/**
	 * Cleanup after the test has completed
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
	 * The main test case. It invokes the sub-tests sequentially thus avoiding synchronization issues in the shared data
	 * structures.
	 * 
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public void testSetup() {
		TestUtils.waitForJobs();
		createRuleSetSetup();
		createReviewGroupSetup();
		verifyHelp();
	}

	// ------------------------------------------------------------------------
	// Rule Sets Test
	// ------------------------------------------------------------------------

	/**
	 * Rule Sets management
	 * 
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void createRuleSetSetup() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createRuleSetSetup");

		// Create a Rule Set
		r4eAssert.setTest("Create RuleSet");
		R4EUIRuleSet newRuleSet = fProxy.getRuleSetProxy().createRuleSet(TestUtils.FSharedFolder,
				TestConstants.RULE_SET_TEST_NAME, TestConstants.RULE_SET_TEST_VERSION);
		r4eAssert.assertNotNull(newRuleSet);
		r4eAssert.assertEquals(TestConstants.RULE_SET_TEST_VERSION, newRuleSet.getRuleSet().getVersion());
		r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString(), newRuleSet.getRuleSet()
				.getFolder());
		r4eAssert.assertEquals(TestConstants.RULE_SET_TEST_NAME, newRuleSet.getRuleSet().getName());

		// Create a Second Rule Set
		r4eAssert.setTest("Create 2nd RuleSet");
		R4EUIRuleSet newRuleSet2 = fProxy.getRuleSetProxy().createRuleSet(TestUtils.FSharedFolder,
				TestConstants.RULE_SET_TEST_NAME2, TestConstants.RULE_SET_TEST_VERSION);
		String newRuleSet2Name = newRuleSet2.getName();
		r4eAssert.assertNotNull(newRuleSet2);
		r4eAssert.assertEquals(TestConstants.RULE_SET_TEST_VERSION, newRuleSet2.getRuleSet().getVersion());
		r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString(), newRuleSet2.getRuleSet()
				.getFolder());
		r4eAssert.assertEquals(TestConstants.RULE_SET_TEST_NAME2, newRuleSet2.getRuleSet().getName());

		// Create Rule Area
		r4eAssert.setTest("Create Rule Area");
		R4EUIRuleArea newRuleArea = fProxy.getRuleAreaProxy().createRuleArea(newRuleSet,
				TestConstants.RULE_AREA_TEST_NAME);
		r4eAssert.assertNotNull(newRuleArea);
		r4eAssert.assertEquals(TestConstants.RULE_AREA_TEST_NAME, newRuleArea.getArea().getName());

		// Create Rule Violation
		r4eAssert.setTest("Create Rule Violation");
		R4EUIRuleViolation newRuleViolation = fProxy.getRuleViolationProxy().createRuleViolation(newRuleArea,
				TestConstants.RULE_VIOLATION_TEST_NAME);
		r4eAssert.assertNotNull(newRuleViolation);
		r4eAssert.assertEquals(TestConstants.RULE_VIOLATION_TEST_NAME, newRuleViolation.getViolation().getName());

		// Create Rule
		r4eAssert.setTest("Create Rule");
		R4EUIRule newRule = fProxy.getRuleProxy().createRule(newRuleViolation, TestConstants.RULE_TEST_ID,
				TestConstants.RULE_TEST_TITLE, TestConstants.RULE_TEST_DESCRIPTION,
				UIUtils.getClassFromString(TestConstants.RULE_TEST_CLASS),
				UIUtils.getRankFromString(TestConstants.RULE_TEST_RANK));
		r4eAssert.assertNotNull(newRule);
		r4eAssert.assertEquals(TestConstants.RULE_TEST_ID, newRule.getRule().getId());
		r4eAssert.assertEquals(TestConstants.RULE_TEST_TITLE, newRule.getRule().getTitle());
		r4eAssert.assertEquals(TestConstants.RULE_TEST_DESCRIPTION, newRule.getRule().getDescription());
		r4eAssert.assertEquals(UIUtils.getClassFromString(TestConstants.RULE_TEST_CLASS), newRule.getRule().getClass_());
		r4eAssert.assertEquals(UIUtils.getRankFromString(TestConstants.RULE_TEST_RANK), newRule.getRule().getRank());

		// Close a Rule Set
		r4eAssert.setTest("Close RuleSet");
		fProxy.getCommandProxy().closeElement(newRuleSet);
		r4eAssert.assertFalse(newRuleSet.isOpen());

		//Open the Closed Rule Set
		r4eAssert.setTest("Re-Open RuleSet");
		fProxy.getCommandProxy().openElement(newRuleSet);
		r4eAssert.assertTrue(newRuleSet.isOpen());
		r4eAssert.assertEquals(TestConstants.RULE_TEST_ID,
				((R4EUIRule) newRuleSet.getChildren()[0].getChildren()[0].getChildren()[0]).getRule().getId());

		// Remove Rule Set from preferences
		r4eAssert.setTest("Remove RuleSet");
		String prefsRuleSet = newRuleSet2.getRuleSet().eResource().getURI().toFileString();
		fProxy.getPreferencesProxy().removeRuleSetFromPreferences(prefsRuleSet);
		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getRuleSet().getName().equals(newRuleSet2.getRuleSet().getName())) {
				fail("RuleSet " + prefsRuleSet + " should not be present since it was removed from preferences");
			}
		}

		// Add back Rule Set to preferences
		r4eAssert.setTest("Put Back RuleSet");
		boolean ruleSetFound = false;
		fProxy.getPreferencesProxy().addRuleSetToPreferences(prefsRuleSet);
		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getRuleSet().getName().equals(newRuleSet2.getRuleSet().getName())) {
				ruleSetFound = true;
				break;
			}
		}
		r4eAssert.assertTrue(ruleSetFound);

		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (newRuleSet2Name.equals(elem.getName())) {
				newRuleSet2 = (R4EUIRuleSet) elem;
			}
		}
		fProxy.getCommandProxy().openElement(newRuleSet2);
		r4eAssert.assertTrue(newRuleSet2.isOpen());
	}

	// ------------------------------------------------------------------------
	// Review Groups Test
	// ------------------------------------------------------------------------

	/**
	 * Method createReviewGroupSetup
	 * 
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void createReviewGroupSetup() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("createReviewGroupSetup");

		// Create Review Group
		r4eAssert.setTest("Create Review Group");
		R4EUIReviewGroup newGroup = fProxy.getReviewGroupProxy().createReviewGroup(
				TestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME,
				TestConstants.REVIEW_GROUP_TEST_NAME, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
				TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
				TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
		r4eAssert.assertNotNull(newGroup);
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME, newGroup.getReviewGroup().getName());
		r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
				+ TestConstants.REVIEW_GROUP_TEST_NAME, newGroup.getReviewGroup().getFolder());
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, newGroup.getReviewGroup().getDescription());
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, newGroup.getReviewGroup()
				.getDefaultEntryCriteria());
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i], newGroup.getReviewGroup()
					.getAvailableProjects()
					.get(i));
		}
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i], newGroup.getReviewGroup()
					.getAvailableComponents()
					.get(i));
		}
		String newGroupName = newGroup.getName();

		// Create a second Review Group
		r4eAssert.setTest("Create 2nd Review Group");
		R4EUIReviewGroup newGroup2 = fProxy.getReviewGroupProxy().createReviewGroup(
				TestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME2,
				TestConstants.REVIEW_GROUP_TEST_NAME2, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
				TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
				TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
		r4eAssert.assertNotNull(newGroup2);
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME2, newGroup2.getReviewGroup().getName());
		r4eAssert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
				+ TestConstants.REVIEW_GROUP_TEST_NAME2, newGroup2.getReviewGroup().getFolder());
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, newGroup2.getReviewGroup().getDescription());
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, newGroup2.getReviewGroup()
				.getDefaultEntryCriteria());
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i], newGroup2.getReviewGroup()
					.getAvailableProjects()
					.get(i));
		}
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i], newGroup2.getReviewGroup()
					.getAvailableComponents()
					.get(i));
		}

		// Close a Review Group
		r4eAssert.setTest("Close Review Group");
		fProxy.getCommandProxy().closeElement(newGroup);
		r4eAssert.assertFalse(newGroup.isOpen());

		// Open the closed Review Group
		r4eAssert.setTest("Re-Open Review Group");
		fProxy.getCommandProxy().openElement(newGroup);
		r4eAssert.assertTrue(newGroup.isOpen());

		// Remove Review Group from preferences
		r4eAssert.setTest("Remove Review Group");
		String prefsGroup = newGroup2.getReviewGroup().eResource().getURI().toFileString();
		fProxy.getPreferencesProxy().removeGroupFromPreferences(prefsGroup);
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(newGroup2.getReviewGroup().getName())) {
				fail("Group " + prefsGroup + " should not be present since it was removed from preferences");
			}
		}

		// Add back Review Group to preferences
		r4eAssert.setTest("Add Removed Review Group");
		boolean groupFound = false;
		fProxy.getPreferencesProxy().addGroupToPreferences(prefsGroup);
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(newGroup2.getReviewGroup().getName())) {
				groupFound = true;
				break;
			}
		}
		r4eAssert.assertTrue(groupFound);

		// Get back handle to Review Group since view is refreshed
		r4eAssert.setTest("Check Selected Element");
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (newGroupName.equals(elem.getName())) {
				newGroup = (R4EUIReviewGroup) elem;
			}
		}
		fProxy.getCommandProxy().openElement(newGroup);
		r4eAssert.assertTrue(newGroup.isOpen());

		// Update Review Group properties
		r4eAssert.setTest("Update Properties");
		fProxy.getReviewGroupProxy().changeReviewGroupDescription(newGroup,
				TestConstants.REVIEW_GROUP_TEST_DESCRIPTION2);
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION2, newGroup.getReviewGroup().getDescription());
		fProxy.getReviewGroupProxy().changeReviewGroupDefaultEntryCriteria(newGroup,
				TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA2);
		r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA2, newGroup.getReviewGroup()
				.getDefaultEntryCriteria());
		fProxy.getReviewGroupProxy().removeReviewGroupAvailableProject(newGroup,
				TestConstants.REVIEW_GROUP_TEST_REM_AVAILABLE_PROJECT);
		fProxy.getReviewGroupProxy().addReviewGroupAvailableProject(newGroup,
				TestConstants.REVIEW_GROUP_TEST_ADD_AVAILABLE_PROJECT);
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS2.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS2[i], newGroup.getReviewGroup()
					.getAvailableProjects()
					.get(i));
		}
		fProxy.getReviewGroupProxy().removeReviewGroupAvailableComponent(newGroup,
				TestConstants.REVIEW_GROUP_TEST_REM_AVAILABLE_COMPONENT);
		fProxy.getReviewGroupProxy().addReviewGroupAvailableComponent(newGroup,
				TestConstants.REVIEW_GROUP_TEST_ADD_AVAILABLE_COMPONENT);
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS2.length; i++) {
			r4eAssert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS2[i], newGroup.getReviewGroup()
					.getAvailableComponents()
					.get(i));
		}

		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getName().equals(TestConstants.RULE_SET_TEST_NAME2)) {
				fProxy.getReviewGroupProxy().addReviewGroupRuleSet(newGroup, ruleSet.getRuleSet().getName());
				r4eAssert.assertEquals(ruleSet.getRuleSet().getName(), newGroup.getReviewGroup()
						.getDesignRuleLocations()
						.get(0));
				break;
			}
		}

		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getName().equals(TestConstants.RULE_SET_TEST_NAME)) {
				fProxy.getReviewGroupProxy().addReviewGroupRuleSet(newGroup, ruleSet.getRuleSet().getName());
			} else if (ruleSet.getName().equals(TestConstants.RULE_SET_TEST_NAME2)) {
				fProxy.getReviewGroupProxy().removeReviewGroupRuleSet(newGroup, ruleSet.getRuleSet().getName());
			}
		}

		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getName().equals(TestConstants.RULE_SET_TEST_NAME)) {
				r4eAssert.assertEquals(ruleSet.getRuleSet().getName(), newGroup.getReviewGroup()
						.getDesignRuleLocations()
						.get(0));
				break;
			}
		}
	}

	// ------------------------------------------------------------------------
	// Help Test
	// ------------------------------------------------------------------------

	/**
	 * Method verifyHelp
	 */
	private void verifyHelp() {

		// Assert object
		R4EAssert r4eAssert = new R4EAssert("verifyHelp");

		// Verify R4E help is present
		r4eAssert.setTest("Help Presence");
		URL openUrl = fProxy.getHelp("/org.eclipse.mylyn.reviews.r4e.help/help/Reviews/R4E/User_Guide/User-Guide.html");
		r4eAssert.assertNotNull(openUrl);

		URL ericssonUrl = fProxy.getHelp("/com.ericsson.reviews.r4e.help/doc/r4eEricsson.html");
		r4eAssert.assertNotNull(ericssonUrl);
	}

}
