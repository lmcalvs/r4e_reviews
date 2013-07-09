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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.mylyn.internal.gerrit.core.GerritConnector;
import org.eclipse.mylyn.internal.gerrit.core.GerritCorePlugin;
import org.eclipse.mylyn.internal.gerrit.core.GerritQuery;
import org.eclipse.mylyn.internal.tasks.core.RepositoryQuery;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskData;

/**
 * Manages Gerrit queries 
 *  
 * @author Francois Chouinard
 * @version 0.1
 */
@SuppressWarnings("restriction")
public class R4EGerritQueryUtils {

    //-------------------------------------------------------------------------
    // Static operations
    //-------------------------------------------------------------------------

    /**
     * Returns the list of reviews matching the supplied query from the gerrit
     * repository. The review structures are minimally filled.
     * 
     * @param repository the repository
     * @param query the query
     * @return the list of reviews matching the query
     */
    public static R4EGerritReviewData[] getReviewList(TaskRepository repository, String queryType) throws R4EQueryException {

        // Format the query
        IRepositoryQuery query = new RepositoryQuery(repository.getConnectorKind(), "query"); //$NON-NLS-1$
        query.setAttribute(GerritQuery.TYPE, queryType);

        // Execute the query
        GerritConnector connector = GerritCorePlugin.getDefault().getConnector();
        R4EGerritTaskDataCollector resultCollector = new R4EGerritTaskDataCollector();
        IStatus status = connector.performQuery(repository, query, resultCollector, null, new NullProgressMonitor());
        if (!status.isOK()) {
            String msg = "Unable to read the Gerrit server.";
            throw new R4EQueryException(status, msg);
        }        

        // Extract the result
        List<R4EGerritReviewData> reviews = new ArrayList<R4EGerritReviewData>();
        List<TaskData> tasksData = resultCollector.getResults();
        for (TaskData taskData : tasksData) {
            R4EGerritReviewData review = new R4EGerritReviewData(taskData);
            if (review.getAttribute(R4EGerritReviewData.DATE_COMPLETION) == null) {
                reviews.add(review);
            }
        }
        return reviews.toArray(new R4EGerritReviewData[0]);
    }

    /**
     * Updates the review data with detailed information from the repository
     * 
     * @param repository the repository
     * @param reviewData the original (partial) review data
     */
    public static void getReviewDetails(TaskRepository repository, R4EGerritReviewData reviewData) {
        GerritConnector connector = GerritCorePlugin.getDefault().getConnector();
        try {
            TaskData taskData = connector.getTaskData(repository, reviewData.getAttribute(R4EGerritReviewData.TASK_ID), new NullProgressMonitor());
            reviewData.setAttribute(R4EGerritReviewData.OWNER,   getValue(taskData.getRoot().getAttribute(R4EGerritReviewData.OWNER)));
            reviewData.setAttribute(R4EGerritReviewData.PROJECT, getValue(taskData.getRoot().getAttribute(R4EGerritReviewData.PROJECT)));
            reviewData.setAttribute(R4EGerritReviewData.BRANCH,  getValue(taskData.getRoot().getAttribute(R4EGerritReviewData.BRANCH)));
        } catch (CoreException e) {
            // TODO Log an error
            e.printStackTrace();
        }
    }

    /*
     * Extract the first value from the specified task attributes list.
     * 
     * @param taskAttribute
     * @return the first value in the list (if any)
     */
    private static String getValue(TaskAttribute taskAttribute) {
        if (taskAttribute != null) {
            List<String> values = taskAttribute.getValues();
            if (values.size() > 0) {
                return values.get(0);
            }
        }
        return null;
    }

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

//    private TaskRepository fGerritRepository;
//    private String fGerritQueryType;
//    private R4EGerritTaskDataCollector fResultCollector;
//
//    private static List<R4EGerritReviewData> fReviews = new ArrayList<R4EGerritReviewData>();

    //-------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------

    /**
     * @param repository the repository
     * @param queryType the query type
     */
//    public R4EGerritQueryUtils(final TaskRepository repository, String queryType) {
//        fGerritRepository = repository;
//        fGerritQueryType  = queryType;
//        fResultCollector  = new R4EGerritTaskDataCollector();
//    }

    //-------------------------------------------------------------------------
    // Operations
    //-------------------------------------------------------------------------

    /**
     * 
     */
//    public IStatus performQuery() {
//        IRepositoryQuery query = new RepositoryQuery(fGerritRepository.getConnectorKind(), "query"); //$NON-NLS-1$
//        query.setAttribute(GerritQuery.TYPE, fGerritQueryType);
//
//        GerritConnector connector = GerritCorePlugin.getDefault().getConnector();
//        IStatus status = connector.performQuery(fGerritRepository, query, fResultCollector, null, new NullProgressMonitor());
//        return status;
//    }

    //-------------------------------------------------------------------------
    // Getters 
    //-------------------------------------------------------------------------

    /**
     * @return the list of Gerrit reviews
     */
//    public List<R4EGerritReviewData> getQueryResult() {
//        List<R4EGerritReviewData> result = new ArrayList<R4EGerritReviewData>();
//        List<TaskData> tasksData = fResultCollector.getResults();
//        for (TaskData taskData : tasksData) {
//            GerritConnector connector = GerritCorePlugin.getDefault().getConnector();
//            try {
//                TaskData gerritTaskData = connector.getTaskData(fGerritRepository, taskData.getTaskId(), new NullProgressMonitor());
//                R4EGerritReviewData review = new R4EGerritReviewData(gerritTaskData);
//                result.add(review);
//            } catch (CoreException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//    }

    //-------------------------------------------------------------------------
    // Object
    //-------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
//    @Override
//    public String toString() {
//        return null;
//    }

}
