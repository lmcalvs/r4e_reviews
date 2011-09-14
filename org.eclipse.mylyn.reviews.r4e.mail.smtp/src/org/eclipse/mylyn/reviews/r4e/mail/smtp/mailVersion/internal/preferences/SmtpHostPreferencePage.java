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
 * This class represents a preference page that is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace that allows
 * us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can be accessed directly via the preference store.
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review SMTP Host preference page
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.preferences;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.SmtpPlugin;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.SMTPHostString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Jacques Bouthillier
 */
public class SmtpHostPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static IPreferenceStore FStore = null;

	private String fLIST_SEPARATOR = ":";

	/**
	 * Field PREFS_CONTAINER_DATA_SPAN. (value is 1)
	 */
	private static final int FPREFS_CONTAINER_DATA_SPAN = 1;
	/**
	 * Field PREFS_CONTAINER_DATA_NUM_COLUMNS. (value is 4)
	 */
	private static final int FPREFS_CONTAINER_DATA_NUM_COLUMNS = 4;

	/**
	 * Field R4E_PREFS_CONTAINER_DATA_SPAN. (value is 2)
	 */
	private static final int FGROUP_PREFS_SERVER_DATA_SPAN = 2;

	// ------------------------------------------------------------------------
	// Member Variables
	// ------------------------------------------------------------------------

	private ListEditor fserverlListBox;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public SmtpHostPreferencePage() {
		super(GRID);
		FStore = SmtpPlugin.getDefault().getPreferenceStore();
		setPreferenceStore(FStore);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	@Override
	protected void createFieldEditors() {

		// The Main preferences composite
		final Composite prefsContainer = new Composite(getFieldEditorParent(),
				SWT.NONE);
		final GridData prefsContainerData = new GridData(GridData.FILL,
				GridData.FILL, true, false);
		prefsContainerData.horizontalSpan = FPREFS_CONTAINER_DATA_SPAN;
		prefsContainer.setLayoutData(prefsContainerData);
		final GridLayout prefsLayout = new GridLayout(
				FPREFS_CONTAINER_DATA_NUM_COLUMNS, false);
		prefsContainer.setLayout(prefsLayout);

		// Create the Server host information area
		createServerHostInformation(prefsContainer);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * Create the Server host information GUI
	 * 
	 * @param Composite
	 *            aPrefsContainer
	 */
	private void createServerHostInformation(Composite aPrefsContainer) {
		// Create a Group to hold SMTP user preferences
		final Group smtpHostPrefsGroup = new Group(aPrefsContainer,
				SWT.BORDER_SOLID);
		final GridData smtpHostPrefsGroupData = new GridData(GridData.FILL,
				GridData.FILL, true, false);
		smtpHostPrefsGroupData.horizontalSpan = FGROUP_PREFS_SERVER_DATA_SPAN;
		smtpHostPrefsGroup
				.setText(PreferenceConstants.FP_SERVER_HOST_GROUP);
		smtpHostPrefsGroup.setLayoutData(smtpHostPrefsGroupData);
		smtpHostPrefsGroup.setLayout(new GridLayout(
				FGROUP_PREFS_SERVER_DATA_SPAN, false));

		// dummy spacer label
		final Label smtpPrefsSpacer = new Label(smtpHostPrefsGroup,
				SWT.FILL);
		final GridData smtpPrefsSpacerData = new GridData(GridData.FILL,
				GridData.FILL, true, false);
		smtpPrefsSpacerData.horizontalSpan = FGROUP_PREFS_SERVER_DATA_SPAN;
		smtpPrefsSpacer.setLayoutData(smtpPrefsSpacerData);

		fserverlListBox = new ListEditor(
				PreferenceConstants.FP_SMTP_SERVER_LIST_ID,
				PreferenceConstants.FP_SMTP_SERVER_LABEL,
				smtpHostPrefsGroup) {

			@Override
			protected String createList(String[] aItems) {
				StringBuilder sb = new StringBuilder();
				int itemSize = aItems.length;
				for (int i = 0; i < itemSize; i++) {
					sb.append(aItems[i]);
					if (i + 1 < itemSize) {
						sb.append(fLIST_SEPARATOR);
					}
				}
				return sb.toString();
			}

			@Override
			protected String getNewInputObject() {
				// New button selected

				InputDialog dialog = new InputDialog(getShell(),
						SMTPHostString.getString("smtp_pref_title"),
						SMTPHostString.getString("smtp_pref_dialog_msg"),
						"",
						SmtpInputValidator());
				dialog.create();
				dialog.open();
				String text = dialog.getValue();
				return text;

			}

			private IInputValidator SmtpInputValidator() {
				// No validation yet
				return null;
			}

			@Override
			protected String[] parseString(String aStringList) {
				String[] star = aStringList.split(fLIST_SEPARATOR);
				return star;
			}
		};

		addField(fserverlListBox);

	}

	/**
	 * Get the SMTP server information from store information
	 * 
	 * @return String []
	 */
	public String[] getSmtpServer() {
		String listServerStr = FStore
				.getString(PreferenceConstants.FP_SMTP_SERVER_LIST_ID);
		String[] star = listServerStr.split(fLIST_SEPARATOR);

		return star;
	}

}
