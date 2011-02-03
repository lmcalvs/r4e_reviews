// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the dialog used to validate that a String received
 * is not null or empty
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class EmptyInputValidator implements IInputValidator {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field EMPTY_VALIDATION_ERROR_MESSAGE.
	 * (value is ""No input given"")
	 */
	private static final String EMPTY_VALIDATION_ERROR_MESSAGE = "No input given";
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method isValid.
	 * @param newText String
	 * @return String
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(String)
	 */
	public String isValid(String newText) {
		if (null == newText || 0 == newText.length()) return EMPTY_VALIDATION_ERROR_MESSAGE;
		return null;
	}

}