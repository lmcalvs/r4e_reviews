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
package org.eclipse.mylyn.reviews.r4e.core.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>R4E Review Phase</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewPhase()
 * @model
 * @generated
 */
public enum R4EReviewPhase implements Enumerator {
	/**
	 * The '<em><b>R4E REVIEW PHASE STARTED</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_STARTED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_PHASE_STARTED(0, "R4E_REVIEW_PHASE_STARTED", "R4E_REVIEW_PHASE_STARTED"),

	/**
	 * The '<em><b>R4E REVIEW PHASE PREPARATION</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_PREPARATION_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_PHASE_PREPARATION(1, "R4E_REVIEW_PHASE_PREPARATION", "R4E_REVIEW_PHASE_PREPARATION"),

	/**
	 * The '<em><b>R4E REVIEW PHASE DECISION</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_DECISION_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_PHASE_DECISION(2, "R4E_REVIEW_PHASE_DECISION", "R4E_REVIEW_PHASE_DECISION"),

	/**
	 * The '<em><b>R4E REVIEW PHASE REWORK</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_REWORK_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_PHASE_REWORK(3, "R4E_REVIEW_PHASE_REWORK", "R4E_REVIEW_PHASE_REWORK"),

	/**
	 * The '<em><b>R4E REVIEW PHASE COMPLETED</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_COMPLETED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_PHASE_COMPLETED(4, "R4E_REVIEW_PHASE_COMPLETED", "R4E_REVIEW_PHASE_COMPLETED");

	/**
	 * The '<em><b>R4E REVIEW PHASE STARTED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW PHASE STARTED</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_STARTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_PHASE_STARTED_VALUE = 0;

	/**
	 * The '<em><b>R4E REVIEW PHASE PREPARATION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW PHASE PREPARATION</b></em>' literal object isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_PREPARATION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_PHASE_PREPARATION_VALUE = 1;

	/**
	 * The '<em><b>R4E REVIEW PHASE DECISION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW PHASE DECISION</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_DECISION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_PHASE_DECISION_VALUE = 2;

	/**
	 * The '<em><b>R4E REVIEW PHASE REWORK</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW PHASE REWORK</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_REWORK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_PHASE_REWORK_VALUE = 3;

	/**
	 * The '<em><b>R4E REVIEW PHASE COMPLETED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW PHASE COMPLETED</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_PHASE_COMPLETED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_PHASE_COMPLETED_VALUE = 4;

	/**
	 * An array of all the '<em><b>R4E Review Phase</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EReviewPhase[] VALUES_ARRAY = new R4EReviewPhase[] {
			R4E_REVIEW_PHASE_STARTED,
			R4E_REVIEW_PHASE_PREPARATION,
			R4E_REVIEW_PHASE_DECISION,
			R4E_REVIEW_PHASE_REWORK,
			R4E_REVIEW_PHASE_COMPLETED,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Review Phase</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static final List<R4EReviewPhase> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Review Phase</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EReviewPhase get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EReviewPhase result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Review Phase</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static R4EReviewPhase getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EReviewPhase result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Review Phase</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EReviewPhase get(int value) {
		switch (value) {
			case R4E_REVIEW_PHASE_STARTED_VALUE: return R4E_REVIEW_PHASE_STARTED;
			case R4E_REVIEW_PHASE_PREPARATION_VALUE: return R4E_REVIEW_PHASE_PREPARATION;
			case R4E_REVIEW_PHASE_DECISION_VALUE: return R4E_REVIEW_PHASE_DECISION;
			case R4E_REVIEW_PHASE_REWORK_VALUE: return R4E_REVIEW_PHASE_REWORK;
			case R4E_REVIEW_PHASE_COMPLETED_VALUE: return R4E_REVIEW_PHASE_COMPLETED;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private R4EReviewPhase(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} //R4EReviewPhase
