/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.internal.transform.resources.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResFactory;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class TransResPackageImpl extends EPackageImpl implements TransResPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass reviewGroupResEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass reviewResEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TransResPackageImpl() {
		super(eNS_URI, TransResFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * <p>
	 * This method is used to initialize {@link TransResPackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TransResPackage init() {
		if (isInited)
			return (TransResPackage) EPackage.Registry.INSTANCE.getEPackage(TransResPackage.eNS_URI);

		// Obtain or create and register package
		TransResPackageImpl theTransResPackage = (TransResPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TransResPackageImpl
				? EPackage.Registry.INSTANCE.get(eNS_URI)
				: new TransResPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		RModelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theTransResPackage.createPackageContents();

		// Initialize created meta-data
		theTransResPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTransResPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TransResPackage.eNS_URI, theTransResPackage);
		return theTransResPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getReviewGroupRes() {
		return reviewGroupResEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getReviewGroupRes_ReviewsRes() {
		return (EReference) reviewGroupResEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getReviewGroupRes_FilesPrefix() {
		return (EAttribute) reviewGroupResEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getReviewRes() {
		return reviewResEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getReviewRes_UsersRes() {
		return (EReference) reviewResEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TransResFactory getTransResFactory() {
		return (TransResFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any invocation but
	 * its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		reviewGroupResEClass = createEClass(REVIEW_GROUP_RES);
		createEReference(reviewGroupResEClass, REVIEW_GROUP_RES__REVIEWS_RES);
		createEAttribute(reviewGroupResEClass, REVIEW_GROUP_RES__FILES_PREFIX);

		reviewResEClass = createEClass(REVIEW_RES);
		createEReference(reviewResEClass, REVIEW_RES__USERS_RES);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RModelPackage theRModelPackage = (RModelPackage) EPackage.Registry.INSTANCE.getEPackage(RModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		reviewGroupResEClass.getESuperTypes().add(theRModelPackage.getR4EReviewGroup());
		reviewResEClass.getESuperTypes().add(theRModelPackage.getR4EFormalReview());

		// Initialize classes and features; add operations and parameters
		initEClass(reviewGroupResEClass, ReviewGroupRes.class, "ReviewGroupRes", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReviewGroupRes_ReviewsRes(), this.getReviewRes(), null, "reviewsRes", null, 0, -1,
				ReviewGroupRes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getReviewGroupRes_FilesPrefix(), ecorePackage.getEString(), "filesPrefix", null, 0, 1,
				ReviewGroupRes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(reviewResEClass, ReviewRes.class, "ReviewRes", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReviewRes_UsersRes(), theRModelPackage.getR4EUser(), null, "usersRes", null, 0, -1,
				ReviewRes.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //TransResPackageImpl
