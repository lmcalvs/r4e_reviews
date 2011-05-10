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

package org.eclipse.mylyn.reviews.r4e.ui.filters;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;

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
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		final IStructuredSelection selection = (IStructuredSelection) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getSelection();
		if (null == selection)
			return true;
		final IR4EUIModelElement selectedElement = (IR4EUIModelElement) selection.getFirstElement();
		if (null == selectedElement)
			return true;

		//Display the current element if itself or one of its ancestors is the selected element
		IR4EUIModelElement currentElement = (IR4EUIModelElement) element;
		do {
			if (currentElement.equals(selectedElement))
				return true;
			currentElement = (currentElement).getParent();
		} while (null != currentElement);
		return false;
	}
}
