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
 * This class implements an annotation that wraps an R4E Delta
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIDelta;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EDeltaAnnotation extends R4EContentAnnotation {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EDeltaAnnotation.
	 * 
	 * @param aSourceDelta
	 *            R4EUIDelta
	 */
	public R4EDeltaAnnotation(R4EUIDelta aSourceDelta) {
		super(aSourceDelta, getAnnotationType(aSourceDelta));
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getAnnotationType.
	 * 
	 * @param aSourceDelta
	 *            R4EUIDelta
	 * @return String
	 */
	private static String getAnnotationType(R4EUIDelta aSourceDelta) {
		if (aSourceDelta.isEnabled()) {
			if (aSourceDelta.isUserReviewed()) {
				return R4EUIConstants.DELTA_REVIEWED_ANNOTATION_ID;
			} else {
				return R4EUIConstants.DELTA_ANNOTATION_ID;
			}
		}
		return R4EUIConstants.DELTA_DISABLED_ANNOTATION_ID;
	}
}
