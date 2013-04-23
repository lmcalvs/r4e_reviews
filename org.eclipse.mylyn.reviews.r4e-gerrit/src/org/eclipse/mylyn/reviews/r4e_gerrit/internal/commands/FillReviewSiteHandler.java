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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EUIConstants;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class FillReviewSiteHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Opening Element..."")
	 */
	private static final String COMMAND_MESSAGE = "Search Gerrit locations ...";

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

				// Begin Test 1  
				//This return me the list of GIT repos defined in the GIT Repositories view
				//This is not exactly what we need, we need the GIT Repo defined in Preferences /team/Git/configuration/Repository setting
				//If not, we need to open the configuration file for each of the following repository and read the gerrit  url
				List<Repository> repositories;
				repositories = new ArrayList<Repository>();
				List<String> repoPaths = org.eclipse.egit.core.Activator.getDefault().getRepositoryUtil().getConfiguredRepositories();
				RepositoryCache repositoryCache = org.eclipse.egit.core.Activator.getDefault().getRepositoryCache();
				for (String repoPath : repoPaths) {
					R4EGerritPlugin.Ftracer.traceInfo("List Gerrit repository: " + repoPath);
					File gitDir = new File(repoPath);
					if (!gitDir.exists())
						continue;
				}

				//End Test 1
				aMonitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

}
