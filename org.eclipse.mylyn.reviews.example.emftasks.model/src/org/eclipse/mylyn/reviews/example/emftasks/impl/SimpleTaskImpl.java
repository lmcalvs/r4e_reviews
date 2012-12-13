/**
 */
package org.eclipse.mylyn.reviews.example.emftasks.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.mylyn.reviews.example.emftasks.Category;
import org.eclipse.mylyn.reviews.example.emftasks.EmfTasksPackage;
import org.eclipse.mylyn.reviews.example.emftasks.Priority;
import org.eclipse.mylyn.reviews.example.emftasks.SimpleTask;
import org.eclipse.mylyn.reviews.example.emftasks.Status;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getSummary <em>Summary</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getDueDate <em>Due Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getCompletionDate <em>Completion Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getCreationDate <em>Creation Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getModificationDate <em>Modification Date</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getCollaborators <em>Collaborators</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getRelatedUrl <em>Related Url</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getRanking <em>Ranking</em>}</li>
 *   <li>{@link org.eclipse.mylyn.reviews.example.emftasks.impl.SimpleTaskImpl#getCategory <em>Category</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimpleTaskImpl extends MinimalEObjectImpl.Container implements SimpleTask {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getSummary() <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSummary()
	 * @generated
	 * @ordered
	 */
	protected static final String SUMMARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSummary() <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSummary()
	 * @generated
	 * @ordered
	 */
	protected String summary = SUMMARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final Status STATUS_EDEFAULT = Status.NEW;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected Status status = STATUS_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final Priority PRIORITY_EDEFAULT = Priority.MINOR;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected Priority priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDueDate() <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDueDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DUE_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDueDate() <em>Due Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDueDate()
	 * @generated
	 * @ordered
	 */
	protected Date dueDate = DUE_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCompletionDate() <em>Completion Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompletionDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date COMPLETION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCompletionDate() <em>Completion Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompletionDate()
	 * @generated
	 * @ordered
	 */
	protected Date completionDate = COMPLETION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected Date creationDate = CREATION_DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getModificationDate() <em>Modification Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModificationDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date MODIFICATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModificationDate() <em>Modification Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModificationDate()
	 * @generated
	 * @ordered
	 */
	protected Date modificationDate = MODIFICATION_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCollaborators() <em>Collaborators</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCollaborators()
	 * @generated
	 * @ordered
	 */
	protected EList<String> collaborators;

	/**
	 * The cached value of the '{@link #getRelatedUrl() <em>Related Url</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelatedUrl()
	 * @generated
	 * @ordered
	 */
	protected EList<String> relatedUrl;

	/**
	 * The default value of the '{@link #getRanking() <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanking()
	 * @generated
	 * @ordered
	 */
	protected static final double RANKING_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRanking() <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanking()
	 * @generated
	 * @ordered
	 */
	protected double ranking = RANKING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected Category category;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleTaskImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EmfTasksPackage.Literals.SIMPLE_TASK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSummary(String newSummary) {
		String oldSummary = summary;
		summary = newSummary;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__SUMMARY, oldSummary, summary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(Status newStatus) {
		Status oldStatus = status;
		status = newStatus == null ? STATUS_EDEFAULT : newStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__STATUS, oldStatus, status));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(Priority newPriority) {
		Priority oldPriority = priority;
		priority = newPriority == null ? PRIORITY_EDEFAULT : newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDueDate(Date newDueDate) {
		Date oldDueDate = dueDate;
		dueDate = newDueDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__DUE_DATE, oldDueDate, dueDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCompletionDate() {
		return completionDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompletionDate(Date newCompletionDate) {
		Date oldCompletionDate = completionDate;
		completionDate = newCompletionDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__COMPLETION_DATE, oldCompletionDate, completionDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationDate(Date newCreationDate) {
		Date oldCreationDate = creationDate;
		creationDate = newCreationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__CREATION_DATE, oldCreationDate, creationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModificationDate(Date newModificationDate) {
		Date oldModificationDate = modificationDate;
		modificationDate = newModificationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__MODIFICATION_DATE, oldModificationDate, modificationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getCollaborators() {
		if (collaborators == null) {
			collaborators = new EDataTypeUniqueEList<String>(String.class, this, EmfTasksPackage.SIMPLE_TASK__COLLABORATORS);
		}
		return collaborators;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getRelatedUrl() {
		if (relatedUrl == null) {
			relatedUrl = new EDataTypeUniqueEList<String>(String.class, this, EmfTasksPackage.SIMPLE_TASK__RELATED_URL);
		}
		return relatedUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getRanking() {
		return ranking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRanking(double newRanking) {
		double oldRanking = ranking;
		ranking = newRanking;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__RANKING, oldRanking, ranking));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category getCategory() {
		if (category != null && category.eIsProxy()) {
			InternalEObject oldCategory = (InternalEObject)category;
			category = (Category)eResolveProxy(oldCategory);
			if (category != oldCategory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EmfTasksPackage.SIMPLE_TASK__CATEGORY, oldCategory, category));
			}
		}
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category basicGetCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategory(Category newCategory) {
		Category oldCategory = category;
		category = newCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EmfTasksPackage.SIMPLE_TASK__CATEGORY, oldCategory, category));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EmfTasksPackage.SIMPLE_TASK__ID:
				return getId();
			case EmfTasksPackage.SIMPLE_TASK__SUMMARY:
				return getSummary();
			case EmfTasksPackage.SIMPLE_TASK__DESCRIPTION:
				return getDescription();
			case EmfTasksPackage.SIMPLE_TASK__STATUS:
				return getStatus();
			case EmfTasksPackage.SIMPLE_TASK__PRIORITY:
				return getPriority();
			case EmfTasksPackage.SIMPLE_TASK__DUE_DATE:
				return getDueDate();
			case EmfTasksPackage.SIMPLE_TASK__COMPLETION_DATE:
				return getCompletionDate();
			case EmfTasksPackage.SIMPLE_TASK__CREATION_DATE:
				return getCreationDate();
			case EmfTasksPackage.SIMPLE_TASK__MODIFICATION_DATE:
				return getModificationDate();
			case EmfTasksPackage.SIMPLE_TASK__COLLABORATORS:
				return getCollaborators();
			case EmfTasksPackage.SIMPLE_TASK__RELATED_URL:
				return getRelatedUrl();
			case EmfTasksPackage.SIMPLE_TASK__RANKING:
				return getRanking();
			case EmfTasksPackage.SIMPLE_TASK__CATEGORY:
				if (resolve) return getCategory();
				return basicGetCategory();
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
			case EmfTasksPackage.SIMPLE_TASK__ID:
				setId((Integer)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__SUMMARY:
				setSummary((String)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__STATUS:
				setStatus((Status)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__PRIORITY:
				setPriority((Priority)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__DUE_DATE:
				setDueDate((Date)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__COMPLETION_DATE:
				setCompletionDate((Date)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__CREATION_DATE:
				setCreationDate((Date)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__MODIFICATION_DATE:
				setModificationDate((Date)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__COLLABORATORS:
				getCollaborators().clear();
				getCollaborators().addAll((Collection<? extends String>)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__RELATED_URL:
				getRelatedUrl().clear();
				getRelatedUrl().addAll((Collection<? extends String>)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__RANKING:
				setRanking((Double)newValue);
				return;
			case EmfTasksPackage.SIMPLE_TASK__CATEGORY:
				setCategory((Category)newValue);
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
			case EmfTasksPackage.SIMPLE_TASK__ID:
				setId(ID_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__SUMMARY:
				setSummary(SUMMARY_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__DUE_DATE:
				setDueDate(DUE_DATE_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__COMPLETION_DATE:
				setCompletionDate(COMPLETION_DATE_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__MODIFICATION_DATE:
				setModificationDate(MODIFICATION_DATE_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__COLLABORATORS:
				getCollaborators().clear();
				return;
			case EmfTasksPackage.SIMPLE_TASK__RELATED_URL:
				getRelatedUrl().clear();
				return;
			case EmfTasksPackage.SIMPLE_TASK__RANKING:
				setRanking(RANKING_EDEFAULT);
				return;
			case EmfTasksPackage.SIMPLE_TASK__CATEGORY:
				setCategory((Category)null);
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
			case EmfTasksPackage.SIMPLE_TASK__ID:
				return id != ID_EDEFAULT;
			case EmfTasksPackage.SIMPLE_TASK__SUMMARY:
				return SUMMARY_EDEFAULT == null ? summary != null : !SUMMARY_EDEFAULT.equals(summary);
			case EmfTasksPackage.SIMPLE_TASK__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case EmfTasksPackage.SIMPLE_TASK__STATUS:
				return status != STATUS_EDEFAULT;
			case EmfTasksPackage.SIMPLE_TASK__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case EmfTasksPackage.SIMPLE_TASK__DUE_DATE:
				return DUE_DATE_EDEFAULT == null ? dueDate != null : !DUE_DATE_EDEFAULT.equals(dueDate);
			case EmfTasksPackage.SIMPLE_TASK__COMPLETION_DATE:
				return COMPLETION_DATE_EDEFAULT == null ? completionDate != null : !COMPLETION_DATE_EDEFAULT.equals(completionDate);
			case EmfTasksPackage.SIMPLE_TASK__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
			case EmfTasksPackage.SIMPLE_TASK__MODIFICATION_DATE:
				return MODIFICATION_DATE_EDEFAULT == null ? modificationDate != null : !MODIFICATION_DATE_EDEFAULT.equals(modificationDate);
			case EmfTasksPackage.SIMPLE_TASK__COLLABORATORS:
				return collaborators != null && !collaborators.isEmpty();
			case EmfTasksPackage.SIMPLE_TASK__RELATED_URL:
				return relatedUrl != null && !relatedUrl.isEmpty();
			case EmfTasksPackage.SIMPLE_TASK__RANKING:
				return ranking != RANKING_EDEFAULT;
			case EmfTasksPackage.SIMPLE_TASK__CATEGORY:
				return category != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", summary: ");
		result.append(summary);
		result.append(", description: ");
		result.append(description);
		result.append(", status: ");
		result.append(status);
		result.append(", priority: ");
		result.append(priority);
		result.append(", dueDate: ");
		result.append(dueDate);
		result.append(", completionDate: ");
		result.append(completionDate);
		result.append(", creationDate: ");
		result.append(creationDate);
		result.append(", modificationDate: ");
		result.append(modificationDate);
		result.append(", collaborators: ");
		result.append(collaborators);
		result.append(", relatedUrl: ");
		result.append(relatedUrl);
		result.append(", ranking: ");
		result.append(ranking);
		result.append(')');
		return result.toString();
	}

} //SimpleTaskImpl
