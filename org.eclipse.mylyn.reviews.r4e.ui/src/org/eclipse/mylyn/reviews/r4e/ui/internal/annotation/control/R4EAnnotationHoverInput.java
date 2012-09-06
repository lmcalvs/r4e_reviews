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
 * This class implements the input provider for the R4E Annotation hover
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import java.util.List;

import org.eclipse.mylyn.reviews.frame.ui.annotation.IAnnotationHoverInput;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationHoverInput implements IAnnotationHoverInput {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fAnnotations.
	 */
	private final List<IReviewAnnotation> fAnnotations;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EAnnotationHoverInput.
	 * 
	 * @param aAnnotations
	 *            List<IReviewAnnotation>
	 */
	public R4EAnnotationHoverInput(List<IReviewAnnotation> aAnnotations) {
		fAnnotations = aAnnotations;
		for (IReviewAnnotation annotation : aAnnotations) {
			annotation.setParentInput(this);
		}
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method isEmpty.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IAnnotationHoverInput#isEmpty()
	 */
	public boolean isEmpty() {
		return (fAnnotations != null) && (fAnnotations.size() > 0);
	}

	/**
	 * Method getAnnotations.
	 * 
	 * @return List<IReviewAnnotation>
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IAnnotationHoverInput#getAnnotations()
	 */
	public List<IReviewAnnotation> getAnnotations() {
		return fAnnotations;
	}
}
