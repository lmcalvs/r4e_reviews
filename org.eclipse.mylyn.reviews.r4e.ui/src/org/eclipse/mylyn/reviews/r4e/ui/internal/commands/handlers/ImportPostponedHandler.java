/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the navigator view context-menu command that is used to 
 * import postponed anomalies from other reviews
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.mylyn.reviews.core.model.IComment;
import org.eclipse.mylyn.reviews.core.model.IReviewItem;
import org.eclipse.mylyn.reviews.core.model.ITopic;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ImportPostponedHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Importing Postponed Anomalies..."")
	 */
	private static final String COMMAND_MESSAGE = "Importing Postponed Anomalies...";

	/**
	 * Field MAX_REVIEW_ERRORS. (value is "5")
	 */
	private static final int MAX_REVIEW_ERRORS = 5;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param event
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) {

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				R4EUIModelController.setJobInProgress(true);
				final R4EUIReviewGroup parentGroup = (R4EUIReviewGroup) R4EUIModelController.getActiveReview()
						.getParent();
				monitor.beginTask(COMMAND_MESSAGE, parentGroup.getChildren().length);

				importPostponedElements(true, monitor);
				R4EUIModelController.setJobInProgress(false);
				UIUtils.setNavigatorViewFocus(R4EUIModelController.getActiveReview().getPostponedContainer(),
						AbstractTreeViewer.ALL_LEVELS);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Method importPostponedElements.
	 * 
	 * @param aAddNewAnomalies
	 *            - boolean
	 * @param aMonitor
	 *            IProgressMonitor
	 */
	public static void importPostponedElements(boolean aAddNewAnomalies, IProgressMonitor aMonitor) {
		final R4EUIReviewGroup parentGroup = (R4EUIReviewGroup) R4EUIModelController.getActiveReview().getParent();

		//For each review in the parent review group, open it and look for postponed anomalies
		//If one is found, add it to the imported postponed elements list
		final List<String> unresolvedReviews = new ArrayList<String>(0);
		for (IR4EUIModelElement oldReview : parentGroup.getChildren()) {
			try {
				//Ignore current review
				if (((R4EUIReview) oldReview).getName().equals(R4EUIModelController.getActiveReview().getName())) {
					continue;
				}

				if (null != aMonitor) {
					aMonitor.subTask("Processing Review: " + oldReview.getName());
				}

				List<R4EAnomaly> oldAnomalies = getAnomalies((R4EUIReview) oldReview, unresolvedReviews);
				for (R4EAnomaly oldAnomaly : oldAnomalies) {
					try {
						importAnomaly((R4EUIReviewBasic) oldReview, oldAnomaly, aAddNewAnomalies, aMonitor);
					} catch (ResourceHandlingException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					}
				}

				//Once the anomalies are imported, verify if we should show them (If they are now not postponed we do not show them)
				CommandUtils.showPostponedElements(R4EUIModelController.getActiveReview());
			} catch (OutOfSyncException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			} catch (ResourceHandlingException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			} catch (CompatibilityException e) {
				R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
			}
			if (null != aMonitor) {
				aMonitor.worked(1);
			}
		}

		//Notify users of reviews not checked because they were not resolvable (incompatible version mismatches)
		if (unresolvedReviews.size() > 0) {
			StringBuffer buffer = new StringBuffer();
			int numReviewErrorsAppened = 0;
			for (String unresolvedReview : unresolvedReviews) {
				if (numReviewErrorsAppened < 5) {
					buffer.append(unresolvedReview + R4EUIConstants.LINE_FEED);
				} else if (MAX_REVIEW_ERRORS == numReviewErrorsAppened) {
					buffer.append("...");
				}
				++numReviewErrorsAppened;
			}
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_WARNING,
					"Some Reviews could not be opened when importing or refreshing Postponed Anomalies", new Status(
							IStatus.WARNING, R4EUIPlugin.PLUGIN_ID, 0, "Unresolved Reviews: "
									+ R4EUIConstants.LINE_FEED + buffer.toString(), null), IStatus.WARNING);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					dialog.open();
				}
			});
		}
	}

	/**
	 * Method getAnomalies.
	 * 
	 * @param aUiOldReview
	 *            R4EUIReviewBasic
	 * @return List<R4EAnomaly>
	 */
	private static List<R4EAnomaly> getAnomalies(R4EUIReview aUiOldReview, List<String> aUnresolvedReviews) {
		final R4EReview currentReview = R4EUIModelController.getActiveReview().getReview();
		final List<R4EAnomaly> anomaliesToConsider = new ArrayList<R4EAnomaly>();

		//Open all compatible old reviews, loop through all anomalies for the review, and check if there are
		//any postponed anomalies on files that are included in the current review
		try {
			if (((R4EUIReviewGroup) aUiOldReview.getParent()).checkChildReviewCompatibility(aUiOldReview.getReview())) {

				final R4EReview oldReview = R4EUIModelController.FModelExt.openR4EReview(
						((R4EUIReviewGroup) aUiOldReview.getParent()).getReviewGroup(), aUiOldReview.getReview()
								.getName());
				final List<ITopic> oldAnomalies = oldReview.getTopics();
				for (ITopic oldAnomaly : oldAnomalies) {

					//Get parent file
					R4EFileVersion oldAnomalyFile = CommandUtils.getAnomalyParentFile((R4EAnomaly) oldAnomaly);
					if (null == oldAnomalyFile) {
						//Global anomaly
						if (null == ((R4EAnomaly) oldAnomaly).getInfoAtt().get(
								R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)) {
							anomaliesToConsider.add((R4EAnomaly) oldAnomaly);
						}
					} else {

						for (IReviewItem currentItem : currentReview.getItems()) {
							//Ignore R4EUIPostponedContainer for current review here
							if ((R4EUIConstants.TRUE_ATTR_VALUE_STR).equals(((R4EItem) currentItem).getInfoAtt().get(
									R4EUIConstants.POSTPONED_ATTR_STR))) {
								continue;
							}

							//NOTE:  We compare the URI of the files.  This means that in order to be considered, 
							//the version of the file in the current review need to be in the workspace.  This is a limitation.
							List<R4EFileContext> currentFiles = ((R4EItem) currentItem).getFileContextList();
							for (R4EFileContext currentFile : currentFiles) {
								if (null != currentFile.getTarget()
										&& null != currentFile.getTarget().getPlatformURI()
										&& currentFile.getTarget()
												.getPlatformURI()
												.equals(oldAnomalyFile.getPlatformURI())
										&& null == ((R4EAnomaly) oldAnomaly).getInfoAtt().get(
												R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)) {
									anomaliesToConsider.add((R4EAnomaly) oldAnomaly);
								}
							}
						}
					}
				}
				R4EUIModelController.FModelExt.closeR4EReview(oldReview);
			} else {
				//The original review could not be checked (compatibility problems)
				aUnresolvedReviews.add(aUiOldReview.getReviewName());
			}
		} catch (ResourceHandlingException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		} catch (CompatibilityException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
		}
		return anomaliesToConsider;
	}

	/**
	 * Method importAnomaly.
	 * 
	 * @param aUiReview
	 *            R4EUIReviewBasic
	 * @param aOldAnomaly
	 *            R4EAnomaly
	 * @param aAddNewAnomalies
	 *            - boolean
	 * @param aMonitor
	 *            IProgressMonitor
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private static void importAnomaly(R4EUIReviewBasic aUiReview, R4EAnomaly aOldAnomaly, boolean aAddNewAnomalies,
			IProgressMonitor aMonitor) throws ResourceHandlingException, OutOfSyncException {

		//Lazily create the postponed elements container if not already done
		R4EUIPostponedContainer uiPostponedContainer = R4EUIModelController.getActiveReview().getPostponedContainer();
		if (null == uiPostponedContainer) {
			if (aAddNewAnomalies) {
				uiPostponedContainer = R4EUIModelController.getActiveReview().createPostponedContainer();
			} else {
				return; //Refresh only, do not create
			}
		}

		//Lazily create the postponed file and add it to the postponed container, if not already done
		final R4EFileVersion oldFile = CommandUtils.getAnomalyParentFile(aOldAnomaly);
		if (null == oldFile) {
			//Global Anomaly
			if (R4EUIPlugin.getDefault()
					.getPreferenceStore()
					.getBoolean(PreferenceConstants.P_IMPORT_GLOBAL_ANOMALIES_POSTPONED)) {
				importGlobalAnomaly(aUiReview, uiPostponedContainer, aOldAnomaly, aAddNewAnomalies, aMonitor);
			}
		} else {
			importLocalAnomaly(aUiReview, uiPostponedContainer, aOldAnomaly, oldFile, aAddNewAnomalies, aMonitor);
		}
	}

	/**
	 * Method importGlobalAnomaly.
	 * 
	 * @param aUiPostponedContainer
	 *            R4EUIPostponedContainer
	 * @param aOldAnomaly
	 *            R4EAnomaly
	 * @param aAddNewAnomalies
	 *            - boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private static void importGlobalAnomaly(R4EUIReviewBasic aUiReview, R4EUIPostponedContainer aUiPostponedContainer,
			R4EAnomaly aOldAnomaly, boolean aAddNewAnomalies, IProgressMonitor aMonitor)
			throws ResourceHandlingException, OutOfSyncException {
		final IR4EUIModelElement[] uiGlobalAnomalies = aUiPostponedContainer.getAnomalyContainer().getChildren();
		R4EUIPostponedAnomaly foundUiAnomaly = null;
		for (IR4EUIModelElement uiAnomaly : uiGlobalAnomalies) {
			R4EAnomaly anomaly = ((R4EUIPostponedAnomaly) uiAnomaly).getAnomaly();
			String oldAnomalyId = CommandUtils.buildOriginalAnomalyID(aOldAnomaly);
			if (oldAnomalyId.equals(anomaly.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID))) {
				foundUiAnomaly = (R4EUIPostponedAnomaly) uiAnomaly;
				break;
			}
		}

		if (null == foundUiAnomaly) {
			if (aAddNewAnomalies) {

				//If the anomaly is new and is postponed, add it
				if (aOldAnomaly.getState().equals(R4EAnomalyState.DEFERRED)) {
					if (null != aMonitor) {
						aMonitor.subTask("Importing Postponed Global Anomaly for Review: " + aUiReview.getName());
					}

					final R4EUIPostponedAnomaly uiPostponedAnomaly = aUiPostponedContainer.getAnomalyContainer()
							.createAnomaly(aUiReview, aOldAnomaly);
					//Also add all child comments
					final List<IComment> comments = aOldAnomaly.getComments();
					for (IComment comment : comments) {
						uiPostponedAnomaly.createComment((R4EComment) comment);
					}
				}
			}
		} else {
			foundUiAnomaly.updateAnomaly(aOldAnomaly);
			if (foundUiAnomaly.isEnabled()) {
				//Update anomaly comments
				final List<IComment> oldComments = aOldAnomaly.getComments();
				final IR4EUIModelElement[] uiComments = foundUiAnomaly.getChildren();
				for (IComment oldComment : oldComments) {
					R4EUIComment foundUiComment = null;
					for (IR4EUIModelElement uiComment : uiComments) {
						R4EComment comment = ((R4EUIComment) uiComment).getComment();
						String oldCommentId = CommandUtils.buildOriginalCommentID((R4EComment) oldComment);
						if (oldCommentId.equals(comment.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_COMMENT_ID))) {
							foundUiComment = (R4EUIComment) uiComment;
							break;
						}
					}
					if (null == foundUiComment) {
						foundUiAnomaly.createComment((R4EComment) oldComment);
					} //no alternative as comments are unmodifiable
				}
			}
		}
	}

	/**
	 * Method importLocalAnomaly.
	 * 
	 * @param aUiReview
	 *            R4EUIReviewBasic
	 * @param aUiPostponedContainer
	 *            R4EUIPostponedContainer
	 * @param aOldAnomaly
	 *            R4EAnomaly
	 * @param aOldFile
	 *            R4EFileVersion
	 * @param aAddNewAnomalies
	 *            - boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private static void importLocalAnomaly(R4EUIReviewBasic aUiReview, R4EUIPostponedContainer aUiPostponedContainer,
			R4EAnomaly aOldAnomaly, R4EFileVersion aOldFile, boolean aAddNewAnomalies, IProgressMonitor aMonitor)
			throws ResourceHandlingException, OutOfSyncException {
		final List<R4EUIFileContext> postponedUiFileContexts = aUiPostponedContainer.getFileContexts();
		R4EUIPostponedFile uiPostponedFile = null;
		for (R4EUIFileContext currentFileContext : postponedUiFileContexts) {
			if (currentFileContext.getFileContext().getTarget().getVersionID().equals(aOldFile.getVersionID())) {
				uiPostponedFile = (R4EUIPostponedFile) currentFileContext;
				break;
			}
		}
		if (null == uiPostponedFile) {
			if (aAddNewAnomalies) {
				uiPostponedFile = aUiPostponedContainer.createFileContext(aOldFile);
			} else {
				return; //Refresh only, do not create
			}
		}

		//Add postponed anomaly if not already present.  If present just update the anomaly values
		final IR4EUIModelElement[] uiAnomalies = uiPostponedFile.getChildren();
		R4EUIPostponedAnomaly foundUiAnomaly = null;
		for (IR4EUIModelElement uiAnomaly : uiAnomalies) {
			R4EAnomaly anomaly = ((R4EUIPostponedAnomaly) uiAnomaly).getAnomaly();
			String oldAnomalyId = CommandUtils.buildOriginalAnomalyID(aOldAnomaly);
			if (oldAnomalyId.equals(anomaly.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID))) {
				foundUiAnomaly = (R4EUIPostponedAnomaly) uiAnomaly;
				break;
			}
		}
		if (null == foundUiAnomaly) {
			if (aAddNewAnomalies) {
				//If the anomaly is new and is postponed, add it
				if (aOldAnomaly.getState().equals(R4EAnomalyState.DEFERRED)) {
					if (null != aMonitor) {
						aMonitor.subTask("Importing Postponed Anomaly for Review: " + aUiReview.getName());
					}

					final R4EUIPostponedAnomaly uiPostponedAnomaly = uiPostponedFile.createAnomaly(aOldAnomaly,
							aUiReview.getReview().getName());

					//Also add all child comments
					final List<IComment> comments = aOldAnomaly.getComments();
					for (IComment comment : comments) {
						uiPostponedAnomaly.createComment((R4EComment) comment);
					}
				}
			}
		} else {
			foundUiAnomaly.updateAnomaly(aOldAnomaly);
			if (foundUiAnomaly.isEnabled()) {
				//Update anomaly comments
				final List<IComment> oldComments = aOldAnomaly.getComments();
				final IR4EUIModelElement[] uiComments = foundUiAnomaly.getChildren();
				for (IComment oldComment : oldComments) {
					R4EUIComment foundUiComment = null;
					for (IR4EUIModelElement uiComment : uiComments) {
						R4EComment comment = ((R4EUIComment) uiComment).getComment();
						String oldCommentId = CommandUtils.buildOriginalCommentID((R4EComment) oldComment);
						if (oldCommentId.equals(comment.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_COMMENT_ID))) {
							foundUiComment = (R4EUIComment) uiComment;
							break;
						}
					}
					if (null == foundUiComment) {
						foundUiAnomaly.createComment((R4EComment) oldComment);
					} //no alternative as comments are unmodifiable
				}
			}
		}
	}

	/**
	 * Method refreshPostponedElements.
	 */
	public static void refreshPostponedElements(IProgressMonitor aMonitor) {
		importPostponedElements(false, aMonitor);
	}
}
