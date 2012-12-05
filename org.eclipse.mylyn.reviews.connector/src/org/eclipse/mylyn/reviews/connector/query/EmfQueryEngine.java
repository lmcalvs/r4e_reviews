/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.connector.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;

public abstract class EmfQueryEngine {

	Map<String, QueryOperation> operationForId;

	private final TaskRepository repository;

	private final AbstractEmfConnector connector;

	public static final String QUERY_DELIMITER = "_"; //$NON-NLS-1$

	public static final String QUERY_VALUE = "query.value"; //$NON-NLS-1$

	public static final String QUERY_OPERATION = "query.operation"; //$NON-NLS-1$

	public static final String LIST_ITEM_SEPERATOR = ","; //$NON-NLS-1$

	public EmfQueryEngine(AbstractEmfConnector connector, TaskRepository repository) {
		this.connector = connector;
		this.repository = repository;
		operationForId = new HashMap<String, QueryOperation>();
		for (QueryOperation op : QueryOperation.ALL_OPERATIONS) {
			operationForId.put(op.id, op);
		}
	}

	public QueryClause[] getClauses(IRepositoryQuery query) throws QueryException {
		Map<String, QueryClause> queriesById = new HashMap<String, QueryClause>();
		for (Entry<String, String> entry : query.getAttributes().entrySet()) {
			String value = entry.getValue();
			String[] keyParts = entry.getKey().split(QUERY_DELIMITER);
			if (keyParts.length == 2) {
				String attributeId = keyParts[0];
				String queryId = keyParts[1];
				QueryClause clause = queriesById.get(attributeId);
				if (clause == null) {
					clause = new QueryClause();
					EStructuralFeature feature = connector.getSchema().getFeature(attributeId);
					clause.setFeature(feature);
					queriesById.put(attributeId, clause);
				}
				if (queryId.equals(QUERY_OPERATION)) {
					QueryOperation operation = operationForId.get(value);
					if (operation == null) {
						throw new QueryException("Unknown operation: " + value); //$NON-NLS-1$
					}
					clause.setOperation(operation);
				} else if (queryId.equals(QUERY_VALUE)) {
					clause.setValue(value);
				} else {
					throw new QueryException("Unknown query identifier: " + queryId); //$NON-NLS-1$
				}
			}
		}
		return queriesById.values().toArray(new QueryClause[] {});
	}

	public QueryClause[] getNonEmptyClauses(IRepositoryQuery query) throws QueryException {
		QueryClause[] clauses = getClauses(query);
		Collection<QueryClause> results = new ArrayList<QueryClause>();
		for (QueryClause queryClause : clauses) {
			if (!queryClause.getValue().equals("")) { //$NON-NLS-1$
				results.add(queryClause);
			}
		}
		return results.toArray(new QueryClause[] {});
	}

	public AbstractEmfConnector getConnector() {
		return connector;
	}

	public TaskRepository getRepository() {
		return repository;
	}

	public abstract List<EObject> performQuery(IRepositoryQuery query, IProgressMonitor monitor) throws QueryException,
			CoreException;
}
