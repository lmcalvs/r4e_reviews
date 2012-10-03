/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.notifications.core.IMeetingData;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;

/**
 * This class implements the IMeetingData interface used in the notifications plugin
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIMeetingData implements IMeetingData {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	private final R4EMeetingData fMeetingData;

	private final ResourceUpdater fResUpdater = R4EUIModelController.FResourceUpdater;

	// ------------------------------------------------------------------------
	// Contructor
	// ------------------------------------------------------------------------

	/**
	 * R4EUIMeetingData constructor
	 * 
	 * @param aMeetingData
	 */
	public R4EUIMeetingData(R4EMeetingData aMeetingData) {
		fMeetingData = aMeetingData;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getSentCounter
	 * 
	 * @return int
	 */
	public int getSentCounter() {
		return fMeetingData.getSentCount();
	}

	/**
	 * Method incrementSentCounter
	 */
	public void incrementSentCounter() {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setSentCount(fMeetingData.getSentCount() + 1);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method clearSentCounter
	 */
	public void clearSentCounter() {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setSentCount(0);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getCustomID
	 * 
	 * @return String
	 */
	public String getCustomID() {
		return fMeetingData.getId();
	}

	/**
	 * Method setCustomID
	 * 
	 * @param aId
	 *            - String
	 */
	public void setCustomID(String aId) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setId(aId);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getSubject
	 * 
	 * @return String
	 */
	public String getSubject() {
		return fMeetingData.getSubject();
	}

	/**
	 * Method setSubject
	 * 
	 * @param aSubject
	 *            - String
	 */
	public void setSubject(String aSubject) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setSubject(aSubject);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getBody
	 * 
	 * @return String
	 */
	public String getBody() {
		return fMeetingData.getBody();
	}

	/**
	 * Method setBody
	 * 
	 * @param aBody
	 *            - String
	 */
	public void setBody(String aBody) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setBody(aBody);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getLocation
	 * 
	 * @return String
	 */
	public String getLocation() {
		return fMeetingData.getLocation();
	}

	/**
	 * Method setLocation
	 * 
	 * @param aLocation
	 *            - String
	 */
	public void setLocation(String aLocation) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setLocation(aLocation);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getStartTime
	 * 
	 * @return Long
	 */
	public Long getStartTime() {
		return Long.valueOf(fMeetingData.getStartTime() + R4EUIConstants.TIME_ZONE_OFFSET);
	}

	/**
	 * Method setStartTime
	 * 
	 * @param aStartTime
	 *            - Long
	 */
	public void setStartTime(Long aStartTime) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setStartTime(aStartTime.longValue());
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getDuration
	 * 
	 * @return Integer
	 */
	public Integer getDuration() {
		return Integer.valueOf(fMeetingData.getDuration());
	}

	/**
	 * Method setDuration
	 * 
	 * @param aDuration
	 *            - Integer
	 */
	public void setDuration(Integer aDuration) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setDuration(aDuration.intValue());
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getSender
	 * 
	 * @return String
	 */
	public String getSender() {
		return fMeetingData.getSender();
	}

	/**
	 * Method setSender
	 * 
	 * @param aSender
	 *            - String
	 */
	public void setSender(String aSender) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.setSender(aSender);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method getReceivers
	 * 
	 * @return String[]
	 */
	public String[] getReceivers() {
		final List<String> receiversRaw = fMeetingData.getReceivers();
		List<String> receiversFiltered = new ArrayList<String>();
		//Remove invalid entries
		for (String entry : receiversRaw) {
			if (null != entry && !entry.trim().equals("")) {
				receiversFiltered.add(entry);
			}
		}
		final String[] receivers = receiversFiltered.toArray(new String[receiversFiltered.size()]);
		return receivers;
	}

	/**
	 * Method clearReceivers
	 */
	public void clearReceivers() {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.getReceivers().clear();
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method addReceiver
	 * 
	 * @param aReceiver
	 *            - String
	 */
	public void addReceiver(String aReceiver) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.getReceivers().add(aReceiver);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method removeReceiver
	 * 
	 * @param aReceiver
	 *            - String
	 */
	public void removeReceiver(String aReceiver) {
		try {
			final Long bookNum = fResUpdater.checkOut(fMeetingData, R4EUIModelController.getReviewer());
			fMeetingData.getReceivers().remove(aReceiver);
			fResUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
	}

	/**
	 * Method equals
	 * 
	 * @param aData
	 *            - IMeetingData
	 */
	public boolean equals(IMeetingData aData) {
		if (null != aData && this.getLocation().equals(aData.getLocation())
				&& this.getDuration().intValue() == aData.getDuration().intValue()
				&& this.getStartTime().longValue() == aData.getStartTime().longValue()) {
			return true;
		}
		return false;
	}
}
