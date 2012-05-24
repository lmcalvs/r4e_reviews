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
package org.eclipse.mylyn.reviews.notifications.internal.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.mylyn.reviews.notifications.core.IMeetingData;

/**
 * 
 * @author Alvaro Sanchez-Leon
 */
public class MeetingData implements IMeetingData {

	public static String[] getAttributeTypes() {
		String[] types = { "Subject", "Location", "Start Time", "Duration", "Body" };
		return types;
	}

	private String	fCcustomID;

	private String	fSubject;

	private String	fBody;

	private String	fLocation;

	private Long	fSartTime;

	private Integer	fDuration;

	private int		fSentCounter	= 0;

	private String		fSender;

	private final Set<String>	fReceivers		= new HashSet<String>();

	/**
	 * @param aCustomId
	 * @param aSubject
	 * @param aBody
	 * @param aLocation
	 * @param aStartTimeMilli
	 * @param aDuration
	 *            - meeting duration in minutes
	 * @param aSender
	 * @param aReceivers
	 * @throws CoreException
	 */
	public MeetingData(String aCustomId, String aSubject, String aBody, String aLocation, Long aStartTimeMilli,
			Integer aDuration, String aSender, String[] aReceivers) throws CoreException {
		fCcustomID = aCustomId;
		fSubject = aSubject;
		fBody = aBody;
		fLocation = aLocation;
		fSartTime = aStartTimeMilli;
		fDuration = aDuration;
		fSender = aSender;
		if (aReceivers != null) {
			for (int i = 0; i < aReceivers.length; i++) {
				String receiver = aReceivers[i];
				fReceivers.add(receiver);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getCustomID()
	 */
	public String getCustomID() {
		return fCcustomID;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getSubject()
	 */
	public String getSubject() {
		return fSubject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getBody()
	 */
	public String getBody() {
		return fBody;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getLocation()
	 */
	public String getLocation() {
		return fLocation;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getStartTime()
	 */
	public Long getStartTime() {
		return fSartTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getDuration()
	 */
	public Integer getDuration() {
		return fDuration;
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
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getSender()
	 */
	public String getSender() {
		return fSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#getReceivers()
	 */
	public String[] getReceivers() {
		String[] receivers = fReceivers.toArray(new String[fReceivers.size()]);
		return receivers;
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
		return fSubject + ", " + fLocation + ", " + fSartTime + ", " + fDuration + ", " + fBody;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setCustomID(java.lang.String)
	 */
	public void setCustomID(String aId) {
		fCcustomID = aId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setSubject(java.lang.String)
	 */
	public void setSubject(String aSubject) {
		fSubject = aSubject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setBody(java.lang.String)
	 */
	public void setBody(String aBody) {
		fBody = aBody;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setLocation(java.lang.String)
	 */
	public void setLocation(String aLocation) {
		fLocation = aLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setStartTime(java.lang.Long)
	 */
	public void setStartTime(Long aStartTime) {
		fSartTime = aStartTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setDuration(java.lang.Integer)
	 */
	public void setDuration(Integer aDuration) {
		fDuration = aDuration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#setSender(java.lang.String)
	 */
	public void setSender(String aSender) {
		fSender = aSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#clearReceivers()
	 */
	public void clearReceivers() {
		fReceivers.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#addReceiver(java.lang.String)
	 */
	public void addReceiver(String aReceiver) {
		if (aReceiver != null) {
			fReceivers.add(aReceiver);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#removeReceiver(java.lang.String)
	 */
	public void removeReceiver(String aReceiver) {
		if (aReceiver != null) {
			fReceivers.remove(aReceiver);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.notifications.core.IMeetingData#clearSentCounter()
	 */
	public void clearSentCounter() {
		fSentCounter = 0;
	}
}
