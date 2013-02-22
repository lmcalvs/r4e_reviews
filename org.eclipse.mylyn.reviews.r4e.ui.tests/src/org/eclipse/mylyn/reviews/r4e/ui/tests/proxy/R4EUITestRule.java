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
 * This class implements a Proxy class used to access/control the UI Rule element
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
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleClass;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IRuleInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.RuleInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleViolation;

@SuppressWarnings("restriction")
public class R4EUITestRule extends R4EUITestElement {

	public R4EUITestRule(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Create a new Rule
	 * 
	 * @param aRuleViolation
	 * @param aRuleId
	 * @param aRuleTitle
	 * @param aRuleDescription
	 * @param aRuleClass
	 * @param aRuleRank
	 * @return R4EUIRule
	 */
	public R4EUIRule createRule(R4EUIRuleViolation aRuleViolation, String aRuleId, String aRuleTitle,
			String aRuleDescription, R4EDesignRuleClass aRuleClass, R4EDesignRuleRank aRuleRank) {

		//Inject mockup dialog for New Rule
		IRuleInputDialog mockRuleDialog = mock(RuleInputDialog.class);
		R4EUIDialogFactory.getInstance().setRuleInputDialog(mockRuleDialog);

		//Here we need to stub the RuleInputDialog get methods to return what we want
		when(mockRuleDialog.getIdValue()).thenReturn(aRuleId);
		when(mockRuleDialog.getTitleValue()).thenReturn(aRuleTitle);
		when(mockRuleDialog.getDescriptionValue()).thenReturn(aRuleDescription);
		when(mockRuleDialog.getClassValue()).thenReturn(aRuleClass);
		when(mockRuleDialog.getRankValue()).thenReturn(aRuleRank);
		when(mockRuleDialog.open()).thenReturn(Window.OK);

		return (R4EUIRule) fParentProxy.getCommandProxy().runNewChildElement(aRuleViolation);
	}
}
