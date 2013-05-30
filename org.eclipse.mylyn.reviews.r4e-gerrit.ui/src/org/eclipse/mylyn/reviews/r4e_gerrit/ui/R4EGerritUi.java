/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the R4E-Gerrit UI.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e_gerrit.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e_gerrit.debug.utils.Tracer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */

/**
 * The activator class controls the plug-in life cycle
 */
public class R4EGerritUi extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e-gerrit.ui"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	// The shared instance
	private static R4EGerritUi fPlugin;
	
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
	public R4EGerritUi() {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext aContext) throws Exception {
		super.start(aContext);
		fPlugin = this;
		Ftracer = new Tracer();
		Ftracer.init(PLUGIN_ID);
		Ftracer.traceDebug("plugin started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext aContext) throws Exception {
		fPlugin = null;
		super.stop(aContext);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static R4EGerritUi getDefault() {
		return fPlugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param aPth the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String aPath) {
		return imageDescriptorFromPlugin(PLUGIN_ID, aPath);
	}

}
