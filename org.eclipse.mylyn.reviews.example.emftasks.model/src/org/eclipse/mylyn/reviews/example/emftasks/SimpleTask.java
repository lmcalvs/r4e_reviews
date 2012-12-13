/**
 */
package org.eclipse.mylyn.reviews.example.emftasks;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getSummary <em>Summary</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDueDate <em>Due Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCompletionDate <em>Completion Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getModificationDate <em>Modification Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCollaborators <em>Collaborators</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRelatedUrl <em>Related Url</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRanking <em>Ranking</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask()
 * @model
 * @generated
 */
public interface SimpleTask extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	int getId();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Returns the value of the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Summary</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Summary</em>' attribute.
	 * @see #setSummary(String)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Summary()
	 * @model required="true"
	 * @generated
	 */
	String getSummary();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getSummary <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Summary</em>' attribute.
	 * @see #getSummary()
	 * @generated
	 */
	void setSummary(String value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.example.emftasks.Status}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Status
	 * @see #setStatus(Status)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Status()
	 * @model
	 * @generated
	 */
	Status getStatus();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Status
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(Status value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.mylyn.reviews.example.emftasks.Priority}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Priority
	 * @see #setPriority(Priority)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Priority()
	 * @model
	 * @generated
	 */
	Priority getPriority();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Priority
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(Priority value);

	/**
	 * Returns the value of the '<em><b>Due Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Due Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Due Date</em>' attribute.
	 * @see #setDueDate(Date)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_DueDate()
	 * @model
	 * @generated
	 */
	Date getDueDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDueDate <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Due Date</em>' attribute.
	 * @see #getDueDate()
	 * @generated
	 */
	void setDueDate(Date value);

	/**
	 * Returns the value of the '<em><b>Completion Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Completion Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Completion Date</em>' attribute.
	 * @see #setCompletionDate(Date)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_CompletionDate()
	 * @model
	 * @generated
	 */
	Date getCompletionDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCompletionDate <em>Completion Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Completion Date</em>' attribute.
	 * @see #getCompletionDate()
	 * @generated
	 */
	void setCompletionDate(Date value);

	/**
	 * Returns the value of the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creation Date</em>' attribute.
	 * @see #setCreationDate(Date)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_CreationDate()
	 * @model
	 * @generated
	 */
	Date getCreationDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCreationDate <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Date</em>' attribute.
	 * @see #getCreationDate()
	 * @generated
	 */
	void setCreationDate(Date value);

	/**
	 * Returns the value of the '<em><b>Modification Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modification Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modification Date</em>' attribute.
	 * @see #setModificationDate(Date)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_ModificationDate()
	 * @model
	 * @generated
	 */
	Date getModificationDate();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getModificationDate <em>Modification Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modification Date</em>' attribute.
	 * @see #getModificationDate()
	 * @generated
	 */
	void setModificationDate(Date value);

	/**
	 * Returns the value of the '<em><b>Collaborators</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Collaborators</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Collaborators</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Collaborators()
	 * @model
	 * @generated
	 */
	EList<String> getCollaborators();

	/**
	 * Returns the value of the '<em><b>Related Url</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Url</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Url</em>' attribute list.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_RelatedUrl()
	 * @model
	 * @generated
	 */
	EList<String> getRelatedUrl();

	/**
	 * Returns the value of the '<em><b>Ranking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ranking</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranking</em>' attribute.
	 * @see #setRanking(double)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Ranking()
	 * @model
	 * @generated
	 */
	double getRanking();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRanking <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ranking</em>' attribute.
	 * @see #getRanking()
	 * @generated
	 */
	void setRanking(double value);

	/**
	 * Returns the value of the '<em><b>Category</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Category</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Category</em>' reference.
	 * @see #setCategory(Category)
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#getSimpleTask_Category()
	 * @model
	 * @generated
	 */
	Category getCategory();

	/**
	 * Sets the value of the '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCategory <em>Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Category</em>' reference.
	 * @see #getCategory()
	 * @generated
	 */
	void setCategory(Category value);

} // SimpleTask
