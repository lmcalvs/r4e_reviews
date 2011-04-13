// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class provides general utility methods used in the UI implementation
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.mylyn.reviews.notifications.core.IMeetingData;
import org.eclipse.mylyn.reviews.notifications.core.NotificationsCore;
import org.eclipse.mylyn.reviews.notifications.spi.NotificationsConnector;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class MailServicesProxy {
	
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field LINE_FEED_MSG_PART.
	 */
	private static final String LINE_FEED_MSG_PART = System.getProperty("line.separator");
	
	/**
	 * Field TAB_MSG_PART.
	 * (value is ""\t"")
	 */
	private static final String TAB_MSG_PART = "\t";

	/**
	 * Field SUBJECT_MSG_HEADER.
	 * (value is "" Review "")
	 */
	private static final String SUBJECT_MSG_HEADER = " Review ";

	/**
	 * Field INTRO_MSG_BODY.
	 * (value is ""Hi,"")
	 */
	private static final String INTRO_MSG_BODY = "Hi,";
	
	/**
	 * Field OUTRO_MSG_BODY.
	 * (value is ""Best Regards,"")
	 */
	private static final String OUTRO_MSG_BODY = "Best Regards,";

	/**
	 * Field ITEMS_READY_MSG_BODY.
	 * (value is ""The following Review Item(s) and Files are Ready for you to Review"")
	 */
	private static final String ITEMS_READY_MSG_BODY = "The following Review Item(s) and Files are Ready for you to Review";

	/**
	 * Field MEETING_REQUEST_MSG_BODY.
	 * (value is ""This invitation is for the decision phase." + LINE_FEED_MSG_PART + 
		"Please review the included items prior to the meeting."")
	 */
	private static final String MEETING_REQUEST_MSG_BODY = "This invitation is for the decision phase." + LINE_FEED_MSG_PART + 
		"Please review the included items prior to the meeting.";
	
	/**
	 * Field REMOVED_ITEMS_MSG_BODY.
	 * (value is ""The following Review Item(s) and Files have been Removed." + LINE_FEED_MSG_PART +
		"Please Refresh your Review if it is currently Open"")
	 */
	private static final String REMOVED_ITEMS_MSG_BODY = "The following Review Item(s) and Files have been Removed." + LINE_FEED_MSG_PART +
		"Please Refresh your Review if it is currently Open";
	
	/**
	 * Field PROGRESS_MESSAGE.
	 * (value is ""Progress Update: " + LINE_FEED_MSG_PART")
	 */
	private static final String PROGRESS_MESSAGE = "Progress Update: " + LINE_FEED_MSG_PART;

	/**
	 * Field COMPLETION_MESSAGE.
	 * (value is ""I have Completed this Review, see Details below: " + LINE_FEED_MSG_PART")
	 */
	private static final String COMPLETION_MESSAGE = "I have Completed this Review, see Details below: " + LINE_FEED_MSG_PART;

	/**
	 * Field QUESTION_MSG_BODY.
	 * (value is ""I have a Question concerning the Following "")
	 */
	private static final String QUESTION_MSG_BODY = "I have a Question concerning the Following ";
	

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Notifications
	
    /**
     * Method sendItemsReadyNotification
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendItemsReadyNotification() throws CoreException, ResourceHandlingException {
    	final String[] messageDestinations = createDestinations(R4EUserRole.R4E_ROLE_REVIEWER);
    	final String messageSubject = createSubject() + " - Items Ready for Review";
    	final String messageBody = createItemsReadyNotificationMessage(false);
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendItemsRemovedNotification
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendItemsRemovedNotification() throws CoreException, ResourceHandlingException {
    	final String[] messageDestinations = createDestinations(R4EUserRole.R4E_ROLE_REVIEWER);
    	final String messageSubject = createSubject() + " - Items Removed from Review";
    	final String messageBody = createRemovedItemsNotificationMessage();
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendProgressNotification
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendProgressNotification() throws CoreException, ResourceHandlingException {
    	final String[] messageDestinations = createDestinations(R4EUserRole.R4E_ROLE_LEAD);
    	final String messageSubject = createSubject() + " - Participant Progress";
    	final String messageBody = createProgressNotification(PROGRESS_MESSAGE);
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendCompletionNotification
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendCompletionNotification() throws CoreException, ResourceHandlingException {
    	final String[] messageDestinations = createDestinations(R4EUserRole.R4E_ROLE_LEAD);
    	final String messageSubject = createSubject() + " - Participant Progress (Completed)";
    	final String messageBody = createProgressNotification(COMPLETION_MESSAGE);
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendQuestion
     * @param aSource Object
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendQuestion(Object aSource) throws CoreException, ResourceHandlingException {
    	String[] messageDestinations = null;
    	if (aSource instanceof R4EUIAnomalyBasic) {
    		messageDestinations = createAnomalyCreatorDestination((R4EUIAnomalyBasic)aSource);
    	} else {
        	messageDestinations = createDestinations(R4EUserRole.R4E_ROLE_AUTHOR);
    	}
    	final String messageSubject = createSubject() + " - Question regarding review ";
    	final String messageBody = createQuestionMessage(aSource);
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }

    /**
     * Method sendMessage
     * @param aDestinations String[]
     * @param aSubject String
     * @param aBody String
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendMessage(String[] aDestinations, String aSubject, String aBody) throws CoreException, ResourceHandlingException {
    	final NotificationsConnector mailService = NotificationsCore.getFirstEnabled(null);
    	mailService.sendEmail(R4EUIModelController.getActiveReview().getParticipant(R4EUIModelController.getReviewer(), false).getEmail(),
    			aDestinations, aSubject, aBody, null, null);
    }
    
    /**
     * Method createSubject
     * @return String
     */
	private static String createSubject() {
		final StringBuilder subject = new StringBuilder();
		subject.append("[r4e-mail] ");
		subject.append(SUBJECT_MSG_HEADER);
		subject.append(R4EUIModelController.getActiveReview().getName());
		return subject.toString();
	}
	
    /**
     * Method createReviewerDestinations
     * @param aRole R4EUserRole
     * @return String[]
     */
	private static String[] createDestinations(R4EUserRole aRole) {
		final ArrayList<String> destinations = new ArrayList<String>();
		final List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		for (R4EParticipant participant : participants) {
			if ((null == aRole || participant.getRoles().contains(aRole)) &&
					null != participant.getEmail()) {
				destinations.add(participant.getEmail());
			}
		}
		return destinations.toArray(new String[destinations.size()]);
	}
	
    /**
     * Method createAnomalyCreatorDestination
     * @param aAnomaly R4EUIAnomalyBasic
     * @return String
     */
	private static String[] createAnomalyCreatorDestination(R4EUIAnomalyBasic aAnomaly) {
		final ArrayList<String> destinations = new ArrayList<String>();
		destinations.add(aAnomaly.getAnomaly().getUser().getEmail());
		return destinations.toArray(new String[destinations.size()]);
	}
	
    /**
     * Method createItemsReadyNotificationMessage
     * @param aMeetingRequestIncluded boolean
     * @return String
     */
    private static String createItemsReadyNotificationMessage(boolean aMeetingRequestIncluded) {
    	StringBuilder msgBody = new StringBuilder();
    	
    	msgBody.append(createIntroPart());
    	if (aMeetingRequestIncluded) {
    		msgBody.append(MEETING_REQUEST_MSG_BODY + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);
    	} else {
    		msgBody.append(ITEMS_READY_MSG_BODY + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);
    	}
    	final List<R4EUIReviewItem> items = R4EUIModelController.getActiveReview().getReviewItems();
    	for (R4EUIReviewItem item : items) {
    		if (!item.isEnabled()) {
    			msgBody.append("Review Item: " + item.getName() + LINE_FEED_MSG_PART);
    			msgBody.append("Eclipse Project:File Path Relative to Eclipse Project" + LINE_FEED_MSG_PART);
    			R4EUIFileContext[] contexts = (R4EUIFileContext[]) item.getChildren();
    			for (R4EUIFileContext context : contexts) {
    				if (!context.isEnabled()) {
    					msgBody.append(TAB_MSG_PART + context.getTargetFile().getProject() + ": " +
    							context.getTargetFile().getProjectRelativePath() + LINE_FEED_MSG_PART);
    					//TODO later add line ranges for selections
    				}
    			}
    		}
    	}
    	msgBody.append(createReviewInfoPart());
		msgBody.append(createOutroPart());
    	return msgBody.toString();
    }
    
    /**
     * Method createRemovedItemsNotificationMessage
     * @return String
     */
    private static String createRemovedItemsNotificationMessage() {
    	StringBuilder msgBody = new StringBuilder();
    	
    	msgBody.append(createIntroPart());
    	msgBody.append(REMOVED_ITEMS_MSG_BODY + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);

    	List<R4EUIReviewItem> items = R4EUIModelController.getActiveReview().getReviewItems();
    	for (R4EUIReviewItem item : items) {
    		if (item.isEnabled()) {
    			msgBody.append("Review Item: " + item.getName() + LINE_FEED_MSG_PART);
    			msgBody.append("Eclipse Project:File Path Relative to Eclipse Project" + LINE_FEED_MSG_PART);
    			R4EUIFileContext[] contexts = (R4EUIFileContext[]) item.getChildren();
    			for (R4EUIFileContext context : contexts) {
    				if (context.isEnabled()) {
    					msgBody.append(TAB_MSG_PART + context.getTargetFile().getProject() + ": " +
    							context.getTargetFile().getProjectRelativePath() + LINE_FEED_MSG_PART);
    				}
    			}
    		}
    	}

    	msgBody.append(createReviewInfoPart());
		msgBody.append(createOutroPart());
    	return msgBody.toString();
    }
    
    /**
     * Method createProgressNotification
     * @param aHeader String
     * @return String
     */
    private static String createProgressNotification(String aHeader) {
    	StringBuilder msgBody = new StringBuilder();
    	
    	msgBody.append(createIntroPart());
    	msgBody.append(aHeader + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);

    	int numReviewedFiles = 0;
    	int numTotalFiles = 0;
    	List<String> anomaliesStr = new ArrayList<String>();
    	List<R4EUIReviewItem> items = R4EUIModelController.getActiveReview().getReviewItems();
    	for (R4EUIReviewItem item : items) {
			R4EUIFileContext[] contexts = (R4EUIFileContext[])item.getChildren();
			for (R4EUIFileContext context : contexts) {
				if (context.isReviewed()) ++numReviewedFiles;
				++numTotalFiles;
				R4EUIAnomalyBasic[] anomalies = 
					(R4EUIAnomalyBasic[]) ((R4EUIAnomalyContainer)context.getAnomalyContainerElement()).getChildren();
				for (R4EUIAnomalyBasic anomaly : anomalies) {
					if (anomaly.getAnomaly().getUser().getId().equals(R4EUIModelController.getReviewer())) {
						anomaliesStr.add(anomaly.getPosition().toString() + ": " + context.getTargetFile().getProject() +
								context.getTargetFile().getProjectRelativePath() + LINE_FEED_MSG_PART +
								TAB_MSG_PART + anomaly.getAnomaly().getTitle() + ": " + anomaly.getAnomaly().getDescription() +
								LINE_FEED_MSG_PART);
					}
				}
			}
    	}
    	
    	//Add current review progress
    	msgBody.append("Files Reviewed: " + numReviewedFiles);
    	msgBody.append("Files Total: " + numTotalFiles);
    	double progress = (numReviewedFiles / new Integer(numTotalFiles).doubleValue()) * 100;
		DecimalFormat fmt = new DecimalFormat("#");
    	msgBody.append("Progress: " + fmt.format(progress) + "%");
    	msgBody.append(LINE_FEED_MSG_PART);

    	//Add anomalies created by current reviewer
    	msgBody.append("Anomalies Created by: " + R4EUIModelController.getReviewer() + LINE_FEED_MSG_PART);
    	msgBody.append("Count: " + anomaliesStr.size() + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);
    	if (anomaliesStr.size() > 0) {
    		msgBody.append(TAB_MSG_PART + "Line Range: Eclipse Project: File Path Relative to Eclipse Project" + LINE_FEED_MSG_PART);
    		msgBody.append(TAB_MSG_PART + "Description" + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);
    	}
    	for (String anomalyStr : anomaliesStr) {
    		msgBody.append(anomalyStr);
    	}
    	msgBody.append(LINE_FEED_MSG_PART);

    	msgBody.append(createReviewInfoPart());
		msgBody.append(createOutroPart());
    	return msgBody.toString();
    }
    
    /**
     * Method createQuestionMessage
     * @param aSource Object
     * @return String
     */
    private static String createQuestionMessage(Object aSource) {
    	StringBuilder msgBody = new StringBuilder();
    	msgBody.append(createIntroPart());
    	msgBody.append(QUESTION_MSG_BODY);

    	if (aSource instanceof R4EUIAnomalyBasic) {
    		R4EFileVersion file = 
    			((R4EAnomalyTextPosition)((R4EContent)((R4EUIAnomalyBasic)aSource).getAnomaly().getLocation().get(0)).getLocation()).getFile();
    		msgBody.append("Anomaly :" + LINE_FEED_MSG_PART);
    		msgBody.append("File: " + file.getName() + LINE_FEED_MSG_PART);
    		msgBody.append("Version: " + file.getVersionID()+ LINE_FEED_MSG_PART);
    		msgBody.append("Line(s): " + ((R4EUIAnomalyBasic)aSource).getPosition().toString()+ LINE_FEED_MSG_PART);
			msgBody.append("Title: " + ((R4EUIAnomalyBasic)aSource).getAnomaly().getTitle()+ LINE_FEED_MSG_PART);
			msgBody.append("Description: " + ((R4EUIAnomalyBasic)aSource).getAnomaly().getDescription()+ LINE_FEED_MSG_PART);
    	} else if (aSource instanceof R4EUIReviewItem) {
    		msgBody.append("Review Item :" + LINE_FEED_MSG_PART);
    		msgBody.append("Description: " + ((R4EUIReviewItem)aSource).getItem().getDescription());
    	} else if (aSource instanceof R4EUIFileContext) {
    		msgBody.append("File Context:" + LINE_FEED_MSG_PART);
    		msgBody.append("File: " + ((R4EUIFileContext)aSource).getFileContext().getTarget().getName() + LINE_FEED_MSG_PART);
    		msgBody.append("Base Version: " + ((R4EUIFileContext)aSource).getFileContext().getBase().getVersionID() + LINE_FEED_MSG_PART);
    		msgBody.append("Target Version: " + ((R4EUIFileContext)aSource).getFileContext().getTarget().getVersionID() + LINE_FEED_MSG_PART);
    	} else if (aSource instanceof R4EUISelection) {
       		R4EFileVersion file = 
    			((R4EUIFileContext)((R4EUISelection)aSource).getParent().getParent()).getTargetFileVersion();
    		msgBody.append("Selection :" + LINE_FEED_MSG_PART);
    		msgBody.append("File: " + file.getName() + LINE_FEED_MSG_PART);
    		msgBody.append("Version: " + file.getVersionID()+ LINE_FEED_MSG_PART);
    		msgBody.append("Line(s): " + ((R4EUISelection)aSource).getPosition().toString() + LINE_FEED_MSG_PART);
    	}
    	msgBody.append(createReviewInfoPart());
		msgBody.append(createOutroPart());
    	return msgBody.toString();
    }
    
    /**
     * Method createReviewInfoPart
     * @return String
     */
    private static String createReviewInfoPart() {
    	StringBuilder msgReviewInfo = new StringBuilder();
    	
    	msgReviewInfo.append(LINE_FEED_MSG_PART);
    	msgReviewInfo.append("Group: " + R4EUIModelController.getActiveReview().getParent().getName() +
    			LINE_FEED_MSG_PART);
    	msgReviewInfo.append("Review: " + R4EUIModelController.getActiveReview().getName() +
    			LINE_FEED_MSG_PART);
    	msgReviewInfo.append("Components: " + LINE_FEED_MSG_PART);
    	List<String> components = R4EUIModelController.getActiveReview().getReview().getComponents();
    	for (String component : components) {
    		msgReviewInfo.append(TAB_MSG_PART + component + LINE_FEED_MSG_PART);
    	}
    	msgReviewInfo.append("Project: " + R4EUIModelController.getActiveReview().getReview().getProject());
    	msgReviewInfo.append("Participants: ");
    	List<String> participants = R4EUIModelController.getActiveReview().getParticipantIDs();
    	for (String participant : participants) {
    		msgReviewInfo.append(TAB_MSG_PART + participant + LINE_FEED_MSG_PART);
    	}
    	msgReviewInfo.append(LINE_FEED_MSG_PART);
    	
    	return msgReviewInfo.toString();
    }
    
    /**
     * Method createIntroPart
     * @return String
     */
    private static String createIntroPart() {
    	StringBuilder msgIntro = new StringBuilder();
    	msgIntro.append(LINE_FEED_MSG_PART);
    	msgIntro.append(INTRO_MSG_BODY);
    	msgIntro.append(LINE_FEED_MSG_PART);
    	msgIntro.append(LINE_FEED_MSG_PART);
    	return msgIntro.toString();
    }
    
    /**
     * Method createOutroPart
     * @return String
     */
    private static String createOutroPart() {
    	StringBuilder msgOutro = new StringBuilder();
    	msgOutro.append(OUTRO_MSG_BODY);
    	msgOutro.append(R4EUIModelController.getReviewer());
    	return msgOutro.toString();
    }
    
    
    //Meetings
    
    /**
     * Method sendMeetingRequest
     * @param aStartDate Long
     * @param aDuration Integer
     * @param aLocation String
     * @throws CoreException
     * @throws ResourceHandlingException
     */
    public static void sendMeetingRequest(Long aStartDate, Integer aDuration, String aLocation ) throws CoreException, ResourceHandlingException {
    	String[] messageDestinations = createDestinations(null);
    	String messageSubject = null;
    	if (null != R4EUIModelController.getActiveReview().getReview().getActiveMeeting()) {
    		messageSubject = createSubject() + " - Decision Meeting Request Updated";
    	} else {
    		messageSubject = createSubject() + " - Items Ready for Review & Decision Meeting Request";
    	}
    	String messageBody = createItemsReadyNotificationMessage(true);
    	sendMeetingRequest(messageDestinations, messageSubject, messageBody, aStartDate, aDuration);
    }
    
    /**
     * Method sendMessage
     * @param aDestinations String[]
     * @param aSubject String
     * @param aBody String
     * @param aStartDate Long
     * @param aDuration Integer
     * @throws CoreException
     */
    public static void sendMeetingRequest(String[] aDestinations, String aSubject, String aBody, 
    		Long aStartDate, Integer aDuration) throws CoreException {
    	NotificationsConnector mailService = NotificationsCore.getFirstEnabled(null);
    	IMeetingData meetingData = mailService.createMeetingRequest(aSubject, aBody, aDestinations, aStartDate, aDuration);
    	R4EReview review = R4EUIModelController.getActiveReview().getReview();
    	//review.setActiveMeeting(meetingData);
    	//review.getActiveMeeting().setSentCount((review.getActiveMeeting().getSentCount() + 1));
    	//TODO verify if we need to do anything else to send the email
    	//TODO the core should be changed to accept IMeetingData
    }
}