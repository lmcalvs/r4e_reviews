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
 * 	 Ericsson AB - Initial API and Implementation 
 *   Alvaro Sanchez-Leon - Adapted for Review for Eclipse
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils.filePermission;

import java.io.File;
import java.io.IOException;

import org.eclipse.mylyn.reviews.r4e.core.Activator;

/**
 * @author Jacques Bouthillier
 * 
 */
public class UserPermission {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Test if the user can create a new file in the specified directory
	 * 
	 * @param fileSt
	 *            File path to attempt creation
	 * @return Boolean
	 */
	public static Boolean canWrite(String fileSt) {
		Boolean ok = true;
		File file = new File(fileSt);

		try {
			file.createNewFile();
			Activator.fTracer.traceInfo("Create a file is allowed: " + fileSt);
		} catch (IOException e) {
			Activator.fTracer.traceInfo("UserPermission.canWrite() Create a  file is NOT allow: "
							+ e.getMessage());
			ok = false;
		}

		// Clean up
		if (file.exists()) {
			Boolean del;
			del = file.delete();
			Activator.fTracer.traceInfo("UserPermission Temp file " + file.getAbsolutePath()
					+ " deleted: " + del);
		}
		return ok;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test to write in a specific directory
		String[] dir = { "C:aa.txt",
				"E:/R4EStorage/R4ERevs2/r4eGroup/testFile",
				"E:/R4EStorage/testFile", "E:/R4EStorage/R4ERevs/testFile",
				"E:/R4EStorage/June2008/testFile", "C:/allo.tst" };
		for (int index = 0; index < dir.length; index++) {
			System.out.println("---------------------------------------");
			Boolean b = UserPermission.canWrite(dir[index]);
			System.out.println("UserPermision dir: " + dir[index]
					+ "\n\t canWrite: " + b);
		}
	}

}
