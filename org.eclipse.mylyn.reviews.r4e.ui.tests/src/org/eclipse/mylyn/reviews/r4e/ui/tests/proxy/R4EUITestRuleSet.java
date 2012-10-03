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
 * This class implements a Proxy class used to access/control the UI Rule Set element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IRuleSetInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.RuleSetInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.swt.widgets.Display;

@SuppressWarnings("restriction")
public class R4EUITestRuleSet extends R4EUITestElement {

	public R4EUITestRuleSet(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Create a new Rule Set
	 * 
	 * @param aParentFolder
	 * @param aName
	 * @param aVersion
	 * @return R4EUIRuleSet
	 */
	public R4EUIRuleSet createRuleSet(String aParentFolder, String aName, String aVersion) {

		//Inject mockup dialog for New Rule Set
		IRuleSetInputDialog mockRuleSetDialog = mock(RuleSetInputDialog.class);
		R4EUIDialogFactory.getInstance().setRuleSetInputDialog(mockRuleSetDialog);

		//Here we need to stub the RuleSetInputDialog get methods to return what we want
		when(mockRuleSetDialog.getFolderValue()).thenReturn(aParentFolder);
		when(mockRuleSetDialog.getNameValue()).thenReturn(aName);
		when(mockRuleSetDialog.getVersionValue()).thenReturn(aVersion);
		when(mockRuleSetDialog.open()).thenReturn(Window.OK);

		//Inner class that runs the command on the UI thread
		class RunCreateRuleSet implements Runnable {
			private R4EUIRuleSet ruleSet;

			public R4EUIRuleSet getRuleSet() {
				return ruleSet;
			}

			public void run() {
				try {
					//Execute New Rule Set Command
					fParentProxy.getCommandProxy().executeCommand(R4EUIConstants.NEW_RULE_SET_COMMAND, null);
					TestUtils.waitForJobs();
					ruleSet = (R4EUIRuleSet) getNavigatorSelectedElement();
				} catch (ExecutionException e) {
					// ignore, test will fail later
				} catch (NotDefinedException e) {
					// ignore, test will fail later
				} catch (NotEnabledException e) {
					// ignore, test will fail later
				} catch (NotHandledException e) {
					// ignore, test will fail later
				}
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunCreateRuleSet newRuleSetJob = new RunCreateRuleSet();
		Display.getDefault().syncExec(newRuleSetJob);
		TestUtils.waitForJobs();
		return newRuleSetJob.getRuleSet();
	}

}
