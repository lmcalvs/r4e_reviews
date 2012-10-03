// $codepro.audit.disable packageNamingConvention, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, packageJavadoc
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
 * This class bootstraps the R4E JUnit UI test plugin
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.frame.core.utils.Tracer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
@SuppressWarnings("restriction")
public class R4EUITestPlugin extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field PLUGIN_ID. (value is ""org.eclipse.mylyn.reviews.r4e.ui.tests"")
	 */
	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.ui.tests"; // The plug-in ID

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field Plugin.
	 */
	private static R4EUITestPlugin Fplugin; // The shared instance

	/**
	 * Field Tracer.
	 */
	public static Tracer Ftracer;

	/**
	 * Field fFormColors.
	 */
	private FormColors fFormColors; // shared colors for all forms

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public R4EUITestPlugin() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.enforceTheSingletonPropertyWithAPrivateConstructor, emptyMethod
		//Empty constructor
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
	@Override
	public void start(BundleContext aContext) throws Exception { // $codepro.audit.disable declaredExceptions, unnecessaryExceptions
		super.start(aContext);
		Fplugin = this;
		Ftracer = new Tracer();
		Ftracer.init(PLUGIN_ID);
		Ftracer.traceDebug("plugin started");
	}

	/**
	 * Method stop.
	 * 
	 * @param aContext
	 *            BundleContext
	 * @throws Exception
	 * @see org.osgi.framework.BundleActivator#stop(BundleContext)
	 */
	@Override
	public void stop(BundleContext aContext) throws Exception { // $codepro.audit.disable declaredExceptions

		//Dispose default colors
		if (null != fFormColors) {
			fFormColors.dispose();
			fFormColors = null;
		}

		Fplugin = null;
		super.stop(aContext);
		Ftracer.traceDebug("plugin stopped");
	}

	/**
	 * Method getFormColors.
	 * 
	 * @param display
	 *            Display
	 * @return FormColors
	 */
	public FormColors getFormColors(Display display) {
		if (null == fFormColors) {
			fFormColors = new FormColors(display);
			fFormColors.markShared();
		}
		return fFormColors;
	}

	/**
	 * Gets the plugin
	 * 
	 * @return the shared instance
	 */
	public static R4EUITestPlugin getDefault() {
		return Fplugin;
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

	/**
	 * Method logWarning.
	 * 
	 * @param aMsg
	 *            String
	 * @param ae
	 *            Exception
	 */
	public void logWarning(String aMsg, Exception ae) {
		getLog().log(new Status(IStatus.WARNING, PLUGIN_ID, IStatus.OK, aMsg, ae));
	}

	/**
	 * Method logInfo.
	 * 
	 * @param aMsg
	 *            String
	 * @param ae
	 *            Exception
	 */
	public void logInfo(String aMsg, Exception ae) {
		getLog().log(new Status(IStatus.INFO, PLUGIN_ID, IStatus.OK, aMsg, ae));
	}

}
