// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity, sourceLength, explicitThisUsage
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

import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
public class ReviewBasicTabPropertySection extends ModelElementTabPropertySection {
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fNameText.
	 */
	private Text fNameText = null;
	
	/**
	 * Field fPhaseCombo.
	 */
	protected CCombo fPhaseCombo = null;
	
	/**
	 * Field fStartDateText.
	 */
	private Text fStartDateText = null;
	
	/**
	 * Field fEndDateText.
	 */
	private Text fEndDateText = null;
	
	/**
	 * Field fDescriptionText.
	 */
	protected Text fDescriptionText = null;
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method shouldUseExtraSpace.
	 * @return boolean
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#shouldUseExtraSpace()
	 */
	@Override
	public boolean shouldUseExtraSpace() {
		return true;
	}
	
	/**
	 * Method createControls.
	 * @param parent Composite
	 * @param aTabbedPropertySheetPage TabbedPropertySheetPage
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(Composite, TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		//Tell element to build its own detailed tab layout
		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
		final Composite mainForm = widgetFactory.createFlatFormComposite(parent);
	    FormData data = null;
	   
	    //Review Name (read-only for now)
	    fNameText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
	    fNameText.setLayoutData(data);
		
	    final CLabel nameLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.NAME_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fNameText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fNameText, 0, SWT.TOP);
	    nameLabel.setLayoutData(data);
	    
    	//Phase
	    fPhaseCombo = widgetFactory.createCCombo(mainForm, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fNameText, ITabbedPropertyConstants.VSPACE);
	    fPhaseCombo.setLayoutData(data);
	    fPhaseCombo.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent e) {
				final AtomicReference<String> aResultMsg = new AtomicReference<String>(null);
				R4EReviewPhase phase = null;
				if (fProperties.getElement() instanceof R4EUIReviewExtended) {
					phase = ((R4EUIReviewExtended)fProperties.getElement()).getPhaseFromString(fPhaseCombo.getText());
				} else {
					phase = ((R4EUIReviewBasic)fProperties.getElement()).getPhaseFromString(fPhaseCombo.getText());
				}
				if (((R4EUIReviewBasic)fProperties.getElement()).validatePhaseChange(phase, aResultMsg)) {
	    			if (!fRefreshInProgress) {
	    				if (null != aResultMsg.get()) {
	    					final ErrorDialog dialog = new ErrorDialog(null, "Warning", aResultMsg.get(),
				    			new Status(IStatus.WARNING, Activator.PLUGIN_ID, 0, null, null), IStatus.WARNING);
	    					dialog.open();
	    				}
	    				try {
	    					if (fProperties.getElement() instanceof R4EUIReviewExtended) {
	    						((R4EUIReviewExtended)fProperties.getElement()).updatePhase(phase);
	    					} else {
	    						((R4EUIReviewBasic)fProperties.getElement()).updatePhase(phase);
	    					}
	    				} catch (ResourceHandlingException e1) {
	    					UIUtils.displayResourceErrorDialog(e1);
	    				} catch (OutOfSyncException e1) {
	    					UIUtils.displaySyncErrorDialog(e1);
	    				}
	    			}
	    			refresh();
	    		} else {
	    			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.REVIEW_NOT_COMPLETED_ERROR, "Review cannot be set to completed",
			    			new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, aResultMsg.get(), null), IStatus.ERROR);
			    	dialog.open();
	    		}
	    	}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel phaseLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.PHASE_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fPhaseCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fPhaseCombo, 0, SWT.CENTER);
	    phaseLabel.setLayoutData(data);
	    
	    //Review Start Date (read-only)
	    fStartDateText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fPhaseCombo, ITabbedPropertyConstants.VSPACE);
	    fStartDateText.setLayoutData(data);

	    final CLabel creationDateLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.START_DATE_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fStartDateText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fStartDateText, 0, SWT.TOP);
	    creationDateLabel.setLayoutData(data);
	
	    //End Date (read-only)
	    fEndDateText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fStartDateText, ITabbedPropertyConstants.VSPACE);
	    fEndDateText.setLayoutData(data);

	    final CLabel endDateLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.END_DATE_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fEndDateText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fEndDateText, 0, SWT.TOP);
	    endDateLabel.setLayoutData(data);
	    
	    //Review Description
	    fDescriptionText = widgetFactory.createText(mainForm, "", SWT.MULTI);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fEndDateText, ITabbedPropertyConstants.VSPACE);
	    fDescriptionText.setLayoutData(data);
	    fDescriptionText.addFocusListener(new FocusListener() {		
			public void focusLost(FocusEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EReview modelReview = ((R4EUIReviewBasic)fProperties.getElement()).getReview();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelReview, currentUser);
	    				modelReview.setExtraNotes(fDescriptionText.getText());
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

	    final CLabel descriptionLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.DESCRIPTION_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fDescriptionText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fDescriptionText, 0, SWT.TOP);
	    descriptionLabel.setLayoutData(data);
	}
	
	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		if (fProperties.getElement() instanceof R4EUIReviewExtended) {
			final R4EUIReviewExtended uiReview = (R4EUIReviewExtended)fProperties.getElement();
			final R4EFormalReview modelReview = (R4EFormalReview) uiReview.getReview();
			fPhaseCombo.setItems(uiReview.getAvailablePhases());
			fPhaseCombo.select(uiReview.mapPhaseToIndex(((R4EReviewState)modelReview.getState()).getState()));
			fNameText.setText(modelReview.getName());
			fStartDateText.setText(modelReview.getStartDate().toString());
			if (null == modelReview.getEndDate()) {
				fEndDateText.setText("(In Progress)");
			} else {
				fEndDateText.setText(modelReview.getEndDate().toString());
			}
			fDescriptionText.setText(modelReview.getExtraNotes());
		} else {
			final R4EUIReviewBasic uiReview = (R4EUIReviewBasic)fProperties.getElement();
			final R4EReview modelReview = uiReview.getReview();
			fPhaseCombo.setItems(uiReview.getAvailablePhases());
			fPhaseCombo.select(uiReview.mapPhaseToIndex(((R4EReviewState)modelReview.getState()).getState()));
			fNameText.setText(modelReview.getName());
			fStartDateText.setText(modelReview.getStartDate().toString());
			if (null == modelReview.getEndDate()) {
				fEndDateText.setText("(In Progress)");
			} else {
				fEndDateText.setText(modelReview.getEndDate().toString());
			}
			fDescriptionText.setText(modelReview.getExtraNotes());
		}

		setEnabledFields();
		fRefreshInProgress = false;
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}

	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen() || (!((R4EUIReviewBasic)fProperties.getElement()).isOpen()) ||
				((R4EUIReviewBasic)fProperties.getElement()).isReviewed()) {
			fNameText.setEnabled(false);
			fPhaseCombo.setEnabled(false);
			fDescriptionText.setEnabled(false);
			fStartDateText.setEnabled(false);
			fEndDateText.setEnabled(false);
		} else {
			fNameText.setEnabled(true);
			fPhaseCombo.setEnabled(true);
			fStartDateText.setEnabled(true);
			fEndDateText.setEnabled(true);
			fDescriptionText.setEnabled(true);
		}
	}
}
