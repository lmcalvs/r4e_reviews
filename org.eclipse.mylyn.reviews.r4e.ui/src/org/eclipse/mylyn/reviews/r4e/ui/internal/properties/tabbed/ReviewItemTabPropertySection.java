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

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
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
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field CHANGE_ID_LABEL. (value is ""Change Id: "")
	 */
	private static final String CHANGE_ID_LABEL = "Change Id: ";

	/**
	 * Field DATE_SUBMITTED_LABEL. (value is ""Date Submitted: "")
	 */
	private static final String DATE_SUBMITTED_LABEL = "Date Submitted: ";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fAuthorText.
	 */
	private CLabel fAuthorText = null;

	/**
	 * Field fAuthorRepText.
	 */
	private CLabel fAuthorRepText = null;

	/**
	 * Field fProjectIdList.
	 */
	private List fProjectIdList = null;

	/**
	 * Field fRepositoryText.
	 */
	private CLabel fRepositoryText = null;

	/**
	 * Field fDateSubmitted.
	 */
	private CLabel fDateSubmitted = null;

	/**
	 * Field fDescriptionText.
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

		//AuthorRep (read-only)
		fAuthorRepText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fAuthorText, ITabbedPropertyConstants.VSPACE);
		fAuthorRepText.setToolTipText(R4EUIConstants.REVIEW_ITEM_AUTHOR_REP_TOOLTIP);
		fAuthorRepText.setLayoutData(data);

		final CLabel authorRepLabel = widgetFactory.createCLabel(composite, R4EUIConstants.EMAIL_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fAuthorRepText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fAuthorRepText, 0, SWT.CENTER);
		authorRepLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_AUTHOR_REP_TOOLTIP);
		authorRepLabel.setLayoutData(data);

		//ProjectId (read-only)
		fProjectIdList = widgetFactory.createList(composite, SWT.READ_ONLY);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fAuthorRepText, ITabbedPropertyConstants.VSPACE);
		fProjectIdList.setToolTipText(R4EUIConstants.REVIEW_ITEM_PROJECT_IDS_TOOLTIP);
		fProjectIdList.setLayoutData(data);

		final CLabel projectIdLabel = widgetFactory.createCLabel(composite, R4EUIConstants.PROJECT_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fProjectIdList, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fProjectIdList, 0, SWT.CENTER);
		projectIdLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_PROJECT_IDS_TOOLTIP);
		projectIdLabel.setLayoutData(data);

		//Change Id (read-only)
		fRepositoryText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fProjectIdList, ITabbedPropertyConstants.VSPACE);
		fRepositoryText.setToolTipText(R4EUIConstants.REVIEW_ITEM_CHANGE_ID_TOOLTIP);
		fRepositoryText.setLayoutData(data);

		final CLabel repositoryLabel = widgetFactory.createCLabel(composite, CHANGE_ID_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fRepositoryText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fRepositoryText, 0, SWT.CENTER);
		repositoryLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_CHANGE_ID_TOOLTIP);
		repositoryLabel.setLayoutData(data);

		//Date Submitted (read-only)
		fDateSubmitted = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fRepositoryText, ITabbedPropertyConstants.VSPACE);
		fDateSubmitted.setToolTipText(R4EUIConstants.REVIEW_ITEM_DATE_SUBMITTED_TOOLTIP);
		fDateSubmitted.setLayoutData(data);

		final CLabel dateSubmittedLabel = widgetFactory.createCLabel(composite, DATE_SUBMITTED_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fDateSubmitted, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fDateSubmitted, 0, SWT.CENTER);
		dateSubmittedLabel.setToolTipText(R4EUIConstants.REVIEW_ITEM_DATE_SUBMITTED_TOOLTIP);
		dateSubmittedLabel.setLayoutData(data);

		//Description
		fDescriptionText = widgetFactory.createText(composite, "", SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fDateSubmitted, ITabbedPropertyConstants.VSPACE);
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
		if (null == fProperties) {
			return; //R4EUIPostponedContainer (subclass of R4EUIReviewItem) does not have any properties
		}
		fRefreshInProgress = true;
		final R4EItem modelItem = ((R4EUIReviewItem) fProperties.getElement()).getItem();
		fAuthorText.setText(modelItem.getAddedById());
		if (null != modelItem.getAuthorRep()) {
			fAuthorRepText.setText(modelItem.getAuthorRep());
		} else {
			try {
				final R4EParticipant participant = R4EUIModelController.getActiveReview().getParticipant(
						modelItem.getAddedById(), false);
				if (null != participant) {
					fAuthorRepText.setText(participant.getEmail());
				} else {
					fAuthorRepText.setText("");
				}
			} catch (ResourceHandlingException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				fAuthorRepText.setText("");
			}
		}
		fProjectIdList.setItems((String[]) modelItem.getProjectURIs().toArray());
		fRepositoryText.setText(modelItem.getRepositoryRef());
		if (null != modelItem.getSubmitted()) {
			final DateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.DEFAULT_DATE_FORMAT);
			fDateSubmitted.setText(dateFormat.format(modelItem.getSubmitted()));
		} else {
			fDateSubmitted.setText("");
		}
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
		if (R4EUIModelController.isJobInProgress()) {
			fAuthorText.setEnabled(false);
			fAuthorRepText.setEnabled(false);
			fProjectIdList.setEnabled(false);
			fRepositoryText.setEnabled(false);
			fDateSubmitted.setEnabled(false);
			fDescriptionText.setEnabled(false);
		} else {
			fAuthorText.setEnabled(true);
			fAuthorRepText.setEnabled(true);
			fProjectIdList.setEnabled(true);
			fRepositoryText.setEnabled(true);
			fDateSubmitted.setEnabled(true);
			fDescriptionText.setEnabled(true);
		}
	}
}
