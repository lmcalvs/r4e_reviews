/*******************************************************************************
 * Copyright (c) 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description: A Data format newer than the one supported in the current application is detected
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial Implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;
/**
 * @author Alvaro Sanchez-Leon
 * 
 */
public class CompatibilityException extends Exception {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * @param aMessage
	 */
	public CompatibilityException(String aMessage) {
		super(aMessage);
	}

	/**
	 * @param aMessage
	 * @param aChainedExc
	 *            - Original chained Exception
	 */
	public CompatibilityException(String aMessage, Throwable aChainedExc) {
		super(aMessage, aChainedExc);
	}

	/**
	 * @param aChainedExc
	 *            Original chained Exception
	 */
	CompatibilityException(Throwable aChainedExc) {
		super(aChainedExc);
	}

}
