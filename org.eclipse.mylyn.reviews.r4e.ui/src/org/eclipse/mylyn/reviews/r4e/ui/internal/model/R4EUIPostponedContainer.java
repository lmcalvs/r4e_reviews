// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Container element of the UI model that is used to
 * provide access to postponed elements
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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.team.core.history.IFileRevision;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIPostponedContainer extends R4EUIFileContainer {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field POSTPONED_CONTAINER_ICON_FILE. (value is ""icons/obj16/postcont_obj.gif"")
	 */
	public static final String POSTPONED_CONTAINER_ICON_FILE = "icons/obj16/postcont_obj.gif";

	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME. (value is ""Delete Review Item"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Postponed Elements";

	/**
	 * Field REMOVE_ELEMENT_ACTION_TOOLTIP. (value is ""Remove this review item from its parent review"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable (and Optionally Remove) the Postponed "
			+ "Elements from their Parent Review";

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fAnomalyContainer.
	 */
	protected R4EUIPostponedAnomalyContainer fAnomalyContainer = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIPostponedContainer.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aItem
	 *            R4EItem
	 * @param aName
	 *            String
	 */
	public R4EUIPostponedContainer(IR4EUIModelElement aParent, R4EItem aItem, String aName) {
		super(aParent, aItem, aName, R4EUIConstants.REVIEW_ITEM_TYPE_POSTPONED);
		fAnomalyContainer = new R4EUIPostponedAnomalyContainer(this, R4EUIConstants.GLOBAL_POSTPONED_ANOMALIES_LABEL);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getImageLocation.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getImageLocation()
	 */
	public String getImageLocation() {
		return POSTPONED_CONTAINER_ICON_FILE;
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
		return null;
	}

	//Hierarchy

	/**
	 * Method getAnomalyContainer.
	 * 
	 * @return R4EUIPostponedAnomalyContainer
	 */
	public R4EUIPostponedAnomalyContainer getAnomalyContainer() {
		return fAnomalyContainer;
	}

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		final List<IR4EUIModelElement> newList = new ArrayList<IR4EUIModelElement>();
		for (R4EUIFileContext file : fFileContexts) {
			if (file.getChildren().length > 0) {
				newList.add(file);
			}
		}
		if (fAnomalyContainer.getChildren().length > 0) {
			newList.add(fAnomalyContainer);
		}
		return newList.toArray(new IR4EUIModelElement[newList.size()]);
	}

	/**
	 * Method open.
	 * 
	 * @throws ResourceHandlingException
	 * @throws FileNotFoundException
	 */
	@Override
	public void open() {
		final EList<R4EFileContext> files = fItem.getFileContextList();
		if (null != files) {
			R4EUIPostponedFile uiFile = null;
			final int filesSize = files.size();
			for (int i = 0; i < filesSize; i++) {
				uiFile = new R4EUIPostponedFile(this, files.get(i));
				addChildren(uiFile);
				uiFile.open();
			}
		}
		if (R4EUIPlugin.getDefault()
				.getPreferenceStore()
				.getBoolean(PreferenceConstants.P_IMPORT_GLOBAL_ANOMALIES_POSTPONED)) {
			fAnomalyContainer.setReadOnly(fReadOnly);
			fAnomalyContainer.open();
		}
		fOpen = true;
	}

	/**
	 * Close the model element
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIFileContext fileContext = null;
		final int fileContextsSize = fFileContexts.size();
		for (int i = 0; i < fileContextsSize; i++) {

			fileContext = fFileContexts.get(i);
			fileContext.close();
		}
		fFileContexts.clear();
		fAnomalyContainer.close();
		fOpen = false;
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fFileContexts.size() > 0 || fAnomalyContainer.getChildren().length > 0) {
			return true;
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
		if (aChildToAdd instanceof R4EUIFileContext) {
			fFileContexts.add((R4EUIFileContext) aChildToAdd);
		} else if (aChildToAdd instanceof R4EUIPostponedAnomalyContainer) {
			fAnomalyContainer = (R4EUIPostponedAnomalyContainer) aChildToAdd;
		}
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
		if (aChildToRemove instanceof R4EUIPostponedFile) {
			final R4EUIFileContext removedElement = fFileContexts.get(fFileContexts.indexOf(aChildToRemove));

			//Also recursively remove all children 
			removedElement.removeAllChildren(aFileRemove);

			/* TODO uncomment when core model supports hard-removing of elements
			if (aFileRemove) removedElement.getFileContext().remove());
			else */
			final R4EFileContext modelFile = removedElement.getFileContext();
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelFile,
					R4EUIModelController.getReviewer());
			modelFile.setEnabled(false);
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);

			//Remove element from UI if the show disabled element option is off
			if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
				fFileContexts.remove(removedElement);
			}
		} else if (aChildToRemove instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer.removeAllChildren(aFileRemove);
		}
	}

	/**
	 * Method createFileContext
	 * 
	 * @param aTargetTempFileVersion
	 *            R4EFileVersion
	 * @return R4EUIPostponedFile
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIPostponedFile createFileContext(R4EFileVersion aTargetTempFileVersion)
			throws ResourceHandlingException, OutOfSyncException {

		IRFSRegistry revRegistry = null;
		try {
			revRegistry = RFSRegistryFactory.getRegistry(((R4EUIReviewBasic) this.getParent()).getReview());
		} catch (ReviewsFileStorageException e1) {
			R4EUIPlugin.Ftracer.traceInfo("Exception: " + e1.toString() + " (" + e1.getMessage() + ")");
		}

		final R4EFileContext fileContext = R4EUIModelController.FModelExt.createR4EFileContext(fItem);

		//Get Target version from Version control system and set core model data
		if (null != aTargetTempFileVersion) {
			final R4EFileVersion rfileTargetVersion = R4EUIModelController.FModelExt.createR4ETargetFileVersion(fileContext);
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(rfileTargetVersion,
					R4EUIModelController.getReviewer());
			CommandUtils.copyFileVersionData(rfileTargetVersion, aTargetTempFileVersion);
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);

			//Add target file version
			try {
				final IFile targetFile = ResourceUtils.toIFile(rfileTargetVersion.getPlatformURI());
				rfileTargetVersion.setResource(targetFile);
			} catch (FileNotFoundException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				rfileTargetVersion.setResource(null);
			}

			//Add IFileRevision info
			if (null != revRegistry) {
				try {
					final IFileRevision fileRev = revRegistry.getIFileRevision(null, rfileTargetVersion);
					rfileTargetVersion.setFileRevision(fileRev);
				} catch (ReviewsFileStorageException e) {
					R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				}
			}
		}

		final R4EUIPostponedFile uiFile = new R4EUIPostponedFile(this, fileContext);
		addChildren(uiFile);
		return uiFile;
	}

	//Commands

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
	 * Method isChangeReviewStateCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isChangeUserReviewStateCmd()
	 */
	@Override
	public boolean isChangeUserReviewStateCmd() {
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
		return false;
	}
}
