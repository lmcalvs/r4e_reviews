/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.frame.core.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.frame.core.model.User;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ItemImpl#getAddedBy <em>Added By</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.impl.ItemImpl#getReview <em>Review</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ItemImpl extends ReviewComponentImpl implements Item {
	/**
	 * The cached value of the '{@link #getAddedBy() <em>Added By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedBy()
	 * @generated
	 * @ordered
	 */
	protected User addedBy;
	/**
	 * The cached value of the '{@link #getReview() <em>Review</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReview()
	 * @generated
	 * @ordered
	 */
	protected Review review;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User getAddedBy() {
		if (addedBy != null && addedBy.eIsProxy()) {
			InternalEObject oldAddedBy = (InternalEObject)addedBy;
			addedBy = (User)eResolveProxy(oldAddedBy);
			if (addedBy != oldAddedBy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ITEM__ADDED_BY, oldAddedBy, addedBy));
			}
		}
		return addedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User basicGetAddedBy() {
		return addedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddedBy(User newAddedBy) {
		User oldAddedBy = addedBy;
		addedBy = newAddedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ITEM__ADDED_BY, oldAddedBy, addedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Review getReview() {
		if (review != null && review.eIsProxy()) {
			InternalEObject oldReview = (InternalEObject)review;
			review = (Review)eResolveProxy(oldReview);
			if (review != oldReview) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ITEM__REVIEW, oldReview, review));
			}
		}
		return review;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Review basicGetReview() {
		return review;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReview(Review newReview, NotificationChain msgs) {
		Review oldReview = review;
		review = newReview;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ITEM__REVIEW, oldReview, newReview);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReview(Review newReview) {
		if (newReview != review) {
			NotificationChain msgs = null;
			if (review != null)
				msgs = ((InternalEObject)review).eInverseRemove(this, ModelPackage.REVIEW__REVIEW_ITEMS, Review.class, msgs);
			if (newReview != null)
				msgs = ((InternalEObject)newReview).eInverseAdd(this, ModelPackage.REVIEW__REVIEW_ITEMS, Review.class, msgs);
			msgs = basicSetReview(newReview, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ITEM__REVIEW, newReview, newReview));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ITEM__REVIEW:
				if (review != null)
					msgs = ((InternalEObject)review).eInverseRemove(this, ModelPackage.REVIEW__REVIEW_ITEMS, Review.class, msgs);
				return basicSetReview((Review)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ITEM__REVIEW:
				return basicSetReview(null, msgs);
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
			case ModelPackage.ITEM__ADDED_BY:
				if (resolve) return getAddedBy();
				return basicGetAddedBy();
			case ModelPackage.ITEM__REVIEW:
				if (resolve) return getReview();
				return basicGetReview();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.ITEM__ADDED_BY:
				setAddedBy((User)newValue);
				return;
			case ModelPackage.ITEM__REVIEW:
				setReview((Review)newValue);
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
			case ModelPackage.ITEM__ADDED_BY:
				setAddedBy((User)null);
				return;
			case ModelPackage.ITEM__REVIEW:
				setReview((Review)null);
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
			case ModelPackage.ITEM__ADDED_BY:
				return addedBy != null;
			case ModelPackage.ITEM__REVIEW:
				return review != null;
		}
		return super.eIsSet(featureID);
	}

} //ItemImpl
