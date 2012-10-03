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
package org.eclipse.mylyn.reviews.r4e.core.model.drules;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>R4E Design Rule Class</b></em>',
 * and utility methods for working with them. <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelPackage#getR4EDesignRuleClass()
 * @model
 * @generated
 */
public enum R4EDesignRuleClass implements Enumerator {
	/**
	 * The '<em><b>Erroneous</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ERRONEOUS_VALUE
	 * @generated
	 * @ordered
	 */
	ERRONEOUS(0, "Erroneous", "ERRONEOUS"), /**
	 * The '<em><b>Superfluous</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SUPERFLUOUS_VALUE
	 * @generated
	 * @ordered
	 */
	SUPERFLUOUS(1, "Superfluous", "SUPERFLUOUS"), /**
	 * The '<em><b>Improvement</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IMPROVEMENT_VALUE
	 * @generated
	 * @ordered
	 */
	IMPROVEMENT(2, "Improvement", "IMPROVEMENT"), /**
	 * The '<em><b>Question</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUESTION_VALUE
	 * @generated
	 * @ordered
	 */
	QUESTION(3, "Question", "QUESTION"), /**
	 * The '<em><b>Comment</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #COMMENT_VALUE
	 * @generated
	 * @ordered
	 */
	COMMENT(4, "Comment", "COMMENT"), /**
	 * The '<em><b>Missing</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MISSING_VALUE
	 * @generated
	 * @ordered
	 */
	MISSING(5, "Missing", "MISSING");

	/**
	 * The '<em><b>Erroneous</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Erroneous</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ERRONEOUS
	 * @model name="Erroneous" literal="ERRONEOUS"
	 * @generated
	 * @ordered
	 */
	public static final int ERRONEOUS_VALUE = 0;

	/**
	 * The '<em><b>Superfluous</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Superfluous</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SUPERFLUOUS
	 * @model name="Superfluous" literal="SUPERFLUOUS"
	 * @generated
	 * @ordered
	 */
	public static final int SUPERFLUOUS_VALUE = 1;

	/**
	 * The '<em><b>Improvement</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Improvement</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IMPROVEMENT
	 * @model name="Improvement" literal="IMPROVEMENT"
	 * @generated
	 * @ordered
	 */
	public static final int IMPROVEMENT_VALUE = 2;

	/**
	 * The '<em><b>Question</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Question</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUESTION
	 * @model name="Question" literal="QUESTION"
	 * @generated
	 * @ordered
	 */
	public static final int QUESTION_VALUE = 3;

	/**
	 * The '<em><b>Comment</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Comment</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #COMMENT
	 * @model name="Comment" literal="COMMENT"
	 * @generated
	 * @ordered
	 */
	public static final int COMMENT_VALUE = 4;

	/**
	 * The '<em><b>Missing</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Missing</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MISSING
	 * @model name="Missing" literal="MISSING"
	 * @generated
	 * @ordered
	 */
	public static final int MISSING_VALUE = 5;

	/**
	 * An array of all the '<em><b>R4E Design Rule Class</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	private static final R4EDesignRuleClass[] VALUES_ARRAY = new R4EDesignRuleClass[] {
			ERRONEOUS,
			SUPERFLUOUS,
			IMPROVEMENT,
			QUESTION,
			COMMENT,
			MISSING,
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
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
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
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
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
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 */
	public static R4EDesignRuleClass get(int value) {
		switch (value) {
			case ERRONEOUS_VALUE: return ERRONEOUS;
			case SUPERFLUOUS_VALUE: return SUPERFLUOUS;
			case IMPROVEMENT_VALUE: return IMPROVEMENT;
			case QUESTION_VALUE: return QUESTION;
			case COMMENT_VALUE: return COMMENT;
			case MISSING_VALUE: return MISSING;
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
	private R4EDesignRuleClass(int value, String name, String literal) {
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

} //R4EDesignRuleClass
