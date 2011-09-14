/**
 * Copyright (c) 2011 Ericsson and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson Research Canada - Dialog used to display SMTP mail
 * 
 */
package org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.SmtpPlugin;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.MailData;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.MailInputValidator;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.SMTPHostString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;



/**
 * @author Jacques Bouthillier
 *
 * @version $Revision: 1.0 $
 */
public class MailDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field MIN_X_WIN_SIZE.
	 * (value is 700)
	 */
	private static final int MIN_X_WIN_SIZE = 700;

	/**
	 * Field MIN_Y_WIN_SIZE.
	 * (value is 425)
	 */
	private static final int MIN_Y_WIN_SIZE = 425;
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fDestinationsInputTextField.
	 */
	private Text fDestinationsInputTextField = null;
	/**
	 * Field fSubjectInputTextField.
	 */
	private Text fSubjectInputTextField = null;
	/**
	 * Field fMessageBodyInputTextField.
	 */
	protected Text fMessageBodyInputTextField = null;
	/**
	 * Field fAttachment.
	 */
	private String fAttachment = null;
	/**
	 * Field fMailData.
	 */
	private MailData fMailData = null;

    /**
     * The input validator, or <code>null</code> if none.
     */
    private final IInputValidator fValidator;
    
    
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for MailDialog.
	 * @param aParentShell Shell
	 */
	public MailDialog(Shell aParentShell) {
		super(aParentShell);
		setBlockOnOpen(true);
		fValidator = new MailInputValidator();
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
    /**
     * Method buttonPressed.
     * @param buttonId int
    
     * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int) */
	@Override
	protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
        	
        	//Validate Destinations
        	String validateResult = validateEmptyInput(fDestinationsInputTextField);
        	if (null != validateResult) {
        		
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, 
    					SMTPHostString.getString("dialog_title_error"), 
    					SMTPHostString.getString("to_Error"),
        				new Status(IStatus.ERROR, 
        						SmtpPlugin.FPLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
    			return;
        	}
        	final String[] destinationValues = fDestinationsInputTextField.getText().split(";");
        	
        	//Validate Subject
        	validateResult = validateEmptyInput(fSubjectInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, 
    					SMTPHostString.getString("dialog_title_error"),
    					SMTPHostString.getString("subject_Error"),
        				new Status(IStatus.ERROR, 
        						SmtpPlugin.FPLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
    			return;
        	}
        	final String subjectValue = fSubjectInputTextField.getText();  
        	
        	//Validate Message Body
        	validateResult = validateEmptyInput(fMessageBodyInputTextField);
        	if (null != validateResult) {
        		//Validation of input failed
    			final ErrorDialog dialog = new ErrorDialog(null, 
    					SMTPHostString.getString("dialog_title_error"),
    					SMTPHostString.getString("message_body_Error"),
        				new Status(IStatus.ERROR, 
        						SmtpPlugin.FPLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
    			dialog.open();
    			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
    			return;
        	}
        	final String messageBodyValue = fMessageBodyInputTextField.getText();  
        	
    		fMailData = new MailData(subjectValue, messageBodyValue, destinationValues, fAttachment);
		}
        super.buttonPressed(buttonId);
	}
	
    /**
     * Method configureShell.
     * @param shell Shell
    
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell) */
    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(SMTPHostString.getString("MailDialog.label.title"));
    }
    
	/**
	 * Configures the dialog form and creates form content. Clients should
	 * override this method.
	 * @param mform - the dialog form
	 */
	@Override
	protected void createFormContent(final IManagedForm mform) {
		
		final FormToolkit toolkit = mform.getToolkit();
		final ScrolledForm sform = mform.getForm();
		sform.setExpandVertical(true);
		final Composite composite = sform.getBody();
		final FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		composite.setLayout(layout);
		
		createMailForm(composite, toolkit);
		getShell().setMinimumSize(MIN_X_WIN_SIZE, MIN_Y_WIN_SIZE);
	}
		
	/**
	 * Creates new mail form area
	 * @param aParent Composite
	 * @param aToolkit FormToolkit
	 */
	private void createMailForm(Composite aParent, FormToolkit aToolkit) {
		
		final Composite mailForm = aToolkit.createComposite(aParent);
		mailForm.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		final FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		mailForm.setLayout(layout);
		
		final FormData mailFormData = new FormData();
		mailFormData.top = new FormAttachment(0, 0);
		mailFormData.left = new FormAttachment(0);
		mailFormData.right = new FormAttachment(100);
		mailFormData.bottom = new FormAttachment(100);
		mailForm.setLayoutData(mailFormData);
		
		//Destinations
		final Label destinationsLabel = aToolkit.createLabel(mailForm, SMTPHostString.getString("MailDialog.label.toLabel"), SWT.NONE);
		destinationsLabel.setToolTipText(SMTPHostString.getString("MailDialog.label.toLabel.tooltip"));
		final FormData destinationsLabelData = new FormData();
		destinationsLabelData.top = new FormAttachment(0, 2);
		destinationsLabelData.left = new FormAttachment(0);
		destinationsLabel.setLayoutData(destinationsLabelData);
		
		fDestinationsInputTextField = aToolkit.createText(mailForm, "", SWT.BORDER);
		fDestinationsInputTextField.setEnabled(true);
		final FormData toTextData = new FormData();
		toTextData.top = new FormAttachment(0);
		toTextData.left = new FormAttachment(destinationsLabel, 34);
		toTextData.right = new FormAttachment(98);
		fDestinationsInputTextField.setLayoutData(toTextData);
		
		//Subject
		final Label subjectLabel = aToolkit.createLabel(mailForm, SMTPHostString.getString("MailDialog.label.subjectLabel"), SWT.NONE);
		subjectLabel.setToolTipText(SMTPHostString.getString("MailDialog.label.subjectLabel.tooltip"));
		final FormData subjectLabelData = new FormData();
		subjectLabelData.top = new FormAttachment(destinationsLabel, 15, SWT.BOTTOM);
		subjectLabelData.left = new FormAttachment(0);
		subjectLabel.setLayoutData(subjectLabelData);

		fSubjectInputTextField = aToolkit.createText(mailForm, "", SWT.BORDER);
		final FormData subjectData = new FormData();
		subjectData.top = new FormAttachment(fDestinationsInputTextField, 10, SWT.BOTTOM);
		subjectData.left = new FormAttachment(subjectLabel, 10);
		subjectData.right = new FormAttachment(98);
		fSubjectInputTextField.setLayoutData(subjectData);
		
		// Description Text
		fMessageBodyInputTextField = new Text(mailForm, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		final FormData messageBodyEditorData = new FormData();
		messageBodyEditorData.top = new FormAttachment(subjectLabel, 15, SWT.BOTTOM);
		messageBodyEditorData.left = new FormAttachment(0);
		messageBodyEditorData.right = new FormAttachment(98);
		messageBodyEditorData.bottom = new FormAttachment(98);
		fMessageBodyInputTextField.setLayoutData(messageBodyEditorData);
		mailForm.setSize(MIN_X_WIN_SIZE, MIN_Y_WIN_SIZE);

		// Adjust the to text offset
		final FormData data = (FormData) fDestinationsInputTextField.getLayoutData();
		data.left = new FormAttachment(fSubjectInputTextField, 0, SWT.LEFT);
		fDestinationsInputTextField.setLayoutData(data);
	}
    
	/**
	 * Method isResizable.
	
	 * @return boolean */
	@Override
	protected boolean isResizable() {
		return true;
	}
	
	/**
	 * Method getEmailData.
	
	 * @return MailData */
	public MailData getEmailData() {
		return fMailData;
	}
	
	/**
	 * Set the data to display in the box
	 * @param aDestinations String[]
	 * @param aSubject String
	 * @param aMessageBody String
	 * @param aAttachment String
	 */ 
	public void setMailInfo(String[] aDestinations, String aSubject, String aMessageBody, String aAttachment) {

		final StringBuilder recipient = new StringBuilder();
		for (int i = 0; i < aDestinations.length; i++) {
			recipient.append(aDestinations[i]);
			if (i < aDestinations.length - 1) {
				recipient.append(';');
			}
		}
		fDestinationsInputTextField.setText(recipient.toString());
		fSubjectInputTextField.setText(aSubject);
		fMessageBodyInputTextField.setText(aMessageBody);
		fAttachment = aAttachment;
	}
	
    /**
     * Method validateEmptyInput.
     * @param aText Text
    
     * @return String */
    private String validateEmptyInput(Text aText) {
        if (null != fValidator) {
            return fValidator.isValid(aText.getText());
        }
        return null;
    }
}
