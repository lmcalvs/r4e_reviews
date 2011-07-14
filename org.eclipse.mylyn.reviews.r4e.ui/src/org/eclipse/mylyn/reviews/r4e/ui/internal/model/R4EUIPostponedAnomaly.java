// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
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
 * This class implements the Postponed Anomaly element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.mylyn.reviews.frame.core.model.Comment;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIPostponedAnomaly extends R4EUIAnomalyExtended {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field POSTPONED_ANOMALY_ICON_FILE. (value is ""icons/obj16/postanmly_obj.gif"")
	 */
	private static final String POSTPONED_ANOMALY_ICON_FILE = "icons/obj16/postanmly_obj.gif";

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIAnomaly.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aAnomaly
	 *            R4EAnomaly
	 * @param aPosition
	 *            IR4EUIPosition
	 */
	public R4EUIPostponedAnomaly(IR4EUIModelElement aParent, R4EAnomaly aAnomaly, IR4EUIPosition aPosition) {
		super(aParent, aAnomaly, aPosition);
		setImage(POSTPONED_ANOMALY_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	//Attributes

	/**
	 * Method updateAnomaly
	 * 
	 * @param aPostponedAnomaly
	 *            R4EAnomaly
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public void updateAnomaly(R4EAnomaly aPostponedAnomaly) throws ResourceHandlingException, OutOfSyncException {

		CommandUtils.copyAnomalyData(fAnomaly, aPostponedAnomaly);

		//Update UI model element
		setToolTip(R4EUIAnomalyBasic.buildAnomalyToolTip(fAnomaly)); //Also set UI tooltip immediately

		//Disable the anomaly if it is not postponed anymore
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED)) {
			fAnomaly.setEnabled(aPostponedAnomaly.isEnabled());
			//Close Anomaly if disabled
			if (!fAnomaly.isEnabled()) {
				close();
			}
		} else {
			fAnomaly.setEnabled(false); //Disable anomaly if it is not postponed to begin with
			close();
		}
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}

	/**
	 * Method updateOriginalAnomaly
	 * 
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public void updateOriginalAnomaly() throws ResourceHandlingException, OutOfSyncException {

		//Open original review
		final String origReviewName = ((R4EUIPostponedFile) getParent()).getFileContext()
				.getInfoAtt()
				.get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME);
		final R4EUIReviewGroup uiGroup = (R4EUIReviewGroup) getParent().getParent().getParent().getParent();
		final R4EReview origReview = R4EUIModelController.FModelExt.openR4EReview(uiGroup.getReviewGroup(), origReviewName);
		final R4EAnomaly origAnomaly = CommandUtils.getOriginalAnomaly(origReview, fAnomaly);
		if (null != origAnomaly) {
			//set data in original anomaly
			CommandUtils.copyAnomalyData(origAnomaly, fAnomaly);

		}
		//Close original review
		R4EUIModelController.FModelExt.closeR4EReview(origReview);
	}

	/**
	 * Method createComment.
	 * 
	 * @param aPostponedComment
	 *            R4EComment
	 * @return R4EUIComment
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIComment createComment(R4EComment aPostponedComment) throws ResourceHandlingException,
			OutOfSyncException {

		//Check if the creator of the postponed anomaly is a participant of the current review.  If not, it will be 
		//created and disabled after the postponed anomaly is created
		final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
		R4EParticipant participant = uiReview.getParticipant(aPostponedComment.getUser().getId(), false);
		boolean isParticipant = true;
		if (null == participant) {
			participant = uiReview.getParticipant(aPostponedComment.getUser().getId(), true);
			isParticipant = false;
		}

		//Copy comment information from postponed comment model element
		R4EUIComment uiComment = null;
		final R4EComment comment = R4EUIModelController.FModelExt.createR4EComment(participant, fAnomaly);
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(comment, R4EUIModelController.getReviewer());
		comment.setDescription(aPostponedComment.getDescription());
		comment.setCreatedOn(aPostponedComment.getCreatedOn());
		final EMap<String, String> info = comment.getInfoAtt(); //We use the R4EComment attribute map to store the original comment ID
		info.put(R4EUIConstants.POSTPONED_ATTR_ORIG_COMMENT_ID, aPostponedComment.getId().getUserID()
				+ aPostponedComment.getId().getSequenceID());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		//Disable original creator if it is not a participant in the current review
		if (!isParticipant) {
			bookNum = R4EUIModelController.FResourceUpdater.checkOut(participant, R4EUIModelController.getReviewer());
			participant.setEnabled(false);
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}

		//Create and set UI model element
		uiComment = new R4EUIComment(this, comment);
		addChildren(uiComment);
		return uiComment;
	}

	/**
	 * Method addChildren.
	 * 
	 * @param aModelComponent
	 *            - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	@Override
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws ResourceHandlingException,
			OutOfSyncException {

		//First create the children on the original anomaly...
		R4EComment origComment = null;

		//Open original review
		final String origReviewName = ((R4EUIPostponedFile) getParent()).getFileContext()
				.getInfoAtt()
				.get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME);
		final R4EUIReviewGroup uiGroup = (R4EUIReviewGroup) getParent().getParent().getParent().getParent();
		final R4EReview origReview = R4EUIModelController.FModelExt.openR4EReview(uiGroup.getReviewGroup(), origReviewName);
		final R4EAnomaly origAnomaly = CommandUtils.getOriginalAnomaly(origReview, fAnomaly);
		if (null != origAnomaly) {
			//Add Child Comments to original anomaly
			final R4EParticipant participantInOrigReview = CommandUtils.getParticipantForReview(origReview,
					R4EUIModelController.getReviewer());
			origComment = R4EUIModelController.FModelExt.createR4EComment(participantInOrigReview, origAnomaly);
			final Long origBookNum = R4EUIModelController.FResourceUpdater.checkOut(origComment,
					participantInOrigReview.getId());
			origComment.setDescription(((Comment) aModelComponent).getDescription());
			R4EUIModelController.FResourceUpdater.checkIn(origBookNum);
		}
		//Close original review
		R4EUIModelController.FModelExt.closeR4EReview(origReview);

		//...then create the comment on the current review
		if (null != origComment) {
			return createComment(origComment);
		}
		return null;
	}

	//Commands

	/**
	 * Method isOpenEditorCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	@Override
	public boolean isOpenEditorCmd() {
		if (!(getParent() instanceof R4EUIPostponedFile)) {
			return false;
		}
		if (isEnabled() && null != ((R4EUIPostponedFile) getParent()).getTargetFileVersion()) {
			return true;
		}
		return false;
	}
}
