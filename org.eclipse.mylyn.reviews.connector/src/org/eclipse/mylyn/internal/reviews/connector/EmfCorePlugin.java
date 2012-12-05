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
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.connector;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * Controls the R4E plug-in life cycle.
 * 
 * @author Miles Parker
 */
public class EmfCorePlugin extends Plugin {

	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.connector"; //$NON-NLS-1$

	private static EmfCorePlugin plugin;

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
	public static EmfCorePlugin getDefault() {
		return plugin;
	}

	protected File getRepositoryConfigurationCache() {
		IPath stateLocation = Platform.getStateLocation(getBundle());
		IPath cache = stateLocation.append("repositoryConfigurations"); //$NON-NLS-1$
		return cache.toFile();
	}

	public static void logError(final String message, final Throwable throwable) {
		getDefault().getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, throwable));
	}

	public static void handleCoreException(String message, Throwable e) throws CoreException {
		throw new CoreException(new Status(IStatus.ERROR, EmfCorePlugin.PLUGIN_ID, message, e));
	}
}
