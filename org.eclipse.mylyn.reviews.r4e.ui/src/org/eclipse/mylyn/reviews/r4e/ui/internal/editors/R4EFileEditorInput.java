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
 * This class is used as R4E editor input from contents present in the local 
 * workspace
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * An Editor input for file revisions
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EFileEditorInput extends PlatformObject implements IFileEditorInput {

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
	public R4EFileEditorInput(R4EFileVersion aFileVersion) {
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
		return (IFile) fFileVersion.getResource();
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
		if (null != fFileVersion.getResource()) {
			return fFileVersion.getResource().getName();
		}
		return null;
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
		return fFileVersion.getResource().getFullPath().makeRelative().toString();
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

		if (IFile.class.equals(aAdapter)) {
			if (null != fFileVersion.getResource()) {
				return fFileVersion.getResource();
			}
		}
		if (IWorkbenchAdapter.class.equals(aAdapter)) {
			return new IWorkbenchAdapter() {

				public Object[] getChildren(Object o) {
					return new Object[0];
				}

				public ImageDescriptor getImageDescriptor(Object object) {
					return R4EFileEditorInput.this.getImageDescriptor();
				}

				public String getLabel(Object o) {
					return R4EFileEditorInput.this.getName();
				}

				public Object getParent(Object o) {
					return R4EFileEditorInput.this.getFile().getParent();
				}
			};
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
		if (aObject instanceof R4EFileEditorInput) {
			final R4EFileEditorInput other = (R4EFileEditorInput) aObject;
			return other.fFileVersion.getResource().equals(this.fFileVersion.getResource());
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
		return fFileVersion.getResource().hashCode();
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
	 * Method getFile.
	 * 
	 * @return IFile
	 */
	public IFile getFile() {
		return (IFile) fFileVersion.getResource();
	}
}
