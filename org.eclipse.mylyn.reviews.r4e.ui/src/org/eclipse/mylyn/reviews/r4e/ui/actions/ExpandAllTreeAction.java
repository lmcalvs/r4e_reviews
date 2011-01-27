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
 * This class implements the action used to expand all elements of the 
 * displayed tree 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ExpandAllTreeAction extends Action {

	/**
	 * Field fExpandAllIconFile.
	 * (value is ""icons/expandall.gif"")
	 */
	private static final String EXPAND_ALL_ACTION_ICON_FILE = "icons/expandall.gif";
	
	/**
	 * Field EXPAND_ALL_ACTION_TEXT.
	 * (value is ""Expand all"")
	 */
	private static final String EXPAND_ALL_ACTION_TEXT = "Expand all";
	
	/**
	 * Field EXPAND_ALL_ACTION_TOOLTIP.
	 * (value is ""Expand all elements of the review navigator tree"")
	 */
	private static final String EXPAND_ALL_ACTION_TOOLTIP = "Expand all elements of the review navigator tree";
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Constructor for ExpandAllTreeAction.
	 * @param aView ReviewNavigatorView
	 */
	public ExpandAllTreeAction(ReviewNavigatorView aView) {
		fView = aView;
		setText(EXPAND_ALL_ACTION_TEXT);
		setToolTipText(EXPAND_ALL_ACTION_TOOLTIP);
		setImageDescriptor(ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(EXPAND_ALL_ACTION_ICON_FILE)));
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {	
		//Expand tree (all levels)
		Activator.Tracer.traceInfo("Expanding whole tree");
		fView.getTreeViewer().expandAll();
	}
}
