/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used for now to access restricted methods and fields in
 * the Egit UI plugin.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.egit.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class EgitProxy {

	public static IFile getCompareLeftFile(GitCompareFileRevisionEditorInput aInput) {
		IPath path = new Path(aInput.getLeftRevision().getPath());
		IFile file = (IFile) ResourcesPlugin.getWorkspace().getRoot().findMember(path);
		return file;
	}
}
