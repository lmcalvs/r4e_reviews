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
	
}
