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
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import java.util.Collection;

import java.util.Date;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.frame.core.model.User;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getAddedBy <em>Added By</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getReview <em>Review</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getXmlVersion <em>Xml Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getAddedById <em>Added By Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getFileContextList <em>File Context List</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getRepositoryRef <em>Repository Ref</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getProjectURIs <em>Project UR Is</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getAuthorRep <em>Author Rep</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getSubmitted <em>Submitted</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl#getInfoAtt <em>Info Att</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4EItemImpl extends R4EIDComponentImpl implements R4EItem {
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
	 * The default value of the '{@link #getXmlVersion() <em>Xml Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String XML_VERSION_EDEFAULT = "1.0.0";

	/**
	 * The cached value of the '{@link #getXmlVersion() <em>Xml Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXmlVersion()
	 * @generated
	 * @ordered
	 */
	protected String xmlVersion = XML_VERSION_EDEFAULT;

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
	 * The default value of the '{@link #getAddedById() <em>Added By Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedById()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDED_BY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddedById() <em>Added By Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddedById()
	 * @generated
	 * @ordered
	 */
	protected String addedById = ADDED_BY_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFileContextList() <em>File Context List</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileContextList()
	 * @generated
	 * @ordered
	 */
	protected EList<R4EFileContext> fileContextList;

	/**
	 * The default value of the '{@link #getRepositoryRef() <em>Repository Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryRef()
	 * @generated
	 * @ordered
	 */
	protected static final String REPOSITORY_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepositoryRef() <em>Repository Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryRef()
	 * @generated
	 * @ordered
	 */
	protected String repositoryRef = REPOSITORY_REF_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProjectURIs() <em>Project UR Is</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectURIs()
	 * @generated
	 * @ordered
	 */
	protected EList<String> projectURIs;

	/**
	 * The default value of the '{@link #getAuthorRep() <em>Author Rep</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthorRep()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_REP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthorRep() <em>Author Rep</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthorRep()
	 * @generated
	 * @ordered
	 */
	protected String authorRep = AUTHOR_REP_EDEFAULT;

	/**
	 * The default value of the '{@link #getSubmitted() <em>Submitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmitted()
	 * @generated
	 * @ordered
	 */
	protected static final Date SUBMITTED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSubmitted() <em>Submitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubmitted()
	 * @generated
	 * @ordered
	 */
	protected Date submitted = SUBMITTED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInfoAtt() <em>Info Att</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoAtt()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> infoAtt;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4EItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_ITEM;
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ITEM__ADDED_BY, oldAddedBy, addedBy));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__ADDED_BY, oldAddedBy, addedBy));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RModelPackage.R4E_ITEM__REVIEW, oldReview, review));
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
	public void setReview(Review newReview) {
		Review oldReview = review;
		review = newReview;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__REVIEW, oldReview, review));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getXmlVersion() {
		return xmlVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXmlVersion(String newXmlVersion) {
		String oldXmlVersion = xmlVersion;
		xmlVersion = newXmlVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__XML_VERSION, oldXmlVersion, xmlVersion));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddedById() {
		return addedById;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddedById(String newAddedById) {
		String oldAddedById = addedById;
		addedById = newAddedById;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__ADDED_BY_ID, oldAddedById, addedById));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<R4EFileContext> getFileContextList() {
		if (fileContextList == null) {
			fileContextList = new EObjectContainmentEList.Resolving<R4EFileContext>(R4EFileContext.class, this, RModelPackage.R4E_ITEM__FILE_CONTEXT_LIST);
		}
		return fileContextList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRepositoryRef() {
		return repositoryRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositoryRef(String newRepositoryRef) {
		String oldRepositoryRef = repositoryRef;
		repositoryRef = newRepositoryRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__REPOSITORY_REF, oldRepositoryRef, repositoryRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getProjectURIs() {
		if (projectURIs == null) {
			projectURIs = new EDataTypeUniqueEList<String>(String.class, this, RModelPackage.R4E_ITEM__PROJECT_UR_IS);
		}
		return projectURIs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthorRep() {
		return authorRep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthorRep(String newAuthorRep) {
		String oldAuthorRep = authorRep;
		authorRep = newAuthorRep;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__AUTHOR_REP, oldAuthorRep, authorRep));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getSubmitted() {
		return submitted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubmitted(Date newSubmitted) {
		Date oldSubmitted = submitted;
		submitted = newSubmitted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_ITEM__SUBMITTED, oldSubmitted, submitted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getInfoAtt() {
		if (infoAtt == null) {
			infoAtt = new EcoreEMap<String,String>(RModelPackage.Literals.MAP_KEY_TO_INFO_ATTRIBUTES, MapKeyToInfoAttributesImpl.class, this, RModelPackage.R4E_ITEM__INFO_ATT);
		}
		return infoAtt;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RModelPackage.R4E_ITEM__FILE_CONTEXT_LIST:
				return ((InternalEList<?>)getFileContextList()).basicRemove(otherEnd, msgs);
			case RModelPackage.R4E_ITEM__INFO_ATT:
				return ((InternalEList<?>)getInfoAtt()).basicRemove(otherEnd, msgs);
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
			case RModelPackage.R4E_ITEM__ADDED_BY:
				if (resolve) return getAddedBy();
				return basicGetAddedBy();
			case RModelPackage.R4E_ITEM__REVIEW:
				if (resolve) return getReview();
				return basicGetReview();
			case RModelPackage.R4E_ITEM__XML_VERSION:
				return getXmlVersion();
			case RModelPackage.R4E_ITEM__DESCRIPTION:
				return getDescription();
			case RModelPackage.R4E_ITEM__ADDED_BY_ID:
				return getAddedById();
			case RModelPackage.R4E_ITEM__FILE_CONTEXT_LIST:
				return getFileContextList();
			case RModelPackage.R4E_ITEM__REPOSITORY_REF:
				return getRepositoryRef();
			case RModelPackage.R4E_ITEM__PROJECT_UR_IS:
				return getProjectURIs();
			case RModelPackage.R4E_ITEM__AUTHOR_REP:
				return getAuthorRep();
			case RModelPackage.R4E_ITEM__SUBMITTED:
				return getSubmitted();
			case RModelPackage.R4E_ITEM__INFO_ATT:
				if (coreType) return getInfoAtt();
				else return getInfoAtt().map();
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
			case RModelPackage.R4E_ITEM__ADDED_BY:
				setAddedBy((User)newValue);
				return;
			case RModelPackage.R4E_ITEM__REVIEW:
				setReview((Review)newValue);
				return;
			case RModelPackage.R4E_ITEM__XML_VERSION:
				setXmlVersion((String)newValue);
				return;
			case RModelPackage.R4E_ITEM__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case RModelPackage.R4E_ITEM__ADDED_BY_ID:
				setAddedById((String)newValue);
				return;
			case RModelPackage.R4E_ITEM__FILE_CONTEXT_LIST:
				getFileContextList().clear();
				getFileContextList().addAll((Collection<? extends R4EFileContext>)newValue);
				return;
			case RModelPackage.R4E_ITEM__REPOSITORY_REF:
				setRepositoryRef((String)newValue);
				return;
			case RModelPackage.R4E_ITEM__PROJECT_UR_IS:
				getProjectURIs().clear();
				getProjectURIs().addAll((Collection<? extends String>)newValue);
				return;
			case RModelPackage.R4E_ITEM__AUTHOR_REP:
				setAuthorRep((String)newValue);
				return;
			case RModelPackage.R4E_ITEM__SUBMITTED:
				setSubmitted((Date)newValue);
				return;
			case RModelPackage.R4E_ITEM__INFO_ATT:
				((EStructuralFeature.Setting)getInfoAtt()).set(newValue);
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
			case RModelPackage.R4E_ITEM__ADDED_BY:
				setAddedBy((User)null);
				return;
			case RModelPackage.R4E_ITEM__REVIEW:
				setReview((Review)null);
				return;
			case RModelPackage.R4E_ITEM__XML_VERSION:
				setXmlVersion(XML_VERSION_EDEFAULT);
				return;
			case RModelPackage.R4E_ITEM__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case RModelPackage.R4E_ITEM__ADDED_BY_ID:
				setAddedById(ADDED_BY_ID_EDEFAULT);
				return;
			case RModelPackage.R4E_ITEM__FILE_CONTEXT_LIST:
				getFileContextList().clear();
				return;
			case RModelPackage.R4E_ITEM__REPOSITORY_REF:
				setRepositoryRef(REPOSITORY_REF_EDEFAULT);
				return;
			case RModelPackage.R4E_ITEM__PROJECT_UR_IS:
				getProjectURIs().clear();
				return;
			case RModelPackage.R4E_ITEM__AUTHOR_REP:
				setAuthorRep(AUTHOR_REP_EDEFAULT);
				return;
			case RModelPackage.R4E_ITEM__SUBMITTED:
				setSubmitted(SUBMITTED_EDEFAULT);
				return;
			case RModelPackage.R4E_ITEM__INFO_ATT:
				getInfoAtt().clear();
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
			case RModelPackage.R4E_ITEM__ADDED_BY:
				return addedBy != null;
			case RModelPackage.R4E_ITEM__REVIEW:
				return review != null;
			case RModelPackage.R4E_ITEM__XML_VERSION:
				return XML_VERSION_EDEFAULT == null ? xmlVersion != null : !XML_VERSION_EDEFAULT.equals(xmlVersion);
			case RModelPackage.R4E_ITEM__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case RModelPackage.R4E_ITEM__ADDED_BY_ID:
				return ADDED_BY_ID_EDEFAULT == null ? addedById != null : !ADDED_BY_ID_EDEFAULT.equals(addedById);
			case RModelPackage.R4E_ITEM__FILE_CONTEXT_LIST:
				return fileContextList != null && !fileContextList.isEmpty();
			case RModelPackage.R4E_ITEM__REPOSITORY_REF:
				return REPOSITORY_REF_EDEFAULT == null ? repositoryRef != null : !REPOSITORY_REF_EDEFAULT.equals(repositoryRef);
			case RModelPackage.R4E_ITEM__PROJECT_UR_IS:
				return projectURIs != null && !projectURIs.isEmpty();
			case RModelPackage.R4E_ITEM__AUTHOR_REP:
				return AUTHOR_REP_EDEFAULT == null ? authorRep != null : !AUTHOR_REP_EDEFAULT.equals(authorRep);
			case RModelPackage.R4E_ITEM__SUBMITTED:
				return SUBMITTED_EDEFAULT == null ? submitted != null : !SUBMITTED_EDEFAULT.equals(submitted);
			case RModelPackage.R4E_ITEM__INFO_ATT:
				return infoAtt != null && !infoAtt.isEmpty();
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
		if (baseClass == Item.class) {
			switch (derivedFeatureID) {
				case RModelPackage.R4E_ITEM__ADDED_BY: return ModelPackage.ITEM__ADDED_BY;
				case RModelPackage.R4E_ITEM__REVIEW: return ModelPackage.ITEM__REVIEW;
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
		if (baseClass == Item.class) {
			switch (baseFeatureID) {
				case ModelPackage.ITEM__ADDED_BY: return RModelPackage.R4E_ITEM__ADDED_BY;
				case ModelPackage.ITEM__REVIEW: return RModelPackage.R4E_ITEM__REVIEW;
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
		result.append(" (xmlVersion: ");
		result.append(xmlVersion);
		result.append(", description: ");
		result.append(description);
		result.append(", addedById: ");
		result.append(addedById);
		result.append(", repositoryRef: ");
		result.append(repositoryRef);
		result.append(", ProjectURIs: ");
		result.append(projectURIs);
		result.append(", authorRep: ");
		result.append(authorRep);
		result.append(", submitted: ");
		result.append(submitted);
		result.append(')');
		return result.toString();
	}

} //R4EItemImpl
