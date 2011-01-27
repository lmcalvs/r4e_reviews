// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the Review Navigator View.  It is used to quickly browse the current
 * R4E elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.navigator;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DecoratingCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.editors.EditorProxy;
import org.eclipse.mylyn.reviews.r4e.ui.filters.LinePositionComparator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorView extends ViewPart implements IMenuListener, IPreferenceChangeListener,
 ITabbedPropertySheetPageContributor, IPropertyListener {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field reviewTreeViewer.
	 */
	private ReviewNavigatorTreeViewer reviewTreeViewer = null;
	
	/**
	 * Field fContextMenu.
	 */
	private Menu fContextMenu;
	
	/**
	 * Field fActionSet.
	 */
	private ActionGroup fActionSet;
	
	/**
	 * Field fEditorLinked - this is set if the editor is linked to the review Navigator view
	 */
	private boolean fEditorLinked;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ReviewNavigatorView.
	 */
	public ReviewNavigatorView() {
		R4EUIModelController.setNavigatorView(this);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method setEditorLinked.
	 * @param aEditorLinked booelan - true is the editor is linked, false otherwise
	 */
	public void setEditorLinked(boolean aEditorLinked) {
		fEditorLinked = aEditorLinked;
	}
	
	/**
	 * Method isEditorLinked.
	
	 * @return - true is the editor is linked, false otherwise */
	public boolean isEditorLinked() {
		return fEditorLinked;
	}
	
	/**
	 * Method dispose.
	 * @see org.eclipse.ui.IWorkbenchPart#dispose()
	 */
	@Override
	public void dispose() {
		if (null != fContextMenu && !fContextMenu.isDisposed()) fContextMenu.dispose();
		if (null != fActionSet)	fActionSet.dispose();
		super.dispose();
	}
	 
	/**
	 * Method createPartControl.
	 * @param parent Composite
	 * @see org.eclipse.ui.IWorkbenchPart#createPartControl(Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
	    Activator.Tracer.traceInfo("Build Review Navigator view");
	    
		//Set tree viewer
		reviewTreeViewer = new ReviewNavigatorTreeViewer(parent, SWT.MULTI);
		reviewTreeViewer.setUseHashlookup(true);
		reviewTreeViewer.getTree().setHeaderVisible(true);
		ColumnViewerToolTipSupport.enableFor(reviewTreeViewer);
		reviewTreeViewer.setContentProvider(new ReviewNavigatorContentProvider());
		
		final DecoratingCellLabelProvider provider = new DecoratingCellLabelProvider(new ReviewNavigatorLabelProvider(), new ReviewNavigatorDecorator());
		reviewTreeViewer.setLabelProvider(provider);
		reviewTreeViewer.setComparator(new LinePositionComparator());
		reviewTreeViewer.setInput(getInitalInput());
		
		//Set Context menus
		final MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(this);
		fContextMenu = menuMgr.createContextMenu(reviewTreeViewer.getTree());
		reviewTreeViewer.getTree().setMenu(fContextMenu);
		
		// Register viewer with site. This must be done before making the actions.
		final IWorkbenchPartSite site = getSite();
		site.registerContextMenu(menuMgr, reviewTreeViewer);
		site.setSelectionProvider(reviewTreeViewer);
		
		//Set Actions in Action group
		makeActions(); // call before registering for selection changes
		
		//Tie UI listeners
		hookListeners();
		final IEclipsePreferences node = new InstanceScope().getNode(Activator.PLUGIN_ID);
		node.addPreferenceChangeListener(this);
		R4EUIModelController.addDialogStateListener(this);
	}

	/**
	 * Method resetInput.
	 */
	public void resetInput() {
		reviewTreeViewer.setInput(getInitalInput());
	}

	/**
	 * Method setFocus.
	 * @see org.eclipse.ui.IWorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		reviewTreeViewer.getControl().setFocus();
	}
	
	/**
	 * Method menuAboutToShow.
	 * @param aMenuManager IMenuManager
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(IMenuManager)
	 */
	@Override
	public void menuAboutToShow(IMenuManager aMenuManager) {
		fActionSet.setContext(new ActionContext(reviewTreeViewer.getSelection()));
		fActionSet.fillContextMenu(aMenuManager);
		fActionSet.setContext(null);
	}
	
	
	/**
	 * Get the view's jface tree viever
	 * @return the tree viewer
	 */
	public TreeViewer getTreeViewer() {
		return reviewTreeViewer;
	}
	
	
	/**
	 * Get the initial input from the R4E model and populate the UI model with it
	 * @return the root element of the UI model
	 */
	public IR4EUIModelElement getInitalInput() {
		R4EUIModelController.loadModel();
		final IR4EUIModelElement rootTreeNode = R4EUIModelController.getRootElement();
		rootTreeNode.getChildren();
		return rootTreeNode;
	}
	
	/**
	 * Add the listeners to the tree viewer
	 */
	protected void hookListeners() {
		reviewTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(event.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					R4EUIModelController.selectionChanged(selection);
				}
			}
		});
		
		reviewTreeViewer.addDoubleClickListener(new IDoubleClickListener() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			@Override
			public void doubleClick(DoubleClickEvent event) {
			    Activator.Tracer.traceInfo("Double-click event received");

				final IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				final IR4EUIModelElement element = (IR4EUIModelElement) selection.getFirstElement();
				if (element instanceof R4EUIReview) {
					//open or close review if ReviewElement is double-clicked
					if (element.isOpen()) {
						((R4EUIReview)element).getCloseReviewAction().run();
					} else {
						((R4EUIReview)element).getOpenReviewAction().run();
					}
				} else if (element instanceof R4EUIReviewGroup) {
					//open or close review group if ReviewGroupElement is double-clicked
					if (element.isOpen()) {
						((R4EUIReviewGroup)element).getCloseReviewAction().run();
					} else {
						((R4EUIReviewGroup)element).getOpenReviewAction().run();
					}
				} else if (isEditorLinked()) {
					EditorProxy.openEditor(getSite().getPage(), selection, false);
				}
			}
		});  
	}
	
	/**
	 * Create the Action Group associated with the view
	 */
	private void makeActions() {
		fActionSet = new ReviewNavigatorActionGroup(this);
		final IActionBars actionBars = getViewSite().getActionBars();
		fActionSet.fillActionBars(actionBars);
	}
	
	/**
	 * Gets the Action Group
	 * @return the action group
	 */
	public ActionGroup getActionSet() {
		return fActionSet;
	}


	/**
	 * Method preferenceChange.
	 * @param event PreferenceChangeEvent
	 * @see org.eclipse.core.runtime.preferences.IEclipsePreferences$IPreferenceChangeListener#preferenceChange(PreferenceChangeEvent)
	 */
	@Override
	public void preferenceChange(PreferenceChangeEvent event) {
		//Reload everything from model when preferences are refreshed
		R4EUIModelController.getRootElement().close();
		resetInput();
	}

	
	/**
	 * Method getAdapter.
	 * @param adapter Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (adapter.equals(IPropertySheetPage.class))  {
			return new TabbedPropertySheetPage(this);
		}
		return super.getAdapter(adapter);
	}
	
	/**
	 * Method getContributorId.
	 * @return String
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor#getContributorId()
	 */
	@Override
	public String getContributorId() {
		return getSite().getId();
	}


	/**
	 * Method propertyChanged.
	 * @param source Object
	 * @param propId int
	 * @see org.eclipse.ui.IPropertyListener#propertyChanged(Object, int)
	 */
	@Override
	public void propertyChanged(Object source, int propId) {
		((ReviewNavigatorActionGroup) fActionSet).dialogOpenNotify();
	}
	
}
