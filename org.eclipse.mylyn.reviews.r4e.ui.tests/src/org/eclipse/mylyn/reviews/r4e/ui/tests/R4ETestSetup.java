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
 * This class implements the common setup for R4E JUnit UI Test suite 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests;

import java.io.IOException;
import java.net.URISyntaxException;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.ui.tests.utils.TestUtils;
import org.osgi.framework.Bundle;

public class R4ETestSetup extends TestSetup {

	public R4ETestSetup(Test suite) {
		super(suite);
	}

	/**
	 * Sets up the R4E test environment.
	 */
	@Override
	protected void setUp() throws Exception {

		try {
			TestUtils.setupTestEnvironment();
			TestUtils.setDefaultUser();
			TestUtils.startNavigatorView();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			Bundle bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
			Platform.getLog(bundle).log(
					new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(), e));
			try {
				TestUtils.cleanupTestEnvironment();
			} catch (CoreException e1) {
				bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
				Platform.getLog(bundle).log(
						new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(),
								e));
			} catch (IOException e1) {
				bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
				Platform.getLog(bundle).log(
						new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(),
								e));
			}
		} catch (IOException e) {
			Bundle bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
			Platform.getLog(bundle).log(
					new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(), e));
			try {
				TestUtils.cleanupTestEnvironment();
			} catch (CoreException e1) {
				bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
				Platform.getLog(bundle).log(
						new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(),
								e));
			} catch (IOException e1) {
				bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
				Platform.getLog(bundle).log(
						new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(),
								e));
			}
		} catch (URISyntaxException e) {
			Bundle bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
			Platform.getLog(bundle).log(
					new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(), e));
			try {
				TestUtils.cleanupTestEnvironment();
			} catch (CoreException e1) {
				bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
				Platform.getLog(bundle).log(
						new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(),
								e));
			} catch (IOException e1) {
				bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
				Platform.getLog(bundle).log(
						new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(),
								e));
			}
		}
	}

	/**
	 * Tears down R4E test setup.
	 */
	@Override
	protected void tearDown() throws Exception {
		try {
			TestUtils.stopNavigatorView();
			TestUtils.cleanupTestEnvironment();
		} catch (CoreException e) {
			Bundle bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
			Platform.getLog(bundle).log(
					new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(), e));
		} catch (IOException e) {
			Bundle bundle = Platform.getBundle(R4EUITestPlugin.PLUGIN_ID);
			Platform.getLog(bundle).log(
					new Status(IStatus.ERROR, R4EUITestPlugin.PLUGIN_ID, IStatus.OK, "Exception: " + e.toString(), e));
		}
	}

}
