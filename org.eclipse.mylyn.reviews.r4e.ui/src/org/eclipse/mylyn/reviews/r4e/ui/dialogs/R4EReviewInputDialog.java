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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EReviewInputDialog extends Dialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field MINIMUM_DIALOG_AREA_HEIGHT.
	 * (value is 150)
	 */
	private static final int MINIMUM_DIALOG_AREA_HEIGHT = 150;

	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	 /**
     * The title of the dialog.
     */
    private final String fTitle;

    /**
     * The message to display, or <code>null</code> if none.
     */
    private final String fReviewNameMessage;
    
    /**
     * The input value; the empty string by default.
     */
    private String fReviewNameValue = "";
    
    /**
     * Input text widget.
     */
    private Text fReviewNameInputTextField;
    
	/**
	 * Field fReviewDescriptionMessage.
	 */
	private String fReviewDescriptionMessage = null;
	
	/**
	 * Field fReviewDescriptionValue.
	 */
	private String fReviewDescriptionValue = "";
	
	/**
	 * Field fReviewDescriptionInputTextField.
	 */
	private Text fReviewDescriptionInputTextField = null;

    /**
     * Ok button widget.
     */
    private Button fOKButton;
    
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
	 * @param aDialogTitle String
	 * @param aReviewNameMessage String
	 * @param aReviewDescriptionMessage String
	 */
	public R4EReviewInputDialog(Shell aParentShell, String aDialogTitle, String aReviewNameMessage, 
			String aReviewDescriptionMessage) {
		super(aParentShell);
    	setBlockOnOpen(true);
		fTitle = aDialogTitle;
		fReviewNameMessage = aReviewNameMessage;
		fReviewDescriptionMessage = aReviewDescriptionMessage;
		fValidator = new EmptyInputValidator();
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

        	//Validate Review Name
        	String validateResult = validateEmptyInput(fReviewNameInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, "Error", "No input given for Review Name",
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
        		final ErrorDialog dialog = new ErrorDialog(null, "Warning", "No input given for Review Description",
        			new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.WARNING);
        		dialog.open();
        	}
        	fReviewDescriptionValue = fReviewDescriptionInputTextField.getText();
        } else {
        	fReviewNameValue = null;
        	fReviewDescriptionValue = null;
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
        if (null != fTitle) {
			shell.setText(fTitle);
		}
    }
    
    /**
     * Method createDialogArea.
     * @param parent Composite
     * @return Control
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
	protected Control createDialogArea(Composite parent) {
        
    	// create composite
        final Composite composite = (Composite) super.createDialogArea(parent);
        
        // create Review Name message label
        if (null != fReviewNameMessage) {
            final Label label = new Label(composite, SWT.WRAP);
            label.setText(fReviewNameMessage);
            final GridData reviewNameLabelData = new GridData(GridData.FILL, GridData.FILL, true, false);
            reviewNameLabelData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
            label.setLayoutData(reviewNameLabelData);
            label.setFont(parent.getFont());
        }
        
        //create Review Name input text field
        fReviewNameInputTextField = new Text(composite, getInputTextStyle());
        fReviewNameInputTextField.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        
        // create Review Description message label
        if (null != fReviewDescriptionMessage) {
            final Label label = new Label(composite, SWT.WRAP);
            label.setText(fReviewDescriptionMessage);
            final GridData reviewDescriptionLabelData = new GridData(GridData.FILL, GridData.FILL, true, false);
            reviewDescriptionLabelData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
            label.setLayoutData(reviewDescriptionLabelData);
            label.setFont(parent.getFont());
        }
        
        //create Review Description input text field
        fReviewDescriptionInputTextField = new Text(composite, getInputTextStyle() | SWT.V_SCROLL);
        fReviewDescriptionInputTextField.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        composite.getShell().setMinimumSize(convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH), 
        		convertHorizontalDLUsToPixels(MINIMUM_DIALOG_AREA_HEIGHT));
        
        applyDialogFont(composite);
        return composite;
    }

    /**
     * Method createButtonsForButtonBar.
     * @param parent Composite
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
	protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
    	fOKButton = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID,
                IDialogConstants.CANCEL_LABEL, false);
        //do this here because setting the text will set enablement on the ok button
        fReviewNameInputTextField.setFocus();
        if (null != fReviewNameValue) {
        	fReviewNameInputTextField.setText(fReviewNameValue);
        	fReviewNameInputTextField.selectAll();
        }
    }
    
	/**
	 * Returns the style bits that should be used for the input text field.
	 * Defaults to a single line entry. Subclasses may override.
	 * @since 3.4
	 * @return the integer style bits that should be used when creating the input text
	 */
	protected int getInputTextStyle() {
		return SWT.MULTI | SWT.WRAP ;
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
     * Returns the ok button.
     * @return the ok button
     */
    protected Button getOkButton() {
        return fOKButton;
    }
    
    /**
     * Returns the text area.
     * @return the review name text area
     */
    protected Text getReviewNameText() {
        return fReviewNameInputTextField;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the review name input string
     */
    public String getReviewNameValue() {
        return fReviewNameValue;
    }
    
    /**
     * Returns the text area.
     * @return the review description text area
     */
    protected Text getReviewDescriptionText() {
        return fReviewDescriptionInputTextField;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the review description input string
     */
    public String getReviewDescriptionValue() {
        return fReviewDescriptionValue;
    }
    
    /**
     * Method validateEmptyInput.
     * @param aText Text
     * @return String
     */
    private String validateEmptyInput(Text aText) {
        if (null != fValidator) {
            return ((EmptyInputValidator) fValidator).isValid(aText.getText());
        }
        return null;
    }
}
