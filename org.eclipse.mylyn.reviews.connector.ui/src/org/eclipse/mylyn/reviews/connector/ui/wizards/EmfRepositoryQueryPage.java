/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * Copyright (c) 2004, 2010 Tasktop Technologies and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation, Donor Bugzilla connector sourced code
 *   Frank Becker - Donor Bugzilla connector sourced code
 *******************************************************************************/
package org.eclipse.mylyn.reviews.connector.ui.wizards;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.mylyn.commons.workbench.forms.SectionComposite;
import org.eclipse.mylyn.internal.reviews.connector.ui.EmfUiPlugin;
import org.eclipse.mylyn.tasks.core.IRepositoryQuery;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.EmfAttributeMapper;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.reviews.connector.query.EmfQueryEngine;
import org.eclipse.mylyn.reviews.connector.query.QueryClause;
import org.eclipse.mylyn.reviews.connector.query.QueryException;
import org.eclipse.mylyn.reviews.connector.query.QueryOperation;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractRepositoryQueryPage2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Miles Parker
 * @author Mik Kersten (Components lifted from Bugzilla) (hardening of prototype)
 * @author Frank Becker (Components lifted from Bugzilla)
 */
public abstract class EmfRepositoryQueryPage extends AbstractRepositoryQueryPage2 {

	abstract class EmfControl {
		EAttribute attribute;

		public EmfControl(EAttribute attribute) {
			this.attribute = attribute;
		}

		abstract void create(Composite parent);

		abstract void applyTo(IRepositoryQuery query);

		abstract void restoreState(QueryClause clause);

		abstract void clearControls();

		public EAttribute getAttribute() {
			return attribute;
		}

		public IStatus validate() {
			return Status.OK_STATUS;
		}

		void clear(IRepositoryQuery query) {
			query.setAttribute(getOperationKey(), null);
			query.setAttribute(getValueKey(), null);
		}

		String getValueKey() {
			return getConnector().getSchema().getKey(attribute) + EmfQueryEngine.QUERY_DELIMITER
					+ EmfQueryEngine.QUERY_VALUE;
		}

		String getOperationKey() {
			return getConnector().getSchema().getKey(attribute) + EmfQueryEngine.QUERY_DELIMITER
					+ EmfQueryEngine.QUERY_OPERATION;
		}
	}

	class EmfTextControl extends EmfControl {

		private Text textControl;

		private Combo operationCombo;

		private final QueryOperation[] operations;

		public EmfTextControl(EAttribute attribute) {
			super(attribute);
			operations = QueryOperation.TEXT_OPERATIONS;
		}

		@Override
		void create(Composite parent) {
			Label label = new Label(parent, SWT.LEFT);
			label.setText(getConnector().getSchema().getLabel(attribute) + ":"); //$NON-NLS-1$

			textControl = new Text(parent, SWT.SINGLE | SWT.BORDER);
			textControl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			textControl.addModifyListener(new ModifyListenerImplementation());

			operationCombo = new Combo(parent, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
			operationCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
			String[] opDescs = QueryOperation.toDescription(operations);
			operationCombo.setItems(opDescs);
			operationCombo.setText(opDescs[0]);
			operationCombo.select(0);
		}

		@Override
		void applyTo(IRepositoryQuery query) {
			if (textControl.getText().equals("")) { //$NON-NLS-1$
				clear(query);
				return;
			}
			QueryOperation selectedOperation = operations[operationCombo.getSelectionIndex()];
			query.setAttribute(getOperationKey(), selectedOperation.getId());
			query.setAttribute(getValueKey(), textControl.getText());
		}

		@Override
		void restoreState(QueryClause clause) {
			int i = 0;
			for (QueryOperation op : operations) {
				if (op == clause.getOperation()) {
					operationCombo.select(i);
					break;
				}
				i++;
			}
			textControl.setText(clause.getValue());
		}

		@Override
		void clearControls() {
			textControl.setText("");
			String[] opDescs = QueryOperation.toDescription(operations);
			operationCombo.setText(opDescs[0]);
			operationCombo.select(0);
		}
	}

	class EmfDateControl extends EmfControl {

		private DateTime dateTimeControl1;

		private DateTime dateTimeControl2;

		private Composite dateTimeSection;

		private Combo operationCombo;

		private final QueryOperation[] operations;

		public EmfDateControl(EAttribute attribute) {
			super(attribute);
			operations = QueryOperation.DATE_OPERATIONS;
		}

		@Override
		void create(Composite parent) {

			Composite fullContainer = new Composite(parent, SWT.NONE);
			fullContainer.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
			fullContainer.setLayout(new GridLayout(1, true));

			Label label = new Label(fullContainer, SWT.LEFT);
			label.setText(getConnector().getSchema().getLabel(attribute) + ":"); //$NON-NLS-1$

			Composite controlContainer = new Composite(fullContainer, SWT.BORDER);
			controlContainer.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 1, 1));
			controlContainer.setLayout(new GridLayout(1, true));
			controlContainer.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));

			operationCombo = new Combo(controlContainer, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
			operationCombo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
			String[] opDescs = QueryOperation.toDescription(operations);
			operationCombo.setItems(opDescs);
			operationCombo.setText(opDescs[0]);
			operationCombo.select(0);
			operationCombo.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					updateControls();
					scrolledComposite.setOrigin(0, scrolledComposite.getSize().y);
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}
			});

			dateTimeSection = new Composite(controlContainer, SWT.NONE);
			dateTimeSection.setBackground(controlContainer.getBackground());
			dateTimeSection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
			dateTimeSection.setLayout(new GridLayout(1, true));
			dateTimeControl1 = new DateTime(dateTimeSection, SWT.DATE | SWT.MEDIUM);
			dateTimeControl1.setBackground(controlContainer.getBackground());
			dateTimeControl2 = new DateTime(dateTimeSection, SWT.DATE | SWT.MEDIUM);
			dateTimeControl2.setBackground(controlContainer.getBackground());
			SelectionListener listener = new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					updateButtons();
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					updateButtons();
				}
			};
			dateTimeControl1.addSelectionListener(listener);
			dateTimeControl2.addSelectionListener(listener);
			operationCombo.addSelectionListener(listener);

			updateControls();
		}

		@Override
		void applyTo(IRepositoryQuery query) {
			final QueryOperation selectedOperation = operations[operationCombo.getSelectionIndex()];
			if (selectedOperation == QueryOperation.ALL_DATES) {
				clear(query);
				return;
			}
			query.setAttribute(getOperationKey(), selectedOperation.getId());

			EmfAttributeMapper mapper = getConnector().getEmfMapper(getTaskRepository());
			String date1 = null;
			if (dateTimeControl1.isEnabled()) {
				Calendar calendar1 = Calendar.getInstance();
				calendar1.set(dateTimeControl1.getYear(), dateTimeControl1.getMonth(), dateTimeControl1.getDay(), 0, 0);
				date1 = mapper.getDateString(calendar1.getTime());
			}
			String date2 = null;
			if (dateTimeControl2.isEnabled()) {
				Calendar calendar2 = Calendar.getInstance();
				calendar2.set(dateTimeControl2.getYear(), dateTimeControl2.getMonth(), dateTimeControl2.getDay(), 0, 0);
				date2 = mapper.getDateString(calendar2.getTime());
			}
			String dateValue;
			if (selectedOperation == QueryOperation.IN_RANGE) {
				dateValue = date1 + EmfQueryEngine.LIST_ITEM_SEPERATOR + date2;
			} else if (selectedOperation == QueryOperation.BEFORE) {
				dateValue = date2;
			} else if (selectedOperation == QueryOperation.AFTER) {
				dateValue = date1;
			} else {
				throw new RuntimeException("Internal Error, unexpected operation: " + selectedOperation); //$NON-NLS-1$
			}
			query.setAttribute(getValueKey(), dateValue);
		}

		@Override
		void restoreState(QueryClause clause) {
			int i = 0;
			for (QueryOperation op : operations) {
				if (op == clause.getOperation()) {
					operationCombo.select(i);
					break;
				}
				i++;
			}
			updateControls();
			QueryOperation selectedOperation = operations[operationCombo.getSelectionIndex()];
			EmfAttributeMapper attributeMapper = getConnector().getEmfMapper(getTaskRepository());
			Date after = null;
			Date before = null;
			if (selectedOperation == QueryOperation.IN_RANGE) {
				String[] dateParts = StringUtils.split(clause.getValue(), ",");
				after = attributeMapper.getDateValue(dateParts[0]);
				before = attributeMapper.getDateValue(dateParts[1]);
			}
			if (selectedOperation == QueryOperation.AFTER) {
				after = attributeMapper.getDateValue(clause.getValue());
			}
			if (selectedOperation == QueryOperation.BEFORE) {
				before = attributeMapper.getDateValue(clause.getValue());
			}
			if (after != null) {
				Calendar calendarAfter = Calendar.getInstance();
				calendarAfter.setTime(after);
				dateTimeControl1.setDate(calendarAfter.get(Calendar.YEAR), calendarAfter.get(Calendar.MONTH),
						calendarAfter.get(Calendar.DAY_OF_MONTH));
			}
			if (before != null) {
				Calendar calendarBefore = Calendar.getInstance();
				calendarBefore.setTime(before);
				dateTimeControl2.setDate(calendarBefore.get(Calendar.YEAR), calendarBefore.get(Calendar.MONTH),
						calendarBefore.get(Calendar.DAY_OF_MONTH));
			}
		}

		void updateControls() {
			QueryOperation selectedOperation = operations[operationCombo.getSelectionIndex()];
			boolean enable1 = false;
			boolean enable2 = false;
			if (selectedOperation == QueryOperation.IN_RANGE) {
				enable1 = true;
				enable2 = true;
			} else if (selectedOperation == QueryOperation.BEFORE) {
				enable1 = true;
			} else if (selectedOperation == QueryOperation.AFTER) {
				enable1 = true;
			}
			dateTimeControl1.setEnabled(enable1);
			dateTimeControl1.setVisible(enable1);
			dateTimeControl2.setEnabled(enable2);
			dateTimeControl2.setVisible(enable2);
		}

		@Override
		void clearControls() {
			String[] opDescs = QueryOperation.toDescription(operations);
			operationCombo.setText(opDescs[0]);
			operationCombo.select(0);
			updateControls();
		}

		@Override
		public IStatus validate() {
			QueryOperation selectedOperation = operations[operationCombo.getSelectionIndex()];
			if (selectedOperation == QueryOperation.IN_RANGE) {
				Calendar calendar1 = Calendar.getInstance();
				calendar1.set(dateTimeControl1.getYear(), dateTimeControl1.getMonth(), dateTimeControl1.getDay(), 0, 0);
				Calendar calendar2 = Calendar.getInstance();
				calendar2.set(dateTimeControl2.getYear(), dateTimeControl2.getMonth(), dateTimeControl2.getDay(), 0, 0);
				if (calendar1.after(calendar2)) {
					return new Status(IStatus.ERROR, EmfUiPlugin.PLUGIN_ID, "The date range for "
							+ getConnector().getSchema().getLabel(attribute) + " isn't valid.");
				}
			}
			return Status.OK_STATUS;
		}
	}

	class EmfMultiSelectControl extends EmfControl {

		private org.eclipse.swt.widgets.List choiceList;

		public EmfMultiSelectControl(EAttribute attribute) {
			super(attribute);
			assert attribute.getEType() instanceof EEnum;
		}

		@Override
		void create(Composite parent) {
			Composite container = new Composite(parent, SWT.NONE);
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			container.setLayout(new GridLayout(1, true));

			Label label = new Label(container, SWT.LEFT);
			label.setText(getConnector().getSchema().getLabel(attribute) + ":"); //$NON-NLS-1$

			choiceList = new org.eclipse.swt.widgets.List(container, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER);
			choiceList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			if (attribute.getEAttributeType() instanceof EEnum) {
				EEnum enumerator = (EEnum) attribute.getEAttributeType();
				for (EEnumLiteral literal : enumerator.getELiterals()) {
					choiceList.add(literal.getName());
				}
			}
		}

		@Override
		void applyTo(IRepositoryQuery query) {
			if (choiceList.getSelection().length == 0) {
				clear(query);
				return;
			}
			String itemList = StringUtils.join(choiceList.getSelection(), EmfQueryEngine.LIST_ITEM_SEPERATOR);
			query.setAttribute(getConnector().getSchema().getKey(attribute) + EmfQueryEngine.QUERY_DELIMITER
					+ EmfQueryEngine.QUERY_OPERATION, QueryOperation.MATCH_ITEMS.getId());
			query.setAttribute(getConnector().getSchema().getKey(attribute) + EmfQueryEngine.QUERY_DELIMITER
					+ EmfQueryEngine.QUERY_VALUE, itemList);
		}

		@Override
		void restoreState(QueryClause clause) {
			String[] items = StringUtils.split(clause.getValue(), EmfQueryEngine.LIST_ITEM_SEPERATOR);
			choiceList.setSelection(items);
		}

		@Override
		void clearControls() {
			choiceList.setSelection(-1);
		}
	}

	private SectionComposite scrolledComposite;

	private final List<EmfControl> managedControls = new ArrayList<EmfControl>();

	private final List<EmfControl> multiControls = new ArrayList<EmfControl>();

	private final List<EmfControl> dateControls = new ArrayList<EmfControl>();

	public EmfRepositoryQueryPage(TaskRepository repository, String pageName, IRepositoryQuery query) {
		super(pageName, repository, query);
		setDescription(Messages.EmfBaseQueryPage_Description);
		setNeedsClear(true);
		setNeedsRefresh(true);
		for (EAttribute searchAttribute : getConnector().getSearchAttributes()) {
			if (searchAttribute.getEType() == EcorePackage.Literals.ESTRING) {
				managedControls.add(new EmfTextControl(searchAttribute));
			} else if (searchAttribute.getEType() == EcorePackage.Literals.EDATE) {
				EmfDateControl dateControl = new EmfDateControl(searchAttribute);
				managedControls.add(dateControl);
				dateControls.add(dateControl);
			} else if (searchAttribute.getEType() instanceof EEnum) {
				EmfMultiSelectControl multiControl = new EmfMultiSelectControl(searchAttribute);
				managedControls.add(multiControl);
				multiControls.add(multiControl);
			}
		}
	}

	private final class ModifyListenerImplementation implements ModifyListener {
		public void modifyText(ModifyEvent e) {
			if (isControlCreated()) {
				setPageComplete(isPageComplete());
			}
		}
	}

	@Override
	protected void createPageContent(SectionComposite parent) {
		this.scrolledComposite = parent;

		Composite scrolledBodyComposite = scrolledComposite.getContent();
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		scrolledBodyComposite.setLayout(layout);

		final Composite basicComposite = new Composite(scrolledBodyComposite, SWT.NONE);
		GridLayout basicLayout = new GridLayout(4, false);
		basicLayout.marginHeight = 0;
		basicLayout.marginWidth = 0;
		//layout.marginRight = 5;
		basicComposite.setLayout(basicLayout);
		GridData g = new GridData(GridData.FILL, GridData.FILL, true, true);
		g.widthHint = 500;
		basicComposite.setLayoutData(g);
		Dialog.applyDialogFont(basicComposite);

		for (EmfControl managedControl : managedControls) {
			if (managedControl instanceof EmfTextControl) {
				managedControl.create(basicComposite);
			}
		}

		Composite choiceArea = new Composite(scrolledBodyComposite, SWT.NONE);
		GridLayout choiceLayout = new GridLayout(multiControls.size(), true);
		choiceArea.setLayout(choiceLayout);
		g = new GridData(GridData.FILL, GridData.FILL, true, true);
		g.widthHint = 500;
		choiceArea.setLayoutData(g);
		Dialog.applyDialogFont(choiceArea);

		for (EmfControl managedControl : multiControls) {
			managedControl.create(choiceArea);
		}

		Composite dateArea = new Composite(scrolledBodyComposite, SWT.NONE);
		GridLayout dateLayout = new GridLayout(dateControls.size(), true);
		dateArea.setLayout(dateLayout);
		g = new GridData(GridData.FILL, GridData.FILL, true, true);
		g.widthHint = 500;
		dateArea.setLayoutData(g);
		Dialog.applyDialogFont(dateArea);

		for (EmfControl managedControl : dateControls) {
			managedControl.create(dateArea);
		}
	}

	@Override
	public abstract AbstractEmfConnector getConnector();

	private EmfClient getClient() {
		AbstractEmfConnector connector = getConnector();
		return connector.getClient(getTaskRepository());
	}

	protected void updateButtons() {
		IWizardContainer c = getContainer();
		if (c != null && c.getCurrentPage() != null) {
			c.updateButtons();
		}
	}

	@Override
	public boolean isPageComplete() {
		setMessage(null);
		if (getQueryTitle() == null || getQueryTitle().trim().length() == 0) {
			setDescription("Enter a title");
			return false;
		}
		for (EmfControl control : managedControls) {
			IStatus validate = control.validate();
			if (!validate.isOK()) {
				setMessage(validate.getMessage(), ERROR);
				return false;
			}
		}
		setDescription("Specify search criteria");
		return true;
	}

	@Override
	public void applyTo(IRepositoryQuery query) {
		query.setSummary(getQueryTitle());
		for (EmfControl managedControl : managedControls) {
			managedControl.applyTo(query);
		}
	}

	@Override
	protected void doRefreshControls() {
		// nothing to do, only the content assist uses the configuration
	}

	@Override
	protected boolean hasRepositoryConfiguration() {
		return getClient().getConfiguration() != null;
	}

	@Override
	protected void doClearControls() {
		restoreState(null);
	}

	@Override
	protected boolean restoreState(IRepositoryQuery query) {
		if (query != null) {
			setQueryTitle(query.getSummary());
			try {
				QueryClause[] clauses = getConnector().getQueryEngine(getTaskRepository()).getClauses(query);
				//TODO, use map
				for (QueryClause clause : clauses) {
					for (EmfControl managedControl : managedControls) {
						if (clause.getFeature().equals(managedControl.getAttribute())) {
							managedControl.restoreState(clause);
							break;
						}
					}
				}
			} catch (QueryException e) {
				//Ignore, we just won't update...
			}
		} else {
			//Query null, so need to clear all values
			for (EmfControl managedControl : managedControls) {
				managedControl.clearControls();
			}
		}

		boolean reflow = false;
		if (reflow) {
			scrolledComposite.reflow(true);
		}

		setPageComplete(isPageComplete());
		updateButtons();

		return true;
	}
}
