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
 * This class holds R4E preferences constants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.preferences;

/**
 * Constant definitions for plug-in preferences
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class PreferenceConstants { // $codepro.audit.disable convertClassToInterface

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * The preferences description text
	 */
	public static final String P_DESC = "R4E Global Preferences";
	
	/**
	 * The user ID preference name
	 */
	public static final String P_USER_ID = "userIdPreference";	
	
	/**
	 * The user ID main label text
	 */
	public static final String P_USER_ID_LABEL =  "User ID:";
	
	/**
	 * The file path preference name
	 */
	public static final String P_FILE_PATH = "filePathPreference";	
	
	/**
	 * The FilePathEditor main label text
	 */
	public static final String P_FILE_PATH_LABEL =  "Review Group files:";
	
	/**
	 * The review group file extension
	 */
	public static final String P_REVIEW_GROUP_FILE_EXT = "*.xrer";
	
	/**
	 * Field P_SHOW_DISABLED.
	 * (value is ""showDisabled"")
	 */
	public static final String P_SHOW_DISABLED = "showDisabled";
	
	/**
	 * Field P_REVIEWS_ONLY_FILTER.
	 * (value is ""reviewsOnlyFilter"")
	 */
	public static final String P_REVIEWS_ONLY_FILTER = "reviewsOnlyFilter";
	
	/**
	 * Field P_REVIEWS_CURRENT_FILTER.
	 * (value is ""reviewsCurrentFilter"")
	 */
	public static final String P_REVIEWS_CURRENT_FILTER = "reviewsCurrentFilter";
	
	/**
	 * Field P_REVIEWS_MY_FILTER.
	 * (value is ""reviewsMyFilter"")
	 */
	public static final String P_REVIEWS_MY_FILTER = "reviewsMyFilter";
	
	/**
	 * Field P_PARTICIPANT_FILTER.
	 * (value is ""participantFilter"")
	 */
	public static final String P_PARTICIPANT_FILTER = "participantFilter";
	
	/**
	 * Field P_ANOMALIES_FILTER.
	 * (value is ""anomaliesFilter"")
	 */
	public static final String P_ANOMALIES_FILTER = "anomaliesFilter";
	
	/**
	 * Field P_REVIEWED_ITEMS_FILTER.
	 * (value is ""reviewItemsFilter"")
	 */
	public static final String P_REVIEWED_ITEMS_FILTER = "reviewItemsFilter";
}
