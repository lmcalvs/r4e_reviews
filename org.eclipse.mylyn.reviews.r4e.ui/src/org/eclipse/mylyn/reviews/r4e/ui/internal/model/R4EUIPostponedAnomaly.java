// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
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

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.mylyn.reviews.core.model.IComment;
import org.eclipse.mylyn.reviews.core.model.IReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.VersionUtils;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.PostponedAnomalyProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIPostponedAnomaly extends R4EUIAnomalyExtended {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field POSTPONED_ANOMALY_ICON_FILE. (value is ""icons/obj16/postanmly_obj.gif"")
	 */
	public static final String POSTPONED_ANOMALY_ICON_FILE = "icons/obj16/postanmly_obj.gif";

	/**
	 * Field COMPATIBILITY_ERROR_MESSAGE. (value is ""Original Anomaly cannot be updated as its meta-data version is
	 * more recent than the current application meta-data.\nOperation cancelled"")
	 */
	private static final String COMPATIBILITY_ERROR_MESSAGE = "Original Anomaly cannot be updated as its meta-data version is more recent than the current application meta-data."
			+ R4EUIConstants.LINE_FEED + "Operation cancelled";

	/**
	 * Field COMPATIBILITY_WARNING_DIALOG_TITLE. (value is ""Original Anomaly Version Mismatch Detected"")
	 */
	private static final String COMPATIBILITY_WARNING_DIALOG_TITLE = "Original Anomaly Version Mismatch Detected";

	/**
	 * Field COMPATIBILITY_WARNING_MESSAGE. (value is ""Original Anomaly format version is older than the one currently
	 * handled by this version of R4E.\n You can update the original Anomaly, which will upgrade its parent Review
	 * version to the current one, or cancel the operation."")
	 */
	private static final String COMPATIBILITY_WARNING_MESSAGE = "Original Anomaly format version is older than the one currently handled by this version of R4E."
			+ R4EUIConstants.LINE_FEED
			+ "You can update the original Anomaly, which will upgrade its parent Review version to the current one, or cancel the operation.";

	/**
	 * Field COMPATIBILITY_WARNING_DIALOG_BUTTONS.
	 */
	private static final String[] COMPATIBILITY_WARNING_DIALOG_BUTTONS = { "Update Anomaly", "Cancel" };

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

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return "Original Review: " + getOriginalReviewName() + R4EUIConstants.LIST_SEPARATOR
				+ R4EUIAnomalyBasic.buildAnomalyToolTip(fAnomaly);
	}

	/**
	 * Method getAdapter.
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) {
			return this;
		}
		if (IPropertySource.class.equals(adapter)) {
			return new PostponedAnomalyProperties(this);
		}
		return null;
	}

	//Attributes

	/**
	 * Method getOriginalReviewName.
	 * 
	 * @return String
	 */
	public String getOriginalReviewName() {
		String originalReviewName = fAnomaly.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME);
		//TODO:  This is for backwards compatibility and should eventually be removed
		if (null == originalReviewName) {
			originalReviewName = ((R4EUIPostponedFile) getParent()).getFileContext()
					.getInfoAtt()
					.get(R4EUIConstants.POSTPONED_ATTR_ORIG_REVIEW_NAME);
		}
		return originalReviewName;
	}

	/**
	 * Method updateAnomaly
	 * 
	 * @param aPostponedAnomaly
	 *            R4EAnomaly
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public void updateAnomaly(R4EAnomaly aPostponedAnomaly) throws ResourceHandlingException, OutOfSyncException {

		//Disable the anomaly if it is not postponed anymore
		if (aPostponedAnomaly.getState().equals(R4EAnomalyState.DEFERRED)) {
			fAnomaly.setEnabled(aPostponedAnomaly.isEnabled());
			//Close Anomaly if disabled
			if (!fAnomaly.isEnabled()) {
				close();
			}
		} else {
			fAnomaly.setEnabled(false); //Disable anomaly if it is not postponed to begin with
			close();
		}

		//Update postponed anomaly state
		updateState(aPostponedAnomaly.getState());
	}

	/**
	 * Method updateOriginalAnomaly
	 * 
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws CompatibilityException
	 */
	public void updateOriginalAnomaly() throws ResourceHandlingException, OutOfSyncException, CompatibilityException {
		final String origReviewName = getOriginalReviewName();
		R4EUIReviewGroup uiGroup = null;
		if (getParent() instanceof R4EUIPostponedFile) {
			uiGroup = (R4EUIReviewGroup) getParent().getParent().getParent().getParent();
		} else if (getParent() instanceof R4EUIAnomalyContainer) {
			uiGroup = (R4EUIReviewGroup) getParent().getParent().getParent().getParent();
		} else {
			return; //should never happen
		}
		final R4EReview origReview = R4EUIModelController.FModelExt.openR4EReview(uiGroup.getReviewGroup(),
				origReviewName);

		//Open original review
		final R4EAnomaly origAnomaly = CommandUtils.getOriginalAnomaly(origReview, fAnomaly);
		if (null != origAnomaly) {
			//set data in original anomaly
			CommandUtils.copyAnomalyData(origAnomaly, fAnomaly);
		}
		//Close original review
		R4EUIModelController.FModelExt.closeR4EReview(origReview);
	}

	/**
	 * Check version compatibility between the element(s) to load and the current R4E application
	 * 
	 * @throws CompatibilityException
	 * @throws ResourceHandlingException
	 */
	public boolean checkCompatibility() throws ResourceHandlingException, CompatibilityException {
		final String origReviewName = getOriginalReviewName();
		final R4EUIReviewGroup uiGroup = (R4EUIReviewGroup) getParent().getParent().getParent().getParent();
		R4EReview originalReview = R4EUIModelController.FModelExt.openR4EReview(uiGroup.getReviewGroup(),
				origReviewName);
		String currentVersion = Persistence.Roots.REVIEW.getVersion();
		int checkResult = VersionUtils.compareVersions(currentVersion, originalReview.getFragmentVersion());
		switch (checkResult) {
		case R4EUIConstants.VERSION_APPLICATION_OLDER:
			displayCompatibilityErrorDialog();
			return false;
		case R4EUIConstants.VERSION_APPLICATION_NEWER:
			int result = displayCompatibilityWarningDialog(originalReview.getFragmentVersion(), currentVersion);
			switch (result) {
			case R4EUIConstants.OPEN_NORMAL:
				//Upgrade version immediately
				try {
					Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(originalReview,
							R4EUIModelController.getReviewer());
					originalReview.setFragmentVersion(currentVersion);
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				} catch (ResourceHandlingException e) {
					UIUtils.displayResourceErrorDialog(e);
					return false;
				} catch (OutOfSyncException e) {
					UIUtils.displaySyncErrorDialog(e);
					return false;
				}
				fReadOnly = false;
				return true;
			default:
				//Assume Cancel
				return false;
			}
		default:
			//Normal case, do nothing
			fReadOnly = false;
			return true;
		}
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
		R4EParticipant participant = uiReview.getParticipant(aPostponedComment.getAuthor().getId(), false);
		boolean isParticipant = true;
		if (null == participant) {
			participant = uiReview.getParticipant(aPostponedComment.getAuthor().getId(), true);
			isParticipant = false;
		}

		//Copy comment information from postponed comment model element
		R4EUIComment uiComment = null;
		final R4EComment comment = R4EUIModelController.FModelExt.createR4EComment(participant, fAnomaly);
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(comment, R4EUIModelController.getReviewer());
		comment.setDescription(aPostponedComment.getDescription());
		comment.setCreatedOn(aPostponedComment.getCreatedOn());
		final Map<String, String> info = comment.getInfoAtt(); //We use the R4EComment attribute map to store the original comment ID
		info.put(R4EUIConstants.POSTPONED_ATTR_ORIG_COMMENT_ID, CommandUtils.buildOriginalCommentID(aPostponedComment));
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
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	@Override
	public IR4EUIModelElement createChildren(IReviewComponent aModelComponent) throws ResourceHandlingException,
			OutOfSyncException, CompatibilityException {

		//First create the children on the original anomaly...
		R4EComment origComment = null;

		//Open original review
		final String origReviewName = getOriginalReviewName();
		final R4EUIReviewGroup uiGroup = (R4EUIReviewGroup) getParent().getParent().getParent().getParent();
		final R4EReview origReview = R4EUIModelController.FModelExt.openR4EReview(uiGroup.getReviewGroup(),
				origReviewName);
		final R4EAnomaly origAnomaly = CommandUtils.getOriginalAnomaly(origReview, fAnomaly);
		if (null != origAnomaly) {
			//Add Child Comments to original anomaly
			final R4EParticipant participantInOrigReview = CommandUtils.getParticipantForReview(origReview,
					R4EUIModelController.getReviewer());
			origComment = R4EUIModelController.FModelExt.createR4EComment(participantInOrigReview, origAnomaly);
			final Long origBookNum = R4EUIModelController.FResourceUpdater.checkOut(origComment,
					participantInOrigReview.getId());
			origComment.setDescription(((IComment) aModelComponent).getDescription());
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
		if (isEnabled() && null != R4EUIModelController.getActiveReview()
				&& null != ((R4EUIPostponedFile) getParent()).getTargetFileVersion()) {
			return true;
		}
		return false;
	}

	/**
	 * Method displayCompatibilityErrorDialog.
	 */
	public void displayCompatibilityErrorDialog() {
		R4EUIPlugin.Ftracer.traceError(COMPATIBILITY_ERROR_MESSAGE);
		final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
				"Compatibility problem Detected", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
						COMPATIBILITY_ERROR_MESSAGE, null), IStatus.ERROR);
		dialog.open();
	}

	/**
	 * Method displayCompatibilityWarningDialog.
	 * 
	 * @return boolean
	 */
	public int displayCompatibilityWarningDialog(String aDataVersion, String aApplVersionl) {
		R4EUIPlugin.Ftracer.traceWarning(COMPATIBILITY_WARNING_MESSAGE);
		final MessageDialog dialog = new MessageDialog(null, COMPATIBILITY_WARNING_DIALOG_TITLE, null,
				COMPATIBILITY_WARNING_MESSAGE + R4EUIConstants.LINE_FEED + "Element meta-data Version: " + aDataVersion
						+ R4EUIConstants.LINE_FEED + "Application meta-data Version: " + aApplVersionl,
				MessageDialog.QUESTION_WITH_CANCEL, COMPATIBILITY_WARNING_DIALOG_BUTTONS, 0);
		return dialog.open();
	}
}
