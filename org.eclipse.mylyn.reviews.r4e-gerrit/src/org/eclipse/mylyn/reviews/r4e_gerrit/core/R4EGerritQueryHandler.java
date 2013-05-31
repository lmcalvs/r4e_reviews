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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.mylyn.internal.gerrit.core.GerritConnector;
import org.eclipse.mylyn.internal.gerrit.core.GerritCorePlugin;
import org.eclipse.mylyn.internal.gerrit.core.GerritQuery;
import org.eclipse.mylyn.internal.tasks.core.RepositoryQuery;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskData;

/**
 * Massages Gerrit query results into Gerrit Review Summaries (dashboard entries) 
 *  
 * @author Francois Chouinard
 * @version 0.1
 */
@SuppressWarnings("restriction")
public class R4EGerritQueryHandler {

    //-------------------------------------------------------------------------
    // Attributes
    //-------------------------------------------------------------------------

    private TaskRepository fGerritRepository;
    private String fGerritQueryType;
    private R4EGerritTaskDataCollector fResultCollector;

    //-------------------------------------------------------------------------
    // Constructor
    //-------------------------------------------------------------------------

    /**
     * @param result the Gerrit query result
     */
    public R4EGerritQueryHandler(final TaskRepository repository, String query) {
        fGerritRepository = repository;
        fGerritQueryType  = query;
        fResultCollector = new R4EGerritTaskDataCollector();
    }

    //-------------------------------------------------------------------------
    // Operations
    //-------------------------------------------------------------------------

    /**
     * 
     */
    public IStatus performQuery() {
        IRepositoryQuery query = new RepositoryQuery(fGerritRepository.getConnectorKind(), "query"); //$NON-NLS-1$
        query.setAttribute(GerritQuery.TYPE, fGerritQueryType);

        GerritConnector connector = GerritCorePlugin.getDefault().getConnector();
        IStatus status = connector.performQuery(fGerritRepository, query, fResultCollector, null, new NullProgressMonitor());
        return status;
    }

    //-------------------------------------------------------------------------
    // Getters 
    //-------------------------------------------------------------------------

    /**
     * 
     */
    public List<R4EGerritReviewSummary> getQueryResult() {
        List<R4EGerritReviewSummary> result = new ArrayList<R4EGerritReviewSummary>();
        List<TaskData> tasksData = fResultCollector.getResults();
        for (TaskData taskData : tasksData) {
            GerritConnector connector = GerritCorePlugin.getDefault().getConnector();
            try {
                TaskData gerritTaskData = connector.getTaskData(fGerritRepository, taskData.getTaskId(), new NullProgressMonitor());
                R4EGerritReviewSummary review = new R4EGerritReviewSummary(gerritTaskData);
                result.add(review);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //-------------------------------------------------------------------------
    // Object
    //-------------------------------------------------------------------------

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return null;
    }

}
