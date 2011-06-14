/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as R4E compare editor input from contents present in the local 
 * workspace
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.io.BufferedInputStream;
import java.io.InputStream;

import org.eclipse.compare.BufferedContent;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.swt.graphics.Image;
import org.eclipse.team.core.history.IFileRevision;

/**
 * An {@link ITypedElement} wrapper for {@link IFileRevision} for use with R4E
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EFileTypedElement extends BufferedContent implements ITypedElement, IResourceProvider,
		IEncodedStreamContentAccessor {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fFileVersion.
	 */
	private final R4EFileVersion fFileVersion;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * @param aFileVersion
	 *            R4EFileVersion - the file revision
	 */
	public R4EFileTypedElement(R4EFileVersion aFileVersion) {
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
		if (fFileVersion.getResource() != null) {
			return fFileVersion.getResource().getName();
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
		return getName().hashCode();
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
		if (aObj instanceof R4EFileTypedElement) {
			final R4EFileTypedElement other = (R4EFileTypedElement) aObj;
			return other.getFileVersion().getResource().equals(getFileVersion().getResource());
		}
		return false;
	}

	/**
	 * Method getResource.
	 * 
	 * @return IResource
	 */
	public IResource getResource() {
		return fFileVersion.getResource();
	}

	/**
	 * Method getImage.
	 * 
	 * @return Image
	 */
	public Image getImage() {
		return CompareUI.getImage(fFileVersion.getResource());
	}

	/**
	 * Method getType.
	 * 
	 * @return String
	 */
	public String getType() {
		if (fFileVersion.getResource() != null) {
			String s = fFileVersion.getResource().getFileExtension();
			if (s != null) {
				return s;
			}
		}
		return ITypedElement.UNKNOWN_TYPE;
	}

	/**
	 * Method getContents.
	 * 
	 * @return InputStream
	 * @throws CoreException
	 */
	@Override
	public InputStream getContents() throws CoreException {
		if (fFileVersion.getResource() instanceof IStorage) {
			return super.getContents();
		}
		return null;
	}

	/**
	 * Method getCharset.
	 * 
	 * @return String
	 * @throws CoreException
	 */
	public String getCharset() throws CoreException {
		return ((IFile) fFileVersion.getResource()).getCharset();
	}

	@Override
	/**
	 * Returns an open stream if the corresponding resource implements the
	 * <code>IStorage</code> interface. Otherwise the value <code>null</code> is returned.
	 *
	 * @return a buffered input stream containing the contents of this storage
	 * @exception CoreException if the contents of this storage could not be accessed
	 */
	protected InputStream createStream() throws CoreException {
		if (fFileVersion.getResource() instanceof IStorage) {
			InputStream is = null;
			IStorage storage = (IStorage) fFileVersion.getResource();
			try {
				is = storage.getContents();
			} catch (CoreException e) {
				if (e.getStatus().getCode() == IResourceStatus.OUT_OF_SYNC_LOCAL) {
					fFileVersion.getResource().refreshLocal(IResource.DEPTH_INFINITE, null);
					is = storage.getContents();
				} else {
					throw e;
				}
			}
			if (is != null) {
				return new BufferedInputStream(is);
			}
		}
		return null;
	}
}
