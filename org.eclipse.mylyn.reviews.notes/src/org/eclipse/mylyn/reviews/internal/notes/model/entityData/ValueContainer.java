/**
 * Copyright (c) 2012, 2013 Ericsson AB and others
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB - Initial API
 */
package org.eclipse.mylyn.reviews.internal.notes.model.entityData;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getText <em>Text</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getNumber <em>Number</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getDate <em>Date</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.EntityDataPackage#getValueContainer()
 * @model
 * @generated
 */
public interface ValueContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.EntityDataPackage#getValueContainer_Text()
	 * @model
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(Long)
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.EntityDataPackage#getValueContainer_Number()
	 * @model default="0"
	 * @generated
	 */
	Long getNumber();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
	void setNumber(Long value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.EntityDataPackage#getValueContainer_Date()
	 * @model
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

} // ValueContainer
