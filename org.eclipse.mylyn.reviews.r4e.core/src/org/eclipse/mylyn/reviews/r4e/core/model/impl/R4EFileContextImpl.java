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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E File Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl#getDeltas <em>Deltas</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl#getBase <em>Base</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EFileContextImpl extends R4EReviewComponentImpl implements R4EFileContext {
	/**
	 * The cached value of the '{@link #getDeltas() <em>Deltas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeltas()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EDelta> deltas;
	/**
	 * The cached value of the '{@link #getBase() <em>Base</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBase()
	 * @generated
	 * @ordered
	 */
	protected R4EFileVersion base;
	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected R4EFileVersion target;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EFileContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_FILE_CONTEXT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<R4EDelta> getDeltas() {
		if (deltas == null) {
			deltas = new EObjectContainmentEList.Resolving<R4EDelta>(R4EDelta.class, this, RModelPackage.R4E_FILE_CONTEXT__DELTAS);
		}
		return deltas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion getBase() {
		if (base != null && base.eIsProxy()) {
			InternalEObject oldBase = (InternalEObject)base;
			base = (R4EFileVersion)eResolveProxy(oldBase);
			if (base != oldBase) {
				InternalEObject newBase = (InternalEObject)base;
				NotificationChain msgs = oldBase.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__BASE, null, null);
				if (newBase.eInternalContainer() == null) {
					msgs = newBase.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__BASE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_FILE_CONTEXT__BASE, oldBase, base));
			}
		}
		return base;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion basicGetBase() {
		return base;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBase(R4EFileVersion newBase, NotificationChain msgs) {
		R4EFileVersion oldBase = base;
		base = newBase;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FILE_CONTEXT__BASE, oldBase, newBase);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBase(R4EFileVersion newBase) {
		if (newBase != base) {
			NotificationChain msgs = null;
			if (base != null)
				msgs = ((InternalEObject)base).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__BASE, null, msgs);
			if (newBase != null)
				msgs = ((InternalEObject)newBase).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__BASE, null, msgs);
			msgs = basicSetBase(newBase, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FILE_CONTEXT__BASE, newBase, newBase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (R4EFileVersion)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				InternalEObject newTarget = (InternalEObject)target;
				NotificationChain msgs = oldTarget.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__TARGET, null, null);
				if (newTarget.eInternalContainer() == null) {
					msgs = newTarget.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__TARGET, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_FILE_CONTEXT__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EFileVersion basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(R4EFileVersion newTarget, NotificationChain msgs) {
		R4EFileVersion oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FILE_CONTEXT__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(R4EFileVersion newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_FILE_CONTEXT__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FILE_CONTEXT__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_FILE_CONTEXT__DELTAS:
				return ((InternalEList<?>)getDeltas()).basicRemove(otherEnd, msgs);
			case RModelPackage.R4E_FILE_CONTEXT__BASE:
				return basicSetBase(null, msgs);
			case RModelPackage.R4E_FILE_CONTEXT__TARGET:
				return basicSetTarget(null, msgs);
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
			case RModelPackage.R4E_FILE_CONTEXT__DELTAS:
				return getDeltas();
			case RModelPackage.R4E_FILE_CONTEXT__BASE:
				if (resolve) return getBase();
				return basicGetBase();
			case RModelPackage.R4E_FILE_CONTEXT__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
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
			case RModelPackage.R4E_FILE_CONTEXT__DELTAS:
				getDeltas().clear();
				getDeltas().addAll((Collection<? extends R4EDelta>)newValue);
				return;
			case RModelPackage.R4E_FILE_CONTEXT__BASE:
				setBase((R4EFileVersion)newValue);
				return;
			case RModelPackage.R4E_FILE_CONTEXT__TARGET:
				setTarget((R4EFileVersion)newValue);
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
			case RModelPackage.R4E_FILE_CONTEXT__DELTAS:
				getDeltas().clear();
				return;
			case RModelPackage.R4E_FILE_CONTEXT__BASE:
				setBase((R4EFileVersion)null);
				return;
			case RModelPackage.R4E_FILE_CONTEXT__TARGET:
				setTarget((R4EFileVersion)null);
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
			case RModelPackage.R4E_FILE_CONTEXT__DELTAS:
				return deltas != null && !deltas.isEmpty();
			case RModelPackage.R4E_FILE_CONTEXT__BASE:
				return base != null;
			case RModelPackage.R4E_FILE_CONTEXT__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //R4EFileContextImpl
