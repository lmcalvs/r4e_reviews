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
 * This class represents the interface to read user information.
 * 
 * Contributors:
 *   Jacques Bouthillier - Interface to extract User information 
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.userSearch.userInfo;

import javax.naming.directory.Attribute;

/**
 * @author Jacques Bouthillier
 */
public interface IUserInfo {
	    

	public IUserInfo setData(Attribute inUserID, Attribute inFullName, Attribute inCompany, Attribute inLocation,
			Attribute inDepartment, Attribute inCountry, Attribute inCity, Attribute inECN, Attribute inBuisnessPhone,
			Attribute inMobilePhone, Attribute inRoom, Attribute inEmail, Attribute inNTDomain, Attribute inTitle)
			throws javax.naming.NamingException;

	public abstract String getUserId();

	public abstract String getName();

	public abstract String getCompany();

	public abstract String getOffice();

	public abstract String getDepartment();

	public abstract String getCountry();

	public abstract String getCity();

	public abstract String getECN();

	public abstract String getBuisnessPhone();

	public abstract String getMobilePhone();

	public abstract String getRoom();

	public abstract String getEmail();

	public abstract String getNTDomain();

	public abstract String getTitle();
	    
	public abstract String[] getAttributeValues();
	    
	public abstract String[] getAttributeTypes();

	public abstract String toString();

}
