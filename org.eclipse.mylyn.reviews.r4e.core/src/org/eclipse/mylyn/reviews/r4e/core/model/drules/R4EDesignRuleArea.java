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
 *    Alvaro Sanchez-Leon - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.drules;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Design Rule Area</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getViolations <em>Violations</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#getR4EDesignRuleArea()
 * @model
 * @generated
 */
public interface R4EDesignRuleArea extends ReviewComponent {
	/**
	 * Returns the value of the '<em><b>Violations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Violations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Violations</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#getR4EDesignRuleArea_Violations()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<R4EDesignRuleViolation> getViolations();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#getR4EDesignRuleArea_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // R4EDesignRuleArea
