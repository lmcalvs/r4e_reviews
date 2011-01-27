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
 *   Alvaro Sanchez-Leon - Initial API and Implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.versions;

import java.util.Collection;


/**
 * @author lmcalvs
 *
 */
public class ReviewVersionsException extends Exception {
	public enum VersionsExceptionCode {
		UNDEFINED, PROVIDER_NOT_SUPPORTED, MISSING_OBJECT, INCORRECT_OBJECT_TYPE, AMBIGUOS_OBJECT, IO, INVALID_ARGS
	};

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static final long		serialVersionUID	= 4079027220778830829L;
	private VersionsExceptionCode	fCode				= VersionsExceptionCode.UNDEFINED;
	private Collection<String>		fDetails			= null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * @param aMessage
	 */
	public ReviewVersionsException(String aMessage) {
		super(aMessage);
	}

	/**
	 * @param aMessage
	 * @param aChainedExc
	 *            - Original chained Exception
	 */
	public ReviewVersionsException(String aMessage, Throwable aChainedExc) {
		super(aMessage, aChainedExc);
	}

	/**
	 * @param aChainedExc
	 *            Original chained Exception
	 */
	public ReviewVersionsException(Throwable aChainedExc) {
		super(aChainedExc);
	}
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @return
	 */
	public VersionsExceptionCode getExceptionCode() {
		return fCode;
	}

	/**
	 * @param aCode
	 */
	public void setExceptionCode(VersionsExceptionCode aCode) {
		fCode = aCode;
	}
	
	/**
	 * @param aDetails
	 */
	public void setDetails(Collection<String> aDetails) {
		fDetails = aDetails;
	}

	/**
	 * @return
	 */
	public Collection<String> getDetails() {
		return fDetails;
	}

}
	