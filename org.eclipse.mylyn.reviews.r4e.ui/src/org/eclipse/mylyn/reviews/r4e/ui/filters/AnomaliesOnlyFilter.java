// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc
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
 * This class implements the Navigator View filter used to filter out
 * the selection elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIParticipantContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleViolation;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelectionContainer;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AnomaliesOnlyFilter extends ViewerFilter  {
	
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
		//if (element instanceof R4EUISelection || element instanceof R4EUISelectionContainer) return false;
		//return true;
		
		//Only show currently open review
		if (element instanceof R4EUIReviewBasic) {
			if (!((R4EUIReviewBasic)element).isOpen()) return false;
		}
		//Only show anomalies
		if (element instanceof R4EUISelectionContainer || element instanceof R4EUISelection || 
				element instanceof R4EUIParticipantContainer || element instanceof R4EUIParticipant ||
				element instanceof R4EUIRuleSet || element instanceof R4EUIRuleArea ||
				element instanceof R4EUIRuleViolation || element instanceof R4EUIRule) return false;
		
		//If these this is an anomaly, show it
		if (element instanceof R4EUIAnomalyBasic) {
			return true;
		}
		//For other elements, we only show the if they are a parent of one of our anomalies
		return isChildrenAnomaly((IR4EUIModelElement) element);		
	}
	
	/**
	 * Checks if the child element is an anomaly
	 * @param aCurrentElement - the element to filter on
	 * @return true/false
	 */
	private boolean isChildrenAnomaly(IR4EUIModelElement aCurrentElement) {
		
		//If the current element is the anomaly container, check if any child anomaly is ours.  If so, we show this parent
		if (aCurrentElement instanceof R4EUIAnomalyContainer) {
			final int length = aCurrentElement.getChildren().length;
			if (length > 0) return true;
		} else if (aCurrentElement instanceof R4EUIAnomalyBasic) {
				return true;
		} else {
			final int length = aCurrentElement.getChildren().length;
			for (int i = 0; i < length; i++) {
				if (isChildrenAnomaly(aCurrentElement.getChildren()[i])) {
					return true;
				}
			}
		}
		return false;
	}
}
