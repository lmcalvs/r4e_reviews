/**
 * Copyright (c) 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.ui.tests;

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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.tests.proxy.R4EUITestMain;
import org.eclipse.mylyn.reviews.r4e.ui.tests.sanity.SanitySetupTests;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.junit.After;
import org.junit.Before;

/**
 * @author Sebastien Dubois
 */
@SuppressWarnings("restriction")
public class RuleSetTests extends TestCase {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String RULE_SET_TEST_VERSION = "0.1";

	private static final String RULE_SET_TEST_NAME = "testRuleSet";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private R4EUIRuleSet fRuleSet;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Sets up the global test environment, if not already done at the suite level.
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(SanitySetupTests.class);
		return new R4ETestSetup(suite);
	}

	/**
	 * Sets up the fixture, for example, open a network connection. This method is called before a test is executed.
	 */
	@Override
	@Before
	public void setUp() throws Exception {
		TestUtils.startNavigatorView();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Override
	@After
	public void tearDown() throws Exception {
		fRuleSet = null;
		TestUtils.stopNavigatorView();
	}

	/**
	 * Test creation of a new Rule Set
	 * 
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void testNewRuleSet() throws ResourceHandlingException, OutOfSyncException, ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException {

		//Create a Rule Set
		R4EUITestMain proxy = R4EUITestMain.getInstance();

		fRuleSet = proxy.getRuleSetProxy().createRuleSet(TestUtils.FSharedFolder, RULE_SET_TEST_NAME,
				RULE_SET_TEST_VERSION);

		Assert.assertNotNull(fRuleSet);
		Assert.assertEquals(RULE_SET_TEST_VERSION, fRuleSet.getRuleSet().getVersion());
		Assert.assertEquals(new Path(TestUtils.FSharedFolder).toPortableString(), fRuleSet.getRuleSet().getFolder());
		Assert.assertEquals(RULE_SET_TEST_NAME, fRuleSet.getRuleSet().getName());
	}
	/**
	 * Open Rule Set
	 * 
	 * @throws ResourceHandlingException
	 */
	/*
	@Test
	public void testOpenRuleSet() throws ResourceHandlingException {
	fRuleSet.open();
	Assert.assertNotNull(fRuleSet.getRuleSet());
	Assert.assertEquals(true, fRuleSet.isOpen());
	}
	
	*//**
	 * Close Rule Set
	 * 
	 * @throws ResourceHandlingException
	 */
	/*
	@Test
	public void testCloseRuleSet() throws ResourceHandlingException {
	fRuleSet.close();
	Assert.assertEquals(false, fRuleSet.isOpen());
	}
	
	*//**
	 * Remove (Disable) Rule Set
	 * 
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	/*
	@Test
	public void testRemoveRuleSet() throws ResourceHandlingException, OutOfSyncException {
	TestUtils.getRootElement().removeChildren(fRuleSet, false);
	Assert.assertEquals(false, fRuleSet.getRuleSet().isEnabled());
	}
	
	*//**
	 * Restore Rule Set
	 * 
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	/*
	@Test
	public void testRestoreRuleSet() throws ResourceHandlingException, OutOfSyncException {
	fRuleSet.setEnabled(true);
	fRuleSet.open();
	Assert.assertEquals(true, fRuleSet.getRuleSet().isEnabled());
	}
	
	*//**
	 * Create a Rule Set element object
	 * 
	 * @return the new Rule Set
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	/*
	public static R4EUIRuleSet newRuleSet(String aVersion, String aFolder, String aName) throws ResourceHandlingException, OutOfSyncException {
	//Simulate getting data from user and set it in model data
	R4EDesignRuleCollection tempRuleSet = DRModelFactory.eINSTANCE.createR4EDesignRuleCollection();
	tempRuleSet.setVersion(aVersion);
	tempRuleSet.setFolder(aFolder);
	tempRuleSet.setName(aName);
	return (R4EUIRuleSet) TestUtils.getRootElement().createChildren(tempRuleSet);
	}*/
}
