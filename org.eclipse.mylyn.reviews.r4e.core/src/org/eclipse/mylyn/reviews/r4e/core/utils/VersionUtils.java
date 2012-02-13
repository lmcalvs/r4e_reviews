/**
 * Copyright (c) 2010 Ericsson AB
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
public class VersionUtils {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Versions expected as three separated numerical segments e.g. 1.0.1
	 * 
	 * @param aBaseVersion
	 * @param aTargetVersion
	 * @return -1 if base is less than target, +1 if base is greater then target, 0 if they are equal <br>
	 *         returns default +1 if a formatting problem is encountered.
	 */
	public static int compareVersions(String aBaseVersion, String aTargetVersion) {
		int result = 1;
		int expectedSegments = 3;
		String dot = "\\.";
		String[] versionSegsA = aBaseVersion.split(dot);
		String[] versionSegsB = aTargetVersion.split(dot);

		// return default
		if ((versionSegsA.length != expectedSegments) || (versionSegsB.length != expectedSegments)) {
			return result;
		}
		
		int a = Integer.valueOf(versionSegsA[0]).intValue();
		int b = Integer.valueOf(versionSegsA[1]).intValue();
		int c = Integer.valueOf(versionSegsA[2]).intValue();

		VersionId baseVersion = new VersionId(a, b, c);
		
		a = Integer.valueOf(versionSegsB[0]).intValue();
		b = Integer.valueOf(versionSegsB[1]).intValue();
		c = Integer.valueOf(versionSegsB[2]).intValue();
		
		VersionId targetVersion = new VersionId(a, b, c);

		return baseVersion.compareTo(targetVersion);
	}
}
