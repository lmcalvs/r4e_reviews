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
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>R4E Context Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EContextType()
 * @model
 * @generated
 */
public enum R4EContextType implements Enumerator {
	/**
	 * The '<em><b>R4E UNDEFINED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_UNDEFINED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_UNDEFINED(4, "R4E_UNDEFINED", "R4E_UNDEFINED"),

	/**
	 * The '<em><b>R4E ADDED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_ADDED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ADDED(0, "R4E_ADDED", "R4E_ADDED"),

	/**
	 * The '<em><b>R4E DELETED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_DELETED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_DELETED(1, "R4E_DELETED", "R4E_DELETED"),

	/**
	 * The '<em><b>R4E MODIFIED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_MODIFIED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_MODIFIED(2, "R4E_MODIFIED", "R4E_MODIFIED"),

	/**
	 * The '<em><b>R4E REPLACED</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REPLACED_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REPLACED(3, "R4E_REPLACED", "R4E_REPLACED");

	/**
	 * The '<em><b>R4E UNDEFINED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E UNDEFINED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_UNDEFINED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_UNDEFINED_VALUE = 4;

	/**
	 * The '<em><b>R4E ADDED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ADDED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ADDED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ADDED_VALUE = 0;

	/**
	 * The '<em><b>R4E DELETED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E DELETED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_DELETED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_DELETED_VALUE = 1;

	/**
	 * The '<em><b>R4E MODIFIED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E MODIFIED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_MODIFIED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_MODIFIED_VALUE = 2;

	/**
	 * The '<em><b>R4E REPLACED</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REPLACED</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REPLACED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REPLACED_VALUE = 3;

	/**
	 * An array of all the '<em><b>R4E Context Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EContextType[] VALUES_ARRAY =
		new R4EContextType[] {
			R4E_UNDEFINED,
			R4E_ADDED,
			R4E_DELETED,
			R4E_MODIFIED,
			R4E_REPLACED,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Context Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<R4EContextType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Context Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EContextType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EContextType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Context Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EContextType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EContextType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Context Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EContextType get(int value) {
		switch (value) {
			case R4E_UNDEFINED_VALUE: return R4E_UNDEFINED;
			case R4E_ADDED_VALUE: return R4E_ADDED;
			case R4E_DELETED_VALUE: return R4E_DELETED;
			case R4E_MODIFIED_VALUE: return R4E_MODIFIED;
			case R4E_REPLACED_VALUE: return R4E_REPLACED;
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
	private R4EContextType(int value, String name, String literal) {
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
	
} //R4EContextType
