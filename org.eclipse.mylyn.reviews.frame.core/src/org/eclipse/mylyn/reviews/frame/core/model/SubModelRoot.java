/*******************************************************************************
 * Copyright (c) 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.frame.core.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sub Model Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getFragmentVersion <em>Fragment Version</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility <em>Compatibility</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion <em>Application Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getSubModelRoot()
 * @model
 * @generated
 */
public interface SubModelRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Fragment Version</b></em>' attribute.
	 * The default value is <code>"1.0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fragment Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fragment Version</em>' attribute.
	 * @see #setFragmentVersion(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getSubModelRoot_FragmentVersion()
	 * @model default="1.0.0"
	 * @generated
	 */
	String getFragmentVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getFragmentVersion <em>Fragment Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fragment Version</em>' attribute.
	 * @see #getFragmentVersion()
	 * @generated
	 */
	void setFragmentVersion(String value);

	/**
	 * Returns the value of the '<em><b>Compatibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * Returns:</br>-1 : The current application has a lower data structure revision than this instance</br> 0 : The
	 * current application has the same data structure revision than this instance</br>+1 : The current application has
	 * a higher data structure revision than this instance</br>
	 * 
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compatibility</em>' attribute.
	 * @see #isSetCompatibility()
	 * @see #unsetCompatibility()
	 * @see #setCompatibility(int)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getSubModelRoot_Compatibility()
	 * @model unsettable="true" transient="true" derived="true"
	 * @generated
	 */
	int getCompatibility();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility <em>Compatibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compatibility</em>' attribute.
	 * @see #isSetCompatibility()
	 * @see #unsetCompatibility()
	 * @see #getCompatibility()
	 * @generated
	 */
	void setCompatibility(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility <em>Compatibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCompatibility()
	 * @see #getCompatibility()
	 * @see #setCompatibility(int)
	 * @generated
	 */
	void unsetCompatibility();

	/**
	 * Returns whether the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getCompatibility <em>Compatibility</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Compatibility</em>' attribute is set.
	 * @see #unsetCompatibility()
	 * @see #getCompatibility()
	 * @see #setCompatibility(int)
	 * @generated
	 */
	boolean isSetCompatibility();

	/**
	 * Returns the value of the '<em><b>Application Version</b></em>' attribute.
	 * The default value is <code>"1.0.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Version</em>' attribute.
	 * @see #isSetApplicationVersion()
	 * @see #unsetApplicationVersion()
	 * @see #setApplicationVersion(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getSubModelRoot_ApplicationVersion()
	 * @model default="1.0.0" unsettable="true" transient="true" derived="true"
	 * @generated
	 */
	String getApplicationVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion <em>Application Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application Version</em>' attribute.
	 * @see #isSetApplicationVersion()
	 * @see #unsetApplicationVersion()
	 * @see #getApplicationVersion()
	 * @generated
	 */
	void setApplicationVersion(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion <em>Application Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetApplicationVersion()
	 * @see #getApplicationVersion()
	 * @see #setApplicationVersion(String)
	 * @generated
	 */
	void unsetApplicationVersion();

	/**
	 * Returns whether the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot#getApplicationVersion <em>Application Version</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Application Version</em>' attribute is set.
	 * @see #unsetApplicationVersion()
	 * @see #getApplicationVersion()
	 * @see #setApplicationVersion(String)
	 * @generated
	 */
	boolean isSetApplicationVersion();

} // SubModelRoot
