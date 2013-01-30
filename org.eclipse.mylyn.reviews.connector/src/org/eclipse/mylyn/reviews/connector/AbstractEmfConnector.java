/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
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
package org.eclipse.mylyn.reviews.connector;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.mylyn.internal.reviews.connector.EmfCorePlugin;
import org.eclipse.mylyn.internal.tasks.core.RepositoryTaskHandleUtil;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema.FieldFeature;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.reviews.connector.query.EmfQueryEngine;
import org.eclipse.mylyn.reviews.connector.query.QueryClause;
import org.eclipse.mylyn.reviews.connector.query.QueryException;
import org.eclipse.mylyn.reviews.connector.query.SimpleQueryEngine;
import org.eclipse.mylyn.tasks.core.AbstractRepositoryConnector;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskMapping;
import org.eclipse.mylyn.tasks.core.RepositoryClientManager;
import org.eclipse.mylyn.tasks.core.RepositoryResponse;
import org.eclipse.mylyn.tasks.core.RepositoryResponse.ResponseKind;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskDataHandler;
import org.eclipse.mylyn.tasks.core.data.AbstractTaskSchema.Field;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.core.data.TaskDataCollector;
import org.eclipse.mylyn.tasks.core.data.TaskMapper;
import org.eclipse.mylyn.tasks.core.sync.ISynchronizationSession;
import org.osgi.framework.Bundle;

/**
 * Generic support for connectors to Emf models. Connector implementors should override, providing all meta-data.
 * 
 * @author Miles Parker
 */
@SuppressWarnings("restriction")
public abstract class AbstractEmfConnector extends AbstractRepositoryConnector {

	/**
	 * Unicode en-dash.
	 */
	public static final String EN_DASH = "\u2013"; //$NON-NLS-1$

	public static final String ITEM_DELIM = "#"; //$NON-NLS-1$

	public enum KeyStrategy {
		EMF_SCHEMA_FIELD, OBJECT_ID, MEMBER_INDEX, XMI_ID
	};

	class EmfClientManager extends RepositoryClientManager<EmfClient, EmfConfiguration> {
		public EmfClientManager() {
			super(getCacheFile(), getConfigurationClass());
		}

		@Override
		protected EmfClient createClient(TaskRepository repository, EmfConfiguration configuration) {
			return AbstractEmfConnector.this.createClient(repository, configuration);
		}
	}

	/**
	 * By delegating back to the EmfConnector for this case, we can greatly simplify API for consumers.
	 */
	class EmfTaskDataHandler extends AbstractTaskDataHandler {

		@Override
		public TaskAttributeMapper getAttributeMapper(TaskRepository repository) {
			return AbstractEmfConnector.this.getAttributeMapper(repository);
		}

		@Override
		public boolean initializeTaskData(TaskRepository repository, TaskData taskData,
				ITaskMapping initializationData, IProgressMonitor monitor) {
			return AbstractEmfConnector.this.initializeTaskData(repository, taskData, initializationData, monitor);
		}

		@Override
		public RepositoryResponse postTaskData(TaskRepository repository, TaskData taskData,
				Set<TaskAttribute> oldAttributes, IProgressMonitor monitor) throws CoreException {
			return AbstractEmfConnector.this.postTaskData(repository, taskData, oldAttributes, monitor);
		}
	}

	/**
	 * By delegating back to the EmfConnector for this case, we can greatly simplify API for consumers.
	 */
	public class EmfTaskSchemaDelegator extends EmfTaskSchema {

		@Override
		protected EClass[] getSchemaEClasses() {
			return AbstractEmfConnector.this.getTaskClasses();
		}

		@Override
		protected FieldFeature[] getSchemaPairs() {
			return AbstractEmfConnector.this.getTaskFeatures();
		}

	}

	public static final String EMF_ITEM_DELIM = "#"; //$NON-NLS-1$

	private final EmfTaskDataHandler taskDataHandler = new EmfTaskDataHandler();

	private RepositoryClientManager<EmfClient, EmfConfiguration> clientManager;

	private EmfTaskSchema taskSchema;

	private final File configurationCacheFile;

	private final Set<QueryClause> queryClauses = new HashSet<QueryClause>();

	public AbstractEmfConnector() {
		IPath stateLocation = Platform.getStateLocation(EmfCorePlugin.getDefault().getBundle());
		IPath cache = stateLocation.append("repositoryConfigurations"); //$NON-NLS-1$
		configurationCacheFile = cache.toFile();
	}

	public Class<EmfConfiguration> getConfigurationClass() {
		return EmfConfiguration.class;
	}

	/**
	 * Not supported yet.
	 */
	@Override
	public boolean canCreateNewTask(TaskRepository arg0) {
		return true;
	}

	@Override
	public boolean canCreateTaskFromKey(TaskRepository arg0) {
		return true;
	}

	public EmfClient getClient(TaskRepository repository) {
		return getClientManager().getClient(repository);
	}

	public TaskAttributeMapper getAttributeMapper(TaskRepository repository) {
		return getSchema().getAttributeMapper(repository);
	}

	public EmfAttributeMapper getEmfMapper(TaskRepository repository) {
		return (EmfAttributeMapper) getAttributeMapper(repository);
	}

	public EmfQueryEngine getQueryEngine(TaskRepository repository) {
		return new SimpleQueryEngine(this, repository);
	}

	public boolean initializeTaskData(TaskRepository repository, TaskData data, ITaskMapping initializationData,
			IProgressMonitor monitor) {
		getSchema().initialize(data);
		String taskId = data.getTaskId();
		data.getRoot()
				.createAttribute(TaskAttribute.TASK_URL)
				.setValue(getTaskUrl(repository.getRepositoryUrl(), taskId));
		data.getRoot().createAttribute(TaskAttribute.TASK_KEY).setValue(encodeTaskKey(taskId));
		return true;
	}

	public RepositoryResponse postTaskData(TaskRepository repository, TaskData taskData,
			Set<TaskAttribute> oldAttributes, IProgressMonitor monitor) throws CoreException {
		EmfClient client = getClient(repository);
		client.open();
		String taskId = taskData.getTaskId();
		EObject emfTask;
		if (taskId.equals("")) { //$NON-NLS-1$
			EClass containedClass = getTaskClasses()[0];
			@SuppressWarnings("unchecked")
			EList<? super EObject> taskContainment = (EList<? super EObject>) client.getRootContainer().eGet(
					getContainmentReference());
			emfTask = client.create(containedClass, taskData);
			taskContainment.add(emfTask);
			//Emf implementation may create a key at model save time (e.g. XMLResource uuid implementation)
			client.save();
			taskId = getTaskKey(repository, emfTask);
			if (StringUtils.isEmpty(taskId) || taskId.equals("0")) { //$NON-NLS-1$
				taskId = getNextTaskId(client.getRootContainer());
				if (taskId != null) {
					TaskAttribute keyAttribute = taskData.getRoot().getAttribute(TaskAttribute.TASK_KEY);
					keyAttribute.setValue(taskId);
					oldAttributes.add(keyAttribute);
				}
			}
			for (Field field : getSchema().getFields()) {
				oldAttributes.add(taskData.getRoot().getAttribute(field.getKey()));
			}
		} else {
			emfTask = getTaskObjectChecked(repository, taskId, monitor);
		}
		boolean emfDirty = false;
		for (TaskAttribute staleAttribute : oldAttributes) {
			String id = staleAttribute.getId();
			TaskAttribute newAttribute = taskData.getRoot().getAttribute(id);
			boolean isSet = newAttribute.getValues().size() > 0;
			if (isSet) {
				boolean setValueForKey = getEmfMapper(repository).copyTaskToEmf(newAttribute, emfTask);
				emfDirty |= setValueForKey;
			}
		}
		if (emfDirty) {
			Date time = Calendar.getInstance().getTime();
			EStructuralFeature dateModificationFeature = getSchema().getFeature(TaskAttribute.DATE_MODIFICATION);
			emfTask.eSet(dateModificationFeature, time);
			client.notifyChanged(emfTask);
		}
		return new RepositoryResponse(ResponseKind.TASK_UPDATED, taskId);
	}

	public TaskData createTaskData(TaskRepository repository, String taskId, IProgressMonitor monitor) {
		TaskData data = new TaskData(getAttributeMapper(repository), getConnectorKind(), repository.getRepositoryUrl(),
				taskId);
		initializeTaskData(repository, data, null, monitor);
		return data;
	}

	public TaskData createPartialTaskData(TaskRepository repository, String taskId, IProgressMonitor monitor) {
		TaskData data = new TaskData(getAttributeMapper(repository), getConnectorKind(), repository.getRepositoryUrl(),
				taskId);
		//Only create summary and other mapped attributes now
		getSchema().initialize(data);
		data.setPartial(true);
		return data;
	}

	/**
	 * Retrieves task data for the given review from repository.
	 */
	@Override
	public TaskData getTaskData(TaskRepository repository, String taskId, IProgressMonitor monitor)
			throws CoreException {
		EmfClient client = getClient(repository);
		client.updateConfiguration(monitor);
		EObject emfObject = getTaskObjectChecked(repository, taskId, monitor);
		String id = getTaskKey(repository, emfObject);
		TaskData data = createTaskData(repository, id, monitor);
		EmfTaskSchema schema = getSchema();
		for (Field field : schema.getFields()) {
			EStructuralFeature feature = schema.getFeature(field.getKey());
			//TODO support object references
			if (feature instanceof EAttribute && !((EAttribute) feature).isMany()) {
				EAttribute emfAttribute = (EAttribute) feature;
				Object emfValue = emfObject.eGet(feature);
				TaskAttribute taskAttribute = field.createAttribute(data.getRoot());
				if (emfAttribute.getEAttributeType() instanceof EEnum) {
					EEnum enumerator = (EEnum) emfAttribute.getEAttributeType();
					for (EEnumLiteral literal : enumerator.getELiterals()) {
						taskAttribute.putOption(literal.getLiteral(), literal.getName());
					}
				}
				getEmfMapper(repository).copyEmfToTask(emfObject, taskAttribute);
				if (emfValue != null) {
					EFactory factory = emfAttribute.getEAttributeType().getEPackage().getEFactoryInstance();
					String stringValue = factory.convertToString(emfAttribute.getEAttributeType(), emfValue);
					taskAttribute.setValue(stringValue);
				} else if (!emfObject.eIsSet(feature)) {
					taskAttribute.clearValues();
				} else {
					taskAttribute.setValue(""); //$NON-NLS-1$
				}
			}
		}
		return data;
	}

	protected KeyStrategy getKeyStrategy() {
		return KeyStrategy.EMF_SCHEMA_FIELD;
	}

	protected String getTaskKey(TaskRepository repository, EObject emfObject) {
		String key = null;
		switch (getKeyStrategy()) {
		case EMF_SCHEMA_FIELD:
			key = getEmfMapper(repository).getEmfString(emfObject, TaskAttribute.TASK_KEY);
			break;
		case OBJECT_ID:
			key = EcoreUtil.getID(emfObject);
			break;
		case XMI_ID:
			Resource resource = emfObject.eResource();
			if (resource instanceof XMLResource) {
				XMLResource xmlResource = (XMLResource) resource;
				key = xmlResource.getID(emfObject);
			}
			break;
		case MEMBER_INDEX:
			EObject rootContainer = EcoreUtil.getRootContainer(emfObject);
			String uriFragment = rootContainer.eResource().getURIFragment(emfObject);
			key = uriFragment;
			int dotIndex = key.indexOf('.');
			if (dotIndex >= 0) {
				key = key.substring(dotIndex + 1);
			}
			break;
		}
		return encodeTaskKey(key);
	}

	public EObject getTaskObject(TaskRepository repository, String taskId, IProgressMonitor monitor)
			throws CoreException {
		taskId = encodeTaskKey(taskId);
		EmfClient client = getClient(repository);
		client.open();
		EObject container = client.getRootContainer();
		EObject rootContainer = EcoreUtil.getRootContainer(container);
		EObject referencedObject = rootContainer.eResource().getEObject(taskId);
		taskId = encodeTaskKey(taskId);
		if (referencedObject == null) {
			for (Object object : ((List<?>) container.eGet(getContainmentReference()))) {
				if (object instanceof EObject) {
					EObject eObject = (EObject) object;
					String stringValueForKey = getTaskKey(repository, eObject);
					if (ObjectUtils.equals(stringValueForKey, taskId)) {
						referencedObject = eObject;
						break;
					}
				}
			}
		}
		return referencedObject;
	}

	public EObject getTaskObjectChecked(TaskRepository repository, String taskId, IProgressMonitor monitor)
			throws CoreException {
		EObject emfObject = getTaskObject(repository, taskId, monitor);
		if (emfObject == null) {
			throw new CoreException(new Status(IStatus.WARNING, EmfCorePlugin.PLUGIN_ID,
					"Couldn't locate task object for taskId: " + taskId //$NON-NLS-1$
			));
		}
		return emfObject;
	}

	@Override
	public AbstractTaskDataHandler getTaskDataHandler() {
		return taskDataHandler;
	}

	/**
	 * Encode a task id to prevent use of handle delimiters.
	 * 
	 * @param taskId
	 * @return
	 */
	public String encodeTaskKey(String taskId) {
		return StringUtils.replace(taskId, RepositoryTaskHandleUtil.HANDLE_DELIM, EN_DASH);
	}

	/**
	 * Decode a task id to restore any handle delimiters.
	 * 
	 * @param taskId
	 * @return
	 */
	public String decodeTaskKey(String taskId) {
		return StringUtils.replace(taskId, EN_DASH, RepositoryTaskHandleUtil.HANDLE_DELIM);
	}

	@Override
	public String getRepositoryUrlFromTaskUrl(String url) {
		if (url == null) {
			return null;
		}

		int i = url.indexOf(ITEM_DELIM);
		if (i != -1) {
			return url.substring(0, i);
		}
		return null;
	}

	@Override
	public String getTaskIdFromTaskUrl(String url) {
		if (url == null) {
			return null;
		}
		int index = url.indexOf(ITEM_DELIM);
		if (index > 0) {
			String taskId = url.substring(index + ITEM_DELIM.length());
			try {
				taskId = URLDecoder.decode(taskId, "utf-8"); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			return taskId;
		}
		return null;
	}

	@Override
	public String getTaskUrl(String repositoryUrl, String taskId) {
		String encodedId = taskId;
		try {
			encodedId = URLEncoder.encode(taskId, "utf-8"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return repositoryUrl + ITEM_DELIM + encodedId;
	}

	@Override
	public ITaskMapping getTaskMapping(TaskData taskData) {
		return new TaskMapper(taskData);
	}

	@Override
	public boolean hasTaskChanged(TaskRepository repository, ITask task, TaskData taskData) {
		ITaskMapping taskMapping = getTaskMapping(taskData);
		if (taskData.isPartial() && task.getModificationDate() != null) {
			return false;
		}
		Date repositoryDate = taskMapping.getModificationDate();
		Date localDate = task.getModificationDate();
		boolean hasChanged = repositoryDate != null && repositoryDate.equals(localDate);
		return hasChanged;
	}

	@Override
	public IStatus performQuery(TaskRepository repository, IRepositoryQuery query, TaskDataCollector resultCollector,
			ISynchronizationSession session, IProgressMonitor monitor) {
		try {
			EmfClient client = getClient(repository);
			client.open();
			List<EObject> results;
			try {
				results = getQueryEngine(repository).performQuery(query, monitor);
				for (EObject taskObject : results) {
					String id = encodeTaskKey(getTaskKey(repository, taskObject));
					if (!StringUtils.isEmpty(id)) {
						TaskData taskData = createPartialTaskData(repository, id, monitor);
						resultCollector.accept(taskData);
					}
				}
				return Status.OK_STATUS;
			} catch (QueryException e) {
				return new Status(IStatus.ERROR, EmfCorePlugin.PLUGIN_ID, "Problem occurred while executing query.", e); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			return e.getStatus();
		} finally {
			monitor.done();
		}
	}

	@Override
	public void updateRepositoryConfiguration(TaskRepository repository, IProgressMonitor monitor) throws CoreException {
		getClient(repository).updateConfiguration(monitor);
	}

	@Override
	public void updateTaskFromTaskData(TaskRepository taskRepository, ITask task, TaskData taskData) {
		Date oldModificationDate = task.getModificationDate();

		TaskMapper mapper = (TaskMapper) getTaskMapping(taskData);
		// retain modification date to force an update when full task data is received
		if (taskData.isPartial()) {
			task.setModificationDate(oldModificationDate);
		} else {
			mapper.applyTo(task);
		}
	}

	public IStatus validate(TaskRepository repository, IProgressMonitor monitor) throws CoreException {
		return Status.OK_STATUS;
	}

	public RepositoryClientManager<EmfClient, EmfConfiguration> getClientManager() {
		if (clientManager == null) {
			clientManager = new EmfClientManager();
		}
		return clientManager;
	}

	public final EmfTaskSchema getSchema() {
		if (taskSchema == null) {
			taskSchema = createTaskSchema();
			taskSchema.initialize();
		}
		return taskSchema;
	}

	/**
	 * Override to provide a custom task schema implementation. (The default Schema will provide values based on the
	 * connector meta-data .)
	 * 
	 * @return
	 */
	public EmfTaskSchema createTaskSchema() {
		return new EmfTaskSchemaDelegator();
	}

	public File getCacheFile() {
		return configurationCacheFile;
	}

	/**
	 * Override to implement a custom EMF client. In most cases this should not be necessary.
	 * 
	 * @param repository
	 * @param configuration
	 * @return
	 */
	protected EmfClient createClient(TaskRepository repository, EmfConfiguration configuration) {
		return new EmfClient(repository, configuration) {
			@Override
			public AbstractEmfConnector getConnector() {
				return AbstractEmfConnector.this;
			}

		};
	}

	public Set<QueryClause> getQueryClauses() {
		return queryClauses;
	}

	public abstract String getNextTaskId(EObject taskContainer);

	public abstract EAttribute getContentsNameAttribute();

	public abstract EReference getContainmentReference();

	/**
	 * The class to be used for task container. By default this is the clss defined by the containment reference.
	 * 
	 * @return
	 */
	public EClass getContainerClass() {
		return getContainmentReference().getEContainingClass();
	}

	public abstract EAttribute[] getSearchAttributes();

	public abstract Bundle getConnectorBundle();

	public abstract EClass[] getTaskClasses();

	public abstract FieldFeature[] getTaskFeatures();
}
