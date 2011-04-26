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
package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.utils.CommandUtils;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * An Editor input for file revisions
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FileRevisionEditorInput extends PlatformObject implements IStorageEditorInput {

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
	 *            the R4E file version
	 * @param aStorage
	 *            the contents of the file revision
	 */
	public FileRevisionEditorInput(R4EFileVersion aFileVersion) {
		Assert.isNotNull(aFileVersion);
		fFileVersion = aFileVersion;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * @param aFileVersion R4EFileVersion - the file revision
	 * @param aMonitor IProgressMonitor
	 * @return a file revision editor input
	 * @throws CoreException
	 */
	public static FileRevisionEditorInput createEditorInputFor(R4EFileVersion aFileVersion, IProgressMonitor aMonitor)
			throws CoreException {
		return new FileRevisionEditorInput(aFileVersion);
	}
	
	/**
	 * Method getStorage.
	 * @return IStorage
	 * @see org.eclipse.ui.IStorageEditorInput#getStorage()
	 */
	public IStorage getStorage() {
		if (CommandUtils.useWorkspaceResource(fFileVersion)) {
			return ((IFile)fFileVersion.getResource());
		}
		try {
			return fFileVersion.getFileRevision().getStorage(null);
		} catch (CoreException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
		return null;
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
		if (CommandUtils.useWorkspaceResource(fFileVersion)) {
			return fFileVersion.getResource().getName();
		}
		try {
			return fFileVersion.getFileRevision().getStorage(null).getName();
		} catch (CoreException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
		return null;
	}

	/**
	 * Method getPersistable.
	 * @return IPersistableElement
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;   // can't persist
	}

	/**
	 * Method getToolTipText.
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		if (CommandUtils.useWorkspaceResource(fFileVersion)) {
			return fFileVersion.getResource().getFullPath().makeRelative().toString();
		}
		try {
			return fFileVersion.getFileRevision().getStorage(null).getFullPath().toString();
		} catch (CoreException e) {
			Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
		}
		return null;
	}

	/**
	 * Method getAdapter.
	 * @param aAdapter Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class aAdapter) {
		
		if (IFile.class.equals(aAdapter)) {
			if (CommandUtils.useWorkspaceResource(fFileVersion)) {
				return fFileVersion.getResource();
			}
		}
		if (IStorage.class.equals(aAdapter)) {
			try {
				return fFileVersion.getFileRevision().getStorage(null);
			} catch (CoreException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
			}
		}
		
		if (IWorkbenchAdapter.class.equals(aAdapter)) {
			return new IWorkbenchAdapter() {

				public Object[] getChildren(Object o) {
					return new Object[0];
				}

				public ImageDescriptor getImageDescriptor(Object object) {
					return FileRevisionEditorInput.this.getImageDescriptor();
				}

				public String getLabel(Object o) {
					return FileRevisionEditorInput.this.getName();
				}

				public Object getParent(Object o) {
					return FileRevisionEditorInput.this.getFile().getParent();
				}
			};
		}
		
		return super.getAdapter(aAdapter);
	}

	/**
	 * Method equals.
	 * @param aObject Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object aObject) {
		if (aObject == this) return true;
		if (aObject instanceof FileRevisionEditorInput) {
			final FileRevisionEditorInput other = (FileRevisionEditorInput) aObject;
			return other.fFileVersion.equals(this.fFileVersion);
		}
		return false;
	}

	/**
	 * Method hashCode.
	 * @return int
	 */
	@Override
	public int hashCode() {
		if (CommandUtils.useWorkspaceResource(fFileVersion)) {
			return fFileVersion.getResource().hashCode();
		}
		return fFileVersion.getFileRevision().hashCode();
	}

	/**
	 * Method getFileVersion.
	 * @return IFileRevision - the revision
	 */
	public R4EFileVersion getFileVersion() {
		return fFileVersion;
	}

	/**
	 * Method getURI.
	 * @return URI - the URI
	 */
	public URI getURI() {
		return fFileVersion.getFileRevision().getURI();
	}

	/**
	 * Method getFile.
	 * @return IFile
	 */
	public IFile getFile() {
		if (CommandUtils.useWorkspaceResource(fFileVersion)) {
			return (IFile) fFileVersion.getResource();
		}
		return null;
	}
}
