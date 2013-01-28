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
 * Default Abstract class that implements the IR4EVersionUpgrader interface.
 * All upgraders should extend it.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader;
import org.eclipse.mylyn.reviews.r4e.upgrade.UpgradePath;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class R4EVersionUpgrader implements IR4EVersionUpgrader {

	/**
	 * Field fPath.
	 */
	protected UpgradePath fPath;

	/**
	 * Method registerUpgrader.
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#registerUpgrader()
	 */
	public void registerUpgrader() {
		R4EUpgradeContainer.addUpgrader(fPath, this);
	}

	/**
	 * Method deRegisterUpgrader.
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#deRegisterUpgrader()
	 */
	public void deRegisterUpgrader() {
		R4EUpgradeContainer.removeUpgrader(fPath);
	}

	/**
	 * Method getUpgradePath.
	 * @return UpgradePath
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#getUpgradePath()
	 */
	public UpgradePath getUpgradePath() {
		return fPath;
	}

	/**
	 * Method isCompatible.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#isCompatible()
	 */
	public abstract boolean isCompatible();

	/**
	 * Method getUpgradePath.
	 * @param aResourceUri - URI
	 * @param aRecursive - boolean
	 * @throws R4EUpgradeException
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#upgrade(URI, boolean)
	 */
	public abstract void upgrade(URI aResourceUri, boolean aRecursive) throws R4EUpgradeException;
}
