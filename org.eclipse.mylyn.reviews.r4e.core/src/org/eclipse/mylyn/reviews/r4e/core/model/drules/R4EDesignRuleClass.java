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
 * A representation of the literals of the enumeration '<em><b>R4E Design Rule Class</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#getR4EDesignRuleClass()
 * @model
 * @generated
 */
public enum R4EDesignRuleClass implements Enumerator {
	/**
	 * The '<em><b>R4E CLASS ERRONEOUS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_ERRONEOUS_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_CLASS_ERRONEOUS(0, "R4E_CLASS_ERRONEOUS", "R4E_CLASS_ERRONEOUS"),

	/**
	 * The '<em><b>R4E CLASS SUPERFLUOUS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_SUPERFLUOUS_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_CLASS_SUPERFLUOUS(1, "R4E_CLASS_SUPERFLUOUS", "R4E_CLASS_SUPERFLUOUS"), /**
	 * The '<em><b>R4E CLASS IMPROVEMENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_IMPROVEMENT_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_CLASS_IMPROVEMENT(2, "R4E_CLASS_IMPROVEMENT", "R4E_CLASS_IMPROVEMENT"),

	/**
	 * The '<em><b>R4E CLASS QUESTION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_QUESTION_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_CLASS_QUESTION(3, "R4E_CLASS_QUESTION", "R4E_CLASS_QUESTION"), /**
	 * The '<em><b>R4E CLASS COMMENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_COMMENT_VALUE
	 * @generated
	 * @ordered
	 */
	R4E_CLASS_COMMENT(4, "R4E_CLASS_COMMENT", "R4E_CLASS_COMMENT");

	/**
	 * The '<em><b>R4E CLASS ERRONEOUS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E CLASS ERRONEOUS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_ERRONEOUS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_CLASS_ERRONEOUS_VALUE = 0;

	/**
	 * The '<em><b>R4E CLASS SUPERFLUOUS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E CLASS SUPERFLUOUS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_SUPERFLUOUS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_CLASS_SUPERFLUOUS_VALUE = 1;

	/**
	 * The '<em><b>R4E CLASS IMPROVEMENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E CLASS IMPROVEMENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_IMPROVEMENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_CLASS_IMPROVEMENT_VALUE = 2;

	/**
	 * The '<em><b>R4E CLASS QUESTION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E CLASS QUESTION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_QUESTION
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_CLASS_QUESTION_VALUE = 3;

	/**
	 * The '<em><b>R4E CLASS COMMENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>R4E CLASS COMMENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #R4E_CLASS_COMMENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int R4E_CLASS_COMMENT_VALUE = 4;

	/**
	 * An array of all the '<em><b>R4E Design Rule Class</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final R4EDesignRuleClass[] VALUES_ARRAY =
		new R4EDesignRuleClass[] {
			R4E_CLASS_ERRONEOUS,
			R4E_CLASS_SUPERFLUOUS,
			R4E_CLASS_IMPROVEMENT,
			R4E_CLASS_QUESTION,
			R4E_CLASS_COMMENT,
		};

	/**
	 * A public read-only list of all the '<em><b>R4E Design Rule Class</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<R4EDesignRuleClass> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>R4E Design Rule Class</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleClass get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EDesignRuleClass result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Design Rule Class</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleClass getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			R4EDesignRuleClass result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>R4E Design Rule Class</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleClass get(int value) {
		switch (value) {
			case R4E_CLASS_ERRONEOUS_VALUE: return R4E_CLASS_ERRONEOUS;
			case R4E_CLASS_SUPERFLUOUS_VALUE: return R4E_CLASS_SUPERFLUOUS;
			case R4E_CLASS_IMPROVEMENT_VALUE: return R4E_CLASS_IMPROVEMENT;
			case R4E_CLASS_QUESTION_VALUE: return R4E_CLASS_QUESTION;
			case R4E_CLASS_COMMENT_VALUE: return R4E_CLASS_COMMENT;
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
	private R4EDesignRuleClass(int value, String name, String literal) {
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
	
} //R4EDesignRuleClass
