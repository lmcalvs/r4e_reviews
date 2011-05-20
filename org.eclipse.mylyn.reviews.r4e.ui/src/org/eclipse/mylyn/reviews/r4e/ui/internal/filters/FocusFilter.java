// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the Navigator View filter used to display only
 * the currently selected element and its descendants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FocusFilter extends ViewerFilter {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method select.
	 * 
	 * @param viewer
	 *            Viewer
	 * @param parentElement
	 *            Object
	 * @param element
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean select(Viewer aViewer, Object aParentElement, Object aElement) {

		//The input of the tree is already set to the parent of the focus element, so just check if the 
		//current element is a child of the focus element
		IR4EUIModelElement focusElement = R4EUIModelController.getCurrentFocusElement();
		IR4EUIModelElement focusElementParent = focusElement.getParent();
		if ((null == aParentElement && null == focusElementParent)
				|| (null != aParentElement && aParentElement.equals(focusElementParent))) {
			if (aElement.equals(focusElement)) {
				return true;
			}
			return false;
		}
		return true;
	}
}
