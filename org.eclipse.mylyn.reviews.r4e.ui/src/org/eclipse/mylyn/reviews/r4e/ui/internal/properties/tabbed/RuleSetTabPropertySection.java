// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the tabbed property section for the Rule Set model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class RuleSetTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fVersionText.
	 */
	protected StyledText fVersionText = null;

	/**
	 * Field fNameText.
	 */
	private StyledText fNameText = null;

	/**
	 * Field fFilePathText.
	 */
	private StyledText fFilePathText = null;

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

		//Name
		fNameText = new StyledText(composite, SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fNameText.setEditable(false);
		fNameText.setToolTipText(R4EUIConstants.RULESET_NAME_TOOLTIP);
		fNameText.setLayoutData(data);

		final CLabel nameLabel = widgetFactory.createCLabel(composite, R4EUIConstants.NAME_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fNameText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fNameText, 0, SWT.CENTER);
		nameLabel.setToolTipText(R4EUIConstants.RULESET_NAME_TOOLTIP);
		nameLabel.setLayoutData(data);

		//File Path (read-only)
		fFilePathText = new StyledText(composite, SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fNameText, ITabbedPropertyConstants.VSPACE);
		fFilePathText.setEditable(false);
		fFilePathText.setToolTipText(R4EUIConstants.RULESET_FILE_PATH_TOOLTIP);
		fFilePathText.setLayoutData(data);

		final CLabel filePathLabel = widgetFactory.createCLabel(composite, R4EUIConstants.FILE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fFilePathText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fFilePathText, 0, SWT.CENTER);
		filePathLabel.setToolTipText(R4EUIConstants.RULESET_FILE_PATH_TOOLTIP);
		filePathLabel.setLayoutData(data);

		//Version
		fVersionText = new StyledText(composite, SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fFilePathText, ITabbedPropertyConstants.VSPACE);
		fVersionText.setEditable(false);
		fVersionText.setToolTipText(R4EUIConstants.RULESET_VERSION_TOOLTIP);
		fVersionText.setLayoutData(data);

		final CLabel versionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.VERSION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fVersionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fVersionText, 0, SWT.CENTER);
		versionLabel.setToolTipText(R4EUIConstants.RULESET_VERSION_TOOLTIP);
		versionLabel.setLayoutData(data);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		if (null != ((R4EUIRuleSet) fProperties.getElement()).getRuleSet()) {
			fRefreshInProgress = true;
			fVersionText.setText(((R4EUIRuleSet) fProperties.getElement()).getRuleSet().getVersion());
			fNameText.setText(((R4EUIRuleSet) fProperties.getElement()).getRuleSet().getName());
			fFilePathText.setText(URI.decode(((R4EUIRuleSet) fProperties.getElement()).getRuleSetFileURI().devicePath()));
			setEnabledFields();
			fRefreshInProgress = false;
		} else {
			fVersionText.setText("");
			fNameText.setText("");
			fFilePathText.setText("");
		}
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isJobInProgress() || !((R4EUIRuleSet) fProperties.getElement()).isOpen()
				|| !fProperties.getElement().isEnabled() || fProperties.getElement().isReadOnly()) {
			fNameText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fFilePathText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fVersionText.setForeground(UIUtils.DISABLED_FONT_COLOR);
		} else {
			fNameText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fFilePathText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fVersionText.setForeground(UIUtils.ENABLED_FONT_COLOR);
		}
	}
}
