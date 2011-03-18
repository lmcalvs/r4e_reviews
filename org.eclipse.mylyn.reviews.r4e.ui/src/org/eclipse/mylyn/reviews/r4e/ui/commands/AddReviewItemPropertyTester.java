/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a property tester that is used to see if a review item can
 * be added
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddReviewItemPropertyTester extends PropertyTester {

	/**
	 * Method test.
	 * @param receiver Object
	 * @param property String
	 * @param args Object[]
	 * @param expectedValue Object
	 * @return boolean
	 * @see org.eclipse.core.expressions.IPropertyTester#test(Object, String, Object[], Object)
	 */
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		//For formal reviews, review items can only be added by reveiwers in the planning and preparation pahse
		final R4EUIReviewBasic activeReview = R4EUIModelController.getActiveReview();
		if (null == activeReview) return false;
		
		R4EParticipant reviewer = null;
		try {
			reviewer = activeReview.getParticipant(R4EUIModelController.getReviewer(), false);
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);
			return false;
		}
		if (null == reviewer) return false;

		if (activeReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
			if (((R4EReviewState)activeReview.getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_STARTED)) {
				if (!(reviewer.getRoles().contains(R4EUserRole.R4E_ROLE_LEAD)) || (reviewer.getRoles().contains(R4EUserRole.R4E_ROLE_AUTHOR))) {
					return false;
				}
			} else if (!((R4EReviewState)activeReview.getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
				return false;
			}
		}
		return true;
	}

}
