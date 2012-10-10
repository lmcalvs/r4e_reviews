/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This interface define method used to query the employee database
 * 
 * Contributors:
 *   Jacques Bouthillier - Interface to define the type of allowed query
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.userSearch.query;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;

/**
 * @author Jacques Bouthillier
 */
public interface IQueryUser {

	public abstract ArrayList<IUserInfo> search(String a_inUserID, String a_inName, String a_inCompany,
			String a_inOffice, String a_inDepartement, String a_inCountry, String a_inCity) throws NamingException,
			IOException;

	public abstract ArrayList<IUserInfo> searchByUserId(String aUserId) throws NamingException, IOException;

	public abstract ArrayList<IUserInfo> searchByUserName(String aUserName) throws NamingException, IOException;

}
