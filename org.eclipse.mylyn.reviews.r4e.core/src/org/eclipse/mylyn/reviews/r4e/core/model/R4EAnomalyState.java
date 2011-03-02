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
 * A representation of the literals of the enumeration '<em><b>R4E Anomaly State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EAnomalyState()
 * @model
 * @generated
 */
public enum R4EAnomalyState implements Enumerator {
	/**
	 * The '<em><b>R4E ANOMALY STATE CREATED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_CREATED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_CREATED(5, "R4E_ANOMALY_STATE_CREATED", "R4E_ANOMALY_STATE_CREATED"),

	/**
	 * The '<em><b>R4E ANOMALY STATE ASSIGNED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_ASSIGNED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_ASSIGNED(4, "R4E_ANOMALY_STATE_ASSIGNED", "R4E_ANOMALY_STATE_ASSIGNED"),

	/**
	 * The '<em><b>R4E ANOMALY STATE ACCEPTED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_ACCEPTED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_ACCEPTED(0, "R4E_ANOMALY_STATE_ACCEPTED", "R4E_ANOMALY_STATE_ACCEPTED"),

	/**
	 * The '<em><b>R4E ANOMALY STATE FIXED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_FIXED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_FIXED(7, "R4E_ANOMALY_STATE_FIXED", "R4E_ANOMALY_STATE_FIXED"),

	/**
	 * The '<em><b>R4E ANOMALY STATE DUPLICATED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_DUPLICATED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_DUPLICATED(1, "R4E_ANOMALY_STATE_DUPLICATED", "R4E_ANOMALY_STATE_DUPLICATED"),

	/**
	 * The '<em><b>R4E ANOMALY STATE REJECTED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_REJECTED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_REJECTED(2, "R4E_ANOMALY_STATE_REJECTED", "R4E_ANOMALY_STATE_REJECTED"),

	/**
	 * The '<em><b>R4E ANOMALY STATE DEFERRED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_DEFERRED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_DEFERRED(3, "R4E_ANOMALY_STATE_DEFERRED", "R4E_ANOMALY_STATE_DEFERRED"), /**
	 * The '<em><b>R4E ANOMALY STATE VERIFIED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_VERIFIED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ANOMALY_STATE_VERIFIED(6, "R4E_ANOMALY_STATE_VERIFIED", "R4E_ANOMALY_STATE_VERIFIED");

	/**
	 * The '<em><b>R4E ANOMALY STATE CREATED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE CREATED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_CREATED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_CREATED_VALUE = 5;

	/**
	 * The '<em><b>R4E ANOMALY STATE ASSIGNED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE ASSIGNED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_ASSIGNED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_ASSIGNED_VALUE = 4;

	/**
	 * The '<em><b>R4E ANOMALY STATE ACCEPTED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE ACCEPTED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_ACCEPTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_ACCEPTED_VALUE = 0;

	/**
	 * The '<em><b>R4E ANOMALY STATE FIXED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE FIXED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_FIXED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_FIXED_VALUE = 7;

	/**
	 * The '<em><b>R4E ANOMALY STATE DUPLICATED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE DUPLICATED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_DUPLICATED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_DUPLICATED_VALUE = 1;

	/**
	 * The '<em><b>R4E ANOMALY STATE REJECTED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE REJECTED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_REJECTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_REJECTED_VALUE = 2;

	/**
	 * The '<em><b>R4E ANOMALY STATE DEFERRED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE DEFERRED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_DEFERRED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_DEFERRED_VALUE = 3;

	/**
	 * The '<em><b>R4E ANOMALY STATE VERIFIED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ANOMALY STATE VERIFIED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ANOMALY_STATE_VERIFIED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ANOMALY_STATE_VERIFIED_VALUE = 6;

	/**
	 * An array of all the '<em><b>R4E Anomaly State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EAnomalyState[] VALUES_ARRAY =
		new R4EAnomalyState[] {
			R4E_ANOMALY_STATE_CREATED,
			R4E_ANOMALY_STATE_ASSIGNED,
			R4E_ANOMALY_STATE_ACCEPTED,
			R4E_ANOMALY_STATE_FIXED,
			R4E_ANOMALY_STATE_DUPLICATED,
			R4E_ANOMALY_STATE_REJECTED,
			R4E_ANOMALY_STATE_DEFERRED,
			R4E_ANOMALY_STATE_VERIFIED,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Anomaly State</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<R4EAnomalyState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Anomaly State</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EAnomalyState get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EAnomalyState result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Anomaly State</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EAnomalyState getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EAnomalyState result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Anomaly State</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EAnomalyState get(int value) {
		switch (value) {
			case R4E_ANOMALY_STATE_CREATED_VALUE: return R4E_ANOMALY_STATE_CREATED;
			case R4E_ANOMALY_STATE_ASSIGNED_VALUE: return R4E_ANOMALY_STATE_ASSIGNED;
			case R4E_ANOMALY_STATE_ACCEPTED_VALUE: return R4E_ANOMALY_STATE_ACCEPTED;
			case R4E_ANOMALY_STATE_FIXED_VALUE: return R4E_ANOMALY_STATE_FIXED;
			case R4E_ANOMALY_STATE_DUPLICATED_VALUE: return R4E_ANOMALY_STATE_DUPLICATED;
			case R4E_ANOMALY_STATE_REJECTED_VALUE: return R4E_ANOMALY_STATE_REJECTED;
			case R4E_ANOMALY_STATE_DEFERRED_VALUE: return R4E_ANOMALY_STATE_DEFERRED;
			case R4E_ANOMALY_STATE_VERIFIED_VALUE: return R4E_ANOMALY_STATE_VERIFIED;
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
	private R4EAnomalyState(int value, String name, String literal) {
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
	
} //R4EAnomalyState
