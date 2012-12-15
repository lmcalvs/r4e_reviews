/**
 * Copyright (c) 2012, 2013 Ericsson AB and others
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB - Initial API
 */
package org.eclipse.mylyn.reviews.internal.notes.model.entityData;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.EntityDataFactory
 * @model kind="package"
 * @generated
 */
public interface EntityDataPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "entityData";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://org.eclipse.mylyn.reviews.model.notes.ecore/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.mylyn.reviews.model.notes.ecore";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EntityDataPackage eINSTANCE = org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityImpl
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ID = 0;

	/**
	 * The feature id for the '<em><b>Values Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__VALUES_MAP = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = 2;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.MapToValuesImpl <em>Map To Values</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.MapToValuesImpl
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl#getMapToValues()
	 * @generated
	 */
	int MAP_TO_VALUES = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_VALUES__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_VALUES__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Map To Values</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAP_TO_VALUES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.ValueContainerImpl <em>Value Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.ValueContainerImpl
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl#getValueContainer()
	 * @generated
	 */
	int VALUE_CONTAINER = 2;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_CONTAINER__TEXT = 0;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_CONTAINER__NUMBER = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_CONTAINER__DATE = 2;

	/**
	 * The number of structural features of the '<em>Value Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_CONTAINER_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity#getId()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Id();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity#getValuesMap <em>Values Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Values Map</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity#getValuesMap()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_ValuesMap();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Name();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Map To Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Map To Values</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString" keyRequired="true"
	 *        valueType="org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer" valueRequired="true"
	 * @generated
	 */
	EClass getMapToValues();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapToValues()
	 * @generated
	 */
	EAttribute getMapToValues_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getMapToValues()
	 * @generated
	 */
	EReference getMapToValues_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer <em>Value Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Container</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer
	 * @generated
	 */
	EClass getValueContainer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getText()
	 * @see #getValueContainer()
	 * @generated
	 */
	EAttribute getValueContainer_Text();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getNumber()
	 * @see #getValueContainer()
	 * @generated
	 */
	EAttribute getValueContainer_Number();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.ValueContainer#getDate()
	 * @see #getValueContainer()
	 * @generated
	 */
	EAttribute getValueContainer_Date();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EntityDataFactory getEntityDataFactory();

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
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityImpl
		 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__ID = eINSTANCE.getEntity_Id();

		/**
		 * The meta object literal for the '<em><b>Values Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__VALUES_MAP = eINSTANCE.getEntity_ValuesMap();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.MapToValuesImpl <em>Map To Values</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.MapToValuesImpl
		 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl#getMapToValues()
		 * @generated
		 */
		EClass MAP_TO_VALUES = eINSTANCE.getMapToValues();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MAP_TO_VALUES__KEY = eINSTANCE.getMapToValues_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAP_TO_VALUES__VALUE = eINSTANCE.getMapToValues_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.ValueContainerImpl <em>Value Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.ValueContainerImpl
		 * @see org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl.EntityDataPackageImpl#getValueContainer()
		 * @generated
		 */
		EClass VALUE_CONTAINER = eINSTANCE.getValueContainer();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_CONTAINER__TEXT = eINSTANCE.getValueContainer_Text();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_CONTAINER__NUMBER = eINSTANCE.getValueContainer_Number();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE_CONTAINER__DATE = eINSTANCE.getValueContainer_Date();

	}

} //EntityDataPackage
