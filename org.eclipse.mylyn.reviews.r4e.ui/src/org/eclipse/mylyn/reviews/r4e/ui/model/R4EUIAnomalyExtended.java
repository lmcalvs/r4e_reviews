package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyRank;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;

public class R4EUIAnomalyExtended extends R4EUIAnomalyBasic {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	private static final String ANOMALY_STATE_CREATED = "CREATED";
	private static final String ANOMALY_STATE_ASSIGNED = "ASSIGNED";
	private static final String ANOMALY_STATE_ACCEPTED = "ACCEPTED";
	private static final String ANOMALY_STATE_DUPLICATED = "DUPLICATED";
	private static final String ANOMALY_STATE_REJECTED = "REJECTED";
	private static final String ANOMALY_STATE_DEFERRED = "DEFERRED";
	private static final String ANOMALY_STATE_FIXED = "FIXED";
	private static final String ANOMALY_STATE_VERIFIED = "VERIFIED";

	private static String[] stateValues = { ANOMALY_STATE_ACCEPTED, ANOMALY_STATE_DUPLICATED, ANOMALY_STATE_REJECTED, 
		ANOMALY_STATE_DEFERRED, ANOMALY_STATE_ASSIGNED, ANOMALY_STATE_CREATED, ANOMALY_STATE_VERIFIED, 
		ANOMALY_STATE_FIXED };  //NOTE: This has to match R4EAnomalyState in R4E core plugin
	
	private static final String ANOMALY_RANK_NONE = "NONE";
	private static final String ANOMALY_RANK_MINOR = "MINOR";
	private static final String ANOMALY_RANK_MAJOR = "MAJOR";
	
	private static String[] rankValues = { ANOMALY_RANK_NONE, ANOMALY_RANK_MINOR,
		ANOMALY_RANK_MAJOR };  //NOTE: This has to match R4EAnomalyRank in R4E core plugin

	private static final String ANOMALY_CLASS_ERROR = "Error";

	private static String[] classValues = { ANOMALY_CLASS_ERROR };  //TODO complete NOTE: This has to match CommentType in R4E core plugin
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	public R4EUIAnomalyExtended(IR4EUIModelElement aParent,
			R4EAnomaly aAnomaly, IR4EUIPosition aPosition) {
		super(aParent, aAnomaly, aPosition);
	}
	
	public void updateState(R4EAnomalyState aNewState) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fAnomaly, 
				R4EUIModelController.getReviewer());
		fAnomaly.setState(aNewState);
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    	setName(getStateString(aNewState) + ": " + getName());
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
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
	
	public static String[] getStates() {
		return stateValues;
	}
	
	public String[] getAvailableStates() {
		//Peek state machine to get available states
		R4EAnomalyState[] states = getAllowedState(R4EUIModelController.getActiveReview().getReview().getType(),
				getAnomaly().getState());
		List<String> stateStrings = new ArrayList<String>();
		for (R4EAnomalyState state : states) {
			stateStrings.add(getStateString(state));
		}
		return stateStrings.toArray(new String[stateStrings.size()]);
	}
	
	public int mapStateToIndex(R4EAnomalyState aState) {
		//Peek state machine to get available states
		R4EAnomalyState[] states = getAllowedState(R4EUIModelController.getActiveReview().getReview().getType(),
				getAnomaly().getState());
		for (int i = 0; i < states.length; i++) {
			if (states[i].getValue() == aState.getValue()) return i;		
		}
		return R4EUIConstants.INVALID_VALUE;   //should never happen
	}
	
	public static String[] getClasses() {
		return classValues;
	} 
	
	//TODO
	public static R4ECommentType getClassFromString(String aClass) {
		if (aClass.equals(ANOMALY_CLASS_ERROR)) {
			return null;
		} else return null;   //should never happen
	}
	
	public static String[] getRanks() {
		return rankValues;
	}
	
	public static R4EAnomalyRank getRankFromString(String aRank) {
		if (aRank.equals(ANOMALY_RANK_NONE)) {
			return R4EAnomalyRank.R4E_ANOMALY_RANK_NONE;
		} else if (aRank.equals(ANOMALY_RANK_MINOR)) {
			return R4EAnomalyRank.R4E_ANOMALY_RANK_MINOR;
		} else if (aRank.equals(ANOMALY_RANK_MAJOR)) {
			return R4EAnomalyRank.R4E_ANOMALY_RANK_MAJOR;
		} else return null;   //should never happen
	}
	
	public int mapParticipantToIndex(String aParticipant) {
		List<R4EParticipant> participants = R4EUIModelController.getActiveReview().getParticipants();
		for (int i = 0; i < participants.size(); i++) {
			if (participants.get(i).getId().equals(aParticipant)) return i;		
		}
		return R4EUIConstants.INVALID_VALUE;   //should never happen
	}
	
	
	//Anomaly State Machine
	
	public boolean isRankEnabled() {
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
			if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isDueDateEnabled() {
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_INFORMAL)) {
			if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				return true;
			}
		}
		return false;
	}
	
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
	
	public boolean isFixedByEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
			return true;
		}
		return false;
	}
	
	public boolean isFollowUpByEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
			return true;
		}
		return false;
	}
	
	public boolean isNotAcceptedReasonEnabled() {
		if (fAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED)) {
			return true;
		}
		return false;
	}
	
	private R4EAnomalyState[] getAllowedState(R4EReviewType aReviewType, R4EAnomalyState aCurrentState) {
		List<R4EAnomalyState> states = new ArrayList<R4EAnomalyState>();
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
			}
		} else {
			//Assume formal review TODO
		}
		return states.toArray(new R4EAnomalyState[states.size()]);
	}
}
