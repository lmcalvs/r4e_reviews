/*******************************************************************************
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
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.frame.core.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Task Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getTaskId <em>Task Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getRepositoryURL <em>Repository URL</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTaskReference()
 * @model
 * @generated
 */
public interface TaskReference extends ReviewComponent {
	/**
	 * Returns the value of the '<em><b>Task Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Id</em>' attribute.
	 * @see #setTaskId(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTaskReference_TaskId()
	 * @model
	 * @generated
	 */
	String getTaskId();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getTaskId <em>Task Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task Id</em>' attribute.
	 * @see #getTaskId()
	 * @generated
	 */
	void setTaskId(String value);

	/**
	 * Returns the value of the '<em><b>Repository URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository URL</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository URL</em>' attribute.
	 * @see #setRepositoryURL(String)
	 * @see org.eclipse.mylyn.reviews.frame.core.model.ModelPackage#getTaskReference_RepositoryURL()
	 * @model
	 * @generated
	 */
	String getRepositoryURL();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.frame.core.model.TaskReference#getRepositoryURL <em>Repository URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository URL</em>' attribute.
	 * @see #getRepositoryURL()
	 * @generated
	 */
	void setRepositoryURL(String value);

} // TaskReference
