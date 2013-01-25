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
 * This class defines the interface for the R4E upgrade engine.  All upgraders should implement it.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeException;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */

public interface IR4EVersionUpgrader {

	/**
	 * Method registerUpgrader.
	 * 
	 */
	public void registerUpgrader();

	/**
	 * Method deRegisterUpgrader.
	 * 
	 */
	public void deRegisterUpgrader();

	/**
	 * Method isCompatible.
	 * @return boolean
	 */
	public boolean isCompatible();

	/**
	 * Method getUpgradePath.
	 * @return UpgradePath
	 */
	public UpgradePath getUpgradePath();

	/**
	 * Method getUpgradePath.
	 * @param aResourceUri - URI
	 * @param aRecursive - boolean
	 * @throws R4EUpgradeException
	 */
	public void upgrade(URI aResourceUri, boolean aRecursive) throws R4EUpgradeException;
}
