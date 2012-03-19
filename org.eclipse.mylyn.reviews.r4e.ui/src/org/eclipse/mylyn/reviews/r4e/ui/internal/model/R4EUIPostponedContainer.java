// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
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
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.team.core.history.IFileRevision;

/**
 * @author lmcdubo
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
		setImage(POSTPONED_CONTAINER_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

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
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		final List<IR4EUIModelElement> newList = new ArrayList<IR4EUIModelElement>();
		for (IR4EUIModelElement file : fFileContexts) {
			if (file.getChildren().length > 0) {
				newList.add(file);
			}
		}
		return newList.toArray(new IR4EUIModelElement[newList.size()]);
	}

	/**
	 * Method open.
	 * 
	 * @throws ReviewVersionsException
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
		fOpen = true;
	}

	/**
	 * Method createReviewItem
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
