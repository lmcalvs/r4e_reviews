/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.internal.emf.compare;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.core.utils.Tracer;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Alvaro Sanchez-Leon
 */
public class EMFCompareCheck extends AbstractUIPlugin implements IStartup {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.emf.compare.detection"; // The plug-in ID //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field Plugin.
	 */
	private static EMFCompareCheck Plugin; // The shared instance

	/**
	 * Field Tracer.
	 */
	public static Tracer fTracer = new Tracer();

	public void earlyStartup() {
		// ignore

	}

	@Override
	public void start(BundleContext context) throws Exception {
		// super.start(context);
		Plugin = this;
		fTracer = new Tracer();
		fTracer.init(PLUGIN_ID);
		fTracer.traceDebug("plugin started: " + PLUGIN_ID);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Plugin = null;
		// super.stop(context);
		fTracer.traceDebug("plugin stopped");
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

}
