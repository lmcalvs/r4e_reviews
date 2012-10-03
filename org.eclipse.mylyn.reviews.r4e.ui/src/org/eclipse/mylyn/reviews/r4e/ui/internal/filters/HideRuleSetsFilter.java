// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Navigator View filter used to hide the
 * Rule Set elements and their descendants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleViolation;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class HideRuleSetsFilter extends ViewerFilter {

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

		if (element instanceof R4EUIRuleSet || element instanceof R4EUIRuleArea
				|| element instanceof R4EUIRuleViolation || element instanceof R4EUIRule) {
			return false;
		}
		return true;
	}
}
