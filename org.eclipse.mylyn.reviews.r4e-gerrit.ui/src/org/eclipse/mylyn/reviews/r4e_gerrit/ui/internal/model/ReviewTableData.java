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


/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class ReviewTableData {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	//Definition of the review table list {name, width of the column, Resizeable, Moveable}
	public enum ReviewTableDefinition {
		//           Name		Width	Resize	Moveable
		ID 			("ID",		 25, 	false, 	true),
		SUBJECT 	("Subject", 200, 	true, 	true),
		OWNER 		("Owner", 	100, 	true, 	true),
		PROJECT 	("Project", 100, 	true, 	true),
		BRANCH 		("Branch",	100, 	true, 	true),
		UPDATED 	("Updated", 100, 	true, 	true),
		CR 			("CR", 		27, 	false, 	true),
		IC 			("IC", 		27, 	false, 	true),
		VERIFY 		("V", 		27, 	false, 	true);
		
	    private String fHeader;    
	    private int fwidth;  
	    private Boolean fResize;
	    private Boolean fMoveable;
	    
	    private ReviewTableDefinition (String aName, int aWidth, Boolean aResize, Boolean aMove) {
	    	fHeader = aName;
	    	fwidth = aWidth;
	    	fResize = aResize;
	    	fMoveable = aMove;
	    }
	    
	    public String getName () {
	    	return fHeader;
	    }
	    
	    public int getWidth () {
	    	return fwidth;
	    }
	    
	    public Boolean getResize() {
	    	return fResize;
	    }
	    
	    public Boolean getMoveable () {
	    	return fMoveable;
	    }

	}
	
//	public ReviewTableDefinition[] REVIEW_TABLE_INFO = ReviewTableDefinition.values();
	
	

    private String fId;    
    private String fSubject;  
    private String fOwner;
    private String fProject;
    private String fBranch;    
    private String fUpdated;
    private String fCr;    
    private String fIc;
    private String fVerify;
    
    public ReviewTableData(String aID, String aSubject, String aOwner, String aProject, String aBranch, 
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
    
    public String getId() {return fId;}
    public String getSubject() {return fSubject;}
    public String getOwner() {return fOwner;}
    public String getProject() {return fProject;}    
    public String getBranch() {return fBranch;}    
    public String getUpdated() {return fUpdated;}
    public String getCr() {return fCr;}    
    public String getIc() {return fIc;}
    public String getVerify() {return fVerify;}
    
    public String[] getAttributeValues() {
        String[] values = {fId, fSubject, fOwner, fProject, fBranch, fUpdated, fCr, fIc, fVerify};
        return values;
    }
    
     

//    public String [][] getTableAttributes () {
//    	
//    }

}
