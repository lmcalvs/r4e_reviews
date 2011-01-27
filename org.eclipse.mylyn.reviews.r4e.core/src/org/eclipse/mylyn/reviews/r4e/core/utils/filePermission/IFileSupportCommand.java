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

/**
 * @author lmcalvs
 * 
 */
public interface IFileSupportCommand {

	/**
	 * Grant write permissions to the given file or folder
	 * 
	 * @param path
	 *            - Absolute string path to a file or folder
	 * @return
	 * @throws IOException
	 */
	public boolean grantWritePermission(String path) throws IOException;
}
