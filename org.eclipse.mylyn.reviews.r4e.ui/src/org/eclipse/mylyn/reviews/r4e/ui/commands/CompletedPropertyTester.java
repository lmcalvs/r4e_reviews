package org.eclipse.mylyn.reviews.r4e.ui.commands;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;

public class CompletedPropertyTester extends PropertyTester {

	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (null == R4EUIModelController.getActiveReview()) return false;
		return !R4EUIModelController.getActiveReview().isReviewed();
	}
}
