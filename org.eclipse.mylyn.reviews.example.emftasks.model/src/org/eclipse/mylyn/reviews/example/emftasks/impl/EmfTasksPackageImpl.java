/**
 */
package org.eclipse.mylyn.reviews.example.emftasks.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.mylyn.reviews.example.emftasks.Category;
import org.eclipse.mylyn.reviews.example.emftasks.EmfTasksFactory;
import org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage;
import org.eclipse.mylyn.reviews.example.emftasks.Priority;
import org.eclipse.mylyn.reviews.example.emftasks.SimpleTask;
import org.eclipse.mylyn.reviews.example.emftasks.Status;
import org.eclipse.mylyn.reviews.example.emftasks.TaskCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EmfTasksPackageImpl extends EPackageImpl implements EmfTasksPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simpleTaskEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass categoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass taskCollectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum statusEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum priorityEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EmfTasksPackageImpl() {
		super(eNS_URI, EmfTasksFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EmfTasksPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EmfTasksPackage init() {
		if (isInited) return (EmfTasksPackage)EPackage.Registry.INSTANCE.getEPackage(EmfTasksPackage.eNS_URI);

		// Obtain or create and register package
		EmfTasksPackageImpl theEmfTasksPackage = (EmfTasksPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EmfTasksPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EmfTasksPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theEmfTasksPackage.createPackageContents();

		// Initialize created meta-data
		theEmfTasksPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEmfTasksPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EmfTasksPackage.eNS_URI, theEmfTasksPackage);
		return theEmfTasksPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimpleTask() {
		return simpleTaskEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Id() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Summary() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Description() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Status() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Priority() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_DueDate() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_CompletionDate() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_CreationDate() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_ModificationDate() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Collaborators() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_RelatedUrl() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimpleTask_Ranking() {
		return (EAttribute)simpleTaskEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimpleTask_Category() {
		return (EReference)simpleTaskEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCategory() {
		return categoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategory_Summary() {
		return (EAttribute)categoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategory_Description() {
		return (EAttribute)categoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTaskCollection() {
		return taskCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTaskCollection_Label() {
		return (EAttribute)taskCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTaskCollection_Tasks() {
		return (EReference)taskCollectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTaskCollection_LastTaskId() {
		return (EAttribute)taskCollectionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getStatus() {
		return statusEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPriority() {
		return priorityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EmfTasksFactory getEmfTasksFactory() {
		return (EmfTasksFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		simpleTaskEClass = createEClass(SIMPLE_TASK);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__ID);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__SUMMARY);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__DESCRIPTION);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__STATUS);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__PRIORITY);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__DUE_DATE);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__COMPLETION_DATE);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__CREATION_DATE);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__MODIFICATION_DATE);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__COLLABORATORS);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__RELATED_URL);
		createEAttribute(simpleTaskEClass, SIMPLE_TASK__RANKING);
		createEReference(simpleTaskEClass, SIMPLE_TASK__CATEGORY);

		categoryEClass = createEClass(CATEGORY);
		createEAttribute(categoryEClass, CATEGORY__SUMMARY);
		createEAttribute(categoryEClass, CATEGORY__DESCRIPTION);

		taskCollectionEClass = createEClass(TASK_COLLECTION);
		createEAttribute(taskCollectionEClass, TASK_COLLECTION__LABEL);
		createEReference(taskCollectionEClass, TASK_COLLECTION__TASKS);
		createEAttribute(taskCollectionEClass, TASK_COLLECTION__LAST_TASK_ID);

		// Create enums
		statusEEnum = createEEnum(STATUS);
		priorityEEnum = createEEnum(PRIORITY);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(simpleTaskEClass, SimpleTask.class, "SimpleTask", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimpleTask_Id(), ecorePackage.getEInt(), "id", null, 1, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_Summary(), ecorePackage.getEString(), "summary", null, 1, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_Description(), ecorePackage.getEString(), "description", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_Status(), this.getStatus(), "status", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_Priority(), this.getPriority(), "priority", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_DueDate(), ecorePackage.getEDate(), "dueDate", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_CompletionDate(), ecorePackage.getEDate(), "completionDate", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_CreationDate(), ecorePackage.getEDate(), "creationDate", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_ModificationDate(), ecorePackage.getEDate(), "modificationDate", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_Collaborators(), ecorePackage.getEString(), "collaborators", null, 0, -1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_RelatedUrl(), ecorePackage.getEString(), "relatedUrl", null, 0, -1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimpleTask_Ranking(), ecorePackage.getEDouble(), "ranking", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimpleTask_Category(), this.getCategory(), null, "category", null, 0, 1, SimpleTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCategory_Summary(), ecorePackage.getEString(), "summary", null, 1, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_Description(), ecorePackage.getEString(), "description", null, 0, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(taskCollectionEClass, TaskCollection.class, "TaskCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTaskCollection_Label(), ecorePackage.getEString(), "label", null, 1, 1, TaskCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTaskCollection_Tasks(), this.getSimpleTask(), null, "tasks", null, 0, -1, TaskCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTaskCollection_LastTaskId(), ecorePackage.getEInt(), "lastTaskId", "0", 1, 1, TaskCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(statusEEnum, Status.class, "Status");
		addEEnumLiteral(statusEEnum, Status.NEW);
		addEEnumLiteral(statusEEnum, Status.ACTIVE);
		addEEnumLiteral(statusEEnum, Status.COMPLETE);
		addEEnumLiteral(statusEEnum, Status.DEFERRED);
		addEEnumLiteral(statusEEnum, Status.INVALID);

		initEEnum(priorityEEnum, Priority.class, "Priority");
		addEEnumLiteral(priorityEEnum, Priority.MINOR);
		addEEnumLiteral(priorityEEnum, Priority.STANDARD);
		addEEnumLiteral(priorityEEnum, Priority.IMPORTANT);
		addEEnumLiteral(priorityEEnum, Priority.URGENT);

		// Create resource
		createResource(eNS_URI);
	}

} //EmfTasksPackageImpl
