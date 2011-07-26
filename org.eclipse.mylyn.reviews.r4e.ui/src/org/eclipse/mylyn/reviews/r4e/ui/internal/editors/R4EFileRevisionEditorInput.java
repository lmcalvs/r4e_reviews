/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * Ericsson (Sebastien Dubois) - Adapted to use with R4E
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.net.URI;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * An Editor input for file revisions
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EFileRevisionEditorInput extends PlatformObject implements IWorkbenchAdapter, IStorageEditorInput {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fVersion
	 */
	private final R4EFileVersion fFileVersion;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * @param aFileVersion
	 *            - the R4E file version
	 */
	public R4EFileRevisionEditorInput(R4EFileVersion aFileVersion) {
		Assert.isNotNull(aFileVersion);
		fFileVersion = aFileVersion;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getStorage.
	 * 
	 * @return IStorage
	 * @see org.eclipse.ui.IStorageEditorInput#getStorage()
	 */
	public IStorage getStorage() {
		try {
			if (null != fFileVersion.getFileRevision()) {
				return fFileVersion.getFileRevision().getStorage(null);
			}
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		}
		return null;
	}

	/**
	 * Method exists.
	 * 
	 * @return boolean
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return true;
	}

	/**
	 * Method getImageDescriptor.
	 * 
	 * @return ImageDescriptor
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	/**
	 * Method getName.
	 * 
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return fFileVersion.getName();
	}

	/**
	 * Method getPersistable.
	 * 
	 * @return IPersistableElement
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null; // can't persist
	}

	/**
	 * Method getToolTipText.
	 * 
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		try {
			if (null != fFileVersion.getFileRevision()) {
				return fFileVersion.getFileRevision().getStorage(null).getFullPath().toString();
			}
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		}
		return null;
	}

	/**
	 * Method getAdapter.
	 * 
	 * @param aAdapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class aAdapter) {

		if (IWorkbenchAdapter.class.equals(aAdapter)) {
			return this;
		}
		if (IStorage.class.equals(aAdapter)) {
			try {
				return fFileVersion.getFileRevision().getStorage(null);
			} catch (CoreException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			}
		}
		return super.getAdapter(aAdapter);
	}

	/**
	 * Method equals.
	 * 
	 * @param aObject
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object aObject) {
		if (aObject == this) {
			return true;
		}
		if (aObject instanceof R4EFileRevisionEditorInput) {
			final R4EFileRevisionEditorInput other = (R4EFileRevisionEditorInput) aObject;
			return other.fFileVersion.equals(fFileVersion);
		}
		return false;
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		if (null != fFileVersion.getFileRevision()) {
			return fFileVersion.getFileRevision().hashCode();
		}
		return fFileVersion.hashCode();
	}

	/**
	 * Method getFileVersion.
	 * 
	 * @return IFileRevision - the revision
	 */
	public R4EFileVersion getFileVersion() {
		return fFileVersion;
	}

	/**
	 * Method getURI.
	 * 
	 * @return URI - the URI
	 */
	public URI getURI() {
		if (null != fFileVersion.getFileRevision()) {
			return fFileVersion.getFileRevision().getURI();
		}
		return null;
	}

	/**
	 * Method getChildren.
	 * 
	 * @param o
	 *            Object
	 * @return Object[]
	 */
	public Object[] getChildren(Object o) {
		return new Object[0];
	}

	/**
	 * Method getImageDescriptor.
	 * 
	 * @param object
	 *            Object
	 * @return ImageDescriptor
	 */
	public ImageDescriptor getImageDescriptor(Object object) {
		return null;
	}

	/**
	 * Method getLabel.
	 * 
	 * @param o
	 *            Object
	 * @return String
	 */
	public String getLabel(Object o) {
		return fFileVersion.getFileRevision().getName();
	}

	/**
	 * Method getParent.
	 * 
	 * @param o
	 *            Object
	 * @return Object
	 */
	public Object getParent(Object o) {
		return null;
	}
}
