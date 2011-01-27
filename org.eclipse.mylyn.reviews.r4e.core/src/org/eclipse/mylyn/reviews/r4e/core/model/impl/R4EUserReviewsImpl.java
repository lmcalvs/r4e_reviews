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
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E User Reviews</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl#getInvitedToMap <em>Invited To Map</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl#getCreatedReviews <em>Created Reviews</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl#getXmlVersion <em>Xml Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EUserReviewsImpl extends EObjectImpl implements R4EUserReviews {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInvitedToMap() <em>Invited To Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInvitedToMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, R4EReview> invitedToMap;

	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected R4EReviewGroup group;

	/**
	 * The cached value of the '{@link #getCreatedReviews() <em>Created Reviews</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreatedReviews()
	 * @generated
	 * @ordered
	 */
	protected EList<String> createdReviews;

	/**
	 * The default value of the '{@link #getXmlVersion() <em>Xml Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_VERSION_EDEFAULT = "1.0.0";

	/**
	 * The cached value of the '{@link #getXmlVersion() <em>Xml Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlVersion()
	 * @generated
	 * @ordered
	 */
	protected String xmlVersion = XML_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EUserReviewsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_USER_REVIEWS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER_REVIEWS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, R4EReview> getInvitedToMap() {
		if (invitedToMap == null) {
			invitedToMap = new EcoreEMap<String,R4EReview>(RModelPackage.Literals.MAP_NAME_TO_REVIEW, MapNameToReviewImpl.class, this, RModelPackage.R4E_USER_REVIEWS__INVITED_TO_MAP);
		}
		return invitedToMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EReviewGroup getGroup() {
		if (group != null && group.eIsProxy()) {
			InternalEObject oldGroup = (InternalEObject)group;
			group = (R4EReviewGroup)eResolveProxy(oldGroup);
			if (group != oldGroup) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_USER_REVIEWS__GROUP, oldGroup, group));
			}
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EReviewGroup basicGetGroup() {
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroup(R4EReviewGroup newGroup) {
		R4EReviewGroup oldGroup = group;
		group = newGroup;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER_REVIEWS__GROUP, oldGroup, group));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getCreatedReviews() {
		if (createdReviews == null) {
			createdReviews = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_USER_REVIEWS__CREATED_REVIEWS);
		}
		return createdReviews;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getXmlVersion() {
		return xmlVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXmlVersion(String newXmlVersion) {
		String oldXmlVersion = xmlVersion;
		xmlVersion = newXmlVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER_REVIEWS__XML_VERSION, oldXmlVersion, xmlVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_USER_REVIEWS__INVITED_TO_MAP:
				return ((InternalEList<?>)getInvitedToMap()).basicRemove(otherEnd, msgs);
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
			case RModelPackage.R4E_USER_REVIEWS__NAME:
				return getName();
			case RModelPackage.R4E_USER_REVIEWS__INVITED_TO_MAP:
				if (coreType) return getInvitedToMap();
				else return getInvitedToMap().map();
			case RModelPackage.R4E_USER_REVIEWS__GROUP:
				if (resolve) return getGroup();
				return basicGetGroup();
			case RModelPackage.R4E_USER_REVIEWS__CREATED_REVIEWS:
				return getCreatedReviews();
			case RModelPackage.R4E_USER_REVIEWS__XML_VERSION:
				return getXmlVersion();
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
			case RModelPackage.R4E_USER_REVIEWS__NAME:
				setName((String)newValue);
				return;
			case RModelPackage.R4E_USER_REVIEWS__INVITED_TO_MAP:
				((EStructuralFeature.Setting)getInvitedToMap()).set(newValue);
				return;
			case RModelPackage.R4E_USER_REVIEWS__GROUP:
				setGroup((R4EReviewGroup)newValue);
				return;
			case RModelPackage.R4E_USER_REVIEWS__CREATED_REVIEWS:
				getCreatedReviews().clear();
				getCreatedReviews().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_USER_REVIEWS__XML_VERSION:
				setXmlVersion((String)newValue);
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
			case RModelPackage.R4E_USER_REVIEWS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RModelPackage.R4E_USER_REVIEWS__INVITED_TO_MAP:
				getInvitedToMap().clear();
				return;
			case RModelPackage.R4E_USER_REVIEWS__GROUP:
				setGroup((R4EReviewGroup)null);
				return;
			case RModelPackage.R4E_USER_REVIEWS__CREATED_REVIEWS:
				getCreatedReviews().clear();
				return;
			case RModelPackage.R4E_USER_REVIEWS__XML_VERSION:
				setXmlVersion(XML_VERSION_EDEFAULT);
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
			case RModelPackage.R4E_USER_REVIEWS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RModelPackage.R4E_USER_REVIEWS__INVITED_TO_MAP:
				return invitedToMap != null && !invitedToMap.isEmpty();
			case RModelPackage.R4E_USER_REVIEWS__GROUP:
				return group != null;
			case RModelPackage.R4E_USER_REVIEWS__CREATED_REVIEWS:
				return createdReviews != null && !createdReviews.isEmpty();
			case RModelPackage.R4E_USER_REVIEWS__XML_VERSION:
				return XML_VERSION_EDEFAULT == null ? xmlVersion != null : !XML_VERSION_EDEFAULT.equals(xmlVersion);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", createdReviews: ");
		result.append(createdReviews);
		result.append(", xmlVersion: ");
		result.append(xmlVersion);
		result.append(')');
		return result.toString();
	}

} //R4EUserReviewsImpl
