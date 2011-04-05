/*******************************************************************************
 * Copyright (c) 2011 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the R4E LDAP preferences
 ********************************************************************************/
package org.eclipse.mylyn.reviews.ldap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.frame.core.utils.Tracer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
/**
 * @author Jacques Bouthillier
 */
public class Activator extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	// The plug-in ID
	public static final String	FPLUGIN_ID	= "org.eclipse.mylyn.reviews.ldap"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	// The shared instance
	private static Activator	FPlugin;

	/**
	 * Field Tracer.
	 */
	public static Tracer		FTracer;

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
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#start(BundleContext)
	 */
	public void start(BundleContext aContext) throws Exception {
		super.start(aContext);
		FPlugin = this;
		FTracer = new Tracer();
		FTracer.traceDebug("plugin started: " + FPLUGIN_ID);
	}

	/**
	 * Method stop.
	 * 
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#stop(BundleContext)
	 */
	public void stop(BundleContext aContext) throws Exception {
		FPlugin = null;
		super.stop(aContext);
		FTracer.traceDebug("plugin stopped");
	}

	/**
	 * Gets the plug-in
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return FPlugin;
	}

	/**
	 * Method logError.
	 * 
	 * @param aMsg
	 *            String
	 * @param aE
	 *            Exception
	 */
	public void logError(String aMsg, Exception aE) {
		getLog().log(new Status(IStatus.ERROR, FPLUGIN_ID, IStatus.OK, aMsg, aE));
	}

	/**
	 * Method logWarning.
	 * 
	 * @param aMsg
	 *            String
	 * @param aE
	 *            Exception
	 */
	public void logWarning(String aMsg, Exception aE) {
		getLog().log(new Status(IStatus.WARNING, FPLUGIN_ID, IStatus.OK, aMsg, aE));
	}

	/**
	 * Method logInfo.
	 * 
	 * @param aMsg
	 *            String
	 * @param aE
	 *            Exception
	 */
	public void logInfo(String aMsg, Exception aE) {
		getLog().log(new Status(IStatus.INFO, FPLUGIN_ID, IStatus.OK, aMsg, aE));
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path.
	 * 
	 * @param aPath
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String aPath) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(FPLUGIN_ID, aPath);
	}

}
