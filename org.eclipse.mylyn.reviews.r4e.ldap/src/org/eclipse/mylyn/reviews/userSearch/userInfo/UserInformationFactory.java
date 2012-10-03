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
 * This class represents the factory to get user information.
 * 
 * Contributors:
 *   Jacques Bouthillier - Class to extract User information 
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.userSearch.userInfo;

import org.eclipse.mylyn.reviews.ldap.internal.queryUtility.UserData;

/**
 * @author Jacques Bouthillier
 */
public class UserInformationFactory {

	public static IUserInfo getInstance() {
		return new UserData();
	}

}
