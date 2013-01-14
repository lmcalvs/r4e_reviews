// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the Contetns Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class R4EUIContentsContainer extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fContents.
	 */
	protected List<R4EUIContent> fContents;

	/**
	 * Field CONTENT_COMPARATOR.
	 */
	private static final Comparator<R4EUIContent> CONTENT_COMPARATOR = new Comparator<R4EUIContent>() {
		// This is where the sorting happens.
		public int compare(R4EUIContent aContent1, R4EUIContent aContent2) {
			IR4EUIPosition uiposition = aContent1.getPosition();
			int sortOrder = 0;
			if (uiposition instanceof R4EUIModelPosition) {
//				System.out.println("JBJB  UI model compare");
//				sortOrder = ((R4EUIModelPosition) aContent1.getPosition()).getOffset()
//						- ((R4EUIModelPosition) aContent2.getPosition()).getOffset();
//				if (sortOrder == 0) {
//					return ((R4EUITextPosition) aContent1.getPosition()).getLength()
//							- ((R4EUITextPosition) aContent2.getPosition()).getLength();
//				}
			} else {
//				System.out.println("Not a UI model compare");
				sortOrder = ((R4EUITextPosition) aContent1.getPosition()).getOffset()
						- ((R4EUITextPosition) aContent2.getPosition()).getOffset();
				if (sortOrder == 0) {
					return ((R4EUITextPosition) aContent1.getPosition()).getLength()
							- ((R4EUITextPosition) aContent2.getPosition()).getLength();
				}
			}
			return sortOrder;
		}
	};

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIContentsContainer.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aName
	 *            String
	 */
	protected R4EUIContentsContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName);
		fReadOnly = aParent.isReadOnly();
		fContents = new ArrayList<R4EUIContent>();
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
	public IR4EUIModelElement[] getChildren() {
		return fContents.toArray(new R4EUIContent[fContents.size()]);
	}

	/**
	 * Method getContentsList.
	 * 
	 * @return List<R4EUIContent>
	 */
	public List<R4EUIContent> getContentsList() {
		return fContents;
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fContents.size() > 0) {
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
		R4EUIContent content = null;
		final int contentSize = fContents.size();
		for (int i = 0; i < contentSize; i++) {
			content = fContents.get(i);
			content.close();
		}
		fContents.clear();
		fOpen = false;
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
			for (R4EUIContent content : fContents) {
				if (content.isEnabled()) {
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
		fContents.add((R4EUIContent) aChildToAdd);
		Collections.sort(fContents, CONTENT_COMPARATOR);
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
		final R4EUIContent removedElement = fContents.get(fContents.indexOf(aChildToRemove));
		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getContent().remove());
		else */
		final R4EDelta modelContent = removedElement.getContent();
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelContent,
				R4EUIModelController.getReviewer());
		modelContent.setEnabled(false);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		//Remove element from UI if the show disabled element option is off
		if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fContents.remove(removedElement);

			//Remove inline markings (local anomalies only)
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					UIUtils.removeAnnotation(removedElement, (R4EUIFileContext) getParent());
				}
			});
		} else {
			//Update inline markings (local anomalies only)
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					UIUtils.updateAnnotation(removedElement, (R4EUIFileContext) getParent());
				}
			});
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
		for (R4EUIContent content : fContents) {
			removeChildren(content, aFileRemove);
		}
	}

	/**
	 * Method verifyUserReviewed.
	 */
	public void verifyUserReviewed() {
		try {
			final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent().getParent();
			final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

			if (null != user) {
				//Check if the file contexts are part of the reviewed content
				for (R4EUIContent uiContent : fContents) {
					if (user.getReviewedContent().contains(uiContent.getContent().getR4eId())) {
						uiContent.setUserReviewed(true, true, false);
					}
				}
			}
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);

		} catch (OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);
		}
	}

	//Listeners

/*	*//**
	 * Method addListener.
	 * 
	 * @param aProvider
	 *            ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	/*
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
	super.addListener(aProvider);
	if (null != fContents) {
		R4EUIContent element = null;
		for (final Iterator<R4EUIContent> iterator = fContents.iterator(); iterator.hasNext();) {
			element = iterator.next();
			element.addListener(aProvider);
		}
	}
	}

	*//**
	 * Method removeListener.
	 * 
	 * @param aProvider
	 *            ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeListener()
	 */
	/*
	@Override
	public void removeListener(ReviewNavigatorContentProvider aProvider) {
	super.removeListener(aProvider);
	if (null != fContents) {
		R4EUIContent element = null;
		for (final Iterator<R4EUIContent> iterator = fContents.iterator(); iterator.hasNext();) {
			element = iterator.next();
			element.removeListener(aProvider);
		}
	}
	}*/

	//Commands

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
}
