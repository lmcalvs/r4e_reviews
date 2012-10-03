// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This class implements the tabbed property section for the File Context model element
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class FileContextTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fBaseFileNameText.
	 */
	protected Text fBaseFileNameText = null;

	/**
	 * Field fBaseFilePathAbsoluteText.
	 */
	protected Text fBaseFilePathAbsoluteText = null;

	/**
	 * Field fBaseFilePathProjectText.
	 */
	protected Text fBaseFilePathProjectText = null;

	/**
	 * Field fBaseFilePathRepositoryText.
	 */
	protected Text fBaseFilePathRepositoryText = null;

	/**
	 * Field fBaseFileVersionText.
	 */
	protected Text fBaseFileVersionText = null;

	/**
	 * Field fTargetFileNameText.
	 */
	protected Text fTargetFileNameText = null;

	/**
	 * Field fTargetFilePathAbsoluteText.
	 */
	protected Text fTargetFilePathAbsoluteText = null;

	/**
	 * Field fTargetFilePathProjectText.
	 */
	protected Text fTargetFilePathProjectText = null;

	/**
	 * Field fTargetFilePathRepositoryText.
	 */
	protected Text fTargetFilePathRepositoryText = null;

	/**
	 * Field fTargetFileVersionText.
	 */
	protected Text fTargetFileVersionText = null;

	/**
	 * Field fAssignedToComposite.
	 */
	private Composite fAssignedToComposite;

	/**
	 * Field fAssignedToText.
	 */
	private Text fAssignedToText;

	/**
	 * Field fAssignedToButton.
	 */
	private Button fAssignedToButton;

	/**
	 * Field fUnassignedFromButton.
	 */
	private Button fUnassignedFromButton;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method createControls.
	 * 
	 * @param aParent
	 *            Composite
	 * @param aTabbedPropertySheetPage
	 *            TabbedPropertySheetPage
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(Composite, TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite aParent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(aParent, aTabbedPropertySheetPage);

		//Tell element to build its own detailed tab layout
		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
		final Composite composite = widgetFactory.createFlatFormComposite(aParent);
		FormData data = null;

		//Target File Version composite (read-only)
		widgetFactory.setBorderStyle(SWT.NULL);
		final Composite targetFileComposite = widgetFactory.createGroup(composite, "Target File");
		final FormLayout targetFileLayout = new FormLayout();
		targetFileComposite.setLayout(targetFileLayout);
		createTargetFileVersionComposite(targetFileComposite, widgetFactory);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		targetFileComposite.setLayoutData(data);

		//Base File Version composite (read-only)
		final Group baseFileComposite = widgetFactory.createGroup(composite, "Base File");
		final FormLayout baseFileLayout = new FormLayout();
		baseFileComposite.setLayout(baseFileLayout);
		createBaseFileVersionComposite(baseFileComposite, widgetFactory);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(targetFileComposite, ITabbedPropertyConstants.VSPACE);
		baseFileComposite.setLayoutData(data);

		//Assigned To
		fAssignedToComposite = widgetFactory.createComposite(composite);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(baseFileComposite, ITabbedPropertyConstants.VSPACE);
		fAssignedToComposite.setToolTipText(R4EUIConstants.ASSIGNED_TO_TOOLTIP);
		fAssignedToComposite.setLayoutData(data);
		fAssignedToComposite.setLayout(new GridLayout(3, false));

		widgetFactory.setBorderStyle(SWT.BORDER);
		fAssignedToText = widgetFactory.createText(fAssignedToComposite, "");
		fAssignedToText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fAssignedToText.setEditable(false);
		fAssignedToButton = widgetFactory.createButton(fAssignedToComposite, R4EUIConstants.ADD_LABEL, SWT.NONE);
		fAssignedToButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		fAssignedToButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event aEvent) {
				((R4EUIFileContext) fProperties.getElement()).addAssignees(UIUtils.getAssignParticipants());
				refresh();
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		});

		fUnassignedFromButton = widgetFactory.createButton(fAssignedToComposite, R4EUIConstants.REMOVE_LABEL, SWT.NONE);
		fUnassignedFromButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		fUnassignedFromButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event aEvent) {
				((R4EUIFileContext) fProperties.getElement()).removeAssignees(UIUtils.getUnassignParticipants(fProperties.getElement()));
				refresh();
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		});

		final CLabel assignedToLabel = widgetFactory.createCLabel(composite, R4EUIConstants.ASSIGNED_TO_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fAssignedToComposite, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fAssignedToComposite, 0, SWT.CENTER);
		assignedToLabel.setToolTipText(R4EUIConstants.ASSIGNED_TO_TOOLTIP);
		assignedToLabel.setLayoutData(data);
	}

	/**
	 * Method createBaseFileVersionComposite.
	 * 
	 * @param aParent
	 *            Composite
	 * @param aWidgetFactory
	 *            TabbedPropertySheetWidgetFactory
	 */
	private void createBaseFileVersionComposite(final Composite aParent, TabbedPropertySheetWidgetFactory aWidgetFactory) {

		//File Name (read-only)
		fBaseFileNameText = aWidgetFactory.createText(aParent, "", SWT.NULL);
		FormData data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(aParent, ITabbedPropertyConstants.VSPACE);
		fBaseFileNameText.setEditable(false);
		fBaseFileNameText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_NAME_TOOLTIP);
		fBaseFileNameText.setLayoutData(data);

		final CLabel fileNameLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.NAME_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fBaseFileNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fBaseFileNameText, 0, SWT.CENTER);
		fileNameLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_NAME_TOOLTIP);
		fileNameLabel.setLayoutData(data);

		//File Version (read-only)
		fBaseFileVersionText = aWidgetFactory.createText(aParent, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fBaseFileNameText, ITabbedPropertyConstants.VSPACE);
		fBaseFileVersionText.setEditable(false);
		fBaseFileVersionText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_VERSION_TOOLTIP);
		fBaseFileVersionText.setLayoutData(data);

		final CLabel fileVersionLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.VERSION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fBaseFileVersionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fBaseFileVersionText, 0, SWT.CENTER);
		fileVersionLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_VERSION_TOOLTIP);
		fileVersionLabel.setLayoutData(data);

		//Path information section
		final ExpandableComposite pathSection = aWidgetFactory.createExpandableComposite(aParent,
				ExpandableComposite.TWISTIE);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fBaseFileVersionText, ITabbedPropertyConstants.VSPACE);
		pathSection.setLayoutData(data);
		pathSection.setText(R4EUIConstants.PATH_INFORMATION_LABEL);
		pathSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent aEvent) {
				final ScrolledComposite scrolledParent = (ScrolledComposite) aParent.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent();
				scrolledParent.setMinSize(aParent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				scrolledParent.layout(true, true);
			}
		});
		pathSection.setLayout(new GridLayout(1, false));

		final Composite pathSectionClient = aWidgetFactory.createComposite(pathSection);
		pathSectionClient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pathSectionClient.setLayout(new GridLayout(4, false));
		pathSection.setClient(pathSectionClient);

		//Repository File Path (read-only)
		final CLabel filePathRepositoryLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_REPOSITORY_LABEL);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gridData.horizontalSpan = 1;
		filePathRepositoryLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_REPOSITORY_TOOLTIP);
		filePathRepositoryLabel.setLayoutData(gridData);

		fBaseFilePathRepositoryText = aWidgetFactory.createText(pathSectionClient, "", SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		fBaseFilePathRepositoryText.setEditable(false);
		fBaseFilePathRepositoryText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_REPOSITORY_TOOLTIP);
		fBaseFilePathRepositoryText.setLayoutData(gridData);

		//Absolute File Path (read-only)
		final CLabel filePathAbsoluteLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_ABSOLUTE_LABEL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gridData.horizontalSpan = 1;
		filePathAbsoluteLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_ABSOLUTE_TOOLTIP);
		filePathAbsoluteLabel.setLayoutData(gridData);

		fBaseFilePathAbsoluteText = aWidgetFactory.createText(pathSectionClient, "", SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		fBaseFilePathAbsoluteText.setEditable(false);
		fBaseFilePathAbsoluteText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_ABSOLUTE_TOOLTIP);
		fBaseFilePathAbsoluteText.setLayoutData(gridData);

		//Project Relative File Path (read-only)
		final CLabel filePathProjectLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_PROJECT_LABEL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gridData.horizontalSpan = 1;
		filePathProjectLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_PROJECT_TOOLTIP);
		filePathProjectLabel.setLayoutData(gridData);

		fBaseFilePathProjectText = aWidgetFactory.createText(pathSectionClient, "", SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		fBaseFilePathProjectText.setEditable(false);
		fBaseFilePathProjectText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_PROJECT_TOOLTIP);
		fBaseFilePathProjectText.setLayoutData(gridData);
	}

	/**
	 * Method createTargetFileVersionComposite.
	 * 
	 * @param aParent
	 *            Composite
	 * @param aWidgetFactory
	 *            TabbedPropertySheetWidgetFactory
	 */
	private void createTargetFileVersionComposite(final Composite aParent,
			TabbedPropertySheetWidgetFactory aWidgetFactory) {
		FormData data = null;

		//File Name (read-only)
		fTargetFileNameText = aWidgetFactory.createText(aParent, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(aParent, ITabbedPropertyConstants.VSPACE);
		fTargetFileNameText.setEditable(false);
		fTargetFileNameText.setToolTipText(R4EUIConstants.FILECONTEXT_TARGET_FILE_NAME_TOOLTIP);
		fTargetFileNameText.setLayoutData(data);

		final CLabel fileNameLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.NAME_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fTargetFileNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fTargetFileNameText, 0, SWT.CENTER);
		fileNameLabel.setToolTipText(R4EUIConstants.FILECONTEXT_TARGET_FILE_NAME_TOOLTIP);
		fileNameLabel.setLayoutData(data);

		//File Version (read-only)
		fTargetFileVersionText = aWidgetFactory.createText(aParent, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fTargetFileNameText, ITabbedPropertyConstants.VSPACE);
		fTargetFileVersionText.setEditable(false);
		fTargetFileVersionText.setToolTipText(R4EUIConstants.FILECONTEXT_TARGET_FILE_VERSION_TOOLTIP);
		fTargetFileVersionText.setLayoutData(data);

		final CLabel fileVersionLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.VERSION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fTargetFileVersionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fTargetFileVersionText, 0, SWT.CENTER);
		fileVersionLabel.setToolTipText(R4EUIConstants.FILECONTEXT_TARGET_FILE_VERSION_TOOLTIP);
		fileVersionLabel.setLayoutData(data);

		//Path information section
		final ExpandableComposite pathSection = aWidgetFactory.createExpandableComposite(aParent,
				ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fTargetFileVersionText, ITabbedPropertyConstants.VSPACE);
		pathSection.setLayoutData(data);
		pathSection.setText(R4EUIConstants.PATH_INFORMATION_LABEL);
		pathSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent aEvent) {
				final ScrolledComposite scrolledParent = (ScrolledComposite) aParent.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent()
						.getParent();
				scrolledParent.setMinSize(aParent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				scrolledParent.layout(true, true);
			}
		});
		pathSection.setLayout(new GridLayout(1, false));

		final Composite pathSectionClient = aWidgetFactory.createComposite(pathSection);
		pathSectionClient.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		pathSectionClient.setLayout(new GridLayout(4, false));
		pathSection.setClient(pathSectionClient);

		//Repository File Path (read-only)
		final CLabel filePathRepositoryLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_REPOSITORY_LABEL);
		GridData gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gridData.horizontalSpan = 1;
		filePathRepositoryLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_REPOSITORY_TOOLTIP);
		filePathRepositoryLabel.setLayoutData(gridData);

		fTargetFilePathRepositoryText = aWidgetFactory.createText(pathSectionClient, "", SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		fTargetFilePathRepositoryText.setEditable(false);
		fTargetFilePathRepositoryText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_REPOSITORY_TOOLTIP);
		fTargetFilePathRepositoryText.setLayoutData(gridData);

		//Absolute File Path (read-only)
		final CLabel filePathAbsoluteLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_ABSOLUTE_LABEL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gridData.horizontalSpan = 1;
		filePathAbsoluteLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_ABSOLUTE_TOOLTIP);
		filePathAbsoluteLabel.setLayoutData(gridData);

		fTargetFilePathAbsoluteText = aWidgetFactory.createText(pathSectionClient, "", SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		fTargetFilePathAbsoluteText.setEditable(false);
		fTargetFilePathAbsoluteText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_ABSOLUTE_TOOLTIP);
		fTargetFilePathAbsoluteText.setLayoutData(gridData);

		//Project Relative File Path (read-only)
		final CLabel filePathProjectLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_PROJECT_LABEL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
		gridData.horizontalSpan = 1;
		filePathProjectLabel.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_PROJECT_TOOLTIP);
		filePathProjectLabel.setLayoutData(gridData);

		fTargetFilePathProjectText = aWidgetFactory.createText(pathSectionClient, "", SWT.NULL);
		gridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gridData.horizontalSpan = 3;
		fTargetFilePathProjectText.setEditable(false);
		fTargetFilePathProjectText.setToolTipText(R4EUIConstants.FILECONTEXT_BASE_FILE_PATH_PROJECT_TOOLTIP);
		fTargetFilePathProjectText.setLayoutData(gridData);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EFileContext modelFile = ((R4EUIFileContext) fProperties.getElement()).getFileContext();
		final R4EFileVersion baseVersion = modelFile.getBase();
		if (null != modelFile.getBase()) {
			//The properties shows the absolute, project relative and repository path
			fBaseFileNameText.setText(baseVersion.getName());
			fBaseFilePathRepositoryText.setText(baseVersion.getRepositoryPath());
			fBaseFilePathProjectText.setText(UIUtils.getProjectPath(baseVersion));
			final IResource baseResource = baseVersion.getResource();
			if (null != baseResource) {
				if (CommandUtils.useWorkspaceResource(baseVersion)) {
					fBaseFilePathAbsoluteText.setText(baseResource.getLocation().toPortableString());
				} else {
					fBaseFilePathAbsoluteText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
				}
			} else {
				fBaseFilePathAbsoluteText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			}
			fBaseFileVersionText.setText(baseVersion.getVersionID());
		} else {
			fBaseFileNameText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			fBaseFilePathRepositoryText.setText("");
			fBaseFilePathAbsoluteText.setText("");
			fBaseFilePathProjectText.setText("");
			fBaseFileVersionText.setText("");
		}

		final R4EFileVersion targetVersion = modelFile.getTarget();
		if (null != targetVersion) {
			//The properties shows the absolute, project relative and repository path
			fTargetFileNameText.setText(targetVersion.getName());
			fTargetFilePathRepositoryText.setText(targetVersion.getRepositoryPath());
			fTargetFilePathProjectText.setText(UIUtils.getProjectPath(targetVersion));
			final IResource targetResource = targetVersion.getResource();
			if (null != targetResource) {
				if (CommandUtils.useWorkspaceResource(targetVersion)) {
					fTargetFilePathAbsoluteText.setText(targetResource.getLocation().toPortableString());
				} else {
					fTargetFilePathAbsoluteText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
				}
			} else {
				fTargetFilePathAbsoluteText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			}
			fTargetFileVersionText.setText(targetVersion.getVersionID());
		} else {
			fTargetFileNameText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			fTargetFilePathRepositoryText.setText("");
			fTargetFilePathAbsoluteText.setText("");
			fTargetFilePathProjectText.setText("");
			fTargetFileVersionText.setText("");
		}

		final List<String> assignedParticipants = modelFile.getAssignedTo();
		fAssignedToText.setText(UIUtils.formatAssignedParticipants(assignedParticipants));

		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		//Since it is a subclass of this class we need this here.  This should be improved later
		//Do not show FileContext elements for the Postponed file.
		if (fProperties.getElement() instanceof R4EUIPostponedFile) {
			fBaseFileNameText.getParent().getParent().setVisible(false);
			return;
		}

		if (R4EUIModelController.isJobInProgress()
				|| fProperties.getElement().isReadOnly()
				|| null == R4EUIModelController.getActiveReview()
				|| ((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.COMPLETED) || !fProperties.getElement().isEnabled()) {
			fBaseFileNameText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fBaseFilePathRepositoryText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fBaseFilePathAbsoluteText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fBaseFilePathProjectText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fBaseFileVersionText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fTargetFileNameText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fTargetFilePathRepositoryText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fTargetFilePathAbsoluteText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fTargetFilePathProjectText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fTargetFileVersionText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fAssignedToText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fAssignedToButton.setEnabled(false);
			fUnassignedFromButton.setEnabled(false);
		} else {
			fBaseFileNameText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fBaseFilePathRepositoryText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fBaseFilePathAbsoluteText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fBaseFilePathProjectText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fBaseFileVersionText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fTargetFileNameText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fTargetFilePathRepositoryText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fTargetFilePathAbsoluteText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fTargetFilePathProjectText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fTargetFileVersionText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fAssignedToText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fAssignedToButton.setEnabled(true);
			if (fAssignedToText.getText().length() > 0) {
				fUnassignedFromButton.setEnabled(true);
			} else {
				fUnassignedFromButton.setEnabled(false);
			}
		}
	}
}
