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

package org.eclipse.mylyn.reviews.r4e.ui.tests.utils;

import org.eclipse.mylyn.reviews.r4e.upgrade.UpgradePath;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeContainer;
import org.eclipse.mylyn.reviews.r4e.upgrade.upgraders.impl.R4EDefaultCompatibleUpgrader;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EDummyCompatibleUpgrader extends R4EDefaultCompatibleUpgrader {

	/**
	 * Field BASE_VERSION. Value is ""0.7.0""
	 */
	private static final String BASE_VERSION = "0.7.0"; //$NON-NLS-1$

	/**
	 * Field TARGET_VERSION. Value is ""0.8.0""
	 */
	private static final String TARGET_VERSION = "0.8.0"; //$NON-NLS-1$ //Dummy version used only for testing

	/**
	 * Constructor
	 */
	public R4EDummyCompatibleUpgrader() {
		fPath = new UpgradePath(BASE_VERSION, TARGET_VERSION);
		R4EUpgradeContainer.addUpgrader(fPath, this);
	}
}
