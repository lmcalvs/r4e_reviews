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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4E Position</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition#getAnomalyFile <em>Anomaly File</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EPosition()
 * @model
 * @generated
 */
public interface R4EPosition extends EObject {

	/**
	 * Returns the value of the '<em><b>Anomaly File</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anomaly File</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anomaly File</em>' reference.
	 * @see #setAnomalyFile(R4EFileVersion)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EPosition_AnomalyFile()
	 * @model
	 * @generated
	 */
	R4EFileVersion getAnomalyFile();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition#getAnomalyFile <em>Anomaly File</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anomaly File</em>' reference.
	 * @see #getAnomalyFile()
	 * @generated
	 */
	void setAnomalyFile(R4EFileVersion value);
} // R4EPosition
