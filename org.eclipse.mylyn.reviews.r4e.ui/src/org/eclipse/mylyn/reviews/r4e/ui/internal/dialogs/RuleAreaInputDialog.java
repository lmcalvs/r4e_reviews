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
 * This class implements the dialog used to fill-in the Rule Area element details.
 * This is a modal dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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

// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class RuleAreaInputDialog extends FormDialog implements IRuleAreaInputDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field ADD_RULE_AREA_DIALOG_TITLE. (value is ""Enter Rule Area Details"")
	 */
	private static final String ADD_RULE_AREA_DIALOG_TITLE = "Enter Rule Area Details";

	/**
	 * Field ADD_RULE_AREA_NAME_DIALOG_VALUE. (value is ""Rule Area Name:"")
	 */
	private static final String ADD_RULE_AREA_NAME_DIALOG_VALUE = "Rule Area Name:";

	/**
	 * Field BASIC_PARAMS_HEADER_MSG. (value is ""Enter the mandatory basic parameters for this Rule Area"")
	 */
	private static final String BASIC_PARAMS_HEADER_MSG = "Enter the mandatory basic parameters for this Rule Area";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fNameValue.
	 */
	private String fNameValue = "";

	/**
	 * Field fVersionInputTextField.
	 */
	private Text fNameInputTextField;

	/**
	 * The input validator, or <code>null</code> if none.
	 */
	private final IInputValidator fValidator;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for RuleAreaInputDialog.
	 * 
	 * @param aParentShell
	 *            Shell
	 */
	public RuleAreaInputDialog(Shell aParentShell) {
		super(aParentShell);
		setBlockOnOpen(true);
		fValidator = new R4EInputValidator();
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method buttonPressed.
	 * 
	 * @param buttonId
	 *            int
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));

			//Validate Name
			final String validateResult = validateEmptyInput(fNameInputTextField);
			if (null != validateResult) {
				//Validation of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"No input given for Rule Area Name", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			}
			fNameValue = fNameInputTextField.getText();
		} else {
			fNameValue = null;
		}
		super.buttonPressed(buttonId);
	}

	/**
	 * Method configureShell.
	 * 
	 * @param shell
	 *            Shell
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(ADD_RULE_AREA_DIALOG_TITLE);
		shell.setMinimumSize(R4EUIConstants.DIALOG_DEFAULT_WIDTH, R4EUIConstants.DIALOG_DEFAULT_HEIGHT);
	}

	/**
	 * Configures the dialog form and creates form content. Clients should override this method.
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

		//Basic parameters section
		final Section basicSection = toolkit.createSection(composite, Section.DESCRIPTION
				| ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		final GridData basicSectionGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		basicSectionGridData.horizontalSpan = 4;
		basicSection.setLayoutData(basicSectionGridData);
		basicSection.setText(R4EUIConstants.BASIC_PARAMS_HEADER);
		basicSection.setDescription(BASIC_PARAMS_HEADER_MSG);
		basicSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				getShell().setSize(getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT));
			}
		});

		final Composite basicSectionClient = toolkit.createComposite(basicSection);
		basicSectionClient.setLayout(layout);
		basicSection.setClient(basicSectionClient);

		//Rule Area Name
		final Label label = toolkit.createLabel(basicSectionClient, ADD_RULE_AREA_NAME_DIALOG_VALUE);
		label.setToolTipText(R4EUIConstants.RULE_AREA_NAME_TOOLTIP);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fNameInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE | SWT.BORDER);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		fNameInputTextField.setToolTipText(R4EUIConstants.RULE_AREA_NAME_TOOLTIP);
		fNameInputTextField.setLayoutData(textGridData);
		fNameInputTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// ignore
				if (fNameInputTextField.getText().length() > 0) {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				} else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});
	}

	/**
	 * Configures the button bar.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return Control
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		final Control bar = super.createButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		return bar;
	}

	/**
	 * Method isResizable.
	 * 
	 * @return boolean
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the version input string
	 */
	public String getNameValue() {
		return fNameValue;
	}

	/**
	 * Method validateEmptyInput.
	 * 
	 * @param aText
	 *            Text
	 * @return String
	 */
	private String validateEmptyInput(Text aText) {
		if (null != fValidator) {
			return fValidator.isValid(aText.getText());
		}
		return null;
	}
}
