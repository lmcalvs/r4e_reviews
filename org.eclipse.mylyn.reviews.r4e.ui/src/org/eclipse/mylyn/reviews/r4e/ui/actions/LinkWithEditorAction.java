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
 * This class implements the action used to link the review navigator view 
 * with the editor
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
public class LinkWithEditorAction extends Action {

	/**
	 * Field LINK_EDITOR_ACTION_TEXT.
	 * (value is ""Link with editor"")
	 */
	private static final String LINK_EDITOR_ACTION_TEXT = "Link with editor";
	
	/**
	 * Field LINK_EDITOR_ACTION_TOOLTIP.
	 * (value is ""Link with editor"")
	 */
	private static final String LINK_EDITOR_ACTION_TOOLTIP = "Link with editor";
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Field fLinkApplied.
	 */
	private boolean fLinkApplied;
	
	/**
	 * @param aView - the review navigator view
	 */
	public LinkWithEditorAction(ReviewNavigatorView aView) {
		fView = aView;
		fLinkApplied = true;
		fView.setEditorLinked(fLinkApplied);
		setChecked(true);
		setText(LINK_EDITOR_ACTION_TEXT);
		setToolTipText(LINK_EDITOR_ACTION_TOOLTIP);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_SYNCED));
	}
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {
		//We need to preserve the expansion state and restore it afterwards
		final Object[] elements =  fView.getTreeViewer().getExpandedElements();
		if (!fLinkApplied) {
			Activator.Tracer.traceInfo("Linking Editor with ReviewNavigator");
			setChecked(true);
			fLinkApplied = true;
		} else {
			Activator.Tracer.traceInfo("Unlinking Editor with ReviewNavigator");
			setChecked(false);
			fLinkApplied = false;
		}
		fView.setEditorLinked(fLinkApplied);
		fView.getTreeViewer().setExpandedElements(elements);
	}
}
