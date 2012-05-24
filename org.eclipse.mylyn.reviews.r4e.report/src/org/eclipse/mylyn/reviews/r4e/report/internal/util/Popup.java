/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of a central location for the dialog windows
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.report.internal.util;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * @author Jacques Bouthillier
 * 
 */
public class Popup {

	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private static final String[]	FWarningButtonLabels	= { "Continue" };

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Information pop-up dialog
	 * 
	 * @param parent
	 *            : window shell
	 * @param message
	 *            : String to be displayed
	 */
	public static void info(Shell aParent, String aMessage) {
		MessageDialog.openInformation(aParent, R4EReportString
				.getString("Popup.infoTitle"), //$NON-NLS-1$
				aMessage); //$NON-NLS-1$
	}

	/**
	 * Error pop-up dialog
	 * 
	 * @param parent
	 *            : window shell
	 * @param message
	 *            : String to be displayed
	 */
	public static void error(final Shell aParent, final String aMessage) {
		// MessageDialog.openError(parent,
		// R4EReportString.getString("Popup.errorTitle"), //$NON-NLS-1$
		// message); //$NON-NLS-1$
		Runnable runnable = new Runnable() {

			public void run() {
				MessageDialog.openError(aParent, R4EReportString
						.getString("Popup.errorTitle"), //$NON-NLS-1$
						aMessage); //$NON-NLS-1$
			}
		};
		Display.getDefault().asyncExec(runnable);
	}

	/**
	 * Warning pop-up dialog
	 * 
	 * @param aParent
	 *            : window shell
	 * @param aMessage
	 *            : String to be displayed
	 */
	public static void warning(Shell aParent, String aMessage) {
		MessageDialog.openWarning(aParent, R4EReportString
				.getString("Popup.warningTitle"), //$NON-NLS-1$
				aMessage); //$NON-NLS-1$
	}

	/**
	 * Pop-up dialog to display any messages
	 * 
	 * @param parent
	 *            : window shell
	 * @param message
	 *            : String to be displayed
	 * 
	 */
	public static void displayMessage(Shell aParent, String aMessage) {
		MessageDialog.openInformation(aParent, R4EReportString
				.getString("Popup.messageTitle"), //$NON-NLS-1$
				aMessage); //$NON-NLS-1$
	}

	/**
	 * Display a warning message dialog
	 * 
	 * @param aTitle
	 *            String
	 * @param aMessage
	 *            String
	 * @return index int
	 */
	public final static int displayWarningMessageDialog(final String aTitle, final String aMessage) {

		MessageDialog dialog = new MessageDialog(null, // Shell
				aTitle, // Dialog title
				null, // Dialog title image message
				aMessage, // Dialog message
				MessageDialog.WARNING, // Dialog type
				FWarningButtonLabels, // Dialog button labels
				0 // Default index (selection)
		);

		return dialog.open();
	}

	// Dialog with details area (containing status messages)
	public final static int displayErrorDialogWithDetails(String aPluginID, String aDialogTitle, String aDialogMessage,
			String[] aStatusMessages, int aStatusType) {

		// Details
		MultiStatus info = new MultiStatus(aPluginID, 1, aDialogMessage, null);
		for (String message : aStatusMessages) {
			info.add(new Status(aStatusType, aPluginID, 1, message, null));
		}

		return ErrorDialog.openError(null, aDialogTitle, null, info);

	}

	/**
	 * Warning dialog running under a runnable
	 * 
	 * @param parent:
	 *            window shell
	 * @param message:
	 *            String to display
	 */
	public static void warningRunnable(final Shell aParent, final String aMessage) {
		Runnable runnable = new Runnable() {

			public void run() {
				MessageDialog.openWarning(aParent, R4EReportString
						.getString("Popup.warningTitle"), //$NON-NLS-1$
						aMessage); //$NON-NLS-1$
			}
		};
		Display.getDefault().asyncExec(runnable);
	}

}
