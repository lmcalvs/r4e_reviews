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
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Review Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getAssignedTo <em>Assigned To</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getDefaultEntryCriteria <em>Default Entry Criteria</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getAvailableProjects <em>Available Projects</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getAvailableComponents <em>Available Components</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getDesignRuleLocations <em>Design Rule Locations</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getXmlVersion <em>Xml Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getAvailableAnomalyTypes <em>Available Anomaly Types</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getAnomalyTypeKeyToReference <em>Anomaly Type Key To Reference</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getReviewsMap <em>Reviews Map</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl#getUserReviews <em>User Reviews</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EReviewGroupImpl extends ReviewGroupImpl implements R4EReviewGroup {
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
	 * The default value of the '{@link #getDefaultEntryCriteria() <em>Default Entry Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultEntryCriteria()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_ENTRY_CRITERIA_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultEntryCriteria() <em>Default Entry Criteria</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultEntryCriteria()
	 * @generated
	 * @ordered
	 */
	protected String defaultEntryCriteria = DEFAULT_ENTRY_CRITERIA_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAvailableProjects() <em>Available Projects</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailableProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<String> availableProjects;

	/**
	 * The cached value of the '{@link #getAvailableComponents() <em>Available Components</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailableComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<String> availableComponents;

	/**
	 * The cached value of the '{@link #getDesignRuleLocations() <em>Design Rule Locations</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesignRuleLocations()
	 * @generated
	 * @ordered
	 */
	protected EList<String> designRuleLocations;

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
	 * The cached value of the '{@link #getAvailableAnomalyTypes() <em>Available Anomaly Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailableAnomalyTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EAnomalyType> availableAnomalyTypes;

	/**
	 * The cached value of the '{@link #getAnomalyTypeKeyToReference() <em>Anomaly Type Key To Reference</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnomalyTypeKeyToReference()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, R4EAnomalyType> anomalyTypeKeyToReference;

	/**
	 * The cached value of the '{@link #getReviewsMap() <em>Reviews Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewsMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, R4EReview> reviewsMap;

	/**
	 * The cached value of the '{@link #getUserReviews() <em>User Reviews</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserReviews()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, R4EUserReviews> userReviews;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EReviewGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_REVIEW_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAssignedTo() {
		if (assignedTo == null) {
			assignedTo = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO);
		}
		return assignedTo;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_REVIEW_GROUP__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_REVIEW_GROUP__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultEntryCriteria() {
		return defaultEntryCriteria;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultEntryCriteria(String newDefaultEntryCriteria) {
		String oldDefaultEntryCriteria = defaultEntryCriteria;
		defaultEntryCriteria = newDefaultEntryCriteria;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA, oldDefaultEntryCriteria, defaultEntryCriteria));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAvailableProjects() {
		if (availableProjects == null) {
			availableProjects = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_PROJECTS);
		}
		return availableProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAvailableComponents() {
		if (availableComponents == null) {
			availableComponents = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS);
		}
		return availableComponents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getDesignRuleLocations() {
		if (designRuleLocations == null) {
			designRuleLocations = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS);
		}
		return designRuleLocations;
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
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_REVIEW_GROUP__XML_VERSION, oldXmlVersion, xmlVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<R4EAnomalyType> getAvailableAnomalyTypes() {
		if (availableAnomalyTypes == null) {
			availableAnomalyTypes = new EObjectContainmentEList.Resolving<R4EAnomalyType>(R4EAnomalyType.class, this, RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES);
		}
		return availableAnomalyTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, R4EAnomalyType> getAnomalyTypeKeyToReference() {
		if (anomalyTypeKeyToReference == null) {
			anomalyTypeKeyToReference = new EcoreEMap<String,R4EAnomalyType>(RModelPackage.Literals.MAP_TO_ANOMALY_TYPE, MapToAnomalyTypeImpl.class, this, RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE);
		}
		return anomalyTypeKeyToReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, R4EReview> getReviewsMap() {
		if (reviewsMap == null) {
			reviewsMap = new EcoreEMap<String,R4EReview>(RModelPackage.Literals.MAP_NAME_TO_REVIEW, MapNameToReviewImpl.class, this, RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP);
		}
		return reviewsMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, R4EUserReviews> getUserReviews() {
		if (userReviews == null) {
			userReviews = new EcoreEMap<String,R4EUserReviews>(RModelPackage.Literals.MAP_USER_ID_TO_USER_REVIEWS, MapUserIDToUserReviewsImpl.class, this, RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS);
		}
		return userReviews;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES:
				return ((InternalEList<?>)getAvailableAnomalyTypes()).basicRemove(otherEnd, msgs);
			case RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE:
				return ((InternalEList<?>)getAnomalyTypeKeyToReference()).basicRemove(otherEnd, msgs);
			case RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP:
				return ((InternalEList<?>)getReviewsMap()).basicRemove(otherEnd, msgs);
			case RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS:
				return ((InternalEList<?>)getUserReviews()).basicRemove(otherEnd, msgs);
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
			case RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO:
				return getAssignedTo();
			case RModelPackage.R4E_REVIEW_GROUP__NAME:
				return getName();
			case RModelPackage.R4E_REVIEW_GROUP__FOLDER:
				return getFolder();
			case RModelPackage.R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA:
				return getDefaultEntryCriteria();
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_PROJECTS:
				return getAvailableProjects();
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS:
				return getAvailableComponents();
			case RModelPackage.R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS:
				return getDesignRuleLocations();
			case RModelPackage.R4E_REVIEW_GROUP__XML_VERSION:
				return getXmlVersion();
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES:
				return getAvailableAnomalyTypes();
			case RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE:
				if (coreType) return getAnomalyTypeKeyToReference();
				else return getAnomalyTypeKeyToReference().map();
			case RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP:
				if (coreType) return getReviewsMap();
				else return getReviewsMap().map();
			case RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS:
				if (coreType) return getUserReviews();
				else return getUserReviews().map();
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
			case RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO:
				getAssignedTo().clear();
				getAssignedTo().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__NAME:
				setName((String)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__FOLDER:
				setFolder((String)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA:
				setDefaultEntryCriteria((String)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_PROJECTS:
				getAvailableProjects().clear();
				getAvailableProjects().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS:
				getAvailableComponents().clear();
				getAvailableComponents().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS:
				getDesignRuleLocations().clear();
				getDesignRuleLocations().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__XML_VERSION:
				setXmlVersion((String)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES:
				getAvailableAnomalyTypes().clear();
				getAvailableAnomalyTypes().addAll((Collection<? extends R4EAnomalyType>)newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE:
				((EStructuralFeature.Setting)getAnomalyTypeKeyToReference()).set(newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP:
				((EStructuralFeature.Setting)getReviewsMap()).set(newValue);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS:
				((EStructuralFeature.Setting)getUserReviews()).set(newValue);
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
			case RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO:
				getAssignedTo().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA:
				setDefaultEntryCriteria(DEFAULT_ENTRY_CRITERIA_EDEFAULT);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_PROJECTS:
				getAvailableProjects().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS:
				getAvailableComponents().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS:
				getDesignRuleLocations().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__XML_VERSION:
				setXmlVersion(XML_VERSION_EDEFAULT);
				return;
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES:
				getAvailableAnomalyTypes().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE:
				getAnomalyTypeKeyToReference().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP:
				getReviewsMap().clear();
				return;
			case RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS:
				getUserReviews().clear();
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
			case RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO:
				return assignedTo != null && !assignedTo.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case RModelPackage.R4E_REVIEW_GROUP__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case RModelPackage.R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA:
				return DEFAULT_ENTRY_CRITERIA_EDEFAULT == null ? defaultEntryCriteria != null : !DEFAULT_ENTRY_CRITERIA_EDEFAULT.equals(defaultEntryCriteria);
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_PROJECTS:
				return availableProjects != null && !availableProjects.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS:
				return availableComponents != null && !availableComponents.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS:
				return designRuleLocations != null && !designRuleLocations.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__XML_VERSION:
				return XML_VERSION_EDEFAULT == null ? xmlVersion != null : !XML_VERSION_EDEFAULT.equals(xmlVersion);
			case RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES:
				return availableAnomalyTypes != null && !availableAnomalyTypes.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE:
				return anomalyTypeKeyToReference != null && !anomalyTypeKeyToReference.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP:
				return reviewsMap != null && !reviewsMap.isEmpty();
			case RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS:
				return userReviews != null && !userReviews.isEmpty();
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
		if (baseClass == R4EReviewComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO: return RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO;
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
		if (baseClass == R4EReviewComponent.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO: return RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO;
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
		result.append(" (assignedTo: ");
		result.append(assignedTo);
		result.append(", name: ");
		result.append(name);
		result.append(", folder: ");
		result.append(folder);
		result.append(", defaultEntryCriteria: ");
		result.append(defaultEntryCriteria);
		result.append(", availableProjects: ");
		result.append(availableProjects);
		result.append(", availableComponents: ");
		result.append(availableComponents);
		result.append(", designRuleLocations: ");
		result.append(designRuleLocations);
		result.append(", xmlVersion: ");
		result.append(xmlVersion);
		result.append(')');
		return result.toString();
	}

} //R4EReviewGroupImpl
