/*******************************************************************************
 * Copyright (c) 2013 Ericsson and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.rfs.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

/**
 * Manage the local version of the R4E metadata.
 * 
 * @author Francois Chouinard
 */

/* TODO: In this version we perform way too many commits to stay on the
 * safe-side. A more discriminate approach is needed for a releaseable solution.
 */

/* TODO: Check-in comments are hard-coded
 * Not such a bad idea but they need to be improved...
 */
public class R4ELocalRepository {

	// --------------------------------------------------------------------------
	// Constants
	// --------------------------------------------------------------------------

	private static final String REVIEW_ITEMS_FOLDER_NAME = "ReviewItems"; //$NON-NLS-1$

	private static final String GIT_FOLDER_NAME = ".git"; //$NON-NLS-1$

	private static final String ROOT_BRANCH = "master"; //$NON-NLS-1$

	// --------------------------------------------------------------------------
	// Attributes
	// --------------------------------------------------------------------------

	// The R4ERepository singleton
	private static R4ELocalRepository fInstance = null;

	// The current review group folder
	private File fReviewGroupFolder = null;

	// The git repo (.git) in the review group folder
	private Git fGit = null;

	// The active review in the review group
	private String fActiveReviewName = null;

	// --------------------------------------------------------------------------
	// Static stuff
	// --------------------------------------------------------------------------

	/**
	 * @return the R4ERepository singleton
	 */
	public static synchronized R4ELocalRepository getInstance() {
		if (fInstance == null) {
			fInstance = new R4ELocalRepository();
		}
		return fInstance;
	}

	/**
	 * Indicates if the folder hosts a valid R4E repository
	 * 
	 * @param folder
	 * @return
	 */
	public static boolean isValidRepository(File folder) {
		File gitFolder = new File(folder, GIT_FOLDER_NAME);
		return FileKey.isGitRepository(gitFolder, FS.DETECTED);
	}

	// --------------------------------------------------------------------------
	// Constructors
	// --------------------------------------------------------------------------

	/**
	 * Private constructor
	 */
	private R4ELocalRepository() {
	}

	// --------------------------------------------------------------------------
	// Create/initialize an R4E repository
	// --------------------------------------------------------------------------

	/**
	 * Create a repository.
	 * 
	 * @throws GitAPIException
	 */
	public synchronized Repository create(File reviewGroupFolder) throws GitAPIException {
		// Create the review items folder
		new File(reviewGroupFolder, REVIEW_ITEMS_FOLDER_NAME).mkdirs();
		return open(reviewGroupFolder);
	}

	/**
	 * Open an existing repository.
	 * 
	 * @throws GitAPIException
	 */
	public synchronized Repository open(File reviewGroupFolder) throws GitAPIException {
		fReviewGroupFolder = reviewGroupFolder;
		fGit = Git.init().setDirectory(fReviewGroupFolder).call();
		return fGit.getRepository();
	}

	/**
	 * @return the R4E metadata repository
	 */
	public synchronized Repository getRepository() {
		return (fGit != null) ? fGit.getRepository() : null;
	}

	// --------------------------------------------------------------------------
	// Add a review item
	// --------------------------------------------------------------------------

	/**
	 * Copy the review item to the review items folder with proper folder structure (to mitigate potential name
	 * conflicts).
	 * 
	 * @param reviewItem
	 * @throws CoreException
	 * @throws IOException
	 */
	public synchronized void addReviewItem(IFile reviewItem) throws CoreException, IOException {

		// Prepare the recipient folder structure 
		String reviewItemFolderName = fReviewGroupFolder.getName() + File.separator + REVIEW_ITEMS_FOLDER_NAME;
		String reviewItemFolderPath = reviewItemFolderName + reviewItem.getFullPath().removeLastSegments(1);
		String reviewItemPath = reviewItemFolderName + File.separator + reviewItem.getName();
		File reviewItemFolder = new File(reviewItemFolderPath);
		if (!reviewItemFolder.exists()) {
			reviewItemFolder.mkdirs();
		}

		// Prepare the review item streaming
		InputStream input = reviewItem.getContents();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		// Prepare the receiving stream
		OutputStream output = new FileOutputStream(reviewItemPath);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

		// Perform a binary copy
		char[] buffer = new char[1024];
		int nbRead = reader.read(buffer);
		while (nbRead > 0) {
			writer.write(buffer, 0, nbRead);
			nbRead = reader.read(buffer);
		}

		// Cleanup
		reader.close();
		writer.close();
	}

	// --------------------------------------------------------------------------
	// openReview
	// --------------------------------------------------------------------------

	/**
	 * Sets the active review
	 */
	public synchronized void openReview(String review) throws Exception {

		// Commit any stuff in the currently active review
		commitReview("Review of ??? by ???"); //$NON-NLS-1$

		// Set the new active review
		fActiveReviewName = review;

		// Checkout the review branch (create if necessary)
		try {
			fGit.checkout().setName(review).setCreateBranch(true).setStartPoint(ROOT_BRANCH).call();
		} catch (RefAlreadyExistsException e) {
			fGit.checkout().setName(review).call();
		}

		// And reset
		fGit.reset().setMode(ResetType.HARD).call();
	}

	// --------------------------------------------------------------------------
	// commitReview
	// --------------------------------------------------------------------------

	/**
	 * Commit the active review
	 */
	public synchronized ObjectId commitReview(String message) throws Exception {

		// Minimal validation
		if (fActiveReviewName == null) {
			return null;
		}

		// Stage the review items and commit
		fGit.add().addFilepattern(".").call(); //$NON-NLS-1$
		return fGit.commit().setMessage(message + "\n").call().getId(); //$NON-NLS-1$
	}

	// --------------------------------------------------------------------------
	// checkoutReview
	// --------------------------------------------------------------------------

	/**
	 * Checkout and set the active review
	 */
	public synchronized void checkoutReview(String review, String sha) throws Exception {

		// Commit any stuff in the currently active review
		commitReview("Review of ??? by ???"); //$NON-NLS-1$

		// Set the new active review
		fActiveReviewName = review;

		// Checkout the review branch (create if necessary)
		try {
			fGit.checkout().setName(review).setCreateBranch(true).setStartPoint(ROOT_BRANCH).call();
		} catch (RefAlreadyExistsException e) {
			fGit.checkout().setName(review).call();
		}

		// Reset
		fGit.reset().setMode(ResetType.HARD).call();

		// And merge the review commit
		ObjectId commitId = ObjectId.fromString(sha);
		fGit.merge().include(commitId).call();
	}

}
