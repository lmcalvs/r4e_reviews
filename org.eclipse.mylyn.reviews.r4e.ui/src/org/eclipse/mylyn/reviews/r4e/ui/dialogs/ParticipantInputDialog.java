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
 * This class implements the dialog used to fill-in the Participant element details.
 *  This is a modal dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.utils.EditableListWidget;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ParticipantInputDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field ADD_PARTICIPANT_DIALOG_TITLE.
	 * (value is ""Enter Review details"")
	 */
	private static final String ADD_PARTICIPANT_DIALOG_TITLE = "Enter Participant Details";
	
	/**
	 * Field ADD_PARTICIPANT_ID_DIALOG_VALUE.
	 * (value is ""Participant Id: "")
	 */
	private static final String ADD_PARTICIPANT_ID_DIALOG_VALUE = "Id: ";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fParticipantIdValue.
	 */
    private String fParticipantIdValue = "";
    
	/**
	 * Field fParticipantIdInputTextField.
	 */
    private Text fParticipantIdInputTextField;
    
	/**
	 * Field fAvailableComponents.
	 */
	private EditableListWidget fRoleTypes = null;
	
	/**
	 * Field fRolesValue.
	 */
	private List<R4EUserRole> fRolesValue = null;
	
	/**
	 * Field fFocusAreaValue.
	 */
    private String fFocusAreaValue = "";
    
	/**
	 * Field fFocusAreaTextField.
	 */
    private Text fFocusAreaTextField;
    
    /**
     * The input validator, or <code>null</code> if none.
     */
    private final IInputValidator fValidator;
    
    
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ParticipantInputDialog.
	 * @param aParentShell Shell
	 */
	public ParticipantInputDialog(Shell aParentShell) {
		super(aParentShell);
    	setBlockOnOpen(true);
		fValidator = new R4EInputValidator();
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
    /**
     * Method buttonPressed.
     * @param buttonId int
     * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
     */
    @Override
	protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
	    	this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
        	
        	//Validate Participant Id
        	String validateResult = validateEmptyInput(fParticipantIdInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "No input given for Participant Id",
        				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
    			return;
        	}
        	fParticipantIdValue = fParticipantIdInputTextField.getText();
        	
        	//Validate Roles (optional)
        	fRolesValue = new ArrayList<R4EUserRole>();
			for (Item item : fRoleTypes.getItems()) {
		    	//Review type (no validation needed as this is a read-only combo box
		        if (item.getText().equals(R4EUIConstants.USER_ROLE_LEAD)) {
		        	fRolesValue.add(R4EUserRole.R4E_ROLE_LEAD);
		        }
		        else if (item.getText().equals(R4EUIConstants.USER_ROLE_AUTHOR)) {
		        	fRolesValue.add(R4EUserRole.R4E_ROLE_AUTHOR);
		        }
		        else if (item.getText().equals(R4EUIConstants.USER_ROLE_REVIEWER)) {
		        	fRolesValue.add(R4EUserRole.R4E_ROLE_REVIEWER);
		        }
			}
			
        	//Validate Focus Area (optional)
        	validateResult = validateEmptyInput(fFocusAreaTextField);
        	if (null == validateResult) {
        		fFocusAreaValue = fFocusAreaTextField.getText();
        	}
        } else {
        	fParticipantIdValue = null;
        	fFocusAreaValue = null;
        }
		this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
        super.buttonPressed(buttonId);
    }
    
    /**
     * Method configureShell.
     * @param shell Shell
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(ADD_PARTICIPANT_DIALOG_TITLE);
    }
    
	/**
	 * Configures the dialog form and creates form content. Clients should
	 * override this method.
	 * 
	 * @param mform
	 *            the dialog form
	 */
	@Override
	protected void createFormContent(final IManagedForm mform) {

		final FormToolkit toolkit = mform.getToolkit();
		final ScrolledForm sform = mform.getForm();
		sform.setExpandVertical(true);
		final Composite composite = sform.getBody();
		final GridLayout layout = new GridLayout(4, false);
		composite.setLayout(layout);
		
        //Grid data values
        final GridData labelData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
        final GridData textSingleData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textSingleData.horizontalSpan = 3;
        final GridData textMultiData = new GridData(GridData.FILL, GridData.FILL, true, false);
        textMultiData.horizontalSpan = 3;
        
		//Basic parameters section
        final Section basicSection = toolkit.createSection(composite, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
        final GridData basicSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        basicSectionGridData.horizontalSpan = 4;
        basicSection.setLayoutData(basicSectionGridData);
        basicSection.setText("Basic Parameters");
        basicSection.setDescription("Enter the mandatory basic parameters for this participant");
        basicSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
				sform.reflow(true);
			}
		});
        
        final Composite basicSectionClient = toolkit.createComposite(basicSection);
        basicSectionClient.setLayout(layout);
        basicSection.setClient(basicSectionClient);
        
        //Participant Id
        Label label = toolkit.createLabel(basicSectionClient, ADD_PARTICIPANT_ID_DIALOG_VALUE);
        label.setLayoutData(labelData);
        fParticipantIdInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE);
        fParticipantIdInputTextField.setLayoutData(textSingleData);
              
        //Extra parameters section
        final Section extraSection = toolkit.createSection(composite, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE);
        final GridData extraSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        extraSectionGridData.horizontalSpan = 4;
        extraSection.setLayoutData(extraSectionGridData);
        extraSection.setText("Extra Parameters");
        extraSection.setDescription("Enter the optional extra parameters for this participant");
        extraSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
				sform.reflow(true);
			}
		});
        
        final Composite extraSectionClient = toolkit.createComposite(extraSection);
        extraSectionClient.setLayout(layout);
        extraSection.setClient(extraSectionClient);

		//Roles
        label = toolkit.createLabel(extraSectionClient, "Roles: ");
        label.setLayoutData(labelData);
        fRoleTypes = new EditableListWidget(toolkit, extraSectionClient, textSingleData, null, 0, CCombo.class,
        		R4EUIConstants.PARTICIPANT_ROLES);

        //Focus Area
        label = toolkit.createLabel(extraSectionClient, "Focus Area: ");
        label.setLayoutData(labelData);
        fFocusAreaTextField = toolkit.createText(extraSectionClient, "", SWT.MULTI | SWT.V_SCROLL);
        fFocusAreaTextField.setLayoutData(textMultiData);
	}
    
	/**
	 * Method isResizable.
	 * @return boolean
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
    
    /**
     * Returns the participant role values
     * @return the participant roles as a List
     */
    public List<R4EUserRole> getParticipantRolesValue() {
    	return fRolesValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the participant id input string
     */
    public String getParticipantIdValue() {
        return fParticipantIdValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the focus area input string
     */
    public String getFocusAreaValue() {
        return fFocusAreaValue;
    }

    /**
     * Method validateEmptyInput.
     * @param aText Text
     * @return String
     */
    private String validateEmptyInput(Text aText) {
        return fValidator.isValid(aText.getText());
    }
}
