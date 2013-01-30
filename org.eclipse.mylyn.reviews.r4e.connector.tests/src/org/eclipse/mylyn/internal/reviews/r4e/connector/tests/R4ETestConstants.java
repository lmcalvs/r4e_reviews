/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.r4e.connector.tests;

import org.eclipse.emf.common.util.URI;

/**
 * @author Miles Parker
 */
public class R4ETestConstants {
	public final static String TEST_REVIEW_GROUP_URI = "data/TestReviewGroup_group_root.xrer";

	//public final static String TEST_REVIEW_GROUP_URI = "file:///Users/milesparker/git/org.eclipse.mylyn.reviews.r4e/org.eclipse.mylyn.reviews.r4e.connector.tests/data/TestReviewGroup_group_root.xrer";
	public static final URI TEST_GROUP_URI = URI.createFileURI("stubs_model/outGroupX/Golden_Group_group_root.xrer");
}
