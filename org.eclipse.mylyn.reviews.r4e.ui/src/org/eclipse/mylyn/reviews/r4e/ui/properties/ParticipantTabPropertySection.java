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

package org.eclipse.mylyn.reviews.r4e.ui.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ParticipantTabPropertySection extends AbstractPropertySection {
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fParticipant.
	 */
	private R4EUIParticipant fParticipant;
	
	//Properties
	/**
	 * Field FIdText.
	 */
	protected static Text FIdText = null;
	
	/**
	 * Field FNumCommentsText.
	 */
	protected static Text FNumCommentsText = null;
	
	
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
	    FIdText = widgetFactory.createText(composite, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
	    FIdText.setLayoutData(data);
	    FIdText.setEditable(false);

	    final CLabel idLabel = widgetFactory.createCLabel(composite, R4EUIConstants.ID_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FIdText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FIdText, 0, SWT.CENTER);
	    idLabel.setLayoutData(data);
	    
	    //Number of Comments (read-only)
	    FNumCommentsText = widgetFactory.createText(composite, "");
	    FNumCommentsText.setEditable(false);
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(FIdText, ITabbedPropertyConstants.VSPACE);
	    FNumCommentsText.setLayoutData(data);
	
	    final CLabel numCommentsLabel = widgetFactory.createCLabel(composite, R4EUIConstants.NUM_COMMENTS_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(FNumCommentsText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(FNumCommentsText, 0, SWT.CENTER);
	    numCommentsLabel.setLayoutData(data);
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
		if (null != element && element instanceof R4EUIParticipant) {
			fParticipant = (R4EUIParticipant)element;
			refresh();
		}
	}

	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		final R4EParticipant modelUser = fParticipant.getParticipant();
		FIdText.setText(modelUser.getId());
		FNumCommentsText.setText(Integer.valueOf(modelUser.getAddedComments().size()).toString());
	}
}
