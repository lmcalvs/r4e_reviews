/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson AB - Initial implementation SMTP mail
 * 
 */
package org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.commons.core.StatusHandler;
import org.eclipse.mylyn.reviews.notifications.core.IMeetingData;
import org.eclipse.mylyn.reviews.notifications.core.NotificationFilter;
import org.eclipse.mylyn.reviews.notifications.core.NotificationsCore;
import org.eclipse.mylyn.reviews.notifications.spi.NotificationsConnector;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.SmtpPlugin;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.MailData;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.SMTPHostString;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.dialogs.ScheduleMeetingInputDialog;
import org.eclipse.mylyn.reviews.r4e.mail.smtp.mailVersion.internal.preferences.SmtpHostPreferencePage;
import org.eclipse.mylyn.reviews.vcalendar.core.VCalendar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class Smtp extends NotificationsConnector {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for Smtp.
	 */
	public Smtp() {
		setEnable();
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Set the flag for the connector when running on any environment
	 */
	private void setEnable() {
		enabled = true;
	}

	/**
	 * Creates and send an e-mail. Will try for each host defined to send e-mail with the main SMP servers. The catch
	 * block of the caller would look like this } catch (MessagingException aEex) { if (try all defined SMTP server) {
	 * throw new CoreException(new Status(Status.ERROR, FPLUGIN_ID, ex.getMessage())
	 * 
	 * @param aEmailFrom
	 *            String
	 * @param aEmailsTo
	 *            String[]
	 * @param aSubject
	 *            String
	 * @param aBody
	 *            String
	 * @param aAttachment
	 *            String
	 * @param aFilter
	 *            NotificationFilter
	 * @throws CoreException
	 * @return Boolean OK
	 */
	@Override
	public Boolean sendEmail(String aEmailFrom, String[] aEmailsTo, String aSubject, String aBody, String aAttachment,
			NotificationFilter aFilter) throws CoreException {
		// The SMTP connection will be tried in this order stored in the preference page. 
		// After all try the exception is thrown to the caller.

		String[] smtpHost = getSMTPHost();
		int numHost = smtpHost.length;
		for (int i = 0; i < numHost; i++) {
			if (smtpHost[i].trim().length() == 0) {
				//There is no SMTP server defined
				final ErrorDialog dialog = new ErrorDialog(
						null,
						SMTPHostString.getString("dialog_title_error"),
						SMTPHostString.getString("smtp_emtpy_server"),
						new Status(
								IStatus.ERROR,
								SmtpPlugin.FPLUGIN_ID,
								0,
								"You should define the SMTP server under \n Window -> Preferences-> R4E-> SMTP Host, \n No E-mail sent",
								null), IStatus.ERROR);
				dialog.open();
				return false;
			}
			try {
				createAndSendEmail(smtpHost[i], aEmailFrom, aEmailsTo, aSubject, aBody, aAttachment);
				break;
			} catch (MessagingException aEex) {
				if ((i + 1) >= numHost) {
					StringBuilder sb = new StringBuilder();
					sb.append("ComponentObjectModelException: ");
					sb.append(aEex.getMessage());
					throw new CoreException(new Status(IStatus.ERROR, SmtpPlugin.FPLUGIN_ID, sb.toString()) {
					});
				}
			}

		}
		return true;
	}

	/**
	 * method sendEmailGraphical
	 * 
	 * @param aEmailFrom
	 *            String
	 * @param aEmailsTo
	 *            String[]
	 * @param aSubject
	 *            String
	 * @param aBody
	 *            String
	 * @param aAttachment
	 *            String
	 * @param aFilter
	 *            NotificationFilter
	 * @throws CoreException
	 *             * @see org.eclipse.mylyn.reviews.notifications.NotificationConnector#sendEmailGraphical(String,
	 *             String[], String, String, String, NotificationFilter)
	 */
	@Override
	public void sendEmailGraphical(String aEmailFrom, String[] aEmailsTo, String aSubject, String aBody,
			String aAttachment, NotificationFilter aFilter) throws CoreException {

		final MailDialog mailD = new MailDialog(getShell());
		mailD.create();
		mailD.setMailInfo(aEmailsTo, aSubject, aBody, aAttachment);

		final int ok = mailD.open();
		if (ok == IDialogConstants.OK_ID) {
			//Send the email message
			final MailData mailData = mailD.getEmailData();
			sendEmail(aEmailFrom, mailData.getSendTo(), mailData.getSubject(), mailData.getBody(),
					mailData.getAttachment(), aFilter);
		}
	}

	/**
	 * method createMeetingRequest
	 * 
	 * @param aSubject
	 *            String
	 * @param aBody
	 *            String
	 * @param aEmailsTo
	 *            String[]
	 * @param aStartDate
	 *            Long
	 * @param aDuration
	 *            Integer
	 * @param aLocation
	 *            - String
	 * @return IMeetingData
	 * @throws CoreException
	 * @see org.eclipse.mylyn.reviews.notifications.NotificationConnector#createMeetingRequest(String, String, String[],
	 *      Long, Integer)
	 */
	@Override
	public IMeetingData createMeetingRequest(String aSubject, String aBody, String[] aEmailsTo, Long aStartDate,
			Integer aDuration, String aLocation) throws CoreException {
		IMeetingData r4eMeetingData = null;

		final ScheduleMeetingInputDialog dialog = new ScheduleMeetingInputDialog(getShell());
		dialog.create();
		dialog.setStartTime(aStartDate);
		dialog.setDuration(aDuration);
		dialog.setLocation(aLocation);

		final int result = dialog.open();
		if (result == Window.OK) {
			final String customID = aSubject + System.currentTimeMillis();
			final String defaultUserId = System.getProperty("user.name");

			//Lets create a Vcalendar
			r4eMeetingData = NotificationsCore.createMeetingData(customID, aSubject, aBody, dialog.getLocation(),
					dialog.getStartTime().longValue(), dialog.getDuration().intValue(), defaultUserId, aEmailsTo);
			final VCalendar vcal = new VCalendar();
			final String vcalAttachment = vcal.createVCalendar(r4eMeetingData, defaultUserId, aEmailsTo);

			//Now send the message
			Boolean ok = sendEmail(defaultUserId, aEmailsTo, r4eMeetingData.getSubject(), r4eMeetingData.getBody(),
					vcalAttachment, null);
			if (!ok) {
				//If not send, then re-initialize the data structure
				r4eMeetingData = null;
			}
		}

		return r4eMeetingData;
	}

	/**
	 * method openAndUpdateMeeting
	 * 
	 * @param aMeetingData
	 *            IMeetingData
	 * @param aSearchFrom
	 *            Date
	 * @param aForceUpdate
	 *            - true if meeting data values should be taken from the argument values, false otherwise
	 * @return IMeetingData
	 * @see org.eclipse.mylyn.reviews.notifications.NotificationConnector#openAndUpdateMeeting(IMeetingData, Date,
	 *      boolean, boolean)
	 */
	@Override
	public IMeetingData openAndUpdateMeeting(IMeetingData aMeetingData, Date aSearchFrom, boolean aForceUpdate) {

		//NOTE:  Since we do not store data here, we assume aForceUpdate and aShowDialog are always true
		IMeetingData newMeetingData = null;
		final ScheduleMeetingInputDialog dialog = new ScheduleMeetingInputDialog(getShell());
		dialog.create();

		//Set the received value
		final Long zoneStartTime = Long.valueOf(aMeetingData.getStartTime().longValue());

		dialog.setStartTime(zoneStartTime);
		dialog.setDuration(aMeetingData.getDuration());
		dialog.setLocation(aMeetingData.getLocation());

		final int result = dialog.open();
		if (result == Window.OK) {
			final String defaultUserId = System.getProperty("user.name");

			//Lets create a Vcalendar
			try {
				newMeetingData = NotificationsCore.createMeetingData(aMeetingData.getCustomID(),
						aMeetingData.getSubject(), aMeetingData.getBody(), dialog.getLocation(), dialog.getStartTime(),
						dialog.getDuration().intValue(), aMeetingData.getSender(), aMeetingData.getReceivers());
			} catch (CoreException e) {
				StatusHandler.log(new Status(IStatus.ERROR, SmtpPlugin.FPLUGIN_ID, IStatus.OK, e.toString(), e));
			}
			final VCalendar vcal = new VCalendar();
			final String vcalAttachment = vcal.createVCalendar(newMeetingData, defaultUserId,
					aMeetingData.getReceivers());

			//Now send the message
			if (null != newMeetingData) {
				try {
					Boolean ok = sendEmail(defaultUserId, newMeetingData.getReceivers(), newMeetingData.getSubject(),
							newMeetingData.getBody(), vcalAttachment, null);
					if (!ok) {
						//If not send, then re-initialize the data structure
						newMeetingData = null;
					}
				} catch (CoreException e) {
					StatusHandler.log(new Status(IStatus.ERROR, SmtpPlugin.FPLUGIN_ID, IStatus.OK, e.toString(), e));
				}
			}
		}
		return newMeetingData;
	}

	/**
	 * method fetchSystemMeetingData
	 * 
	 * @param aLocalData
	 *            IMeetingData
	 * @param aSearchFrom
	 *            Date
	 * @return IMeetingData
	 * @see org.eclipse.mylyn.reviews.notifications.NotificationConnector#fetchSystemMeetingData(IMeetingData, Date)
	 */
	@Override
	public IMeetingData fetchSystemMeetingData(IMeetingData aLocalData, Date aSearchFrom) {
		//We do not store any data so just echo back what was passed-in
		return aLocalData;
	}

	/**
	 * method createAndSendEmail
	 * 
	 * @param aSMTPServer
	 *            String
	 * @param aFrom
	 *            String
	 * @param aEmails
	 *            String[]
	 * @param aSubject
	 *            String
	 * @param aBody
	 *            String
	 * @param aAttachment
	 *            String
	 * @throws MessagingException
	 *             * @see
	 *             org.eclipse.mylyn.reviews.notifications.NotificationConnector#fetchSystemMeetingData(IMeetingData,
	 *             Date)
	 */
	private static void createAndSendEmail(String aSMTPServer, String aFrom, String[] aEmails, String aSubject,
			String aBody, String aAttachment) throws MessagingException {
		final Properties props = new Properties();
		props.setProperty("mail.smtp.host", aSMTPServer);
		final Session session = Session.getInstance(props, null);
		final Message msg = new MimeMessage(session);
		if (aFrom != null && aFrom != "") {
			msg.setFrom(new InternetAddress(aFrom));
		}

		for (String aEmail : aEmails) {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(aEmail));
		}

		msg.setSubject(aSubject);
		if (null == aAttachment) {

			// Regular message
			msg.setText(aBody);

		} else {
			//Message with a vCalendar in attachment
			//Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			//Fill the message
			messageBodyPart.setText(aBody);

			//Create a Multipart
			final Multipart multipart = new MimeMultipart();

			//Add part one
			multipart.addBodyPart(messageBodyPart);

			//Part two is attachment
			//Create second body part
			messageBodyPart = new MimeBodyPart();
			final String filename = "review calendar.vcs";
			messageBodyPart.setFileName(filename);
			messageBodyPart.setContent(aAttachment, "text/plain");

			//Add part two
			multipart.addBodyPart(messageBodyPart);

			//Put parts in message
			msg.setContent(multipart);
		}
		msg.setSentDate(new Date());
		Transport.send(msg);
	}

	/**
	 * method getShell
	 * 
	 * @return Shell
	 */
	private Shell getShell() {
		final Shell sh = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		return sh;
	}

	private String[] getSMTPHost() {
		String[] host = null;
		SmtpHostPreferencePage prefOrder = new SmtpHostPreferencePage();
		host = prefOrder.getSmtpServer();
		return host;
	}
}
