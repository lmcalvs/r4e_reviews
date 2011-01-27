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
package org.eclipse.mylyn.reviews.r4e.core.model;

import org.eclipse.mylyn.reviews.frame.core.model.ReviewState;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Review State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState#getState <em>State</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewState()
 * @model
 * @generated
 */
public interface R4EReviewState extends ReviewState {

	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase
	 * @see #setState(R4EReviewPhase)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewState_State()
	 * @model
	 * @generated
	 */
	R4EReviewPhase getState();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase
	 * @see #getState()
	 * @generated
	 */
	void setState(R4EReviewPhase value);
} // R4EReviewState
