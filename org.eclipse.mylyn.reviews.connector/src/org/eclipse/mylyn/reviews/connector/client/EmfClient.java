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
 *******************************************************************************/
package org.eclipse.mylyn.reviews.connector.client;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.mylyn.internal.reviews.connector.EmfCorePlugin;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.EmfConfiguration;

/**
 * Manages interaction with specific review file.
 * 
 * @author MilesParker
 */
public abstract class EmfClient {

	private EmfConfiguration configuration;

	private final TaskRepository repository;

	private EObject taskContainer;

	public EmfClient(TaskRepository repository) {
		this.repository = repository;
	}

	public EmfClient(TaskRepository repository, EmfConfiguration configuration) {
		this(repository);
		this.configuration = configuration;
	}

	public EmfConfiguration updateConfiguration(IProgressMonitor monitor) throws CoreException {
		if (taskContainer != null) {
			URI uri = URI.createURI(repository.getRepositoryUrl());
			URI currentUri = taskContainer.eResource().getURI();
			if (!uri.equals(currentUri)) {
				save();
				close();
				taskContainer = null;
			}
		}
		if (taskContainer == null) {
			open();
			configuration = new EmfConfiguration();
		}
		return configuration;
	}

	/**
	 * Opens the EMF resource at the underlying location. A resource must exist at the repository url. Safe and
	 * efficient to call when resource is already open.
	 * 
	 * @throws CoreException
	 *             If the resource at the repository URI doesn't exist or has problems
	 */
	public synchronized void open() throws CoreException {
		URI uri = URI.createURI(getRepository().getRepositoryUrl());
		if (taskContainer != null) {
			URI currentUri = taskContainer.eResource().getURI();
			if (uri.equals(currentUri)) {
				return;
			}
		}
		try {
			taskContainer = openContainer(uri);
		} catch (WrappedException e) {
			throw new CoreException(new Status(IStatus.ERROR, EmfCorePlugin.PLUGIN_ID, "File doesn't exist: "
					+ repository.getRepositoryUrl(), e));
		}
	}

	/**
	 * Persists the current client to underlying resource. Safe to call when no resource is currently loaded.
	 * 
	 * @throws CoreException
	 *             If there are issues saving the resources
	 */
	public synchronized void save() throws CoreException {
		if (taskContainer != null) {
			saveObject(taskContainer);
		}
	}

	/**
	 * Persists the current client to underlying resource. Safe to call when no resource is currently loaded.
	 * 
	 * @throws CoreException
	 *             If there are issues saving the resources
	 */
	public final synchronized void close() throws CoreException {
		if (taskContainer != null) {
			closeObject(taskContainer);
			taskContainer = null;
		}
	}

	/**
	 * Override to provide custom loading for object.
	 * 
	 * @return
	 * @throws CoreException
	 */
	protected EObject openContainer(URI uri) throws CoreException {
		ResourceSet set = new ResourceSetImpl();
		Resource resource = set.getResource(uri, true);
		if (resource.getContents().size() < 1) {
			EmfCorePlugin.handleCoreException("No model object at " + uri, null); //$NON-NLS-1$
		}
		taskContainer = resource.getContents().get(0);
		EClass referenceClass = getConnector().getContainmentReference().getEContainingClass();
		if (!referenceClass.isSuperTypeOf(taskContainer.eClass())) {
			EmfCorePlugin.handleCoreException("Unexpected containment object. Expected: " + referenceClass.getName() //$NON-NLS-1$
					+ " Actual: " + taskContainer.eClass().getName(), null); //$NON-NLS-1$
		}
		return taskContainer;
	}

	/**
	 * Override to provide custom persistence for object.
	 * 
	 * @param object
	 */
	protected void saveObject(EObject object) throws CoreException {
		Resource currentResource = object.eResource();
		try {
			currentResource.save(new HashMap<Object, Object>());
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, getConnector().getConnectorBundle().getSymbolicName(),
					"Problem saving resource at " + currentResource.getURI(), e));
		}
	}

	/**
	 * Override to provide custom disposal of object.
	 * 
	 * @param object
	 */
	protected void closeObject(EObject object) {
		Resource resource = taskContainer.eResource();
		if (resource != null) {
			resource.unload();
			ResourceSet set = resource.getResourceSet();
			if (set != null) {
				set.getResources().remove(resource);
			}
		}
	}

	/**
	 * Override to provide custom factory implementations.
	 * 
	 * @param eClass
	 * @param taskData
	 * @return
	 * @throws CoreException
	 */
	public EObject create(EClass eClass, TaskData taskData) throws CoreException {
		return eClass.getEPackage().getEFactoryInstance().create(eClass);
	}

	/**
	 * Called when the supplied object has changed. This value should be updated once per posted change for an object,
	 * providing higher granularity than is provided by EMF notification mechanism. The object must be a member of the
	 * current task container. In the base case, which assumes that the EMF resource is a unified artifact (e.g. a
	 * file), this simply saves the entire model.
	 * 
	 * @throws CoreException
	 *             If there are issues saving the object
	 */
	public synchronized void notifyChanged(EObject object) throws CoreException {
		save();
	}

	public EObject getRootContainer() {
		return taskContainer;
	}

	public EmfConfiguration getConfiguration() {
		return configuration;
	}

	public TaskRepository getRepository() {
		return repository;
	}

	public abstract AbstractEmfConnector getConnector();
}
