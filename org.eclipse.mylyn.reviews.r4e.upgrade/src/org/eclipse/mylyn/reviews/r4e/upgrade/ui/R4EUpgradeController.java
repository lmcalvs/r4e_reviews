/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the upgrade routines and their presentation to users
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.mylyn.reviews.r4e.core.utils.VersionUtils;
import org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader;
import org.eclipse.mylyn.reviews.r4e.upgrade.R4EUpgradePlugin;
import org.eclipse.mylyn.reviews.r4e.upgrade.UpgradePath;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeContainer;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeException;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUpgradeController {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field FRAGMENT_VERSION_DEFAULT.  (value is ""0.8.0"")
	 */
	public static final String FRAGMENT_VERSION_DEFAULT = "0.8.0";

	/**
	 * Field VERSION_APPLICATION_OLDER. (value is 1)
	 */
	public static final int VERSION_APPLICATION_OLDER = 1;

	/**
	 * Field VERSION_APPLICATION_NEWER. (value is -1)
	 */
	public static final int VERSION_APPLICATION_NEWER = -1;

	/**
	 * Field DO_UPGRADE.  (value is 0)
	 */
	public static final int DO_UPGRADE = 0;

	/**
	 * Field NO_UPGRADE.  (value is 1)
	 */
	public static final int NO_UPGRADE = 1;

	/**
	 * Field UPGRADE_SUCCESS.  (value is 0)
	 */
	public static final int UPGRADE_SUCCESS = 0;

	/**
	 * Field UPGRADE_NONE.  (value is 1)
	 */
	public static final int UPGRADE_NONE = 1;

	/**
	 * Field UPGRADE_FAILURE.  (value is 2)
	 */
	public static final int UPGRADE_FAILURE = 2;

	/**
	 * Field COMPATIBILITY_ERROR_MESSAGE. (value is ""You cannot use an older application version of R4E to open newer elements versions.  Please upgrade your application"")
	 */
	private static final String COMPATIBILITY_ERROR_MESSAGE = "You cannot use an older application version of R4E to open newer elements versions.  Please upgrade your application";

	/**
	 * Field NO_UPGRADER_ERROR_MESSAGE. (value is ""You are trying to load an older version of the element than the one currently handled by this version of R4E, and no upgrader is available for this upgrade path."")
	 */
	private static final String NO_UPGRADER_ERROR_MESSAGE = "You are trying to open an older version of the element than the one currently handled by this version of R4E, and no upgrader is available for this upgrade path.";

	/**
	 * Field COMPATIBILITY_ERROR_DIALOG_TITLE. (value is ""Compatibility problem Detected for "")
	 */
	private static final String COMPATIBILITY_ERROR_DIALOG_TITLE = "Compatibility problem Detected for ";
	
	/**
	 * Field NO_UPGRADER_ERROR_DIALOG_TITLE. (value is ""No upgrader available for "")
	 */
	private static final String NO_UPGRADER_ERROR_DIALOG_TITLE = "No upgrader available for ";
	
	/**
	 * Field COMPATIBILITY_WARNING_DIALOG_TITLE. (value is ""Version Mismatch Detected for"")
	 */
	private static final String COMPATIBILITY_WARNING_DIALOG_TITLE = "Version Mismatch Detected for ";

	/**
	 * Field COMPATIBILITY_WARNING_MESSAGE. (value is ""You are trying to load an older version of the element than the
	 * one currently handled by this version of R4E.\n You can open the element normally, which will upgrade its version
	 * to the current one, or in Read-only mode, which will preserve its version.\n"")
	 */
	private static final String COMPATIBILITY_COMPATIBLE_WARNING_MESSAGE = "You are trying to load an older version of the element than the one currently handled by this version of R4E."
			+ System.getProperty("line.separator") + System.getProperty("line.separator")
			+ "You can open the element normally, which will convert its version to the current application meta-data, or in Read-only mode, which will preserve its version.";

	/**
	 * Field COMPATIBILITY_WARNING_MESSAGE. (value is ""You are trying to load an older version of the element than the
	 * one currently handled by this version of R4E.\n You can open the element, which will convert its version to the current application meta-data, or cancel the action.\n"")
	 */
	private static final String COMPATIBILITY_INCOMPATIBLE_WARNING_MESSAGE = "You are trying to load an older version of the element than the one currently handled by this version of R4E."
			+ System.getProperty("line.separator") + System.getProperty("line.separator")
			+ "You can open the element, which will convert its version to the current application meta-data, or cancel the action";
	
	/**
	 * Field COMPATIBLE_UPGRADE_DIALOG_BUTTONS.
	 */
	private static final String[] COMPATIBLE_UPGRADE_DIALOG_BUTTONS = { "Load (Convert Version)",
			"Load in Read-Only Mode (Preserve Version)", "Cancel" };

	/**
	 * Field DIALOG_TITLE_ERROR. (value is ""R4E Error"")
	 */
	public static final String DIALOG_TITLE_ERROR = "R4E Compatibility Error";

	/**
	 * Field INCOMPATIBLE_UPGRADE_DIALOG_BUTTONS.
	 */
	private static final String[] INCOMPATIBLE_UPGRADE_DIALOG_BUTTONS = { "Load (Convert Version)", "Cancel" };

	
	private static int fUpgradeTestDialogResult = -1;
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method upgradeCompatibilityCheck.
	 * @param aUpgradeRootURI - URI
	 * @param aOldVersion - String
	 * @param aNewVersion - String
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean upgradeCompatibilityCheck(URI aUpgradeRootURI, String aOldVersion, String aNewVersion) throws IOException {
		int checkResult = VersionUtils.compareVersions(aOldVersion, aNewVersion);
		
		switch (checkResult) {
		case VERSION_APPLICATION_OLDER:
			return false;

		case VERSION_APPLICATION_NEWER:
			//First check if an upgrader is registered for this upgradePath
			IR4EVersionUpgrader upgrader = R4EUpgradeContainer.getUpgrader(new UpgradePath(aOldVersion,
					aNewVersion));
			if (null != upgrader) {
				if (upgrader.isCompatible()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		default:
			//Normal case, do nothing
			return true;
		}
	}
	
	
	/**
	 * Method upgradeCheck.
	 * @param aUpgradeRootURI - URI
	 * @param aElementMsg - String
	 * @param aOldVersion - String
	 * @param aNewVersion - String
	 * @param aRecursive - boolean
	 * @return int
	 * @throws IOException
	 */
	public static int upgradeCheck(URI aUpgradeRootURI, String aElementMsg, String aOldVersion, String aNewVersion, boolean aRecursive) throws IOException {
		int checkResult = VersionUtils.compareVersions(aOldVersion, aNewVersion);
		
		switch (checkResult) {
		case VERSION_APPLICATION_OLDER:
			displayOlderCompatibilityErrorDialog(aElementMsg, aOldVersion, aNewVersion);
			return UPGRADE_FAILURE;

		case VERSION_APPLICATION_NEWER:
			//First check if an upgrader is registered for this upgradePath
			IR4EVersionUpgrader upgrader = R4EUpgradeContainer.getUpgrader(new UpgradePath(aOldVersion,
					aNewVersion));
			if (null != upgrader) {
				int result;
				if (upgrader.isCompatible()) {
					result = displayCompatibleUpgradeDialog(aElementMsg, upgrader);
					switch (result) {
					case DO_UPGRADE:
						//Upgrade version immediately
						try {
							upgrader.upgrade(aUpgradeRootURI, aRecursive);
						} catch (R4EUpgradeException e) {
							String failureMsg = "Upgrade failure from compatible version "
									+ upgrader.getUpgradePath().getBaseVersion() + " to "
									+ upgrader.getUpgradePath().getTargetVersion();
							R4EUpgradePlugin.Ftracer.traceError(failureMsg);
							R4EUpgradePlugin.getDefault().logError(failureMsg, e);
							return UPGRADE_FAILURE;
						}
						return UPGRADE_SUCCESS;
						
					case NO_UPGRADE:
						return UPGRADE_NONE;

					default:
						//Assume COMPATIBLE_CANCEL_UPGRADE
						return UPGRADE_FAILURE;
					}
				} else {
					result = displayIncompatibleUpgradeDialog(aElementMsg, upgrader);
					switch (result) {
					case DO_UPGRADE:
						//Upgrade version immediately
						try {
							upgrader.upgrade(aUpgradeRootURI, aRecursive);
						} catch (R4EUpgradeException e) {
							String failureMsg = "Upgrade failure from non-compatible version "
									+ upgrader.getUpgradePath().getBaseVersion() + " to "
									+ upgrader.getUpgradePath().getTargetVersion();
							R4EUpgradePlugin.Ftracer.traceError(failureMsg);
							R4EUpgradePlugin.getDefault().logError(failureMsg, e);
							return UPGRADE_FAILURE;
						}
						return UPGRADE_SUCCESS;

					default:
						//Assume INCOMPATIBLE_CANCEL_UPGRADE
						return UPGRADE_FAILURE;
					}
				}
			} else {
				displayNoUpgraderCompatibilityErrorDialog(aElementMsg, aOldVersion, aNewVersion);
				return UPGRADE_FAILURE;
			}

		default:
			//Normal case, do nothing
			return UPGRADE_SUCCESS;
		}
	}

	/**
	 * Method displayCompatibilityErrorDialog.
	 * @param aDataVersion - String
	 * @param aApplVersion - String
	 */
	public static void displayOlderCompatibilityErrorDialog(String aElementMsg, String aDataVersion, String aApplVersion) {
		final String upgraderMessage = COMPATIBILITY_ERROR_MESSAGE + System.getProperty("line.separator") + "Element meta-data Version: "
				+ aDataVersion + System.getProperty("line.separator") + "Application meta-data Version: "
				+ aApplVersion;
		
		R4EUpgradePlugin.Ftracer.traceError(upgraderMessage);
		final ErrorDialog dialog = new ErrorDialog(null, DIALOG_TITLE_ERROR, COMPATIBILITY_ERROR_DIALOG_TITLE + aElementMsg,
				new Status(IStatus.ERROR, R4EUpgradePlugin.PLUGIN_ID, 0, upgraderMessage, null), IStatus.ERROR);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				dialog.open();
			}
		});
	}

	/**
	 * Method displayCompatibilityErrorDialog.
	 * @param aDataVersion - String
	 * @param aApplVersion - String
	 */
	public static void displayNoUpgraderCompatibilityErrorDialog(String aElementMsg, String aDataVersion, String aApplVersion) {
		final String upgraderMessage = NO_UPGRADER_ERROR_MESSAGE + System.getProperty("line.separator") + "Element meta-data Version: "
				+ aDataVersion + System.getProperty("line.separator") + "Application meta-data Version: "
				+ aApplVersion;
		
		R4EUpgradePlugin.Ftracer.traceError(upgraderMessage);
		final ErrorDialog dialog = new ErrorDialog(null, DIALOG_TITLE_ERROR, NO_UPGRADER_ERROR_DIALOG_TITLE + aElementMsg,
				new Status(IStatus.ERROR, R4EUpgradePlugin.PLUGIN_ID, 0, upgraderMessage, null), IStatus.ERROR);
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				dialog.open();
			}
		});
	}
	
	/**
	 * Method displayCompatibleUpgradeDialog.
	 * 
	 * @param aUpgrader
	 *            IR4EVersionUpgrader
	 * @return int
	 */
	public static int displayCompatibleUpgradeDialog(final String aElementMsg, final IR4EVersionUpgrader aUpgrader) {
		final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
			if (-1 == fUpgradeTestDialogResult) {
				final String upgraderMessage = COMPATIBILITY_COMPATIBLE_WARNING_MESSAGE + System.getProperty("line.separator") + System.getProperty("line.separator") + "Element meta-data Version: "
						+ aUpgrader.getUpgradePath().getBaseVersion() + System.getProperty("line.separator") + System.getProperty("line.separator") + "Application meta-data Version: "
						+ aUpgrader.getUpgradePath().getTargetVersion();
		
				R4EUpgradePlugin.Ftracer.traceWarning(upgraderMessage);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						final MessageDialog dialog = new MessageDialog(null, COMPATIBILITY_WARNING_DIALOG_TITLE + aElementMsg, null,
								upgraderMessage, MessageDialog.QUESTION_WITH_CANCEL,
								COMPATIBLE_UPGRADE_DIALOG_BUTTONS, 0);
						result[0] = dialog.open();
					}
				});
		} else {
			result[0] = fUpgradeTestDialogResult;
		}
		return result[0];
	}

	/**
	 * Method displayIncompatibleUpgradeDialog.
	 * 
	 * @param aUpgrader
	 *            IR4EVersionUpgrader
	 * @return int
	 */
	public static int displayIncompatibleUpgradeDialog(final String aElementMsg, final IR4EVersionUpgrader aUpgrader) {
		final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
		if (-1 == fUpgradeTestDialogResult) {
			final String upgraderMessage = COMPATIBILITY_INCOMPATIBLE_WARNING_MESSAGE + System.getProperty("line.separator") + System.getProperty("line.separator") + "Element meta-data Version: "
					+ aUpgrader.getUpgradePath().getBaseVersion() + System.getProperty("line.separator") + System.getProperty("line.separator") + "Application meta-data Version: "
					+ aUpgrader.getUpgradePath().getTargetVersion();
			R4EUpgradePlugin.Ftracer.traceWarning(upgraderMessage);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					final MessageDialog dialog = new MessageDialog(null, COMPATIBILITY_WARNING_DIALOG_TITLE + aElementMsg, null,
							upgraderMessage, MessageDialog.QUESTION_WITH_CANCEL,
							INCOMPATIBLE_UPGRADE_DIALOG_BUTTONS, 0);
					result[0] = dialog.open();
				}
			});
		} else {
			result[0] = fUpgradeTestDialogResult;
		}
		return result[0];
	
	}
	
	/**
	 * Method getVersionFromResourceFile.
	 * @param aResourceUri - URI
	 * @throws IOException
	 */
	public static String getVersionFromResourceFile(URI aResourceUri) throws IOException {
		Pattern versionPattern = Pattern.compile("fragmentVersion=\\S*\"");
		Matcher matcher = null;
		String version = null;
		
		File file = new File(aResourceUri.toFileString());
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (br.ready()) {
				String line = br.readLine();
				matcher = versionPattern.matcher(line);
				if (matcher.find()) {
					version =  matcher.group().split("=")[1].replace("\"", "");
					break;
				}
			}
			br.close();
		}
		
		if (null == version) {
			version = FRAGMENT_VERSION_DEFAULT;
		}
		return version;
	}
	
	//Test Methods
	public static void setUpgradeDialogResult(int aResult) {
		fUpgradeTestDialogResult = aResult;
	}
}
