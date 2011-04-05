/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class accesses the LDAP (Lightweight Directory Access Protocol) 
 * database according to the the database field provided on the 
 * preference store.
 * 
 * Contributors:
 *   Jacques Bouthillier - Query the LDAP database based on the field description.
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.ldap.internal.queryUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.eclipse.mylyn.reviews.ldap.Activator;
import org.eclipse.mylyn.reviews.ldap.internal.preferences.R4ELdapPreferencePage;
import org.eclipse.mylyn.reviews.ldap.internal.util.R4EString;
import org.eclipse.mylyn.reviews.userSearch.query.IQueryUser;
import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;
import org.eclipse.mylyn.reviews.userSearch.userInfo.UserInformationFactory;

/**
 * Allow to search the LDAP employee database
 */

/**
 * @author Jacques Bouthillier
 */
public class QueryGlobalLdapDirectory implements IQueryUser {

	private R4ELdapPreferencePage	fLdap			= new R4ELdapPreferencePage();

	/**
	 * Search in the Employee Global Directory with all search parameters having a string different than the empty
	 * string (""). The search is done with a logical AND between parameters and all parameters are searched with the
	 * regular expression *<parameter>*. Search parameters cannot be null and should have an empty String ("") if the
	 * search should skip them.
	 * 
	 * @param a_inUserID
	 * @param a_inName
	 * @param a_inCompany
	 * @param a_inOffice
	 * @param a_inDepartement
	 * @param a_inCountry
	 * @param a_inCity
	 * 
	 * @return ArrayList<IUserInfo> The max seems to be 1200 users, may be empty.
	 * @throws IOException
	 */

	public ArrayList<IUserInfo> search(String a_inUserID, String a_inName, String a_inCompany, String a_inOffice,
			String a_inDepartement, String a_inCountry, String a_inCity) throws NamingException, IOException {
		ArrayList<IUserInfo> userList = new ArrayList<IUserInfo>(
				50);
		Hashtable<String, String> env = new Hashtable<String, String>();

		env = (Hashtable<String, String>) getConnectionProperties();
		DirContext ctx = new InitialDirContext(env);

		// Grammar filter can be found at http://www.ietf.org/rfc/rfc2254.txt
		StringBuilder filter = new StringBuilder(150);
		filter.append('(');
		if (a_inUserID != null && !a_inUserID.equals("")) {
			filter.append("&(" + fLdap.getFieldUserId() + "=" + a_inUserID + "*)");
		}
		if (a_inName != null && !a_inName.equals("")) {
			filter.append("&(" + fLdap.getFieldUserName() + "=" + a_inName + "*)");
		}
		if (a_inCompany != null && !a_inCompany.equals("")) {
			filter.append("&(" + fLdap.getFieldCompany() + "=*" + a_inCompany + "*)");
		}
		if (a_inOffice != null && !a_inOffice.equals("")) {
			filter.append("&(" + fLdap.getFieldOfficeName() + "=*" + a_inOffice
					+ "*)");
		}
		if (a_inDepartement != null && !a_inDepartement.equals("")) {
			filter.append("&(" + fLdap.getFieldDepartment() + "=" + a_inDepartement
					+ "*)");
		}
		if (a_inCountry != null && !a_inCountry.equals("")) {
			filter.append("&(" + fLdap.getFieldCountry() + "=*" + a_inCountry + "*)");
		}
		if (a_inCity != null && !a_inCity.equals("")) {
			filter.append("&(" + fLdap.getFieldCity() + "=" + a_inCity + "*)");
		}

		filter.append(')');

		Activator.FTracer.traceInfo("Filter search:" + filter);
		// Order of data field received from querying the LDAP database
		String[] returningAttributes = {
				fLdap.getFieldUserId(), // uid
				fLdap.getFieldUserName(),
				fLdap.getFieldCompany(), // "ou",
				fLdap.getFieldCity(), // "L"
				fLdap.getFieldDepartment(), // "departmentNumber",
				fLdap.getFieldCountry(),
				fLdap.getFieldOfficeName(), // "physicalDeliveryOfficeName",
				fLdap.getFieldTelephone(), // "telephoneNumber",
				fLdap.getFieldECN(),
				fLdap.getFieldMobile(), // "mobile"
				fLdap.getFieldRoom(), // "roomNumber"
				fLdap.getFieldOfficeName(), fLdap.getFieldStreetAddress(), fLdap.getFieldTitle(),
				fLdap.getFieldEmail(), // "mail"
				fLdap.getFieldDomain(), fLdap.getFieldTitle() };
		SearchControls mySearchControl = new SearchControls(2, 0, 30000,
				returningAttributes, false, false); //

		// String dn = "DC=[cie],DC=se";
		String dn = fLdap.getBaseId();
		NamingEnumeration<SearchResult> result = ctx.search(dn,
				filter.toString(), mySearchControl);

		Attributes userAttribs;

		while (result.hasMore()) {
			userAttribs = (result.next()).getAttributes();

			// Test if we have an e-mail. If not, we do not
			// add it since we will not be able to send an e-mail to it
			if (userAttribs.get(fLdap.getFieldEmail()) != null) {
				IUserInfo userData = UserInformationFactory.getInstance();
				userList.add(userData.setData(
						userAttribs.get(fLdap.getFieldUserId()), // "cn"
						userAttribs.get(fLdap.getFieldUserName()),
						userAttribs.get(fLdap.getFieldCompany()),// company "ou"
						userAttribs.get(fLdap.getFieldOfficeName()), // "L"
						userAttribs.get(fLdap.getFieldDepartment()), // "departmentNumber"
						userAttribs.get(fLdap.getFieldCountry()),
						userAttribs.get(fLdap.getFieldCity()), // "physicalDeliveryOfficeName"
						userAttribs.get(fLdap.getFieldECN()),
						userAttribs.get(fLdap.getFieldTelephone()), userAttribs.get(fLdap.getFieldMobile()),
						userAttribs.get(fLdap.getFieldRoom()), userAttribs.get(fLdap.getFieldEmail()), userAttribs
								.get(fLdap.getFieldDomain()), userAttribs.get(fLdap.getFieldTitle())));

			} else {
				// Activator.FTracer
				// .traceWarning("Warning: " + "No E-mail for " + userAttribs.get(fLdap.getFieldUserId()));
			}
		}

		// Close the DirContext
		ctx.close();
		env.clear();
		return userList;
	}

	/**
	 * Perform a query from the database using the user Id only
	 * 
	 * @param aUerId
	 * @return ArrayList<IUserInfo>
	 * @throws NamingException
	 * @throws IOException
	 */
	public ArrayList<IUserInfo> searchByUserId(String aUserId) throws NamingException, IOException {
		ArrayList<IUserInfo> list = null;
		if (aUserId == null || aUserId.equals("")) {
			throw new IOException(R4EString.getString("messageError1") + R4EString.getString("noUserIdProvided"));
		}
		list = search(aUserId, // User Id
				"", // User Name
				"", // Company name
				"", // Office
				"", // Department
				"", // Country
				"");
		return list;

	}

	/**
	 * Perform a query from the database using the user Name only
	 * 
	 * @param aUerId
	 * @return ArrayList<IUserInfo>
	 * @throws NamingException
	 * @throws IOException
	 */
	public ArrayList<IUserInfo> searchByUserName(String aUserName) throws NamingException, IOException {
		ArrayList<IUserInfo> list = null;
		if (aUserName == null || aUserName.equals("")) {
			throw new IOException(R4EString.getString("messageError1") + R4EString.getString("noUserNameProvided"));
		}

		list = search("", // User Id
				aUserName, // User Name
				"", // Company name
				"", // Office
				"", // Department
				"", // Country
				"");
		return list;

	}

	/**
	 * Get the information about the server information
	 * 
	 * @return String
	 * @throws IOException
	 */
	private String getProviderURL() throws IOException {
		// "ldap://localhost:389"
		final StringBuffer sb = new StringBuffer(150);

		// Server information
		String serverInfo = fLdap.getServerInfo();
		if (serverInfo != null) {
			sb.append(serverInfo); // New LDAP
		} else {
			// XX May log an error here since the LDAP server is not defined in the property
			Activator.FTracer.traceWarning("Warning " + R4EString.getString("messageError4"));
		}
		// sb.append("ldap://127.0.0.1:3268"); // To test internally

		return sb.toString();
	}

	/**
	 * Set the environment properties
	 * 
	 * @return Map environment
	 * @throws IOException
	 */
	private Map<String, String> getConnectionProperties() throws IOException {
		final Map<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory"); //$NON-NLS-1$
		env.put(Context.PROVIDER_URL, getProviderURL());

		// sowieso default
		// env.put(Context.SECURITY_AUTHENTICATION, "none");

		// Authentication: "none", "simple", "strong".
		env.put(Context.SECURITY_AUTHENTICATION, fLdap.getAuthentication());
		Activator.FTracer.traceInfo("Info: " + "User: " + fLdap.getUserName()
				+ "\t Authentication: " + fLdap.getAuthentication());
		env.put(Context.SECURITY_PRINCIPAL, fLdap.getUserName());
		env.put(Context.SECURITY_CREDENTIALS, fLdap.getPassword());

		return env;
	}

	public void main(String argv[]) {
		ArrayList<IUserInfo> userList = null;

		try {
			QueryGlobalLdapDirectory ericGlobalDir = new QueryGlobalLdapDirectory();
			try {
				userList = ericGlobalDir.search("", "", "", "", "", "", "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < userList.size(); i++) {
				System.out.println(userList.get(i) + ", "
						+ userList.get(i).getNTDomain());
			}

		} catch (NamingException exc) {
			System.out.println("Error when searching in the LDAP Global Directory");
		}

	}

}