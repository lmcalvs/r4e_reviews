// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the tabbed property section for the Postponed File model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.core.resources.IResource;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class PostponedFileTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fOriginalFileNameText.
	 */
	protected CLabel fOriginalFileNameText = null;

	/**
	 * Field fOriginalFilePathRepositoryText.
	 */
	protected CLabel fOriginalFilePathRepositoryText = null;

	/**
	 * Field fOriginalFilePathAbsoluteText.
	 */
	protected CLabel fOriginalFilePathAbsoluteText = null;

	/**
	 * Field fOriginalFilePathProjectText.
	 */
	protected CLabel fOriginalFilePathProjectText = null;

	/**
	 * Field fOriginalFileVersionText.
	 */
	protected CLabel fOriginalFileVersionText = null;

	/**
	 * Field fReviewNameText.
	 */
	private CLabel fReviewNameText = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method createControls.
	 * 
	 * @param parent
	 *            Composite
	 * @param aTabbedPropertySheetPage
	 *            TabbedPropertySheetPage
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(Composite, TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		//Tell element to build its own detailed tab layout
		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
		final Composite composite = widgetFactory.createFlatFormComposite(parent);
		FormData data = null;

		//File Name (read-only)
		fReviewNameText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(composite, ITabbedPropertyConstants.VSPACE);
		fReviewNameText.setToolTipText(R4EUIConstants.PARENT_REVIEW_TOOLTIP);
		fReviewNameText.setLayoutData(data);

		final CLabel reviewNameLabel = widgetFactory.createCLabel(composite, R4EUIConstants.PARENT_REVIEW_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fReviewNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fReviewNameText, 0, SWT.CENTER);
		reviewNameLabel.setToolTipText(R4EUIConstants.PARENT_REVIEW_TOOLTIP);
		reviewNameLabel.setLayoutData(data);

		//Target File Version composite (read-only)
		final Composite targetFileComposite = widgetFactory.createGroup(composite, "Original File");
		final FormLayout targetFileLayout = new FormLayout();
		targetFileComposite.setLayout(targetFileLayout);
		createOriginalFileVersionComposite(targetFileComposite, widgetFactory);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fReviewNameText, ITabbedPropertyConstants.VSPACE);
		targetFileComposite.setLayoutData(data);

	}

	/**
	 * Method createOriginalFileVersionComposite.
	 * 
	 * @param aParent
	 *            Composite
	 * @param aWidgetFactory
	 *            TabbedPropertySheetWidgetFactory
	 */
	private void createOriginalFileVersionComposite(final Composite aParent,
			TabbedPropertySheetWidgetFactory aWidgetFactory) {
		FormData data = null;

		//File Name (read-only)
		fOriginalFileNameText = aWidgetFactory.createCLabel(aParent, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(aParent, ITabbedPropertyConstants.VSPACE);
		fOriginalFileNameText.setToolTipText(R4EUIConstants.POSTPONED_FILE_NAME_TOOLTIP);
		fOriginalFileNameText.setLayoutData(data);

		final CLabel fileNameLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.NAME_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fOriginalFileNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fOriginalFileNameText, 0, SWT.CENTER);
		fileNameLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_NAME_TOOLTIP);
		fileNameLabel.setLayoutData(data);

		//File Version (read-only)
		fOriginalFileVersionText = aWidgetFactory.createCLabel(aParent, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fOriginalFileNameText, ITabbedPropertyConstants.VSPACE);
		fOriginalFileVersionText.setToolTipText(R4EUIConstants.POSTPONED_FILE_VERSION_TOOLTIP);
		fOriginalFileVersionText.setLayoutData(data);

		final CLabel fileVersionLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.VERSION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fOriginalFileVersionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fOriginalFileVersionText, 0, SWT.TOP);
		fileVersionLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_VERSION_TOOLTIP);
		fileVersionLabel.setLayoutData(data);

		//Path information section
		final ExpandableComposite pathSection = aWidgetFactory.createExpandableComposite(aParent,
				ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fOriginalFileVersionText, ITabbedPropertyConstants.VSPACE);
		pathSection.setLayoutData(data);
		pathSection.setText(R4EUIConstants.PATH_INFORMATION_LABEL);
		pathSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
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
		pathSectionClient.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		pathSectionClient.setLayout(new GridLayout(4, false));
		pathSection.setClient(pathSectionClient);

		//Repository File Path (read-only)
		final CLabel filePathRepositoryLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_REPOSITORY_LABEL);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		filePathRepositoryLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_PATH_REPOSITORY_TOOLTIP);
		filePathRepositoryLabel.setLayoutData(gridData);

		fOriginalFilePathRepositoryText = aWidgetFactory.createCLabel(pathSectionClient, "");
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fOriginalFilePathRepositoryText.setToolTipText(R4EUIConstants.POSTPONED_FILE_PATH_REPOSITORY_TOOLTIP);
		fOriginalFilePathRepositoryText.setLayoutData(gridData);

		//Absolute File Path (read-only)
		final CLabel filePathAbsoluteLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_ABSOLUTE_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		filePathAbsoluteLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_PATH_ABSOLUTE_TOOLTIP);
		filePathAbsoluteLabel.setLayoutData(gridData);

		fOriginalFilePathAbsoluteText = aWidgetFactory.createCLabel(pathSectionClient, "");
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fOriginalFilePathAbsoluteText.setToolTipText(R4EUIConstants.POSTPONED_FILE_PATH_ABSOLUTE_TOOLTIP);
		fOriginalFilePathAbsoluteText.setLayoutData(gridData);

		//Project Relative File Path (read-only)
		final CLabel filePathProjectLabel = aWidgetFactory.createCLabel(pathSectionClient,
				R4EUIConstants.PATH_PROJECT_LABEL);
		gridData = new GridData(GridData.FILL, GridData.FILL, false, false);
		gridData.horizontalSpan = 1;
		filePathProjectLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_PATH_PROJECT_TOOLTIP);
		filePathProjectLabel.setLayoutData(gridData);

		fOriginalFilePathProjectText = aWidgetFactory.createCLabel(pathSectionClient, "");
		gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
		gridData.horizontalSpan = 3;
		fOriginalFilePathProjectText.setToolTipText(R4EUIConstants.POSTPONED_FILE_PATH_PROJECT_TOOLTIP);
		fOriginalFilePathProjectText.setLayoutData(gridData);
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

		fReviewNameText.setText(modelFile.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME));

		final R4EFileVersion targetVersion = modelFile.getTarget();
		if (null != targetVersion) {
			fOriginalFileNameText.setText(targetVersion.getName());
			final IResource targetResource = targetVersion.getResource();
			//The properties shows the absolute path
			if (null != targetResource) {
				fOriginalFilePathAbsoluteText.setText(targetResource.getLocation().toOSString());
			} else {
				fOriginalFilePathAbsoluteText.setText("");
			}
			fOriginalFileVersionText.setText(targetVersion.getVersionID());
		} else {
			fOriginalFileNameText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			fOriginalFilePathAbsoluteText.setText("");
			fOriginalFileVersionText.setText("");
		}
		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isJobInProgress()
				|| ((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED) || !fProperties.getElement().isEnabled()) {
			fReviewNameText.setEnabled(false);
			fOriginalFileNameText.setEnabled(false);
			fOriginalFilePathAbsoluteText.setEnabled(false);
			fOriginalFileVersionText.setEnabled(false);
		} else {
			fReviewNameText.setEnabled(true);
			fOriginalFileNameText.setEnabled(true);
			fOriginalFilePathAbsoluteText.setEnabled(true);
			fOriginalFileVersionText.setEnabled(true);
		}
	}
}
