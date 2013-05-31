/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Francois Chouinard - Initial implementation
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e_gerrit.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.mylyn.internal.gerrit.core.GerritQueryResultSchema;
import org.eclipse.mylyn.internal.gerrit.core.GerritTaskSchema;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;

/**
 * A Gerrit dashboard entry as per the Gerrit Web UI. Used to populate the
 * Gerrit Dashboard view.
 *  
 * @author Francois Chouinard
 * @version 0.1
 */
@SuppressWarnings("restriction")
public class R4EGerritReviewSummary {

    //-------------------------------------------------------------------------
    // Constants
    //-------------------------------------------------------------------------

    /**
     * Mylyn Task ID
     */
    public static final String TASK_ID = "r4e.mylyn.task.id"; 

    /**
     * Gerrit Review shortened Change-Id
     */
    public static final String SHORT_CHANGE_ID = TaskAttribute.TASK_KEY;

    /**
     * Gerrit Review subject
     */
    public static final String SUBJECT = TaskAttribute.SUMMARY;

    /**
     * Gerrit Review project
     */
    public static final String PROJECT = TaskAttribute.PRODUCT; 

    /**
     * Gerrit Review Change-Id
     */
    public static final String CHANGE_ID = GerritQueryResultSchema.getDefault().CHANGE_ID.getKey();

    /**
     * Gerrit Review owner
     */
    public static final String OWNER = GerritTaskSchema.getDefault().OWNER.getKey();

    /**
     * Gerrit Review branch
     */
    public static final String BRANCH = GerritTaskSchema.getDefault().BRANCH.getKey();

    /**
     * Gerrit Review creation date
     */
    public static final String DATE_CREATION = TaskAttribute.DATE_CREATION;

    /**
     * Gerrit Review last modification date
     */
    public static final String DATE_MODIFICATION = TaskAttribute.DATE_MODIFICATION;

    /**
     * Gerrit Review completion date
     */
    public static final String DATE_COMPLETION = TaskAttribute.DATE_COMPLETION;

    /**
     * Gerrit Review flags
     */
    public static final String REVIEW_FLAG_STAR = "org.eclipse.gerrit.Starred";
    public static final String REVIEW_FLAG_CI   = "org.eclipse.gerrit.Checked";
    public static final String REVIEW_FLAG_CR   = "org.eclipse.gerrit.Reviewed";
    public static final String REVIEW_FLAG_V    = "org.eclipse.gerrit.Verified";

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    /*
     * Map of review attributes 
     */
    private Map<String, String> fReviewAttributes;

    //-------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------

    /**
     * Construct an R4EGerritReviewSumamry from a Gerrit query result
     * 
     * @param result the Gerrit query result
     */
    public R4EGerritReviewSummary(final TaskData result) {
        TaskAttribute root = result.getRoot();
        Map<String, TaskAttribute> attributes = root.getAttributes();

        fReviewAttributes = new HashMap<String, String>();
        
        fReviewAttributes.put(TASK_ID,           result.getTaskId());
        fReviewAttributes.put(SHORT_CHANGE_ID,   getValue(attributes.get(SHORT_CHANGE_ID)));
        fReviewAttributes.put(CHANGE_ID,         getValue(attributes.get(CHANGE_ID)));
        fReviewAttributes.put(SUBJECT,           getValue(attributes.get(SUBJECT)));
        fReviewAttributes.put(OWNER,             getValue(attributes.get(OWNER)));
        fReviewAttributes.put(PROJECT,           getValue(attributes.get(PROJECT)));
        fReviewAttributes.put(BRANCH,            getValue(attributes.get(BRANCH)));

        fReviewAttributes.put(DATE_CREATION,     getValue(attributes.get(DATE_CREATION)));
        fReviewAttributes.put(DATE_MODIFICATION, getValue(attributes.get(DATE_MODIFICATION)));
        fReviewAttributes.put(DATE_COMPLETION,   getValue(attributes.get(DATE_COMPLETION)));

        fReviewAttributes.put(REVIEW_FLAG_STAR,  getValue(attributes.get(REVIEW_FLAG_STAR)));
        fReviewAttributes.put(REVIEW_FLAG_CI,    getValue(attributes.get(REVIEW_FLAG_CI)));
        fReviewAttributes.put(REVIEW_FLAG_CR,    getValue(attributes.get(REVIEW_FLAG_CR)));
        fReviewAttributes.put(REVIEW_FLAG_V,     getValue(attributes.get(REVIEW_FLAG_V)));
    }

    /*
     * Extract the first attribute value, if present
     * 
     * @param taskAttribute
     * @return the first value (null if absent)
     */
    private String getValue(TaskAttribute taskAttribute) {
        if (taskAttribute != null) {
            List<String> values = taskAttribute.getValues();
            if (values.size() > 0) {
                return values.get(0);
            }
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Getters
    //-------------------------------------------------------------------------

    /**
     * @return the requested Gerrit Review attribute
     */
    public String getAttribute(String key) {
        return fReviewAttributes.get(key);
    }

    /**
     * @return the requested Gerrit Review attribute as a date
     */
    public Date getAttributeAsDate(String key) {
        return new Date(Long.parseLong(fReviewAttributes.get(key)));
    }

    //-------------------------------------------------------------------------
    // Object
    //-------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("TaskID  = ").append(getAttribute(R4EGerritReviewSummary.TASK_ID)).append("\n");
        buffer.append("ShortID = ").append(getAttribute(R4EGerritReviewSummary.SHORT_CHANGE_ID)).append("\n");
        buffer.append("ChangeID= ").append(getAttribute(R4EGerritReviewSummary.CHANGE_ID)).append("\n");
        buffer.append("Subject = ").append(getAttribute(R4EGerritReviewSummary.SUBJECT)).append("\n");
        buffer.append("Owner   = ").append(getAttribute(R4EGerritReviewSummary.OWNER)).append("\n");
        buffer.append("Project = ").append(getAttribute(R4EGerritReviewSummary.PROJECT)).append("\n");
        buffer.append("Branch  = ").append(getAttribute(R4EGerritReviewSummary.BRANCH)).append("\n");
        buffer.append("Updated = ").append(getAttributeAsDate(R4EGerritReviewSummary.DATE_MODIFICATION)).append("\n");
        buffer.append("STAR = ").append(getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_STAR))
              .append(", CR = ").append(getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CR))
              .append(", IC = ").append(getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_CI))
              .append(", V  = ").append(getAttribute(R4EGerritReviewSummary.REVIEW_FLAG_V)).append("\n");
        return buffer.toString();
    }

}
