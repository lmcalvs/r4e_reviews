package org.eclipse.mylyn.reviews.r4e.ui.properties.tabbed;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.ModelElementProperties;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;

public class ModelElementTabPropertySection extends AbstractPropertySection
		implements IPropertyListener {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fReviewProps.
	 */
	protected ModelElementProperties fProperties;
	
	/**
	 * Field fRefreshInProgress.
	 */
	protected boolean fRefreshInProgress = false;
	
	
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
		R4EUIModelController.removeElementStateListener(this);
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
		R4EUIModelController.addElementStateListener(this);
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
		if (null != element) {
			fProperties = (ModelElementProperties) ((R4EUIModelElement)element).getAdapter(IPropertySource.class);
			refresh();
		}
	}

	/**
	 * Method propertyChanged.
	 * @param source Object
	 * @param propId int
	 * @see org.eclipse.ui.IPropertyListener#propertyChanged(Object, int)
	 */
	public void propertyChanged(Object source, int propId) {
		refresh();
	}

	/**
	 * Method refresh.
	 * @see org.eclipse.ui.views.properties.tabbed.ISection#refresh()
	 */
	@Override
	public void refresh() {
		fRefreshInProgress = true;
		//default implementation
		setEnabledFields();
		fRefreshInProgress = false;
	}
	
	/**
	 * Method setEnabledFields.
	 */
	protected void setEnabledFields() {
		//default implementation
	}
}