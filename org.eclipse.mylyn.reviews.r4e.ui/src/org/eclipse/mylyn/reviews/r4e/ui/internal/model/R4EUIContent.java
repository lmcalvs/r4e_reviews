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
 * This class implements the Content element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.ContentsProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class R4EUIContent extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field PASTE_ELEMENT_COMMAND_NAME. (value is ""Paste Anomalies"")
	 */
	private static final String PASTE_ELEMENT_COMMAND_NAME = "Paste Anomalies";

	/**
	 * Field PASTE_ELEMENT_COMMAND_TOOLTIP. (value is ""Clone anomalies in Clipboard using the Contents position" +
	 * " from its Parent Container"")
	 */
	private static final String PASTE_ELEMENT_COMMAND_TOOLTIP = "Clone anomalies in Clipboard using the Contents position";

	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME. (value is ""Delete Content"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Content";

	/**
	 * Field REMOVE_ELEMENT_ACTION_TOOLTIP. (value is ""Disable (and Optionally Remove) this Content" +
	 * " from its Parent Container"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable (and Optionally Remove) this Content"
			+ " from its Parent Container";

	/**
	 * Field RESTORE_ELEMENT_COMMAND_NAME. (value is ""Restore Content"")
	 */
	private static final String RESTORE_ELEMENT_COMMAND_NAME = "Restore Content";

	/**
	 * Field RESTORE_ELEMENT_ACTION_TOOLTIP. (value is ""Restore this disabled Content"")
	 */
	private static final String RESTORE_ELEMENT_COMMAND_TOOLTIP = "Restore this disabled Content";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fDelta.
	 */
	private final R4EDelta fContent;

	/**
	 * Field fPosition.
	 */
	private final IR4EUIPosition fPosition;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIContent.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aDelta
	 *            R4EDelta
	 * @param aPosition
	 *            IR4EUIPosition
	 */
	protected R4EUIContent(IR4EUIModelElement aParent, R4EDelta aDelta, IR4EUIPosition aPosition) {
		super(aParent, aPosition.toString());
		fReadOnly = aParent.isReadOnly();
		fContent = aDelta;
		fPosition = aPosition;

		//Remove check on parent, since at least one children is not set anymore
		try {
			getParent().getParent().setUserReviewed(false, false, true);
		} catch (OutOfSyncException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		}
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
		return R4EUIConstants.AUTHOR_LABEL + ((R4EItem) fContent.eContainer().eContainer()).getAddedBy().getId();
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
			return new ContentsProperties(this);
		}
		return null;
	}

	//Attributes

	/**
	 * Method getContent.
	 * 
	 * @return R4EDelta
	 */
	public R4EDelta getContent() {
		return fContent;
	}

	/**
	 * Method getPosition.
	 * 
	 * @return IR4EPosition
	 */
	public IR4EUIPosition getPosition() {
		return fPosition;
	}

	/**
	 * Method setReviewed.
	 * 
	 * @param aReviewed
	 *            boolean
	 * @param aSetChildren
	 *            boolean
	 * @param aUpdateModel
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setUserReviewed(boolean, boolean,
	 *      boolean)
	 */
	@Override
	public void setUserReviewed(boolean aReviewed, boolean aSetChildren, boolean aUpdateModel)
			throws ResourceHandlingException, OutOfSyncException {
		if (fUserReviewed != aReviewed) { //Reviewed state is changed
			fUserReviewed = aReviewed;
			if (fUserReviewed) {
				if (aUpdateModel) {
					//Add delta to the reviewedContent for this user
					addContentReviewed();
				}
				//Check to see if we should mark the parent reviewed as well
				getParent().getParent().checkToSetUserReviewed(aUpdateModel);
			} else {
				if (aUpdateModel) {
					//Remove delta from the reviewedContent for this user
					removeContentReviewed();
				}

				//Remove check on parent, since at least one children is not set anymore
				getParent().getParent().setUserReviewed(fUserReviewed, false, aUpdateModel);
			}

			//Update inline markings
			final R4EUIContent currentContent = this;
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					UIUtils.updateAnnotation(currentContent, (R4EUIFileContext) getParent().getParent());
				}
			});
		}
	}

	/**
	 * Method setChildrenReviewed.
	 * 
	 * @param aUpdateModel
	 *            - flag that is used to see whether we should also update the serialization model
	 * @param aReviewed
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setChildUserReviewed(boolean, boolean)
	 */
	@Override
	public void setChildUserReviewed(boolean aReviewed, boolean aUpdateModel) throws ResourceHandlingException,
			OutOfSyncException {
		if (fUserReviewed != aReviewed) { //Reviewed state is changed
			fUserReviewed = aReviewed;
			if (aUpdateModel) {
				if (aReviewed) {
					//Add delta to the reviewedContent for this user
					addContentReviewed();
				} else {
					//Remove delta from the reviewedContent for this user
					removeContentReviewed();
				}
			}
			fUserReviewed = aReviewed;

			//Update inline markings
			final R4EUIContent currentContent = this;
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					UIUtils.updateAnnotation(currentContent, (R4EUIFileContext) getParent().getParent());
				}
			});
		}
	}

	/**
	 * Method addContentReviewed.
	 * 
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addContentReviewed() throws ResourceHandlingException, OutOfSyncException {
		//First get the current user
		final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent().getParent().getParent(); // $codepro.audit.disable methodChainLength
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), true);

		//Add this content to the reviewed contents for this user
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
		user.getReviewedContent().add(fContent.getId());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Method removeContentReviewed.
	 * 
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void removeContentReviewed() throws ResourceHandlingException, OutOfSyncException {
		//First get the current user
		final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent().getParent().getParent(); // $codepro.audit.disable methodChainLength
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

		if (null != user) {
			//Remove this content from the reviewed contents for this user
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
			user.getReviewedContent().remove(fContent.getId());
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}
	}

	/**
	 * Method setEnabled.
	 * 
	 * @param aEnabled
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException {
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fContent,
				R4EUIModelController.getReviewer());
		fContent.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fContent.isEnabled();
	}

	/**
	 * Method addAssignees.
	 * 
	 * @param aParticipants
	 *            - List<R4EParticipant> aParticipants
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addAssignees(List<R4EParticipant>,
	 *      boolean)
	 */
	@Override
	public void addAssignees(List<R4EParticipant> aParticipants) {
		try {
			//Set new participants assigned
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fContent,
					R4EUIModelController.getReviewer());
			final EList<String> assignedParticipants = fContent.getAssignedTo();
			for (R4EParticipant participant : aParticipants) {
				assignedParticipants.add(participant.getId());
				((R4EUIReviewBasic) getParent().getParent().getParent().getParent()).getParticipant(
						participant.getId(), true);
			}
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e1) {
			UIUtils.displayResourceErrorDialog(e1);
		} catch (OutOfSyncException e1) {
			UIUtils.displaySyncErrorDialog(e1);
		}
	}

	/**
	 * Method removeAssignees.
	 * 
	 * @param aParticipants
	 *            - List<R4EParticipant> aParticipants
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeAssignees(List<R4EParticipant>,
	 *      boolean)
	 */
	@Override
	public void removeAssignees(List<R4EParticipant> aParticipants) {
		try {
			//Set new partcipants assigned
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fContent,
					R4EUIModelController.getReviewer());
			final EList<String> assignedParticipants = fContent.getAssignedTo();
			for (R4EParticipant participant : aParticipants) {
				assignedParticipants.remove(participant.getId());
			}
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e1) {
			UIUtils.displayResourceErrorDialog(e1);
		} catch (OutOfSyncException e1) {
			UIUtils.displaySyncErrorDialog(e1);
		}
	}

	/**
	 * Method restore.
	 * 
	 * @throws CompatibilityException
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	@Override
	public void restore() throws ResourceHandlingException, OutOfSyncException, CompatibilityException {
		super.restore();

		//Update inline markings (local anomalies only)
		final R4EUIContent currentContent = this;
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				UIUtils.updateAnnotation(currentContent, (R4EUIFileContext) getParent().getParent());
			}
		});

		//Also restore any participant assigned to this element
		for (String participant : fContent.getAssignedTo()) {
			R4EUIModelController.getActiveReview().getParticipant(participant, true);
		}
	}

	//Commands

	/**
	 * Checks if the corresponding model element is assigned to a user
	 * 
	 * @param aUserName
	 *            - the user name
	 * @param aCheckChildren
	 *            - a flag that determines whether we will also check the child elements
	 * @return true/false
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isAssigned(String, boolean)
	 */
	@Override
	public boolean isAssigned(String aUsername, boolean aCheckChildren) {
		if (fContent.isEnabled() && fContent.getAssignedTo().contains(aUsername)) {
			return true;
		}
		return false;
	}

	/**
	 * Method isAddLinkedAnomalyCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isAddLinkedAnomalyCmd()
	 */
	@Override
	public boolean isAddLinkedAnomalyCmd() {
		if (isEnabled()
				&& !isReadOnly()
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))) {
			return true;
		}
		return false;
	}

	/**
	 * Method isOpenEditorCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	@Override
	public boolean isOpenEditorCmd() {
		if (isEnabled() && null != ((R4EUIFileContext) getParent().getParent()).getTargetFileVersion()) {
			return true;
		}
		return false;
	}

	/**
	 * Method isPasteElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isPasteElementCmd()
	 */
	@Override
	public boolean isPasteElementCmd() {
		if (isEnabled()
				&& !isReadOnly()
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))) {
			//We can only paste if there is a least 1 Anomaly in the clipboard
			Object element = null;
			ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
			if (selection instanceof IStructuredSelection) {
				for (final Iterator<?> iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();) {
					element = iterator.next();
					if (element instanceof R4EUIAnomalyBasic) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Method getPasteElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getPasteElementCmdName()
	 */
	@Override
	public String getPasteElementCmdName() {
		return PASTE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getPasteElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getPasteElementCmdTooltip()
	 */
	@Override
	public String getPasteElementCmdTooltip() {
		return PASTE_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isChangeReviewStateCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isChangeUserReviewStateCmd()
	 */
	@Override
	public boolean isChangeUserReviewStateCmd() {
		if (isEnabled()
				&& !isReadOnly()
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))) {
			return true;
		}
		return false;
	}

	/**
	 * Method isAssignToCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isAssignToCmd()
	 */
	@Override
	public boolean isAssignToCmd() {
		if (isEnabled()
				&& !isReadOnly()
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))) {
			return true;
		}
		return false;
	}

	/**
	 * Method isUnassignToCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isUnassignToCmd()
	 */
	@Override
	public boolean isUnassignToCmd() {
		if (isEnabled()
				&& !isReadOnly()
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))
				&& fContent.getAssignedTo().size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method isRemoveElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		if (isEnabled()
				&& !isReadOnly()
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))) {
			return true;
		}
		return false;
	}

	/**
	 * Method getRemoveElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	@Override
	public String getRemoveElementCmdName() {
		return REMOVE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getRemoveElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	@Override
	public String getRemoveElementCmdTooltip() {
		return REMOVE_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (!(getParent().getParent().isEnabled())) {
			return false;
		}
		if (isEnabled()
				|| isReadOnly()
				|| ((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(
						R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED)) {
			return false;
		}
		return true;
	}

	/**
	 * Method getRestoreElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdName()
	 */
	@Override
	public String getRestoreElementCmdName() {
		return RESTORE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getRestoreElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdTooltip()
	 */
	@Override
	public String getRestoreElementCmdTooltip() {
		return RESTORE_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isSendEmailCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isSendEmailCmd()
	 */
	@Override
	public boolean isSendEmailCmd() {
		if (isEnabled()) {
			return true;
		}
		return false;
	}
}
