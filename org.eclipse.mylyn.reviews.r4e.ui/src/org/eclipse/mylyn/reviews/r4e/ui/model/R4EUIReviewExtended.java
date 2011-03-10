package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.ReviewExtraProperties;
import org.eclipse.ui.views.properties.IPropertySource;

public class R4EUIReviewExtended extends R4EUIReviewBasic {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field REVIEW_PHASE_PLANNING.
	 * (value is ""PLANNING"")
	 */
	private static final String REVIEW_PHASE_PLANNING = "PLANNING";
	
	/**
	 * Field REVIEW_PHASE_PREPARATION.
	 * (value is ""PREPARATION"")
	 */
	private static final String REVIEW_PHASE_PREPARATION = "PREPARATION";
	
	/**
	 * Field REVIEW_PHASE_DECISION.
	 * (value is ""DECISION"")
	 */
	private static final String REVIEW_PHASE_DECISION = "DECISION";
	
	/**
	 * Field REVIEW_PHASE_REWORK.
	 * (value is ""REWORK"")
	 */
	private static final String REVIEW_PHASE_REWORK = "REWORK";
	
	/**
	 * Field FFormalPhaseValues.
	 */
	private static final String[] FFormalPhaseValues = { REVIEW_PHASE_PLANNING, REVIEW_PHASE_PREPARATION, REVIEW_PHASE_DECISION, 
		REVIEW_PHASE_REWORK, REVIEW_PHASE_COMPLETED };  //NOTE: This has to match R4EReviewPhase in R4E core plugin

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUIReview.
	 * @param aParent R4EUIReviewGroup
	 * @param aReview R4EReview
	 * @param aType R4EReviewType
	 * @param aOpen boolean
	 * @throws ResourceHandlingException
	 */
	public R4EUIReviewExtended(R4EUIReviewGroup aParent, R4EReview aReview, R4EReviewType aType, boolean aOpen) throws ResourceHandlingException {
		super(aParent, aReview, aType, aOpen);
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
		if (IPropertySource.class.equals(adapter)) return new ReviewExtraProperties(this);
		return null;
	}
	
	//Phase Management
	
	/**
	 * Method updatePhase.
	 * @param aNewPhase R4EReviewPhase
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 */
	@Override
	public void updatePhase(R4EReviewPhase aNewPhase) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fReview, 
				R4EUIModelController.getReviewer());
		((R4EReviewState)fReview.getState()).setState(aNewPhase);
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    	setName(getPhaseString(aNewPhase) + ": " + getName());
	}
	
	/**
	 * Method getPhaseString.
	 * @param aNewPhase R4EReviewPhase
 	 * @return String
	 */
	public static String getPhaseString(R4EReviewPhase aNewPhase) {
		if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_STARTED)) {
			return REVIEW_PHASE_PLANNING;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
			return REVIEW_PHASE_PREPARATION;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION)) {
			return REVIEW_PHASE_DECISION;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK)) {
			return REVIEW_PHASE_REWORK;
		} else if (aNewPhase.equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED)) {
			return REVIEW_PHASE_COMPLETED;
		} else return "";
	}
	
	/**
	 * Method getStateFromString.
	 * @param aNewPhase String
	 * @return R4EReviewPhase
	 */
	public static R4EReviewPhase getPhaseFromString(String aNewPhase) {
		if (aNewPhase.equals(REVIEW_PHASE_PLANNING)) {
			return R4EReviewPhase.R4E_REVIEW_PHASE_STARTED;
		} else if (aNewPhase.equals(REVIEW_PHASE_PREPARATION)) {
			return R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION;
		} else if (aNewPhase.equals(REVIEW_PHASE_DECISION)) {
			return R4EReviewPhase.R4E_REVIEW_PHASE_DECISION;
		} else if (aNewPhase.equals(REVIEW_PHASE_REWORK)) {
			return R4EReviewPhase.R4E_REVIEW_PHASE_REWORK;
		} else if (aNewPhase.equals(REVIEW_PHASE_COMPLETED)) {
			return R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED;
		} else return null;   //should never happen
	}
	
	/**
	 * Method getPhases.
	 * @return String[]
	 */
	public static String[] getPhases() {
		return FFormalPhaseValues;
	}
	
	/**
	 * Method getAvailablePhases.
	 * @return String[]
	 */
	@Override
	public String[] getAvailablePhases() {
		//Peek state machine to get available states
		final R4EReviewPhase[] phases = getAllowedPhases(((R4EReviewState)getReview().getState()).getState());
		final List<String> phaseStrings = new ArrayList<String>();
		for (R4EReviewPhase phase : phases) {
			phaseStrings.add(getPhaseString(phase));
		}
		return phaseStrings.toArray(new String[phaseStrings.size()]);
	}
	
	
	//Review State Machine
	
	/**
	 * Method getAllowedPhases.
	 * @param aReviewPhase R4EReviewPhase
	 * @return R4EReviewPhase[]
	 */
	private R4EReviewPhase[] getAllowedPhases(R4EReviewPhase aCurrentPhase) {
		final List<R4EReviewPhase> phases = new ArrayList<R4EReviewPhase>();

		switch (aCurrentPhase.getValue()) {
			case R4EReviewPhase.R4E_REVIEW_PHASE_STARTED_VALUE:
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_STARTED);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION);
				break;

			case R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION_VALUE:
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_STARTED);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION);
				break;

			case R4EReviewPhase.R4E_REVIEW_PHASE_DECISION_VALUE:
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED);
				break;

			case R4EReviewPhase.R4E_REVIEW_PHASE_REWORK_VALUE:
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED);
				break;

			case R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED_VALUE:
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION);
				phases.add(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK);
				break;

		default:
			//should never happen
		}

		return phases.toArray(new R4EReviewPhase[phases.size()]);
	}
	
	@Override
	public boolean validatePhaseChange(R4EReviewPhase aNextPhase, AtomicReference<String> aErrorMessage) {
		
		if (!R4EUIModelController.getReviewer().equals(((R4EFormalReview)fReview).getPhaseOwnerID())) {
			aErrorMessage.set("Phase cannot be changed as you are not the phase owner");
			return false;
		}
			
		switch (aNextPhase.getValue()) {
			case R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION_VALUE:
				//No other constraint
				break;
			
			case R4EReviewPhase.R4E_REVIEW_PHASE_DECISION_VALUE:
				//Check if all reviewers are done, otherwise do not prevent phase change, but notify phase owner
				List<R4EUser> users = (List<R4EUser>) fReview.getUsersMap().values();
				List<String> pendingUsers = new ArrayList<String>();
				for (R4EUser user : users) {
					if (!user.isReviewCompleted()) {
						pendingUsers.add(user.getId());
					}
				}
				if (pendingUsers.size() > 0) {
					aErrorMessage.set("Take note that the following reviewers did not complete the review: " +
										pendingUsers.toString());
					return true;	
				}
				break;
		
			case R4EReviewPhase.R4E_REVIEW_PHASE_REWORK_VALUE:
				if (!checkReworkStatus()) {
					aErrorMessage.set("Phase cannot be changed to " + REVIEW_PHASE_REWORK + 
										" as some anomalies are not in the proper state");
					return false;
				}
				break;
			
			case R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED_VALUE:
				if (!checkCompletionStatus()) {
					aErrorMessage.set("Phase cannot be changed to " + REVIEW_PHASE_COMPLETED + 
										" as some anomalies are not in the proper state");
					return false;
				}
				break;
				
			default:
				//Nothing to do
		}
		return true;
	}
	
	/**
	 * Method checkReworkStatus.
	 * @return boolean
	 */
	public boolean checkReworkStatus() { // $codepro.audit.disable booleanMethodNamingConvention
		if (null == fReview.getDecision() || null == fReview.getDecision().getValue()) return false;
		if (fReview.getDecision().getValue().equals(R4EDecision.R4E_REVIEW_DECISION_NONE)) return false;	
		if (fReview.getDecision().getValue().equals(R4EDecision.R4E_REVIEW_DECISION_REJECTED)) return false;

		//Check global anomalies state
		if (!(fAnomalyContainer.checkReworkStatus())) return false;

		for (R4EUIReviewItem item : fItems) {
			R4EUIFileContext[] contexts = (R4EUIFileContext[]) item.getChildren();
			for (R4EUIFileContext context : contexts) {
				R4EUIAnomalyContainer container = (R4EUIAnomalyContainer) context.getAnomalyContainerElement();
				if (!(container.checkReworkStatus())) return false;
			}
		}
		return true;
	}
	
	/**
	 * Method isPreparationDateEnabled.
	 * @return boolean
	 */
	public boolean isPreparationDateEnabled() {
		if (((R4EReviewState)fReview.getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION))
			return true;
		return false;
	}
	
	/**
	 * Method isDecisionDateEnabled.
	 * @return boolean
	 */
	public boolean isDecisionDateEnabled() {
		if (((R4EReviewState)fReview.getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_DECISION))
			return true;
		return false;
	}
	
	/**
	 * Method isReworkDateEnabled.
	 * @return boolean
	 */
	public boolean isReworkDateEnabled() {
		if (((R4EReviewState)fReview.getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_REWORK))
			return true;
		return false;
	}
}
