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

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewGroup;
import org.eclipse.mylyn.reviews.frame.core.model.TaskReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Review Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl#getReviews <em>Reviews</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl#getReviewGroupTask <em>Review Group Task</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewGroupImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReviewGroupImpl extends ReviewComponentImpl implements ReviewGroup {
	/**
	 * The cached value of the '{@link #getReviews() <em>Reviews</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviews()
	 * @generated
	 * @ordered
	 */
	protected EList<Review> reviews;

	/**
	 * The cached value of the '{@link #getReviewGroupTask() <em>Review Group Task</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewGroupTask()
	 * @generated
	 * @ordered
	 */
	protected TaskReference reviewGroupTask;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReviewGroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.REVIEW_GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Review> getReviews() {
		if (reviews == null) {
			reviews = new EObjectContainmentEList.Resolving<Review>(Review.class, this, ModelPackage.REVIEW_GROUP__REVIEWS);
		}
		return reviews;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskReference getReviewGroupTask() {
		if (reviewGroupTask != null && reviewGroupTask.eIsProxy()) {
			InternalEObject oldReviewGroupTask = (InternalEObject)reviewGroupTask;
			reviewGroupTask = (TaskReference)eResolveProxy(oldReviewGroupTask);
			if (reviewGroupTask != oldReviewGroupTask) {
				InternalEObject newReviewGroupTask = (InternalEObject)reviewGroupTask;
				NotificationChain msgs = oldReviewGroupTask.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, null, null);
				if (newReviewGroupTask.eInternalContainer() == null) {
					msgs = newReviewGroupTask.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, null, msgs);
				}
				if (msgs != null) msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, oldReviewGroupTask, reviewGroupTask));
			}
		}
		return reviewGroupTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskReference basicGetReviewGroupTask() {
		return reviewGroupTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReviewGroupTask(TaskReference newReviewGroupTask, NotificationChain msgs) {
		TaskReference oldReviewGroupTask = reviewGroupTask;
		reviewGroupTask = newReviewGroupTask;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, oldReviewGroupTask, newReviewGroupTask);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewGroupTask(TaskReference newReviewGroupTask) {
		if (newReviewGroupTask != reviewGroupTask) {
			NotificationChain msgs = null;
			if (reviewGroupTask != null)
				msgs = ((InternalEObject)reviewGroupTask).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, null, msgs);
			if (newReviewGroupTask != null)
				msgs = ((InternalEObject)newReviewGroupTask).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, null, msgs);
			msgs = basicSetReviewGroupTask(newReviewGroupTask, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK, newReviewGroupTask, newReviewGroupTask));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.REVIEW_GROUP__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.REVIEW_GROUP__REVIEWS:
				return ((InternalEList<?>)getReviews()).basicRemove(otherEnd, msgs);
			case ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK:
				return basicSetReviewGroupTask(null, msgs);
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
			case ModelPackage.REVIEW_GROUP__REVIEWS:
				return getReviews();
			case ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK:
				if (resolve) return getReviewGroupTask();
				return basicGetReviewGroupTask();
			case ModelPackage.REVIEW_GROUP__DESCRIPTION:
				return getDescription();
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
			case ModelPackage.REVIEW_GROUP__REVIEWS:
				getReviews().clear();
				getReviews().addAll((Collection<? extends Review>)newValue);
				return;
			case ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK:
				setReviewGroupTask((TaskReference)newValue);
				return;
			case ModelPackage.REVIEW_GROUP__DESCRIPTION:
				setDescription((String)newValue);
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
			case ModelPackage.REVIEW_GROUP__REVIEWS:
				getReviews().clear();
				return;
			case ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK:
				setReviewGroupTask((TaskReference)null);
				return;
			case ModelPackage.REVIEW_GROUP__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
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
			case ModelPackage.REVIEW_GROUP__REVIEWS:
				return reviews != null && !reviews.isEmpty();
			case ModelPackage.REVIEW_GROUP__REVIEW_GROUP_TASK:
				return reviewGroupTask != null;
			case ModelPackage.REVIEW_GROUP__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
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
		result.append(" (description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //ReviewGroupImpl
