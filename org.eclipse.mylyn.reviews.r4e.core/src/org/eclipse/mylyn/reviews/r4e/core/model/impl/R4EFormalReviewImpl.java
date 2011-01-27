/**
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
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Formal Review</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getPreparationDate <em>Preparation Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getDecisionDate <em>Decision Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getReworkDate <em>Rework Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getPhaseOwnerID <em>Phase Owner ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getPhaseOwner <em>Phase Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EFormalReviewImpl extends R4EReviewImpl implements R4EFormalReview {
	/**
	 * The default value of the '{@link #getPreparationDate() <em>Preparation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreparationDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date PREPARATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreparationDate() <em>Preparation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreparationDate()
	 * @generated
	 * @ordered
	 */
	protected Date preparationDate = PREPARATION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDecisionDate() <em>Decision Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecisionDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DECISION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDecisionDate() <em>Decision Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecisionDate()
	 * @generated
	 * @ordered
	 */
	protected Date decisionDate = DECISION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getReworkDate() <em>Rework Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReworkDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date REWORK_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReworkDate() <em>Rework Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReworkDate()
	 * @generated
	 * @ordered
	 */
	protected Date reworkDate = REWORK_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPhaseOwnerID() <em>Phase Owner ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhaseOwnerID()
	 * @generated
	 * @ordered
	 */
	protected static final String PHASE_OWNER_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPhaseOwnerID() <em>Phase Owner ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhaseOwnerID()
	 * @generated
	 * @ordered
	 */
	protected String phaseOwnerID = PHASE_OWNER_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPhaseOwner() <em>Phase Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPhaseOwner()
	 * @generated
	 * @ordered
	 */
	protected R4EParticipant phaseOwner;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EFormalReviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_FORMAL_REVIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getPreparationDate() {
		return preparationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreparationDate(Date newPreparationDate) {
		Date oldPreparationDate = preparationDate;
		preparationDate = newPreparationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__PREPARATION_DATE, oldPreparationDate, preparationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDecisionDate() {
		return decisionDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDecisionDate(Date newDecisionDate) {
		Date oldDecisionDate = decisionDate;
		decisionDate = newDecisionDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__DECISION_DATE, oldDecisionDate, decisionDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getReworkDate() {
		return reworkDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReworkDate(Date newReworkDate) {
		Date oldReworkDate = reworkDate;
		reworkDate = newReworkDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__REWORK_DATE, oldReworkDate, reworkDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPhaseOwnerID() {
		return phaseOwnerID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPhaseOwnerID(String newPhaseOwnerID) {
		String oldPhaseOwnerID = phaseOwnerID;
		phaseOwnerID = newPhaseOwnerID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER_ID, oldPhaseOwnerID, phaseOwnerID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EParticipant getPhaseOwner() {
		if (phaseOwner != null && phaseOwner.eIsProxy()) {
			InternalEObject oldPhaseOwner = (InternalEObject)phaseOwner;
			phaseOwner = (R4EParticipant)eResolveProxy(oldPhaseOwner);
			if (phaseOwner != oldPhaseOwner) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER, oldPhaseOwner, phaseOwner));
			}
		}
		return phaseOwner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EParticipant basicGetPhaseOwner() {
		return phaseOwner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPhaseOwner(R4EParticipant newPhaseOwner) {
		R4EParticipant oldPhaseOwner = phaseOwner;
		phaseOwner = newPhaseOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER, oldPhaseOwner, phaseOwner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_FORMAL_REVIEW__PREPARATION_DATE:
				return getPreparationDate();
			case RModelPackage.R4E_FORMAL_REVIEW__DECISION_DATE:
				return getDecisionDate();
			case RModelPackage.R4E_FORMAL_REVIEW__REWORK_DATE:
				return getReworkDate();
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER_ID:
				return getPhaseOwnerID();
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				if (resolve) return getPhaseOwner();
				return basicGetPhaseOwner();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RModelPackage.R4E_FORMAL_REVIEW__PREPARATION_DATE:
				setPreparationDate((Date)newValue);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__DECISION_DATE:
				setDecisionDate((Date)newValue);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__REWORK_DATE:
				setReworkDate((Date)newValue);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER_ID:
				setPhaseOwnerID((String)newValue);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				setPhaseOwner((R4EParticipant)newValue);
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
			case RModelPackage.R4E_FORMAL_REVIEW__PREPARATION_DATE:
				setPreparationDate(PREPARATION_DATE_EDEFAULT);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__DECISION_DATE:
				setDecisionDate(DECISION_DATE_EDEFAULT);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__REWORK_DATE:
				setReworkDate(REWORK_DATE_EDEFAULT);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER_ID:
				setPhaseOwnerID(PHASE_OWNER_ID_EDEFAULT);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				setPhaseOwner((R4EParticipant)null);
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
			case RModelPackage.R4E_FORMAL_REVIEW__PREPARATION_DATE:
				return PREPARATION_DATE_EDEFAULT == null ? preparationDate != null : !PREPARATION_DATE_EDEFAULT.equals(preparationDate);
			case RModelPackage.R4E_FORMAL_REVIEW__DECISION_DATE:
				return DECISION_DATE_EDEFAULT == null ? decisionDate != null : !DECISION_DATE_EDEFAULT.equals(decisionDate);
			case RModelPackage.R4E_FORMAL_REVIEW__REWORK_DATE:
				return REWORK_DATE_EDEFAULT == null ? reworkDate != null : !REWORK_DATE_EDEFAULT.equals(reworkDate);
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER_ID:
				return PHASE_OWNER_ID_EDEFAULT == null ? phaseOwnerID != null : !PHASE_OWNER_ID_EDEFAULT.equals(phaseOwnerID);
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				return phaseOwner != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (preparationDate: ");
		result.append(preparationDate);
		result.append(", decisionDate: ");
		result.append(decisionDate);
		result.append(", reworkDate: ");
		result.append(reworkDate);
		result.append(", phaseOwnerID: ");
		result.append(phaseOwnerID);
		result.append(')');
		return result.toString();
	}

} //R4EFormalReviewImpl
