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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.internal.core.model.Comment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>R4E Comment</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl#getAssignedTo <em>Assigned To</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl#getR4eId <em>R4e Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl#getCreatedOn <em>Created On</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl#getAnomaly <em>Anomaly</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl#getInfoAtt <em>Info Att</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4ECommentImpl extends Comment implements R4EComment {
	/**
	 * The cached value of the '{@link #getAssignedTo() <em>Assigned To</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssignedTo()
	 * @generated
	 * @ordered
	 */
	protected EList<String> assignedTo;

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
	 * The default value of the '{@link #getCreatedOn() <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getCreatedOn()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATED_ON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreatedOn() <em>Created On</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getCreatedOn()
	 * @generated
	 * @ordered
	 */
	protected Date createdOn = CREATED_ON_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnomaly() <em>Anomaly</em>}' reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getAnomaly()
	 * @generated
	 * @ordered
	 */
	protected R4EAnomaly anomaly;

	/**
	 * The cached value of the '{@link #getInfoAtt() <em>Info Att</em>}' map. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getInfoAtt()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> infoAtt;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected R4ECommentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_COMMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getAssignedTo() {
		if (assignedTo == null) {
			assignedTo = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_COMMENT__ASSIGNED_TO);
		}
		return assignedTo;
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
				NotificationChain msgs = oldR4eId.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_COMMENT__R4E_ID, null, null);
				if (newR4eId.eInternalContainer() == null) {
					msgs = newR4eId.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_COMMENT__R4E_ID, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_COMMENT__R4E_ID, oldR4eId, r4eId));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_COMMENT__R4E_ID, oldR4eId, newR4eId);
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
				msgs = ((InternalEObject)r4eId).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_COMMENT__R4E_ID, null, msgs);
			if (newR4eId != null)
				msgs = ((InternalEObject)newR4eId).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RModelPackage.R4E_COMMENT__R4E_ID, null, msgs);
			msgs = basicSetR4eId(newR4eId, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_COMMENT__R4E_ID, newR4eId, newR4eId));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreatedOn(Date newCreatedOn) {
		Date oldCreatedOn = createdOn;
		createdOn = newCreatedOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_COMMENT__CREATED_ON, oldCreatedOn, createdOn));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EAnomaly getAnomaly() {
		if (anomaly != null && anomaly.eIsProxy()) {
			InternalEObject oldAnomaly = (InternalEObject)anomaly;
			anomaly = (R4EAnomaly)eResolveProxy(oldAnomaly);
			if (anomaly != oldAnomaly) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_COMMENT__ANOMALY, oldAnomaly, anomaly));
			}
		}
		return anomaly;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EAnomaly basicGetAnomaly() {
		return anomaly;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setAnomaly(R4EAnomaly newAnomaly) {
		R4EAnomaly oldAnomaly = anomaly;
		anomaly = newAnomaly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_COMMENT__ANOMALY, oldAnomaly, anomaly));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Map<String, String> getInfoAtt() {
		if (infoAtt == null) {
			infoAtt = new EcoreEMap<String,String>(RModelPackage.Literals.MAP_KEY_TO_INFO_ATTRIBUTES, MapKeyToInfoAttributesImpl.class, this, RModelPackage.R4E_COMMENT__INFO_ATT);
		}
		return infoAtt.map();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_COMMENT__R4E_ID:
				return basicSetR4eId(null, msgs);
			case RModelPackage.R4E_COMMENT__INFO_ATT:
				return ((InternalEList<?>)((EMap.InternalMapView<String, String>)getInfoAtt()).eMap()).basicRemove(otherEnd, msgs);
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
			case RModelPackage.R4E_COMMENT__ASSIGNED_TO:
				return getAssignedTo();
			case RModelPackage.R4E_COMMENT__R4E_ID:
				if (resolve) return getR4eId();
				return basicGetR4eId();
			case RModelPackage.R4E_COMMENT__CREATED_ON:
				return getCreatedOn();
			case RModelPackage.R4E_COMMENT__ANOMALY:
				if (resolve) return getAnomaly();
				return basicGetAnomaly();
			case RModelPackage.R4E_COMMENT__INFO_ATT:
				if (coreType) return ((EMap.InternalMapView<String, String>)getInfoAtt()).eMap();
				else return getInfoAtt();
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
			case RModelPackage.R4E_COMMENT__ASSIGNED_TO:
				getAssignedTo().clear();
				getAssignedTo().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_COMMENT__R4E_ID:
				setR4eId((R4EID)newValue);
				return;
			case RModelPackage.R4E_COMMENT__CREATED_ON:
				setCreatedOn((Date)newValue);
				return;
			case RModelPackage.R4E_COMMENT__ANOMALY:
				setAnomaly((R4EAnomaly)newValue);
				return;
			case RModelPackage.R4E_COMMENT__INFO_ATT:
				((EStructuralFeature.Setting)((EMap.InternalMapView<String, String>)getInfoAtt()).eMap()).set(newValue);
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
			case RModelPackage.R4E_COMMENT__ASSIGNED_TO:
				getAssignedTo().clear();
				return;
			case RModelPackage.R4E_COMMENT__R4E_ID:
				setR4eId((R4EID)null);
				return;
			case RModelPackage.R4E_COMMENT__CREATED_ON:
				setCreatedOn(CREATED_ON_EDEFAULT);
				return;
			case RModelPackage.R4E_COMMENT__ANOMALY:
				setAnomaly((R4EAnomaly)null);
				return;
			case RModelPackage.R4E_COMMENT__INFO_ATT:
				getInfoAtt().clear();
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
			case RModelPackage.R4E_COMMENT__ASSIGNED_TO:
				return assignedTo != null && !assignedTo.isEmpty();
			case RModelPackage.R4E_COMMENT__R4E_ID:
				return r4eId != null;
			case RModelPackage.R4E_COMMENT__CREATED_ON:
				return CREATED_ON_EDEFAULT == null ? createdOn != null : !CREATED_ON_EDEFAULT.equals(createdOn);
			case RModelPackage.R4E_COMMENT__ANOMALY:
				return anomaly != null;
			case RModelPackage.R4E_COMMENT__INFO_ATT:
				return infoAtt != null && !infoAtt.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == R4EReviewComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_COMMENT__ASSIGNED_TO: return RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO;
				default: return -1;
			}
		}
		if (baseClass == R4EIDComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_COMMENT__R4E_ID: return RModelPackage.R4EID_COMPONENT__R4E_ID;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == R4EReviewComponent.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO: return RModelPackage.R4E_COMMENT__ASSIGNED_TO;
				default: return -1;
			}
		}
		if (baseClass == R4EIDComponent.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4EID_COMPONENT__R4E_ID: return RModelPackage.R4E_COMMENT__R4E_ID;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (assignedTo: ");
		result.append(assignedTo);
		result.append(", createdOn: ");
		result.append(createdOn);
		result.append(')');
		return result.toString();
	}

} //R4ECommentImpl
