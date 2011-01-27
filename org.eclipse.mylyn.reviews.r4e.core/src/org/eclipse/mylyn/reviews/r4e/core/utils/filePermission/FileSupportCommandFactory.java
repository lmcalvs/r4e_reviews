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

import org.eclipse.mylyn.reviews.r4e.core.utils.sys.OSPLATFORM;

/**
 * @author lmcbout
 * 
 */
public class FileSupportCommandFactory {

	public static IFileSupportCommand getInstance() {
		if (OSPLATFORM.TYPE.isWindowsOS()) {
			// PC environment
			return new WindowPermission();
		} else if (OSPLATFORM.TYPE.isLinuxOS()) {
			// Running on "Linux" operating system
			// Debug.print(" *** Operating On a LINUX ****");
			// UNIX environment
			return new UnixPermission();
		} else if (OSPLATFORM.TYPE.isSolarisOS()) {
			// Running on "Solaris" operating system
			// Debug.print(" *** Operating On a SOLARIS ****");
			// Might be the same as Linux
			// UNIX environment
			return new UnixPermission();
		}
		return null;

	}

}
