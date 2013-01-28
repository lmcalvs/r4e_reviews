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
 * This class defines and implements Upgrade Paths.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.impl;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUpgradeException extends Exception {

	/**
	 * Field serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * @param message
	 */
	public R4EUpgradeException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param chainedExc
	 *            - Original chained Exception
	 */
	public R4EUpgradeException(String message, Throwable chainedExc) {
		super(message, chainedExc);
	}

	/**
	 * @param chainedExc
	 *            Original chained Exception
	 */
	public R4EUpgradeException(Throwable chainedExc) {
		super(chainedExc);
	}
}
