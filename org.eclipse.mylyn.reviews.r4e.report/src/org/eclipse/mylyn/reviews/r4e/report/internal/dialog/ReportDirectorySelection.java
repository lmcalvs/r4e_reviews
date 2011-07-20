/*******************************************************************************
 * Copyright (c) 2011 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of R4E Report directory selection
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.report.internal.dialog;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Jacques Bouthillier
 * 
 */
public class ReportDirectorySelection extends Dialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final int fWIDTH = 400;

	private final int fHEIGHT = 150;
	
	private final String fTITLE = "R4E Directory selection";
	
	private final String fOkTooltip = "Consider the new directory as the valid one";

	private final String fCancelTooltip = "Return to the old directory";
	
	private final String fStorage = "storage";
	
	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------
	private File fReportFolder = null;
	
	private static Button ok;

	private static Button cancel;

	private DirectoryFieldEditor dirFieldEditor;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * 
	 */
	public ReportDirectorySelection(Shell aParentShell) {
		super(aParentShell);
		// setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MODELESS);
		setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE);
	}
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite aParent) {

		getShell().setText(fTITLE);
		final Composite composite = (Composite) super.createDialogArea(aParent);

		// Set the minimum size for the window
		composite.getShell().setMinimumSize(fWIDTH, fHEIGHT);

		// Set the window data
		createDirSelectionWindow(composite);

		return composite;
	}

	protected void createButtonsForButtonBar(Composite aParent) {
		ok = createButton(aParent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, false);
		cancel = createButton(aParent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		ok.setToolTipText(fOkTooltip);
		cancel.setToolTipText(fCancelTooltip);
	}

	protected void buttonPressed(int aButtonId) {

		// Ok button selected
		if (aButtonId == IDialogConstants.OK_ID) {
			File fi = getFieldDirectory();
			setReportDirectory(fi);
			super.close();
		}

		// Cancel Button selected
		if (aButtonId == IDialogConstants.CANCEL_ID) {
			super.close();
		}
	}

	/**
	 * Creates message info content window.
	 * 
	 * @param aParent
	 *            of the composite.
	 */
	private void createDirSelectionWindow(Composite aParent) {

		dirFieldEditor = new DirectoryFieldEditor(
				fStorage, " &Directory:", aParent);
		setTextIntoGrid(aParent);
	}

	// Fills the field editor's controls into the given parent.
	private void setTextIntoGrid(Composite aPparent) {
		final Text txt = dirFieldEditor.getTextControl(aPparent);

		txt.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				// Set the text also in the tooltip
				txt.setToolTipText(txt.getText());
			}
		});

		GridData gridData = new GridData();

		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;

		txt.setLayoutData(gridData);
	}

	public void setFieldDirectory(String aSt) {
		dirFieldEditor.setStringValue(aSt);
	}

	private File getFieldDirectory() {
		String st = dirFieldEditor.getStringValue();
		return new File(st);
	}

	private void setReportDirectory(File afi) {
		fReportFolder = afi;
	}
	
	public File getReportDirectory () {
		return fReportFolder;
	}
	
}
