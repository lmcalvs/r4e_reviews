/*******************************************************************************
 * Copyright (c) 2010 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Initial API and Implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.versions.git;

import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.GitProvider;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException.VersionsExceptionCode;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.GitDiffUtils;
import org.eclipse.team.core.RepositoryProvider;

/**
 * @author lmcalvs
 *
 */
/**
 * @author lmcalvs
 * 
 */
public class ReviewsGITVersionsIFImpl implements ReviewsVersionsIF {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private final Map<String, CompareSessionInfo>	fSessionIndex	= new HashMap<String, CompareSessionInfo>();
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#isProviderSupported(org.eclipse.core.resources.IProject)
	 */
	public boolean isProviderSupported(IProject project) {
		return isGitRepository(project);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#getLastCommitInfo(org.eclipse.core.resources.IProject)
	 */
	public CommitDescriptor getLastCommitInfo(IProject project) throws ReviewVersionsException {
		if (project == null) {
			return null;
		}
		Repository repo = getRepository(project);
		RevCommit dCommit = resolveLastCommit(repo);
		CommitDescriptor descriptor = new GitCommitDescriptorImpl(dCommit, project, repo);

		return descriptor;
	}

	/**
	 * @param project
	 * @return
	 * @throws CoreException
	 */
	public List<String> getCommitIds(IProject project) throws ReviewVersionsException {
		if (project == null) {
			return null;
		}

		Repository repo = getRepository(project);
		List<String> commits = new ArrayList<String>();

		Git git = new Git(repo);

		Iterable<RevCommit> revs = null;
		try {
			revs = git.log().call();
		} catch (NoHeadException e) {
			throw new ReviewVersionsException(e);
		} catch (JGitInternalException e) {
			throw new ReviewVersionsException(e);
		}

		for (RevCommit r : revs) {
			commits.add(r.getName());
		}

		return commits;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#getCommitInfo(org.eclipse.core.resources.IProject, java.lang.String)
	 */
	public CommitDescriptor getCommitInfo(IProject project, String commitId) throws ReviewVersionsException {
		if (project == null || commitId == null) {
			return null;
		}

		// resolve id
		ObjectId obid = ObjectId.fromString(commitId);
		Repository repo = getRepository(project);
		RevCommit dCommit = resolveCommit(repo, obid);

		CommitDescriptor descriptor = new GitCommitDescriptorImpl(dCommit, project, repo);

		return descriptor;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#getFileVersionInfo(org.eclipse.core.resources.IFile)
	 */
	public FileVersionInfo getFileVersionInfo(IFile workspaceFile) throws ReviewVersionsException {
		FileVersionInfo info = null;
		// resolve repository
		RepositoryMapping repoMap = RepositoryMapping.getMapping(workspaceFile);
		if (repoMap == null || repoMap.getRepository() == null) {
			return null;
		}

		String resPath = repoMap.getRepoRelativePath(workspaceFile);

		URI resUri = URI.createURI(resPath);
		Repository repo = repoMap.getRepository();
		RevCommit commit = resolveLastCommit(repo);
		ObjectId id = findObjectId(repo, resPath, commit);
		info = new GitFileVersionInfoImpl(resPath, id.getName(), resUri.lastSegment());
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#createCommitReviewItem(org.eclipse.core.resources
	 * .IProject, java.lang.String, org.eclipse.mylyn.reviews.r4e.core.model.R4EUser)
	 */
	public R4EItem createCommitReviewItem(IProject project, String commitId, R4EUser reviewUser)
			throws ReviewVersionsException {
		ObjectId commitID = ObjectId.fromString(commitId);
		Repository repo = getRepository(project);
		DiffContext[] changeSet = getChangeSet(repo, project, commitID);


		// nothing to return
		if (changeSet == null || changeSet.length == 0) {
			return null;
		}

		R4EItem commitRevItem;
		try {
			// File repoDir = getRepoDirectory(repo.getDirectory());
			File dir = repo.getWorkTree().getAbsoluteFile();
			commitRevItem = createReviewItem(changeSet, project, dir, reviewUser);
		} catch (Exception e) {
			throw new ReviewVersionsException(e);
		}
		return commitRevItem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#getBlobById(org.eclipse.core.resources.IProject,
	 * java.lang.String)
	 */
	public InputStream getBlobById(IProject project, String id) throws ReviewVersionsException {
		InputStream resStream = null;
		ObjectId objId = ObjectId.fromString(id);
		Repository repo = getRepository(project);

		try {
			objId = ObjectId.fromString(id);
			resStream = repo.open(objId, Constants.OBJ_BLOB).openStream();
		} catch (Exception e) {
			throw new ReviewVersionsException(e);
		}

		return resStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#openCompareSession(org.eclipse.mylyn.reviews.r4e
	 * .core.model.R4EItem)
	 */
	public String openCompareSession(R4EItem item) throws ReviewVersionsException {
		// Resolve Projects
		List<String> projectURIs = item.getProjectURIs();
		if (projectURIs == null || projectURIs.size() < 1) {
			StringBuilder sb = new StringBuilder(
					"No projects associated to the review item i.e. unable to resolve repository");
			ReviewVersionsException exc = new ReviewVersionsException(sb.toString());
			throw exc;
		}

		List<IProject> projects = resolveProjects(projectURIs);
		// TODO: Long term, implement support for multiple projects associated to the same commit review item
		// resolve the Repo, Assuming all projects associated to the review time shall belong to the same repo
		IProject project = projects.get(0);
		Repository repo = getRepository(project);
		ObjectId commitObId = ObjectId.fromString(item.getRepositoryRef());
		// Resolve the changeSet
		DiffContext[] diffs = getChangeSet(repo, project, commitObId);
		Map<String, DiffContext> indexedDiffs = indexDiffs(diffs);
		// Create session information
		String bookingId = EcoreUtil.generateUUID();
		CompareSessionInfo sessionContainer = new CompareSessionInfo(repo, indexedDiffs);
		// save session
		fSessionIndex.put(bookingId, sessionContainer);

		return bookingId;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#openCompareEditor(java.lang.String, org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext)
	 */
	public void openCompareEditor(String aSession, R4EFileContext aContext) throws ReviewVersionsException {
		// validate
		if (aSession == null || aContext == null) {
			Activator.fTracer.traceError("Invalid null argument(s) received");
			return;
		}

		CompareSessionInfo session = fSessionIndex.get(aSession);
		if (session == null) {
			ReviewVersionsException exc = new ReviewVersionsException("Session not found: " + aSession);
			throw exc;
		}

		// Read received Context data
		String targetSHAIndx = aContext.getTarget().getVersionID();
		if (targetSHAIndx == null) {
			ReviewVersionsException exc = new ReviewVersionsException(
					"Unable to resolve target file received in compare Context i.e. empty id");
			throw exc;
		}

		// Read session Data
		Repository repo = session.getRepo();
		Map<String, DiffContext> indexedDiffs = session.getIndexedDiffs();

		// Resolve to a diff
		DiffContext ddiff = indexedDiffs.get(targetSHAIndx);
		if (ddiff == null) {
			ReviewVersionsException exc = new ReviewVersionsException("Unable to provide target file id: "
					+ targetSHAIndx + ", to a valid diff");
			throw exc;
		}

		GitDiffUtils.showTwoWayFileDiff(ddiff, repo);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF#closeCompareSession(java.lang.String)
	 */
	public void closeCompareSession(String session) {
		if (session == null) {
			return;
		}

		fSessionIndex.remove(session);
	}

	/**
	 * @param resource
	 * @return
	 */
	private Repository getRepository(IResource resource) {
		RepositoryMapping repoMap = RepositoryMapping.getMapping(resource);
		return repoMap.getRepository();
	}

	/**
	 * @param repo
	 * @param path
	 * @param commit
	 * @return
	 * @throws CoreException
	 * @throws ReviewVersionsException
	 */
	private ObjectId findObjectId(Repository repo, String path, RevCommit commit) throws ReviewVersionsException {
		try {
			final TreeWalk tWalk = TreeWalk.forPath(repo, path, commit.getTree());
			if (tWalk == null) {
				StringBuilder sb = new StringBuilder("Unable to resolve repository tree for commit id: "
						+ commit.getId().name() + ", path: " + path);
				ReviewVersionsException exc = new ReviewVersionsException(sb.toString());
				exc.setExceptionCode(VersionsExceptionCode.INVALID_ARGS);
				throw exc;
			}
			return tWalk.getObjectId(0);
		} catch (IOException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.IO);
			throw exc;
		}
	}

	/**
	 * @param repo
	 * @return
	 * @throws ReviewVersionsException
	 */
	private RevCommit resolveLastCommit(Repository repo) throws ReviewVersionsException {
		try {
			return resolveCommit(repo, repo.resolve(HEAD));
		} catch (AmbiguousObjectException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.AMBIGUOS_OBJECT);
			throw exc;
		} catch (IOException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.IO);
			throw exc;
		}
	}

	/**
	 * @param repo
	 * @param obId
	 * @return
	 * @throws ReviewVersionsException
	 */
	private RevCommit resolveCommit(Repository repo, ObjectId obId) throws ReviewVersionsException {
		RevWalk commitWalk = new RevWalk(repo);
		RevCommit dCommit;
		try {
			dCommit = commitWalk.parseCommit(obId);
		} catch (MissingObjectException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.MISSING_OBJECT);
			throw exc;
		} catch (IncorrectObjectTypeException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.INCORRECT_OBJECT_TYPE);
			throw exc;
		} catch (IOException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.IO);
			throw exc;
		}
		return dCommit;
	}

	/**
	 * @param changeSet
	 * @param project
	 * @param repoDir
	 * @param reviewUser
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ReviewVersionsException
	 */
	private R4EItem createReviewItem(DiffContext[] changeSet, IProject project, File repoDir, R4EUser reviewUser)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, ReviewVersionsException {
		if (changeSet == null || changeSet.length < 1) {
			return null;
		}
	
		String projectURI = ResourceUtils.toPlatformURIStr(project);

		R4EItem revItem = RModelFactory.eINSTANCE.createR4EItem();
		RevCommit commit = changeSet[0].getCommit();
		revItem.setAddedBy(reviewUser);
		revItem.setAddedById(reviewUser.getId());
		revItem.setDescription(commit.getFullMessage());
		revItem.setRepositoryRef(commit.getName());
		revItem.getProjectURIs().add(projectURI);
		
		List<String> unresolvedFiles = new ArrayList<String>();

		// define path to repo working area
		// NOTE: Necessary to include the file:// scheme and in portable format
		IPath repoWorkAreaPath = new Path(repoDir.getAbsolutePath()).addTrailingSeparator();
		URI workAreaURI = URI.createFileURI(repoWorkAreaPath.toPortableString());

		for (int i = 0; i < changeSet.length; i++) {
			DiffContext diffContext = changeSet[i];

			// resolve the expected absolute path to the file in the commit
			URI fileRepoPath = URI.createURI(diffContext.getPath());
			URI absURI = fileRepoPath.resolve(workAreaURI, true);
			java.net.URI absPath = java.net.URI.create(absURI.toString());

			IFile ifile = ResourceUtils.getWorkSpaceFile(absPath, project);
			if (ifile == null) {
				unresolvedFiles.add(absPath.toString());
				Activator.fTracer.traceDebug("Unable to resolve file to the workspace: " + absPath);
				continue;
			}

			String name = absURI.lastSegment();
			String platformURI = ResourceUtils.toPlatformURIStr(ifile);
	
			// Context
			R4EFileContext rContext = RModelFactory.eINSTANCE.createR4EFileContext();
			// associate to parent item
			revItem.getFileContextList().add(rContext);
			// create base
			R4EFileVersion baseVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
			// create target
			R4EFileVersion targetVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
			// associate base and target to context
			rContext.setBase(baseVersion);
			rContext.setTarget(targetVersion);
	
			// Base version
			baseVersion.setName(name);
			baseVersion.setRepositoryPath(fileRepoPath.toString());
			baseVersion.setVersionID(diffContext.getBlobs()[0].getName());
			// invalid for target for the time being, needs to consider renaming and the fact that base is not expected
			// to be available in the workspace
			// baseVersion.setPlatformURI(platformURI);
			// baseVersion.setResource(ifile);
	
			// Target Version
			targetVersion.setName(name);
			targetVersion.setRepositoryPath(fileRepoPath.toString());
			targetVersion.setVersionID(diffContext.getBlobs()[1].getName());
			targetVersion.setPlatformURI(platformURI);
			targetVersion.setResource(ifile);
		}
		
		if (unresolvedFiles.size() > 0 ) {
			ReviewVersionsException exc = new ReviewVersionsException(
					"Unable to resolve commit files to workspace files");
			exc.setDetails(unresolvedFiles);
			throw exc;
		}
		return revItem;
	}

	/**
	 * @param repo
	 * @param resource
	 * @param commitID
	 * @return
	 * @throws ReviewVersionsException
	 */
	private DiffContext[] getChangeSet(Repository repo, IResource resource, ObjectId commitID)
			throws ReviewVersionsException {
		DiffContext[] changeSet = null;
		try {
			changeSet = GitDiffUtils.getChangeSet(repo, resource, commitID);
		} catch (MissingObjectException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.MISSING_OBJECT);
			throw exc;
		} catch (IncorrectObjectTypeException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.INCORRECT_OBJECT_TYPE);
			throw exc;
		} catch (IOException e) {
			ReviewVersionsException exc = new ReviewVersionsException(e);
			exc.setExceptionCode(VersionsExceptionCode.IO);
			throw exc;
		}
	
		return changeSet;
	}

	/**
	 * @param diffs
	 * @return
	 */
	private Map<String, DiffContext> indexDiffs(DiffContext[] diffs) {
		Map<String, DiffContext> diffIndex = new HashMap<String, DiffContext>();
		for (int i = 0; i < diffs.length; i++) {
			DiffContext diffContext = diffs[i];
			ObjectId[] objects = diffContext.getBlobs();
			if (objects.length > 1) {
				// Expected
				diffIndex.put(objects[1].getName(), diffContext);
			} else {
				// Unexpected
				Activator.fTracer.traceError("Diff contains a blob with unexpected size: " + objects.length + ", for: "
						+ diffContext.getPath());
			}
		}
	
		return diffIndex;
	}

	/**
	 * @param projectURIs
	 * @return
	 * @throws ReviewVersionsException
	 */
	private List<IProject> resolveProjects(List<String> projectURIs) throws ReviewVersionsException {
		List<IProject> projects = new ArrayList<IProject>(projectURIs.size());
		List<String> exceptionDetails = null;
	
		for (Iterator<String> iterator = projectURIs.iterator(); iterator.hasNext();) {
			String platformUriStr = iterator.next();
			try {
				IProject project = ResourceUtils.toIProject(platformUriStr);
				projects.add(project);
			} catch (FileNotFoundException e) {
				if (exceptionDetails == null) {
					exceptionDetails = new ArrayList<String>();
				}
				exceptionDetails.add(platformUriStr);
			}
		}
	
		if (exceptionDetails != null) {
			// An exception occurred
			StringBuilder sb = new StringBuilder(exceptionDetails.size() + " out of " + projectURIs.size()
					+ " were not resolved to the current workspace");
			ReviewVersionsException exc = new ReviewVersionsException(sb.toString());
			exc.setDetails(exceptionDetails);
			throw exc;
		}
	
		if (projects.size() < 1) {
			// At least one project needed to resolve to the workspace to resolve the repository
			StringBuilder sb = new StringBuilder("No projects resolved i.e. unable to resolve repository");
			ReviewVersionsException exc = new ReviewVersionsException(sb.toString());
			exc.setDetails(exceptionDetails);
		}
	
		return projects;
	}

	/**
	// private File getRepoDirectory(File directory) {
	// File convertedDir = directory;
	// if (!directory.isDirectory()) {
	// URI uri = URI.createURI(directory.getAbsolutePath());
	// if (uri.segmentCount() > 1) {
	// convertedDir = new File(uri.trimSegments(1).devicePath());
	// }
	// }
	// return convertedDir;
	// }
	 * @param project
	 * @return
	 */
	public static boolean isGitRepository(IProject project) {
		RepositoryProvider teamProvider = RepositoryProvider.getProvider(project);
		// check if the provider is for git
		if ((teamProvider instanceof GitProvider)) {
			return true;
		}
		return false;
	}

	/**
	 * @author lmcalvs
	 * 
	 */
	class GitFileVersionInfoImpl implements FileVersionInfo {
		String	fPath	= null;
		String	fId		= null;
		String	fName	= null;

		/**
		 * @param aPath
		 * @param aId
		 * @param aName
		 */
		GitFileVersionInfoImpl(String aPath, String aId, String aName) {
			fPath = aPath;
			fId = aId;
			fName = aName;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.FileVersionInfo#getRepositoryPath()
		 */
		public String getRepositoryPath() {
			return fPath;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.FileVersionInfo#getId()
		 */
		public String getId() {
			return fId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.FileVersionInfo#getName()
		 */
		public String getName() {
			return fName;
		}
	}
	
	class GitCommitDescriptorImpl implements CommitDescriptor {
		RevCommit	fCommit	= null;
		Repository	fRepo	= null;
		IResource	fResource	= null;

		/**
		 * @param aCommit
		 * @param aResource
		 * @param aRepo
		 */
		GitCommitDescriptorImpl(RevCommit aCommit, IResource aResource, Repository aRepo) {
			fCommit = aCommit;
			fRepo = aRepo;
			fResource = aResource;
		}

		public String getId() {
			return fCommit.getName();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getCommitDate()
		 */
		public Long getCommitDate() {
			// from sec to msec
			Long commitTime = Integer.valueOf(fCommit.getCommitTime()).longValue() * 1000;
			return commitTime;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getCommitter()
		 */
		public String getCommitter() {
			return getPersonId(fCommit.getCommitterIdent());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getAuthor()
		 */
		public String getAuthor() {
			return getPersonId(fCommit.getAuthorIdent());
		}

		/**
		 * @param person
		 * @return
		 */
		private String getPersonId(PersonIdent person) {
			StringBuilder sb = new StringBuilder();
			sb.append(person.getName() + ":");
			sb.append(person.getEmailAddress());
			return sb.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getTitle()
		 */
		public String getTitle() {
			return fCommit.getShortMessage();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getMessage()
		 */
		public String getMessage() {
			return fCommit.getFullMessage();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getParentIDs()
		 */
		public String[] getParentIDs() {
			RevCommit[] commits = fCommit.getParents();
			if (commits == null) {
				return null;
			}

			String[] parentCommitIDs = new String[commits.length];
			for (int i = 0; i < commits.length; i++) {
				RevCommit revCommit = commits[i];
				parentCommitIDs[i] = revCommit.getName();
			}

			return parentCommitIDs;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor#getChangeSet()
		 */
		public String[] getChangeSet() {
			DiffContext[] diffs = null;
			String[] changeSet = null;
			try {
				diffs = ReviewsGITVersionsIFImpl.this.getChangeSet(fRepo, fResource, fCommit.getId());
			} catch (ReviewVersionsException e) {
				StringBuilder sb = new StringBuilder(
						"Exception while trying to resolve the changeset associated to the commit: "
								+ fCommit.getName());
				try {
					sb.append(", repo: " + fRepo.getDirectory().getCanonicalPath().toString());
				} catch (IOException e1) {
					// proceed.
				}
				Activator.fTracer.traceError(sb.toString());
				e.printStackTrace();
			}

			if (diffs == null) {
				changeSet = new String[0];
			} else {
				changeSet = new String[diffs.length];
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < diffs.length; i++) {
					sb.setLength(0);
					DiffContext diff = diffs[i];
					sb.append(diff.getChange().toString());
					sb.append(" " + diff.getPath());
					changeSet[i] = sb.toString();
				}
			}

			return changeSet;
		}
		
	}
	
	/**
	 * Container of compare session information
	 * @author lmcalvs
	 *
	 */
	class CompareSessionInfo {
		private Map<String, DiffContext>	fIndexedDiffs	= null;
		private Repository					fRepo	= null;

		CompareSessionInfo(Repository aRepo, Map<String, DiffContext> aIndexedDiffs) {
			fIndexedDiffs = aIndexedDiffs;
			fRepo = aRepo;
		}

		public Repository getRepo() {
			return fRepo;
		}

		public Map<String, DiffContext> getIndexedDiffs() {
			return fIndexedDiffs;
		}

	}
	
	
}
	