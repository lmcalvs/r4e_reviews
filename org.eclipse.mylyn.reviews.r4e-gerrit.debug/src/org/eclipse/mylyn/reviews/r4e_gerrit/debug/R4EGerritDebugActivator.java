package org.eclipse.mylyn.reviews.r4e_gerrit.debug;

import org.eclipse.mylyn.reviews.r4e_gerrit.debug.utils.Tracer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class R4EGerritDebugActivator extends AbstractUIPlugin {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field Tracer.
	 */
	public static Tracer Ftracer;

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.mylyn.reviews.r4e-gerrit.debug"; //$NON-NLS-1$

	// The shared instance
	private static R4EGerritDebugActivator fPlugin;
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * The constructor
	 */
	public R4EGerritDebugActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		fPlugin = this;
		Ftracer = new Tracer();
		Ftracer.init(PLUGIN_ID);
		Ftracer.traceDebug("plugin started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		fPlugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static R4EGerritDebugActivator getDefault() {
		return fPlugin;
	}
	
//	public static Tracer  initTracer(String aPluginId) {
//		Ftracer = new Tracer();
//		Ftracer.init(aPluginId);
//		Ftracer.traceDebug("plugin started");
//		return Ftracer;
//	}

}
