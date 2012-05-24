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
 *   Jacques Bouthillier - Initial Implementation of the international string 
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.report.internal.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.mylyn.reviews.r4e.report.internal.Activator;



public class R4EReportString {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static final String			FBUNDLE_NAME		= "org.eclipse.mylyn.reviews.r4e.report.R4EReportString";	//$NON-NLS-1$

	private static final ResourceBundle	FRESOURCE_BUNDLE	= ResourceBundle.getBundle(FBUNDLE_NAME);

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * Prevents clients from instantiating this class.
	 */
	private R4EReportString() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Gets the key from the value, or 'value' if not found.
	 * 
	 * @param value
	 *            the value to search value.
	 * @return the key from the value, or 'value' if not found.
	 */
	public static String getString(String aKey) {
		try {
			return FRESOURCE_BUNDLE.getString(aKey);
		} catch (MissingResourceException e) {
			Activator.FTracer.traceWarning("Warning: " + "EXCEPTION In getString for:" + aKey);
			return '!' + aKey + '!';
		}
	}

	/**
	 * Format the String according to the object pass
	 * 
	 * @param aKey
	 *            String
	 * @param aArg
	 *            Object
	 * @return the key from the value, or 'value' if not found.
	 */
	public static String getFormattedString(String aKey, Object aArg) {
		String format = null;
		try {
			format = FRESOURCE_BUNDLE.getString(aKey);
		} catch (MissingResourceException e) {
			return "!" + aKey + "!";//$NON-NLS-2$ //$NON-NLS-1$ 
		}
		if (aArg == null)
			aArg = ""; //$NON-NLS-1$ 
		return MessageFormat.format(format, new Object[] { aArg });
	}

	/**
	 * Format the String according to the object pass
	 * 
	 * @param aKey
	 *            String
	 * @param arg
	 *            Object array
	 * @return the key from the value, or 'value' if not found.
	 */
	public static String getFormattedString(String aKey, Object[] aArgs) {
		return MessageFormat.format(FRESOURCE_BUNDLE.getString(aKey), aArgs);
	}

}
