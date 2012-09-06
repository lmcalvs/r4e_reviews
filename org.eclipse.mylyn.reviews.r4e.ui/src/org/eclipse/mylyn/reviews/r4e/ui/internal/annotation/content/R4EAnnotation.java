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
 * This class implements a generic annotation for R4E.  It must be subclassed.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IAnnotationHoverInput;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class R4EAnnotation extends Annotation implements IReviewAnnotation {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fSourceElement.
	 */
	protected final IR4EUIModelElement fSourceElement;

	/**
	 * Field fParentInput.
	 */
	private IAnnotationHoverInput fParentInput = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EAnnotation.
	 * 
	 * @param aSourceElement
	 *            IR4EUIModelElement
	 * @param aType
	 *            String
	 */
	public R4EAnnotation(IR4EUIModelElement aSourceElement, String aType, String aName) {
		super(aType, false, aName);
		fSourceElement = aSourceElement;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getId.
	 * 
	 * @return Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getId()
	 */
	public abstract Object getId();

	/**
	 * Method getR4EPosition.
	 * 
	 * @return IR4EUIPosition
	 */
	protected abstract IR4EUIPosition getR4EPosition();

	/**
	 * Method getPosition.
	 * 
	 * @return Position
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getPosition()
	 */
	public Position getPosition() {
		final IR4EUIPosition position = getR4EPosition();
		if (position instanceof R4EUITextPosition) {
			return new Position(((R4EUITextPosition) position).getOffset(), ((R4EUITextPosition) position).getLength());
		}
		return null;
	}

	/**
	 * Method getSourceElement.
	 * 
	 * @return IR4EUIModelElement
	 */
	public IR4EUIModelElement getSourceElement() {
		return fSourceElement;
	}

	/**
	 * Method setParentInput.
	 * 
	 * @param aAnnotationHoverInput
	 *            IAnnotationHoverInput
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#setParentInput(IAnnotationHoverInput)
	 */
	public void setParentInput(IAnnotationHoverInput aAnnotationHoverInput) {
		fParentInput = aAnnotationHoverInput;
	}

	/**
	 * Method getParentInput.
	 * 
	 * @return IAnnotationHoverInput
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getParentInput()
	 */
	public IAnnotationHoverInput getParentInput() {
		return fParentInput;
	}

}
