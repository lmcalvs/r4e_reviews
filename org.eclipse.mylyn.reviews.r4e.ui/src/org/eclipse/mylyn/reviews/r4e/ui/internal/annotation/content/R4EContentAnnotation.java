/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements an annotation that wraps an R4E Content
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EContentAnnotation extends R4EAnnotation {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EContentAnnotation.
	 * 
	 * @param aContentElement
	 *            R4EUIContent
	 * @param aType
	 *            String
	 */
	public R4EContentAnnotation(R4EUIContent aContentElement, String aType) {
		super(aContentElement, aType, aContentElement.getName());
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getR4EPosition.
	 * 
	 * @return IR4EUIPosition
	 */
	@Override
	public IR4EUIPosition getR4EPosition() {
		return ((R4EUIContent) fSourceElement).getPosition();
	}

	/**
	 * Method getId.
	 * 
	 * @return R4EID
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getId()
	 */
	@Override
	public R4EID getId() {
		return ((R4EUIContent) fSourceElement).getContent().getR4eId();
	}

	/**
	 * Method getParent.
	 * 
	 * @return Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getParent()
	 */
	public Object getParent() {
		return getParentInput();
	}

	/**
	 * Method getChildren.
	 * 
	 * @return Object[]
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getChildren()
	 */
	public Object[] getChildren() {
		R4EAnnotationText[] values = null;
		final List<String> assignees = ((R4EUIContent) fSourceElement).getContent().getAssignedTo();
		if ((null != assignees) && (assignees.size() > 0)) {
			values = new R4EAnnotationText[1];
			final List<String> assignedParticipants = ((R4EUIContent) fSourceElement).getContent().getAssignedTo();
			values[0] = new R4EAnnotationText(this, R4EUIConstants.ASSIGNED_TO_LABEL
					+ UIUtils.formatAssignedParticipants(assignedParticipants), null);
		}
		return values;
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#hasChildren()
	 */
	public boolean hasChildren() {
		final List<String> assignees = ((R4EUIContent) fSourceElement).getContent().getAssignedTo();
		if ((null != assignees) && (assignees.size() > 0)) {
			return true;
		}
		return false;
	}
}
