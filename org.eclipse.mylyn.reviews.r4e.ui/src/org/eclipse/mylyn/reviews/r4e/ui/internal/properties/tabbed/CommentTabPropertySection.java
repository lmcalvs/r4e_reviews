// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity, sourceLength, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the tabbed property section for the Comment model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class CommentTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field FAuthorText.
	 */
	private Text fAuthorText = null;

	/**
	 * Field FCreationDateText.
	 */
	private Text fCreationDateText = null;

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

		//Author (read-only)
		widgetFactory.setBorderStyle(SWT.NULL);
		fAuthorText = widgetFactory.createText(composite, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fAuthorText.setEditable(false);
		fAuthorText.setToolTipText(R4EUIConstants.COMMENT_AUTHOR_TOOLTIP);
		fAuthorText.setLayoutData(data);

		final CLabel authorLabel = widgetFactory.createCLabel(composite, R4EUIConstants.AUTHOR_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fAuthorText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fAuthorText, 0, SWT.CENTER);
		authorLabel.setToolTipText(R4EUIConstants.COMMENT_AUTHOR_TOOLTIP);
		authorLabel.setLayoutData(data);

		//Creation Date (read-only)
		fCreationDateText = widgetFactory.createText(composite, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fAuthorText, ITabbedPropertyConstants.VSPACE);
		fCreationDateText.setEditable(false);
		fCreationDateText.setToolTipText(R4EUIConstants.COMMENT_CREATION_DATE_TOOLTIP);
		fCreationDateText.setLayoutData(data);

		final CLabel creationDateLabel = widgetFactory.createCLabel(composite, R4EUIConstants.CREATION_DATE_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fCreationDateText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fCreationDateText, 0, SWT.CENTER);
		creationDateLabel.setToolTipText(R4EUIConstants.COMMENT_CREATION_DATE_TOOLTIP);
		creationDateLabel.setLayoutData(data);

		//Description (read-only)
		fDescriptionText = widgetFactory.createText(composite, "", SWT.MULTI);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fCreationDateText, ITabbedPropertyConstants.VSPACE);
		fDescriptionText.setEditable(false);
		fDescriptionText.setToolTipText(R4EUIConstants.COMMENT_DESCRIPTION_TOOLTIP);
		fDescriptionText.setLayoutData(data);

		final CLabel descriptionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DESCRIPTION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fDescriptionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fDescriptionText, 0, SWT.CENTER);
		descriptionLabel.setToolTipText(R4EUIConstants.COMMENT_DESCRIPTION_TOOLTIP);
		descriptionLabel.setLayoutData(data);
		widgetFactory.setBorderStyle(SWT.BORDER);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EComment modelComment = ((R4EUIComment) fProperties.getElement()).getComment();
		fAuthorText.setText(modelComment.getUser().getId());
		fCreationDateText.setText(modelComment.getCreatedOn().toString());
		fDescriptionText.setText(modelComment.getDescription());
		fDescriptionText.getParent().layout();
		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEditableFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isJobInProgress()
				|| fProperties.getElement().isReadOnly()
				|| null == R4EUIModelController.getActiveReview()
				|| ((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED) || !fProperties.getElement().isEnabled()) {
			fAuthorText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fCreationDateText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fDescriptionText.setForeground(UIUtils.DISABLED_FONT_COLOR);
		} else {
			fAuthorText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fCreationDateText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fDescriptionText.setForeground(UIUtils.ENABLED_FONT_COLOR);
		}
	}
}
