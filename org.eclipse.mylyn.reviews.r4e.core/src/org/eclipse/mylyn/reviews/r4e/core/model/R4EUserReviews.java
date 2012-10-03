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

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4E User Reviews</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getInvitedToMap <em>Invited To Map</em>}</li>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getGroup <em>Group</em>}</li>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getCreatedReviews <em>Created Reviews</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUserReviews()
 * @model
 * @generated
 */
public interface R4EUserReviews extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUserReviews_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Invited To Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invited To Map</em>' map isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invited To Map</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUserReviews_InvitedToMap()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapNameToReview<org.eclipse.emf.ecore.EString, org.eclipse.mylyn.reviews.r4e.core.model.R4EReview>"
	 * @generated
	 */
	Map<String, R4EReview> getInvitedToMap();

	/**
	 * Returns the value of the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' reference.
	 * @see #setGroup(R4EReviewGroup)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUserReviews_Group()
	 * @model
	 * @generated
	 */
	R4EReviewGroup getGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getGroup <em>Group</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group</em>' reference.
	 * @see #getGroup()
	 * @generated
	 */
	void setGroup(R4EReviewGroup value);

	/**
	 * Returns the value of the '<em><b>Created Reviews</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created Reviews</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created Reviews</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUserReviews_CreatedReviews()
	 * @model
	 * @generated
	 */
	List<String> getCreatedReviews();

} // R4EUserReviews
