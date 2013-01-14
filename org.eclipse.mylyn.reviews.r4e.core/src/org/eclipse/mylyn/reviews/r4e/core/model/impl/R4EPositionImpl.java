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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>R4E Position</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EPositionImpl#getAnomalyFile <em>Anomaly File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EPositionImpl extends EObjectImpl implements R4EPosition {
	/**
	 * The cached value of the '{@link #getAnomalyFile() <em>Anomaly File</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnomalyFile()
	 * @generated
	 * @ordered
	 */
	protected R4EFileVersion anomalyFile;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EPositionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_POSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion getAnomalyFile() {
		if (anomalyFile != null && anomalyFile.eIsProxy()) {
			InternalEObject oldAnomalyFile = (InternalEObject)anomalyFile;
			anomalyFile = (R4EFileVersion)eResolveProxy(oldAnomalyFile);
			if (anomalyFile != oldAnomalyFile) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_POSITION__ANOMALY_FILE, oldAnomalyFile, anomalyFile));
			}
		}
		return anomalyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion basicGetAnomalyFile() {
		return anomalyFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnomalyFile(R4EFileVersion newAnomalyFile) {
		R4EFileVersion oldAnomalyFile = anomalyFile;
		anomalyFile = newAnomalyFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_POSITION__ANOMALY_FILE, oldAnomalyFile, anomalyFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_POSITION__ANOMALY_FILE:
				if (resolve) return getAnomalyFile();
				return basicGetAnomalyFile();
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
			case RModelPackage.R4E_POSITION__ANOMALY_FILE:
				setAnomalyFile((R4EFileVersion)newValue);
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
			case RModelPackage.R4E_POSITION__ANOMALY_FILE:
				setAnomalyFile((R4EFileVersion)null);
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
			case RModelPackage.R4E_POSITION__ANOMALY_FILE:
				return anomalyFile != null;
		}
		return super.eIsSet(featureID);
	}

} //R4EPositionImpl
