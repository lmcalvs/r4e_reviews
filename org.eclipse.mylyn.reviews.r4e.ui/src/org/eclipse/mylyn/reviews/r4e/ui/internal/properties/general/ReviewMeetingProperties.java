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
 * This class encapsulates the properties for the Review Decision Meeting model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import java.util.Date;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewMeetingProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_MEETING_SUBJECT_ID. (value is ""reviewElement.meetingSubject"")
	 */
	protected static final String REVIEW_MEETING_SUBJECT_ID = "reviewElement.meetingSubject";

	/**
	 * Field REVIEW_MEETING_SUBJECT_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_MEETING_SUBJECT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_MEETING_SUBJECT_ID, R4EUIConstants.SUBJECT_LABEL);

	/**
	 * Field REVIEW_MEETING_DATE_ID. (value is ""reviewElement.meetingDate"")
	 */
	protected static final String REVIEW_MEETING_DATE_ID = "reviewElement.meetingDate";

	/**
	 * Field REVIEW_MEETING_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_MEETING_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_MEETING_DATE_ID, R4EUIConstants.START_DATE_LABEL);

	/**
	 * Field REVIEW_MEETING_DURATION_ID. (value is ""reviewElement.meetingDuration"")
	 */
	protected static final String REVIEW_MEETING_DURATION_ID = "reviewElement.meetingDuration";

	/**
	 * Field REVIEW_MEETING_DURATION_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_MEETING_DURATION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_MEETING_DURATION_ID, R4EUIConstants.DURATION_LABEL);

	/**
	 * Field REVIEW_MEETING_LOCATION_ID. (value is ""reviewElement.meetingLocation"")
	 */
	protected static final String REVIEW_MEETING_LOCATION_ID = "reviewElement.meetingLocation";

	/**
	 * Field REVIEW_MEETING_LOCATION_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_MEETING_LOCATION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_MEETING_LOCATION_ID, R4EUIConstants.LOCATION_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { REVIEW_MEETING_SUBJECT_PROPERTY_DESCRIPTOR,
			REVIEW_MEETING_DATE_PROPERTY_DESCRIPTOR, REVIEW_MEETING_DURATION_PROPERTY_DESCRIPTOR,
			REVIEW_MEETING_LOCATION_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewExtraProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public ReviewMeetingProperties(R4EUIModelElement aElement) {
		super(aElement);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getPropertyDescriptors.
	 * 
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
	 * @param aId
	 *            Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		if (null != getElement()) {
			if (REVIEW_MEETING_SUBJECT_ID.equals(aId)) {
				final R4EMeetingData meeting = ((R4EUIReviewBasic) getElement()).getReview().getActiveMeeting();
				if (null != meeting) {
					return meeting.getSubject();
				}
			} else if (REVIEW_MEETING_DATE_ID.equals(aId)) {
				final R4EMeetingData meeting = ((R4EUIReviewBasic) getElement()).getReview().getActiveMeeting();
				if (null != meeting) {
					return new Date(meeting.getStartTime());
				}
			} else if (REVIEW_MEETING_DURATION_ID.equals(aId)) {
				final R4EMeetingData meeting = ((R4EUIReviewBasic) getElement()).getReview().getActiveMeeting();
				if (null != meeting) {
					return Integer.valueOf(meeting.getDuration());
				}
			} else if (REVIEW_MEETING_LOCATION_ID.equals(aId)) {
				final R4EMeetingData meeting = ((R4EUIReviewBasic) getElement()).getReview().getActiveMeeting();
				if (null != meeting) {
					return meeting.getLocation();
				}
			}
		}
		return null;
	}
}
