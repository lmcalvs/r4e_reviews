/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui;

import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.core.utils.Tracer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Sebastien Dubois
 */
public class R4EUIPlugin extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field PLUGIN_ID. (value is ""org.eclipse.mylyn.reviews.r4e.ui"")
	 */
	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.ui"; // The plug-in ID //$NON-NLS-1$

	/**
	 * Field JDT_PLUGIN_ID. (value is ""org.eclipse.jdt.core"")
	 */
	public static final String JDT_PLUGIN_ID = "org.eclipse.jdt.core"; //$NON-NLS-1$

	/**
	 * Field CDT_PLUGIN_ID. (value is ""org.eclipse.cdt.core"")
	 */
	public static final String CDT_PLUGIN_ID = "org.eclipse.cdt.core"; //$NON-NLS-1$

	/**
	 * Field R4E_REPORT_PLUGIN_ID. (value is ""org.eclipse.mylyn.reviews.r4e.report"")
	 */
	public static final String R4E_REPORT_PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.report"; //$NON-NLS-1$

	/**
	 * Field R4E_REPORT_FEATURE_ID. (value is ""org.eclipse.mylyn.reviews.r4e.reports"")
	 */
	public static final String R4E_REPORT_FEATURE_ID = "org.eclipse.mylyn.reviews.r4e.reports"; //$NON-NLS-1$

	/**
	 * Field R4E_MAIL_PLUGIN_ID. (value is ""org.eclipse.mylyn.reviews.r4e.mail.smtp"")
	 */
	public static final String R4E_MAIL_PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e.mail.smtp"; //$NON-NLS-1$

	/**
	 * Field R4E_VERSION_QUALIFIER. (value is ""qualifier"")
	 */
	private static final String R4E_VERSION_QUALIFIER = "qualifier"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field Plugin.
	 */
	private static R4EUIPlugin Fplugin; // The shared instance

	/**
	 * Field Tracer.
	 */
	public static Tracer Ftracer;

	/**
	 * Field fFormColors.
	 */
	private FormColors fFormColors; // shared colors for all forms

	/**
	 * Field FJDTAvailable.
	 */
	private static boolean FJDTAvailable = false;

	/**
	 * Field FCDTAvailable.
	 */
	private static boolean FCDTAvailable = false;

	/**
	 * Field FReportAvailable.
	 */
	private static boolean FReportAvailable = false;

	/**
	 * Field FMailAvailable.
	 */
	private static boolean FMailAvailable = false;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * The constructor
	 */
	public R4EUIPlugin() { // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.enforceTheSingletonPropertyWithAPrivateConstructor, emptyMethod

		//Set Plugin validity: JDT
		final Bundle bdleJDT = Platform.getBundle(JDT_PLUGIN_ID);
		if (null != bdleJDT) {
			FJDTAvailable = true;
		}

		//Set Plugin validity: CDT
		final Bundle bdleCDT = Platform.getBundle(CDT_PLUGIN_ID);
		if (null != bdleCDT) {
			FCDTAvailable = true;
		}

		//Set Plugin validity: Mail
		final Bundle bdleMail = Platform.getBundle(R4E_MAIL_PLUGIN_ID);
		if (null != bdleMail) {
			FMailAvailable = true;
		}
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

		//Set Plugin validity: Report
		verifyReportFeature();
		Ftracer.traceDebug("Report features available: " + FReportAvailable);

	}

	/**
	 * Verify if we should consider the availability for the REPORT option based on the features level
	 */
	private void verifyReportFeature() {

		//Testing for the eclipse runtime here
		final Bundle bdleReport = Platform.getBundle(R4E_REPORT_PLUGIN_ID);
		if (bdleReport != null) {
			Version ver = bdleReport.getVersion();
			if (ver.getQualifier().equals(R4E_VERSION_QUALIFIER)) {
				//We are in a runtime environment, so enable it
				FReportAvailable = true;
				Ftracer.traceDebug("In a runtime environment for the report"); //$NON-NLS-1$
				return;
			}
		}

		//Testing for the binary execution
		IBundleGroupProvider[] grpprovider = Platform.getBundleGroupProviders();
		for (IBundleGroupProvider element : grpprovider) {
			IBundleGroup[] bdlgrp = element.getBundleGroups();
			Ftracer.traceDebug("bundle group count: " + bdlgrp.length); //$NON-NLS-1$
			for (int j = 0; j < bdlgrp.length; j++) {
				if (bdlgrp[j].getIdentifier().contains(R4E_REPORT_FEATURE_ID)) {
					Ftracer.traceDebug("\t bdlgrp[" + j + "] : " + bdlgrp[j].getName() + "\t : " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ bdlgrp[j].getProviderName() + "\t version: " + bdlgrp[j].getVersion() + "\t : " //$NON-NLS-1$//$NON-NLS-2$
							+ bdlgrp[j].getIdentifier());
					FReportAvailable = true;
					break;

				}
			}

		}

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
	public static R4EUIPlugin getDefault() {
		return Fplugin;
	}

	/**
	 * Check if JDT functionality is available
	 * 
	 * @return Boolean
	 */
	public static boolean isJDTAvailable() {
		return FJDTAvailable;
	}

	/**
	 * Check if CDT functionality is available
	 * 
	 * @return Boolean
	 */
	public static boolean isCDTAvailable() {
		return FCDTAvailable;
	}

	/**
	 * Method isUserReportAvailable.
	 * 
	 * @return Boolean
	 */
	public static boolean isUserReportAvailable() {
		return FReportAvailable;
	}

	/**
	 * Method isMailAvailable.
	 * 
	 * @return Boolean
	 */
	public static boolean isMailAvailable() {
		return FMailAvailable;
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
