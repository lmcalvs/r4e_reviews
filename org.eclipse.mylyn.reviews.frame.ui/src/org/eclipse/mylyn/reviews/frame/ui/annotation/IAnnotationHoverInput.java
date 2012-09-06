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
 * This interface defines a generic input provider for Annotation hovers
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation;

import java.util.List;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IAnnotationHoverInput {

	/**
	 * Checks whether the annotation hover has any annotation information.
	 * 
	 * @return boolean - true if empty, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Gets the annotation the mouse is hovering over.
	 * 
	 * @return List<IReviewAnnotation> a list of the annotations being hovered over
	 */
	List<IReviewAnnotation> getAnnotations();
}
