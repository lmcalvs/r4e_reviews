package org.eclipse.mylyn.reviews.r4e.ui.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;

public class R4EUIAnomalyExtended extends R4EUIAnomalyBasic {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	private static final String ANOMALY_STATE_CREATED = "CREATED";
	private static final String ANOMALY_STATE_ASSIGNED = "ASSIGNED";
	private static final String ANOMALY_STATE_ACCEPTED = "ACCEPTED";
	private static final String ANOMALY_STATE_DUPLICATED = "DUPLICATED";
	private static final String ANOMALY_STATE_REJECTED = "REJECTED";
	private static final String ANOMALY_STATE_POSTPONED = "POSTPONED";
	private static final String ANOMALY_STATE_FIXED = "FIXED";
	private static final String ANOMALY_STATE_VERIFIED = "VERIFIED";

	
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
	
	private String getStateString(R4EAnomalyState aNewState) {
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
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_POSTPONED)) {
			return ANOMALY_STATE_POSTPONED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
			return ANOMALY_STATE_FIXED;
		} else if (aNewState.equals(R4EAnomalyState.R4E_ANOMALY_SATE_VERIFIED)) {
			return ANOMALY_STATE_VERIFIED;
		} else return "";
	}
}
