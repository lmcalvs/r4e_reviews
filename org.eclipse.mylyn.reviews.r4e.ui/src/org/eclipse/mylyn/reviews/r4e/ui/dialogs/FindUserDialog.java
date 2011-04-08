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
 * This class implements the dialog used to fill-in the Participant element details.
 *  This is a modal dialog
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.dialogs;

import java.io.IOException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.filters.FindUsersTableViewerSorter;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.mylyn.reviews.userSearch.query.IQueryUser;
import org.eclipse.mylyn.reviews.userSearch.query.QueryUserFactory;
import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;
import org.eclipse.mylyn.reviews.userSearch.userInfo.UserInformationFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FindUserDialog extends FormDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field TEXT_FIELD_WIDTH.
	 * (value is "200")
	 */
	private static final int TEXT_FIELD_WIDTH = 200;

	/**
	 * Field FIND_USER_DIALOG_TITLE.
	 * (value is ""Find User"")
	 */
	private static final String FIND_USER_DIALOG_TITLE = "Find User";

	private static final String OFFICE_LABEL = "Office: ";

	private static final String COMPANY_LABEL = "Company: ";

	private static final String DEPARTMENT_LABEL = "Department: ";

	private static final String CITY_LABEL = "City: ";

	private static final String COUNTRY_LABEL = "Country: ";

	private static final String SEARCH_BUTTON_TEXT = "Search";

	private static final String CLEAR_BUTTON_TEXT = "Clear";
	
	private final String NUM_ENTRIES_LABEL = "Number of Entries: ";

	protected static IUserInfo[] NONE = new IUserInfo[] {};
	
	private final int[] USER_TABLE_COLUMN_WIDTH = { 
			60, // User Id
			100, // User name
			80, // Room
			120, // Business phone
			80, // Other phone
			100, // Mobile phone
			70, // Company
			60, // Office
			80, // Department
			80, // City
			80, // Country
			180 }; // E-mail

	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	Composite fUserDetailsForm = null;
	Composite fUserQueyResultsForm = null;
	
	/**
	 * Field fUserIdValue.
	 */
    private String fUserIdValue = "";
    
	/**
	 * Field fUserIdInputTextField.
	 */
    private Text fUserIdInputTextField = null;
    
	/**
	 * Field fUserEmailValue.
	 */
	private String fUserEmailValue = "";
	
	/**
	 * Field fUserEmailInputTextField.
	 */
    //private Text fUserEmailInputTextField = null;
    
	/**
	 * Field fUserDetailsValue.
	 */
	private String fUserDetailsValue = "";

	private Text fUserNameInputTextField = null;

	private Text fUserOfficeInputTextField = null;

	private Text fUserCompanyInputTextField = null;

	private Text fUserDepartmentInputTextField = null;

	private Text fUserCityInputTextField = null;

	private Text fUserCountryInputTextField = null;

	private Button fSearchButton = null;

	private Button fClearButton = null;

	private Table fUsersTable = null;
    
	protected ArrayList<IUserInfo> fUsersList = null;

	protected Label fNumEntriesValue = null;

	private TableViewer fUsersTableViewer;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ParticipantInputDialog.
	 * @param aParentShell Shell
	 */
	public FindUserDialog(Shell aParentShell) {
		super(aParentShell);
    	setBlockOnOpen(true);
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
        	
	    	int selectedTableIndex = fUsersTable.getSelectionIndex();
	    	if (selectedTableIndex < fUsersList.size() && R4EUIConstants.INVALID_VALUE != selectedTableIndex) {
	    		IUserInfo userInfo = fUsersList.get(selectedTableIndex);
		    	fUserIdValue = userInfo.getUserId();
	    		fUserEmailValue = userInfo.getEmail();
	    		UIUtils.buildUserDetailsString(fUsersList.get(selectedTableIndex));
	    	} else {
	        	fUserIdValue = "";
	        	fUserEmailValue = "";
	        	fUserDetailsValue = "";
	    	}
        } else {
        	fUserIdValue = null;
        	fUserEmailValue = null;
        	fUserDetailsValue = null;
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
        shell.setText(FIND_USER_DIALOG_TITLE);
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
		FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		composite.setLayout(layout);
		
		createUserDetailsForm(composite, toolkit);
		createUsersTableForm(composite, toolkit);
	}
    
	/**
	 * Creates new user details area
	 * @param aParent Composite
	 * @param aToolkit FormToolkit
	 */
	private void createUserDetailsForm(Composite aParent, FormToolkit aToolkit) {

		fUserDetailsForm = aToolkit.createComposite(aParent, SWT.BORDER);
		FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		fUserDetailsForm.setLayout(layout);
		
		FormData userDetailsFormData = new FormData();
		userDetailsFormData.top = new FormAttachment(0, 0);
		userDetailsFormData.left = new FormAttachment(0);
		userDetailsFormData.right = new FormAttachment(100);
		fUserDetailsForm.setLayoutData(userDetailsFormData);

		// Id
		Label userIdLabel = aToolkit.createLabel(fUserDetailsForm, R4EUIConstants.ID_LABEL);
		FormData userIdLabelData = new FormData();
		userIdLabelData.top = new FormAttachment(5, 0);
		userIdLabel.setLayoutData(userIdLabelData);
		
		fUserIdInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData userIdTextData = new FormData();
		userIdTextData.top = new FormAttachment(userIdLabel, 0, SWT.TOP);
		userIdTextData.left = new FormAttachment(userIdLabel, 40, SWT.RIGHT);
		userIdTextData.width = (int) (TEXT_FIELD_WIDTH * 1.5);
		fUserIdInputTextField.setLayoutData(userIdTextData);

		// Name
		Label userNameLabel = aToolkit.createLabel(fUserDetailsForm, R4EUIConstants.NAME_LABEL);
		FormData userNameLabelData = new FormData();
		userNameLabelData.top = new FormAttachment(userIdLabel, 0, SWT.TOP);
		userNameLabelData.left = new FormAttachment(fUserIdInputTextField, 40, SWT.RIGHT);
		userNameLabel.setLayoutData(userNameLabelData);

		fUserNameInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData userNameTextData = new FormData();
		userNameTextData.top = new FormAttachment(userNameLabel, 0, SWT.TOP);
		userNameTextData.left = new FormAttachment(userNameLabel, 20, SWT.RIGHT);
		userNameTextData.width = (int) (TEXT_FIELD_WIDTH * 1.5);
		fUserNameInputTextField.setLayoutData(userNameTextData);
		
		// Office
		Label officeLabel = aToolkit.createLabel(fUserDetailsForm, OFFICE_LABEL);
		FormData officeLabelData = new FormData();
		officeLabelData.top = new FormAttachment(fUserIdInputTextField, 5, SWT.BOTTOM);
		officeLabelData.left = new FormAttachment(userIdLabel, 0, SWT.LEFT);
		officeLabel.setLayoutData(officeLabelData);

		fUserOfficeInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData officeTextData = new FormData();
		officeTextData.top = new FormAttachment(officeLabel, 0, SWT.TOP);
		officeTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		officeTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserOfficeInputTextField.setLayoutData(officeTextData);

		// Company
		Label companyLabel = aToolkit.createLabel(fUserDetailsForm, COMPANY_LABEL);
		FormData companyLabelData = new FormData();
		companyLabelData.top = new FormAttachment(officeLabel, 0, SWT.TOP);
		companyLabelData.left = new FormAttachment(userNameLabel, 0, SWT.LEFT);
		companyLabel.setLayoutData(companyLabelData);

		fUserCompanyInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData companyTextData = new FormData();
		companyTextData.top = new FormAttachment(officeLabel, 0, SWT.TOP);
		companyTextData.left = new FormAttachment(fUserNameInputTextField, 0, SWT.LEFT);
		companyTextData.right = new FormAttachment(fUserNameInputTextField, 0, SWT.RIGHT);
		fUserCompanyInputTextField.setLayoutData(companyTextData);

		// Department
		Label deptLabel = aToolkit.createLabel(fUserDetailsForm, DEPARTMENT_LABEL);
		FormData deptLabelData = new FormData();
		deptLabelData.top = new FormAttachment(fUserOfficeInputTextField, 5, SWT.BOTTOM);
		deptLabelData.left = new FormAttachment(userIdLabel, 0, SWT.LEFT);
		deptLabel.setLayoutData(deptLabelData);

		fUserDepartmentInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData deptTextData = new FormData();
		deptTextData.top = new FormAttachment(deptLabel, 0, SWT.TOP);
		deptTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		deptTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserDepartmentInputTextField.setLayoutData(deptTextData);

		// City 
		Label cityLabel = aToolkit.createLabel(fUserDetailsForm, CITY_LABEL);
		FormData cityLabelData = new FormData();
		cityLabelData.top = new FormAttachment(deptLabel, 0, SWT.TOP);
		cityLabelData.left = new FormAttachment(userNameLabel, 0, SWT.LEFT);
		cityLabel.setLayoutData(cityLabelData);

		fUserCityInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData cityTextData = new FormData();
		cityTextData.top = new FormAttachment(cityLabel, 0, SWT.TOP);
		cityTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		cityTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserCityInputTextField.setLayoutData(cityTextData);

		// Country
		Label countryLabel = aToolkit.createLabel(fUserDetailsForm, COUNTRY_LABEL);
		FormData countryLabelData = new FormData();
		countryLabelData.top = new FormAttachment(fUserDepartmentInputTextField, 5, SWT.BOTTOM);
		countryLabelData.left = new FormAttachment(userIdLabel, 0, SWT.LEFT);
		countryLabel.setLayoutData(countryLabelData);

		fUserCountryInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		FormData countryTextData = new FormData();
		countryTextData.top = new FormAttachment(countryLabel, 0, SWT.TOP);
		countryTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		countryTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserCountryInputTextField.setLayoutData(countryTextData);

		// Search button
		fSearchButton = aToolkit.createButton(fUserDetailsForm, SEARCH_BUTTON_TEXT, SWT.PUSH);
		FormData searchButtonData = new FormData();
		searchButtonData.top = new FormAttachment(countryLabel, 0, SWT.TOP);
		searchButtonData.left = new FormAttachment(fUserNameInputTextField, 0, SWT.LEFT);
		fSearchButton.setLayoutData(searchButtonData);
		fSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				searchUser();
			}
		});
		// Set the search button as the default button
		fUserDetailsForm.getShell().setDefaultButton(fSearchButton);
		
		// Clear search button
		fClearButton = aToolkit.createButton(fUserDetailsForm, CLEAR_BUTTON_TEXT, SWT.PUSH);
		FormData clearSearchButtonData = new FormData();
		clearSearchButtonData.top = new FormAttachment(countryLabel, 0, SWT.TOP);
		clearSearchButtonData.left = new FormAttachment(fSearchButton, 10, SWT.RIGHT);
		fClearButton.setLayoutData(clearSearchButtonData);
		fClearButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				clearSearchField();
			}
		});
	}

	/**
	 * Create a form and its table to display the result of the search
	 * @param aParent Composite
	 * @param aToolkit FormToolkit
	 */
	private void createUsersTableForm(Composite aParent, FormToolkit aToolkit) {
		
		fUserQueyResultsForm = aToolkit.createComposite(aParent);
		FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		fUserQueyResultsForm.setLayout(layout);
		
		FormData userFormData = new FormData();
		userFormData.top = new FormAttachment(fUserDetailsForm, 10, SWT.BOTTOM);
		userFormData.left = new FormAttachment(0);
		userFormData.right = new FormAttachment(100);
		userFormData.bottom = new FormAttachment(80);
		fUserQueyResultsForm.setLayoutData(userFormData);

		fUsersTable = aToolkit.createTable(fUserQueyResultsForm, SWT.FULL_SELECTION | SWT.BORDER | SWT.SINGLE);
		fUsersTableViewer = new TableViewer(fUsersTable);

		// Define the layout and columns in the table
		String[] columnId = UserInformationFactory.getInstance().getAttributeTypes();
		for (int i = 0; i < columnId.length; i++) {
			// Create a new column
			TableColumn nameColumn = new TableColumn(fUsersTable, SWT.LEFT);
			nameColumn.setText(columnId[i].toString());
			nameColumn.setWidth(USER_TABLE_COLUMN_WIDTH[i]);
			nameColumn.setMoveable(true);
		}
		fUsersTable.setLayout(new TableLayout());
		fUsersTable.setHeaderVisible(true);
		fUsersTable.setLinesVisible(true);
		fUsersTable.setSize(fUsersTable.getParent().getSize());
		FormData usersTableData = new FormData();
		usersTableData.top = new FormAttachment(fUserQueyResultsForm, 10, SWT.BOTTOM);
		usersTableData.left = new FormAttachment(fUserQueyResultsForm, 0, SWT.LEFT);
		fUsersTable.setLayoutData(usersTableData);
		
		// Attach the sorter to the viewer table and to each column with the bind call
		FindUsersTableViewerSorter.bind(fUsersTableViewer);
		attachContentProvider(fUsersTableViewer);
		attachLabelProvider(fUsersTableViewer);
		
		// Label for the number of items in the table
		Label numEntriesLabel = new Label(fUserQueyResultsForm, SWT.NONE);
		numEntriesLabel.setText(NUM_ENTRIES_LABEL);
		FormData numEntriesLabelData = new FormData();
		numEntriesLabelData.top = new FormAttachment(fUsersTable, 10, SWT.BOTTOM);
		numEntriesLabelData.left = new FormAttachment(fUserQueyResultsForm, 0, SWT.LEFT);
		numEntriesLabel.setLayoutData(numEntriesLabelData);

		//Count Label
		fNumEntriesValue = new Label(fUserQueyResultsForm, SWT.NONE);
		FormData numEntriesValueData = new FormData();
		numEntriesValueData.top = new FormAttachment(numEntriesLabel, 0, SWT.TOP);
		numEntriesValueData.left = new FormAttachment(numEntriesLabel, 10, SWT.RIGHT);
		numEntriesValueData.width = 30;
		fNumEntriesValue.setLayoutData(numEntriesValueData);
		fNumEntriesValue.setText("0");
	}
	
	private void attachLabelProvider(final TableViewer aViewer) {
		aViewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object aElement, int aColumnIndex) {
				return null;
			}

			public String getColumnText(Object aElement, int aColumnIndex) {
				String[] usrElem = ((IUserInfo)aElement).getAttributeValues();
				return usrElem[aColumnIndex];
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void removeListener(ILabelProviderListener lpl) {
			}	
			
			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		});
	}

	private void attachContentProvider(final TableViewer viewer) {
		viewer.setContentProvider(new IStructuredContentProvider() {

			public Object[] getElements(Object inputElement) {
				if (fUsersList == null) {
					return NONE;
				}
				return fUsersList.toArray(new IUserInfo[]{});
			}

			public void dispose() {
			}

			public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
				if (aNewInput instanceof IUserInfo[]) {
					IUserInfo[] usersData = (IUserInfo[]) aNewInput;
					fNumEntriesValue.setText(Integer.toString(usersData.length));
				}
			}
		});
	}
	
	protected void searchUser() {
		try {
			IQueryUser query = new QueryUserFactory().getInstance();
			fUsersList = query.search(fUserIdInputTextField.getText().trim(),
					fUserNameInputTextField.getText().trim(), 
					fUserCompanyInputTextField.getText().trim(),
					fUserOfficeInputTextField.getText().trim(),
					fUserDepartmentInputTextField.getText().trim(),
					fUserCountryInputTextField.getText().trim(),
					fUserCityInputTextField.getText().trim());

			if (fUsersList.size() > 0) {
				// Initially, remove previous list + clear search fields
				fUsersTable.removeAll();
				fUsersTableViewer.setInput(fUsersList.toArray(new IUserInfo[fUsersList.size()]));
				TableColumn[] columns = fUsersTable.getColumns();
				for (TableColumn column : columns) {
					column.pack();
				}
			} else {
				MessageDialog.openInformation(getShell(), "Find User Result", "No Users found");
			}
		} catch (NamingException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Naming Error Detected",
					new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		} catch (IOException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "I/O Error Detected",
					new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		}
	}
	
	/**
	 * Clear all fields in the search area
	 * 
	 */
	protected void clearSearchField() {
		fUserIdInputTextField.setText("");
		fUserNameInputTextField.setText("");
		fUserOfficeInputTextField.setText("");
		fUserCompanyInputTextField.setText("");
		fUserDepartmentInputTextField.setText("");
		fUserCityInputTextField.setText("");
		fUserCountryInputTextField.setText("");
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
	 * Method getUserIdValue.
     * @return the user id 
     */
    public String getUserIdValue() {
        return fUserIdValue;
    }
    
    /**
	 * Method getUserEmailValue.
     * @return the user email
     */
    public String getUserEmailValue() {
        return fUserEmailValue;
    }
    
    /**
	 * Method getUserDetailsValue.
     * @return the user details
     */
    public String getUserDetailsValue() {
        return fUserDetailsValue;
    }
}
