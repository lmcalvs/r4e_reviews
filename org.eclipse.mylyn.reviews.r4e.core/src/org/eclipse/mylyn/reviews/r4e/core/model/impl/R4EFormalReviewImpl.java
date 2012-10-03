/**
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
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>R4E Formal Review</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getPhaseOwner <em>Phase Owner</em>}</li>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getPhases <em>Phases</em>}</li>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl#getCurrent <em>Current</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class R4EFormalReviewImpl extends R4EReviewImpl implements R4EFormalReview {
	/**
	 * The cached value of the '{@link #getPhaseOwner() <em>Phase Owner</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getPhaseOwner()
	 * @generated
	 * @ordered
	 */
	protected R4EParticipant phaseOwner;

	/**
	 * The cached value of the '{@link #getPhases() <em>Phases</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getPhases()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EReviewPhaseInfo> phases;

	/**
	 * The cached value of the '{@link #getCurrent() <em>Current</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getCurrent()
	 * @generated
	 * @ordered
	 */
	protected R4EReviewPhaseInfo current;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EFormalReviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_FORMAL_REVIEW;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EParticipant basicGetPhaseOwner() {
		return phaseOwner;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPhaseOwner(R4EParticipant newPhaseOwner) {
		R4EParticipant oldPhaseOwner = phaseOwner;
		phaseOwner = newPhaseOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER, oldPhaseOwner, phaseOwner));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<R4EReviewPhaseInfo> getPhases() {
		if (phases == null) {
			phases = new EObjectContainmentEList.Resolving<R4EReviewPhaseInfo>(R4EReviewPhaseInfo.class, this, RModelPackage.R4E_FORMAL_REVIEW__PHASES);
		}
		return phases;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EReviewPhaseInfo getCurrent() {
		if (current != null && current.eIsProxy()) {
			InternalEObject oldCurrent = (InternalEObject)current;
			current = (R4EReviewPhaseInfo)eResolveProxy(oldCurrent);
			if (current != oldCurrent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_FORMAL_REVIEW__CURRENT, oldCurrent, current));
			}
		}
		return current;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EReviewPhaseInfo basicGetCurrent() {
		return current;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCurrent(R4EReviewPhaseInfo newCurrent) {
		R4EReviewPhaseInfo oldCurrent = current;
		current = newCurrent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_FORMAL_REVIEW__CURRENT, oldCurrent, current));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_FORMAL_REVIEW__PHASES:
				return ((InternalEList<?>)getPhases()).basicRemove(otherEnd, msgs);
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
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				if (resolve) return getPhaseOwner();
				return basicGetPhaseOwner();
			case RModelPackage.R4E_FORMAL_REVIEW__PHASES:
				return getPhases();
			case RModelPackage.R4E_FORMAL_REVIEW__CURRENT:
				if (resolve) return getCurrent();
				return basicGetCurrent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				setPhaseOwner((R4EParticipant)newValue);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASES:
				getPhases().clear();
				getPhases().addAll((Collection<? extends R4EReviewPhaseInfo>)newValue);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__CURRENT:
				setCurrent((R4EReviewPhaseInfo)newValue);
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
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				setPhaseOwner((R4EParticipant)null);
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASES:
				getPhases().clear();
				return;
			case RModelPackage.R4E_FORMAL_REVIEW__CURRENT:
				setCurrent((R4EReviewPhaseInfo)null);
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
			case RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER:
				return phaseOwner != null;
			case RModelPackage.R4E_FORMAL_REVIEW__PHASES:
				return phases != null && !phases.isEmpty();
			case RModelPackage.R4E_FORMAL_REVIEW__CURRENT:
				return current != null;
		}
		return super.eIsSet(featureID);
	}

} //R4EFormalReviewImpl
