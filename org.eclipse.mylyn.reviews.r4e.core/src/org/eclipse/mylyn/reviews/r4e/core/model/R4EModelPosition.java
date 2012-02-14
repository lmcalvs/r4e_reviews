/**
 * Copyright (c) 2010 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.core.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Model Position</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition#getObjectID <em>Object ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition#getDifferenceDescription <em>Difference Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EModelPosition()
 * @model
 * @generated
 */
public interface R4EModelPosition extends R4EPosition {
	/**
	 * Returns the value of the '<em><b>Object ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Object ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Object ID</em>' attribute.
	 * @see #setObjectID(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EModelPosition_ObjectID()
	 * @model
	 * @generated
	 */
	String getObjectID();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition#getObjectID <em>Object ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Object ID</em>' attribute.
	 * @see #getObjectID()
	 * @generated
	 */
	void setObjectID(String value);

	/**
	 * Returns the value of the '<em><b>Difference Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Difference Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Difference Description</em>' attribute.
	 * @see #setDifferenceDescription(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EModelPosition_DifferenceDescription()
	 * @model
	 * @generated
	 */
	String getDifferenceDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition#getDifferenceDescription <em>Difference Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Difference Description</em>' attribute.
	 * @see #getDifferenceDescription()
	 * @generated
	 */
	void setDifferenceDescription(String value);

} // R4EModelPosition
