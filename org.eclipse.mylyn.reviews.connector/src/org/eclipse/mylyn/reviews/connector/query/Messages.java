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

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.mylyn.reviews.connector.query.messages"; //$NON-NLS-1$
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String QueryClause_Operation_exact;

	public static String QueryClause_Operation_notequals;

	public static String QueryClause_Operation_notregexp;

	public static String QueryClause_Operation_regexp;

	public static String QueryClause_Operation_substring;

	public static String QueryClause_Operation_date_all;

	public static String QueryClause_Operation_date_inrange;

	public static String QueryClause_Operation_date_after;

	public static String QueryClause_Operation_date_before;

	public static String QueryClause_Operation_contains_all_of_the_strings;

	public static String QueryClause_Operation_contains_all_of_the_words;

	public static String QueryClause_Operation_contains_any_of_he_words;

	public static String QueryClause_Operation_contains_any_of_the_strings;

	public static String QueryClause_Operation_contains_none_of_the_strings;

	public static String QueryClause_Operation_contains_none_of_the_words;

	public static String QueryClause_Operation_contains_regexp;

	public static String QueryClause_Operation_contains_the_string;

	public static String QueryClause_Operation_contains_the_string_exact_case;

	public static String QueryClause_Operation_does_not_contain_regexp;

	public static String QueryClause_Operation_does_not_contain_the_string;

	public static String QueryClause_Operation_is_equal_to;

	public static String QueryClause_Operation_is_equal_to_any_of_the_strings;

	public static String QueryClause_Operation_is_greater_than;

	public static String QueryClause_Operation_is_less_than;

	public static String QueryClause_Operation_is_not_equal_to;

	public static String QueryClause_Operation_matches;

	public static String QueryClause_Operation_Noop;

	public static String QueryClause_OperationText_allwords;

	public static String QueryClause_OperationText_allwordssubstr;

	public static String QueryClause_OperationText_anywords;

	public static String QueryClause_OperationText_anywordssubstr;

	public static String QueryClause_OperationText_casesubstring;

	public static String QueryClause_OperationText_notregexp;

	public static String QueryClause_OperationText_regexp;

	public static String QueryClause_OperationText_substring;

	private Messages() {
	}
}
