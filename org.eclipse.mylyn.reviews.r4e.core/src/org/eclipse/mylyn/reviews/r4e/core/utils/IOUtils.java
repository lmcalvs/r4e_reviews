/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.utils;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.util.IO;

/**
 * @author Alvaro Sanchez-Leon
 *
 */
public class IOUtils {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Used when the size of the stream is unknown, the caller shall close the input stream
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFully(InputStream in) throws IOException {
		return IO.readWholeStream(in, 1).array();
	}

}
