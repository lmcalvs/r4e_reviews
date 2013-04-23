/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the dialog used to select the Participant to unassign.
 *  This is a modal dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ParticipantUnassignDialog extends FormDialog implements IParticipantUnassignDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field UNASSIGN_PARTICIPANT_DIALOG_TITLE. (value is ""Select Participants to Unassign"")
	 */
	private static final String UNASSIGN_PARTICIPANT_DIALOG_TITLE = "Select Participants to Unassign";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fParticipantsUnassignValues.
	 */
	private final List<R4EParticipant> fParticipantsUnassignValues = new ArrayList<R4EParticipant>();

	/**
	 * Field fElement.
	 */
	private IR4EUIModelElement fElement = null;

	/**
	 * Field fParticipantsTable.
	 */
	private Table fParticipantsTable = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ParticipantInputDialog.
	 * 
	 * @param aParentShell
	 *            Shell
	 * @param aElement
	 *            IR4EUIModelElement
	 */
	public ParticipantUnassignDialog(Shell aParentShell, IR4EUIModelElement aElement) {
		super(aParentShell);
		fElement = aElement;
		setBlockOnOpen(true);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method buttonPressed.
	 * 
	 * @param aButtonId
	 *            int
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int aButtonId) {
		if (aButtonId == IDialogConstants.OK_ID) {
			for (TableItem item : fParticipantsTable.getItems()) {
				if (item.getChecked()) {
					R4EParticipant participant;
					try {
						participant = R4EUIModelController.getActiveReview().getParticipant(item.getText(), false);
						if (null != participant) {
							fParticipantsUnassignValues.add(participant);
						}
					} catch (ResourceHandlingException e) {
						// just continue
					}
				}
			}
		}
		super.buttonPressed(aButtonId);
	}

	/**
	 * Method configureShell.
	 * 
	 * @param aShell
	 *            Shell
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell aShell) {
		super.configureShell(aShell);
		aShell.setText(UNASSIGN_PARTICIPANT_DIALOG_TITLE);
		aShell.setMinimumSize(R4EUIConstants.DIALOG_DEFAULT_WIDTH / 2, R4EUIConstants.DIALOG_DEFAULT_HEIGHT);
	}

	/**
	 * Method create.
	 * 
	 * @see org.eclipse.ui.forms.FormDialog#create()
	 */
	@Override
	public void create() {
		fParticipantsUnassignValues.clear();
		super.create();
	}

	/**
	 * Method open.
	 * 
	 * @return int
	 * @see org.eclipse.ui.forms.FormDialog#open()
	 */
	@Override
	public int open() {
		return super.open();
	}

	/**
	 * Method close.
	 * 
	 * @return int
	 * @see org.eclipse.ui.forms.FormDialog#close()
	 */
	@Override
	public boolean close() {
		return super.close();
	}

	/**
	 * Configures the dialog form and creates form content. Clients should override this method.
	 * 
	 * @param aForm
	 *            the dialog form
	 */
	@Override
	protected void createFormContent(final IManagedForm aForm) {

		final FormToolkit toolkit = aForm.getToolkit();
		final ScrolledForm sform = aForm.getForm();
		sform.setExpandVertical(true);
		final Composite composite = sform.getBody();
		final GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);

		//Create table
		fParticipantsTable = toolkit.createTable(composite, SWT.CHECK | SWT.V_SCROLL);
		final GridData tableData = new GridData(GridData.FILL, GridData.FILL, true, true);
		fParticipantsTable.setLayoutData(tableData);
		fParticipantsTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				final TableItem[] items = fParticipantsTable.getItems();
				getButton(IDialogConstants.OK_ID).setEnabled(false);
				for (TableItem item : items) {
					if (item.getChecked()) {
						getButton(IDialogConstants.OK_ID).setEnabled(true);
					}
				}
			}
		});

		//Populate table
		EList<String> assignedParticipants = null;
		if (fElement instanceof R4EUIReviewItem) {
			assignedParticipants = ((R4EUIReviewItem) fElement).getItem().getAssignedTo();
		} else if (fElement instanceof R4EUIFileContext) {
			assignedParticipants = ((R4EUIFileContext) fElement).getFileContext().getAssignedTo();
		} else if (fElement instanceof R4EUIContent) {
			assignedParticipants = ((R4EUIContent) fElement).getContent().getAssignedTo();
		} else {
			return; //should never happen
		}
		for (String participant : assignedParticipants) {
			final TableItem item = new TableItem(fParticipantsTable, SWT.NONE);
			item.setText(participant);
		}
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
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * Method getParticpants.
	 * 
	 * @return List<R4EParticipant>
	 */
	public List<R4EParticipant> getParticipants() {
		return fParticipantsUnassignValues;
	}
}
