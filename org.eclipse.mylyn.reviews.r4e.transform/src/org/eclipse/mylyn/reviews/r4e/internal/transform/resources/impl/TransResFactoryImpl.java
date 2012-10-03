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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TransResFactoryImpl extends EFactoryImpl implements TransResFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TransResFactory init() {
		try {
			TransResFactory theTransResFactory = (TransResFactory)EPackage.Registry.INSTANCE.getEFactory("http://org.eclipse.mylyn.reviews.r4e.model.transform/1.0"); 
			if (theTransResFactory != null) {
				return theTransResFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TransResFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransResFactoryImpl() {
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
			case TransResPackage.REVIEW_GROUP_RES: return createReviewGroupRes();
			case TransResPackage.REVIEW_RES: return createReviewRes();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewGroupRes createReviewGroupRes() {
		ReviewGroupResImpl reviewGroupRes = new ReviewGroupResImpl();
		return reviewGroupRes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReviewRes createReviewRes() {
		ReviewResImpl reviewRes = new ReviewResImpl();
		return reviewRes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransResPackage getTransResPackage() {
		return (TransResPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TransResPackage getPackage() {
		return TransResPackage.eINSTANCE;
	}

} //TransResFactoryImpl
