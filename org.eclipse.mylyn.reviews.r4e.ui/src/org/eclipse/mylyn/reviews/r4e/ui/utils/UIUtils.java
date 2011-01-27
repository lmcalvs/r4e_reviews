// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class provides general utility methods used in the UI implementation
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.utils;

import java.net.URL;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class UIUtils {
	
    /**
     * Load the current image and add it to the image registry
     * @param url - the localtion of the image to load
     * @return Image
     */
    public static Image loadIcon(String url) {
    	final Activator plugin = Activator.getDefault();
        Image icon = plugin.getImageRegistry().get(url);
        if (null == icon) {
            final URL imageURL = plugin.getBundle().getEntry(url);
            final ImageDescriptor descriptor = ImageDescriptor.createFromURL(imageURL);
            icon = descriptor.createImage();
            plugin.getImageRegistry().put(url, icon);
        }
        return icon;
    }
    
    /**
     * Method displayResourceErrorDialog.
     * @param e ResourceHandlingException
     */
    public static void displayResourceErrorDialog(ResourceHandlingException e) {
		Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		Activator.getDefault().logError("Exception: " + e.toString(), e);
		final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while changing element value",
				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
		dialog.open();
    }
    
    /**
     * Method displaySyncErrorDialog.
     * @param e OutOfSyncException
     */
    public static void displaySyncErrorDialog(OutOfSyncException e) {
		Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while changing element value.  " +
				"Please refresh the review navigator view and try the command again",
				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
		dialog.open();
		// TODO later we will want to do this automatically
    }
}
