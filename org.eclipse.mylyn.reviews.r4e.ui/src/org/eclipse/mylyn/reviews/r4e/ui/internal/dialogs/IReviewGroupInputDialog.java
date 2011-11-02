/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This interface is used to inject a dialog used to fill-in the Review Group element 
 * details.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public interface IReviewGroupInputDialog {

	/**
	 * Method create.
	 */
	void create();

	/**
	 * Method open.
	 * 
	 * @return int
	 */
	int open();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the group name input string
	 */
	String getGroupNameValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the group folder input string
	 */
	String getGroupFolderValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the group description input string
	 */
	String getGroupDescriptionValue();

	/**
	 * Returns the strings typed into this input dialog.
	 * 
	 * @return the Available projects input strings
	 */
	String[] getAvailableProjectsValues();

	/**
	 * Returns the strings typed into this input dialog.
	 * 
	 * @return the Available components input strings
	 */
	String[] getAvailableComponentsValues();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the Default entry criteria input string
	 */
	String getDefaultEntryCriteriaValue();

	/**
	 * Returns the strings typed into this input dialog.
	 * 
	 * @return the Rule Set input strings
	 */
	String[] getRuleSetValues();

}