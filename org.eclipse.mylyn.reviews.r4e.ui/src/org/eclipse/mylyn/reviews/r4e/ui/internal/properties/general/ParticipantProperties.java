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

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import java.util.Date;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
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
	 * Field AUTHOR. (value is ""AUTHOR"")
	 */
	private static final String AUTHOR = "AUTHOR";

	/**
	 * Field LEAD. (value is ""LEAD"")
	 */
	private static final String LEAD = "LEAD";

	/**
	 * Field ORGANIZER. (value is ""ORGANIZER"")
	 */
	private static final String ORGANIZER = "ORGANIZER";

	/**
	 * Field REVIEWER. (value is ""REVIEWER"")
	 */
	private static final String REVIEWER = "REVIEWER";

	/**
	 * Field PARTICIPANT_ID_ID. (value is ""participantElement.id"")
	 */
	protected static final String PARTICIPANT_ID_ID = "participantElement.id";

	/**
	 * Field PARTICIPANT_ID_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_ID_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_ID_ID, R4EUIConstants.ID_LABEL);

	/**
	 * Field PARTICIPANT_EMAIL_ID. (value is ""participantElement.email"")
	 */
	protected static final String PARTICIPANT_EMAIL_ID = "participantElement.email";

	/**
	 * Field PARTICIPANT_EMAIL_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_EMAIL_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_EMAIL_ID, R4EUIConstants.EMAIL_LABEL);

	/**
	 * Field PARTICIPANT_NUM_ITEMS_ID. (value is ""participantElement.numItems"")
	 */
	protected static final String PARTICIPANT_NUM_ITEMS_ID = "participantElement.numItems";

	/**
	 * Field PARTICIPANT_NUM_ITEMS_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_NUM_ITEMS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_NUM_ITEMS_ID, R4EUIConstants.NUM_ITEMS_LABEL);

	/**
	 * Field PARTICIPANT_NUM_ANOMALIES_ID. (value is ""participantElement.numAnomalies"")
	 */
	protected static final String PARTICIPANT_NUM_ANOMALIES_ID = "participantElement.numAnomalies";

	/**
	 * Field PARTICIPANT_NUM_ANOMALIES_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_NUM_ANOMALIES_PROPERTY_DESCR = new PropertyDescriptor(
			PARTICIPANT_NUM_ANOMALIES_ID, R4EUIConstants.NUM_ANOMALIES_LABEL);

	/**
	 * Field PARTICIPANT_NUM_COMMENTS_ID. (value is ""participantElement.numComments"")
	 */
	protected static final String PARTICIPANT_NUM_COMMENTS_ID = "participantElement.numComments";

	/**
	 * Field PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_NUM_COMMENTS_ID, R4EUIConstants.NUM_COMMENTS_LABEL);

	/**
	 * Field PARTICIPANT_DETAILS_ID. (value is ""participantElement.details"")
	 */
	protected static final String PARTICIPANT_DETAILS_ID = "participantElement.details";

	/**
	 * Field PARTICIPANT_DETAILS_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_DETAILS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_DETAILS_ID, R4EUIConstants.USER_DETAILS_LABEL);

	/**
	 * Field PARTICIPANT_TIME_SPENT_ID. (value is ""participantElement.timeSpent"")
	 */
	protected static final String PARTICIPANT_TIME_SPENT_ID = "participantElement.timeSpent";

	/**
	 * Field PARTICIPANT_TIME_SPENT_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor PARTICIPANT_TIME_SPENT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_TIME_SPENT_ID, R4EUIConstants.TIME_SPENT_TOTAL_LABEL);

	/**
	 * Field PARTICIPANT_ROLES_ID. (value is ""participantElement.roles"")
	 */
	private static final String PARTICIPANT_ROLES_ID = "participantElement.roles";

	/**
	 * Field PARTICIPANT_ROLES_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_ROLES_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_ROLES_ID, R4EUIConstants.ROLES_LABEL);

	/**
	 * Field PARTICIPANT_FOCUS_AREA_ID. (value is ""participantElement.focusArea"")
	 */
	private static final String PARTICIPANT_FOCUS_AREA_ID = "participantElement.focusArea";

	/**
	 * Field PARTICIPANT_FOCUS_AREA_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_FOCUS_AREA_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_FOCUS_AREA_ID, R4EUIConstants.FOCUS_AREA_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { PARTICIPANT_ID_PROPERTY_DESCRIPTOR,
			PARTICIPANT_EMAIL_PROPERTY_DESCRIPTOR, PARTICIPANT_NUM_ITEMS_PROPERTY_DESCRIPTOR,
			PARTICIPANT_NUM_ANOMALIES_PROPERTY_DESCR, PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR,
			PARTICIPANT_DETAILS_PROPERTY_DESCRIPTOR, PARTICIPANT_TIME_SPENT_PROPERTY_DESCRIPTOR,
			PARTICIPANT_ROLES_PROPERTY_DESCRIPTOR, PARTICIPANT_FOCUS_AREA_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ParticipantProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public ParticipantProperties(R4EUIModelElement aElement) {
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
	 * Method mapUserRole.
	 * 
	 * @param aRole
	 *            R4EUserRole
	 * @return String
	 */
	private String mapUserRole(R4EUserRole aRole) {
		if (aRole.equals(R4EUserRole.R4E_ROLE_AUTHOR)) {
			return AUTHOR;
		}
		if (aRole.equals(R4EUserRole.R4E_ROLE_LEAD)) {
			return LEAD;
		}
		if (aRole.equals(R4EUserRole.R4E_ROLE_ORGANIZER)) {
			return ORGANIZER;
		}
		if (aRole.equals(R4EUserRole.R4E_ROLE_REVIEWER)) {
			return REVIEWER;
		} else {
			return "";
		}
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
		if (PARTICIPANT_ID_ID.equals(aId)) {
			return ((R4EUIParticipant) getElement()).getParticipant().getId();
		} else if (PARTICIPANT_EMAIL_ID.equals(aId)) {
			return ((R4EUIParticipant) getElement()).getParticipant().getEmail();
		} else if (PARTICIPANT_NUM_ITEMS_ID.equals(aId)) {
			return Integer.valueOf(((R4EUIParticipant) getElement()).getParticipant().getAddedItems().size());
		} else if (PARTICIPANT_NUM_ANOMALIES_ID.equals(aId)) {
			int numAnomalies = 0;
			final EList<R4EComment> comments = ((R4EUIParticipant) getElement()).getParticipant().getAddedComments();
			final int commentsSize = comments.size();
			for (int i = 0; i < commentsSize; i++) {
				if (comments.get(i) instanceof R4EAnomaly) {
					++numAnomalies;
				}
			}
			return Integer.valueOf(numAnomalies);
		} else if (PARTICIPANT_NUM_COMMENTS_ID.equals(aId)) {
			int numComments = 0;
			final EList<R4EComment> comments = ((R4EUIParticipant) getElement()).getParticipant().getAddedComments();
			final int commentsSize = comments.size();
			for (int i = 0; i < commentsSize; i++) {
				if (!(comments.get(i) instanceof R4EAnomaly)) {
					++numComments;
				}
			}
			return Integer.valueOf(numComments);
		} else if (PARTICIPANT_DETAILS_ID.equals(aId)) {
			return ((R4EUIParticipant) getElement()).getParticipantDetails();
		} else if (PARTICIPANT_TIME_SPENT_ID.equals(aId)) {
			final R4EParticipant modelUser = ((R4EUIParticipant) getElement()).getParticipant();
			final int numTimeEntries = modelUser.getTimeLog().size();
			int totalTimeSpent = 0;
			Entry<Date, Integer> timeEntry = null;
			for (int i = 0; i < numTimeEntries; i++) {
				timeEntry = modelUser.getTimeLog().get(i);
				totalTimeSpent += timeEntry.getValue().intValue();
			}
			return Integer.toString(totalTimeSpent);
		} else if (PARTICIPANT_ROLES_ID.equals(aId)) {
			final EList<R4EUserRole> roles = ((R4EUIParticipant) getElement()).getParticipant().getRoles();
			final StringBuilder rolesStr = new StringBuilder();
			for (R4EUserRole role : roles) {
				rolesStr.append(mapUserRole(role) + ", ");
			}
			return rolesStr.toString().substring(0, rolesStr.length() - 2);
		} else if (PARTICIPANT_FOCUS_AREA_ID.equals(aId)) {
			if (null != ((R4EUIParticipant) getElement()).getParticipant().getFocusArea()) {
				return ((R4EUIParticipant) getElement()).getParticipant().getFocusArea();
			}
			return "";
		}
		return null;
	}
}
