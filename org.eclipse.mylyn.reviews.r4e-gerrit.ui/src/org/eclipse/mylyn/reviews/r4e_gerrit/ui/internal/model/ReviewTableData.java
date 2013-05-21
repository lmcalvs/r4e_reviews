/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the review table view information.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the table view information
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model;

import java.util.ArrayList;

import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;



/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class ReviewTableData {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private ArrayList<ReviewTableListItem> fReviewList = null;
	// A dummy list
	
//	public ReviewTableDefinition[] REVIEW_TABLE_INFO = ReviewTableDefinition.values();
	
	
	// //////////////////////////
	// Class to handle the content of each column
	// Can create a public class if needed
	public class ReviewTableListItem {

		private String[] reviewStr;
		//Debug Test BEGIN
		String[] st = ReviewTableDefinition.getColumnName();
		//END


		//List of fields available to build the list of reviews
	    private String fId = null;    
	    private String fSubject = null;  
	    private String fOwner = null;
	    private String fProject = null;
	    private String fBranch = null;    
	    private String fUpdated = null;
	    private String fCr = null;    
	    private String fIc = null;
	    private String fVerify = null;
	    
	    public ReviewTableListItem(String aID, String aSubject, String aOwner, String aProject, String aBranch, 
	    		String aUpdated, String aCr, String  aIc, String aVerify) {
	    	fId = null != aID ? aID : "";
	    	fSubject = null != aSubject ? aSubject : "";
	    	fOwner =  null != aOwner ? aOwner : "";
	    	fProject =  null != aProject ? aProject : "";        
	    	fBranch =  null != aBranch ? aBranch : "";
	    	fUpdated =  null != aUpdated ? aUpdated : "";
	    	fCr =  null != aCr ? aCr : "";
	    	fIc =  null != aIc ? aIc : "";
	    	fVerify =  null != aVerify ? aVerify : "";
	    }
	    
	    public ReviewTableListItem (Object aObj) {
	    	R4EGerritPlugin.Ftracer.traceInfo("ReviewTableListItem object: " + aObj );
//	    	if (aObj instanceof ???) {
//	    		
//	    	}
	    }

	    public String getId() {return fId;}
	    public String getSubject() {return fSubject;}
	    public String getOwner() {return fOwner;}
	    public String getProject() {return fProject;}    
	    public String getBranch() {return fBranch;}    
	    public String getUpdated() {return fUpdated;}
	    public String getCr() {return fCr;}    
	    public String getIc() {return fIc;}
	    public String getVerify() {return fVerify;}
	    
	    public Boolean getIdValue() { 
	    	return Boolean.valueOf(fId);
	    }

	    public void setIdValue(Boolean aSt) { 
	    	fId = String.valueOf(aSt);
	    }

	}

	/**
	 * Create a new review entry to insert to the list of reviews
	 * 
	 * @param Object
	 */
	public void createReviewItem(Object aObj) {

		// Create the new object
		ReviewTableListItem newItem = new ReviewTableListItem(
				aObj);
		if (null != newItem.getSubject()) {
			// /We have a valid review, so need to add it to the list
			if (fReviewList == null) {
				fReviewList = new ArrayList<ReviewTableListItem>();
			}
			fReviewList.add(newItem);
		}
	}
	
	/**
	 * Provide the list of review available for the table list
	 * @return ArrayList<ReviewTableListItem>
	 */
	public ArrayList<ReviewTableListItem> getReviewList () {
		return fReviewList;
	}

	/**
	 * Provide the list of review available for the table list
	 * @return ReviewTableListItem[]
	 */
	public ReviewTableListItem[] getReviews () {
		if (fReviewList != null) {
			return fReviewList.toArray(new ReviewTableListItem[] {} );
			
		}
		fReviewList = new ArrayList<ReviewTableListItem>();
		return fReviewList.toArray(new ReviewTableListItem[] {} );
	}

	String[][] testTable = { 
			{ "true", "Subject number 1", "HoHo 1" ,"proj 1", "branch 1", "updated 1", "1", "2", "1"}, 
			{ "true", "Subject number 2", "HoHo 2" ,"proj 2", "branch 2", "updated 2", "-2", "2", "2"},
			{ "", "Subject number 3", "HoHo 3" ,"proj 3", "branch 3", "updated 3", "2", "1", "-1"},
			{ "false", "Subject number 5", "HoHo 5" ,"proj 5", "branch 5", "updated 5", "3", "-1", "-2"},
			{ "false", "Subject number 4", "HoHo 4" ,"proj 4", "branch 4", "updated 4", "", "1", ""}};

	//Purpose to load the data into the table
	public void TestLoad () {
		if (fReviewList == null) {
			fReviewList = new ArrayList<ReviewTableListItem>();
		}

		int size = testTable.length;
		ReviewTableListItem item;
		for (int i = 0; i < size; i++) {
			 item = new ReviewTableListItem (testTable[i][0],
					testTable[i][1],
					testTable[i][2],
					testTable[i][3],
					testTable[i][4],
					testTable[i][5],
					testTable[i][6],
					testTable[i][7],
					testTable[i][8] );
			 fReviewList.add(item);
		}
	}

//    public String[] getAttributeValues() {
//        String[] values = {fId, fSubject, fOwner, fProject, fBranch, fUpdated, fCr, fIc, fVerify};
//        return values;
//    }
//    
     

//    public String [][] getTableAttributes () {
//    	
//    }

}
