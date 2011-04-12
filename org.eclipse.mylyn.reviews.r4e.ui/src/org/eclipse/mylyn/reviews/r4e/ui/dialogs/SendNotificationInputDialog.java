// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the dialog used to choose the email/notification type to send
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class SendNotificationInputDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field ADD_COMMENT_DIALOG_TITLE.
	 * (value is ""Enter Comment details"")
	 */
	private static final String SEND_MAIL_DIALOG_TITLE = "Send Email/Notification";

	/**
	 * Field BASIC_PARAMS_HEADER_MSG.
	 * (value is ""Which Email type do you want to send?"")
	 */
	private static final String BASIC_PARAMS_HEADER_MSG = "Which Email type do you want to send?";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fSource.
	 */
	Object fSource = null;
	
	/**
	 * Field fMessageType.
	 */
    private int fMessageType;
    
	/**
	 * Field fItemsReadyButton.
	 */
    Button fItemsReadyButton = null;
    
	/**
	 * Field fItemsRemovedButton.
	 */
    Button fItemsRemovedButton = null;
    
	/**
	 * Field fProgressButton.
	 */
    Button fProgressButton = null;
    
	/**
	 * Field fCompletionButton.
	 */
    Button fCompletionButton = null;
    
	/**
	 * Field fQuestionButton.
	 */
    Button fQuestionButton = null;
    
    
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
    
	/**
	 * Constructor for R4ECommentInputDialog.
	 * @param aParentShell Shell
	 */
	public SendNotificationInputDialog(Shell aParentShell, Object aSource) {
		super(aParentShell);
		setBlockOnOpen(true);
		fSource = aSource;
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
        	fMessageType = R4EUIConstants.INVALID_VALUE;
	    	this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
	    	if (fItemsReadyButton.getSelection()) fMessageType = R4EUIConstants.MESSAGE_TYPE_ITEMS_READY;
	    	if (fItemsRemovedButton.getSelection()) fMessageType = R4EUIConstants.MESSAGE_TYPE_ITEMS_REMOVED;
	    	else if (fProgressButton.getSelection()) fMessageType = R4EUIConstants.MESSAGE_TYPE_PROGRESS;
	    	else if (fCompletionButton.getSelection()) fMessageType = R4EUIConstants.MESSAGE_TYPE_COMPLETION;
	    	else if (fQuestionButton.getSelection()) fMessageType = R4EUIConstants.MESSAGE_TYPE_QUESTION;
        } else {
        	fMessageType = R4EUIConstants.INVALID_VALUE;
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
        shell.setText(SEND_MAIL_DIALOG_TITLE);
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
		final GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);
        
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
        
        //Email/Notification type radio button
        if (fSource instanceof R4EUIReviewBasic) {
        	fItemsReadyButton = toolkit.createButton(basicSectionClient, 
        			"Notify Participants of new Items Ready for Review", SWT.RADIO);
        	fItemsReadyButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        	fItemsRemovedButton = toolkit.createButton(basicSectionClient, 
        			"Notify Participants of Items Removed from Review", SWT.RADIO);
        	fItemsRemovedButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        	fProgressButton = toolkit.createButton(basicSectionClient, 
        			"Notify review Owner of Progress", SWT.RADIO);
        	fProgressButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        	fCompletionButton = toolkit.createButton(basicSectionClient, 
        			"Notify Review Owner of Completion", SWT.RADIO);
        	fCompletionButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
        }
        fQuestionButton = toolkit.createButton(basicSectionClient, 
        		"Ask Question to Participant", SWT.RADIO);
        fQuestionButton.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
    }
    
	/**
	 * Method isResizable.
	 * @return boolean
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}
    
    /**
     * Method getMessageTypeValue
     * @return the message type value
     */
    public int getMessageTypeValue() {
        return fMessageType;
    }
}
