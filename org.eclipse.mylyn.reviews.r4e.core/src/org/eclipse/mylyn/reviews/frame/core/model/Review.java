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
 *    Alvaro Sanchez-Leon - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.frame.core.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getTopics <em>Topics</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewItems <em>Review Items</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewTask <em>Review Task</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getState <em>State</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview()
 * @model
 * @generated
 */
public interface Review extends ReviewComponent {
	/**
	 * Returns the value of the '<em><b>Topics</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.frame.core.model.Topic}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Topics</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Topics</em>' reference list.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_Topics()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	EList<Topic> getTopics();

	/**
	 * Returns the value of the '<em><b>Review Items</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.frame.core.model.Item}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Items</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Items</em>' reference list.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_ReviewItems()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	EList<Item> getReviewItems();

	/**
	 * Returns the value of the '<em><b>Review Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Task</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Task</em>' containment reference.
	 * @see #setReviewTask(TaskReference)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_ReviewTask()
	 * @model containment="true"
	 * @generated
	 */
	TaskReference getReviewTask();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewTask <em>Review Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Task</em>' containment reference.
	 * @see #getReviewTask()
	 * @generated
	 */
	void setReviewTask(TaskReference value);

	/**
	 * Returns the value of the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference.
	 * @see #setState(ReviewState)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_State()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ReviewState getState();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getState <em>State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' containment reference.
	 * @see #getState()
	 * @generated
	 */
	void setState(ReviewState value);

} // Review
