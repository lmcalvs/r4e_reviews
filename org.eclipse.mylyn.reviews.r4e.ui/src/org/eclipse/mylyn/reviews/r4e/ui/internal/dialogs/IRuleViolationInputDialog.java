/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This interface is used to inject the dialog used to fill-in the Rule Violation element details.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IRuleViolationInputDialog {

	/**
	 * Method open.
	 * 
	 * @return int
	 */
	int open();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the version input string
	 */
	String getNameValue();

}