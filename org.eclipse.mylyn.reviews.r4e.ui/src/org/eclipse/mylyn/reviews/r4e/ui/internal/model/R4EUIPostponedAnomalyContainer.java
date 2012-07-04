// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Postponed Anomaly Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIPostponedAnomalyContainer extends R4EUIAnomalyContainer {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fAnomalies.
	 */
	private final List<R4EUIPostponedAnomaly> fPostponedAnomalies;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for AnomalyContainerElement.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aName
	 *            String
	 */
	public R4EUIPostponedAnomalyContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName);
		fReadOnly = aParent.isReadOnly();
		fPostponedAnomalies = new ArrayList<R4EUIPostponedAnomaly>();
		setImage(ANOMALY_CONTAINER_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	//Hierarchy

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() { // $codepro.audit.disable
		return fPostponedAnomalies.toArray(new R4EUIPostponedAnomaly[fPostponedAnomalies.size()]);
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fPostponedAnomalies.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Close the model element (i.e. disable it)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIPostponedAnomaly anomaly = null;
		final int anomaliesSize = fPostponedAnomalies.size();
		for (int i = 0; i < anomaliesSize; i++) {

			anomaly = fPostponedAnomalies.get(i);
			anomaly.close();
			//fireRemove(anomaly);
		}
		fPostponedAnomalies.clear();
		fOpen = false;
	}

	/**
	 * Method open. Load the serialization model data into UI model
	 */
	@Override
	public void open() {
		R4EUIPostponedAnomaly uiAnomaly = null;
		final IR4EUIModelElement parentElement = getParent();
		//Get global postponed anomalies
		final EList<Topic> anomalies = ((R4EUIReviewBasic) parentElement.getParent()).getReview().getTopics();
		if (null != anomalies) {
			final int anomaliesSize = anomalies.size();
			R4EAnomaly anomaly = null;
			for (int i = 0; i < anomaliesSize; i++) {
				anomaly = (R4EAnomaly) anomalies.get(i);
				if (null != anomaly.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)) {
					if (anomaly.isEnabled()
							|| R4EUIPlugin.getDefault()
									.getPreferenceStore()
									.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
						if (0 == anomaly.getLocation().size()) {
							uiAnomaly = new R4EUIPostponedAnomaly(this, anomaly, null);
							uiAnomaly.setName(R4EUIAnomalyExtended.getStateString(anomaly.getState()) + ": "
									+ uiAnomaly.getName());
							addChildren(uiAnomaly);
							if (uiAnomaly.isEnabled()) {
								uiAnomaly.open();
							}
						}
					}
				}
			}
		}
		fOpen = true;
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		if (getParent().isEnabled()) {
			if (0 == fPostponedAnomalies.size()) {
				return true;
			}
			for (R4EUIPostponedAnomaly anomaly : fPostponedAnomalies) {
				if (anomaly.isEnabled()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method addChildren.
	 * 
	 * @param aChildToAdd
	 *            IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fPostponedAnomalies.add((R4EUIPostponedAnomaly) aChildToAdd);
	}

	/**
	 * Method createAnomaly. Used for postponed global anomalies
	 * 
	 * @param aPostponedAnomaly
	 *            R4EFileVersion
	 * @return R4EUIPostponedAnomaly
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIPostponedAnomaly createAnomaly(R4EUIReviewBasic aUiReview, R4EAnomaly aPostponedAnomaly)
			throws ResourceHandlingException, OutOfSyncException {

		//Check if the creator of the postponed anomaly is a participant of the current review.  If not, it will be 
		//created and disabled after the postponed anomaly is created
		final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
		R4EParticipant participant = uiReview.getParticipant(aPostponedAnomaly.getUser().getId(), false);
		boolean isParticipant = true;
		if (null == participant) {
			participant = uiReview.getParticipant(aPostponedAnomaly.getUser().getId(), true);
			isParticipant = false;
		}

		//Copy anomaly information from postponed anomaly model element if Anomaly does not already exist.  Otherwise it means it is disabled so restore it
		List<Topic> savedGlobalAnomalies = ((R4EUIReviewBasic) getParent().getParent()).getReview().getTopics();
		R4EAnomaly anomaly = null;
		for (Topic savedAnomaly : savedGlobalAnomalies) {
			if (null == ((R4EAnomaly) savedAnomaly).getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)) {
				//This is a genuine anomaly, not a postponed one, so we ignore it
				continue;
			} else {
				if (null == CommandUtils.getAnomalyParentFile((R4EAnomaly) savedAnomaly)) {
					String postponedAnomalyId = CommandUtils.buildOriginalAnomalyID(aPostponedAnomaly);
					if (postponedAnomalyId.equals(((R4EAnomaly) savedAnomaly).getInfoAtt().get(
							R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID))) {
						//Postponed anomaly existed but was disabled, restore it
						anomaly = ((R4EAnomaly) savedAnomaly);
					}
				}
			}
		}

		//Copy anomaly data
		if (null == anomaly) {
			//Brand new imported anomaly, set data
			anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(participant);
			CommandUtils.copyAnomalyData(anomaly, aPostponedAnomaly);
			Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomaly, R4EUIModelController.getReviewer());
			final EMap<String, String> info = anomaly.getInfoAtt(); //We use the R4EAnomaly attribute map to store the original anomaly ID
			info.put(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID,
					CommandUtils.buildOriginalAnomalyID(aPostponedAnomaly));
			info.put(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME, aUiReview.getReview().getName());
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}

		//Create and set UI model element
		final R4EUIPostponedAnomaly uiAnomaly = new R4EUIPostponedAnomaly(this, anomaly, null);
		uiAnomaly.updateState(aPostponedAnomaly.getState());
		uiAnomaly.setEnabled(true);
		addChildren(uiAnomaly);

		//Disable original creator of the postponed anomaly since he is not part of this review
		if (!isParticipant) {
			Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(participant,
					R4EUIModelController.getReviewer());
			participant.setEnabled(false);
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}
		return uiAnomaly;

	}

	/**
	 * Method removeChildren.
	 * 
	 * @param aChildToRemove
	 *            IR4EUIModelElement
	 * @param aFileRemove
	 *            - also remove from file (hard remove)
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove)
			throws ResourceHandlingException, OutOfSyncException {
		final R4EUIPostponedAnomaly removedElement = fPostponedAnomalies.get(fPostponedAnomalies.indexOf(aChildToRemove));

		//Also recursively remove all children 
		removedElement.removeAllChildren(aFileRemove);

		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getAnomaly().remove());
		else */
		final R4EAnomaly modelAnomaly = removedElement.getAnomaly();
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelAnomaly,
				R4EUIModelController.getReviewer());
		modelAnomaly.setEnabled(false);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		//Remove element from UI if the show disabled element option is off
		if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fPostponedAnomalies.remove(removedElement);
		}
	}

	/**
	 * Method removeAllChildren.
	 * 
	 * @param aFileRemove
	 *            boolean
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		//Recursively remove all children
		for (R4EUIPostponedAnomaly anomaly : fPostponedAnomalies) {
			removeChildren(anomaly, aFileRemove);
		}
	}
}
