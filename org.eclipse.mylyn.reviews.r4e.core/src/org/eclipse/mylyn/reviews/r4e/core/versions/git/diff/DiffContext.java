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
package org.eclipse.mylyn.reviews.r4e.core.versions.git.diff;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public interface DiffContext {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	// Getters
	public RevCommit getCommit();

	public String getPath();

	public ChangeType getChange();

	public ObjectId[] getBlobs();

	public FileMode[] getModes();


	public interface DiffContextSettable extends DiffContext {
		// setters
		public void setPath(String aPath);

		public void setChange(ChangeType aChangeType);

		public void setBlobs(ObjectId[] aObjectids);

		public void setModes(FileMode[] aModes);
	}
}
