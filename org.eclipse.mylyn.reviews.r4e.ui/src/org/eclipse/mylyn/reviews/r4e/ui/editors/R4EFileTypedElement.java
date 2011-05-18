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
package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.io.InputStream;

import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.IResourceProvider;
import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.SharedDocumentAdapter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.IDocument;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.swt.graphics.Image;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * An {@link ITypedElement} wrapper for {@link IFileRevision} for use with R4E
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EFileTypedElement implements ITypedElement, IResourceProvider, IEncodedStreamContentAccessor, IAdaptable {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fFileVersion.
	 */
	private final R4EFileVersion fFileVersion;

	/**
	 * Field sharedDocumentAdapter.
	 */
	private ISharedDocumentAdapter sharedDocumentAdapter;

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
	 * Method getAdapter.
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (ISharedDocumentAdapter.class.equals(adapter)) {
			synchronized (this) {
				if (null == sharedDocumentAdapter) {
					sharedDocumentAdapter = new SharedDocumentAdapter() {
						@Override
						public IEditorInput getDocumentKey(Object element) {
							return R4EFileTypedElement.this.getDocumentKey(element);
						}

						public void flushDocument(IDocumentProvider provider, IEditorInput documentKey,
								IDocument document, boolean overwrite) {
							// The document is read-only
						}
					};
				}
				return sharedDocumentAdapter;
			}
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

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
		return fFileVersion.getResource().getName();
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return fFileVersion.getResource().hashCode();
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
			return other.getFileVersion().equals(getFileVersion());
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
		return null;
	}

	/**
	 * Method getType.
	 * 
	 * @return String
	 */
	public String getType() {
		final String name = getName();
		if (null != name) {
			final int index = name.lastIndexOf('.');
			if (index == -1) {
				return "";
			}
			if (index == (name.length() - 1)) {
				return "";
			}
			return name.substring(index + 1);
		}
		return ITypedElement.TEXT_TYPE;
	}

	/**
	 * Method getContents.
	 * 
	 * @return InputStream
	 * @throws CoreException
	 */
	public InputStream getContents() throws CoreException {
		return ((IFile) fFileVersion.getResource()).getContents();
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

	/**
	 * Method getDocumentKey.
	 * 
	 * @param aElement
	 *            Object
	 * @return IEditorInput
	 */
	public IEditorInput getDocumentKey(Object aElement) {
		if (aElement.equals(this)) {
			return new R4EFileEditorInput(fFileVersion);
		}
		return null;
	}
}
