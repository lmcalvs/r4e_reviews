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

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Review Group Res</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getReviewsRes <em>Reviews Res</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getFilesPrefix <em>Files Prefix</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage#getReviewGroupRes()
 * @model
 * @generated
 */
public interface ReviewGroupRes extends R4EReviewGroup {
	/**
	 * Returns the value of the '<em><b>Reviews Res</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviews Res</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reviews Res</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage#getReviewGroupRes_ReviewsRes()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<ReviewRes> getReviewsRes();

	/**
	 * Returns the value of the '<em><b>Files Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Files Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files Prefix</em>' attribute.
	 * @see #setFilesPrefix(String)
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage#getReviewGroupRes_FilesPrefix()
	 * @model
	 * @generated
	 */
	String getFilesPrefix();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getFilesPrefix <em>Files Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Files Prefix</em>' attribute.
	 * @see #getFilesPrefix()
	 * @generated
	 */
	void setFilesPrefix(String value);

} // ReviewGroupRes
