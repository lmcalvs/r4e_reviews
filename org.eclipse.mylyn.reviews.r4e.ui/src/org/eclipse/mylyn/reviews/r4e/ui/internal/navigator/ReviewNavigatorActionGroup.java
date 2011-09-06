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

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.commands.sorters.ReviewTypeComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.AnomaliesMyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.AnomaliesOnlyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.FocusFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.HideDeltasFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.HideRuleSetsFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.ReviewCompletedFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.ReviewParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.ReviewedElemsFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.filters.ReviewsOnlyFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerActivation;
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
	 * Field fReviewTypeSorter.
	 */
	private final ViewerComparator fReviewTypeSorter;

	/**
	 * Field fFocusFilter.
	 */
	private final FocusFilter fFocusFilter;

	/**
	 * Field fCurrentReviewFilter.
	 */
	private final AnomaliesMyFilter fCurrentReviewFilter;

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
	 * Field fReviewsCompletedFilter.
	 */
	private final ReviewCompletedFilter fReviewsCompletedFilter;

	/**
	 * Field fAnomaliesFilter.
	 */
	private final AnomaliesOnlyFilter fAnomaliesFilter;

	/**
	 * Field fReviewedElemsFilter.
	 */
	private final ReviewedElemsFilter fReviewedElemsFilter;

	/**
	 * Field fHideRuleSetsFilter.
	 */
	private final HideRuleSetsFilter fHideRuleSetsFilter;

	/**
	 * Field fHideDeltasFilter.
	 */
	private final HideDeltasFilter fHideDeltasFilter;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * @param aView
	 *            - the review navigator view
	 */
	public ReviewNavigatorActionGroup(ReviewNavigatorView aView) {
		super();
		R4EUIPlugin.Ftracer.traceInfo("Create Action Group for view " + aView.getPartName());
		fView = aView;
		fCommandService = (ICommandService) fView.getSite().getWorkbenchWindow().getService(ICommandService.class);
		fHandlerService = (IHandlerService) fView.getSite().getWorkbenchWindow().getService(IHandlerService.class);
		fView.setEditorLinked(((Boolean) fCommandService.getCommand(R4EUIConstants.LINK_EDITOR_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.getValue()).booleanValue());
		fView.setPropertiesLinked(((Boolean) fCommandService.getCommand(R4EUIConstants.LINK_PROPERTIES_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.getValue()).booleanValue());

		fAlphaReviewSorter = new NavigatorElementComparator();
		fReviewTypeSorter = new ReviewTypeComparator();
		fFocusFilter = new FocusFilter();
		fCurrentReviewFilter = new AnomaliesMyFilter();
		fReviewsOnlyFilter = new ReviewsOnlyFilter();
		fReviewsMyFilter = new ReviewParticipantFilter();
		fReviewsParticipantFilter = new ReviewParticipantFilter();
		fReviewsCompletedFilter = new ReviewCompletedFilter();
		fAnomaliesFilter = new AnomaliesOnlyFilter();
		fReviewedElemsFilter = new ReviewedElemsFilter();
		fHideRuleSetsFilter = new HideRuleSetsFilter();
		fHideDeltasFilter = new HideDeltasFilter();

		final ReviewParticipantFilter filter = new ReviewParticipantFilter();
		filter.setParticipant(R4EUIModelController.getReviewer());
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Remove all the currently applied filters
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void resetAllFilterActions() throws ExecutionException, NotDefinedException, NotEnabledException,
			NotHandledException {
		runReviewsOnlyFilterCommand(false);
		runReviewsMyFilterCommand(false);
		runReviewsParticipantFilterCommand("");
		runReviewsCompletedFilterCommand(false);
		runAnomaliesFilterCommand(false);
		runAnomaliesMyFilterCommand(false);
		runReviewElemsFilterCommand(false);
		runHideRuleSetsFilterCommand(false);
		runHideDeltasFilterCommand(false);
		runFocusFilterCommand(false);
	}

	/**
	 * Method dialogOpenNotify.
	 */
	public void dialogOpenNotify() {
		//A dialog is open, refresh menus accordingly
		final IToolBarManager toolbar = fView.getViewSite().getActionBars().getToolBarManager();
		final IContributionItem[] items = toolbar.getItems();
		for (IContributionItem item : items) {
			if (item instanceof ActionContributionItem) {
				if (R4EUIModelController.isJobInProgress()) {
					((ActionContributionItem) item).getAction().setEnabled(false);
				} else {
					((ActionContributionItem) item).getAction().setEnabled(true);
				}
			}
		}
	}

	/**
	 * Method getDefaultSorter.
	 * 
	 * @return ViewerComparator
	 */
	public ViewerComparator getAlphaSorter() {
		return fAlphaReviewSorter;
	}

	/**
	 * Method getDefaultSorter.
	 * 
	 * @return ViewerComparator
	 */
	public ViewerComparator getReviewTypeSorter() {
		return fReviewTypeSorter;
	}

	/**
	 * Method getReviewsCompletedFilter.
	 * 
	 * @return fReviewsCompletedFilter
	 */
	public ReviewCompletedFilter getReviewsCompletedFilter() {
		return fReviewsCompletedFilter;
	}

	/**
	 * Method resetReviewsCompletedFilterCommand.
	 */
	private void resetReviewsCompletedFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewsCompletedFilter);
		fCommandService.getCommand(R4EUIConstants.REVIEWS_COMPLETED_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runReviewsCompletedFilterCommand.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runReviewsCompletedFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetReviewsCompletedFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.REVIEWS_COMPLETED_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getReviewsOnlyFilter.
	 * 
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
		fCommandService.getCommand(R4EUIConstants.REVIEWS_ONLY_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runReviewsOnlyFilterCommand.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runReviewsOnlyFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetReviewsOnlyFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.REVIEWS_ONLY_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getCurrentReviewFilter.
	 * 
	 * @return CurrentReviewFilter
	 */
	public AnomaliesMyFilter getCurrentReviewFilter() {
		return fCurrentReviewFilter;
	}

	/**
	 * Method isCurrentReviewFilterSet.
	 * 
	 * @return boolean
	 */
	public boolean isAnomaliesMyFilterSet() {
		return ((Boolean) fCommandService.getCommand(R4EUIConstants.ANOMALIES_MY_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.getValue()).booleanValue();
	}

	/**
	 * Method resetReviewCurrentFilterCommand.
	 */
	private void resetAnomaliesMyFilterCommand() {
		fView.getTreeViewer().removeFilter(fCurrentReviewFilter);
		fCommandService.getCommand(R4EUIConstants.ANOMALIES_MY_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runReviewCurrentFilterCommand.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runAnomaliesMyFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetAnomaliesMyFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.ANOMALIES_MY_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getReviewsMyFilter.
	 * 
	 * @return ReviewParticipantFilter
	 */
	public ReviewParticipantFilter getReviewsMyFilter() {
		return fReviewsMyFilter;
	}

	/**
	 * Method isMyReviewFilterSet.
	 * 
	 * @return boolean
	 */
	public boolean isMyReviewFilterSet() {
		return ((Boolean) fCommandService.getCommand(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.getValue()).booleanValue();
	}

	/**
	 * Method resetReviewsMyFilterCommand.
	 */
	private void resetReviewsMyFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewsMyFilter);
		fCommandService.getCommand(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runMyReviewsFilterAction.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runReviewsMyFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetReviewsMyFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.REVIEWS_MY_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getFilterParticipant.
	 * 
	 * @return String
	 */
	public String getFilterParticipant() {
		return fReviewsParticipantFilter.getParticipant();
	}

	/**
	 * Method getReviewsParticipantFilter.
	 * 
	 * @return ReviewParticipantFilter
	 */
	public ReviewParticipantFilter getReviewsParticipantFilter() {
		return fReviewsParticipantFilter;
	}

	/**
	 * Method isParticipantFilterSet.
	 * 
	 * @return boolean
	 */
	public boolean isParticipantFilterSet() {
		return ((Boolean) fCommandService.getCommand(R4EUIConstants.REVIEWS_PARTICIPANT_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.getValue()).booleanValue();
	}

	/**
	 * Method resetReviewsParticipantFilterCommand.
	 */
	private void resetReviewsParticipantFilterCommand() {
		fView.getTreeViewer().removeFilter(fReviewsParticipantFilter);
		fCommandService.getCommand(R4EUIConstants.REVIEWS_PARTICIPANT_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runParticipantFilterAction.
	 * 
	 * @param aParticipant
	 *            String
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runReviewsParticipantFilterCommand(String aParticipant) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
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
	 * 
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
		fCommandService.getCommand(R4EUIConstants.ANOMALIES_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runAnomaliesFilterAction.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runAnomaliesFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetAnomaliesFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.ANOMALIES_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getReviewedElemsFilter.
	 * 
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
		fCommandService.getCommand(R4EUIConstants.REVIEWED_ELEMS_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runReviewItemsFilterAction.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runReviewElemsFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetReviewedElemsFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.REVIEWED_ELEMS_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getHideRuleSetsFilter.
	 * 
	 * @return HideRuleSetsFilter
	 */
	public HideRuleSetsFilter getHideRuleSetsFilter() {
		return fHideRuleSetsFilter;
	}

	/**
	 * Method resetHideRuleSetsFilterCommand.
	 */
	private void resetHideRuleSetsFilterCommand() {
		fView.getTreeViewer().removeFilter(fHideRuleSetsFilter);
		fCommandService.getCommand(R4EUIConstants.HIDE_RULE_SETS_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runHideRuleSetsFilterCommand.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runHideRuleSetsFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetHideRuleSetsFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.HIDE_RULE_SETS_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getHideDeltasFilter.
	 * 
	 * @return HideDeltasFilter
	 */
	public HideDeltasFilter getHideDeltasFilter() {
		return fHideDeltasFilter;
	}

	/**
	 * Method resetHideDeltasFilterCommand.
	 */
	private void resetHideDeltasFilterCommand() {
		fView.getTreeViewer().removeFilter(fHideDeltasFilter);
		fCommandService.getCommand(R4EUIConstants.HIDE_DELTAS_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runHideDeltasFilterCommand.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runHideDeltasFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetHideDeltasFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.HIDE_DELTAS_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method getFocusFilter.
	 * 
	 * @return FocusFilter
	 */
	public FocusFilter getFocusFilter() {
		return fFocusFilter;
	}

	/**
	 * Method resetFocusFilterCommand.
	 */
	private void resetFocusFilterCommand() {
		fView.getTreeViewer().removeFilter(fFocusFilter);
		fCommandService.getCommand(R4EUIConstants.GO_INTO_FILTER_COMMAND)
				.getState(R4EUIConstants.TOGGLE_STATE_COMMAND_KEY)
				.setValue(Boolean.valueOf(false));
	}

	/**
	 * Method runSetFocusFilterCommand.
	 * 
	 * @param aApply
	 *            boolean
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void runFocusFilterCommand(boolean aApply) throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException {
		resetFocusFilterCommand();
		if (aApply) {
			fHandlerService.executeCommand(R4EUIConstants.GO_INTO_FILTER_COMMAND, null);
		}
	}

	/**
	 * Method openElementCommand.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void openElementCommand() throws ExecutionException, NotDefinedException, NotEnabledException,
			NotHandledException {
		fHandlerService.executeCommand(R4EUIConstants.OPEN_ELEMENT_COMMAND, null);
	}

	/**
	 * Method closeElementCommand.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void closeElementCommand() throws ExecutionException, NotDefinedException, NotEnabledException,
			NotHandledException {
		fHandlerService.executeCommand(R4EUIConstants.CLOSE_ELEMENT_COMMAND, null);
	}

	/**
	 * Method addReviewItemCommand.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	public void addReviewItemCommand() throws ExecutionException, NotDefinedException, NotEnabledException,
			NotHandledException {
		boolean commandActive = fCommandService.getCommand(R4EUIConstants.NEW_REVIEW_ITEM_COMMAND)
				.getHandler()
				.isEnabled();
		final IHandlerActivation activationToken = fHandlerService.activateHandler(
				R4EUIConstants.NEW_REVIEW_ITEM_COMMAND,
				fCommandService.getCommand(R4EUIConstants.NEW_REVIEW_ITEM_COMMAND).getHandler());
		fHandlerService.executeCommand(R4EUIConstants.NEW_REVIEW_ITEM_COMMAND, null);
		if (!commandActive) {
			fHandlerService.deactivateHandler(activationToken);
		}
	}
}
