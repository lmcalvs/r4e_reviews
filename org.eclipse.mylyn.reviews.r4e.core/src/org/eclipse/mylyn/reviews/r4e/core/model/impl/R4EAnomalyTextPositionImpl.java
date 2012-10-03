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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Anomaly Text Position</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTextPositionImpl#getFile <em>File</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EAnomalyTextPositionImpl extends R4ETextPositionImpl implements R4EAnomalyTextPosition {
	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected R4EFileVersion file;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EAnomalyTextPositionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_ANOMALY_TEXT_POSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion getFile() {
		if (file != null && file.eIsProxy()) {
			InternalEObject oldFile = (InternalEObject)file;
			file = (R4EFileVersion)eResolveProxy(oldFile);
			if (file != oldFile) {
				InternalEObject newFile = (InternalEObject)file;
				NotificationChain msgs = oldFile.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, null, null);
				if (newFile.eInternalContainer() == null) {
					msgs = newFile.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, oldFile, file));
			}
		}
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion basicGetFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFile(R4EFileVersion newFile, NotificationChain msgs) {
		R4EFileVersion oldFile = file;
		file = newFile;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, oldFile, newFile);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFile(R4EFileVersion newFile) {
		if (newFile != file) {
			NotificationChain msgs = null;
			if (file != null)
				msgs = ((InternalEObject)file).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, null, msgs);
			if (newFile != null)
				msgs = ((InternalEObject)newFile).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, null, msgs);
			msgs = basicSetFile(newFile, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE, newFile, newFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE:
				return basicSetFile(null, msgs);
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
			case RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE:
				if (resolve) return getFile();
				return basicGetFile();
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
			case RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE:
				setFile((R4EFileVersion)newValue);
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
			case RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE:
				setFile((R4EFileVersion)null);
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
			case RModelPackage.R4E_ANOMALY_TEXT_POSITION__FILE:
				return file != null;
		}
		return super.eIsSet(featureID);
	}

} //R4EAnomalyTextPositionImpl
