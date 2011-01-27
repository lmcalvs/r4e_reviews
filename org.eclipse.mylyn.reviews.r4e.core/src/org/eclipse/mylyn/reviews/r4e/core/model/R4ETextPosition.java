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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Text Position</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getLength <em>Length</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getEndLine <em>End Line</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETextPosition()
 * @model
 * @generated
 */
public interface R4ETextPosition extends R4EPosition {
	/**
	 * Returns the value of the '<em><b>Start Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Position</em>' attribute.
	 * @see #setStartPosition(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETextPosition_StartPosition()
	 * @model
	 * @generated
	 */
	int getStartPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartPosition <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Position</em>' attribute.
	 * @see #getStartPosition()
	 * @generated
	 */
	void setStartPosition(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETextPosition_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

	/**
	 * Returns the value of the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Line</em>' attribute.
	 * @see #setStartLine(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETextPosition_StartLine()
	 * @model
	 * @generated
	 */
	int getStartLine();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartLine <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Line</em>' attribute.
	 * @see #getStartLine()
	 * @generated
	 */
	void setStartLine(int value);

	/**
	 * Returns the value of the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Line</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Line</em>' attribute.
	 * @see #setEndLine(int)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETextPosition_EndLine()
	 * @model
	 * @generated
	 */
	int getEndLine();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getEndLine <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Line</em>' attribute.
	 * @see #getEndLine()
	 * @generated
	 */
	void setEndLine(int value);

} // R4ETextPosition
