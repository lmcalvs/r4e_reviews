// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the action used to collapse all elements of the 
 * displayed tree 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class CollapseAllTreeAction extends Action {

	/**
	 * Field COLLAPSE_ALL_ACTION_TEXT.
	 * (value is ""Collapse all"")
	 */
	private static final String COLLAPSE_ALL_ACTION_TEXT = "Collapse all";
	
	/**
	 * Field COLLAPSE_ALL_ACTION_TOOLTIP.
	 * (value is ""Collapse all elements of the review navigator tree"")
	 */
	private static final String COLLAPSE_ALL_ACTION_TOOLTIP = "Collapse all elements of the review navigator tree";
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * @param aView - the review navigator view
	 */
	public CollapseAllTreeAction(ReviewNavigatorView aView) {
		fView = aView;
		setText(COLLAPSE_ALL_ACTION_TEXT);
		setToolTipText(COLLAPSE_ALL_ACTION_TOOLTIP);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_COLLAPSEALL));
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {	
		//Collapse tree (all levels)
		Activator.Tracer.traceInfo("Collapsing whole tree");
		fView.getTreeViewer().collapseAll();
	}
}
