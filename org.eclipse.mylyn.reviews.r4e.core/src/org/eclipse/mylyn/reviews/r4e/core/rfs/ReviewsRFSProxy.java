/*******************************************************************************
 * Copyright (c) 2011 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.rfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.util.FS;
import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.FileSupportCommandFactory;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;

/**
 * @author lmcalvs
 *
 */
public class ReviewsRFSProxy implements IRFSRegistry {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	private static final String	repoName	= "R4EBareRepo.git";
	private ObjectInserter		fInserter	= null;
	private Repository			fRepository	= null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	/**
	 * @param aParentDir
	 * @throws IOException
	 */
	public ReviewsRFSProxy(File aReviewGroupDir, boolean create) throws ReviewsFileStorageException {
		File repoLoc = new File(aReviewGroupDir, repoName);
		if (create) {
			fRepository = initializeRepo(repoLoc);
		} else {
			fRepository = openRepository(repoLoc);
		}

		fInserter = fRepository.newObjectInserter();
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @param aReviewGroupDir
	 * @return
	 * @throws ReviewVersionsException
	 */
	private Repository initializeRepo(File aReviewGroupDir) throws ReviewsFileStorageException {
		try {
			Repository newRepo = new FileRepository(aReviewGroupDir);
			newRepo.create(true);
			newRepo.getConfig().setString("core", null, "sharedrepository", "0666");
			newRepo.getConfig().save();

			return newRepo;
		} catch (IOException e) {
			throw new ReviewsFileStorageException(e);
		}
	}

	/**
	 * @param aReviewGroupDir
	 * @return
	 * @throws ReviewVersionsException
	 */
	private Repository openRepository(File aReviewGroupDir) throws ReviewsFileStorageException {
		Repository r;
		try {
			r = FileKey.exact(aReviewGroupDir, FS.DETECTED).open(true);
		} catch (IOException e) {
			throw new ReviewsFileStorageException(e);
		}
		return r;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.rrepo.IBlobRegistry#registerReviewBlob(byte[])
	 */
	public ObjectId registerReviewBlob(final byte[] content) throws Exception {
		ObjectId id;

		try {
			id = fInserter.insert(Constants.OBJ_BLOB, content);
			fInserter.flush();
			FileSupportCommandFactory.getInstance().grantWritePermission(fRepository.getDirectory().getAbsolutePath());
		} finally {
			fInserter.release();
		}

		return id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.rrepo.IBlobRegistry#registerReviewBlob(java.io.File)
	 */
	public ObjectId registerReviewBlob(final File aFromFile) throws ReviewsFileStorageException {
		InputStream stream = null;
		try {
			stream = new FileInputStream(aFromFile);
		} catch (FileNotFoundException e) {
			throw new ReviewsFileStorageException(e);
		}

		ObjectId id;
		try {
			id = fInserter.insert(Constants.OBJ_BLOB, aFromFile.length(), stream);
			fInserter.flush();
			FileSupportCommandFactory.getInstance().grantWritePermission(fRepository.getDirectory().getAbsolutePath());
		} catch (IOException e) {
			throw new ReviewsFileStorageException(e);
		} finally {
			fInserter.release();
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					StringBuilder sb = new StringBuilder("Exception: " + e.getMessage());
					Activator.fTracer.traceDebug(sb.toString());
				}
			}
		}

		return id;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.rrepo.IBlobRegistry#getBlobContent(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.jgit.lib.ObjectId)
	 */
	public InputStream getBlobContent(IProgressMonitor monitor, ObjectId id) throws ReviewsFileStorageException {
		InputStream resStream = null;

		try {
			resStream = fRepository.open(id, Constants.OBJ_BLOB).openStream();
		} catch (Exception e) {
			throw new ReviewsFileStorageException(e);
		}

		return resStream;
	}

	/**
	 * @return
	 */
	public Repository getRepository() {
		return fRepository;
	}

	/**
	 * Convenience method to close the repository data base
	 */
	public void close() {
		fRepository.close();
	}

}
