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
 * This class implements the search to pre-filled the list of gerrit
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
import org.eclipse.jgit.lib.Repository;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EGerritServerUtility;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EUIConstants;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.handlers.IHandlerService;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class SelectReviewSiteHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Opening Element..."")
	 */
	private static final String COMMAND_MESSAGE = "Search Gerrit locations ...";
	
	
	
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

		R4EGerritPlugin.Ftracer.traceInfo("Collecting the gerrit review locations"); //$NON-NLS-1$

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object aFamily) {
				return familyName.equals(aFamily);
			}

			@Override
			public IStatus run(IProgressMonitor aMonitor) {
				aMonitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);


				//Map the Gerrit server
				fServerUtil = new R4EGerritServerUtility();
				
				//Debug purpose, see which project have a gerrit server
				fMapRepoServer = fServerUtil.getGerritMapping();
				if (!fMapRepoServer.isEmpty()) {
					Set<Repository> mapSet = fMapRepoServer.keySet();
					R4EGerritPlugin.Ftracer.traceInfo("-------------------");
					for (Repository key: mapSet) {
						R4EGerritPlugin.Ftracer.traceInfo("Map Key repo name : " + key.getWorkTree().getName() + "\t URL: " + fMapRepoServer.get(key));
					}
				}
				//End Debug
				
				String serverToUsed = fServerUtil.getLastSavedGerritServer();
				if (serverToUsed!= null) {
					//Initiate the request for the list of reviews
					fServerUtil.getReviewListFromServer();
				} else {
					//Need to open the Dialogue to fill a Gerrit server
					R4EGerritPlugin.Ftracer.traceInfo("Need to open the Dialogue to fill a gerrit server " );
					//Get the service
					IWorkbench workbench = R4EGerritPlugin.getDefault().getWorkbench();
					IHandlerService handlerService = (IHandlerService) workbench.getService(IHandlerService.class);
					try {
						
						  handlerService.executeCommand(R4EUIConstants.ADD_GERRIT_SITE_COMMAND_ID, null);
					  } catch (Exception ex) {
						  R4EGerritPlugin.Ftracer.traceError("Exception: " + ex.toString());
						  R4EGerritPlugin.getDefault().logError("Exception: ", ex);
					  //  throw new RuntimeException("org.eclipse.mylyn.reviews.r4e_gerrit.internal.commands.AddGerritSite not found");
					    
					  }
				}
				
				
				aMonitor.done();
				return Status.OK_STATUS;
			}
			
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
