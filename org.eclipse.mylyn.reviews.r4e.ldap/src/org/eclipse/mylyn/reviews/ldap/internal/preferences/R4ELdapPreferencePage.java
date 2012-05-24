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
 * This class represents a preference page that is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we can use the field support built into JFace that allows
 * us to create a page that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can be accessed directly via the preference store.
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E LDAP project
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.ldap.internal.preferences;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.mylyn.reviews.ldap.LdapPlugin;
import org.eclipse.mylyn.reviews.ldap.internal.util.R4EString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
public class R4ELdapPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static IPreferenceStore FStore = LdapPlugin.getDefault().getPreferenceStore();
	private static final String FP_PORT_SEPARATOR = ":";

	/**
	 * Field PREFS_CONTAINER_DATA_SPAN. (value is 1)
	 */
	private static final int	PREFS_CONTAINER_DATA_SPAN					= 1;
	/**
	 * Field PREFS_CONTAINER_DATA_NUM_COLUMNS. (value is 4)
	 */
	private static final int		PREFS_CONTAINER_DATA_NUM_COLUMNS			= 4;

	/**
	 * Field R4E_PREFS_CONTAINER_DATA_SPAN. (value is 2)
	 */
	private static final int	R4E_GROUP_PREFS_SERVER_DATA_SPAN			= 2;

	/**
	 * Field R4E_GROUP_PREFS_CONTAINER_DATA_SPAN. (value is 2)
	 */
	private static final int	R4E_GROUP_PREFS_SECURITY_DATA_SPAN			= 2;
	/**
	 * Field R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS. (value is 4)
	 */
	private static final int		R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS	= 4;
	/**
	 * Field R4E_GROUP_OPTIONAL_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS. (value is 2)
	 */
	private static final int		R4E_GROUP_OPTIONAL_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS	= 2;
	

	// ------------------------------------------------------------------------
	// Member Variables
	// ------------------------------------------------------------------------
	// Server Information
	private RadioGroupFieldEditor	fLdapSelectBtn;
	private StringFieldEditor	fHostFieldEditor;
	private StringFieldEditor	fPortFieldEditor;
	private StringFieldEditor	fBaseFieldEditor;

	// Security Information
	private RadioGroupFieldEditor	fAuthenficationBtn;
	private StringFieldEditor	fUserNamedEditor;
	private StringFieldEditor	fPasswordEditor;

	// LDAP fields definition
	private StringFieldEditor	fUserIdEditor;
	private StringFieldEditor	fUserNameEditor;
	private StringFieldEditor	fEmailEditor;
	private StringFieldEditor	fTelephoneEditor;
	private StringFieldEditor	fMobileEditor;
	private StringFieldEditor	fEcnEditor;
	private StringFieldEditor	fCieEditor;
	private StringFieldEditor	fDeptEditor;
	private StringFieldEditor	fOfficeEditor;
	private StringFieldEditor	fRoomEditor;
	private StringFieldEditor	fCityEditor;
	private StringFieldEditor	fCountryEditor;
	private StringFieldEditor	fDomainEditor;
	private StringFieldEditor	fTitleEditor;
	private StringFieldEditor	fStreetAddrEditor;
	private StringFieldEditor	fPostalCodeEditor;


	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public R4ELdapPreferencePage() {
		super(GRID);
		setPreferenceStore(FStore);
		setDescription(PreferenceConstants.FP_DESC);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	@Override
	protected void createFieldEditors() {
		LdapPlugin.FTracer.traceInfo("Build R4E LDPA Preference page");

		// The Main preferences composite
		final Composite prefsContainer = new Composite(getFieldEditorParent(), SWT.NONE);
		final GridData prefsContainerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		prefsContainerData.horizontalSpan = PREFS_CONTAINER_DATA_SPAN;
		prefsContainer.setLayoutData(prefsContainerData);
		final GridLayout prefsLayout = new GridLayout(PREFS_CONTAINER_DATA_NUM_COLUMNS, false);
		prefsContainer.setLayout(prefsLayout);

		// Create the Server information area
		createServerInformation(prefsContainer);

		// Create the Security information area
		createSecurityInformation(prefsContainer);

		// Create the LDAP Fields definition information area
		createFieldDefinitionInformation(prefsContainer);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/**
	 * Method performOk.
	 * 
	 * @return boolean
	 * @see org.eclipse.jface.preference.IPreferencePage#performOk()
	 */
	@Override
	public boolean performOk() {

		/**
		 * Need to store the radio selection before using the parent store (store.performOK())
		 */

		// Server Information
		fLdapSelectBtn.store();

		// Security information
		// Need to store the radio button selection
		fAuthenficationBtn.store();

		// LDAP Fields definition

		// For field editors, all are saved with performOk() method
		return super.performOk();
	}

	/**
	 * The field editor preference page implementation of a <code>PreferencePage</code> method loads all the field
	 * editors with their default values.
	 */
	public void performDefaults() {
		// Need to handle in extra the radio button
		// Server Information
		if (fLdapSelectBtn != null) {
			fLdapSelectBtn.setPreferenceStore(getPreferenceStore());
			fLdapSelectBtn.loadDefault();
		}

		// Security information
		if (fAuthenficationBtn != null) {
			fAuthenficationBtn.setPreferenceStore(getPreferenceStore());
			fAuthenficationBtn.loadDefault();
		}

		super.performDefaults();
	}

	/**
	 * Create the Server information GUI
	 * 
	 * @param Composite
	 *            aPrefsContainer
	 */
	private void createServerInformation(Composite aPrefsContainer) {
		// Create a Group to hold LDAP user preferences
		final Group r4ELdapHoostPrefsGroup = new Group(aPrefsContainer, SWT.BORDER_SOLID);
		final GridData r4eLdapHostPrefsGroupData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4eLdapHostPrefsGroupData.horizontalSpan = R4E_GROUP_PREFS_SERVER_DATA_SPAN;
		r4ELdapHoostPrefsGroup.setText(PreferenceConstants.FP_SERVER_GROUP);
		r4ELdapHoostPrefsGroup.setLayoutData(r4eLdapHostPrefsGroupData);
		r4ELdapHoostPrefsGroup.setLayout(new GridLayout(R4E_GROUP_PREFS_SERVER_DATA_SPAN, false));

		// dummy spacer label
		final Label r4ELdapPrefsSpacer = new Label(r4ELdapHoostPrefsGroup, SWT.FILL);
		final GridData r4EUserPrefsSpacerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EUserPrefsSpacerData.horizontalSpan = R4E_GROUP_PREFS_SERVER_DATA_SPAN;
		r4ELdapPrefsSpacer.setLayoutData(r4EUserPrefsSpacerData);

		// Create Radio buttons to define the type of Server
		int numberRadio = 2;
		fLdapSelectBtn = new RadioGroupFieldEditor(PreferenceConstants.FP_SERVER_TYPE_ID,
				PreferenceConstants.FP_SERVER_TYPE_LABEL, numberRadio, PreferenceConstants.FP_SERVER_TYPE_VALUES,
				r4ELdapHoostPrefsGroup, false);
		fLdapSelectBtn.setPreferenceStore(getPreferenceStore());

		fLdapSelectBtn.load();

		// Test fields definitions
		fHostFieldEditor = new StringFieldEditor(PreferenceConstants.FP_HOST_ID,
				PreferenceConstants.FP_HOST_LABEL, StringFieldEditor.UNLIMITED, r4ELdapHoostPrefsGroup);
		fHostFieldEditor.getTextControl(r4ELdapHoostPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", R4EString.getString("fhostTooltip")));
		addField(fHostFieldEditor);


		fPortFieldEditor = new StringFieldEditor(PreferenceConstants.FP_PORT_ID,
				PreferenceConstants.FP_PORT_LABEL, StringFieldEditor.UNLIMITED, r4ELdapHoostPrefsGroup);
		fPortFieldEditor.getTextControl(r4ELdapHoostPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", R4EString.getString("fPortTooltip")));
		addField(fPortFieldEditor);

		fBaseFieldEditor = new StringFieldEditor(PreferenceConstants.FP_BASE_ID,
				PreferenceConstants.FP_BASE_LABEL, StringFieldEditor.UNLIMITED, r4ELdapHoostPrefsGroup);
		fBaseFieldEditor.getTextControl(r4ELdapHoostPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", R4EString.getString("fBaseTooltip")));
		addField(fBaseFieldEditor);

	}

	/**
	 * Create the Security information GUI
	 * 
	 * @param Composite
	 *            aPrefsContainer
	 */
	private void createSecurityInformation(Composite aPrefsContainer) {
		// Create a Group to hold R4E Security preferences
		final Group r4ESecurityPrefsGroup = new Group(aPrefsContainer, SWT.BORDER_SOLID);
		final GridData r4EGroupPrefsGroupData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EGroupPrefsGroupData.horizontalSpan = R4E_GROUP_PREFS_SECURITY_DATA_SPAN;
		r4ESecurityPrefsGroup.setText(PreferenceConstants.FP_SECURITY_GROUP);
		r4ESecurityPrefsGroup.setLayoutData(r4EGroupPrefsGroupData);
		r4ESecurityPrefsGroup.setLayout(new GridLayout(R4E_GROUP_PREFS_SECURITY_DATA_SPAN, false));

		// dummy spacer label
		Label r4ESecurityGroupPrefsSpacer = new Label(r4ESecurityPrefsGroup, SWT.FILL);

		// Create Radio buttons to define the type of AUTHENTICATION
		int numberRadio = 3;
		fAuthenficationBtn = new RadioGroupFieldEditor(PreferenceConstants.FP_SECURITY_AUTHENTICATION_ID,
				PreferenceConstants.FP_SECURITY_AUTHENTICATION_LABEL, numberRadio,
				PreferenceConstants.FP_AUTHENTICATION_RADIO_VALUES, r4ESecurityPrefsGroup, false);
		fAuthenficationBtn.setPreferenceStore(getPreferenceStore());
		fAuthenficationBtn.load();

		// dummy spacer label
		Label r4EFieldPrefsSpacer = new Label(r4ESecurityPrefsGroup, SWT.FILL);

		final GridData r4EFieldPrefsSpacerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EFieldPrefsSpacerData.horizontalSpan = R4E_GROUP_PREFS_SECURITY_DATA_SPAN;
		r4EFieldPrefsSpacer.setLayoutData(r4EFieldPrefsSpacerData);

		final GridData r4EGroupPrefsSpacerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EGroupPrefsSpacerData.horizontalSpan = R4E_GROUP_PREFS_SECURITY_DATA_SPAN;
		r4ESecurityGroupPrefsSpacer.setLayoutData(r4EGroupPrefsSpacerData);

		// Test fields definitions
		fUserNamedEditor = new StringFieldEditor(PreferenceConstants.FP_SECURITY_USER_NAME_ID,
				PreferenceConstants.FP_SECURITY_USER_NAME_LABEL, StringFieldEditor.UNLIMITED, r4ESecurityPrefsGroup);
		fUserNamedEditor.getTextControl(r4ESecurityPrefsGroup).setToolTipText(R4EString.getString("fUserNameTooltip"));
		addField(fUserNamedEditor);

		fPasswordEditor = new StringFieldEditor(PreferenceConstants.FP_SECURITY_PASSWORD_ID,
				PreferenceConstants.FP_SECURITY_PASSWORD_LABEL, StringFieldEditor.UNLIMITED, r4ESecurityPrefsGroup);
		addField(fPasswordEditor);
		fPasswordEditor.getTextControl(r4ESecurityPrefsGroup).addModifyListener(new ModifyListener() {

			// Echo * when typing a new password
			public void modifyText(ModifyEvent e) {
				fPasswordEditor.getTextControl(r4ESecurityPrefsGroup).setEchoChar('*');
			}
		});
	}

	/**
	 * Create the LDAP fields description information GUI
	 * 
	 * @param Composite
	 *            aPrefsContainer
	 */
	private void createFieldDefinitionInformation(Composite aPrefsContainer) {
		// Create a Group to hold R4E LDAP Field preferences
		final Group r4EFieldPrefsGroup = new Group(aPrefsContainer, SWT.BORDER_SOLID);
		final GridData r4EFieldPrefsGroupData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EFieldPrefsGroupData.horizontalSpan = R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS;
		r4EFieldPrefsGroup.setText(PreferenceConstants.FP_FIELD_GROUP);
		r4EFieldPrefsGroup.setLayoutData(r4EFieldPrefsGroupData);
		r4EFieldPrefsGroup.setLayout(new GridLayout(R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS, false));

		// dummy spacer label
		Label r4EFieldPrefsSpacer = new Label(r4EFieldPrefsGroup, SWT.FILL);
		final GridData r4EFieldPrefsSpacerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EFieldPrefsSpacerData.horizontalSpan = R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS;
		r4EFieldPrefsSpacer.setLayoutData(r4EFieldPrefsSpacerData);

		// Create a Group to hold R4E LDAP Mandatory Field preferences
		final Group r4EFieldMandatoryPrefsGroup = new Group(r4EFieldPrefsGroup, SWT.BORDER_SOLID);
		final GridData r4EFieldMandatoryPrefsGroupData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EFieldMandatoryPrefsGroupData.horizontalSpan = R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS;
		r4EFieldMandatoryPrefsGroup.setText(PreferenceConstants.FP_FIELD_MANDATORY_GROUP);
		r4EFieldMandatoryPrefsGroup.setLayoutData(r4EFieldMandatoryPrefsGroupData);
		r4EFieldMandatoryPrefsGroup.setLayout(new GridLayout(R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS, false));

		fUserIdEditor = new StringFieldEditor(PreferenceConstants.FP_UID_ID,
				PreferenceConstants.FP_UID_LABEL, StringFieldEditor.UNLIMITED, r4EFieldMandatoryPrefsGroup);
		fUserIdEditor.setEmptyStringAllowed(false);
		fUserIdEditor.getTextControl(r4EFieldMandatoryPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_UID_ID));
		addField(fUserIdEditor);

		fUserNameEditor = new StringFieldEditor(PreferenceConstants.FP_NAME_ID,
				PreferenceConstants.FP_NAME_LABEL, StringFieldEditor.UNLIMITED, r4EFieldMandatoryPrefsGroup);
		fUserNameEditor.setEmptyStringAllowed(false);
		fUserNameEditor.getTextControl(r4EFieldMandatoryPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_NAME_ID));
		addField(fUserNameEditor);

		fEmailEditor = new StringFieldEditor(PreferenceConstants.FP_EMAIL_ID,
				PreferenceConstants.FP_EMAIL_LABEL, StringFieldEditor.UNLIMITED, r4EFieldMandatoryPrefsGroup);
		fEmailEditor.setEmptyStringAllowed(false);
		fEmailEditor.getTextControl(r4EFieldMandatoryPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_EMAIL_ID));
		addField(fEmailEditor);

		// dummy spacer label
		Label r4EFieldPrefsSpacer2 = new Label(r4EFieldPrefsGroup, SWT.FILL);
		final GridData r4EFieldPrefsSpacer2Data = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EFieldPrefsSpacer2Data.horizontalSpan = R4E_GROUP_MANDATORY_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS;
		r4EFieldPrefsSpacer2.setLayoutData(r4EFieldPrefsSpacer2Data);

		// Create a Group to hold R4E LDAP Optional Field preferences
		final Group r4EFieldOptionalPrefsGroup = new Group(r4EFieldPrefsGroup, SWT.BORDER_SOLID);
		final GridData r4EFieldOptionalPrefsGroupData = new GridData(SWT.FILL, SWT.FILL, true, false);

		r4EFieldOptionalPrefsGroupData.horizontalSpan = R4E_GROUP_OPTIONAL_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS;
		r4EFieldOptionalPrefsGroup.setText(PreferenceConstants.FP_FIELD_OPTIONAL_GROUP);
		r4EFieldOptionalPrefsGroup.setLayoutData(r4EFieldOptionalPrefsGroupData);
		r4EFieldOptionalPrefsGroup
				.setLayout(new GridLayout(R4E_GROUP_OPTIONAL_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS, false));

		fTelephoneEditor = new StringFieldEditor(PreferenceConstants.FP_TELEPHONE_ID,
				PreferenceConstants.FP_TELEPHONE_LABEL, StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fTelephoneEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_TELEPHONE_ID));
		addField(fTelephoneEditor);

		fMobileEditor = new StringFieldEditor(PreferenceConstants.FP_MOBILE_ID, PreferenceConstants.FP_MOBILE_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fMobileEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_MOBILE_ID));
		addField(fMobileEditor);

		fEcnEditor = new StringFieldEditor(PreferenceConstants.FP_ECN_ID, PreferenceConstants.FP_ECN_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fEcnEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_ECN_ID));
		addField(fEcnEditor);

		fCieEditor = new StringFieldEditor(PreferenceConstants.FP_COMPANY_ID, PreferenceConstants.FP_COMPANY_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fCieEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_COMPANY_ID));
		addField(fCieEditor);

		fDeptEditor = new StringFieldEditor(PreferenceConstants.FP_DEPARTMENT_ID,
				PreferenceConstants.FP_DEPARTMENT_LABEL, StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fDeptEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_DEPARTMENT_ID));
		addField(fDeptEditor);

		fDomainEditor = new StringFieldEditor(PreferenceConstants.FP_DOMAIN_ID, PreferenceConstants.FP_DOMAIN_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fDomainEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_DOMAIN_ID));
		addField(fDomainEditor);

		fTitleEditor = new StringFieldEditor(PreferenceConstants.FP_TITLE_ID, PreferenceConstants.FP_TITLE_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup);
		fTitleEditor.getTextControl(r4EFieldOptionalPrefsGroup).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_TITLE_ID));
		addField(fTitleEditor);

		// Create a second optional group to maintain the remaining LDAP fields definition
		final Group r4EFieldOptionalPrefsGroup2 = new Group(r4EFieldPrefsGroup, SWT.BORDER_SOLID);
		final GridData r4EFieldOptionalPrefsGroupRightData2 = new GridData(GridData.FILL, GridData.FILL, true,
				false);

		r4EFieldOptionalPrefsGroupRightData2.horizontalSpan = R4E_GROUP_OPTIONAL_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS;
		r4EFieldOptionalPrefsGroup2.setText(PreferenceConstants.FP_FIELD_OPTIONAL_GROUP);
		r4EFieldOptionalPrefsGroup2.setLayoutData(r4EFieldOptionalPrefsGroupRightData2);
		r4EFieldOptionalPrefsGroup2.setLayout(new GridLayout(R4E_GROUP_OPTIONAL_PREFS_FIELDS_DEF_DATA_NUM_COLUMNS,
				false));

		// Fields editor
		fOfficeEditor = new StringFieldEditor(PreferenceConstants.FP_OFFICE_NAME_ID,
				PreferenceConstants.FP_OFFICE_ROOM_Label, StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup2);
		fOfficeEditor.getTextControl(r4EFieldOptionalPrefsGroup2).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_OFFICE_NAME_ID));
		addField(fOfficeEditor);

		fRoomEditor = new StringFieldEditor(PreferenceConstants.FP_ROOM_NUMBER_ID,
				PreferenceConstants.FP_ROOM_NUMBER_LABEL, StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup2);
		fRoomEditor.getTextControl(r4EFieldOptionalPrefsGroup2).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_ROOM_NUMBER_ID));
		addField(fRoomEditor);

		fStreetAddrEditor = new StringFieldEditor(PreferenceConstants.FP_STREET_ADDRESS_ID,
				PreferenceConstants.FP_STREET_ADDRESS_LABEL, StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup2);
		fStreetAddrEditor.getTextControl(r4EFieldOptionalPrefsGroup2).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_STREET_ADDRESS_ID));
		addField(fStreetAddrEditor);

		fCityEditor = new StringFieldEditor(PreferenceConstants.FP_CITY_ID, PreferenceConstants.FP_CITY_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup2);
		fCityEditor.getTextControl(r4EFieldOptionalPrefsGroup2).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_CITY_ID));
		addField(fCityEditor);

		fCountryEditor = new StringFieldEditor(PreferenceConstants.FP_COUNTRY_ID, PreferenceConstants.FP_COUNTRY_LABEL,
				StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup2);
		fCountryEditor.getTextControl(r4EFieldOptionalPrefsGroup2).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_COUNTRY_ID));
		addField(fCountryEditor);

		fPostalCodeEditor = new StringFieldEditor(PreferenceConstants.FP_POSTAL_CODE_ID,
				PreferenceConstants.FP_POSTAL_CODE_LABEL, StringFieldEditor.UNLIMITED, r4EFieldOptionalPrefsGroup2);
		fPostalCodeEditor.getTextControl(r4EFieldOptionalPrefsGroup2).setToolTipText(
				R4EString.getFormattedString("defaultDescription", PreferenceConstants.FP_POSTAL_CODE_ID));
		addField(fPostalCodeEditor);

	}

	/********************************* */
	/*                                 */
	/* Read the preference information */
	/*                                 */
	/********************************* */

	/**
	 * Get the Host Id ex: hostname.server
	 * 
	 * @return String
	 */
	public String getHostId() {
		return FStore.getString(PreferenceConstants.FP_HOST_ID);
	}

	/**
	 * Get the Port Id
	 * 
	 * @return String
	 */
	public String getPortId() {
		return FStore.getString(PreferenceConstants.FP_PORT_ID);
	}

	/**
	 * Get the Base DN Id ex: "DC=cie,DC=se"
	 * 
	 * @return String
	 */
	public String getBaseId() {
	 return FStore.getString(PreferenceConstants.FP_BASE_ID);
	}

	/**
	 * Get the server type uri ex: ldap://, ldaps://
	 * 
	 * @return String
	 */
	public String getServerType() {
		return FStore.getString(PreferenceConstants.FP_SERVER_TYPE_ID);
	}

	/**
	 * Build the server information from the host and the port Create a string representing the URI. ex:
	 * ldap://hostname.server:port
	 * 
	 * @return String
	 * @throws IOException
	 */
	public String getServerInfo() throws IOException {
		String host = getHostId();
		String port = getPortId();
		StringBuilder sb = new StringBuilder();
		sb.append(getServerType());
		sb.append(host);
		sb.append(FP_PORT_SEPARATOR);
		sb.append(port);
		if (host.equals("") || port.equals("")) {
			LdapPlugin.FTracer.traceWarning("Warning " + "Host or port is empty");

			throw new IOException(R4EString.getString("messageError1") + R4EString.getString("noHostOrPort"));
		}
		return sb.toString();
	}

	/**
	 * Get the Security section fields. It could be "none", "simple" or "strong"
	 * 
	 * @return String
	 */

	public String getAuthentication () {
		return FStore.getString(PreferenceConstants.FP_SECURITY_AUTHENTICATION_ID);
	}

	/**
	 * Get the user name to log to the LDAP database if needed depending of the Authentication selected. It can be
	 * domain\\userId
	 * 
	 * @return String
	 */
	public String getUserName() {
		return FStore.getString(PreferenceConstants.FP_SECURITY_USER_NAME_ID);
	}

	/**
	 * Get the user password to log to the LDAP database if needed depending of the Authentication selected.
	 * 
	 * @return String
	 */
	public String getPassword() {
		String encodedPwd = "";
		try {
			encodedPwd = encodePassword();
		} catch (UnsupportedEncodingException aE) {
			LdapPlugin.getDefault().logError(R4EString.getString("messageError2"), aE);

		} catch (NoSuchAlgorithmException aE) {
			LdapPlugin.getDefault().logError(R4EString.getString("messageError3"), aE);
		}
		return encodedPwd;
	}

	/**
	 * Get the LDAP fields description
	 */

	/**
	 * Get the user id field definition defined in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldUserId() {
	 return FStore.getString(PreferenceConstants.FP_UID_ID);
	}

	/**
	 * Get the user name field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldUserName() {
	 return FStore.getString(PreferenceConstants.FP_NAME_ID);
	}

	/**
	 * Get the e-mail id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldEmail() {
	 return FStore.getString(PreferenceConstants.FP_EMAIL_ID);
	}

	/**
	 * Get the telephone id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldTelephone() {
		return FStore.getString(PreferenceConstants.FP_TELEPHONE_ID);
	}

	/**
	 * Get the mobile id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldMobile() {
		return FStore.getString(PreferenceConstants.FP_MOBILE_ID);
	}

	/**
	 * Get the ECN id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldECN() {
		return FStore.getString(PreferenceConstants.FP_ECN_ID);
	}

	/**
	 * Get the company id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldCompany() {
		return FStore.getString(PreferenceConstants.FP_COMPANY_ID);
	}

	/**
	 * Get the department id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldDepartment() {
		return FStore.getString(PreferenceConstants.FP_DEPARTMENT_ID);
	}

	/**
	 * Get the office name id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldOfficeName() {
		return FStore.getString(PreferenceConstants.FP_OFFICE_NAME_ID);
	}

	/**
	 * Get the room id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldRoom() {
		return FStore.getString(PreferenceConstants.FP_ROOM_NUMBER_ID);
	}

	/**
	 * Get the city name id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldCity() {
		return FStore.getString(PreferenceConstants.FP_CITY_ID);
	}

	/**
	 * Get the country name id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldCountry() {
		return FStore.getString(PreferenceConstants.FP_COUNTRY_ID);
	}

	/**
	 * Get the domain name id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldDomain() {
		return FStore.getString(PreferenceConstants.FP_DOMAIN_ID);
	}

	/**
	 * Get the title name id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldTitle() {
		return FStore.getString(PreferenceConstants.FP_TITLE_ID);
	}

	/**
	 * Get the street address id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldStreetAddress() {
		return FStore.getString(PreferenceConstants.FP_STREET_ADDRESS_ID);
	}

	/**
	 * Get the postal code id field definition in the current LDAP.
	 * 
	 * @return String
	 */
	public String getFieldPostalCode() {
		return FStore.getString(PreferenceConstants.FP_POSTAL_CODE_ID);
	}

	/**
	 * Encode the user password uses to connect to LDAP.
	 * 
	 * @return String
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private String encodePassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// String algorith = "SHA";
		// String algorith = "MD5";
		// String algorith = "SHA-1";
		
		String storePwd = FStore.getString(PreferenceConstants.FP_SECURITY_PASSWORD_ID);

		// If we return the password with not SHA encoding
		// Activator.FTracer.traceInfo("Info: " + storePwd + "\n\t UTF-8: " + storePwd.getBytes("UTF-8"));

		// Not encrypted
		return storePwd;

		 // ENCRYPTED password
		// MessageDigest mesDigest = MessageDigest.getInstance(algorith);
		// mesDigest.update(FStore.getString(PreferenceConstants.FP_SECURITY_PASSWORD_ID).getBytes("UTF-8"));
		// byte[] bytes = mesDigest.digest();
		// Activator.FTracer.traceInfo("Info: SHA bytes length:  " + bytes.length);
		//
		// // Debug purpose
		// // for (int i = 0; i < bytes.length; i++) {
		// // System.out.print(bytes[i] + " ");
		// // }
		// // System.out.println("");
		//
		// // Convert from UTF-8 to String
		// String shaStr = Convert.fromUTF8(bytes);
		// // Activator.FTracer.traceInfo("Info SHA init str: " + shaStr);
		//
		// // Print the value in Base64 encoding
		// Base64 base64 = new Base64();
		// byte[] encodeByte = base64.encode(bytes);
		//
		// // Debug purpose
		// // System.out.println("Uncoded bytes:");
		// // for (int i = 0; i < encodeByte.length; i++) {
		// // System.out.print(encodeByte[i] + " ");
		// // }
		// // System.out.println("");
		//
		// // Convert from UTF-8 to String
		// String generatedStr = Convert.fromUTF8(encodeByte);
		// Activator.FTracer.traceInfo("Info encode str: " + generatedStr);
		// String encodeString = '{' + algorith + '}' + generatedStr;
		// Activator.FTracer.traceInfo("Info: pwd: " + encodeString);
		//
		// return encodeString;
	}

	/************ */
	/*            */
	/* Set method */
	/*            */
	/************ */

	/**
	 * Set the Host Id ex: hostname.server
	 * 
	 */
	public void setHostId(String aHost) {
		FStore.setValue(PreferenceConstants.FP_HOST_ID, aHost);
	}

	/**
	 * Set the Port Id
	 * 
	 */
	public void setPortId(String aPort) {
		FStore.setValue(PreferenceConstants.FP_PORT_ID, aPort);
	}

	/**
	 * Set the Base DN Id ex: "DC=cie,DC=se"
	 * 
	 */
	public void setBaseId(String aBase) {
		FStore.setValue(PreferenceConstants.FP_BASE_ID, aBase);
	}

	/**
	 * Set the server type uri ex: ldap://, ldaps://
	 * 
	 */
	public void setServerType(String aServerType) {
		FStore.setValue(PreferenceConstants.FP_SERVER_TYPE_ID, aServerType);
	}

	/**
	 * Set the Security section fields. It could be "none", "simple" or "strong"
	 * 
	 */

	public void setAuthentication(String aAuthentication) {
		FStore.setValue(PreferenceConstants.FP_SECURITY_AUTHENTICATION_ID, aAuthentication);
	}

	/**
	 * Set the user name to log to the LDAP database if needed depending of the Authentication selected. It can be
	 * domain\\userId
	 * 
	 */
	public void setUserName(String aUserName) {
		FStore.setValue(PreferenceConstants.FP_SECURITY_USER_NAME_ID, aUserName);
	}

	/**
	 * Set the user password to log to the LDAP database if needed depending of the Authentication selected.
	 * 
	 */
	public void setPassword(String aPasswd) {
		FStore.setValue(PreferenceConstants.FP_SECURITY_PASSWORD_ID, aPasswd);
	}

	/**
	 * @param args
	 */
	public void main(String[] args) {
	}

}
