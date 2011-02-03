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
 * This class implements the dialog used to fill-in the Review Group element 
 * details.  This is a modal dialog
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
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EReviewGroupInputDialog extends Dialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field MINIMUM_DIALOG_AREA_HEIGHT.
	 * (value is 200)
	 */
	private static final int MINIMUM_DIALOG_AREA_HEIGHT = 200;
	
	/**
	 * Field NUM_COLUMNS_FOLDER_INPUT_COMPOSITE.
	 * (value is 2)
	 */
	private static final int NUM_COLUMNS_FOLDER_INPUT_COMPOSITE = 2;

	 /**
     * The title of the dialog.
     */
    private final String fTitle;

    /**
     * The message to display, or <code>null</code> if none.
     */
    private final String fGroupNameMessage;
    
    /**
     * The input value; the empty string by default.
     */
    private String fGroupNameValue = "";
    
    /**
     * Input text widget.
     */
    private Text fGroupNameInputTextField;
    
	/**
	 * Field fGroupFolderMessage.
	 */
	private String fGroupFolderMessage = null;
	
	/**
	 * Field fGroupFolderValue.
	 */
	private String fGroupFolderValue = "";
	
	/**
	 * Field fGroupFolderInputTextField.
	 */
	private Text fGroupFolderInputTextField = null;
	
	/**
	 * Field fGroupFolderInputButton.
	 */
	private Button fGroupFolderInputButton = null;
	
	/**
	 * Field fGroupDescriptionMessage.
	 */
	private String fGroupDescriptionMessage = null;
	
	/**
	 * Field fGroupDescriptionValue.
	 */
	private String fGroupDescriptionValue = "";
	
	/**
	 * Field fGroupDescriptionInputTextField.
	 */
	private Text fGroupDescriptionInputTextField = null;

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
	 * Constructor for R4EReviewGroupInputDialog.
	 * @param aParentShell Shell
	 * @param aDialogTitle String
	 * @param aGroupNameMessage String
	 * @param aGroupFolderMessage String
	 * @param aGroupDescriptionMessage String
	 */
	public R4EReviewGroupInputDialog(Shell aParentShell, String aDialogTitle, String aGroupNameMessage, 
			String aGroupFolderMessage, String aGroupDescriptionMessage) {
		super(aParentShell);
    	setBlockOnOpen(true);
		fTitle = aDialogTitle;
		fGroupNameMessage = aGroupNameMessage;
		fGroupFolderMessage = aGroupFolderMessage;
		fGroupDescriptionMessage = aGroupDescriptionMessage;
		fValidator = new FolderInputValidator();
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
			//Validate Name
			String validateResult = validateEmptyInput(fGroupNameInputTextField);
			if (null != validateResult) {
				//Validate of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "No input given for Group Name",
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			} 

			//Validate Folder
			validateResult = validateInput(fGroupFolderInputTextField);
			if (null != validateResult) {
				//Validate of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Invalid input folder",
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			}

			validateResult = validateGroupExists(fGroupFolderInputTextField);
			if (null != validateResult) {
				//Validate of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Invalid input folder",
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			} 

			//Validate Description
			validateResult = validateEmptyInput(fGroupDescriptionInputTextField);
			if (null != validateResult) {
				//Validate of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING, "No input given for Group Description",
						new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, validateResult, null), IStatus.WARNING);
				dialog.open();
			}
			fGroupNameValue = fGroupNameInputTextField.getText();
			fGroupFolderValue = fGroupFolderInputTextField.getText();
			fGroupDescriptionValue = fGroupDescriptionInputTextField.getText();
		} else {
			fGroupNameValue = null;
			fGroupFolderValue = null;
			fGroupDescriptionValue = null;
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
        
        // create Group Name message label
        if (null != fGroupNameMessage) {
            final Label label = new Label(composite, SWT.WRAP);
            label.setText(fGroupNameMessage);
            final GridData groupNameLabelData = new GridData(GridData.FILL, GridData.FILL, true, false);
            groupNameLabelData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
            label.setLayoutData(groupNameLabelData);
            label.setFont(parent.getFont());
        }
        
        //create Group Name input text field
        fGroupNameInputTextField = new Text(composite, getInputTextStyle());
        fGroupNameInputTextField.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        
        // create Group Folder input composite
        final Composite folderComposite = new Composite(composite, SWT.NONE);
		final GridLayout layout = new GridLayout(NUM_COLUMNS_FOLDER_INPUT_COMPOSITE, false);
		folderComposite.setLayout(layout);
		folderComposite.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		
        // create Group Folder message label
        if (null != fGroupFolderMessage) {
            final Label label = new Label(folderComposite, SWT.WRAP);
            label.setText(fGroupFolderMessage);
            final GridData reviewFolderLabelData = new GridData(GridData.FILL, GridData.END, true, false);
            reviewFolderLabelData.horizontalSpan = NUM_COLUMNS_FOLDER_INPUT_COMPOSITE;
            label.setLayoutData(reviewFolderLabelData);
            label.setFont(parent.getFont());
        }
        
        // create Group Folder text field and button
        fGroupFolderInputTextField = new Text(folderComposite, getInputTextStyle());
        fGroupFolderInputTextField.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));
        fGroupFolderInputButton = new Button(folderComposite, SWT.NONE);
        fGroupFolderInputButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER).createImage()); // $codepro.audit.disable methodChainLength
        fGroupFolderInputButton.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
        fGroupFolderInputButton.addSelectionListener(new SelectionAdapter() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			@SuppressWarnings("synthetic-access")
			@Override
			public void widgetSelected(SelectionEvent event) {
				fGroupFolderInputTextField.setText(folderButtonPressed());
			}
		});
		
        // create Group Description message label
        if (null != fGroupDescriptionMessage) {
            final Label label = new Label(composite, SWT.WRAP);
            label.setText(fGroupDescriptionMessage);
            final GridData reviewDescriptionLabelData = new GridData(GridData.FILL, GridData.FILL, true, false);
            reviewDescriptionLabelData.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
            label.setLayoutData(reviewDescriptionLabelData);
            label.setFont(parent.getFont());
        }
        
        //create Group Description input text field
        fGroupDescriptionInputTextField = new Text(composite, getInputTextStyle() | SWT.V_SCROLL);
        fGroupDescriptionInputTextField.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
        composite.getShell().setMinimumSize(convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH), 
        		convertHorizontalDLUsToPixels(MINIMUM_DIALOG_AREA_HEIGHT));
        
        applyDialogFont(composite);
        return composite;
    }

    /**
     * Method folderButtonPressed.
     * @return String
     */
    protected String folderButtonPressed() {
    	//Open folder dialog
        final DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
        dialog.setMessage("Select Folder...");
        String dir = dialog.open();
        if (null != dir) {
            dir = dir.trim();
            if (0 == dir.length()) {
				return null;
			}
        }
        return dir;
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
        fGroupNameInputTextField.setFocus();
        if (null != fGroupNameValue) {
        	fGroupNameInputTextField.setText(fGroupNameValue);
        	fGroupNameInputTextField.selectAll();
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
     * @return the group name text area
     */
    protected Text getGroupNameText() {
        return fGroupNameInputTextField;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the group name input string
     */
    public String getGroupNameValue() {
        return fGroupNameValue;
    }
    
    /**
     * Returns the text area.
     * @return the group folder text area
     */
    protected Text getGroupFolderText() {
        return fGroupNameInputTextField;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the group folder input string
     */
    public String getGroupFolderValue() {
        return fGroupFolderValue;
    }
    
    /**
     * Returns the text area.
     * @return the group description text area
     */
    protected Text getGroupDescriptionText() {
        return fGroupDescriptionInputTextField;
    }
    
    /**
     * Returns the string typed into this input dialog.
     * @return the group description input string
     */
    public String getGroupDescriptionValue() {
        return fGroupDescriptionValue;
    }
    
    /**
     * Validates the input.
     * <p>
     * The default implementation of this framework method delegates the request
     * to the supplied input validator object; if it finds the input invalid,
     * the error message is displayed in the dialog's message line. This hook
     * method is called whenever the text changes in the input field.
     * </p>
     * @param aText Text
     * @return String
     */
    protected String validateInput(Text aText) {
        if (null != fValidator) {
            return fValidator.isValid(aText.getText());
        }
        return null;
    }
    
    /**
     * Method validateGroupExists.
     * @param aText Text
     * @return String
     */
    private String validateGroupExists(Text aText) {
        if (null != fValidator) {
            return ((FolderInputValidator) fValidator).isGroupExists(aText.getText());
        }
        return null;
    }
    
    /**
     * Method validateEmptyInput.
     * @param aText Text
     * @return String
     */
    private String validateEmptyInput(Text aText) {
        if (null != fValidator) {
            return ((FolderInputValidator) fValidator).isEmpty(aText.getText());
        }
        return null;
    }
}
