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
 * This class implements the action used to apply the default filter to the 
 * UI model tree structure
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewNavigatorFilterAction extends Action {

	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Field fFilter.
	 */
	private final ViewerFilter fFilter;
	
	/**
	 * Field fFilterApplied.
	 */
	private boolean fFilterApplied;
	
	/**
	 * Constructor for ReviewNavigatorFilterAction.
	 * @param aView ReviewNavigatorView
	 * @param aFilter ViewerFilter
	 * @param aFilterName String
	 */
	public ReviewNavigatorFilterAction(ReviewNavigatorView aView, ViewerFilter aFilter, String aFilterName) {
		super(aFilterName, IAction.AS_CHECK_BOX);
		fView = aView;
		fFilter = aFilter;
		fFilterApplied = false;
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {
		//We need to preserve the expansion state and restore it afterwards
		final Object[] elements =  fView.getTreeViewer().getExpandedElements();
		if (!fFilterApplied) {
			    Activator.Tracer.traceInfo("Apply filter " + fFilter.toString());
				fView.getTreeViewer().addFilter(fFilter);
				this.setChecked(true);
				fFilterApplied = true;
		} else {
		    Activator.Tracer.traceInfo("Remove filter " + fFilter.toString());
			fView.getTreeViewer().removeFilter(fFilter);
			this.setChecked(false);
			fFilterApplied = false;
		}
		fView.getTreeViewer().setExpandedElements(elements);
	}
	
	/**
	 * Method reset.
	 */
	public void reset() {
		this.setChecked(false);
		fFilterApplied = false;
	}
}
