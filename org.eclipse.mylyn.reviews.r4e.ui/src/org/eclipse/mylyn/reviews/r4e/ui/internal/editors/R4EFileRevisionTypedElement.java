/*******************************************************************************
 * Copyright (c) 2006, 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 * Ericsson AB     - Adapted to use with R4E
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;

//import java.net.URI;

/**
 * An {@link ITypedElement} wrapper for {@link IFileRevision} for use with R4E
 * 
 * @author Sebastien Dubois
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
@SuppressWarnings(value = { "nls" })
public class R4EFileRevisionTypedElement extends StorageTypedElement implements URIHandler {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fFileVersion.
	 */
	private final R4EFileVersion fFileVersion;

	/**
	 * Field author.
	 */
	private String fAuthor;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * @param aFileVersion
	 *            R4EFileVersion - the file revision
	 */
	public R4EFileRevisionTypedElement(R4EFileVersion aFileVersion) {
		this(aFileVersion, null);
	}

	/**
	 * Method FileRevisionTypedElement
	 * 
	 * @param aFileVersion
	 *            R4EFileVersion - the file revision
	 * @param aLocalEncoding
	 *            String the encoding of the local file that corresponds to the given file revision
	 */
	public R4EFileRevisionTypedElement(R4EFileVersion aFileVersion, String aLocalEncoding) {
		super(aLocalEncoding);
		Assert.isNotNull(aFileVersion);
		fFileVersion = aFileVersion;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getFileVersion.
	 * 
	 * @return R4EFileVersion
	 */
	public R4EFileVersion getFileVersion() {
		return fFileVersion;
	}

	/**
	 * Method getName.
	 * 
	 * @return String
	 * @see org.eclipse.compare.ITypedElement#getName()
	 */
	public String getName() {
		return fFileVersion.getName();
	}

	/**
	 * Method fetchContents.
	 * 
	 * @param aMonitor
	 *            IProgressMonitor
	 * @return IStorage
	 * @throws CoreException
	 */
	@Override
	protected IStorage fetchContents(IProgressMonitor aMonitor) {
		//Fetch contents from the local repository
		try {
			final IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview()
					.getReview());
			return localRepository.getIStorage(null, fFileVersion);
		} catch (ReviewsFileStorageException e) {
			UIUtils.displayReviewsFileStorageErrorDialog(e);
		}
		return null;
	}

	/**
	 * Method getFileRevision. Method used in EMF compare for the git file
	 * 
	 * @return R4EFileVersion
	 */
	public IFileRevision getFileRevision() {
		R4EUIPlugin.Ftracer.traceInfo("R4EFileRevisionTypedElement,getFileRevision() used with git: "
				+ fFileVersion.getFileRevision().toString());
		return fFileVersion.getFileRevision();
	}

	/**
	 * @return String the string contains a unique content id
	 */
	public String getContentIdentifier() {
		return fFileVersion.getFileRevision().getContentIdentifier();
	}

	/**
	 * @return the human readable timestamp of this element
	 */
	public String getTimestamp() {
		final long date = fFileVersion.getFileRevision().getTimestamp();
		final Date dateFromLong = new Date(date);
		return DateFormat.getDateTimeInstance().format(dateFromLong);
	}

	/**
	 * Method getPath
	 * 
	 * @return the human readable path of this element
	 */
	public String getPath() {
		final java.net.URI uri = fFileVersion.getFileRevision().getURI();
		if (null != uri) {
			return uri.getPath();
		}
		return getName();
	}

	/**
	 * Method getDocumentKey.
	 * 
	 * @param aElement
	 *            Object
	 * @return IEditorInput
	 */
	@Override
	public IEditorInput getDocumentKey(Object aElement) {
		//The following lines were removed because it caused the document to become dirty
		//when open at the same time in a single editor
		if (aElement.equals(this) && null != getBufferedStorage()) {
			return new R4EFileRevisionEditorInput(fFileVersion);
		}
		return null;
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return fFileVersion.getFileRevision().hashCode();
	}

	/**
	 * Method equals.
	 * 
	 * @param aObj
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object aObj) {
		if (aObj == this) {
			return true;
		}
		if (aObj instanceof R4EFileRevisionTypedElement) {
			final R4EFileRevisionTypedElement other = (R4EFileRevisionTypedElement) aObj;
			return other.getFileVersion().equals(getFileVersion());
		}
		return false;
	}

	/**
	 * Method getAuthor.
	 * 
	 * @return the author
	 */
	public String getAuthor() {
		if (null == fAuthor) {
			fAuthor = fFileVersion.getFileRevision().getAuthor();
		}
		return fAuthor;
	}

	/**
	 * Method setAuthor.
	 * 
	 * @param aAuthor
	 *            String - the author
	 */
	public void setAuthor(String aAuthor) {
		fAuthor = aAuthor;
	}

	/**
	 * Method fetchAuthor.
	 * 
	 * @param aMonitor
	 *            IProgressMonitor
	 * @throws CoreException
	 */
	public void fetchAuthor(IProgressMonitor aMonitor) throws CoreException {
		if (null == getAuthor() && fFileVersion.getFileRevision().isPropertyMissing()) {
			final IFileRevision other = fFileVersion.getFileRevision().withAllProperties(aMonitor);
			fAuthor = other.getAuthor();
		}
	}

	//
	//
	//	Method to handle the URIHandlers
	//
	//
	public boolean canHandle(org.eclipse.emf.common.util.URI uri) {
		// ignore
		return false;
	}

	public InputStream createInputStream(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {
		try {
			return getContents();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public OutputStream createOutputStream(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {
		// ignore
		return null;
	}

	public void delete(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) throws IOException {
		// ignore

	}

	public boolean exists(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) {
		// ignore
		return false;
	}

	public Map<String, ?> getAttributes(org.eclipse.emf.common.util.URI uri, Map<?, ?> options) {
		// ignore
		return null;
	}

	public void setAttributes(org.eclipse.emf.common.util.URI uri, Map<String, ?> attributes, Map<?, ?> options)
			throws IOException {
		// ignore

	}

	/**
	 * This implementation delegates to the {@link #getURIConverter(Map) URI converter}'s
	 * {@link URIConverter#getContentHandlers() content handlers}.
	 */
	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		URIConverter uriConverter = (URIConverter) options.get(URIConverter.OPTION_URI_CONVERTER);
		R4EUIPlugin.Ftracer.traceInfo("R4EFileRevisionTypedElement,contentDescription() used with git: " + uri);
		InputStream inputStream = null;
		Map<String, ?> result = null;
		Map<Object, Object> context = new HashMap<Object, Object>();
		try {
			for (ContentHandler contentHandler : uriConverter.getContentHandlers()) {
				if (contentHandler.canHandle(uri)) {
					if (inputStream == null) {
						try {
							inputStream = createInputStream(uri, options);
						} catch (IOException exception) {
							inputStream = new ByteArrayInputStream(new byte[0]);
						}
						if (!inputStream.markSupported()) {
							inputStream = new BufferedInputStream(inputStream);
						}
						inputStream.mark(Integer.MAX_VALUE);
					} else {
						inputStream.reset();
					}
////					URI uriTemp = getFileURI(getFileVersion().getFileRevision());
					//Lets remove the version of the uri
					IPath ipath = null;
//					try {
//						ipath = getFileVersion().getFileRevision().getStorage(null).getFullPath();
//						String fileVersion = ipath.lastSegment();
//						String[] strar = fileVersion.split(" ");
//						ipath = ipath.removeLastSegments(1).append(strar[0]);
//						R4EUIPlugin.Ftracer.traceInfo("Path with no version: " + ipath);
//					} catch (CoreException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					org.eclipse.emf.common.util.URI uriTemp = URI.createURI(ipath.toString());
//
////					org.eclipse.emf.common.util.URI uriTemp = URI.createURI(fFileVersion.getFileRevision()
////							.getURI()
////							.toString());
//					//fFileVersion.getFileRevision().getURI();
//					R4EUIPlugin.Ftracer.traceInfo("R4EFileRevisionTypedElement,contentDescription() used with git: "
//							+ uriTemp);
//					Map<String, ?> contentDescription = contentHandler.contentDescription(uriTemp, inputStream,
//							options, context);
					Map<String, ?> contentDescription = contentHandler.contentDescription(uri, inputStream, options,
							context);
					switch ((ContentHandler.Validity) contentDescription.get(ContentHandler.VALIDITY_PROPERTY)) {
					case VALID: {
						return contentDescription;
					}
					case INDETERMINATE: {
						if (result == null) {
							result = contentDescription;
						}
						break;
					}
					case INVALID: {
						break;
					}
					}
				}
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}

		return result == null ? ContentHandler.INVALID_CONTENT_DESCRIPTION : result;
	}

//	/**
//	 * Get the URI for the file
//	 * 
//	 * @param IFileRevision
//	 *            aRevision
//	 * @return org.eclipse.emf.common.util.URI
//	 */
//	private org.eclipse.emf.common.util.URI getFileURI(IFileRevision aRevision) {
//
//		final java.net.URI baseURI = aRevision.getURI();
//		if (baseURI != null) {
//			return URI.createURI(baseURI.toString());
//		}
//
//		URI result = null;
//		try {
//			final IStorage storage = aRevision.getStorage(new NullProgressMonitor());
//			final String name = aRevision.getName();
//
//			IPath path = storage.getFullPath();
//			final String lastSegment = path.lastSegment();
//			if (!lastSegment.equals(name)) {
//				final int nameIndex = lastSegment.indexOf(name);
//				if (nameIndex != -1) {
//					path = path.removeLastSegments(1);
//					path = path.append(lastSegment.substring(0, nameIndex + name.length()));
//				}
//			}
//
//			result = URI.createURI(path.toString());
//		} catch (CoreException e) {
//			R4EUIPlugin.Ftracer.traceError("CoreException : " + e);
//		}
//		return result;
//	}

}
