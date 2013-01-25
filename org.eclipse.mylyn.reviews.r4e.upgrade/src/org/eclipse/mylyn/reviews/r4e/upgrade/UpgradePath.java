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

package org.eclipse.mylyn.reviews.r4e.upgrade;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class UpgradePath {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fBaseVersion.
	 */
	private final String fBaseVersion;

	/**
	 * Field fTargetVersion.
	 */
	private final String fTargetVersion;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * The constructor
	 */
	public UpgradePath(String aBaseVersion, String aTargetVersion) {
		fBaseVersion = aBaseVersion;
		fTargetVersion = aTargetVersion;
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getBaseVersion.
	 * 
	 * @return String
	 */
	public String getBaseVersion() {
		return fBaseVersion;
	}

	/**
	 * Method getTargetVersion.
	 * 
	 * @return String
	 */
	public String getTargetVersion() {
		return fTargetVersion;
	}

	/**
	 * Method equals.
	 * 
	 * @param aObject
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UpgradePath) {
			if (fBaseVersion.equals(((UpgradePath) obj).getBaseVersion())
					&& fTargetVersion.equals(((UpgradePath) obj).getTargetVersion())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return fBaseVersion.hashCode() + 31 * fTargetVersion.hashCode();
	}
}
