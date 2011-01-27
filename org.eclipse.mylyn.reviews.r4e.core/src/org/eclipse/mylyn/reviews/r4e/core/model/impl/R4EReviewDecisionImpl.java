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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Review Decision</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewDecisionImpl#getSpentTime <em>Spent Time</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewDecisionImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EReviewDecisionImpl extends EObjectImpl implements R4EReviewDecision {
	/**
	 * The default value of the '{@link #getSpentTime() <em>Spent Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpentTime()
	 * @generated
	 * @ordered
	 */
	protected static final int SPENT_TIME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSpentTime() <em>Spent Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSpentTime()
	 * @generated
	 * @ordered
	 */
	protected int spentTime = SPENT_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final R4EDecision VALUE_EDEFAULT = R4EDecision.R4E_REVIEW_DECISION_NONE;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected R4EDecision value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EReviewDecisionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_REVIEW_DECISION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSpentTime() {
		return spentTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSpentTime(int newSpentTime) {
		int oldSpentTime = spentTime;
		spentTime = newSpentTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_REVIEW_DECISION__SPENT_TIME, oldSpentTime, spentTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDecision getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(R4EDecision newValue) {
		R4EDecision oldValue = value;
		value = newValue == null ? VALUE_EDEFAULT : newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_REVIEW_DECISION__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_REVIEW_DECISION__SPENT_TIME:
				return getSpentTime();
			case RModelPackage.R4E_REVIEW_DECISION__VALUE:
				return getValue();
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
			case RModelPackage.R4E_REVIEW_DECISION__SPENT_TIME:
				setSpentTime((Integer)newValue);
				return;
			case RModelPackage.R4E_REVIEW_DECISION__VALUE:
				setValue((R4EDecision)newValue);
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
			case RModelPackage.R4E_REVIEW_DECISION__SPENT_TIME:
				setSpentTime(SPENT_TIME_EDEFAULT);
				return;
			case RModelPackage.R4E_REVIEW_DECISION__VALUE:
				setValue(VALUE_EDEFAULT);
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
			case RModelPackage.R4E_REVIEW_DECISION__SPENT_TIME:
				return spentTime != SPENT_TIME_EDEFAULT;
			case RModelPackage.R4E_REVIEW_DECISION__VALUE:
				return value != VALUE_EDEFAULT;
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
		result.append(" (spentTime: ");
		result.append(spentTime);
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} //R4EReviewDecisionImpl
