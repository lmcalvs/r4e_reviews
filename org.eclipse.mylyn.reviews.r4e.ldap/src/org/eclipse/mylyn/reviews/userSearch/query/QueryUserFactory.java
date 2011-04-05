/**
 * 
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
