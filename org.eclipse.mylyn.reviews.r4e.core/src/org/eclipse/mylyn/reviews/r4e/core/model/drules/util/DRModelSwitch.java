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
package org.eclipse.mylyn.reviews.r4e.core.model.drules.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.mylyn.reviews.core.model.IModelVersioning;
import org.eclipse.mylyn.reviews.core.model.IReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.*;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleViolation;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage
 * @generated
 */
public class DRModelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static DRModelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DRModelSwitch() {
		if (modelPackage == null) {
			modelPackage = DRModelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case DRModelPackage.R4E_DESIGN_RULE_COLLECTION: {
				R4EDesignRuleCollection r4EDesignRuleCollection = (R4EDesignRuleCollection)theEObject;
				T result = caseR4EDesignRuleCollection(r4EDesignRuleCollection);
				if (result == null) result = caseReviewComponent(r4EDesignRuleCollection);
				if (result == null) result = caseModelVersioning(r4EDesignRuleCollection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DRModelPackage.R4E_DESIGN_RULE: {
				R4EDesignRule r4EDesignRule = (R4EDesignRule)theEObject;
				T result = caseR4EDesignRule(r4EDesignRule);
				if (result == null) result = caseReviewComponent(r4EDesignRule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DRModelPackage.R4E_DESIGN_RULE_AREA: {
				R4EDesignRuleArea r4EDesignRuleArea = (R4EDesignRuleArea)theEObject;
				T result = caseR4EDesignRuleArea(r4EDesignRuleArea);
				if (result == null) result = caseReviewComponent(r4EDesignRuleArea);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case DRModelPackage.R4E_DESIGN_RULE_VIOLATION: {
				R4EDesignRuleViolation r4EDesignRuleViolation = (R4EDesignRuleViolation)theEObject;
				T result = caseR4EDesignRuleViolation(r4EDesignRuleViolation);
				if (result == null) result = caseReviewComponent(r4EDesignRuleViolation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Design Rule Collection</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Design Rule Collection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EDesignRuleCollection(R4EDesignRuleCollection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Design Rule</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Design Rule</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EDesignRule(R4EDesignRule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Design Rule Area</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Design Rule Area</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EDesignRuleArea(R4EDesignRuleArea object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>R4E Design Rule Violation</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>R4E Design Rule Violation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseR4EDesignRuleViolation(R4EDesignRuleViolation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Review Component</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Review Component</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReviewComponent(IReviewComponent object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model Versioning</em>'. <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model Versioning</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelVersioning(IModelVersioning object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
	 * anyway. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //DRModelSwitch
