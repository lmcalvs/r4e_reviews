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
package org.eclipse.mylyn.internal.reviews.r4e.connector.ui.editor;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnector;
import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnectorPlugin;
import org.eclipse.mylyn.internal.reviews.r4e.connector.ui.R4EUiPlugin;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.core.model.IReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPage;
import org.eclipse.mylyn.tasks.ui.editors.AbstractTaskEditorPart;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditor;
import org.eclipse.mylyn.tasks.ui.editors.TaskEditorPartDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Customize the editor for R4E usage.
 * 
 * @author Miles Parker
 */
public class R4ETaskEditorPage extends AbstractTaskEditorPage {

	private IReview review;

	public R4ETaskEditorPage(TaskEditor editor) {
		super(editor, R4EConnector.CONNECTOR_KIND);
		setNeedsPrivateSection(true);
		R4EUIModelController.loadModel();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		super.init(site, input);
		forceModelUpdate();
		if (review == null) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, R4EUiPlugin.PLUGIN_ID, "Couldn't locate review for task: "
							+ getTask().getTaskId()));
		}
	}

	private void forceModelUpdate() {
		try {
			review = (IReview) ((AbstractEmfConnector) getConnector()).getTaskObject(getTaskRepository(),
					getTask().getTaskId(), new NullProgressMonitor());
			//TODO This is totally ugly hack, but we need to make sure the model loads here.
			try {
				RModelFactoryExt r4eFactory = SerializeFactory.getModelExtension();
				r4eFactory.openR4EReview((R4EReviewGroup) review.eContainer(), ((R4EReview) review).getName());
			} catch (ResourceHandlingException e) {
				throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID,
						"Couldn't open review for task.", e));
			} catch (CompatibilityException e) {
				throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID,
						"Couldn't open review for task.", e));
			}
		} catch (CoreException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.ERROR, R4EUiPlugin.PLUGIN_ID, "Couldn't open review for task: "
							+ getTask().getTaskId(), e));
		}
	}

	@Override
	protected Set<TaskEditorPartDescriptor> createPartDescriptors() {
		Set<TaskEditorPartDescriptor> descriptors = super.createPartDescriptors();
		for (TaskEditorPartDescriptor taskEditorPartDescriptor : descriptors) {
			if (taskEditorPartDescriptor.getId().equals(ID_PART_PEOPLE)) {
				descriptors.remove(taskEditorPartDescriptor);
				break;
			}
		}
		descriptors.add(new TaskEditorPartDescriptor(ID_PART_PEOPLE) {
			@Override
			public AbstractTaskEditorPart createPart() {
				return new R4EEditorPeoplePart();
			}
		}.setPath(PATH_PEOPLE));
		descriptors.add(new TaskEditorPartDescriptor(ArtifactsSection.class.getName()) {
			@Override
			public AbstractTaskEditorPart createPart() {
				return new ArtifactsSection();
			}
		});
		return descriptors;
	}

	@Override
	public void refresh() {
		forceModelUpdate();
		super.refresh();
	}

	public synchronized IReview getReview() {
		return review;
	}
}
