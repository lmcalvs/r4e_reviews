// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the action used to remove are applied filters
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class RemoveAllFiltersAction extends Action {

	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Constructor for RemoveAllFiltersAction.
	 * @param aView ReviewNavigatorView
	 * @param aFilterName String
	 */
	public RemoveAllFiltersAction(ReviewNavigatorView aView, String aFilterName) {
		super(aFilterName);
		fView = aView;
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {
		Activator.Tracer.traceInfo("Remove all filters");

		//We need to preserve the expansion state and restore it afterwards
		final Object[] elements =  fView.getTreeViewer().getExpandedElements();
		fView.getTreeViewer().setInput(R4EUIModelController.getRootElement());
		fView.getTreeViewer().resetFilters();
		((ReviewNavigatorActionGroup)fView.getActionSet()).resetAllFilterActions();
		fView.getTreeViewer().setExpandedElements(elements);
	}
}
