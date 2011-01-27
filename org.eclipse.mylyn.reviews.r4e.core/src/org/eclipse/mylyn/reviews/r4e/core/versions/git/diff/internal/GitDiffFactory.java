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
 *   Based on internal org.eclipse.egit.ui.internal.history.FileDiff
 *   Alvaro Sanchez-Leon - Adapted for R4E
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.internal;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext.DiffContextSettable;

/**
 * @author lmcalvs
 *
 */
public class GitDiffFactory {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Returns a new instance of the DiffContext to be used when the commit has a single commit parent
	 * 
	 * @param aCommit
	 * @param aEntry
	 * @return
	 */
	public static DiffContext createGitDiffContext(RevCommit aCommit, DiffEntry aEntry) {
		return new GitDiffContext(aCommit, aEntry);
	}

	/**
	 * Returns a new instance of the DiffContext to be used when the commit has a multiple commit parent
	 * 
	 * @param aCommit
	 * @return
	 */
	public static DiffContextSettable createGitDiffContextMerged(RevCommit aCommit) {
		return new GitDiffContextMerged(aCommit);
	}

}


