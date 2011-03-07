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
 * This class extends the anomaly erlement to include additional parameters used
 * in informal and formal reviews
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentClass;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.AnomalyExtraProperties;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIAnomalyExtended extends R4EUIAnomalyBasic {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field ANOMALY_STATE_CREATED.
	 * (value is ""CREATED"")
	 */
	private static final String ANOMALY_STATE_CREATED = "CREATED";
	
	/**
	 * Field ANOMALY_STATE_ASSIGNED.
	 * (value is ""ASSIGNED"")
	 */
	private static final String ANOMALY_STATE_ASSIGNED = "ASSIGNED";
	
	/**
	 * Field ANOMALY_STATE_ACCEPTED.
	 * (value is ""ACCEPTED"")
	 */
	private static final String ANOMALY_STATE_ACCEPTED = "ACCEPTED";
	
	/**
	 * Field ANOMALY_STATE_DUPLICATED.
	 * (value is ""DUPLICATED"")
	 */
	private static final String ANOMALY_STATE_DUPLICATED = "DUPLICATED";
	
	/**
	 * Field ANOMALY_STATE_REJECTED.
	 * (value is ""REJECTED"")
	 */
	private static final String ANOMALY_STATE_REJECTED = "REJECTED";
	
	/**
	 * Field ANOMALY_STATE_DEFERRED.
	 * (value is ""DEFERRED"")
	 */
	private static final String ANOMALY_STATE_DEFERRED = "DEFERRED";
	
	/**
	 * Field ANOMALY_STATE_FIXED.
	 * (value is ""FIXED"")
	 */
	private static final String ANOMALY_STATE_FIXED = "FIXED";
	
	/**
	 * Field ANOMALY_STATE_VERIFIED.
	 * (value is ""VERIFIED"")
	 */
	private static final String ANOMALY_STATE_VERIFIED = "VERIFIED";
	
	/**
	 * Field ANOMALY_RANK_NONE.
	 * (value is ""NONE"")
	 */
	private static final String ANOMALY_RANK_NONE = "NONE";
	
	/**
	 * Field ANOMALY_RANK_MINOR.
	 * (value is ""MINOR"")
	 */
	private static final String ANOMALY_RANK_MINOR = "MINOR";
	/**
	 * Field ANOMALY_RANK_MAJOR.
	 * (value is ""MAJOR"")
	 */
	private static final String ANOMALY_RANK_MAJOR = "MAJOR";

	/**
	 * Field ANOMALY_CLASS_ERRONEOUS.
	 * (value is ""Erroneous"")
	 */
	private static final String ANOMALY_CLASS_ERRONEOUS = "Erroneous";
	
	/**
	 * Field ANOMALY_CLASS_SUPERFLUOUS.
	 * (value is ""Superfluous"")
	 */
	private static final String ANOMALY_CLASS_SUPERFLUOUS = "Superfluous";
	
	/**
	 * Field ANOMALY_CLASS_IMPROVEMENT.
	 * (value is ""Improvement"")
	 */
	private static final String ANOMALY_CLASS_IMPROVEMENT = "Improvement";
	
	/**
	 * Field ANOMALY_CLASS_QUESTION.
	 * (value is ""Question"")
	 */
	private static final String ANOMALY_CLASS_QUESTION = "Question";
	
	/**
	 * Field FStateValues.
	 */
	private static final String[] FStateValues = { ANOMALY_STATE_ACCEPTED, ANOMALY_STATE_DUPLICATED, ANOMALY_STATE_REJECTED, 
		ANOMALY_STATE_DEFERRED, ANOMALY_STATE_ASSIGNED, ANOMALY_STATE_CREATED, ANOMALY_STATE_VERIFIED, 
		ANOMALY_STATE_FIXED };  //NOTE: This has to match R4EAnomalyState in R4E core plugin
	
	/**
	 * Field rankValues.
	 */
	private static final String[] FRankValues = { ANOMALY_RANK_NONE, ANOMALY_RANK_MINOR,
		ANOMALY_RANK_MAJOR };  //NOTE: This has to match R4EAnomalyRank in R4E core plugin
	
	/**
	 * Field FClassValues.
	 */
	private static final String[] FClassValues = { ANOMALY_CLASS_ERRONEOUS, ANOMALY_CLASS_SUPERFLUOUS,
		ANOMALY_CLASS_IMPROVEMENT, ANOMALY_CLASS_QUESTION };  //NOTE: This has to match CommentType in R4E core plugin
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUIAnomalyExtended.
	 * @param aParent IR4EUIModelElement
	 * @param aAnomaly R4EAnomaly
	 * @param aPosition IR4EUIPosition
	 */
	public R4EUIAnomalyExtended(IR4EUIModelElement aParent,
			R4EAnomaly aAnomaly, IR4EUIPosition aPosition) {
		super(aParent, aAnomaly, aPosition);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getAdapter.
	 * @param adapter Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) return this;
		if (IPropertySource.class.equals(adapter)) return new AnomalyExtraProperties(this);
		return null;
	}
	
	/**
	 * Method updateState.
	 * @param aNewState R4EAnomalyState
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 */
	public void updateState(R4EAnomalyState aNewState) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fAnomaly, 
				R4EUIModelController.getReviewer());
		fAnomaly.setState(aNewState);
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    	setName(getStateString(aNewState) + ": " + getName());
	}
	
	/**
	 * Method getStateString.
	 * @param aNewState R4EAnomalyState
 	 * @return String
	 */
	public static String getStateString(R4EAnomalyState aNewState) {
		if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED)) {
			return ANOMALY_STATE_CREATED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
			return ANOMALY_STATE_ASSIGNED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_ACCEPTED)) {
			return ANOMALY_STATE_ACCEPTED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED)) {
			return ANOMALY_STATE_DUPLICATED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED)) {
			return ANOMALY_STATE_REJECTED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED)) {
			return ANOMALY_STATE_DEFERRED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
			return ANOMALY_STATE_FIXED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED)) {
			return ANOMALY_STATE_VERIFIED;
		} else return "";
	}
	
	/**
	 * Method getStateFromString.
	 * @param aNewState String
	 * @return R4EAnomalyState
	 */
	public static R4EAnomalyState getStateFromString(String aNewState) {
		if (aNewState.equals(ANOMALY_STATE_CREATED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_CREATED;
		} else if (aNewState.equals(ANOMALY_STATE_ASSIGNED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED;
		} else if (aNewState.equals(ANOMALY_STATE_ACCEPTED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_ACCEPTED;
		} else if (aNewState.equals(ANOMALY_STATE_DUPLICATED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED;
		} else if (aNewState.equals(ANOMALY_STATE_REJECTED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED;
		} else if (aNewState.equals(ANOMALY_STATE_DEFERRED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED;
		} else if (aNewState.equals(ANOMALY_STATE_FIXED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_FIXED;
		} else if (aNewState.equals(ANOMALY_STATE_VERIFIED)) {
			return R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED;
		} else return null;   //should never happen
	}
	
	/**
	 * Method getStates.
	 * @return String[]
	 */
	public static String[] getStates() {
		return FStateValues;
	}
	
	/**
	 * Method getAvailableStates.
	 * @return String[]
	 */
	public String[] getAvailableStates() {
		//Peek state machine to get available states
		final R4EAnomalyState[] states = getAllowedState(R4EUIModelController.getActiveReview().getReview().getType(),
				getAnomaly().getState());
		final List<String> stateStrings = new ArrayList<String>();
		for (R4EAnomalyState state : states) {
			stateStrings.add(getStateString(state));
		}
		return stateStrings.toArray(new String[stateStrings.size()]);
	}
	
	/**
	 * Method mapStateToIndex.
	 * @param aState R4EAnomalyState
	 * @return int
	 */
	public int mapStateToIndex(R4EAnomalyState aState) {
		//Peek state machine to get available states
		final R4EAnomalyState[] states = getAllowedState(R4EUIModelController.getActiveReview().getReview().getType(),
				getAnomaly().getState());
		for (int i = 0; i < states.length; i++) {
			if (states[i].getValue() == aState.getValue()) return i;		
		}
		return R4EUIConstants.INVALID_VALUE;   //should never happen
	}
	
	/**
	 * Method getClasses.
	 * @return String[]
	 */
	public static String[] getClasses() {
		return FClassValues;
	} 
	
	/**
	 * Method getClassFromString.
	 * @param aClass String
	 * @return R4ECommentClass
	 */
	public static R4ECommentClass getClassFromString(String aClass) {
		if (aClass.equals(ANOMALY_CLASS_ERRONEOUS)) {
			return R4ECommentClass.R4E_CLASS_ERRONEOUS;
		} else if (aClass.equals(ANOMALY_CLASS_SUPERFLUOUS)) {
			return R4ECommentClass.R4E_CLASS_SUPERFLUOUS;
		} else if (aClass.equals(ANOMALY_CLASS_IMPROVEMENT)) {
			return R4ECommentClass.R4E_CLASS_IMPROVEMENT;
		} else if (aClass.equals(ANOMALY_CLASS_QUESTION)) {
			return R4ECommentClass.R4E_CLASS_QUESTION;
		} else return null;   //should never happen
	}

	/**
	 * Method getRanks.
	 * @return String[]
	 */
	public static String[] getRanks() {
		return FRankValues;
	}
	
	/**
	 * Method getRankFromString.
	 * @param aRank String
	 * @return R4EAnomalyRank
	 */
	public static R4EAnomalyRank getRankFromString(String aRank) {
		if (aRank.equals(ANOMALY_RANK_NONE)) {
			return R4EAnomalyRank.R4E_ANOMALY_RANK_NONE;
		} else if (aRank.equals(ANOMALY_RANK_MINOR)) {
			return R4EAnomalyRank.R4E_ANOMALY_RANK_MINOR;
		} else if (aRank.equals(ANOMALY_RANK_MAJOR)) {
			return R4EAnomalyRank.R4E_ANOMALY_RANK_MAJOR;
		} else return null;   //should never happen
	}
	
	/**
	 * Method mapParticipantToIndex.
	 * @param aParticipant String
	 * @return int
	 */
	public int mapParticipantToIndex(String aParticipant) {
		final List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		final int numParticipants = participants.size();
		for (int i = 0; i < numParticipants; i++) {
			if (participants.get(i).getId().equals(aParticipant)) return i;		
		}
		return R4EUIConstants.INVALID_VALUE;   //should never happen
	}
	
	
	//Anomaly State Machine
	
	/**
	 * Method isClassEnabled.
	 * @return boolean
	 */
	public boolean isClassEnabled() {
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
			if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method isRankEnabled.
	 * @return boolean
	 */
	public boolean isRankEnabled() {
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
			if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method isDueDateEnabled.
	 * @return boolean
	 */
	public boolean isDueDateEnabled() {
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
			if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method isDecidedByEnabled.
	 * @return boolean
	 */
	public boolean isDecidedByEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED) ||
			fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED) ||
			fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED) ||
			fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED) || 
			fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method isFixedByEnabled.
	 * @return boolean
	 */
	public boolean isFixedByEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method isFollowUpByEnabled.
	 * @return boolean
	 */
	public boolean isFollowUpByEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method isNotAcceptedReasonEnabled.
	 * @return boolean
	 */
	public boolean isNotAcceptedReasonEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method isNotAcceptedReasonEnabled.
	 * @param aReviewType R4EReviewType
	 * @param aCurrentState R4EAnomalyState
	 * @return R4EAnomalyState[]
	 */
	private R4EAnomalyState[] getAllowedState(R4EReviewType aReviewType, R4EAnomalyState aCurrentState) {
		final List<R4EAnomalyState> states = new ArrayList<R4EAnomalyState>();
		if (aReviewType == R4EReviewType.R4E_REVIEW_TYPE_INFORMAL) {
			switch (aCurrentState.getValue()) {
				case R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED_VALUE:
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED);
					break;
					
				case R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED_VALUE:
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED);
					break;
					
				case R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED_VALUE:
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DUPLICATED);
					break;
					
				case R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED_VALUE:
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED);
					break;
					
				case R4EAnomalyState.R4E_ANOMALY_STATE_FIXED_VALUE:
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED);
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED);
					break;
					
				case R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED_VALUE:
					states.add(R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED);
					break;
					
				default:
					//should never happen
			}
		} else {
			//TODO Assume formal review 
		}
		return states.toArray(new R4EAnomalyState[states.size()]);
	}
}
