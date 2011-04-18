// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the tabbed property section for the File Context model 
 * element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.tabbed;

import org.eclipse.core.resources.IResource;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FileContextTabPropertySection extends ModelElementTabPropertySection {
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field FBaseFileNameText.
	 */
	protected CLabel fBaseFileNameText = null;
	
	/**
	 * Field FBaseFilePathText.
	 */
	protected CLabel fBaseFilePathText = null;
	
	/**
	 * Field FBaseFileVersionText.
	 */
	protected CLabel fBaseFileVersionText = null;
	
	/**
	 * Field FTargetFileNameText.
	 */
	protected CLabel fTargetFileNameText = null;
	
	/**
	 * Field FTargetFilePathText.
	 */
	protected CLabel fTargetFilePathText = null;
	
	/**
	 * Field FTargetFileVersionText.
	 */
	protected CLabel fTargetFileVersionText = null;
	
	
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
	    
	    //Target File Version composite (read-only)
		final Composite targetFileComposite = widgetFactory.createGroup(composite, "Target File");
		final FormLayout targetFileLayout = new FormLayout();
		targetFileComposite.setLayout(targetFileLayout);
		createTargetFileVersionComposite(targetFileComposite, widgetFactory);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(0, ITabbedPropertyConstants.VSPACE);
	    targetFileComposite.setLayoutData(data);
	    
	    //Base File Version composite (read-only)
		final Group baseFileComposite = widgetFactory.createGroup(composite, "Base File");
		final FormLayout baseFileLayout = new FormLayout();
		baseFileComposite.setLayout(baseFileLayout);
		createBaseFileVersionComposite(baseFileComposite, widgetFactory);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(targetFileComposite, ITabbedPropertyConstants.VSPACE);
	    baseFileComposite.setLayoutData(data); 
	}
		
	
	/**
	 * Method createBaseFileVersionComposite.
	 * @param aParent Composite
	 * @param aWidgetFactory TabbedPropertySheetWidgetFactory
	 */
	private void createBaseFileVersionComposite(Composite aParent, TabbedPropertySheetWidgetFactory aWidgetFactory) {
	    FormData data = null;

	    //File Name (read-only)
	    fBaseFileNameText = aWidgetFactory.createCLabel(aParent, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(aParent, ITabbedPropertyConstants.VSPACE);
	    fBaseFileNameText.setLayoutData(data);
	
	    final CLabel fileNameLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.NAME_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fBaseFileNameText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fBaseFileNameText, 0, SWT.CENTER);
	    fileNameLabel.setLayoutData(data);
	
	    //File Path (read-only)
	    fBaseFilePathText = aWidgetFactory.createCLabel(aParent, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fBaseFileNameText, ITabbedPropertyConstants.VSPACE);
	    fBaseFilePathText.setLayoutData(data);

	    final CLabel filePathLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.PATH_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fBaseFilePathText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fBaseFilePathText, 0, SWT.TOP);
	    filePathLabel.setLayoutData(data);
	    
	    //File Version (read-only)
	    fBaseFileVersionText = aWidgetFactory.createCLabel(aParent, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fBaseFilePathText, ITabbedPropertyConstants.VSPACE);
	    fBaseFileVersionText.setLayoutData(data);

	    final CLabel fileVersionLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.VERSION_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fBaseFileVersionText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fBaseFileVersionText, 0, SWT.TOP);
	    fileVersionLabel.setLayoutData(data);
	}
	
	/**
	 * Method createTargetFileVersionComposite.
	 * @param aParent Composite
	 * @param aWidgetFactory TabbedPropertySheetWidgetFactory
	 */
	private void createTargetFileVersionComposite(Composite aParent, TabbedPropertySheetWidgetFactory aWidgetFactory) {
	    FormData data = null;

	    //File Name (read-only)
	    fTargetFileNameText = aWidgetFactory.createCLabel(aParent, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(aParent, ITabbedPropertyConstants.VSPACE);
	    fTargetFileNameText.setLayoutData(data);
	
	    final CLabel fileNameLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.NAME_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fTargetFileNameText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fTargetFileNameText, 0, SWT.CENTER);
	    fileNameLabel.setLayoutData(data);
	
	    //File Path (read-only)
	    fTargetFilePathText = aWidgetFactory.createCLabel(aParent, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fTargetFileNameText, ITabbedPropertyConstants.VSPACE);
	    fTargetFilePathText.setLayoutData(data);

	    final CLabel filePathLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.PATH_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fTargetFilePathText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fTargetFilePathText, 0, SWT.TOP);
	    filePathLabel.setLayoutData(data);
	    
	    //File Version (read-only)
	    fTargetFileVersionText = aWidgetFactory.createCLabel(aParent, "");
	    data = new FormData();
	    data.left = new FormAttachment(0, R4EUIConstants.TABBED_PROPERTY_LABEL_WIDTH);
	    data.right = new FormAttachment(100, 0); // $codepro.audit.disable numericLiterals
	    data.top = new FormAttachment(fTargetFilePathText, ITabbedPropertyConstants.VSPACE);
	    fTargetFileVersionText.setLayoutData(data);

	    final CLabel fileVersionLabel = aWidgetFactory.createCLabel(aParent, R4EUIConstants.VERSION_LABEL);
	    data = new FormData();
	    data.left = new FormAttachment(0, 0);
	    data.right = new FormAttachment(fTargetFileVersionText, -ITabbedPropertyConstants.HSPACE);
	    data.top = new FormAttachment(fTargetFileVersionText, 0, SWT.TOP);
	    fileVersionLabel.setLayoutData(data);
	}

	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EFileContext modelFile = ((R4EUIFileContext)fProperties.getElement()).getFileContext();
		final R4EFileVersion baseVersion = modelFile.getBase();
		if (null != modelFile.getBase()) {
			fBaseFileNameText.setText(baseVersion.getName());
			final IResource baseResource = baseVersion.getResource();
			//The properties shows the absolute path
			if (null != baseResource) {
				fBaseFilePathText.setText(baseResource.getLocation().toOSString());
			} else {
				fBaseFilePathText.setText("");
			}
			fBaseFileVersionText.setText(baseVersion.getVersionID());
		} else {
			fBaseFileNameText.setText(R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE);
			fBaseFilePathText.setText("");
			fBaseFileVersionText.setText("");
		}

		final R4EFileVersion targetVersion = modelFile.getTarget();
		fTargetFileNameText.setText(targetVersion.getName());
		final IResource targetResource = targetVersion.getResource();
		//The properties shows the absolute path
		if (null != targetResource) {
			fTargetFilePathText.setText(targetResource.getLocation().toOSString());
		} else {
			fTargetFilePathText.setText("");
		}
		fTargetFileVersionText.setText(targetVersion.getVersionID());
		setEnabledFields();
		fRefreshInProgress = false;
	}
	
	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen()) {
			fBaseFileNameText.setEnabled(false);
			fBaseFilePathText.setEnabled(false);
			fBaseFileVersionText.setEnabled(false);
			fTargetFileNameText.setEnabled(false);
			fTargetFilePathText.setEnabled(false);
			fTargetFileVersionText.setEnabled(false);
		} else {
			fBaseFileNameText.setEnabled(true);
			fBaseFilePathText.setEnabled(true);
			fBaseFileVersionText.setEnabled(true);
			fTargetFileNameText.setEnabled(true);
			fTargetFilePathText.setEnabled(true);
			fTargetFileVersionText.setEnabled(true);
		}
	}
}
