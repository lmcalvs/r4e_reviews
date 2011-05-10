/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.io.InputStream;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.SharedDocumentAdapter;
import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Image;
import org.eclipse.team.core.TeamException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

/**
 * Abstract Storage-based element
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
abstract class StorageTypedElement implements ITypedElement, IEncodedStreamContentAccessor, IAdaptable {

	/**
	 * Field bufferedContents.
	 */
	private IStorage bufferedContents;

	/**
	 * Field localEncoding.
	 */
	private final String localEncoding;

	/**
	 * Field sharedDocumentAdapter.
	 */
	private ISharedDocumentAdapter sharedDocumentAdapter;

	/**
	 * @param localEncoding
	 */
	protected StorageTypedElement(String localEncoding) {
		this.localEncoding = localEncoding;
	}

	/**
	 * Method getContents.
	 * 
	 * @return InputStream
	 * @throws CoreException
	 * @see org.eclipse.compare.IStreamContentAccessor#getContents()
	 */
	public InputStream getContents() throws CoreException {
		if (null == bufferedContents) {
			cacheContents(new NullProgressMonitor());
		}
		if (null != bufferedContents) {
			return bufferedContents.getContents();
		}
		return null;
	}

	/**
	 * Cache the contents for the remote resource in a local buffer. This method should be invoked before
	 * {@link #getContents()} to ensure that a round trip is not made in that method.
	 * 
	 * @param monitor
	 *            a progress monitor.
	 * @throws CoreException
	 */
	public void cacheContents(IProgressMonitor monitor) throws CoreException {
		bufferedContents = fetchContents(monitor);
	}

	/**
	 * @param monitor
	 * @return a storage for the element\
	 * @throws CoreException
	 * @throws TeamException
	 */
	protected abstract IStorage fetchContents(IProgressMonitor monitor) throws CoreException;

	/**
	 * @return the {@link IStorage} that has been buffered for this element\
	 */
	public IStorage getBufferedStorage() {
		return bufferedContents;
	}

	/**
	 * Method getImage.
	 * 
	 * @return Image
	 * @see org.eclipse.compare.ITypedElement#getImage()
	 */
	public Image getImage() {
		return CompareUI.getImage(getType());
	}

	/**
	 * Method getType.
	 * 
	 * @return String
	 * @see org.eclipse.compare.ITypedElement#getType()
	 */
	public String getType() {
		final String name = getName();
		if (null != name) {
			final int index = name.lastIndexOf('.');
			if (index == -1)
				return "";
			if (index == (name.length() - 1))
				return "";
			return name.substring(index + 1);
		}
		return ITypedElement.FOLDER_TYPE;
	}

	/**
	 * Method getCharset.
	 * 
	 * @return String
	 * @throws CoreException
	 * @see org.eclipse.compare.IEncodedStreamContentAccessor#getCharset()
	 */
	public String getCharset() throws CoreException {
		if (null != localEncoding)
			return localEncoding;
		if (null == bufferedContents) {
			cacheContents(new NullProgressMonitor());
		}
		if (bufferedContents instanceof IEncodedStorage) {
			final String charset = ((IEncodedStorage) bufferedContents).getCharset();
			return charset;
		}
		return null;
	}

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
							return StorageTypedElement.this.getDocumentKey(element);
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
	 * Method called from the shared document adapter to get the document key.
	 * 
	 * @param element
	 *            the element
	 * @return the document key
	 */
	protected abstract IEditorInput getDocumentKey(Object element);

	/**
	 * Method getLocalEncoding.
	 * 
	 * @return the encoding
	 */
	public String getLocalEncoding() {
		return localEncoding;
	}
}
