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
 * This class implements the File Context element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.FileContextProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIFileContext extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field FILE_CONTEXT_ICON_FILE. (value is ""icons/obj16/filectx_obj.gif"")
	 */
	public static final String FILE_CONTEXT_ICON_FILE = "icons/obj16/filectx_obj.gif";

	/**
	 * Field REMOVED_OVERLAY_ICON_FILE.
	 */
	public static final String REMOVED_OVERLAY_ICON_FILE = "icons/ovr16/removr_tsk.png";

	/**
	 * Field ADDED_OVERLAY_ICON_FILE.
	 */
	public static final String ADDED_OVERLAY_ICON_FILE = "icons/ovr16/addovr_tsk.png";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fFile.
	 */
	protected final R4EFileContext fFile;

	/**
	 * Field fContentsContainer.
	 */
	private R4EUIContentsContainer fContentsContainer = null;

	/**
	 * Field fAnomalyContainer.
	 */
	private R4EUIAnomalyContainer fAnomalyContainer = null;

	/**
	 * Field fAnomalies.
	 */
	protected List<R4EAnomaly> fAnomalies = null; //Used to cache anomalies for this file context (used at startup)

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIFileContext.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aFile
	 *            R4EFileContext
	 * @param aParentItemType
	 *            - int
	 */
	public R4EUIFileContext(IR4EUIModelElement aParent, R4EFileContext aFile, int aParentItemType) {
		super(aParent, "");
		if (null != aFile.getTarget()) {
			setName(aFile.getTarget().getName());
		} else if (null != aFile.getBase()) {
			setName(aFile.getBase().getName());
		}
		if (aParentItemType == R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT) {
			fContentsContainer = new R4EUIDeltaContainer(this, R4EUIConstants.DELTAS_LABEL);
		} else {
			fContentsContainer = new R4EUISelectionContainer(this, R4EUIConstants.SELECTIONS_LABEL);
		}
		fAnomalyContainer = new R4EUIAnomalyContainer(this, R4EUIConstants.ANOMALIES_LABEL);
		fFile = aFile;
		setImage(FILE_CONTEXT_ICON_FILE);
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
		return getNavigatorTooltip(fFile.getTarget(), fFile.getBase());
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
			return new FileContextProperties(this);
		}
		return null;
	}

	//Attributes

	/**
	 * Method getAnomalies.
	 * 
	 * @return List<R4EAnomaly>
	 */
	public List<R4EAnomaly> getAnomalies() {
		return fAnomalies;
	}

	/**
	 * Method getBaseFileVersion.
	 * 
	 * @return R4EFileVersion
	 */
	public R4EFileVersion getBaseFileVersion() {
		return fFile.getBase();
	}

	/**
	 * Method getTargetFileVersion.
	 * 
	 * @return R4EFileVersion
	 */
	public R4EFileVersion getTargetFileVersion() {
		return fFile.getTarget();
	}

	/**
	 * Method getRemovedImage.
	 * 
	 * @return Image
	 */
	public Image getRemovedImage() {
		return UIUtils.loadIcon(REMOVED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getAddedImage.
	 * 
	 * @return Image
	 */
	public Image getAddedImage() {
		return UIUtils.loadIcon(ADDED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getFileContext.
	 * 
	 * @return R4EFileContext
	 */
	public R4EFileContext getFileContext() {
		return fFile;
	}

	/**
	 * Method setReviewed.
	 * 
	 * @param aReviewed
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setUserReviewed(boolean)
	 */
	@Override
	public void setUserReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException {
		if (fUserReviewed != aReviewed) { //Reviewed state is changed
			fUserReviewed = aReviewed;
			if (fUserReviewed) {
				//Add delta to the reviewedContent for this user
				addContentReviewed();

				//Also set the children
				final int length = fContentsContainer.getChildren().length;
				for (int i = 0; i < length; i++) {
					fContentsContainer.getChildren()[i].setChildUserReviewed(aReviewed);
				}

				//Check to see if we should mark the parent reviewed as well
				getParent().checkToSetUserReviewed();
			} else {
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();

				//Remove check on parent, since at least one children is not set anymore
				getParent().setUserReviewed(fUserReviewed);
			}
			fireUserReviewStateChanged(this, R4EUIConstants.CHANGE_TYPE_REVIEWED_STATE);
		}
	}

	/**
	 * Method getNavigatorTooltip.
	 * 
	 * @param aTarget
	 *            R4EFileVersion
	 * @param aBase
	 *            R4EFileVersion
	 * @return String
	 */
	public static String getNavigatorTooltip(R4EFileVersion aTarget, R4EFileVersion aBase) {

		//The tooltip shows the file versions
		final StringBuilder tooltip = new StringBuilder();

		tooltip.append("Base Version: ");
		if (null != aBase) {
			tooltip.append(aBase.getVersionID());
		} else {
			tooltip.append("(not present)");
		}
		tooltip.append(R4EUIConstants.LINE_FEED);
		tooltip.append("Target Version: ");
		if (null != aTarget) {
			tooltip.append(aTarget.getVersionID());
		} else {
			tooltip.append("(not present)");
		}
		return tooltip.toString();
	}

	/**
	 * Method setChildrenReviewed.
	 * 
	 * @param aReviewed
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setChildUserReviewed(boolean)
	 */
	@Override
	public void setChildUserReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException {
		if (fUserReviewed != aReviewed) { //Reviewed state is changed
			fUserReviewed = aReviewed;
			if (aReviewed) {
				//Add delta to the reviewedContent for this user
				addContentReviewed();

				//Also set the children
				final int length = fContentsContainer.getChildren().length;
				for (int i = 0; i < length; i++) {
					fContentsContainer.getChildren()[i].setChildUserReviewed(aReviewed);
				}
			} else {
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
			}
			fUserReviewed = aReviewed;
			fireUserReviewStateChanged(this, R4EUIConstants.CHANGE_TYPE_REVIEWED_STATE);
		}
	}

	/**
	 * Method checkToSetReviewed.
	 * 
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#checkToSetUserReviewed()
	 */
	@Override
	public void checkToSetUserReviewed() throws ResourceHandlingException, OutOfSyncException {
		boolean allChildrenReviewed = true;
		final int length = fContentsContainer.getChildren().length;
		for (int i = 0; i < length; i++) {
			if (!(fContentsContainer.getChildren()[i].isUserReviewed())) {
				allChildrenReviewed = false;
			}
		}
		//If all children are reviewed, mark the parent as reviewed as well
		if (allChildrenReviewed) {
			fUserReviewed = true;
			addContentReviewed();
			getParent().checkToSetUserReviewed();
			fireUserReviewStateChanged(this, R4EUIConstants.CHANGE_TYPE_REVIEWED_STATE);
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
		final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent();
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), true);

		//Add this content to the reviewed contents for this user
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
		user.getReviewedContent().add(fFile.getId());
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
		final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent();
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

		if (null != user) {
			//Remove this content from the reviewed contents for this user
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
			user.getReviewedContent().remove(fFile.getId());
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setUserReviewed(boolean)
	 */
	@Override
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException {
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fFile, R4EUIModelController.getReviewer());
		fFile.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fFile.isEnabled();
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
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fFile,
					R4EUIModelController.getReviewer());
			EList<String> assignedParticipants = fFile.getAssignedTo();
			for (R4EParticipant participant : aParticipants) {
				assignedParticipants.add(participant.getId());

				//If this user is not a participant, add it to the review
				if (!((R4EUIReviewBasic) getParent().getParent()).isParticipant(participant.getId())) {
					((R4EUIReviewBasic) getParent().getParent()).getParticipantContainer().createChildren(participant);
				}
			}
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e1) {
			UIUtils.displayResourceErrorDialog(e1);
		} catch (OutOfSyncException e1) {
			UIUtils.displaySyncErrorDialog(e1);
		}

		//Also assign children
		for (R4EUIContent content : fContentsContainer.fContents) {
			content.addAssignees(aParticipants);
		}
	}

	/**
	 * Method removeAssignees.
	 * 
	 * @param aParticipants
	 *            - List<R4EParticipant> aParticipants
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeAssignees(List<R4EParticipant>)
	 */
	@Override
	public void removeAssignees(List<R4EParticipant> aParticipants) {
		try {
			//Set new participants assigned
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fFile,
					R4EUIModelController.getReviewer());
			EList<String> assignedParticipants = fFile.getAssignedTo();
			for (R4EParticipant participant : aParticipants) {
				assignedParticipants.remove(participant.getId());
			}
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		} catch (ResourceHandlingException e1) {
			UIUtils.displayResourceErrorDialog(e1);
		} catch (OutOfSyncException e1) {
			UIUtils.displaySyncErrorDialog(e1);
		}

		//Also assign children
		if (null != fContentsContainer) {
			for (R4EUIContent content : fContentsContainer.fContents) {
				content.removeAssignees(aParticipants);
			}
		}
	}

	/**
	 * Method getNumChanges.
	 * 
	 * @return int
	 */
	public int getNumChanges() {
		IR4EUIModelElement container = getContentsContainerElement();
		if (null != container) {
			IR4EUIModelElement[] children = container.getChildren();
			if (null != children) {
				return children.length;
			}
		}
		return 0;
	}

	/**
	 * Method getNumReviewedChanges.
	 * 
	 * @return int
	 */
	public int getNumReviewedChanges() {
		//NOTE:  We only check the file and assume all changes are reviewed if the file is, or none if
		//the file is not
		if (isUserReviewed()) {
			return getNumChanges();
		}
		return 0;
	}

	/**
	 * Method getNumAnomalies.
	 * 
	 * @return int
	 */
	public int getNumAnomalies() {
		IR4EUIModelElement container = getAnomalyContainerElement();
		if (null != container) {
			IR4EUIModelElement[] children = container.getChildren();
			if (null != children) {
				return children.length;
			}
		}
		return 0;
	}

	//Hierarchy

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		final List<IR4EUIModelElement> newList = new ArrayList<IR4EUIModelElement>();
		if (fContentsContainer.getChildren().length > 0) {
			newList.add(fContentsContainer);
		}
		if (fAnomalyContainer.getChildren().length > 0) {
			newList.add(fAnomalyContainer);
		}
		return newList.toArray(new IR4EUIModelElement[newList.size()]);
	}

	/**
	 * Method getContenssContainerElement.
	 * 
	 * @return IR4EUIModelElement
	 */
	public R4EUIContentsContainer getContentsContainerElement() {
		return fContentsContainer;
	}

	/**
	 * Method getAnomalyContainerElement.
	 * 
	 * @return IR4EUIModelElement
	 */
	public R4EUIAnomalyContainer getAnomalyContainerElement() {
		return fAnomalyContainer;
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		//Children are only present only when the Tree view is displayed, if there are changes and/or anomalies.
		if ((0 == fContentsContainer.getChildren().length && 0 == fAnomalyContainer.getChildren().length)
				|| !R4EUIModelController.getNavigatorView().isDefaultDisplay()) {
			return false;
		}
		return true;
	}

	/**
	 * Close the model element (i.e. disable it)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		fContentsContainer.close();
		fAnomalyContainer.close();
		fOpen = false;
		removeListeners();
	}

	/**
	 * Method open. Load the serialization model data into UI model
	 */
	@Override
	public void open() {

		//Restore resource data in serialization model
		IRFSRegistry revRegistry = null;
		try {
			revRegistry = RFSRegistryFactory.getRegistry(((R4EUIReviewBasic) this.getParent().getParent()).getReview());
		} catch (ReviewsFileStorageException e1) {
			R4EUIPlugin.Ftracer.traceInfo("Exception: " + e1.toString() + " (" + e1.getMessage() + ")");
			R4EUIPlugin.getDefault().logInfo("Exception: " + e1.toString(), e1);
		}

		//Restore base file version (if it exists)
		final R4EFileVersion baseFileVersion = fFile.getBase();
		if (null != baseFileVersion) {
			try {
				final IFile baseFile = ResourceUtils.toIFile(baseFileVersion.getPlatformURI());
				baseFileVersion.setResource(baseFile);
			} catch (FileNotFoundException e) {
				R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				baseFileVersion.setResource(null);
			}

			baseFileVersion.setFileRevision(null);
			if (null != revRegistry) {
				try {
					final IFileRevision fileRev = revRegistry.getIFileRevision(null, baseFileVersion);
					baseFileVersion.setFileRevision(fileRev);
				} catch (ReviewsFileStorageException e) {
					R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				}
			}
		}

		//Restore target file version
		final R4EFileVersion targetFileVersion = fFile.getTarget();
		if (null != targetFileVersion) {
			try {
				final IFile targetFile = ResourceUtils.toIFile(targetFileVersion.getPlatformURI());
				targetFileVersion.setResource(targetFile);
			} catch (FileNotFoundException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				targetFileVersion.setResource(null);
			}

			targetFileVersion.setFileRevision(null);
			if (null != revRegistry) {
				try {
					final IFileRevision fileRev = revRegistry.getIFileRevision(null, targetFileVersion);
					targetFileVersion.setFileRevision(fileRev);
				} catch (ReviewsFileStorageException e) {
					R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				}
			}
		}

		//Load child data
		if (fFile.getDeltas().size() > 0) {
			try {
				fContentsContainer.open();
			} catch (FileNotFoundException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);
			} catch (ReviewVersionsException e) {
				UIUtils.displayVersionErrorDialog(e);
			}
		}

		if (null != fFile.getTarget()) {
			fAnomalies = R4EUIModelController.getAnomaliesForFile(fFile.getTarget().getLocalVersionID());
			if (null != fAnomalies && fAnomalies.size() > 0) {
				fAnomalyContainer.open();
			}
		}
		fOpen = true;
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
		if (aChildToAdd instanceof R4EUISelectionContainer) {
			fContentsContainer = (R4EUISelectionContainer) aChildToAdd;
		} else if (aChildToAdd instanceof R4EUIDeltaContainer) {
			fContentsContainer = (R4EUIDeltaContainer) aChildToAdd;
		} else if (aChildToAdd instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer = (R4EUIAnomalyContainer) aChildToAdd;
		} else {
			return;
		}
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getContentProvider());
		fireAdd(aChildToAdd);
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
		if (aChildToRemove instanceof R4EUIContentsContainer) {
			fContentsContainer.removeAllChildren(aFileRemove);
			if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
				aChildToRemove.removeListeners();
				fireRemove(aChildToRemove);
			} else {
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		} else if (aChildToRemove instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer.removeAllChildren(aFileRemove);
			if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
				aChildToRemove.removeListeners();
				fireRemove(aChildToRemove);
			} else {
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
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
		removeChildren(fContentsContainer, aFileRemove);
		removeChildren(fAnomalyContainer, aFileRemove);
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
		fContentsContainer.addListener(aProvider);
		fAnomalyContainer.addListener(aProvider);
	}

	/**
	 * Method removeListener.
	 * 
	 * @param aProvider
	 *            - the treeviewer content provider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener(ReviewNavigatorContentProvider aProvider) {
		super.removeListener(aProvider);
		fContentsContainer.removeListener(aProvider);
		fAnomalyContainer.removeListener(aProvider);
	}

	//Commands

	/**
	 * Return true if the associated target file version can be compared with the associated base version
	 * 
	 * @return true/false
	 */
	public boolean isFileVersionsComparable() {
		//Do we have at lease a file present?
		if (null != fFile.getBase() || null != fFile.getTarget()) {

			//Are the base and target file the same?
			if (null != fFile.getBase() && null != fFile.getTarget()) {
				if (fFile.getBase().getLocalVersionID().equals(fFile.getTarget().getLocalVersionID())) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Method isOpenEditorCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	@Override
	public boolean isOpenEditorCmd() {
		if (isEnabled() && (null != getTargetFileVersion() || null != getBaseFileVersion())) {
			return true;
		}
		return false;
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
				&& !(((R4EReviewState) R4EUIModelController.getActiveReview().getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED))
				&& fFile.getAssignedTo().size() > 0) {
			return true;
		}
		//If at least on children has participants assigned, enable the command
		for (IR4EUIModelElement content : fContentsContainer.getChildren()) {
			if (content.isUnassignToCmd()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method isSendEmailCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isSendEmailCmd()
	 */
	@Override
	public boolean isSendEmailCmd() {
		return true;
	}
}
