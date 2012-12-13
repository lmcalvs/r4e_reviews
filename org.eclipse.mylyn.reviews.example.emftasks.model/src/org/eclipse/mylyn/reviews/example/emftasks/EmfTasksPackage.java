/**
 */
package org.eclipse.mylyn.reviews.example.emftasks;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksFactory
 * @model kind="package"
 * @generated
 */
public interface EmfTasksPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "emftasks";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://eclipse.org/mylyn/tasks/emftasks/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "emftasks";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmfTasksPackage eINSTANCE = org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl <em>Simple Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getSimpleTask()
	 * @generated
	 */
	int SIMPLE_TASK = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__ID = 0;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__SUMMARY = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__STATUS = 3;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__PRIORITY = 4;

	/**
	 * The feature id for the '<em><b>Due Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__DUE_DATE = 5;

	/**
	 * The feature id for the '<em><b>Completion Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__COMPLETION_DATE = 6;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__CREATION_DATE = 7;

	/**
	 * The feature id for the '<em><b>Modification Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__MODIFICATION_DATE = 8;

	/**
	 * The feature id for the '<em><b>Collaborators</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__COLLABORATORS = 9;

	/**
	 * The feature id for the '<em><b>Related Url</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__RELATED_URL = 10;

	/**
	 * The feature id for the '<em><b>Ranking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__RANKING = 11;

	/**
	 * The feature id for the '<em><b>Category</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK__CATEGORY = 12;

	/**
	 * The number of structural features of the '<em>Simple Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK_FEATURE_COUNT = 13;

	/**
	 * The number of operations of the '<em>Simple Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TASK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.example.emftasks.impl.CategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.CategoryImpl
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 1;

	/**
	 * The feature id for the '<em><b>Summary</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__SUMMARY = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl <em>Task Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getTaskCollection()
	 * @generated
	 */
	int TASK_COLLECTION = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_COLLECTION__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_COLLECTION__TASKS = 1;

	/**
	 * The feature id for the '<em><b>Last Task Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_COLLECTION__LAST_TASK_ID = 2;

	/**
	 * The number of structural features of the '<em>Task Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_COLLECTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Task Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_COLLECTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.example.emftasks.Status <em>Status</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Status
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getStatus()
	 * @generated
	 */
	int STATUS = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.mylyn.reviews.example.emftasks.Priority <em>Priority</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Priority
	 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getPriority()
	 * @generated
	 */
	int PRIORITY = 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask <em>Simple Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Task</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask
	 * @generated
	 */
	EClass getSimpleTask();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getId()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getSummary <em>Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Summary</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getSummary()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Summary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDescription()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getStatus()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Status();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getPriority()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Priority();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDueDate <em>Due Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Due Date</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getDueDate()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_DueDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCompletionDate <em>Completion Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Completion Date</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCompletionDate()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_CompletionDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCreationDate <em>Creation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Date</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCreationDate()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_CreationDate();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getModificationDate <em>Modification Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modification Date</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getModificationDate()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_ModificationDate();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCollaborators <em>Collaborators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Collaborators</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCollaborators()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Collaborators();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRelatedUrl <em>Related Url</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Related Url</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRelatedUrl()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_RelatedUrl();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRanking <em>Ranking</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ranking</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getRanking()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EAttribute getSimpleTask_Ranking();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Category</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.SimpleTask#getCategory()
	 * @see #getSimpleTask()
	 * @generated
	 */
	EReference getSimpleTask_Category();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.example.emftasks.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Category
	 * @generated
	 */
	EClass getCategory();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.Category#getSummary <em>Summary</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Summary</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Category#getSummary()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Summary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.Category#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Category#getDescription()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.mylyn.reviews.example.emftasks.TaskCollection <em>Task Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Collection</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.TaskCollection
	 * @generated
	 */
	EClass getTaskCollection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.TaskCollection#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.TaskCollection#getLabel()
	 * @see #getTaskCollection()
	 * @generated
	 */
	EAttribute getTaskCollection_Label();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.mylyn.reviews.example.emftasks.TaskCollection#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.TaskCollection#getTasks()
	 * @see #getTaskCollection()
	 * @generated
	 */
	EReference getTaskCollection_Tasks();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.mylyn.reviews.example.emftasks.TaskCollection#getLastTaskId <em>Last Task Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Task Id</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.TaskCollection#getLastTaskId()
	 * @see #getTaskCollection()
	 * @generated
	 */
	EAttribute getTaskCollection_LastTaskId();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.example.emftasks.Status <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Status</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Status
	 * @generated
	 */
	EEnum getStatus();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.mylyn.reviews.example.emftasks.Priority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Priority</em>'.
	 * @see org.eclipse.mylyn.reviews.example.emftasks.Priority
	 * @generated
	 */
	EEnum getPriority();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EmfTasksFactory getEmfTasksFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl <em>Simple Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getSimpleTask()
		 * @generated
		 */
		EClass SIMPLE_TASK = eINSTANCE.getSimpleTask();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__ID = eINSTANCE.getSimpleTask_Id();

		/**
		 * The meta object literal for the '<em><b>Summary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__SUMMARY = eINSTANCE.getSimpleTask_Summary();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__DESCRIPTION = eINSTANCE.getSimpleTask_Description();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__STATUS = eINSTANCE.getSimpleTask_Status();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__PRIORITY = eINSTANCE.getSimpleTask_Priority();

		/**
		 * The meta object literal for the '<em><b>Due Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__DUE_DATE = eINSTANCE.getSimpleTask_DueDate();

		/**
		 * The meta object literal for the '<em><b>Completion Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__COMPLETION_DATE = eINSTANCE.getSimpleTask_CompletionDate();

		/**
		 * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__CREATION_DATE = eINSTANCE.getSimpleTask_CreationDate();

		/**
		 * The meta object literal for the '<em><b>Modification Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__MODIFICATION_DATE = eINSTANCE.getSimpleTask_ModificationDate();

		/**
		 * The meta object literal for the '<em><b>Collaborators</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__COLLABORATORS = eINSTANCE.getSimpleTask_Collaborators();

		/**
		 * The meta object literal for the '<em><b>Related Url</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__RELATED_URL = eINSTANCE.getSimpleTask_RelatedUrl();

		/**
		 * The meta object literal for the '<em><b>Ranking</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_TASK__RANKING = eINSTANCE.getSimpleTask_Ranking();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_TASK__CATEGORY = eINSTANCE.getSimpleTask_Category();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.example.emftasks.impl.CategoryImpl <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.CategoryImpl
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getCategory()
		 * @generated
		 */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
		 * The meta object literal for the '<em><b>Summary</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__SUMMARY = eINSTANCE.getCategory_Summary();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__DESCRIPTION = eINSTANCE.getCategory_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl <em>Task Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getTaskCollection()
		 * @generated
		 */
		EClass TASK_COLLECTION = eINSTANCE.getTaskCollection();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_COLLECTION__LABEL = eINSTANCE.getTaskCollection_Label();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_COLLECTION__TASKS = eINSTANCE.getTaskCollection_Tasks();

		/**
		 * The meta object literal for the '<em><b>Last Task Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_COLLECTION__LAST_TASK_ID = eINSTANCE.getTaskCollection_LastTaskId();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.example.emftasks.Status <em>Status</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.example.emftasks.Status
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getStatus()
		 * @generated
		 */
		EEnum STATUS = eINSTANCE.getStatus();

		/**
		 * The meta object literal for the '{@link org.eclipse.mylyn.reviews.example.emftasks.Priority <em>Priority</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.mylyn.reviews.example.emftasks.Priority
		 * @see org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksPackageImpl#getPriority()
		 * @generated
		 */
		EEnum PRIORITY = eINSTANCE.getPriority();

	}

} //EmfTasksPackage
