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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.mylyn.reviews.frame.core.model.Review;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Review</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getProject <em>Project</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getComponents <em>Components</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEntryCriteria <em>Entry Criteria</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getExtraNotes <em>Extra Notes</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getObjectives <em>Objectives</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getReferenceMaterial <em>Reference Material</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getDecision <em>Decision</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getStartDate <em>Start Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getAnomalyTemplate <em>Anomaly Template</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getUsersMap <em>Users Map</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getCreatedBy <em>Created By</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getIdsMap <em>Ids Map</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getActiveMeeting <em>Active Meeting</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview()
 * @model
 * @generated
 */
public interface R4EReview extends Review, R4EReviewComponent {
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
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_Project()
	 * @model
	 * @generated
	 */
	String getProject();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

	/**
	 * Returns the value of the '<em><b>Components</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Components</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_Components()
	 * @model
	 * @generated
	 */
	EList<String> getComponents();

	/**
	 * Returns the value of the '<em><b>Entry Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Criteria</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Criteria</em>' attribute.
	 * @see #setEntryCriteria(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_EntryCriteria()
	 * @model
	 * @generated
	 */
	String getEntryCriteria();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEntryCriteria <em>Entry Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Criteria</em>' attribute.
	 * @see #getEntryCriteria()
	 * @generated
	 */
	void setEntryCriteria(String value);

	/**
	 * Returns the value of the '<em><b>Extra Notes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extra Notes</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extra Notes</em>' attribute.
	 * @see #setExtraNotes(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_ExtraNotes()
	 * @model
	 * @generated
	 */
	String getExtraNotes();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getExtraNotes <em>Extra Notes</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extra Notes</em>' attribute.
	 * @see #getExtraNotes()
	 * @generated
	 */
	void setExtraNotes(String value);

	/**
	 * Returns the value of the '<em><b>Objectives</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objectives</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objectives</em>' attribute.
	 * @see #setObjectives(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_Objectives()
	 * @model
	 * @generated
	 */
	String getObjectives();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getObjectives <em>Objectives</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Objectives</em>' attribute.
	 * @see #getObjectives()
	 * @generated
	 */
	void setObjectives(String value);

	/**
	 * Returns the value of the '<em><b>Reference Material</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference Material</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference Material</em>' attribute.
	 * @see #setReferenceMaterial(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_ReferenceMaterial()
	 * @model
	 * @generated
	 */
	String getReferenceMaterial();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getReferenceMaterial <em>Reference Material</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference Material</em>' attribute.
	 * @see #getReferenceMaterial()
	 * @generated
	 */
	void setReferenceMaterial(String value);

	/**
	 * Returns the value of the '<em><b>Decision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decision</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decision</em>' containment reference.
	 * @see #setDecision(R4EReviewDecision)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_Decision()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	R4EReviewDecision getDecision();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getDecision <em>Decision</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decision</em>' containment reference.
	 * @see #getDecision()
	 * @generated
	 */
	void setDecision(R4EReviewDecision value);

	/**
	 * Returns the value of the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Date</em>' attribute.
	 * @see #setStartDate(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_StartDate()
	 * @model
	 * @generated
	 */
	Date getStartDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getStartDate <em>Start Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Date</em>' attribute.
	 * @see #getStartDate()
	 * @generated
	 */
	void setStartDate(Date value);

	/**
	 * Returns the value of the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Date</em>' attribute.
	 * @see #setEndDate(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_EndDate()
	 * @model
	 * @generated
	 */
	Date getEndDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEndDate <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Date</em>' attribute.
	 * @see #getEndDate()
	 * @generated
	 */
	void setEndDate(Date value);

	/**
	 * Returns the value of the '<em><b>Anomaly Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anomaly Template</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anomaly Template</em>' containment reference.
	 * @see #setAnomalyTemplate(R4EAnomaly)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_AnomalyTemplate()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	R4EAnomaly getAnomalyTemplate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getAnomalyTemplate <em>Anomaly Template</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anomaly Template</em>' containment reference.
	 * @see #getAnomalyTemplate()
	 * @generated
	 */
	void setAnomalyTemplate(R4EAnomaly value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType
	 * @see #setType(R4EReviewType)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_Type()
	 * @model default=""
	 * @generated
	 */
	R4EReviewType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType
	 * @see #getType()
	 * @generated
	 */
	void setType(R4EReviewType value);

	/**
	 * Returns the value of the '<em><b>Users Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Users Map</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_UsersMap()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapToUsers<org.eclipse.emf.ecore.EString, org.eclipse.mylyn.reviews.r4e.core.model.R4EUser>" transient="true" derived="true"
	 * @generated
	 */
	EMap<String, R4EUser> getUsersMap();

	/**
	 * Returns the value of the '<em><b>Ids Map</b></em>' map.
	 * The key is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EID},
	 * and the value is of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ids Map</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ids Map</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_IdsMap()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapIDToComponent<org.eclipse.mylyn.reviews.r4e.core.model.R4EID, org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent>" transient="true" derived="true"
	 * @generated
	 */
	EMap<R4EID, R4EIDComponent> getIdsMap();

	/**
	 * Returns the value of the '<em><b>Active Meeting</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Active Meeting</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Active Meeting</em>' containment reference.
	 * @see #setActiveMeeting(R4EMeetingData)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_ActiveMeeting()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	R4EMeetingData getActiveMeeting();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getActiveMeeting <em>Active Meeting</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Active Meeting</em>' containment reference.
	 * @see #getActiveMeeting()
	 * @generated
	 */
	void setActiveMeeting(R4EMeetingData value);

	/**
	 * Returns the value of the '<em><b>Created By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Created By</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Created By</em>' reference.
	 * @see #setCreatedBy(R4EUser)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReview_CreatedBy()
	 * @model required="true"
	 * @generated
	 */
	R4EUser getCreatedBy();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getCreatedBy <em>Created By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Created By</em>' reference.
	 * @see #getCreatedBy()
	 * @generated
	 */
	void setCreatedBy(R4EUser value);

} // R4EReview
