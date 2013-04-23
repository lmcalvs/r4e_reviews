/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Upgrader implemententation for 1.0.0 to 2.0.0 upgrades
 * 
 * Contributors:
 *   Miles Parker (Tasktop) - Initial implementation of Replacement rules
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.upgraders.impl;

import org.eclipse.mylyn.reviews.r4e.upgrade.UpgradePath;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeContainer;

/**
 * @author Miles Parker (Tasktop)
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4E_012_020_Upgrader extends R4EDefaultCompatibleUpgrader {

	/**
	 * Field BASE_VERSION.  Value is ""1.0.0""
	 */
	private static final String BASE_VERSION = "0.12.0"; //$NON-NLS-1$

	/**
	 * Field TARGET_VERSION.  Value is ""2.0.0""
	 */
	private static final String TARGET_VERSION = "0.20.0"; //$NON-NLS-1$

	/**
	 * Constructor
	 */
	public R4E_012_020_Upgrader() {
		fPath = new UpgradePath(BASE_VERSION, TARGET_VERSION);
		R4EUpgradeContainer.addUpgrader(fPath, this);
	}
}
