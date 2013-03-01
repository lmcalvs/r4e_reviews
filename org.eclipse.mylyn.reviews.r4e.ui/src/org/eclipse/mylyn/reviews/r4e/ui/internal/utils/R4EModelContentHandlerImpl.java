/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as R4E content handler for the model review
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.impl.PlatformContentHandlerImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionTypedElement;
import org.eclipse.team.core.history.IFileRevision;

/**
 * @author Jacques Bouthillier
 */
public class R4EModelContentHandlerImpl extends PlatformContentHandlerImpl {

	/**
	 * Field fFileVersion.
	 */
	private R4EFileVersion fFileVersion;

	private R4EFileRevisionTypedElement r4eFileRevision;

//	public void setFileVersion(R4EFileRevisionTypedElement aFileVersion) {
//		r4eFileRevision = aFileVersion;
//		fFileVersion = r4eFileRevision.getFileVersion();
//	}

	public void setFileVersion(R4EFileVersion aFileVersion) {
		//r4eFileRevision = aFileVersion;
		fFileVersion = aFileVersion;
	}

	/**
	 * This implementation delegates to the platform's content description support,
	 */
	@Override
	public Map<String, Object> contentDescription(URI aUri, InputStream inputStream, Map<?, ?> options,
			Map<Object, Object> context) throws IOException {
		IContentDescription contentDescription;
		URI uri = null;
		if (fFileVersion != null) {
			uri = getFileURI(fFileVersion.getFileRevision());
		} else {
			//Just keep the uri not converted
			uri = aUri;
		}

//		if (uri.isPlatformResource() && PlatformResourceURIHandlerImpl.workspaceRoot != null) {
//			contentDescription = PlatformResourceURIHandlerImpl.WorkbenchHelper.getContentDescription(
//					uri.toPlatformString(true), options);
//		} else {
//			contentDescription = Platform.getContentTypeManager().getDescriptionFor(inputStream, uri.lastSegment(),
//					IContentDescription.ALL);
//		}

		contentDescription = Platform.getContentTypeManager().getDescriptionFor(inputStream, uri.lastSegment(),
				IContentDescription.ALL);

		if (contentDescription == null) {
			return INVALID_CONTENT_DESCRIPTION;
		} else {
			Map<String, Object> result = createContentDescription(ContentHandler.Validity.VALID);
			result.put(ContentHandler.CONTENT_TYPE_PROPERTY, contentDescription.getContentType().getId());
			Set<String> requestedProperties = getRequestedProperties(options);
			if (requestedProperties != null) {
				for (String property : requestedProperties) {
					QualifiedName qualifiedName = getQualifiedName(property);
					if (qualifiedName != null) {
						Object value = getDescriptionValue(qualifiedName, contentDescription.getProperty(qualifiedName));
						if (value != null) {
							result.put(property, value);
						}
					}
				}
			}
			return result;
		}
	}

	/**
	 * Get the URI for the file
	 * 
	 * @param IFileRevision
	 *            aRevision
	 * @return org.eclipse.emf.common.util.URI
	 */
	private org.eclipse.emf.common.util.URI getFileURI(IFileRevision aRevision) {

		final java.net.URI baseURI = aRevision.getURI();
		if (baseURI != null) {
			return URI.createURI(baseURI.toString());
		}

		URI result = null;
		try {
			final IStorage storage = aRevision.getStorage(new NullProgressMonitor());
			final String name = aRevision.getName();

			IPath path = storage.getFullPath();
			final String lastSegment = path.lastSegment();
			if (!lastSegment.equals(name)) {
				final int nameIndex = lastSegment.indexOf(name);
				if (nameIndex != -1) {
					path = path.removeLastSegments(1);
					path = path.append(lastSegment.substring(0, nameIndex + name.length()));
				}
			}

			result = URI.createURI(path.toString());
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceError("CoreException : " + e);
		}
		return result;
	}

}
