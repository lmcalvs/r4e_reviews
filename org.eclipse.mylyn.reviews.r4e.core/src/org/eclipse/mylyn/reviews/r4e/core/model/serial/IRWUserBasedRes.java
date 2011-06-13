/*******************************************************************************
 * Copyright (c) 2011 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Ericsson AB - Initial API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.model.serial;

import java.util.regex.Pattern;

/**
 * @author Alvaro Sanchez-Leon
 */
public interface IRWUserBasedRes {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	public enum ResourceType {
		GROUP, USER_GROUP, REVIEW, USER_COMMENT, USER_ITEM, DRULE_SET
	}

	final static String		EXTENSION				= "xrer";
	final static String		REVIEW_RES_TAG			= "_review";
	final static String		REVIEW_UCOMMENT_TAG		= "_comments";
	final static String		REVIEW_UITEM_TAG		= "_items";
	final static String		GROUP_ROOT_TAG			= "_group_root";
	final static String		GROUP_UREVIEW_TAG		= "_group_reviews";
	final static String		DRULE_SET_TAG			= "_rule_set";

	// Patterns
	final static String		END						= "\\z";
	final static Pattern	USER_GROUP_REVS_PATT	= Pattern.compile(GROUP_UREVIEW_TAG + "." + EXTENSION + END);
	final static Pattern	USER_ITEMS_PATT			= Pattern.compile(REVIEW_UITEM_TAG + "." + EXTENSION + END);
	final static Pattern	USER_COMMENTS_PATT		= Pattern.compile(REVIEW_UCOMMENT_TAG + "." + EXTENSION + END);
	final static Pattern	ROOT_GROUP_PATT			= Pattern.compile(GROUP_ROOT_TAG + "." + EXTENSION + END);
	final static Pattern	DRULE_SET_PATT			= Pattern.compile(DRULE_SET_TAG + "." + EXTENSION + END);
}
