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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Item#getAddedBy <em>Added By</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.Item#getReview <em>Review</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getItem()
 * @model
 * @generated
 */
public interface Item extends ReviewComponent {
	/**
	 * Returns the value of the '<em><b>Added By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Added By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Added By</em>' reference.
	 * @see #setAddedBy(User)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getItem_AddedBy()
	 * @model required="true"
	 * @generated
	 */
	User getAddedBy();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Item#getAddedBy <em>Added By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Added By</em>' reference.
	 * @see #getAddedBy()
	 * @generated
	 */
	void setAddedBy(User value);

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
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getItem_Review()
	 * @model required="true"
	 * @generated
	 */
	Review getReview();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.Item#getReview <em>Review</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Review</em>' reference.
	 * @see #getReview()
	 * @generated
	 */
	void setReview(Review value);

} // Item
