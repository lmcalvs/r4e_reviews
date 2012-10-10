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
 * This interface defines the annotation model used to Review Annotations.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation;

import org.eclipse.jface.text.source.AnnotationModelEvent;
import org.eclipse.jface.text.source.IAnnotationModel;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IReviewAnnotationModel extends IAnnotationModel {

	/**
	 * Method fireModelChanged. Notify UI that the Annotation model has changed
	 * 
	 * @param aEvent
	 *            - the Annotation Model Event
	 */
	void fireModelChanged(AnnotationModelEvent aEvent);

	/**
	 * Method addAnnotation. Add annotation to the model
	 * 
	 * @param aAnnotationContent
	 *            - The annotation contents
	 */
	void addAnnotation(Object aAnnotationContent);

	/**
	 * Method removeAnnotation. Remove annotation from the model
	 * 
	 * @param aAnnotationContent
	 *            - The annotation contents
	 */
	void removeAnnotation(Object aAnnotationContent);

	/**
	 * Method updateAnnotation. Update annotation in the model
	 * 
	 * @param aAnnotationContent
	 *            - The annotation contents
	 */
	void updateAnnotation(Object aAnnotationContent);

	/**
	 * Method clearAnnotations. remove all annotations in the model
	 */
	void clearAnnotations();

	/**
	 * Method refreshAnnotations. rebuilds the annotations model
	 */
	void refreshAnnotations();

	/**
	 * Method isAnnotationsAvailable. checks if there are annotations of the given type in model
	 * 
	 * @param aType
	 *            String
	 * @return boolean
	 */
	boolean isAnnotationsAvailable(String aType);

	/**
	 * Method findAnnotation. Finds the annotation for the source element of the given type if it exists
	 * 
	 * @param aType
	 *            String
	 * @param aSourceElement
	 *            Object
	 * @return IReviewAnnotation
	 */
	IReviewAnnotation findAnnotation(String aType, Object aSourceElement);

	/**
	 * Method getNextAnnotation. Get next annotation in the model
	 * 
	 * @param aType
	 *            - The annotation type to filter in, if applicable
	 * @return - The next Annotation
	 */
	IReviewAnnotation getNextAnnotation(String aType);

	/**
	 * Method getPreviousAnnotation. Get previous annotation in the model
	 * 
	 * @param aType
	 *            - The annotation type to filter in, if applicable
	 * @return - The previous Annotation
	 */
	IReviewAnnotation getPreviousAnnotation(String aType);

	/**
	 * Method setFile. Sets the file the current annotation model refers to
	 * 
	 * @param aFile
	 *            - The file the annotation model refers to
	 */
	void setFile(Object aFile);
}
