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
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.reviews.connector.ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.internal.reviews.connector.ui.EmfUiPlugin;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.client.EmfClient;
import org.eclipse.mylyn.tasks.ui.wizards.AbstractTaskRepositoryPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Wizard page to support connecting to a basic EMF task file. In the simple case, just override specifying connector
 * ui.
 * 
 * @author Miles Parker
 */
public abstract class EmfRepositorySettingsPage extends AbstractTaskRepositoryPage {

	private final TaskRepository taskRepository;

	protected Text uriEditor;

	private Text labelEditor;

	private boolean validating;

	private IStatus validity;

	private EmfClient client;

	private Button createButton;

	public EmfRepositorySettingsPage(String name, String description, TaskRepository taskRepository) {
		super(name, description, taskRepository);
		this.taskRepository = taskRepository;
		validity = Status.OK_STATUS;
	}

	@Override
	public String getRepositoryUrl() {
		String url = uriEditor.getText();
		if (new File(url).isAbsolute()) {
			url = URI.createFileURI(url).toString();
		}
		return url;
	}

	@Override
	protected void createSettingControls(Composite parent) {
		Label uriLabel = new Label(parent, SWT.NONE);
		uriLabel.setText("Location:");
		GridDataFactory.fillDefaults()
				.align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false)
				.span(1, 1)
				.applyTo(uriLabel);

		uriEditor = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.BEGINNING)
				.hint(440, SWT.DEFAULT)
				.grab(true, false)
				.span(2, 1)
				.applyTo(uriEditor);

		uriEditor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (getWizard() != null) {
					onUriModify();
				}
			}
		});

		Label labelLabel = new Label(parent, SWT.NONE);
		labelLabel.setText("Label:");
		GridDataFactory.fillDefaults()
				.align(SWT.BEGINNING, SWT.BEGINNING)
				.grab(false, false)
				.span(1, 1)
				.applyTo(labelLabel);

		labelEditor = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).span(2, 1).applyTo(labelEditor);

		labelEditor.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (getWizard() != null && !validating) {
					onLabelModify();
				}
			}
		});

		Composite dummyPanel = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(false, false).span(1, 1).applyTo(dummyPanel);
		Composite uriButtonPanel = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults()
				.align(SWT.END, SWT.BEGINNING)
				.grab(false, false)
				.span(2, 1)
				.applyTo(uriButtonPanel);
		uriButtonPanel.setLayout(new GridLayout(2, true));
		Button browseButton = new Button(uriButtonPanel, SWT.PUSH);
		browseButton.setText("Browse...");
		GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false)
				.span(1, 1)
				.applyTo(browseButton);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browse();
			}
		});

		createButton = new Button(uriButtonPanel, SWT.PUSH);
		createButton.setText("Create...");
		GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.BEGINNING)
				.grab(false, false)
				.span(1, 1)
				.applyTo(createButton);
		createButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				create();
			}
		});

		if (taskRepository != null) {
			uriEditor.setText(StringUtils.trimToEmpty(taskRepository.getRepositoryUrl()));
			labelEditor.setText(StringUtils.trimToEmpty(taskRepository.getRepositoryLabel()));
		} else {
			uriEditor.setText("");
		}

		onLabelModify();
	}

	@Override
	public boolean isPageComplete() {
		return validity.getSeverity() != IStatus.ERROR;
	}

	@SuppressWarnings("restriction")
	@Override
	public void applyTo(TaskRepository repository) {
		repository.setRepositoryUrl(getRepositoryUrl());
		repository.setRepositoryLabel(labelEditor.getText());
	}

	protected void validateSettings() {
		validity = validate();
		updateMessage();
	}

	protected void updateMessage() {
		int messageError = IMessageProvider.INFORMATION;
		if (validity.getSeverity() == IStatus.WARNING) {
			messageError = IMessageProvider.WARNING;
		} else if (validity.getSeverity() == IStatus.ERROR) {
			messageError = IMessageProvider.ERROR;
		}
		setMessage(validity.getMessage(), messageError);
	}

	@Override
	protected IStatus validate() {
		String repositoryUrl = getRepositoryUrl();
		if (client != null && !client.getRepository().getUrl().equals(repositoryUrl)) {
			try {
				client.close();
			} catch (CoreException e) {
			}
			client = null;
			validating = true;
			labelEditor.setText("");
			validating = false;
		}
		if (client == null) {
			if (StringUtils.isBlank(repositoryUrl)) {
				return new Status(IStatus.ERROR, getConnector().getConnectorBundle().getSymbolicName(),
						"Specify a repository.");
			}
			try {
				URI testURI = URI.createURI(repositoryUrl);
				if (testURI.isRelative()) {
					return new Status(IStatus.ERROR, getConnector().getConnectorBundle().getSymbolicName(),
							"Specify an absolute URI. (Like \"C:/..\" or \"http://..\")");
				}
			} catch (IllegalArgumentException e) {
				return new Status(IStatus.ERROR, EmfUiPlugin.PLUGIN_ID, "Repository URI is not valid: "
						+ e.getMessage());
			}
			client = new EmfClient(new TaskRepository(getConnectorKind(), repositoryUrl)) {
				@Override
				public AbstractEmfConnector getConnector() {
					return EmfRepositorySettingsPage.this.getConnector();
				}
			};
			try {
				client.open();
				EObject contentsObject = client.getRootContainer();
				EAttribute nameAttribute = getConnector().getContentsNameAttribute();
				if (nameAttribute.getEAttributeType() != EcorePackage.Literals.ESTRING) {
					return new Status(IStatus.ERROR, EmfUiPlugin.PLUGIN_ID,
							"[Internal] The EmfConnector implementation of getContentsNameAttribute() must return a string attribute.");
				}
				if (!nameAttribute.getEContainingClass().isInstance(contentsObject)) {
					return new Status(IStatus.ERROR, EmfUiPlugin.PLUGIN_ID,
							"[Internal] The EmfConnector implementation of getContentsNameAttribute() parent type: "
									+ nameAttribute.getEContainingClass().getName() + " does not match the contents: "
									+ contentsObject.eClass());
				}
				String description = (String) contentsObject.eGet(nameAttribute);
				validating = true;
				labelEditor.setText(description);
				validating = false;
			} catch (CoreException e) {
				try {
					client.close();
				} catch (CoreException e1) {
				}
				client = null;
				return e.getStatus();
			}
		}
		if (StringUtils.isBlank(labelEditor.getText())) {
			return new Status(IStatus.ERROR, getConnector().getConnectorBundle().getSymbolicName(),
					"Provide a repository label.");
		}
		return new Status(IStatus.OK, getConnector().getConnectorBundle().getSymbolicName(), "Valid "
				+ getConnector().getLabel() + " Repository.");
	}

	private void onUriModify() {
		validateSettings();
		getWizard().getContainer().updateButtons();
	}

	private void onLabelModify() {
		validateSettings();
		if (StringUtils.isBlank(labelEditor.getText())) {
			createButton.setEnabled(false);
		} else {
			createButton.setEnabled(true);
		}
		getWizard().getContainer().updateButtons();
	}

	private void browse() {
		FileDialog browseDialog = new FileDialog(getShell(), SWT.OPEN);
		String fileName = null;
		String filterPath;
		File currentLocation = new File(uriEditor.getText());
		if (currentLocation.exists()) {
			String currentPath = currentLocation.getAbsolutePath();
			if (currentLocation.isFile()) {
				filterPath = StringUtils.substringBeforeLast(currentPath, File.separator);
				fileName = StringUtils.substringAfterLast(currentPath, File.separator);
			} else {
				filterPath = currentPath;
			}
		} else {
			IPath configurationDir = ConfigurationScope.INSTANCE.getLocation();
			filterPath = configurationDir.toString();
		}
		browseDialog.setFilterPath(filterPath);
		if (fileName != null) {
			browseDialog.setFileName(fileName);
		}
		browseDialog.setFilterExtensions(getConnectorUi().getFileNameExtensions());
		String browseResult = browseDialog.open();
		if (browseResult != null) {
			uriEditor.setText(browseResult);
		}
	}

	private void create() {
		FileDialog browseDialog = new FileDialog(getShell(), SWT.SAVE);
		String fileName = null;
		String filterPath;
		File currentLocation = new File(uriEditor.getText());
		if (currentLocation.exists()) {
			String currentPath = currentLocation.getAbsolutePath();
			if (currentLocation.isFile()) {
				filterPath = StringUtils.substringBeforeLast(currentPath, File.separator);
				fileName = StringUtils.substringAfterLast(currentPath, File.separator);
			} else {
				filterPath = currentPath;
			}
		} else {
			IPath configurationDir = ConfigurationScope.INSTANCE.getLocation();
			filterPath = configurationDir.toString();
		}
		browseDialog.setFilterPath(filterPath);
		if (fileName == null) {
			fileName = StringUtils.deleteWhitespace(labelEditor.getText());
		}
		if (fileName != null) {
			browseDialog.setFileName(fileName);
		}
		browseDialog.setFilterExtensions(getConnectorUi().getFileNameExtensions());
		String browseResult = browseDialog.open();
		if (browseResult != null) {
			File checkFile = new File(browseResult);
			if (checkFile.exists()) {
				MessageDialog dialog = new MessageDialog(getShell(), "Replace Existing?", null, checkFile.getName()
						+ " already exists. Are you sure you want to replace it?", MessageDialog.WARNING, new String[] {
						"Yes", "No" }, 1);
				int open = dialog.open();
				if (open == 1) {
					return;
				}
			}
			ResourceSet resourceSet = new ResourceSetImpl();
			URI fileURI = URI.createFileURI(browseResult);
			Resource resource = resourceSet.createResource(fileURI);
			EReference containmentRef = getConnector().getContainmentReference();
			EClass eContainingClass = containmentRef.getEContainingClass();
			EObject rootObject = eContainingClass.getEPackage().getEFactoryInstance().create(eContainingClass);
			if (rootObject != null) {
				resource.getContents().add(rootObject);
				rootObject.eSet(getConnector().getContentsNameAttribute(), labelEditor.getText());
				Map<Object, Object> options = new HashMap<Object, Object>();
				try {
					resource.save(options);
				} catch (IOException e) {
					StatusManager.getManager().handle(
							new Status(IStatus.WARNING, EmfUiPlugin.PLUGIN_ID, "Couldn't create repository."));
					return;
				}
			}

			uriEditor.setText(browseResult);
		}
	}

	@Override
	public void performFinish(TaskRepository repository) {
		super.performFinish(repository);
	}

	@Override
	public void dispose() {
		if (client != null) {
			try {
				client.close();
			} catch (CoreException e) {
			}
		}
	};

	@Override
	public String getConnectorKind() {
		return getConnector().getConnectorKind();
	}

	public AbstractEmfConnector getConnector() {
		return getConnectorUi().getConnector();
	}

	public abstract AbstractEmfConnectorUi getConnectorUi();
}
