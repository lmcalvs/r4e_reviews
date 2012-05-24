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
 * This interface is used to inject a dialog used to fill-in the Anomaly element details
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import java.util.Date;

import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IAnomalyInputDialog {

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
	 * @return the anomaly title input string
	 */
	String getAnomalyTitleValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the anomaly description input string
	 */
	String getAnomalyDescriptionValue();

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the R4EUIRule reference (if any)
	 */
	R4EUIRule getRuleReferenceValue();

	/**
	 * Method setClass_.
	 * 
	 * @param aClass
	 *            R4EDesignRuleClass
	 */
	void setClass_(R4EDesignRuleClass aClass);

	/**
	 * Method getClass_.
	 * 
	 * @return R4EDesignRuleClass
	 */
	R4EDesignRuleClass getClass_();

	/**
	 * Method setRank.
	 * 
	 * @param aRank
	 *            R4EDesignRuleRank
	 */
	void setRank(R4EDesignRuleRank aRank);

	/**
	 * Method getRank.
	 * 
	 * @return R4EDesignRuleRank
	 */
	R4EDesignRuleRank getRank();

	/**
	 * Method setDueDate.
	 * 
	 * @param aDate
	 *            Date
	 */
	void setDueDate(Date aDate);

	/**
	 * Method getDueDate.
	 * 
	 * @return Date
	 */
	Date getDueDate();

	/**
	 * Method setTitle.
	 * 
	 * @param aTitle
	 *            String
	 */
	void setTitle(String aTitle);

	/**
	 * Method setDescription.
	 * 
	 * @param aDescription
	 *            String
	 */
	void setDescription(String aDescription);

	/**
	 * Method setRuleID.
	 * 
	 * @param aId
	 *            String
	 */
	void setRuleID(String aId);

	/**
	 * Method getAssigned.
	 * 
	 * @return String
	 */
	String getAssigned();

	/**
	 * Method setAssigned.
	 * 
	 * @param aParticipant
	 *            - String
	 */
	void setAssigned(String aParticipant);
}