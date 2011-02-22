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
package org.eclipse.mylyn.reviews.r4e.core.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory
 * @model kind="package"
 * @generated
 */
public interface RModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.mylyn.reviews.r4e.core.model/";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "r4ecore";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RModelPackage eINSTANCE = org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl <em>R4E Review Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewGroup()
	 * @generated
	 */
	int R4E_REVIEW_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__ENABLED = ModelPackage.REVIEW_GROUP__ENABLED;

	/**
	 * The feature id for the '<em><b>Reviews</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__REVIEWS = ModelPackage.REVIEW_GROUP__REVIEWS;

	/**
	 * The feature id for the '<em><b>Review Group Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__REVIEW_GROUP_TASK = ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__DESCRIPTION = ModelPackage.REVIEW_GROUP__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__NAME = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__FOLDER = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Default Entry Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Available Projects</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__AVAILABLE_PROJECTS = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Available Components</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Design Rule Locations</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__XML_VERSION = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Available Anomaly Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Anomaly Type Key To Reference</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Reviews Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__REVIEWS_MAP = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>User Reviews</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP__USER_REVIEWS = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>R4E Review Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_GROUP_FEATURE_COUNT = ModelPackage.REVIEW_GROUP_FEATURE_COUNT + 11;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewImpl <em>R4E Review</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReview()
	 * @generated
	 */
	int R4E_REVIEW = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__ENABLED = ModelPackage.REVIEW__ENABLED;

	/**
	 * The feature id for the '<em><b>Topics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__TOPICS = ModelPackage.REVIEW__TOPICS;

	/**
	 * The feature id for the '<em><b>Review Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__REVIEW_ITEMS = ModelPackage.REVIEW__REVIEW_ITEMS;

	/**
	 * The feature id for the '<em><b>Review Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__REVIEW_TASK = ModelPackage.REVIEW__REVIEW_TASK;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__STATE = ModelPackage.REVIEW__STATE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__NAME = ModelPackage.REVIEW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__PROJECT = ModelPackage.REVIEW_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Components</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__COMPONENTS = ModelPackage.REVIEW_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Entry Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__ENTRY_CRITERIA = ModelPackage.REVIEW_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Extra Notes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__EXTRA_NOTES = ModelPackage.REVIEW_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Objectives</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__OBJECTIVES = ModelPackage.REVIEW_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Reference Material</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__REFERENCE_MATERIAL = ModelPackage.REVIEW_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Decision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__DECISION = ModelPackage.REVIEW_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__START_DATE = ModelPackage.REVIEW_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__END_DATE = ModelPackage.REVIEW_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__XML_VERSION = ModelPackage.REVIEW_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Anomaly Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__ANOMALY_TEMPLATE = ModelPackage.REVIEW_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__TYPE = ModelPackage.REVIEW_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Users Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__USERS_MAP = ModelPackage.REVIEW_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Created By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__CREATED_BY = ModelPackage.REVIEW_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Ids Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW__IDS_MAP = ModelPackage.REVIEW_FEATURE_COUNT + 15;

	/**
	 * The number of structural features of the '<em>R4E Review</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_FEATURE_COUNT = ModelPackage.REVIEW_FEATURE_COUNT + 16;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl <em>R4E Anomaly</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomaly()
	 * @generated
	 */
	int R4E_ANOMALY = 2;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__ENABLED = ModelPackage.TOPIC__ENABLED;

	/**
	 * The feature id for the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__USER = ModelPackage.TOPIC__USER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__TYPE = ModelPackage.TOPIC__TYPE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__DESCRIPTION = ModelPackage.TOPIC__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__TASK = ModelPackage.TOPIC__TASK;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__LOCATION = ModelPackage.TOPIC__LOCATION;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__COMMENTS = ModelPackage.TOPIC__COMMENTS;

	/**
	 * The feature id for the '<em><b>Review</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__REVIEW = ModelPackage.TOPIC__REVIEW;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__TITLE = ModelPackage.TOPIC__TITLE;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__ID = ModelPackage.TOPIC_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__CREATED_ON = ModelPackage.TOPIC_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Anomaly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__ANOMALY = ModelPackage.TOPIC_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__STATE = ModelPackage.TOPIC_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>User Assigned</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__USER_ASSIGNED = ModelPackage.TOPIC_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>User Follow Up</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__USER_FOLLOW_UP = ModelPackage.TOPIC_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>User Decision</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__USER_DECISION = ModelPackage.TOPIC_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Due Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__DUE_DATE = ModelPackage.TOPIC_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__RANK = ModelPackage.TOPIC_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__RULE = ModelPackage.TOPIC_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Decided By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__DECIDED_BY = ModelPackage.TOPIC_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Fixed By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__FIXED_BY = ModelPackage.TOPIC_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Followup By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__FOLLOWUP_BY = ModelPackage.TOPIC_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Not Accepted Reason</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__NOT_ACCEPTED_REASON = ModelPackage.TOPIC_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Is Imported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__IS_IMPORTED = ModelPackage.TOPIC_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Fixed In Version</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__FIXED_IN_VERSION = ModelPackage.TOPIC_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Rule ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__RULE_ID = ModelPackage.TOPIC_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Decided By ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__DECIDED_BY_ID = ModelPackage.TOPIC_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Fiexe By ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__FIEXE_BY_ID = ModelPackage.TOPIC_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Follow Up By ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY__FOLLOW_UP_BY_ID = ModelPackage.TOPIC_FEATURE_COUNT + 19;

	/**
	 * The number of structural features of the '<em>R4E Anomaly</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_FEATURE_COUNT = ModelPackage.TOPIC_FEATURE_COUNT + 20;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl <em>R4E Formal Review</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EFormalReview()
	 * @generated
	 */
	int R4E_FORMAL_REVIEW = 3;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__ENABLED = R4E_REVIEW__ENABLED;

	/**
	 * The feature id for the '<em><b>Topics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__TOPICS = R4E_REVIEW__TOPICS;

	/**
	 * The feature id for the '<em><b>Review Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__REVIEW_ITEMS = R4E_REVIEW__REVIEW_ITEMS;

	/**
	 * The feature id for the '<em><b>Review Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__REVIEW_TASK = R4E_REVIEW__REVIEW_TASK;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__STATE = R4E_REVIEW__STATE;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__NAME = R4E_REVIEW__NAME;

	/**
	 * The feature id for the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__PROJECT = R4E_REVIEW__PROJECT;

	/**
	 * The feature id for the '<em><b>Components</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__COMPONENTS = R4E_REVIEW__COMPONENTS;

	/**
	 * The feature id for the '<em><b>Entry Criteria</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__ENTRY_CRITERIA = R4E_REVIEW__ENTRY_CRITERIA;

	/**
	 * The feature id for the '<em><b>Extra Notes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__EXTRA_NOTES = R4E_REVIEW__EXTRA_NOTES;

	/**
	 * The feature id for the '<em><b>Objectives</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__OBJECTIVES = R4E_REVIEW__OBJECTIVES;

	/**
	 * The feature id for the '<em><b>Reference Material</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__REFERENCE_MATERIAL = R4E_REVIEW__REFERENCE_MATERIAL;

	/**
	 * The feature id for the '<em><b>Decision</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__DECISION = R4E_REVIEW__DECISION;

	/**
	 * The feature id for the '<em><b>Start Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__START_DATE = R4E_REVIEW__START_DATE;

	/**
	 * The feature id for the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__END_DATE = R4E_REVIEW__END_DATE;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__XML_VERSION = R4E_REVIEW__XML_VERSION;

	/**
	 * The feature id for the '<em><b>Anomaly Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__ANOMALY_TEMPLATE = R4E_REVIEW__ANOMALY_TEMPLATE;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__TYPE = R4E_REVIEW__TYPE;

	/**
	 * The feature id for the '<em><b>Users Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__USERS_MAP = R4E_REVIEW__USERS_MAP;

	/**
	 * The feature id for the '<em><b>Created By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__CREATED_BY = R4E_REVIEW__CREATED_BY;

	/**
	 * The feature id for the '<em><b>Ids Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__IDS_MAP = R4E_REVIEW__IDS_MAP;

	/**
	 * The feature id for the '<em><b>Preparation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__PREPARATION_DATE = R4E_REVIEW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Decision Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__DECISION_DATE = R4E_REVIEW_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Rework Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__REWORK_DATE = R4E_REVIEW_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Phase Owner ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__PHASE_OWNER_ID = R4E_REVIEW_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Phase Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW__PHASE_OWNER = R4E_REVIEW_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>R4E Formal Review</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FORMAL_REVIEW_FEATURE_COUNT = R4E_REVIEW_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EPositionImpl <em>R4E Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EPositionImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EPosition()
	 * @generated
	 */
	int R4E_POSITION = 21;

	/**
	 * The number of structural features of the '<em>R4E Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_POSITION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl <em>R4E Text Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ETextPosition()
	 * @generated
	 */
	int R4E_TEXT_POSITION = 4;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_POSITION__START_POSITION = R4E_POSITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_POSITION__LENGTH = R4E_POSITION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_POSITION__START_LINE = R4E_POSITION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_POSITION__END_LINE = R4E_POSITION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>R4E Text Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_POSITION_FEATURE_COUNT = R4E_POSITION_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewDecisionImpl <em>R4E Review Decision</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewDecisionImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewDecision()
	 * @generated
	 */
	int R4E_REVIEW_DECISION = 5;

	/**
	 * The feature id for the '<em><b>Spent Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_DECISION__SPENT_TIME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_DECISION__VALUE = 1;

	/**
	 * The number of structural features of the '<em>R4E Review Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_DECISION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl <em>R4E User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EUser()
	 * @generated
	 */
	int R4E_USER = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__ID = ModelPackage.USER__ID;

	/**
	 * The feature id for the '<em><b>Group Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__GROUP_PATHS = ModelPackage.USER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sequence ID Counter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__SEQUENCE_ID_COUNTER = ModelPackage.USER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Added Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__ADDED_COMMENTS = ModelPackage.USER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Added Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__ADDED_ITEMS = ModelPackage.USER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Review Created By Me</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__REVIEW_CREATED_BY_ME = ModelPackage.USER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Review Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__REVIEW_INSTANCE = ModelPackage.USER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__XML_VERSION = ModelPackage.USER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Review Completed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__REVIEW_COMPLETED = ModelPackage.USER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Review Completed Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER__REVIEW_COMPLETED_CODE = ModelPackage.USER_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>R4E User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_FEATURE_COUNT = ModelPackage.USER_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EParticipantImpl <em>R4E Participant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EParticipantImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EParticipant()
	 * @generated
	 */
	int R4E_PARTICIPANT = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__ID = R4E_USER__ID;

	/**
	 * The feature id for the '<em><b>Group Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__GROUP_PATHS = R4E_USER__GROUP_PATHS;

	/**
	 * The feature id for the '<em><b>Sequence ID Counter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__SEQUENCE_ID_COUNTER = R4E_USER__SEQUENCE_ID_COUNTER;

	/**
	 * The feature id for the '<em><b>Added Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__ADDED_COMMENTS = R4E_USER__ADDED_COMMENTS;

	/**
	 * The feature id for the '<em><b>Added Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__ADDED_ITEMS = R4E_USER__ADDED_ITEMS;

	/**
	 * The feature id for the '<em><b>Review Created By Me</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__REVIEW_CREATED_BY_ME = R4E_USER__REVIEW_CREATED_BY_ME;

	/**
	 * The feature id for the '<em><b>Review Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__REVIEW_INSTANCE = R4E_USER__REVIEW_INSTANCE;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__XML_VERSION = R4E_USER__XML_VERSION;

	/**
	 * The feature id for the '<em><b>Review Completed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__REVIEW_COMPLETED = R4E_USER__REVIEW_COMPLETED;

	/**
	 * The feature id for the '<em><b>Review Completed Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__REVIEW_COMPLETED_CODE = R4E_USER__REVIEW_COMPLETED_CODE;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__ROLES = R4E_USER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Spent Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__SPENT_TIME = R4E_USER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Focus Area</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__FOCUS_AREA = R4E_USER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Part Of Decision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__IS_PART_OF_DECISION = R4E_USER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reviewed Content</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT__REVIEWED_CONTENT = R4E_USER_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>R4E Participant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_PARTICIPANT_FEATURE_COUNT = R4E_USER_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl <em>R4E Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EItem()
	 * @generated
	 */
	int R4E_ITEM = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EContentImpl <em>R4E Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EContentImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EContent()
	 * @generated
	 */
	int R4E_CONTENT = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextContentImpl <em>R4E Text Content</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextContentImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ETextContent()
	 * @generated
	 */
	int R4E_TEXT_CONTENT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDImpl <em>R4EID</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EID()
	 * @generated
	 */
	int R4EID = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTypeImpl <em>R4E Anomaly Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTypeImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyType()
	 * @generated
	 */
	int R4E_ANOMALY_TYPE = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETaskReferenceImpl <em>R4E Task Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETaskReferenceImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ETaskReference()
	 * @generated
	 */
	int R4E_TASK_REFERENCE = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewStateImpl <em>R4E Review State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewStateImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewState()
	 * @generated
	 */
	int R4E_REVIEW_STATE = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl <em>R4E Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EComment()
	 * @generated
	 */
	int R4E_COMMENT = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewComponentImpl <em>R4E Review Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewComponentImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewComponent()
	 * @generated
	 */
	int R4E_REVIEW_COMPONENT = 15;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_COMPONENT__ENABLED = ModelPackage.REVIEW_COMPONENT__ENABLED;

	/**
	 * The number of structural features of the '<em>R4E Review Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_COMPONENT_FEATURE_COUNT = ModelPackage.REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDComponentImpl <em>R4EID Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDComponentImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EIDComponent()
	 * @generated
	 */
	int R4EID_COMPONENT = 26;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4EID_COMPONENT__ENABLED = R4E_REVIEW_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4EID_COMPONENT__ID = R4E_REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4EID Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4EID_COMPONENT_FEATURE_COUNT = R4E_REVIEW_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__ENABLED = R4EID_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__ID = R4EID_COMPONENT__ID;

	/**
	 * The feature id for the '<em><b>Added By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__ADDED_BY = R4EID_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Review</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__REVIEW = R4EID_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__XML_VERSION = R4EID_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__DESCRIPTION = R4EID_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Added By Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__ADDED_BY_ID = R4EID_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>File Context List</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__FILE_CONTEXT_LIST = R4EID_COMPONENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Repository Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__REPOSITORY_REF = R4EID_COMPONENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Project UR Is</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__PROJECT_UR_IS = R4EID_COMPONENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Author Rep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__AUTHOR_REP = R4EID_COMPONENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Submitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM__SUBMITTED = R4EID_COMPONENT_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>R4E Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ITEM_FEATURE_COUNT = R4EID_COMPONENT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_CONTENT__LOCATION = ModelPackage.LOCATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Info</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_CONTENT__INFO = ModelPackage.LOCATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>R4E Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_CONTENT_FEATURE_COUNT = ModelPackage.LOCATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_CONTENT__LOCATION = R4E_CONTENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Info</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_CONTENT__INFO = R4E_CONTENT__INFO;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_CONTENT__CONTENT = R4E_CONTENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4E Text Content</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TEXT_CONTENT_FEATURE_COUNT = R4E_CONTENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sequence ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4EID__SEQUENCE_ID = 0;

	/**
	 * The feature id for the '<em><b>User ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4EID__USER_ID = 1;

	/**
	 * The number of structural features of the '<em>R4EID</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4EID_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TYPE__TYPE = ModelPackage.COMMENT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4E Anomaly Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TYPE_FEATURE_COUNT = ModelPackage.COMMENT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TASK_REFERENCE__ENABLED = ModelPackage.TASK_REFERENCE__ENABLED;

	/**
	 * The feature id for the '<em><b>Task Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TASK_REFERENCE__TASK_ID = ModelPackage.TASK_REFERENCE__TASK_ID;

	/**
	 * The feature id for the '<em><b>Repository URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TASK_REFERENCE__REPOSITORY_URL = ModelPackage.TASK_REFERENCE__REPOSITORY_URL;

	/**
	 * The feature id for the '<em><b>Task</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TASK_REFERENCE__TASK = ModelPackage.TASK_REFERENCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4E Task Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_TASK_REFERENCE_FEATURE_COUNT = ModelPackage.TASK_REFERENCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_STATE__ENABLED = ModelPackage.REVIEW_STATE__ENABLED;

	/**
	 * The feature id for the '<em><b>State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_STATE__STATE = ModelPackage.REVIEW_STATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4E Review State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_REVIEW_STATE_FEATURE_COUNT = ModelPackage.REVIEW_STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__ENABLED = ModelPackage.COMMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__USER = ModelPackage.COMMENT__USER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__TYPE = ModelPackage.COMMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__DESCRIPTION = ModelPackage.COMMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__ID = ModelPackage.COMMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__CREATED_ON = ModelPackage.COMMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Anomaly</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT__ANOMALY = ModelPackage.COMMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>R4E Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT_FEATURE_COUNT = ModelPackage.COMMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl <em>R4E File Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EFileContext()
	 * @generated
	 */
	int R4E_FILE_CONTEXT = 16;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT__ENABLED = R4EID_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT__ID = R4EID_COMPONENT__ID;

	/**
	 * The feature id for the '<em><b>Deltas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT__DELTAS = R4EID_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Base</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT__BASE = R4EID_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT__TARGET = R4EID_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT__TYPE = R4EID_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>R4E File Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_CONTEXT_FEATURE_COUNT = R4EID_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EDeltaImpl <em>R4E Delta</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EDeltaImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EDelta()
	 * @generated
	 */
	int R4E_DELTA = 17;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DELTA__ENABLED = R4EID_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Id</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DELTA__ID = R4EID_COMPONENT__ID;

	/**
	 * The feature id for the '<em><b>Base</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DELTA__BASE = R4EID_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DELTA__TARGET = R4EID_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>R4E Delta</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DELTA_FEATURE_COUNT = R4EID_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentTypeImpl <em>R4E Comment Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentTypeImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ECommentType()
	 * @generated
	 */
	int R4E_COMMENT_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT_TYPE__TYPE = ModelPackage.COMMENT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4E Comment Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_COMMENT_TYPE_FEATURE_COUNT = ModelPackage.COMMENT_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToAnomalyTypeImpl <em>Map To Anomaly Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToAnomalyTypeImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapToAnomalyType()
	 * @generated
	 */
	int MAP_TO_ANOMALY_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_ANOMALY_TYPE__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_ANOMALY_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map To Anomaly Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_ANOMALY_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileVersionImpl <em>R4E File Version</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileVersionImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EFileVersion()
	 * @generated
	 */
	int R4E_FILE_VERSION = 22;

	/**
	 * The feature id for the '<em><b>Platform URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__PLATFORM_URI = 0;

	/**
	 * The feature id for the '<em><b>Version ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__VERSION_ID = 1;

	/**
	 * The feature id for the '<em><b>Repository Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__REPOSITORY_PATH = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__NAME = 3;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__RESOURCE = 4;

	/**
	 * The feature id for the '<em><b>Local Version ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__LOCAL_VERSION_ID = 5;

	/**
	 * The feature id for the '<em><b>File Revision</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION__FILE_REVISION = 6;

	/**
	 * The number of structural features of the '<em>R4E File Version</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_FILE_VERSION_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapNameToReviewImpl <em>Map Name To Review</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapNameToReviewImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapNameToReview()
	 * @generated
	 */
	int MAP_NAME_TO_REVIEW = 23;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_NAME_TO_REVIEW__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_NAME_TO_REVIEW__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map Name To Review</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_NAME_TO_REVIEW_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToUsersImpl <em>Map To Users</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToUsersImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapToUsers()
	 * @generated
	 */
	int MAP_TO_USERS = 24;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_USERS__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_USERS__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map To Users</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_USERS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl <em>R4E User Reviews</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EUserReviews()
	 * @generated
	 */
	int R4E_USER_REVIEWS = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_REVIEWS__NAME = 0;

	/**
	 * The feature id for the '<em><b>Invited To Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_REVIEWS__INVITED_TO_MAP = 1;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_REVIEWS__GROUP = 2;

	/**
	 * The feature id for the '<em><b>Created Reviews</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_REVIEWS__CREATED_REVIEWS = 3;

	/**
	 * The feature id for the '<em><b>Xml Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_REVIEWS__XML_VERSION = 4;

	/**
	 * The number of structural features of the '<em>R4E User Reviews</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_USER_REVIEWS_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapIDToComponentImpl <em>Map ID To Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapIDToComponentImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapIDToComponent()
	 * @generated
	 */
	int MAP_ID_TO_COMPONENT = 27;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ID_TO_COMPONENT__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ID_TO_COMPONENT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map ID To Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_ID_TO_COMPONENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapUserIDToUserReviewsImpl <em>Map User ID To User Reviews</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapUserIDToUserReviewsImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapUserIDToUserReviews()
	 * @generated
	 */
	int MAP_USER_ID_TO_USER_REVIEWS = 28;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_USER_ID_TO_USER_REVIEWS__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_USER_ID_TO_USER_REVIEWS__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map User ID To User Reviews</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_USER_ID_TO_USER_REVIEWS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTextPositionImpl <em>R4E Anomaly Text Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTextPositionImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyTextPosition()
	 * @generated
	 */
	int R4E_ANOMALY_TEXT_POSITION = 29;

	/**
	 * The feature id for the '<em><b>Start Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TEXT_POSITION__START_POSITION = R4E_TEXT_POSITION__START_POSITION;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TEXT_POSITION__LENGTH = R4E_TEXT_POSITION__LENGTH;

	/**
	 * The feature id for the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TEXT_POSITION__START_LINE = R4E_TEXT_POSITION__START_LINE;

	/**
	 * The feature id for the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TEXT_POSITION__END_LINE = R4E_TEXT_POSITION__END_LINE;

	/**
	 * The feature id for the '<em><b>File</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TEXT_POSITION__FILE = R4E_TEXT_POSITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>R4E Anomaly Text Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_ANOMALY_TEXT_POSITION_FEATURE_COUNT = R4E_TEXT_POSITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState <em>R4E Anomaly State</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyState()
	 * @generated
	 */
	int R4E_ANOMALY_STATE = 30;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank <em>R4E Anomaly Rank</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyRank()
	 * @generated
	 */
	int R4E_ANOMALY_RANK = 31;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase <em>R4E Review Phase</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewPhase()
	 * @generated
	 */
	int R4E_REVIEW_PHASE = 32;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole <em>R4E User Role</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EUserRole()
	 * @generated
	 */
	int R4E_USER_ROLE = 33;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision <em>R4E Decision</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EDecision()
	 * @generated
	 */
	int R4E_DECISION = 34;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType <em>R4E Review Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewType()
	 * @generated
	 */
	int R4E_REVIEW_TYPE = 35;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentEnum <em>R4E Comment Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentEnum
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ECommentEnum()
	 * @generated
	 */
	int R4E_COMMENT_ENUM = 36;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType <em>R4E Context Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EContextType()
	 * @generated
	 */
	int R4E_CONTEXT_TYPE = 37;

	/**
	 * The meta object id for the '<em>Mylyn Task</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.tasks.core.ITask
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMylynTask()
	 * @generated
	 */
	int MYLYN_TASK = 38;

	/**
	 * The meta object id for the '<em>IResource</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IResource
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getIResource()
	 * @generated
	 */
	int IRESOURCE = 39;


	/**
	 * The meta object id for the '<em>URI</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.URI
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getURI()
	 * @generated
	 */
	int URI = 40;


	/**
	 * The meta object id for the '<em>IFile Revision</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.team.core.history.IFileRevision
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getIFileRevision()
	 * @generated
	 */
	int IFILE_REVISION = 41;

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup <em>R4E Review Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Review Group</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup
	 * @generated
	 */
	EClass getR4EReviewGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getName()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getFolder()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_Folder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDefaultEntryCriteria <em>Default Entry Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Entry Criteria</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDefaultEntryCriteria()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_DefaultEntryCriteria();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableProjects <em>Available Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Available Projects</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableProjects()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_AvailableProjects();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableComponents <em>Available Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Available Components</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableComponents()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_AvailableComponents();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDesignRuleLocations <em>Design Rule Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Design Rule Locations</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getDesignRuleLocations()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_DesignRuleLocations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getXmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getXmlVersion()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EAttribute getR4EReviewGroup_XmlVersion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableAnomalyTypes <em>Available Anomaly Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Available Anomaly Types</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAvailableAnomalyTypes()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EReference getR4EReviewGroup_AvailableAnomalyTypes();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAnomalyTypeKeyToReference <em>Anomaly Type Key To Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Anomaly Type Key To Reference</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getAnomalyTypeKeyToReference()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EReference getR4EReviewGroup_AnomalyTypeKeyToReference();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getReviewsMap <em>Reviews Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Reviews Map</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getReviewsMap()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EReference getR4EReviewGroup_ReviewsMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getUserReviews <em>User Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>User Reviews</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup#getUserReviews()
	 * @see #getR4EReviewGroup()
	 * @generated
	 */
	EReference getR4EReviewGroup_UserReviews();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview <em>R4E Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Review</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview
	 * @generated
	 */
	EClass getR4EReview();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getName()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getProject <em>Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getProject()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_Project();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Components</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getComponents()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_Components();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEntryCriteria <em>Entry Criteria</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry Criteria</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEntryCriteria()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_EntryCriteria();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getExtraNotes <em>Extra Notes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extra Notes</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getExtraNotes()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_ExtraNotes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getObjectives <em>Objectives</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Objectives</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getObjectives()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_Objectives();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getReferenceMaterial <em>Reference Material</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reference Material</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getReferenceMaterial()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_ReferenceMaterial();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getDecision <em>Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Decision</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getDecision()
	 * @see #getR4EReview()
	 * @generated
	 */
	EReference getR4EReview_Decision();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getStartDate <em>Start Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Date</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getStartDate()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_StartDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEndDate <em>End Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getEndDate()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_EndDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getXmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getXmlVersion()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_XmlVersion();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getAnomalyTemplate <em>Anomaly Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Anomaly Template</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getAnomalyTemplate()
	 * @see #getR4EReview()
	 * @generated
	 */
	EReference getR4EReview_AnomalyTemplate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getType()
	 * @see #getR4EReview()
	 * @generated
	 */
	EAttribute getR4EReview_Type();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getUsersMap <em>Users Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Users Map</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getUsersMap()
	 * @see #getR4EReview()
	 * @generated
	 */
	EReference getR4EReview_UsersMap();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getIdsMap <em>Ids Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Ids Map</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getIdsMap()
	 * @see #getR4EReview()
	 * @generated
	 */
	EReference getR4EReview_IdsMap();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getCreatedBy <em>Created By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Created By</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview#getCreatedBy()
	 * @see #getR4EReview()
	 * @generated
	 */
	EReference getR4EReview_CreatedBy();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly <em>R4E Anomaly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Anomaly</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly
	 * @generated
	 */
	EClass getR4EAnomaly();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getState()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_State();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getUserAssigned <em>User Assigned</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User Assigned</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getUserAssigned()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_UserAssigned();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getUserFollowUp <em>User Follow Up</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User Follow Up</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getUserFollowUp()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_UserFollowUp();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getUserDecision <em>User Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User Decision</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getUserDecision()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_UserDecision();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDueDate <em>Due Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Due Date</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDueDate()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_DueDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRank <em>Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rank</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRank()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_Rank();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRule()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_Rule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDecidedBy <em>Decided By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Decided By</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDecidedBy()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_DecidedBy();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedBy <em>Fixed By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Fixed By</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedBy()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_FixedBy();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFollowupBy <em>Followup By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Followup By</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFollowupBy()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_FollowupBy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getNotAcceptedReason <em>Not Accepted Reason</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not Accepted Reason</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getNotAcceptedReason()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_NotAcceptedReason();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#isIsImported <em>Is Imported</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Imported</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#isIsImported()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_IsImported();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedInVersion <em>Fixed In Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Fixed In Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFixedInVersion()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EReference getR4EAnomaly_FixedInVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRuleID <em>Rule ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getRuleID()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_RuleID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDecidedByID <em>Decided By ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Decided By ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getDecidedByID()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_DecidedByID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFiexeByID <em>Fiexe By ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fiexe By ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFiexeByID()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_FiexeByID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFollowUpByID <em>Follow Up By ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Follow Up By ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly#getFollowUpByID()
	 * @see #getR4EAnomaly()
	 * @generated
	 */
	EAttribute getR4EAnomaly_FollowUpByID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview <em>R4E Formal Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Formal Review</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview
	 * @generated
	 */
	EClass getR4EFormalReview();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPreparationDate <em>Preparation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Preparation Date</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPreparationDate()
	 * @see #getR4EFormalReview()
	 * @generated
	 */
	EAttribute getR4EFormalReview_PreparationDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getDecisionDate <em>Decision Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Decision Date</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getDecisionDate()
	 * @see #getR4EFormalReview()
	 * @generated
	 */
	EAttribute getR4EFormalReview_DecisionDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getReworkDate <em>Rework Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rework Date</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getReworkDate()
	 * @see #getR4EFormalReview()
	 * @generated
	 */
	EAttribute getR4EFormalReview_ReworkDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwnerID <em>Phase Owner ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Phase Owner ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwnerID()
	 * @see #getR4EFormalReview()
	 * @generated
	 */
	EAttribute getR4EFormalReview_PhaseOwnerID();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwner <em>Phase Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Phase Owner</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview#getPhaseOwner()
	 * @see #getR4EFormalReview()
	 * @generated
	 */
	EReference getR4EFormalReview_PhaseOwner();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition <em>R4E Text Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Text Position</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition
	 * @generated
	 */
	EClass getR4ETextPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartPosition <em>Start Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Position</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartPosition()
	 * @see #getR4ETextPosition()
	 * @generated
	 */
	EAttribute getR4ETextPosition_StartPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getLength()
	 * @see #getR4ETextPosition()
	 * @generated
	 */
	EAttribute getR4ETextPosition_Length();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartLine <em>Start Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Line</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getStartLine()
	 * @see #getR4ETextPosition()
	 * @generated
	 */
	EAttribute getR4ETextPosition_StartLine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getEndLine <em>End Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Line</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition#getEndLine()
	 * @see #getR4ETextPosition()
	 * @generated
	 */
	EAttribute getR4ETextPosition_EndLine();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision <em>R4E Review Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Review Decision</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision
	 * @generated
	 */
	EClass getR4EReviewDecision();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getSpentTime <em>Spent Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Spent Time</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getSpentTime()
	 * @see #getR4EReviewDecision()
	 * @generated
	 */
	EAttribute getR4EReviewDecision_SpentTime();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision#getValue()
	 * @see #getR4EReviewDecision()
	 * @generated
	 */
	EAttribute getR4EReviewDecision_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser <em>R4E User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E User</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser
	 * @generated
	 */
	EClass getR4EUser();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getGroupPaths <em>Group Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group Paths</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getGroupPaths()
	 * @see #getR4EUser()
	 * @generated
	 */
	EAttribute getR4EUser_GroupPaths();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getSequenceIDCounter <em>Sequence ID Counter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sequence ID Counter</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getSequenceIDCounter()
	 * @see #getR4EUser()
	 * @generated
	 */
	EAttribute getR4EUser_SequenceIDCounter();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getAddedComments <em>Added Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Added Comments</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getAddedComments()
	 * @see #getR4EUser()
	 * @generated
	 */
	EReference getR4EUser_AddedComments();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getAddedItems <em>Added Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Added Items</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getAddedItems()
	 * @see #getR4EUser()
	 * @generated
	 */
	EReference getR4EUser_AddedItems();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCreatedByMe <em>Review Created By Me</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Review Created By Me</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCreatedByMe()
	 * @see #getR4EUser()
	 * @generated
	 */
	EAttribute getR4EUser_ReviewCreatedByMe();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewInstance <em>Review Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Review Instance</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewInstance()
	 * @see #getR4EUser()
	 * @generated
	 */
	EReference getR4EUser_ReviewInstance();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getXmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getXmlVersion()
	 * @see #getR4EUser()
	 * @generated
	 */
	EAttribute getR4EUser_XmlVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCompleted <em>Review Completed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Review Completed</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#isReviewCompleted()
	 * @see #getR4EUser()
	 * @generated
	 */
	EAttribute getR4EUser_ReviewCompleted();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewCompletedCode <em>Review Completed Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Review Completed Code</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getReviewCompletedCode()
	 * @see #getR4EUser()
	 * @generated
	 */
	EAttribute getR4EUser_ReviewCompletedCode();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant <em>R4E Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Participant</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant
	 * @generated
	 */
	EClass getR4EParticipant();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Roles</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getRoles()
	 * @see #getR4EParticipant()
	 * @generated
	 */
	EAttribute getR4EParticipant_Roles();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getSpentTime <em>Spent Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Spent Time</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getSpentTime()
	 * @see #getR4EParticipant()
	 * @generated
	 */
	EAttribute getR4EParticipant_SpentTime();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getFocusArea <em>Focus Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Focus Area</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getFocusArea()
	 * @see #getR4EParticipant()
	 * @generated
	 */
	EAttribute getR4EParticipant_FocusArea();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#isIsPartOfDecision <em>Is Part Of Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Part Of Decision</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#isIsPartOfDecision()
	 * @see #getR4EParticipant()
	 * @generated
	 */
	EAttribute getR4EParticipant_IsPartOfDecision();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getReviewedContent <em>Reviewed Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Reviewed Content</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant#getReviewedContent()
	 * @see #getR4EParticipant()
	 * @generated
	 */
	EReference getR4EParticipant_ReviewedContent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem <em>R4E Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Item</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem
	 * @generated
	 */
	EClass getR4EItem();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getXmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getXmlVersion()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_XmlVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getDescription()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAddedById <em>Added By Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Added By Id</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAddedById()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_AddedById();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getFileContextList <em>File Context List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>File Context List</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getFileContextList()
	 * @see #getR4EItem()
	 * @generated
	 */
	EReference getR4EItem_FileContextList();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getRepositoryRef <em>Repository Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Ref</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getRepositoryRef()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_RepositoryRef();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getProjectURIs <em>Project UR Is</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Project UR Is</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getProjectURIs()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_ProjectURIs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAuthorRep <em>Author Rep</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author Rep</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAuthorRep()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_AuthorRep();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getSubmitted <em>Submitted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Submitted</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getSubmitted()
	 * @see #getR4EItem()
	 * @generated
	 */
	EAttribute getR4EItem_Submitted();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent <em>R4E Text Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Text Content</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent
	 * @generated
	 */
	EClass getR4ETextContent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent#getContent()
	 * @see #getR4ETextContent()
	 * @generated
	 */
	EAttribute getR4ETextContent_Content();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EID <em>R4EID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4EID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EID
	 * @generated
	 */
	EClass getR4EID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EID#getSequenceID <em>Sequence ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sequence ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EID#getSequenceID()
	 * @see #getR4EID()
	 * @generated
	 */
	EAttribute getR4EID_SequenceID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EID#getUserID <em>User ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EID#getUserID()
	 * @see #getR4EID()
	 * @generated
	 */
	EAttribute getR4EID_UserID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType <em>R4E Anomaly Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Anomaly Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType
	 * @generated
	 */
	EClass getR4EAnomalyType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType#getType()
	 * @see #getR4EAnomalyType()
	 * @generated
	 */
	EAttribute getR4EAnomalyType_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference <em>R4E Task Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Task Reference</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference
	 * @generated
	 */
	EClass getR4ETaskReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference#getTask()
	 * @see #getR4ETaskReference()
	 * @generated
	 */
	EAttribute getR4ETaskReference_Task();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState <em>R4E Review State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Review State</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState
	 * @generated
	 */
	EClass getR4EReviewState();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>State</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState#getState()
	 * @see #getR4EReviewState()
	 * @generated
	 */
	EAttribute getR4EReviewState_State();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment <em>R4E Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Comment</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EComment
	 * @generated
	 */
	EClass getR4EComment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getCreatedOn <em>Created On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created On</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getCreatedOn()
	 * @see #getR4EComment()
	 * @generated
	 */
	EAttribute getR4EComment_CreatedOn();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getAnomaly <em>Anomaly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Anomaly</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EComment#getAnomaly()
	 * @see #getR4EComment()
	 * @generated
	 */
	EReference getR4EComment_Anomaly();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent <em>R4E Review Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Review Component</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent
	 * @generated
	 */
	EClass getR4EReviewComponent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext <em>R4E File Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E File Context</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext
	 * @generated
	 */
	EClass getR4EFileContext();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getDeltas <em>Deltas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Deltas</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getDeltas()
	 * @see #getR4EFileContext()
	 * @generated
	 */
	EReference getR4EFileContext_Deltas();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getBase <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getBase()
	 * @see #getR4EFileContext()
	 * @generated
	 */
	EReference getR4EFileContext_Base();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getTarget()
	 * @see #getR4EFileContext()
	 * @generated
	 */
	EReference getR4EFileContext_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext#getType()
	 * @see #getR4EFileContext()
	 * @generated
	 */
	EAttribute getR4EFileContext_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta <em>R4E Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Delta</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta
	 * @generated
	 */
	EClass getR4EDelta();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta#getBase <em>Base</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Base</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta#getBase()
	 * @see #getR4EDelta()
	 * @generated
	 */
	EReference getR4EDelta_Base();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta#getTarget()
	 * @see #getR4EDelta()
	 * @generated
	 */
	EReference getR4EDelta_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType <em>R4E Comment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Comment Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType
	 * @generated
	 */
	EClass getR4ECommentType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType#getType()
	 * @see #getR4ECommentType()
	 * @generated
	 */
	EAttribute getR4ECommentType_Type();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Map To Anomaly Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map To Anomaly Type</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType"
	 * @generated
	 */
	EClass getMapToAnomalyType();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapToAnomalyType()
	 * @generated
	 */
	EAttribute getMapToAnomalyType_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapToAnomalyType()
	 * @generated
	 */
	EReference getMapToAnomalyType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContent <em>R4E Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Content</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContent
	 * @generated
	 */
	EClass getR4EContent();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContent#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Location</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContent#getLocation()
	 * @see #getR4EContent()
	 * @generated
	 */
	EReference getR4EContent_Location();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContent#getInfo <em>Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContent#getInfo()
	 * @see #getR4EContent()
	 * @generated
	 */
	EAttribute getR4EContent_Info();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition <em>R4E Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Position</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition
	 * @generated
	 */
	EClass getR4EPosition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion <em>R4E File Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E File Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion
	 * @generated
	 */
	EClass getR4EFileVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getPlatformURI <em>Platform URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Platform URI</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getPlatformURI()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_PlatformURI();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getVersionID <em>Version ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getVersionID()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_VersionID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getRepositoryPath <em>Repository Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository Path</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getRepositoryPath()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_RepositoryPath();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getName()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getResource()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_Resource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getLocalVersionID <em>Local Version ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Local Version ID</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getLocalVersionID()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_LocalVersionID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getFileRevision <em>File Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Revision</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion#getFileRevision()
	 * @see #getR4EFileVersion()
	 * @generated
	 */
	EAttribute getR4EFileVersion_FileRevision();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Map Name To Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map Name To Review</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.mylyn.reviews.r4e.core.model.R4EReview" valueRequired="true"
	 * @generated
	 */
	EClass getMapNameToReview();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapNameToReview()
	 * @generated
	 */
	EAttribute getMapNameToReview_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapNameToReview()
	 * @generated
	 */
	EReference getMapNameToReview_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Map To Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map To Users</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.mylyn.reviews.r4e.core.model.R4EUser" valueRequired="true"
	 * @generated
	 */
	EClass getMapToUsers();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapToUsers()
	 * @generated
	 */
	EAttribute getMapToUsers_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapToUsers()
	 * @generated
	 */
	EReference getMapToUsers_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews <em>R4E User Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E User Reviews</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews
	 * @generated
	 */
	EClass getR4EUserReviews();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getName()
	 * @see #getR4EUserReviews()
	 * @generated
	 */
	EAttribute getR4EUserReviews_Name();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getInvitedToMap <em>Invited To Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Invited To Map</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getInvitedToMap()
	 * @see #getR4EUserReviews()
	 * @generated
	 */
	EReference getR4EUserReviews_InvitedToMap();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Group</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getGroup()
	 * @see #getR4EUserReviews()
	 * @generated
	 */
	EReference getR4EUserReviews_Group();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getCreatedReviews <em>Created Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Created Reviews</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getCreatedReviews()
	 * @see #getR4EUserReviews()
	 * @generated
	 */
	EAttribute getR4EUserReviews_CreatedReviews();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getXmlVersion <em>Xml Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Xml Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews#getXmlVersion()
	 * @see #getR4EUserReviews()
	 * @generated
	 */
	EAttribute getR4EUserReviews_XmlVersion();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent <em>R4EID Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4EID Component</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent
	 * @generated
	 */
	EClass getR4EIDComponent();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Id</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent#getId()
	 * @see #getR4EIDComponent()
	 * @generated
	 */
	EReference getR4EIDComponent_Id();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Map ID To Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map ID To Component</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.mylyn.reviews.r4e.core.model.R4EID" keyRequired="true"
	 *        valueType="org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent" valueRequired="true"
	 * @generated
	 */
	EClass getMapIDToComponent();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapIDToComponent()
	 * @generated
	 */
	EReference getMapIDToComponent_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapIDToComponent()
	 * @generated
	 */
	EReference getMapIDToComponent_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Map User ID To User Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map User ID To User Reviews</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueType="org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews" valueRequired="true"
	 * @generated
	 */
	EClass getMapUserIDToUserReviews();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapUserIDToUserReviews()
	 * @generated
	 */
	EAttribute getMapUserIDToUserReviews_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapUserIDToUserReviews()
	 * @generated
	 */
	EReference getMapUserIDToUserReviews_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition <em>R4E Anomaly Text Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Anomaly Text Position</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition
	 * @generated
	 */
	EClass getR4EAnomalyTextPosition();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>File</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition#getFile()
	 * @see #getR4EAnomalyTextPosition()
	 * @generated
	 */
	EReference getR4EAnomalyTextPosition_File();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState <em>R4E Anomaly State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Anomaly State</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState
	 * @generated
	 */
	EEnum getR4EAnomalyState();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank <em>R4E Anomaly Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Anomaly Rank</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank
	 * @generated
	 */
	EEnum getR4EAnomalyRank();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase <em>R4E Review Phase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Review Phase</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase
	 * @generated
	 */
	EEnum getR4EReviewPhase();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole <em>R4E User Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E User Role</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole
	 * @generated
	 */
	EEnum getR4EUserRole();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision <em>R4E Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Decision</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision
	 * @generated
	 */
	EEnum getR4EDecision();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType <em>R4E Review Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Review Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType
	 * @generated
	 */
	EEnum getR4EReviewType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentEnum <em>R4E Comment Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Comment Enum</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentEnum
	 * @generated
	 */
	EEnum getR4ECommentEnum();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType <em>R4E Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Context Type</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType
	 * @generated
	 */
	EEnum getR4EContextType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.mylyn.tasks.core.ITask <em>Mylyn Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Mylyn Task</em>'.
	 * @see org.eclipse.mylyn.tasks.core.ITask
	 * @model instanceClass="org.eclipse.mylyn.tasks.core.ITask"
	 * @generated
	 */
	EDataType getMylynTask();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.resources.IResource <em>IResource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IResource</em>'.
	 * @see org.eclipse.core.resources.IResource
	 * @model instanceClass="org.eclipse.core.resources.IResource"
	 * @generated
	 */
	EDataType getIResource();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.common.util.URI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>URI</em>'.
	 * @see org.eclipse.emf.common.util.URI
	 * @model instanceClass="org.eclipse.emf.common.util.URI" serializeable="false"
	 * @generated
	 */
	EDataType getURI();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.team.core.history.IFileRevision <em>IFile Revision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IFile Revision</em>'.
	 * @see org.eclipse.team.core.history.IFileRevision
	 * @model instanceClass="org.eclipse.team.core.history.IFileRevision"
	 * @generated
	 */
	EDataType getIFileRevision();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RModelFactory getRModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl <em>R4E Review Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewGroupImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewGroup()
		 * @generated
		 */
		EClass R4E_REVIEW_GROUP = eINSTANCE.getR4EReviewGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__NAME = eINSTANCE.getR4EReviewGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__FOLDER = eINSTANCE.getR4EReviewGroup_Folder();

		/**
		 * The meta object literal for the '<em><b>Default Entry Criteria</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__DEFAULT_ENTRY_CRITERIA = eINSTANCE.getR4EReviewGroup_DefaultEntryCriteria();

		/**
		 * The meta object literal for the '<em><b>Available Projects</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__AVAILABLE_PROJECTS = eINSTANCE.getR4EReviewGroup_AvailableProjects();

		/**
		 * The meta object literal for the '<em><b>Available Components</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__AVAILABLE_COMPONENTS = eINSTANCE.getR4EReviewGroup_AvailableComponents();

		/**
		 * The meta object literal for the '<em><b>Design Rule Locations</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__DESIGN_RULE_LOCATIONS = eINSTANCE.getR4EReviewGroup_DesignRuleLocations();

		/**
		 * The meta object literal for the '<em><b>Xml Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_GROUP__XML_VERSION = eINSTANCE.getR4EReviewGroup_XmlVersion();

		/**
		 * The meta object literal for the '<em><b>Available Anomaly Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW_GROUP__AVAILABLE_ANOMALY_TYPES = eINSTANCE.getR4EReviewGroup_AvailableAnomalyTypes();

		/**
		 * The meta object literal for the '<em><b>Anomaly Type Key To Reference</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW_GROUP__ANOMALY_TYPE_KEY_TO_REFERENCE = eINSTANCE.getR4EReviewGroup_AnomalyTypeKeyToReference();

		/**
		 * The meta object literal for the '<em><b>Reviews Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW_GROUP__REVIEWS_MAP = eINSTANCE.getR4EReviewGroup_ReviewsMap();

		/**
		 * The meta object literal for the '<em><b>User Reviews</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW_GROUP__USER_REVIEWS = eINSTANCE.getR4EReviewGroup_UserReviews();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewImpl <em>R4E Review</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReview()
		 * @generated
		 */
		EClass R4E_REVIEW = eINSTANCE.getR4EReview();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__NAME = eINSTANCE.getR4EReview_Name();

		/**
		 * The meta object literal for the '<em><b>Project</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__PROJECT = eINSTANCE.getR4EReview_Project();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__COMPONENTS = eINSTANCE.getR4EReview_Components();

		/**
		 * The meta object literal for the '<em><b>Entry Criteria</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__ENTRY_CRITERIA = eINSTANCE.getR4EReview_EntryCriteria();

		/**
		 * The meta object literal for the '<em><b>Extra Notes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__EXTRA_NOTES = eINSTANCE.getR4EReview_ExtraNotes();

		/**
		 * The meta object literal for the '<em><b>Objectives</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__OBJECTIVES = eINSTANCE.getR4EReview_Objectives();

		/**
		 * The meta object literal for the '<em><b>Reference Material</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__REFERENCE_MATERIAL = eINSTANCE.getR4EReview_ReferenceMaterial();

		/**
		 * The meta object literal for the '<em><b>Decision</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW__DECISION = eINSTANCE.getR4EReview_Decision();

		/**
		 * The meta object literal for the '<em><b>Start Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__START_DATE = eINSTANCE.getR4EReview_StartDate();

		/**
		 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__END_DATE = eINSTANCE.getR4EReview_EndDate();

		/**
		 * The meta object literal for the '<em><b>Xml Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__XML_VERSION = eINSTANCE.getR4EReview_XmlVersion();

		/**
		 * The meta object literal for the '<em><b>Anomaly Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW__ANOMALY_TEMPLATE = eINSTANCE.getR4EReview_AnomalyTemplate();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW__TYPE = eINSTANCE.getR4EReview_Type();

		/**
		 * The meta object literal for the '<em><b>Users Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW__USERS_MAP = eINSTANCE.getR4EReview_UsersMap();

		/**
		 * The meta object literal for the '<em><b>Ids Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW__IDS_MAP = eINSTANCE.getR4EReview_IdsMap();

		/**
		 * The meta object literal for the '<em><b>Created By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_REVIEW__CREATED_BY = eINSTANCE.getR4EReview_CreatedBy();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl <em>R4E Anomaly</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomaly()
		 * @generated
		 */
		EClass R4E_ANOMALY = eINSTANCE.getR4EAnomaly();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__STATE = eINSTANCE.getR4EAnomaly_State();

		/**
		 * The meta object literal for the '<em><b>User Assigned</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__USER_ASSIGNED = eINSTANCE.getR4EAnomaly_UserAssigned();

		/**
		 * The meta object literal for the '<em><b>User Follow Up</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__USER_FOLLOW_UP = eINSTANCE.getR4EAnomaly_UserFollowUp();

		/**
		 * The meta object literal for the '<em><b>User Decision</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__USER_DECISION = eINSTANCE.getR4EAnomaly_UserDecision();

		/**
		 * The meta object literal for the '<em><b>Due Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__DUE_DATE = eINSTANCE.getR4EAnomaly_DueDate();

		/**
		 * The meta object literal for the '<em><b>Rank</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__RANK = eINSTANCE.getR4EAnomaly_Rank();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__RULE = eINSTANCE.getR4EAnomaly_Rule();

		/**
		 * The meta object literal for the '<em><b>Decided By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__DECIDED_BY = eINSTANCE.getR4EAnomaly_DecidedBy();

		/**
		 * The meta object literal for the '<em><b>Fixed By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__FIXED_BY = eINSTANCE.getR4EAnomaly_FixedBy();

		/**
		 * The meta object literal for the '<em><b>Followup By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__FOLLOWUP_BY = eINSTANCE.getR4EAnomaly_FollowupBy();

		/**
		 * The meta object literal for the '<em><b>Not Accepted Reason</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__NOT_ACCEPTED_REASON = eINSTANCE.getR4EAnomaly_NotAcceptedReason();

		/**
		 * The meta object literal for the '<em><b>Is Imported</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__IS_IMPORTED = eINSTANCE.getR4EAnomaly_IsImported();

		/**
		 * The meta object literal for the '<em><b>Fixed In Version</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY__FIXED_IN_VERSION = eINSTANCE.getR4EAnomaly_FixedInVersion();

		/**
		 * The meta object literal for the '<em><b>Rule ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__RULE_ID = eINSTANCE.getR4EAnomaly_RuleID();

		/**
		 * The meta object literal for the '<em><b>Decided By ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__DECIDED_BY_ID = eINSTANCE.getR4EAnomaly_DecidedByID();

		/**
		 * The meta object literal for the '<em><b>Fiexe By ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__FIEXE_BY_ID = eINSTANCE.getR4EAnomaly_FiexeByID();

		/**
		 * The meta object literal for the '<em><b>Follow Up By ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY__FOLLOW_UP_BY_ID = eINSTANCE.getR4EAnomaly_FollowUpByID();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl <em>R4E Formal Review</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFormalReviewImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EFormalReview()
		 * @generated
		 */
		EClass R4E_FORMAL_REVIEW = eINSTANCE.getR4EFormalReview();

		/**
		 * The meta object literal for the '<em><b>Preparation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FORMAL_REVIEW__PREPARATION_DATE = eINSTANCE.getR4EFormalReview_PreparationDate();

		/**
		 * The meta object literal for the '<em><b>Decision Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FORMAL_REVIEW__DECISION_DATE = eINSTANCE.getR4EFormalReview_DecisionDate();

		/**
		 * The meta object literal for the '<em><b>Rework Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FORMAL_REVIEW__REWORK_DATE = eINSTANCE.getR4EFormalReview_ReworkDate();

		/**
		 * The meta object literal for the '<em><b>Phase Owner ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FORMAL_REVIEW__PHASE_OWNER_ID = eINSTANCE.getR4EFormalReview_PhaseOwnerID();

		/**
		 * The meta object literal for the '<em><b>Phase Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_FORMAL_REVIEW__PHASE_OWNER = eINSTANCE.getR4EFormalReview_PhaseOwner();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl <em>R4E Text Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ETextPosition()
		 * @generated
		 */
		EClass R4E_TEXT_POSITION = eINSTANCE.getR4ETextPosition();

		/**
		 * The meta object literal for the '<em><b>Start Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_TEXT_POSITION__START_POSITION = eINSTANCE.getR4ETextPosition_StartPosition();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_TEXT_POSITION__LENGTH = eINSTANCE.getR4ETextPosition_Length();

		/**
		 * The meta object literal for the '<em><b>Start Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_TEXT_POSITION__START_LINE = eINSTANCE.getR4ETextPosition_StartLine();

		/**
		 * The meta object literal for the '<em><b>End Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_TEXT_POSITION__END_LINE = eINSTANCE.getR4ETextPosition_EndLine();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewDecisionImpl <em>R4E Review Decision</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewDecisionImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewDecision()
		 * @generated
		 */
		EClass R4E_REVIEW_DECISION = eINSTANCE.getR4EReviewDecision();

		/**
		 * The meta object literal for the '<em><b>Spent Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_DECISION__SPENT_TIME = eINSTANCE.getR4EReviewDecision_SpentTime();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_DECISION__VALUE = eINSTANCE.getR4EReviewDecision_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl <em>R4E User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EUser()
		 * @generated
		 */
		EClass R4E_USER = eINSTANCE.getR4EUser();

		/**
		 * The meta object literal for the '<em><b>Group Paths</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER__GROUP_PATHS = eINSTANCE.getR4EUser_GroupPaths();

		/**
		 * The meta object literal for the '<em><b>Sequence ID Counter</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER__SEQUENCE_ID_COUNTER = eINSTANCE.getR4EUser_SequenceIDCounter();

		/**
		 * The meta object literal for the '<em><b>Added Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_USER__ADDED_COMMENTS = eINSTANCE.getR4EUser_AddedComments();

		/**
		 * The meta object literal for the '<em><b>Added Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_USER__ADDED_ITEMS = eINSTANCE.getR4EUser_AddedItems();

		/**
		 * The meta object literal for the '<em><b>Review Created By Me</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER__REVIEW_CREATED_BY_ME = eINSTANCE.getR4EUser_ReviewCreatedByMe();

		/**
		 * The meta object literal for the '<em><b>Review Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_USER__REVIEW_INSTANCE = eINSTANCE.getR4EUser_ReviewInstance();

		/**
		 * The meta object literal for the '<em><b>Xml Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER__XML_VERSION = eINSTANCE.getR4EUser_XmlVersion();

		/**
		 * The meta object literal for the '<em><b>Review Completed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER__REVIEW_COMPLETED = eINSTANCE.getR4EUser_ReviewCompleted();

		/**
		 * The meta object literal for the '<em><b>Review Completed Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER__REVIEW_COMPLETED_CODE = eINSTANCE.getR4EUser_ReviewCompletedCode();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EParticipantImpl <em>R4E Participant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EParticipantImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EParticipant()
		 * @generated
		 */
		EClass R4E_PARTICIPANT = eINSTANCE.getR4EParticipant();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_PARTICIPANT__ROLES = eINSTANCE.getR4EParticipant_Roles();

		/**
		 * The meta object literal for the '<em><b>Spent Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_PARTICIPANT__SPENT_TIME = eINSTANCE.getR4EParticipant_SpentTime();

		/**
		 * The meta object literal for the '<em><b>Focus Area</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_PARTICIPANT__FOCUS_AREA = eINSTANCE.getR4EParticipant_FocusArea();

		/**
		 * The meta object literal for the '<em><b>Is Part Of Decision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_PARTICIPANT__IS_PART_OF_DECISION = eINSTANCE.getR4EParticipant_IsPartOfDecision();

		/**
		 * The meta object literal for the '<em><b>Reviewed Content</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_PARTICIPANT__REVIEWED_CONTENT = eINSTANCE.getR4EParticipant_ReviewedContent();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl <em>R4E Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EItem()
		 * @generated
		 */
		EClass R4E_ITEM = eINSTANCE.getR4EItem();

		/**
		 * The meta object literal for the '<em><b>Xml Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__XML_VERSION = eINSTANCE.getR4EItem_XmlVersion();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__DESCRIPTION = eINSTANCE.getR4EItem_Description();

		/**
		 * The meta object literal for the '<em><b>Added By Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__ADDED_BY_ID = eINSTANCE.getR4EItem_AddedById();

		/**
		 * The meta object literal for the '<em><b>File Context List</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ITEM__FILE_CONTEXT_LIST = eINSTANCE.getR4EItem_FileContextList();

		/**
		 * The meta object literal for the '<em><b>Repository Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__REPOSITORY_REF = eINSTANCE.getR4EItem_RepositoryRef();

		/**
		 * The meta object literal for the '<em><b>Project UR Is</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__PROJECT_UR_IS = eINSTANCE.getR4EItem_ProjectURIs();

		/**
		 * The meta object literal for the '<em><b>Author Rep</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__AUTHOR_REP = eINSTANCE.getR4EItem_AuthorRep();

		/**
		 * The meta object literal for the '<em><b>Submitted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ITEM__SUBMITTED = eINSTANCE.getR4EItem_Submitted();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextContentImpl <em>R4E Text Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextContentImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ETextContent()
		 * @generated
		 */
		EClass R4E_TEXT_CONTENT = eINSTANCE.getR4ETextContent();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_TEXT_CONTENT__CONTENT = eINSTANCE.getR4ETextContent_Content();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDImpl <em>R4EID</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EID()
		 * @generated
		 */
		EClass R4EID = eINSTANCE.getR4EID();

		/**
		 * The meta object literal for the '<em><b>Sequence ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4EID__SEQUENCE_ID = eINSTANCE.getR4EID_SequenceID();

		/**
		 * The meta object literal for the '<em><b>User ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4EID__USER_ID = eINSTANCE.getR4EID_UserID();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTypeImpl <em>R4E Anomaly Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTypeImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyType()
		 * @generated
		 */
		EClass R4E_ANOMALY_TYPE = eINSTANCE.getR4EAnomalyType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_ANOMALY_TYPE__TYPE = eINSTANCE.getR4EAnomalyType_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETaskReferenceImpl <em>R4E Task Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETaskReferenceImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ETaskReference()
		 * @generated
		 */
		EClass R4E_TASK_REFERENCE = eINSTANCE.getR4ETaskReference();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_TASK_REFERENCE__TASK = eINSTANCE.getR4ETaskReference_Task();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewStateImpl <em>R4E Review State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewStateImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewState()
		 * @generated
		 */
		EClass R4E_REVIEW_STATE = eINSTANCE.getR4EReviewState();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_REVIEW_STATE__STATE = eINSTANCE.getR4EReviewState_State();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl <em>R4E Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EComment()
		 * @generated
		 */
		EClass R4E_COMMENT = eINSTANCE.getR4EComment();

		/**
		 * The meta object literal for the '<em><b>Created On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_COMMENT__CREATED_ON = eINSTANCE.getR4EComment_CreatedOn();

		/**
		 * The meta object literal for the '<em><b>Anomaly</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_COMMENT__ANOMALY = eINSTANCE.getR4EComment_Anomaly();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewComponentImpl <em>R4E Review Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EReviewComponentImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewComponent()
		 * @generated
		 */
		EClass R4E_REVIEW_COMPONENT = eINSTANCE.getR4EReviewComponent();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl <em>R4E File Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileContextImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EFileContext()
		 * @generated
		 */
		EClass R4E_FILE_CONTEXT = eINSTANCE.getR4EFileContext();

		/**
		 * The meta object literal for the '<em><b>Deltas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_FILE_CONTEXT__DELTAS = eINSTANCE.getR4EFileContext_Deltas();

		/**
		 * The meta object literal for the '<em><b>Base</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_FILE_CONTEXT__BASE = eINSTANCE.getR4EFileContext_Base();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_FILE_CONTEXT__TARGET = eINSTANCE.getR4EFileContext_Target();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_CONTEXT__TYPE = eINSTANCE.getR4EFileContext_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EDeltaImpl <em>R4E Delta</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EDeltaImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EDelta()
		 * @generated
		 */
		EClass R4E_DELTA = eINSTANCE.getR4EDelta();

		/**
		 * The meta object literal for the '<em><b>Base</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_DELTA__BASE = eINSTANCE.getR4EDelta_Base();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_DELTA__TARGET = eINSTANCE.getR4EDelta_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentTypeImpl <em>R4E Comment Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ECommentTypeImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ECommentType()
		 * @generated
		 */
		EClass R4E_COMMENT_TYPE = eINSTANCE.getR4ECommentType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_COMMENT_TYPE__TYPE = eINSTANCE.getR4ECommentType_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToAnomalyTypeImpl <em>Map To Anomaly Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToAnomalyTypeImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapToAnomalyType()
		 * @generated
		 */
		EClass MAP_TO_ANOMALY_TYPE = eINSTANCE.getMapToAnomalyType();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_TO_ANOMALY_TYPE__KEY = eINSTANCE.getMapToAnomalyType_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_TO_ANOMALY_TYPE__VALUE = eINSTANCE.getMapToAnomalyType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EContentImpl <em>R4E Content</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EContentImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EContent()
		 * @generated
		 */
		EClass R4E_CONTENT = eINSTANCE.getR4EContent();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_CONTENT__LOCATION = eINSTANCE.getR4EContent_Location();

		/**
		 * The meta object literal for the '<em><b>Info</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_CONTENT__INFO = eINSTANCE.getR4EContent_Info();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EPositionImpl <em>R4E Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EPositionImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EPosition()
		 * @generated
		 */
		EClass R4E_POSITION = eINSTANCE.getR4EPosition();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileVersionImpl <em>R4E File Version</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EFileVersionImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EFileVersion()
		 * @generated
		 */
		EClass R4E_FILE_VERSION = eINSTANCE.getR4EFileVersion();

		/**
		 * The meta object literal for the '<em><b>Platform URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__PLATFORM_URI = eINSTANCE.getR4EFileVersion_PlatformURI();

		/**
		 * The meta object literal for the '<em><b>Version ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__VERSION_ID = eINSTANCE.getR4EFileVersion_VersionID();

		/**
		 * The meta object literal for the '<em><b>Repository Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__REPOSITORY_PATH = eINSTANCE.getR4EFileVersion_RepositoryPath();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__NAME = eINSTANCE.getR4EFileVersion_Name();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__RESOURCE = eINSTANCE.getR4EFileVersion_Resource();

		/**
		 * The meta object literal for the '<em><b>Local Version ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__LOCAL_VERSION_ID = eINSTANCE.getR4EFileVersion_LocalVersionID();

		/**
		 * The meta object literal for the '<em><b>File Revision</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_FILE_VERSION__FILE_REVISION = eINSTANCE.getR4EFileVersion_FileRevision();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapNameToReviewImpl <em>Map Name To Review</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapNameToReviewImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapNameToReview()
		 * @generated
		 */
		EClass MAP_NAME_TO_REVIEW = eINSTANCE.getMapNameToReview();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_NAME_TO_REVIEW__KEY = eINSTANCE.getMapNameToReview_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_NAME_TO_REVIEW__VALUE = eINSTANCE.getMapNameToReview_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToUsersImpl <em>Map To Users</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapToUsersImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapToUsers()
		 * @generated
		 */
		EClass MAP_TO_USERS = eINSTANCE.getMapToUsers();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_TO_USERS__KEY = eINSTANCE.getMapToUsers_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_TO_USERS__VALUE = eINSTANCE.getMapToUsers_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl <em>R4E User Reviews</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserReviewsImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EUserReviews()
		 * @generated
		 */
		EClass R4E_USER_REVIEWS = eINSTANCE.getR4EUserReviews();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER_REVIEWS__NAME = eINSTANCE.getR4EUserReviews_Name();

		/**
		 * The meta object literal for the '<em><b>Invited To Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_USER_REVIEWS__INVITED_TO_MAP = eINSTANCE.getR4EUserReviews_InvitedToMap();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_USER_REVIEWS__GROUP = eINSTANCE.getR4EUserReviews_Group();

		/**
		 * The meta object literal for the '<em><b>Created Reviews</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER_REVIEWS__CREATED_REVIEWS = eINSTANCE.getR4EUserReviews_CreatedReviews();

		/**
		 * The meta object literal for the '<em><b>Xml Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_USER_REVIEWS__XML_VERSION = eINSTANCE.getR4EUserReviews_XmlVersion();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDComponentImpl <em>R4EID Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EIDComponentImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EIDComponent()
		 * @generated
		 */
		EClass R4EID_COMPONENT = eINSTANCE.getR4EIDComponent();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4EID_COMPONENT__ID = eINSTANCE.getR4EIDComponent_Id();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapIDToComponentImpl <em>Map ID To Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapIDToComponentImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapIDToComponent()
		 * @generated
		 */
		EClass MAP_ID_TO_COMPONENT = eINSTANCE.getMapIDToComponent();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_ID_TO_COMPONENT__KEY = eINSTANCE.getMapIDToComponent_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_ID_TO_COMPONENT__VALUE = eINSTANCE.getMapIDToComponent_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.MapUserIDToUserReviewsImpl <em>Map User ID To User Reviews</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.MapUserIDToUserReviewsImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMapUserIDToUserReviews()
		 * @generated
		 */
		EClass MAP_USER_ID_TO_USER_REVIEWS = eINSTANCE.getMapUserIDToUserReviews();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_USER_ID_TO_USER_REVIEWS__KEY = eINSTANCE.getMapUserIDToUserReviews_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_USER_ID_TO_USER_REVIEWS__VALUE = eINSTANCE.getMapUserIDToUserReviews_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTextPositionImpl <em>R4E Anomaly Text Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EAnomalyTextPositionImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyTextPosition()
		 * @generated
		 */
		EClass R4E_ANOMALY_TEXT_POSITION = eINSTANCE.getR4EAnomalyTextPosition();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_ANOMALY_TEXT_POSITION__FILE = eINSTANCE.getR4EAnomalyTextPosition_File();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState <em>R4E Anomaly State</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyState()
		 * @generated
		 */
		EEnum R4E_ANOMALY_STATE = eINSTANCE.getR4EAnomalyState();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank <em>R4E Anomaly Rank</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EAnomalyRank()
		 * @generated
		 */
		EEnum R4E_ANOMALY_RANK = eINSTANCE.getR4EAnomalyRank();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase <em>R4E Review Phase</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewPhase()
		 * @generated
		 */
		EEnum R4E_REVIEW_PHASE = eINSTANCE.getR4EReviewPhase();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole <em>R4E User Role</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EUserRole()
		 * @generated
		 */
		EEnum R4E_USER_ROLE = eINSTANCE.getR4EUserRole();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision <em>R4E Decision</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EDecision()
		 * @generated
		 */
		EEnum R4E_DECISION = eINSTANCE.getR4EDecision();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType <em>R4E Review Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EReviewType()
		 * @generated
		 */
		EEnum R4E_REVIEW_TYPE = eINSTANCE.getR4EReviewType();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentEnum <em>R4E Comment Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentEnum
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4ECommentEnum()
		 * @generated
		 */
		EEnum R4E_COMMENT_ENUM = eINSTANCE.getR4ECommentEnum();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType <em>R4E Context Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getR4EContextType()
		 * @generated
		 */
		EEnum R4E_CONTEXT_TYPE = eINSTANCE.getR4EContextType();

		/**
		 * The meta object literal for the '<em>Mylyn Task</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.tasks.core.ITask
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getMylynTask()
		 * @generated
		 */
		EDataType MYLYN_TASK = eINSTANCE.getMylynTask();

		/**
		 * The meta object literal for the '<em>IResource</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.resources.IResource
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getIResource()
		 * @generated
		 */
		EDataType IRESOURCE = eINSTANCE.getIResource();

		/**
		 * The meta object literal for the '<em>URI</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.common.util.URI
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getURI()
		 * @generated
		 */
		EDataType URI = eINSTANCE.getURI();

		/**
		 * The meta object literal for the '<em>IFile Revision</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.team.core.history.IFileRevision
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl#getIFileRevision()
		 * @generated
		 */
		EDataType IFILE_REVISION = eINSTANCE.getIFileRevision();

	}

} //RModelPackage
