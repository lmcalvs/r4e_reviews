package org.eclipse.mylyn.reviews.r4e.ui.properties.general;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ReviewExtraProperties extends ReviewGeneralProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field REVIEW_PHASE_OWNER_ID. (value is ""reviewElement.phaseOwner"")
	 */
	protected static final String REVIEW_PHASE_OWNER_ID = "reviewElement.phaseOwner";

	/**
	 * Field REVIEW_PHASE_OWNER_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor REVIEW_PHASE_OWNER_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_PHASE_OWNER_ID, R4EUIConstants.PHASE_OWNER_LABEL);
	
	/**
	 * Field REVIEW_PREPARATION_DATE_ID. (value is ""reviewElement.preparationDate"")
	 */
	protected static final String REVIEW_PREPARATION_DATE_ID = "reviewElement.preparationDate";

	/**
	 * Field REVIEW_PREPARATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor REVIEW_PREPARATION_DATE_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_PREPARATION_DATE_ID, R4EUIConstants.PREPARATION_DATE_LABEL);
	
	/**
	 * Field REVIEW_DECISION_DATE_ID. (value is ""reviewElement.decisionDate"")
	 */
	protected static final String REVIEW_DECISION_DATE_ID = "reviewElement.decisionDate";

	/**
	 * Field REVIEW_DECISION_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor REVIEW_DECISION_DATE_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_DECISION_DATE_ID, R4EUIConstants.DECISION_DATE_LABEL);
	
	/**
	 * Field REVIEW_REWORK_DATE_ID. (value is ""reviewElement.reworkDate"")
	 */
	protected static final String REVIEW_REWORK_DATE_ID = "reviewElement.reworkDate";

	/**
	 * Field REVIEW_REWORK_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor REVIEW_REWORK_DATE_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_REWORK_DATE_ID, R4EUIConstants.REWORK_DATE_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { REVIEW_NAME_PROPERTY_DESCRIPTOR,  
		REVIEW_START_DATE_PROPERTY_DESCRIPTOR, REVIEW_END_DATE_PROPERTY_DESCRIPTOR, 
		REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR, REVIEW_PROJECT_PROPERTY_DESCRIPTOR,
		REVIEW_COMPONENTS_PROPERTY_DESCRIPTOR, REVIEW_ENTRY_CRITERIA_PROPERTY_DESCRIPTOR,
		REVIEW_OBJECTIVES_PROPERTY_DESCRIPTOR, REVIEW_REFERENCE_MATERIAL_PROPERTY_DESCRIPTOR, 
		REVIEW_PHASE_PROPERTY_DESCRIPTOR, REVIEW_PHASE_OWNER_PROPERTY_DESCRIPTOR,
		REVIEW_PREPARATION_DATE_PROPERTY_DESCRIPTOR, REVIEW_DECISION_DATE_PROPERTY_DESCRIPTOR,
		REVIEW_REWORK_DATE_PROPERTY_DESCRIPTOR};
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ReviewExtraProperties.
	 * @param aElement R4EUIModelElement
	 */
	public ReviewExtraProperties(R4EUIModelElement aElement) {
		super(aElement);
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getPropertyDescriptors.
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}
	
	/**
	 * Method getPropertyValue.
	 * 
	 * @param aId Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		final Object result = super.getPropertyValue(aId);
		if (null != result) return result;
    	R4EReview review = ((R4EUIReviewExtended)getElement()).getReview();
	    if (REVIEW_PHASE_OWNER_ID.equals(aId)) {
	    	return ((R4EFormalReview)review).getCurrent().getPhaseOwnerID();
		} else if (REVIEW_PREPARATION_DATE_ID.equals(aId)) {
			return ((R4EFormalReview)((R4EUIReviewExtended)getElement()).getReview()).getCurrent().getEndDate().toString();
		} else if (REVIEW_DECISION_DATE_ID.equals(aId)) {
			return ((R4EFormalReview)((R4EUIReviewExtended)getElement()).getReview()).getCurrent().getEndDate().toString();
		} else if (REVIEW_REWORK_DATE_ID.equals(aId)) {
			return ((R4EFormalReview)((R4EUIReviewExtended)getElement()).getReview()).getCurrent().getEndDate().toString();
		}
		return null;
	}
	//NOTE:  Since state management for anomalies is complex, the value are only editable using the tabbed properties view

}
