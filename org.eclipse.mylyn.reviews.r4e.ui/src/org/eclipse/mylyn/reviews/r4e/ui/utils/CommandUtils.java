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

package org.eclipse.mylyn.reviews.r4e.ui.utils;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.ResourceNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4ECompareItem;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.mylyn.versions.core.ChangeType;
import org.eclipse.mylyn.versions.core.ScmArtifact;
import org.eclipse.mylyn.versions.core.ScmCore;
import org.eclipse.mylyn.versions.core.spi.ScmConnector;
import org.eclipse.team.core.RepositoryProvider;
import org.eclipse.team.core.TeamException;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.core.subscribers.Subscriber;
import org.eclipse.team.core.synchronize.SyncInfo;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class CommandUtils {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getTargetFileURI.
	 * @return URI 
	 * @throws URISyntaxException 
	 * @throws CoreException
	 * @throws ReviewsFileStorageException 
	 */
	public static ScmArtifact getTargetFileData(AtomicReference<String> aVersionId) throws CoreException, ReviewsFileStorageException {
		final IEditorInput input = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput(); // $codepro.audit.disable methodChainLength
		IFile editorFile = null;   //The file we use in the current editor/workspace
		if (input instanceof IFileEditorInput) {
			editorFile = ((IFileEditorInput) input).getFile();
			//Now we have the file in editor, we need to use the versions interface to see if we should copy it
			//to the local repo and update model info
			return updateTargetFile(editorFile, aVersionId);
		} else if (input instanceof R4ECompareEditorInput) {
			//If we get here, this is because we are trying to act on the compare editor contents
			//this means that the file we are acting on is already in the local repository
			//in this case, we only need to provide the versionId of this file
			aVersionId.set(((R4ECompareEditorInput)input).getLeftElementVersion());
			return null;
		} else {
			//Should never happen
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, 
					"Invalid input " + input.getClass().toString() , null));
		}
	}
	
	/**
	 * Method updateTargetFile.
	 * @param aFile IFile
	 * @return IFile 
	 * @throws CoreException
	 * @throws ReviewsFileStorageException 
	 */
	public static ScmArtifact updateTargetFile(IFile aFile,
			AtomicReference<String> aVersionId) throws CoreException,
			ReviewsFileStorageException {

		// Get handle to local storage repository
		IRFSRegistry localRepository = RFSRegistryFactory
				.getRegistry(R4EUIModelController.getActiveReview().getReview());
		String workspaceFileVersion = localRepository.blobIdFor(aFile
				.getContents());

		// Get Remote repository file info
		ScmConnector connector = ScmCore.getConnector(aFile.getProject());
		if (null != connector) {

			ScmArtifact artifact = connector.getArtifact(aFile);
			if (null != artifact) { // No remote file detected, just return the
									// workspace file

				// Check if the file in workspace if the same as the file in the
				// remote repository
				if (artifact.getId().equals(workspaceFileVersion)) {
					// Files are different, so fetch file from remote repo and
					// copy it to the temp work area
					aVersionId.set(copyRemoteFileToLocalRepository(
							localRepository, artifact));
					return artifact;
				}
				// else Files are the same so we can copy the file in the
				// workspace to the local repository, see below
			}
		} // else this is the case where files are not in source control

		// The current file was modified by the user, so we need to copy the
		// current file in the workspace to the work area
		aVersionId.set(copyWorkspaceFileToLocalRepository(localRepository,
				aFile));
		return null;
	}

	/**
	 * Method getBaseFileURI.
	 * @return IFile 
	 * @throws URISyntaxException
	 * @throws CoreException
	 * @throws ReviewsFileStorageException 
	 */
	public static ScmArtifact getBaseFileData(AtomicReference<String> aVersionId) throws CoreException, ReviewsFileStorageException {
		final IEditorInput input = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor().getEditorInput(); // $codepro.audit.disable methodChainLength
		IFile editorFile = null;   //The file we use in the current editor/workspace
		if (input instanceof IFileEditorInput) {
			editorFile = ((IFileEditorInput) input).getFile();
			//Now we have the file in editor, we need to use the versions interface to see if we should copy it
			//to the local repo and update model info
			return updateBaseFile(editorFile, aVersionId);
		} else if (input instanceof R4ECompareEditorInput) {
			//If we get here, this is because we are trying to act on the compare editor contents
			//this means that the file we are acting on is already in the local repository
			//in this case, we only need to provide the versionId of this file
			aVersionId.set(((R4ECompareEditorInput)input).getRightElementVersion());
			return null;
		} else {
			//Should never happen
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, 
					"Invalid input " + input.getClass().toString() , null));
		}
	}
	
	/**
	 * Method updateBaseFile.
	 * @param aFile IFile
	 * @return IFile 
	 * @throws CoreException
	 * @throws ReviewsFileStorageException 
	 */
	public static ScmArtifact updateBaseFile(IFile aFile, AtomicReference<String> aVersionId) throws CoreException, ReviewsFileStorageException {
		if (!isFileInSynch(aFile)) { 

			// Get handle to local storage repository
			IRFSRegistry localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview().getReview());

			//Get Remote repository file info
			ScmConnector connector = ScmCore.getConnector(aFile.getProject());
			if (null != connector) {
				ScmArtifact artifact = connector.getArtifact(aFile);
				if (null != artifact) {
					//File was modified, so we need to fetch the base file from the versions repository and copy it to our own local repository
					aVersionId.set(copyRemoteFileToLocalRepository(localRepository, artifact));
					return artifact;
				}
			}
		}
		//File was not modified, or No Version Control System or Remote File detected, so there is no base
		aVersionId.set("");
		return null;
	}

	/**
	 * Method isFileInSynch.
	 * @param aFile IFile
	 * @return boolean 
	 * @throws TeamException
	 */
	public static boolean isFileInSynch(IFile aFile) throws TeamException {
		// TODO: Later optimization
		// RepositoryProvider provider =
		// RepositoryProvider.getProvider(aFile.getProject());
		// Subscriber subscriber = provider.getSubscriber();
		// SyncInfo info = subscriber.getSyncInfo(aFile);
		// return SyncInfo.isInSync(info.getKind());
		return false;
	}
	
	/**
	 * Method copyRemoteFileToLocalRepository.
	 * @param aLocalRepository IRFSRegistry
	 * @param aArtifact ScmArtifact
	 * @return IFile 
	 * @throws CoreException
	 * @throws ReviewsFileStorageException 
	 */
	public static String copyRemoteFileToLocalRepository(IRFSRegistry aLocalRepository, ScmArtifact aArtifact) 
	throws CoreException, ReviewsFileStorageException {

		IFileRevision fileRev = aArtifact.getFileRevision(null);
		
		// Pull file from the version control system
		InputStream iStream = fileRev.getStorage(null).getContents();
		
		// Push a local copy to local review repository, and obtain the local id
		return aLocalRepository.registerReviewBlob(iStream);
	}
	
	/**
	 * Method copyWorkspaceFileToLocalRepository.
	 * @param aLocalRepository IRFSRegistry
	 * @param aFile IFile
	 * @return IFile 
	 * @throws CoreException
	 * @throws ReviewsFileStorageException 
	 */
	public static String copyWorkspaceFileToLocalRepository(IRFSRegistry aLocalRepository, IFile aFile)
	throws CoreException, ReviewsFileStorageException {
		return aLocalRepository.registerReviewBlob(aFile.getContents());
	}
	
	/**
	 * Method copyWorkspaceFileToLocalRepository.
	 * @param aStream InputStream
	 * @param aFilename String
	 * @return IFile 
	 * @throws CoreException
	 */
	public static IFile createTempFile(InputStream aStream, String aFilename) throws CoreException {
		
		IFile file = null;
		
		//TODO For now we use a dummy project in the workspace to store the temp files.  This should be improved later
		//IPath path = Activator.getDefault().getStateLocation().addTrailingSeparator().append("temp");
		//IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(R4EUIConstants.R4E_TEMP_PROJECT);
		if (!project.exists()) project.create(null);
		if (!project.isOpen()) project.open(null);
		final IFolder folder = project.getFolder(R4EUIConstants.R4E_TEMP_FOLDER);
		if (!folder.exists()) folder.create(IResource.NONE, true, null);
		file = folder.getFile(aFilename);
		
		//Always start from fresh copy because we never know what the temp file version is
		if (file.exists()) file.delete(true, null);
		file.create(aStream, IResource.NONE, null);
		
		return file;
	}
	
	/**
	 * Method getPosition.
	 * @param aSelection ITextSelection
	 * @return TextPosition
	 */
	public static R4EUITextPosition getPosition(ITextSelection aSelection) {
		return new R4EUITextPosition(aSelection.getOffset(), (aSelection).getLength(), 
				(aSelection).getStartLine(), (aSelection).getEndLine());
	}
	
	/**
	 * Method getPosition.
	 * 		Get position for generic workspace files
	 * @param aSelectedElement IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(IFile aSelectedElement) throws CoreException { // $codepro.audit.disable overloadedMethods
		final R4EUITextPosition position = new R4EUITextPosition(R4EUIConstants.NO_OFFSET, R4EUIConstants.INVALID_VALUE, aSelectedElement);
		position.setName(aSelectedElement.getName());
		return position;
	}
	
	/**
	 * Method getPosition.
	 * 		Get position for workspace java source files
	 * @param aSelectedElement org.eclipse.jdt.core.ISourceReference
	 * @param aFile IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(org.eclipse.jdt.core.ISourceReference aSelectedElement, IFile aFile) 
		throws CoreException {
		final R4EUITextPosition position = new R4EUITextPosition(aSelectedElement.getSourceRange().getOffset(), 
				aSelectedElement.getSourceRange().getLength(), aFile);
		position.setName(((IJavaElement)aSelectedElement).getElementName());
		return position;	
	}

	/**
	 * Method getPosition.
	 * 		Get position for workspace C and C++ source files
	 * @param aSelectedElement org.eclipse.cdt.core.model.ISourceReference
	 * @param aFile IFile
	 * @return TextPosition
	 * @throws CoreException
	 */
	public static R4EUITextPosition getPosition(org.eclipse.cdt.core.model.ISourceReference aSelectedElement, IFile aFile)  // $codepro.audit.disable overloadedMethods
		throws CoreException {
		final R4EUITextPosition position = new R4EUITextPosition(aSelectedElement.getSourceRange().getStartPos(), 
				aSelectedElement.getSourceRange().getLength(), aFile);
		position.setStartLine(aSelectedElement.getSourceRange().getStartLine());
		position.setEndLine(aSelectedElement.getSourceRange().getEndLine());
		position.setName(((ICElement)aSelectedElement).getElementName());
		return position;
	}
	
	/**
	 * Adapt change types from Mylyn Versions to R4E model
	 * 
	 * @param changeType
	 * @return
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
	 * @param fileVer
	 * @param scmArt
	 * @param projectUris
	 * @param localId
	 */
	public static String setFileVersionData(R4EFileVersion aFileVer, ScmArtifact aScmArt) {
		
		aFileVer.setName(aScmArt.getFileRevision(null).getName());
		aFileVer.setVersionID(aScmArt.getId());
		aFileVer.setRepositoryPath(aScmArt.getPath());

		String projPath = aScmArt.getProjectRelativePath();
		if (projPath == null) {
			Activator.Ftracer.traceDebug("Invalid relative project path in scmArtifact with path: " + 
					aScmArt.getPath());
		}
		IProject project = ResourceUtils.getProject(aScmArt.getProjectName());
		IResource resource = ResourceUtils.findResource(project, projPath);
		
		aFileVer.setPlatformURI(ResourceUtils.toPlatformURIStr(resource));
		aFileVer.setResource(resource);
		
		String projPlatformURI = ResourceUtils.toPlatformURIStr(project);
		if (projPlatformURI != null) {
			return projPlatformURI;
		}
		Activator.Ftracer.traceDebug("Unable to resolve the project: " + aScmArt.getProjectName() + 
				" platform's URI, in scmArtifact with path: " + aScmArt.getPath());
		return null;
	}
}
