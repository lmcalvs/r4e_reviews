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

package org.eclipse.mylyn.reviews.r4e.ui.model;

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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.AnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.CommandUtils;

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
	private static final String ANOMALY_CONTAINER_ICON_FILE = "icons/obj16/anmlycont_obj.gif";

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
		super(aParent, aName, null);
		fAnomalies = new ArrayList<R4EUIAnomalyBasic>();
		setImage(ANOMALY_CONTAINER_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	//Attributes

	/**
	 * Create a serialization model element object
	 * 
	 * @return the new serialization element object
	 */
	@Override
	public ReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EAnomaly tempAnomaly = null;
		R4EUIModelController.setDialogOpen(true);
		final AnomalyInputDialog dialog = new AnomalyInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite()
				.getWorkbenchWindow()
				.getShell());
		final int result = dialog.open();
		if (result == Window.OK) {
			tempAnomaly = RModelFactory.eINSTANCE.createR4EAnomaly();
			final R4ECommentType tempCommentType = RModelFactory.eINSTANCE.createR4ECommentType();
			tempAnomaly.setTitle(dialog.getAnomalyTitleValue());
			tempAnomaly.setDescription(dialog.getAnomalyDescriptionValue());
			if (null != dialog.getRuleReferenceValue()) {
				final R4EDesignRule rule = dialog.getRuleReferenceValue().getRule();
				tempCommentType.setType(rule.getClass_());
				tempAnomaly.setType(tempCommentType);
				tempAnomaly.setRank(rule.getRank());
				tempAnomaly.setRuleID(rule.getId());
			}
		}
		// else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
		return tempAnomaly;
	}

	//Hierarchy

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() { // $codepro.audit.disable
		return fAnomalies.toArray(new R4EUIAnomalyBasic[fAnomalies.size()]);
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
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
	 * // $codepro.audit.disable blockDepth Method loadModelData. Load the serialization model data into UI model
	 */
	@Override
	public void open() {

		R4EUIAnomalyBasic uiAnomaly = null;
		final IR4EUIModelElement parentElement = getParent();
		if (parentElement instanceof R4EUIFileContext) {

			//get anomalies that are specific to a file
			final List<R4EAnomaly> anomalies = ((R4EUIFileContext) parentElement).getAnomalies();
			R4EUITextPosition position = null;
			final int anomaliesSize = anomalies.size();
			R4EAnomaly anomaly = null;
			for (int i = 0; i < anomaliesSize; i++) {
				anomaly = anomalies.get(i);
				if (anomaly.isEnabled()
						|| Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					//Do not set position for global EList<E>lies
					position = null;
					EList<Location> locations = anomalies.get(i).getLocation(); // $codepro.audit.disable variableDeclaredInLoop
					if (null != locations) {
						if (null != locations.get(0)) {
							int locationsSize = locations.size(); // $codepro.audit.disable variableDeclaredInLoop
							for (int j = 0; j < locationsSize; j++) {
								position = new R4EUITextPosition(
										((R4EContent) anomalies.get(i).getLocation().get(j)).getLocation()); // $codepro.audit.disable methodChainLength
								if (((R4EUIReviewBasic) getParent().getParent().getParent()).getReview()
										.getType()
										.equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
									uiAnomaly = new R4EUIAnomalyBasic(this, anomalies.get(i), position);
								} else {
									uiAnomaly = new R4EUIAnomalyExtended(this, anomalies.get(i), position);
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
							|| Activator.getDefault()
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isEnabled()
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
	 * Method addChildren.
	 * 
	 * @param aChildToAdd
	 *            IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(ReviewNavigatorContentProvider)
	 */
	@Override
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws ResourceHandlingException,
			OutOfSyncException {
		final String user = R4EUIModelController.getReviewer();
		final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(((R4EUIReviewBasic) getParent()).getParticipant(
				user, true));
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
	 * Method createAnomaly
	 * 
	 * @param aAnomalyTempFileVersion
	 *            R4EFileVersion
	 * @param aUiPosition
	 *            - the position of the anomaly to create
	 * @return R4EUIAnomalyBasic
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIAnomalyBasic createAnomaly(R4EFileVersion aAnomalyTempFileVersion, R4EUITextPosition aUiPosition)
			throws ResourceHandlingException, OutOfSyncException {

		R4EUIAnomalyBasic uiAnomaly = null;

		//Get anomaliy details from user
		R4EUIModelController.setDialogOpen(true);
		final AnomalyInputDialog dialog = new AnomalyInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite()
				.getWorkbenchWindow()
				.getShell());
		final int result = dialog.open();

		if (result == Window.OK) {

			//Create anomaly model element
			final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
			final R4EParticipant participant = uiReview.getParticipant(R4EUIModelController.getReviewer(), true);
			final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(participant);

			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomaly,
					R4EUIModelController.getReviewer());
			anomaly.setTitle(dialog.getAnomalyTitleValue());
			anomaly.setDescription(dialog.getAnomalyDescriptionValue());
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);

			if (null != dialog.getRuleReferenceValue()) {
				final R4EDesignRule rule = dialog.getRuleReferenceValue().getRule();
				final R4ECommentType commentType = RModelFactory.eINSTANCE.createR4ECommentType();
				commentType.setType(rule.getClass_());
				anomaly.setType(commentType);
				anomaly.setRank(rule.getRank());
				anomaly.setRuleID(rule.getId());
			}

			//Set position data
			final R4EAnomalyTextPosition position = R4EUIModelController.FModelExt.createR4EAnomalyTextPosition(R4EUIModelController.FModelExt.createR4ETextContent(anomaly));

			//Set File version data
			final R4EFileVersion anomalyFileVersion = R4EUIModelController.FModelExt.createR4EFileVersion(position);
			CommandUtils.copyFileVersionData(anomalyFileVersion, aAnomalyTempFileVersion);

			//Create and set UI model element
			if (uiReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_BASIC)) {
				uiAnomaly = new R4EUIAnomalyBasic(this, anomaly, aUiPosition);
			} else {
				uiAnomaly = new R4EUIAnomalyExtended(this, anomaly, aUiPosition);
				if (uiReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
					((R4EUIAnomalyExtended) uiAnomaly).updateState(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED);
				} else { //R4EReviewType.R4E_REVIEW_TYPE_INFORMAL
					((R4EUIAnomalyExtended) uiAnomaly).updateState(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED);
				}
			}
			aUiPosition.setPositionInModel(position);
			uiAnomaly.setToolTip(R4EUIAnomalyBasic.buildAnomalyToolTip(anomaly)); //Also set UI tooltip immediately
			addChildren(uiAnomaly);
		}
		// else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
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
		if (!(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddChildElementCmd()
	 */
	@Override
	public boolean isAddChildElementCmd() {
		//If this is a formal review, we need to be in the preparation phase
		if (R4EUIModelController.getActiveReview().getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
			if (!(((R4EFormalReview) R4EUIModelController.getActiveReview().getReview()).getCurrent().getType().equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION))) {
				return false;
			}
		}
		if (getParent().isEnabled() && !(R4EUIModelController.getActiveReview().isUserReviewed())) {
			if (getParent() instanceof R4EUIReviewBasic) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method getAddChildElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdName()
	 */
	@Override
	public String getAddChildElementCmdName() {
		return NEW_CHILD_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getAddChildElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdTooltip()
	 */
	@Override
	public String getAddChildElementCmdTooltip() {
		return NEW_CHILD_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method checkCompletionStatus.
	 * 
	 * @return boolean
	 */
	public boolean checkCompletionStatus(AtomicReference<String> aMessage) { // $codepro.audit.disable booleanMethodNamingConvention
		StringBuilder sb = new StringBuilder();
		boolean resultOk = true;
		for (R4EUIAnomalyBasic anomaly : fAnomalies) {
			if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state CREATED"
						+ System.getProperty("line.separator"));
				resultOk = false;
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ASSIGNED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state ASSIGNED"
						+ System.getProperty("line.separator"));
				resultOk = false;
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_ACCEPTED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state ACCEPTED"
						+ System.getProperty("line.separator"));
				resultOk = false;
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_FIXED)) {
				if (null == anomaly.getAnomaly().getFixedByID() || ("").equals(anomaly.getAnomaly().getFixedByID())) {
					sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") does not have a fixer"
							+ System.getProperty("line.separator"));
					resultOk = false;
				}
				if (R4EUIModelController.getActiveReview()
						.getReview()
						.getDecision()
						.getValue()
						.equals(R4EDecision.R4E_REVIEW_DECISION_ACCEPTED_FOLLOWUP)) {
					sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state FIXED, but Review"
							+ " Decision is set to Accepted with Followup" + System.getProperty("line.separator"));
					resultOk = false;
				}
			} else if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_VERIFIED)) {
				if (null == anomaly.getAnomaly().getFollowUpByID()
						|| ("").equals(anomaly.getAnomaly().getFollowUpByID())) {
					sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state VERIFIED and "
							+ "does not have a follower" + System.getProperty("line.separator"));
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
	 * @return boolean
	 */
	public boolean checkReworkStatus(AtomicReference<String> aMessage) {
		StringBuilder sb = new StringBuilder();
		boolean resultOk = true;
		for (R4EUIAnomalyBasic anomaly : fAnomalies) {
			if (anomaly.getAnomaly().getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_CREATED)) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") is in state CREATED"
						+ System.getProperty("line.separator"));
				resultOk = false;
			} else if (null == anomaly.getAnomaly().getDecidedByID()
					|| ("").equals(anomaly.getAnomaly().getDecidedByID())) {
				sb.append("Anomaly (" + anomaly.getAnomaly().getTitle() + ") does not have a decider"
						+ System.getProperty("line.separator"));
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
