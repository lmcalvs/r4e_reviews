/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIHandler;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;
import org.eclipse.ui.IEditorInput;

/**
 * An {@link ITypedElement} wrapper for {@link IFileRevision} for use with R4E
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
//public class R4EFileTypedElement extends LocalResourceTypedElement implements IAdaptable, URIHandler {
public class R4EFileTypedElement extends LocalResourceTypedElement implements IAdaptable, URIHandler {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fFileVersion.
	 */
	private final R4EFileVersion fFileVersion;

	/**
	 * Field fDirty.
	 */
	private boolean fDirty = false;

	/**
	 * Field sharedDocumentAdapter.
	 */
	private EditableSharedDocumentAdapter sharedDocumentAdapter;

	/**
	 * Field timestamp.
	 */
	private long timestamp;

	/**
	 * Field exists.
	 */
	private boolean exists;

	/**
	 * Field useSharedDocument.
	 */
	private boolean useSharedDocument = true;

	/**
	 * Field sharedDocumentListener.
	 */
	private EditableSharedDocumentAdapter.ISharedDocumentAdapterListener sharedDocumentListener;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * @param aFileVersion
	 *            R4EFileVersion - the file revision
	 */
	public R4EFileTypedElement(R4EFileVersion aFileVersion) {
		super(aFileVersion.getResource());
		fFileVersion = aFileVersion;
		exists = aFileVersion.getResource().exists();
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
	 * Method setContent.
	 * 
	 * @param contents
	 *            byte[]
	 * @see org.eclipse.compare.IEditableContent#setContent(byte[])
	 */
	@Override
	public void setContent(byte[] contents) {
		fDirty = true;
		super.setContent(contents);
	}

	/**
	 * Commits buffered contents to the underlying resource. Note that if the element has a shared document, the commit
	 * will not succeed since the contents will be buffered in the shared document and will not be pushed to this
	 * element using {@link #setContent(byte[])}. Clients should check whether the element {@link #isConnected()} and,
	 * if it is, they should call {@link #saveDocument(boolean, IProgressMonitor)} to save the buffered contents to the
	 * underlying resource.
	 * 
	 * @param monitor
	 *            a progress monitor
	 * @throws CoreException
	 */
	@Override
	public void commit(IProgressMonitor monitor) throws CoreException {
		if (isDirty()) {
			if (isConnected()) {
				saveDocument(true, monitor);
			} else {
				final IResource resource = getResource();
				if (resource instanceof IFile) {
					ByteArrayInputStream is = null;
					try {
						is = new ByteArrayInputStream(getContent());
						final IFile file = (IFile) resource;
						if (file.exists()) {
							file.setContents(is, false, true, monitor);
						} else {
							file.create(is, false, monitor);
						}
						fDirty = false;
					} finally {
						fireContentChanged();
						if (null != is) {
							try {
								is.close();
							} catch (IOException ex) {
								// ignore
							}
						}
					}
				}
				updateTimestamp();
			}
		}
	}

	/**
	 * Method getContents.
	 * 
	 * @return InputStream
	 * @throws CoreException
	 * @see org.eclipse.compare.IStreamContentAccessor#getContents()
	 */
	@Override
	public InputStream getContents() throws CoreException {
		if (exists) {
			return super.getContents();
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
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (ISharedDocumentAdapter.class.equals(adapter)) {
			if (isSharedDocumentsEnable()) {
				return getSharedDocumentAdapter();
			} else {
				return null;
			}
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	/**
	 * Method getSharedDocumentAdapter.
	 * 
	 * @return ISharedDocumentAdapter
	 */
	private synchronized ISharedDocumentAdapter getSharedDocumentAdapter() {
		if (null == sharedDocumentAdapter) {
			sharedDocumentAdapter = new EditableSharedDocumentAdapter(
					new EditableSharedDocumentAdapter.ISharedDocumentAdapterListener() {
						public void handleDocumentConnected() {
							R4EFileTypedElement.this.updateTimestamp();
							if (null != sharedDocumentListener) {
								sharedDocumentListener.handleDocumentConnected();
							}
						}

						public void handleDocumentFlushed() {
							R4EFileTypedElement.this.fireContentChanged();
							if (null != sharedDocumentListener) {
								sharedDocumentListener.handleDocumentFlushed();
							}
						}

						public void handleDocumentDeleted() {
							R4EFileTypedElement.this.update();
							if (null != sharedDocumentListener) {
								sharedDocumentListener.handleDocumentDeleted();
							}
						}

						public void handleDocumentSaved() {
							R4EFileTypedElement.this.updateTimestamp();
							if (null != sharedDocumentListener) {
								sharedDocumentListener.handleDocumentSaved();
							}
						}

						public void handleDocumentDisconnected() {
							if (null != sharedDocumentListener) {
								sharedDocumentListener.handleDocumentDisconnected();
							}
						}
					});
		}
		return sharedDocumentAdapter;
	}

	/**
	 * Method isEditable.
	 * 
	 * @return boolean
	 * @see org.eclipse.compare.IEditableContent#isEditable()
	 */
	@Override
	public boolean isEditable() {
		// Do not allow non-existent files to be edited
		final IResource resource = getResource();
		return resource.getType() == IResource.FILE && exists;
	}

	/**
	 * @return whether the element is connected to a shared document<br>
	 *         When connected, the element can be saved using {@link #saveDocument(boolean, IProgressMonitor)}.
	 *         Otherwise, {@link #commit(IProgressMonitor)} should be used to save the buffered contents.
	 */
	@Override
	public boolean isConnected() {
		return null != sharedDocumentAdapter && sharedDocumentAdapter.isConnected();
	}

	/**
	 * @param overwrite
	 *            indicates whether overwrite should be performed while saving the given element if necessary
	 * @param monitor
	 *            a progress monitor
	 * @return whether the save succeeded or not <br>
	 *         The save can only be performed if the element is connected to a shared document. If the element is not
	 *         connected, <code>false</code> is returned (see {@link #isConnected()}).
	 * @throws CoreException
	 */
	@Override
	public boolean saveDocument(boolean overwrite, IProgressMonitor monitor) throws CoreException {
		if (isConnected()) {
			final IEditorInput input = sharedDocumentAdapter.getDocumentKey(this);
			sharedDocumentAdapter.saveDocument(input, overwrite, monitor);
			updateTimestamp();
			return true;
		}
		return false;
	}

	/**
	 * Method createStream.
	 * 
	 * @return InputStream
	 * @throws CoreException
	 */
	@Override
	protected InputStream createStream() throws CoreException {
		final InputStream inputStream = super.createStream();
		updateTimestamp();
		return inputStream;
	}

	/**
	 * Update the cached timestamp of the resource.
	 */
	void updateTimestamp() {
		if (getResource().exists()) {
			timestamp = getResource().getLocalTimeStamp();
		} else {
			exists = false;
		}
	}

	/**
	 * @return the cached timestamp of the resource
	 */
	private long getTimestamp() {
		return timestamp;
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		return getResource().hashCode();
	}

	/**
	 * Does not consider the content of the resources
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 * @see org.eclipse.compare.structuremergeviewer.IStructureComparator#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof R4EFileTypedElement) {
			final R4EFileTypedElement otherElement = (R4EFileTypedElement) obj;
			return otherElement.getResource().equals(getResource()) && exists == otherElement.exists;
		}
		return false;
	}

	/**
	 * Method called to update the state of this element when the compare input that contains this element is issuing a
	 * change event. It is not necessarily the case that the {@link #isSynchronized()} will return <code>true</code>
	 * after this call.
	 */
	@Override
	public void update() {
		exists = getResource().exists();
	}

	/**
	 * @return whether the contents provided by this typed element are in-sync with what is on disk <br>
	 *         Sill return <code>false</code> if the file has been changed externally since the last time the contents
	 *         were obtained or saved through this element.
	 */
	@Override
	public boolean isSynchronized() {
		final long current = getResource().getLocalTimeStamp();
		return current == getTimestamp();
	}

	/**
	 * @return whether the resource of this element existed at the last time the typed element was updated
	 */
	@Override
	public boolean exists() {
		return exists;
	}

	/**
	 * Discard of any buffered contents. This must be called when the local element is no longer needed but is dirty
	 * since a the element will connect to a shared document when a merge viewer flushes its contents to the element and
	 * it must be disconnected or the buffer will remain. #see {@link #isDirty()}
	 */
	@Override
	public void discardBuffer() {
		if (null != sharedDocumentAdapter) {
			sharedDocumentAdapter.releaseBuffer();
		}
		super.discardBuffer();
	}

	/**
	 * @return whether this element can use a shared document
	 */
	@Override
	public boolean isSharedDocumentsEnable() {
		return useSharedDocument && getResource().getType() == IResource.FILE && exists;
	}

	/**
	 * @param enablement
	 *            whether this element can use shared documents <br>
	 *            This will only apply to files (i.e. shared documents never apply to folders).
	 */
	@Override
	public void enableSharedDocument(boolean enablement) {
		useSharedDocument = enablement;
	}

	/**
	 * @return whether this element is dirty <br>
	 *         The element is dirty if a merge viewer has flushed it's contents to the element and the contents have not
	 *         been saved. * @see #commit(IProgressMonitor) * @see #saveDocument(boolean, IProgressMonitor) * @see
	 *         #discardBuffer()
	 */
	@Override
	public boolean isDirty() {
		return fDirty || (null != sharedDocumentAdapter && sharedDocumentAdapter.hasBufferedContents());
	}

	/**
	 * @param sharedDocumentListener
	 */
	public void setSharedDocumentListener(
			EditableSharedDocumentAdapter.ISharedDocumentAdapterListener sharedDocumentListener) {
		this.sharedDocumentListener = sharedDocumentListener;
	}

	//
	//
	//	Method to handle the URIHandlers
	//
	//
	public boolean canHandle(URI uri) {
		// ignore
		return false;
	}

	public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException {
		// ignore
		try {
			return createStream();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException {
		// ignore
		return null;
	}

	public void delete(URI uri, Map<?, ?> options) throws IOException {
		// ignore

	}

	public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException {
		// ignore

		return null;
	}

	public boolean exists(URI uri, Map<?, ?> options) {
		// ignore
		return false;
	}

	public Map<String, ?> getAttributes(URI uri, Map<?, ?> options) {
		// ignore
		return null;
	}

	public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException {
		// ignore

	}
}
