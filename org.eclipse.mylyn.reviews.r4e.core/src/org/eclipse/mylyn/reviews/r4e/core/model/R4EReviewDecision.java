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
 * Alvaro Sanchez-Leon  - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Review Decision</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getSpentTime <em>Spent Time</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewDecision()
 * @model
 * @generated
 */
public interface R4EReviewDecision extends EObject {
	/**
	 * Returns the value of the '<em><b>Spent Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Spent Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Spent Time</em>' attribute.
	 * @see #setSpentTime(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewDecision_SpentTime()
	 * @model
	 * @generated
	 */
	int getSpentTime();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getSpentTime <em>Spent Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Spent Time</em>' attribute.
	 * @see #getSpentTime()
	 * @generated
	 */
	void setSpentTime(int value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision
	 * @see #setValue(R4EDecision)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewDecision_Value()
	 * @model
	 * @generated
	 */
	R4EDecision getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision
	 * @see #getValue()
	 * @generated
	 */
	void setValue(R4EDecision value);

} // R4EReviewDecision
