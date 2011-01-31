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
 * This class encapsulates the properties for the Participant UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ParticipantProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field PARTICIPANT_ID. (value is ""participantElement.id"")
	 */
	private static final String PARTICIPANT_ID = "participantElement.id";

	/**
	 * Field PARTICIPANT_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_ID, R4EUIConstants.ID_LABEL);
	
	/**
	 * Field PARTICIPANT_NUM_ITEMS_ID. (value is ""participantElement.numItems"")
	 */
	private static final String PARTICIPANT_NUM_ITEMS_ID = "participantElement.numItems";

	/**
	 * Field PARTICIPANT_NUM_ITEMS_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_NUM_ITEMS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_NUM_ITEMS_ID, R4EUIConstants.NUM_ITEMS_LABEL);
	
	/**
	 * Field PARTICIPANT_NUM_ANOMALIES_ID. (value is ""participantElement.numAnomalies"")
	 */
	private static final String PARTICIPANT_NUM_ANOMALIES_ID = "participantElement.numAnomalies";

	/**
	 * Field PARTICIPANT_NUM_ANOMALIES_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_NUM_ANOMALIES_PROPERTY_DESCR = new PropertyDescriptor(
			PARTICIPANT_NUM_ANOMALIES_ID, R4EUIConstants.NUM_ANOMALIES_LABEL);
	
	/**
	 * Field PARTICIPANT_NUM_COMMENTS_ID. (value is ""participantElement.numComments"")
	 */
	private static final String PARTICIPANT_NUM_COMMENTS_ID = "participantElement.numComments";

	/**
	 * Field PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_NUM_COMMENTS_ID, R4EUIConstants.NUM_COMMENTS_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { PARTICIPANT_PROPERTY_DESCRIPTOR,  
		PARTICIPANT_NUM_ITEMS_PROPERTY_DESCRIPTOR, PARTICIPANT_NUM_ANOMALIES_PROPERTY_DESCR, 
		PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ParticipantProperties.
	 * @param aElement R4EUIModelElement
	 */
	public ParticipantProperties(R4EUIModelElement aElement) {
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
	 * @param aId Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */

	@Override
	public Object getPropertyValue(Object aId) {
		if (PARTICIPANT_ID.equals(aId)) { 
			return ((R4EUIParticipant)getElement()).getParticipant().getId();
		} else if (PARTICIPANT_NUM_ITEMS_ID.equals(aId)) {
			return Integer.valueOf(((R4EUIParticipant)getElement()).getParticipant().getAddedItems().size());
		} else if (PARTICIPANT_NUM_ANOMALIES_ID.equals(aId)) {
			int numAnomalies = 0;
			final EList<R4EComment> comments = ((R4EUIParticipant)getElement()).getParticipant().getAddedComments();
			final int commentsSize = comments.size();
			for (int i = 0; i < commentsSize; i++) {
				if (comments.get(i) instanceof R4EAnomaly) {
					++numAnomalies;
				}
			}
			return Integer.valueOf(numAnomalies);
		} else if (PARTICIPANT_NUM_COMMENTS_ID.equals(aId)) {
			int numComments = 0;
			final EList<R4EComment> comments = ((R4EUIParticipant)getElement()).getParticipant().getAddedComments();
			final int commentsSize = comments.size();
			for (int i = 0; i < commentsSize; i++) {
				if (!(comments.get(i) instanceof R4EAnomaly)) {
					++numComments;
				}
			}
			return Integer.valueOf(numComments);
		}
		return null;
	}
}
