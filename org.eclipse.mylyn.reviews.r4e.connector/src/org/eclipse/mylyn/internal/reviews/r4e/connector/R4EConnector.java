/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *   Others (includes code modified from GerritConnector in org.eclipse.mylyn.gerit, see author annotations.)
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.r4e.connector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.EmfConfiguration;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema.FieldFeature;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.reviews.frame.core.model.ModelPackage;
import org.eclipse.mylyn.reviews.frame.core.model.User;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.DefaultTaskSchema;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.osgi.framework.Bundle;

/**
 * Core R4E connector support.
 * 
 * @author Miles Parker
 */
public class R4EConnector extends AbstractEmfConnector {

	public static final String R4E_PARTICIPANTS_KEY = "task.r4e.user.participants";

	public static final String R4E_REVIEW_ITEM_COUNT_KEY = "task.r4e.review.item.count";

	/**
	 * Connector kind
	 */
	public static final String CONNECTOR_KIND = "org.eclipse.mylyn.r4e"; //$NON-NLS-1$

	/**
	 * Label for the connector.
	 */
	public static final String CONNECTOR_LABEL = "R4E Code Review"; //$NON-NLS-1$

	private final EClass[] R4E_CLASSES = new EClass[] { RModelPackage.Literals.R4E_REVIEW };

	private final DefaultTaskSchema parentSchema = DefaultTaskSchema.getInstance();

	private final FieldFeature[] R4E_PAIRS = new FieldFeature[] {
			new FieldFeature(parentSchema.DESCRIPTION, RModelPackage.Literals.R4E_REVIEW__EXTRA_NOTES),
			new FieldFeature(parentSchema.SUMMARY, RModelPackage.Literals.R4E_REVIEW__NAME, false),
			new FieldFeature(parentSchema.TASK_KIND, RModelPackage.Literals.R4E_REVIEW__TYPE, true),
			new FieldFeature(parentSchema.DATE_CREATION, RModelPackage.Literals.R4E_REVIEW__START_DATE, true),
			new FieldFeature(parentSchema.DATE_MODIFICATION, RModelPackage.Literals.R4E_REVIEW__MODIFIED_DATE, true),
			new FieldFeature(parentSchema.DATE_COMPLETION, RModelPackage.Literals.R4E_REVIEW__END_DATE, false),
			new FieldFeature(parentSchema.USER_REPORTER, RModelPackage.Literals.R4E_REVIEW__CREATED_BY) };

	private final EAttribute[] R4E_SEARCH_FIELDS = new EAttribute[] { RModelPackage.Literals.R4E_REVIEW__NAME,
			RModelPackage.Literals.R4E_REVIEW__PROJECT, RModelPackage.Literals.R4E_REVIEW__OBJECTIVES,
			RModelPackage.Literals.R4E_REVIEW__DUE_DATE, RModelPackage.Literals.R4E_REVIEW__START_DATE,
			RModelPackage.Literals.R4E_REVIEW__END_DATE, RModelPackage.Literals.R4E_REVIEW__MODIFIED_DATE,
			RModelPackage.Literals.R4E_REVIEW__TYPE };

	public R4EConnector() {
		if (R4EConnectorPlugin.getDefault() != null) {
			R4EConnectorPlugin.getDefault().setConnector(this);
		}
	}

	@Override
	protected KeyStrategy getKeyStrategy() {
		return KeyStrategy.MEMBER_INDEX;
	}

	@Override
	public EmfTaskSchema createTaskSchema() {
		EmfTaskSchemaDelegator taskSchema = new EmfTaskSchemaDelegator() {
			@Override
			public TaskAttributeMapper getAttributeMapper(TaskRepository repository) {
				return new R4EAttributeMapper(this, repository);
			}

			@Override
			public boolean isSupported(EStructuralFeature feature) {
				return (feature != ModelPackage.Literals.SUB_MODEL_ROOT__FRAGMENT_VERSION)
						&& (feature != ModelPackage.Literals.SUB_MODEL_ROOT__APPLICATION_VERSION)
						&& (feature != ModelPackage.Literals.SUB_MODEL_ROOT__COMPATIBILITY)
						&& (feature != ModelPackage.Literals.REVIEW__ID)
						&& (feature != RModelPackage.Literals.R4E_REVIEW__START_DATE)
						&& (feature != RModelPackage.Literals.R4E_REVIEW__MODIFIED_DATE)
						&& (feature != RModelPackage.Literals.R4E_REVIEW_COMPONENT__ASSIGNED_TO)
						&& (feature != RModelPackage.Literals.R4E_REVIEW__COMPONENTS) //TODO:  Removed for now as the editor has issues displaying EList values
						&& (feature != ModelPackage.Literals.REVIEW_COMPONENT__ENABLED) && super.isSupported(feature);
			}

			@Override
			public void initialize() {
				super.initialize();
				createField(RModelPackage.Literals.R4E_REVIEW__PROJECT, Flag.READ_ONLY);
				//createField(RModelPackage.Literals.R4E_REVIEW__COMPONENTS, Flag.READ_ONLY);  //TODO:  Removed for now as the editor has issues displaying EList values
			}

			@Override
			public void initialize(TaskData taskData) {
				super.initialize(taskData);
				inheritFrom(parentSchema.USER_ASSIGNED).addFlags(Flag.READ_ONLY)
						.create()
						.createAttribute(taskData.getRoot());
				Field partipantsField = createField(R4E_PARTICIPANTS_KEY, "Participants", TaskAttribute.TYPE_PERSON,
						Flag.READ_ONLY);
				partipantsField.createAttribute(taskData.getRoot());
			}
		};
		return taskSchema;
	}

	@Override
	public TaskData getTaskData(TaskRepository repository, String taskId, IProgressMonitor monitor)
			throws CoreException {
		TaskData taskData = super.getTaskData(repository, taskId, monitor);

		R4EReview review = (R4EReview) getTaskObjectChecked(repository, taskId, monitor);
		RModelFactoryExt r4eFactory = SerializeFactory.getModelExtension();
		try {
			r4eFactory.openR4EReview((R4EReviewGroup) review.eContainer(), review.getName());
		} catch (ResourceHandlingException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID,
					"Couldn't open review for task: " + taskId, e));
		} catch (CompatibilityException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID,
					"Couldn't open review for task: " + taskId, e));
		}

		User owner = review.getCreatedBy();
		if (owner != null && owner.getEmail() != null) {
			TaskAttribute attribute = taskData.getRoot().getAttribute(parentSchema.USER_ASSIGNED.getKey());
			attribute.setValue(owner.getEmail());
		}

		Collection<R4EUser> participants = review.getUsersMap().values();
		List<String> emails = new ArrayList<String>();
		for (R4EUser participant : participants) {
			emails.add(participant.getEmail());
		}
		String emailList = StringUtils.join(emails, ",");
		taskData.getRoot().getAttribute(R4E_PARTICIPANTS_KEY).setValue(emailList);

		TaskAttribute attribute = taskData.getRoot().getAttribute(R4E_REVIEW_ITEM_COUNT_KEY);
		if (attribute == null) {
			attribute = taskData.getRoot().createAttribute(R4E_REVIEW_ITEM_COUNT_KEY);
		} else {
			String newSize = Integer.toString(review.getReviewItems().size());
			if (attribute.getValue().equals(newSize)) {
				attribute.setValue(newSize);
			}
		}
		return taskData;
	}

	@Override
	public boolean hasRepositoryDueDate(TaskRepository taskRepository, ITask task, TaskData taskData) {
		return true;
	}

	@Override
	protected EmfClient createClient(final TaskRepository repository, EmfConfiguration configuration) {
		return new R4EClient(repository);
	}

	@Override
	public String getConnectorKind() {
		return CONNECTOR_KIND;
	}

	@Override
	public String getLabel() {
		return CONNECTOR_LABEL;
	}

	@Override
	public EReference getContainmentReference() {
		return ModelPackage.Literals.REVIEW_GROUP__REVIEWS;
	}

	@Override
	public EAttribute getContentsNameAttribute() {
		return RModelPackage.Literals.R4E_REVIEW_GROUP__NAME;
	}

	@Override
	public EClass getContainerClass() {
		return RModelPackage.Literals.R4E_REVIEW_GROUP;
	}

	@Override
	public Bundle getConnectorBundle() {
		return R4EConnectorPlugin.getDefault().getBundle();
	}

	@Override
	public EClass[] getTaskClasses() {
		return R4E_CLASSES;
	}

	@Override
	public FieldFeature[] getTaskFeatures() {
		return R4E_PAIRS;
	}

	@Override
	public EAttribute[] getSearchAttributes() {
		return R4E_SEARCH_FIELDS;
	}

	@Override
	public String getNextTaskId(EObject taskContainer) {
		return null;
	}
}
