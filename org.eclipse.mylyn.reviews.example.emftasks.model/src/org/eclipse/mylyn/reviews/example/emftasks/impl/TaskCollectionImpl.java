/**
 */
package org.eclipse.mylyn.reviews.example.emftasks.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage;
import org.eclipse.mylyn.reviews.example.emftasks.SimpleTask;
import org.eclipse.mylyn.reviews.example.emftasks.TaskCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Collection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl#getTasks <em>Tasks</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.TaskCollectionImpl#getLastTaskId <em>Last Task Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskCollectionImpl extends MinimalEObjectImpl.Container implements TaskCollection {
	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTasks() <em>Tasks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTasks()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleTask> tasks;

	/**
	 * The default value of the '{@link #getLastTaskId() <em>Last Task Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastTaskId()
	 * @generated
	 * @ordered
	 */
	protected static final int LAST_TASK_ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLastTaskId() <em>Last Task Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastTaskId()
	 * @generated
	 * @ordered
	 */
	protected int lastTaskId = LAST_TASK_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TaskCollectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfTasksPackage.Literals.TASK_COLLECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.TASK_COLLECTION__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SimpleTask> getTasks() {
		if (tasks == null) {
			tasks = new EObjectContainmentEList<SimpleTask>(SimpleTask.class, this, EmfTasksPackage.TASK_COLLECTION__TASKS);
		}
		return tasks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLastTaskId() {
		return lastTaskId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastTaskId(int newLastTaskId) {
		int oldLastTaskId = lastTaskId;
		lastTaskId = newLastTaskId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.TASK_COLLECTION__LAST_TASK_ID, oldLastTaskId, lastTaskId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EmfTasksPackage.TASK_COLLECTION__TASKS:
				return ((InternalEList<?>)getTasks()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfTasksPackage.TASK_COLLECTION__LABEL:
				return getLabel();
			case EmfTasksPackage.TASK_COLLECTION__TASKS:
				return getTasks();
			case EmfTasksPackage.TASK_COLLECTION__LAST_TASK_ID:
				return getLastTaskId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EmfTasksPackage.TASK_COLLECTION__LABEL:
				setLabel((String)newValue);
				return;
			case EmfTasksPackage.TASK_COLLECTION__TASKS:
				getTasks().clear();
				getTasks().addAll((Collection<? extends SimpleTask>)newValue);
				return;
			case EmfTasksPackage.TASK_COLLECTION__LAST_TASK_ID:
				setLastTaskId((Integer)newValue);
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
			case EmfTasksPackage.TASK_COLLECTION__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case EmfTasksPackage.TASK_COLLECTION__TASKS:
				getTasks().clear();
				return;
			case EmfTasksPackage.TASK_COLLECTION__LAST_TASK_ID:
				setLastTaskId(LAST_TASK_ID_EDEFAULT);
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
			case EmfTasksPackage.TASK_COLLECTION__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case EmfTasksPackage.TASK_COLLECTION__TASKS:
				return tasks != null && !tasks.isEmpty();
			case EmfTasksPackage.TASK_COLLECTION__LAST_TASK_ID:
				return lastTaskId != LAST_TASK_ID_EDEFAULT;
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
		result.append(" (label: ");
		result.append(label);
		result.append(", lastTaskId: ");
		result.append(lastTaskId);
		result.append(')');
		return result.toString();
	}

} //TaskCollectionImpl
