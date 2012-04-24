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

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.compare.rangedifferencer.RangeDifference;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.frame.core.utils.Tracer;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContextType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.IRFSRegistry;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.RFSRegistryFactory;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIDeltaContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.Diff;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.DiffUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.MailServicesProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.versions.core.Change;
import org.eclipse.mylyn.versions.core.ChangeSet;
import org.eclipse.mylyn.versions.core.ScmArtifact;
import org.eclipse.mylyn.versions.ui.spi.ScmConnectorUi;
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
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		// Get project to use (use adapters if needed)
		final ISelection selection = HandlerUtil.getCurrentSelection(aEvent);
		if (selection instanceof IStructuredSelection) {
			final Object selectedElement = ((IStructuredSelection) selection).getFirstElement();
			IProject project = null;

			// NOTE: The validity testes are done if the ProjectPropertyTester class
			if (selectedElement instanceof IProject) {
				project = (IProject) selectedElement;
			} else if (R4EUIPlugin.isJDTAvailable() && selectedElement instanceof IJavaProject) {
				project = ((IJavaProject) selectedElement).getProject();
			} else if (R4EUIPlugin.isCDTAvailable() && selectedElement instanceof org.eclipse.cdt.core.model.ICProject) {
				project = ((org.eclipse.cdt.core.model.ICProject) selectedElement).getProject();
			} else if (selectedElement instanceof IPackageFragment || selectedElement instanceof IPackageFragmentRoot) {
				project = ((IJavaElement) selectedElement).getJavaProject().getProject();
			} else if (selectedElement instanceof IFolder) {
				project = ((IFolder) selectedElement).getProject();
			} else if (selectedElement instanceof IAdaptable) {
				final IAdaptable adaptableProject = (IAdaptable) selectedElement;
				project = (IProject) adaptableProject.getAdapter(IProject.class);
			} else {
				// Should never happen
				R4EUIPlugin.Ftracer.traceError("No project defined for selection of class "
						+ selectedElement.getClass());
				R4EUIPlugin.getDefault().logError(
						"No project defined for selection of class " + selectedElement.getClass(), null);
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Find Review Item Error", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								"No project defined for selection", null), IStatus.ERROR);
				dialog.open();
				return null;
			}

			final ScmConnectorUi uiConnector = R4EUIDialogFactory.getInstance().getScmUiConnector(project);
			if (null != uiConnector) {
				R4EUIPlugin.Ftracer.traceDebug("Resolved Scm Ui connector: " + uiConnector);
				final ChangeSet changeSet = uiConnector.getChangeSet(null, project);
				createReviewItem(aEvent, changeSet);
			} else {
				// We could not find any version control system, thus no items
				final String strProject = ((null == project) ? "null" : project.getName());
				R4EUIPlugin.Ftracer.traceDebug("No Scm Ui connector found for project: " + strProject);
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING,
						"Cannot find new Review Items", new Status(IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, 0,
								"No SCM Connector detected for Project " + strProject, null), IStatus.WARNING);
				dialog.open();
			}
		}
		return null;
	}

	/**
	 * Create and serialize the changeset in a Review Item
	 * 
	 * @param aChangeSet
	 *            ChangeSet
	 * @param aEvent
	 *            ExecutionEvent
	 */
	private void createReviewItem(final ExecutionEvent aEvent, final ChangeSet aChangeSet) {

		if (null == aChangeSet) {
			R4EUIPlugin.Ftracer.traceInfo("Received null ChangeSet");
			return;
		}

		final int size = aChangeSet.getChanges().size();
		R4EUIPlugin.Ftracer.traceInfo("Received ChangeSet with " + size + " elements");
		if (0 == size) {
			return; // nothing to add
		}

		//Check if Review Item already exists
		final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
		for (R4EUIReviewItem uiItem : uiReview.getReviewItems()) {
			if (aChangeSet.getId().equals(uiItem.getItem().getRepositoryRef())) {
				//The commit item already exists so ignore command
				R4EUIPlugin.Ftracer.traceWarning("Review Item already exists.  Ignoring");
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING,
						"Cannot add Review Item", new Status(IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, 0,
								"Review Item already exists", null), IStatus.WARNING);
				dialog.open();
				return;
			}
		}

		final IRFSRegistry localRepository;
		try {
			// Get handle to local storage repository
			localRepository = RFSRegistryFactory.getRegistry(R4EUIModelController.getActiveReview().getReview());

			//Create Synchronized list that will temporarly hold the elements to be added
			final List<TempFileContext> filesToAddlist = Collections.synchronizedList(new ArrayList());

			final Job job = new Job("Importing Files and Calculating Changes...") {
				@Override
				public IStatus run(IProgressMonitor aMonitor) {
					R4EUIModelController.setJobInProgress(true); //Disable operations on UI
					aMonitor.beginTask("Importing Files and Calculating Changes...", aChangeSet.getChanges().size() * 2);
					//Prepare to collect performance trace information if enabled.
					Date startUpdatingModel = null;
					Date startImportingTime = null;
					if (Tracer.isInfo()) {
						startImportingTime = new Date();
					}

					//Since importing files and calculating delta can take a while, we run this in a parallel job.  When it completes
					//We update the UI with the new elements
					for (final Change change : aChangeSet.getChanges()) {
						TempFileContext fetchedFile = fetchFiles(change, localRepository, aMonitor);
						if (null != fetchedFile) {
							filesToAddlist.add(fetchedFile);
						}
						aMonitor.worked(1);

						//If the task is cancelled, we break here and set currently imported elements
						if (aMonitor.isCanceled()) {
							aMonitor.done();
							R4EUIModelController.setJobInProgress(false);
							return Status.CANCEL_STATUS;
						}
					}

					if (startImportingTime != null) {
						R4EUIPlugin.Ftracer.traceInfo("Total time to Import/Push files and Compute changes is: " //$NON-NLS-1$
								+ ((new Date()).getTime() - startImportingTime.getTime()));
					}

					//Now add elements to the UI model if there are any elements to add
					synchronized (filesToAddlist) {
						try {
							final R4EUIReviewItem uiReviewItem;
							if (filesToAddlist.size() > 0) {
								String strSubtask = "Adding Review Item to R4E model"; //$NON-NLS-1$
								aMonitor.subTask(strSubtask);
								if (Tracer.isInfo()) {
									startUpdatingModel = new Date();
								}

								uiReviewItem = uiReview.createCommitReviewItem(aChangeSet, null);
								Resource resource = uiReviewItem.getItem().eResource();
								//Lock the resource to the user review items to avoid parallel updates from other users
								final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(
										uiReviewItem.getItem(), R4EUIModelController.getReviewer());

								//Prevent serialization for each individual child element and wait till the end
								R4EUIModelController.stopSerialization(resource);

								for (TempFileContext file : filesToAddlist) {
									try {
										String addedFilename;
										if (null != file.getTarget()) {
											addedFilename = file.getTarget().getName();
										} else if (null != file.getBase()) {
											addedFilename = file.getBase().getName();
										} else {
											addedFilename = ""; //Should never happen
										}
										aMonitor.subTask("Adding file " + addedFilename + " to R4E model");
										final R4EUIFileContext uiFileContext = uiReviewItem.createFileContext(
												file.getBase(), file.getTarget(), file.getType());

										for (IR4EUIPosition position : file.getPositions()) {
											//Lazily create the Delta container if not already done
											R4EUIDeltaContainer deltaContainer = (R4EUIDeltaContainer) uiFileContext.getContentsContainerElement();
											deltaContainer.createDelta((R4EUITextPosition) position);
										}
									} catch (OutOfSyncException e) {
										R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " ("
												+ e.getMessage() + ")");
										R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
									} catch (ResourceHandlingException e) {
										R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " ("
												+ e.getMessage() + ")");
										R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
									}
									aMonitor.worked(1);

									//If the task is cancelled, we break here and set currently imported elements
									if (aMonitor.isCanceled()) {
										aMonitor.done();
										R4EUIModelController.setJobInProgress(false);
										return Status.CANCEL_STATUS;
									}
								}

								//Resume serialization
								R4EUIModelController.resetToDefaultSerialization();

								//Check-in to serialise the whole commit element with children
								R4EUIModelController.FResourceUpdater.checkIn(bookNum);

								R4EUIModelController.setJobInProgress(false);
								UIUtils.setNavigatorViewFocus(uiReviewItem, 1);

								//Notify users if need be
								final List<R4EReviewComponent> addedItems = new ArrayList<R4EReviewComponent>();
								addedItems.add(uiReviewItem.getItem());
								final R4EReview review = uiReview.getReview();
								if (review.getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
									if (((R4EFormalReview) review).getCurrent()
											.getType()
											.equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
										MailServicesProxy.sendItemsAddedNotification(addedItems);

									}
								}

								if (startUpdatingModel != null) {
									Date jobEnd = new Date();
									R4EUIPlugin.Ftracer.traceInfo("Total time to " + strSubtask + " is: " //$NON-NLS-1$ //$NON-NLS-2$
											+ (jobEnd.getTime() - startUpdatingModel.getTime()));
									if (startImportingTime != null) {
										R4EUIPlugin.Ftracer.traceInfo("Total time to fetch files, compute deltas and update model is: " //$NON-NLS-1$
												+ (jobEnd.getTime() - startImportingTime.getTime()));
									}
								}
							}
						} catch (CoreException e) {
							UIUtils.displayCoreErrorDialog(e);
						} catch (ResourceHandlingException e) {
							UIUtils.displayResourceErrorDialog(e);
						} catch (OutOfSyncException e) {
							UIUtils.displaySyncErrorDialog(e);
						} finally {
							//Minimise the possibility to remain with serialization off 
							R4EUIModelController.resetToDefaultSerialization();
						}
					}
					R4EUIModelController.setJobInProgress(false);
					aMonitor.done();
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();
		} catch (ReviewsFileStorageException e) {
			UIUtils.displayReviewsFileStorageErrorDialog(e);
		}
	}

	/**
	 * Method fetchFiles.
	 * 
	 * @param aChange
	 *            Change
	 * @param aLocalRepository
	 *            IRFSRegistry
	 * @param aMonitor
	 *            IProgressMonitor
	 * @return TempFileContext
	 * @throws CoreException
	 */
	private TempFileContext fetchFiles(final Change aChange, IRFSRegistry aLocalRepository, IProgressMonitor aMonitor) {

		TempFileContext file = null;

		aMonitor.subTask("Getting base and target artifacts.");
		final ScmArtifact baseArt = aChange.getBase();
		final ScmArtifact targetArt = aChange.getTarget();
		if (null != baseArt || null != targetArt) {
			try {
				// Copy remote files to the local repository
				R4EFileVersion baseLocalVersion = null;
				R4EFileVersion targetLocalVersion = null;
				if (null != baseArt) {
					aMonitor.subTask("Fetching file " + baseArt.getPath() + " (base) from remote repository");
					baseLocalVersion = CommandUtils.copyRemoteFileToLocalRepository(aLocalRepository, baseArt);
				}
				if (null != targetArt) {
					aMonitor.subTask("Fetching file " + targetArt.getPath() + " (target) from remote repository");
					targetLocalVersion = CommandUtils.copyRemoteFileToLocalRepository(aLocalRepository, targetArt);
				}

				// Add File Context to the list to be added
				file = new TempFileContext(aLocalRepository, baseLocalVersion, targetLocalVersion,
						CommandUtils.adaptType(aChange.getChangeType()));

				//If configured, get deltas for this file
				if (R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_USE_DELTAS)) {
					aMonitor.subTask("Computing differences for file " + file.toString());
					Date startingComputeTime = null;
					if (Tracer.isInfo()) {
						startingComputeTime = new Date();
					}
					updateFilesWithDeltas(file);

					if (startingComputeTime != null) {
						R4EFileVersion fileVersion = file.getTarget();
						if (null == fileVersion) {
							fileVersion = file.getBase();
						}

						if (fileVersion != null) {
							R4EUIPlugin.Ftracer.traceInfo("Computed deltas for: " + fileVersion.getName() + ", (ms): " //$NON-NLS-1$ //$NON-NLS-2$
									+ (new Date().getTime() - startingComputeTime.getTime()));
						}
					}

				}
			} catch (final ReviewsFileStorageException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			} catch (final CoreException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			}
		}
		return file;
	}

	/**
	 * Method updateFilesWithDeltas.
	 * 
	 * @param aFile
	 *            TempFileContext
	 * @throws CoreException
	 */
	private void updateFilesWithDeltas(final TempFileContext aFile) throws CoreException {

		//Find all differecences between Base and Target files
		final R4ECompareEditorInput input = CommandUtils.createCompareEditorInput(aFile.getBase(), aFile.getTarget());
		input.prepareCompareInputNoEditor();

		final DiffUtils diffUtils = new DiffUtils();
		final List<Diff> diffs;

		diffs = diffUtils.doDiff(false, true, input);

		//Add Deltas from the list of differences
		for (Diff diff : diffs) {
			IR4EUIPosition position = CommandUtils.getPosition(diff.getPosition(R4EUIConstants.LEFT_CONTRIBUTOR)
					.getOffset(), diff.getPosition(R4EUIConstants.LEFT_CONTRIBUTOR).getLength(),
					diff.getDocument(R4EUIConstants.LEFT_CONTRIBUTOR));

			if (null == position || RangeDifference.NOCHANGE == diff.getKind()) {
				continue; //Cannot resolve position for this delta or no change
			}
			aFile.getPositions().add(position);
		}
	}

	/**
	 * @author lmcdubo
	 */
	private static class TempFileContext {
		/**
		 * Field base.
		 */
		private final R4EFileVersion fBase;

		/**
		 * Field target.
		 */
		private final R4EFileVersion fTarget;

		/**
		 * Field type.
		 */
		private final R4EContextType fType;

		/**
		 * Field positions.
		 */
		private final List<IR4EUIPosition> fPositions;

		/**
		 * Constructor for TempFileContext.
		 * 
		 * @param aRepository
		 *            IRFSRegistry
		 * @param aBase
		 *            R4EFileVersion
		 * @param aTarget
		 *            R4EFileVersion
		 * @param aType
		 *            R4EContextType
		 */
		TempFileContext(IRFSRegistry aRepository, R4EFileVersion aBase, R4EFileVersion aTarget, R4EContextType aType) {
			fBase = aBase;
			//Add IFileRevision info
			if (null != fBase && null != aRepository) {
				try {
					final IFileRevision fileRev = aRepository.getIFileRevision(null, fBase);
					fBase.setFileRevision(fileRev);
				} catch (ReviewsFileStorageException e) {
					R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				}
			}

			fTarget = aTarget;
			//Add IFileRevision info
			if (null != fTarget && null != aRepository) {
				try {
					final IFileRevision fileRev = aRepository.getIFileRevision(null, fTarget);
					fTarget.setFileRevision(fileRev);
				} catch (ReviewsFileStorageException e) {
					R4EUIPlugin.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				}
			}

			fType = aType;
			fPositions = new ArrayList<IR4EUIPosition>();
		}

		/**
		 * Method getBase.
		 * 
		 * @return R4EFileVersion
		 */
		public R4EFileVersion getBase() {
			return fBase;
		}

		/**
		 * Method getTarget.
		 * 
		 * @return R4EFileVersion
		 */
		public R4EFileVersion getTarget() {
			return fTarget;
		}

		/**
		 * Method getType.
		 * 
		 * @return R4EContextType
		 */
		public R4EContextType getType() {
			return fType;
		}

		/**
		 * Method getPositions.
		 * 
		 * @return List<IR4EUIPosition>
		 */
		public List<IR4EUIPosition> getPositions() {
			return fPositions;
		}
	}
}