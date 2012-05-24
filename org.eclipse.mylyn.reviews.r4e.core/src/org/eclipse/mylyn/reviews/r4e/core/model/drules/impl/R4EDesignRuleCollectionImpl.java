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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot;
import org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewComponentImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.Roots;
import org.eclipse.mylyn.reviews.r4e.core.utils.VersionUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Design Rule Collection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getFragmentVersion <em>Fragment Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getCompatibility <em>Compatibility</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getApplicationVersion <em>Application Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getAreas <em>Areas</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EDesignRuleCollectionImpl extends ReviewComponentImpl implements R4EDesignRuleCollection {
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
	 * The default value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected String folder = FOLDER_EDEFAULT;

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
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION, oldFragmentVersion, fragmentVersion));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility()
	 */
	public int getCompatibility() {
		return VersionUtils.compareVersions(Roots.RULESET.getVersion(), fragmentVersion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion()
	 */
	public String getApplicationVersion() {
		return Persistence.Roots.RULESET.getVersion();
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
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY, oldCompatibility, compatibility, !oldCompatibilityESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY, oldCompatibility, COMPATIBILITY_EDEFAULT, oldCompatibilityESet));
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
	public void setApplicationVersion(String newApplicationVersion) {
		String oldApplicationVersion = applicationVersion;
		applicationVersion = newApplicationVersion;
		boolean oldApplicationVersionESet = applicationVersionESet;
		applicationVersionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION, oldApplicationVersion, applicationVersion, !oldApplicationVersionESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION, oldApplicationVersion, APPLICATION_VERSION_EDEFAULT, oldApplicationVersionESet));
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
	public EList<R4EDesignRuleArea> getAreas() {
		if (areas == null) {
			areas = new EObjectContainmentEList.Resolving<R4EDesignRuleArea>(R4EDesignRuleArea.class, this, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS);
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
	public String getFolder() {
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFolder(String newFolder) {
		String oldFolder = folder;
		folder = newFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FOLDER, oldFolder, folder));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE_COLLECTION__NAME, oldName, name));
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION:
				return getFragmentVersion();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY:
				return getCompatibility();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION:
				return getApplicationVersion();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				return getAreas();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				return getVersion();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FOLDER:
				return getFolder();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__NAME:
				return getName();
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION:
				setFragmentVersion((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY:
				setCompatibility((Integer)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION:
				setApplicationVersion((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				getAreas().clear();
				getAreas().addAll((Collection<? extends R4EDesignRuleArea>)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				setVersion((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FOLDER:
				setFolder((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__NAME:
				setName((String)newValue);
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION:
				setFragmentVersion(FRAGMENT_VERSION_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY:
				unsetCompatibility();
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION:
				unsetApplicationVersion();
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				getAreas().clear();
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__NAME:
				setName(NAME_EDEFAULT);
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
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION:
				return FRAGMENT_VERSION_EDEFAULT == null ? fragmentVersion != null : !FRAGMENT_VERSION_EDEFAULT.equals(fragmentVersion);
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY:
				return isSetCompatibility();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION:
				return isSetApplicationVersion();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__AREAS:
				return areas != null && !areas.isEmpty();
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == SubModelRoot.class) {
			switch (derivedFeatureID) {
				case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION: return ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION;
				case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY: return ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY;
				case DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION: return ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == SubModelRoot.class) {
			switch (baseFeatureID) {
				case ModelPackage.SUB_MODEL_ROOT__FRAGMENT_VERSION: return DRModelPackage.R4E_DESIGN_RULE_COLLECTION__FRAGMENT_VERSION;
				case ModelPackage.SUB_MODEL_ROOT__COMPATIBILITY: return DRModelPackage.R4E_DESIGN_RULE_COLLECTION__COMPATIBILITY;
				case ModelPackage.SUB_MODEL_ROOT__APPLICATION_VERSION: return DRModelPackage.R4E_DESIGN_RULE_COLLECTION__APPLICATION_VERSION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", version: ");
		result.append(version);
		result.append(", folder: ");
		result.append(folder);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //R4EDesignRuleCollectionImpl
