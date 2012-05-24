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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E File Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getDeltas <em>Deltas</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getBase <em>Base</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getInfoAtt <em>Info Att</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFileContext()
 * @model
 * @generated
 */
public interface R4EFileContext extends R4EIDComponent {

	/**
	 * Returns the value of the '<em><b>Deltas</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deltas</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deltas</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFileContext_Deltas()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<R4EDelta> getDeltas();

	/**
	 * Returns the value of the '<em><b>Base</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base</em>' containment reference.
	 * @see #setBase(R4EFileVersion)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFileContext_Base()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	R4EFileVersion getBase();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getBase <em>Base</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base</em>' containment reference.
	 * @see #getBase()
	 * @generated
	 */
	void setBase(R4EFileVersion value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(R4EFileVersion)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFileContext_Target()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	R4EFileVersion getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(R4EFileVersion value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType
	 * @see #setType(R4EContextType)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFileContext_Type()
	 * @model
	 * @generated
	 */
	R4EContextType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType
	 * @see #getType()
	 * @generated
	 */
	void setType(R4EContextType value);

	/**
	 * Returns the value of the '<em><b>Info Att</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Att</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Att</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EFileContext_InfoAtt()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapKeyToInfoAttributes<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getInfoAtt();
} // R4EFileContext
