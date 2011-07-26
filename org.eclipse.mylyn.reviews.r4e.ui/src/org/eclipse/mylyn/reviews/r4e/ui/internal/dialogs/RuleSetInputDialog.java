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
 * This class implements the dialog used to fill-in the Rule Set element details.
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
public class RuleSetInputDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field ADD_RULE_SET_DIALOG_TITLE. (value is ""Enter Rule Set Details"")
	 */
	private static final String ADD_RULE_SET_DIALOG_TITLE = "Enter Rule Set Details";

	/**
	 * Field ADD_RULE_SET_VERSION_DIALOG_VALUE. (value is ""Version:"")
	 */
	private static final String ADD_RULE_SET_VERSION_DIALOG_VALUE = "Version:";

	/**
	 * Field ADD_RULE_SET_NAME_DIALOG_VALUE. (value is ""Name:"")
	 */
	private static final String ADD_RULE_SET_NAME_DIALOG_VALUE = "Name:";

	/**
	 * Field ADD_RULE_SET_FOLDER_DIALOG_VALUE. (value is ""Folder:"")
	 */
	private static final String ADD_RULE_SET_FOLDER_DIALOG_VALUE = "Folder:";

	/**
	 * Field BASIC_PARAMS_HEADER_MSG. (value is ""Enter the mandatory basic parameters for this Rule Set"")
	 */
	private static final String BASIC_PARAMS_HEADER_MSG = "Enter the mandatory basic parameters for this Rule Set";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fVersionValue.
	 */
	private String fVersionValue = "";

	/**
	 * Field fVersionInputTextField.
	 */
	private Text fVersionInputTextField;

	/**
	 * Field fFolderValue.
	 */
	private String fFolderValue = "";

	/**
	 * Field fFolderInputTextField.
	 */
	private Text fFolderInputTextField = null;

	/**
	 * Field fFolderValue.
	 */
	private String fNameValue = "";

	/**
	 * Field fFolderInputTextField.
	 */
	private Text fNameInputTextField = null;

	/**
	 * The input validator, or <code>null</code> if none.
	 */
	private final IInputValidator fValidator;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for RuleSetInputDialog.
	 * 
	 * @param aParentShell
	 *            Shell
	 */
	public RuleSetInputDialog(Shell aParentShell) {
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

			//Validate Version
			String validateResult = validateEmptyInput(fVersionInputTextField);
			if (null != validateResult) {
				//Validation of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"No input given for Rule Set Version", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			}
			fVersionValue = fVersionInputTextField.getText();

			//Validate Folder
			validateResult = validateFolderInput(fFolderInputTextField);
			if (null != validateResult) {
				//Validate of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Invalid input folder",
						new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			}

			validateResult = validateFileExists(fFolderInputTextField.getText() + System.getProperty("file.separator")
					+ fNameInputTextField.getText() + R4EUIConstants.RULE_SET_FILE_SUFFIX);
			if (null != validateResult) {
				//Validate of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Rule Set file already exists", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			}
			fFolderValue = fFolderInputTextField.getText();

			//Validate Name
			validateResult = validateEmptyInput(fNameInputTextField);
			if (null != validateResult) {
				//Validation of input failed
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"No input given for Rule Set Name", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								validateResult, null), IStatus.ERROR);
				dialog.open();
				this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
				return;
			}
			fNameValue = fNameInputTextField.getText();

		} else {
			fVersionValue = null;
			fFolderValue = null;
			fNameValue = null;
		}
		this.getShell().setCursor(this.getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
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
		shell.setText(ADD_RULE_SET_DIALOG_TITLE);
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

		//Rule Set Version
		Label label = toolkit.createLabel(basicSectionClient, ADD_RULE_SET_VERSION_DIALOG_VALUE);
		label.setToolTipText(R4EUIConstants.RULESET_VERSION_TOOLTIP);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fVersionInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE | SWT.BORDER);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		fVersionInputTextField.setToolTipText(R4EUIConstants.RULESET_VERSION_TOOLTIP);
		fVersionInputTextField.setLayoutData(textGridData);
		fVersionInputTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// ignore
				if (fVersionInputTextField.getText().length() > 0 && fNameInputTextField.getText().length() > 0
						&& fFolderInputTextField.getText().length() > 0) {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				} else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});

		//Rule Set Name
		label = toolkit.createLabel(basicSectionClient, ADD_RULE_SET_NAME_DIALOG_VALUE);
		label.setToolTipText(R4EUIConstants.RULESET_NAME_TOOLTIP);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fNameInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE | SWT.BORDER);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		fNameInputTextField.setToolTipText(R4EUIConstants.RULESET_NAME_TOOLTIP);
		fNameInputTextField.setLayoutData(textGridData);
		fNameInputTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// ignore
				if (fVersionInputTextField.getText().length() > 0 && fNameInputTextField.getText().length() > 0
						&& fFolderInputTextField.getText().length() > 0) {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				} else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});

		//Rule Set Folder
		label = toolkit.createLabel(basicSectionClient, ADD_RULE_SET_FOLDER_DIALOG_VALUE);
		label.setToolTipText(R4EUIConstants.RULESET_FILE_PATH_TOOLTIP);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fFolderInputTextField = toolkit.createText(basicSectionClient, "", SWT.SINGLE | SWT.BORDER);
		final GridData folderTextData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		folderTextData.horizontalSpan = 2;
		fFolderInputTextField.setToolTipText(R4EUIConstants.RULESET_FILE_PATH_TOOLTIP);
		fFolderInputTextField.setLayoutData(folderTextData);
		final Button folderButton = toolkit.createButton(basicSectionClient, "", SWT.NONE);
		folderButton.setImage(PlatformUI.getWorkbench()
				.getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER)
				.createImage()); // $codepro.audit.disable methodChainLength
		textGridData = new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false);
		textGridData.horizontalSpan = 1;
		folderButton.setToolTipText(R4EUIConstants.RULESET_FOLDER_TOOLTIP);
		folderButton.setLayoutData(textGridData);
		folderButton.addSelectionListener(new SelectionAdapter() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			@SuppressWarnings("synthetic-access")
			@Override
			public void widgetSelected(SelectionEvent event) {
				final String result = folderButtonPressed();
				if (null == result) {
					fFolderInputTextField.setText("");
				} else {
					fFolderInputTextField.setText(result);
				}
			}
		});
		fFolderInputTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				// ignore
				if (fVersionInputTextField.getText().length() > 0 && fNameInputTextField.getText().length() > 0
						&& fFolderInputTextField.getText().length() > 0) {
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
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		final Control bar = super.createButtonBar(parent);
		getButton(IDialogConstants.OK_ID).setEnabled(false);
		return bar;
	}

	/**
	 * Method folderButtonPressed.
	 * 
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
	public String getVersionValue() {
		return fVersionValue;
	}

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the rule set folder input string
	 */
	public String getFolderValue() {
		return fFolderValue;
	}

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the rule set name input string
	 */
	public String getNameValue() {
		return fNameValue;
	}

	/**
	 * Validates the input.
	 * <p>
	 * The default implementation of this framework method delegates the request to the supplied input validator object;
	 * if it finds the input invalid, the error message is displayed in the dialog's message line. This hook method is
	 * called whenever the text changes in the input field.
	 * </p>
	 * 
	 * @param aText
	 *            Text
	 * @return String
	 */
	protected String validateFolderInput(Text aText) {
		return ((R4EInputValidator) fValidator).isFolderValid(aText.getText());
	}

	/**
	 * Method validateFileExists.
	 * 
	 * @param aFilePath
	 *            String
	 * @return String
	 */
	private String validateFileExists(String aFilePath) {
		return ((R4EInputValidator) fValidator).isFileExists(aFilePath);
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
