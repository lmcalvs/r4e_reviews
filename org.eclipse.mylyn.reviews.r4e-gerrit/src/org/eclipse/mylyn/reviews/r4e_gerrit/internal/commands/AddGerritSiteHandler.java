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
 * This class implements the "Add ..." a new Gerrit 
 * project locations.
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E-Gerrit project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.internal.commands;

import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.mylyn.internal.gerrit.core.GerritConnector;
import org.eclipse.mylyn.internal.tasks.ui.wizards.EditRepositoryWizard;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EGerritServerUtility;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.ui.wizards.TaskRepositoryWizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Widget;


/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class AddGerritSiteHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Add a Gerrit location ..."")
	 */
	private static final String COMMAND_MESSAGE = "Add a Gerrit location ...";

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private R4EGerritServerUtility fServerUtil = null;
	
	private Map<Repository, String> fMapRepoServer = null;

	
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

		R4EGerritPlugin.Ftracer.traceInfo("Create the Add button to search the Gerrit location " ); //$NON-NLS-1$
		String menuItemText = "";
		fServerUtil = new R4EGerritServerUtility();
		Object obj = aEvent.getTrigger();
		Map<String, String> param = aEvent.getParameters();
		
		if (obj instanceof Event) {
			Event ev = (Event) obj;
			Widget objWidget = ev.widget;
			if (objWidget instanceof MenuItem) {
				MenuItem menuItem = (MenuItem) objWidget;
				menuItemText = menuItem.getText();
//				R4EGerritPlugin.Ftracer.traceInfo("MenuItem: " + menuItemText );
				R4EGerritPlugin.Ftracer.traceInfo("MenuItem: " + menuItemText + "\t value: " +
						param.get(menuItemText) + " VS saved: "  + fServerUtil.getLastSavedGerritServer());
				fMapRepoServer = fServerUtil.getGerritMapping();
				String stURL = fServerUtil.getMenuSelectionURL(menuItemText);
				R4EGerritPlugin.Ftracer.traceInfo("URL for the menuItemText: " + stURL);
				if (!fMapRepoServer.isEmpty()) {
					Set<Repository> mapSet = fMapRepoServer.keySet();
					R4EGerritPlugin.Ftracer.traceInfo("-------------------");
					for (Repository key: mapSet) {
						R4EGerritPlugin.Ftracer.traceInfo("Map Key name: " + key.getWorkTree().getName() + "\t URL: " + fMapRepoServer.get(key));
					}
				}
				
				//Verify if we selected the "Add.." button or a pre=defined Gerrit
				if (stURL != null) {
					if (stURL.equals(fServerUtil.getLastSavedGerritServer())) {
						R4EGerritPlugin.Ftracer.traceInfo("LAST SAVED server is the SAME ");
						fServerUtil.getReviewListFromServer ();
						return Status.OK_STATUS; //For now , do not process the dialogue
					} else {
						//Store the new Gerrit server into a file
						fServerUtil.saveLastGerritServer(stURL);
						fServerUtil.getReviewListFromServer ();
						return Status.OK_STATUS; //For now , do not process the dialogue
					}
				}
			}
		}

		//Open the Dialogue to enter a new Gerrit URL
		return openDialogue ();
	}

	/**
	 * Initiate a JOB to open the Gerrit definition dialogue
	 * @return Object
	 */
	private Object openDialogue () {
		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object aFamily) {
				return familyName.equals(aFamily);
			}

			@Override
			public IStatus run(final IProgressMonitor aMonitor) {
				aMonitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);
						
//				final TaskRepository repository = getTaskRepository(); 
				final TaskRepository repository = getTaskRepository(fServerUtil.getLastSavedGerritServer()); 
				
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
						repository.getUrl().endsWith(R4EUIConstants.DEFAULT_REPOSITORY)) {
					//User selected the Cancel button
					R4EGerritPlugin.Ftracer.traceInfo("AFTER: repository: CANCEL "  ); //$NON-NLS-1$
				} else {
					R4EGerritPlugin.Ftracer.traceInfo("AFTER: repository: :  FINISH " ); //$NON-NLS-1$		
					fServerUtil.saveLastGerritServer(repository.getUrl());
					/*****************************************************/
					/*                                                   */
					/*    Now, we need to get the Gerrit repo data       */
					/*    and populate the list of Reviews               */
					/*                                                   */
					/*                                                   */
					/*****************************************************/
					fServerUtil.getReviewListFromServer ();
				}
				
				R4EGerritPlugin.Ftracer.traceInfo("AFTER: repository: :  " + repository.getUrl() + 
						"\n\t repo: " + repository.getRepositoryUrl() ); //$NON-NLS-1$
				

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
	 * 
	 * @param String default URL
	 * @return TaskRepository
	 */
//	private TaskRepository getTaskRepository () {
	private TaskRepository getTaskRepository (String aUrl) {
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
//			taskRepo = new TaskRepository (GerritConnector.CONNECTOR_KIND, R4EUIConstants.DEFAULT_REPOSITORY);
			if (aUrl != null) {
				taskRepo = new TaskRepository (GerritConnector.CONNECTOR_KIND, aUrl);
			} else {
				taskRepo = new TaskRepository (GerritConnector.CONNECTOR_KIND, R4EUIConstants.DEFAULT_REPOSITORY);				
			}
			
		}
		return taskRepo;
	}
	
}
