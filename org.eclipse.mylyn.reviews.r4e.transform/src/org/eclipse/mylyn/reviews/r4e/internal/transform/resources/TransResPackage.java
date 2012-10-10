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
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResFactory
 * @model kind="package"
 * @generated
 */
public interface TransResPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "resources";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.mylyn.reviews.r4e.model.transform/1.0";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "ResTrans";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	TransResPackage eINSTANCE = org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.TransResPackageImpl.init();

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewGroupResImpl
	 * <em>Review Group Res</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewGroupResImpl
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.TransResPackageImpl#getReviewGroupRes()
	 * @generated
	 */
	int REVIEW_GROUP_RES = 0;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__ENABLED = RModelPackage.R4E_REVIEW_GROUP__ENABLED;

	/**
	 * The feature id for the '<em><b>Fragment Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__FRAGMENT_VERSION = RModelPackage.R4E_REVIEW_GROUP__FRAGMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Compatibility</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__COMPATIBILITY = RModelPackage.R4E_REVIEW_GROUP__COMPATIBILITY;

	/**
	 * The feature id for the '<em><b>Reviews</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__REVIEWS = RModelPackage.R4E_REVIEW_GROUP__REVIEWS;

	/**
	 * The feature id for the '<em><b>Review Group Task</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__REVIEW_GROUP_TASK = RModelPackage.R4E_REVIEW_GROUP__REVIEW_GROUP_TASK;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__DESCRIPTION = RModelPackage.R4E_REVIEW_GROUP__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Assigned To</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__ASSIGNED_TO = RModelPackage.R4E_REVIEW_GROUP__ASSIGNED_TO;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__NAME = RModelPackage.R4E_REVIEW_GROUP__NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__FOLDER = RModelPackage.R4E_REVIEW_GROUP__FOLDER;

	/**
	 * The feature id for the '<em><b>Default Entry Criteria</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__DEFAULT_ENTRY_CRITERIA = RModelPackage.R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA;

	/**
	 * The feature id for the '<em><b>Available Projects</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__AVAILABLE_PROJECTS = RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_PROJECTS;

	/**
	 * The feature id for the '<em><b>Available Components</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__AVAILABLE_COMPONENTS = RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS;

	/**
	 * The feature id for the '<em><b>Design Rule Locations</b></em>' attribute list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__DESIGN_RULE_LOCATIONS = RModelPackage.R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS;

	/**
	 * The feature id for the '<em><b>Available Anomaly Types</b></em>' containment reference list. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__AVAILABLE_ANOMALY_TYPES = RModelPackage.R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES;

	/**
	 * The feature id for the '<em><b>Anomaly Type Key To Reference</b></em>' map. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__ANOMALY_TYPE_KEY_TO_REFERENCE = RModelPackage.R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE;

	/**
	 * The feature id for the '<em><b>Reviews Map</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__REVIEWS_MAP = RModelPackage.R4E_REVIEW_GROUP__REVIEWS_MAP;

	/**
	 * The feature id for the '<em><b>User Reviews</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__USER_REVIEWS = RModelPackage.R4E_REVIEW_GROUP__USER_REVIEWS;

	/**
	 * The feature id for the '<em><b>Reviews Res</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__REVIEWS_RES = RModelPackage.R4E_REVIEW_GROUP_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Files Prefix</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES__FILES_PREFIX = RModelPackage.R4E_REVIEW_GROUP_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Review Group Res</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_RES_FEATURE_COUNT = RModelPackage.R4E_REVIEW_GROUP_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewResImpl
	 * <em>Review Res</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewResImpl
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.TransResPackageImpl#getReviewRes()
	 * @generated
	 */
	int REVIEW_RES = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__ENABLED = RModelPackage.R4E_FORMAL_REVIEW__ENABLED;

	/**
	 * The feature id for the '<em><b>Fragment Version</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__FRAGMENT_VERSION = RModelPackage.R4E_FORMAL_REVIEW__FRAGMENT_VERSION;

	/**
	 * The feature id for the '<em><b>Compatibility</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__COMPATIBILITY = RModelPackage.R4E_FORMAL_REVIEW__COMPATIBILITY;

	/**
	 * The feature id for the '<em><b>Topics</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__TOPICS = RModelPackage.R4E_FORMAL_REVIEW__TOPICS;

	/**
	 * The feature id for the '<em><b>Review Items</b></em>' reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__REVIEW_ITEMS = RModelPackage.R4E_FORMAL_REVIEW__REVIEW_ITEMS;

	/**
	 * The feature id for the '<em><b>Review Task</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__REVIEW_TASK = RModelPackage.R4E_FORMAL_REVIEW__REVIEW_TASK;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__STATE = RModelPackage.R4E_FORMAL_REVIEW__STATE;

	/**
	 * The feature id for the '<em><b>Assigned To</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__ASSIGNED_TO = RModelPackage.R4E_FORMAL_REVIEW__ASSIGNED_TO;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__NAME = RModelPackage.R4E_FORMAL_REVIEW__NAME;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__PROJECT = RModelPackage.R4E_FORMAL_REVIEW__PROJECT;

	/**
	 * The feature id for the '<em><b>Components</b></em>' attribute list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__COMPONENTS = RModelPackage.R4E_FORMAL_REVIEW__COMPONENTS;

	/**
	 * The feature id for the '<em><b>Entry Criteria</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__ENTRY_CRITERIA = RModelPackage.R4E_FORMAL_REVIEW__ENTRY_CRITERIA;

	/**
	 * The feature id for the '<em><b>Extra Notes</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__EXTRA_NOTES = RModelPackage.R4E_FORMAL_REVIEW__EXTRA_NOTES;

	/**
	 * The feature id for the '<em><b>Objectives</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__OBJECTIVES = RModelPackage.R4E_FORMAL_REVIEW__OBJECTIVES;

	/**
	 * The feature id for the '<em><b>Reference Material</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__REFERENCE_MATERIAL = RModelPackage.R4E_FORMAL_REVIEW__REFERENCE_MATERIAL;

	/**
	 * The feature id for the '<em><b>Decision</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__DECISION = RModelPackage.R4E_FORMAL_REVIEW__DECISION;

	/**
	 * The feature id for the '<em><b>Start Date</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__START_DATE = RModelPackage.R4E_FORMAL_REVIEW__START_DATE;

	/**
	 * The feature id for the '<em><b>End Date</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__END_DATE = RModelPackage.R4E_FORMAL_REVIEW__END_DATE;

	/**
	 * The feature id for the '<em><b>Anomaly Template</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__ANOMALY_TEMPLATE = RModelPackage.R4E_FORMAL_REVIEW__ANOMALY_TEMPLATE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__TYPE = RModelPackage.R4E_FORMAL_REVIEW__TYPE;

	/**
	 * The feature id for the '<em><b>Users Map</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__USERS_MAP = RModelPackage.R4E_FORMAL_REVIEW__USERS_MAP;

	/**
	 * The feature id for the '<em><b>Created By</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__CREATED_BY = RModelPackage.R4E_FORMAL_REVIEW__CREATED_BY;

	/**
	 * The feature id for the '<em><b>Ids Map</b></em>' map. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__IDS_MAP = RModelPackage.R4E_FORMAL_REVIEW__IDS_MAP;

	/**
	 * The feature id for the '<em><b>Active Meeting</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__ACTIVE_MEETING = RModelPackage.R4E_FORMAL_REVIEW__ACTIVE_MEETING;

	/**
	 * The feature id for the '<em><b>Phase Owner</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__PHASE_OWNER = RModelPackage.R4E_FORMAL_REVIEW__PHASE_OWNER;

	/**
	 * The feature id for the '<em><b>Phases</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__PHASES = RModelPackage.R4E_FORMAL_REVIEW__PHASES;

	/**
	 * The feature id for the '<em><b>Current</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__CURRENT = RModelPackage.R4E_FORMAL_REVIEW__CURRENT;

	/**
	 * The feature id for the '<em><b>Users Res</b></em>' containment reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES__USERS_RES = RModelPackage.R4E_FORMAL_REVIEW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Review Res</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REVIEW_RES_FEATURE_COUNT = RModelPackage.R4E_FORMAL_REVIEW_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes <em>Review Group Res</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Review Group Res</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes
	 * @generated
	 */
	EClass getReviewGroupRes();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getReviewsRes
	 * <em>Reviews Res</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Reviews Res</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getReviewsRes()
	 * @see #getReviewGroupRes()
	 * @generated
	 */
	EReference getReviewGroupRes_ReviewsRes();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getFilesPrefix
	 * <em>Files Prefix</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Files Prefix</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes#getFilesPrefix()
	 * @see #getReviewGroupRes()
	 * @generated
	 */
	EAttribute getReviewGroupRes_FilesPrefix();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes
	 * <em>Review Res</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Review Res</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes
	 * @generated
	 */
	EClass getReviewRes();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes#getUsersRes <em>Users Res</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Users Res</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes#getUsersRes()
	 * @see #getReviewRes()
	 * @generated
	 */
	EReference getReviewRes_UsersRes();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TransResFactory getTransResFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewGroupResImpl
		 * <em>Review Group Res</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewGroupResImpl
		 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.TransResPackageImpl#getReviewGroupRes()
		 * @generated
		 */
		EClass REVIEW_GROUP_RES = eINSTANCE.getReviewGroupRes();

		/**
		 * The meta object literal for the '<em><b>Reviews Res</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REVIEW_GROUP_RES__REVIEWS_RES = eINSTANCE.getReviewGroupRes_ReviewsRes();

		/**
		 * The meta object literal for the '<em><b>Files Prefix</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute REVIEW_GROUP_RES__FILES_PREFIX = eINSTANCE.getReviewGroupRes_FilesPrefix();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewResImpl <em>Review Res</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.ReviewResImpl
		 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl.TransResPackageImpl#getReviewRes()
		 * @generated
		 */
		EClass REVIEW_RES = eINSTANCE.getReviewRes();

		/**
		 * The meta object literal for the '<em><b>Users Res</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REVIEW_RES__USERS_RES = eINSTANCE.getReviewRes_UsersRes();

	}

} //TransResPackage
