/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson AB - Initial implementation
 * 
 */
package org.eclipse.mylyn.reviews.vcalendar.core;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.mylyn.reviews.notifications.core.IMeetingData;

/**
 * @author Jacques Bouthillier
 *
 * @version $Revision: 1.0 $
 */
public class VCalendar {

	private long fTIME_ZONE_OFFSET = TimeZone.getDefault().getOffset(
			System.currentTimeMillis());

	private final String fNEW_LINE = "\n";

	private final String fGMT_TIME = "Z";

	private final SimpleDateFormat fDATE_FORMAT = new SimpleDateFormat(
			"yyyyMMdd'T'HHmmss", new Locale("eng", "US"));

	public VCalendar() {
	}

	/**
	 * Build the VCalendar and return it as a String
	 * 
	 * @param aInfo
	 *            Meeting data information
	 * @param aMsgBody
	 * @param aFrom
	 *            Creator of the calendar
	 * @param aEmails
	 *            List of email's of the recipients
	 * @return
	 */
	public String createVCalendar(IMeetingData aInfo, String aFrom,
			String[] aEmails) {
		StringBuffer sb = new StringBuffer();
		sb.append("BEGIN:VCALENDAR");
		sb.append(fNEW_LINE);
		sb.append("PRODID:-//Microsoft Corporation//Outlook 11.0 MIMEDIR//EN");
		sb.append(fNEW_LINE);
		sb.append("VERSION:1.0");
		sb.append(fNEW_LINE);
		sb.append("BEGIN:VEVENT");
		sb.append(fNEW_LINE);
		sb.append("ATTENDEE:");
		sb.append(getAttendee(aEmails));
		sb.append(fNEW_LINE);
		sb.append("DTSTART:");
		sb.append(getStartDate(aInfo));
		sb.append(fGMT_TIME);
		sb.append(fNEW_LINE);
		sb.append("DTEND:");
		sb.append(getEndDate(aInfo));
		sb.append(fGMT_TIME);
		sb.append(fNEW_LINE);
		sb.append("LOCATION:");
		sb.append(aInfo.getLocation());
		sb.append(fNEW_LINE);

		sb.append("DESCRIPTION;ENCODING=QUOTED-PRINTABLE:");
		sb.append(reformatMessageBody(aInfo.getBody()));
		sb.append("=0D=0A");
		sb.append(fNEW_LINE);
		sb.append("SUMMARY;ENCODING=QUOTED-PRINTABLE:");
		sb.append(aInfo.getSubject());
		sb.append(fNEW_LINE);
		sb.append("PRIORITY:3");
		sb.append(fNEW_LINE);
		sb.append("END:VEVENT");
		sb.append(fNEW_LINE);
		sb.append("END:VCALENDAR");

		return sb.toString();

	}

	/**
	 * Reformat the content of the message to replace "\n" char by the
	 * appropriate value to be used in the vCalendar
	 * 
	 * @param aBody
	 *            String
	 * @return String
	 */
	private String reformatMessageBody(String aBody) {

		String lineSeparator = System.getProperty("line.separator");

		String str = aBody.trim().replaceAll(lineSeparator, "=0D=0A"); //line feed + carriage return 

		str = str.replaceAll("\n", "=0D=0A"); //new line 

		str = str.replaceAll("\r", "=0D=0A"); //CR

		return str;


	}

	/**
	 * Get the list of attendee email's to include in the Vcalendar
	 * 
	 * @return String
	 */
	private String getAttendee(String[] aEmails) {
		StringBuffer sb = new StringBuffer();
		int size = aEmails.length;

		for (int i = 0; i < size; i++) {
			sb.append("\"MAILTO:");
			sb.append(aEmails[i]);
			if (i < (size - 1)) {
				sb.append("\","); // Separator between attendees
			} else {
				sb.append('"'); // End quotes only
			}
		}
		return sb.toString();

	}

	/**
	 * Compute a date the decision meeting should take place
	 * 
	 * @return String
	 */
	private String getStartDate(IMeetingData aInfo) {
		return fDATE_FORMAT.format(aInfo.getStartTime() - fTIME_ZONE_OFFSET);
	}

	/**
	 * Compute the end meeting date and time
	 * 
	 * @return String
	 */
	private String getEndDate(IMeetingData aInfo) {
		Long timeDuration= (long) (aInfo.getDuration() * 60 * 1000); //convert minutes to millisec
		return fDATE_FORMAT.format(aInfo.getStartTime() + timeDuration- fTIME_ZONE_OFFSET);
	}

}
