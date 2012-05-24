/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources;

import org.eclipse.emf.common.util.EList;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Review Res</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes#getUsersRes <em>Users Res</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage#getReviewRes()
 * @model
 * @generated
 */
public interface ReviewRes extends R4EFormalReview {
	/**
	 * Returns the value of the '<em><b>Users Res</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users Res</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Users Res</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage#getReviewRes_UsersRes()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<R4EUser> getUsersRes();

} // ReviewRes
