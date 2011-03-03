package org.eclipse.mylyn.reviews.r4e.core.rfs.spi;

import java.io.File;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;

public interface IRFSRegistry {

	/**
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public abstract ObjectId registerReviewBlob(final byte[] content) throws Exception;

	/**
	 * Register a blob by copying existing file content
	 * 
	 * @param aFromFile
	 * @return
	 * @throws ReviewVersionsException
	 */
	public abstract ObjectId registerReviewBlob(final File aFromFile) throws ReviewsFileStorageException;

	/**
	 * @param monitor
	 * @param id
	 *            - Blob type is expected
	 * @return - The stream shall be closed by the receiver
	 * @throws ReviewVersionsException
	 */
	public abstract InputStream getBlobContent(IProgressMonitor monitor, ObjectId id)
			throws ReviewsFileStorageException;

}