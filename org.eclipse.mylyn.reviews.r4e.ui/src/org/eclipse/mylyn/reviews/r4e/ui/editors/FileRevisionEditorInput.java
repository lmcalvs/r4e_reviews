/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.io.InputStream;
import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.core.resources.IEncodedStorage;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * An Editor input for file revisions
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FileRevisionEditorInput extends PlatformObject implements
		IWorkbenchAdapter, IStorageEditorInput {

	/**
	 * Field fileRevision.
	 */
	private final Object fileRevision;

	/**
	 * Field storage.
	 */
	private final IStorage storage;

	/**
	 * @param revision IFileRevision - the file revision
	 * @param monitor IProgressMonitor
	 * @return a file revision editor input
	 * @throws CoreException
	 */
	public static FileRevisionEditorInput createEditorInputFor(
			IFileRevision revision, IProgressMonitor monitor)
			throws CoreException {
		final IStorage storage = revision.getStorage(monitor);
		return new FileRevisionEditorInput(revision, storage);
	}

	/**
	 * Method wrapStorage.
	 * @param storage IStorage
	 * @param charset String
	 * @return IStorage
	 */
	private static IStorage wrapStorage(final IStorage storage,
			final String charset) {
		if (null == charset) return storage;
		if (storage instanceof IFileState) {
			return new IFileState() {
				public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
					return storage.getAdapter(adapter);
				}

				public boolean isReadOnly() {
					return storage.isReadOnly();
				}

				public String getName() {
					return storage.getName();
				}

				public IPath getFullPath() {
					return storage.getFullPath();
				}

				public InputStream getContents() throws CoreException {
					return storage.getContents();
				}

				public String getCharset() {
					return charset;
				}

				public boolean exists() {
					return ((IFileState) storage).exists();
				}

				public long getModificationTime() {
					return ((IFileState) storage).getModificationTime();
				}
			};
		}

		return new IEncodedStorage() {
			public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
				return storage.getAdapter(adapter);
			}

			public boolean isReadOnly() {
				return storage.isReadOnly();
			}

			public String getName() {
				return storage.getName();
			}

			public IPath getFullPath() {
				return storage.getFullPath();
			}

			public InputStream getContents() throws CoreException {
				return storage.getContents();
			}

			public String getCharset() {
				return charset;
			}
		};
	}

	/**
	 * @param revision
	 *            the file revision
	 * @param storage
	 *            the contents of the file revision
	 */
	public FileRevisionEditorInput(Object revision, IStorage storage) {
		Assert.isNotNull(revision);
		Assert.isNotNull(storage);
		this.fileRevision = revision;
		this.storage = storage;
	}

	/**
	 * @param state
	 *            the file state
	 */
	public FileRevisionEditorInput(IFileState state) {
		this(state, state);
	}

	/**
	 * @param revision
	 * @param storage
	 * @param charset
	 */
	public FileRevisionEditorInput(Object revision, IStorage storage,
			String charset) {
		this(revision, wrapStorage(storage, charset));
	}

	/**
	 * Method getStorage.
	 * @return IStorage
	 * @see org.eclipse.ui.IStorageEditorInput#getStorage()
	 */
	public IStorage getStorage() {
		return storage;
	}

	/**
	 * Method exists.
	 * @return boolean
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return true;
	}

	/**
	 * Method getImageDescriptor.
	 * @return ImageDescriptor
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 * Method getName.
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		final IFileState state = (IFileState) getAdapter(IFileState.class);
		if (null != state) {
			return state.getName() + " " + DateFormat.getInstance().format(new Date(state.getModificationTime()));
		}
		return storage.getName();

	}

	/**
	 * Method getPersistable.
	 * @return IPersistableElement
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		// can't persist
		return null;
	}

	/**
	 * Method getToolTipText.
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return storage.getFullPath().toString();
	}

	/**
	 * Method getAdapter.
	 * @param adapter Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IWorkbenchAdapter.class.equals(adapter)) return this;
		if (IStorage.class.equals(adapter)) return storage;
		
		final Object object = super.getAdapter(adapter);
		if (null != object) return object;
		if (adapter.isInstance(fileRevision)) return fileRevision;
		if (fileRevision instanceof IAdaptable) {
			final Object adapted = ((IAdaptable) fileRevision).getAdapter(adapter);
			if (adapter.isInstance(adapted)) return adapted;
		}
		final Object adapted = Platform.getAdapterManager().getAdapter(fileRevision, adapter);
		if (adapter.isInstance(adapted)) return adapted;
		return null;
	}

	/**
	 * Method getChildren.
	 * @param o Object
	 * @return Object[]
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(Object)
	 */
	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	/**
	 * Method getImageDescriptor.
	 * @param object Object
	 * @return ImageDescriptor
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(Object)
	 */
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	/**
	 * Method getLabel.
	 * @param o Object
	 * @return String
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(Object)
	 */
	public String getLabel(Object o) {
		final IFileRevision rev = (IFileRevision) getAdapter(IFileRevision.class);
		if (null != rev) return rev.getName();
		return storage.getName();
	}

	/**
	 * Method getParent.
	 * @param o Object
	 * @return Object
	 * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(Object)
	 */
	public Object getParent(Object o) {
		return null;
	}

	/**
	 * Method equals.
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;

		if (obj instanceof FileRevisionEditorInput) {
			final FileRevisionEditorInput other = (FileRevisionEditorInput) obj;
			return other.fileRevision.equals(this.fileRevision);
		}
		return false;
	}

	/**
	 * Method hashCode.
	 * @return int
	 */
	@Override
	public int hashCode() {
		return fileRevision.hashCode();
	}

	/**
	 * @return IFileRevision - the revision
	 */
	public IFileRevision getFileRevision() {
		if (fileRevision instanceof IFileRevision) {
			return (IFileRevision) fileRevision;
		}
		return null;
	}

	/**
	 * @return URI - the URI
	 */
	public URI getURI() {
		if (fileRevision instanceof IFileRevision) {
			final IFileRevision fr = (IFileRevision) fileRevision;
			return fr.getURI();
		}
		return null;
	}
}
