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
 *    Alvaro Sanchez-Leon - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.drules.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Design Rule Collection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getAreas <em>Areas</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getFilePaths <em>File Paths</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EDesignRuleCollectionImpl extends EObjectImpl implements R4EDesignRuleCollection {
	/**
	 * The cached value of the '{@link #getAreas() <em>Areas</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAreas()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EDesignRuleArea> areas;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFilePaths() <em>File Paths</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFilePaths()
	 * @generated
	 * @ordered
	 */
	protected EList<String> filePaths;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EDesignRuleCollectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DRModelPackage.Literals.R4E_DESIGN_RULE_COLLECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<R4EDesignRuleArea> getAreas() {
		if (areas == null) {
			areas = new EObjectContainmentEList<R4EDesignRuleArea>(R4EDesignRuleArea.class, this, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS);
		}
		return areas;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getFilePaths() {
		if (filePaths == null) {
			filePaths = new EDataTypeUniqueEList<String>(String.class, this, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FILE_PATHS);
		}
		return filePaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				return ((InternalEList<?>)getAreas()).basicRemove(otherEnd, msgs);
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				return getAreas();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				return getVersion();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FILE_PATHS:
				return getFilePaths();
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				getAreas().clear();
				getAreas().addAll((Collection<? extends R4EDesignRuleArea>)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				setVersion((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FILE_PATHS:
				getFilePaths().clear();
				getFilePaths().addAll((Collection<? extends String>)newValue);
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				getAreas().clear();
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FILE_PATHS:
				getFilePaths().clear();
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				return areas != null && !areas.isEmpty();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FILE_PATHS:
				return filePaths != null && !filePaths.isEmpty();
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
		result.append(" (version: ");
		result.append(version);
		result.append(", filePaths: ");
		result.append(filePaths);
		result.append(')');
		return result.toString();
	}

} //R4EDesignRuleCollectionImpl
