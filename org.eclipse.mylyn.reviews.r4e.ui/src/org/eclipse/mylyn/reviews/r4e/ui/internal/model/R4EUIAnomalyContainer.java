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
 * This class implements the Anomaly Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.Location;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IAnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIAnomalyContainer extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field fAnomalyContainerFile. (value is ""icons/obj16/anmlycont_obj.gif"")
	 */
	public static final String ANOMALY_CONTAINER_ICON_FILE = "icons/obj16/anmlycont_obj.gif";

	/**
	 * Field NEW_CHILD_ELEMENT_COMMAND_NAME. (value is ""Add a New Anomaly"")
	 */
	private static final String NEW_CHILD_ELEMENT_COMMAND_NAME = "New Anomaly";

	/**
	 * Field NEW_CHILD_ELEMENT_COMMAND_TOOLTIP. (value is ""Add a New Global Anomaly to the Current Review Item"")
	 */
	private static final String NEW_CHILD_ELEMENT_COMMAND_TOOLTIP = "Add a New Global Anomaly to the Current Review Item";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fAnomalies.
	 */
	private final List<R4EUIAnomalyBasic> fAnomalies;

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
	public R4EUIAnomalyContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName);
		fReadOnly = aParent.isReadOnly();
		fAnomalies = new ArrayList<R4EUIAnomalyBasic>();
		setImage(ANOMALY_CONTAINER_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	//Attributes

	/**
	 * Method createDetachedAnomaly.
	 * 
	 * @return R4EAnomaly
	 */
	public static R4EAnomaly createDetachedAnomaly() {
		//Get comment from user and set it in model data
		R4EAnomaly tempAnomaly = null;
		R4EUIModelController.setJobInProgress(true);
		final IAnomalyInputDialog dialog = R4EUIDialogFactory.getInstance().getAnomalyInputDialog();
		final int result = dialog.open();
		if (result == Window.OK) {
			tempAnomaly = RModelFactory.eINSTANCE.createR4EAnomaly();
			final R4ECommentType tempCommentType = RModelFactory.eINSTANCE.createR4ECommentType();
			tempAnomaly.setTitle(dialog.getAnomalyTitleValue());
			tempAnomaly.setDescription(dialog.getAnomalyDescriptionValue());
			tempAnomaly.setDueDate(dialog.getDueDate());
			tempAnomaly.getAssignedTo().add(dialog.getAssigned());
			if (null != dialog.getRuleReferenceValue()) {
				final R4EDesignRule rule = dialog.getRuleReferenceValue().getRule();
				tempCommentType.setType(rule.getClass_());
				tempAnomaly.setType(tempCommentType);
				tempAnomaly.setRank(rule.getRank());
				tempAnomaly.setRuleID(rule.getId());
			} else {
				if (null != dialog.getClass_()) {
					final R4ECommentType commentType = RModelFactory.eINSTANCE.createR4ECommentType();
					commentType.setType(dialog.getClass_());
					tempAnomaly.setType(commentType);
				}
				if (null != dialog.getRank()) {
					tempAnomaly.setRank(dialog.getRank());
				}
			}
		}
		R4EUIModelController.setJobInProgress(false); //Enable commands in case of error
		return tempAnomaly;
	}

	/**
	 * Create a serialization model element object
	 * 
	 * @return the new serialization element object
	 */
	@Override
	public List<ReviewComponent> createChildModelDataElement() {
		//Get Anomaly from user and set it in model data
		List<ReviewComponent> tempAnomalies = new ArrayList<ReviewComponent>();

		R4EUIModelController.setJobInProgress(true);
		final IAnomalyInputDialog dialog = R4EUIDialogFactory.getInstance().getAnomalyInputDialog();
		final int result = dialog.open();
		if (result == Window.OK) {
			R4EAnomaly tempAnomaly = RModelFactory.eINSTANCE.createR4EAnomaly();
			setAnomalyWithDialogValues(tempAnomaly, dialog);
			tempAnomalies.add(tempAnomaly);
		}
		R4EUIModelController.setJobInProgress(false); //Enable view
		return tempAnomalies;
	}

	//Hierarchy

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() { // $codepro.audit.disable
		return fAnomalies.toArray(new R4EUIAnomalyBasic[fAnomalies.size()]);
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fAnomalies.size() > 0) {
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
		R4EUIAnomalyBasic anomaly = null;
		final int anomaliesSize = fAnomalies.size();
		for (int i = 0; i < anomaliesSize; i++) {

			anomaly = fAnomalies.get(i);
			anomaly.close();
			//fireRemove(anomaly);
		}
		fAnomalies.clear();
		fOpen = false;
		removeListeners();
	}

	/**
	 * Method open. Load the serialization model data into UI model
	 */
	@Override
	public void open() {

		R4EUIAnomalyBasic uiAnomaly = null;
		final IR4EUIModelElement parentElement = getParent();
		if (parentElement instanceof R4EUIFileContext) {

			//get anomalies that are specific to a file
			final List<R4EAnomaly> anomalies = ((R4EUIFileContext) parentElement).getAnomalies();
			IR4EUIPosition uiPosition = null;
			final int anomaliesSize = anomalies.size();
			R4EAnomaly anomaly = null;
			for (int i = 0; i < anomaliesSize; i++) {
				anomaly = anomalies.get(i);
				if (null != anomaly.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)) {
					//This is a postponed anomaly, so we ignore it
					continue;
				}

				if (anomaly.isEnabled()
						|| R4EUIPlugin.getDefault()
								.getPreferenceStore()
								.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					//Do not set position for global EList<E>lies
					uiPosition = null;
					EList<Location> locations = anomalies.get(i).getLocation(); // $codepro.audit.disable variableDeclaredInLoop
					if (null != locations) {
						if (null != locations.get(0)) {
							int locationsSize = locations.size(); // $codepro.audit.disable variableDeclaredInLoop
							for (int j = 0; j < locationsSize; j++) {
								R4EPosition pos = ((R4EContent) anomalies.get(i).getLocation().get(j)).getLocation();
								if (pos instanceof R4ETextPosition) {
									uiPosition = new R4EUITextPosition((R4ETextPosition) pos); // $codepro.audit.disable methodChainLength
								} else if (pos instanceof R4EModelPosition) {
									uiPosition = new R4EUIModelPosition((R4EModelPosition) pos);
								}

								if (((R4EUIReviewBasic) getParent().getParent().getParent()).getReview()
										.getType()
										.equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
									uiAnomaly = new R4EUIAnomalyBasic(this, anomalies.get(i), uiPosition);
								} else {
									uiAnomaly = new R4EUIAnomalyExtended(this, anomalies.get(i), uiPosition);
									uiAnomaly.setName(R4EUIAnomalyExtended.getStateString(anomalies.get(i).getState())
											+ ": " + uiAnomaly.getName());
								}

								addChildren(uiAnomaly);
								if (uiAnomaly.isEnabled()) {
									uiAnomaly.open();
								}
							}
						} else {
							uiAnomaly = new R4EUIAnomalyBasic(this, anomalies.get(i), null);
							addChildren(uiAnomaly);
							if (uiAnomaly.isEnabled()) {
								uiAnomaly.open();
							}
						}
					}
				}
			}
		} else if (parentElement instanceof R4EUIReviewBasic) {

			//Get anomalies that do not have any location.  These are global anomalies

			final EList<Topic> anomalies = ((R4EUIReviewBasic) parentElement).getReview().getTopics();
			if (null != anomalies) {
				final int anomaliesSize = anomalies.size();
				R4EAnomaly anomaly = null;
				for (int i = 0; i < anomaliesSize; i++) {
					anomaly = (R4EAnomaly) anomalies.get(i);
					if (anomaly.isEnabled()
							|| R4EUIPlugin.getDefault()
									.getPreferenceStore()
									.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
						if (0 == anomaly.getLocation().size()) {
							if (((R4EUIReviewBasic) getParent()).getReview()
									.getType()
									.equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
								uiAnomaly = new R4EUIAnomalyBasic(this, anomaly, null);
							} else {
								uiAnomaly = new R4EUIAnomalyExtended(this, anomaly, null);
								uiAnomaly.setName(R4EUIAnomalyExtended.getStateString(anomaly.getState()) + ": "
										+ uiAnomaly.getName());
							}
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
			if (0 == fAnomalies.size()) {
				return true;
			}
			for (R4EUIAnomalyBasic anomaly : fAnomalies) {
				if (anomaly.isEnabled()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method setReadOnly.
	 * 
	 * @param boolean
	 */
	public void setReadOnly(boolean aReadOnly) {
		fReadOnly = aReadOnly;
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
		fAnomalies.add((R4EUIAnomalyBasic) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getContentProvider());
		fireAdd(aChildToAdd);
	}

	/**
	 * Method createChildren
	 * 
	 * @param aModelComponent
	 *            - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildren(ReviewNavigatorContentProvider)
	 */
	@Override
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws ResourceHandlingException,
			OutOfSyncException {
		final String user = R4EUIModelController.getReviewer();
		final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(R4EUIModelController.getActiveReview()
				.getParticipant(user, true));
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomaly, R4EUIModelController.getReviewer());
		anomaly.setTitle(((R4EAnomaly) aModelComponent).getTitle()); //This is needed as the global anomaly title is displayed in the navigator view
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		R4EUIAnomalyBasic addedChild = null;
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
			addedChild = new R4EUIAnomalyBasic(this, anomaly, null);
		} else {
			addedChild = new R4EUIAnomalyExtended(this, anomaly, null);
			if (R4EUIModelController.getActiveReview()
					.getReview()
					.getType()
					.equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
				((R4EUIAnomalyExtended) addedChild).updateState(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED);
			} else { //R4EReviewType.R4E_REVIEW_TYPE_INFORMAL
				((R4EUIAnomalyExtended) addedChild).updateState(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
			}
		}
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);
		return addedChild;
	}

	/**
	 * Method createAnomaly Creates a new Anomaly from the user dialog
	 * 
	 * @param aAnomalyTempFileVersion
	 *            R4EFileVersion
	 * @param aUiPosition
	 *            - the position of the anomaly to create
	 * @return R4EUIAnomalyBasic
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIAnomalyBasic createAnomaly(R4EFileVersion aAnomalyTempFileVersion, IR4EUIPosition aUiPosition)
			throws ResourceHandlingException, OutOfSyncException {

		R4EUIAnomalyBasic uiAnomaly = null;

		//Get anomaly details from user
		R4EUIModelController.setJobInProgress(true);
		final IAnomalyInputDialog dialog = R4EUIDialogFactory.getInstance().getAnomalyInputDialog();
		final int result = dialog.open();

		if (result == Window.OK) {

			//Create anomaly model element
			final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
			final R4EParticipant participant = uiReview.getParticipant(R4EUIModelController.getReviewer(), true);
			final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(participant);
			Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomaly, R4EUIModelController.getReviewer());
			setAnomalyWithDialogValues(anomaly, dialog);
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);

			uiAnomaly = createAnomalyDetails(anomaly, aAnomalyTempFileVersion, aUiPosition);
		}
		R4EUIModelController.setJobInProgress(false); //Enable commands in case of error
		return uiAnomaly;
	}

	/**
	 * Method setAnomalyWithDialogValues
	 * 
	 * @param aAnomaly
	 *            R4EAnomaly
	 * @param aDialog
	 *            AnomalyInputDialog
	 * @return R4EUIAnomalyBasic
	 */
	private void setAnomalyWithDialogValues(R4EAnomaly aAnomaly, IAnomalyInputDialog aDialog) {

		aAnomaly.setTitle(aDialog.getAnomalyTitleValue());
		aAnomaly.setDescription(aDialog.getAnomalyDescriptionValue());
		aAnomaly.setDueDate(aDialog.getDueDate());
		aAnomaly.getAssignedTo().clear();
		aAnomaly.getAssignedTo().add(aDialog.getAssigned());
		if (null != aDialog.getRuleReferenceValue()) {
			final R4EDesignRule rule = aDialog.getRuleReferenceValue().getRule();
			final R4ECommentType commentType = RModelFactory.eINSTANCE.createR4ECommentType();
			commentType.setType(rule.getClass_());
			aAnomaly.setType(commentType);
			aAnomaly.setRank(rule.getRank());
			aAnomaly.setRuleID(rule.getId());
		} else {
			if (null != aDialog.getClass_()) {
				final R4ECommentType commentType = RModelFactory.eINSTANCE.createR4ECommentType();
				commentType.setType(aDialog.getClass_());
				aAnomaly.setType(commentType);
			}
			if (null != aDialog.getRank()) {
				aAnomaly.setRank(aDialog.getRank());
			}
		}
	}

	/**
	 * Method createAnomalyFromDetached Create a new anomaly from a detached model anomaly
	 * 
	 * @param aAnomalyTempFileVersion
	 *            R4EFileVersion
	 * @param aModelComponent
	 *            R4EAnomaly
	 * @param aUiPosition
	 *            R4EUITextPosition
	 * @return R4EUIAnomalyBasic
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIAnomalyBasic createAnomalyFromDetached(R4EFileVersion aAnomalyTempFileVersion,
			R4EAnomaly aModelComponent, R4EUITextPosition aUiPosition) throws ResourceHandlingException,
			OutOfSyncException {
		final String user = R4EUIModelController.getReviewer();
		final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(R4EUIModelController.getActiveReview()
				.getParticipant(user, true));
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomaly, R4EUIModelController.getReviewer());
		anomaly.setTitle(aModelComponent.getTitle()); //This is needed as the anomaly title is displayed in the navigator view
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		R4EUIAnomalyBasic uiAnomaly = createAnomalyDetails(anomaly, aAnomalyTempFileVersion, aUiPosition);
		uiAnomaly.setModelData(aModelComponent);
		return uiAnomaly;
	}

	/**
	 * Method createAnomalyDetails
	 * 
	 * @param aAnomalyTempFileVersion
	 *            R4EFileVersion
	 * @param aAnomaly
	 *            R4EAnomaly
	 * @param aAnomalyTempFileVersion
	 *            R4EFileVersion
	 * @param aUiPosition
	 *            R4EUITextPosition
	 * @return R4EUIAnomalyBasic
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIAnomalyBasic createAnomalyDetails(R4EAnomaly aAnomaly, R4EFileVersion aAnomalyTempFileVersion,
			IR4EUIPosition aUiPosition) throws ResourceHandlingException, OutOfSyncException {

		R4EUIAnomalyBasic uiAnomaly = null;
		final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();

		R4EPosition position = null;
		if (aUiPosition instanceof R4EUIModelPosition) {
			//Set position data
			position = R4EUIModelController.FModelExt.createR4EAnomalyModelPosition(R4EUIModelController.FModelExt.createR4ETextContent(aAnomaly));
		} else {
			//Set position data
			position = R4EUIModelController.FModelExt.createR4EAnomalyTextPosition(R4EUIModelController.FModelExt.createR4ETextContent(aAnomaly));
		}

		//Set File version data
		final R4EFileVersion anomalyFileVersion = R4EUIModelController.FModelExt.createR4EFileVersion(position);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomalyFileVersion,
				R4EUIModelController.getReviewer());
		CommandUtils.copyFileVersionData(anomalyFileVersion, aAnomalyTempFileVersion);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		//Create and set UI model element
		if (uiReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
			uiAnomaly = new R4EUIAnomalyBasic(this, aAnomaly, aUiPosition);
		} else {
			uiAnomaly = new R4EUIAnomalyExtended(this, aAnomaly, aUiPosition);
			if (uiReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
				((R4EUIAnomalyExtended) uiAnomaly).updateState(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED);
			} else { //R4EReviewType.R4E_REVIEW_TYPE_INFORMAL
				((R4EUIAnomalyExtended) uiAnomaly).updateState(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
			}
		}
		aUiPosition.setPositionInModel(position);
		addChildren(uiAnomaly);
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
		final R4EUIAnomalyBasic removedElement = fAnomalies.get(fAnomalies.indexOf(aChildToRemove));

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
			fAnomalies.remove(removedElement);
			aChildToRemove.removeListeners();
			fireRemove(aChildToRemove);
		} else {
			R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
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
		for (R4EUIAnomalyBasic anomaly : fAnomalies) {
			removeChildren(anomaly, aFileRemove);
		}
	}

	//Listeners

	/**
	 * Method addListener.
	 * 
	 * @param aProvider
	 *            ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
		super.addListener(aProvider);
		if (null != fAnomalies) {
			R4EUIAnomalyBasic element = null;
			for (final Iterator<R4EUIAnomalyBasic> iterator = fAnomalies.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.addListener(aProvider);
			}
		}
	}

	/**
	 * Method removeListener
	 * 
	 * @param aProvider
	 *            - the treeviewer content provider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener(ReviewNavigatorContentProvider aProvider) {
		super.removeListener(aProvider);
		if (null != fAnomalies) {
			R4EUIAnomalyBasic element = null;
			for (final Iterator<R4EUIAnomalyBasic> iterator = fAnomalies.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener(aProvider);
			}
		}
	}

	//Commands

	/**
	 * Method isAddChildElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isNewChildElementCmd()
	 */
	@Override
	public boolean isNewChildElementCmd() {
		if (!isReadOnly()
				&& !((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED) && getParent().isEnabled()) {
			return true;
		}
		return false;
	}

	/**
	 * Method getAddChildElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNewChildElementCmdName()
	 */
	@Override
	public String getNewChildElementCmdName() {
		return NEW_CHILD_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getAddChildElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNewChildElementCmdTooltip()
	 */
	@Override
	public String getNewChildElementCmdTooltip() {
		return NEW_CHILD_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isShowPropertiesCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isShowPropertiesCmd()
	 */
	@Override
	public boolean isShowPropertiesCmd() {
		return false;
	}

	/**
	 * Method checkCompletionStatus.
	 * 
	 * @param aMessage
	 *            AtomicReference<String>
	 * @return boolean
	 */
	public boolean checkCompletionStatus(AtomicReference<String> aMessage) { // $codepro.audit.disable booleanMethodNamingConvention
		final StringBuilder sb = new StringBuilder();
		boolean resultOk = true;
		for (R4EUIAnomalyBasic anomaly : fAnomalies) {
			if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state CREATED"
						+ R4EUIConstants.LINE_FEED);
				resultOk = false;
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state ASSIGNED"
						+ R4EUIConstants.LINE_FEED);
				resultOk = false;
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ACCEPTED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state ACCEPTED"
						+ R4EUIConstants.LINE_FEED);
				resultOk = false;
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
				if (null == anomaly.getAnomaly().getFixedByID() || ("").equals(anomaly.getAnomaly().getFixedByID())) {
					sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") does not have a fixer"
							+ R4EUIConstants.LINE_FEED);
					resultOk = false;
				}
				if (R4EUIModelController.getActiveReview()
						.getReview()
						.getDecision()
						.getValue()
						.equals(R4EDecision.R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP)) {
					sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state FIXED, but Review"
							+ " Decision is set to Accepted with Followup" + R4EUIConstants.LINE_FEED);
					resultOk = false;
				}
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED)) {
				if (null == anomaly.getAnomaly().getFollowUpByID()
						|| ("").equals(anomaly.getAnomaly().getFollowUpByID())) {
					sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state VERIFIED and "
							+ "does not have a follower" + R4EUIConstants.LINE_FEED);
					resultOk = false;
				}
			}
		}
		if (!resultOk) {
			aMessage.set(sb.toString());
			return false;
		}
		return true;
	}

	/**
	 * Method checkCompletionStatus.
	 * 
	 * @param aMessage
	 *            AtomicReference<String>
	 * @return boolean
	 */
	public boolean checkReworkStatus(AtomicReference<String> aMessage) {
		final StringBuilder sb = new StringBuilder();
		boolean resultOk = true;
		for (R4EUIAnomalyBasic anomaly : fAnomalies) {
			if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state CREATED"
						+ R4EUIConstants.LINE_FEED);
				resultOk = false;
			} else if (null == anomaly.getAnomaly().getDecidedByID()
					|| ("").equals(anomaly.getAnomaly().getDecidedByID())) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") does not have a decider"
						+ R4EUIConstants.LINE_FEED);
				resultOk = false;
			}
		}
		if (!resultOk) {
			aMessage.set(sb.toString());
			return false;
		}
		return true;
	}
}
