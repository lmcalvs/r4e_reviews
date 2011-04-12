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
import org.eclipse.mylyn.reviews.notifications.core.NotificationsCore;
import org.eclipse.mylyn.reviews.notifications.spi.NotificationsConnector;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
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
	
	private final static String LINE_FEED_MSG_PART = System.getProperty("line.separator");
	
	private final static String TAB_MSG_PART = "\t";

	private final static String SUBJECT_MSG_HEADER = " Review ";

	private final static String INTRO_MSG_BODY = "Hi,";
	
	private final static String OUTRO_MSG_BODY = "Best Regards,";

	private final static String ITEMS_READY_MSG_BODY = "The following Review Item(s) and Files are Ready for you to Review";

	private final static String REMOVED_ITEMS_MSG_BODY = "The following Review Item(s) and Files have been Removed." + LINE_FEED_MSG_PART +
		"Please Refresh your Review if it is currently Open";
	
	private final static String PROGRESS_MESSAGE = "Progress Update: " + LINE_FEED_MSG_PART;

	private final static String COMPLETION_MESSAGE = "I have Completed this Review, see Details below: " + LINE_FEED_MSG_PART;

	private final static String QUESTION_MSG_BODY = "I have a question concerning the following ";
	

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
    /**
     * Method sendItemsReadyNotification
     * @throws ResourceHandlingException 
     * @throws CoreException 
     */
    public static void sendItemsReadyNotification() throws CoreException, ResourceHandlingException {
    	String messageBody = createItemsReadyNotificationMessage();
    	String messageSubject = createSubject() + " - Items Ready for Review";
    	String[] messageDestinations = createReviewerDestinations();
    	sendMessage(messageDestinations, messageSubject, messageBody);
    	//TODO combine with meeting request for formal reviews
    }
    
    /**
     * Method sendItemsRemovedNotification
     * @throws ResourceHandlingException 
     * @throws CoreException 
     */
    public static void sendItemsRemovedNotification() throws CoreException, ResourceHandlingException {
    	String messageBody = createRemovedItemsNotificationMessage();
    	String messageSubject = createSubject() + " - Items Removed from Review";
    	String[] messageDestinations = createReviewerDestinations();
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendProgressNotification
     * @throws ResourceHandlingException 
     * @throws CoreException 
     */
    public static void sendProgressNotification() throws CoreException, ResourceHandlingException {
    	String messageBody = createProgressNotification(PROGRESS_MESSAGE);
    	String messageSubject = createSubject() + " - Participant Progress";
    	String[] messageDestinations = createLeadDestinations();
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendCompletionNotification
     * @throws ResourceHandlingException 
     * @throws CoreException 
     */
    public static void sendCompletionNotification() throws CoreException, ResourceHandlingException {
    	String messageBody = createProgressNotification(COMPLETION_MESSAGE);
    	String messageSubject = createSubject() + " - Participant Progress (Completed)";
    	String[] messageDestinations = createLeadDestinations();
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }
    
    /**
     * Method sendQuestion
     * @throws ResourceHandlingException 
     * @throws CoreException 
     */
    public static void sendQuestion(Object source) throws CoreException, ResourceHandlingException {
    	String messageBody = createQuestionMessage(source);
    	String messageSubject = createSubject() + " - Question regarding review " + 
    		R4EUIModelController.getActiveReview().getName();
    	String[] messageDestinations = null;
    	if (source instanceof R4EUIAnomalyBasic) {
    		messageDestinations = createAnomalyCreatorDestination((R4EUIAnomalyBasic)source);
    	} else {
        	messageDestinations = createAuthorDestinations();
    	}
    	sendMessage(messageDestinations, messageSubject, messageBody);
    }

    /**
     * Method sendMessage
     * @param aDestinations String[]
     * @param aSubject String
     * @param aBody String
     */
    public static void sendMessage(String[] aDestinations, String aSubject, String aBody) throws CoreException, ResourceHandlingException {
    	NotificationsConnector mailService = NotificationsCore.getFirstEnabled(null);
    	mailService.sendEmail(R4EUIModelController.getActiveReview().getParticipant(R4EUIModelController.getReviewer(), false).getEmail(),
    			aDestinations, aSubject, aBody, null, null);
    }
    
    /**
     * Method createSubject
     * @return String
     */
	private static String createSubject() {
		StringBuilder subject = new StringBuilder();
		subject.append("[r4e-mail] ");
		subject.append(SUBJECT_MSG_HEADER);
		subject.append(R4EUIModelController.getActiveReview().getName());
		return subject.toString();
	}
	
    /**
     * Method createReviewerDestinations
     * @return String[]
     */
	private static String[] createReviewerDestinations() {
		ArrayList<String> destinations = new ArrayList<String>();
		List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		for (R4EParticipant participant : participants) {
			if (participant.getRoles().contains(R4EUserRole.R4E_ROLE_REVIEWER) &&
					null != participant.getEmail()) {
				destinations.add(participant.getEmail());
			}
		}
		return destinations.toArray(new String[destinations.size()]);
	}
	
    /**
     * Method createLeadDestinations
     * @return String[]
     */
	private static String[] createLeadDestinations() {
		ArrayList<String> destinations = new ArrayList<String>();
		List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		for (R4EParticipant participant : participants) {
			if (participant.getRoles().contains(R4EUserRole.R4E_ROLE_LEAD) &&
					null != participant.getEmail()) {
				destinations.add(participant.getEmail());
			}
		}
		return destinations.toArray(new String[destinations.size()]);
	}
	
    /**
     * Method createAnomalyCreatorDestination
     * @return String
     */
	private static String[] createAnomalyCreatorDestination(R4EUIAnomalyBasic aAnomaly) {
		ArrayList<String> destinations = new ArrayList<String>();
		destinations.add(aAnomaly.getAnomaly().getUser().getEmail());
		return destinations.toArray(new String[destinations.size()]);
	}
	
    /**
     * Method createAuthorDestinations
     * @return String[]
     */
	private static String[] createAuthorDestinations() {
		ArrayList<String> destinations = new ArrayList<String>();
		List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		for (R4EParticipant participant : participants) {
			if (participant.getRoles().contains(R4EUserRole.R4E_ROLE_AUTHOR) &&
					null != participant.getEmail()) {
				destinations.add(participant.getEmail());
			}
		}
		return destinations.toArray(new String[destinations.size()]);
	}
	
    /**
     * Method createItemsReadyNotificationMessage
     * @return String
     */
    private static String createItemsReadyNotificationMessage() {
    	StringBuilder msgBody = new StringBuilder();
    	
    	msgBody.append(createIntroPart());
    	msgBody.append(ITEMS_READY_MSG_BODY + LINE_FEED_MSG_PART + LINE_FEED_MSG_PART);

    	List<R4EUIReviewItem> items = R4EUIModelController.getActiveReview().getReviewItems();
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
}
