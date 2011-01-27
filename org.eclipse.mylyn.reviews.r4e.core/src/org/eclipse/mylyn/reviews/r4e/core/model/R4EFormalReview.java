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

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Formal Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPreparationDate <em>Preparation Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getDecisionDate <em>Decision Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getReworkDate <em>Rework Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwnerID <em>Phase Owner ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwner <em>Phase Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview()
 * @model
 * @generated
 */
public interface R4EFormalReview extends R4EReview {
	/**
	 * Returns the value of the '<em><b>Preparation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preparation Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preparation Date</em>' attribute.
	 * @see #setPreparationDate(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_PreparationDate()
	 * @model
	 * @generated
	 */
	Date getPreparationDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPreparationDate <em>Preparation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Preparation Date</em>' attribute.
	 * @see #getPreparationDate()
	 * @generated
	 */
	void setPreparationDate(Date value);

	/**
	 * Returns the value of the '<em><b>Decision Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decision Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decision Date</em>' attribute.
	 * @see #setDecisionDate(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_DecisionDate()
	 * @model
	 * @generated
	 */
	Date getDecisionDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getDecisionDate <em>Decision Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decision Date</em>' attribute.
	 * @see #getDecisionDate()
	 * @generated
	 */
	void setDecisionDate(Date value);

	/**
	 * Returns the value of the '<em><b>Rework Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rework Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rework Date</em>' attribute.
	 * @see #setReworkDate(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_ReworkDate()
	 * @model
	 * @generated
	 */
	Date getReworkDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getReworkDate <em>Rework Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rework Date</em>' attribute.
	 * @see #getReworkDate()
	 * @generated
	 */
	void setReworkDate(Date value);

	/**
	 * Returns the value of the '<em><b>Phase Owner ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Phase Owner ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Phase Owner ID</em>' attribute.
	 * @see #setPhaseOwnerID(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFormalReview_PhaseOwnerID()
	 * @model
	 * @generated
	 */
	String getPhaseOwnerID();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwnerID <em>Phase Owner ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Phase Owner ID</em>' attribute.
	 * @see #getPhaseOwnerID()
	 * @generated
	 */
	void setPhaseOwnerID(String value);

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

} // R4EFormalReview
