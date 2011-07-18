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
 * This class implements the tabbed property section for the Participant model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ParticipantBasicTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field FIdText.
	 */
	private CLabel fIdText = null;

	/**
	 * Field fEmailText.
	 */
	protected Text fEmailText = null;

	/**
	 * Field FNumItemsText.
	 */
	private CLabel fNumItemsText = null;

	/**
	 * Field FNumAnomaliesText.
	 */
	private CLabel fNumAnomaliesText = null;

	/**
	 * Field FNumCommentsText.
	 */
	private CLabel fNumCommentsText = null;

	/**
	 * Field fDetailsText.
	 */
	private CLabel fDetailsText = null;

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
		final Composite mainForm = widgetFactory.createFlatFormComposite(parent);
		FormData data = null;

		//Author (read-only)
		fIdText = widgetFactory.createCLabel(mainForm, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fIdText.setToolTipText(R4EUIConstants.PARTICIPANT_ID_TOOLTIP);
		fIdText.setLayoutData(data);

		final CLabel idLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.ID_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fIdText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fIdText, 0, SWT.CENTER);
		idLabel.setToolTipText(R4EUIConstants.PARTICIPANT_ID_TOOLTIP);
		idLabel.setLayoutData(data);

		//Email
		fEmailText = widgetFactory.createText(mainForm, "", SWT.BORDER);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fIdText, ITabbedPropertyConstants.VSPACE);
		fEmailText.setToolTipText(R4EUIConstants.PARTICIPANT_EMAIL_TOOLTIP);
		fEmailText.setLayoutData(data);
		fEmailText.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EParticipant modelParticipant = ((R4EUIParticipant) fProperties.getElement()).getParticipant();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelParticipant,
								currentUser);
						modelParticipant.setEmail(fEmailText.getText());
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
		UIUtils.addTabbedPropertiesTextResizeListener(fEmailText);

		final CLabel emailLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.EMAIL_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fEmailText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fEmailText, 0, SWT.CENTER);
		emailLabel.setToolTipText(R4EUIConstants.PARTICIPANT_EMAIL_TOOLTIP);
		emailLabel.setLayoutData(data);

		//Number of Review Items added (read-only)
		fNumItemsText = widgetFactory.createCLabel(mainForm, "");
		fNumItemsText.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fIdText, ITabbedPropertyConstants.VSPACE);
		fNumItemsText.setToolTipText(R4EUIConstants.PARTICIPANT_NUM_ITEMS_TOOLTIP);
		fNumItemsText.setLayoutData(data);

		final CLabel numItemsLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.NUM_ITEMS_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fNumItemsText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fNumItemsText, 0, SWT.CENTER);
		numItemsLabel.setToolTipText(R4EUIConstants.PARTICIPANT_NUM_ITEMS_TOOLTIP);
		numItemsLabel.setLayoutData(data);

		//Number of Anomalies added (read-only)
		fNumAnomaliesText = widgetFactory.createCLabel(mainForm, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fNumItemsText, ITabbedPropertyConstants.VSPACE);
		fNumAnomaliesText.setToolTipText(R4EUIConstants.PARTICIPANT_NUM_ANOMALIES_TOOLTIP);
		fNumAnomaliesText.setLayoutData(data);

		final CLabel numAnomaliesLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.NUM_ANOMALIES_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fNumAnomaliesText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fNumAnomaliesText, 0, SWT.CENTER);
		numAnomaliesLabel.setToolTipText(R4EUIConstants.PARTICIPANT_NUM_ANOMALIES_TOOLTIP);
		numAnomaliesLabel.setLayoutData(data);

		//Number of Comments added (read-only)
		fNumCommentsText = widgetFactory.createCLabel(mainForm, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fNumAnomaliesText, ITabbedPropertyConstants.VSPACE);
		fNumCommentsText.setToolTipText(R4EUIConstants.PARTICIPANT_NUM_COMMENTS_TOOLTIP);
		fNumCommentsText.setLayoutData(data);

		final CLabel numCommentsLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.NUM_COMMENTS_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fNumCommentsText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fNumCommentsText, 0, SWT.CENTER);
		numCommentsLabel.setToolTipText(R4EUIConstants.PARTICIPANT_NUM_COMMENTS_TOOLTIP);
		numCommentsLabel.setLayoutData(data);

		//Details (read-only)
		fDetailsText = widgetFactory.createCLabel(mainForm, "");
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fNumCommentsText, ITabbedPropertyConstants.VSPACE);
		fDetailsText.setToolTipText(R4EUIConstants.PARTICIPANT_DETAILS_TOOLTIP);
		fDetailsText.setLayoutData(data);

		final CLabel detailsLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.USER_DETAILS_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fDetailsText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fDetailsText, 0, SWT.TOP);
		detailsLabel.setToolTipText(R4EUIConstants.PARTICIPANT_DETAILS_TOOLTIP);
		detailsLabel.setLayoutData(data);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EParticipant modelUser = ((R4EUIParticipant) fProperties.getElement()).getParticipant();
		fIdText.setText(modelUser.getId());
		if (null != modelUser.getEmail()) {
			fEmailText.setText(modelUser.getEmail());
		}
		fNumItemsText.setText(String.valueOf(modelUser.getAddedItems().size()));

		int numAnomalies = 0;
		int numComments = 0;
		final EList<R4EComment> comments = modelUser.getAddedComments();
		final int commentsSize = comments.size();
		for (int i = 0; i < commentsSize; i++) {
			if (comments.get(i) instanceof R4EAnomaly) {
				++numAnomalies;
			} else {
				++numComments;
			}
		}
		fNumAnomaliesText.setText(String.valueOf(numAnomalies));
		fNumCommentsText.setText(String.valueOf(numComments));
		final String details = ((R4EUIParticipant) fProperties.getElement()).getParticipantDetails();
		if (null != details) {
			fDetailsText.setText(details);
		} else {
			fDetailsText.setText("");
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
			fIdText.setEnabled(false);
			fNumItemsText.setEnabled(false);
			fNumAnomaliesText.setEnabled(false);
			fNumCommentsText.setEnabled(false);
		} else {
			fIdText.setEnabled(true);
			fNumItemsText.setEnabled(true);
			fNumAnomaliesText.setEnabled(true);
			fNumCommentsText.setEnabled(true);
		}
	}
}
