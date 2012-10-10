/**
/**
 * Copyright (c) 2010, 2012 Ericsson
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * Alvaro Sanchez-Leon  - Initial implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc --> A test suite for the '<em><b>model</b></em>' package. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModelTests extends TestSuite {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ModelTests("model Tests");
		// suite.addTestSuite(R4EReviewTest.class);
		// suite.addTestSuite(R4EAnomalyTest.class);
		// suite.addTestSuite(R4EFormalReviewTest.class);
		// suite.addTestSuite(R4EUserTest.class);
		// suite.addTestSuite(R4EParticipantTest.class);
		// suite.addTestSuite(R4EitemTest.class);
		// suite.addTestSuite(R4ETaskTest.class);
		// suite.addTestSuite(R4ECommentTest.class);
		// suite.addTestSuite(R4EComponentTest.class);
		// suite.addTestSuite(R4EFileContextTest.class);
		// suite.addTestSuite(R4EDeltaTest.class);
		// suite.addTestSuite(R4EFileVersionTest.class);
		suite.addTestSuite(R4EReviewGroupTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModelTests(String name) {
		super(name);
	}

} //ModelTests
