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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
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
	 * Field fOriginalFilePathText.
	 */
	protected CLabel fOriginalFilePathText = null;

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
	private void createOriginalFileVersionComposite(Composite aParent, TabbedPropertySheetWidgetFactory aWidgetFactory) {
		FormData data = null;

		//File Name (read-only)
		fOriginalFileNameText = aWidgetFactory.createCLabel(aParent, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(aParent, ITabbedPropertyConstants.VSPACE);
		fOriginalFileNameText.setToolTipText(R4EUIConstants.POSTPONED_FILE_FILE_NAME_TOOLTIP);
		fOriginalFileNameText.setLayoutData(data);

		final CLabel fileNameLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.NAME_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fOriginalFileNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fOriginalFileNameText, 0, SWT.CENTER);
		fileNameLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_FILE_NAME_TOOLTIP);
		fileNameLabel.setLayoutData(data);

		//File Path (read-only)
		fOriginalFilePathText = aWidgetFactory.createCLabel(aParent, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fOriginalFileNameText, ITabbedPropertyConstants.VSPACE);
		fOriginalFilePathText.setToolTipText(R4EUIConstants.POSTPONED_FILE_FILE_PATH_TOOLTIP);
		fOriginalFilePathText.setLayoutData(data);

		final CLabel filePathLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.PATH_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fOriginalFilePathText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fOriginalFilePathText, 0, SWT.TOP);
		filePathLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_FILE_PATH_TOOLTIP);
		filePathLabel.setLayoutData(data);

		//File Version (read-only)
		fOriginalFileVersionText = aWidgetFactory.createCLabel(aParent, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fOriginalFilePathText, ITabbedPropertyConstants.VSPACE);
		fOriginalFileVersionText.setToolTipText(R4EUIConstants.POSTPONED_FILE_FILE_VERSION_TOOLTIP);
		fOriginalFileVersionText.setLayoutData(data);

		final CLabel fileVersionLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.VERSION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fOriginalFileVersionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fOriginalFileVersionText, 0, SWT.TOP);
		fileVersionLabel.setToolTipText(R4EUIConstants.POSTPONED_FILE_FILE_VERSION_TOOLTIP);
		fileVersionLabel.setLayoutData(data);
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
				fOriginalFilePathText.setText(targetResource.getLocation().toOSString());
			} else {
				fOriginalFilePathText.setText("");
			}
			fOriginalFileVersionText.setText(targetVersion.getVersionID());
		} else {
			fOriginalFileNameText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			fOriginalFilePathText.setText("");
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
		if (R4EUIModelController.isJobInProgress()) {
			fReviewNameText.setEnabled(false);
			fOriginalFileNameText.setEnabled(false);
			fOriginalFilePathText.setEnabled(false);
			fOriginalFileVersionText.setEnabled(false);
		} else {
			fReviewNameText.setEnabled(true);
			fOriginalFileNameText.setEnabled(true);
			fOriginalFilePathText.setEnabled(true);
			fOriginalFileVersionText.setEnabled(true);
		}
	}
}
