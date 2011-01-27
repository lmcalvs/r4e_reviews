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

import org.eclipse.mylyn.reviews.frame.core.model.TaskReference;

import org.eclipse.mylyn.tasks.core.ITask;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>R4E Task Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference#getTask <em>Task</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETaskReference()
 * @model
 * @generated
 */
public interface R4ETaskReference extends TaskReference, R4EReviewComponent {
	/**
	 * Returns the value of the '<em><b>Task</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task</em>' attribute.
	 * @see #setTask(ITask)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4ETaskReference_Task()
	 * @model dataType="org.eclipse.mylyn.reviews.r4e.core.model.MylynTask"
	 * @generated
	 */
	ITask getTask();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference#getTask <em>Task</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task</em>' attribute.
	 * @see #getTask()
	 * @generated
	 */
	void setTask(ITask value);

} // R4ETaskReference
