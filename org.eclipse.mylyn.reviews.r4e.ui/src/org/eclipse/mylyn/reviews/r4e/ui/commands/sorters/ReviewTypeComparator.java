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
 * This class implements the comparator used to sort the reviews by type
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands.sorters;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.ui.filters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewTypeComparator extends NavigatorElementComparator {

	/**
	 * Method category.
	 * 
	 * @param element
	 *            Object
	 * @return int
	 * @see org.eclipse.mylyn.reviews.r4e.ui.filters.NavigatorElementComparator#category(Object)
	 */
	@Override
	public int category(Object element) {
		if (element instanceof R4EUIReviewBasic) {
			if (((R4EUIReviewBasic) element).getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
				return 0;
			} else if (((R4EUIReviewBasic) element).getReview()
					.getType()
					.equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
				return 1;
			} else {
				//Assume R4EReviewType.R4E_REVIEW_TYPE_FORMAL
				return 2;
			}
		}
		return 0;
	}
}
