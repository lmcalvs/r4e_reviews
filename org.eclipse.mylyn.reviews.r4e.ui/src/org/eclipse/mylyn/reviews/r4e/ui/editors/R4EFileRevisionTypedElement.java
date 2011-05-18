/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 * Ericsson (Sebastien Dubois) - Adapted to use with R4E
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.editors;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.utils.CommandUtils;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;

/**
 * An {@link ITypedElement} wrapper for {@link IFileRevision} for use with R4E
 * 
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EFileRevisionTypedElement extends StorageTypedElement {

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
	protected IStorage fetchContents(IProgressMonitor aMonitor) throws CoreException {
		if (CommandUtils.useWorkspaceResource(fFileVersion)) {
			return (IFile) fFileVersion.getResource();
		}
		return fFileVersion.getFileRevision().getStorage(aMonitor);
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
		final URI uri = fFileVersion.getFileRevision().getURI();
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
		if (aElement.equals(this)) {
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
}
