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

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.FindUsersTableViewerSorter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.userSearch.query.IQueryUser;
import org.eclipse.mylyn.reviews.userSearch.query.QueryUserFactory;
import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;
import org.eclipse.mylyn.reviews.userSearch.userInfo.UserInformationFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
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
public class FindUserDialog extends FormDialog implements IFindUserDialog {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field TEXT_FIELD_WIDTH. (value is "300")
	 */
	private static final int TEXT_FIELD_WIDTH = 300;

	/**
	 * Field DIALOG_MIN_WIDTH. (value is "860")
	 */
	private static final int DIALOG_MIN_WIDTH = 860;

	/**
	 * Field DIALOG_MIN_HEIGTH. (value is "550")
	 */
	private static final int DIALOG_MIN_HEIGHT = 550;

	/**
	 * Field FIND_USER_DIALOG_TITLE. (value is ""Find User"")
	 */
	private static final String FIND_USER_DIALOG_TITLE = "Find User";

	/**
	 * Field USER_INFORMATION_LABEL. (value is ""User Information"")
	 */
	private static final String USER_INFORMATION_LABEL = "Search Filters";

	/**
	 * Field QUERY_RESULTS_LABEL. (value is ""Query Results"")
	 */
	private static final String QUERY_RESULTS_LABEL = "Query Results";

	/**
	 * Field OFFICE_LABEL. (value is ""Office: "")
	 */
	private static final String OFFICE_LABEL = "Office: ";

	/**
	 * Field COMPANY_LABEL. (value is ""Company: "")
	 */
	private static final String COMPANY_LABEL = "Company: ";

	/**
	 * Field DEPARTMENT_LABEL. (value is ""Department: "")
	 */
	private static final String DEPARTMENT_LABEL = "Department: ";

	/**
	 * Field CITY_LABEL. (value is ""City: "")
	 */
	private static final String CITY_LABEL = "City: ";

	/**
	 * Field COUNTRY_LABEL. (value is ""Country: "")
	 */
	private static final String COUNTRY_LABEL = "Country: ";

	/**
	 * Field SEARCH_BUTTON_TEXT. (value is ""Search"")
	 */
	private static final String SEARCH_BUTTON_TEXT = "Search";

	/**
	 * Field CLEAR_BUTTON_TEXT. (value is ""Clear"")
	 */
	private static final String CLEAR_BUTTON_TEXT = "Clear";

	/**
	 * Field NUM_ENTRIES_LABEL. (value is ""Number of Entries: "")
	 */
	private static final String NUM_ENTRIES_LABEL = "Number of Entries: ";

	/**
	 * Field USERS_ADDED_LABEL. (value is ""Added Users: "")
	 */
	private static final String USERS_ADDED_LABEL = "Users: ";

	/**
	 * Field ADD_USER_BUTTON_LABEL. (value is ""Add Users"")
	 */
	private static final String ADD_USER_BUTTON_LABEL = "Add Users";

	/**
	 * Field NONE.
	 */
	protected static final IUserInfo[] NONE = new IUserInfo[] {};

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fUserDetailsForm.
	 */
	private Group fUserDetailsForm = null;

	/**
	 * Field fUserQueyResultsForm.
	 */
	private Group fUserQueyResultsForm = null;

	/**
	 * Field fUserAddedForm.
	 */
	private Group fUserAddedForm = null;

	/**
	 * Field fUserIdInputTextField.
	 */
	protected Text fUserIdInputTextField = null;

	/**
	 * Field fUserNameInputTextField.
	 */
	protected Text fUserNameInputTextField = null;

	/**
	 * Field fUserOfficeInputTextField.
	 */
	protected Text fUserOfficeInputTextField = null;

	/**
	 * Field fUserCompanyInputTextField.
	 */
	protected Text fUserCompanyInputTextField = null;

	/**
	 * Field fUserDepartmentInputTextField.
	 */
	protected Text fUserDepartmentInputTextField = null;

	/**
	 * Field fUserCityInputTextField.
	 */
	protected Text fUserCityInputTextField = null;

	/**
	 * Field fUserCountryInputTextField.
	 */
	protected Text fUserCountryInputTextField = null;

	/**
	 * Field fSearchButton.
	 */
	private Button fSearchButton = null;

	/**
	 * Field fClearButton.
	 */
	private Button fClearButton = null;

	/**
	 * Field fUsersList.
	 */
	protected List<IUserInfo> fUsersList = null;

	/**
	 * Field fNumEntriesValue.
	 */
	protected Label fNumEntriesValue = null;

	/**
	 * Field fUsersTableViewer.
	 */
	protected TableViewer fUsersTableViewer;

	/**
	 * Field fUserAddedValue.
	 */
	protected Text fUserAddedValue = null;

	/**
	 * Field fAddUserButton.
	 */
	private Button fAddUserButton;

	/**
	 * Field fUserInfos.
	 */
	private List<IUserInfo> fUserInfos = new ArrayList<IUserInfo>();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for FindUserDialog.
	 * 
	 * @param aParentShell
	 *            Shell
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
	 * 
	 * @param buttonId
	 *            int
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			boolean userFound;
			final String[] ids = fUserAddedValue.getText().trim().split(R4EUIConstants.LIST_SEPARATOR);
			for (String id : ids) {
				userFound = false;
				for (IUserInfo userInfo : fUserInfos) {
					if (id.trim().equalsIgnoreCase(userInfo.getUserId())) {
						userFound = true;
						break;
					}
				}
				if (!userFound) {
					final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
							"Cannot add User", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, "User " + id.trim()
									+ " not found in database", null), IStatus.ERROR);
					dialog.open();
					continue;
				}
			}
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
		shell.setText(FIND_USER_DIALOG_TITLE);
	}

	/**
	 * Configures the dialog form and creates form content. Clients should override this method.
	 * 
	 * @param mform
	 *            IManagedForm - the dialog form
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
		createSearchFiltersForm(composite, toolkit);
		createAddUsersForm(composite, toolkit);
		createUsersTableForm(composite, toolkit);
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
	 * Creates new search filters area
	 * 
	 * @param aParent
	 *            Composite
	 * @param aToolkit
	 *            FormToolkit
	 */
	private void createSearchFiltersForm(Composite aParent, FormToolkit aToolkit) {

		fUserDetailsForm = new Group(aParent, SWT.NONE);
		fUserDetailsForm.setText(USER_INFORMATION_LABEL);
		fUserDetailsForm.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		final FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		fUserDetailsForm.setLayout(layout);

		final FormData userDetailsFormData = new FormData();
		userDetailsFormData.top = new FormAttachment(0, 0);
		userDetailsFormData.left = new FormAttachment(0, 0);
		userDetailsFormData.right = new FormAttachment(100, 0);
		fUserDetailsForm.setLayoutData(userDetailsFormData);

		// Id
		final Label userIdLabel = aToolkit.createLabel(fUserDetailsForm, R4EUIConstants.ID_LABEL);
		final FormData userIdLabelData = new FormData();
		userIdLabelData.top = new FormAttachment(5, 0);
		userIdLabel.setToolTipText(R4EUIConstants.USER_ID_TOOLTIP);
		userIdLabel.setLayoutData(userIdLabelData);

		fUserIdInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData userIdTextData = new FormData();
		userIdTextData.top = new FormAttachment(userIdLabel, 0, SWT.TOP);
		userIdTextData.left = new FormAttachment(userIdLabel, 60, SWT.RIGHT);
		userIdTextData.width = TEXT_FIELD_WIDTH;
		fUserIdInputTextField.setToolTipText(R4EUIConstants.USER_ID_TOOLTIP);
		fUserIdInputTextField.setLayoutData(userIdTextData);

		// Name
		final Label userNameLabel = aToolkit.createLabel(fUserDetailsForm, R4EUIConstants.NAME_LABEL);
		final FormData userNameLabelData = new FormData();
		userNameLabelData.top = new FormAttachment(userIdLabel, 0, SWT.TOP);
		userNameLabelData.left = new FormAttachment(fUserIdInputTextField, 40, SWT.RIGHT);
		userNameLabel.setToolTipText(R4EUIConstants.USER_NAME_TOOLTIP);
		userNameLabel.setLayoutData(userNameLabelData);

		fUserNameInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData userNameTextData = new FormData();
		userNameTextData.top = new FormAttachment(userNameLabel, 0, SWT.TOP);
		userNameTextData.left = new FormAttachment(userNameLabel, 25, SWT.RIGHT);
		userNameTextData.width = TEXT_FIELD_WIDTH;
		fUserNameInputTextField.setToolTipText(R4EUIConstants.USER_NAME_TOOLTIP);
		fUserNameInputTextField.setLayoutData(userNameTextData);

		// Office
		final Label officeLabel = aToolkit.createLabel(fUserDetailsForm, OFFICE_LABEL);
		final FormData officeLabelData = new FormData();
		officeLabelData.top = new FormAttachment(fUserIdInputTextField, 5, SWT.BOTTOM);
		officeLabelData.left = new FormAttachment(userIdLabel, 0, SWT.LEFT);
		officeLabel.setToolTipText(R4EUIConstants.USER_OFFICE_TOOLTIP);
		officeLabel.setLayoutData(officeLabelData);

		fUserOfficeInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData officeTextData = new FormData();
		officeTextData.top = new FormAttachment(officeLabel, 0, SWT.TOP);
		officeTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		officeTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserOfficeInputTextField.setToolTipText(R4EUIConstants.USER_OFFICE_TOOLTIP);
		fUserOfficeInputTextField.setLayoutData(officeTextData);

		// Company
		final Label companyLabel = aToolkit.createLabel(fUserDetailsForm, COMPANY_LABEL);
		final FormData companyLabelData = new FormData();
		companyLabelData.top = new FormAttachment(officeLabel, 0, SWT.TOP);
		companyLabelData.left = new FormAttachment(userNameLabel, 0, SWT.LEFT);
		companyLabel.setToolTipText(R4EUIConstants.USER_COMPANY_TOOLTIP);
		companyLabel.setLayoutData(companyLabelData);

		fUserCompanyInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData companyTextData = new FormData();
		companyTextData.top = new FormAttachment(officeLabel, 0, SWT.TOP);
		companyTextData.left = new FormAttachment(fUserNameInputTextField, 0, SWT.LEFT);
		companyTextData.right = new FormAttachment(fUserNameInputTextField, 0, SWT.RIGHT);
		fUserCompanyInputTextField.setToolTipText(R4EUIConstants.USER_COMPANY_TOOLTIP);
		fUserCompanyInputTextField.setLayoutData(companyTextData);

		// Department
		final Label deptLabel = aToolkit.createLabel(fUserDetailsForm, DEPARTMENT_LABEL);
		final FormData deptLabelData = new FormData();
		deptLabelData.top = new FormAttachment(fUserOfficeInputTextField, 5, SWT.BOTTOM);
		deptLabelData.left = new FormAttachment(userIdLabel, 0, SWT.LEFT);
		deptLabel.setToolTipText(R4EUIConstants.USER_DEPARTMENT_TOOLTIP);
		deptLabel.setLayoutData(deptLabelData);

		fUserDepartmentInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData deptTextData = new FormData();
		deptTextData.top = new FormAttachment(deptLabel, 0, SWT.TOP);
		deptTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		deptTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserDepartmentInputTextField.setToolTipText(R4EUIConstants.USER_DEPARTMENT_TOOLTIP);
		fUserDepartmentInputTextField.setLayoutData(deptTextData);

		// City 
		final Label cityLabel = aToolkit.createLabel(fUserDetailsForm, CITY_LABEL);
		final FormData cityLabelData = new FormData();
		cityLabelData.top = new FormAttachment(deptLabel, 0, SWT.TOP);
		cityLabelData.left = new FormAttachment(userNameLabel, 0, SWT.LEFT);
		cityLabel.setToolTipText(R4EUIConstants.USER_CITY_TOOLTIP);
		cityLabel.setLayoutData(cityLabelData);

		fUserCityInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData cityTextData = new FormData();
		cityTextData.top = new FormAttachment(cityLabel, 0, SWT.TOP);
		cityTextData.left = new FormAttachment(fUserNameInputTextField, 0, SWT.LEFT);
		cityTextData.right = new FormAttachment(fUserNameInputTextField, 0, SWT.RIGHT);
		fUserCityInputTextField.setToolTipText(R4EUIConstants.USER_CITY_TOOLTIP);
		fUserCityInputTextField.setLayoutData(cityTextData);

		// Country
		final Label countryLabel = aToolkit.createLabel(fUserDetailsForm, COUNTRY_LABEL);
		final FormData countryLabelData = new FormData();
		countryLabelData.top = new FormAttachment(fUserDepartmentInputTextField, 5, SWT.BOTTOM);
		countryLabelData.left = new FormAttachment(userIdLabel, 0, SWT.LEFT);
		countryLabel.setToolTipText(R4EUIConstants.USER_COUNTRY_TOOLTIP);
		countryLabel.setLayoutData(countryLabelData);

		fUserCountryInputTextField = aToolkit.createText(fUserDetailsForm, "", SWT.SINGLE | SWT.BORDER);
		final FormData countryTextData = new FormData();
		countryTextData.top = new FormAttachment(countryLabel, 0, SWT.TOP);
		countryTextData.left = new FormAttachment(fUserIdInputTextField, 0, SWT.LEFT);
		countryTextData.right = new FormAttachment(fUserIdInputTextField, 0, SWT.RIGHT);
		fUserCountryInputTextField.setToolTipText(R4EUIConstants.USER_COUNTRY_TOOLTIP);
		fUserCountryInputTextField.setLayoutData(countryTextData);

		// Search button
		fSearchButton = aToolkit.createButton(fUserDetailsForm, SEARCH_BUTTON_TEXT, SWT.PUSH);
		final FormData searchButtonData = new FormData();
		searchButtonData.top = new FormAttachment(countryLabel, 0, SWT.TOP);
		searchButtonData.left = new FormAttachment(fUserNameInputTextField, 0, SWT.LEFT);
		fSearchButton.setToolTipText(R4EUIConstants.USER_SEARCH_TOOLTIP);
		fSearchButton.setLayoutData(searchButtonData);
		fSearchButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				getShell().setCursor(getShell().getDisplay().getSystemCursor(SWT.CURSOR_WAIT));
				searchUser();
				updateTableSelections();
				getShell().setCursor(getShell().getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
			}
		});

		// Clear search button
		fClearButton = aToolkit.createButton(fUserDetailsForm, CLEAR_BUTTON_TEXT, SWT.PUSH);
		final FormData clearSearchButtonData = new FormData();
		clearSearchButtonData.top = new FormAttachment(countryLabel, 0, SWT.TOP);
		clearSearchButtonData.left = new FormAttachment(fSearchButton, 10, SWT.RIGHT);
		fClearButton.setToolTipText(R4EUIConstants.USER_CLEAR_TOOLTIP);
		fClearButton.setLayoutData(clearSearchButtonData);
		fClearButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				clearSearchField();
			}
		});
	}

	/**
	 * Create a form and its table to display the result of the search
	 * 
	 * @param aParent
	 *            Composite
	 * @param aToolkit
	 *            FormToolkit
	 */
	private void createUsersTableForm(Composite aParent, FormToolkit aToolkit) {

		fUserQueyResultsForm = new Group(aParent, SWT.NONE);
		fUserQueyResultsForm.setText(QUERY_RESULTS_LABEL);
		fUserQueyResultsForm.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		final FormData userFormData = new FormData();
		userFormData.top = new FormAttachment(fUserDetailsForm, 10, SWT.BOTTOM);
		userFormData.left = new FormAttachment(0);
		userFormData.right = new FormAttachment(100);
		userFormData.bottom = new FormAttachment(fUserAddedForm);

		fUserQueyResultsForm.setLayoutData(userFormData);
		final FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		fUserQueyResultsForm.setLayout(layout);

		// Label for the number of items in the table
		final Label numEntriesLabel = aToolkit.createLabel(fUserQueyResultsForm, NUM_ENTRIES_LABEL);
		final FormData numEntriesLabelData = new FormData();
		numEntriesLabelData.left = new FormAttachment(fUserQueyResultsForm, 0, SWT.LEFT);
		numEntriesLabelData.bottom = new FormAttachment(100);
		numEntriesLabel.setToolTipText(R4EUIConstants.USER_NUM_ENTRIES_TOOLTIP);
		numEntriesLabel.setLayoutData(numEntriesLabelData);

		//Count Label
		fNumEntriesValue = aToolkit.createLabel(fUserQueyResultsForm, "0");
		final FormData numEntriesValueData = new FormData();
		numEntriesValueData.left = new FormAttachment(numEntriesLabel, 10, SWT.RIGHT);
		numEntriesValueData.bottom = new FormAttachment(100);
		numEntriesValueData.width = 30;
		fNumEntriesValue.setToolTipText(R4EUIConstants.USER_NUM_ENTRIES_TOOLTIP);
		fNumEntriesValue.setLayoutData(numEntriesValueData);

		fUsersTableViewer = new TableViewer(fUserQueyResultsForm, SWT.MULTI | SWT.FULL_SELECTION);
		fUsersTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				addUser();
			}
		});

		// Define the layout and columns in the table
		final String[] columnId = UserInformationFactory.getInstance().getAttributeTypes();
		TableColumn nameColumn = null;
		for (String element : columnId) {
			// Create a new column
			nameColumn = new TableColumn(fUsersTableViewer.getTable(), SWT.LEFT);
			nameColumn.setText(element);
			nameColumn.setMoveable(true);
			nameColumn.pack();
		}
		fUsersTableViewer.getTable().setHeaderVisible(true);
		fUsersTableViewer.getTable().setLinesVisible(true);

		final FormData usersTableData = new FormData();
		usersTableData.top = new FormAttachment(0);
		usersTableData.bottom = new FormAttachment(numEntriesLabel, -5, SWT.TOP);
		usersTableData.left = new FormAttachment(0);
		usersTableData.right = new FormAttachment(100);
		fUsersTableViewer.getControl().setLayoutData(usersTableData);

		// Attach the sorter to the viewer table and to each column with the bind call
		FindUsersTableViewerSorter.bind(fUsersTableViewer);
		attachContentProvider(fUsersTableViewer);
		attachLabelProvider(fUsersTableViewer);
	}

	/**
	 * Creates new add users area
	 * 
	 * @param aParent
	 *            Composite
	 * @param aToolkit
	 *            FormToolkit
	 */
	private void createAddUsersForm(Composite aParent, FormToolkit aToolkit) {

		fUserAddedForm = new Group(aParent, SWT.NONE);
		fUserAddedForm.setText(USERS_ADDED_LABEL);
		fUserAddedForm.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
		final FormData userFormData = new FormData();
		userFormData.left = new FormAttachment(0);
		userFormData.right = new FormAttachment(100);
		userFormData.bottom = new FormAttachment(100);

		fUserAddedForm.setLayoutData(userFormData);
		final FormLayout layout = new FormLayout();
		layout.marginWidth = 7;
		layout.marginHeight = 3;
		fUserAddedForm.setLayout(layout);

		//Add Users Button
		fAddUserButton = aToolkit.createButton(fUserAddedForm, ADD_USER_BUTTON_LABEL, SWT.NONE);
		final FormData userAddedButtonData = new FormData();
		userAddedButtonData.top = new FormAttachment(5, 0);
		userAddedButtonData.left = new FormAttachment(fUserAddedForm, 10, SWT.RIGHT);
		fAddUserButton.setToolTipText(R4EUIConstants.USER_ADD_TOOLTIP);
		fAddUserButton.setLayoutData(userAddedButtonData);
		fAddUserButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				addUser();
			}
		});

		//Added Users
		fUserAddedValue = aToolkit.createText(fUserAddedForm, "", SWT.BORDER);
		final FormData userAddedValueData = new FormData();
		userAddedValueData.top = new FormAttachment(fAddUserButton, 0, SWT.TOP);
		userAddedValueData.bottom = new FormAttachment(fAddUserButton, 0, SWT.BOTTOM);
		userAddedValueData.left = new FormAttachment(fAddUserButton, 10, SWT.RIGHT);
		userAddedValueData.right = new FormAttachment(100);
		fUserAddedValue.setToolTipText(R4EUIConstants.USER_ADDED_TOOLTIP);
		fUserAddedValue.setLayoutData(userAddedValueData);
		fUserAddedValue.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (fUserAddedValue.getText().length() > 0) {
					getButton(IDialogConstants.OK_ID).setEnabled(true);
				} else {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});
		fUserAddedValue.addListener(SWT.FocusOut, new Listener() {
			public void handleEvent(Event event) {
				//Update the list of Participants to add
				boolean userFound;
				final List<IUserInfo> updatedInfos = new ArrayList<IUserInfo>();
				final String[] ids = fUserAddedValue.getText().split(R4EUIConstants.LIST_SEPARATOR);
				for (String id : ids) {
					userFound = false;
					for (IUserInfo userInfo : fUserInfos) {
						if (id.trim().equalsIgnoreCase(userInfo.getUserId())) {
							updatedInfos.add(userInfo);
							userFound = true;
							break;
						}
					}
					if (!userFound) {
						IUserInfo newUserInfo = getUser(id.trim());
						if (null != newUserInfo) {
							updatedInfos.add(newUserInfo);
						}
					}
				}
				fUserInfos = updatedInfos;
				updateTableSelections();
			}
		});
	}

	/**
	 * Method attachLabelProvider
	 * 
	 * @param aViewer
	 *            TableViewer
	 */
	private void attachLabelProvider(final TableViewer aViewer) {
		aViewer.setLabelProvider(new ITableLabelProvider() {
			public Image getColumnImage(Object aElement, int aColumnIndex) {
				return null;
			}

			public String getColumnText(Object aElement, int aColumnIndex) {
				final String[] usrElem = ((IUserInfo) aElement).getAttributeValues();
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

	/**
	 * Method attachContentProvider
	 * 
	 * @param aTableViewer
	 *            TableViewer
	 */
	private void attachContentProvider(final TableViewer aTableViewer) {
		aTableViewer.setContentProvider(new IStructuredContentProvider() {

			public Object[] getElements(Object inputElement) {
				if (null == fUsersList) {
					return NONE;
				}
				return fUsersList.toArray(new IUserInfo[] {});
			}

			public void dispose() {
			}

			public void inputChanged(Viewer aViewer, Object aOldInput, Object aNewInput) {
				if (aNewInput instanceof IUserInfo[]) {
					final IUserInfo[] usersData = (IUserInfo[]) aNewInput;
					fNumEntriesValue.setText(Integer.toString(usersData.length));
				}
			}
		});
	}

	/**
	 * Method getUser Queries the external database to get info about a specific user
	 * 
	 * @param aUserId
	 * @return IUserInfo
	 */
	protected IUserInfo getUser(String aUserId) {
		if (null != aUserId && !"".equals(aUserId)) {
			final IQueryUser query = new QueryUserFactory().getInstance();
			try {
				final List<IUserInfo> users = query.searchByUserId(aUserId);
				if (null != users && users.size() > 0) {
					return users.get(0); //return first user
				}
			} catch (NamingException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			} catch (IOException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			}
		}
		return null;
	}

	/**
	 * Method searchUser Queries the external database for a list of users that are matching given criterias
	 */
	protected void searchUser() {
		try {
			if (fUserIdInputTextField.getText().equals("") && fUserNameInputTextField.getText().equals("")
					&& fUserCompanyInputTextField.getText().equals("")
					&& fUserOfficeInputTextField.getText().equals("")
					&& fUserDepartmentInputTextField.getText().equals("")
					&& fUserCountryInputTextField.getText().equals("") && fUserCityInputTextField.getText().equals("")) {
				return; //No filters selected for query, ignore request
			}
			final IQueryUser query = new QueryUserFactory().getInstance();
			fUsersList = query.search(fUserIdInputTextField.getText().trim(), fUserNameInputTextField.getText().trim(),
					fUserCompanyInputTextField.getText().trim(), fUserOfficeInputTextField.getText().trim(),
					fUserDepartmentInputTextField.getText().trim(), fUserCountryInputTextField.getText().trim(),
					fUserCityInputTextField.getText().trim());

			if (fUsersList.size() > 0) {
				fUsersTableViewer.setInput(fUsersList.toArray(new IUserInfo[fUsersList.size()]));
				final TableColumn[] columns = fUsersTableViewer.getTable().getColumns();
				for (TableColumn column : columns) {
					column.pack();
				}
				fUsersTableViewer.refresh();
				fUsersTableViewer.getTable().layout();
			} else {
				MessageDialog.openInformation(getShell(), "Find User Result", "No Users found");
			}
		} catch (NamingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
					"Naming Error Detected", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e),
					IStatus.ERROR);
			dialog.open();
		} catch (IOException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "I/O Error Detected",
					new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		}
	}

	/**
	 * Method clearSearchField Clears all fields in the search area
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
	 * 
	 * @return boolean
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * Method getUserInfos.
	 * 
	 * @return the users infos
	 */
	public List<IUserInfo> getUserInfos() {
		return fUserInfos;
	}

	/**
	 * Method setFocus.
	 */
	public void setDialogsDefaults() {
		// Set the search button as the default button
		getShell().setDefaultButton(fSearchButton);
		getShell().setMinimumSize(DIALOG_MIN_WIDTH, DIALOG_MIN_HEIGHT);
		fUserInfos.clear();
	}

	/**
	 * Method updateTableSelections.
	 */
	private void updateTableSelections() {
		final List<IUserInfo> selectedElements = new ArrayList<IUserInfo>();
		for (IUserInfo userInfo : fUserInfos) {
			for (int i = 0; i < fUsersTableViewer.getTable().getItemCount(); i++) {
				IUserInfo tableUserInfo = (IUserInfo) fUsersTableViewer.getElementAt(i);
				if (userInfo.getUserId().equalsIgnoreCase(tableUserInfo.getUserId())) {
					selectedElements.add(tableUserInfo);
				}
			}
		}
		final StructuredSelection selection = new StructuredSelection(selectedElements);
		fUsersTableViewer.setSelection(selection, true);
	}

	/**
	 * Method addUser.
	 */
	public void addUser() {
		//Add selected Users to the list of Participants to add
		final IStructuredSelection selection = (IStructuredSelection) fUsersTableViewer.getSelection();
		IUserInfo element = null;
		boolean userFound = false;
		for (final Iterator<IUserInfo> iterator = selection.iterator(); iterator.hasNext();) {
			element = iterator.next();
			for (IUserInfo userInfo : fUserInfos) {
				if (element.getUserId().equalsIgnoreCase(userInfo.getUserId())) {
					userFound = true;
					break;
				}
			}
			if (!userFound) {
				fUserInfos.add(element);
			}
		}
		final StringBuffer buffer = new StringBuffer();
		for (IUserInfo userInfo : fUserInfos) {
			buffer.append(userInfo.getUserId().toLowerCase() + R4EUIConstants.LIST_SEPARATOR + " ");
		}
		fUserAddedValue.setText(buffer.toString());
	}
}