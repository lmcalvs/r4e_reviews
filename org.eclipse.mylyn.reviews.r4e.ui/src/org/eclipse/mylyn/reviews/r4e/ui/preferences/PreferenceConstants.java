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
	 * The user Email preference name
	 */
	public static final String P_USER_EMAIL = "userEmailPreference";	
	
	/**
	 * The user ID main label text
	 */
	public static final String P_USER_ID_LABEL =  "User ID:";
	
	/**
	 * The user Email main label text
	 */
	public static final String P_USER_EMAIL_LABEL =  "User Email:";
	
	/**
	 * The group file path preference name
	 */
	public static final String P_GROUP_FILE_PATH = "groupFilePathPreference";	
	
	/**
	 * The group FilePathEditor main label text
	 */
	public static final String P_GROUP_FILE_PATH_LABEL =  "Review Group Files:";
	
	/**
	 * The review group file extension
	 */
	public static final String P_GROUP_FILE_EXT = "*_group_root.xrer";
	
	/**
	 * The rule set file path preference name
	 */
	public static final String P_RULE_SET_FILE_PATH = "ruleSetFilePathPreference";	
	
	/**
	 * The rule set file path preference name
	 */
	public static final String P_RULE_SET_FILE_PATH_LABEL = "Rule Set Files:";	

	/**
	 * The rule set file extension
	 */
	public static final String P_RULE_SET_FILE_EXT = "*_rule_set.xrer";
	
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
	 * Field P_ANOMALIES_ALL_FILTER.
	 * (value is ""anomaliesFilter"")
	 */
	public static final String P_ANOMALIES_ALL_FILTER = "anomaliesFilter";
	
	/**
	 * Field P_ANOMALIES_MY_FILTER.
	 * (value is ""anomaliesMyFilter"")
	 */
	public static final String P_ANOMALIES_MY_FILTER = "anomaliesMyFilter";
	
	/**
	 * Field P_REVIEWED_ITEMS_FILTER.
	 * (value is ""reviewItemsFilter"")
	 */
	public static final String P_REVIEWED_ITEMS_FILTER = "reviewItemsFilter";
	
	/**
	 * Field P_HIDE_RULE_SETS_FILTER.
	 * (value is ""hideRuleSetsFilter"")
	 */
	public static final String P_HIDE_RULE_SETS_FILTER = "hideRuleSetsFilter";
	
}
