// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the dialog used to fill-in the Review element details
 * This is a modal dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import java.util.ArrayList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewGroup;
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
public class ReviewInputDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field ADD_REVIEW_DIALOG_TITLE.
	 * (value is ""Enter Review details"")
	 */
	private static final String ADD_REVIEW_DIALOG_TITLE = "Enter Review Details";

	/**
	 * Field ADD_REVIEW_TYPE_DIALOG_VALUE.
	 * (value is ""Review Type: "")
	 */
	private static final String ADD_REVIEW_TYPE_DIALOG_VALUE = "Review Type: ";
	
	/**
	 * Field ADD_REVIEW_NAME_DIALOG_VALUE.
	 * (value is ""Review Name: "")
	 */
	private static final String ADD_REVIEW_NAME_DIALOG_VALUE = "Review Name: ";
	
	/**
	 * Field ADD_REVIEW_DESCRIPTION_DIALOG_VALUE.
	 * (value is ""Review Description: "")
	 */
	private static final String ADD_REVIEW_DESCRIPTION_DIALOG_VALUE = "Review Description: ";
	
	/**
	 * Field BASIC_PARAMS_HEADER_MSG.
	 * (value is ""Enter the mandatory basic parameters for this Review Group"")
	 */
	private static final String BASIC_PARAMS_HEADER_MSG = "Enter the mandatory basic parameters for this Review";
	
	/**
	 * Field EXTRA_PARAMS_HEADER_MSG.
	 * (value is ""Enter the optional extra parameters for this Review Group"")
	 */
	private static final String EXTRA_PARAMS_HEADER_MSG = "Enter the optional extra parameters for this Review";
	
	/**
	 * Field ADD_REVIEW_GROUP_PROJECT_DIALOG_VALUE.
	 * (value is ""Available Projects:"")
	 */
	private static final String ADD_REVIEW_PROJECT_DIALOG_VALUE = "Project:";
	
	/**
	 * Field ADD_REVIEW_GROUP_COMPONENTS_DIALOG_VALUE.
	 * (value is ""Available Components:"")
	 */
	private static final String ADD_REVIEW_COMPONENTS_DIALOG_VALUE = "Components:";
	
	/**
	 * Field ADD_REVIEW_GROUP_ENTRY_CRITERIA_DIALOG_VALUE.
	 * (value is ""Default Entry Criteria:"")
	 */
	private static final String ADD_REVIEW_ENTRY_CRITERIA_DIALOG_VALUE = "Entry Criteria:";
	
	/**
	 * Field ADD_REVIEW_GROUP_ENTRY_CRITERIA_DIALOG_VALUE.
	 * (value is ""Default Entry Criteria:"")
	 */
	private static final String ADD_REVIEW_OBJECTIVES_DIALOG_VALUE = "Objectives:";
	
	/**
	 * Field ADD_REVIEW_GROUP_ENTRY_CRITERIA_DIALOG_VALUE.
	 * (value is ""Default Entry Criteria:"")
	 */
	private static final String ADD_REVIEW_REFERENCE_MATERIAL_DIALOG_VALUE = "Reference Material:";
	
	/**
	 * Field REVIEW_TYPES.
	 */
	private static final String[] REVIEW_TYPES = { R4EUIConstants.REVIEW_TYPE_BASIC, 
		R4EUIConstants.REVIEW_TYPE_INFORMAL, R4EUIConstants.REVIEW_TYPE_FORMAL };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fReviewGroup.
	 */
	protected final R4EUIReviewGroup fReviewGroup;
	
	/**
	 * Field fReviewType.
	 */
	private CCombo fReviewType = null;
	
	/**
	 * Field fReviewTypeValue.
	 */
	private R4EReviewType fReviewTypeValue;
	
    /**
     * The input value; the empty string by default.
     */
    private String fReviewNameValue = "";
    
    /**
     * Input text widget.
     */
    protected Text fReviewNameInputTextField;
	
	/**
	 * Field fReviewDescriptionValue.
	 */
	private String fReviewDescriptionValue = "";
	
	/**
	 * Field fReviewDescriptionInputTextField.
	 */
	private Text fReviewDescriptionInputTextField = null;
	
	/**
	 * Field fProjectValue.
	 */
	private String fProjectValue = "";
	
	/**
	 * Field fProjectsCombo.
	 */
	private CCombo fProjectsCombo = null;
	
	/**
	 * Field fComponentsValues.
	 */
	private String[] fComponentsValues = null;
	
	/**
	 * Field fAvailableComponents.
	 */
	private EditableListWidget fComponents = null;
	
	/**
	 * Field fEntryCriteriaValue.
	 */
	private String fEntryCriteriaValue = "";
	
	/**
	 * Field fEntryCriteriaTextField.
	 */
	private Text fEntryCriteriaTextField = null;
	
	/**
	 * Field fObjectivesValue.
	 */
	private String fObjectivesValue = "";
	
	/**
	 * Field fObjectivesTextField.
	 */
	private Text fObjectivesTextField = null;
	
	/**
	 * Field fReferenceMaterialValue.
	 */
	private String fReferenceMaterialValue = "";
	
	/**
	 * Field fReferenceMaterialTextField.
	 */
	private Text fReferenceMaterialTextField = null;
    
    /**
     * The input validator, or <code>null</code> if none.
     */
    private final IInputValidator fValidator;
    
    
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
    
	/**
	 * Constructor for R4EReviewInputDialog.
	 * @param aParentShell Shell
	 * @param aReviewGroup R4EUIReviewGroup
	 */
	public ReviewInputDialog(Shell aParentShell, R4EUIReviewGroup aReviewGroup) {
		super(aParentShell);
    	setBlockOnOpen(true);
    	fReviewGroup = aReviewGroup;
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
        	
	    	//Review type (no validation needed as this is a read-only combo box
	        if (fReviewType.getText().equals(R4EUIConstants.REVIEW_TYPE_FORMAL)) {
	        	fReviewTypeValue = R4EReviewType.R4E_REVIEW_TYPE_FORMAL;
	        } else if (fReviewType.getText().equals(R4EUIConstants.REVIEW_TYPE_INFORMAL)) {
	        	fReviewTypeValue = R4EReviewType.R4E_REVIEW_TYPE_INFORMAL;
	        } else {
	        	fReviewTypeValue = R4EReviewType.R4E_REVIEW_TYPE_BASIC;
	        }
	    	
        	//Validate Review Name
        	String validateResult = validateEmptyInput(fReviewNameInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "No input given for Review Type",
        				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
    			return;
        	}
        	fReviewNameValue = fReviewNameInputTextField.getText();
        	
        	//Validate Review Description
        	validateResult = validateEmptyInput(fReviewNameInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
        		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "No input given for Review Description",
        			new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.WARNING);
        		dialog.open();
        	}
        	fReviewDescriptionValue = fReviewDescriptionInputTextField.getText();
        	
        	//Validate Project (optional)
        	validateResult = validateEmptyInput(fProjectsCombo.getText());
        	if (null == validateResult) {
        		fProjectValue = fProjectsCombo.getText();
        	}
        	
        	//Validate Components (optional)
        	final ArrayList<String> componentsValues = new ArrayList<String>();
			for (Item item : fComponents.getItems()) {
	        	validateResult = validateEmptyInput(item.getText());
	        	if (null == validateResult) {
	        		componentsValues.add(item.getText());
	        	}
			}
			fComponentsValues = componentsValues.toArray(new String[componentsValues.size()]);
        	
        	//Validate Entry Criteria (optional)
        	validateResult = validateEmptyInput(fEntryCriteriaTextField);
        	if (null == validateResult) {
        		fEntryCriteriaValue = fEntryCriteriaTextField.getText();
        	}
        	
        	//Validate Objectives (optional)
        	validateResult = validateEmptyInput(fObjectivesTextField);
        	if (null == validateResult) {
        		fObjectivesValue = fObjectivesTextField.getText();
        	}
        	
        	//Validate ReferenceMaterial (optional)
        	validateResult = validateEmptyInput(fReferenceMaterialTextField);
        	if (null == validateResult) {
        		fReferenceMaterialValue = fReferenceMaterialTextField.getText();
        	}	
        } else {
        	fReviewNameValue = null;
        	fReviewDescriptionValue = null;
        	fProjectValue = null;
        	fEntryCriteriaValue = null;
        	fObjectivesValue = null;
        	fReferenceMaterialValue = null;
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
        shell.setText(ADD_REVIEW_DIALOG_TITLE);
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
        GridData textGridData = null;
        
		//Review Type
        Label label = toolkit.createLabel(composite, ADD_REVIEW_TYPE_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fReviewType = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		fReviewType.setItems(REVIEW_TYPES);
		fReviewType.select(0);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		fReviewType.setLayoutData(textGridData);
		
		//Basic parameters section
        final Section basicSection = toolkit.createSection(composite, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
        final GridData basicSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        basicSectionGridData.horizontalSpan = 4;
        basicSection.setLayoutData(basicSectionGridData);
        basicSection.setText(R4EUIConstants.BASIC_PARAMS_HEADER);
        basicSection.setDescription(BASIC_PARAMS_HEADER_MSG);
        basicSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
        
        final Composite basicSectionClient = toolkit.createComposite(basicSection);
        basicSectionClient.setLayout(layout);
        basicSection.setClient(basicSectionClient);
        
        //Review Name
        label = toolkit.createLabel(basicSectionClient, ADD_REVIEW_NAME_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fReviewNameInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
        fReviewNameInputTextField.setLayoutData(textGridData);
        
        //Review Description
        label = toolkit.createLabel(basicSectionClient, ADD_REVIEW_DESCRIPTION_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fReviewDescriptionInputTextField = toolkit.createText(basicSectionClient, "", SWT.MULTI | SWT.V_SCROLL);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		textGridData.heightHint = fReviewNameInputTextField.getLineHeight() * 3;
        fReviewDescriptionInputTextField.setLayoutData(textGridData);
              
        //Extra parameters section
        final Section extraSection = toolkit.createSection(composite, Section.DESCRIPTION | ExpandableComposite.TITLE_BAR |
        		  ExpandableComposite.TWISTIE);
        final GridData extraSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
        extraSectionGridData.horizontalSpan = 4;
        extraSection.setLayoutData(extraSectionGridData);
        extraSection.setText(R4EUIConstants.EXTRA_PARAMS_HEADER);
        extraSection.setDescription(EXTRA_PARAMS_HEADER_MSG);
        extraSection.addExpansionListener(new ExpansionAdapter()
		{
			@Override
			public void expansionStateChanged(ExpansionEvent e){
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});
        
        final Composite extraSectionClient = toolkit.createComposite(extraSection);
        extraSectionClient.setLayout(layout);
        extraSection.setClient(extraSectionClient);

        //Project
        label = toolkit.createLabel(extraSectionClient, ADD_REVIEW_PROJECT_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fProjectsCombo = new CCombo(extraSectionClient, SWT.BORDER | SWT.READ_ONLY);
		final String[] projects = (String[]) fReviewGroup.getReviewGroup().getAvailableProjects().toArray();
		if (0 == projects.length) fProjectsCombo.setEnabled(false);
		fProjectsCombo.setItems(projects);
		final GridData data1 = new GridData(GridData.FILL, GridData.FILL, true, false);
		data1.horizontalSpan = 3;
		fProjectsCombo.setLayoutData(data1);
		
		//Components
        label = toolkit.createLabel(extraSectionClient, ADD_REVIEW_COMPONENTS_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		final String[] components = (String[]) fReviewGroup.getReviewGroup().getAvailableComponents().toArray();
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
        fComponents = new EditableListWidget(toolkit, extraSectionClient, textGridData, null, 0, CCombo.class,
        		components);
		if (0 == components.length) fComponents.setEnabled(false);

        //Entry Criteria
        label = toolkit.createLabel(extraSectionClient, ADD_REVIEW_ENTRY_CRITERIA_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fEntryCriteriaTextField = toolkit.createText(extraSectionClient, "", SWT.MULTI | SWT.V_SCROLL);
        if (null != fReviewGroup.getGroup().getDefaultEntryCriteria()) {
        	fEntryCriteriaTextField.setText(fReviewGroup.getGroup().getDefaultEntryCriteria());
        }
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		textGridData.heightHint = fReviewNameInputTextField.getLineHeight() * 3;
        fEntryCriteriaTextField.setLayoutData(textGridData);

        //Objectives
        label = toolkit.createLabel(extraSectionClient, ADD_REVIEW_OBJECTIVES_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fObjectivesTextField = toolkit.createText(extraSectionClient, "", SWT.MULTI | SWT.V_SCROLL);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		textGridData.heightHint = fReviewNameInputTextField.getLineHeight() * 3;
        fObjectivesTextField.setLayoutData(textGridData);
        
        //Reference Material
        label = toolkit.createLabel(extraSectionClient, ADD_REVIEW_REFERENCE_MATERIAL_DIALOG_VALUE);
        label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        fReferenceMaterialTextField = toolkit.createText(extraSectionClient, "", SWT.MULTI | SWT.V_SCROLL);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		textGridData.heightHint = fReviewNameInputTextField.getLineHeight() * 3;
        fReferenceMaterialTextField.setLayoutData(textGridData);
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
     * Returns the text area.
     * @return the review name text area
     */
    public R4EReviewType getReviewTypeValue() {
    	return fReviewTypeValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the review name input string
     */
    public String getReviewNameValue() {
        return fReviewNameValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the review description input string
     */
    public String getReviewDescriptionValue() {
        return fReviewDescriptionValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the project input string
     */
    public String getProjectValue() {
        return fProjectValue;
    }
    
    /**
     * Returns the strings typed into this input dialog.
     * @return the components input strings
     */
    public String[] getComponentsValues() {
        return fComponentsValues;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the entry criteria input string
     */
    public String getEntryCriteriaValue() {
        return fEntryCriteriaValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the objectives input string
     */
    public String getObjectivesValue() {
        return fObjectivesValue;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the reference material input string
     */
    public String getReferenceMaterialValue() {
        return fReferenceMaterialValue;
    }
    
    /**
     * Method validateEmptyInput.
     * @param aText Text
     * @return String
     */
    private String validateEmptyInput(Text aText) {
        return fValidator.isValid(aText.getText());
    }
    
    /**
     * Method validateEmptyInput.
     * @param aString String
     * @return String
     */
    private String validateEmptyInput(String aString) {
    	return fValidator.isValid(aString);
    }
}
