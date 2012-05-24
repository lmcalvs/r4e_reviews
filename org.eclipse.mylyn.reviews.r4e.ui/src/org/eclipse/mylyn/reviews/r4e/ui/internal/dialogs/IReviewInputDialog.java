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
 * This interface is used to inject the dialog used to fill-in the Review element details
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IReviewInputDialog {

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
	 * Returns the text area.
	 * 
	 * @return the review name text area
	 */
	R4EReviewType getReviewTypeValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the review name input string
	 */
	String getReviewNameValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the review description input string
	 */
	String getReviewDescriptionValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the project input string
	 */
	String getProjectValue();

	/**
	 * Returns the strings typed into this input dialog.
	 * 
	 * @return the components input strings
	 */
	String[] getComponentsValues();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the entry criteria input string
	 */
	String getEntryCriteriaValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the objectives input string
	 */
	String getObjectivesValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the reference material input string
	 */
	String getReferenceMaterialValue();

}