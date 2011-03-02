package org.eclipse.mylyn.reviews.r4e.ui.properties.tabbed;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertyConstants;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class ReviewExtraTabPropertySection extends ModelElementTabPropertySection {

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
	    Composite mainForm = widgetFactory.createFlatFormComposite(parent);

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
	}
	
	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		final R4EReview modelReview = ((R4EUIReview)fProperties.getElement()).getReview();
		fProjectText.setText(modelReview.getProject());
		String[] components = (String[]) modelReview.getComponents().toArray();
		fComponents.clearAll();
		for (int i = 0; i < components.length; i++) {
			String component  = components[i];
			TableItem item;
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
		setEnabledFields();
		fRefreshInProgress = false;
	}
	
	/**
	 * Method setEnabledFields.
	 */
	@Override
	protected void setEnabledFields() {
		if (R4EUIModelController.isDialogOpen() || (!((R4EUIReview)fProperties.getElement()).isOpen()) ||
				((R4EUIReview)fProperties.getElement()).isReviewed()) {
			fProjectText.setEnabled(false);
			fComponents.setEnabled(false);
			fEntryCriteriaText.setEnabled(false);
			fObjectivesText.setEnabled(false);
			fReferenceMaterialText.setEnabled(false);
		} else {
			fProjectText.setEnabled(true);
			fComponents.setEnabled(true);
			fEntryCriteriaText.setEnabled(true);
			fObjectivesText.setEnabled(true);
			fReferenceMaterialText.setEnabled(true);
		}
	}
}
