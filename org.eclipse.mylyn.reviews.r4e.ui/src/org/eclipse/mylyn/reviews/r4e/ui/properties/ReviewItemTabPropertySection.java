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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
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
public class ReviewItemTabPropertySection extends AbstractPropertySection {
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fItem.
	 */
	private ReviewItemProperties fItemProps;
	
	/**
	 * Field FAuthorText.
	 */
	protected static Text FAuthorText = null;
	
	/**
	 * Field FProjectIdList.
	 */
	protected static List FProjectIdList = null;
	
	/**
	 * Field FDescriptionText.
	 */
	protected static Text FDescriptionText = null;

	
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
		super.createControls(parent, aTabbedPropertySheetPage);

		//Tell element to build its own detailed tab layout
		final TabbedPropertySheetWidgetFactory widgetFactory = aTabbedPropertySheetPage.getWidgetFactory();
		final Composite composite = widgetFactory.createFlatFormComposite(parent);
	    FormData data = null;
	   
	    //Author (read-only)
	    FAuthorText = widgetFactory.createText(composite, "");
	    FAuthorText.setEditable(false);
	    FAuthorText.setEnabled(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
	    FAuthorText.setLayoutData(data);


	    final CLabel authorLabel = widgetFactory.createCLabel(composite, R4EUIConstants.AUTHOR_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FAuthorText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FAuthorText, 0, SWT.CENTER);
	    authorLabel.setLayoutData(data);
	    
	    //ProjectId (read-only)
	    FProjectIdList = widgetFactory.createList(composite, SWT.NONE);
	    FProjectIdList.setEnabled(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(FAuthorText, ITabbedPropertyConstants.VSPACE);
	    FProjectIdList.setLayoutData(data);


	    final CLabel projectIdLabel = widgetFactory.createCLabel(composite, R4EUIConstants.PROJECT_ID_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FProjectIdList, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FProjectIdList, 0, SWT.CENTER);
	    projectIdLabel.setLayoutData(data);

	    //Description (read-only)
	    FDescriptionText = widgetFactory.createText(composite, "");
	    FDescriptionText.setEditable(false);
	    FDescriptionText.setEnabled(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(FProjectIdList, ITabbedPropertyConstants.VSPACE);
	    FDescriptionText.setLayoutData(data);

	    final CLabel descriptionLabel = widgetFactory.createCLabel(composite, R4EUIConstants.DESCRIPTION_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FDescriptionText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FDescriptionText, 0, SWT.CENTER);
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
		if (null != element && element instanceof R4EUIReviewItem) {
			fItemProps = (ReviewItemProperties) ((R4EUIReviewItem)element).getAdapter(IPropertySource.class);
			refresh();
		}
	}

	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
			final R4EItem modelItem = ((R4EUIReviewItem)fItemProps.getElement()).getItem();
			FAuthorText.setText(modelItem.getAddedById());
			FProjectIdList.setItems((String[]) modelItem.getProjectURIs().toArray());
			FDescriptionText.setText(modelItem.getDescription());
	}
}
