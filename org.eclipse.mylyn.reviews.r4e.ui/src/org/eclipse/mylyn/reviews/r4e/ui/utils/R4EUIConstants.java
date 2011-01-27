// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class defines various constants used in the R4E UI plugin
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.utils;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIConstants { // $codepro.audit.disable convertClassToInterface

	/**
	 * Field NO_OFFSET.
	 * (value is 0)
	 */
	public static final int NO_OFFSET = 0;
	
	/**
	 * Field LINE_OFFSET.
	 * (value is 1)
	 */
	public static final int LINE_OFFSET = 1;
	
	/**
	 * Field REVIEW_GROUP_PATHS_LENGTH.
	 * (value is 128)
	 */
	public static final int REVIEW_GROUP_PATHS_LENGTH = 128;
	/**
	 * Field TOOLTIP_CONTENTS_LENGTH.
	 * (value is 25)
	 */
	public static final int TOOLTIP_CONTENTS_LENGTH = 25;

	/**
	 * Field INVALID_VALUE.
	 * (value is -1)
	 */
	public static final int INVALID_VALUE = -1;
	
	/**
	 * Field SELECTIONS_LABEL_NAME.
	 * (value is ""Selections"")
	 */
	public static final String SELECTIONS_LABEL_NAME = "Selections";
	
	/**
	 * Field ANOMALIES_LABEL_NAME.
	 * (value is ""Anomalies"")
	 */
	public static final String ANOMALIES_LABEL_NAME = "Anomalies";
	
	/**
	 * Field GLOBAL_ANOMALIES_LABEL_NAME.
	 * (value is ""Global Anomalies"")
	 */
	public static final String  GLOBAL_ANOMALIES_LABEL_NAME = "Global Anomalies";
	/**
	 * Field PARTICIPANTS_LABEL_NAME.
	 * (value is ""Participants"")
	 */
	public static final String PARTICIPANTS_LABEL_NAME = "Participants";
	
	/**
	 * Field LINE_TAG.
	 * (value is ""Line "")
	 */
	public static final String LINE_TAG = "Line ";
	
	/**
	 * Field LINES_TAG.
	 * (value is ""Lines "")
	 */
	public static final String LINES_TAG = "Lines ";
	
	/**
	 * Field DEFAULT_LINE_TAG_LENGTH.
	 * (value is 28)
	 */
	public static final int DEFAULT_LINE_TAG_LENGTH = 28;
	
	
	//Tooltips
	
	/**
	 * Field TOOLTIP_DISPLAY_DELAY.
	 * (value is 250)
	 */
	public static final int TOOLTIP_DISPLAY_DELAY = 250; //milliseconds
	
	/**
	 * Field TOOLTIP_DISPLAY_TIME.
	 * (value is 10000)
	 */
	public static final int TOOLTIP_DISPLAY_TIME = 10000; //milliseconds
	
	/**
	 * Field TOOLTIP_DISPLAY_OFFSET_X.
	 * (value is 5)
	 */
	public static final int TOOLTIP_DISPLAY_OFFSET_X = 5;   //pixels
	
	/**
	 * Field TOOLTIP_DISPLAY_OFFSET_Y.
	 * (value is 5)
	 */
	public static final int TOOLTIP_DISPLAY_OFFSET_Y = 5;   //pixels
	
	/**
	 * Field DIALOG_YES.
	 * (value is 0)
	 */
	public static final int DIALOG_YES = 0;
	/**
	 * Field DIALOG_NO.
	 * (value is 1)
	 */
	public static final int DIALOG_NO = 1;
	
	
	//Icons
	
	/**
	 * Field fReviewGroupFile.
	 * (value is ""icons/groups.gif"")
	 */
	public static final String REVIEW_GROUP_ICON_FILE = "icons/groups.gif";
	
	
	//Properties
	
	/**
	 * Field TABBED_PROPERTY_LABEL_WIDTH.
	 * (value is 105)
	 */
	public static final int TABBED_PROPERTY_LABEL_WIDTH = 105;
	/**
	 * Field NAME_LABEL.
	 * (value is ""Name: "")
	 */
	public static final String NAME_LABEL = "Name: ";
	/**
	 * Field FOLDER_LABEL.
	 * (value is ""Folder: "")
	 */
	public static final String FOLDER_LABEL = "Folder: ";
	/**
	 * Field DESCRIPTION_LABEL.
	 * (value is ""Description: "")
	 */
	public static final String DESCRIPTION_LABEL = "Description: ";
	/**
	 * Field CREATION_DATE_LABEL.
	 * (value is ""Creation date: "")
	 */
	public static final String CREATION_DATE_LABEL = "Creation date: ";
	/**
	 * Field AUTHOR_LABEL.
	 * (value is ""Added by: "")
	 */
	public static final String AUTHOR_LABEL = "Added by: ";
	
	/**
	 * Field ID_LABEL.
	 * (value is ""Id: "")
	 */
	public static final String ID_LABEL = "Id: ";
	/**
	 * Field NUM_COMMENTS_LABEL.
	 * (value is ""Comments: "")
	 */
	public static final String NUM_COMMENTS_LABEL = "Comments: ";
	
	/**
	 * Field NO_VERSION_PROPERTY_MESSAGE.
	 * (value is ""(Not present)"")
	 */
	public static final String NO_VERSION_PROPERTY_MESSAGE = "(Not present)";
	/**
	 * Field PROJECT_ID_LABEL.
	 * (value is ""Project Id: "")
	 */
	public static final String PROJECT_ID_LABEL = "Project Id: ";
	/**
	 * Field PATH_LABEL.
	 * (value is ""Path: "")
	 */
	public static final String PATH_LABEL = "Path: ";
	/**
	 * Field VERSION_LABEL.
	 * (value is ""Version: "")
	 */
	public static final String VERSION_LABEL = "Version: ";
	/**
	 * Field POSITION_LABEL.
	 * (value is ""Position: "")
	 */
	public static final String POSITION_LABEL = "Position: ";
	/**
	 * Field TITLE_LABEL.
	 * (value is ""Title: "")
	 */
	public static final String TITLE_LABEL = "Title: ";
	/**
	 * Field GLOBAL_ANOMALY_PROPERTY_VALUE.
	 * (value is ""(Global review anomaly)"")
	 */
	public static final String GLOBAL_ANOMALY_PROPERTY_VALUE = "(Global review anomaly)";
	
	/**
	 * Field FILE_NOT_IN_VERSION_CONTROL_MSG.
	 * (value is ""(Not version controlled)"")
	 */
	public static final String FILE_NOT_IN_VERSION_CONTROL_MSG = "(Not version controlled)";
	
	//Review item types
	/**
	 * Field REVIEW_ITEM_TYPE_RESOURCE.
	 * (value is 0)
	 */
	public static final int REVIEW_ITEM_TYPE_RESOURCE = 0;
	/**
	 * Field REVIEW_ITEM_TYPE_COMMIT.
	 * (value is 1)
	 */
	public static final int REVIEW_ITEM_TYPE_COMMIT = 1;
	
}
