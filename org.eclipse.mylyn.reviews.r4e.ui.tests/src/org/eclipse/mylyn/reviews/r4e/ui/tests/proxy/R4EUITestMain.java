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
 * This class implements a Proxy class used to access/control R4E programmatically
 * for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import java.net.URL;

import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

public class R4EUITestMain {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field FInstance.
	 */
	private static R4EUITestMain FInstance = null;

	/**
	 * Field fCommandProxy.
	 */
	private R4EUITestElement fCommandProxy = null;

	/**
	 * Field fPreferencesProxy.
	 */
	private R4EUITestElement fPreferencesProxy = null;

	/**
	 * Field fReviewGroupProxy.
	 */
	private R4EUITestElement fReviewGroupProxy = null;

	/**
	 * Field fReviewProxy.
	 */
	private R4EUITestElement fReviewProxy = null;

	/**
	 * Field fItemProxy.
	 */
	private R4EUITestElement fItemProxy = null;

	/**
	 * Field fParticipantProxy.
	 */
	private R4EUITestElement fParticipantProxy = null;

	/**
	 * Field fAnomalyProxy.
	 */
	private R4EUITestElement fAnomalyProxy = null;

	/**
	 * Field fCommentProxy.
	 */
	private R4EUITestElement fCommentProxy = null;

	/**
	 * Field fRuleSetProxy.
	 */
	private R4EUITestElement fRuleSetProxy = null;

	/**
	 * Field fRuleAreaProxy.
	 */
	private R4EUITestElement fRuleAreaProxy = null;

	/**
	 * Field fRuleViolationProxy.
	 */
	private R4EUITestElement fRuleViolationProxy = null;

	/**
	 * Field fRuleProxy.
	 */
	private R4EUITestElement fRuleProxy = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ECommandProxy.
	 */
	private R4EUITestMain() {
		fCommandProxy = new R4EUITestCommands(this);
		fPreferencesProxy = new R4EUITestPreferences(this);
		fReviewGroupProxy = new R4EUITestReviewGroup(this);
		fReviewProxy = new R4EUITestReview(this);
		fItemProxy = new R4EUITestItem(this);
		fParticipantProxy = new R4EUITestParticipant(this);
		fAnomalyProxy = new R4EUITestAnomaly(this);
		fCommentProxy = new R4EUITestComment(this);
		fRuleSetProxy = new R4EUITestRuleSet(this);
		fRuleAreaProxy = new R4EUITestRuleArea(this);
		fRuleViolationProxy = new R4EUITestRuleViolation(this);
		fRuleProxy = new R4EUITestRule(this);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getInstance.
	 * 
	 * @return R4EUITestProxy
	 */
	public static R4EUITestMain getInstance() {
		if (null == FInstance) {
			FInstance = new R4EUITestMain();
		}
		return FInstance;
	}

	/**
	 * Method getCommandProxy.
	 * 
	 * @return R4EUITestCommands
	 */
	public R4EUITestCommands getCommandProxy() {
		return (R4EUITestCommands) fCommandProxy;
	}

	/**
	 * Method getPreferencesProxy.
	 * 
	 * @return R4EUITestPreferences
	 */
	public R4EUITestPreferences getPreferencesProxy() {
		return (R4EUITestPreferences) fPreferencesProxy;
	}

	/**
	 * Method getReviewGroupProxy.
	 * 
	 * @return R4EUITestReviewGroup
	 */
	public R4EUITestReviewGroup getReviewGroupProxy() {
		return (R4EUITestReviewGroup) fReviewGroupProxy;
	}

	/**
	 * Method getReviewProxy.
	 * 
	 * @return R4EUITestReview
	 */
	public R4EUITestReview getReviewProxy() {
		return (R4EUITestReview) fReviewProxy;
	}

	/**
	 * Method getItemProxy.
	 * 
	 * @return R4EUITestItem
	 */
	public R4EUITestItem getItemProxy() {
		return (R4EUITestItem) fItemProxy;
	}

	/**
	 * Method getParticipantProxy.
	 * 
	 * @return R4EUITestParticipant
	 */
	public R4EUITestParticipant getParticipantProxy() {
		return (R4EUITestParticipant) fParticipantProxy;
	}

	/**
	 * Method getAnomalyProxy.
	 * 
	 * @return R4EUITestAnomaly
	 */
	public R4EUITestAnomaly getAnomalyProxy() {
		return (R4EUITestAnomaly) fAnomalyProxy;
	}

	/**
	 * Method getCommentProxy.
	 * 
	 * @return R4EUITestComment
	 */
	public R4EUITestComment getCommentProxy() {
		return (R4EUITestComment) fCommentProxy;

	}

	/**
	 * Method getRuleSetProxy.
	 * 
	 * @return R4EUITestRuleSet
	 */
	public R4EUITestRuleSet getRuleSetProxy() {
		return (R4EUITestRuleSet) fRuleSetProxy;
	}

	/**
	 * Method getRuleAreaProxy.
	 * 
	 * @return R4EUITestRuleArea
	 */
	public R4EUITestRuleArea getRuleAreaProxy() {
		return (R4EUITestRuleArea) fRuleAreaProxy;
	}

	/**
	 * Method getRuleViolationProxy.
	 * 
	 * @return R4EUITestRuleViolation
	 */
	public R4EUITestRuleViolation getRuleViolationProxy() {
		return (R4EUITestRuleViolation) fRuleViolationProxy;
	}

	/**
	 * Method getRuleProxy.
	 * 
	 * @return R4EUITestRule
	 */
	public R4EUITestRule getRuleProxy() {
		return (R4EUITestRule) fRuleProxy;
	}

	/**
	 * Method getHelp
	 * 
	 * @param aPath
	 * @return URL
	 */
	public URL getHelp(String aPath) {

		//Inner class that runs the command on the UI thread
		class RunGetHelp implements Runnable {
			private String path;

			private URL helpUrl;

			public URL getUrl() {
				return helpUrl;
			}

			public void setPath(String aPath) {
				path = aPath;
			}

			public void run() {
				//Resolve path to get help contents
				helpUrl = PlatformUI.getWorkbench().getHelpSystem().resolve(path, true);
			}
		}
		;

		//Run the UI job and wait until the command is completely executed before continuing
		RunGetHelp getHelpJob = new RunGetHelp();
		getHelpJob.setPath(aPath);
		Display.getDefault().syncExec(getHelpJob);
		TestUtils.waitForJobs();
		return getHelpJob.getUrl();
	}
}
