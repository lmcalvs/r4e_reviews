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
 * the currently open review and its descendants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class CurrentReviewFilter extends ViewerFilter  {
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method select.
	 * @param viewer Viewer
	 * @param parentElement Object
	 * @param element Object
	 * @return boolean
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		
		//When there is no active review, display all elements.  The filter will be re-applied 
		//when a review becomes active
		if (null != R4EUIModelController.getActiveReview()) {
			if (isParentCurrentReview((IR4EUIModelElement)element)) return true;
			return isChildrenCurrentReview((IR4EUIModelElement)element);
		}
		return true;
	}
	
	/**
	 * Checks if the children element contains the current participant
	 * @param aCurrentElement - the element to filter on
	 * @return true/false
	 */
	private boolean isChildrenCurrentReview(IR4EUIModelElement aCurrentElement) {
		final int length = aCurrentElement.getChildren().length;
		IR4EUIModelElement element = null;
		for (int i = 0; i < length; i++) {
			element = aCurrentElement.getChildren()[i];
			if (!(element instanceof R4EUIReview)) return false;
			if (((R4EUIReview)element).equals(R4EUIModelController.getActiveReview())) return true;
		}
		return false;
	}
	
	/**
	 * Checks if the parent element is the currently active review
	 * @param aCurrentElement - the element to filter on
	 * @return true/false
	 */
	private boolean isParentCurrentReview(IR4EUIModelElement aCurrentElement) {
		
		//Get Review parent
		IR4EUIModelElement reviewParentElement = aCurrentElement;
		while (!(reviewParentElement instanceof R4EUIReview)) {
			reviewParentElement = reviewParentElement.getParent();
			if (null == reviewParentElement) return false;
		}
	
		//Check if we are a participant for this review
		if (((R4EUIReview)reviewParentElement).equals(R4EUIModelController.getActiveReview())) return true;
		return false;
	}
}
