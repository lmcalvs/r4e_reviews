/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Review Group Res</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewGroupResImpl#getReviewsRes <em>
 * Reviews Res</em>}</li>
 * <li>{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewGroupResImpl#getFilesPrefix <em>
 * Files Prefix</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ReviewGroupResImpl extends R4EReviewGroupImpl implements ReviewGroupRes {
	/**
	 * The cached value of the '{@link #getReviewsRes() <em>Reviews Res</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getReviewsRes()
	 * @generated
	 * @ordered
	 */
	protected EList<ReviewRes> reviewsRes;

	/**
	 * The default value of the '{@link #getFilesPrefix() <em>Files Prefix</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFilesPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String FILES_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilesPrefix() <em>Files Prefix</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getFilesPrefix()
	 * @generated
	 * @ordered
	 */
	protected String filesPrefix = FILES_PREFIX_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ReviewGroupResImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TransResPackage.Literals.REVIEW_GROUP_RES;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<ReviewRes> getReviewsRes() {
		if (reviewsRes == null) {
			reviewsRes = new EObjectContainmentEList.Resolving<ReviewRes>(ReviewRes.class, this,
					TransResPackage.REVIEW_GROUP_RES__REVIEWS_RES);
		}
		return reviewsRes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFilesPrefix() {
		return filesPrefix;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFilesPrefix(String newFilesPrefix) {
		String oldFilesPrefix = filesPrefix;
		filesPrefix = newFilesPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TransResPackage.REVIEW_GROUP_RES__FILES_PREFIX,
					oldFilesPrefix, filesPrefix));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case TransResPackage.REVIEW_GROUP_RES__REVIEWS_RES:
			return ((InternalEList<?>) getReviewsRes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TransResPackage.REVIEW_GROUP_RES__REVIEWS_RES:
			return getReviewsRes();
		case TransResPackage.REVIEW_GROUP_RES__FILES_PREFIX:
			return getFilesPrefix();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case TransResPackage.REVIEW_GROUP_RES__REVIEWS_RES:
			getReviewsRes().clear();
			getReviewsRes().addAll((Collection<? extends ReviewRes>) newValue);
			return;
		case TransResPackage.REVIEW_GROUP_RES__FILES_PREFIX:
			setFilesPrefix((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case TransResPackage.REVIEW_GROUP_RES__REVIEWS_RES:
			getReviewsRes().clear();
			return;
		case TransResPackage.REVIEW_GROUP_RES__FILES_PREFIX:
			setFilesPrefix(FILES_PREFIX_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case TransResPackage.REVIEW_GROUP_RES__REVIEWS_RES:
			return reviewsRes != null && !reviewsRes.isEmpty();
		case TransResPackage.REVIEW_GROUP_RES__FILES_PREFIX:
			return FILES_PREFIX_EDEFAULT == null ? filesPrefix != null : !FILES_PREFIX_EDEFAULT.equals(filesPrefix);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (filesPrefix: ");
		result.append(filesPrefix);
		result.append(')');
		return result.toString();
	}

} //ReviewGroupResImpl
