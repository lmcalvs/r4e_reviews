// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
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
 * This class implements the tabbed property section for the Anomaly model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
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
public class AnomalyBasicTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fPosition.
	 */
	private IR4EUIPosition fPosition;

	/**
	 * Field FTitleText.
	 */
	protected Text fTitleText = null;

	/**
	 * Field FAuthorText.
	 */
	protected CLabel fAuthorText = null;

	/**
	 * Field FCreationDateText.
	 */
	protected CLabel fCreationDateText = null;

	/**
	 * Field FPositionText.
	 */
	protected CLabel fPositionText = null;

	/**
	 * Field FDescriptionText.
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

		//Anomaly title
		fTitleText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fTitleText.setToolTipText(R4EUIConstants.ANOMALY_TITLE_TOOLTIP);
		fTitleText.setLayoutData(data);
		fTitleText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						//Set new model data
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyBasic) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setTitle(fTitleText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//Set new UI display
						if (fProperties.getElement() instanceof R4EUIAnomalyExtended) {
							fProperties.getElement().setName(
									R4EUIAnomalyExtended.buildAnomalyName(modelAnomaly,
											((R4EUIAnomalyBasic) fProperties.getElement()).getPosition()));
						} else {
							fProperties.getElement().setName(
									R4EUIAnomalyBasic.buildAnomalyName(modelAnomaly,
											((R4EUIAnomalyBasic) fProperties.getElement()).getPosition()));
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
				refresh();
			}

			public void focusGained(FocusEvent e) { // $codepro.audit.disable emptyMethod
				//Nothing to do
			}
		});
		UIUtils.addTabbedPropertiesTextResizeListener(fTitleText);

		final CLabel titleLabel = widgetFactory.createCLabel(composite, R4EUIConstants.TITLE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fTitleText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fTitleText, 0, SWT.CENTER);
		titleLabel.setToolTipText(R4EUIConstants.ANOMALY_TITLE_TOOLTIP);
		titleLabel.setLayoutData(data);

		//Anomaly Author (read-only)
		fAuthorText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fTitleText, ITabbedPropertyConstants.VSPACE);
		fAuthorText.setToolTipText(R4EUIConstants.ANOMALY_AUTHOR_TOOLTIP);
		fAuthorText.setLayoutData(data);

		final CLabel authorLabel = widgetFactory.createCLabel(composite, R4EUIConstants.AUTHOR_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fAuthorText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fAuthorText, 0, SWT.CENTER);
		authorLabel.setToolTipText(R4EUIConstants.ANOMALY_AUTHOR_TOOLTIP);
		authorLabel.setLayoutData(data);

		//Anomaly Creation Date (read-only)
		fCreationDateText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fAuthorText, ITabbedPropertyConstants.VSPACE);
		fCreationDateText.setToolTipText(R4EUIConstants.ANOMALY_CREATION_DATE_TOOLTIP);
		fCreationDateText.setLayoutData(data);

		final CLabel creationDateLabel = widgetFactory.createCLabel(composite, R4EUIConstants.CREATION_DATE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fCreationDateText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fCreationDateText, 0, SWT.CENTER);
		creationDateLabel.setToolTipText(R4EUIConstants.ANOMALY_CREATION_DATE_TOOLTIP);
		creationDateLabel.setLayoutData(data);

		//Anomaly position (read-only)
		fPositionText = widgetFactory.createCLabel(composite, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fCreationDateText, ITabbedPropertyConstants.VSPACE);
		fPositionText.setToolTipText(R4EUIConstants.ANOMALY_POSITION_TOOLTIP);
		fPositionText.setLayoutData(data);

		final CLabel positionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.POSITION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fPositionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fPositionText, 0, SWT.CENTER);
		positionLabel.setToolTipText(R4EUIConstants.ANOMALY_POSITION_TOOLTIP);
		positionLabel.setLayoutData(data);

		//Anomaly Description
		fDescriptionText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fPositionText, ITabbedPropertyConstants.VSPACE);
		fDescriptionText.setLayoutData(data);
		fDescriptionText.setToolTipText(R4EUIConstants.ANOMALY_DESCRIPTION_TOOLTIP);
		fDescriptionText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						//Set new model data
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyBasic) fProperties.getElement()).getAnomaly();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
						modelAnomaly.setDescription(fDescriptionText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);

						//Set new UI display
						fProperties.getElement().setToolTip(R4EUIAnomalyBasic.buildAnomalyToolTip(modelAnomaly));
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
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
		descriptionLabel.setToolTipText(R4EUIConstants.ANOMALY_DESCRIPTION_TOOLTIP);
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
		final R4EAnomaly modelAnomaly = ((R4EUIAnomalyBasic) fProperties.getElement()).getAnomaly();
		fTitleText.setText(modelAnomaly.getTitle());
		fAuthorText.setText(modelAnomaly.getUser().getId());
		fCreationDateText.setText(modelAnomaly.getCreatedOn().toString());
		fPosition = ((R4EUIAnomalyBasic) fProperties.getElement()).getPosition();
		if (null == fPosition) {
			fPositionText.setText(R4EUIConstants.GLOBAL_ANOMALY_PROPERTY_VALUE);
		} else {
			fPositionText.setText(fPosition.toString());
		}
		fDescriptionText.setText(modelAnomaly.getDescription());
		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEditableFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen()) {
			fTitleText.setEnabled(false);
			fAuthorText.setEnabled(false);
			fCreationDateText.setEnabled(false);
			fPositionText.setEnabled(false);
			fDescriptionText.setEnabled(false);
		} else {
			fTitleText.setEnabled(true);
			fAuthorText.setEnabled(true);
			fCreationDateText.setEnabled(true);
			fPositionText.setEnabled(true);
			fDescriptionText.setEnabled(true);
		}
	}
}
