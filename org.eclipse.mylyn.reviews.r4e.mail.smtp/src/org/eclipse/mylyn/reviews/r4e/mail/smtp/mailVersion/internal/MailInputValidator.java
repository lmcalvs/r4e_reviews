/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson AB - Mail data information structure
 * 
 */
package org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class MailInputValidator implements IInputValidator {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method isEmpty.
	 * 
	 * @param aNewText
	 *            String
	 * @return String
	 */
	public String isValid(String aNewText) {
		if (null == aNewText || 0 == aNewText.length())
			return SMTPHostString.getString("no_input_given");
		return null;
	}

}
