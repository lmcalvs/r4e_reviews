/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Review element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

public class R4EUIReview extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_UNKNOWN_ICON_FILE. (value is ""icons/obj16/review_obj.gif"")
	 */
	public static final String REVIEW_UNKNOWN_ICON_FILE = "icons/obj16/review_obj.gif";

	/**
	 * Field OPEN_ELEMENT_COMMAND_NAME. (value is ""Open Review"")
	 */
	private static final String OPEN_ELEMENT_COMMAND_NAME = "Open Review";

	/**
	 * Field OPEN_ELEMENT_COMMAND_TOOLTIP. (value is ""Open and Load Data for this Review"")
	 */
	private static final String OPEN_ELEMENT_COMMAND_TOOLTIP = "Open and Load Data for this Review";

	/**
	 * Field UNKNOWN_REVIEW_TYPE_TOOLTIP. (value is ""Review Version Data is not Compatible with the current R4E
	 * Version. Open Review to Upgrade."")
	 */
	private static final String UNKNOWN_REVIEW_TYPE_TOOLTIP = "Review Version Data is not Compatible with the current R4E Version."
			+ R4EUIConstants.LINE_FEED + "Open Review to Upgrade.";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fReview.
	 */
	protected R4EReview fReview;

	/**
	 * Field fReviewName.
	 */
	protected final String fReviewName;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIReviewUnknown.
	 * 
	 * @param aParent
	 *            R4EUIReviewGroup
	 * @param aReview
	 *            R4EReview
	 */
	public R4EUIReview(R4EUIReviewGroup aParent, R4EReview aReview, boolean aResolved) {
		super(aParent, R4EUIConstants.REVIEW_TYPE_UNKNOWN + ": " + aReview.getName());
		fResolved = aResolved;
		fReview = aReview;
		fReviewName = aReview.getName();
		fOpen = false;
	}

	/**
	 * Constructor for R4EUIReviewUnknown.
	 * 
	 * @param aParent
	 *            R4EUIReviewGroup
	 * @param aName
	 *            String
	 */
	protected R4EUIReview(R4EUIReviewGroup aParent, R4EReview aReview, String aName) {
		super(aParent, aName);
		fReview = aReview;
		fReviewName = aReview.getName();
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getReview.
	 * 
	 * @return R4EReview
	 */
	public R4EReview getReview() {
		return fReview;
	}

	/**
	 * Method getReviewName.
	 * 
	 * @return String
	 */
	public String getReviewName() {
		return fReviewName;
	}

	/**
	 * Method getImageLocation.
	 * 
	 * @return String
	 */
	public String getImageLocation() {
		return REVIEW_UNKNOWN_ICON_FILE;
	}

	//Hierarchy

	/**
	 * Method hasChildren
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return false;
	}

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return new IR4EUIModelElement[0];
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fReview.isEnabled();
	}

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return UNKNOWN_REVIEW_TYPE_TOOLTIP;
	}

	//Commands

	/**
	 * Method isOpenElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	@Override
	public boolean isOpenElementCmd() {
		if (!isEnabled() || isOpen()) {
			return false;
		}
		return true;
	}

	/**
	 * Method getOpenElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getOpenElementCmdName()
	 */
	@Override
	public String getOpenElementCmdName() {
		return OPEN_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getOpenElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getOpenElementCmdTooltip()
	 */
	@Override
	public String getOpenElementCmdTooltip() {
		return OPEN_ELEMENT_COMMAND_TOOLTIP;
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
	 * Method isPushReviewCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isPushReviewCmd()
	 */
	@Override
	public boolean isPushReviewCmd() {
		return isOpen();
	}

	/**
	 * Method isFetchReviewCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isFetchReviewCmd()
	 */
	@Override
	public boolean isFetchReviewCmd() {
		return isOpen();
	}

}
