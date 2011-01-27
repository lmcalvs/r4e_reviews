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
package org.eclipse.mylyn.reviews.r4e.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>R4E Decision</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EDecision()
 * @model
 * @generated
 */
public enum R4EDecision implements Enumerator {
	/**
	 * The '<em><b>R4E REVIEW DECISION NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_NONE_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_DECISION_NONE(0, "R4E_REVIEW_DECISION_NONE", "R4E_REVIEW_DECISION_NONE"),

	/**
	 * The '<em><b>R4E REVIEW DECISION ACCEPTED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_ACCEPTED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_DECISION_ACCEPTED(1, "R4E_REVIEW_DECISION_ACCEPTED", "R4E_REVIEW_DECISION_ACCEPTED"),

	/**
	 * The '<em><b>R4E REVIEW DECISION ACCEPTED FOLLOWUP</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP(2, "R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP", "R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP"),

	/**
	 * The '<em><b>R4E REVIEW DECISION REJECTED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_REJECTED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_DECISION_REJECTED(3, "R4E_REVIEW_DECISION_REJECTED", "R4E_REVIEW_DECISION_REJECTED");

	/**
	 * The '<em><b>R4E REVIEW DECISION NONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW DECISION NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_NONE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_DECISION_NONE_VALUE = 0;

	/**
	 * The '<em><b>R4E REVIEW DECISION ACCEPTED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW DECISION ACCEPTED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_ACCEPTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_DECISION_ACCEPTED_VALUE = 1;

	/**
	 * The '<em><b>R4E REVIEW DECISION ACCEPTED FOLLOWUP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW DECISION ACCEPTED FOLLOWUP</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP_VALUE = 2;

	/**
	 * The '<em><b>R4E REVIEW DECISION REJECTED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW DECISION REJECTED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_DECISION_REJECTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_DECISION_REJECTED_VALUE = 3;

	/**
	 * An array of all the '<em><b>R4E Decision</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EDecision[] VALUES_ARRAY =
		new R4EDecision[] {
			R4E_REVIEW_DECISION_NONE,
			R4E_REVIEW_DECISION_ACCEPTED,
			R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP,
			R4E_REVIEW_DECISION_REJECTED,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Decision</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<R4EDecision> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Decision</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDecision get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EDecision result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Decision</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDecision getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EDecision result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Decision</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDecision get(int value) {
		switch (value) {
			case R4E_REVIEW_DECISION_NONE_VALUE: return R4E_REVIEW_DECISION_NONE;
			case R4E_REVIEW_DECISION_ACCEPTED_VALUE: return R4E_REVIEW_DECISION_ACCEPTED;
			case R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP_VALUE: return R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP;
			case R4E_REVIEW_DECISION_REJECTED_VALUE: return R4E_REVIEW_DECISION_REJECTED;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private R4EDecision(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //R4EDecision
