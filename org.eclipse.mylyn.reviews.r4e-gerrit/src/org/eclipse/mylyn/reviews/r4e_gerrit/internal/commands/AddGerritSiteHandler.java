/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Add to pre-filled the list of gerrit
 * project locations.
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E-Gerrit project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.internal.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.mylyn.internal.gerrit.core.GerritConnector;
import org.eclipse.mylyn.internal.tasks.core.RepositoryTemplateManager;
import org.eclipse.mylyn.internal.tasks.ui.TasksUiPlugin;
import org.eclipse.mylyn.internal.tasks.ui.wizards.EditRepositoryWizard;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.tasks.core.RepositoryTemplate;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.TasksUiUtil;
import org.eclipse.mylyn.tasks.ui.wizards.TaskRepositoryWizardDialog;
import org.eclipse.swt.widgets.Display;


/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class AddGerritSiteHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field DEFAULT_REPOSITORY. (value is ""https://repository"")
	 */
	private  String DEFAULT_REPOSITORY = "https://";

	/**
	 * Field COMMAND_MESSAGE. (value is ""Add a Gerrit location ..."")
	 */
	private static final String COMMAND_MESSAGE = "Add a Gerrit location ...";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		R4EGerritPlugin.Ftracer.traceInfo("Create the Add button for to search the Gerrit location "); //$NON-NLS-1$

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object aFamily) {
				return familyName.equals(aFamily);
			}

			@Override
			public IStatus run(final IProgressMonitor aMonitor) {
				aMonitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);
				
//				TaskRepository repository = new TaskRepository(GerritConnector.CONNECTOR_KIND, "http://repository"); //$NON-NLS-1$
//				final TaskRepository repository = new TaskRepository(GerritConnector.CONNECTOR_KIND, "https://"); //$NON-NLS-1$
				
				final TaskRepository repository = getTaskRepository(); //$NON-NLS-1$
				R4EGerritPlugin.Ftracer.traceInfo("repository:   " + repository.getUrl()); //$NON-NLS-1$
//				int ret = TasksUiUtil.openEditRepositoryWizard(repository); //Generate a null pointer for the workbench window
				
				
				R4EGerritPlugin.Ftracer.traceInfo("Before: repository:   " + repository.getUrl() ); //$NON-NLS-1$
				final EditRepositoryWizard wizard = new EditRepositoryWizard(repository);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
							WizardDialog dialog = new TaskRepositoryWizardDialog(wizard.getShell(), wizard);
							dialog.create();
							dialog.setBlockOnOpen(true);
							dialog.open();
					}
				});
				
				//When the wizard is closed
				if (repository.getUrl().isEmpty() || 
						repository.getUrl().endsWith(DEFAULT_REPOSITORY)) {
					//User selected the Cancel button
					R4EGerritPlugin.Ftracer.traceInfo("AFTER: repository: CANCEL "  ); //$NON-NLS-1$
				} else {
					R4EGerritPlugin.Ftracer.traceInfo("AFTER: repository: :  FINISH " ); //$NON-NLS-1$				
				}
				
				R4EGerritPlugin.Ftracer.traceInfo("AFTER: repository: :  " + repository.getUrl() + 
						"\n\t repo: " + repository.getRepositoryUrl() ); //$NON-NLS-1$
				
				/***********************************************************************************/
				/*                                                                                 */
				/*    Now, we need to save the Gerrit repo data in the preference or elsewhere     */
				/*    for handling the review                                                      */
				/*                                                                                 */
				/*                                                                                 */
				/***********************************************************************************/
				

				aMonitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Look at the current Gerrit repository and return a default value 
	 * i.e the first Gerrit if found ???
	 * @return TaskRepository
	 */
	private TaskRepository getTaskRepository () {
		TaskRepository taskRepo = null;
		//Begin search for the current Gerrit connector
//		final RepositoryTemplateManager templateManager = TasksUiPlugin.getRepositoryTemplateManager();
//		
//		for (RepositoryTemplate template : templateManager.getTemplates(GerritConnector.CONNECTOR_KIND)) {
//			R4EGerritPlugin.Ftracer.traceInfo("Gerrit repository: " + template.label + "\t URL: " + template.repositoryUrl);
//			taskRepo = new TaskRepository (GerritConnector.CONNECTOR_KIND, template.repositoryUrl);
//			taskRepo.setRepositoryLabel(template.label);
//		}


		if (taskRepo == null) {
			//Create a default Task repo
			taskRepo = new TaskRepository (GerritConnector.CONNECTOR_KIND, DEFAULT_REPOSITORY);
			
		}
		return taskRepo;
	}
}
