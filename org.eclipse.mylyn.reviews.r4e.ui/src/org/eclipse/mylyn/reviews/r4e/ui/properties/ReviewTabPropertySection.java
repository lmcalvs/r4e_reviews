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

package org.eclipse.mylyn.reviews.r4e.ui.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewTabPropertySection extends AbstractPropertySection implements IPropertyListener {
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fReview.
	 */
	private ReviewProperties fReviewProps;
	
	/**
	 * Field FNameText.
	 */
	protected static Text FNameText = null;
	
	/**
	 * Field FCreationDateText.
	 */
	protected static Text FCreationDateText = null;
	
	/**
	 * Field FDescriptionText.
	 */
	protected static Text FDescriptionText = null;
	
	/**
	 * Field fRefreshInProgress.
	 */
	private boolean fRefreshInProgress = false;

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method dispose.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		R4EUIModelController.removeDialogStateListener(this);
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
		R4EUIModelController.addDialogStateListener(this);

		//Tell element to build its own detailed tab layout
		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
		final Composite composite = widgetFactory.createFlatFormComposite(parent);
	    FormData data = null;
	   
	    //Review Name
	    FNameText = widgetFactory.createText(composite, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
	    FNameText.setLayoutData(data);
		FNameText.addModifyListener(new ModifyListener() {			 // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			@SuppressWarnings("synthetic-access")
			public void modifyText(ModifyEvent e) {
				if (!fRefreshInProgress) {
					try {
						final String currentUser = R4EUIModelController.getReviewer();
						final R4EReview modelReview = ((R4EUIReview)fReviewProps.getElement()).getReview();
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelReview, currentUser);
						modelReview.setName(FNameText.getText());
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);
					} catch (ResourceHandlingException e1) {
						UIUtils.displayResourceErrorDialog(e1);
					} catch (OutOfSyncException e1) {
						UIUtils.displaySyncErrorDialog(e1);
					}
				}
			}
		});
		
	    final CLabel nameLabel = widgetFactory.createCLabel(composite, R4EUIConstants.NAME_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FNameText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FNameText, 0, SWT.CENTER);
	    nameLabel.setLayoutData(data);
	    
	    //Review Creation Date (read-only)
	    FCreationDateText = widgetFactory.createText(composite, "");
	    FCreationDateText.setEditable(false);
	    FCreationDateText.setEnabled(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(FNameText, ITabbedPropertyConstants.VSPACE);
	    FCreationDateText.setLayoutData(data);

	    final CLabel creationDateLabel = widgetFactory.createCLabel(composite, R4EUIConstants.CREATION_DATE_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FCreationDateText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FCreationDateText, 0, SWT.CENTER);
	    creationDateLabel.setLayoutData(data);
	
	    //Review Description
	    FDescriptionText = widgetFactory.createText(composite, "", SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(FCreationDateText, ITabbedPropertyConstants.VSPACE);
	    data.bottom = new FormAttachment(100, -ITabbedPropertyConstants.VSPACE); // $codepro.audit.disable numericLiterals
	    FDescriptionText.setLayoutData(data);
	    FDescriptionText.addModifyListener(new ModifyListener() {			 // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
	    	@SuppressWarnings("synthetic-access")
	    	public void modifyText(ModifyEvent e) {
	    		if (!fRefreshInProgress) {
	    			try {
	    				final String currentUser = R4EUIModelController.getReviewer();
						final R4EReview modelReview = ((R4EUIReview)fReviewProps.getElement()).getReview();
	    				final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelReview, currentUser);
	    				modelReview.setExtraNotes(FDescriptionText.getText());
	    				R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	    			} catch (ResourceHandlingException e1) {
	    				UIUtils.displayResourceErrorDialog(e1);
	    			} catch (OutOfSyncException e1) {
	    				UIUtils.displaySyncErrorDialog(e1);
	    			}
	    		}
	    	}
	    });
		
	    final CLabel descriptionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DESCRIPTION_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FDescriptionText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FDescriptionText, 0, SWT.TOP);
	    descriptionLabel.setLayoutData(data);
	}
		
	/**
	 * Method setInput.
	 * @param part IWorkbenchPart
	 * @param aSelection ISelection
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#setInput(IWorkbenchPart, ISelection)
	 */
	@Override
	public void setInput(IWorkbenchPart part, ISelection aSelection) {
		//Get current selection.
		if (null == aSelection || aSelection.isEmpty()) return;
		
		//Get model element selected
		final IR4EUIModelElement element = (IR4EUIModelElement) ((StructuredSelection)aSelection).getFirstElement();
		if (null != element && element instanceof R4EUIReview) {
			fReviewProps = (ReviewProperties) ((R4EUIReview)element).getAdapter(IPropertySource.class);
			refresh();
		}
	}

	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EReview modelReview = ((R4EUIReview)fReviewProps.getElement()).getReview();
		FNameText.setText(modelReview.getName());
		FCreationDateText.setText(modelReview.getCreationDate().toString());
		if (null != modelReview.getExtraNotes()) FDescriptionText.setText(modelReview.getExtraNotes());
		setEnabledFields();
		fRefreshInProgress = false;
	}
	
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
	 * Method propertyChanged.
	 * @param source Object
	 * @param propId int
	 * @see org.eclipse.ui.IPropertyListener#propertyChanged(Object, int)
	 */
	public void propertyChanged(Object source, int propId) {
		setEnabledFields();
	}
	
	/**
	 * Method setEnabledFields.
	 */
	private void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen() || (!((R4EUIReview)fReviewProps.getElement()).isOpen())) {
			FNameText.setEnabled(false);
			FDescriptionText.setEnabled(false);
		} else {
			FNameText.setEnabled(true);
			FDescriptionText.setEnabled(true);
		}
	}
}
