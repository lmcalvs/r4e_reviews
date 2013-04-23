// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at>
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class defines various constants used in the R4E UI plugin
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Jacques Bouthillier - Add definition for Report
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils;

import java.util.TimeZone;

/**
 * @author Sebastien Dubois
 * @authot Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class R4EUIConstants { // $codepro.audit.disable convertClassToInterface

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field TIME_ZONE_OFFSET. (value is "TimeZone.getDefault().getOffset(System.currentTimeMillis()")
	 */
	public final static long TIME_ZONE_OFFSET = TimeZone.getDefault().getOffset(System.currentTimeMillis());


	//Test Constants
	/**
	 * Field R4E_UI_JOB_FAMILY.
	 */
	public static final String R4E_UI_JOB_FAMILY = "R4EUI";


}
