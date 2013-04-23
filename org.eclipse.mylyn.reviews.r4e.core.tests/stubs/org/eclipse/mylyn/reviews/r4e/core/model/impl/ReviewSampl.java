/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation
 *******************************************************************************/

/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;

/**
 * @author Alvaro Sanchez-Leon
 */
public class ReviewSampl {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static RModelFactoryExt fResFactory = SerializeFactory.getModelExtension();

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public static R4EReview createReview(String aName, R4EReviewGroup aGroup) {
		R4EReview fReview = null;

		if (aGroup == null) {
			return null;
		}

		String userId1 = "Tom10";
		String userId2 = "Jerry20";

		// Create Review
		try {
			fReview = fResFactory.createR4EReview(aGroup, aName, userId1);
		} catch (ResourceHandlingException e1) {
			e1.printStackTrace();
		}

		Calendar indCal = new GregorianCalendar(1867, Calendar.JULY, 1);
		Date startDate = indCal.getTime();
		// 2 days after
		Date endDate = new Date(startDate.getTime() + 2 * 24 * 60 * 60 * 1000);
		fReview.setStartDate(startDate);
		fReview.setEndDate(endDate);

		EList<String> projects = aGroup.getAvailableProjects();
		String project = "TBD";
		if (projects != null && projects.size() > 0) {
			project = projects.get(0);
		}

		EList<String> components = aGroup.getAvailableComponents();
		String component = "TBD";
		if (components != null && components.size() > 0) {
			component = components.get(0);
		}

		R4EReviewDecision decision = RModelFactory.eINSTANCE.createR4EReviewDecision();
		decision.setValue(R4EDecision.R4E_REVIEW_DECISION_ACCEPTED);

		List<R4EUserRole> roles = new ArrayList<R4EUserRole>();
		roles.add(R4EUserRole.R4E_ROLE_LEAD);
		int value = 0;
		R4EParticipant participant = null;
		try {
			participant = fResFactory.createR4EParticipant(fReview, userId1, roles);
		} catch (ResourceHandlingException e3) {
			e3.printStackTrace();
		}
		participant.getTimeLog().put(startDate, 30);
		participant.getTimeLog().put(endDate, 30);

		participant.setFocusArea("Performance");
		participant.setIsPartOfDecision(true);
		participant.getGroupPaths().add("c:/users/test/group1/");
		participant.getGroupPaths().add("c:/users/test/group2/");

		Collection<Integer> userTimes = participant.getTimeLog().values();
		for (Iterator<Integer> iterator = userTimes.iterator(); iterator.hasNext();) {
			Integer time = iterator.next();
			value += time;
		}

		R4EParticipant participant2 = null;
		try {
			participant2 = fResFactory.createR4EParticipant(fReview, userId2, roles);
		} catch (ResourceHandlingException e2) {
			e2.printStackTrace();
		}
		participant2.getTimeLog().put(startDate, 30);
		participant2.getTimeLog().put(endDate, 30);
		participant2.setFocusArea("Feature");
		participant2.setIsPartOfDecision(true);
		participant2.getGroupPaths().add("c:/group1/");
		participant2.getGroupPaths().add("c:/group2/");

		userTimes = participant2.getTimeLog().values();
		for (Iterator<Integer> iterator = userTimes.iterator(); iterator.hasNext();) {
			Integer time = iterator.next();
			value += time;
		}

		// Anomaly
		R4EAnomaly anomalyP1 = createAnomalies(participant);
		R4EAnomaly anomalyP2 = createAnomalies(participant2);

		// comments on anomalies createed by other users
		createComments(participant, anomalyP2);
		createComments(participant2, anomalyP1);

		// ITEMS
		createItems(participant);
		createItems(participant2);

		// Review ATTRIBUTES
		fReview.setProject(project);
		fReview.getComponents().add(component);
		fReview.setEntryCriteria("Sample Entry Cryteria");
		fReview.setExtraNotes("Sample extra notes");
		fReview.setObjectives("Sample Objectives");
		fReview.setReferenceMaterial("sample reference material");
		fReview.setEndDate(endDate);
		fReview.setType(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL);

		// REFERENCES LOCAL
		decision.setSpentTime(value);
		fReview.setDecision(decision);

		// REFERENCES - SUPERTYPE
		R4EReviewState revState = RModelFactory.eINSTANCE.createR4EReviewState();
		revState.setState(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION);
		fReview.setState(revState);

		R4ETaskReference task = RModelFactory.eINSTANCE.createR4ETaskReference();
		task.setRepositoryURL("https://bugs.eclipse.org/bugs/show_bug.cgi?id=324328");
		task.setTaskId("324328");
		fReview.setReviewTask(task);

		return fReview;
	}

	private static R4EAnomaly createAnomalies(R4EParticipant participant) {
		R4EAnomaly anomaly = null;
		try {
			anomaly = fResFactory.createR4EAnomaly(participant);
			anomaly.setDescription(participant.getId() + "- Anomaly text here .....");
			anomaly.setTitle("This is sample Title - Anomaly");
			Calendar indCal = new GregorianCalendar(1867, Calendar.JULY, 1);
			Date createdOn = new Date(indCal.getTime().getTime() + 10 * 24 * 60 * 60 * 1000);
			anomaly.setCreatedOn(createdOn);

			R4ETextContent content = fResFactory.createR4ETextContent(anomaly);
			content.setInfo("Write any information here");
			R4EAnomalyTextPosition location = fResFactory.createR4EAnomalyTextPosition(content);
			location.setStartLine(50);
			location.setEndLine(100);
			location.setStartPosition(1230);
			location.setLength(40);

			R4EFileVersion fileVer = fResFactory.createR4EFileVersion(location);
			fileVer.setName("R4ETestFile");
			fileVer.setRepositoryPath("/Dir1/Dir2");
			fileVer.setPlatformURI("platform:/resource/projName/src/dir1/dir2");
			fileVer.setVersionID("8");
		} catch (ResourceHandlingException e2) {
			e2.printStackTrace();
		}

		return anomaly;
	}

	/**
	 * @param participant
	 */
	private static void createComments(R4EParticipant participant, R4EAnomaly anomaly) {
		// Additional comments from two users on the same anomaly
		// Comments
		R4ECommentType commType = RModelFactory.eINSTANCE.createR4ECommentType();
		commType.setType(R4EDesignRuleClass.R4E_CLASS_ERRONEOUS);

		// comment1
		R4EComment comment1 = null;
		try {
			comment1 = fResFactory.createR4EComment(participant, anomaly);
		} catch (ResourceHandlingException e1) {
			e1.printStackTrace();
		}

		comment1.setType(commType);
		comment1.setDescription("This is test comment number 1");

		// comment2
		R4EComment comment2 = null;
		try {
			comment2 = fResFactory.createR4EComment(participant, anomaly);
		} catch (ResourceHandlingException e1) {
			e1.printStackTrace();
		}
		comment2.setType(commType);
		comment2.setDescription("This is test comment number 2");
	}

	/**
	 * @param participant
	 */
	private static void createItems(R4EParticipant participant) {
		String[] itemDescriptions = new String[] { participant.getId() + "-Item to review 11",
				participant.getId() + "-Item to review 12", participant.getId() + "-Item to review 21",
				participant.getId() + "-Item to review 22" };

		R4EItem item;

		Calendar indCal = new GregorianCalendar(1867, Calendar.JULY, 1);
		Date submittedDate = indCal.getTime();

		for (int i = 0; i < itemDescriptions.length; i++) {
			try {
				item = fResFactory.createR4EItem(participant);
				item.setDescription(itemDescriptions[i]);
				item.setRepositoryRef("repository ref.." + i);
				item.getProjectURIs().add("platform:resource/projX" + i);
				item.setAuthorRep("changeAuthor_" + i);
				item.setSubmitted(submittedDate);

				R4EFileContext context = fResFactory.createR4EFileContext(item);
				context.setType(R4EContextType.R4E_ADDED);

				R4EFileVersion fvBase = fResFactory.createR4EBaseFileVersion(context);
				fvBase.setName("file_" + i);
				fvBase.setRepositoryPath("root/folder_" + i);
				fvBase.setPlatformURI("platform:/resource/proj/src/dir3/dir4/file.xxx");
				fvBase.setVersionID(Integer.toString(itemDescriptions[i].hashCode()));
				fvBase.setLocalVersionID("locIdB_1234_" + i);

				R4EFileVersion fvTarget = fResFactory.createR4ETargetFileVersion(context);
				fvTarget.setName("file_" + i);
				fvTarget.setRepositoryPath("root/folder_" + i);
				fvTarget.setPlatformURI("platform:/resource/proj/src/dir6/dir7/file.yyy");
				fvTarget.setVersionID(Integer.toString(itemDescriptions[i].hashCode() + 1));
				fvTarget.setLocalVersionID("locIdT_1234_" + i);

				R4EDelta delta = fResFactory.createR4EDelta(context);

				R4ETextContent baseContent = fResFactory.createR4EBaseTextContent(delta);
				baseContent.setContent("base content");
				baseContent.setInfo("information for base content");
				R4ETextPosition basePosition = fResFactory.createR4ETextPosition(baseContent);
				basePosition.setStartPosition(200);
				basePosition.setLength(50);
				basePosition.setStartLine(10);
				basePosition.setEndLine(20);

				R4ETextContent targetContent = fResFactory.createR4ETargetTextContent(delta);
				targetContent.setContent("target content");
				targetContent.setInfo("information for target content");
				R4ETextPosition targetPosition = fResFactory.createR4ETextPosition(targetContent);
				targetPosition.setStartPosition(300);
				targetPosition.setLength(100);
				targetPosition.setStartLine(30);
				targetPosition.setEndLine(40);

			} catch (ResourceHandlingException e) {
				e.printStackTrace();
			}
		}
	}
}
