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
 * A representation of the model object '<em><b>Topic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getTask <em>Task</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getLocation <em>Location</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getComments <em>Comments</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getReview <em>Review</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getTitle <em>Title</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTopic()
 * @model
 * @generated
 */
public interface Topic extends Comment {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' containment reference.
	 * @see #setTask(TaskReference)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTopic_Task()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	TaskReference getTask();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getTask <em>Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' containment reference.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(TaskReference value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.frame.core.model.Location}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTopic_Location()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Location> getLocation();

	/**
	 * Returns the value of the '<em><b>Comments</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.frame.core.model.Comment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Comments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comments</em>' reference list.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTopic_Comments()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	EList<Comment> getComments();

	/**
	 * Returns the value of the '<em><b>Review</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Review</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Review</em>' reference.
	 * @see #setReview(Review)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTopic_Review()
	 * @model required="true"
	 * @generated
	 */
	Review getReview();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getReview <em>Review</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review</em>' reference.
	 * @see #getReview()
	 * @generated
	 */
	void setReview(Review value);

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTopic_Title()
	 * @model
	 * @generated
	 */
	String getTitle();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

} // Topic
