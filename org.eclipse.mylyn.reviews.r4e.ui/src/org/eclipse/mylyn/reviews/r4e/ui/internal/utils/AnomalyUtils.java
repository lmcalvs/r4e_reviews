// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements various utility methods used in Anomaly handling
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.utils;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ISourceReference;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.spi.ReviewsFileStorageException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;

/**
 * This class implements various utility methods used in anomaly-related commands
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class AnomalyUtils {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field WARNING_BUTTONS_LABELS.
	 */
	private static final String[] WARNING_BUTTONS_LABELS = { "Continue", "Cancel" }; //$NON-NLS-1$

	/**
	 * Field VERSION_STR. (value is ""Version: "")
	 */
	private static final String VERSION_STR = "Version: ";

	/**
	 * Field QUESTION_TITLE. (value is ""R4E question"")
	 */
	private static final String QUESTION_TITLE = "R4E question";

	/**
	 * Field WORKSPACE_FILE_STR. (value is ""Workspace file: "")
	 */
	private static final String WORKSPACE_FILE_STR = "Workspace file: ";

	/**
	 * Field FILE_VERSION_STR. (value is ""Selected file version to review: "")
	 */
	private static final String FILE_VERSION_STR = "Selected file version to review: ";

	/**
	 * Field QUESTION_STR. (value is ""Are you sure you want to add this anomaly to the workspace file ?"")
	 */
	private static final String QUESTION_STR = "Are you sure you want to add this anomaly to the workspace file ?";

	/**
	 * Field MESSAGE_STR. (value is ""You are adding an anomaly to a file version which is different from the one
	 * selected for review."")
	 */
	private static final String MESSAGE_STR = "You are adding an anomaly to a file version which is different from the one selected for review.";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method addAnomalyFromText.
	 * 
	 * @param aSelection
	 *            ITextSelection
	 * @param aInput
	 *            - IEditorInput
	 */
	public static void addAnomalyFromText(ITextSelection aSelection, IEditorInput aInput, boolean aClone) {
		//This is a text selection in a text editor, we need to get the file path and
		//the position of the selection within the file
		try {
			final R4EUITextPosition position = CommandUtils.getPosition(aSelection);
			final R4EFileVersion baseVersion = CommandUtils.getBaseFileData(aInput);
			final R4EFileVersion targetVersion = CommandUtils.getTargetFileData(aInput);

			//Add anomaly to model
			if (null != targetVersion) {
				addAnomaly(baseVersion, targetVersion, position, aClone);
			} else {
				R4EUIPlugin.Ftracer.traceWarning("Trying to add review item to base file");
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Add Anomaly Error", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								"No Target File present to Add Anomaly", null), IStatus.ERROR);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						dialog.open();
					}
				});
			}
		} catch (CoreException e) {
			UIUtils.displayCoreErrorDialog(e);
		} catch (ReviewsFileStorageException e) {
			UIUtils.displayReviewsFileStorageErrorDialog(e);
		}
	}

	/**
	 * Method addAnomalyFromTree.
	 * 
	 * @param aSelection
	 *            ITreeSelection
	 * @param aMonitor
	 *            IProgressMonitor
	 */
	public static void addAnomalyFromTree(Object aSelection, IProgressMonitor aMonitor, boolean aClone) {

		//This is a selection from the tree view (e.g. Review Navigator, Package Explorer etc...)
		//We will need to get the parent file path and the position of the element in a text editor
		//If the selection is on the File itself, then the selection will include all the lines
		//in the file.  Otherwise it will include all the lines corresponding to the currently 
		//selected element	
		try {

			R4EUITextPosition position = null;
			IFile workspaceFile = null;

			if (aSelection instanceof IFile) {
				position = CommandUtils.getPosition((IFile) aSelection);
				workspaceFile = (IFile) aSelection;
			} else if (R4EUIPlugin.isJDTAvailable() && aSelection instanceof ISourceReference) {
				//NOTE:  This is always true because all elements that implement ISourceReference
				//       also implement IJavaElement.  The resource is always an IFile
				workspaceFile = (IFile) ((IJavaElement) aSelection).getResource();
				//TODO is that the right file to get the position???
				position = CommandUtils.getPosition((ISourceReference) aSelection, workspaceFile);
			} else if (R4EUIPlugin.isCDTAvailable()
					&& aSelection instanceof org.eclipse.cdt.core.model.ISourceReference) {
				//NOTE:  This is always true because all elements that implement ISourceReference
				//       also implement ICElement.  The resource is always an IFile
				if (aSelection instanceof org.eclipse.cdt.core.model.ITranslationUnit) {
					workspaceFile = (IFile) ((org.eclipse.cdt.core.model.ICElement) aSelection).getResource();
				} else if (aSelection instanceof org.eclipse.cdt.core.model.ICElement) {
					workspaceFile = (IFile) ((org.eclipse.cdt.core.model.ICElement) aSelection).getParent()
							.getResource();
				} else {
					//This should never happen
					R4EUIPlugin.Ftracer.traceWarning("Invalid selection " + aSelection.getClass().toString()
							+ ".  Ignoring");
					return;
				}
				//TODO is that the right file to get the position???
				position = CommandUtils.getPosition((org.eclipse.cdt.core.model.ISourceReference) aSelection,
						workspaceFile);
			} else {
				//This should never happen
				R4EUIPlugin.Ftracer.traceWarning("Invalid selection " + aSelection.getClass().toString()
						+ ".  Ignoring");
				return;
			}

			//Add anomaly to model
			final R4EFileVersion baseVersion = CommandUtils.updateBaseFile(workspaceFile);
			final R4EFileVersion targetVersion = CommandUtils.updateTargetFile(workspaceFile);

			//Add anomaly to model
			if (null != targetVersion) {
				aMonitor.subTask("Adding " + targetVersion.getName());
				addAnomaly(baseVersion, targetVersion, position, aClone);
				aMonitor.worked(1);
			} else {
				R4EUIPlugin.Ftracer.traceWarning("Trying to add review item to base file");
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Add Anomaly Error", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								"No Target File present to Add Anomaly", null), IStatus.ERROR);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						dialog.open();
					}
				});
			}
		} catch (CoreException e) {
			UIUtils.displayCoreErrorDialog(e);
		} catch (ReviewsFileStorageException e) {
			UIUtils.displayReviewsFileStorageErrorDialog(e);
		}
	}

	/**
	 * Method AddAnomaly. Adds an anomaly to the model based on user input
	 * 
	 * @param aBaseFileVersion
	 *            R4EFileVersion
	 * @param aTargetFileVersion
	 *            R4EFileVersion
	 * @param aUIPosition
	 *            IR4EUIPosition
	 */
	public static void addAnomaly(R4EFileVersion aBaseFileVersion, final R4EFileVersion aTargetFileVersion,
			IR4EUIPosition aUIPosition, final boolean aClone) {

		R4EUIFileContext tempFileContext = null;
		//Check if the file element and/or anomaly already exist
		//If file exists, add anomaly element to it
		//if anomaly element already exist, add a new comment to it
		//for all other cases, create the parent elements as needed as well.
		final List<R4EUIReviewItem> reviewItems = R4EUIModelController.getActiveReview().getReviewItems();

		for (R4EUIReviewItem reviewItem : reviewItems) {
			R4EUIFileContext[] files = (R4EUIFileContext[]) reviewItem.getChildren();
			for (R4EUIFileContext file : files) {
				if (null != file.getFileContext().getTarget()
						&& aTargetFileVersion.getLocalVersionID().equals(
								file.getFileContext().getTarget().getLocalVersionID())) {

					//File already exists, add anomaly to it
					R4EUIAnomalyContainer anomalyContainer = file.getAnomalyContainerElement();
					addAnomalyToExistingFileContext(file, anomalyContainer, aUIPosition, aClone);
					R4EUIPlugin.Ftracer.traceInfo("Added anomaly: Target = "
							+ file.getFileContext().getTarget().getName()
							+ ((null != file.getFileContext().getBase()) ? "Base = "
									+ file.getFileContext().getBase().getName() : "") + " Position = "
							+ aUIPosition.toString());
					return; //We found the file so we are done here	
				} else if (null != file.getFileContext().getTarget()) {
					//Test if we find both file in the workspace
					String reviewPlatformURI = file.getFileContext().getTarget().getPlatformURI();
					String targetPlatformURI = aTargetFileVersion.getPlatformURI();
					if (null != reviewPlatformURI && null != targetPlatformURI) {
						//Now we can compare the path
						if (reviewPlatformURI.equals(targetPlatformURI)) {
							//Found the same file but not the same version
							tempFileContext = file;
						}
					}
				}
			}
		}

		//Ask a question to see if the end-user wants to continue or not
		if (null != tempFileContext) {
			//The file exist with a different file version
			final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
			final R4EUIFileContext dContext = tempFileContext;
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					final MessageDialog dialog = displayDifferentFileVersionDialog(aTargetFileVersion, dContext);
					result[0] = dialog.open();
				}
			});
			if (result[0] == Window.CANCEL) {
				// Cancel selected, so just exit here and do not add anomaly
				return;
			}
		}

		//This is a new file create it (and its parent reviewItem) and all its children
		addAnomalyToNewFileContext(aBaseFileVersion, aTargetFileVersion, aUIPosition, aClone);
		R4EUIPlugin.Ftracer.traceInfo("Added Anomaly: Target = "
				+ aTargetFileVersion.getName()
				+ "_"
				+ aTargetFileVersion.getVersionID()
				+ ((null != aBaseFileVersion) ? "Base = " + aBaseFileVersion.getName() + "_"
						+ aBaseFileVersion.getVersionID() : "") + " Position = " + aUIPosition.toString());
	}

	/**
	 * Method displayDifferentFileVersionDialog.
	 * 
	 * @param aTargetFileVersion
	 *            R4EFileVersion
	 * @param aTempFileContext
	 *            R4EUIFileContext
	 * @return MessageDialog
	 */
	private static MessageDialog displayDifferentFileVersionDialog(R4EFileVersion aTargetFileVersion,
			R4EUIFileContext aTempFileContext) {

		//The file exist with a different file version
		final String wsFileName = aTargetFileVersion.getRepositoryPath();
		final String wsFileVersion = aTargetFileVersion.getVersionID();
		final String riFileName = aTempFileContext.getTargetFileVersion().getRepositoryPath();
		final String riFileVersion = aTempFileContext.getTargetFileVersion().getVersionID();

		final StringBuilder sb = new StringBuilder();
		sb.append(MESSAGE_STR + R4EUIConstants.LINE_FEED + R4EUIConstants.LINE_FEED);
		sb.append(FILE_VERSION_STR);
		sb.append(riFileName + R4EUIConstants.LINE_FEED);
		sb.append(VERSION_STR);
		sb.append(riFileVersion + R4EUIConstants.LINE_FEED + R4EUIConstants.LINE_FEED);
		sb.append(WORKSPACE_FILE_STR);
		sb.append(wsFileName + R4EUIConstants.LINE_FEED);
		sb.append(VERSION_STR);
		sb.append(wsFileVersion + R4EUIConstants.LINE_FEED + R4EUIConstants.LINE_FEED);
		sb.append(QUESTION_STR);

		final MessageDialog dialog = new MessageDialog(null, // Shell
				QUESTION_TITLE, // Dialog title
				null, // Dialog title image message
				sb.toString(), // Dialog message
				MessageDialog.WARNING, // Dialog type
				WARNING_BUTTONS_LABELS, // Dialog button labels
				Window.OK // Default index (selection)
		);

		return dialog;
	}

	/**
	 * Method addAnomalyToExistingFileContext.
	 * 
	 * @param aTargetFile
	 *            R4EUIFileContext
	 * @param aContainer
	 *            R4EUIAnomalyContainer
	 * @param aUIPosition
	 *            IR4EUIPosition
	 * @param aClone
	 *            - boolean
	 */
	private static void addAnomalyToExistingFileContext(R4EUIFileContext aTargetFile, R4EUIAnomalyContainer aContainer,
			IR4EUIPosition aUIPosition, boolean aClone) {
		aContainer.createAnomaly(aTargetFile, (R4EUITextPosition) aUIPosition, aClone);
	}

	/**
	 * Method addAnomalyToNewFileContext.
	 * 
	 * @param aBaseFileVersion
	 *            R4EFileVersion
	 * @param aTargetFileVersion
	 *            R4EFileVersion
	 * @param aUIPosition
	 *            IR4EUIPosition
	 */
	private static void addAnomalyToNewFileContext(final R4EFileVersion aBaseFileVersion,
			final R4EFileVersion aTargetFileVersion, final IR4EUIPosition aUIPosition, final boolean aClone) {

		final R4EAnomaly tempAnomaly = R4EUIAnomalyContainer.createDetachedAnomaly(aClone);

		if (null != tempAnomaly) {

			final Job job = new Job(R4EUIAnomalyContainer.CREATE_ANOMALY_MESSAGE) {
				public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

				@Override
				public boolean belongsTo(Object family) {
					return familyName.equals(family);
				}

				@Override
				public IStatus run(IProgressMonitor monitor) {
					try {
						final R4EUIReviewBasic uiReview = R4EUIModelController.getActiveReview();
						final R4EUIReviewItem uiReviewItem = uiReview.createResourceReviewItem(aTargetFileVersion.getName());
						if (null == uiReviewItem) {
							return Status.CANCEL_STATUS;
						}
						final R4EUIFileContext uiFileContext = uiReviewItem.createFileContext(aBaseFileVersion,
								aTargetFileVersion, null);
						if (null == uiFileContext) {
							uiReview.removeChildren(uiReviewItem, false);
							return Status.CANCEL_STATUS;
						}

						final R4EUIAnomalyContainer uiAnomalyContainer = uiFileContext.getAnomalyContainerElement();
						final R4EUIAnomalyBasic uiAnomaly = uiAnomalyContainer.createAnomalyFromDetached(
								aTargetFileVersion, tempAnomaly, (R4EUITextPosition) aUIPosition, false);
						R4EUIModelController.setJobInProgress(false);
						UIUtils.setNavigatorViewFocus(uiAnomaly, AbstractTreeViewer.ALL_LEVELS);
					} catch (ResourceHandlingException e) {
						UIUtils.displayResourceErrorDialog(e);
					} catch (OutOfSyncException e) {
						UIUtils.displaySyncErrorDialog(e);
					}
					monitor.done();
					return Status.OK_STATUS;
				}
			};
			job.setUser(true);
			job.schedule();
		}
	}

	/**
	 * Method isAnomalyExist.
	 * 
	 * @param aFile
	 *            R4EUIFileContext
	 * @param aNewUiAnomaly
	 *            R4EUIAnomalyBasic
	 * @return String - the name of the existing anomaly or null if none
	 */
	public static String isAnomalyExist(R4EUIFileContext aFile, IR4EUIPosition aNewAnomalyPosition,
			String aNewAnomalyDescription) {
		//Check if the same anomaly as the one to be added already exists
		R4EUIAnomalyContainer anomalyContainer = aFile.getAnomalyContainerElement();
		R4EUIAnomalyBasic[] existingAnomalies = (R4EUIAnomalyBasic[]) anomalyContainer.getChildren();
		for (R4EUIAnomalyBasic uiAnomaly : existingAnomalies) {
			if (uiAnomaly.getPosition().isSameAs(aNewAnomalyPosition)
					&& uiAnomaly.getAnomaly().getDescription().equals(aNewAnomalyDescription)) {
				return aNewAnomalyDescription;
			}
		}
		return null;
	}

	/**
	 * Method isCommentExist.
	 * 
	 * @param aAnomaly
	 *            R4EUIAnomalyBasic
	 * @param String
	 *            aNewCommentDescription
	 * @return String - the description of the existing comment or null if none
	 */
	public static String isCommentExist(R4EUIAnomalyBasic aAnomaly, String aNewCommentDescription) {
		//Check if the same anomaly as the one to be added already exists
		IR4EUIModelElement[] existingUiComments = aAnomaly.getChildren();
		for (IR4EUIModelElement uiComment : existingUiComments) {
			if (((R4EUIComment) uiComment).getComment().getDescription().equals(aNewCommentDescription)) {
				return aNewCommentDescription;
			}
		}
		return null;
	}

	/**
	 * Method cloneLinkedAnomaly.
	 * 
	 * @param aElement
	 *            R4EUIContent
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	public static void cloneLinkedAnomaly(R4EUIContent aTargetContent, R4EUIAnomalyBasic aSourceAnomaly)
			throws ResourceHandlingException, OutOfSyncException {
		final R4EUIFileContext fileContext = (R4EUIFileContext) aTargetContent.getParent().getParent();
		final R4EUIAnomalyContainer container = (fileContext.getAnomalyContainerElement());
		R4EUIAnomalyBasic newUiAnomaly = container.createAnomalyFromDetached(fileContext.getTargetFileVersion(),
				aSourceAnomaly.getAnomaly(), (R4EUITextPosition) aTargetContent.getPosition(), true);
		UIUtils.setNavigatorViewFocus(newUiAnomaly, AbstractTreeViewer.ALL_LEVELS);
	}
}
