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
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>R4E Text Position</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl#getStartPosition <em>Start Position</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl#getLength <em>Length</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.impl.R4ETextPositionImpl#getEndLine <em>End Line</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class R4ETextPositionImpl extends R4EPositionImpl implements R4ETextPosition {
	/**
	 * The default value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int START_POSITION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartPosition() <em>Start Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPosition()
	 * @generated
	 * @ordered
	 */
	protected int startPosition = START_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected static final int LENGTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLength() <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLength()
	 * @generated
	 * @ordered
	 */
	protected int length = LENGTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartLine()
	 * @generated
	 * @ordered
	 */
	protected static final int START_LINE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartLine()
	 * @generated
	 * @ordered
	 */
	protected int startLine = START_LINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndLine()
	 * @generated
	 * @ordered
	 */
	protected static final int END_LINE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndLine()
	 * @generated
	 * @ordered
	 */
	protected int endLine = END_LINE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected R4ETextPositionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RModelPackage.Literals.R4E_TEXT_POSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPosition(int newStartPosition) {
		int oldStartPosition = startPosition;
		startPosition = newStartPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_TEXT_POSITION__START_POSITION, oldStartPosition, startPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLength() {
		return length;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLength(int newLength) {
		int oldLength = length;
		length = newLength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_TEXT_POSITION__LENGTH, oldLength, length));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartLine() {
		return startLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartLine(int newStartLine) {
		int oldStartLine = startLine;
		startLine = newStartLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_TEXT_POSITION__START_LINE, oldStartLine, startLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getEndLine() {
		return endLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndLine(int newEndLine) {
		int oldEndLine = endLine;
		endLine = newEndLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RModelPackage.R4E_TEXT_POSITION__END_LINE, oldEndLine, endLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RModelPackage.R4E_TEXT_POSITION__START_POSITION:
				return getStartPosition();
			case RModelPackage.R4E_TEXT_POSITION__LENGTH:
				return getLength();
			case RModelPackage.R4E_TEXT_POSITION__START_LINE:
				return getStartLine();
			case RModelPackage.R4E_TEXT_POSITION__END_LINE:
				return getEndLine();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RModelPackage.R4E_TEXT_POSITION__START_POSITION:
				setStartPosition((Integer)newValue);
				return;
			case RModelPackage.R4E_TEXT_POSITION__LENGTH:
				setLength((Integer)newValue);
				return;
			case RModelPackage.R4E_TEXT_POSITION__START_LINE:
				setStartLine((Integer)newValue);
				return;
			case RModelPackage.R4E_TEXT_POSITION__END_LINE:
				setEndLine((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RModelPackage.R4E_TEXT_POSITION__START_POSITION:
				setStartPosition(START_POSITION_EDEFAULT);
				return;
			case RModelPackage.R4E_TEXT_POSITION__LENGTH:
				setLength(LENGTH_EDEFAULT);
				return;
			case RModelPackage.R4E_TEXT_POSITION__START_LINE:
				setStartLine(START_LINE_EDEFAULT);
				return;
			case RModelPackage.R4E_TEXT_POSITION__END_LINE:
				setEndLine(END_LINE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RModelPackage.R4E_TEXT_POSITION__START_POSITION:
				return startPosition != START_POSITION_EDEFAULT;
			case RModelPackage.R4E_TEXT_POSITION__LENGTH:
				return length != LENGTH_EDEFAULT;
			case RModelPackage.R4E_TEXT_POSITION__START_LINE:
				return startLine != START_LINE_EDEFAULT;
			case RModelPackage.R4E_TEXT_POSITION__END_LINE:
				return endLine != END_LINE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startPosition: ");
		result.append(startPosition);
		result.append(", length: ");
		result.append(length);
		result.append(", startLine: ");
		result.append(startLine);
		result.append(", endLine: ");
		result.append(endLine);
		result.append(')');
		return result.toString();
	}

} //R4ETextPositionImpl
