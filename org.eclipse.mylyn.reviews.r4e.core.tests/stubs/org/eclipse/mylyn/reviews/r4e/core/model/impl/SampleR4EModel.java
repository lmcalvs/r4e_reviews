/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.model.impl;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;

public class SampleR4EModel {
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	// private final String fUserName;
	private static ResourceUpdater fSetUpdater = SerializeFactory.getResourceSetUpdater();

	// private final R4EWriter fWriter = SerializeFactory.getWriter();

	// private final static String _ROOT = "stubOut/";

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	public static R4EReviewGroup createAndSerialize(String root, String GroupName) {
		R4EReviewGroup fGroup = null;

		fGroup = GroupSampl.createGroup(root, GroupName);

		// Check out resource set
		Long bookingNum = 0L;
		try {
			bookingNum = fSetUpdater.checkOut(fGroup, "Alvaro Sanchez-Leon");
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		} catch (OutOfSyncException e) {
			e.printStackTrace();
		}

		// two reviews different names, same content in same group
		ReviewSampl.createReview("ReviewSampl", fGroup);
		ReviewSampl.createReview("ReviewTwo", fGroup);

		// Serialize changes ResourceSet level
		try {
			fSetUpdater.checkIn(bookingNum);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

		return fGroup;
	}

}
