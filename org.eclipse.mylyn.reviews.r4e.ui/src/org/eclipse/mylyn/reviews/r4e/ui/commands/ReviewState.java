// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements a command variable used to verify if a given
 * context-sensitive command should be enabled or disabled
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewState extends AbstractSourceProvider {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_CURRENT. (value is ""org.eclipse.mylyn.reviews.r4e.ui.commands.reviewCurrent"")
	 */
	public static final String REVIEW_CURRENT = "org.eclipse.mylyn.reviews.r4e.ui.commands.reviewCurrent";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewState.
	 */
	public ReviewState() {
		R4EUIModelController.setReviewCommandSourceProvider(this);
	}

	/**
	 * Method dispose.
	 * 
	 * @see org.eclipse.ui.ISourceProvider#dispose()
	 */
	public void dispose() { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method getCurrentState.
	 * 
	 * @return Map<String,ReviewElement>
	 * @see org.eclipse.ui.ISourceProvider#getCurrentState()
	 */
	public Map<String, R4EUIReviewBasic> getCurrentState() {
		final Map<String, R4EUIReviewBasic> map = new HashMap<String, R4EUIReviewBasic>(1, 1);
		map.put(REVIEW_CURRENT, R4EUIModelController.getActiveReview());
		return map;
	}

	/**
	 * Method getProvidedSourceNames.
	 * 
	 * @return String[]
	 * @see org.eclipse.ui.ISourceProvider#getProvidedSourceNames()
	 */
	public String[] getProvidedSourceNames() {
		return new String[] { REVIEW_CURRENT };
	}

	/**
	 * Method setReview.
	 * 
	 * @param aOpenReview
	 *            ReviewElement
	 */
	public void setCurrentReview(R4EUIReviewBasic aOpenReview) {
		fireSourceChanged(ISources.WORKBENCH, REVIEW_CURRENT, aOpenReview);
	}
}
