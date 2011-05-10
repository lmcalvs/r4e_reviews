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
 * This class implements the dialog used to display and select a date in a calendar
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class CalendarDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field ADD_CALENDAR_DIALOG_TITLE. (value is ""Select Date"")
	 */
	private static final String ADD_CALENDAR_DIALOG_TITLE = "Select Date";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fIncludeTime.
	 */
	private final boolean fIncludeTime;

	/**
	 * Field fCalendar.
	 */
	private DateTime fCalendar = null;

	/**
	 * Field fTime.
	 */
	private DateTime fTime = null;

	/**
	 * Field fDate.
	 */
	private GregorianCalendar fDate = null;

	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------

	/**
	 * Constructor for CalendarDialog.
	 * 
	 * @param aParentShell
	 *            Shell
	 * @param aIncludeTime
	 *            boolean
	 */
	public CalendarDialog(Shell aParentShell, boolean aIncludeTime) {
		super(aParentShell);
		setBlockOnOpen(true);
		fIncludeTime = aIncludeTime;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method configureShell.
	 * 
	 * @param shell
	 *            Shell
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(ADD_CALENDAR_DIALOG_TITLE);
	}

	/**
	 * Method buttonPressed.
	 * 
	 * @param buttonId
	 *            int
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			if (fIncludeTime) {
				fDate = new GregorianCalendar(fCalendar.getYear(), fCalendar.getMonth(), fCalendar.getDay(),
						fTime.getHours(), fTime.getMinutes());
			} else {
				fDate = new GregorianCalendar(fCalendar.getYear(), fCalendar.getMonth(), fCalendar.getDay());
			}
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Configures the dialog form and creates form content. Clients should override this method.
	 * 
	 * @param mform
	 *            the dialog form
	 */
	@Override
	protected void createFormContent(final IManagedForm mform) {

		final ScrolledForm sform = mform.getForm();
		sform.setExpandVertical(true);
		final Composite composite = sform.getBody();
		final GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		fCalendar = new DateTime(composite, SWT.CALENDAR | SWT.BORDER);
		fCalendar.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		if (fIncludeTime) {
			fTime = new DateTime(composite, SWT.TIME | SWT.SHORT | SWT.BORDER);
			fTime.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		}
	}

	/**
	 * Method getDate.
	 * 
	 * @return Date
	 */
	public Date getDate() {
		return fDate.getTime();
	}
}
