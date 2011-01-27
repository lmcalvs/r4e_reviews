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
 *   Alvaro Sanchez-Leon - Initial API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.versions;

import org.eclipse.core.resources.IProject;
import org.eclipse.mylyn.reviews.r4e.core.versions.impl.ReviewsVersionsIFFactoryImpl;

public interface ReviewsVersionsIFFactory {

	/**
	 * Return the singleton instance of the factory
	 */
	public ReviewsVersionsIFFactory	instance	= ReviewsVersionsIFFactoryImpl.init();
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @param project
	 * @return
	 * @throws ReviewVersionsException
	 */
	public ReviewsVersionsIF getVersionsIF(IProject project) throws ReviewVersionsException;

}


