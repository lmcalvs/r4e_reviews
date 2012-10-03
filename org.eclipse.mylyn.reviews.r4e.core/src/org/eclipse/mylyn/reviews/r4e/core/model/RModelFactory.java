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

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.team.core.history.IFileRevision;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage
 */
public interface RModelFactory extends EFactory, Persistence.RModelFactoryExt {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RModelFactory eINSTANCE = org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelFactoryImpl.init();



	/**
	 * Returns a new object of class '<em>R4E Review Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Review Group</em>'.
	 * @generated
	 */
	R4EReviewGroup createR4EReviewGroup();

	/**
	 * Returns a new object of class '<em>R4E Review</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Review</em>'.
	 * @generated
	 */
	R4EReview createR4EReview();

	/**
	 * Returns a new object of class '<em>R4E Anomaly</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Anomaly</em>'.
	 * @generated
	 */
	R4EAnomaly createR4EAnomaly();

	/**
	 * Returns a new object of class '<em>R4E Formal Review</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Formal Review</em>'.
	 * @generated
	 */
	R4EFormalReview createR4EFormalReview();

	/**
	 * Returns a new object of class '<em>R4E Text Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Text Position</em>'.
	 * @generated
	 */
	R4ETextPosition createR4ETextPosition();

	/**
	 * Returns a new object of class '<em>R4E Review Decision</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Review Decision</em>'.
	 * @generated
	 */
	R4EReviewDecision createR4EReviewDecision();

	/**
	 * Returns a new object of class '<em>R4E User</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E User</em>'.
	 * @generated
	 */
	R4EUser createR4EUser();

	/**
	 * Returns a new object of class '<em>R4E Participant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Participant</em>'.
	 * @generated
	 */
	R4EParticipant createR4EParticipant();

	/**
	 * Returns a new object of class '<em>R4E Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Item</em>'.
	 * @generated
	 */
	R4EItem createR4EItem();

	/**
	 * Returns a new object of class '<em>R4E Text Content</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Text Content</em>'.
	 * @generated
	 */
	R4ETextContent createR4ETextContent();

	/**
	 * Returns a new object of class '<em>R4EID</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4EID</em>'.
	 * @generated
	 */
	R4EID createR4EID();

	/**
	 * Returns a new object of class '<em>R4E Anomaly Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Anomaly Type</em>'.
	 * @generated
	 */
	R4EAnomalyType createR4EAnomalyType();

	/**
	 * Returns a new object of class '<em>R4E Task Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Task Reference</em>'.
	 * @generated
	 */
	R4ETaskReference createR4ETaskReference();

	/**
	 * Returns a new object of class '<em>R4E Review State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Review State</em>'.
	 * @generated
	 */
	R4EReviewState createR4EReviewState();

	/**
	 * Returns a new object of class '<em>R4E Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Comment</em>'.
	 * @generated
	 */
	R4EComment createR4EComment();

	/**
	 * Returns a new object of class '<em>R4E Review Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Review Component</em>'.
	 * @generated
	 */
	R4EReviewComponent createR4EReviewComponent();

	/**
	 * Returns a new object of class '<em>R4E File Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E File Context</em>'.
	 * @generated
	 */
	R4EFileContext createR4EFileContext();

	/**
	 * Returns a new object of class '<em>R4E Delta</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Delta</em>'.
	 * @generated
	 */
	R4EDelta createR4EDelta();

	/**
	 * Returns a new object of class '<em>R4E Comment Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Comment Type</em>'.
	 * @generated
	 */
	R4ECommentType createR4ECommentType();

	/**
	 * Returns a new object of class '<em>R4E Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Position</em>'.
	 * @generated
	 */
	R4EPosition createR4EPosition();

	/**
	 * Returns a new object of class '<em>R4E File Version</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E File Version</em>'.
	 * @generated
	 */
	R4EFileVersion createR4EFileVersion();

	/**
	 * Returns a new object of class '<em>R4E User Reviews</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E User Reviews</em>'.
	 * @generated
	 */
	R4EUserReviews createR4EUserReviews();

	/**
	 * Returns a new object of class '<em>R4EID Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4EID Component</em>'.
	 * @generated
	 */
	R4EIDComponent createR4EIDComponent();

	/**
	 * Returns a new object of class '<em>R4E Anomaly Text Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Anomaly Text Position</em>'.
	 * @generated
	 */
	R4EAnomalyTextPosition createR4EAnomalyTextPosition();

	/**
	 * Returns a new object of class '<em>R4E Review Phase Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Review Phase Info</em>'.
	 * @generated
	 */
	R4EReviewPhaseInfo createR4EReviewPhaseInfo();

	/**
	 * Returns a new object of class '<em>R4E Meeting Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>R4E Meeting Data</em>'.
	 * @generated
	 */
	R4EMeetingData createR4EMeetingData();

	/**
	 * Returns an instance of data type '<em>R4E Anomaly State</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	R4EAnomalyState createR4EAnomalyState(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>R4E Anomaly State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertR4EAnomalyState(R4EAnomalyState instanceValue);

	/**
	 * Returns an instance of data type '<em>R4E Review Phase</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	R4EReviewPhase createR4EReviewPhase(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>R4E Review Phase</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertR4EReviewPhase(R4EReviewPhase instanceValue);

	/**
	 * Returns an instance of data type '<em>R4E User Role</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	R4EUserRole createR4EUserRole(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>R4E User Role</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertR4EUserRole(R4EUserRole instanceValue);

	/**
	 * Returns an instance of data type '<em>R4E Decision</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	R4EDecision createR4EDecision(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>R4E Decision</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertR4EDecision(R4EDecision instanceValue);

	/**
	 * Returns an instance of data type '<em>R4E Review Type</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	R4EReviewType createR4EReviewType(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>R4E Review Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertR4EReviewType(R4EReviewType instanceValue);

	/**
	 * Returns an instance of data type '<em>R4E Context Type</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	R4EContextType createR4EContextType(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>R4E Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertR4EContextType(R4EContextType instanceValue);

	/**
	 * Returns an instance of data type '<em>IResource</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	IResource createIResource(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>IResource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertIResource(IResource instanceValue);

	/**
	 * Returns an instance of data type '<em>IFile Revision</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	IFileRevision createIFileRevision(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>IFile Revision</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertIFileRevision(IFileRevision instanceValue);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RModelPackage getRModelPackage();

} //RModelFactory
