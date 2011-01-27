/**
/**
 * Copyright (c) 2010 Ericsson
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 * Alvaro Sanchez-Leon  - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.mylyn.reviews.r4e.core.model.serial.PersistenceTest;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ChangeResControllerTest;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CommonTest;
import org.eclipse.mylyn.reviews.r4e.core.model.tests.ModelTests;
import org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.OSPermissionTest;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>R4e</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class R4ECoreAllTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new R4ECoreAllTests("R4E Core Tests");
		suite.addTest(ModelTests.suite());
		suite.addTestSuite(PersistenceTest.class);
		suite.addTestSuite(OSPermissionTest.class);
		suite.addTestSuite(ChangeResControllerTest.class);
		suite.addTestSuite(CommonTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4ECoreAllTests(String name) {
		super(name);
	}

} //R4eAllTests
