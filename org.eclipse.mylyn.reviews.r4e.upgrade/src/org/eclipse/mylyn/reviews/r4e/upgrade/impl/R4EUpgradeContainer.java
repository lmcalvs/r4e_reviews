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
 * This class defines and implements the container for all upgrade paths objects
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.impl;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader;
import org.eclipse.mylyn.reviews.r4e.upgrade.UpgradePath;
import org.eclipse.mylyn.reviews.r4e.upgrade.upgraders.impl.R4E_012_020_Upgrader;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUpgradeContainer {

	/**
	 * Field fBaseVersion.
	 */
	private static Map<UpgradePath, IR4EVersionUpgrader> FUpgradeMap = new HashMap<UpgradePath, IR4EVersionUpgrader>();

	/**
	 * Method initializeUpgraders.
	 */
	public static void initializeUpgraders() {
		//NOTE:  This code should be modified to include new upgraders.
		new R4E_012_020_Upgrader();
	}

	/**
	 * Method addUpgrader.
	 * @param aPath - UpgradePath
	 * @param aR4EVersionUpdater - IR4EVersionUpgrader
	 */
	public static void addUpgrader(UpgradePath aPath, IR4EVersionUpgrader aR4EVersionUpdater) {
		FUpgradeMap.put(aPath, aR4EVersionUpdater);
	}

	/**
	 * Method removeUpgrader.
	 * @param aPath - UpgradePath
	 */
	public static void removeUpgrader(UpgradePath aPath) {
		FUpgradeMap.remove(aPath);
	}

	/**
	 * Method getUpgrader.
	 * @param aPath - UpgradePath
	 * @return IR4EVersionUpgrader
	 */
	public static IR4EVersionUpgrader getUpgrader(UpgradePath aPath) {
		return FUpgradeMap.get(aPath);
	}
}
