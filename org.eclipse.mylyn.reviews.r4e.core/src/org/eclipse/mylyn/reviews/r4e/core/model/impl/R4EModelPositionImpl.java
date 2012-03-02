/**
 * Copyright (c) 2010 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Model Position</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EModelPositionImpl#getObjectID <em>Object ID</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EModelPositionImpl#getDifferenceDescription <em>Difference Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EModelPositionImpl extends R4EPositionImpl implements R4EModelPosition {
	/**
	 * The default value of the '{@link #getObjectID() <em>Object ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectID()
	 * @generated
	 * @ordered
	 */
	protected static final String OBJECT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectID() <em>Object ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectID()
	 * @generated
	 * @ordered
	 */
	protected String objectID = OBJECT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getDifferenceDescription() <em>Difference Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDifferenceDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DIFFERENCE_DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDifferenceDescription() <em>Difference Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDifferenceDescription()
	 * @generated
	 * @ordered
	 */
	protected String differenceDescription = DIFFERENCE_DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EModelPositionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_MODEL_POSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getObjectID() {
		return objectID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectID(String newObjectID) {
		String oldObjectID = objectID;
		objectID = newObjectID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_MODEL_POSITION__OBJECT_ID, oldObjectID, objectID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDifferenceDescription() {
		return differenceDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDifferenceDescription(String newDifferenceDescription) {
		String oldDifferenceDescription = differenceDescription;
		differenceDescription = newDifferenceDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_MODEL_POSITION__DIFFERENCE_DESCRIPTION, oldDifferenceDescription, differenceDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_MODEL_POSITION__OBJECT_ID:
				return getObjectID();
			case RModelPackage.R4E_MODEL_POSITION__DIFFERENCE_DESCRIPTION:
				return getDifferenceDescription();
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
			case RModelPackage.R4E_MODEL_POSITION__OBJECT_ID:
				setObjectID((String)newValue);
				return;
			case RModelPackage.R4E_MODEL_POSITION__DIFFERENCE_DESCRIPTION:
				setDifferenceDescription((String)newValue);
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
			case RModelPackage.R4E_MODEL_POSITION__OBJECT_ID:
				setObjectID(OBJECT_ID_EDEFAULT);
				return;
			case RModelPackage.R4E_MODEL_POSITION__DIFFERENCE_DESCRIPTION:
				setDifferenceDescription(DIFFERENCE_DESCRIPTION_EDEFAULT);
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
			case RModelPackage.R4E_MODEL_POSITION__OBJECT_ID:
				return OBJECT_ID_EDEFAULT == null ? objectID != null : !OBJECT_ID_EDEFAULT.equals(objectID);
			case RModelPackage.R4E_MODEL_POSITION__DIFFERENCE_DESCRIPTION:
				return DIFFERENCE_DESCRIPTION_EDEFAULT == null ? differenceDescription != null : !DIFFERENCE_DESCRIPTION_EDEFAULT.equals(differenceDescription);
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
		result.append(" (objectID: ");
		result.append(objectID);
		result.append(", differenceDescription: ");
		result.append(differenceDescription);
		result.append(')');
		return result.toString();
	}

} //R4EModelPositionImpl
