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
import java.util.Arrays;
import java.util.List;

public class QueryOperation {

	String id;

	String description;

	public final static QueryOperation SUBSTRING = new QueryOperation(Messages.QueryClause_Operation_substring,
			"substring"); //$NON-NLS-1$

	public final static QueryOperation EXACT = new QueryOperation(Messages.QueryClause_Operation_exact, "exact"); //$NON-NLS-1$

	public final static QueryOperation NOT_EQUALS = new QueryOperation(Messages.QueryClause_Operation_notequals,
			"notequals"); //$NON-NLS-1$

	public final static QueryOperation REGEXP = new QueryOperation(Messages.QueryClause_Operation_regexp, "regexp"); //$NON-NLS-1$

	public final static QueryOperation NOT_REGEXP = new QueryOperation(Messages.QueryClause_Operation_notregexp,
			"notregexp"); //$NON-NLS-1$

	public final static QueryOperation[] TEXT_OPERATIONS = new QueryOperation[] { SUBSTRING, EXACT, NOT_EQUALS, REGEXP,
			NOT_REGEXP };

	public final static QueryOperation MATCH_ITEMS = new QueryOperation(Messages.QueryClause_Operation_matches,
			"anyitems"); //$NON-NLS-1$

	public final static QueryOperation[] LIST_OPERATIONS = new QueryOperation[] { MATCH_ITEMS };

	public final static QueryOperation ALL_DATES = new QueryOperation(Messages.QueryClause_Operation_date_all, "all"); //$NON-NLS-1$

	public final static QueryOperation IN_RANGE = new QueryOperation(Messages.QueryClause_Operation_date_inrange,
			"inrange"); //$NON-NLS-1$

	public final static QueryOperation AFTER = new QueryOperation(Messages.QueryClause_Operation_date_after, "after"); //$NON-NLS-1$

	public final static QueryOperation BEFORE = new QueryOperation(Messages.QueryClause_Operation_date_before, "before"); //$NON-NLS-1$

	public final static QueryOperation[] DATE_OPERATIONS = new QueryOperation[] { ALL_DATES, IN_RANGE, AFTER, BEFORE };

	private final static List<QueryOperation> all_operations_list = new ArrayList<QueryOperation>();

	static {
		all_operations_list.addAll(Arrays.asList(TEXT_OPERATIONS));
		all_operations_list.addAll(Arrays.asList(LIST_OPERATIONS));
		all_operations_list.addAll(Arrays.asList(DATE_OPERATIONS));
	}

	public static QueryOperation[] ALL_OPERATIONS = all_operations_list.toArray(new QueryOperation[] {});

	public static final String[] patternOperationText = { Messages.QueryClause_OperationText_allwordssubstr,
			Messages.QueryClause_OperationText_anywordssubstr, Messages.QueryClause_OperationText_substring,
			Messages.QueryClause_OperationText_casesubstring, Messages.QueryClause_OperationText_allwords,
			Messages.QueryClause_OperationText_anywords, Messages.QueryClause_OperationText_regexp,
			Messages.QueryClause_OperationText_notregexp };

	public static final String[] patternOperationValues = {
			"allwordssubstr", "anywordssubstr", "substring", "casesubstring", "allwords", "anywords", "regexp", "notregexp" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$

	public QueryOperation(String description, String id) {
		super();
		this.id = id;
		this.description = description;
	}

	public static String[] toDescription(QueryOperation[] operations) {
		String[] values = new String[operations.length];
		int i = 0;
		for (QueryOperation queryOperation : operations) {
			values[i++] = queryOperation.description;
		}
		return values;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
