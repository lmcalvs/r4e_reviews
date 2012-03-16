// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a property tester that is used to see if the Navigator
 * view display the default view widgets
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.testers;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewActivePropertyTester extends PropertyTester {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method test.
	 * 
	 * @param aReceiver
	 *            Object
	 * @param aProperty
	 *            String
	 * @param aArgs
	 *            Object[]
	 * @param aExpectedValue
	 *            Object
	 * @return boolean
	 * @see org.eclipse.core.expressions.IPropertyTester#test(Object, String, Object[], Object)
	 */
	public boolean test(Object aReceiver, String aProperty, Object[] aArgs, Object aExpectedValue) {
		return null != R4EUIModelController.getActiveReview();
	}
}
