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
import org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Review Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getFolder <em>Folder</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDefaultEntryCriteria <em>Default Entry Criteria</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableProjects <em>Available Projects</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableComponents <em>Available Components</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDesignRuleLocations <em>Design Rule Locations</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableAnomalyTypes <em>Available Anomaly Types</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAnomalyTypeKeyToReference <em>Anomaly Type Key To Reference</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getReviewsMap <em>Reviews Map</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getUserReviews <em>User Reviews</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup()
 * @model extendedMetaData="name='R4EReviewGroup'"
 * @generated
 */
public interface R4EReviewGroup extends ReviewGroup, R4EReviewComponent {
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
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Folder</em>' attribute.
	 * @see #setFolder(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_Folder()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	String getFolder();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getFolder <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Folder</em>' attribute.
	 * @see #getFolder()
	 * @generated
	 */
	void setFolder(String value);

	/**
	 * Returns the value of the '<em><b>Default Entry Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Entry Criteria</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Entry Criteria</em>' attribute.
	 * @see #setDefaultEntryCriteria(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_DefaultEntryCriteria()
	 * @model
	 * @generated
	 */
	String getDefaultEntryCriteria();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDefaultEntryCriteria <em>Default Entry Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Entry Criteria</em>' attribute.
	 * @see #getDefaultEntryCriteria()
	 * @generated
	 */
	void setDefaultEntryCriteria(String value);

	/**
	 * Returns the value of the '<em><b>Available Projects</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Projects</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Available Projects</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_AvailableProjects()
	 * @model
	 * @generated
	 */
	EList<String> getAvailableProjects();

	/**
	 * Returns the value of the '<em><b>Available Components</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Components</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Available Components</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_AvailableComponents()
	 * @model
	 * @generated
	 */
	EList<String> getAvailableComponents();

	/**
	 * Returns the value of the '<em><b>Design Rule Locations</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Design Rule Locations</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Design Rule Locations</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_DesignRuleLocations()
	 * @model
	 * @generated
	 */
	EList<String> getDesignRuleLocations();

	/**
	 * Returns the value of the '<em><b>Available Anomaly Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Anomaly Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Available Anomaly Types</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_AvailableAnomalyTypes()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<R4EAnomalyType> getAvailableAnomalyTypes();

	/**
	 * Returns the value of the '<em><b>Anomaly Type Key To Reference</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anomaly Type Key To Reference</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anomaly Type Key To Reference</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_AnomalyTypeKeyToReference()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapToAnomalyType<org.eclipse.emf.ecore.EString, org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType>" transient="true" derived="true"
	 * @generated
	 */
	EMap<String, R4EAnomalyType> getAnomalyTypeKeyToReference();

	/**
	 * Returns the value of the '<em><b>Reviews Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reviews Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reviews Map</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_ReviewsMap()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapNameToReview<org.eclipse.emf.ecore.EString, org.eclipse.mylyn.reviews.r4e.core.model.R4EReview>" transient="true" derived="true"
	 * @generated
	 */
	EMap<String, R4EReview> getReviewsMap();

	/**
	 * Returns the value of the '<em><b>User Reviews</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Reviews</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Reviews</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewGroup_UserReviews()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapUserIDToUserReviews<org.eclipse.emf.ecore.EString, org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews>" transient="true" derived="true"
	 * @generated
	 */
	EMap<String, R4EUserReviews> getUserReviews();

} // R4EReviewGroup
