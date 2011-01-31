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
 * 	 Ericsson Research Canada - Initial API and Implementation 
 *   Alvaro Sanchez-Leon - Adapted for Review for Eclipse
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils.filePermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.utils.cmd.BaseSupportCommand;

/**
 * @author lmcbout
 * 
 */
public class WindowPermission extends BaseSupportCommand implements
		IFileSupportCommand {

	private static final String	SUCCESS_MSG	= "Failed processing 0";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.IFileSupportCommand#changePermission(java.lang.String)
	 */
	public boolean grantWritePermission(String dir) throws IOException {
		Boolean b = false;
		// Activator.Tracer.traceInfo("Changing permissions for dir: " + dir);

		// NOTE: The permissions can be changed by the file / folder owner only, i.e. This update shall be done right
		// after creation.
		List<String> command = new ArrayList<String>();
		command.add("icacls");
		command.add(dir); // filename or directory to modify
		command.add("/grant");
		command.add("Everyone:F");
		// command.add("/T"); // Change ACLs recursively

		String ret = executeWithStringReturned(null, command, true /* wait */,
				true /* ignore error */);
		// Activator.Tracer.traceInfo("changePermission() ret: " + ret);
		Activator.fTracer.traceInfo("Return msg from changePermission: " + ret);
		if (ret.length() > 0 && ret.contains(SUCCESS_MSG)) {
			b = true;
		}

		return b;
	}
}
