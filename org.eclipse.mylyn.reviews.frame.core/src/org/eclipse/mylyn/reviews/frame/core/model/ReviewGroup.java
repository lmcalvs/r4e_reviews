/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.frame.core.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Review Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviews <em>Reviews</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviewGroupTask <em>Review Group Task</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReviewGroup()
 * @model
 * @generated
 */
public interface ReviewGroup extends ReviewComponent {
	/**
	 * Returns the value of the '<em><b>Reviews</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.frame.core.model.Review}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviews</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reviews</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReviewGroup_Reviews()
	 * @model containment="true" resolveProxies="true" transient="true" derived="true"
	 * @generated
	 */
	EList<Review> getReviews();

	/**
	 * Returns the value of the '<em><b>Review Group Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Group Task</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Group Task</em>' containment reference.
	 * @see #setReviewGroupTask(TaskReference)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReviewGroup_ReviewGroupTask()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	TaskReference getReviewGroupTask();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviewGroupTask <em>Review Group Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Group Task</em>' containment reference.
	 * @see #getReviewGroupTask()
	 * @generated
	 */
	void setReviewGroupTask(TaskReference value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReviewGroup_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // ReviewGroup
