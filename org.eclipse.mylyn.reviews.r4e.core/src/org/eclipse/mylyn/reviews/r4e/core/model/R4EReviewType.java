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
 * A representation of the literals of the enumeration '<em><b>R4E Review Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EReviewType()
 * @model
 * @generated
 */
public enum R4EReviewType implements Enumerator {
	/**
	 * The '<em><b>R4E REVIEW TYPE BASIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_TYPE_BASIC_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_TYPE_BASIC(0, "R4E_REVIEW_TYPE_BASIC", "R4E_REVIEW_TYPE_BASIC"), /**
	 * The '<em><b>R4E REVIEW TYPE INFORMAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_TYPE_INFORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_TYPE_INFORMAL(1, "R4E_REVIEW_TYPE_INFORMAL", "R4E_REVIEW_TYPE_INFORMAL"), /**
	 * The '<em><b>R4E REVIEW TYPE FORMAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_TYPE_FORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_REVIEW_TYPE_FORMAL(2, "R4E_REVIEW_TYPE_FORMAL", "R4E_REVIEW_TYPE_FORMAL");

	/**
	 * The '<em><b>R4E REVIEW TYPE BASIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW TYPE BASIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_TYPE_BASIC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_TYPE_BASIC_VALUE = 0;

	/**
	 * The '<em><b>R4E REVIEW TYPE INFORMAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E REVIEW TYPE INFORMAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_TYPE_INFORMAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_TYPE_INFORMAL_VALUE = 1;

	/**
	 * The '<em><b>R4E REVIEW TYPE FORMAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E Review Type Formal</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_REVIEW_TYPE_FORMAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_REVIEW_TYPE_FORMAL_VALUE = 2;

	/**
	 * An array of all the '<em><b>R4E Review Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EReviewType[] VALUES_ARRAY =
		new R4EReviewType[] {
			R4E_REVIEW_TYPE_BASIC,
			R4E_REVIEW_TYPE_INFORMAL,
			R4E_REVIEW_TYPE_FORMAL,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Review Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<R4EReviewType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Review Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EReviewType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EReviewType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Review Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EReviewType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EReviewType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Review Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EReviewType get(int value) {
		switch (value) {
			case R4E_REVIEW_TYPE_BASIC_VALUE: return R4E_REVIEW_TYPE_BASIC;
			case R4E_REVIEW_TYPE_INFORMAL_VALUE: return R4E_REVIEW_TYPE_INFORMAL;
			case R4E_REVIEW_TYPE_FORMAL_VALUE: return R4E_REVIEW_TYPE_FORMAL;
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
	private R4EReviewType(int value, String name, String literal) {
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
	
} //R4EReviewType
