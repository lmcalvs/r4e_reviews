// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
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

package org.eclipse.mylyn.reviews.r4e.ui.internal.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipantContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleViolation;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class AnomaliesOnlyFilter extends ViewerFilter {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method select.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aParentElement
	 *            Object
	 * @param aElement
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean select(Viewer aViewer, Object aParentElement, Object aElement) {

		//Always elements not directly related to anomalies
		if (aElement instanceof R4EUIReviewGroup || aElement instanceof R4EUIReviewBasic
				|| aElement instanceof R4EUIParticipantContainer || aElement instanceof R4EUIParticipant
				|| aElement instanceof R4EUIRuleSet || aElement instanceof R4EUIRuleArea
				|| aElement instanceof R4EUIRuleViolation || aElement instanceof R4EUIRule) {
			return true;
		}

		//If these this is an anomaly, show it
		if (aElement instanceof R4EUIAnomalyBasic || aElement instanceof R4EUIComment) {
			return true;
		}
		//For other elements, we only show the if they are a parent of one of our anomalies
		return isChildrenAnomaly((IR4EUIModelElement) aElement);
	}

	/**
	 * Checks if the child element is an anomaly
	 * 
	 * @param aCurrentElement
	 *            - the element to filter on
	 * @return true/false
	 */
	private boolean isChildrenAnomaly(IR4EUIModelElement aCurrentElement) {

		//If the current element is the anomaly container, check if any child anomaly is ours.  If so, we show this parent
		if (aCurrentElement instanceof R4EUIAnomalyContainer) {
			final int length = aCurrentElement.getChildren().length;
			if (length > 0) {
				return true;
			}
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
