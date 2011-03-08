package org.eclipse.mylyn.reviews.r4e.ui.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.ReviewExtraProperties;
import org.eclipse.ui.views.properties.IPropertySource;

public class R4EUIReviewExtended extends R4EUIReviewBasic {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUIReview.
	 * @param aParent R4EUIReviewGroup
	 * @param aReview R4EReview
	 * @param aType R4EReviewType
	 * @param aOpen boolean
	 * @throws ResourceHandlingException
	 */
	public R4EUIReviewExtended(R4EUIReviewGroup aParent, R4EReview aReview, R4EReviewType aType, boolean aOpen) throws ResourceHandlingException {
		super(aParent, aReview, aType, aOpen);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getAdapter.
	 * @param adapter Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) return this;
		if (IPropertySource.class.equals(adapter)) return new ReviewExtraProperties(this);
		return null;
	}
	

	//Review State Machine
	
	/**
	 * Method isPreparationDateEnabled.
	 * @return boolean
	 */
	public boolean isPreparationDateEnabled() {
		if (((R4EReviewState)fReview.getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION))
			return true;
		return false;
	}
	
	/**
	 * Method isDecisionDateEnabled.
	 * @return boolean
	 */
	public boolean isDecisionDateEnabled() {
		if (((R4EReviewState)fReview.getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION))
			return true;
		return false;
	}
	
	/**
	 * Method isReworkDateEnabled.
	 * @return boolean
	 */
	public boolean isReworkDateEnabled() {
		if (((R4EReviewState)fReview.getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK))
			return true;
		return false;
	}
}
