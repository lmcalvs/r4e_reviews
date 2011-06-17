// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements various utility methods used in the context-
 * sensitive commands
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.io.InputStream;
import java.net.URISyntaxException;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileRevisionTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4EFileTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.versions.core.ChangeType;
import org.eclipse.mylyn.versions.core.ScmArtifact;
import org.eclipse.mylyn.versions.core.ScmCore;
import org.eclipse.mylyn.versions.core.spi.ScmConnector;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
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
	 * @return URI
	 * @throws URISyntaxException
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion getTargetFileData() throws CoreException, ReviewsFileStorageException {
		final IEditorInput input = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getActiveEditor()
				.getEditorInput(); // $codepro.audit.disable methodChainLength
		IFile editorFile = null; //The file we use in the current editor/workspace
		if (input instanceof IFileEditorInput) {
			editorFile = ((IFileEditorInput) input).getFile();
			//Now we have the file in editor, we need to use the versions interface to see if we should copy it
			//to the local repo and update model info
			return updateTargetFile(editorFile);
		} else if (input instanceof R4ECompareEditorInput) {
			//If we get here, this is because we are trying to act on the compare editor contents
			//this means that the file we are acting on is already in the local repository
			//in this case, we only need to provide the versionId of this file
			final ITypedElement element = ((R4ECompareEditorInput) input).getLeftElement();
			if (element instanceof R4EFileRevisionTypedElement) {
				return ((R4EFileRevisionTypedElement) element).getFileVersion();
			} else if (element instanceof R4EFileTypedElement) {
				return ((R4EFileTypedElement) element).getFileVersion();
			} else {
				return null;
			}
		} else if (input instanceof R4EFileRevisionEditorInput) {
			return ((R4EFileRevisionEditorInput) input).getFileVersion();
		} else if (input instanceof R4EFileEditorInput) {
			return ((R4EFileEditorInput) input).getFileVersion();
		} else {
			//Should never happen
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, "Invalid input "
					+ input.getClass().toString(), null));
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

		String remoteID = null;
		String localID = null;

		// Get handle to local storage repository
		final IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview()
				.getReview());

		//Get Remote repository file info
		final ScmConnector connector = ScmCore.getConnector(aFile.getProject());
		if (null != connector) {
			final ScmArtifact artifact = connector.getArtifact(aFile);
			if (null != artifact) {
				//File found in remote repo.  

				//TODO:  This is a hack because the versions always return the latest file stored.
				remoteID = artifact.getId();
				localID = localRepository.blobIdFor(aFile.getContents());
				if (localID.equals(remoteID)) {
					//The files are the same. Copy from the remote repo
					return copyRemoteFileToLocalRepository(localRepository, artifact);
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
	 * @return IFile
	 * @throws URISyntaxException
	 * @throws CoreException
	 * @throws ReviewsFileStorageException
	 */
	public static R4EFileVersion getBaseFileData() throws CoreException, ReviewsFileStorageException {
		final IEditorInput input = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getActiveEditor()
				.getEditorInput(); // $codepro.audit.disable methodChainLength
		IFile editorFile = null; //The file we use in the current editor/workspace
		if (input instanceof IFileEditorInput) {
			editorFile = ((IFileEditorInput) input).getFile();
			//Now we have the file in editor, we need to use the versions interface to see if we should copy it
			//to the local repo and update model info
			return updateBaseFile(editorFile);
		} else if (input instanceof R4ECompareEditorInput) {
			//If we get here, this is because we are trying to act on the compare editor contents
			//this means that the file we are acting on is already in the local repository
			//in this case, we only need to provide the versionId of this file
			final ITypedElement element = ((R4ECompareEditorInput) input).getRightElement();
			if (element instanceof R4EFileRevisionTypedElement) {
				return ((R4EFileRevisionTypedElement) element).getFileVersion();
			} else if (element instanceof R4EFileTypedElement) {
				return ((R4EFileTypedElement) element).getFileVersion();
			} else {
				return null;
			}
		} else if (input instanceof R4EFileRevisionEditorInput) {
			return ((R4EFileRevisionEditorInput) input).getFileVersion();
		} else if (input instanceof R4EFileEditorInput) {
			return ((R4EFileEditorInput) input).getFileVersion();
		} else {
			//Should never happen
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, "Invalid input "
					+ input.getClass().toString(), null));
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

		//Get Remote repository file info
		final ScmConnector connector = ScmCore.getConnector(aFile.getProject());
		if (null != connector) {
			final ScmArtifact artifact = connector.getArtifact(aFile);
			if (null != artifact) {
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

		if (aArtifact.getPath().equals(INVALID_PATH)) {
			return null; //File not found in remote repository
		}

		final IFileRevision fileRev = aArtifact.getFileRevision(null);

		// Pull file from the version control system
		InputStream iStream = null;
		try {
			iStream = fileRev.getStorage(null).getContents();
		} catch (CoreException e) {
			Activator.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			return null;
		}

		//Create and Set value in temporary File version
		final R4EFileVersion tmpFileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
		updateFileVersion(tmpFileVersion, aArtifact);

		// Push a local copy to local review repository, and obtain the local id
		tmpFileVersion.setLocalVersionID(aLocalRepository.registerReviewBlob(iStream));

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

		//Create and Set value in temporary File version
		final R4EFileVersion tmpFileVersion = RModelFactory.eINSTANCE.createR4EFileVersion();
		updateFileVersion(tmpFileVersion, aFile);

		// Push a local copy to local review repository, and obtain the local id
		tmpFileVersion.setLocalVersionID(aLocalRepository.registerReviewBlob(aFile.getContents()));
		tmpFileVersion.setVersionID(NO_SOURCE_CONTROL_ID_TEXT);
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
		return new R4EUITextPosition(aSelection.getOffset(), (aSelection).getLength(), (aSelection).getStartLine(),
				(aSelection).getEndLine());
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
		final R4EUITextPosition position = new R4EUITextPosition(R4EUIConstants.NO_OFFSET,
				R4EUIConstants.INVALID_VALUE, aSelectedElement);
		position.setName(aSelectedElement.getName());
		return position;
	}

	/**
	 * Method getPosition. Get position for workspace java source files
	 * 
	 * @param aSelectedElement
	 *            org.eclipse.jdt.core.ISourceReference
	 * @param aFile
	 *            IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(org.eclipse.jdt.core.ISourceReference aSelectedElement, IFile aFile)
			throws CoreException {
		final R4EUITextPosition position = new R4EUITextPosition(aSelectedElement.getSourceRange().getOffset(),
				aSelectedElement.getSourceRange().getLength(), aFile);
		position.setName(((IJavaElement) aSelectedElement).getElementName());
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
		final R4EUITextPosition position = new R4EUITextPosition(aSelectedElement.getSourceRange().getStartPos(),
				aSelectedElement.getSourceRange().getLength(), aFile);
		position.setStartLine(aSelectedElement.getSourceRange().getStartLine());
		position.setEndLine(aSelectedElement.getSourceRange().getEndLine());
		position.setName(((ICElement) aSelectedElement).getElementName());
		return position;
	}

	/**
	 * Adapt change types from Mylyn Versions to R4E model
	 * 
	 * @param changeType
	 *            ChangeType
	 * @return R4EContextType
	 */
	public static R4EContextType adaptType(ChangeType changeType) {
		R4EContextType dtype = null;
		switch (changeType) {
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
		aTargetFileVer.setName(aSourceFileVer.getName());
		aTargetFileVer.setVersionID(aSourceFileVer.getVersionID());
		aTargetFileVer.setRepositoryPath(aSourceFileVer.getRepositoryPath());
		aTargetFileVer.setLocalVersionID(aSourceFileVer.getLocalVersionID());
		aTargetFileVer.setPlatformURI(aSourceFileVer.getPlatformURI());
		aTargetFileVer.setResource(aSourceFileVer.getResource());
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

		aTargetFileVer.setName(aScmArt.getFileRevision(null).getName());
		aTargetFileVer.setVersionID(aScmArt.getId());
		aTargetFileVer.setRepositoryPath(aScmArt.getFileRevision(null)
				.getStorage(null)
				.getFullPath()
				.toPortableString());

		final String fileRelPath = aScmArt.getProjectRelativePath();
		if (null == fileRelPath) {
			Activator.Ftracer.traceDebug("Invalid relative file path in scmArtifact with path: " + aScmArt.getPath());
		}
		final IProject project = ResourceUtils.getProject(aScmArt.getProjectName());
		final IResource resource = ResourceUtils.findResource(project, fileRelPath);

		aTargetFileVer.setPlatformURI(ResourceUtils.toPlatformURIStr(resource));
		aTargetFileVer.setResource(resource);

		final String projPlatformURI = ResourceUtils.toPlatformURIStr(project);
		if (null == projPlatformURI) {
			Activator.Ftracer.traceDebug("Unable to resolve the project: " + aScmArt.getProjectName()
					+ " platform's URI, in scmArtifact with path: " + aScmArt.getPath());
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

		aTargetFileVer.setName(aSrcFile.getName());
		aTargetFileVer.setRepositoryPath(aSrcFile.getLocation().toPortableString());
		aTargetFileVer.setResource(aSrcFile);
		aTargetFileVer.setPlatformURI(ResourceUtils.toPlatformURIStr(aSrcFile));
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
		final IRFSRegistry localRepository;
		try {
			localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview().getReview());

			//If resource is available in the workspace, use it.  Otherwise use the local repo version
			if (null != aVersion && null != aVersion.getResource()) {
				final String workspaceFileId = localRepository.blobIdFor(((IFile) aVersion.getResource()).getContents());
				final String repoFileId = aVersion.getLocalVersionID();
				if (workspaceFileId.equals((repoFileId))) {
					return true;
				}
			}
		} catch (ReviewsFileStorageException e) {
			Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		} catch (CoreException e) {
			Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
		}
		return false;
	}

	public static R4ECompareEditorInput createCompareEditorInput(R4EFileVersion aBaseFileVersion,
			R4EFileVersion aTargetFileVersion, boolean aTargetFileEditable) {

		final CompareConfiguration config = new CompareConfiguration();
		config.setLeftEditable(aTargetFileEditable);
		config.setRightEditable(false);
		config.setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.valueOf(true));

		//NOTE:  We use the workspace file as input if it is in sync with the file to review,
		//		 otherwise we use the file to review that is included in the review repository
		final R4EFileRevisionTypedElement ancestor = null; //Might be improved later
		ITypedElement target = null;
		if (null != aTargetFileVersion) {
			if (CommandUtils.useWorkspaceResource(aTargetFileVersion)) {
				target = new R4EFileTypedElement(aTargetFileVersion);
			} else {
				target = new R4EFileRevisionTypedElement(aTargetFileVersion);
			}
		}
		ITypedElement base = null;
		if (null != aBaseFileVersion) {
			if (CommandUtils.useWorkspaceResource(aBaseFileVersion)) {
				base = new R4EFileTypedElement(aBaseFileVersion);
			} else {
				base = new R4EFileRevisionTypedElement(aBaseFileVersion);
			}
		}
		return new R4ECompareEditorInput(config, ancestor, target, base);
	}
}
