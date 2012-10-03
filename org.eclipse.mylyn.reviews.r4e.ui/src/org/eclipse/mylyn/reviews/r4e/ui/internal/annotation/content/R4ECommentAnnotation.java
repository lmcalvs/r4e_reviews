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
 * This class implements an annotation that wraps an R4E Comment
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ECommentAnnotation extends R4EAnnotation {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fParent.
	 */
	private final R4EAnomalyAnnotation fParent;

	/**
	 * Field fCommentLines.
	 */
	private final List<String> fCommentLines = new ArrayList<String>();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ECommentAnnotation.
	 * 
	 * @param aSourceComment
	 *            R4EUIComment
	 * @param aParentAnomaly
	 *            R4EAnomalyAnnotation
	 */
	public R4ECommentAnnotation(R4EUIComment aSourceComment, R4EAnomalyAnnotation aParentAnomaly) {
		super(aSourceComment, getAnnotationType(aSourceComment), aSourceComment.getComment().getAuthor().getId());
		fParent = aParentAnomaly;
		final StringTokenizer st = new StringTokenizer(aSourceComment.getComment().getDescription(),
				R4EUIConstants.LINE_FEED);
		while (st.hasMoreElements()) {
			fCommentLines.add((String) st.nextElement());
		}
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
		return null;
	}

	/**
	 * Method getId.
	 * 
	 * @return R4EID
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getId()
	 */
	@Override
	public R4EID getId() {
		return ((R4EUIComment) fSourceElement).getComment().getR4eId();
	}

	/**
	 * Method getAnnotationType.
	 * 
	 * @param aSourceComment
	 *            R4EUIComment
	 * @return String
	 */
	private static String getAnnotationType(R4EUIComment aSourceComment) {
		if (aSourceComment.isEnabled()) {
			return R4EUIConstants.COMMENT_ANNOTATION_ID;
		}
		return R4EUIConstants.COMMENT_DISABLED_ANNOTATION_ID;
	}

	/**
	 * Method getParent.
	 * 
	 * @return Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getParent()
	 */
	public Object getParent() {
		return fParent;
	}

	/**
	 * Method getChildren.
	 * 
	 * @return Object[]
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getChildren()
	 */
	public Object[] getChildren() {
		final List<R4EAnnotationText> values = new ArrayList<R4EAnnotationText>();
		for (String contentLine : fCommentLines) {
			values.add(new R4EAnnotationText(this, contentLine, null));
		}
		return values.toArray(new Object[values.size()]);
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#hasChildren()
	 */
	public boolean hasChildren() {
		return true;
	}
}
