/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Proxy class used to interact with R4E Preferences programmatically
 * for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.R4EPreferencePage;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.PreferencesUtil;

@SuppressWarnings("restriction")
public class R4EUITestPreferences extends R4EUITestElement {

	public R4EUITestPreferences(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	private static final String PREFERENCES_PAGE_ID = "org.eclipse.mylyn.reviews.r4e.ui.preferences.R4EPreferencePage";

	/**
	 * Method getUser.
	 * 
	 * @return String
	 */
	public String getUser() {
		//Inner class that runs the command on the UI thread
		class RunGetUser implements Runnable {
			private String user;

			public String getUser() {
				return user;
			}

			public void setUser(String aUser) {
				user = aUser;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				setUser(preferences.getUser());
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunGetUser getUserJob = new RunGetUser();
		Display.getDefault().syncExec(getUserJob);
		TestUtils.waitForJobs();
		return getUserJob.getUser();
	}

	/**
	 * Method setUser.
	 * 
	 * @param aUser
	 *            - String
	 */
	public void setUser(String aUser) {
		//Inner class that runs the command on the UI thread
		class RunSetUser implements Runnable {
			private String user;

			public String getUser() {
				return user;
			}

			public void setUser(String aUser) {
				user = aUser;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.setUser(getUser());
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunSetUser setUserJob = new RunSetUser();
		setUserJob.setUser(aUser);
		Display.getDefault().syncExec(setUserJob);
		TestUtils.waitForJobs();
		return;
	}

	/**
	 * Method getEmail.
	 * 
	 * @return String
	 */
	public String getEmail() {
		//Inner class that runs the command on the UI thread
		class RunGetEmail implements Runnable {
			private String email;

			public String getEmail() {
				return email;
			}

			public void setEmail(String aEmail) {
				email = aEmail;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				setEmail(preferences.getEmail());
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunGetEmail getEmailJob = new RunGetEmail();
		Display.getDefault().syncExec(getEmailJob);
		TestUtils.waitForJobs();
		return getEmailJob.getEmail();
	}

	/**
	 * Method setEmail.
	 * 
	 * @param aEmail
	 *            - String
	 */
	public void setEmail(String aEmail) {
		//Inner class that runs the command on the UI thread
		class RunSetEmail implements Runnable {
			private String email;

			public String getEmail() {
				return email;
			}

			public void setEmail(String aEmail) {
				email = aEmail;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.setEmail(getEmail());
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunSetEmail setEmailJob = new RunSetEmail();
		setEmailJob.setEmail(aEmail);
		Display.getDefault().syncExec(setEmailJob);
		TestUtils.waitForJobs();
		return;
	}

	/**
	 * Method addGroupToPreferences.
	 * 
	 * @param aGroupPath
	 */
	public void addGroupToPreferences(String aGroupPath) {
		//Inner class that runs the command on the UI thread
		class AddGroupPreferences implements Runnable {

			private String group;

			public void setGroup(String aGroupPath) {
				group = aGroupPath;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.addGroupPrefs(group);
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		AddGroupPreferences addGroupPreferencesJob = new AddGroupPreferences();
		addGroupPreferencesJob.setGroup(aGroupPath);
		Display.getDefault().syncExec(addGroupPreferencesJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method removeGroupFromPreferences.
	 * 
	 * @param aGroupPath
	 */
	public void removeGroupFromPreferences(String aGroupPath) {
		//Inner class that runs the command on the UI thread
		class RemoveGroupPreferences implements Runnable {

			private String group;

			public void setGroup(String aGroupPath) {
				group = aGroupPath;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.removeGroupPrefs(group);
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RemoveGroupPreferences removeGroupPreferencesJob = new RemoveGroupPreferences();
		removeGroupPreferencesJob.setGroup(aGroupPath);
		Display.getDefault().syncExec(removeGroupPreferencesJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method addRuleSetToPreferences.
	 * 
	 * @param aRuleSetPath
	 */
	public void addRuleSetToPreferences(String aRuleSetPath) {
		//Inner class that runs the command on the UI thread
		class AddRuleSetPreferences implements Runnable {

			private String ruleSet;

			public void setRuleSet(String aRuleSetPath) {
				ruleSet = aRuleSetPath;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.addRuleSetPrefs(ruleSet);
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		AddRuleSetPreferences addRuleSetPreferencesJob = new AddRuleSetPreferences();
		addRuleSetPreferencesJob.setRuleSet(aRuleSetPath);
		Display.getDefault().syncExec(addRuleSetPreferencesJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method removeRuleSetFromPreferences.
	 * 
	 * @param aRuleSetPath
	 */
	public void removeRuleSetFromPreferences(String aRuleSetPath) {
		//Inner class that runs the command on the UI thread
		class RemoveRuleSetPreferences implements Runnable {

			private String ruleSet;

			public void setRuleSet(String aRuleSetPath) {
				ruleSet = aRuleSetPath;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.removeRuleSetPrefs(ruleSet);
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RemoveRuleSetPreferences removeRuleSetPreferencesJob = new RemoveRuleSetPreferences();
		removeRuleSetPreferencesJob.setRuleSet(aRuleSetPath);
		Display.getDefault().syncExec(removeRuleSetPreferencesJob);
		TestUtils.waitForJobs();
	}

	/**
	 * Method getGlobalPostponedImport.
	 * 
	 * @return boolean
	 */
	public boolean getGlobalPostponedImport() {
		//Inner class that runs the command on the UI thread
		class RunGetGlobalPostponedImport implements Runnable {
			private boolean fValue;

			public boolean getGlobalPostponedImport() {
				return fValue;
			}

			public void setGlobalPostponedImport(boolean aValue) {
				fValue = aValue;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				setGlobalPostponedImport(preferences.getGlobalPostponedImport());
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunGetGlobalPostponedImport getGlobalPostponedImportJob = new RunGetGlobalPostponedImport();
		Display.getDefault().syncExec(getGlobalPostponedImportJob);
		TestUtils.waitForJobs();
		return getGlobalPostponedImportJob.getGlobalPostponedImport();
	}

	/**
	 * Method toggleGlobalPostponedImport Toggles the importing of Global Postponed Anomalies on/off
	 */
	public void setGlobalPostponedImport(boolean aValue) {
		//Inner class that runs the command on the UI thread
		class RunToggleGlobalPostponedImport implements Runnable {
			private boolean fValue;

			public void setGlobalPostponedImport(boolean aValue) {
				fValue = aValue;
			}

			public void run() {
				PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, PREFERENCES_PAGE_ID, null,
						null);
				R4EPreferencePage preferences = (R4EPreferencePage) dialog.getSelectedPage();
				preferences.setGlobalPostponedImport(fValue);
				preferences.performOk();
				dialog.close();
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunToggleGlobalPostponedImport toggleGlobalPostponedImportJob = new RunToggleGlobalPostponedImport();
		toggleGlobalPostponedImportJob.setGlobalPostponedImport(aValue);
		Display.getDefault().syncExec(toggleGlobalPostponedImportJob);
		TestUtils.waitForJobs();
	}
}
