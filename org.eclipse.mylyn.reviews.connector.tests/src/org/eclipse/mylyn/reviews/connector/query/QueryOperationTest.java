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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.eclipse.mylyn.reviews.connector.query.QueryOperation;
import org.junit.Test;

@SuppressWarnings("nls")
public class QueryOperationTest extends TestCase {

	@Test
	public void testDescriptions() {
		String[] description = QueryOperation.toDescription(QueryOperation.ALL_OPERATIONS);
		assertThat(description[0], is("contains"));
	}
}
