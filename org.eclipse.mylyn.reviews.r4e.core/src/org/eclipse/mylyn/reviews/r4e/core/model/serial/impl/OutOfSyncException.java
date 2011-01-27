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
 *   Alvaro Sanchez-Leon - Initial Implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

/**
 * @author lmcalvs
 * 
 */
public class OutOfSyncException extends Exception {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	private Boolean				fGroupOutOfSynch	= false;
	private Boolean				fReviewOutOfSynch	= false;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * @param message
	 */
	OutOfSyncException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param chainedExc
	 *            - Original chained Exception
	 */
	OutOfSyncException(String message, Throwable chainedExc) {
		super(message, chainedExc);
	}

	/**
	 * @param chainedExc
	 *            Original chained Exception
	 */
	OutOfSyncException(Throwable chainedExc) {
		super(chainedExc);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * @return
	 */
	public Boolean isGroupOutOfSynch() {
		return fGroupOutOfSynch;
	}

	/**
	 * @return
	 */
	public Boolean isReviewOutOfSynch() {
		return fReviewOutOfSynch;
	}

	/**
	 * @return
	 */
	public void setGroupOutOfSynch(boolean aGroupOutOfSynch) {
		fGroupOutOfSynch = aGroupOutOfSynch;
	}

	/**
	 * @return
	 */
	public void setReviewOutOfSynch(boolean aReviewOutOfSynch) {
		fReviewOutOfSynch = aReviewOutOfSynch;
	}

}