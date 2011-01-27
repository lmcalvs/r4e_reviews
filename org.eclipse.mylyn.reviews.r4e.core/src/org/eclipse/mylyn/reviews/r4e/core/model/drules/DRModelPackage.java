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
package org.eclipse.mylyn.reviews.r4e.core.model.drules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelFactory
 * @model kind="package"
 * @generated
 */
public interface DRModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "drules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.mylyn.reviews.r4e.core.model.drules.ecore/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.mylyn.reviews.r4e.core.model.drules.ecore";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DRModelPackage eINSTANCE = org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl <em>R4E Design Rule Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleCollection()
	 * @generated
	 */
	int R4E_DESIGN_RULE_COLLECTION = 0;

	/**
	 * The feature id for the '<em><b>Areas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_COLLECTION__AREAS = 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_COLLECTION__VERSION = 1;

	/**
	 * The feature id for the '<em><b>File Paths</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_COLLECTION__FILE_PATHS = 2;

	/**
	 * The number of structural features of the '<em>R4E Design Rule Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_COLLECTION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl <em>R4E Design Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRule()
	 * @generated
	 */
	int R4E_DESIGN_RULE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE__ID = 0;

	/**
	 * The feature id for the '<em><b>Rank</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE__RANK = 1;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE__CLASS = 2;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE__TITLE = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE__DESCRIPTION = 4;

	/**
	 * The number of structural features of the '<em>R4E Design Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleAreaImpl <em>R4E Design Rule Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleAreaImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleArea()
	 * @generated
	 */
	int R4E_DESIGN_RULE_AREA = 2;

	/**
	 * The feature id for the '<em><b>Violations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_AREA__VIOLATIONS = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_AREA__NAME = 1;

	/**
	 * The number of structural features of the '<em>R4E Design Rule Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_AREA_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleViolationImpl <em>R4E Design Rule Violation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleViolationImpl
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleViolation()
	 * @generated
	 */
	int R4E_DESIGN_RULE_VIOLATION = 3;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_VIOLATION__RULES = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_VIOLATION__NAME = 1;

	/**
	 * The number of structural features of the '<em>R4E Design Rule Violation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int R4E_DESIGN_RULE_VIOLATION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank <em>R4E Design Rule Rank</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleRank()
	 * @generated
	 */
	int R4E_DESIGN_RULE_RANK = 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection <em>R4E Design Rule Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Design Rule Collection</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection
	 * @generated
	 */
	EClass getR4EDesignRuleCollection();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection#getAreas <em>Areas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Areas</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection#getAreas()
	 * @see #getR4EDesignRuleCollection()
	 * @generated
	 */
	EReference getR4EDesignRuleCollection_Areas();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection#getVersion()
	 * @see #getR4EDesignRuleCollection()
	 * @generated
	 */
	EAttribute getR4EDesignRuleCollection_Version();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection#getFilePaths <em>File Paths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>File Paths</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection#getFilePaths()
	 * @see #getR4EDesignRuleCollection()
	 * @generated
	 */
	EAttribute getR4EDesignRuleCollection_FilePaths();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule <em>R4E Design Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Design Rule</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule
	 * @generated
	 */
	EClass getR4EDesignRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getId()
	 * @see #getR4EDesignRule()
	 * @generated
	 */
	EAttribute getR4EDesignRule_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getRank <em>Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rank</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getRank()
	 * @see #getR4EDesignRule()
	 * @generated
	 */
	EAttribute getR4EDesignRule_Rank();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getClass_()
	 * @see #getR4EDesignRule()
	 * @generated
	 */
	EAttribute getR4EDesignRule_Class();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getTitle()
	 * @see #getR4EDesignRule()
	 * @generated
	 */
	EAttribute getR4EDesignRule_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule#getDescription()
	 * @see #getR4EDesignRule()
	 * @generated
	 */
	EAttribute getR4EDesignRule_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea <em>R4E Design Rule Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Design Rule Area</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea
	 * @generated
	 */
	EClass getR4EDesignRuleArea();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getViolations <em>Violations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Violations</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getViolations()
	 * @see #getR4EDesignRuleArea()
	 * @generated
	 */
	EReference getR4EDesignRuleArea_Violations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea#getName()
	 * @see #getR4EDesignRuleArea()
	 * @generated
	 */
	EAttribute getR4EDesignRuleArea_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation <em>R4E Design Rule Violation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>R4E Design Rule Violation</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation
	 * @generated
	 */
	EClass getR4EDesignRuleViolation();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation#getRules()
	 * @see #getR4EDesignRuleViolation()
	 * @generated
	 */
	EReference getR4EDesignRuleViolation_Rules();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation#getName()
	 * @see #getR4EDesignRuleViolation()
	 * @generated
	 */
	EAttribute getR4EDesignRuleViolation_Name();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank <em>R4E Design Rule Rank</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>R4E Design Rule Rank</em>'.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank
	 * @generated
	 */
	EEnum getR4EDesignRuleRank();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DRModelFactory getDRModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl <em>R4E Design Rule Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleCollectionImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleCollection()
		 * @generated
		 */
		EClass R4E_DESIGN_RULE_COLLECTION = eINSTANCE.getR4EDesignRuleCollection();

		/**
		 * The meta object literal for the '<em><b>Areas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_DESIGN_RULE_COLLECTION__AREAS = eINSTANCE.getR4EDesignRuleCollection_Areas();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE_COLLECTION__VERSION = eINSTANCE.getR4EDesignRuleCollection_Version();

		/**
		 * The meta object literal for the '<em><b>File Paths</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE_COLLECTION__FILE_PATHS = eINSTANCE.getR4EDesignRuleCollection_FilePaths();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl <em>R4E Design Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRule()
		 * @generated
		 */
		EClass R4E_DESIGN_RULE = eINSTANCE.getR4EDesignRule();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE__ID = eINSTANCE.getR4EDesignRule_Id();

		/**
		 * The meta object literal for the '<em><b>Rank</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE__RANK = eINSTANCE.getR4EDesignRule_Rank();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE__CLASS = eINSTANCE.getR4EDesignRule_Class();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE__TITLE = eINSTANCE.getR4EDesignRule_Title();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE__DESCRIPTION = eINSTANCE.getR4EDesignRule_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleAreaImpl <em>R4E Design Rule Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleAreaImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleArea()
		 * @generated
		 */
		EClass R4E_DESIGN_RULE_AREA = eINSTANCE.getR4EDesignRuleArea();

		/**
		 * The meta object literal for the '<em><b>Violations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_DESIGN_RULE_AREA__VIOLATIONS = eINSTANCE.getR4EDesignRuleArea_Violations();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE_AREA__NAME = eINSTANCE.getR4EDesignRuleArea_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleViolationImpl <em>R4E Design Rule Violation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.R4EDesignRuleViolationImpl
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleViolation()
		 * @generated
		 */
		EClass R4E_DESIGN_RULE_VIOLATION = eINSTANCE.getR4EDesignRuleViolation();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference R4E_DESIGN_RULE_VIOLATION__RULES = eINSTANCE.getR4EDesignRuleViolation_Rules();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute R4E_DESIGN_RULE_VIOLATION__NAME = eINSTANCE.getR4EDesignRuleViolation_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank <em>R4E Design Rule Rank</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank
		 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.impl.DRModelPackageImpl#getR4EDesignRuleRank()
		 * @generated
		 */
		EEnum R4E_DESIGN_RULE_RANK = eINSTANCE.getR4EDesignRuleRank();

	}

} //DRModelPackage
