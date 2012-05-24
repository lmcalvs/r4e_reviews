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
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.frame.core.model.impl.TopicImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Anomaly</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getAssignedTo <em>Assigned To</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getAnomaly <em>Anomaly</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getInfoAtt <em>Info Att</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getState <em>State</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getDueDate <em>Due Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getRank <em>Rank</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getNotAcceptedReason <em>Not Accepted Reason</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#isIsImported <em>Is Imported</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getFixedInVersion <em>Fixed In Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getRuleID <em>Rule ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getDecidedByID <em>Decided By ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getFixedByID <em>Fixed By ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl#getFollowUpByID <em>Follow Up By ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EAnomalyImpl extends TopicImpl implements R4EAnomaly {
	/**
	 * The cached value of the '{@link #getAssignedTo() <em>Assigned To</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignedTo()
	 * @generated
	 * @ordered
	 */
	protected EList<String> assignedTo;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected R4EID id;

	/**
	 * The default value of the '{@link #getCreatedOn() <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedOn()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATED_ON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreatedOn() <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedOn()
	 * @generated
	 * @ordered
	 */
	protected Date createdOn = CREATED_ON_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnomaly() <em>Anomaly</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnomaly()
	 * @generated
	 * @ordered
	 */
	protected R4EAnomaly anomaly;

	/**
	 * The cached value of the '{@link #getInfoAtt() <em>Info Att</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoAtt()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> infoAtt;

	/**
	 * The default value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected static final R4EAnomalyState STATE_EDEFAULT = R4EAnomalyState.R4E_ANOMALY_STATE_CREATED;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected R4EAnomalyState state = STATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDueDate() <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDueDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DUE_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDueDate() <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDueDate()
	 * @generated
	 * @ordered
	 */
	protected Date dueDate = DUE_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRank() <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRank()
	 * @generated
	 * @ordered
	 */
	protected static final R4EDesignRuleRank RANK_EDEFAULT = R4EDesignRuleRank.R4E_RANK_NONE;

	/**
	 * The cached value of the '{@link #getRank() <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRank()
	 * @generated
	 * @ordered
	 */
	protected R4EDesignRuleRank rank = RANK_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected R4EDesignRule rule;

	/**
	 * The default value of the '{@link #getNotAcceptedReason() <em>Not Accepted Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotAcceptedReason()
	 * @generated
	 * @ordered
	 */
	protected static final String NOT_ACCEPTED_REASON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNotAcceptedReason() <em>Not Accepted Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotAcceptedReason()
	 * @generated
	 * @ordered
	 */
	protected String notAcceptedReason = NOT_ACCEPTED_REASON_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsImported() <em>Is Imported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsImported()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_IMPORTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsImported() <em>Is Imported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsImported()
	 * @generated
	 * @ordered
	 */
	protected boolean isImported = IS_IMPORTED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFixedInVersion() <em>Fixed In Version</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFixedInVersion()
	 * @generated
	 * @ordered
	 */
	protected R4EFileVersion fixedInVersion;

	/**
	 * The default value of the '{@link #getRuleID() <em>Rule ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleID()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRuleID() <em>Rule ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleID()
	 * @generated
	 * @ordered
	 */
	protected String ruleID = RULE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDecidedByID() <em>Decided By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecidedByID()
	 * @generated
	 * @ordered
	 */
	protected static final String DECIDED_BY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDecidedByID() <em>Decided By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecidedByID()
	 * @generated
	 * @ordered
	 */
	protected String decidedByID = DECIDED_BY_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getFixedByID() <em>Fixed By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFixedByID()
	 * @generated
	 * @ordered
	 */
	protected static final String FIXED_BY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFixedByID() <em>Fixed By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFixedByID()
	 * @generated
	 * @ordered
	 */
	protected String fixedByID = FIXED_BY_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getFollowUpByID() <em>Follow Up By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFollowUpByID()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLLOW_UP_BY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFollowUpByID() <em>Follow Up By ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFollowUpByID()
	 * @generated
	 * @ordered
	 */
	protected String followUpByID = FOLLOW_UP_BY_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EAnomalyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_ANOMALY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAssignedTo() {
		if (assignedTo == null) {
			assignedTo = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_ANOMALY__ASSIGNED_TO);
		}
		return assignedTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreatedOn(Date newCreatedOn) {
		Date oldCreatedOn = createdOn;
		createdOn = newCreatedOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__CREATED_ON, oldCreatedOn, createdOn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EAnomaly getAnomaly() {
		if (anomaly != null && anomaly.eIsProxy()) {
			InternalEObject oldAnomaly = (InternalEObject)anomaly;
			anomaly = (R4EAnomaly)eResolveProxy(oldAnomaly);
			if (anomaly != oldAnomaly) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ANOMALY__ANOMALY, oldAnomaly, anomaly));
			}
		}
		return anomaly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EAnomaly basicGetAnomaly() {
		return anomaly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnomaly(R4EAnomaly newAnomaly) {
		R4EAnomaly oldAnomaly = anomaly;
		anomaly = newAnomaly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__ANOMALY, oldAnomaly, anomaly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getInfoAtt() {
		if (infoAtt == null) {
			infoAtt = new EcoreEMap<String,String>(RModelPackage.Literals.MAP_KEY_TO_INFO_ATTRIBUTES, MapKeyToInfoAttributesImpl.class, this, RModelPackage.R4E_ANOMALY__INFO_ATT);
		}
		return infoAtt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EAnomalyState getState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(R4EAnomalyState newState) {
		R4EAnomalyState oldState = state;
		state = newState == null ? STATE_EDEFAULT : newState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__STATE, oldState, state));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDueDate(Date newDueDate) {
		Date oldDueDate = dueDate;
		dueDate = newDueDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__DUE_DATE, oldDueDate, dueDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleRank getRank() {
		return rank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRank(R4EDesignRuleRank newRank) {
		R4EDesignRuleRank oldRank = rank;
		rank = newRank == null ? RANK_EDEFAULT : newRank;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__RANK, oldRank, rank));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRule getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (R4EDesignRule)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ANOMALY__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRule basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(R4EDesignRule newRule) {
		R4EDesignRule oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNotAcceptedReason() {
		return notAcceptedReason;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotAcceptedReason(String newNotAcceptedReason) {
		String oldNotAcceptedReason = notAcceptedReason;
		notAcceptedReason = newNotAcceptedReason;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__NOT_ACCEPTED_REASON, oldNotAcceptedReason, notAcceptedReason));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsImported() {
		return isImported;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsImported(boolean newIsImported) {
		boolean oldIsImported = isImported;
		isImported = newIsImported;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__IS_IMPORTED, oldIsImported, isImported));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion getFixedInVersion() {
		if (fixedInVersion != null && fixedInVersion.eIsProxy()) {
			InternalEObject oldFixedInVersion = (InternalEObject)fixedInVersion;
			fixedInVersion = (R4EFileVersion)eResolveProxy(oldFixedInVersion);
			if (fixedInVersion != oldFixedInVersion) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ANOMALY__FIXED_IN_VERSION, oldFixedInVersion, fixedInVersion));
			}
		}
		return fixedInVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion basicGetFixedInVersion() {
		return fixedInVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFixedInVersion(R4EFileVersion newFixedInVersion) {
		R4EFileVersion oldFixedInVersion = fixedInVersion;
		fixedInVersion = newFixedInVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__FIXED_IN_VERSION, oldFixedInVersion, fixedInVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRuleID() {
		return ruleID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleID(String newRuleID) {
		String oldRuleID = ruleID;
		ruleID = newRuleID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__RULE_ID, oldRuleID, ruleID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDecidedByID() {
		return decidedByID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDecidedByID(String newDecidedByID) {
		String oldDecidedByID = decidedByID;
		decidedByID = newDecidedByID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__DECIDED_BY_ID, oldDecidedByID, decidedByID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFixedByID() {
		return fixedByID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFixedByID(String newFixedByID) {
		String oldFixedByID = fixedByID;
		fixedByID = newFixedByID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__FIXED_BY_ID, oldFixedByID, fixedByID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFollowUpByID() {
		return followUpByID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFollowUpByID(String newFollowUpByID) {
		String oldFollowUpByID = followUpByID;
		followUpByID = newFollowUpByID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__FOLLOW_UP_BY_ID, oldFollowUpByID, followUpByID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EID getId() {
		if (id != null && id.eIsProxy()) {
			InternalEObject oldId = (InternalEObject)id;
			id = (R4EID)eResolveProxy(oldId);
			if (id != oldId) {
				InternalEObject newId = (InternalEObject)id;
				NotificationChain msgs = oldId.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY__ID, null, null);
				if (newId.eInternalContainer() == null) {
					msgs = newId.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY__ID, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ANOMALY__ID, oldId, id));
			}
		}
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EID basicGetId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetId(R4EID newId, NotificationChain msgs) {
		R4EID oldId = id;
		id = newId;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__ID, oldId, newId);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(R4EID newId) {
		if (newId != id) {
			NotificationChain msgs = null;
			if (id != null)
				msgs = ((InternalEObject)id).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY__ID, null, msgs);
			if (newId != null)
				msgs = ((InternalEObject)newId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY__ID, null, msgs);
			msgs = basicSetId(newId, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY__ID, newId, newId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_ANOMALY__ID:
				return basicSetId(null, msgs);
			case RModelPackage.R4E_ANOMALY__INFO_ATT:
				return ((InternalEList<?>)getInfoAtt()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_ANOMALY__ASSIGNED_TO:
				return getAssignedTo();
			case RModelPackage.R4E_ANOMALY__ID:
				if (resolve) return getId();
				return basicGetId();
			case RModelPackage.R4E_ANOMALY__CREATED_ON:
				return getCreatedOn();
			case RModelPackage.R4E_ANOMALY__ANOMALY:
				if (resolve) return getAnomaly();
				return basicGetAnomaly();
			case RModelPackage.R4E_ANOMALY__INFO_ATT:
				if (coreType) return getInfoAtt();
				else return getInfoAtt().map();
			case RModelPackage.R4E_ANOMALY__STATE:
				return getState();
			case RModelPackage.R4E_ANOMALY__DUE_DATE:
				return getDueDate();
			case RModelPackage.R4E_ANOMALY__RANK:
				return getRank();
			case RModelPackage.R4E_ANOMALY__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case RModelPackage.R4E_ANOMALY__NOT_ACCEPTED_REASON:
				return getNotAcceptedReason();
			case RModelPackage.R4E_ANOMALY__IS_IMPORTED:
				return isIsImported();
			case RModelPackage.R4E_ANOMALY__FIXED_IN_VERSION:
				if (resolve) return getFixedInVersion();
				return basicGetFixedInVersion();
			case RModelPackage.R4E_ANOMALY__RULE_ID:
				return getRuleID();
			case RModelPackage.R4E_ANOMALY__DECIDED_BY_ID:
				return getDecidedByID();
			case RModelPackage.R4E_ANOMALY__FIXED_BY_ID:
				return getFixedByID();
			case RModelPackage.R4E_ANOMALY__FOLLOW_UP_BY_ID:
				return getFollowUpByID();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RModelPackage.R4E_ANOMALY__ASSIGNED_TO:
				getAssignedTo().clear();
				getAssignedTo().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__ID:
				setId((R4EID)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__CREATED_ON:
				setCreatedOn((Date)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__ANOMALY:
				setAnomaly((R4EAnomaly)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__INFO_ATT:
				((EStructuralFeature.Setting)getInfoAtt()).set(newValue);
				return;
			case RModelPackage.R4E_ANOMALY__STATE:
				setState((R4EAnomalyState)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__DUE_DATE:
				setDueDate((Date)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__RANK:
				setRank((R4EDesignRuleRank)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__RULE:
				setRule((R4EDesignRule)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__NOT_ACCEPTED_REASON:
				setNotAcceptedReason((String)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__IS_IMPORTED:
				setIsImported((Boolean)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__FIXED_IN_VERSION:
				setFixedInVersion((R4EFileVersion)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__RULE_ID:
				setRuleID((String)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__DECIDED_BY_ID:
				setDecidedByID((String)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__FIXED_BY_ID:
				setFixedByID((String)newValue);
				return;
			case RModelPackage.R4E_ANOMALY__FOLLOW_UP_BY_ID:
				setFollowUpByID((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RModelPackage.R4E_ANOMALY__ASSIGNED_TO:
				getAssignedTo().clear();
				return;
			case RModelPackage.R4E_ANOMALY__ID:
				setId((R4EID)null);
				return;
			case RModelPackage.R4E_ANOMALY__CREATED_ON:
				setCreatedOn(CREATED_ON_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__ANOMALY:
				setAnomaly((R4EAnomaly)null);
				return;
			case RModelPackage.R4E_ANOMALY__INFO_ATT:
				getInfoAtt().clear();
				return;
			case RModelPackage.R4E_ANOMALY__STATE:
				setState(STATE_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__DUE_DATE:
				setDueDate(DUE_DATE_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__RANK:
				setRank(RANK_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__RULE:
				setRule((R4EDesignRule)null);
				return;
			case RModelPackage.R4E_ANOMALY__NOT_ACCEPTED_REASON:
				setNotAcceptedReason(NOT_ACCEPTED_REASON_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__IS_IMPORTED:
				setIsImported(IS_IMPORTED_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__FIXED_IN_VERSION:
				setFixedInVersion((R4EFileVersion)null);
				return;
			case RModelPackage.R4E_ANOMALY__RULE_ID:
				setRuleID(RULE_ID_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__DECIDED_BY_ID:
				setDecidedByID(DECIDED_BY_ID_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__FIXED_BY_ID:
				setFixedByID(FIXED_BY_ID_EDEFAULT);
				return;
			case RModelPackage.R4E_ANOMALY__FOLLOW_UP_BY_ID:
				setFollowUpByID(FOLLOW_UP_BY_ID_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RModelPackage.R4E_ANOMALY__ASSIGNED_TO:
				return assignedTo != null && !assignedTo.isEmpty();
			case RModelPackage.R4E_ANOMALY__ID:
				return id != null;
			case RModelPackage.R4E_ANOMALY__CREATED_ON:
				return CREATED_ON_EDEFAULT == null ? createdOn != null : !CREATED_ON_EDEFAULT.equals(createdOn);
			case RModelPackage.R4E_ANOMALY__ANOMALY:
				return anomaly != null;
			case RModelPackage.R4E_ANOMALY__INFO_ATT:
				return infoAtt != null && !infoAtt.isEmpty();
			case RModelPackage.R4E_ANOMALY__STATE:
				return state != STATE_EDEFAULT;
			case RModelPackage.R4E_ANOMALY__DUE_DATE:
				return DUE_DATE_EDEFAULT == null ? dueDate != null : !DUE_DATE_EDEFAULT.equals(dueDate);
			case RModelPackage.R4E_ANOMALY__RANK:
				return rank != RANK_EDEFAULT;
			case RModelPackage.R4E_ANOMALY__RULE:
				return rule != null;
			case RModelPackage.R4E_ANOMALY__NOT_ACCEPTED_REASON:
				return NOT_ACCEPTED_REASON_EDEFAULT == null ? notAcceptedReason != null : !NOT_ACCEPTED_REASON_EDEFAULT.equals(notAcceptedReason);
			case RModelPackage.R4E_ANOMALY__IS_IMPORTED:
				return isImported != IS_IMPORTED_EDEFAULT;
			case RModelPackage.R4E_ANOMALY__FIXED_IN_VERSION:
				return fixedInVersion != null;
			case RModelPackage.R4E_ANOMALY__RULE_ID:
				return RULE_ID_EDEFAULT == null ? ruleID != null : !RULE_ID_EDEFAULT.equals(ruleID);
			case RModelPackage.R4E_ANOMALY__DECIDED_BY_ID:
				return DECIDED_BY_ID_EDEFAULT == null ? decidedByID != null : !DECIDED_BY_ID_EDEFAULT.equals(decidedByID);
			case RModelPackage.R4E_ANOMALY__FIXED_BY_ID:
				return FIXED_BY_ID_EDEFAULT == null ? fixedByID != null : !FIXED_BY_ID_EDEFAULT.equals(fixedByID);
			case RModelPackage.R4E_ANOMALY__FOLLOW_UP_BY_ID:
				return FOLLOW_UP_BY_ID_EDEFAULT == null ? followUpByID != null : !FOLLOW_UP_BY_ID_EDEFAULT.equals(followUpByID);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == R4EReviewComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_ANOMALY__ASSIGNED_TO: return RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO;
				default: return -1;
			}
		}
		if (baseClass == R4EIDComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_ANOMALY__ID: return RModelPackage.R4EID_COMPONENT__ID;
				default: return -1;
			}
		}
		if (baseClass == R4EComment.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_ANOMALY__CREATED_ON: return RModelPackage.R4E_COMMENT__CREATED_ON;
				case RModelPackage.R4E_ANOMALY__ANOMALY: return RModelPackage.R4E_COMMENT__ANOMALY;
				case RModelPackage.R4E_ANOMALY__INFO_ATT: return RModelPackage.R4E_COMMENT__INFO_ATT;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == R4EReviewComponent.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO: return RModelPackage.R4E_ANOMALY__ASSIGNED_TO;
				default: return -1;
			}
		}
		if (baseClass == R4EIDComponent.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4EID_COMPONENT__ID: return RModelPackage.R4E_ANOMALY__ID;
				default: return -1;
			}
		}
		if (baseClass == R4EComment.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4E_COMMENT__CREATED_ON: return RModelPackage.R4E_ANOMALY__CREATED_ON;
				case RModelPackage.R4E_COMMENT__ANOMALY: return RModelPackage.R4E_ANOMALY__ANOMALY;
				case RModelPackage.R4E_COMMENT__INFO_ATT: return RModelPackage.R4E_ANOMALY__INFO_ATT;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (assignedTo: ");
		result.append(assignedTo);
		result.append(", createdOn: ");
		result.append(createdOn);
		result.append(", state: ");
		result.append(state);
		result.append(", dueDate: ");
		result.append(dueDate);
		result.append(", rank: ");
		result.append(rank);
		result.append(", notAcceptedReason: ");
		result.append(notAcceptedReason);
		result.append(", isImported: ");
		result.append(isImported);
		result.append(", ruleID: ");
		result.append(ruleID);
		result.append(", decidedByID: ");
		result.append(decidedByID);
		result.append(", fixedByID: ");
		result.append(fixedByID);
		result.append(", followUpByID: ");
		result.append(followUpByID);
		result.append(')');
		return result.toString();
	}

} //R4EAnomalyImpl
