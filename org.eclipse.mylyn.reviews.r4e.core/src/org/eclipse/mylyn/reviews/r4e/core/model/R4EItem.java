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

import java.util.Date;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.mylyn.reviews.frame.core.model.Item;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>R4E Item</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAddedById <em>Added By Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getFileContextList <em>File Context List</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getRepositoryRef <em>Repository Ref</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getProjectURIs <em>Project UR Is</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAuthorRep <em>Author Rep</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getSubmitted <em>Submitted</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getInfoAtt <em>Info Att</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem()
 * @model
 * @generated
 */
public interface R4EItem extends R4EIDComponent, Item {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Added By Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Added By Id</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Added By Id</em>' attribute.
	 * @see #setAddedById(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_AddedById()
	 * @model
	 * @generated
	 */
	String getAddedById();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAddedById <em>Added By Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Added By Id</em>' attribute.
	 * @see #getAddedById()
	 * @generated
	 */
	void setAddedById(String value);

	/**
	 * Returns the value of the '<em><b>File Context List</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Context List</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Context List</em>' containment reference list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_FileContextList()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<R4EFileContext> getFileContextList();

	/**
	 * Returns the value of the '<em><b>Repository Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository Ref</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository Ref</em>' attribute.
	 * @see #setRepositoryRef(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_RepositoryRef()
	 * @model
	 * @generated
	 */
	String getRepositoryRef();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getRepositoryRef <em>Repository Ref</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository Ref</em>' attribute.
	 * @see #getRepositoryRef()
	 * @generated
	 */
	void setRepositoryRef(String value);

	/**
	 * Returns the value of the '<em><b>Project UR Is</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project UR Is</em>' attribute list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project UR Is</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_ProjectURIs()
	 * @model
	 * @generated
	 */
	EList<String> getProjectURIs();

	/**
	 * Returns the value of the '<em><b>Author Rep</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author Rep</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Author Rep</em>' attribute.
	 * @see #setAuthorRep(String)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_AuthorRep()
	 * @model
	 * @generated
	 */
	String getAuthorRep();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getAuthorRep <em>Author Rep</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author Rep</em>' attribute.
	 * @see #getAuthorRep()
	 * @generated
	 */
	void setAuthorRep(String value);

	/**
	 * Returns the value of the '<em><b>Submitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Submitted</em>' attribute isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Submitted</em>' attribute.
	 * @see #setSubmitted(Date)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_Submitted()
	 * @model
	 * @generated
	 */
	Date getSubmitted();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.r4e.core.model.R4EItem#getSubmitted <em>Submitted</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Submitted</em>' attribute.
	 * @see #getSubmitted()
	 * @generated
	 */
	void setSubmitted(Date value);

	/**
	 * Returns the value of the '<em><b>Info Att</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Att</em>' map isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Att</em>' map.
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage#getR4EItem_InfoAtt()
	 * @model mapType="org.eclipse.mylyn.reviews.r4e.core.model.MapKeyToInfoAttributes<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getInfoAtt();

} // R4EItem
