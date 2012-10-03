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
 * This class is used to initialize SMTP host preferences
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review SMTP host preferences
 *   
 *******************************************************************************/



package org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

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
		//final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		// Default Host Information
		//store.setDefault(PreferenceConstants.FP_SMTP_SERVER_LIST_ID, F_HOST_LIST);

	}

	/**
	 * Load the default value to the store value Use in Junit to reset the value
	 */
	public void storeDefaultPreferences() {
		// final IPreferenceStore store =
		// Activator.getDefault().getPreferenceStore();
		//
		// // Default Host Information
		// store.setValue(PreferenceConstants.FP_SMTP_SERVER_LIST_ID, "389");

	}

}
