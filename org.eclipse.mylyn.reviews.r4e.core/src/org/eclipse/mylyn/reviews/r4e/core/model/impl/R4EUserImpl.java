/**
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.frame.core.model.impl.UserImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getAssignedTo <em>Assigned To</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getGroupPaths <em>Group Paths</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getSequenceIDCounter <em>Sequence ID Counter</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getAddedComments <em>Added Comments</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getAddedItems <em>Added Items</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#isReviewCreatedByMe <em>Review Created By Me</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getReviewInstance <em>Review Instance</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#isReviewCompleted <em>Review Completed</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EUserImpl#getReviewCompletedCode <em>Review Completed Code</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EUserImpl extends UserImpl implements R4EUser {
	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

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
	 * The cached value of the '{@link #getGroupPaths() <em>Group Paths</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupPaths()
	 * @generated
	 * @ordered
	 */
	protected EList<String> groupPaths;

	/**
	 * The default value of the '{@link #getSequenceIDCounter() <em>Sequence ID Counter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceIDCounter()
	 * @generated
	 * @ordered
	 */
	protected static final int SEQUENCE_ID_COUNTER_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getSequenceIDCounter() <em>Sequence ID Counter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSequenceIDCounter()
	 * @generated
	 * @ordered
	 */
	protected int sequenceIDCounter = SEQUENCE_ID_COUNTER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAddedComments() <em>Added Comments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedComments()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EComment> addedComments;

	/**
	 * The cached value of the '{@link #getAddedItems() <em>Added Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedItems()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EItem> addedItems;

	/**
	 * The default value of the '{@link #isReviewCreatedByMe() <em>Review Created By Me</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewCreatedByMe()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REVIEW_CREATED_BY_ME_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReviewCreatedByMe() <em>Review Created By Me</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewCreatedByMe()
	 * @generated
	 * @ordered
	 */
	protected boolean reviewCreatedByMe = REVIEW_CREATED_BY_ME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReviewInstance() <em>Review Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewInstance()
	 * @generated
	 * @ordered
	 */
	protected R4EReview reviewInstance;

	/**
	 * The default value of the '{@link #isReviewCompleted() <em>Review Completed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewCompleted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REVIEW_COMPLETED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReviewCompleted() <em>Review Completed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReviewCompleted()
	 * @generated
	 * @ordered
	 */
	protected boolean reviewCompleted = REVIEW_COMPLETED_EDEFAULT;

	/**
	 * The default value of the '{@link #getReviewCompletedCode() <em>Review Completed Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewCompletedCode()
	 * @generated
	 * @ordered
	 */
	protected static final int REVIEW_COMPLETED_CODE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getReviewCompletedCode() <em>Review Completed Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReviewCompletedCode()
	 * @generated
	 * @ordered
	 */
	protected int reviewCompletedCode = REVIEW_COMPLETED_CODE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EUserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAssignedTo() {
		if (assignedTo == null) {
			assignedTo = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_USER__ASSIGNED_TO);
		}
		return assignedTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getGroupPaths() {
		if (groupPaths == null) {
			groupPaths = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_USER__GROUP_PATHS);
		}
		return groupPaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSequenceIDCounter() {
		return sequenceIDCounter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSequenceIDCounter(int newSequenceIDCounter) {
		int oldSequenceIDCounter = sequenceIDCounter;
		sequenceIDCounter = newSequenceIDCounter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER__SEQUENCE_ID_COUNTER, oldSequenceIDCounter, sequenceIDCounter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<R4EComment> getAddedComments() {
		if (addedComments == null) {
			addedComments = new EObjectContainmentEList.Resolving<R4EComment>(R4EComment.class, this, RModelPackage.R4E_USER__ADDED_COMMENTS);
		}
		return addedComments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<R4EItem> getAddedItems() {
		if (addedItems == null) {
			addedItems = new EObjectContainmentEList.Resolving<R4EItem>(R4EItem.class, this, RModelPackage.R4E_USER__ADDED_ITEMS);
		}
		return addedItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReviewCreatedByMe() {
		return reviewCreatedByMe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewCreatedByMe(boolean newReviewCreatedByMe) {
		boolean oldReviewCreatedByMe = reviewCreatedByMe;
		reviewCreatedByMe = newReviewCreatedByMe;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER__REVIEW_CREATED_BY_ME, oldReviewCreatedByMe, reviewCreatedByMe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EReview getReviewInstance() {
		if (reviewInstance != null && reviewInstance.eIsProxy()) {
			InternalEObject oldReviewInstance = (InternalEObject)reviewInstance;
			reviewInstance = (R4EReview)eResolveProxy(oldReviewInstance);
			if (reviewInstance != oldReviewInstance) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_USER__REVIEW_INSTANCE, oldReviewInstance, reviewInstance));
			}
		}
		return reviewInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EReview basicGetReviewInstance() {
		return reviewInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewInstance(R4EReview newReviewInstance) {
		R4EReview oldReviewInstance = reviewInstance;
		reviewInstance = newReviewInstance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER__REVIEW_INSTANCE, oldReviewInstance, reviewInstance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReviewCompleted() {
		return reviewCompleted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewCompleted(boolean newReviewCompleted) {
		boolean oldReviewCompleted = reviewCompleted;
		reviewCompleted = newReviewCompleted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER__REVIEW_COMPLETED, oldReviewCompleted, reviewCompleted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getReviewCompletedCode() {
		return reviewCompletedCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReviewCompletedCode(int newReviewCompletedCode) {
		int oldReviewCompletedCode = reviewCompletedCode;
		reviewCompletedCode = newReviewCompletedCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_USER__REVIEW_COMPLETED_CODE, oldReviewCompletedCode, reviewCompletedCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_USER__ADDED_COMMENTS:
				return ((InternalEList<?>)getAddedComments()).basicRemove(otherEnd, msgs);
			case RModelPackage.R4E_USER__ADDED_ITEMS:
				return ((InternalEList<?>)getAddedItems()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.R4EUser#getSequenceIDCounterNext()
	 */
	public int getSequenceIDCounterNext() {
		int count = getSequenceIDCounter() + 1;
		setSequenceIDCounter(count);
		return count;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_USER__ENABLED:
				return isEnabled();
			case RModelPackage.R4E_USER__ASSIGNED_TO:
				return getAssignedTo();
			case RModelPackage.R4E_USER__GROUP_PATHS:
				return getGroupPaths();
			case RModelPackage.R4E_USER__SEQUENCE_ID_COUNTER:
				return getSequenceIDCounter();
			case RModelPackage.R4E_USER__ADDED_COMMENTS:
				return getAddedComments();
			case RModelPackage.R4E_USER__ADDED_ITEMS:
				return getAddedItems();
			case RModelPackage.R4E_USER__REVIEW_CREATED_BY_ME:
				return isReviewCreatedByMe();
			case RModelPackage.R4E_USER__REVIEW_INSTANCE:
				if (resolve) return getReviewInstance();
				return basicGetReviewInstance();
			case RModelPackage.R4E_USER__REVIEW_COMPLETED:
				return isReviewCompleted();
			case RModelPackage.R4E_USER__REVIEW_COMPLETED_CODE:
				return getReviewCompletedCode();
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
			case RModelPackage.R4E_USER__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case RModelPackage.R4E_USER__ASSIGNED_TO:
				getAssignedTo().clear();
				getAssignedTo().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_USER__GROUP_PATHS:
				getGroupPaths().clear();
				getGroupPaths().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_USER__SEQUENCE_ID_COUNTER:
				setSequenceIDCounter((Integer)newValue);
				return;
			case RModelPackage.R4E_USER__ADDED_COMMENTS:
				getAddedComments().clear();
				getAddedComments().addAll((Collection<? extends R4EComment>)newValue);
				return;
			case RModelPackage.R4E_USER__ADDED_ITEMS:
				getAddedItems().clear();
				getAddedItems().addAll((Collection<? extends R4EItem>)newValue);
				return;
			case RModelPackage.R4E_USER__REVIEW_CREATED_BY_ME:
				setReviewCreatedByMe((Boolean)newValue);
				return;
			case RModelPackage.R4E_USER__REVIEW_INSTANCE:
				setReviewInstance((R4EReview)newValue);
				return;
			case RModelPackage.R4E_USER__REVIEW_COMPLETED:
				setReviewCompleted((Boolean)newValue);
				return;
			case RModelPackage.R4E_USER__REVIEW_COMPLETED_CODE:
				setReviewCompletedCode((Integer)newValue);
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
			case RModelPackage.R4E_USER__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case RModelPackage.R4E_USER__ASSIGNED_TO:
				getAssignedTo().clear();
				return;
			case RModelPackage.R4E_USER__GROUP_PATHS:
				getGroupPaths().clear();
				return;
			case RModelPackage.R4E_USER__SEQUENCE_ID_COUNTER:
				setSequenceIDCounter(SEQUENCE_ID_COUNTER_EDEFAULT);
				return;
			case RModelPackage.R4E_USER__ADDED_COMMENTS:
				getAddedComments().clear();
				return;
			case RModelPackage.R4E_USER__ADDED_ITEMS:
				getAddedItems().clear();
				return;
			case RModelPackage.R4E_USER__REVIEW_CREATED_BY_ME:
				setReviewCreatedByMe(REVIEW_CREATED_BY_ME_EDEFAULT);
				return;
			case RModelPackage.R4E_USER__REVIEW_INSTANCE:
				setReviewInstance((R4EReview)null);
				return;
			case RModelPackage.R4E_USER__REVIEW_COMPLETED:
				setReviewCompleted(REVIEW_COMPLETED_EDEFAULT);
				return;
			case RModelPackage.R4E_USER__REVIEW_COMPLETED_CODE:
				setReviewCompletedCode(REVIEW_COMPLETED_CODE_EDEFAULT);
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
			case RModelPackage.R4E_USER__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case RModelPackage.R4E_USER__ASSIGNED_TO:
				return assignedTo != null && !assignedTo.isEmpty();
			case RModelPackage.R4E_USER__GROUP_PATHS:
				return groupPaths != null && !groupPaths.isEmpty();
			case RModelPackage.R4E_USER__SEQUENCE_ID_COUNTER:
				return sequenceIDCounter != SEQUENCE_ID_COUNTER_EDEFAULT;
			case RModelPackage.R4E_USER__ADDED_COMMENTS:
				return addedComments != null && !addedComments.isEmpty();
			case RModelPackage.R4E_USER__ADDED_ITEMS:
				return addedItems != null && !addedItems.isEmpty();
			case RModelPackage.R4E_USER__REVIEW_CREATED_BY_ME:
				return reviewCreatedByMe != REVIEW_CREATED_BY_ME_EDEFAULT;
			case RModelPackage.R4E_USER__REVIEW_INSTANCE:
				return reviewInstance != null;
			case RModelPackage.R4E_USER__REVIEW_COMPLETED:
				return reviewCompleted != REVIEW_COMPLETED_EDEFAULT;
			case RModelPackage.R4E_USER__REVIEW_COMPLETED_CODE:
				return reviewCompletedCode != REVIEW_COMPLETED_CODE_EDEFAULT;
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
		if (baseClass == ReviewComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_USER__ENABLED: return ModelPackage.REVIEW_COMPONENT__ENABLED;
				default: return -1;
			}
		}
		if (baseClass == R4EReviewComponent.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_USER__ASSIGNED_TO: return RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO;
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
		if (baseClass == ReviewComponent.class) {
			switch (baseFeatureID) {
				case ModelPackage.REVIEW_COMPONENT__ENABLED: return RModelPackage.R4E_USER__ENABLED;
				default: return -1;
			}
		}
		if (baseClass == R4EReviewComponent.class) {
			switch (baseFeatureID) {
				case RModelPackage.R4E_REVIEW_COMPONENT__ASSIGNED_TO: return RModelPackage.R4E_USER__ASSIGNED_TO;
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
		result.append(" (enabled: ");
		result.append(enabled);
		result.append(", assignedTo: ");
		result.append(assignedTo);
		result.append(", groupPaths: ");
		result.append(groupPaths);
		result.append(", sequenceIDCounter: ");
		result.append(sequenceIDCounter);
		result.append(", reviewCreatedByMe: ");
		result.append(reviewCreatedByMe);
		result.append(", reviewCompleted: ");
		result.append(reviewCompleted);
		result.append(", reviewCompletedCode: ");
		result.append(reviewCompletedCode);
		result.append(')');
		return result.toString();
	}

} //R4EUserImpl
