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
package org.eclipse.mylyn.reviews.r4e.core.model.drules.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.*;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class DRModelFactoryImpl extends EFactoryImpl implements DRModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static DRModelFactory init() {
		try {
			DRModelFactory theDRModelFactory = (DRModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://org.eclipse.mylyn.reviews.r4e.core.model.drules.ecore/1.0"); 
			if (theDRModelFactory != null) {
				return theDRModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DRModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DRModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION: return createR4EDesignRuleCollection();
			case DRModelPackage.R4E_DESIGN_RULE: return createR4EDesignRule();
			case DRModelPackage.R4E_DESIGN_RULE_AREA: return createR4EDesignRuleArea();
			case DRModelPackage.R4E_DESIGN_RULE_VIOLATION: return createR4EDesignRuleViolation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case DRModelPackage.R4E_DESIGN_RULE_RANK:
				return createR4EDesignRuleRankFromString(eDataType, initialValue);
			case DRModelPackage.R4E_DESIGN_RULE_CLASS:
				return createR4EDesignRuleClassFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case DRModelPackage.R4E_DESIGN_RULE_RANK:
				return convertR4EDesignRuleRankToString(eDataType, instanceValue);
			case DRModelPackage.R4E_DESIGN_RULE_CLASS:
				return convertR4EDesignRuleClassToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleCollection createR4EDesignRuleCollection() {
		R4EDesignRuleCollectionImpl r4EDesignRuleCollection = new R4EDesignRuleCollectionImpl();
		return r4EDesignRuleCollection;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRule createR4EDesignRule() {
		R4EDesignRuleImpl r4EDesignRule = new R4EDesignRuleImpl();
		return r4EDesignRule;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleArea createR4EDesignRuleArea() {
		R4EDesignRuleAreaImpl r4EDesignRuleArea = new R4EDesignRuleAreaImpl();
		return r4EDesignRuleArea;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleViolation createR4EDesignRuleViolation() {
		R4EDesignRuleViolationImpl r4EDesignRuleViolation = new R4EDesignRuleViolationImpl();
		return r4EDesignRuleViolation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleRank createR4EDesignRuleRankFromString(EDataType eDataType, String initialValue) {
		R4EDesignRuleRank result = R4EDesignRuleRank.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertR4EDesignRuleRankToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public R4EDesignRuleClass createR4EDesignRuleClassFromString(EDataType eDataType, String initialValue) {
		R4EDesignRuleClass result = R4EDesignRuleClass.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertR4EDesignRuleClassToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DRModelPackage getDRModelPackage() {
		return (DRModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DRModelPackage getPackage() {
		return DRModelPackage.eINSTANCE;
	}

} //DRModelFactoryImpl
