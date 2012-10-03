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
package org.eclipse.mylyn.reviews.notifications.core;

/**
 * @author Alvaro Sanchez-Leon
 * 
 */
public interface IMeetingData {

	/**
	 * Unique id associated to the meeting instance
	 * 
	 * @return
	 */
	public abstract String getCustomID();

	/**
	 * Replace the id for the given one
	 * 
	 * @param aId
	 */
	public abstract void setCustomID(String aId);

	/**
	 * @return
	 */
	public abstract String getSubject();

	/**
	 * @param aSubject
	 */
	public abstract void setSubject(String aSubject);

	/**
	 * Body of the message within Meeting appointment
	 * 
	 * @return
	 */
	public abstract String getBody();

	/**
	 * @param aBody
	 */
	public abstract void setBody(String aBody);
	/**
	 * Physical location information e.g. room details
	 * 
	 * @return
	 */
	public abstract String getLocation();

	/**
	 * @param aLocation
	 */
	public abstract void setLocation(String aLocation);
	/**
	 * recommended to reflect a UTC value
	 * 
	 * @return
	 */
	public abstract Long getStartTime();

	/**
	 * @param aStartTime
	 */
	public abstract void setStartTime(Long aStartTime);

	/**
	 * Meeting duration in minutes
	 * 
	 * @return
	 */
	public abstract Integer getDuration();

	/**
	 * @param aDuration
	 */
	public abstract void setDuration(Integer aDuration);
	/**
	 * The organiser of the meeting (e-mail address)
	 * 
	 * @return
	 */
	public abstract String getSender();

	/**
	 * @param aSender
	 */
	public abstract void setSender(String aSender);

	/**
	 * The invited participants (e-mail addresses)
	 * 
	 * @return
	 */
	public abstract String[] getReceivers();

	/**
	 * Remove all receivers from the receivers list
	 */
	public abstract void clearReceivers();

	/**
	 * Add a new receiver to the list
	 * 
	 * @param aReceiver
	 */
	public abstract void addReceiver(String aReceiver);

	/**
	 * Remove the given receiver from the list
	 * 
	 * @param aReceiver
	 */
	public abstract void removeReceiver(String aReceiver);

	/**
	 * An indication of the number of times and invitation with this instance has been sent
	 * 
	 * @return
	 */
	public abstract int getSentCounter();

	/**
	 * counter which shall be used to record the number of times this instance of the meeting has been successfully sent
	 */
	public abstract void incrementSentCounter();

	/**
	 * Reset the sent counter
	 */
	public abstract void clearSentCounter();


}