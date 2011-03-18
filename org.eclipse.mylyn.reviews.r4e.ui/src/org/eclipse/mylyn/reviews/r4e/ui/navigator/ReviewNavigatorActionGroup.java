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
 * The action group handles all the commands that can be used on the view
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.navigator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.filters.AnomaliesOnlyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.CurrentReviewFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewedElemsFilter;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewsOnlyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorActionGroup extends ActionGroup {
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fView.
	 */
	private ReviewNavigatorView fView = null;
	
	/**
	 * Field fCommandService.
	 */
	private final ICommandService fCommandService;
	
	/**
	 * Field fHandlerService.
	 */
	private final IHandlerService fHandlerService;
	
	/**
	 * Field fAlphaReviewSorterAction.
	 */
	private final ViewerComparator fAlphaReviewSorter;
	
	/**
	 * Field fCurrentReviewFilter.
	 */
	private final CurrentReviewFilter fCurrentReviewFilter;
	
	/**
	 * Field fReviewsOnlyFilter.
	 */
	private final ReviewsOnlyFilter fReviewsOnlyFilter;
	
	/**
	 * Field fReviewsMyFilter.
	 */
	private final ReviewParticipantFilter fReviewsMyFilter;
	
	/**
	 * Field fReviewsParticipantFilter.
	 */
	private final ReviewParticipantFilter fReviewsParticipantFilter;
	
	/**
	 * Field fAnomaliesFilter.
	 */
	private final AnomaliesOnlyFilter fAnomaliesFilter;
	
	/**
	 * Field fReviewedElemsFilter.
	 */
	private final ReviewedElemsFilter fReviewedElemsFilter;
	

	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * @param aView - the review navigator view
	 */
	public ReviewNavigatorActionGroup(ReviewNavigatorView aView) {
		super();
	    Activator.Ftracer.traceInfo("Create Action Group for view " + aView.getPartName());
		fView = aView;
		fCommandService = (ICommandService) fView.getSite().getWorkbenchWindow().getService(ICommandService.class);
		fHandlerService = (IHandlerService) fView.getSite().getWorkbenchWindow().getService(IHandlerService.class);
		fView.setEditorLinked(((Boolean) fCommandService.getCommand(
				R4EUIConstants.LINK_EDITOR_COMMAND).getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).getValue()).booleanValue());
		fView.setPropertiesLinked(((Boolean) fCommandService.getCommand(
				R4EUIConstants.LINK_PROPERTIES_COMMAND).getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).getValue()).booleanValue());
		
		fAlphaReviewSorter = new NavigatorElementComparator();
		fCurrentReviewFilter = new CurrentReviewFilter();
		fReviewsOnlyFilter = new ReviewsOnlyFilter();
		fReviewsMyFilter = new ReviewParticipantFilter();
		fReviewsParticipantFilter = new ReviewParticipantFilter();
		fAnomaliesFilter = new AnomaliesOnlyFilter();
		fReviewedElemsFilter = new ReviewedElemsFilter();

		final ReviewParticipantFilter filter = new ReviewParticipantFilter();
		filter.setParticipant(R4EUIModelController.getReviewer());
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	

	
	/**
	 * Remove all the currently applied filters
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void resetAllFilterActions() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		runReviewCurrentFilterCommand(false);
		runReviewsOnlyFilterCommand(false);
		runReviewsMyFilterCommand(false);
		runReviewsParticipantFilterCommand("");
		runAnomaliesFilterCommand(false);
		runReviewElemsFilterCommand(false);
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
	
	/**
	 * Method getDefaultSorter.
	 * @return ViewerComparator
	 */
	public ViewerComparator getAlphaSorter() {
		return fAlphaReviewSorter;
	}
	
	/**
	 * Method getReviewsOnlyFilter.
	 * @return ReviewsOnlyFilter
	 */
	public ReviewsOnlyFilter getReviewsOnlyFilter() {
		return fReviewsOnlyFilter;
	}
	
	/**
	 * Method resetReviewsOnlyFilterCommand.
	 */
	private void resetReviewsOnlyFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewsOnlyFilter);
        fCommandService.getCommand(R4EUIConstants.REVIEWS_ONLY_FILTER_COMMAND).
    		getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).setValue(Boolean.valueOf(false));
	}
	
	/**
	 * Method runReviewsOnlyFilterCommand.
	 * @param aApply boolean
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void runReviewsOnlyFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		resetReviewsOnlyFilterCommand();
		if (aApply) {
				fHandlerService.executeCommand(R4EUIConstants.REVIEWS_ONLY_FILTER_COMMAND, null);
		}
	}
	
	/**
	 * Method getCurrentReviewFilter.
	 * @return CurrentReviewFilter
	 */
	public CurrentReviewFilter getCurrentReviewFilter() {
		return fCurrentReviewFilter;
	}
	
	/**
	 * Method isCurrentReviewFilterSet.
	 * @return boolean
	 */
	public boolean isCurrentReviewFilterSet() {
		return ((Boolean) fCommandService.getCommand(R4EUIConstants.CURRENT_REVIEW_FILTER_COMMAND).
    			getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).getValue()).booleanValue();
	}
	
	/**
	 * Method resetReviewCurrentFilterCommand.
	 */
	private void resetReviewCurrentFilterCommand() {
		fView.getTreeViewer().removeFilter(fCurrentReviewFilter);
        fCommandService.getCommand(R4EUIConstants.CURRENT_REVIEW_FILTER_COMMAND).
    		getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).setValue(Boolean.valueOf(false));
	}
	
	/**
	 * Method runReviewCurrentFilterCommand.
	 * @param aApply boolean
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void runReviewCurrentFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		resetReviewCurrentFilterCommand();
		if (aApply) {
				fHandlerService.executeCommand(R4EUIConstants.CURRENT_REVIEW_FILTER_COMMAND, null);
		}
	}	
	
	/**
	 * Method getReviewsMyFilter.
	 * @return ReviewParticipantFilter
	 */
	public ReviewParticipantFilter getReviewsMyFilter() {
		return fReviewsMyFilter;
	}
	
	/**
	 * Method isMyReviewFilterSet.
	 * @return boolean
	 */
	public boolean isMyReviewFilterSet() {
		return ((Boolean) fCommandService.getCommand(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND).
    			getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).getValue()).booleanValue();
	}
	
	/**
	 * Method resetReviewsMyFilterCommand.
	 */
	private void resetReviewsMyFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewsMyFilter);
        fCommandService.getCommand(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND).
    		getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).setValue(Boolean.valueOf(false));
	}
	
	/**
	 * Method runMyReviewsFilterAction.
	 * @param aApply boolean
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void runReviewsMyFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		resetReviewsMyFilterCommand();
		if (aApply) {
				fHandlerService.executeCommand(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getFilterParticipant.
	 * @return String
	 */
	public String getFilterParticipant() {
		return fReviewsParticipantFilter.getParticipant();
	}
	
	/**
	 * Method getReviewsParticipantFilter.
	 * @return ReviewParticipantFilter
	 */
	public ReviewParticipantFilter getReviewsParticipantFilter() {
		return fReviewsParticipantFilter;
	}
	
	/**
	 * Method isParticipantFilterSet.
	 * @return boolean
	 */
	public boolean isParticipantFilterSet() {
		return ((Boolean) fCommandService.getCommand(R4EUIConstants.REVIEWS_PARTICIPANT_FILTER_COMMAND).
    			getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).getValue()).booleanValue();
	}
	
	/**
	 * Method resetReviewsParticipantFilterCommand.
	 */
	private void resetReviewsParticipantFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewsParticipantFilter);
        fCommandService.getCommand(R4EUIConstants.REVIEWS_PARTICIPANT_FILTER_COMMAND).
    		getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).setValue(Boolean.valueOf(false));
	}
	
	/**
	 * Method runParticipantFilterAction.
	 * @param aParticipant String
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void runReviewsParticipantFilterCommand(String aParticipant) 
			throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		resetReviewsParticipantFilterCommand();
		if (null == aParticipant || aParticipant.equals("")) {
			fReviewsParticipantFilter.setParticipant("");
			return;
		}
		fReviewsParticipantFilter.setParticipant(aParticipant);
		fHandlerService.executeCommand(R4EUIConstants.REVIEWS_PARTICIPANT_FILTER_COMMAND, null);
	}
	
	/**
	 * Method getAnomaliesFilter.
	 * @return AnomaliesOnlyFilter
	 */
	public AnomaliesOnlyFilter getAnomaliesFilter() {
		return fAnomaliesFilter;
	}
	
	/**
	 * Method resetAnomaliesFilterCommand.
	 */
	private void resetAnomaliesFilterCommand() {
		fView.getTreeViewer().removeFilter(fAnomaliesFilter);
        fCommandService.getCommand(R4EUIConstants.ANOMALIES_FILTER_COMMAND).
    		getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).setValue(Boolean.valueOf(false));
	}
	
	/**
	 * Method runAnomaliesFilterAction.
	 * @param aApply boolean
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void runAnomaliesFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		resetAnomaliesFilterCommand();
		if (aApply) {
				fHandlerService.executeCommand(R4EUIConstants.ANOMALIES_FILTER_COMMAND, null);
		}
	}	
	
	/**
	 * Method getReviewedElemsFilter.
	 * @return ReviewedElemsFilter
	 */
	public ReviewedElemsFilter getReviewedElemsFilter() {
		return fReviewedElemsFilter;
	}
	
	/**
	 * Method resetReviewedElemsFilterCommand.
	 */
	private void resetReviewedElemsFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewedElemsFilter);
        fCommandService.getCommand(R4EUIConstants.REVIEWED_ELEMS_FILTER_COMMAND).
    		getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY).setValue(Boolean.valueOf(false));
	}
	
	/**
	 * Method runReviewItemsFilterAction.
	 * @param aApply boolean
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void runReviewElemsFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		resetReviewedElemsFilterCommand();
		if (aApply) {
				fHandlerService.executeCommand(R4EUIConstants.REVIEWED_ELEMS_FILTER_COMMAND, null);
		}
	}
	
	/**
	 * Method openElementCommand.
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void openElementCommand() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		fHandlerService.executeCommand(R4EUIConstants.OPEN_ELEMENT_COMMAND, null);
	}
	
	/**
	 * Method closeElementCommand.
	 * @throws NotHandledException 
	 * @throws NotEnabledException 
	 * @throws NotDefinedException 
	 * @throws ExecutionException 
	 */
	public void closeElementCommand() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		fHandlerService.executeCommand(R4EUIConstants.CLOSE_ELEMENT_COMMAND, null);
	}
}
