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
 * This class implements a Proxy class used to access/control the UI Rule Violation element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IRuleViolationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.RuleViolationInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleViolation;

@SuppressWarnings("restriction")
public class R4EUITestRuleViolation extends R4EUITestElement {

	public R4EUITestRuleViolation(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Create a new Rule Violation
	 * 
	 * @param aRuleSet
	 * @param aName
	 * @return R4EUIRuleArea
	 */
	public R4EUIRuleViolation createRuleViolation(R4EUIRuleArea aRuleArea, String aName) {

		//Inject mockup dialog for New Rule Violation
		IRuleViolationInputDialog mockRuleViolationDialog = mock(RuleViolationInputDialog.class);
		R4EUIDialogFactory.getInstance().setRuleViolationInputDialog(mockRuleViolationDialog);

		//Here we need to stub the RuleViolationInputDialog get methods to return what we want
		when(mockRuleViolationDialog.getNameValue()).thenReturn(aName);
		when(mockRuleViolationDialog.open()).thenReturn(Window.OK);

		return (R4EUIRuleViolation) fParentProxy.getCommandProxy().runNewChildElement(aRuleArea);
	}
}
