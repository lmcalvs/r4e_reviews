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
 * This class is used to initialize R4E LDAP preferences
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E LDAP project
 *   
 *******************************************************************************/



package org.eclipse.mylyn.reviews.ldap.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.mylyn.reviews.ldap.LdapPlugin;

/**
 * @author Jacques Bouthillier
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method initializeDefaultPreferences.
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = LdapPlugin.getDefault().getPreferenceStore();
		

		// Default Server Information
		store.setDefault(PreferenceConstants.FP_SERVER_TYPE_ID, PreferenceConstants.FP_SERVER_BASIC);
		store.setDefault(PreferenceConstants.FP_HOST_ID, "");
		store.setDefault(PreferenceConstants.FP_PORT_ID, "389");
		store.setDefault(PreferenceConstants.FP_BASE_ID, "");


		// Store the default authentication to "none";
		store.setDefault(PreferenceConstants.FP_SECURITY_AUTHENTICATION_ID, PreferenceConstants.FP_NONE);
		store.setDefault(PreferenceConstants.FP_SECURITY_USER_NAME_ID, "");
		store.setDefault(PreferenceConstants.FP_SECURITY_PASSWORD_ID, "");

		/**
		 * The group to keep the LDAP default field definition EX: KEY,VALUE
		 */
		store.setDefault(PreferenceConstants.FP_UID_ID, PreferenceConstants.FP_UID_ID);
		store.setDefault(PreferenceConstants.FP_NAME_ID, PreferenceConstants.FP_NAME_ID);
		store.setDefault(PreferenceConstants.FP_TELEPHONE_ID, PreferenceConstants.FP_TELEPHONE_ID);
		store.setDefault(PreferenceConstants.FP_MOBILE_ID, PreferenceConstants.FP_MOBILE_ID);
		store.setDefault(PreferenceConstants.FP_ECN_ID, PreferenceConstants.FP_ECN_ID);
		store.setDefault(PreferenceConstants.FP_COMPANY_ID, PreferenceConstants.FP_COMPANY_ID);
		store.setDefault(PreferenceConstants.FP_DEPARTMENT_ID, PreferenceConstants.FP_DEPARTMENT_ID);
		store.setDefault(PreferenceConstants.FP_OFFICE_NAME_ID, PreferenceConstants.FP_OFFICE_NAME_ID);
		store.setDefault(PreferenceConstants.FP_ROOM_NUMBER_ID, PreferenceConstants.FP_ROOM_NUMBER_ID);
		store.setDefault(PreferenceConstants.FP_CITY_ID, PreferenceConstants.FP_CITY_ID);
		store.setDefault(PreferenceConstants.FP_COUNTRY_ID, PreferenceConstants.FP_COUNTRY_ID);
		store.setDefault(PreferenceConstants.FP_EMAIL_ID, PreferenceConstants.FP_EMAIL_ID);
		// Fields that we could use as well
		store.setDefault(PreferenceConstants.FP_DOMAIN_ID, PreferenceConstants.FP_DOMAIN_ID);
		store.setDefault(PreferenceConstants.FP_TITLE_ID, PreferenceConstants.FP_TITLE_ID);
		store.setDefault(PreferenceConstants.FP_STREET_ADDRESS_ID, PreferenceConstants.FP_STREET_ADDRESS_ID);
		store.setDefault(PreferenceConstants.FP_POSTAL_CODE_ID, PreferenceConstants.FP_POSTAL_CODE_ID);

	}

	/**
	 * Load the default value to the store value Use in Junit to reset the value
	 */
	public void storeDefaultPreferences() {
		final IPreferenceStore store = LdapPlugin.getDefault().getPreferenceStore();

		// Default Server Information
		store.setValue(PreferenceConstants.FP_SERVER_TYPE_ID, PreferenceConstants.FP_SERVER_BASIC);
		store.setValue(PreferenceConstants.FP_HOST_ID, "");
		store.setValue(PreferenceConstants.FP_PORT_ID, "389");
		store.setValue(PreferenceConstants.FP_BASE_ID, "");

		// Store the default authentication to "none";
		store.setValue(PreferenceConstants.FP_SECURITY_AUTHENTICATION_ID, PreferenceConstants.FP_NONE);
		store.setValue(PreferenceConstants.FP_SECURITY_USER_NAME_ID, "");
		store.setValue(PreferenceConstants.FP_SECURITY_PASSWORD_ID, "");

		/**
		 * The group to keep the LDAP default field definition EX: KEY,VALUE
		 */
		store.setValue(PreferenceConstants.FP_UID_ID, PreferenceConstants.FP_UID_ID);
		store.setValue(PreferenceConstants.FP_NAME_ID, PreferenceConstants.FP_NAME_ID);
		store.setValue(PreferenceConstants.FP_TELEPHONE_ID, PreferenceConstants.FP_TELEPHONE_ID);
		store.setValue(PreferenceConstants.FP_MOBILE_ID, PreferenceConstants.FP_MOBILE_ID);
		store.setValue(PreferenceConstants.FP_ECN_ID, PreferenceConstants.FP_ECN_ID);
		store.setValue(PreferenceConstants.FP_COMPANY_ID, PreferenceConstants.FP_COMPANY_ID);
		store.setValue(PreferenceConstants.FP_DEPARTMENT_ID, PreferenceConstants.FP_DEPARTMENT_ID);
		store.setValue(PreferenceConstants.FP_OFFICE_NAME_ID, PreferenceConstants.FP_OFFICE_NAME_ID);
		store.setValue(PreferenceConstants.FP_ROOM_NUMBER_ID, PreferenceConstants.FP_ROOM_NUMBER_ID);
		store.setValue(PreferenceConstants.FP_CITY_ID, PreferenceConstants.FP_CITY_ID);
		store.setValue(PreferenceConstants.FP_COUNTRY_ID, PreferenceConstants.FP_COUNTRY_ID);
		store.setValue(PreferenceConstants.FP_EMAIL_ID, PreferenceConstants.FP_EMAIL_ID);
		// Fields that we could use as well
		store.setValue(PreferenceConstants.FP_DOMAIN_ID, PreferenceConstants.FP_DOMAIN_ID);
		store.setValue(PreferenceConstants.FP_TITLE_ID, PreferenceConstants.FP_TITLE_ID);
		store.setValue(PreferenceConstants.FP_STREET_ADDRESS_ID, PreferenceConstants.FP_STREET_ADDRESS_ID);
		store.setValue(PreferenceConstants.FP_POSTAL_CODE_ID, PreferenceConstants.FP_POSTAL_CODE_ID);

	}

}
