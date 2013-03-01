/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileTypedElement;

/**
 * @author Jacques Bouthillier
 */
public class R4EEMFConverter extends ExtensibleURIConverterImpl {

	private R4EFileTypedElement r4eFileTypeElement = null;

	private R4EFileRevisionTypedElement r4eFileRevision = null;

	/**
	 * Creates an instance.
	 */
	public R4EEMFConverter(R4EFileTypedElement aFileTypedElement) {
		super();

		//Add the new R4E contentHandler
		Collection<ContentHandler> r4eModelContentHandlers = new ArrayList<ContentHandler>();
		R4EModelContentHandlerImpl r4eContentHandler = new R4EModelContentHandlerImpl();
		r4eContentHandler.setFileVersion(aFileTypedElement.getFileVersion());
		r4eModelContentHandlers.add(r4eContentHandler);
		getContentHandlers().addAll(r4eModelContentHandlers);

		r4eFileTypeElement = aFileTypedElement;
		this.uriHandlers.add(r4eFileTypeElement);
	}

	public R4EEMFConverter(R4EFileRevisionTypedElement aFileRevision) {
		super();
		getContentHandlers().clear();

		//Add the new R4E contentHandler
		Collection<ContentHandler> r4eModelContentHandlers = new ArrayList<ContentHandler>();
		R4EModelContentHandlerImpl r4eContentHandler = new R4EModelContentHandlerImpl();
		r4eContentHandler.setFileVersion(aFileRevision.getFileVersion());
		r4eModelContentHandlers.add(r4eContentHandler);
		getContentHandlers().addAll(r4eModelContentHandlers);

		r4eFileRevision = aFileRevision;
		this.uriHandlers.add(r4eFileRevision);
	}

	@Override
	public OutputStream createOutputStream(URI uri) throws IOException {
		return createOutputStream(uri, null);
	}

	@Override
	public InputStream createInputStream(URI uri) throws IOException {
		return createInputStream(uri, null);
	}

	/**
	 * Creates an input stream for the file path and returns it.
	 * <p>
	 * This implementation allocates a {@link FileInputStream}.
	 * </p>
	 * 
	 * @return an open input stream.
	 * @exception IOException
	 *                if there is a problem obtaining an open input stream.
	 */
	@Override
	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		if (r4eFileTypeElement != null) {
			try {
				R4EUIPlugin.Ftracer.traceDebug("using: R4EFileTypedElement for : "
						+ r4eFileTypeElement.getFileVersion().getRepositoryPath());

//				if (response != null) {
//					response.put(URIConverter.RESPONSE_TIME_STAMP_PROPERTY, r4eFileTypeElement.getModificationDate());
//				}
				return r4eFileTypeElement.getContents();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (r4eFileRevision != null) {
			try {
				R4EUIPlugin.Ftracer.traceDebug("using: r4eFileRevision for : " + r4eFileRevision.getName() + " ver: "
						+ r4eFileRevision.getFileVersion().getLocalVersionID());
//				if (response != null) {
//					response.put(URIConverter.RESPONSE_TIME_STAMP_PROPERTY, r4eFileRevision.getTimestamp());
//				}
				return r4eFileRevision.getContents();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}

	public boolean canHandle(URI uri) {
		// ignore
		String strFileVersion = uri.lastSegment();

		return false;
	}

	@Override
	public URIHandler getURIHandler(URI uri) {

		int size = uriHandlers.size();
		System.out.println("Number of uriHandlers: " + size);
		if (size > 0) {
			URIHandler[] data = uriHandlers.data();
			for (int i = 0; i < size; ++i) {
				URIHandler uriHandler = data[i];
				if (uriHandler instanceof R4EFileRevisionTypedElement || uriHandler instanceof R4EFileTypedElement) {
					return uriHandler;
				}
			}
		}
		return super.getURIHandler(uri);
	}

}
