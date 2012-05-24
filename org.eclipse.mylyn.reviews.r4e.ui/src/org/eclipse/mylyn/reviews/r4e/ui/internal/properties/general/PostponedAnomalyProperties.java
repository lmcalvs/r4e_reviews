/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class encapsulates the properties for the Postponed Anomaly UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class PostponedAnomalyProperties extends AnomalyExtraProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field POSTPONED_ANOMALY_REVIEW_ID. (value is ""postponedAnomalyElement.reviewName"")
	 */
	protected static final String POSTPONED_ANOMALY_REVIEW_ID = "postponedAnomalyElement.reviewName";

	/**
	 * Field POSTPONED_ANOMALY_REVIEW_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor POSTPONED_ANOMALY_REVIEW_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			POSTPONED_ANOMALY_REVIEW_ID, R4EUIConstants.ORIGINAL_REVIEW_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { POSTPONED_ANOMALY_REVIEW_PROPERTY_DESCRIPTOR,
			ANOMALY_TITLE_PROPERTY_DESCRIPTOR, ANOMALY_POSITION_PROPERTY_DESCRIPTOR,
			ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR, ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR,
			ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR, ANOMALY_STATE_PROPERTY_DESCRIPTOR,
			ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR, ANOMALY_CLASS_PROPERTY_DESCRIPTOR, ANOMALY_RANK_PROPERTY_DESCRIPTOR,
			ANOMALY_RULE_ID_PROPERTY_DESCRIPTOR, ANOMALY_NOT_ACCEPTED_REASON_PROPERTY_DESCRIPTOR,
			ANOMALY_DECIDED_BY_PROPERTY_DESCRIPTOR, ANOMALY_FIXED_BY_PROPERTY_DESCRIPTOR,
			ANOMALY_FOLLOWUP_BY_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for AnomalyProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public PostponedAnomalyProperties(R4EUIModelElement aElement) {
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
		final Object result = super.getPropertyValue(aId);
		if (null != result) {
			return result;
		}
		if (POSTPONED_ANOMALY_REVIEW_ID.equals(aId)) {
			return ((R4EUIPostponedAnomaly) getElement()).getOriginalReviewName();
		}
		return null;
	}
	//NOTE:  Since state management for anomalies is complex, the value are only editable using the tabbed properties view
}
