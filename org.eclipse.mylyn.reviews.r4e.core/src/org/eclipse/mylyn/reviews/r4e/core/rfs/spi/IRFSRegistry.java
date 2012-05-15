package org.eclipse.mylyn.reviews.r4e.core.rfs.spi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.team.core.history.IFileRevision;

public interface IRFSRegistry {

	/**
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public abstract String registerReviewBlob(final byte[] content) throws ReviewsFileStorageException;

	/**
	 * Register a blob by copying existing file content
	 * 
	 * @param aFromFile
	 * @return
	 */
	public abstract String registerReviewBlob(final File aFromFile) throws ReviewsFileStorageException;

	/**
	 * @param monitor
	 * @param id
	 *            - Blob type is expected
	 * @return - The stream shall be closed by the receiver
	 */
	public abstract InputStream getBlobContent(IProgressMonitor monitor, String id)
			throws ReviewsFileStorageException;

	/**
	 * @param monitor
	 * @param fileVersion
	 *            - with populated localVersionId to an associated Blob type element
	 * @return - The IFileRevision in the local review repository associated with localVersionId
	 */
	public abstract IFileRevision getIFileRevision(IProgressMonitor monitor, R4EFileVersion fileVersion)
			throws ReviewsFileStorageException;

	/**
	 * @param monitor
	 * @param fileVersion
	 *            - with populated localVersionId to an associated Blob type element
	 * @return - The IStorage associated to the local review repository by the localVersionId
	 */
	public IStorage getIStorage(IProgressMonitor monitor, R4EFileVersion fileVersion);

	/**
	 * Used when the size of the stream is unknown, inputstream shall be closed by the caller
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public abstract String registerReviewBlob(final InputStream content) throws ReviewsFileStorageException;

	/**
	 * Calculate the SHA-1 id for the given input without registering the contents in the repository
	 * 
	 * @param contect
	 * @return
	 * @throws ReviewsFileStorageException
	 */
	public abstract String blobIdFor(final InputStream content) throws ReviewsFileStorageException;

	/**
	 * Calculate the SHA-1 id for the given input without registering the contents in the repository
	 * 
	 * @param contect
	 * @return
	 * @throws ReviewsFileStorageException
	 */
	public abstract String blobIdFor(final byte[] content) throws ReviewsFileStorageException;

	/**
	 * Calculate the SHA-1 id for the given input without registering the contents in the repository
	 * 
	 * @param contect
	 * @return
	 * @throws ReviewsFileStorageException
	 */
	public abstract String blobIdFor(final File content) throws ReviewsFileStorageException;

}