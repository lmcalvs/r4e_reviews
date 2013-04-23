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
 * This class implements the comparator used to sort the reviews by type
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.sorters;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ReviewTypeComparator extends NavigatorElementComparator {

	/**
	 * Method category.
	 * 
	 * @param aElement
	 *            Object
	 * @return int
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.sorters.NavigatorElementComparator#category(Object)
	 */
	@Override
	public int category(Object aElement) {
		if (aElement instanceof R4EUIReviewGroup) {
			return 0;
		}
		if (aElement instanceof R4EUIRuleSet) {
			return 1;
		}
		if (aElement instanceof R4EUIReviewBasic) {
			if (((R4EUIReviewBasic) aElement).getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
				return 0;
			} else if (((R4EUIReviewBasic) aElement).getReview()
					.getType()
					.equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
				return 1;
			} else if (((R4EUIReviewBasic) aElement).getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
				return 2;
			} else {
				return 3;
			}
		}
		return 2;
	}
}
