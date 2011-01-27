// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the action group for the Review Navigator View.  
 * The action group handles all the actions that can be used on the view
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.filters.AnomaliesOnlyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.CurrentReviewFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewedItemsFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewsOnlyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.actions.ActionGroup;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorActionGroup extends ActionGroup {
	
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field REVIEW_ONLY_FILTER_NAME.
	 * (value is ""Reviews only"")
	 */
	private static final String REVIEW_ONLY_FILTER_NAME = "Show reviews only";
	
	/**
	 * Field REVIEW_CURRENT_FILTER_NAME.
	 * (value is ""Current review(s) only"")
	 */
	private static final String REVIEW_CURRENT_FILTER_NAME = "Show Current review";
	
	/**
	 * Field REVIEW_MY_FILTER_NAME.
	 * (value is ""My reviews"")
	 */
	private static final String REVIEW_MY_FILTER_NAME = "Show my reviews";
	
	/**
	 * Field REVIEW_PARTICIPANT_FILTER_NAME.
	 * (value is ""Reviews for participant"")
	 */
	private static final String REVIEW_PARTICIPANT_FILTER_NAME = "Show reviews for participant... ";
	
	/**
	 * Field REMOVE_ALL_FILTER_NAME.
	 * (value is ""Remove all filters"")
	 */
	private static final String REMOVE_ALL_FILTER_NAME = "Remove all filters";
	
	/**
	 * Field APLHA_REVIEW_SORTER_NAME.
	 * (value is ""Review name"")
	 */
	private static final String APLHA_REVIEW_SORTER_NAME = "element name";
	
	/**
	 * Field ANOMALIES_FILTER_NAME.
	 * (value is ""Hide selections"")
	 */
	private static final String ANOMALIES_FILTER_NAME = "Hide selections";
	
	/**
	 * Field REVIEWED_ITEMS_FILTER_NAME.
	 * (value is ""Hide reviewed elements"")
	 */
	private static final String REVIEWED_ITEMS_FILTER_NAME = "Hide reviewed elements";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fView.
	 */
	private ReviewNavigatorView fView = null;
	
	
	//Actions (Note: Specific actions are defined in the model element classes themselves)
	
	/**
	 * Field fMenuExpandAllTreeAction.
	 */
	private final ExpandAllTreeAction fMenuExpandAllTreeAction;
	
	/**
	 * Field fMenuCollapseAllTreeAction.
	 */
	private final CollapseAllTreeAction fMenuCollapseAllTreeAction;
	
	/**
	 * Field fRemoveAllFiltersAction.
	 */
	private final RemoveAllFiltersAction fRemoveAllFiltersAction;
	
	/**
	 * Field fLinkWithEditorAction.
	 */
	private final LinkWithEditorAction fLinkWithEditorAction;
	
	
	//Filters
	
	/**
	 * Field fReviewOnlyFilterAction.
	 */
	private final DisplayCurrentReviewAction fReviewCurrentFilterAction;
	
	/**
	 * Field fReviewOpenFilterAction.
	 */
	private final ReviewNavigatorFilterAction fReviewsOnlyFilterAction;
	
	/**
	 * Field fReviewMyFilterAction.
	 */
	private final ParticipantFilterAction fReviewMyFilterAction;
	
	/**
	 * Field fReviewParticipantFilterAction.
	 */
	private final ParticipantFilterAction fReviewParticipantFilterAction;
	
	/**
	 * Field fAnomaliesFilterAction.
	 */
	private final ReviewNavigatorFilterAction fAnomaliesFilterAction;
	
	/**
	 * Field fReviewedItemsFilterAction.
	 */
	private final ReviewNavigatorFilterAction fReviewedItemsFilterAction;
	
	//Sorters
	
	/**
	 * Field fAlphaReviewSorterAction.
	 */
	private final DefaultSorterAction fAlphaReviewSorterAction;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * @param aView - the review navigator view
	 */
	public ReviewNavigatorActionGroup(ReviewNavigatorView aView) {
		super();
	    Activator.Tracer.traceInfo("Create Action Group for view " + aView.getPartName());
		fView = aView;
		fMenuExpandAllTreeAction = new ExpandAllTreeAction(aView);
		fMenuCollapseAllTreeAction = new CollapseAllTreeAction(aView);
		fLinkWithEditorAction = new LinkWithEditorAction(aView);
		
		fReviewsOnlyFilterAction = new ReviewNavigatorFilterAction(aView, new ReviewsOnlyFilter(), REVIEW_ONLY_FILTER_NAME);
		fReviewCurrentFilterAction = new DisplayCurrentReviewAction(aView, new CurrentReviewFilter(), REVIEW_CURRENT_FILTER_NAME);
		final ReviewParticipantFilter filter = new ReviewParticipantFilter();
		filter.setParticipant(R4EUIModelController.getReviewer());
		fReviewMyFilterAction = new ParticipantFilterAction(aView, filter, REVIEW_MY_FILTER_NAME);
		fReviewParticipantFilterAction = new ParticipantFilterAction(aView, new ReviewParticipantFilter(), REVIEW_PARTICIPANT_FILTER_NAME);
		fAnomaliesFilterAction = new ReviewNavigatorFilterAction(aView, new AnomaliesOnlyFilter(), ANOMALIES_FILTER_NAME);
		fReviewedItemsFilterAction = new ReviewNavigatorFilterAction(aView, new ReviewedItemsFilter(), REVIEWED_ITEMS_FILTER_NAME);
		fRemoveAllFiltersAction = new RemoveAllFiltersAction(aView, REMOVE_ALL_FILTER_NAME);
		
		fAlphaReviewSorterAction = new DefaultSorterAction(aView, new NavigatorElementComparator(), APLHA_REVIEW_SORTER_NAME);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method fillActionBars.
	 * @param aActionBars IActionBars
	 */
	@Override
	public void fillActionBars(IActionBars aActionBars) {
		super.fillActionBars(aActionBars);
		fillToolBar(aActionBars.getToolBarManager());
		fillViewMenu(aActionBars.getMenuManager());
	}
	
	/**
	 * Method fillContextMenu.
	 * @param aMenu IMenuManager
	 */
	@Override
	public void fillContextMenu(IMenuManager aMenu) {
		
		//Set Context Menu based on selection
		final IStructuredSelection selection = (IStructuredSelection)getContext().getSelection();
		if (null == selection) return;
		
		final Object element  = selection.getFirstElement();
		if (0 == selection.size()) return;
		
		//All selected element must be of the same type to be acted upon
		if (selection.size() > 1 && !element.getClass().equals(selection.toArray()[1].getClass())) {
			return;
		}
		
		final List<IAction> actions = ((IR4EUIModelElement)element).getActions(fView);
		final int numActions = actions.size();
		for (int i = 0; i < numActions; i++) {
			aMenu.add(actions.get(i));
		}
		
		aMenu.add(new Separator("other"));
		super.fillContextMenu(aMenu);
	}

	/**
	 * Add actions to the view's toolbar
	 * @param toolBar - the view's toolbar
	 */
	private void fillToolBar(IToolBarManager toolBar) {
		toolBar.add((Action)R4EUIModelController.getRootElement().getActions(fView).toArray()[0]);
		toolBar.add(fAlphaReviewSorterAction);
		toolBar.add(fMenuExpandAllTreeAction);
		toolBar.add(fMenuCollapseAllTreeAction);
		toolBar.add(fLinkWithEditorAction);
		toolBar.update(true);
	}

	/**
	 * Add actions to the view's menu
	 * @param rootMenu - the view"s root menu
	 */
	private void fillViewMenu(IMenuManager rootMenu) {
		
		final IMenuManager filterSubmenu = new MenuManager("Filters");
		rootMenu.add(filterSubmenu);
		filterSubmenu.add(fReviewCurrentFilterAction);
		filterSubmenu.add(fReviewsOnlyFilterAction);
		filterSubmenu.add(fReviewMyFilterAction);
		filterSubmenu.add(fReviewParticipantFilterAction);
		filterSubmenu.add(fAnomaliesFilterAction);
		filterSubmenu.add(fReviewedItemsFilterAction);
		filterSubmenu.add(new Separator());
		filterSubmenu.add(fRemoveAllFiltersAction);
		
		final IMenuManager sorterSubmenu = new MenuManager("Sort by");
		rootMenu.add(sorterSubmenu);
		sorterSubmenu.add(fAlphaReviewSorterAction);
		
		rootMenu.add((Action)R4EUIModelController.getRootElement().getActions(fView).toArray()[0]);
		rootMenu.add(fMenuExpandAllTreeAction);
		rootMenu.add(fMenuCollapseAllTreeAction);
		rootMenu.add(fLinkWithEditorAction);
	}
	
	/**
	 * Remove all the currently applied filters
	 */
	public void resetAllFilterActions() {
		fReviewCurrentFilterAction.reset();
		fReviewsOnlyFilterAction.reset();
		fReviewMyFilterAction.reset();
		fReviewParticipantFilterAction.reset();
		fAnomaliesFilterAction.reset();
		fReviewedItemsFilterAction.reset();
	}
	
	/**
	 * Method dialogOpenNotify.
	 */
	public void dialogOpenNotify() {
		//A dialog is open, refresh menus accordingly
		final IToolBarManager toolbar = fView.getViewSite().  getActionBars().getToolBarManager();
		final IContributionItem[] items = toolbar.getItems();
		for (IContributionItem item : items) {
			if (item instanceof ActionContributionItem) {
				if (R4EUIModelController.isDialogOpen()) {
					((ActionContributionItem)item).getAction().setEnabled(false);
				} else {
					((ActionContributionItem)item).getAction().setEnabled(true);
				}
			}
		}		
	}
}
