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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import org.eclipse.core.runtime.Path;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DecoratingCellLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.editors.EditorProxy;
import org.eclipse.mylyn.reviews.r4e.ui.filters.LinePositionComparator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
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
	
	/**
	 * Field fPropertiesLinked - this is set if the R4E properties view is linked to the review Navigator view
	 */
	private boolean fPropertiesLinked;
	
	
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
	 * @return - true is the editor is linked, false otherwise
	 */
	public boolean isEditorLinked() {
		return fEditorLinked;
	}
	
	/**
	 * Method setPropertiesLinked.
	 * @param aPropertiesLinked booelan - true is the R4E properties view is linked, false otherwise
	 */
	public void setPropertiesLinked(boolean aPropertiesLinked) {
		fPropertiesLinked = aPropertiesLinked;
	}
	
	/**
	 * Method isPropertiesLinked.
	 * @return - true is the R4E properties view is linked, false otherwise
	 */
	public boolean isPropertiesLinked() {
		return fPropertiesLinked;
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
	    Activator.Ftracer.traceInfo("Build Review Navigator view");
	    
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
		
        //Apply default filters
        applyDefaultFilters();
	}

	/**
	 * Method resetInput.
	 */
	public void resetInput() {
		R4EUIReviewGroup[] groups = (R4EUIReviewGroup[]) R4EUIModelController.getRootElement().getChildren();
		List<String> openGroupNames = new ArrayList<String>();
		for (R4EUIReviewGroup group : groups) {
			if (group.isOpen()) {
				group.close();
				openGroupNames.add(group.getName());
			}
		}
		R4EUIModelController.setActiveReview(null);
		reviewTreeViewer.setInput(getInitalInput());
		
		//Restore previously open groups
		groups = (R4EUIReviewGroup[]) R4EUIModelController.getRootElement().getChildren();
		for (String groupName : openGroupNames) {
			for (R4EUIReviewGroup group : groups) {
				if (group.getName().equals(groupName)) {
					try {
						group.open();
					} catch (ResourceHandlingException e) {
						UIUtils.displayResourceErrorDialog(e);
					}
					break;
				}
			}
		}
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
			public void selectionChanged(SelectionChangedEvent event) {
				if(event.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection selection = (IStructuredSelection)event.getSelection();
					R4EUIModelController.selectionChanged(selection);
					if (isPropertiesLinked()) {
						try {
							final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
							final IViewPart propertiesView = page.findView(R4EUIConstants.R4E_PROPERTIES_VIEW_NAME);
							if (!page.isPartVisible(propertiesView)) {
								page.showView(R4EUIConstants.R4E_PROPERTIES_VIEW_NAME);
							}
						} catch (PartInitException e) {
							Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
							Activator.getDefault().logError("Exception: " + e.toString(), e);
						}
					}
				}
			}
		});
		
		reviewTreeViewer.addDoubleClickListener(new IDoubleClickListener() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.avoidInnerClasses
			public void doubleClick(DoubleClickEvent event) {
				Activator.Ftracer.traceInfo("Double-click event received");

				final IStructuredSelection selection = (IStructuredSelection)event.getSelection();
				final IR4EUIModelElement element = (IR4EUIModelElement) selection.getFirstElement();
				if (element instanceof R4EUIReview || element instanceof R4EUIReviewGroup) {
					try {
						//open or close review if ReviewElement is double-clicked
						if (element.isEnabled()) {
							if (element.isOpen()) {
								((ReviewNavigatorActionGroup) getActionSet()).closeElementCommand();
							} else {
								((ReviewNavigatorActionGroup) getActionSet()).openElementCommand();
							}
						}
					} catch (ExecutionException e) {
						Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						Activator.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotDefinedException e) {
						Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						Activator.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotEnabledException e) {
						Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						Activator.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotHandledException e) {
						Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						Activator.getDefault().logError("Exception: " + e.toString(), e);
					}
				} else if (isEditorLinked()) {
					EditorProxy.openEditor(getSite().getPage(), selection, false);
				}
			}
		});  
	}
	
	
	/**
	 * Method showProperties.
	 * @param aSelection ISelection
	 */
	protected void showProperties(ISelection aSelection) {
		//Force show properties view
		try {
			final IViewPart propertiesView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView("org.eclipse.ui.views.PropertySheet");
			((PropertySheet) propertiesView).selectionChanged(getSite().getPart(), aSelection);
		} catch (PartInitException e) {
			Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logWarning("Exception: " + e.toString(), e);
			// Do nothing
		}
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
	public void preferenceChange(PreferenceChangeEvent event) {
		
		if (event.getKey().equals(PreferenceConstants.P_USER_ID)) {
			//Reset reviewer to current ID
			final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			R4EUIModelController.setReviewer(store.getString(PreferenceConstants.P_USER_ID));
			
		} else if (event.getKey().equals(PreferenceConstants.P_FILE_PATH)) {
			//Check what is currently loaded vs. what is in the preferences.  Adjust input accordingly
			final List<IR4EUIModelElement> groupsLoaded = Arrays.asList(R4EUIModelController.getRootElement().getChildren());
			final List<String> groupsPreferencesPaths = UIUtils.parseStringList((String) event.getNewValue());

			//Convert the loaded groups array to array of File Paths
			final List<String> groupsLoadedPaths = new ArrayList<String>();
			for (IR4EUIModelElement group : groupsLoaded) {
				groupsLoadedPaths.add(new File(((R4EUIReviewGroup)group).getGroup().getFolder() + File.separator + 
						group.getName() + R4EUIConstants.GROUP_FILE_SUFFIX).getPath());
			}
			
			//Groups that are in preferences, but not loaded should be loaded
			final List<String> result = new ArrayList<String>(groupsPreferencesPaths);
		    result.removeAll(groupsLoadedPaths);
		    R4EUIModelController.loadReviewGroups(result);
		    
			//Groups that are loaded, but not in preferences should be removed from the UI model
		    result.clear();
		    result.addAll(groupsLoadedPaths);
		    result.removeAll(groupsPreferencesPaths);
		    final List<IR4EUIModelElement> groupsToRemove = new ArrayList<IR4EUIModelElement>();
			for (IR4EUIModelElement group : groupsLoaded) {				
				for (String groupPath : result) {
					if (groupPath.equals(new File(((R4EUIReviewGroup)group).getGroup().getFolder() + File.separator + 
							group.getName() + R4EUIConstants.GROUP_FILE_SUFFIX).getPath())) {
						groupsToRemove.add(group);
					}
				}
			}
			for (IR4EUIModelElement groupToRemove : groupsToRemove) {
				try {
					R4EUIModelController.getRootElement().removeChildren(groupToRemove, false);
				} catch (ResourceHandlingException e) {
					UIUtils.displayResourceErrorDialog(e);
				} catch (OutOfSyncException e) {
					UIUtils.displaySyncErrorDialog(e);
				}
			}
		} else if (event.getKey().equals(PreferenceConstants.P_SHOW_DISABLED)) {
			resetInput();
		}

	}

	/**
	 * Method applyDefaultFilters.
	 */
	public void applyDefaultFilters() {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();	
		try {
			((ReviewNavigatorActionGroup) fActionSet).resetAllFilterActions();
			((ReviewNavigatorActionGroup) fActionSet).runReviewCurrentFilterCommand(
					store.getBoolean(PreferenceConstants.P_REVIEWS_CURRENT_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewsOnlyFilterCommand(
					store.getBoolean(PreferenceConstants.P_REVIEWS_ONLY_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewsMyFilterCommand(
					store.getBoolean(PreferenceConstants.P_REVIEWS_MY_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewsParticipantFilterCommand(
					store.getString(PreferenceConstants.P_PARTICIPANT_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runAnomaliesFilterCommand(
					store.getBoolean(PreferenceConstants.P_ANOMALIES_FILTER));
			((ReviewNavigatorActionGroup) fActionSet).runReviewElemsFilterCommand(
					store.getBoolean(PreferenceConstants.P_REVIEWED_ITEMS_FILTER));
		} catch (ExecutionException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (NotDefinedException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (NotEnabledException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		} catch (NotHandledException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
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
	public String getContributorId() {
		return getSite().getId();
	}


	/**
	 * Method propertyChanged.
	 * @param source Object
	 * @param propId int
	 * @see org.eclipse.ui.IPropertyListener#propertyChanged(Object, int)
	 */
	public void propertyChanged(Object source, int propId) {
		((ReviewNavigatorActionGroup) fActionSet).dialogOpenNotify();
	}
}
