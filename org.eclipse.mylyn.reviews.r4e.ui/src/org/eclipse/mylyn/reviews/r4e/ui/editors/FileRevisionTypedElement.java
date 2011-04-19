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

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;

/**
 * An {@link ITypedElement} wrapper for {@link IFileRevision} for use with the
 * Compare framework.
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FileRevisionTypedElement extends StorageTypedElement {

	/**
	 * Field fileRevision.
	 */
	private IFileRevision fileRevision;

	/**
	 * Field author.
	 */
	private String author;

	/**
	 * @param fileRevision
	 *            the file revision
	 */
	public FileRevisionTypedElement(IFileRevision fileRevision) {
		this(fileRevision, null);
	}

	/**
	 * Method FileRevisionTypedElement
	 * @param fileRevision
	 *            the file revision
	 * @param localEncoding
	 *            the encoding of the local file that corresponds to the given
	 *            file revision
	 */
	public FileRevisionTypedElement(IFileRevision fileRevision, String localEncoding) {
		super(localEncoding);
		Assert.isNotNull(fileRevision);
		this.fileRevision = fileRevision;
	}

	/**
	 * Method getName.
	 * @return String
	 * @see org.eclipse.compare.ITypedElement#getName()
	 */
	public String getName() {
		return fileRevision.getName();
	}

	/**
	 * Method fetchContents.
	 * @param monitor IProgressMonitor
	 * @return IStorage
	 * @throws CoreException
	 */
	@Override
	protected IStorage fetchContents(IProgressMonitor monitor)
			throws CoreException {
		return fileRevision.getStorage(monitor);

	}

	/**
	
	 * @return String the string contains a unique content id */
	public String getContentIdentifier() {
		return fileRevision.getContentIdentifier();
	}

	/**
	
	 * @return the human readable timestamp of this element */
	public String getTimestamp() {
		final long date = fileRevision.getTimestamp();
		final Date dateFromLong = new Date(date);
		return DateFormat.getDateTimeInstance().format(dateFromLong);
	}

	/**
	 * Method getFileRevision
	 * @return the file revision of this element
	 */
	public IFileRevision getFileRevision() {
		return fileRevision;
	}

	/**
	 * Method getPath
	 * @return the human readable path of this element
	 */
	public String getPath() {
		final URI uri = fileRevision.getURI();
		if (null != uri) return uri.getPath();
		return getName();
	}

	/**
	 * Method getDocumentKey.
	 * @param element Object
	 * @return IEditorInput
	 */
	@Override
	public IEditorInput getDocumentKey(Object element) {
		if (element.equals(this) && null != getBufferedStorage()) {
			return new FileRevisionEditorInput(fileRevision,
					getBufferedStorage(), getLocalEncoding());
		}
		return null;
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
	 * Method equals.
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof FileRevisionTypedElement) {
			final FileRevisionTypedElement other = (FileRevisionTypedElement) obj;
			return other.getFileRevision().equals(getFileRevision());
		}
		return false;
	}

	/**
	 * Method getAuthor.
	 * @return the author
	 * */
	public String getAuthor() {
		if (null == author) author = fileRevision.getAuthor();
		return author;
	}

	/**
	 * Method setAuthor.
	 * @param author
	 *            the author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Method fetchAuthor.
	 * @param monitor
	 * @throws CoreException
	 */
	public void fetchAuthor(IProgressMonitor monitor) throws CoreException {
		if (null == getAuthor() && fileRevision.isPropertyMissing()) {
			final IFileRevision other = fileRevision.withAllProperties(monitor);
			author = other.getAuthor();
		}
	}

	/**
	 * Method getRevision.	
	 * @return the revision
	 */
	public IFileRevision getRevision() {
		return fileRevision;
	}
}
