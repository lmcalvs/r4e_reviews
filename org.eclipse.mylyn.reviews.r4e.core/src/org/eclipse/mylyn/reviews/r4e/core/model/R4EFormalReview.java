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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Formal Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwner <em>Phase Owner</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhases <em>Phases</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getCurrent <em>Current</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview()
 * @model
 * @generated
 */
public interface R4EFormalReview extends R4EReview {
	/**
	 * Returns the value of the '<em><b>Phase Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phase Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Phase Owner</em>' reference.
	 * @see #setPhaseOwner(R4EParticipant)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_PhaseOwner()
	 * @model required="true" transient="true" derived="true"
	 * @generated
	 */
	R4EParticipant getPhaseOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwner <em>Phase Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phase Owner</em>' reference.
	 * @see #getPhaseOwner()
	 * @generated
	 */
	void setPhaseOwner(R4EParticipant value);

	/**
	 * Returns the value of the '<em><b>Phases</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phases</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Phases</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_Phases()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<R4EReviewPhaseInfo> getPhases();

	/**
	 * Returns the value of the '<em><b>Current</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Current</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Current</em>' reference.
	 * @see #setCurrent(R4EReviewPhaseInfo)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_Current()
	 * @model
	 * @generated
	 */
	R4EReviewPhaseInfo getCurrent();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getCurrent <em>Current</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Current</em>' reference.
	 * @see #getCurrent()
	 * @generated
	 */
	void setCurrent(R4EReviewPhaseInfo value);

} // R4EFormalReview
