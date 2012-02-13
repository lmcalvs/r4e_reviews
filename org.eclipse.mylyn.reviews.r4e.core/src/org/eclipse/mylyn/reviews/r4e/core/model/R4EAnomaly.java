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
 * Alvaro Sanchez-Leon  - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model;

import java.util.Date;

import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Anomaly</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getState <em>State</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDueDate <em>Due Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRank <em>Rank</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getNotAcceptedReason <em>Not Accepted Reason</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#isIsImported <em>Is Imported</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedInVersion <em>Fixed In Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRuleID <em>Rule ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDecidedByID <em>Decided By ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedByID <em>Fixed By ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFollowUpByID <em>Follow Up By ID</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly()
 * @model
 * @generated
 */
public interface R4EAnomaly extends Topic, R4EReviewComponent, R4EComment {
	/**
	 * Returns the value of the '<em><b>State</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>State</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState
	 * @see #setState(R4EAnomalyState)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_State()
	 * @model
	 * @generated
	 */
	R4EAnomalyState getState();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getState <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>State</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState
	 * @see #getState()
	 * @generated
	 */
	void setState(R4EAnomalyState value);

	/**
	 * Returns the value of the '<em><b>Due Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Due Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Due Date</em>' attribute.
	 * @see #setDueDate(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_DueDate()
	 * @model
	 * @generated
	 */
	Date getDueDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDueDate <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Due Date</em>' attribute.
	 * @see #getDueDate()
	 * @generated
	 */
	void setDueDate(Date value);

	/**
	 * Returns the value of the '<em><b>Rank</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rank</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rank</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank
	 * @see #setRank(R4EDesignRuleRank)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_Rank()
	 * @model
	 * @generated
	 */
	R4EDesignRuleRank getRank();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRank <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rank</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank
	 * @see #getRank()
	 * @generated
	 */
	void setRank(R4EDesignRuleRank value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule</em>' reference.
	 * @see #setRule(R4EDesignRule)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_Rule()
	 * @model transient="true" derived="true"
	 * @generated
	 */
	R4EDesignRule getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRule <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(R4EDesignRule value);

	/**
	 * Returns the value of the '<em><b>Not Accepted Reason</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Not Accepted Reason</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Not Accepted Reason</em>' attribute.
	 * @see #setNotAcceptedReason(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_NotAcceptedReason()
	 * @model
	 * @generated
	 */
	String getNotAcceptedReason();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getNotAcceptedReason <em>Not Accepted Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Not Accepted Reason</em>' attribute.
	 * @see #getNotAcceptedReason()
	 * @generated
	 */
	void setNotAcceptedReason(String value);

	/**
	 * Returns the value of the '<em><b>Is Imported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Imported</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Imported</em>' attribute.
	 * @see #setIsImported(boolean)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_IsImported()
	 * @model
	 * @generated
	 */
	boolean isIsImported();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#isIsImported <em>Is Imported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Imported</em>' attribute.
	 * @see #isIsImported()
	 * @generated
	 */
	void setIsImported(boolean value);

	/**
	 * Returns the value of the '<em><b>Fixed In Version</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed In Version</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed In Version</em>' reference.
	 * @see #setFixedInVersion(R4EFileVersion)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_FixedInVersion()
	 * @model
	 * @generated
	 */
	R4EFileVersion getFixedInVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedInVersion <em>Fixed In Version</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed In Version</em>' reference.
	 * @see #getFixedInVersion()
	 * @generated
	 */
	void setFixedInVersion(R4EFileVersion value);

	/**
	 * Returns the value of the '<em><b>Rule ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule ID</em>' attribute.
	 * @see #setRuleID(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_RuleID()
	 * @model
	 * @generated
	 */
	String getRuleID();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRuleID <em>Rule ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule ID</em>' attribute.
	 * @see #getRuleID()
	 * @generated
	 */
	void setRuleID(String value);

	/**
	 * Returns the value of the '<em><b>Decided By ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decided By ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decided By ID</em>' attribute.
	 * @see #setDecidedByID(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_DecidedByID()
	 * @model
	 * @generated
	 */
	String getDecidedByID();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDecidedByID <em>Decided By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decided By ID</em>' attribute.
	 * @see #getDecidedByID()
	 * @generated
	 */
	void setDecidedByID(String value);

	/**
	 * Returns the value of the '<em><b>Fixed By ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fixed By ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fixed By ID</em>' attribute.
	 * @see #setFixedByID(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_FixedByID()
	 * @model
	 * @generated
	 */
	String getFixedByID();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedByID <em>Fixed By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fixed By ID</em>' attribute.
	 * @see #getFixedByID()
	 * @generated
	 */
	void setFixedByID(String value);

	/**
	 * Returns the value of the '<em><b>Follow Up By ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Follow Up By ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Follow Up By ID</em>' attribute.
	 * @see #setFollowUpByID(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomaly_FollowUpByID()
	 * @model
	 * @generated
	 */
	String getFollowUpByID();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFollowUpByID <em>Follow Up By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Follow Up By ID</em>' attribute.
	 * @see #getFollowUpByID()
	 * @generated
	 */
	void setFollowUpByID(String value);
} // R4EAnomaly
