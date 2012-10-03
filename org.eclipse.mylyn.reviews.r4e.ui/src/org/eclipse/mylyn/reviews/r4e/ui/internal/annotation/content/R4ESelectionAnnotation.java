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
 * This class implements an annotation that wraps an R4E Selection
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4ESelectionAnnotation extends R4EContentAnnotation {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ESelectionAnnotation.
	 * 
	 * @param aSourceSelection
	 *            R4EUISelection
	 */
	public R4ESelectionAnnotation(R4EUISelection aSourceSelection) {
		super(aSourceSelection, getAnnotationType(aSourceSelection));
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getAnnotationType.
	 * 
	 * @param aSourceSelection
	 *            R4EUISelection
	 * @return String
	 */
	private static String getAnnotationType(R4EUISelection aSourceSelection) {
		if (aSourceSelection.isEnabled()) {
			if (aSourceSelection.isUserReviewed()) {
				return R4EUIConstants.SELECTION_REVIEWED_ANNOTATION_ID;
			} else {
				return R4EUIConstants.SELECTION_ANNOTATION_ID;
			}
		}
		return R4EUIConstants.SELECTION_DISABLED_ANNOTATION_ID;
	}
}
