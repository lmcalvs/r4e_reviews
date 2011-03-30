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
 * anomalies assigned to the current user
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIParticipantContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelectionContainer;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AnomaliesMyFilter extends ViewerFilter  {
	
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
		
		//Only show currently open review
		if (element instanceof R4EUIReviewBasic) {
			if (!((R4EUIReviewBasic)element).isOpen()) return false;
		}
		//Only show anomalies
		if (element instanceof R4EUISelectionContainer || element instanceof R4EUISelection || 
				element instanceof R4EUIParticipantContainer || element instanceof R4EUIParticipant) return false;
		
		//For basic reviews, show all anomalies, For other review types, only show anomalies assigned to us
		if (element instanceof R4EUIAnomalyBasic) {
			if (!(((R4EUIAnomalyExtended)element).getAnomaly().getUser().getId().equals(
					R4EUIModelController.getReviewer()))) return false;
		}
		return true;
	}
}
