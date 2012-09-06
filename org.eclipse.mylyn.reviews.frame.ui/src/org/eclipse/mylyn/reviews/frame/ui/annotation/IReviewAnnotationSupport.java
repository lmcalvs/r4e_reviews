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
 * This interface defines generic accessor methods to the inline commenting 
 * functionality
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation;

import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IReviewAnnotationSupport {

	/**
	 * Gets the current Text Editor opened.
	 * 
	 * @return ITextEditor - the Text Editor instance
	 */
	ITextEditor getTargetEditor();

	/**
	 * Gets the Annotation Model connected to the current file.
	 * 
	 * @return IReviewAnnotationModel - the Annotation Model
	 */
	IReviewAnnotationModel getTargetAnnotationModel();

	/**
	 * Sets the current object instance the Annotation Model is connected to.
	 * 
	 * @param aElement - the current object instance the Annotation Model is connected to
	 */
	void setAnnotationModelElement(Object aElement);

	/**
	 * Add new Annotation to the Annotation Model.
	 * 
	 * @param aElement - the element source for the new annotation 
	 */
	void addAnnotation(Object aElement);

	/**
	 * Update Annotation data in the Annotation Model.
	 * 
	 * @param aElement - the element source for the annotation to update
	 */
	void updateAnnotation(Object aElement);

	/**
	 * Remove an Annotation from the Annotation Model.
	 * 
	 * @param aElement - the element source for the annotation to remove
	 *
	 */
	void removeAnnotation(Object aElement);

}
