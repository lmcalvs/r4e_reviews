package org.eclipse.mylyn.reviews.r4e.ui.commands.sorters;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.ui.filters.NavigatorElementComparator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;

public class ReviewTypeComparator extends NavigatorElementComparator {

    @Override
	public int category(Object element) {
        if (element instanceof R4EUIReviewBasic) {
        	if (((R4EUIReviewBasic)element).getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
        		return 0;
        	} else if (((R4EUIReviewBasic)element).getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
        		return 1;
        	} else {
        		//Assume R4EReviewType.R4E_REVIEW_TYPE_FORMAL
        		return 2;
        	}
        }
        return 0;
    }
}
