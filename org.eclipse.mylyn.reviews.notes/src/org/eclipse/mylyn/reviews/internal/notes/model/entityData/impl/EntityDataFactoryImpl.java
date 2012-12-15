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
package org.eclipse.mylyn.reviews.internal.notes.model.entityData.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.mylyn.reviews.internal.notes.model.entityData.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EntityDataFactoryImpl extends EFactoryImpl implements EntityDataFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static EntityDataFactory init() {
		try {
			EntityDataFactory theEntityDataFactory = (EntityDataFactory)EPackage.Registry.INSTANCE.getEFactory("http://org.eclipse.mylyn.reviews.model.notes.ecore/1.0"); 
			if (theEntityDataFactory != null) {
				return theEntityDataFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new EntityDataFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityDataFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case EntityDataPackage.ENTITY: return createEntity();
			case EntityDataPackage.MAP_TO_VALUES: return (EObject)createMapToValues();
			case EntityDataPackage.VALUE_CONTAINER: return createValueContainer();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, ValueContainer> createMapToValues() {
		MapToValuesImpl mapToValues = new MapToValuesImpl();
		return mapToValues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueContainer createValueContainer() {
		ValueContainerImpl valueContainer = new ValueContainerImpl();
		return valueContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityDataPackage getEntityDataPackage() {
		return (EntityDataPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static EntityDataPackage getPackage() {
		return EntityDataPackage.eINSTANCE;
	}

} //EntityDataFactoryImpl
