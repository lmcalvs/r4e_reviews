/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation and API
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.utils.Tracer;

public class TstGeneral {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	public static final String GROUP_PATH_STR = "outGroupX";

	//Pre common model fixes

	public static final String GOLDEN_GROUP_DIR_STR = "stubs_model/" + GROUP_PATH_STR;

	public static final String GOLDEN_GROUP_FILE_STR = GOLDEN_GROUP_DIR_STR + "/Golden_Group_group_root.xrer";

	//Windows specific
	public static final String GROUP_PATH_STRW = "outGroupW";

	public static final String GOLDEN_GROUP_DIR_STRW = "stubs_model/" + GROUP_PATH_STRW;

	public static final String GOLDEN_GROUP_FILE_STRW = GOLDEN_GROUP_DIR_STRW + "/Golden_Group_group_root.xrer";

	public static final URI GOLDEN_GROUP_DIR = URI.createFileURI(GOLDEN_GROUP_DIR_STR);

	public static final URI GOLDEN_GROUP_DIRW = URI.createFileURI(GOLDEN_GROUP_DIR_STRW);

	public static final URI GOLDEN_GROUP_FILE = URI.createFileURI(GOLDEN_GROUP_FILE_STR);

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public static void activateTracer() {
		Tracer.setInfo(true);
		Tracer.setDebug(true);
		Tracer.setError(true);
		Tracer.setWarning(true);
	}

	/**
	 * @param fileA
	 * @param fileB
	 * @return - true if they are the same
	 */
	public static boolean compareDirectories(File fileA, File fileB) {
		// valid directories
		if (!(fileA.isDirectory() && fileB.isDirectory())) {
			return false;
		}

		// must exist
		if (!(fileA.exists() && fileB.exists())) {
			return false;
		}

		// TODO: implement a recursive check file per file e.g. using FileUtils.contentEquals(file1, file2);
		// The following is a temporary workaround by directory size

		// Find the size of the Golden directory
		long fileSizeA = FileUtils.sizeOfDirectory(fileA);
		// Find the size of the new Directory
		long fileSizeB = FileUtils.sizeOfDirectory(fileB);

		boolean same = (fileSizeA == fileSizeB);
		if (!same) {
			StringBuilder sb = new StringBuilder("TestGeneral.compareDirectories, directories size are different:");
			try {
				sb.append("\n\tfile Size is:" + fileSizeA + " for: " + fileA.getCanonicalPath());
				sb.append("\n\tfile Size is:" + fileSizeB + " for: " + fileB.getCanonicalPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(sb.toString());
		}

		return same;
	}
}
