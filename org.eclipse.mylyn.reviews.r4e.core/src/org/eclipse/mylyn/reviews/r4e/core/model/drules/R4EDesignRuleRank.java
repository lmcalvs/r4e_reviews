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
 *    Alvaro Sanchez-Leon - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.drules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>R4E Design Rule Rank</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#getR4EDesignRuleRank()
 * @model
 * @generated
 */
public enum R4EDesignRuleRank implements Enumerator {
	/**
	 * The '<em><b>R4E RANK NONE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_RANK_NONE_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_RANK_NONE(0, "R4E_RANK_NONE", "R4E_RANK_NONE"),

	/**
	 * The '<em><b>R4E RANK MINOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_RANK_MINOR_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_RANK_MINOR(1, "R4E_RANK_MINOR", ""),

	/**
	 * The '<em><b>R4E RANK MAJOR</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_RANK_MAJOR_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_RANK_MAJOR(2, "R4E_RANK_MAJOR", "R4E_RANK_MAJOR");

	/**
	 * The '<em><b>R4E RANK NONE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E RANK NONE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_RANK_NONE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_RANK_NONE_VALUE = 0;

	/**
	 * The '<em><b>R4E RANK MINOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E RANK MINOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_RANK_MINOR
	 * @model literal=""
	 * @generated
	 * @ordered
	 */
	public static final int R4E_RANK_MINOR_VALUE = 1;

	/**
	 * The '<em><b>R4E RANK MAJOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E RANK MAJOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_RANK_MAJOR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_RANK_MAJOR_VALUE = 2;

	/**
	 * An array of all the '<em><b>R4E Design Rule Rank</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EDesignRuleRank[] VALUES_ARRAY =
		new R4EDesignRuleRank[] {
			R4E_RANK_NONE,
			R4E_RANK_MINOR,
			R4E_RANK_MAJOR,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Design Rule Rank</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<R4EDesignRuleRank> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Design Rule Rank</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleRank get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EDesignRuleRank result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Design Rule Rank</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleRank getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EDesignRuleRank result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Design Rule Rank</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleRank get(int value) {
		switch (value) {
			case R4E_RANK_NONE_VALUE: return R4E_RANK_NONE;
			case R4E_RANK_MINOR_VALUE: return R4E_RANK_MINOR;
			case R4E_RANK_MAJOR_VALUE: return R4E_RANK_MAJOR;
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
	private R4EDesignRuleRank(int value, String name, String literal) {
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
	
} //R4EDesignRuleRank
