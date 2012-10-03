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
 * This interface is used to inject the dialog used to fill-in the Rule element details.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IRuleInputDialog {

	/**
	 * Method open.
	 * 
	 * @return int
	 */
	int open();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the id input string
	 */
	String getIdValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the title input string
	 */
	String getTitleValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the Class input string
	 */
	R4EDesignRuleClass getClassValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the Rank input string
	 */
	R4EDesignRuleRank getRankValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the description input string
	 */
	String getDescriptionValue();

}