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
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.r4e.connector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;

/**
 * Manages interaction with specific review file.
 * 
 * @author Miles Parker
 */
public class R4EClient extends EmfClient {

	public R4EClient(TaskRepository repository) {
		super(repository);
		setReloadOnOpen(true);
	}

	/**
	 * Opens the root EMF object at the underlying URI location. A resource must exist at the repository url.
	 * 
	 * @throws CoreException
	 *             If the resource at the repository URI doesn't exist or has problems
	 */
	@Override
	public EObject openContainer(URI uri) throws CoreException {
		RModelFactoryExt r4eFactory = SerializeFactory.getModelExtension();
		try {
			return r4eFactory.openR4EReviewGroup(uri);
		} catch (ResourceHandlingException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID, e.getLocalizedMessage(), e));
		} catch (CompatibilityException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID, e.getLocalizedMessage(), e));
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID, "Couldn't open resource: " //$NON-NLS-1$
					+ e.getClass().getSimpleName() + " [" + uri + "]", e)); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	@Override
	public EObject create(EClass eClass, TaskData taskData) throws CoreException {
		String reviewName = taskData.getRoot().getMappedAttribute(TaskAttribute.SUMMARY).getValue();
		try {
			return SerializeFactory.getModelExtension().createR4EReview((R4EReviewGroup) getRootContainer(),
					reviewName, "unknown");
		} catch (ResourceHandlingException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID, "Couldn't create task: " //$NON-NLS-1$
					+ e.getClass().getSimpleName() + " [" + eClass + "]", e)); //$NON-NLS-1$//$NON-NLS-2$
		}
	}

	@Override
	protected void saveObject(EObject object) throws CoreException {
		super.saveObject(object);
	}

	@Override
	public AbstractEmfConnector getConnector() {
		return R4EConnectorPlugin.getDefault().getConnector();
	}

}
