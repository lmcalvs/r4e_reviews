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
 * This class implements the tabbed property section for the Anomaly Extended 
 * model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.tabbed;

import java.text.SimpleDateFormat;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.CalendarDialog;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AnomalyExtraTabPropertySection extends ModelElementTabPropertySection {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fStateCombo.
	 */
	protected CCombo fStateCombo = null;
	
	/**
	 * Field fClassCombo.
	 */
	protected CCombo fClassCombo = null;
	
	/**
	 * Field fRankCombo.
	 */
	protected CCombo fRankCombo = null;
	
	/**
	 * Field fDateText.
	 */
	protected Text fDateText = null;
	
	/**
	 * Field fCalendarButton.
	 */
	protected Button fCalendarButton = null;
	
	/**
	 * Field fRankCombo.
	 */
	protected CCombo fDecidedByCombo = null;
	
	/**
	 * Field fRankCombo.
	 */
	protected CCombo fFixedByCombo = null;
	
	/**
	 * Field fRankCombo.
	 */
	protected CCombo fFollowUpByCombo = null;
	
	/**
	 * Field fNotAcceptedReasonText.
	 */
	protected Text fNotAcceptedReasonText = null;
	
	
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
		final Composite composite = widgetFactory.createFlatFormComposite(parent);
	    FormData data = null;
        
	    //State
	    fStateCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
	    fStateCombo.setLayoutData(data);
	    fStateCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				((R4EUIAnomalyExtended)fProperties.getElement()).updateState(
	    						R4EUIAnomalyExtended.getStateFromString(fStateCombo.getText()));
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}
    			refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel stateLabel = widgetFactory.createCLabel(composite, R4EUIConstants.STATE_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fStateCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fStateCombo, 0, SWT.CENTER);
	    stateLabel.setLayoutData(data);
	    
	    //Class
	    fClassCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fStateCombo, ITabbedPropertyConstants.VSPACE);
	    fClassCombo.setLayoutData(data);
	    fClassCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
	    				final R4ECommentType type = RModelFactoryExt.eINSTANCE.createR4ECommentType();
	    				type.setType(R4EUIAnomalyExtended.getClassFromString(fClassCombo.getText()));
	    				modelAnomaly.setType(type);
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}
    			refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel classLabel = widgetFactory.createCLabel(composite, R4EUIConstants.CLASS_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fClassCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fClassCombo, 0, SWT.CENTER);
	    classLabel.setLayoutData(data);
	    
	    //Rank
	    fRankCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fClassCombo, ITabbedPropertyConstants.VSPACE);
	    fRankCombo.setLayoutData(data);
	    fRankCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
	    				modelAnomaly.setRank(R4EUIAnomalyExtended.getRankFromString(fClassCombo.getText()));
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}	
    			refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel rankLabel = widgetFactory.createCLabel(composite, R4EUIConstants.RANK_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fRankCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fRankCombo, 0, SWT.CENTER);
	    rankLabel.setLayoutData(data);

	    //Due Date
	    final Composite dateComposite = widgetFactory.createComposite(composite);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fRankCombo, ITabbedPropertyConstants.VSPACE);
	    dateComposite.setLayoutData(data);
	    dateComposite.setLayout(new GridLayout(2, false));
	    
	    fDateText = widgetFactory.createText(dateComposite, "", SWT.READ_ONLY);
	    fDateText.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
	    fDateText.setEditable(false);
	    fCalendarButton = widgetFactory.createButton(dateComposite, "...", SWT.NONE);
	    fCalendarButton.setLayoutData(new GridData(GridData.FILL, GridData.FILL, false, false));
	    fCalendarButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				final CalendarDialog dialog = new CalendarDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
						getSite().getWorkbenchWindow().getShell());
		    	final int result = dialog.open();
		    	if (result == Window.OK) {
		    		final SimpleDateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.SIMPLE_DATE_FORMAT);	
		    		fDateText.setText(dateFormat.format(dialog.getDate()));
		    		if (!fRefreshInProgress) {
		    			try {
		    				final String currentUser = R4EUIModelController.getReviewer();
							final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
		    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
		    				modelAnomaly.setDueDate(dialog.getDate());
		    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		    			} catch (ResourceHandlingException e1) {
		    				UIUtils.displayResourceErrorDialog(e1);
		    			} catch (OutOfSyncException e1) {
		    				UIUtils.displaySyncErrorDialog(e1);
		    			}
		    		}
	    			refresh();
		    	}
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				// No implementation needed
			}
		});
	    
	    final CLabel dateLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DUE_DATE_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(dateComposite, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(dateComposite, 0, SWT.CENTER);
	    dateLabel.setLayoutData(data);
	    
	    //Decided by
	    fDecidedByCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(dateComposite, ITabbedPropertyConstants.VSPACE);
	    fDecidedByCombo.setLayoutData(data);
	    fDecidedByCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
	    				modelAnomaly.setDecidedByID(fDecidedByCombo.getText());
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}
    			refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel decidedByLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DECIDED_BY_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fDecidedByCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fDecidedByCombo, 0, SWT.CENTER);
	    decidedByLabel.setLayoutData(data);
	    
	    //Fixed by
	    fFixedByCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fDecidedByCombo, ITabbedPropertyConstants.VSPACE);
	    fFixedByCombo.setLayoutData(data);
	    fFixedByCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
	    				modelAnomaly.setFixedByID(fFixedByCombo.getText());
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}
    			refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel fixedByLabel = widgetFactory.createCLabel(composite, R4EUIConstants.FIXED_BY_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fFixedByCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fFixedByCombo, 0, SWT.CENTER);
	    fixedByLabel.setLayoutData(data);
	    
	    //Folluw-up by
	    fFollowUpByCombo = widgetFactory.createCCombo(composite, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fFixedByCombo, ITabbedPropertyConstants.VSPACE);
	    fFollowUpByCombo.setLayoutData(data);
	    fFollowUpByCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
	    				modelAnomaly.setFollowUpByID(fFollowUpByCombo.getText());
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}
    			refresh();
			}
			public void widgetDefaultSelected(SelectionEvent e) { // $codepro.audit.disable emptyMethod
				//No implementation needed
			}
		});
	    
	    final CLabel followUpByLabel = widgetFactory.createCLabel(composite, R4EUIConstants.FOLLOWUP_BY_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fFollowUpByCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fFollowUpByCombo, 0, SWT.CENTER);
	    followUpByLabel.setLayoutData(data);
	    
	    //Not accepted reason
	    fNotAcceptedReasonText = widgetFactory.createText(composite, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fFollowUpByCombo, ITabbedPropertyConstants.VSPACE);
	    fNotAcceptedReasonText.setLayoutData(data);
	    fNotAcceptedReasonText.addFocusListener(new FocusListener() {		
			public void focusLost(FocusEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EAnomaly modelAnomaly = ((R4EUIAnomalyExtended)fProperties.getElement()).getAnomaly();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly, currentUser);
	    				modelAnomaly.setNotAcceptedReason(fNotAcceptedReasonText.getText());
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
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
	    UIUtils.addTabbedPropertiesTextResizeListener(fNotAcceptedReasonText);
	    
	    final CLabel notAcceptedReasonLabel = widgetFactory.createCLabel(composite, R4EUIConstants.NOT_ACCEPTED_REASON_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fNotAcceptedReasonText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fNotAcceptedReasonText, 0, SWT.CENTER);
	    notAcceptedReasonLabel.setLayoutData(data);
	}
	
	
	
	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EUIAnomalyExtended uiModelAnomaly = (R4EUIAnomalyExtended)fProperties.getElement();
		final R4EAnomaly modelAnomaly = uiModelAnomaly.getAnomaly();
    	final List<String> participants = R4EUIModelController.getActiveReview().getParticipantIDs();
		fStateCombo.setItems(uiModelAnomaly.getAvailableStates());
		fStateCombo.select(uiModelAnomaly.mapStateToIndex(modelAnomaly.getState()));
		fClassCombo.setItems(R4EUIAnomalyExtended.getClasses());
		if (null != modelAnomaly.getType() && null != ((R4ECommentType)modelAnomaly.getType()).getType()) {
			fClassCombo.select(((R4ECommentType)modelAnomaly.getType()).getType().getValue());
		}
		fRankCombo.setItems(R4EUIAnomalyExtended.getRanks());
		fRankCombo.select(modelAnomaly.getRank().getValue());
		if (null != modelAnomaly.getDueDate()) {
    		final SimpleDateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.SIMPLE_DATE_FORMAT);	
    		fDateText.setText(dateFormat.format(modelAnomaly.getDueDate()));
		}
		fDecidedByCombo.setItems(participants.toArray(new String[participants.size()]));
		fDecidedByCombo.select(UIUtils.mapParticipantToIndex(modelAnomaly.getDecidedByID()));
		fFixedByCombo.setItems(participants.toArray(new String[participants.size()]));
		fFixedByCombo.select(UIUtils.mapParticipantToIndex(modelAnomaly.getFixedByID()));
		fFollowUpByCombo.setItems(participants.toArray(new String[participants.size()]));
		fFollowUpByCombo.select(UIUtils.mapParticipantToIndex(modelAnomaly.getFollowUpByID()));
		if (null != modelAnomaly.getNotAcceptedReason()) fNotAcceptedReasonText.setText(modelAnomaly.getNotAcceptedReason());
		setEnabledFields();
		fRefreshInProgress = false;
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}
	
	/**
	 * Method setEditableFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen()) {
			fStateCombo.setEnabled(false);
			fClassCombo.setEnabled(false);
			fRankCombo.setEnabled(false);
			fDateText.setEnabled(false);
			fCalendarButton.setEnabled(false);
			fDecidedByCombo.setEnabled(false);
			fFixedByCombo.setEnabled(false);
			fFollowUpByCombo.setEnabled(false);
			fNotAcceptedReasonText.setEnabled(false);
		} else {
			final R4EUIAnomalyExtended uiAnomaly = (R4EUIAnomalyExtended)fProperties.getElement();
			fStateCombo.setEnabled(true);
			
			if (uiAnomaly.isClassEnabled()) {
				fClassCombo.setEnabled(true);
			} else {
				fClassCombo.setEnabled(false);
			}
			
			if (uiAnomaly.isRankEnabled()) {
				fRankCombo.setEnabled(true);
			} else  {
				fRankCombo.setEnabled(false);
			}
			
			if (uiAnomaly.isDueDateEnabled()) {
				fDateText.setEnabled(true);
				fCalendarButton.setEnabled(true);
			} else {
				fDateText.setEnabled(false);
				fCalendarButton.setEnabled(false);
			}
			
			if (uiAnomaly.isDecidedByEnabled()) {
				fDecidedByCombo.setEnabled(true);
			} else {
				fDecidedByCombo.setEnabled(false);
			}
			
			if (uiAnomaly.isFixedByEnabled()) {
				fFixedByCombo.setEnabled(true);
			} else {
				fFixedByCombo.setEnabled(false);
			}
			
			if (uiAnomaly.isFollowUpByEnabled()) {
				fFollowUpByCombo.setEnabled(true);
			} else {
				fFollowUpByCombo.setEnabled(false);
			}
			
			if (uiAnomaly.isNotAcceptedReasonEnabled()) {
				fNotAcceptedReasonText.setEnabled(true);
				fNotAcceptedReasonText.setEditable(true);
			}
			else {
				fNotAcceptedReasonText.setEnabled(false);
			}
		}
	}
}
