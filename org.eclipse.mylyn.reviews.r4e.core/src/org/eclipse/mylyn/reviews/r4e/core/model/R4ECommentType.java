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

import org.eclipse.mylyn.reviews.frame.core.model.CommentType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Comment Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ECommentType()
 * @model
 * @generated
 */
public interface R4ECommentType extends CommentType {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentClass
	 * @see #setType(R4ECommentClass)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ECommentType_Type()
	 * @model
	 * @generated
	 */
	R4ECommentClass getType();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentClass
	 * @see #getType()
	 * @generated
	 */
	void setType(R4ECommentClass value);

} // R4ECommentType
