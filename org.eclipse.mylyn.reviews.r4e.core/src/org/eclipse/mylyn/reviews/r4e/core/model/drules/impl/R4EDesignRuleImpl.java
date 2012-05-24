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
 *    Alvaro Sanchez-Leon - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.drules.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.mylyn.reviews.frame.core.model.impl.ReviewComponentImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Design Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl#getRank <em>Rank</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EDesignRuleImpl extends ReviewComponentImpl implements R4EDesignRule {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getRank() <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRank()
	 * @generated
	 * @ordered
	 */
	protected static final R4EDesignRuleRank RANK_EDEFAULT = R4EDesignRuleRank.R4E_RANK_NONE;

	/**
	 * The cached value of the '{@link #getRank() <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRank()
	 * @generated
	 * @ordered
	 */
	protected R4EDesignRuleRank rank = RANK_EDEFAULT;

	/**
	 * The default value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected static final R4EDesignRuleClass CLASS_EDEFAULT = R4EDesignRuleClass.R4E_CLASS_ERRONEOUS;

	/**
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected R4EDesignRuleClass class_ = CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

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
	protected R4EDesignRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DRModelPackage.Literals.R4E_DESIGN_RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleRank getRank() {
		return rank;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRank(R4EDesignRuleRank newRank) {
		R4EDesignRuleRank oldRank = rank;
		rank = newRank == null ? RANK_EDEFAULT : newRank;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE__RANK, oldRank, rank));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleClass getClass_() {
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(R4EDesignRuleClass newClass) {
		R4EDesignRuleClass oldClass = class_;
		class_ = newClass == null ? CLASS_EDEFAULT : newClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE__CLASS, oldClass, class_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE__TITLE, oldTitle, title));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DRModelPackage.R4E_DESIGN_RULE__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DRModelPackage.R4E_DESIGN_RULE__ID:
				return getId();
			case DRModelPackage.R4E_DESIGN_RULE__RANK:
				return getRank();
			case DRModelPackage.R4E_DESIGN_RULE__CLASS:
				return getClass_();
			case DRModelPackage.R4E_DESIGN_RULE__TITLE:
				return getTitle();
			case DRModelPackage.R4E_DESIGN_RULE__DESCRIPTION:
				return getDescription();
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
			case DRModelPackage.R4E_DESIGN_RULE__ID:
				setId((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__RANK:
				setRank((R4EDesignRuleRank)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__CLASS:
				setClass((R4EDesignRuleClass)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__TITLE:
				setTitle((String)newValue);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__DESCRIPTION:
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
			case DRModelPackage.R4E_DESIGN_RULE__ID:
				setId(ID_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__RANK:
				setRank(RANK_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__CLASS:
				setClass(CLASS_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case DRModelPackage.R4E_DESIGN_RULE__DESCRIPTION:
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
			case DRModelPackage.R4E_DESIGN_RULE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case DRModelPackage.R4E_DESIGN_RULE__RANK:
				return rank != RANK_EDEFAULT;
			case DRModelPackage.R4E_DESIGN_RULE__CLASS:
				return class_ != CLASS_EDEFAULT;
			case DRModelPackage.R4E_DESIGN_RULE__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case DRModelPackage.R4E_DESIGN_RULE__DESCRIPTION:
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
		result.append(" (id: ");
		result.append(id);
		result.append(", rank: ");
		result.append(rank);
		result.append(", class: ");
		result.append(class_);
		result.append(", title: ");
		result.append(title);
		result.append(", description: ");
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //R4EDesignRuleImpl
