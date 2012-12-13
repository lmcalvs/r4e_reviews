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
package org.eclipse.mylyn.internal.reviews.example.emftasks.ui;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * @author Miles Parker
 */
public class EmfExampleImages {

	private static final URL baseURL = EmfExampleUiPlugin.getDefault().getBundle().getEntry("/icons/"); //$NON-NLS-1$

	public static final ImageDescriptor ECORE_OVERLAY = create("eview16/EcoreOverlay.png"); //$NON-NLS-1$

	public static final ImageDescriptor ECORE = create("obj16/Ecore.png"); //$NON-NLS-1$

	private static ImageDescriptor create(String path) {
		try {
			return ImageDescriptor.createFromURL(makeIconFileURL(path));
		} catch (MalformedURLException e) {
			return ImageDescriptor.getMissingImageDescriptor();
		}
	}

	private static URL makeIconFileURL(String path) throws MalformedURLException {
		if (baseURL == null) {
			throw new MalformedURLException();
		}
		return new URL(baseURL, path);
	}
}
