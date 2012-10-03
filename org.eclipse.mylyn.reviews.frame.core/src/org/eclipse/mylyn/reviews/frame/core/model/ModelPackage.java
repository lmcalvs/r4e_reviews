/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.frame.core.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
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
	String eNS_URI = "http://org.eclipse.mylyn.reviews.frame.core.model/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.mylyn.reviews.frame.core";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewComponentImpl <em>Review Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewComponentImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReviewComponent()
	 * @generated
	 */
	int REVIEW_COMPONENT = 10;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_COMPONENT__ENABLED = 0;

	/**
	 * The number of structural features of the '<em>Review Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_COMPONENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl <em>Review</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReview()
	 * @generated
	 */
	int REVIEW = 0;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__ENABLED = REVIEW_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Fragment Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__FRAGMENT_VERSION = REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Compatibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__COMPATIBILITY = REVIEW_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Application Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__APPLICATION_VERSION = REVIEW_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Topics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__TOPICS = REVIEW_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Review Items</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__REVIEW_ITEMS = REVIEW_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Review Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__REVIEW_TASK = REVIEW_COMPONENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW__STATE = REVIEW_COMPONENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Review</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_FEATURE_COUNT = REVIEW_COMPONENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.CommentImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 1;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__ENABLED = REVIEW_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__USER = REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__TYPE = REVIEW_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__DESCRIPTION = REVIEW_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = REVIEW_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ItemImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getItem()
	 * @generated
	 */
	int ITEM = 2;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__ENABLED = REVIEW_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Added By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__ADDED_BY = REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Review</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__REVIEW = REVIEW_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_FEATURE_COUNT = REVIEW_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.LocationImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 3;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.UserImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getUser()
	 * @generated
	 */
	int USER = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__ID = 0;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__EMAIL = 1;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.TaskReferenceImpl <em>Task Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.TaskReferenceImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getTaskReference()
	 * @generated
	 */
	int TASK_REFERENCE = 5;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REFERENCE__ENABLED = REVIEW_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Task Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REFERENCE__TASK_ID = REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Repository URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REFERENCE__REPOSITORY_URL = REVIEW_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Task Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REFERENCE_FEATURE_COUNT = REVIEW_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewStateImpl <em>Review State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewStateImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReviewState()
	 * @generated
	 */
	int REVIEW_STATE = 6;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_STATE__ENABLED = REVIEW_COMPONENT__ENABLED;

	/**
	 * The number of structural features of the '<em>Review State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_STATE_FEATURE_COUNT = REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl <em>Review Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReviewGroup()
	 * @generated
	 */
	int REVIEW_GROUP = 7;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__ENABLED = REVIEW_COMPONENT__ENABLED;

	/**
	 * The feature id for the '<em><b>Fragment Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__FRAGMENT_VERSION = REVIEW_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Compatibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__COMPATIBILITY = REVIEW_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Application Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__APPLICATION_VERSION = REVIEW_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Reviews</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__REVIEWS = REVIEW_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Review Group Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__REVIEW_GROUP_TASK = REVIEW_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP__DESCRIPTION = REVIEW_COMPONENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Review Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REVIEW_GROUP_FEATURE_COUNT = REVIEW_COMPONENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.CommentTypeImpl <em>Comment Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.CommentTypeImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getCommentType()
	 * @generated
	 */
	int COMMENT_TYPE = 8;

	/**
	 * The number of structural features of the '<em>Comment Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_TYPE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.TopicImpl <em>Topic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.TopicImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getTopic()
	 * @generated
	 */
	int TOPIC = 9;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__ENABLED = COMMENT__ENABLED;

	/**
	 * The feature id for the '<em><b>User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__USER = COMMENT__USER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__TYPE = COMMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__DESCRIPTION = COMMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__TASK = COMMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__LOCATION = COMMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__COMMENTS = COMMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Review</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__REVIEW = COMMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC__TITLE = COMMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Topic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TOPIC_FEATURE_COUNT = COMMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl <em>Sub Model Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl
	 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getSubModelRoot()
	 * @generated
	 */
	int SUB_MODEL_ROOT = 11;

	/**
	 * The feature id for the '<em><b>Fragment Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_MODEL_ROOT__FRAGMENT_VERSION = 0;

	/**
	 * The feature id for the '<em><b>Compatibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_MODEL_ROOT__COMPATIBILITY = 1;

	/**
	 * The feature id for the '<em><b>Application Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_MODEL_ROOT__APPLICATION_VERSION = 2;

	/**
	 * The number of structural features of the '<em>Sub Model Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_MODEL_ROOT_FEATURE_COUNT = 3;

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.Review <em>Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Review
	 * @generated
	 */
	EClass getReview();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getTopics <em>Topics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Topics</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Review#getTopics()
	 * @see #getReview()
	 * @generated
	 */
	EReference getReview_Topics();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewItems <em>Review Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Review Items</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewItems()
	 * @see #getReview()
	 * @generated
	 */
	EReference getReview_ReviewItems();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewTask <em>Review Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Review Task</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Review#getReviewTask()
	 * @see #getReview()
	 * @generated
	 */
	EReference getReview_ReviewTask();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Review#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>State</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Review#getState()
	 * @see #getReview()
	 * @generated
	 */
	EReference getReview_State();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Comment#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>User</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Comment#getUser()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_User();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Comment#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Comment#getType()
	 * @see #getComment()
	 * @generated
	 */
	EReference getComment_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.Comment#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Comment#getDescription()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.Item <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Item
	 * @generated
	 */
	EClass getItem();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Item#getAddedBy <em>Added By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Added By</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Item#getAddedBy()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_AddedBy();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Item#getReview <em>Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Review</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Item#getReview()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_Review();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.User#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.User#getId()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.User#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.User#getEmail()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Email();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference <em>Task Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Reference</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.TaskReference
	 * @generated
	 */
	EClass getTaskReference();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getTaskId <em>Task Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task Id</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getTaskId()
	 * @see #getTaskReference()
	 * @generated
	 */
	EAttribute getTaskReference_TaskId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getRepositoryURL <em>Repository URL</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repository URL</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getRepositoryURL()
	 * @see #getTaskReference()
	 * @generated
	 */
	EAttribute getTaskReference_RepositoryURL();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewState <em>Review State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review State</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewState
	 * @generated
	 */
	EClass getReviewState();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup <em>Review Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review Group</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup
	 * @generated
	 */
	EClass getReviewGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviews <em>Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reviews</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviews()
	 * @see #getReviewGroup()
	 * @generated
	 */
	EReference getReviewGroup_Reviews();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviewGroupTask <em>Review Group Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Review Group Task</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getReviewGroupTask()
	 * @see #getReviewGroup()
	 * @generated
	 */
	EReference getReviewGroup_ReviewGroupTask();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup#getDescription()
	 * @see #getReviewGroup()
	 * @generated
	 */
	EAttribute getReviewGroup_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.CommentType <em>Comment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment Type</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.CommentType
	 * @generated
	 */
	EClass getCommentType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic <em>Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Topic</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic
	 * @generated
	 */
	EClass getTopic();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Task</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic#getTask()
	 * @see #getTopic()
	 * @generated
	 */
	EReference getTopic_Task();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Location</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic#getLocation()
	 * @see #getTopic()
	 * @generated
	 */
	EReference getTopic_Location();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Comments</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic#getComments()
	 * @see #getTopic()
	 * @generated
	 */
	EReference getTopic_Comments();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getReview <em>Review</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Review</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic#getReview()
	 * @see #getTopic()
	 * @generated
	 */
	EReference getTopic_Review();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic#getTitle()
	 * @see #getTopic()
	 * @generated
	 */
	EAttribute getTopic_Title();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent <em>Review Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Review Component</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent
	 * @generated
	 */
	EClass getReviewComponent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent#isEnabled()
	 * @see #getReviewComponent()
	 * @generated
	 */
	EAttribute getReviewComponent_Enabled();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot <em>Sub Model Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub Model Root</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot
	 * @generated
	 */
	EClass getSubModelRoot();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getFragmentVersion <em>Fragment Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fragment Version</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getFragmentVersion()
	 * @see #getSubModelRoot()
	 * @generated
	 */
	EAttribute getSubModelRoot_FragmentVersion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility <em>Compatibility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Compatibility</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility()
	 * @see #getSubModelRoot()
	 * @generated
	 */
	EAttribute getSubModelRoot_Compatibility();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion <em>Application Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Application Version</em>'.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion()
	 * @see #getSubModelRoot()
	 * @generated
	 */
	EAttribute getSubModelRoot_ApplicationVersion();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl <em>Review</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReview()
		 * @generated
		 */
		EClass REVIEW = eINSTANCE.getReview();

		/**
		 * The meta object literal for the '<em><b>Topics</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW__TOPICS = eINSTANCE.getReview_Topics();

		/**
		 * The meta object literal for the '<em><b>Review Items</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW__REVIEW_ITEMS = eINSTANCE.getReview_ReviewItems();

		/**
		 * The meta object literal for the '<em><b>Review Task</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW__REVIEW_TASK = eINSTANCE.getReview_ReviewTask();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW__STATE = eINSTANCE.getReview_State();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.CommentImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT__USER = eINSTANCE.getComment_User();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMENT__TYPE = eINSTANCE.getComment_Type();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__DESCRIPTION = eINSTANCE.getComment_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ItemImpl <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ItemImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getItem()
		 * @generated
		 */
		EClass ITEM = eINSTANCE.getItem();

		/**
		 * The meta object literal for the '<em><b>Added By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM__ADDED_BY = eINSTANCE.getItem_AddedBy();

		/**
		 * The meta object literal for the '<em><b>Review</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM__REVIEW = eINSTANCE.getItem_Review();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.LocationImpl <em>Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.LocationImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getLocation()
		 * @generated
		 */
		EClass LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.UserImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__ID = eINSTANCE.getUser_Id();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__EMAIL = eINSTANCE.getUser_Email();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.TaskReferenceImpl <em>Task Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.TaskReferenceImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getTaskReference()
		 * @generated
		 */
		EClass TASK_REFERENCE = eINSTANCE.getTaskReference();

		/**
		 * The meta object literal for the '<em><b>Task Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_REFERENCE__TASK_ID = eINSTANCE.getTaskReference_TaskId();

		/**
		 * The meta object literal for the '<em><b>Repository URL</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_REFERENCE__REPOSITORY_URL = eINSTANCE.getTaskReference_RepositoryURL();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewStateImpl <em>Review State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewStateImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReviewState()
		 * @generated
		 */
		EClass REVIEW_STATE = eINSTANCE.getReviewState();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl <em>Review Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReviewGroup()
		 * @generated
		 */
		EClass REVIEW_GROUP = eINSTANCE.getReviewGroup();

		/**
		 * The meta object literal for the '<em><b>Reviews</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW_GROUP__REVIEWS = eINSTANCE.getReviewGroup_Reviews();

		/**
		 * The meta object literal for the '<em><b>Review Group Task</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REVIEW_GROUP__REVIEW_GROUP_TASK = eINSTANCE.getReviewGroup_ReviewGroupTask();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW_GROUP__DESCRIPTION = eINSTANCE.getReviewGroup_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.CommentTypeImpl <em>Comment Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.CommentTypeImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getCommentType()
		 * @generated
		 */
		EClass COMMENT_TYPE = eINSTANCE.getCommentType();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.TopicImpl <em>Topic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.TopicImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getTopic()
		 * @generated
		 */
		EClass TOPIC = eINSTANCE.getTopic();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOPIC__TASK = eINSTANCE.getTopic_Task();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOPIC__LOCATION = eINSTANCE.getTopic_Location();

		/**
		 * The meta object literal for the '<em><b>Comments</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOPIC__COMMENTS = eINSTANCE.getTopic_Comments();

		/**
		 * The meta object literal for the '<em><b>Review</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TOPIC__REVIEW = eINSTANCE.getTopic_Review();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TOPIC__TITLE = eINSTANCE.getTopic_Title();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewComponentImpl <em>Review Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewComponentImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getReviewComponent()
		 * @generated
		 */
		EClass REVIEW_COMPONENT = eINSTANCE.getReviewComponent();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REVIEW_COMPONENT__ENABLED = eINSTANCE.getReviewComponent_Enabled();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl <em>Sub Model Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.SubModelRootImpl
		 * @see org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl#getSubModelRoot()
		 * @generated
		 */
		EClass SUB_MODEL_ROOT = eINSTANCE.getSubModelRoot();

		/**
		 * The meta object literal for the '<em><b>Fragment Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_MODEL_ROOT__FRAGMENT_VERSION = eINSTANCE.getSubModelRoot_FragmentVersion();

		/**
		 * The meta object literal for the '<em><b>Compatibility</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_MODEL_ROOT__COMPATIBILITY = eINSTANCE.getSubModelRoot_Compatibility();

		/**
		 * The meta object literal for the '<em><b>Application Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_MODEL_ROOT__APPLICATION_VERSION = eINSTANCE.getSubModelRoot_ApplicationVersion();

	}

} //ModelPackage
