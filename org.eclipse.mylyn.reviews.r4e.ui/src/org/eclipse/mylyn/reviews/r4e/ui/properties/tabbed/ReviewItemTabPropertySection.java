// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the tabbed property section for the Review Item model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.tabbed;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewItemTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field FAuthorText.
	 */
	private CLabel fAuthorText = null;

	/**
	 * Field FProjectIdList.
	 */
	private List fProjectIdList = null;

	/**
	 * Field FDescriptionText.
	 */
	protected Text fDescriptionText = null;

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

		//Author (read-only)
		fAuthorText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fAuthorText.setToolTipText(R4EUIConstants.REVIEW_ITEM_AUTHOR_TOOLTIP);
		fAuthorText.setLayoutData(data);

		final CLabel authorLabel = widgetFactory.createCLabel(composite, R4EUIConstants.AUTHOR_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fAuthorText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fAuthorText, 0, SWT.CENTER);
		authorLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_AUTHOR_TOOLTIP);
		authorLabel.setLayoutData(data);

		//ProjectId (read-only)
		fProjectIdList = widgetFactory.createList(composite, SWT.READ_ONLY);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fAuthorText, ITabbedPropertyConstants.VSPACE);
		fProjectIdList.setToolTipText(R4EUIConstants.REVIEW_ITEM_PROJECT_IDS_TOOLTIP);
		fProjectIdList.setLayoutData(data);

		final CLabel projectIdLabel = widgetFactory.createCLabel(composite, R4EUIConstants.PROJECT_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fProjectIdList, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fProjectIdList, 0, SWT.CENTER);
		projectIdLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_PROJECT_IDS_TOOLTIP);
		projectIdLabel.setLayoutData(data);

		//Description
		fDescriptionText = widgetFactory.createText(composite, "", SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fProjectIdList, ITabbedPropertyConstants.VSPACE);
		fDescriptionText.setToolTipText(R4EUIConstants.REVIEW_ITEM_DESCRIPTION_TOOLTIP);
		fDescriptionText.setLayoutData(data);
		fDescriptionText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EItem modelItem = ((R4EUIReviewItem) fProperties.getElement()).getItem();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelItem, currentUser);
						modelItem.setDescription(fDescriptionText.getText());
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
		data.top = new FormAttachment(fDescriptionText, 0, SWT.CENTER);
		descriptionLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_DESCRIPTION_TOOLTIP);
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
		final R4EItem modelItem = ((R4EUIReviewItem) fProperties.getElement()).getItem();
		fAuthorText.setText(modelItem.getAddedById());
		fProjectIdList.setItems((String[]) modelItem.getProjectURIs().toArray());
		if (null != modelItem.getDescription()) {
			fDescriptionText.setText(modelItem.getDescription());
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
		if (R4EUIModelController.isDialogOpen()) {
			fAuthorText.setEnabled(false);
			fProjectIdList.setEnabled(false);
			fDescriptionText.setEnabled(false);
		} else {
			fAuthorText.setEnabled(true);
			fProjectIdList.setEnabled(true);
			fDescriptionText.setEnabled(true);
		}
	}
}
