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

/**
 * @author Alvaro Sanchez-Leon
 */
public class ReviewsFileStorageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2039821628997910836L;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * @param aMessage
	 */
	public ReviewsFileStorageException(String aMessage) {
		super(aMessage);
	}

	/**
	 * @param aMessage
	 * @param aChainedExc
	 *            - Original chained Exception
	 */
	public ReviewsFileStorageException(String aMessage, Throwable aChainedExc) {
		super(aMessage, aChainedExc);
	}

	/**
	 * @param aChainedExc
	 *            Original chained Exception
	 */
	public ReviewsFileStorageException(Throwable aChainedExc) {
		super(aChainedExc);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

}
