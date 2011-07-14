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
package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import org.eclipse.compare.SharedDocumentAdapter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

/**
 * A shared document adapter that tracks whether the element is connected to a shared document and whether the contents
 * have been flushed from a compare viewer. When contents are flushed, this adapter will connect to the document
 * provider to ensure that the changes are not lost (see {@link #hasBufferedContents()}). In order to avoid a leak, the
 * buffer must either be saved (see {@link #saveDocument(IEditorInput, boolean, IProgressMonitor)}) or released (see
 * {@link #releaseBuffer()}).
 * <p>
 * This adapter must have a one-to-one correspondence to a typed element.
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
class EditableSharedDocumentAdapter extends SharedDocumentAdapter implements IElementStateListener {

	/**
	 * Field connectionCount.
	 */
	private int connectionCount;

	/**
	 * Field listener.
	 */
	private final ISharedDocumentAdapterListener listener;

	/**
	 * Field bufferedKey.
	 */
	private IEditorInput bufferedKey;

	/**
	 * Interface that provides this adapter with the state of the typed element and supports call backs to the element
	 * when the adapter state changes.
	 * 
	 * @author lmcdubo
	 */
	public interface ISharedDocumentAdapterListener {

		/**
		 * Method that is invoked when the adapter connects to the document provider. This method is only invoked when
		 * the adapter first connects to the document.
		 */
		void handleDocumentConnected();

		/**
		 * Method that is invoked when the adapter disconnects from the document provider. This method is only invoked
		 * when the adapter no longer has any connection to the document provider.
		 */
		void handleDocumentDisconnected();

		/**
		 * Method invoked when changes in the document are flushed to the adapter.
		 */
		void handleDocumentFlushed();

		/**
		 * Method invoked when the file behind the shared document is deleted.
		 */
		void handleDocumentDeleted();

		/**
		 * Method invoked when the document dirty state changes from dirty to clean.
		 */
		void handleDocumentSaved();
	}

	/**
	 * @param listener
	 *            access to element internals
	 */
	EditableSharedDocumentAdapter(ISharedDocumentAdapterListener listener) {
		super();
		this.listener = listener;
	}

	/**
	 * Method connect.
	 * 
	 * @param provider
	 *            IDocumentProvider
	 * @param documentKey
	 *            IEditorInput
	 * @throws CoreException
	 * @see org.eclipse.compare.ISharedDocumentAdapter#connect(IDocumentProvider, IEditorInput)
	 */
	@Override
	public void connect(IDocumentProvider provider, IEditorInput documentKey) throws CoreException {
		super.connect(provider, documentKey);
		connectionCount++;
		if (1 == connectionCount) {
			provider.addElementStateListener(this);
			listener.handleDocumentConnected();
		}
	}

	/**
	 * Method disconnect.
	 * 
	 * @param provider
	 *            IDocumentProvider
	 * @param documentKey
	 *            IEditorInput
	 * @see org.eclipse.compare.ISharedDocumentAdapter#disconnect(IDocumentProvider, IEditorInput)
	 */
	@Override
	public void disconnect(IDocumentProvider provider, IEditorInput documentKey) {
		try {
			super.disconnect(provider, documentKey);
		} finally {
			if (connectionCount > 0) {
				connectionCount--;
			}
			if (0 == connectionCount) {
				provider.removeElementStateListener(this);
				listener.handleDocumentDisconnected();
			}
		}
	}

	/**
	 * @return whether the element is connected to a shared document
	 */
	public boolean isConnected() {
		return connectionCount > 0;
	}

	/**
	 * @param input
	 *            the document key of the element.
	 * @param overwrite
	 *            indicates whether overwrite should be performed while saving the given element if necessary
	 * @param monitor
	 *            a progress monitor
	 * @return whether the save succeeded or not
	 * @throws CoreException
	 */
	public boolean saveDocument(IEditorInput input, boolean overwrite, IProgressMonitor monitor) throws CoreException {
		if (isConnected()) {
			final IDocumentProvider provider = SharedDocumentAdapter.getDocumentProvider(input);
			try {
				saveDocument(provider, input, provider.getDocument(input), overwrite, monitor);
			} finally {
				// When we write the document, remove out hold on the buffer
				releaseBuffer();
			}
			return true;
		}
		return false;
	}

	/**
	 * Release the buffer if this adapter has buffered the contents in response to a
	 * {@link #flushDocument(IDocumentProvider, IEditorInput, IDocument, boolean)} .
	 */
	public void releaseBuffer() {
		if (null != bufferedKey) {
			final IDocumentProvider provider = SharedDocumentAdapter.getDocumentProvider(bufferedKey);
			provider.disconnect(bufferedKey);
			bufferedKey = null;
		}
	}

	/**
	 * Method flushDocument.
	 * 
	 * @param provider
	 *            IDocumentProvider
	 * @param documentKey
	 *            IEditorInput
	 * @param document
	 *            IDocument
	 * @param overwrite
	 *            boolean
	 * @throws CoreException
	 * @see org.eclipse.compare.ISharedDocumentAdapter#flushDocument(IDocumentProvider, IEditorInput, IDocument,
	 *      boolean)
	 */
	public void flushDocument(IDocumentProvider provider, IEditorInput documentKey, IDocument document,
			boolean overwrite) throws CoreException {
		if (!hasBufferedContents()) {
			// On a flush, make an extra connection to the shared document so it
			// will be kept even
			// if it is no longer being viewed.
			bufferedKey = documentKey;
			provider.connect(bufferedKey);
		}
		listener.handleDocumentFlushed();
	}

	/**
	 * Method elementContentAboutToBeReplaced.
	 * 
	 * @param element
	 *            Object
	 * @see org.eclipse.ui.texteditor.IElementStateListener#elementContentAboutToBeReplaced(Object)
	 */
	public void elementContentAboutToBeReplaced(Object element) {
		// Nothing to do
	}

	/**
	 * Method elementContentReplaced.
	 * 
	 * @param element
	 *            Object
	 * @see org.eclipse.ui.texteditor.IElementStateListener#elementContentReplaced(Object)
	 */
	public void elementContentReplaced(Object element) {
		// Nothing to do
	}

	/**
	 * Method elementDeleted.
	 * 
	 * @param element
	 *            Object
	 * @see org.eclipse.ui.texteditor.IElementStateListener#elementDeleted(Object)
	 */
	public void elementDeleted(Object element) {
		listener.handleDocumentDeleted();
	}

	/**
	 * Method elementDirtyStateChanged.
	 * 
	 * @param element
	 *            Object
	 * @param isDirty
	 *            boolean
	 * @see org.eclipse.ui.texteditor.IElementStateListener#elementDirtyStateChanged(Object, boolean)
	 */
	public void elementDirtyStateChanged(Object element, boolean isDirty) {
		if (!isDirty) {
			listener.handleDocumentSaved();
		}
	}

	/**
	 * Method elementMoved.
	 * 
	 * @param originalElement
	 *            Object
	 * @param movedElement
	 *            Object
	 * @see org.eclipse.ui.texteditor.IElementStateListener#elementMoved(Object, Object)
	 */
	public void elementMoved(Object originalElement, Object movedElement) {
		// Nothing to do
	}

	/**
	 * Return whether the adapter has buffered contents. The adapter buffers contents by connecting to the document
	 * through the document provider. This means that the adapter must be disconnected either by saving or discarding
	 * the buffer.
	 * 
	 * @return whether the adapter has buffered contents
	 */
	public boolean hasBufferedContents() {
		return null != bufferedKey;
	}
}