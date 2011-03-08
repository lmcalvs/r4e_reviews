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
 * This class implements the tabbed property section for the additional properties
 * for the Review model element.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.properties.tabbed;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.utils.EditableListWidget;
import org.eclipse.mylyn.reviews.r4e.ui.utils.IEditableListListener;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewExtraTabPropertySection extends ModelElementTabPropertySection
	implements IEditableListListener {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field FProjectText.
	 */
	protected Text fProjectText = null;
	
	/**
	 * Field FComponents.
	 */
	protected Table fComponents = null;
	
	/**
	 * Field FEntryCriteriaText.
	 */
	protected Text fEntryCriteriaText = null;
	
	/**
	 * Field FObjectivesText.
	 */
	protected Text fObjectivesText = null;
	
	/**
	 * Field FReferenceMaterialText.
	 */
	protected Text fReferenceMaterialText = null;
	
	/**
	 * Field fExitDecision.
	 */
	protected CCombo fExitDecisionCombo = null;
	
	/**
	 * Field fPhaseOwnerCombo.
	 */
	protected CCombo fPhaseOwnerCombo = null;
	
	/**
	 * Field fPreparationDateText.
	 */
	private Text fPreparationDateText = null;
	
	/**
	 * Field fDecisionDetailsList.
	 */
	protected EditableListWidget fDecisionDetailsList = null;
	
	/**
	 * Field fDecisionDateText.
	 */
	private Text fDecisionDateText = null;
	
	/**
	 * Field fReworkDateText.
	 */
	private Text fReworkDateText = null;
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method createControls.
	 * @param parent Composite
	 * @param aTabbedPropertySheetPage TabbedPropertySheetPage
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#createControls(Composite, TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent.getParent(), aTabbedPropertySheetPage);

		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
	    FormData data = null;
	    final Composite mainForm = widgetFactory.createFlatFormComposite(parent);

	    //Project (read-only)
	    fProjectText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(mainForm, ITabbedPropertyConstants.VSPACE);
	    fProjectText.setLayoutData(data);

	    final CLabel projectLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.PROJECT_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fProjectText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fProjectText, 0, SWT.TOP);
	    projectLabel.setLayoutData(data);
	    
	    //Components (Read-only)
	    fComponents = widgetFactory.createTable(mainForm, SWT.READ_ONLY | SWT.VIRTUAL);
	    fComponents.setLinesVisible(true);
	    fComponents.setItemCount(0);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fProjectText, ITabbedPropertyConstants.VSPACE);
	    fComponents.setLayoutData(data);
		
	    final CLabel componentsLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.COMPONENTS_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fComponents, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fComponents, 0, SWT.TOP);
	    componentsLabel.setLayoutData(data);
	    
	    //Entry Criteria (read-only)
	    fEntryCriteriaText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fComponents, ITabbedPropertyConstants.VSPACE);
	    fEntryCriteriaText.setLayoutData(data);

	    final CLabel entryCriteriaLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.ENTRY_CRITERIA_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fEntryCriteriaText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fEntryCriteriaText, 0, SWT.TOP);
	    entryCriteriaLabel.setLayoutData(data);
	
	    //Objectives (Read-only)
	    fObjectivesText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fEntryCriteriaText, ITabbedPropertyConstants.VSPACE);
	    fObjectivesText.setLayoutData(data);
		
	    final CLabel objectivesLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.OBJECTIVES_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fObjectivesText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fObjectivesText, 0, SWT.TOP);
	    objectivesLabel.setLayoutData(data);
	    
	    //Reference Material (Read-only)
	    fReferenceMaterialText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fObjectivesText, ITabbedPropertyConstants.VSPACE);
	    fReferenceMaterialText.setLayoutData(data);
		
	    final CLabel referenceMaterialLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.REFERENCE_MATERIAL_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fReferenceMaterialText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fReferenceMaterialText, 0, SWT.TOP);
	    referenceMaterialLabel.setLayoutData(data);
	    
	    //Exit Decision
	    fExitDecisionCombo = widgetFactory.createCCombo(mainForm, SWT.READ_ONLY);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fReferenceMaterialText, ITabbedPropertyConstants.VSPACE);
	    fExitDecisionCombo.setLayoutData(data);
	    fExitDecisionCombo.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EReview modelReview = ((R4EUIReviewBasic)fProperties.getElement()).getReview();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelReview, currentUser);
	    				modelReview.setDecision(R4EUIReviewBasic.getDecisionValueFromString(fExitDecisionCombo.getText()));
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
	    
	    final CLabel rankLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.EXIT_DECISION_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fExitDecisionCombo, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fExitDecisionCombo, 0, SWT.CENTER);
	    rankLabel.setLayoutData(data);
	    
	    if (fProperties.getElement() instanceof R4EUIReviewExtended) {
	    	//Phase Owner
		    fPhaseOwnerCombo = widgetFactory.createCCombo(mainForm, SWT.READ_ONLY);
		    data = new FormData();
		    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		    data.top = new FormAttachment(fExitDecisionCombo, ITabbedPropertyConstants.VSPACE);
		    fPhaseOwnerCombo.setLayoutData(data);
		    fPhaseOwnerCombo.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
		    		if (!fRefreshInProgress) {
		    			try {
		    				final String currentUser = R4EUIModelController.getReviewer();
							final R4EReview modelReview = ((R4EUIReviewExtended)fProperties.getElement()).getReview();
		    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelReview, currentUser);
		    				((R4EFormalReview)modelReview).setPhaseOwnerID(fPhaseOwnerCombo.getText());
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
		    		    
		    final CLabel phaseOwnerLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.DECIDED_BY_LABEL);
		    data = new FormData();
		    data.left = new FormAttachment(0, 0);
		    data.right = new FormAttachment(fPhaseOwnerCombo, -ITabbedPropertyConstants.HSPACE);
		    data.top = new FormAttachment(fPhaseOwnerCombo, 0, SWT.CENTER);
		    phaseOwnerLabel.setLayoutData(data);
		    
	    	//Preparation Date
		    fPreparationDateText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
		    data = new FormData();
		    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		    data.top = new FormAttachment(fPhaseOwnerCombo, ITabbedPropertyConstants.VSPACE);
		    fPreparationDateText.setLayoutData(data);

		    final CLabel preparationDateLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.PREPARATION_DATE_LABEL);
		    data = new FormData();
		    data.left = new FormAttachment(0, 0);
		    data.right = new FormAttachment(fPreparationDateText, -ITabbedPropertyConstants.HSPACE);
		    data.top = new FormAttachment(fPreparationDateText, 0, SWT.TOP);
		    preparationDateLabel.setLayoutData(data);
		    
		    //Decision Details
			data = new FormData();
			data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
			data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
			data.top = new FormAttachment(fPreparationDateText, ITabbedPropertyConstants.VSPACE);
			List<String> participants = ((R4EUIReviewBasic)fProperties.getElement()).getParticipantIDs();
			fDecisionDetailsList = new EditableListWidget(
					widgetFactory, mainForm, data, this, 1, CCombo.class, (String[]) participants.toArray());
		    //TODO update editableList widget to handle a table with two text columns
			//Also talk to alvaro for a model change to be able to track decision time spent for every participant
			
		    final CLabel decisionDetailsLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.TIME_SPENT_DETAILED_LABEL);
		    data = new FormData();
		    data.left = new FormAttachment(0, 0);
		    data.right = new FormAttachment(fDecisionDetailsList.getComposite(), -ITabbedPropertyConstants.HSPACE);
		    data.top = new FormAttachment(fDecisionDetailsList.getComposite(), 0, SWT.CENTER);
		    decisionDetailsLabel.setLayoutData(data);
		    
	    	//Decision Date
		    fDecisionDateText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
		    data = new FormData();
		    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		    data.top = new FormAttachment(fDecisionDetailsList.getComposite(), ITabbedPropertyConstants.VSPACE);
		    fDecisionDateText.setLayoutData(data);

		    final CLabel decisionDateLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.PREPARATION_DATE_LABEL);
		    data = new FormData();
		    data.left = new FormAttachment(0, 0);
		    data.right = new FormAttachment(fDecisionDateText, -ITabbedPropertyConstants.HSPACE);
		    data.top = new FormAttachment(fDecisionDateText, 0, SWT.TOP);
		    decisionDateLabel.setLayoutData(data);
		    
	    	//Rework Date
		    fReworkDateText = widgetFactory.createText(mainForm, "", SWT.READ_ONLY);
		    data = new FormData();
		    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
		    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
		    data.top = new FormAttachment(fDecisionDateText, ITabbedPropertyConstants.VSPACE);
		    fReworkDateText.setLayoutData(data);

		    final CLabel reworkDateLabel = widgetFactory.createCLabel(mainForm, R4EUIConstants.PREPARATION_DATE_LABEL);
		    data = new FormData();
		    data.left = new FormAttachment(0, 0);
		    data.right = new FormAttachment(fReworkDateText, -ITabbedPropertyConstants.HSPACE);
		    data.top = new FormAttachment(fReworkDateText, 0, SWT.TOP);
		    reworkDateLabel.setLayoutData(data);
	    }
	}
	
	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EReview modelReview = ((R4EUIReviewBasic)fProperties.getElement()).getReview();
		fProjectText.setText(modelReview.getProject());
		final String[] components = (String[]) modelReview.getComponents().toArray();
		fComponents.clearAll();
		TableItem item = null;
		for (int i = 0; i < components.length; i++) {
			String component  = components[i];
			if (i >= fComponents.getItemCount()) {
				 item = new TableItem (fComponents, SWT.NONE);
			} else {
				item = fComponents.getItem(i);
				if (null == item) item = new TableItem (fComponents, SWT.NONE);
			}
			item.setText(component);
		}
		fEntryCriteriaText.setText(modelReview.getEntryCriteria());
		fObjectivesText.setText(modelReview.getObjectives());
		fReferenceMaterialText.setText(modelReview.getReferenceMaterial());
		fExitDecisionCombo.setItems(R4EUIReviewBasic.getExitDecisionValues());
		if (null != modelReview.getDecision()) fExitDecisionCombo.select((null == modelReview.getDecision().getValue()) ? 0 : 
			modelReview.getDecision().getValue().getValue());
		
		if (modelReview instanceof R4EUIReviewExtended) {
	    	final List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
	    	final List<String> participantsList = new ArrayList<String>();
	    	for (R4EParticipant participant : participants) {
	    		participantsList.add(participant.getId());
	    	}
			final String[] participantsStr = participantsList.toArray(new String[participantsList.size()]);
			fPhaseOwnerCombo.setItems(participantsStr);
			fPhaseOwnerCombo.select(UIUtils.mapParticipantToIndex(((R4EFormalReview)modelReview).getPhaseOwnerID()));
			fPreparationDateText.setText(((R4EFormalReview)modelReview).getPreparationDate().toString());
			fDecisionDateText.setText(((R4EFormalReview)modelReview).getDecisionDate().toString());
			fReworkDateText.setText(((R4EFormalReview)modelReview).getReworkDate().toString());
		}
		
		setEnabledFields();
		fRefreshInProgress = false;
	}
	
	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen() || (!((R4EUIReviewBasic)fProperties.getElement()).isOpen()) ||
				((R4EUIReviewBasic)fProperties.getElement()).isReviewed()) {
			fProjectText.setEnabled(false);
			fComponents.setEnabled(false);
			fEntryCriteriaText.setEnabled(false);
			fObjectivesText.setEnabled(false);
			fReferenceMaterialText.setEnabled(false);
			fExitDecisionCombo.setEnabled(false);
			
			fPhaseOwnerCombo.setEnabled(false);
			fPreparationDateText.setEnabled(false);
			fDecisionDetailsList.setEnabled(false);
			fDecisionDateText.setEnabled(false);
			fReworkDateText.setEnabled(false);
		} else {
			fProjectText.setEnabled(true);
			fComponents.setEnabled(true);
			fEntryCriteriaText.setEnabled(true);
			fObjectivesText.setEnabled(true);
			fReferenceMaterialText.setEnabled(true);
			fExitDecisionCombo.setEnabled(true);
			
		    if (fProperties.getElement() instanceof R4EUIReviewExtended) {
				final R4EUIReviewExtended uiReview = (R4EUIReviewExtended)fProperties.getElement();

				fPhaseOwnerCombo.setEnabled(true);

				if (uiReview.isPreparationDateEnabled()) {
					fPreparationDateText.setEnabled(true);
				} else {
					fPreparationDateText.setEnabled(false);
				}
				
				if (uiReview.isDecisionDateEnabled()) {
					fDecisionDateText.setEnabled(true);
					fDecisionDetailsList.setEnabled(true);
				} else {
					fDecisionDateText.setEnabled(false);
					fDecisionDetailsList.setEnabled(false);
				}
				
				if (uiReview.isReworkDateEnabled()) {
					fReworkDateText.setEnabled(true);
				} else {
					fReworkDateText.setEnabled(false);
				}
		    }   	
		}
	}

	/**
	 * Method itemsUpdated.
	 * @param aItems Item[]
	 * @param aInstanceId int
	 * @see org.eclipse.mylyn.reviews.r4e.ui.utils.IEditableListListener#itemsUpdated(Item[], int)
	 */
	public void itemsUpdated(Item[] aItems, int aInstanceId) {
		try {
			final R4EReview modelReview = ((R4EUIReviewBasic)fProperties.getElement()).getReview();
			final String currentUser = R4EUIModelController.getReviewer();
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelReview, currentUser);
			
			for (Item item : aItems) {
				R4EParticipant participant = (R4EParticipant) modelReview.getUsersMap().get(item.getText());
				if (null != participant) participant.setIsPartOfDecision(true);
				//TODO model change: track time spent in decision for every participant
			}
			
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
			refresh();
		} catch (ResourceHandlingException e1) {
			UIUtils.displayResourceErrorDialog(e1);
		} catch (OutOfSyncException e1) {
			UIUtils.displaySyncErrorDialog(e1);
		}
	}
}
