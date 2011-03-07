package org.eclipse.mylyn.reviews.r4e.ui.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.ReviewExtraProperties;
import org.eclipse.ui.views.properties.IPropertySource;

public class R4EUIReviewExtended extends R4EUIReviewBasic {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_PHASE_PLANNING.
	 * (value is ""PLANNING"")
	 */
	private static final String REVIEW_PHASE_PLANNING = "PLANNING";
	
	/**
	 * Field REVIEW_PHASE_PREPARATION.
	 * (value is ""PREPARATION"")
	 */
	private static final String REVIEW_PHASE_PREPARATION = "PREPARATION";
	
	/**
	 * Field REVIEW_PHASE_DECISION.
	 * (value is ""DECISION"")
	 */
	private static final String REVIEW_PHASE_DECISION = "DECISION";
	
	/**
	 * Field REVIEW_PHASE_REWORK.
	 * (value is ""REWORK"")
	 */
	private static final String REVIEW_PHASE_REWORK = "REWORK";
	
	/**
	 * Field REVIEW_PHASE_COMPLETED.
	 * (value is ""COMPLETED"")
	 */
	private static final String REVIEW_PHASE_COMPLETED = "COMPLETED";
	
	
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
	
	/**
	 * Method updatePhase.
	 * @param aNewPhase R4EReviewPhase
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 */
	public void updateState(R4EReviewPhase aNewPhase) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fReview, 
				R4EUIModelController.getReviewer());
		((R4EReviewState)fReview.getState()).setState(aNewPhase);
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    	setName(getPhaseString(aNewPhase) + ": " + getName());
	}
	
	/**
	 * Method getPhaseString.
	 * @param aNewPhase R4EReviewPhase
 	 * @return String
	 */
	public static String getPhaseString(R4EReviewPhase aNewPhase) {
		if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_STARTED)) {
			return REVIEW_PHASE_PLANNING;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
			return REVIEW_PHASE_PREPARATION;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION)) {
			return REVIEW_PHASE_DECISION;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK)) {
			return REVIEW_PHASE_REWORK;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED)) {
			return REVIEW_PHASE_COMPLETED;
		} else return "";
	}
}
