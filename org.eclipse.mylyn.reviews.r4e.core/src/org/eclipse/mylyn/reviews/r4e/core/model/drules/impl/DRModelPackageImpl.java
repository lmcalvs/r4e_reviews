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
package org.eclipse.mylyn.reviews.r4e.core.model.drules.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.impl.ModelPackageImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DRModelPackageImpl extends EPackageImpl implements DRModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EDesignRuleCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EDesignRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EDesignRuleAreaEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass r4EDesignRuleViolationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EDesignRuleRankEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum r4EDesignRuleClassEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DRModelPackageImpl() {
		super(eNS_URI, DRModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link DRModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DRModelPackage init() {
		if (isInited) return (DRModelPackage)EPackage.Registry.INSTANCE.getEPackage(DRModelPackage.eNS_URI);

		// Obtain or create and register package
		DRModelPackageImpl theDRModelPackage = (DRModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DRModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new DRModelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI) : ModelPackage.eINSTANCE);

		// Create package meta-data objects
		theDRModelPackage.createPackageContents();
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theDRModelPackage.initializePackageContents();
		theModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDRModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DRModelPackage.eNS_URI, theDRModelPackage);
		return theDRModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EDesignRuleCollection() {
		return r4EDesignRuleCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EDesignRuleCollection_Areas() {
		return (EReference)r4EDesignRuleCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRuleCollection_Version() {
		return (EAttribute)r4EDesignRuleCollectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRuleCollection_Folder() {
		return (EAttribute)r4EDesignRuleCollectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRuleCollection_Name() {
		return (EAttribute)r4EDesignRuleCollectionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EDesignRule() {
		return r4EDesignRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRule_Id() {
		return (EAttribute)r4EDesignRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRule_Rank() {
		return (EAttribute)r4EDesignRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRule_Class() {
		return (EAttribute)r4EDesignRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRule_Title() {
		return (EAttribute)r4EDesignRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRule_Description() {
		return (EAttribute)r4EDesignRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EDesignRuleArea() {
		return r4EDesignRuleAreaEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EDesignRuleArea_Violations() {
		return (EReference)r4EDesignRuleAreaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRuleArea_Name() {
		return (EAttribute)r4EDesignRuleAreaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getR4EDesignRuleViolation() {
		return r4EDesignRuleViolationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getR4EDesignRuleViolation_Rules() {
		return (EReference)r4EDesignRuleViolationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getR4EDesignRuleViolation_Name() {
		return (EAttribute)r4EDesignRuleViolationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EDesignRuleRank() {
		return r4EDesignRuleRankEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getR4EDesignRuleClass() {
		return r4EDesignRuleClassEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DRModelFactory getDRModelFactory() {
		return (DRModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		r4EDesignRuleCollectionEClass = createEClass(R4E_DESIGN_RULE_COLLECTION);
		createEReference(r4EDesignRuleCollectionEClass, R4E_DESIGN_RULE_COLLECTION__AREAS);
		createEAttribute(r4EDesignRuleCollectionEClass, R4E_DESIGN_RULE_COLLECTION__VERSION);
		createEAttribute(r4EDesignRuleCollectionEClass, R4E_DESIGN_RULE_COLLECTION__FOLDER);
		createEAttribute(r4EDesignRuleCollectionEClass, R4E_DESIGN_RULE_COLLECTION__NAME);

		r4EDesignRuleEClass = createEClass(R4E_DESIGN_RULE);
		createEAttribute(r4EDesignRuleEClass, R4E_DESIGN_RULE__ID);
		createEAttribute(r4EDesignRuleEClass, R4E_DESIGN_RULE__RANK);
		createEAttribute(r4EDesignRuleEClass, R4E_DESIGN_RULE__CLASS);
		createEAttribute(r4EDesignRuleEClass, R4E_DESIGN_RULE__TITLE);
		createEAttribute(r4EDesignRuleEClass, R4E_DESIGN_RULE__DESCRIPTION);

		r4EDesignRuleAreaEClass = createEClass(R4E_DESIGN_RULE_AREA);
		createEReference(r4EDesignRuleAreaEClass, R4E_DESIGN_RULE_AREA__VIOLATIONS);
		createEAttribute(r4EDesignRuleAreaEClass, R4E_DESIGN_RULE_AREA__NAME);

		r4EDesignRuleViolationEClass = createEClass(R4E_DESIGN_RULE_VIOLATION);
		createEReference(r4EDesignRuleViolationEClass, R4E_DESIGN_RULE_VIOLATION__RULES);
		createEAttribute(r4EDesignRuleViolationEClass, R4E_DESIGN_RULE_VIOLATION__NAME);

		// Create enums
		r4EDesignRuleRankEEnum = createEEnum(R4E_DESIGN_RULE_RANK);
		r4EDesignRuleClassEEnum = createEEnum(R4E_DESIGN_RULE_CLASS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		r4EDesignRuleCollectionEClass.getESuperTypes().add(theModelPackage.getReviewComponent());
		r4EDesignRuleEClass.getESuperTypes().add(theModelPackage.getReviewComponent());
		r4EDesignRuleAreaEClass.getESuperTypes().add(theModelPackage.getReviewComponent());
		r4EDesignRuleViolationEClass.getESuperTypes().add(theModelPackage.getReviewComponent());

		// Initialize classes and features; add operations and parameters
		initEClass(r4EDesignRuleCollectionEClass, R4EDesignRuleCollection.class, "R4EDesignRuleCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EDesignRuleCollection_Areas(), this.getR4EDesignRuleArea(), null, "areas", null, 0, -1, R4EDesignRuleCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRuleCollection_Version(), ecorePackage.getEString(), "version", null, 0, 1, R4EDesignRuleCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRuleCollection_Folder(), ecorePackage.getEString(), "folder", null, 0, 1, R4EDesignRuleCollection.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRuleCollection_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EDesignRuleCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EDesignRuleEClass, R4EDesignRule.class, "R4EDesignRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getR4EDesignRule_Id(), ecorePackage.getEString(), "id", null, 0, 1, R4EDesignRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRule_Rank(), this.getR4EDesignRuleRank(), "rank", null, 0, 1, R4EDesignRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRule_Class(), this.getR4EDesignRuleClass(), "class", null, 0, 1, R4EDesignRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRule_Title(), ecorePackage.getEString(), "title", null, 0, 1, R4EDesignRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRule_Description(), ecorePackage.getEString(), "description", null, 0, 1, R4EDesignRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EDesignRuleAreaEClass, R4EDesignRuleArea.class, "R4EDesignRuleArea", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EDesignRuleArea_Violations(), this.getR4EDesignRuleViolation(), null, "violations", null, 0, -1, R4EDesignRuleArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRuleArea_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EDesignRuleArea.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(r4EDesignRuleViolationEClass, R4EDesignRuleViolation.class, "R4EDesignRuleViolation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getR4EDesignRuleViolation_Rules(), this.getR4EDesignRule(), null, "rules", null, 0, -1, R4EDesignRuleViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getR4EDesignRuleViolation_Name(), ecorePackage.getEString(), "name", null, 0, 1, R4EDesignRuleViolation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(r4EDesignRuleRankEEnum, R4EDesignRuleRank.class, "R4EDesignRuleRank");
		addEEnumLiteral(r4EDesignRuleRankEEnum, R4EDesignRuleRank.R4E_RANK_NONE);
		addEEnumLiteral(r4EDesignRuleRankEEnum, R4EDesignRuleRank.R4E_RANK_MINOR);
		addEEnumLiteral(r4EDesignRuleRankEEnum, R4EDesignRuleRank.R4E_RANK_MAJOR);

		initEEnum(r4EDesignRuleClassEEnum, R4EDesignRuleClass.class, "R4EDesignRuleClass");
		addEEnumLiteral(r4EDesignRuleClassEEnum, R4EDesignRuleClass.R4E_CLASS_ERRONEOUS);
		addEEnumLiteral(r4EDesignRuleClassEEnum, R4EDesignRuleClass.R4E_CLASS_SUPERFLUOUS);
		addEEnumLiteral(r4EDesignRuleClassEEnum, R4EDesignRuleClass.R4E_CLASS_IMPROVEMENT);
		addEEnumLiteral(r4EDesignRuleClassEEnum, R4EDesignRuleClass.R4E_CLASS_QUESTION);

		// Create resource
		createResource(eNS_URI);
	}

} //DRModelPackageImpl
