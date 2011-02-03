/*******************************************************************************
 * Copyright (c) 2010 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API and Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.mylyn.reviews.frame.core.utils.Tracer;
import org.osgi.framework.BundleContext;

public class Activator extends Plugin {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	public static final String	PLUGIN_ID	= "org.eclipse.mylyn.reviews.r4e.core"; // The plug-in ID

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field Plugin.
	 */
	private static Activator	Plugin;											// The shared instance

	/**
	 * Field Tracer.
	 */
	public static Tracer		fTracer		= new Tracer();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public Activator() {
		// Empty constructor
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method start.
	 * 
	 * @param context
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#start(BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		// super.start(context);
		Plugin = this;
		fTracer = new Tracer();
		fTracer.traceDebug("plugin started: " + PLUGIN_ID);
	}

	/**
	 * Method stop.
	 * 
	 * @param context
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#stop(BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		Plugin = null;
		// super.stop(context);
		fTracer.traceDebug("plugin stopped");
	}

	/**
	 * Gets the plugin
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return Plugin;
	}

	/**
	 * Method logError.
	 * 
	 * @param msg
	 *            String
	 * @param e
	 *            Exception
	 */
	public void logError(String msg, Exception e) {
		getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, msg, e));
	}

	/**
	 * Method logWarning.
	 * 
	 * @param msg
	 *            String
	 * @param e
	 *            Exception
	 */
	public void logWarning(String msg, Exception e) {
		getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, IStatus.OK, msg, e));
	}

	/**
	 * Method logInfo.
	 * 
	 * @param msg
	 *            String
	 * @param e
	 *            Exception
	 */
	public void logInfo(String msg, Exception e) {
		getLog().log(new Status(IStatus.INFO, PLUGIN_ID, IStatus.OK, msg, e));
	}

	/**
	 * TODO: EGit API discussion required, tight coupling
	 * 
	 * @return
	 */
	public RepositoryCache getGitRepositoryCache() {
		return org.eclipse.egit.core.Activator.getDefault().getRepositoryCache();
	}

}
