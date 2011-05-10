// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class is used to initialize R4E preferences
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;

/**
 * Class used to initialize default preference values.
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method initializeDefaultPreferences.
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		//Set default User ID if none already stored in preferences
		final String defaultUserId = System.getProperty("user.name");
		store.setDefault(PreferenceConstants.P_USER_ID, defaultUserId);
		if (store.getString(PreferenceConstants.P_USER_ID).equals("")) {
			store.setValue(PreferenceConstants.P_USER_ID, defaultUserId);
		}
	}
}
