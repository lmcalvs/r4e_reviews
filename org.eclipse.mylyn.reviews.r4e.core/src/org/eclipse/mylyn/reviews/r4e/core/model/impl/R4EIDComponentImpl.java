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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>R4EID Component</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDComponentImpl#getR4eId <em>R4e Id</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class R4EIDComponentImpl extends R4EReviewComponentImpl implements R4EIDComponent {
	/**
	 * The cached value of the '{@link #getR4eId() <em>R4e Id</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getR4eId()
	 * @generated
	 * @ordered
	 */
	protected R4EID r4eId;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EIDComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4EID_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EID getR4eId() {
		if (r4eId != null && r4eId.eIsProxy()) {
			InternalEObject oldR4eId = (InternalEObject)r4eId;
			r4eId = (R4EID)eResolveProxy(oldR4eId);
			if (r4eId != oldR4eId) {
				InternalEObject newR4eId = (InternalEObject)r4eId;
				NotificationChain msgs = oldR4eId.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4EID_COMPONENT__R4E_ID, null, null);
				if (newR4eId.eInternalContainer() == null) {
					msgs = newR4eId.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4EID_COMPONENT__R4E_ID, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4EID_COMPONENT__R4E_ID, oldR4eId, r4eId));
			}
		}
		return r4eId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EID basicGetR4eId() {
		return r4eId;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetR4eId(R4EID newR4eId, NotificationChain msgs) {
		R4EID oldR4eId = r4eId;
		r4eId = newR4eId;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RModelPackage.R4EID_COMPONENT__R4E_ID, oldR4eId, newR4eId);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setR4eId(R4EID newR4eId) {
		if (newR4eId != r4eId) {
			NotificationChain msgs = null;
			if (r4eId != null)
				msgs = ((InternalEObject)r4eId).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4EID_COMPONENT__R4E_ID, null, msgs);
			if (newR4eId != null)
				msgs = ((InternalEObject)newR4eId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4EID_COMPONENT__R4E_ID, null, msgs);
			msgs = basicSetR4eId(newR4eId, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4EID_COMPONENT__R4E_ID, newR4eId, newR4eId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4EID_COMPONENT__R4E_ID:
				return basicSetR4eId(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4EID_COMPONENT__R4E_ID:
				if (resolve) return getR4eId();
				return basicGetR4eId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RModelPackage.R4EID_COMPONENT__R4E_ID:
				setR4eId((R4EID)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RModelPackage.R4EID_COMPONENT__R4E_ID:
				setR4eId((R4EID)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RModelPackage.R4EID_COMPONENT__R4E_ID:
				return r4eId != null;
		}
		return super.eIsSet(featureID);
	}

} //R4EIDComponentImpl
