/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * R4E Upgrade plugin class.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.upgrade;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.core.utils.Tracer;
import org.eclipse.mylyn.reviews.r4e.upgrade.impl.R4EUpgradeContainer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUpgradePlugin extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.upgrade"; //$NON-NLS-1$

	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	private static R4EUpgradePlugin FPlugin;
	
	/**
	 * Field Tracer.
	 */
	public static Tracer Ftracer;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * The constructor
	 */
	public R4EUpgradePlugin() {
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method start.
	 * 
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#start(BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		FPlugin = this;
		
		//Add Tracing support
		Ftracer = new Tracer();
		Ftracer.init(PLUGIN_ID);
		Ftracer.traceDebug("plugin started");
		
		R4EUpgradeContainer.initializeUpgraders();
	}

	/**
	 * Method stop.
	 * 
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#stop(BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		FPlugin = null;
		super.stop(context);
		Ftracer.traceDebug("plugin stopped");
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static R4EUpgradePlugin getDefault() {
		return FPlugin;
	}
	
	/**
	 * Method logError.
	 * 
	 * @param aMsg
	 *            String
	 * @param ae
	 *            Exception
	 */
	public void logError(String aMsg, Exception ae) {
		getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, IStatus.OK, aMsg, ae));
	}

}
