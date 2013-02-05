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
package org.eclipse.mylyn.reviews.r4e.core.model.util;

import java.util.Date;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.mylyn.reviews.core.model.IComment;
import org.eclipse.mylyn.reviews.core.model.ICommentType;
import org.eclipse.mylyn.reviews.core.model.IDated;
import org.eclipse.mylyn.reviews.core.model.IIndexed;
import org.eclipse.mylyn.reviews.core.model.ILocation;
import org.eclipse.mylyn.reviews.core.model.IModelVersioning;
import org.eclipse.mylyn.reviews.core.model.IReview;
import org.eclipse.mylyn.reviews.core.model.IReviewComponent;
import org.eclipse.mylyn.reviews.core.model.IReviewGroup;
import org.eclipse.mylyn.reviews.core.model.IReviewItem;
import org.eclipse.mylyn.reviews.core.model.IReviewState;
import org.eclipse.mylyn.reviews.core.model.ITaskReference;
import org.eclipse.mylyn.reviews.core.model.ITopic;
import org.eclipse.mylyn.reviews.core.model.ITopicContainer;
import org.eclipse.mylyn.reviews.core.model.IUser;
import org.eclipse.mylyn.reviews.r4e.core.model.*;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage
 * @generated
 */
public class RModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static RModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RModelSwitch() {
		if (modelPackage == null) {
			modelPackage = RModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case RModelPackage.R4E_REVIEW_GROUP: {
				R4EReviewGroup r4EReviewGroup = (R4EReviewGroup)theEObject;
				T result = caseR4EReviewGroup(r4EReviewGroup);
				if (result == null) result = caseReviewGroup(r4EReviewGroup);
				if (result == null) result = caseR4EReviewComponent(r4EReviewGroup);
				if (result == null) result = caseModelVersioning(r4EReviewGroup);
				if (result == null) result = caseReviewComponent(r4EReviewGroup);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_REVIEW: {
				R4EReview r4EReview = (R4EReview)theEObject;
				T result = caseR4EReview(r4EReview);
				if (result == null) result = caseReview(r4EReview);
				if (result == null) result = caseR4EReviewComponent(r4EReview);
				if (result == null) result = caseModelVersioning(r4EReview);
				if (result == null) result = caseTopicContainer(r4EReview);
				if (result == null) result = caseDated(r4EReview);
				if (result == null) result = caseReviewComponent(r4EReview);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_ANOMALY: {
				R4EAnomaly r4EAnomaly = (R4EAnomaly)theEObject;
				T result = caseR4EAnomaly(r4EAnomaly);
				if (result == null) result = caseTopic(r4EAnomaly);
				if (result == null) result = caseR4EComment(r4EAnomaly);
				if (result == null) result = caseComment(r4EAnomaly);
				if (result == null) result = caseR4EIDComponent(r4EAnomaly);
				if (result == null) result = caseReviewComponent(r4EAnomaly);
				if (result == null) result = caseIndexed(r4EAnomaly);
				if (result == null) result = caseDated(r4EAnomaly);
				if (result == null) result = caseR4EReviewComponent(r4EAnomaly);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_FORMAL_REVIEW: {
				R4EFormalReview r4EFormalReview = (R4EFormalReview)theEObject;
				T result = caseR4EFormalReview(r4EFormalReview);
				if (result == null) result = caseR4EReview(r4EFormalReview);
				if (result == null) result = caseReview(r4EFormalReview);
				if (result == null) result = caseR4EReviewComponent(r4EFormalReview);
				if (result == null) result = caseModelVersioning(r4EFormalReview);
				if (result == null) result = caseTopicContainer(r4EFormalReview);
				if (result == null) result = caseDated(r4EFormalReview);
				if (result == null) result = caseReviewComponent(r4EFormalReview);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_TEXT_POSITION: {
				R4ETextPosition r4ETextPosition = (R4ETextPosition)theEObject;
				T result = caseR4ETextPosition(r4ETextPosition);
				if (result == null) result = caseR4EPosition(r4ETextPosition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_REVIEW_DECISION: {
				R4EReviewDecision r4EReviewDecision = (R4EReviewDecision)theEObject;
				T result = caseR4EReviewDecision(r4EReviewDecision);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_USER: {
				R4EUser r4EUser = (R4EUser)theEObject;
				T result = caseR4EUser(r4EUser);
				if (result == null) result = caseUser(r4EUser);
				if (result == null) result = caseR4EReviewComponent(r4EUser);
				if (result == null) result = caseReviewComponent(r4EUser);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_PARTICIPANT: {
				R4EParticipant r4EParticipant = (R4EParticipant)theEObject;
				T result = caseR4EParticipant(r4EParticipant);
				if (result == null) result = caseR4EUser(r4EParticipant);
				if (result == null) result = caseUser(r4EParticipant);
				if (result == null) result = caseR4EReviewComponent(r4EParticipant);
				if (result == null) result = caseReviewComponent(r4EParticipant);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_ITEM: {
				R4EItem r4EItem = (R4EItem)theEObject;
				T result = caseR4EItem(r4EItem);
				if (result == null) result = caseR4EIDComponent(r4EItem);
				if (result == null) result = caseReviewItem(r4EItem);
				if (result == null) result = caseR4EReviewComponent(r4EItem);
				if (result == null) result = caseTopicContainer(r4EItem);
				if (result == null) result = caseReviewComponent(r4EItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_TEXT_CONTENT: {
				R4ETextContent r4ETextContent = (R4ETextContent)theEObject;
				T result = caseR4ETextContent(r4ETextContent);
				if (result == null) result = caseR4EContent(r4ETextContent);
				if (result == null) result = caseLocation(r4ETextContent);
				if (result == null) result = caseIndexed(r4ETextContent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4EID: {
				R4EID r4EID = (R4EID)theEObject;
				T result = caseR4EID(r4EID);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_ANOMALY_TYPE: {
				R4EAnomalyType r4EAnomalyType = (R4EAnomalyType)theEObject;
				T result = caseR4EAnomalyType(r4EAnomalyType);
				if (result == null) result = caseCommentType(r4EAnomalyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_TASK_REFERENCE: {
				R4ETaskReference r4ETaskReference = (R4ETaskReference)theEObject;
				T result = caseR4ETaskReference(r4ETaskReference);
				if (result == null) result = caseTaskReference(r4ETaskReference);
				if (result == null) result = caseR4EReviewComponent(r4ETaskReference);
				if (result == null) result = caseReviewComponent(r4ETaskReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_REVIEW_STATE: {
				R4EReviewState r4EReviewState = (R4EReviewState)theEObject;
				T result = caseR4EReviewState(r4EReviewState);
				if (result == null) result = caseReviewState(r4EReviewState);
				if (result == null) result = caseReviewComponent(r4EReviewState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_COMMENT: {
				R4EComment r4EComment = (R4EComment)theEObject;
				T result = caseR4EComment(r4EComment);
				if (result == null) result = caseComment(r4EComment);
				if (result == null) result = caseR4EIDComponent(r4EComment);
				if (result == null) result = caseReviewComponent(r4EComment);
				if (result == null) result = caseIndexed(r4EComment);
				if (result == null) result = caseDated(r4EComment);
				if (result == null) result = caseR4EReviewComponent(r4EComment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_REVIEW_COMPONENT: {
				R4EReviewComponent r4EReviewComponent = (R4EReviewComponent)theEObject;
				T result = caseR4EReviewComponent(r4EReviewComponent);
				if (result == null) result = caseReviewComponent(r4EReviewComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_FILE_CONTEXT: {
				R4EFileContext r4EFileContext = (R4EFileContext)theEObject;
				T result = caseR4EFileContext(r4EFileContext);
				if (result == null) result = caseR4EIDComponent(r4EFileContext);
				if (result == null) result = caseR4EReviewComponent(r4EFileContext);
				if (result == null) result = caseReviewComponent(r4EFileContext);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_DELTA: {
				R4EDelta r4EDelta = (R4EDelta)theEObject;
				T result = caseR4EDelta(r4EDelta);
				if (result == null) result = caseR4EIDComponent(r4EDelta);
				if (result == null) result = caseR4EReviewComponent(r4EDelta);
				if (result == null) result = caseReviewComponent(r4EDelta);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_COMMENT_TYPE: {
				R4ECommentType r4ECommentType = (R4ECommentType)theEObject;
				T result = caseR4ECommentType(r4ECommentType);
				if (result == null) result = caseCommentType(r4ECommentType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_TO_ANOMALY_TYPE: {
				@SuppressWarnings("unchecked") Map.Entry<String, R4EAnomalyType> mapToAnomalyType = (Map.Entry<String, R4EAnomalyType>)theEObject;
				T result = caseMapToAnomalyType(mapToAnomalyType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_CONTENT: {
				R4EContent r4EContent = (R4EContent)theEObject;
				T result = caseR4EContent(r4EContent);
				if (result == null) result = caseLocation(r4EContent);
				if (result == null) result = caseIndexed(r4EContent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_POSITION: {
				R4EPosition r4EPosition = (R4EPosition)theEObject;
				T result = caseR4EPosition(r4EPosition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_FILE_VERSION: {
				R4EFileVersion r4EFileVersion = (R4EFileVersion)theEObject;
				T result = caseR4EFileVersion(r4EFileVersion);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_NAME_TO_REVIEW: {
				@SuppressWarnings("unchecked") Map.Entry<String, R4EReview> mapNameToReview = (Map.Entry<String, R4EReview>)theEObject;
				T result = caseMapNameToReview(mapNameToReview);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_TO_USERS: {
				@SuppressWarnings("unchecked") Map.Entry<String, R4EUser> mapToUsers = (Map.Entry<String, R4EUser>)theEObject;
				T result = caseMapToUsers(mapToUsers);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_USER_REVIEWS: {
				R4EUserReviews r4EUserReviews = (R4EUserReviews)theEObject;
				T result = caseR4EUserReviews(r4EUserReviews);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4EID_COMPONENT: {
				R4EIDComponent r4EIDComponent = (R4EIDComponent)theEObject;
				T result = caseR4EIDComponent(r4EIDComponent);
				if (result == null) result = caseR4EReviewComponent(r4EIDComponent);
				if (result == null) result = caseReviewComponent(r4EIDComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_ID_TO_COMPONENT: {
				@SuppressWarnings("unchecked") Map.Entry<R4EID, R4EIDComponent> mapIDToComponent = (Map.Entry<R4EID, R4EIDComponent>)theEObject;
				T result = caseMapIDToComponent(mapIDToComponent);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_USER_ID_TO_USER_REVIEWS: {
				@SuppressWarnings("unchecked") Map.Entry<String, R4EUserReviews> mapUserIDToUserReviews = (Map.Entry<String, R4EUserReviews>)theEObject;
				T result = caseMapUserIDToUserReviews(mapUserIDToUserReviews);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_ANOMALY_TEXT_POSITION: {
				R4EAnomalyTextPosition r4EAnomalyTextPosition = (R4EAnomalyTextPosition)theEObject;
				T result = caseR4EAnomalyTextPosition(r4EAnomalyTextPosition);
				if (result == null) result = caseR4ETextPosition(r4EAnomalyTextPosition);
				if (result == null) result = caseR4EPosition(r4EAnomalyTextPosition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_DATE_TO_DURATION: {
				@SuppressWarnings("unchecked") Map.Entry<Date, Integer> mapDateToDuration = (Map.Entry<Date, Integer>)theEObject;
				T result = caseMapDateToDuration(mapDateToDuration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.MAP_KEY_TO_INFO_ATTRIBUTES: {
				@SuppressWarnings("unchecked") Map.Entry<String, String> mapKeyToInfoAttributes = (Map.Entry<String, String>)theEObject;
				T result = caseMapKeyToInfoAttributes(mapKeyToInfoAttributes);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_REVIEW_PHASE_INFO: {
				R4EReviewPhaseInfo r4EReviewPhaseInfo = (R4EReviewPhaseInfo)theEObject;
				T result = caseR4EReviewPhaseInfo(r4EReviewPhaseInfo);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_MEETING_DATA: {
				R4EMeetingData r4EMeetingData = (R4EMeetingData)theEObject;
				T result = caseR4EMeetingData(r4EMeetingData);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case RModelPackage.R4E_MODEL_POSITION: {
				R4EModelPosition r4EModelPosition = (R4EModelPosition)theEObject;
				T result = caseR4EModelPosition(r4EModelPosition);
				if (result == null) result = caseR4EPosition(r4EModelPosition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Review Group</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Review Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EReviewGroup(R4EReviewGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Review</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EReview(R4EReview object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Anomaly</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Anomaly</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EAnomaly(R4EAnomaly object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Formal Review</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Formal Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EFormalReview(R4EFormalReview object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Text Position</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Text Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4ETextPosition(R4ETextPosition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Review Decision</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Review Decision</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EReviewDecision(R4EReviewDecision object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E User</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E User</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EUser(R4EUser object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Participant</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Participant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EParticipant(R4EParticipant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Item</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EItem(R4EItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Text Content</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Text Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4ETextContent(R4ETextContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4EID</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4EID</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EID(R4EID object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Anomaly Type</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Anomaly Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EAnomalyType(R4EAnomalyType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Task Reference</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Task Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4ETaskReference(R4ETaskReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Review State</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Review State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EReviewState(R4EReviewState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Comment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EComment(R4EComment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Review Component</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Review Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EReviewComponent(R4EReviewComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E File Context</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E File Context</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EFileContext(R4EFileContext object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Delta</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Delta</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EDelta(R4EDelta object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Comment Type</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Comment Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4ECommentType(R4ECommentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map To Anomaly Type</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map To Anomaly Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapToAnomalyType(Map.Entry<String, R4EAnomalyType> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Content</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Content</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EContent(R4EContent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Position</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EPosition(R4EPosition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E File Version</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E File Version</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EFileVersion(R4EFileVersion object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Name To Review</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Name To Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapNameToReview(Map.Entry<String, R4EReview> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map To Users</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map To Users</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapToUsers(Map.Entry<String, R4EUser> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E User Reviews</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E User Reviews</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EUserReviews(R4EUserReviews object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4EID Component</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4EID Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EIDComponent(R4EIDComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map ID To Component</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map ID To Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapIDToComponent(Map.Entry<R4EID, R4EIDComponent> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map User ID To User Reviews</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map User ID To User Reviews</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapUserIDToUserReviews(Map.Entry<String, R4EUserReviews> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Anomaly Text Position</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Anomaly Text Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EAnomalyTextPosition(R4EAnomalyTextPosition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Date To Duration</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Date To Duration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapDateToDuration(Map.Entry<Date, Integer> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Map Key To Info Attributes</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Map Key To Info Attributes</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMapKeyToInfoAttributes(Map.Entry<String, String> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Review Phase Info</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Review Phase Info</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EReviewPhaseInfo(R4EReviewPhaseInfo object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Meeting Data</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Meeting Data</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EMeetingData(R4EMeetingData object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Model Position</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Model Position</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EModelPosition(R4EModelPosition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review Component</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewComponent(IReviewComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewGroup(IReviewGroup object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Versioning</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Versioning</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelVersioning(IModelVersioning object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Topic Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Topic Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTopicContainer(ITopicContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Dated</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Dated</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDated(IDated object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReview(IReview object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Indexed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Indexed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIndexed(IIndexed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseComment(IComment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Topic</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Topic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTopic(ITopic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>User</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>User</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUser(IUser object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review Item</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewItem(IReviewItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Location</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Location</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLocation(ILocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Comment Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Comment Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCommentType(ICommentType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Reference</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskReference(ITaskReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewState(IReviewState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //RModelSwitch
