/*******************************************************************************
 * Copyright (c) 2010 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.versions.impl;

import org.eclipse.core.resources.IProject;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException.VersionsExceptionCode;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.ReviewsGITVersionsIFImpl;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIFFactory;

/**
 * @author lmcalvs
 *
 */
public class ReviewsVersionsIFFactoryImpl implements ReviewsVersionsIFFactory {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static ReviewsVersionsIFFactory	instance	= null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	private ReviewsVersionsIFFactoryImpl() {

	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * @return
	 */
	public static ReviewsVersionsIFFactory init() {
		if (instance == null) {
			instance = new ReviewsVersionsIFFactoryImpl();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.impl.ReviewsVersionsIFFactory#getVersionsIF(org.eclipse.core.resources.IProject)
	 */
	public ReviewsVersionsIF getVersionsIF(IProject project) throws ReviewVersionsException {
		// Git supportted in initial version
		if (!ReviewsGITVersionsIFImpl.isGitRepository(project)) {
			ReviewVersionsException exc = new ReviewVersionsException("");
			exc.setExceptionCode(VersionsExceptionCode.PROVIDER_NOT_SUPPORTED);
			throw exc;
		}

		return new ReviewsGITVersionsIFImpl();
	}
}
	