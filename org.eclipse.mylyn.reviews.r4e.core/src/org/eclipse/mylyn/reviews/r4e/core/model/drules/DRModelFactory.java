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
 *    Alvaro Sanchez-Leon - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.drules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage
 * @generated
 */
public interface DRModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	DRModelFactory eINSTANCE = org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>R4E Design Rule Collection</em>'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return a new object of class '<em>R4E Design Rule Collection</em>'.
	 * @generated
	 */
	R4EDesignRuleCollection createR4EDesignRuleCollection();

	/**
	 * Returns a new object of class '<em>R4E Design Rule</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Design Rule</em>'.
	 * @generated
	 */
	R4EDesignRule createR4EDesignRule();

	/**
	 * Returns a new object of class '<em>R4E Design Rule Area</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Design Rule Area</em>'.
	 * @generated
	 */
	R4EDesignRuleArea createR4EDesignRuleArea();

	/**
	 * Returns a new object of class '<em>R4E Design Rule Violation</em>'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Design Rule Violation</em>'.
	 * @generated
	 */
	R4EDesignRuleViolation createR4EDesignRuleViolation();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	DRModelPackage getDRModelPackage();

} //DRModelFactory
