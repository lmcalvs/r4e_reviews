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
 * This class encapsulates the properties for the Review Phase model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.general;

import java.util.Date;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public  class ReviewPhaseProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_PHASE_ID. (value is ""reviewElement.phase"")
	 */
	protected static final String REVIEW_PHASE_ID = "reviewElement.phase";

	/**
	 * Field REVIEW_PHASE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_PHASE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_PHASE_ID, R4EUIConstants.PHASE_LABEL);

	/**
	 * Field REVIEW_PHASE_OWNER_ID. (value is ""reviewElement.phaseOwner"")
	 */
	protected static final String REVIEW_PHASE_OWNER_ID = "reviewElement.phaseOwner";

	/**
	 * Field REVIEW_PHASE_OWNER_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_PHASE_OWNER_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_PHASE_OWNER_ID, R4EUIConstants.PHASE_OWNER_LABEL);

	/**
	 * Field REVIEW_PHASE_START_DATE_ID. (value is ""reviewElement.phaseStartDate"")
	 */
	protected static final String REVIEW_PHASE_START_DATE_ID = "reviewElement.phaseStartDate";

	/**
	 * Field REVIEW_PHASE_START_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_PHASE_START_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_PHASE_START_DATE_ID, R4EUIConstants.START_DATE_LABEL);

	/**
	 * Field REVIEW_PHASE_END_DATE_ID. (value is ""reviewElement.phaseEndDate"")
	 */
	protected static final String REVIEW_PHASE_END_DATE_ID = "reviewElement.phaseEndDate";

	/**
	 * Field REVIEW_PHASE_END_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_PHASE_END_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_PHASE_END_DATE_ID, R4EUIConstants.END_DATE_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = {
		REVIEW_PHASE_PROPERTY_DESCRIPTOR, REVIEW_PHASE_OWNER_PROPERTY_DESCRIPTOR,
		REVIEW_PHASE_START_DATE_PROPERTY_DESCRIPTOR, REVIEW_PHASE_END_DATE_PROPERTY_DESCRIPTOR};


	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public ReviewPhaseProperties(R4EUIModelElement aElement) {
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
		if (null != getElement()) {
			if (REVIEW_PHASE_ID.equals(aId)) {
				if (getElement() instanceof R4EUIReviewExtended) {
					return ((R4EUIReviewBasic)getElement()).getPhaseString(
							((R4EFormalReview)((R4EUIReviewExtended)getElement()).
							getReview()).getCurrent().getType());
				}
				return ((R4EUIReviewBasic)getElement()).getPhaseString(
						((R4EReviewState)((R4EUIReviewBasic)getElement()).getReview().getState()).getState());	
			} else if (REVIEW_PHASE_OWNER_ID.equals(aId)) {
				if (getElement() instanceof R4EUIReviewExtended) {
					return (((R4EFormalReview)((R4EUIReviewExtended)getElement()).
							getReview()).getCurrent().getPhaseOwnerID());
				}
			} else if (REVIEW_PHASE_START_DATE_ID.equals(aId)) {
				if (getElement() instanceof R4EUIReviewExtended) {
					return (((R4EFormalReview)((R4EUIReviewExtended)getElement()).
							getReview()).getCurrent().getStartDate());
				}
			} else if (REVIEW_PHASE_END_DATE_ID.equals(aId)) {
				if (getElement() instanceof R4EUIReviewExtended) {
					Date endDate = ((R4EFormalReview)((R4EUIReviewExtended)getElement()).
							getReview()).getCurrent().getEndDate();
					if (null != endDate) {
						return endDate;
					}
					return "(In Progress)";
				}
			}
		}
		return null;
	}
}
