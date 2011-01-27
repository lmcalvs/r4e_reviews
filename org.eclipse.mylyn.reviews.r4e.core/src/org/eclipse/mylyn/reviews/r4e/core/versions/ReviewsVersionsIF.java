/*******************************************************************************
 * Copyright (c) 2010 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.versions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;

/**
 * @author lmcalvs
 *
 */
public interface ReviewsVersionsIF {
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Query for the support of the team provider associated to a given project
	 * 
	 * @param project
	 * @return
	 */
	public boolean isProviderSupported(IProject project);

	/**
	 * @param project
	 * @return
	 * @throws ReviewVersionsException
	 */
	public CommitDescriptor getLastCommitInfo(IProject project) throws ReviewVersionsException;

	/**
	 * @param project
	 * @param commitId
	 * @return
	 * @throws ReviewVersionsException
	 */
	public CommitDescriptor getCommitInfo(IProject project, String commitId) throws ReviewVersionsException;

	/**
	 * Creates a Commit review item with the related file contexts derived from the commit NOTE: The R4EItem return is
	 * not associated to any EMF Resources
	 * 
	 * @param project
	 * @param commiId
	 * @param reviewUser
	 * @return
	 * @throws ReviewVersionsException
	 */
	public R4EItem createCommitReviewItem(IProject project, String commiId, R4EUser reviewUser)
			throws ReviewVersionsException;

	/**
	 * Obtain the repository information for the last commit of the given file
	 * 
	 * @param workspaceFile
	 * @return
	 * @throws ReviewVersionsException
	 */
	public FileVersionInfo getFileVersionInfo(IFile workspaceFile) throws ReviewVersionsException;

	/**
	 * Request a compare session to open the compare editor for the associated context
	 * 
	 * @param item
	 * @return - compare session id as a string
	 * @throws ReviewVersionsException
	 */
	public String openCompareSession(R4EItem item) throws ReviewVersionsException;

	/**
	 * @param session
	 * @param context
	 * @throws ReviewVersionsException
	 */
	public void openCompareEditor(String session, R4EFileContext context) throws ReviewVersionsException;

	/**
	 * @param session
	 */
	public void closeCompareSession(String session);

	public interface CommitDescriptor {
		public String getId();

		/**
		 * @return time, expressed as milliseconds since the epoch.
		 */
		public Long getCommitDate();

		public String getCommitter();

		public String getAuthor();

		public String getTitle();

		public String getMessage();

		public String[] getParentIDs();

		/**
		 * Returns a list of paths to the affected elements
		 * 
		 * @return
		 */
		public String[] getChangeSet();
	}
	
	public interface FileVersionInfo {
		public String getRepositoryPath();

		public String getId();

		public String getName();
	}
}
