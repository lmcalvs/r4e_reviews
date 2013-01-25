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
 * Upgrader implemententation for 0.12.0 to 2.0.0 upgrades
 * 
 * Contributors:
 *   Miles Parker (Tasktop) - Initial implementation of Replacement rules
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade.upgraders.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.upgrade.UpgradePath;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeContainer;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeException;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EVersionUpgrader;
import org.eclipse.mylyn.reviews.r4e.upgrade.utils.SimpleFileConverter;
import org.eclipse.mylyn.reviews.r4e.upgrade.utils.SimpleFileConverter.Replacement;

/**
 * @author Miles Parker (Tasktop)
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4E_0120_200_Upgrader extends R4EVersionUpgrader {

	/**
	 * Field BASE_VERSION.  Value is ""0.12.0""
	 */
	private static final String BASE_VERSION = "0.12.0"; //$NON-NLS-1$

	/**
	 * Field TARGET_VERSION.  Value is ""2.0.0""
	 */
	private static final String TARGET_VERSION = "2.0.0"; //$NON-NLS-1$

	/**
	 * Field REPLACEMENTS.  Defines the replacement rules for the target version metadata
	 */
	static Replacement[] REPLACEMENTS = new Replacement[] {
		new Replacement("R4E_ROLE_(\\s*)", "$1"), new Replacement("<id\\sxmi", "<r4eId xmi"), //$NON-NLS-1$ //$NON-NLS-2$
		new Replacement("R4E_ANOMALY_STATE_(\\s*)", "$1"), new Replacement("R4E_REVIEW_PHASE_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
		new Replacement("R4E_REVIEW_DECISION_(\\s*)", "$1"), new Replacement("R4E_REVIEW_TYPE_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		new Replacement("R4E_CLASS_(\\s*)", "$1"), new Replacement("R4E_RANK_(\\s*)", "$1"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		new Replacement("R4E_(\\s*)", "$1"), new Replacement("\\suser=\"", " author=\""), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		new Replacement("<location\\sxsi", "<locations xsi"), new Replacement(" fragmentVersion=\\S*\"", ""), new Replacement("(<r4ecore:R4EReview.*\" )(name=\".*>)", "$1fragmentVersion=\"2\\.0\\.0\" $2") }; //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Constructor
	 */
	public R4E_0120_200_Upgrader() {
		fPath = new UpgradePath(BASE_VERSION, TARGET_VERSION);
		R4EUpgradeContainer.addUpgrader(fPath, this);
	}

	/**
	 * Method isCompatible.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.upgrade.IR4EVersionUpgrader#isCompatible()
	 */
	@Override
	public boolean isCompatible() {
		return false;
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
		SimpleFileConverter converter = new SimpleFileConverter(aResourceUri, "xrer", new String[] { "git" }, REPLACEMENTS, aRecursive);
		converter.setUser(true);
		converter.schedule();
		while (converter.getResult() == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new R4EUpgradeException(e);
			}
		}
		System.err.println(converter.getResult().getMessage());
	}
	
}
