// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This class implements the tabbed property section for the Rule model element
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class RuleTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fIdText.
	 */
	protected Text fIdText = null;

	/**
	 * Field fTitleText.
	 */
	protected Text fTitleText = null;

	/**
	 * Field fDescriptionText.
	 */
	protected Text fDescriptionText = null;

	/**
	 * Field fClassCombo.
	 */
	protected CCombo fClassCombo = null;

	/**
	 * Field fRankCombo.
	 */
	protected CCombo fRankCombo = null;

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

		//ID
		widgetFactory.setBorderStyle(SWT.NULL);
		fIdText = widgetFactory.createText(composite, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fIdText.setEditable(false);
		fIdText.setToolTipText(R4EUIConstants.RULE_ID_TOOLTIP);
		fIdText.setLayoutData(data);

		final CLabel idLabel = widgetFactory.createCLabel(composite, R4EUIConstants.ID_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fIdText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fIdText, 0, SWT.CENTER);
		idLabel.setToolTipText(R4EUIConstants.RULE_ID_TOOLTIP);
		idLabel.setLayoutData(data);

		//Title
		fTitleText = widgetFactory.createText(composite, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fIdText, ITabbedPropertyConstants.VSPACE);
		fTitleText.setEditable(false);
		fTitleText.setToolTipText(R4EUIConstants.RULE_TITLE_TOOLTIP);
		fTitleText.setLayoutData(data);

		final CLabel titleLabel = widgetFactory.createCLabel(composite, R4EUIConstants.TITLE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fTitleText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fTitleText, 0, SWT.CENTER);
		titleLabel.setToolTipText(R4EUIConstants.RULE_TITLE_TOOLTIP);
		titleLabel.setLayoutData(data);

		//Description
		widgetFactory.setBorderStyle(SWT.BORDER);
		fDescriptionText = widgetFactory.createText(composite, "", SWT.MULTI);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fTitleText, ITabbedPropertyConstants.VSPACE);
		fDescriptionText.setToolTipText(R4EUIConstants.RULE_DESCRIPTION_TOOLTIP);
		fDescriptionText.setLayoutData(data);
		fDescriptionText.addListener(SWT.FocusOut, new Listener() {
			public void handleEvent(Event event) {
				if (!fRefreshInProgress && fDescriptionText.getForeground().equals(UIUtils.ENABLED_FONT_COLOR)) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EDesignRule modelRule = ((R4EUIRule) fProperties.getElement()).getRule();
						String newValue = fDescriptionText.getText().trim();
						if (!(newValue.equals(modelRule.getDescription()))) {
							final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelRule, currentUser);
							modelRule.setDescription(newValue);
							R4EUIModelController.FResourceUpdater.checkIn(bookNum);
						}
						fDescriptionText.setText(newValue);
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
			}
		});
		UIUtils.addTabbedPropertiesTextResizeListener(fDescriptionText);

		final CLabel descriptionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DESCRIPTION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fDescriptionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fDescriptionText, 0, SWT.CENTER);
		descriptionLabel.setToolTipText(R4EUIConstants.RULE_DESCRIPTION_TOOLTIP);
		descriptionLabel.setLayoutData(data);

		//Class
		fClassCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fDescriptionText, ITabbedPropertyConstants.VSPACE);
		fClassCombo.setToolTipText(R4EUIConstants.RULE_CLASS_TOOLTIP);
		fClassCombo.setLayoutData(data);
		fClassCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EDesignRule modelRule = ((R4EUIRule) fProperties.getElement()).getRule();
						R4EDesignRuleClass newClass = UIUtils.getClassFromString(fClassCombo.getText());
						R4EDesignRuleClass oldClass = modelRule.getClass_();
						if (!newClass.equals(oldClass)) {
							final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelRule, currentUser);
							modelRule.setClass(newClass);
							R4EUIModelController.FResourceUpdater.checkIn(bookNum);
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}
		});
		addScrollListener(fClassCombo);

		final CLabel classLabel = widgetFactory.createCLabel(composite, R4EUIConstants.CLASS_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fClassCombo, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fClassCombo, 0, SWT.CENTER);
		classLabel.setToolTipText(R4EUIConstants.RULE_CLASS_TOOLTIP);
		classLabel.setLayoutData(data);

		//Rank
		fRankCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fClassCombo, ITabbedPropertyConstants.VSPACE);
		fRankCombo.setToolTipText(R4EUIConstants.RULE_RANK_TOOLTIP);
		fRankCombo.setLayoutData(data);
		fRankCombo.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EDesignRule modelRule = ((R4EUIRule) fProperties.getElement()).getRule();
						R4EDesignRuleRank newRank = UIUtils.getRankFromString(fRankCombo.getText());
						R4EDesignRuleRank oldRank = modelRule.getRank();
						if (!newRank.equals(oldRank)) {
							final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelRule, currentUser);
							modelRule.setRank(UIUtils.getRankFromString(fRankCombo.getText()));
							R4EUIModelController.FResourceUpdater.checkIn(bookNum);
						}
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
				refresh();
			}
		});
		addScrollListener(fRankCombo);

		final CLabel rankLabel = widgetFactory.createCLabel(composite, R4EUIConstants.RANK_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fRankCombo, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fRankCombo, 0, SWT.CENTER);
		rankLabel.setToolTipText(R4EUIConstants.RULE_RANK_TOOLTIP);
		rankLabel.setLayoutData(data);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		if (null != ((R4EUIRule) fProperties.getElement()).getRule()) {
			fRefreshInProgress = true;
			final R4EDesignRule modelRule = ((R4EUIRule) fProperties.getElement()).getRule();
			fIdText.setText(modelRule.getId());
			fTitleText.setText(modelRule.getTitle());
			fDescriptionText.setText(modelRule.getDescription());
			fClassCombo.setItems(UIUtils.getClasses());
			if (null != modelRule.getClass()) {
				fClassCombo.select(modelRule.getClass_().getValue());
			} else {
				fClassCombo.setText("");
			}
			fRankCombo.setItems(UIUtils.getRanks());
			//Bug 368865:  Mapping needed for DEPRECATED value to MINOR
			final int rankValue = modelRule.getRank().getValue();
			fRankCombo.select(rankValue == R4EDesignRuleRank.R4E_RANK_DEPRECATED_VALUE
					? R4EDesignRuleRank.R4E_RANK_MINOR_VALUE
					: rankValue);

			setEnabledFields();
			fRefreshInProgress = false;
		} else {
			fIdText.setText("");
			fTitleText.setText("");
			fDescriptionText.setText("");
			fClassCombo.setText("");
			fRankCombo.setText("");
		}
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isJobInProgress() || !fProperties.getElement().isEnabled()
				|| fProperties.getElement().isReadOnly()) {
			fIdText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fTitleText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fDescriptionText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fDescriptionText.setEditable(false);
			fClassCombo.setEnabled(false);
			fRankCombo.setEnabled(false);
		} else {
			fIdText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fTitleText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fDescriptionText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fDescriptionText.setEditable(true);
			fClassCombo.setEnabled(true);
			fRankCombo.setEnabled(true);
		}
	}
}
