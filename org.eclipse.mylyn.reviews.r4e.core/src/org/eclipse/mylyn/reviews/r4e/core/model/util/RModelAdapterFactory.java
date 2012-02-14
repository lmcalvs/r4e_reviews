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
package org.eclipse.mylyn.reviews.r4e.core.model.util;

import java.util.Date;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.reviews.frame.core.model.Comment;
import org.eclipse.mylyn.reviews.frame.core.model.CommentType;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.frame.core.model.Location;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewState;
import org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot;
import org.eclipse.mylyn.reviews.frame.core.model.TaskReference;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.frame.core.model.User;
import org.eclipse.mylyn.reviews.r4e.core.model.*;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage
 * @generated
 */
public class RModelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RModelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RModelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RModelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RModelSwitch<Adapter> modelSwitch =
		new RModelSwitch<Adapter>() {
			@Override
			public Adapter caseR4EReviewGroup(R4EReviewGroup object) {
				return createR4EReviewGroupAdapter();
			}
			@Override
			public Adapter caseR4EReview(R4EReview object) {
				return createR4EReviewAdapter();
			}
			@Override
			public Adapter caseR4EAnomaly(R4EAnomaly object) {
				return createR4EAnomalyAdapter();
			}
			@Override
			public Adapter caseR4EFormalReview(R4EFormalReview object) {
				return createR4EFormalReviewAdapter();
			}
			@Override
			public Adapter caseR4ETextPosition(R4ETextPosition object) {
				return createR4ETextPositionAdapter();
			}
			@Override
			public Adapter caseR4EReviewDecision(R4EReviewDecision object) {
				return createR4EReviewDecisionAdapter();
			}
			@Override
			public Adapter caseR4EUser(R4EUser object) {
				return createR4EUserAdapter();
			}
			@Override
			public Adapter caseR4EParticipant(R4EParticipant object) {
				return createR4EParticipantAdapter();
			}
			@Override
			public Adapter caseR4EItem(R4EItem object) {
				return createR4EItemAdapter();
			}
			@Override
			public Adapter caseR4ETextContent(R4ETextContent object) {
				return createR4ETextContentAdapter();
			}
			@Override
			public Adapter caseR4EID(R4EID object) {
				return createR4EIDAdapter();
			}
			@Override
			public Adapter caseR4EAnomalyType(R4EAnomalyType object) {
				return createR4EAnomalyTypeAdapter();
			}
			@Override
			public Adapter caseR4ETaskReference(R4ETaskReference object) {
				return createR4ETaskReferenceAdapter();
			}
			@Override
			public Adapter caseR4EReviewState(R4EReviewState object) {
				return createR4EReviewStateAdapter();
			}
			@Override
			public Adapter caseR4EComment(R4EComment object) {
				return createR4ECommentAdapter();
			}
			@Override
			public Adapter caseR4EReviewComponent(R4EReviewComponent object) {
				return createR4EReviewComponentAdapter();
			}
			@Override
			public Adapter caseR4EFileContext(R4EFileContext object) {
				return createR4EFileContextAdapter();
			}
			@Override
			public Adapter caseR4EDelta(R4EDelta object) {
				return createR4EDeltaAdapter();
			}
			@Override
			public Adapter caseR4ECommentType(R4ECommentType object) {
				return createR4ECommentTypeAdapter();
			}
			@Override
			public Adapter caseMapToAnomalyType(Map.Entry<String, R4EAnomalyType> object) {
				return createMapToAnomalyTypeAdapter();
			}
			@Override
			public Adapter caseR4EContent(R4EContent object) {
				return createR4EContentAdapter();
			}
			@Override
			public Adapter caseR4EPosition(R4EPosition object) {
				return createR4EPositionAdapter();
			}
			@Override
			public Adapter caseR4EFileVersion(R4EFileVersion object) {
				return createR4EFileVersionAdapter();
			}
			@Override
			public Adapter caseMapNameToReview(Map.Entry<String, R4EReview> object) {
				return createMapNameToReviewAdapter();
			}
			@Override
			public Adapter caseMapToUsers(Map.Entry<String, R4EUser> object) {
				return createMapToUsersAdapter();
			}
			@Override
			public Adapter caseR4EUserReviews(R4EUserReviews object) {
				return createR4EUserReviewsAdapter();
			}
			@Override
			public Adapter caseR4EIDComponent(R4EIDComponent object) {
				return createR4EIDComponentAdapter();
			}
			@Override
			public Adapter caseMapIDToComponent(Map.Entry<R4EID, R4EIDComponent> object) {
				return createMapIDToComponentAdapter();
			}
			@Override
			public Adapter caseMapUserIDToUserReviews(Map.Entry<String, R4EUserReviews> object) {
				return createMapUserIDToUserReviewsAdapter();
			}
			@Override
			public Adapter caseR4EAnomalyTextPosition(R4EAnomalyTextPosition object) {
				return createR4EAnomalyTextPositionAdapter();
			}
			@Override
			public Adapter caseMapDateToDuration(Map.Entry<Date, Integer> object) {
				return createMapDateToDurationAdapter();
			}
			@Override
			public Adapter caseMapKeyToInfoAttributes(Map.Entry<String, String> object) {
				return createMapKeyToInfoAttributesAdapter();
			}
			@Override
			public Adapter caseR4EReviewPhaseInfo(R4EReviewPhaseInfo object) {
				return createR4EReviewPhaseInfoAdapter();
			}
			@Override
			public Adapter caseR4EMeetingData(R4EMeetingData object) {
				return createR4EMeetingDataAdapter();
			}
			@Override
			public Adapter caseR4EModelPosition(R4EModelPosition object) {
				return createR4EModelPositionAdapter();
			}
			@Override
			public Adapter caseReviewComponent(ReviewComponent object) {
				return createReviewComponentAdapter();
			}
			@Override
			public Adapter caseSubModelRoot(SubModelRoot object) {
				return createSubModelRootAdapter();
			}
			@Override
			public Adapter caseReviewGroup(ReviewGroup object) {
				return createReviewGroupAdapter();
			}
			@Override
			public Adapter caseReview(Review object) {
				return createReviewAdapter();
			}
			@Override
			public Adapter caseComment(Comment object) {
				return createCommentAdapter();
			}
			@Override
			public Adapter caseTopic(Topic object) {
				return createTopicAdapter();
			}
			@Override
			public Adapter caseUser(User object) {
				return createUserAdapter();
			}
			@Override
			public Adapter caseItem(Item object) {
				return createItemAdapter();
			}
			@Override
			public Adapter caseLocation(Location object) {
				return createLocationAdapter();
			}
			@Override
			public Adapter caseCommentType(CommentType object) {
				return createCommentTypeAdapter();
			}
			@Override
			public Adapter caseTaskReference(TaskReference object) {
				return createTaskReferenceAdapter();
			}
			@Override
			public Adapter caseReviewState(ReviewState object) {
				return createReviewStateAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup <em>R4E Review Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup
	 * @generated
	 */
	public Adapter createR4EReviewGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReview <em>R4E Review</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReview
	 * @generated
	 */
	public Adapter createR4EReviewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly <em>R4E Anomaly</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly
	 * @generated
	 */
	public Adapter createR4EAnomalyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview <em>R4E Formal Review</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview
	 * @generated
	 */
	public Adapter createR4EFormalReviewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition <em>R4E Text Position</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition
	 * @generated
	 */
	public Adapter createR4ETextPositionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision <em>R4E Review Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision
	 * @generated
	 */
	public Adapter createR4EReviewDecisionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUser <em>R4E User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser
	 * @generated
	 */
	public Adapter createR4EUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant <em>R4E Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant
	 * @generated
	 */
	public Adapter createR4EParticipantAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem <em>R4E Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EItem
	 * @generated
	 */
	public Adapter createR4EItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent <em>R4E Text Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent
	 * @generated
	 */
	public Adapter createR4ETextContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EID <em>R4EID</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EID
	 * @generated
	 */
	public Adapter createR4EIDAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType <em>R4E Anomaly Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType
	 * @generated
	 */
	public Adapter createR4EAnomalyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference <em>R4E Task Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference
	 * @generated
	 */
	public Adapter createR4ETaskReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState <em>R4E Review State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState
	 * @generated
	 */
	public Adapter createR4EReviewStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EComment <em>R4E Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EComment
	 * @generated
	 */
	public Adapter createR4ECommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent <em>R4E Review Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent
	 * @generated
	 */
	public Adapter createR4EReviewComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext <em>R4E File Context</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext
	 * @generated
	 */
	public Adapter createR4EFileContextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta <em>R4E Delta</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta
	 * @generated
	 */
	public Adapter createR4EDeltaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType <em>R4E Comment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType
	 * @generated
	 */
	public Adapter createR4ECommentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map To Anomaly Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapToAnomalyTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EContent <em>R4E Content</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EContent
	 * @generated
	 */
	public Adapter createR4EContentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition <em>R4E Position</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition
	 * @generated
	 */
	public Adapter createR4EPositionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion <em>R4E File Version</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion
	 * @generated
	 */
	public Adapter createR4EFileVersionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map Name To Review</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapNameToReviewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map To Users</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapToUsersAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews <em>R4E User Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUserReviews
	 * @generated
	 */
	public Adapter createR4EUserReviewsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent <em>R4EID Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EIDComponent
	 * @generated
	 */
	public Adapter createR4EIDComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map ID To Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapIDToComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map User ID To User Reviews</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapUserIDToUserReviewsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition <em>R4E Anomaly Text Position</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition
	 * @generated
	 */
	public Adapter createR4EAnomalyTextPositionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map Date To Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapDateToDurationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Map Key To Info Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createMapKeyToInfoAttributesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo <em>R4E Review Phase Info</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo
	 * @generated
	 */
	public Adapter createR4EReviewPhaseInfoAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData <em>R4E Meeting Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData
	 * @generated
	 */
	public Adapter createR4EMeetingDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition <em>R4E Model Position</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition
	 * @generated
	 */
	public Adapter createR4EModelPositionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent <em>Review Component</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent
	 * @generated
	 */
	public Adapter createReviewComponentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot <em>Sub Model Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot
	 * @generated
	 */
	public Adapter createSubModelRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup <em>Review Group</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup
	 * @generated
	 */
	public Adapter createReviewGroupAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.Review <em>Review</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Review
	 * @generated
	 */
	public Adapter createReviewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Comment
	 * @generated
	 */
	public Adapter createCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.Topic <em>Topic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Topic
	 * @generated
	 */
	public Adapter createTopicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.User
	 * @generated
	 */
	public Adapter createUserAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.Item <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Item
	 * @generated
	 */
	public Adapter createItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.CommentType <em>Comment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.CommentType
	 * @generated
	 */
	public Adapter createCommentTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference <em>Task Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.TaskReference
	 * @generated
	 */
	public Adapter createTaskReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.ReviewState <em>Review State</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ReviewState
	 * @generated
	 */
	public Adapter createReviewStateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.mylyn.reviews.frame.core.model.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.mylyn.reviews.frame.core.model.Location
	 * @generated
	 */
	public Adapter createLocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RModelAdapterFactory
