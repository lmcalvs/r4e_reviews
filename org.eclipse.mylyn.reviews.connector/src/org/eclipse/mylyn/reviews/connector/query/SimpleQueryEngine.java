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
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.EmfAttributeMapper;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;

/**
 * Just a brain-dead emf query engine to manage simple cases. TODO Implement a real query engine based on OCL, Emf
 * QueryInc, CDO, etc..
 * 
 * @author milesparker
 */
public class SimpleQueryEngine extends EmfQueryEngine {

	public SimpleQueryEngine(AbstractEmfConnector connector, TaskRepository repository) {
		super(connector, repository);
	}

	public boolean isSatisfied(QueryOperation operation, String tested, String searchString) {
		if (operation == QueryOperation.SUBSTRING) {
			return StringUtils.contains(tested, searchString);
		}
		return false;
	}

	@Override
	public List<EObject> performQuery(IRepositoryQuery query, IProgressMonitor monitor) throws QueryException,
			CoreException {
		List<EObject> results = new ArrayList<EObject>();
		EmfClient client = getConnector().getClient(getRepository());
		client.open();
		@SuppressWarnings("unchecked")
		List<EObject> all = (List<EObject>) client.getRootContainer().eGet(getConnector().getContainmentReference());
		monitor.beginTask("Querying repository", all.size()); //$NON-NLS-1$
		//TODO O(nc) (where c is a limited size constant value)
		QueryClause[] clauses = getNonEmptyClauses(query);
		//For now, just assume that we want everything
		if (clauses.length == 0) {
			return all;
		}
		for (Object object : all) {
			if (object instanceof EObject) {
				EObject candidate = (EObject) object;
				boolean allSatisfied = true;
				for (QueryClause clause : clauses) {
					Object candidateValue = candidate.eGet(clause.getFeature());
					if (candidate.eClass().getEAllStructuralFeatures().contains(clause.getFeature())) {
						if (!isSatisifed(clause.getOperation(), candidateValue, clause.getValue())) {
							allSatisfied = false;
							break;
						}
					}
				}
				if (allSatisfied) {
					results.add(candidate);
				}
			}
		}
		monitor.done();

		return results;
	}

	protected boolean isSatisifed(QueryOperation operation, Object candidateValue, String testValue) {
		if (operation == QueryOperation.MATCH_ITEMS) {
			if (candidateValue instanceof Enumerator) {
				Enumerator literal = (Enumerator) candidateValue;
				String[] items = StringUtils.split(testValue, EmfQueryEngine.LIST_ITEM_SEPERATOR);
				for (String item : items) {
					if (item.equals(literal.getName())) {
						return true;
					}
				}
			}
			return false;
		}
		if (operation == QueryOperation.EXACT) {
			return candidateValue.equals(testValue);
		}
		if (operation == QueryOperation.NOT_EQUALS) {
			return !candidateValue.equals(testValue);
		}
		if (candidateValue instanceof String) {
			String stringValue = (String) candidateValue;
			if (operation == QueryOperation.SUBSTRING) {
				return stringValue.indexOf(testValue) > 0;
			}
			if (operation == QueryOperation.REGEXP) {
				Pattern pattern = Pattern.compile(testValue);
				return pattern.matcher(stringValue).matches();
			}
			if (operation == QueryOperation.NOT_REGEXP) {
				Pattern pattern = Pattern.compile(testValue);
				return !pattern.matcher(stringValue).matches();
			}
		}
		if (candidateValue instanceof Date) {
			EmfAttributeMapper attributeMapper = getConnector().getEmfMapper(getRepository());
			Date candidateDate = (Date) candidateValue;
			if (operation == QueryOperation.IN_RANGE) {
				String[] dateParts = StringUtils.split(testValue, ",");
				Date after = attributeMapper.getDateValue(dateParts[0]);
				Date before = attributeMapper.getDateValue(dateParts[1]);
				return (candidateDate.after(after) && candidateDate.before(before)) || candidateDate.equals(after)
						|| candidateDate.equals(before);
			}
			if (operation == QueryOperation.AFTER) {
				Date upto = attributeMapper.getDateValue(testValue);
				return candidateDate.after(upto) || candidateDate.equals(upto);
			}
			if (operation == QueryOperation.BEFORE) {
				Date upto = attributeMapper.getDateValue(testValue);
				return candidateDate.before(upto) || candidateDate.equals(upto);
			}
		}
		return false;
	}
}
