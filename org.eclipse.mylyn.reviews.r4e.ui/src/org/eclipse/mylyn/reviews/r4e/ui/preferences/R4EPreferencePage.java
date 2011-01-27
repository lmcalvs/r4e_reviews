// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.editors.FilePathEditor;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	// ------------------------------------------------------------------------
	// Contants
	// ------------------------------------------------------------------------
	
	/**
	 * Field PREFS_CONTAINER_DATA_SPAN.
	 * (value is 1)
	 */
	private static final int PREFS_CONTAINER_DATA_SPAN = 1;
	
	/**
	 * Field PREFS_CONTAINER_DATA_NUM_COLUMNS.
	 * (value is 1)
	 */
	private static final int PREFS_CONTAINER_DATA_NUM_COLUMNS = 1;
	
	/**
	 * Field R4E_PREFS_CONTAINER_DATA_SPAN.
	 * (value is 2)
	 */
	private static final int R4E_PREFS_CONTAINER_DATA_SPAN = 2; // $codepro.audit.disable constantNamingConvention
	
	/**
	 * Field R4E_PREFS_CONTAINER_DATA_NUM_COLUMNS.
	 * (value is 2)
	 */
	private static final int R4E_PREFS_CONTAINER_DATA_NUM_COLUMNS = 2; // $codepro.audit.disable constantNamingConvention
	
	/**
	 * Field R4E_GROUP_PREFS_CONTAINER_DATA_SPAN.
	 * (value is 2)
	 */
	private static final int R4E_GROUP_PREFS_CONTAINER_DATA_SPAN = 2; // $codepro.audit.disable constantNamingConvention
	
	/**
	 * Field R4E_GROUP_PREFS_CONTAINER_DATA_NUM_COLUMNS.
	 * (value is 2)
	 */
	private static final int R4E_GROUP_PREFS_CONTAINER_DATA_NUM_COLUMNS = 2; // $codepro.audit.disable constantNamingConvention
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EPreferencePage.
	 */
	public R4EPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(PreferenceConstants.P_DESC);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	@SuppressWarnings("unused")
	@Override
	public void createFieldEditors() {

		Activator.Tracer.traceInfo("Build R4E Preference page");

		//The Main preferences composite
		final Composite prefsContainer = new Composite(getFieldEditorParent(),SWT.NONE);
		final GridData prefsContainerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		prefsContainerData.horizontalSpan = PREFS_CONTAINER_DATA_SPAN;
		prefsContainer.setLayoutData(prefsContainerData);
		final GridLayout prefsLayout = new GridLayout(PREFS_CONTAINER_DATA_NUM_COLUMNS, false);
		prefsContainer.setLayout(prefsLayout);
		
		// Create a Group to hold R4E user preferences
		final Group r4EUserPrefsGroup = new Group(prefsContainer, SWT.BORDER_SOLID);
		final GridData r4eUserPrefsGroupData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4eUserPrefsGroupData.horizontalSpan = R4E_PREFS_CONTAINER_DATA_SPAN;
		r4EUserPrefsGroup.setText("User Preferences");
		r4EUserPrefsGroup.setLayoutData(r4eUserPrefsGroupData);
		r4EUserPrefsGroup.setLayout(new GridLayout(R4E_PREFS_CONTAINER_DATA_NUM_COLUMNS, false));

		//dummy spacer label
		final Label r4EUserPrefsSpacer = new Label(r4EUserPrefsGroup, SWT.FILL);
		final GridData r4EUserPrefsSpacerData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EUserPrefsSpacerData.horizontalSpan = R4E_PREFS_CONTAINER_DATA_SPAN;
		r4EUserPrefsSpacer.setLayoutData(r4EUserPrefsSpacerData);
		
		final StringFieldEditor userFieldEditor = new StringFieldEditor(PreferenceConstants.P_USER_ID, PreferenceConstants.P_USER_ID_LABEL,
				StringFieldEditor.UNLIMITED, r4EUserPrefsGroup);
		addField(userFieldEditor);
		if (R4EUIModelController.isDialogOpen()) {
			userFieldEditor.setEnabled(false, r4EUserPrefsGroup);
		} else {
			userFieldEditor.setEnabled(true, r4EUserPrefsGroup);
		}
		
		//dummy spacer label
		new Label(prefsContainer, SWT.FILL);
		
		// Create a Group to hold R4E Group preferences
		final Group r4EGroupPrefsGroup = new Group(prefsContainer, SWT.BORDER_SOLID);
		final GridData r4EGroupPrefsGroupData = new GridData(GridData.FILL, GridData.FILL, true, false);
		r4EGroupPrefsGroupData.horizontalSpan = R4E_GROUP_PREFS_CONTAINER_DATA_SPAN;
		r4EGroupPrefsGroup.setText("Group Preferences");
		r4EGroupPrefsGroup.setLayoutData(r4EGroupPrefsGroupData);
		r4EGroupPrefsGroup.setLayout(new GridLayout(R4E_GROUP_PREFS_CONTAINER_DATA_NUM_COLUMNS, false));

		//dummy spacer label
		final Label r4EGroupPrefsSpacer = new Label(r4EGroupPrefsGroup, SWT.FILL); // $codepro.audit.disable variableUsage
		
		// File Path Editor
        final String[] extensions = { PreferenceConstants.P_REVIEW_GROUP_FILE_EXT };
        final FilePathEditor groupFilesEditor = new FilePathEditor(PreferenceConstants.P_FILE_PATH, PreferenceConstants.P_FILE_PATH_LABEL, extensions, 
				r4EGroupPrefsGroup);
		addField(groupFilesEditor);
		if (R4EUIModelController.isDialogOpen()) {
			groupFilesEditor.setEnabled(false, r4EGroupPrefsGroup);
		} else { 
			groupFilesEditor.setEnabled(true, r4EGroupPrefsGroup);
		}
	}

	/**
	 * Method init.
	 * @param workbench IWorkbench
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) { // $codepro.audit.disable emptyMethod
	}
	
}