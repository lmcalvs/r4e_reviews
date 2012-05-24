/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.rfs.spi;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.rfs.ReviewsRFSProxy;

/**
 * @author Alvaro Sanchez-Leon
 *
 */
public class RFSRegistryFactory {
	private static final Map<R4EReviewGroup, ReviewsRFSProxy>	fOpenStore	= new HashMap<R4EReviewGroup, ReviewsRFSProxy>();

	public static IRFSRegistry getRegistry(R4EReview aReview) throws ReviewsFileStorageException {

		R4EReviewGroup group = (R4EReviewGroup) aReview.eContainer();
		// If still using the same group, return cached instance
		ReviewsRFSProxy cachedGroupStorage = fOpenStore.get(group);
		if (cachedGroupStorage != null) {
			return cachedGroupStorage;
		}

		// close previous review group storage
		Set<R4EReviewGroup> groups = fOpenStore.keySet();
		for (Iterator<R4EReviewGroup> iterator = groups.iterator(); iterator.hasNext();) {
			R4EReviewGroup oGroup = iterator.next();
			ReviewsRFSProxy rfs = fOpenStore.get(oGroup);
			if (rfs != null) {
				rfs.close();
			}
		}
		fOpenStore.clear();

		// Create new instance and cache it
		File groupDir = new File(group.getFolder());
		ReviewsRFSProxy fsStore = new ReviewsRFSProxy(groupDir, false);
		fOpenStore.put(group, fsStore);

		return fsStore;
	}
}
