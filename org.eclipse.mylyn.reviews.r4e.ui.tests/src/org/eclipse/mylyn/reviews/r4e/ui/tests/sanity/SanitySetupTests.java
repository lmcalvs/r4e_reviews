/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
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
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.sanity;

import java.io.File;
import java.net.URL;

import junit.framework.Assert;
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
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

@SuppressWarnings("restriction")
public class SanitySetupTests extends TestCase {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUITestMain fProxy = null;

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
		suite.addTestSuite(SanitySetupTests.class);
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
	 * Method testSetup
	 * 
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public void testSetup() {
		createRuleSetSetup();
		createReviewGroupSetup();
		verifyHelp();
	}

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
	public void createReviewGroupSetup() {

		//Create Review Group
		R4EUIReviewGroup newGroup = fProxy.getReviewGroupProxy().createReviewGroup(
				TestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME,
				TestConstants.REVIEW_GROUP_TEST_NAME, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
				TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
				TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
		Assert.assertNotNull(newGroup);
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME, newGroup.getReviewGroup().getName());
		Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
				+ TestConstants.REVIEW_GROUP_TEST_NAME, newGroup.getReviewGroup().getFolder());
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, newGroup.getReviewGroup().getDescription());
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, newGroup.getReviewGroup()
				.getDefaultEntryCriteria());
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i], newGroup.getReviewGroup()
					.getAvailableProjects()
					.get(i));
		}
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i], newGroup.getReviewGroup()
					.getAvailableComponents()
					.get(i));
		}
		String newGroupName = newGroup.getName();

		//Create a second Review Group
		R4EUIReviewGroup newGroup2 = fProxy.getReviewGroupProxy().createReviewGroup(
				TestUtils.FSharedFolder + File.separator + TestConstants.REVIEW_GROUP_TEST_NAME2,
				TestConstants.REVIEW_GROUP_TEST_NAME2, TestConstants.REVIEW_GROUP_TEST_DESCRIPTION,
				TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS,
				TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS, new String[0]);
		Assert.assertNotNull(newGroup2);
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_NAME2, newGroup2.getReviewGroup().getName());
		Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString() + "/"
				+ TestConstants.REVIEW_GROUP_TEST_NAME2, newGroup2.getReviewGroup().getFolder());
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION, newGroup2.getReviewGroup().getDescription());
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA, newGroup2.getReviewGroup()
				.getDefaultEntryCriteria());
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS[i], newGroup2.getReviewGroup()
					.getAvailableProjects()
					.get(i));
		}
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS[i], newGroup2.getReviewGroup()
					.getAvailableComponents()
					.get(i));
		}

		//Close a Review Group
		fProxy.getCommandProxy().closeElement(newGroup);
		Assert.assertFalse(newGroup.isOpen());

		//Open the closed Review Group
		fProxy.getCommandProxy().openElement(newGroup);
		Assert.assertTrue(newGroup.isOpen());

		//Remove Review Group from preferences
		String prefsGroup = newGroup2.getReviewGroup().eResource().getURI().toFileString();
		fProxy.getPreferencesProxy().removeGroupFromPreferences(prefsGroup);
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(newGroup2.getReviewGroup().getName())) {
				fail("Group " + prefsGroup + " should not be present since it was removed from preferences");
			}
		}

		//Add back Review Group to preferences
		boolean groupFound = false;
		fProxy.getPreferencesProxy().addGroupToPreferences(prefsGroup);
		for (R4EUIReviewGroup group : R4EUIModelController.getRootElement().getGroups()) {
			if (group.getReviewGroup().getName().equals(newGroup2.getReviewGroup().getName())) {
				groupFound = true;
				break;
			}
		}
		Assert.assertTrue(groupFound);

		//Get back handle to Review Group since view is refreshed
		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (newGroupName.equals(elem.getName())) {
				newGroup = (R4EUIReviewGroup) elem;
			}
		}
		fProxy.getCommandProxy().openElement(newGroup);
		Assert.assertTrue(newGroup.isOpen());

		//Update Review Group properties
		fProxy.getReviewGroupProxy().changeReviewGroupDescription(newGroup,
				TestConstants.REVIEW_GROUP_TEST_DESCRIPTION2);
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_DESCRIPTION2, newGroup.getReviewGroup().getDescription());
		fProxy.getReviewGroupProxy().changeReviewGroupDefaultEntryCriteria(newGroup,
				TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA2);
		Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_ENTRY_CRITERIA2, newGroup.getReviewGroup()
				.getDefaultEntryCriteria());
		fProxy.getReviewGroupProxy().removeReviewGroupAvailableProject(newGroup,
				TestConstants.REVIEW_GROUP_TEST_REM_AVAILABLE_PROJECT);
		fProxy.getReviewGroupProxy().addReviewGroupAvailableProject(newGroup,
				TestConstants.REVIEW_GROUP_TEST_ADD_AVAILABLE_PROJECT);
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS2.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_PROJECTS2[i], newGroup.getReviewGroup()
					.getAvailableProjects()
					.get(i));
		}
		fProxy.getReviewGroupProxy().removeReviewGroupAvailableComponent(newGroup,
				TestConstants.REVIEW_GROUP_TEST_REM_AVAILABLE_COMPONENT);
		fProxy.getReviewGroupProxy().addReviewGroupAvailableComponent(newGroup,
				TestConstants.REVIEW_GROUP_TEST_ADD_AVAILABLE_COMPONENT);
		for (int i = 0; i < TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS2.length; i++) {
			Assert.assertEquals(TestConstants.REVIEW_GROUP_TEST_AVAILABLE_COMPONENTS2[i], newGroup.getReviewGroup()
					.getAvailableComponents()
					.get(i));
		}

		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getName().equals(TestConstants.RULE_SET_TEST_NAME2)) {
				fProxy.getReviewGroupProxy().addReviewGroupRuleSet(newGroup, ruleSet.getRuleSet().getName());
				Assert.assertEquals(ruleSet.getRuleSet().getName(), newGroup.getReviewGroup()
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
				Assert.assertEquals(ruleSet.getRuleSet().getName(), newGroup.getReviewGroup()
						.getDesignRuleLocations()
						.get(0));
				break;
			}
		}
	}

	/**
	 * Method createRuleSetSetup
	 * 
	 * @throws ExecutionException
	 * @throws NotDefinedException
	 * @throws NotEnabledException
	 * @throws NotHandledException
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public void createRuleSetSetup() {

		//Create a Rule Set
		R4EUIRuleSet newRuleSet = fProxy.getRuleSetProxy().createRuleSet(TestUtils.FSharedFolder,
				TestConstants.RULE_SET_TEST_NAME, TestConstants.RULE_SET_TEST_VERSION);
		Assert.assertNotNull(newRuleSet);
		Assert.assertEquals(TestConstants.RULE_SET_TEST_VERSION, newRuleSet.getRuleSet().getVersion());
		Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString(), newRuleSet.getRuleSet().getFolder());
		Assert.assertEquals(TestConstants.RULE_SET_TEST_NAME, newRuleSet.getRuleSet().getName());

		//Create a second Rule Set
		R4EUIRuleSet newRuleSet2 = fProxy.getRuleSetProxy().createRuleSet(TestUtils.FSharedFolder,
				TestConstants.RULE_SET_TEST_NAME2, TestConstants.RULE_SET_TEST_VERSION);
		String newRuleSet2Name = newRuleSet2.getName();
		Assert.assertNotNull(newRuleSet2);
		Assert.assertEquals(TestConstants.RULE_SET_TEST_VERSION, newRuleSet2.getRuleSet().getVersion());
		Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString(), newRuleSet2.getRuleSet().getFolder());
		Assert.assertEquals(TestConstants.RULE_SET_TEST_NAME2, newRuleSet2.getRuleSet().getName());

		//Create Rule Area
		R4EUIRuleArea newRuleArea = fProxy.getRuleAreaProxy().createRuleArea(newRuleSet,
				TestConstants.RULE_AREA_TEST_NAME);
		Assert.assertNotNull(newRuleArea);
		Assert.assertEquals(TestConstants.RULE_AREA_TEST_NAME, newRuleArea.getArea().getName());

		//Create Rule Violation
		R4EUIRuleViolation newRuleViolation = fProxy.getRuleViolationProxy().createRuleViolation(newRuleArea,
				TestConstants.RULE_VIOLATION_TEST_NAME);
		Assert.assertNotNull(newRuleViolation);
		Assert.assertEquals(TestConstants.RULE_VIOLATION_TEST_NAME, newRuleViolation.getViolation().getName());

		//Create Rule
		R4EUIRule newRule = fProxy.getRuleProxy().createRule(newRuleViolation, TestConstants.RULE_TEST_ID,
				TestConstants.RULE_TEST_TITLE, TestConstants.RULE_TEST_DESCRIPTION,
				UIUtils.getClassFromString(TestConstants.RULE_TEST_CLASS),
				UIUtils.getRankFromString(TestConstants.RULE_TEST_RANK));
		Assert.assertNotNull(newRule);
		Assert.assertEquals(TestConstants.RULE_TEST_ID, newRule.getRule().getId());
		Assert.assertEquals(TestConstants.RULE_TEST_TITLE, newRule.getRule().getTitle());
		Assert.assertEquals(TestConstants.RULE_TEST_DESCRIPTION, newRule.getRule().getDescription());
		Assert.assertEquals(UIUtils.getClassFromString(TestConstants.RULE_TEST_CLASS), newRule.getRule().getClass_());
		Assert.assertEquals(UIUtils.getRankFromString(TestConstants.RULE_TEST_RANK), newRule.getRule().getRank());

		//Close a Rule Set
		fProxy.getCommandProxy().closeElement(newRuleSet);
		Assert.assertFalse(newRuleSet.isOpen());

		//Open the closed Rule Set
		fProxy.getCommandProxy().openElement(newRuleSet);
		Assert.assertTrue(newRuleSet.isOpen());
		Assert.assertEquals(TestConstants.RULE_TEST_ID,
				((R4EUIRule) newRuleSet.getChildren()[0].getChildren()[0].getChildren()[0]).getRule().getId());

		//Remove Rule Set from preferences
		String prefsRuleSet = newRuleSet2.getRuleSet().eResource().getURI().toFileString();
		fProxy.getPreferencesProxy().removeRuleSetFromPreferences(prefsRuleSet);
		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getRuleSet().getName().equals(newRuleSet2.getRuleSet().getName())) {
				fail("RuleSet " + prefsRuleSet + " should not be present since it was removed from preferences");
			}
		}

		//Add back Rule Set to preferences
		boolean ruleSetFound = false;
		fProxy.getPreferencesProxy().addRuleSetToPreferences(prefsRuleSet);
		for (R4EUIRuleSet ruleSet : R4EUIModelController.getRootElement().getRuleSets()) {
			if (ruleSet.getRuleSet().getName().equals(newRuleSet2.getRuleSet().getName())) {
				ruleSetFound = true;
				break;
			}
		}
		Assert.assertTrue(ruleSetFound);

		for (IR4EUIModelElement elem : R4EUIModelController.getRootElement().getChildren()) {
			if (newRuleSet2Name.equals(elem.getName())) {
				newRuleSet2 = (R4EUIRuleSet) elem;
			}
		}
		fProxy.getCommandProxy().openElement(newRuleSet2);
		Assert.assertTrue(newRuleSet2.isOpen());
	}

	/**
	 * Method verifyHelp
	 */
	public void verifyHelp() {

		//Verify R4E help is present
		URL openUrl = fProxy.getHelp("/org.eclipse.mylyn.reviews.r4e.help/help/Reviews/R4E/User_Guide/User-Guide.html");
		Assert.assertNotNull(openUrl);

		URL ericssonUrl = fProxy.getHelp("/com.ericsson.reviews.r4e.help/doc/r4eEricsson.html");
		Assert.assertNotNull(ericssonUrl);
	}
}
