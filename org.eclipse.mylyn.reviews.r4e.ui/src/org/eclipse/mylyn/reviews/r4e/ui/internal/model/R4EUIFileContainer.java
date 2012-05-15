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
 * This class implements the File Container element of the UI model.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public abstract class R4EUIFileContainer extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fType.
	 */
	protected final int fType;

	/**
	 * Field fItem.
	 */
	protected final R4EItem fItem;

	/**
	 * Field fFileContexts.
	 */
	protected final List<R4EUIFileContext> fFileContexts;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIReviewItem.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aItem
	 *            R4EItem
	 * @param aName
	 *            String
	 * @param aItemType
	 *            - int
	 */
	protected R4EUIFileContainer(IR4EUIModelElement aParent, R4EItem aItem, String aName, int aItemType) {
		super(aParent, aName);
		fReadOnly = aParent.isReadOnly();
		fItem = aItem;
		fType = aItemType;
		fFileContexts = new ArrayList<R4EUIFileContext>();
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
		return fItem.getDescription();
	}

	/**
	 * Method getItem.
	 * 
	 * @return R4EItem
	 */
	public R4EItem getItem() {
		return fItem;

	}

	/**
	 * Method getFileContexts.
	 * 
	 * @return List<R4EUIFileContext>
	 */
	public List<R4EUIFileContext> getFileContexts() {
		return fFileContexts;

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
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fItem, R4EUIModelController.getReviewer());
		fItem.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		for (R4EUIFileContext file : fFileContexts) {
			file.setEnabled(true);
		}
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fItem.isEnabled();
	}

	//Hierarchy

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
		fOpen = false;
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
			R4EUIFileContext uiFileContext = null;
			final int filesSize = files.size();
			R4EFileContext file = null;
			for (int i = 0; i < filesSize; i++) {
				file = files.get(i);
				if (file.isEnabled()
						|| R4EUIPlugin.getDefault()
								.getPreferenceStore()
								.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					uiFileContext = new R4EUIFileContext(this, files.get(i), fType);
					addChildren(uiFileContext);
					if (uiFileContext.isEnabled()) {
						uiFileContext.open();
					}
				}
			}
		}
		fOpen = true;
	}

	/**
	 * Method verifyUserReviewed.
	 */
	public void verifyUserReviewed() {
		try {
			final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent();
			final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

			if (null != user) {

				//Check if the file contexts are part of the reviewed content
				for (R4EUIFileContext uiFile : fFileContexts) {
					uiFile.verifyUserReviewed();
					if (user.getReviewedContent().contains(uiFile.getFileContext().getId())) {
						uiFile.setUserReviewed(true, true, false);
					}
				}
			}
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);

		} catch (OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);
		}
	}

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fFileContexts.toArray(new R4EUIFileContext[fFileContexts.size()]);
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fFileContexts.size() > 0) {
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
		fFileContexts.add((R4EUIFileContext) aChildToAdd);
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
		for (R4EUIFileContext file : fFileContexts) {
			removeChildren(file, aFileRemove);
		}
	}
}
