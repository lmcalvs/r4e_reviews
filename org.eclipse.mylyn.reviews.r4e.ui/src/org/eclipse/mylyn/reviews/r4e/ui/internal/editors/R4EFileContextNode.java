/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;

/**
 * @author Steffen Pingel
 */
public class R4EFileContextNode extends DiffNode {

	private IPath fPath;

	private String fName;

	public R4EFileContextNode(ITypedElement aTarget, ITypedElement aBase) {
		super(Differencer.NO_CHANGE);
		R4EFileVersion targetVersion = null;
		R4EFileVersion baseVersion = null;
		if (aTarget instanceof R4EFileTypedElement) {
			targetVersion = ((R4EFileTypedElement) aTarget).getFileVersion();
		} else { //Assume R4EFileRevisionTypedElement
			targetVersion = ((R4EFileRevisionTypedElement) aTarget).getFileVersion();
		}
		if (aBase instanceof R4EFileTypedElement) {
			baseVersion = ((R4EFileTypedElement) aBase).getFileVersion();
		} else { //Assume R4EFileRevisionTypedElement
			baseVersion = ((R4EFileRevisionTypedElement) aBase).getFileVersion();
		}

		String targetPath = targetVersion.getRepositoryPath();
		int kind = Differencer.CHANGE;
		if (targetPath == null) {
			targetPath = baseVersion.getRepositoryPath();
			kind = Differencer.ADDITION;
		}
		String basePath = baseVersion.getRepositoryPath();
		if (basePath == null) {
			basePath = targetPath;
			kind = Differencer.DELETION;
		}
		if (targetPath.equals("/COMMIT_MSG")) { //$NON-NLS-1$
			kind = Differencer.NO_CHANGE;
		}

		//Set content for the two sides of the compare editor
		setLeft(aTarget);
		setRight(aBase);
		setKind(kind);
		IPath path = Path.fromPortableString(targetPath);
		setPath(path);
		setName(path.lastSegment());
	}

	public IPath getPath() {
		return fPath;
	}

	public void setPath(IPath path) {
		fPath = path;
	}

	@Override
	public String getName() {
		return fName;
	}

	public void setName(String name) {
		fName = name;
	}
}
