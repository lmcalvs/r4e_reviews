package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class CalendarDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	private static final String ADD_CALENDAR_DIALOG_TITLE = "Select Date";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	protected DateTime fCalendar = null;
	private GregorianCalendar fDate = null;
	
	
	// ------------------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------------------
	
	public CalendarDialog(Shell aParentShell) {
		super(aParentShell);
    	setBlockOnOpen(true);
	}
    
    
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
    
    /**
     * Method configureShell.
     * @param shell Shell
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
	protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(ADD_CALENDAR_DIALOG_TITLE);
    }
    
    /**
     * Method buttonPressed.
     * @param buttonId int
     * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
     */
    @Override
	protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
        	fDate = new GregorianCalendar(fCalendar.getYear(), fCalendar.getMonth(), fCalendar.getDay());
        }
        super.buttonPressed(buttonId);
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

		final ScrolledForm sform = mform.getForm();
		sform.setExpandVertical(true);
		Composite composite = sform.getBody();
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
	    fCalendar = new DateTime(composite, SWT.CALENDAR | SWT.BORDER);
	    fCalendar.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
	}
    
	public Date getDate() {
		return fDate.getTime();
	}
}
