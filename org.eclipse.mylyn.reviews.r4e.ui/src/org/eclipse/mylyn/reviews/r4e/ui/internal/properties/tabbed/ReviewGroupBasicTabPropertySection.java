// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity, sourceLength, explicitThisUsage
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
 * This class implements the tabbed property section for the Review Group model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewGroupBasicTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fNameText.
	 */
	private CLabel fNameText = null;

	/**
	 * Field fFolderText.
	 */
	private CLabel fFolderText = null;

	/**
	 * Field fDescriptionText.
	 */
	protected Text fDescriptionText = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method shouldUseExtraSpace.
	 * 
	 * @return boolean
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}

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

		//Group Name (Read-only for now)
		fNameText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fNameText.setToolTipText(R4EUIConstants.REVIEW_GROUP_NAME_TOOLTIP);
		fNameText.setLayoutData(data);

		final CLabel nameLabel = widgetFactory.createCLabel(composite, R4EUIConstants.NAME_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fNameText, 0, SWT.CENTER);
		nameLabel.setToolTipText(R4EUIConstants.REVIEW_GROUP_NAME_TOOLTIP);
		nameLabel.setLayoutData(data);

		//Group Folder (read-only)
		fFolderText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fNameText, ITabbedPropertyConstants.VSPACE);
		fFolderText.setToolTipText(R4EUIConstants.REVIEW_GROUP_FOLDER_TOOLTIP);
		fFolderText.setLayoutData(data);

		final CLabel folderLabel = widgetFactory.createCLabel(composite, R4EUIConstants.FOLDER_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fFolderText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fFolderText, 0, SWT.CENTER);
		folderLabel.setToolTipText(R4EUIConstants.REVIEW_GROUP_FOLDER_TOOLTIP);
		folderLabel.setLayoutData(data);

		//Group Description
		fDescriptionText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fFolderText, ITabbedPropertyConstants.VSPACE);
		fDescriptionText.setToolTipText(R4EUIConstants.REVIEW_GROUP_DESCRIPTION_TOOLTIP);
		fDescriptionText.setLayoutData(data);
		fDescriptionText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EReviewGroup modelGroup = ((R4EUIReviewGroup) fProperties.getElement()).getReviewGroup();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelGroup, currentUser);
						modelGroup.setDescription(fDescriptionText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
			}

			public void focusGained(FocusEvent e) { // $codepro.audit.disable emptyMethod
				//Nothing to do
			}
		});
		UIUtils.addTabbedPropertiesTextResizeListener(fDescriptionText);

		final CLabel descriptionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DESCRIPTION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fDescriptionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fDescriptionText, 0, SWT.TOP);
		descriptionLabel.setToolTipText(R4EUIConstants.REVIEW_GROUP_DESCRIPTION_TOOLTIP);
		descriptionLabel.setLayoutData(data);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EReviewGroup modelGroup = ((R4EUIReviewGroup) fProperties.getElement()).getReviewGroup();
		fNameText.setText(modelGroup.getName());
		fFolderText.setText(modelGroup.getFolder());
		if (null != modelGroup.getDescription()) {
			fDescriptionText.setText(modelGroup.getDescription());
		} else {
			fDescriptionText.setText("");
		}
		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen() || !((R4EUIReviewGroup) fProperties.getElement()).isOpen()) {
			fNameText.setEnabled(false);
			fFolderText.setEnabled(false);
			fDescriptionText.setEnabled(false);
		} else {
			fNameText.setEnabled(true);
			fFolderText.setEnabled(true);
			fDescriptionText.setEnabled(true);
		}
	}
}
