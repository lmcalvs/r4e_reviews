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
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.ISources;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewState extends AbstractSourceProvider {

	/**
	 * Field MY_STATE.
	 * (value is ""org.eclipse.mylyn.reviews.r4e.ui.editors.commands.open"")
	 */
	public static final String MY_REVIEW_STATE = "org.eclipse.mylyn.reviews.r4e.ui.commands.review";
	
	
	/**
	 * Constructor for ReviewState.
	 */
	public ReviewState() {
		R4EUIModelController.setReviewCommandSourceProvider(this);
	}
	
	/**
	 * Method dispose.
	 * @see org.eclipse.ui.ISourceProvider#dispose()
	 */
	@Override
	public void dispose() { // $codepro.audit.disable emptyMethod
	}

	/**
	 * Method getCurrentState.
	 * @return Map<String,ReviewElement> 
	 * @see org.eclipse.ui.ISourceProvider#getCurrentState()
	 */
	@Override
	public Map<String, R4EUIReview> getCurrentState() {
		final Map<String, R4EUIReview> map = new HashMap<String, R4EUIReview>(1, 1);
		map.put(MY_REVIEW_STATE, R4EUIModelController.getActiveReview());
		return map;
	}

	/**
	 * Method getProvidedSourceNames.
	 * @return String[] 
	 * @see org.eclipse.ui.ISourceProvider#getProvidedSourceNames()
	 */
	@Override
	public String[] getProvidedSourceNames() {
		return new String[] { MY_REVIEW_STATE };
	}
	
	/**
	 * Method setReview.
	 * @param aOpenReview ReviewElement
	 */
	public void setReview(R4EUIReview aOpenReview) {
		fireSourceChanged(ISources.WORKBENCH, ReviewState.MY_REVIEW_STATE, aOpenReview);
	}
}
