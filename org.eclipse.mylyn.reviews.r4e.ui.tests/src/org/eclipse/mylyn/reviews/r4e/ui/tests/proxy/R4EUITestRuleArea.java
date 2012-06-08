/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Proxy class used to access/control the UI Rule Area element
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IRuleAreaInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.RuleAreaInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleArea;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;

@SuppressWarnings("restriction")
public class R4EUITestRuleArea extends R4EUITestElement {

	public R4EUITestRuleArea(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Create a new Rule Area
	 * 
	 * @param aRuleSet
	 * @param aName
	 * @return R4EUIRuleArea
	 */
	public R4EUIRuleArea createRuleArea(R4EUIRuleSet aRuleSet, String aName) {

		//Inject mockup dialog for New Rule Area
		IRuleAreaInputDialog mockRuleAreaDialog = mock(RuleAreaInputDialog.class);
		R4EUIDialogFactory.getInstance().setRuleAreaInputDialog(mockRuleAreaDialog);

		//Here we need to stub the RuleAreaInputDialog get methods to return what we want
		when(mockRuleAreaDialog.getNameValue()).thenReturn(aName);
		when(mockRuleAreaDialog.open()).thenReturn(Window.OK);

		return (R4EUIRuleArea) fParentProxy.getCommandProxy().runNewChildElement(aRuleSet);
	}
}
