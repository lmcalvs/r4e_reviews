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
 * Default implemententation for compatible upgraders.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.upgraders.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeException;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EVersionUpgrader;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EDefaultCompatibleUpgrader extends R4EVersionUpgrader {

	/**
	 * Method isCompatible.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#isCompatible()
	 */
	@Override
	public boolean isCompatible() {
		return true;
	}

	/**
	 * Method getUpgradePath.
	 * @param aResourceUri - URI
	 * @param aRecursive - boolean
	 * @throws R4EUpgradeException
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#upgrade(URI, boolean)
	 */
	@Override
	public void upgrade(URI aResourceUri, boolean aRecursive) throws R4EUpgradeException {
		//Nothing to do
	}
}
