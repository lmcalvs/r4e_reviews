// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * This class implements a simple wildcard filter used in file search
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class WildcardFileFilter implements FileFilter {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fPattern.
	 */
	private final String fPattern;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Constructor for WildcardFileFilter.
	 * 
	 * @param pattern
	 *            String
	 */
	public WildcardFileFilter(String pattern) {
		fPattern = pattern.replace("*", ".*").replace("?", ".");
	}

	/**
	 * Method accept.
	 * 
	 * @param file
	 *            File
	 * @return boolean
	 * @see java.io.FileFilter#accept(File)
	 */
	public boolean accept(File file) {
		return Pattern.compile(fPattern).matcher(file.getName()).find();
	}
}
