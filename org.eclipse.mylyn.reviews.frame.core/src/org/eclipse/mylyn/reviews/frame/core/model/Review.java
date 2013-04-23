/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
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
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview()
 * @model
 * @generated
 */
public interface Review extends ReviewComponent, SubModelRoot {
	/**
	 * Returns the value of the '<em><b>Topics</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.frame.core.model.Topic}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Topics</em>' containment reference list isn't clear,
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
	 * If the meaning of the '<em>Review Task</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Task</em>' containment reference.
	 * @see #setReviewTask(TaskReference)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_ReviewTask()
	 * @model containment="true" resolveProxies="true"
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
	 * If the meaning of the '<em>State</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' containment reference.
	 * @see #setState(ReviewState)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_State()
	 * @model containment="true" resolveProxies="true" required="true"
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

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getReview_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // Review
