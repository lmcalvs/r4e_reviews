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
 * This class implements the tabbed property section for the Selection model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ContentsTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fPositionText.
	 */
	private Text fPositionText = null;

	/**
	 * Field fAssignedToComposite.
	 */
	private Composite fAssignedToComposite;

	/**
	 * Field fAssignedToText.
	 */
	private Text fAssignedToText;

	/**
	 * Field fAssignedToButton.
	 */
	private Button fAssignedToButton;

	/**
	 * Field fUnassignedFromButton.
	 */
	private Button fUnassignedFromButton;

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

		//Position (read-only)
		widgetFactory.setBorderStyle(SWT.NULL);
		fPositionText = widgetFactory.createText(composite, "", SWT.NULL);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
		fPositionText.setEditable(false);
		fPositionText.setToolTipText(R4EUIConstants.CONTENTS_POSITION_TOOLTIP);
		fPositionText.setLayoutData(data);

		final CLabel positionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.POSITION_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fPositionText, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fPositionText, 0, SWT.CENTER);

		positionLabel.setToolTipText(R4EUIConstants.CONTENTS_POSITION_TOOLTIP);
		positionLabel.setLayoutData(data);

		//Assigned To
		fAssignedToComposite = widgetFactory.createComposite(composite);
		data = new FormData();
		data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		data.top = new FormAttachment(fPositionText, ITabbedPropertyConstants.VSPACE);
		fAssignedToComposite.setToolTipText(R4EUIConstants.ASSIGNED_TO_TOOLTIP);
		fAssignedToComposite.setLayoutData(data);
		fAssignedToComposite.setLayout(new GridLayout(3, false));

		widgetFactory.setBorderStyle(SWT.BORDER);
		fAssignedToText = widgetFactory.createText(fAssignedToComposite, "");
		fAssignedToText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fAssignedToText.setEditable(false);
		fAssignedToButton = widgetFactory.createButton(fAssignedToComposite, R4EUIConstants.ADD_LABEL, SWT.NONE);
		fAssignedToButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		fAssignedToButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				((R4EUIContent) fProperties.getElement()).addAssignees(UIUtils.getAssignParticipants());
				refresh();
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		});

		fUnassignedFromButton = widgetFactory.createButton(fAssignedToComposite, R4EUIConstants.REMOVE_LABEL, SWT.NONE);
		fUnassignedFromButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		fUnassignedFromButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				((R4EUIContent) fProperties.getElement()).removeAssignees(UIUtils.getUnassignParticipants(fProperties.getElement()));
				refresh();
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		});

		final CLabel assignedToLabel = widgetFactory.createCLabel(composite, R4EUIConstants.ASSIGNED_TO_LABEL);
		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fAssignedToComposite, -ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(fAssignedToComposite, 0, SWT.CENTER);
		assignedToLabel.setToolTipText(R4EUIConstants.ASSIGNED_TO_TOOLTIP);
		assignedToLabel.setLayoutData(data);
	}

	/**
	 * Method refresh.
	 * 
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		final R4EUIContent uiContent = ((R4EUIContent) fProperties.getElement());
		fRefreshInProgress = true;
		if (null != uiContent.getPosition()) {
			fPositionText.setText(uiContent.getPosition().toString());
		} else {
			fPositionText.setText("");
		}

		final EList<String> assignedParticipants = uiContent.getContent().getAssignedTo();
		fAssignedToText.setText(UIUtils.formatAssignedParticipants(assignedParticipants));
		setEnabledFields();
		fRefreshInProgress = false;
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isJobInProgress()
				|| fProperties.getElement().isReadOnly()
				|| !fProperties.getElement().isEnabled()
				|| null == R4EUIModelController.getActiveReview()
				|| ((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED)) {
			fPositionText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fAssignedToText.setForeground(UIUtils.DISABLED_FONT_COLOR);
			fAssignedToButton.setEnabled(false);
			fUnassignedFromButton.setEnabled(false);
		} else {
			fPositionText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fAssignedToText.setForeground(UIUtils.ENABLED_FONT_COLOR);
			fAssignedToButton.setEnabled(true);
			if (fAssignedToText.getText().length() > 0) {
				fUnassignedFromButton.setEnabled(true);
			} else {
				fUnassignedFromButton.setEnabled(false);
			}
		}
	}
}
