/**
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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>R4E User Role</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EUserRole()
 * @model
 * @generated
 */
public enum R4EUserRole implements Enumerator {
	/**
	 * The '<em><b>R4E ROLE REVIEWER</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_ROLE_REVIEWER_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ROLE_REVIEWER(0, "R4E_ROLE_REVIEWER", "R4E_ROLE_REVIEWER"), /**
	 * The '<em><b>R4E ROLE LEAD</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_ROLE_LEAD_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ROLE_LEAD(1, "R4E_ROLE_LEAD", "R4E_ROLE_LEAD"), /**
	 * The '<em><b>R4E ROLE AUTHOR</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #R4E_ROLE_AUTHOR_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ROLE_AUTHOR(2, "R4E_ROLE_AUTHOR", "R4E_ROLE_AUTHOR"), /**
	 * The '<em><b>R4E ROLE ORGANIZER</b></em>' literal object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #R4E_ROLE_ORGANIZER_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_ROLE_ORGANIZER(3, "R4E_ROLE_ORGANIZER", "R4E_ROLE_ORGANIZER");

	/**
	 * The '<em><b>R4E ROLE REVIEWER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ROLE REVIEWER</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ROLE_REVIEWER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ROLE_REVIEWER_VALUE = 0;

	/**
	 * The '<em><b>R4E ROLE LEAD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ROLE LEAD</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ROLE_LEAD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ROLE_LEAD_VALUE = 1;

	/**
	 * The '<em><b>R4E ROLE AUTHOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ROLE AUTHOR</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ROLE_AUTHOR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ROLE_AUTHOR_VALUE = 2;

	/**
	 * The '<em><b>R4E ROLE ORGANIZER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E ROLE ORGANIZER</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_ROLE_ORGANIZER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_ROLE_ORGANIZER_VALUE = 3;

	/**
	 * An array of all the '<em><b>R4E User Role</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EUserRole[] VALUES_ARRAY = new R4EUserRole[] {
			R4E_ROLE_REVIEWER,
			R4E_ROLE_LEAD,
			R4E_ROLE_AUTHOR,
			R4E_ROLE_ORGANIZER,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E User Role</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static final List<R4EUserRole> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E User Role</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EUserRole get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EUserRole result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E User Role</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static R4EUserRole getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EUserRole result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E User Role</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EUserRole get(int value) {
		switch (value) {
			case R4E_ROLE_REVIEWER_VALUE: return R4E_ROLE_REVIEWER;
			case R4E_ROLE_LEAD_VALUE: return R4E_ROLE_LEAD;
			case R4E_ROLE_AUTHOR_VALUE: return R4E_ROLE_AUTHOR;
			case R4E_ROLE_ORGANIZER_VALUE: return R4E_ROLE_ORGANIZER;
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
	private R4EUserRole(int value, String name, String literal) {
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

} //R4EUserRole
