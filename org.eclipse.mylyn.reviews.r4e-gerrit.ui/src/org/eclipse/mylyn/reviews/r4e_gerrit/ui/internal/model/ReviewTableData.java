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
import java.util.List;

import org.eclipse.mylyn.reviews.r4e_gerrit.core.R4EGerritReviewSummary;
import org.eclipse.mylyn.tasks.core.TaskRepository;



/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class ReviewTableData {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	private List<R4EGerritReviewSummary> fReviewList = null;
	
	private TaskRepository fTaskRepo = null;
	
	private String fQuery = null;
	
	
	/**
	 * Create a new review entry to insert to the list of reviews
	 * 
	 * @param Object
	 */
	public void createReviewItem(List<R4EGerritReviewSummary> aList, String aQuery, TaskRepository aTaskRepo) {

		// Create the new object
		if (fQuery != aQuery) {
			fReviewList = aList;
			fTaskRepo = aTaskRepo;
			fQuery = aQuery;
			
		} else {
			//Need to reset the list, we just created a null entry
			reset();
		}
	}	
	
	/**
	 * Provide the list of review available for the table list
	 * @return ReviewTableListItem[]
	 */
	public R4EGerritReviewSummary[] getReviews () {
		if (fReviewList != null) {
			return fReviewList.toArray(new R4EGerritReviewSummary[] {} );
			
		}
		fReviewList = new ArrayList<R4EGerritReviewSummary>();
		return fReviewList.toArray(new R4EGerritReviewSummary[] {} );
	}
	
	/**
	 * Get the current TaskRepo populating the table list view
	 * @return TaskRepository
	 */
    public TaskRepository getCurrentTaskRepo () {
    	return  fTaskRepo; 
    }
    
    /**
     * Return the query information used to populate the review table
     * @return String
     */
    public String getQueryInfo () {
    	return fQuery;
    }

    	
	private void reset() {
		if (fReviewList != null) {
			fReviewList.clear();			
		}
	}

}
