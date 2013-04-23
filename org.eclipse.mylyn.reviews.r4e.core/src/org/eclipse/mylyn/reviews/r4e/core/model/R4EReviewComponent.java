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
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4E Review Component</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent#getAssignedTo <em>Assigned To</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewComponent()
 * @model
 * @generated
 */
public interface R4EReviewComponent extends ReviewComponent {

	/**
	 * Returns the value of the '<em><b>Assigned To</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assigned To</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Assigned To</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewComponent_AssignedTo()
	 * @model
	 * @generated
	 */
	EList<String> getAssignedTo();

} // R4EReviewComponent
