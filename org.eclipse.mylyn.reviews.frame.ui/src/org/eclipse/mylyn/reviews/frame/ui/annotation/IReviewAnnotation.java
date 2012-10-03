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
 * This interface defines a generic annotation for a review.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation;

import org.eclipse.jface.text.Position;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IReviewAnnotation {

	/**
	 * Method getPosition.  Get the position for the Annotation
	 * 
	 * @return Position - the annotation Position
	 */
	Position getPosition();

	/**
	 * Method getText.  Get the Annotation text
	 * 
	 * @return String - the annotation text
	 */
	String getText();

	/**
	 * Method getId.  Get the Annotation ID
	 * 
	 * @return Object - the Annotation ID
	 */
	Object getId();

	/**
	 * Method getParent.  Get the annotation parent (if any)
	 * 
	 * @return Object - the Annotation parent
	 */
	Object getParent();

	/**
	 * Method getChildren.  Get The Annotation children (if any)
	 * 
	 * @return Object[] - the Anotation children
	 */
	Object[] getChildren();

	/**
	 * Method hasChildren.  Returns whether this Annotation has children
	 * 
	 * @return boolean - true if the Annotation has children, false otherwise
	 */
	boolean hasChildren();

	/**
	 * Method setParentInput.  Set the parent input from the Annotation Hover input information
	 * 
	 * @param aAnnotationHoverInput -the Annotation Hover input information
	 */
	void setParentInput(IAnnotationHoverInput aAnnotationHoverInput);

	/**
	 * Method getParentInput.  Get the parent input from the Annotation Hover input information
	 * 
	 * @return IAnnotationHoverInput - the Annotation Hover input information
	 */
	IAnnotationHoverInput getParentInput();
}
