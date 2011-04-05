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
	 * @return
	 */
	public abstract String getSubject();

	/**
	 * Physical location information
	 * 
	 * @return
	 */
	public abstract String getLocation();

	/**
	 * 
	 * @return
	 */
	public abstract String getStartTime();

	/**
	 * @return
	 */
	public abstract String getEndTime();

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
	 * @return
	 */
	public abstract String[] getAttributeValues();

}