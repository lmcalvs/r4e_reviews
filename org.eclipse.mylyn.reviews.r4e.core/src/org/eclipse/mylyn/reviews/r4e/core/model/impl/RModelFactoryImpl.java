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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.team.core.history.IFileRevision;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 */
public class RModelFactoryImpl extends EFactoryImpl implements RModelFactory, Persistence.RModelFactoryExt {
	private static RModelFactoryExt factoryExtension = SerializeFactory.getModelExtension();

	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static RModelFactory init() {
		try {
			RModelFactory theRModelFactory = (RModelFactory) EPackage.Registry.INSTANCE.getEFactory("http://org.eclipse.mylyn.reviews.r4e.core.model/");
			if (theRModelFactory != null) {
				return theRModelFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new RModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case RModelPackage.R4E_REVIEW_GROUP:
			return createR4EReviewGroup();
		case RModelPackage.R4E_REVIEW:
			return createR4EReview();
		case RModelPackage.R4E_ANOMALY:
			return createR4EAnomaly();
		case RModelPackage.R4E_FORMAL_REVIEW:
			return createR4EFormalReview();
		case RModelPackage.R4E_TEXT_POSITION:
			return createR4ETextPosition();
		case RModelPackage.R4E_REVIEW_DECISION:
			return createR4EReviewDecision();
		case RModelPackage.R4E_USER:
			return createR4EUser();
		case RModelPackage.R4E_PARTICIPANT:
			return createR4EParticipant();
		case RModelPackage.R4E_ITEM:
			return createR4EItem();
		case RModelPackage.R4E_TEXT_CONTENT:
			return createR4ETextContent();
		case RModelPackage.R4EID:
			return createR4EID();
		case RModelPackage.R4E_ANOMALY_TYPE:
			return createR4EAnomalyType();
		case RModelPackage.R4E_TASK_REFERENCE:
			return createR4ETaskReference();
		case RModelPackage.R4E_REVIEW_STATE:
			return createR4EReviewState();
		case RModelPackage.R4E_COMMENT:
			return createR4EComment();
		case RModelPackage.R4E_REVIEW_COMPONENT:
			return createR4EReviewComponent();
		case RModelPackage.R4E_FILE_CONTEXT:
			return createR4EFileContext();
		case RModelPackage.R4E_DELTA:
			return createR4EDelta();
		case RModelPackage.R4E_COMMENT_TYPE:
			return createR4ECommentType();
		case RModelPackage.MAP_TO_ANOMALY_TYPE:
			return (EObject) createMapToAnomalyType();
		case RModelPackage.R4E_POSITION:
			return createR4EPosition();
		case RModelPackage.R4E_FILE_VERSION:
			return createR4EFileVersion();
		case RModelPackage.MAP_NAME_TO_REVIEW:
			return (EObject) createMapNameToReview();
		case RModelPackage.MAP_TO_USERS:
			return (EObject) createMapToUsers();
		case RModelPackage.R4E_USER_REVIEWS:
			return createR4EUserReviews();
		case RModelPackage.R4EID_COMPONENT:
			return createR4EIDComponent();
		case RModelPackage.MAP_ID_TO_COMPONENT:
			return (EObject) createMapIDToComponent();
		case RModelPackage.MAP_USER_ID_TO_USER_REVIEWS:
			return (EObject) createMapUserIDToUserReviews();
		case RModelPackage.R4E_ANOMALY_TEXT_POSITION:
			return createR4EAnomalyTextPosition();
		case RModelPackage.MAP_DATE_TO_DURATION:
			return (EObject) createMapDateToDuration();
		case RModelPackage.MAP_KEY_TO_INFO_ATTRIBUTES:
			return (EObject) createMapKeyToInfoAttributes();
		case RModelPackage.R4E_REVIEW_PHASE_INFO:
			return createR4EReviewPhaseInfo();
		case RModelPackage.R4E_MEETING_DATA:
			return createR4EMeetingData();
		case RModelPackage.R4E_MODEL_POSITION:
			return createR4EModelPosition();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case RModelPackage.R4E_ANOMALY_STATE:
			return createR4EAnomalyStateFromString(eDataType, initialValue);
		case RModelPackage.R4E_REVIEW_PHASE:
			return createR4EReviewPhaseFromString(eDataType, initialValue);
		case RModelPackage.R4E_USER_ROLE:
			return createR4EUserRoleFromString(eDataType, initialValue);
		case RModelPackage.R4E_DECISION:
			return createR4EDecisionFromString(eDataType, initialValue);
		case RModelPackage.R4E_REVIEW_TYPE:
			return createR4EReviewTypeFromString(eDataType, initialValue);
		case RModelPackage.R4E_CONTEXT_TYPE:
			return createR4EContextTypeFromString(eDataType, initialValue);
		case RModelPackage.IRESOURCE:
			return createIResourceFromString(eDataType, initialValue);
		case RModelPackage.IFILE_REVISION:
			return createIFileRevisionFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case RModelPackage.R4E_ANOMALY_STATE:
			return convertR4EAnomalyStateToString(eDataType, instanceValue);
		case RModelPackage.R4E_REVIEW_PHASE:
			return convertR4EReviewPhaseToString(eDataType, instanceValue);
		case RModelPackage.R4E_USER_ROLE:
			return convertR4EUserRoleToString(eDataType, instanceValue);
		case RModelPackage.R4E_DECISION:
			return convertR4EDecisionToString(eDataType, instanceValue);
		case RModelPackage.R4E_REVIEW_TYPE:
			return convertR4EReviewTypeToString(eDataType, instanceValue);
		case RModelPackage.R4E_CONTEXT_TYPE:
			return convertR4EContextTypeToString(eDataType, instanceValue);
		case RModelPackage.IRESOURCE:
			return convertIResourceToString(eDataType, instanceValue);
		case RModelPackage.IFILE_REVISION:
			return convertIFileRevisionToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewGroup createR4EReviewGroup() {
		R4EReviewGroupImpl r4EReviewGroup = new R4EReviewGroupImpl();
		return r4EReviewGroup;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReview createR4EReview() {
		R4EReviewImpl r4EReview = new R4EReviewImpl();
		return r4EReview;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EAnomaly createR4EAnomaly() {
		R4EAnomalyImpl r4EAnomaly = new R4EAnomalyImpl();
		return r4EAnomaly;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EFormalReview createR4EFormalReview() {
		R4EFormalReviewImpl r4EFormalReview = new R4EFormalReviewImpl();
		return r4EFormalReview;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4ETextPosition createR4ETextPosition() {
		R4ETextPositionImpl r4ETextPosition = new R4ETextPositionImpl();
		return r4ETextPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewDecision createR4EReviewDecision() {
		R4EReviewDecisionImpl r4EReviewDecision = new R4EReviewDecisionImpl();
		return r4EReviewDecision;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EUser createR4EUser() {
		R4EUserImpl r4EUser = new R4EUserImpl();
		return r4EUser;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EParticipant createR4EParticipant() {
		R4EParticipantImpl r4EParticipant = new R4EParticipantImpl();
		return r4EParticipant;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EItem createR4EItem() {
		R4EItemImpl r4EItem = new R4EItemImpl();
		return r4EItem;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4ETextContent createR4ETextContent() {
		R4ETextContentImpl r4ETextContent = new R4ETextContentImpl();
		return r4ETextContent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EID createR4EID() {
		R4EIDImpl r4EID = new R4EIDImpl();
		return r4EID;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EAnomalyType createR4EAnomalyType() {
		R4EAnomalyTypeImpl r4EAnomalyType = new R4EAnomalyTypeImpl();
		return r4EAnomalyType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4ETaskReference createR4ETaskReference() {
		R4ETaskReferenceImpl r4ETaskReference = new R4ETaskReferenceImpl();
		return r4ETaskReference;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewState createR4EReviewState() {
		R4EReviewStateImpl r4EReviewState = new R4EReviewStateImpl();
		return r4EReviewState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EComment createR4EComment() {
		R4ECommentImpl r4EComment = new R4ECommentImpl();
		return r4EComment;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewComponent createR4EReviewComponent() {
		R4EReviewComponentImpl r4EReviewComponent = new R4EReviewComponentImpl();
		return r4EReviewComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EFileContext createR4EFileContext() {
		R4EFileContextImpl r4EFileContext = new R4EFileContextImpl();
		return r4EFileContext;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EDelta createR4EDelta() {
		R4EDeltaImpl r4EDelta = new R4EDeltaImpl();
		return r4EDelta;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EFileVersion createR4EFileVersion() {
		R4EFileVersionImpl r4EFileVersion = new R4EFileVersionImpl();
		return r4EFileVersion;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, R4EReview> createMapNameToReview() {
		MapNameToReviewImpl mapNameToReview = new MapNameToReviewImpl();
		return mapNameToReview;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, R4EUser> createMapToUsers() {
		MapToUsersImpl mapToUsers = new MapToUsersImpl();
		return mapToUsers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EUserReviews createR4EUserReviews() {
		R4EUserReviewsImpl r4EUserReviews = new R4EUserReviewsImpl();
		return r4EUserReviews;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EIDComponent createR4EIDComponent() {
		R4EIDComponentImpl r4EIDComponent = new R4EIDComponentImpl();
		return r4EIDComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<R4EID, R4EIDComponent> createMapIDToComponent() {
		MapIDToComponentImpl mapIDToComponent = new MapIDToComponentImpl();
		return mapIDToComponent;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, R4EUserReviews> createMapUserIDToUserReviews() {
		MapUserIDToUserReviewsImpl mapUserIDToUserReviews = new MapUserIDToUserReviewsImpl();
		return mapUserIDToUserReviews;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EAnomalyTextPosition createR4EAnomalyTextPosition() {
		R4EAnomalyTextPositionImpl r4EAnomalyTextPosition = new R4EAnomalyTextPositionImpl();
		return r4EAnomalyTextPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<Date, Integer> createMapDateToDuration() {
		MapDateToDurationImpl mapDateToDuration = new MapDateToDurationImpl();
		return mapDateToDuration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, String> createMapKeyToInfoAttributes() {
		MapKeyToInfoAttributesImpl mapKeyToInfoAttributes = new MapKeyToInfoAttributesImpl();
		return mapKeyToInfoAttributes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewPhaseInfo createR4EReviewPhaseInfo() {
		R4EReviewPhaseInfoImpl r4EReviewPhaseInfo = new R4EReviewPhaseInfoImpl();
		return r4EReviewPhaseInfo;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EMeetingData createR4EMeetingData() {
		R4EMeetingDataImpl r4EMeetingData = new R4EMeetingDataImpl();
		return r4EMeetingData;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EModelPosition createR4EModelPosition() {
		R4EModelPositionImpl r4EModelPosition = new R4EModelPositionImpl();
		return r4EModelPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4ECommentType createR4ECommentType() {
		R4ECommentTypeImpl r4ECommentType = new R4ECommentTypeImpl();
		return r4ECommentType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Map.Entry<String, R4EAnomalyType> createMapToAnomalyType() {
		MapToAnomalyTypeImpl mapToAnomalyType = new MapToAnomalyTypeImpl();
		return mapToAnomalyType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EPosition createR4EPosition() {
		R4EPositionImpl r4EPosition = new R4EPositionImpl();
		return r4EPosition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EAnomalyState createR4EAnomalyState(String literal) {
		R4EAnomalyState result = R4EAnomalyState.get(literal);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + literal + "' is not a valid enumerator of '"
					+ RModelPackage.Literals.R4E_ANOMALY_STATE.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EAnomalyState createR4EAnomalyStateFromString(EDataType eDataType, String initialValue) {
		return createR4EAnomalyState(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EAnomalyState(R4EAnomalyState instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EAnomalyStateToString(EDataType eDataType, Object instanceValue) {
		return convertR4EAnomalyState((R4EAnomalyState) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewPhase createR4EReviewPhase(String literal) {
		R4EReviewPhase result = R4EReviewPhase.get(literal);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + literal + "' is not a valid enumerator of '"
					+ RModelPackage.Literals.R4E_REVIEW_PHASE.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewPhase createR4EReviewPhaseFromString(EDataType eDataType, String initialValue) {
		return createR4EReviewPhase(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EReviewPhase(R4EReviewPhase instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EReviewPhaseToString(EDataType eDataType, Object instanceValue) {
		return convertR4EReviewPhase((R4EReviewPhase) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EUserRole createR4EUserRole(String literal) {
		R4EUserRole result = R4EUserRole.get(literal);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + literal + "' is not a valid enumerator of '"
					+ RModelPackage.Literals.R4E_USER_ROLE.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EUserRole createR4EUserRoleFromString(EDataType eDataType, String initialValue) {
		return createR4EUserRole(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EUserRole(R4EUserRole instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EUserRoleToString(EDataType eDataType, Object instanceValue) {
		return convertR4EUserRole((R4EUserRole) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EDecision createR4EDecision(String literal) {
		R4EDecision result = R4EDecision.get(literal);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + literal + "' is not a valid enumerator of '"
					+ RModelPackage.Literals.R4E_DECISION.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EDecision createR4EDecisionFromString(EDataType eDataType, String initialValue) {
		return createR4EDecision(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EDecision(R4EDecision instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EDecisionToString(EDataType eDataType, Object instanceValue) {
		return convertR4EDecision((R4EDecision) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewType createR4EReviewType(String literal) {
		R4EReviewType result = R4EReviewType.get(literal);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + literal + "' is not a valid enumerator of '"
					+ RModelPackage.Literals.R4E_REVIEW_TYPE.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EReviewType createR4EReviewTypeFromString(EDataType eDataType, String initialValue) {
		return createR4EReviewType(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EReviewType(R4EReviewType instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EReviewTypeToString(EDataType eDataType, Object instanceValue) {
		return convertR4EReviewType((R4EReviewType) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EContextType createR4EContextType(String literal) {
		R4EContextType result = R4EContextType.get(literal);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + literal + "' is not a valid enumerator of '"
					+ RModelPackage.Literals.R4E_CONTEXT_TYPE.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public R4EContextType createR4EContextTypeFromString(EDataType eDataType, String initialValue) {
		return createR4EContextType(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EContextType(R4EContextType instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertR4EContextTypeToString(EDataType eDataType, Object instanceValue) {
		return convertR4EContextType((R4EContextType) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IResource createIResource(String literal) {
		return (IResource) super.createFromString(RModelPackage.Literals.IRESOURCE, literal);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IResource createIResourceFromString(EDataType eDataType, String initialValue) {
		return createIResource(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertIResource(IResource instanceValue) {
		return super.convertToString(RModelPackage.Literals.IRESOURCE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertIResourceToString(EDataType eDataType, Object instanceValue) {
		return convertIResource((IResource) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public IFileRevision createIFileRevision(String literal) {
		return (IFileRevision) super.createFromString(RModelPackage.Literals.IFILE_REVISION, literal);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc --> <br>
	 * This is a transient field from version 0.10, the value is derived at loading so the null is only to support
	 * reading reviews created before 0.10
	 */
	public IFileRevision createIFileRevisionFromString(EDataType eDataType, String initialValue) {
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertIFileRevision(IFileRevision instanceValue) {
		return super.convertToString(RModelPackage.Literals.IFILE_REVISION, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertIFileRevisionToString(EDataType eDataType, Object instanceValue) {
		return convertIFileRevision((IFileRevision) instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RModelPackage getRModelPackage() {
		return (RModelPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static RModelPackage getPackage() {
		return RModelPackage.eINSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.GroupResFactory#createR4EReviewGroup(org.eclipse.
	 * emf.common.util.URI, java.lang.String)
	 */
	public R4EReviewGroup createR4EReviewGroup(URI aResourcePath, String aGroupName) throws ResourceHandlingException {
		return factoryExtension.createR4EReviewGroup(aResourcePath, aGroupName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.GroupResFactory#openR4EReviewGroup(org.eclipse.emf
	 * .common.util.URI)
	 */
	public R4EReviewGroup openR4EReviewGroup(URI aResourcePath) throws ResourceHandlingException,
			CompatibilityException {
		return factoryExtension.openR4EReviewGroup(aResourcePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.GroupResFactory#closeR4EReviewGroup(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EReviewGroup)
	 */
	public String closeR4EReviewGroup(R4EReviewGroup aReviewGroup) {
		return factoryExtension.closeR4EReviewGroup(aReviewGroup);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#createR4EReview(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EReviewGroup, java.lang.String, java.lang.String)
	 */
	public R4EReview createR4EReview(R4EReviewGroup aRviewGroup, String aReviewName, String aCreatedByUser)
			throws ResourceHandlingException {
		return factoryExtension.createR4EReview(aRviewGroup, aReviewName, aCreatedByUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#createR4EFormalReview(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EReviewGroup, java.lang.String, java.lang.String)
	 */
	public R4EFormalReview createR4EFormalReview(R4EReviewGroup aRviewGroup, String aReviewName, String aCreatedByUser)
			throws ResourceHandlingException {
		return factoryExtension.createR4EFormalReview(aRviewGroup, aReviewName, aCreatedByUser);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#createR4EReviewPhaseInfo(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EFormalReview)
	 */
	public R4EReviewPhaseInfo createR4EReviewPhaseInfo(R4EFormalReview review) throws ResourceHandlingException {
		return factoryExtension.createR4EReviewPhaseInfo(review);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#createR4EMeetingData(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EReview)
	 */
	public R4EMeetingData createR4EMeetingData(R4EReview aReview) throws ResourceHandlingException {
		return factoryExtension.createR4EMeetingData(aReview);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#openR4EReview(org.eclipse.mylyn.
	 * reviews.r4e.core.model.R4EReviewGroup, java.lang.String)
	 */
	public R4EReview openR4EReview(R4EReviewGroup aRreviewGroup, String aReviewName) throws ResourceHandlingException,
			CompatibilityException {
		return factoryExtension.openR4EReview(aRreviewGroup, aReviewName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#closeR4EReview(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EReview)
	 */
	public String closeR4EReview(R4EReview aReview) {
		return factoryExtension.closeR4EReview(aReview);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ReviewResFactory#deleteR4EReview(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EReview, boolean)
	 */
	public String deleteR4EReview(R4EReview aReview, boolean aDeleteOnDisk) throws ResourceHandlingException {
		return factoryExtension.deleteR4EReview(aReview, aDeleteOnDisk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4EItem(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EParticipant)
	 */
	public R4EItem createR4EItem(R4EParticipant aParticipant) throws ResourceHandlingException {
		return factoryExtension.createR4EItem(aParticipant);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#deleteR4EItem(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EItem, boolean)
	 */
	public void deleteR4EItem(R4EItem aItem, boolean aDeleteOnDisk) throws ResourceHandlingException {
		factoryExtension.deleteR4EItem(aItem, aDeleteOnDisk);
		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4EFileContext(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EItem)
	 */
	public R4EFileContext createR4EFileContext(R4EItem item) throws ResourceHandlingException {
		return factoryExtension.createR4EFileContext(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4EFileVersion(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EFileContext)
	 */
	public R4EFileVersion createR4EBaseFileVersion(R4EFileContext context) throws ResourceHandlingException {
		return factoryExtension.createR4EBaseFileVersion(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4ETargetFileVersion(org
	 * .eclipse.mylyn.reviews.r4e.core.model.R4EFileContext)
	 */
	public R4EFileVersion createR4ETargetFileVersion(R4EFileContext context) throws ResourceHandlingException {
		return factoryExtension.createR4ETargetFileVersion(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4EDelta(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EFileContext, org.eclipse.mylyn.reviews.r4e.core.model.R4EUser)
	 */
	public R4EDelta createR4EDelta(R4EFileContext context) throws ResourceHandlingException {
		return factoryExtension.createR4EDelta(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#deleteR4EDelta(org.eclipse.mylyn
	 * .reviews.r4e.core.model.R4EDelta)
	 */
	public void deleteR4EDelta(R4EDelta delta) throws ResourceHandlingException {
		factoryExtension.deleteR4EDelta(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4ETextContent(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EDelta)
	 */
	public R4ETextContent createR4EBaseTextContent(R4EDelta delta) throws ResourceHandlingException {
		return factoryExtension.createR4EBaseTextContent(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4ETargetTextContent(org
	 * .eclipse.mylyn.reviews.r4e.core.model.R4EDelta)
	 */
	public R4ETextContent createR4ETargetTextContent(R4EDelta delta) throws ResourceHandlingException {
		return factoryExtension.createR4ETargetTextContent(delta);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4ETextPosition(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4ETextContent)
	 */
	public R4ETextPosition createR4ETextPosition(R4ETextContent content) throws ResourceHandlingException {
		return factoryExtension.createR4ETextPosition(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#createR4EParticipant(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EReview, java.lang.String, java.util.List)
	 */
	public R4EParticipant createR4EParticipant(R4EReview aReview, String aParticipantId, List<R4EUserRole> aRoles)
			throws ResourceHandlingException {
		return factoryExtension.createR4EParticipant(aReview, aParticipantId, aRoles);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#createR4EAnomaly(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EParticipant)
	 */
	public R4EAnomaly createR4EAnomaly(R4EParticipant aAnomalyCreator) throws ResourceHandlingException {
		return factoryExtension.createR4EAnomaly(aAnomalyCreator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#createR4EComment(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EParticipant, org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly)
	 */
	public R4EComment createR4EComment(R4EParticipant aParticipant, R4EAnomaly aContainerAnomaly)
			throws ResourceHandlingException {
		return factoryExtension.createR4EComment(aParticipant, aContainerAnomaly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#createR4ETextContent(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EAnomaly)
	 */
	public R4ETextContent createR4ETextContent(R4EAnomaly anomaly) throws ResourceHandlingException {
		return factoryExtension.createR4ETextContent(anomaly);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#creeateR4EAnomalyTextPosition
	 * (org.eclipse.mylyn.reviews.r4e.core.model.R4EContent)
	 */
	public R4ETextPosition createR4EAnomalyTextPosition(R4EContent content) throws ResourceHandlingException {
		return factoryExtension.createR4EAnomalyTextPosition(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#createR4EAnomalyTextPosition
	 * (org.eclipse.mylyn.reviews.r4e.core.model.R4EContent)
	 */
	public R4EModelPosition createR4EAnomalyModelPosition(R4EContent content) throws ResourceHandlingException {
		return factoryExtension.createR4EAnomalyModelPosition(content);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#createR4EFileVersion(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EPosition)
	 */
	public R4EFileVersion createR4EFileVersion(R4EPosition position) throws ResourceHandlingException {
		return factoryExtension.createR4EFileVersion(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#deleteR4EComment(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EComment, boolean)
	 */
	public void deleteR4EComment(R4EComment aComment, boolean aDeleteOnDisk) throws ResourceHandlingException {
		factoryExtension.deleteR4EComment(aComment, aDeleteOnDisk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserCommentResFactory#deleteR4EAnomaly(org.eclipse
	 * .mylyn.reviews.r4e.core.model.R4EAnomaly, boolean)
	 */
	public void deleteR4EAnomaly(R4EAnomaly aAnomaly, boolean aDeleteOnDisk) throws ResourceHandlingException {
		factoryExtension.deleteR4EAnomaly(aAnomaly, aDeleteOnDisk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence#testReadWritePermissions(org.eclipse.emf.common.util
	 * .URI)
	 */
	public boolean testWritePermissions(URI aLocation) throws ResourceHandlingException {
		return factoryExtension.testWritePermissions(aLocation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence#PollDirUpdates(org.eclipse.emf.ecore.EObject)
	 */
	public List<Resource> pollDirUpdates(EObject atElementLoc) {
		return factoryExtension.pollDirUpdates(atElementLoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.DRulesFactory#createR4EDesignRuleCollection(org.eclipse
	 * .emf.common.util.URI, java.lang.String)
	 */
	public R4EDesignRuleCollection createR4EDesignRuleCollection(URI aFolderPath, String aRuleCollectionName)
			throws ResourceHandlingException {
		return factoryExtension.createR4EDesignRuleCollection(aFolderPath, aRuleCollectionName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.DRulesFactory#openR4EDesignRuleCollection(org.eclipse
	 * .emf.common.util.URI)
	 */
	public R4EDesignRuleCollection openR4EDesignRuleCollection(URI aResourcePath) throws ResourceHandlingException,
			CompatibilityException {
		return factoryExtension.openR4EDesignRuleCollection(aResourcePath);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.DRulesFactory#closeR4EDesignRuleCollection(org.eclipse
	 * .mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection)
	 */
	public String closeR4EDesignRuleCollection(R4EDesignRuleCollection aDesRuleCollection) {
		return factoryExtension.closeR4EDesignRuleCollection(aDesRuleCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.DRulesFactory#createR4EDesignRuleArea(org.eclipse
	 * .mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection)
	 */
	public R4EDesignRuleArea createR4EDesignRuleArea(R4EDesignRuleCollection aRuleCollection)
			throws ResourceHandlingException {
		return factoryExtension.createR4EDesignRuleArea(aRuleCollection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.DRulesFactory#createR4EDesignRuleViolation(org.eclipse
	 * .mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection)
	 */
	public R4EDesignRuleViolation createR4EDesignRuleViolation(R4EDesignRuleArea aRuleArea)
			throws ResourceHandlingException {
		return factoryExtension.createR4EDesignRuleViolation(aRuleArea);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.DRulesFactory#createR4EDesignRule(org.eclipse.mylyn
	 * .reviews.r4e.core.model.drules.R4EDesignRuleViolation)
	 */
	public R4EDesignRule createR4EDesignRule(R4EDesignRuleViolation aViolation) throws ResourceHandlingException {
		return factoryExtension.createR4EDesignRule(aViolation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ModelAdapter#copyR4EReview(org.eclipse.emf.common
	 * .util.URI, org.eclipse.emf.common.util.URI, java.lang.String, java.lang.String)
	 */
	public R4EReview copyR4EReview(URI origGroup, URI destGroup, String origReviewName, String destReviewName) {
		return factoryExtension.copyR4EReview(origGroup, destGroup, origReviewName, destReviewName);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.UserItemResFactory#createR4EModelPosition(org.eclipse.mylyn.reviews.r4e.core.model.R4EContent)
	 */
	public R4EModelPosition createR4EModelPosition(R4EContent content) throws ResourceHandlingException {
		return factoryExtension.createR4EModelPosition(content);
	}

} //RModelFactoryImpl
