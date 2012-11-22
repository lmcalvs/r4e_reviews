/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson AB - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.notifications.spi;

import java.util.Date;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.mylyn.reviews.notifications.core.IMeetingData;
import org.eclipse.mylyn.reviews.notifications.core.NotificationFilter;

/**
 * @author Alvaro Sanchez-Leon
 * @author Jacques Bouthillier
 */
public abstract class NotificationsConnector {
	// ------------------------------------------------------------------------
	// Instance variables
	// ------------------------------------------------------------------------
	protected boolean enabled = false;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * @param aEmailFrom
	 * @param aEmailsTo
	 * @param aSubject
	 * @param aBody
	 * @param aAttachment
	 * @param aFilter
	 * @return Boolean OK
	 * @throws CoreException
	 */
	public abstract Boolean sendEmail(String aEmailFrom, String[] aEmailsTo, String aSubject, String aBody,
			String aAttachment, NotificationFilter aFilter) throws CoreException;

	/**
	 * @param aEmailFrom
	 * @param aEmailsTo
	 * @param aSubject
	 * @param aBody
	 * @param aAttachment
	 * @param aFilter
	 * @throws CoreException
	 */
	public abstract void sendEmailGraphical(String aEmailFrom, String[] aEmailsTo, String aSubject, String aBody,
			String aAttachment, NotificationFilter aFilter) throws CoreException;

	/**
	 * @param aSubject
	 * @param aBody
	 * @param aEmailsTo
	 * @param startDate
	 *            - proposed start date with time for the meeting
	 * @param duration
	 *            - proposed duration
	 * @return
	 * @throws CoreException
	 */
	public abstract IMeetingData createMeetingRequest(String aSubject, String aBody, String[] aEmailsTo,
			Long startDate, Integer duration, String location) throws CoreException;

	/**
	 * Open the dialog from the system and allow the user to update as necessary
	 * 
	 * @param aMeetingData
	 * @param searchFrom
	 *            - narrow down the search window, 6 months before current date is recommended
	 * @param forceUpdate
	 *            - true if meeting data values should be taken from the argument values, false otherwise
	 * @return IMeetingData
	 */
	public abstract IMeetingData openAndUpdateMeeting(IMeetingData aMeetingData, Date searchFrom, boolean forceUpdate);

	/**
	 * Fetch the meeting data from the calendar system using the local data
	 * 
	 * @param aLocalData
	 * @param searchFrom
	 * @return
	 */
	public abstract IMeetingData fetchSystemMeetingData(IMeetingData aLocalData, Date searchFrom);

	/**
	 * A connector can be available but disabled if the requirements to function are not met e.g. internal dependencies
	 * to the OS
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}

}
