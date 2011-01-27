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

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext.DiffContextSettable;

public class GitDiffContextMerged implements DiffContextSettable {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private String		path;

	private ChangeType	change;

	private ObjectId[]	blobs;

	private FileMode[]	modes;

	private RevCommit	fCommit;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	GitDiffContextMerged(final RevCommit aCommit) {
		fCommit = aCommit;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public String getPath() {
		return path;
	}

	public ChangeType getChange() {
		return change;
	}

	public ObjectId[] getBlobs() {
		return blobs;
	}

	public FileMode[] getModes() {
		return modes;
	}

	public RevCommit getCommit() {
		return fCommit;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setChange(ChangeType change) {
		this.change = change;
	}

	public void setBlobs(ObjectId[] blobs) {
		this.blobs = blobs;
	}

	public void setModes(FileMode[] modes) {
		this.modes = modes;
	}
}


	

	