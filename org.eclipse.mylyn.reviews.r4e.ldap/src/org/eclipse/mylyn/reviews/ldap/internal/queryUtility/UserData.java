/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class represents the user information extracted from the LDAP
 * database.
 * 
 * Contributors:
 *   Jacques Bouthillier - User information extracted from an LDAP database
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.ldap.internal.queryUtility;

import javax.naming.directory.Attribute;

import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;

/**
 * @author Jacques Bouthillier
 */
public class UserData implements IUserInfo {
	private String f_userID;

	private String f_name;

	private String f_company;

	private String f_office;

	private String f_department;

	private String f_country;

	private String f_city;

	private String f_ecn;

	private String f_buisnessPhone;

	private String f_mobilePhone;

	private String f_room;

	private String f_email;

	private String f_ntDomain;

	private String f_title;

	public UserData() {
	}

	public UserData setData(Attribute inUserID, Attribute inFullName, Attribute inCompany, Attribute inLocation,
			Attribute inDepartment, Attribute inCountry, Attribute inCity, Attribute inECN, Attribute inBuisnessPhone,
			Attribute inMobilePhone, Attribute inRoom, Attribute inEmail, Attribute inNTDomain, Attribute inTitle)
			throws javax.naming.NamingException {
		f_userID = null != inUserID ? inUserID.get().toString() : "";
		f_name = null != inFullName ? inFullName.get().toString() : "";
		f_company = null != inCompany ? inCompany.get().toString() : "";
		f_office = null != inLocation ? inLocation.get().toString() : "";
		f_department = null != inDepartment ? inDepartment.get().toString() : "";
		f_country = null != inCountry ? inCountry.get().toString() : "";
		f_city = null != inCity ? inCity.get().toString() : "";
		f_ecn = null != inECN ? inECN.get().toString() : "";
		f_buisnessPhone = null != inBuisnessPhone ? inBuisnessPhone.get().toString() : "";
		f_mobilePhone = null != inMobilePhone ? inMobilePhone.get().toString() : "";
		f_room = null != inRoom ? inRoom.get().toString() : "";
		f_email = null != inEmail ? inEmail.get().toString() : "";
		f_ntDomain = null != inNTDomain ? inNTDomain.get().toString() : "";
		f_title = null != inTitle ? inTitle.get().toString() : "";
		return this;
	}

	public String getUserId() {
		return f_userID;
	}

	public String getName() {
		return f_name;
	}

	public String getCompany() {
		return f_company;
	}

	public String getOffice() {
		return f_office;
	}

	public String getDepartment() {
		return f_department;
	}

	public String getCountry() {
		return f_country;
	}

	public String getCity() {
		return f_city;
	}

	public String getECN() {
		return f_ecn;
	}

	public String getBuisnessPhone() {
		return f_buisnessPhone;
	}

	public String getMobilePhone() {
		return f_mobilePhone;
	}

	public String getRoom() {
		return f_room;
	}

	public String getEmail() {
		return f_email;
	}

	public String getNTDomain() {
		return f_ntDomain;
	}

	public String getTitle() {
		return f_title;
	}

	public String[] getAttributeValues() {
		String[] values = { f_userID, f_name, f_room, f_buisnessPhone, f_ecn, f_mobilePhone, f_company, f_office,
				f_department, f_city, f_country, f_email, f_title };
		return values;
	}

	public String[] getAttributeTypes() {
		String[] types = { "UserID", "Name", "Room", "Business Phone", "ECN", "Mobile Phone", "Company", "Office",
				"Department", "City", "Country", "E-Mail", "Title" };
		return types;
	}

	public String toString() {
		return f_name + ", " + f_room + ", " + f_buisnessPhone + ", " + f_ecn + ", " + f_mobilePhone + ", " + f_company
				+ "/" + f_office + "/" + f_department + ", " + f_city + ", " + f_country + ", " + f_email + ", "
				+ f_title;
	}

}
