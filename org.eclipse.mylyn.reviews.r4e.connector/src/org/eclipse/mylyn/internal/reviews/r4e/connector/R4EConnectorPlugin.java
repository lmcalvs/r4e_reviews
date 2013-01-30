/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.r4e.connector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * Controls the R4E plug-in life cycle.
 * 
 * @author Miles Parker
 */
public class R4EConnectorPlugin extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.connector"; //$NON-NLS-1$

	private static R4EConnectorPlugin plugin;

	private R4EConnector connector;

	public R4EConnectorPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static R4EConnectorPlugin getDefault() {
		return plugin;
	}

	void setConnector(R4EConnector connector) {
		this.connector = connector;
	}

	public R4EConnector getConnector() {
		return connector;
	}

	public static void logError(final String message, final Throwable throwable) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, throwable));
	}

	public static void handleCoreException(String message, Throwable e) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, R4EConnectorPlugin.PLUGIN_ID, message, e));
	}
}
