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

import org.eclipse.mylyn.reviews.core.model.IUser;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4E User</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getGroupPaths <em>Group Paths</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getSequenceIDCounter <em>Sequence ID Counter</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getAddedComments <em>Added Comments</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getAddedItems <em>Added Items</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCreatedByMe <em>Review Created By Me</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewInstance <em>Review Instance</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCompleted <em>Review Completed</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewCompletedCode <em>Review Completed Code</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser()
 * @model
 * @generated
 */
public interface R4EUser extends IUser, R4EReviewComponent {
	/**
	 * Returns the value of the '<em><b>Group Paths</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group Paths</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Paths</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_GroupPaths()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	List<String> getGroupPaths();

	/**
	 * Returns the value of the '<em><b>Sequence ID Counter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sequence ID Counter</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence ID Counter</em>' attribute.
	 * @see #setSequenceIDCounter(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_SequenceIDCounter()
	 * @model
	 * @generated
	 */
	int getSequenceIDCounter();

	/**
	 * Increments the sequence counter and returns its new value
	 * 
	 * @return <br>
	 *         MANUAL: Manual entry
	 * @generated NOT
	 */
	int getSequenceIDCounterNext();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getSequenceIDCounter <em>Sequence ID Counter</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sequence ID Counter</em>' attribute.
	 * @see #getSequenceIDCounter()
	 * @generated
	 */
	void setSequenceIDCounter(int value);

	/**
	 * Returns the value of the '<em><b>Added Comments</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Added Comments</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Added Comments</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_AddedComments()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	List<R4EComment> getAddedComments();

	/**
	 * Returns the value of the '<em><b>Added Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Added Items</em>' containment reference list isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Added Items</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_AddedItems()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	List<R4EItem> getAddedItems();

	/**
	 * Returns the value of the '<em><b>Review Created By Me</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Created By Me</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Created By Me</em>' attribute.
	 * @see #setReviewCreatedByMe(boolean)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_ReviewCreatedByMe()
	 * @model
	 * @generated
	 */
	boolean isReviewCreatedByMe();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCreatedByMe <em>Review Created By Me</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Created By Me</em>' attribute.
	 * @see #isReviewCreatedByMe()
	 * @generated
	 */
	void setReviewCreatedByMe(boolean value);

	/**
	 * Returns the value of the '<em><b>Review Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Instance</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Instance</em>' reference.
	 * @see #setReviewInstance(R4EReview)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_ReviewInstance()
	 * @model required="true"
	 * @generated
	 */
	R4EReview getReviewInstance();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewInstance <em>Review Instance</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Instance</em>' reference.
	 * @see #getReviewInstance()
	 * @generated
	 */
	void setReviewInstance(R4EReview value);

	/**
	 * Returns the value of the '<em><b>Review Completed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Completed</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Completed</em>' attribute.
	 * @see #setReviewCompleted(boolean)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_ReviewCompleted()
	 * @model
	 * @generated
	 */
	boolean isReviewCompleted();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCompleted <em>Review Completed</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Completed</em>' attribute.
	 * @see #isReviewCompleted()
	 * @generated
	 */
	void setReviewCompleted(boolean value);

	/**
	 * Returns the value of the '<em><b>Review Completed Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review Completed Code</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review Completed Code</em>' attribute.
	 * @see #setReviewCompletedCode(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUser_ReviewCompletedCode()
	 * @model
	 * @generated
	 */
	int getReviewCompletedCode();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewCompletedCode <em>Review Completed Code</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review Completed Code</em>' attribute.
	 * @see #getReviewCompletedCode()
	 * @generated
	 */
	void setReviewCompletedCode(int value);

} // R4EUser
