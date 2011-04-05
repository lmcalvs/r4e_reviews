/**
 * Copyright (c) 2011 Ericsson and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson Research Canada - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.notifications.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.core.runtime.CoreException;

/**
 * 
 * @author Alvaro Sanchez-Leon
 */
public class MeetingData implements IMeetingData {
	private static long M_TIME_ZONE_OFFSET = TimeZone.getDefault().getOffset(
			System.currentTimeMillis());

	public static String[] getAttributeTypes() {
		String[] types = { "Subject", "Location", "Start Time", "End Time" };
		return types;
	}

	private String	m_customID;

	private String	m_subject;

	private String	m_location;

	private String	m_startTime;

	private String	m_endTime;

	private int		fSentCounter	= 0;

	public MeetingData(String customId, String subject, String location,
			long startTimeMilli, long endTimeMilli)
			throws CoreException {
		m_customID = customId;
		m_subject = subject;
		m_location = location;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		long dateMilli = startTimeMilli - M_TIME_ZONE_OFFSET;
		m_startTime = dateFormat.format(new Date(dateMilli));
		dateMilli = endTimeMilli - M_TIME_ZONE_OFFSET;
		m_endTime = dateFormat.format(new Date(dateMilli));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getCustomID()
	 */
	public String getCustomID() {
		return m_customID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getSubject()
	 */
	public String getSubject() {
		return m_subject;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getLocation()
	 */
	public String getLocation() {
		return m_location;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getStartTime()
	 */
	public String getStartTime() {
		return m_startTime;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getEndTime()
	 */
	public String getEndTime() {
		return m_endTime;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getAttributeValues()
	 */
	public String[] getAttributeValues() {
		String[] values = { m_subject, m_location, m_startTime, m_endTime, };
		return values;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getSentCounter()
	 */
	public int getSentCounter() {
		return fSentCounter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#incrementSentCounter()
	 */
	public void incrementSentCounter() {
		fSentCounter++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return m_subject + ", " + m_location + ", " + m_startTime + ", "
				+ m_endTime;
	}

}
