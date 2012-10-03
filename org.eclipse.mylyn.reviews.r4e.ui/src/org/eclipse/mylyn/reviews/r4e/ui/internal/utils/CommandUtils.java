// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.mylyn.reviews.frame.core.model.Location;
import org.eclipse.mylyn.reviews.frame.core.utils.Tracer;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.versions.core.ChangeType;
import org.eclipse.mylyn.versions.core.ScmArtifact;
import org.eclipse.mylyn.versions.core.ScmCore;
import org.eclipse.mylyn.versions.core.spi.ScmConnector;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

/**
 * This class implements various utility methods used in the context- sensitive commands
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class CommandUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field NO_SOURCE_CONTROL_ID_TEXT. (value is ""(Version not in Source Control)"")
	 */
	private static final String NO_SOURCE_CONTROL_ID_TEXT = "(Version not in Source Control)";

	/**
	 * Field INVALID_PATH. (value is ""/dev/null"")
	 */
	private static final String INVALID_PATH = "/dev/null";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getTargetFileURI.
	 * 
	 * @param aInput
	 *            - IEditorInput
	 * @return URI
	 * @throws URISyntaxException
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion getTargetFileData(IEditorInput aInput) throws CoreException,
			ReviewsFileStorageException {
		IFile editorFile = null; //The file we use in the current editor/workspace
		if (aInput instanceof IFileEditorInput) {
			editorFile = ((IFileEditorInput) aInput).getFile();
			//Now we have the file in editor, we need to use the versions interface to see if we should copy it
			//to the local repo and update model info
			if (null == editorFile) {
				return null;
			}
			return updateTargetFile(editorFile);
		} else if (aInput instanceof R4ECompareEditorInput) {
			//If we get here, this is because we are trying to act on the compare editor contents
			//this means that the file we are acting on is already in the local repository
			//in this case, we only need to provide the versionId of this file
			final ITypedElement element = ((R4ECompareEditorInput) aInput).getLeftElement();
			if (element instanceof R4EFileRevisionTypedElement) {
				return ((R4EFileRevisionTypedElement) element).getFileVersion();
			} else if (element instanceof R4EFileTypedElement) {
				return ((R4EFileTypedElement) element).getFileVersion();
			} else {
				return null;
			}
		} else if (aInput instanceof R4EFileRevisionEditorInput) {
			return ((R4EFileRevisionEditorInput) aInput).getFileVersion();
		} else if (aInput instanceof R4EFileEditorInput) {
			return ((R4EFileEditorInput) aInput).getFileVersion();
		} else {
			//Should never happen
			throw new CoreException(new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, IStatus.OK, "Invalid input "
					+ ((null != aInput) ? aInput.getClass().toString() : ""), null));
		}
	}

	/**
	 * Method updateTargetFile.
	 * 
	 * @param aFile
	 *            IFile
	 * @return IFile
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion updateTargetFile(IFile aFile) throws CoreException, ReviewsFileStorageException {

		if (null == aFile) {
			return null; //should never happen
		}

		String remoteID = null;
		String localID = null;

		// Get handle to local storage repository
		final IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview()
				.getReview());
		if (null == localRepository) {
			return null;
		}
		//Get Remote repository file info
		final ScmConnector connector = ScmCore.getConnector(aFile.getProject());
		if (null != connector) {
			final ScmArtifact artifact = connector.getArtifact(aFile);
			if ((null != artifact) && (null != artifact.getPath())) {
				//File found in remote repo.  

				//Here we check if the file in the remote repository is different than the input file.
				//We cannot use the artifact ID directly and we need to fetch and calculate that SHA of the remote file 
				//because we do not know which version control system is used.
				//We need to do this comparison because the versions always return the latest file stored.

				final IFileRevision fileRev = artifact.getFileRevision(null);
				if (null != fileRev) {
					final IStorage fileStore = fileRev.getStorage(null);
					if (null != fileStore) {
						remoteID = localRepository.blobIdFor(fileStore.getContents());
						localID = localRepository.blobIdFor(aFile.getContents());
						if ((null != remoteID) && remoteID.equals(localID)) {
							//The files are the same. Copy from the remote repo
							return copyRemoteFileToLocalRepository(localRepository, artifact);
						}
					}
				}
				//The files are different.  This means the current user modified the file in his workspace
				return copyWorkspaceFileToLocalRepository(localRepository, aFile);
			}
		}
		//Else we copy the file that is in the current workspace
		return copyWorkspaceFileToLocalRepository(localRepository, aFile);
	}

	/**
	 * Method getBaseFileURI.
	 * 
	 * @param aInput
	 *            - IEditorInput
	 * @return IFile
	 * @throws URISyntaxException
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion getBaseFileData(IEditorInput aInput) throws CoreException, ReviewsFileStorageException {
/*		final IEditorInput input = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getActiveEditor()
				.getEditorInput(); // $codepro.audit.disable methodChainLength
*/		IFile editorFile = null; //The file we use in the current editor/workspace
		if (aInput instanceof IFileEditorInput) {
			editorFile = ((IFileEditorInput) aInput).getFile();
			//Now we have the file in editor, we need to use the versions interface to see if we should copy it
			//to the local repo and update model info
			return updateBaseFile(editorFile);
		} else if (aInput instanceof R4ECompareEditorInput) {
			//If we get here, this is because we are trying to act on the compare editor contents
			//We have three cases:
			//1) The left file is an R4EFileTypedElement and had no version ID. This means that it is a modified file not yet in source control.
			//	 In this case, the base file is the version that is in source control i.e. the right file version (if it is in source control).
			//2) The left file is an R4EFileTypedElement and had a version ID. This means that it is file in source control in sync with the workspace.
			//   In this case, the base file for the new Resource Review item should be the same as the target file i.e. the left file version 
			//3) The left file is an R4EFileRevisionTypedElement. This means that it is a file in source control.
			//   In this case, the base file for the new Resource Review item should be the same as the target file i.e. the left file version 
			final ITypedElement leftElement = ((R4ECompareEditorInput) aInput).getLeftElement();
			final ITypedElement rightElement = ((R4ECompareEditorInput) aInput).getRightElement();
			if (leftElement instanceof R4EFileTypedElement && rightElement instanceof R4EFileRevisionTypedElement) {
				if (((R4EFileTypedElement) leftElement).getFileVersion()
						.getVersionID()
						.equals(NO_SOURCE_CONTROL_ID_TEXT)) {
					return ((R4EFileRevisionTypedElement) rightElement).getFileVersion();
				} else {
					return ((R4EFileTypedElement) leftElement).getFileVersion();
				}
			} else if (leftElement instanceof R4EFileRevisionTypedElement) {
				return ((R4EFileRevisionTypedElement) leftElement).getFileVersion();
			} else {
				return null;
			}
		} else if (aInput instanceof R4EFileRevisionEditorInput) {
			return ((R4EFileRevisionEditorInput) aInput).getFileVersion();
		} else if (aInput instanceof R4EFileEditorInput) {
			return ((R4EFileEditorInput) aInput).getFileVersion();
		} else {
			//Should never happen
			throw new CoreException(new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, IStatus.OK, "Invalid input "
					+ ((null != aInput) ? aInput.getClass().toString() : ""), null));
		}
	}

	/**
	 * Method updateBaseFile.
	 * 
	 * @param aFile
	 *            IFile
	 * @return IFile
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion updateBaseFile(IFile aFile) throws CoreException, ReviewsFileStorageException {
		if (null == aFile) {
			return null; //should never happen
		}

		//Get Remote repository file info
		final ScmConnector connector = ScmCore.getConnector(aFile.getProject());
		if (null != connector) {
			final ScmArtifact artifact = connector.getArtifact(aFile);
			if ((null != artifact) && (null != artifact.getId())) {
				//File was modified, so we need to fetch the base file from the versions repository and copy it to our own local repository
				final IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview()
						.getReview());
				return copyRemoteFileToLocalRepository(localRepository, artifact);
			}
		} //else file not in source control

		//File was not modified, or No Version Control System or Remote File detected, so there is no base
		return null;
	}

	/**
	 * Method copyRemoteFileToLocalRepository.
	 * 
	 * @param aLocalRepository
	 *            IRFSRegistry
	 * @param aArtifact
	 *            ScmArtifact
	 * @return IFile
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion copyRemoteFileToLocalRepository(IRFSRegistry aLocalRepository, ScmArtifact aArtifact)
			throws ReviewsFileStorageException, CoreException {

		if ((null == aArtifact) || (null == aArtifact.getPath()) || aArtifact.getPath().equals(INVALID_PATH)) {
			return null; //File not found in remote repository
		}
		final IFileRevision fileRev = aArtifact.getFileRevision(null);
		if (null == fileRev) {
			return null;
		}

		// Pull file from the version control system
		InputStream iStream = null;
		final IStorage fileStore = fileRev.getStorage(null);
		if (null == fileStore) {
			return null;
		}
		try {
			iStream = fileStore.getContents();
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			return null;
		}

		//Create and Set value in temporary File version
		final R4EFileVersion tmpFileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
		if ((null != tmpFileVersion) && (null != aLocalRepository)) {
			updateFileVersion(tmpFileVersion, aArtifact);

			// Push a local copy to local review repository, and obtain the local id
			tmpFileVersion.setLocalVersionID(aLocalRepository.registerReviewBlob(iStream));
			if (null != iStream) {
				try {
					iStream.close();
				} catch (IOException e) {
					R4EUIPlugin.Ftracer.traceWarning("Exception while closing stream, " + e.toString());
				}
			}
		}
		return tmpFileVersion;
	}

	/**
	 * Method copyRemoteFileToLocalRepository.
	 * 
	 * @param aLock
	 *            ReentrantLock
	 * @param aLocalRepository
	 *            IRFSRegistry
	 * @param aArtifact
	 *            ScmArtifact
	 * @param aMainMonitor
	 *            IProgressMonitor
	 * @param aSubMonitor
	 *            IProgressMonitor
	 * @return IFile
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 * @throws MainJobCancelledException
	 * @throws SubJobCancelledException
	 */
	public static R4EFileVersion copyRemoteFileToLocalRepository(ReentrantLock aLock, IRFSRegistry aLocalRepository,
			ScmArtifact aArtifact, IProgressMonitor aMainMonitor) throws ReviewsFileStorageException, CoreException {
		ReentrantLock lock = aLock;
		if (null == lock) {
			lock = new ReentrantLock();
		}

		//Check if Main Job or Sub Job was cancelled before fetching
		if (aMainMonitor.isCanceled()) {
			throw new CoreException(new Status(IStatus.CANCEL, R4EUIPlugin.PLUGIN_ID, IStatus.CANCEL,
					R4EUIConstants.CANCEL_EXCEPTION_MSG, null));
		}

		if ((null == aArtifact) || (null == aArtifact.getPath()) || aArtifact.getPath().equals(INVALID_PATH)) {
			return null; //File not found in remote repository
		}

		//Tracing benchmarks
		Date fetchStart = null;
		if (Tracer.isInfo()) {
			fetchStart = new Date();
		}

		final IFileRevision fileRev = aArtifact.getFileRevision(null);
		if (null == fileRev) {
			return null;
		}

		// Pull file from the version control system
		InputStream iStream = null;
		final IStorage fileStore = fileRev.getStorage(null);
		if (null == fileStore) {
			return null;
		}
		try {
			iStream = fileStore.getContents();
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			return null;
		}

		//Create and Set value in temporary File version
		final R4EFileVersion tmpFileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
		if ((null != tmpFileVersion) && (null != aLocalRepository)) {
			updateFileVersion(tmpFileVersion, aArtifact);

			//Check if Main Job or Sub Job was cancelled before pushing a local copy
			if (aMainMonitor.isCanceled()) {
				throw new CoreException(new Status(IStatus.CANCEL, R4EUIPlugin.PLUGIN_ID, IStatus.CANCEL,
						R4EUIConstants.CANCEL_EXCEPTION_MSG, null));
			}

			//Tracing benchmarks
			Date blobRegStart = null;
			if (fetchStart != null) {
				blobRegStart = new Date();
			}

			// Push a local copy to local review repository, and obtain the local id
			lock.lock();
			try {
				tmpFileVersion.setLocalVersionID(aLocalRepository.registerReviewBlob(iStream));
			} finally {
				lock.unlock();

				if (fetchStart != null) {
					long downloadTime = blobRegStart.getTime() - fetchStart.getTime();
					long uploadTime = (new Date()).getTime() - blobRegStart.getTime();
					R4EUIPlugin.Ftracer.traceInfo("Registered blob for " + fileRev.getName() + " " + //$NON-NLS-1$//$NON-NLS-2$
							aArtifact.getId() + ", fetch (ms): " + downloadTime + ", push (ms) " + uploadTime); //$NON-NLS-1$ //$NON-NLS-2$
				}

				if (null != iStream) {
					try {
						iStream.close();
					} catch (IOException e) {
						R4EUIPlugin.Ftracer.traceWarning("Exception while closing stream, " + e.toString());
					}
				}
			}
		}
		return tmpFileVersion;
	}

	/**
	 * Method copyWorkspaceFileToLocalRepository.
	 * 
	 * @param aLocalRepository
	 *            IRFSRegistry
	 * @param aFile
	 *            IFile
	 * @return IFile
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion copyWorkspaceFileToLocalRepository(IRFSRegistry aLocalRepository, IFile aFile)
			throws CoreException, ReviewsFileStorageException {
		R4EFileVersion tmpFileVersion = null;
		if ((null != aLocalRepository) && (null != aFile)) {
			//Create and Set value in temporary File version
			tmpFileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
			if ((null != tmpFileVersion)) {
				updateFileVersion(tmpFileVersion, aFile);

				// Push a local copy to local review repository, and obtain the local id
				tmpFileVersion.setLocalVersionID(aLocalRepository.registerReviewBlob(aFile.getContents()));
				tmpFileVersion.setVersionID(NO_SOURCE_CONTROL_ID_TEXT);
			}
		}
		return tmpFileVersion;
	}

	/**
	 * Method getPosition.
	 * 
	 * @param aSelection
	 *            ITextSelection
	 * @return TextPosition
	 */
	public static R4EUITextPosition getPosition(ITextSelection aSelection) {
		if (null == aSelection) {
			return null;
		}
		return new R4EUITextPosition(aSelection.getOffset(), (aSelection).getLength(), (aSelection).getStartLine(),
				(aSelection).getEndLine());
	}

	/**
	 * Method getPosition. Get position based on position in Document
	 * 
	 * @param aOffset
	 *            int
	 * @param aLength
	 *            int
	 * @param aDoc
	 *            IDocument
	 * @return R4EUITextPosition
	 */
	public static R4EUITextPosition getPosition(int aOffset, int aLength, IDocument aDoc) { // $codepro.audit.disable overloadedMethods
		final R4EUITextPosition position = new R4EUITextPosition(aOffset, aLength, aDoc);
		return position;
	}

	/**
	 * Method getPosition. Get position for generic workspace files
	 * 
	 * @param aSelectedElement
	 *            IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(IFile aSelectedElement) throws CoreException { // $codepro.audit.disable overloadedMethods
		if (null == aSelectedElement) {
			return null;
		}
		final R4EUITextPosition position = new R4EUITextPosition(R4EUIConstants.NO_OFFSET,
				R4EUIConstants.INVALID_VALUE, aSelectedElement);
		position.setName(aSelectedElement.getName());
		return position;
	}

	/**
	 * Method getPosition. Get position for workspace java source files
	 * 
	 * @param aSelectedElement
	 *            ISourceReference
	 * @param aFile
	 *            IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(ISourceReference aSelectedElement, IFile aFile) throws CoreException {
		int offset = 0;
		int length = 0;
		String name = "";
		if (null != aSelectedElement) {
			final ISourceRange sourceRange = aSelectedElement.getSourceRange();
			if (null != sourceRange) {
				offset = sourceRange.getOffset();
				length = sourceRange.getLength();
			}
			name = ((IJavaElement) aSelectedElement).getElementName();
		}
		final R4EUITextPosition position = new R4EUITextPosition(offset, length, aFile);
		position.setName(name);
		return position;
	}

	/**
	 * Method getPosition. Get position for workspace C and C++ source files
	 * 
	 * @param aSelectedElement
	 *            org.eclipse.cdt.core.model.ISourceReference
	 * @param aFile
	 *            IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(org.eclipse.cdt.core.model.ISourceReference aSelectedElement,
			IFile aFile) // $codepro.audit.disable overloadedMethods
			throws CoreException {
		int startPos = 0;
		int length = 0;
		int startLine = 0;
		int endLine = 0;
		String name = "";
		if (null != aSelectedElement) {
			final org.eclipse.cdt.core.model.ISourceRange sourceRange = aSelectedElement.getSourceRange();
			if (null != sourceRange) {
				startPos = sourceRange.getStartPos();
				length = sourceRange.getLength();
				startLine = sourceRange.getStartLine();
				endLine = sourceRange.getEndLine();
			}
			name = ((org.eclipse.cdt.core.model.ICElement) aSelectedElement).getElementName();
		}
		final R4EUITextPosition position = new R4EUITextPosition(startPos, length, aFile);
		position.setStartLine(startLine);
		position.setEndLine(endLine);
		position.setName(name);
		return position;
	}

	/**
	 * Adapt change types from Mylyn Versions to R4E model
	 * 
	 * @param aChangeType
	 *            ChangeType
	 * @return R4EContextType
	 */
	public static R4EContextType adaptType(ChangeType aChangeType) {
		R4EContextType dtype = null;
		switch (aChangeType) {
		case ADDED:
			dtype = R4EContextType.R4E_ADDED;
			break;
		case DELETED:
			dtype = R4EContextType.R4E_DELETED;
			break;
		case MODIFIED:
			dtype = R4EContextType.R4E_MODIFIED;
			break;
		case REPLACED:
			dtype = R4EContextType.R4E_REPLACED;
			break;
		default:
			break;
		}
		return dtype;
	}

	/**
	 * Resolve and transfer values from ScmArtifact to R4EFileVersion
	 * 
	 * @param aTargetFileVer
	 *            R4EFileVersion
	 * @param aSourceFileVer
	 *            R4EFileVersion
	 */
	public static void copyFileVersionData(R4EFileVersion aTargetFileVer, R4EFileVersion aSourceFileVer) {
		if ((null != aTargetFileVer) && (null != aSourceFileVer)) {
			aTargetFileVer.setName(aSourceFileVer.getName());
			aTargetFileVer.setVersionID(aSourceFileVer.getVersionID());
			aTargetFileVer.setRepositoryPath(aSourceFileVer.getRepositoryPath());
			aTargetFileVer.setLocalVersionID(aSourceFileVer.getLocalVersionID());
			aTargetFileVer.setPlatformURI(aSourceFileVer.getPlatformURI());
			aTargetFileVer.setResource(aSourceFileVer.getResource());
		}
	}

	/**
	 * Method updateFileVersion
	 * 
	 * @param aTargetFileVer
	 *            R4EFileVersion
	 * @param aScmArt
	 *            ScmArtifact
	 * @throws CoreException
	 */
	public static void updateFileVersion(R4EFileVersion aTargetFileVer, ScmArtifact aScmArt) throws CoreException {

		if ((null != aTargetFileVer) && (null != aScmArt)) {
			aTargetFileVer.setName(aScmArt.getFileRevision(null).getName());
			aTargetFileVer.setVersionID(aScmArt.getId());
			final IFileRevision fileRev = aScmArt.getFileRevision(null);
			if (null != fileRev) {
				final IStorage fileStore = fileRev.getStorage(null);
				if (null != fileStore) {
					final IPath filePath = fileStore.getFullPath();
					if (null != filePath) {
						aTargetFileVer.setRepositoryPath(filePath.toPortableString());
					}
				}
			}

			final String fileRelPath = aScmArt.getProjectRelativePath();
			if (null == fileRelPath) {
				R4EUIPlugin.Ftracer.traceDebug("Invalid relative file path in scmArtifact with path: "
						+ aScmArt.getPath());
			}
			final IProject project = ResourceUtils.getProject(aScmArt.getProjectName());
			final IResource resource = ResourceUtils.findResource(project, fileRelPath);

			aTargetFileVer.setPlatformURI(ResourceUtils.toPlatformURIStr(resource));
			aTargetFileVer.setResource(resource);

			final String projPlatformURI = ResourceUtils.toPlatformURIStr(project);
			if (null == projPlatformURI) {
				R4EUIPlugin.Ftracer.traceDebug("Unable to resolve the project: " + aScmArt.getProjectName()
						+ " platform's URI, in scmArtifact with path: " + aScmArt.getPath());
			}
		}
	}

	/**
	 * Method updateFileVersion
	 * 
	 * @param aTargetFileVer
	 *            R4EFileVersion
	 * @param aSrcFile
	 *            IFile
	 */
	public static void updateFileVersion(R4EFileVersion aTargetFileVer, IFile aSrcFile) {
		if ((null != aTargetFileVer) && (null != aSrcFile)) {
			aTargetFileVer.setName(aSrcFile.getName());
			aTargetFileVer.setRepositoryPath(""); //No repositories for workspace files since they are not in source control
			aTargetFileVer.setResource(aSrcFile);
			aTargetFileVer.setPlatformURI(ResourceUtils.toPlatformURIStr(aSrcFile));
		}
	}

	/**
	 * Method useWorkspaceResource.
	 * 
	 * @param aVersion
	 *            R4EFileVersion
	 * @return boolean
	 */
	public static boolean useWorkspaceResource(R4EFileVersion aVersion) {
		// Get handle to local storage repository
		try {
			if (null != R4EUIModelController.getActiveReview()) {
				final IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview()
						.getReview());
				if (null != localRepository) {
					//If resource is available in the workspace, use it.  Otherwise use the local repo version
					if ((null != aVersion) && (null != aVersion.getResource())) {
						final String workspaceFileId = localRepository.blobIdFor(((IFile) aVersion.getResource()).getContents());
						final String repoFileId = aVersion.getLocalVersionID();
						if ((null != workspaceFileId) && workspaceFileId.equals((repoFileId))) {
							return true;
						}
					}
				}
			}
		} catch (ReviewsFileStorageException e) {
			R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (CoreException e) {
			R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
		return false;
	}

	/**
	 * Method createCompareEditorInput.
	 * 
	 * @param aBaseFileVersion
	 *            R4EFileVersion
	 * @param aTargetFileVersion
	 *            R4EFileVersion
	 * @return R4ECompareEditorInput
	 */
	public static R4ECompareEditorInput createCompareEditorInput(R4EFileVersion aBaseFileVersion,
			R4EFileVersion aTargetFileVersion) {

		final CompareConfiguration config = new CompareConfiguration();
		config.setRightEditable(false);
		config.setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.valueOf(true));

		//NOTE:  We use the workspace file as input if it is in sync with the file to review,
		//		 otherwise we use the file to review that is included in the review repository.
		//		 Only workspace files are editable.
		final R4EFileRevisionTypedElement ancestor = null; //Might be improved later
		ITypedElement target = null;
		if (null != aTargetFileVersion) {
			if (CommandUtils.useWorkspaceResource(aTargetFileVersion)) {
				target = new R4EFileTypedElement(aTargetFileVersion);
				config.setLeftEditable(true);
			} else {
				target = new R4EFileRevisionTypedElement(aTargetFileVersion);
				config.setLeftEditable(false);
			}
		}
		ITypedElement base = null;
		if (null != aBaseFileVersion) {
			if (CommandUtils.useWorkspaceResource(aBaseFileVersion)) {
				base = new R4EFileTypedElement(aBaseFileVersion);
				config.setRightEditable(true);
			} else {
				base = new R4EFileRevisionTypedElement(aBaseFileVersion);
				config.setRightEditable(false);
			}
		}
		return new R4ECompareEditorInput(config, ancestor, target, base);
	}

	/**
	 * Method getAnomalyPosition.
	 * 
	 * @param aAnomaly
	 *            R4EAnomaly
	 * @return R4EAnomalyTextPosition
	 */
	public static R4EAnomalyTextPosition getAnomalyPosition(R4EAnomaly aAnomaly) {
		if (null != aAnomaly) {
			final EList<Location> location = aAnomaly.getLocation();
			if ((null != location) && (location.size() > 0)) {
				final R4EContent content = (R4EContent) location.get(0); //look at first location only
				if (null != content) {
					final R4EAnomalyTextPosition position = (R4EAnomalyTextPosition) content.getLocation();
					return position;
				}
			}
		}
		return null;
	}

	/**
	 * Method getAnomalyParentFile.
	 * 
	 * @param aAnomaly
	 *            R4EAnomaly
	 * @return R4EFileVersion
	 */
	public static R4EFileVersion getAnomalyParentFile(R4EAnomaly aAnomaly) {
		if (null != aAnomaly) {
			final EList<Location> location = aAnomaly.getLocation();
			if ((null != location) && (location.size() > 0)) {
				final R4EContent content = (R4EContent) location.get(0); //look at first location only
				if (null != content) {
					final R4EAnomalyTextPosition position = (R4EAnomalyTextPosition) content.getLocation();
					if (null != position) {
						return position.getFile();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Method copyAnomalyData.
	 * 
	 * @param aTargetAnomaly
	 *            R4EAnomaly
	 * @param aSourceAnomaly
	 *            R4EAnomaly
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public static void copyAnomalyData(R4EAnomaly aTargetAnomaly, R4EAnomaly aSourceAnomaly)
			throws ResourceHandlingException, OutOfSyncException {
		if ((null != aTargetAnomaly) && (null != aSourceAnomaly)) {
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(aTargetAnomaly,
					R4EUIModelController.getReviewer());

			//Data copied unconditionally
			aTargetAnomaly.setCreatedOn(aSourceAnomaly.getCreatedOn());
			aTargetAnomaly.setDecidedByID(aSourceAnomaly.getDecidedByID());
			aTargetAnomaly.setFixedByID(aSourceAnomaly.getFixedByID());
			aTargetAnomaly.setFollowUpByID(aSourceAnomaly.getFollowUpByID());
			aTargetAnomaly.setState(aSourceAnomaly.getState());
			aTargetAnomaly.setFixedInVersion(aSourceAnomaly.getFixedInVersion());
			aTargetAnomaly.setNotAcceptedReason(aSourceAnomaly.getNotAcceptedReason());

			//Data copied only if not set previously
			if (null == aTargetAnomaly.getTitle() || ("").equals(aTargetAnomaly.getTitle())) {
				aTargetAnomaly.setTitle(aSourceAnomaly.getTitle());
			}
			if (null == aTargetAnomaly.getDescription() || ("").equals(aTargetAnomaly.getDescription())) {
				aTargetAnomaly.setDescription(aSourceAnomaly.getDescription());
			}
			if (null == aTargetAnomaly.getRank()) {
				aTargetAnomaly.setRank(aSourceAnomaly.getRank());
			}
			if (null == aTargetAnomaly.getRuleID() && null != aSourceAnomaly.getRuleID()) {
				aTargetAnomaly.setRuleID(aSourceAnomaly.getRuleID());
			}
			if (null != aSourceAnomaly.getType()) {
				final R4ECommentType oldCommentType = (R4ECommentType) aSourceAnomaly.getType();
				R4ECommentType commentType = (R4ECommentType) aTargetAnomaly.getType();
				if (null != oldCommentType) {
					if (null == commentType) {
						commentType = RModelFactory.eINSTANCE.createR4ECommentType();
						if (null != commentType) {
							commentType.setType(oldCommentType.getType());
						}
						aTargetAnomaly.setType(commentType);
					}
				}
			}
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}
	}

	/**
	 * Method showPostponedElements.
	 * 
	 * @param aReview
	 *            R4EUIReviewBasic
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws CompatibilityException
	 */
	public static void showPostponedElements(R4EUIReviewBasic aReview) throws ResourceHandlingException,
			OutOfSyncException, CompatibilityException {
		if (null != aReview) {
			final R4EUIPostponedContainer container = aReview.getPostponedContainer();
			if (null != container) {
				boolean containerEnabled = false;
				for (IR4EUIModelElement element : container.getChildren()) {
					if (null != element) {
						if (element instanceof R4EUIPostponedFile) {
							R4EUIPostponedFile postFile = (R4EUIPostponedFile) element;
							for (IR4EUIModelElement anomaly : postFile.getChildren()) {
								if ((null != anomaly) && !anomaly.isEnabled()) {
									element.removeChildren(anomaly, false);
								}
							}
							if (!element.hasChildren()) {
								element.close();
							} else {
								containerEnabled = true; //At least one file contains postponed anomaly(ies)
							}
						} else if (element instanceof R4EUIPostponedAnomalyContainer) {
							for (IR4EUIModelElement anomaly : element.getChildren()) {
								if ((null != anomaly) && !anomaly.isEnabled()) {
									element.removeChildren(anomaly, false);
								}
							}
							if (!element.hasChildren()) {
								element.close();
								container.removeChildren(element, false);
							} else {
								containerEnabled = true; //At least one global postponed anomaly exists
							}
						}
					}
				}
				if (!containerEnabled) {
					container.close();
					//aReview.removeChildren(container, false);  TODO this is temporary
				}
			}
		}
	}

	/**
	 * Method getOriginalAnomaly.
	 * 
	 * @param aOriginalReview
	 *            R4EReview
	 * @param aCurrentAnomaly
	 *            R4EAnomaly
	 * @return R4EAnomaly
	 */
	public static R4EAnomaly getOriginalAnomaly(R4EReview aOriginalReview, R4EAnomaly aCurrentAnomaly) {
		if ((null != aOriginalReview) && (null != aCurrentAnomaly)) {
			//Loop through all anomalies and find the one whose R4EID is the same as the currently imported one
			final String[] origIdTokens = aCurrentAnomaly.getInfoAtt()
					.get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)
					.split(R4EUIConstants.SEPARATOR); //First token is user name, second token is sequence number
			if (null != origIdTokens) {
				final R4EUser origUser = aOriginalReview.getUsersMap().get(origIdTokens[0]);
				if (null != origUser) {
					for (R4EComment anomaly : origUser.getAddedComments()) {
						if (anomaly instanceof R4EAnomaly) {
							Integer id = Integer.valueOf(origIdTokens[1]);
							if (null != id) {
								if (id.intValue() == anomaly.getId().getSequenceID()) {
									return (R4EAnomaly) anomaly;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Method buildOriginalAnomalyID.
	 * 
	 * @param aOrigAnomaly
	 *            R4EAnomaly
	 * @return String
	 */
	public static String buildOriginalAnomalyID(R4EAnomaly aOrigAnomaly) {
		return aOrigAnomaly.getId().getUserID() + R4EUIConstants.SEPARATOR + aOrigAnomaly.getId().getSequenceID();
	}

	/**
	 * Method buildOriginalCommentID.
	 * 
	 * @param aOrigComment
	 *            R4EComment
	 * @return String
	 */
	public static String buildOriginalCommentID(R4EComment aOrigComment) {
		return aOrigComment.getId().getUserID() + aOrigComment.getId().getSequenceID();
	}

	/**
	 * Method getParticipantForReview.
	 * 
	 * @param aReview
	 *            R4EReview
	 * @param aParticipantId
	 *            String
	 * @return R4EParticipant
	 * @throws ResourceHandlingException
	 */
	public static R4EParticipant getParticipantForReview(R4EReview aReview, String aParticipantId)
			throws ResourceHandlingException {
		if (null == aReview) {
			return null;
		}
		R4EParticipant participant = (R4EParticipant) aReview.getUsersMap().get(aParticipantId);
		if (null == participant) {
			//Add the participant
			final List<R4EUserRole> role = new ArrayList<R4EUserRole>(1);
			role.add(R4EUserRole.R4E_ROLE_REVIEWER);
			participant = R4EUIModelController.FModelExt.createR4EParticipant(aReview, aParticipantId, role);
		}
		return participant;
	}

	/**
	 * Method isEmailValid.
	 * 
	 * @param aEmailAddress
	 *            String
	 * @return boolean
	 */
	public static boolean isEmailValid(String aEmailAddress) {

		//Ignore empty entry
		if ((null == aEmailAddress) || aEmailAddress.equals("")) {
			return true;
		}

		boolean result = false;
		final Pattern pattern = Pattern.compile(
				"^([\\w]((\\.(?!\\.))|[-!#\\$%'\\*\\+/=\\?\\^`\\{\\}\\|~\\w])*)(?<=[\\w])@(([\\w][-\\w]*[\\w]\\.)+[a-zA-Z]{2,6})$",
				Pattern.CASE_INSENSITIVE);
		if (null != pattern) {
			final Matcher matcher = pattern.matcher(aEmailAddress);
			if (null != matcher) {
				result = matcher.matches();
			}
		}

		//Validation of input failed
		if (!result) {

			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
					"Invalid format for Participant Email", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
							aEmailAddress + " is invalid", null), IStatus.ERROR);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					if (null != dialog) {
						dialog.open();
					}
				}
			});
		}
		return result;
	}

	/**
	 * Method isSameFileVersion.
	 * 
	 * @param aFile1
	 *            R4EFileVersion
	 * @param aFile2
	 *            R4EFileVersion
	 * @return boolean
	 */
	public static boolean isSameFileVersion(R4EFileVersion aFile1, R4EFileVersion aFile2) {
		return (compareStrings(aFile1.getPlatformURI(), aFile2.getPlatformURI())
				&& compareStrings(aFile1.getRepositoryPath(), aFile2.getRepositoryPath()) && compareStrings(
					aFile1.getVersionID(), aFile2.getVersionID()));
	}

	/**
	 * Method compareStrings.
	 * 
	 * @param aStr1
	 *            String
	 * @param aStr2
	 *            String
	 * @return boolean
	 */
	public static boolean compareStrings(String aStr1, String aStr2) {
		return ((aStr1 == aStr2) || (aStr1 != null && aStr1.equals(aStr2)));
	}
}
