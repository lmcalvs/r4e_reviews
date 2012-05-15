/**
 * Copyright (c) 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.userSearch.query;

import org.eclipse.mylyn.reviews.ldap.internal.queryUtility.QueryGlobalLdapDirectory;

/**
 * @author Jacques Bouthillier
 */
public class QueryUserFactory {

	public IQueryUser getInstance() {
		return new QueryGlobalLdapDirectory();
	}

}
