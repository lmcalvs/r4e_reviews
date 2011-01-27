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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext;

/**
 * @author lmcalvs
 *
 */
public class GitDiffContext extends DiffEntry implements DiffContext {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	RevCommit	fCommit	= null;
	DiffEntry	fDiffEntry	= null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	GitDiffContext(RevCommit aCommit, DiffEntry aEntry) {
		fCommit = aCommit;
		fDiffEntry = aEntry;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public RevCommit getCommit() {
		return fCommit;
	}


	public DiffEntry getDiffEntry() {
		return fDiffEntry;
	}

	public String getPath() {
		if (ChangeType.DELETE.equals(fDiffEntry.getChangeType()))
			return fDiffEntry.getOldPath();
		return fDiffEntry.getNewPath();
	}

	public ChangeType getChange() {
		return fDiffEntry.getChangeType();
	}

	public ObjectId[] getBlobs() {
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		if (fDiffEntry.getOldId() != null)
			objectIds.add(fDiffEntry.getOldId().toObjectId());
		if (fDiffEntry.getNewId() != null)
			objectIds.add(fDiffEntry.getNewId().toObjectId());
		return objectIds.toArray(new ObjectId[] {});
	}

	public FileMode[] getModes() {
		List<FileMode> modes = new ArrayList<FileMode>();
		if (fDiffEntry.getOldMode() != null)
			modes.add(fDiffEntry.getOldMode());
		if (fDiffEntry.getOldMode() != null)
			modes.add(fDiffEntry.getOldMode());
		return modes.toArray(new FileMode[] {});
	}
}
	