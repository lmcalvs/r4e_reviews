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

public class AddAnomalyPropertyTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		//For formal reviews, anomalies can only be added by reveiwers in the preparation pahse
		R4EUIReviewBasic activeReview = R4EUIModelController.getActiveReview();
		if (null == activeReview) return false;
		
		R4EParticipant reviewer = null;
		try {
			reviewer = activeReview.getParticipant(R4EUIModelController.getReviewer(), false);
		} catch (ResourceHandlingException e) {
			return false;
		}
		if (null == reviewer) return false;

		if (activeReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
			if (!reviewer.getRoles().contains(R4EUserRole.R4E_ROLE_REVIEWER) || 
					!((R4EReviewState)activeReview.getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
				return false;
			}
		}
		return true;
	}
}
