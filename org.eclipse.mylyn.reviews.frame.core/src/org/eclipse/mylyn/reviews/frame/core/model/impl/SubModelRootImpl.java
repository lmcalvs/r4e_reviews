/*******************************************************************************
 * Copyright (c) 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.frame.core.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sub Model Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl#getFragmentVersion <em>Fragment Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl#getCompatibility <em>Compatibility</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl#getApplicationVersion <em>Application Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubModelRootImpl extends EObjectImpl implements SubModelRoot {
	/**
	 * The default value of the '{@link #getFragmentVersion() <em>Fragment Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragmentVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String FRAGMENT_VERSION_EDEFAULT = "0.8.0";

	/**
	 * The cached value of the '{@link #getFragmentVersion() <em>Fragment Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFragmentVersion()
	 * @generated
	 * @ordered
	 */
	protected String fragmentVersion = FRAGMENT_VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getCompatibility() <em>Compatibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompatibility()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPATIBILITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCompatibility() <em>Compatibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompatibility()
	 * @generated
	 * @ordered
	 */
	protected int compatibility = COMPATIBILITY_EDEFAULT;

	/**
	 * This is true if the Compatibility attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean compatibilityESet;

	/**
	 * The default value of the '{@link #getApplicationVersion() <em>Application Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplicationVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String APPLICATION_VERSION_EDEFAULT = "1.0.0";

	/**
	 * The cached value of the '{@link #getApplicationVersion() <em>Application Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getApplicationVersion()
	 * @generated
	 * @ordered
	 */
	protected String applicationVersion = APPLICATION_VERSION_EDEFAULT;

	/**
	 * This is true if the Application Version attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean applicationVersionESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubModelRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SUB_MODEL_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFragmentVersion() {
		return fragmentVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFragmentVersion(String newFragmentVersion) {
		String oldFragmentVersion = fragmentVersion;
		fragmentVersion = newFragmentVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION, oldFragmentVersion, fragmentVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCompatibility() {
		return compatibility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompatibility(int newCompatibility) {
		int oldCompatibility = compatibility;
		compatibility = newCompatibility;
		boolean oldCompatibilityESet = compatibilityESet;
		compatibilityESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY, oldCompatibility, compatibility, !oldCompatibilityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCompatibility() {
		int oldCompatibility = compatibility;
		boolean oldCompatibilityESet = compatibilityESet;
		compatibility = COMPATIBILITY_EDEFAULT;
		compatibilityESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY, oldCompatibility, COMPATIBILITY_EDEFAULT, oldCompatibilityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCompatibility() {
		return compatibilityESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getApplicationVersion() {
		return applicationVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setApplicationVersion(String newApplicationVersion) {
		String oldApplicationVersion = applicationVersion;
		applicationVersion = newApplicationVersion;
		boolean oldApplicationVersionESet = applicationVersionESet;
		applicationVersionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION, oldApplicationVersion, applicationVersion, !oldApplicationVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetApplicationVersion() {
		String oldApplicationVersion = applicationVersion;
		boolean oldApplicationVersionESet = applicationVersionESet;
		applicationVersion = APPLICATION_VERSION_EDEFAULT;
		applicationVersionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION, oldApplicationVersion, APPLICATION_VERSION_EDEFAULT, oldApplicationVersionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetApplicationVersion() {
		return applicationVersionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION:
				return getFragmentVersion();
			case ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY:
				return getCompatibility();
			case ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION:
				return getApplicationVersion();
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
			case ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION:
				setFragmentVersion((String)newValue);
				return;
			case ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY:
				setCompatibility((Integer)newValue);
				return;
			case ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION:
				setApplicationVersion((String)newValue);
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
			case ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION:
				setFragmentVersion(FRAGMENT_VERSION_EDEFAULT);
				return;
			case ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY:
				unsetCompatibility();
				return;
			case ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION:
				unsetApplicationVersion();
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
			case ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION:
				return FRAGMENT_VERSION_EDEFAULT == null ? fragmentVersion != null : !FRAGMENT_VERSION_EDEFAULT.equals(fragmentVersion);
			case ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY:
				return isSetCompatibility();
			case ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION:
				return isSetApplicationVersion();
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
		result.append(" (fragmentVersion: ");
		result.append(fragmentVersion);
		result.append(", compatibility: ");
		if (compatibilityESet) result.append(compatibility); else result.append("<unset>");
		result.append(", applicationVersion: ");
		if (applicationVersionESet) result.append(applicationVersion); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SubModelRootImpl
