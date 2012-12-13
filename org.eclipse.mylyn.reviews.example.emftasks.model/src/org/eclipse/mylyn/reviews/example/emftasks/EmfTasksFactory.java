/**
 */
package org.eclipse.mylyn.reviews.example.emftasks;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage
 * @generated
 */
public interface EmfTasksFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EmfTasksFactory eINSTANCE = org.eclipse.mylyn.reviews.example.emftasks.impl.EmfTasksFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Simple Task</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Task</em>'.
	 * @generated
	 */
	SimpleTask createSimpleTask();

	/**
	 * Returns a new object of class '<em>Category</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Category</em>'.
	 * @generated
	 */
	Category createCategory();

	/**
	 * Returns a new object of class '<em>Task Collection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Task Collection</em>'.
	 * @generated
	 */
	TaskCollection createTaskCollection();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	EmfTasksPackage getEmfTasksPackage();

} //EmfTasksFactory
