/*******************************************************************************
 * Copyright (c) 2013 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.reviews.r4e.connector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.reviews.connector.EmfAttributeMapper;
import org.eclipse.mylyn.reviews.connector.EmfTaskSchema;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;

/**
 * Extends attribute mapper to manage checking out of R4E objects.
 * 
 * @author Miles Parker
 */
public class R4EAttributeMapper extends EmfAttributeMapper {

	public R4EAttributeMapper(EmfTaskSchema emfTaskSchema, TaskRepository taskRepository) {
		super(emfTaskSchema, taskRepository);
	}

	@Override
	public boolean copyTaskToEmf(TaskAttribute attribute, EObject object) throws CoreException {
		//Introduces apparently unavoidable dependency on r4e ui..
		final String currentUser = R4EUIModelController.getReviewer();
		Long bookNum;
		try {
			ResourceUpdater fResourceUpdater = R4EUIModelController.FResourceUpdater;
			bookNum = fResourceUpdater.checkOut(object, currentUser);
			boolean result = super.copyTaskToEmf(attribute, object);
			fResourceUpdater.checkIn(bookNum);
			return result;
		} catch (ResourceHandlingException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID,
					"Problem while updating value: " + e.getLocalizedMessage(), e));
		} catch (OutOfSyncException e) {
			throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID,
					"Problem while updating value: " + e.getLocalizedMessage(), e));
		}
	}
}
