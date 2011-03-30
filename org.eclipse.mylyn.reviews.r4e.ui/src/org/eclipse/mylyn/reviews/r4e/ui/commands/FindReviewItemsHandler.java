// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the context-sensitive command to find review items
 * in the parent project to add to the review
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.model.ICProject;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.FindReviewItemsDialog;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.mylyn.versions.core.Change;
import org.eclipse.mylyn.versions.core.ChangeSet;
import org.eclipse.mylyn.versions.core.ChangeType;
import org.eclipse.mylyn.versions.core.ScmArtifact;
import org.eclipse.mylyn.versions.ui.ScmUi;
import org.eclipse.mylyn.versions.ui.spi.ScmUiConnector;
import org.eclipse.mylyn.versions.ui.spi.ScmUiException;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FindReviewItemsHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) {

		//Get project to use (use adapters if needed)
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final Object selectedElement = ((IStructuredSelection)selection).getFirstElement();
		IProject project = null;
		//NOTE:  The valadity testes are done if the ProjectPropertyTester class
		if (selectedElement instanceof IProject) { 
			project = (IProject) selectedElement;
		} else if (selectedElement instanceof IJavaProject) {
			project = ((IJavaProject)selectedElement).getProject();
		} else if (selectedElement instanceof ICProject) {
			project = ((ICProject)selectedElement).getProject();
		} else if (selectedElement instanceof IPackageFragment || selectedElement instanceof IPackageFragmentRoot) {
			project = ((IJavaElement)selectedElement).getJavaProject().getProject();
		} else if (selectedElement instanceof IFolder) {
			project = ((IFolder)selectedElement).getProject();
		} else if (selectedElement instanceof IAdaptable) {
			final IAdaptable adaptableProject = (IAdaptable) selectedElement; 
			project = (IProject) adaptableProject.getAdapter(IProject.class); 
		} else {
			//Should never happen
			Activator.Ftracer.traceError("No project defined for selection of class " + selectedElement.getClass());
			Activator.getDefault().logError("No project defined for selection of class " + selectedElement.getClass(), null);
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Find Review Item Error",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "No project defined for selection", null), IStatus.ERROR);
			dialog.open();
			return null;
		}
	
		ScmUiConnector uiConnector = ScmUi.getUiConnector(project);
		if (uiConnector != null) {
			ChangeSet changeSet = null;
			try {
				changeSet = uiConnector.getChangeSet(null, project, null);
			} catch (ScmUiException e) {
				Activator.Ftracer.traceError("Exception: " + e.getMessage());
				e.printStackTrace();
				return null;
			}

			createReviewItem(changeSet);
			return null;
		}


		//We could not find any version control system, thus no items

		// TODO: So this code should be removed and replace by a Git connector
		R4EUIModelController.setDialogOpen(true);

		final FindReviewItemsDialog dialog = new FindReviewItemsDialog(R4EUIModelController.getNavigatorView().
				getSite().getWorkbenchWindow().getShell(), project);
    	dialog.open();
    	//Note the review item will be added to the review in the dialog if needed
		R4EUIModelController.setDialogOpen(false);
		
		return null;
	}

	/**
	 * Create and serialize the changeset in a Review Item
	 * 
	 * @param changeSet
	 */
	private void createReviewItem(ChangeSet changeSet) {
		StringBuilder sb = new StringBuilder();
		// TODO Auto-generated method stub
		if (changeSet == null) {
			sb.setLength(0);
			sb.append("Received ChangeSet of null");
			Activator.Ftracer.traceInfo(sb.toString());
			return;
		}
		
		int size = changeSet.getChanges().size();
		sb.setLength(0);
		sb.append("Received ChangeSet with " + size + " elements");
		Activator.Ftracer.traceInfo(sb.toString());

		if (size == 0) {
			// nothing to add
			return;
		}
		
		try {
			// Get the reviewer (i.e. ourselves :-) or create it if it does not exist
			final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
			final String user = R4EUIModelController.getReviewer();
			final R4EParticipant participant = uiReview.getParticipant(user, true);

			// Create a new review item in the serialization model
			final R4EItem reviewItem = R4EUIModelController.FModelExt.createR4EItem(participant);
			// initialize list of project uri's, will be filled as file versions are created.
			Set<String> projectUris = new HashSet<String>(2);

			//For each change
			//- Build File Contexts
			//- Build Base File Version
			//- Build Target File Version
			for (Change change : changeSet.getChanges()) {
				ScmArtifact baseArt = change.getBase();
				ScmArtifact targetArt = change.getTarget();
				
				if (baseArt ==null && targetArt == null) {
					sb.setLength(0);
					sb.append("Received a Change with no base and target in ChangeSet: " + changeSet.getId()
							+ ", Date: " + changeSet.getDate().toString());
					Activator.Ftracer.traceDebug(sb.toString());
				}
				
				// create the file context
				R4EFileContext itemContext = R4EUIModelController.FModelExt.createR4EFileContext(reviewItem);
				R4EContextType type = adaptType(change.getChangeType());
				itemContext.setType(type);
				R4EReview review = uiReview.getReview();

				// Create base
				if (baseArt != null) {
					R4EFileVersion rfileBaseVersion = R4EUIModelController.FModelExt
							.createR4EBaseFileVersion(itemContext);
					IFileRevision baseFileRev = baseArt.getFileRevision(null);
					rfileBaseVersion.setFileRevision(baseFileRev);

					handleFileVersion(review, rfileBaseVersion, baseArt, baseFileRev, projectUris);
				}

				// Create target
				if (targetArt != null) {
					R4EFileVersion rfileTargetVersion = R4EUIModelController.FModelExt
							.createR4ETargetFileVersion(itemContext);
					IFileRevision targetFileRev = targetArt.getFileRevision(null);
					rfileTargetVersion.setFileRevision(targetFileRev);

					handleFileVersion(review, rfileTargetVersion, targetArt,
							targetFileRev, projectUris);
				}
			}

			// Load the Review item with the list of projects involved
			for (String projUri : projectUris) {
				reviewItem.getProjectURIs().add(projUri);
			}

			//Finally, populate UI model with item info
			final R4EUIReviewItem uiReviewItem = new R4EUIReviewItem(uiReview, reviewItem,
					R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT, changeSet, null);
			uiReviewItem.open();
			
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);
		} catch (ReviewsFileStorageException e) {
			UIUtils.displayReviewsFileStorageErrorDialog(e);
		}

	}

	/**
	 * Fetch file from Version control system, push to local review repo and delegate loading values to R4EFileVersion
	 * 
	 * @param review
	 * @param rfileVer
	 * @param scmArt
	 * @param fileRev
	 * @param projectUris
	 * @throws ReviewsFileStorageException
	 */
	private void handleFileVersion(R4EReview review, R4EFileVersion rfileVer, ScmArtifact scmArt,
			IFileRevision fileRev, Set<String> projectUris) throws ReviewsFileStorageException {

		String localId = "";
		try {
			// Pull file from the version control system
			InputStream iStream = fileRev.getStorage(null).getContents();
			// Push a local copy to local review repository, and obtain the local id
			IRFSRegistry revRepo = RFSRegistryFactory.getRegistry(review);
			localId = revRepo.registerReviewBlob(iStream);
			// fill in values in the base file version
			loadVersionValues(rfileVer, scmArt, projectUris, localId);
		} catch (CoreException e) {
			throw new ReviewsFileStorageException(e);
		}

		return;
	}


	/**
	 * Resolve and transfer values from ScmArtifact to R4EFileVersion
	 * 
	 * @param fileVer
	 * @param scmArt
	 * @param projectUris
	 * @param localId
	 */
	private void loadVersionValues(R4EFileVersion fileVer, ScmArtifact scmArt, Set<String> projectUris, String localId) {
		IFileRevision fileRevision = fileVer.getFileRevision();
		StringBuilder sb = new StringBuilder();

		String fname = fileRevision.getName();
		// resolve platform uri
		String projPath = scmArt.getProjectRelativePath();
		String projectName = scmArt.getProjectName();
		IProject project = ResourceUtils.getProject(projectName);
		IResource resource = ResourceUtils.findResource(project, projPath);
		String repoPath = scmArt.getPath();
		String versionId = scmArt.getId();
		String platformURI = ResourceUtils.toPlatformURIStr(project);

		if (projPath == null) {
			sb.setLength(0);
			sb.append("Invalid relative project path in scmArtifact with path: " + repoPath);
			Activator.Ftracer.traceDebug(sb.toString());
		}

		if (platformURI != null) {
			projectUris.add(platformURI);
		} else {
			sb.setLength(0);
			sb.append("Unable to resolve the project: " + projectName + " platform's URI, in scmArtifact with path: "
					+ repoPath);
			Activator.Ftracer.traceDebug(sb.toString());
		}

		fileVer.setLocalVersionID(localId);
		fileVer.setName(fname);
		fileVer.setPlatformURI(projPath);
		fileVer.setRepositoryPath(repoPath);
		fileVer.setResource(resource);
		fileVer.setVersionID(versionId);
	}

	/**
	 * Adapt change types from Mylyn Versions to R4E model
	 * 
	 * @param changeType
	 * @return
	 */
	private R4EContextType adaptType(ChangeType changeType) {
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

}
