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
package org.eclipse.mylyn.reviews.frame.core.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewState;
import org.eclipse.mylyn.reviews.frame.core.model.TaskReference;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Review</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl#getTopics <em>Topics</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl#getReviewItems <em>Review Items</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl#getReviewTask <em>Review Task</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewImpl#getState <em>State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReviewImpl extends ReviewComponentImpl implements Review {
	/**
	 * The cached value of the '{@link #getTopics() <em>Topics</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopics()
	 * @generated
	 * @ordered
	 */
	protected EList<Topic> topics;

	/**
	 * The cached value of the '{@link #getReviewItems() <em>Review Items</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewItems()
	 * @generated
	 * @ordered
	 */
	protected EList<Item> reviewItems;

	/**
	 * The cached value of the '{@link #getReviewTask() <em>Review Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewTask()
	 * @generated
	 * @ordered
	 */
	protected TaskReference reviewTask;

	/**
	 * The cached value of the '{@link #getState() <em>State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getState()
	 * @generated
	 * @ordered
	 */
	protected ReviewState state;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.REVIEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Topic> getTopics() {
		if (topics == null) {
			topics = new EObjectResolvingEList<Topic>(Topic.class, this, ModelPackage.REVIEW__TOPICS);
		}
		return topics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Item> getReviewItems() {
		if (reviewItems == null) {
			reviewItems = new EObjectResolvingEList<Item>(Item.class, this, ModelPackage.REVIEW__REVIEW_ITEMS);
		}
		return reviewItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskReference getReviewTask() {
		if (reviewTask != null && reviewTask.eIsProxy()) {
			InternalEObject oldReviewTask = (InternalEObject)reviewTask;
			reviewTask = (TaskReference)eResolveProxy(oldReviewTask);
			if (reviewTask != oldReviewTask) {
				InternalEObject newReviewTask = (InternalEObject)reviewTask;
				NotificationChain msgs = oldReviewTask.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__REVIEW_TASK, null, null);
				if (newReviewTask.eInternalContainer() == null) {
					msgs = newReviewTask.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__REVIEW_TASK, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.REVIEW__REVIEW_TASK, oldReviewTask, reviewTask));
			}
		}
		return reviewTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskReference basicGetReviewTask() {
		return reviewTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReviewTask(TaskReference newReviewTask, NotificationChain msgs) {
		TaskReference oldReviewTask = reviewTask;
		reviewTask = newReviewTask;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW__REVIEW_TASK, oldReviewTask, newReviewTask);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewTask(TaskReference newReviewTask) {
		if (newReviewTask != reviewTask) {
			NotificationChain msgs = null;
			if (reviewTask != null)
				msgs = ((InternalEObject)reviewTask).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__REVIEW_TASK, null, msgs);
			if (newReviewTask != null)
				msgs = ((InternalEObject)newReviewTask).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__REVIEW_TASK, null, msgs);
			msgs = basicSetReviewTask(newReviewTask, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW__REVIEW_TASK, newReviewTask, newReviewTask));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewState getState() {
		if (state != null && state.eIsProxy()) {
			InternalEObject oldState = (InternalEObject)state;
			state = (ReviewState)eResolveProxy(oldState);
			if (state != oldState) {
				InternalEObject newState = (InternalEObject)state;
				NotificationChain msgs = oldState.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__STATE, null, null);
				if (newState.eInternalContainer() == null) {
					msgs = newState.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__STATE, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.REVIEW__STATE, oldState, state));
			}
		}
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewState basicGetState() {
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetState(ReviewState newState, NotificationChain msgs) {
		ReviewState oldState = state;
		state = newState;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW__STATE, oldState, newState);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setState(ReviewState newState) {
		if (newState != state) {
			NotificationChain msgs = null;
			if (state != null)
				msgs = ((InternalEObject)state).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__STATE, null, msgs);
			if (newState != null)
				msgs = ((InternalEObject)newState).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW__STATE, null, msgs);
			msgs = basicSetState(newState, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW__STATE, newState, newState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.REVIEW__REVIEW_TASK:
				return basicSetReviewTask(null, msgs);
			case ModelPackage.REVIEW__STATE:
				return basicSetState(null, msgs);
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
			case ModelPackage.REVIEW__TOPICS:
				return getTopics();
			case ModelPackage.REVIEW__REVIEW_ITEMS:
				return getReviewItems();
			case ModelPackage.REVIEW__REVIEW_TASK:
				if (resolve) return getReviewTask();
				return basicGetReviewTask();
			case ModelPackage.REVIEW__STATE:
				if (resolve) return getState();
				return basicGetState();
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
			case ModelPackage.REVIEW__TOPICS:
				getTopics().clear();
				getTopics().addAll((Collection<? extends Topic>)newValue);
				return;
			case ModelPackage.REVIEW__REVIEW_ITEMS:
				getReviewItems().clear();
				getReviewItems().addAll((Collection<? extends Item>)newValue);
				return;
			case ModelPackage.REVIEW__REVIEW_TASK:
				setReviewTask((TaskReference)newValue);
				return;
			case ModelPackage.REVIEW__STATE:
				setState((ReviewState)newValue);
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
			case ModelPackage.REVIEW__TOPICS:
				getTopics().clear();
				return;
			case ModelPackage.REVIEW__REVIEW_ITEMS:
				getReviewItems().clear();
				return;
			case ModelPackage.REVIEW__REVIEW_TASK:
				setReviewTask((TaskReference)null);
				return;
			case ModelPackage.REVIEW__STATE:
				setState((ReviewState)null);
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
			case ModelPackage.REVIEW__TOPICS:
				return topics != null && !topics.isEmpty();
			case ModelPackage.REVIEW__REVIEW_ITEMS:
				return reviewItems != null && !reviewItems.isEmpty();
			case ModelPackage.REVIEW__REVIEW_TASK:
				return reviewTask != null;
			case ModelPackage.REVIEW__STATE:
				return state != null;
		}
		return super.eIsSet(featureID);
	}

} //ReviewImpl
