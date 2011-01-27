// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the Review model event that is fired to update to UI 
 * when the UI model is updated
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIModelEvent {

	/**
	 * Field fActedUpon.
	 */
	private final Object fActedUpon;
	
	/**
	 * Constructor for ReviewModelEvent.
	 * @param aReceiver Object
	 */
	public R4EUIModelEvent(Object aReceiver) {
		fActedUpon = aReceiver;
	}
	
	/**
	 * Method receiver.
	 * @return Object
	 */
	public Object receiver() {
		return fActedUpon;
	}
}
