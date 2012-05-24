// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the dialog used to get the next review phase to transition to
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ChangeStateDialog extends FormDialog implements IChangeStateDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field NEXT_REVIEW_PHASE_DIALOG_TITLE. (value is ""Select New Review Phase"")
	 */
	private static final String NEXT_REVIEW_PHASE_DIALOG_TITLE = "Select New Review Phase";

	/**
	 * Field NEXT_REVIEW_PHASE_DIALOG_VALUE. (value is ""New Phase"")
	 */
	private static final String NEXT_REVIEW_PHASE_DIALOG_VALUE = "New Phase";

	/**
	 * Field NEXT_REVIEW_PHASE_TOOLTIP. (value is ""This is used to specify the new review phase to advance the review
	 * to"")
	 */
	private static final String NEXT_REVIEW_PHASE_TOOLTIP = "This is used to specify the new Review Phase to advance the Review to";

	/**
	 * Field NEXT_ANOMALY_STATE_DIALOG_TITLE. (value is ""Select New Anomaly State"")
	 */
	private static final String NEXT_ANOMALY_STATE_DIALOG_TITLE = "Select New Anomaly State";

	/**
	 * Field NEXT_ANOMALY_STATE_DIALOG_VALUE. (value is ""New State"")
	 */
	private static final String NEXT_ANOMALY_STATE_DIALOG_VALUE = "New State";

	/**
	 * Field NEXT_ANOMALY_STATE_TOOLTIP. (value is ""This is used to specify the new Anomaly State to advance the
	 * Anomaly to"")
	 */
	private static final String NEXT_ANOMALY_STATE_TOOLTIP = "This is used to specify the new Anomaly State to advance the Anomaly to";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fNewStateCombo.
	 */
	private CCombo fNewStateCombo = null;

	/**
	 * Field fNewState.
	 */
	private String fNewState;

	/**
	 * Field fElementType.
	 */
	private Class<?> fElementType = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ChangeStateDialog.
	 * 
	 * @param aParentShell
	 *            Shell
	 * @param aElementType
	 *            IR4EUIModelElement
	 */
	public ChangeStateDialog(Shell aParentShell, Class<?> aElementType) {
		super(aParentShell);
		fElementType = aElementType;
		setBlockOnOpen(true);
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
			//Next Review Phase (no validation needed as this is a read-only combo box
			fNewState = fNewStateCombo.getText();
		} else {
			fNewState = null;
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
		shell.setText(fElementType.equals(R4EUIReviewBasic.class)
				? NEXT_REVIEW_PHASE_DIALOG_TITLE
				: NEXT_ANOMALY_STATE_DIALOG_TITLE);
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

		//Next Review Phase
		final Label label = toolkit.createLabel(composite, fElementType.equals(R4EUIReviewBasic.class)
				? NEXT_REVIEW_PHASE_DIALOG_VALUE
				: NEXT_ANOMALY_STATE_DIALOG_VALUE);
		label.setToolTipText(R4EUIConstants.REVIEW_TYPE_TOOLTIP);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false));
		fNewStateCombo = new CCombo(composite, SWT.BORDER | SWT.READ_ONLY);
		textGridData = new GridData(GridData.FILL, GridData.FILL, true, false);
		textGridData.horizontalSpan = 3;
		fNewStateCombo.setToolTipText(fElementType.equals(R4EUIReviewBasic.class)
				? NEXT_REVIEW_PHASE_TOOLTIP
				: NEXT_ANOMALY_STATE_TOOLTIP);
		fNewStateCombo.setLayoutData(textGridData);
	}

	/**
	 * Returns new state.
	 * 
	 * @return the new state as a String
	 */
	public String getState() {
		return fNewState;
	}

	/**
	 * Method setPhases.
	 * 
	 * @param aStates
	 *            String[]
	 */
	public void setStates(String[] aStates) {
		fNewStateCombo.setItems(aStates);
		fNewStateCombo.select(0);
	}
}
