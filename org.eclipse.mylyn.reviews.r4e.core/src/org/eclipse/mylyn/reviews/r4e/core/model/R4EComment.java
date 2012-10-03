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

import java.util.Date;
import java.util.Map;

import org.eclipse.mylyn.reviews.core.model.IComment;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4E Comment</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getAnomaly <em>Anomaly</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getInfoAtt <em>Info Att</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EComment()
 * @model
 * @generated
 */
public interface R4EComment extends IComment, R4EReviewComponent, R4EIDComponent {
	/**
	 * Returns the value of the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created On</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created On</em>' attribute.
	 * @see #setCreatedOn(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EComment_CreatedOn()
	 * @model
	 * @generated
	 */
	Date getCreatedOn();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getCreatedOn <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created On</em>' attribute.
	 * @see #getCreatedOn()
	 * @generated
	 */
	void setCreatedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Anomaly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anomaly</em>' reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anomaly</em>' reference.
	 * @see #setAnomaly(R4EAnomaly)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EComment_Anomaly()
	 * @model required="true"
	 * @generated
	 */
	R4EAnomaly getAnomaly();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getAnomaly <em>Anomaly</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anomaly</em>' reference.
	 * @see #getAnomaly()
	 * @generated
	 */
	void setAnomaly(R4EAnomaly value);

	/**
	 * Returns the value of the '<em><b>Info Att</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Att</em>' map isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Att</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EComment_InfoAtt()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapKeyToInfoAttributes<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	Map<String, String> getInfoAtt();

} // R4EComment
