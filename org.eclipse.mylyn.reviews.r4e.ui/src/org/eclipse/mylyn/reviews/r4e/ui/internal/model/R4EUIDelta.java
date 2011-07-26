// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Delta element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIDelta extends R4EUIContent {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field DELTA_ICON_FILE. (value is ""icons/obj16/sel_obj.gif"")
	 */
	public static final String DELTA_ICON_FILE = "icons/obj16/delta_obj.gif";

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIDelta.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aDelta
	 *            R4EDelta
	 * @param aPosition
	 *            IR4EUIPosition
	 */
	public R4EUIDelta(IR4EUIModelElement aParent, R4EDelta aDelta, IR4EUIPosition aPosition) {
		super(aParent, aDelta, aPosition);
		super.setImage(DELTA_ICON_FILE);
	}
}
