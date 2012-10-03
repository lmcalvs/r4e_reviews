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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4EID Component</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent#getR4eId <em>R4e Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EIDComponent()
 * @model
 * @generated
 */
public interface R4EIDComponent extends R4EReviewComponent {
	/**
	 * Returns the value of the '<em><b>R4e Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>R4e Id</em>' containment reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>R4e Id</em>' containment reference.
	 * @see #setR4eId(R4EID)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EIDComponent_R4eId()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	R4EID getR4eId();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent#getR4eId <em>R4e Id</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>R4e Id</em>' containment reference.
	 * @see #getR4eId()
	 * @generated
	 */
	void setR4eId(R4EID value);

} // R4EIDComponent
