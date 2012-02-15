/**
 * Copyright (c) 2012 Ericsson AB
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB  - Initial API and implementation
 */
package org.eclipse.mylyn.reviews.r4e.core.utils;

/**
 * @author Alvaro Sanchez-Leon
 * 
 */
public class VersionId implements Comparable<VersionId> {

	private int	fMajor	= 0;
	private int	fMinor	= 0;
	private int	fMicro	= 0;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public VersionId(int aMajor, int aMinor, int aMicro) {
		fMajor = aMajor;
		fMinor = aMinor;
		fMicro = aMicro;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public int getfMajor() {
		return fMajor;
	}

	public int getfMinor() {
		return fMinor;
	}

	public int getfMicro() {
		return fMicro;
	}

	public int compareTo(VersionId aOther) {
		// major
		if (fMajor < aOther.getfMajor()) {
			return -1;
		}

		if (fMajor > aOther.getfMajor()) {
			return +1;
		}

		// minor
		if (fMinor < aOther.getfMinor()) {
			return -1;
		}

		if (fMinor > aOther.getfMinor()) {
			return +1;
		}

		// micro
		if (fMicro < aOther.getfMicro()) {
			return -1;
		}

		if (fMicro > aOther.getfMicro()) {
			return +1;
		}

		return 0;
	}
}
