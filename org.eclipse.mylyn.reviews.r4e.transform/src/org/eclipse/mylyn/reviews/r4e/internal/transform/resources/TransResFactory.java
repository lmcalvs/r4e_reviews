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
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage
 * @generated
 */
public interface TransResFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TransResFactory eINSTANCE = org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.TransResFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Review Group Res</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Review Group Res</em>'.
	 * @generated
	 */
	ReviewGroupRes createReviewGroupRes();

	/**
	 * Returns a new object of class '<em>Review Res</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Review Res</em>'.
	 * @generated
	 */
	ReviewRes createReviewRes();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TransResPackage getTransResPackage();

} //TransResFactory
