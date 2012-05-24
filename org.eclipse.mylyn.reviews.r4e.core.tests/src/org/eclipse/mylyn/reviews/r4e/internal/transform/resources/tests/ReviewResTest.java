/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Review Res</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReviewResTest extends TestCase {

	/**
	 * The fixture for this Review Res test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewRes fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ReviewResTest.class);
	}

	/**
	 * Constructs a new Review Res test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewResTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Review Res test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ReviewRes fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Review Res test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewRes getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(TransResFactory.eINSTANCE.createReviewRes());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ReviewResTest
