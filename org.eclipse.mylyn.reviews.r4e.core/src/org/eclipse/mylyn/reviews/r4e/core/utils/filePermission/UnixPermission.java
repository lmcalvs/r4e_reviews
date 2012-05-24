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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.utils.cmd.BaseSupportCommand;

/**
 * @author Jacques Bouthillier
 * 
 */
public class UnixPermission extends BaseSupportCommand implements
		IFileSupportCommand {

	/**
	 * Method to change permission on a directory
	 * 
	 * @param String
	 *            dir
	 * @throws IOException
	 */
	public boolean grantWritePermission(String dir) throws IOException {
		Boolean b = false;
		Activator.fTracer.traceInfo("UnixPermission.changePermission() for :" + dir);

		List<String> command = new ArrayList<String>();
		command.add("chmod");
		command.add("-R");
		command.add("0774");
		command.add(dir);
		
		String ret = executeWithStringReturned(null, command, true /* wait */,
				false /* ignore error */);
		Activator.fTracer.traceInfo("changePermission() ret: " + ret);
		if (ret.length() == 0) {
			b = true;
		} else {
			Activator.fTracer.traceInfo("Return msg from changePermission: " + ret);			
		}

		return b;
	}

}
