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

import org.eclipse.mylyn.reviews.r4e.core.utils.sys.OSPLATFORM;

/**
 * @author Jacques Bouthillier
 * 
 */
public class FileSupportCommandFactory {

	public static IFileSupportCommand getInstance() {
		if (OSPLATFORM.TYPE.isWindowsOS()) {
			//Windows operating system
			return new WindowPermission();
		} else if (OSPLATFORM.TYPE.isLinuxOS() || OSPLATFORM.TYPE.isSolarisOS() || OSPLATFORM.TYPE.isMacOS()) {
			//Unix/Linux operating system
			return new UnixPermission();
		}
		return null;

	}

}
