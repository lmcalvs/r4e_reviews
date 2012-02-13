/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
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
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.frame.core.model.Comment;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.CommandUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.progress.UIJob;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ImportPostponedHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param event
	 *            ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) {

		final UIJob job = new UIJob("Importing Postponed elements...") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				importPostponedElements();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Method importPostponedElements.
	 */
	public static void importPostponedElements() {
		final R4EUIReviewGroup parentGroup = (R4EUIReviewGroup) R4EUIModelController.getActiveReview().getParent();

		//For each review in the parent review group, open it and look for postponed anomalies
		//If one is found, add it to the imported postponed elements list
		for (IR4EUIModelElement oldReview : parentGroup.getChildren()) {
			try {
				//Ignore current review
				if (((R4EUIReviewBasic) oldReview).getName().equals(R4EUIModelController.getActiveReview().getName())) {
					continue;
				}

				List<R4EAnomaly> oldAnomalies = getAnomalies((R4EUIReviewBasic) oldReview);
				for (R4EAnomaly oldAnomaly : oldAnomalies) {
					try {
						importAnomaly((R4EUIReviewBasic) oldReview, oldAnomaly);
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
		}
	}

	/**
	 * Method getAnomalies.
	 * 
	 * @param aUiOldReview
	 *            R4EUIReviewBasic
	 * @return List<R4EAnomaly>
	 */
	private static List<R4EAnomaly> getAnomalies(R4EUIReviewBasic aUiOldReview) {
		final R4EReview currentReview = R4EUIModelController.getActiveReview().getReview();
		final List<R4EAnomaly> anomaliesToConsider = new ArrayList<R4EAnomaly>();

		//Open all old reviews, loop through all anomalies for the review, and check if there are
		//any postponed anomalies on files that are included in the current review
		try {
			final R4EReview oldReview = R4EUIModelController.FModelExt.openR4EReview(
					((R4EUIReviewGroup) aUiOldReview.getParent()).getReviewGroup(), aUiOldReview.getReview().getName());
			final EList<Topic> oldAnomalies = oldReview.getTopics();
			for (Topic oldAnomaly : oldAnomalies) {

				//Get parent file
				R4EFileVersion oldAnomalyFile = CommandUtils.getAnomalyParentFile((R4EAnomaly) oldAnomaly);
				if (null == oldAnomalyFile) {
					continue; //Global anomalies cannot be imported
				}

				for (Item currentItem : currentReview.getReviewItems()) {
					//Ignore R4EUIPostponedContainer for current review here
					if ((R4EUIConstants.TRUE_ATTR_VALUE_STR).equals(((R4EItem) currentItem).getInfoAtt().get(
							R4EUIConstants.POSTPONED_ATTR_STR))) {
						continue;
					}

					//NOTE:  We compare the URI of the files.  This means that in order to be considered, 
					//the version of the file in the current review need to be in the workspace.  This is a limitation.
					EList<R4EFileContext> currentFiles = ((R4EItem) currentItem).getFileContextList();
					for (R4EFileContext currentFile : currentFiles) {
						if (null != currentFile.getTarget()
								&& null != currentFile.getTarget().getPlatformURI()
								&& currentFile.getTarget().getPlatformURI().equals(oldAnomalyFile.getPlatformURI())
								&& null == ((R4EAnomaly) oldAnomaly).getInfoAtt().get(
										R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID)) {
							anomaliesToConsider.add((R4EAnomaly) oldAnomaly);
						}
					}
				}
			}
			R4EUIModelController.FModelExt.closeR4EReview(oldReview);

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
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private static void importAnomaly(R4EUIReviewBasic aUiReview, R4EAnomaly aOldAnomaly)
			throws ResourceHandlingException, OutOfSyncException {

		//Lazily create the postponed elements container if not already done
		R4EUIPostponedContainer uiPostponedContainer = R4EUIModelController.getActiveReview().getPostponedContainer();
		if (null == uiPostponedContainer) {
			uiPostponedContainer = R4EUIModelController.getActiveReview().createPostponedContainer();
		}

		//Lazily create the postponed file and add it to the postponed container, if not already done
		final R4EFileVersion oldFile = CommandUtils.getAnomalyParentFile(aOldAnomaly);
		if (null == oldFile) {
			return; //Global anomalies cannot be imported
		}

		final List<R4EUIFileContext> postponedUiFileContexts = uiPostponedContainer.getFileContexts();
		R4EUIPostponedFile uiPostponedFile = null;
		for (R4EUIFileContext currentFileContext : postponedUiFileContexts) {
			if (currentFileContext.getFileContext().getTarget().getVersionID().equals(oldFile.getVersionID())) {
				uiPostponedFile = (R4EUIPostponedFile) currentFileContext;
				break;
			}
		}
		if (null == uiPostponedFile) {
			uiPostponedFile = uiPostponedContainer.createFileContext(oldFile, aUiReview.getReview().getName());
		}

		//Add postponed anomaly if not already present.  If present just update the anomaly values
		final IR4EUIModelElement[] uiAnomalies = uiPostponedFile.getChildren();
		R4EUIPostponedAnomaly foundUiAnomaly = null;
		for (IR4EUIModelElement uiAnomaly : uiAnomalies) {
			R4EAnomaly anomaly = ((R4EUIPostponedAnomaly) uiAnomaly).getAnomaly();
			String oldAnomalyId = aOldAnomaly.getId().getUserID() + R4EUIConstants.SEPARATOR
					+ aOldAnomaly.getId().getSequenceID();
			if (oldAnomalyId.equals(anomaly.getInfoAtt().get(R4EUIConstants.POSTPONED_ATTR_ORIG_ANOMALY_ID))) {
				foundUiAnomaly = (R4EUIPostponedAnomaly) uiAnomaly;
				break;
			}
		}
		if (null == foundUiAnomaly) {
			//If the anomaly is new and is postponed, add it
			if (aOldAnomaly.getState().equals(R4EAnomalyState.R4E_ANOMALY_STATE_DEFERRED)) {
				final R4EUIPostponedAnomaly uiPostponedAnomaly = uiPostponedFile.createAnomaly(aOldAnomaly);
				//Also add all child comments
				final EList<Comment> comments = aOldAnomaly.getComments();
				for (Comment comment : comments) {
					uiPostponedAnomaly.createComment((R4EComment) comment);
				}
			}
		} else {
			foundUiAnomaly.updateAnomaly(aOldAnomaly);
			if (foundUiAnomaly.isEnabled()) {
				//Update anomaly comments
				final EList<Comment> oldComments = aOldAnomaly.getComments();
				final IR4EUIModelElement[] uiComments = foundUiAnomaly.getChildren();
				for (Comment oldComment : oldComments) {
					R4EUIComment foundUiComment = null;
					for (IR4EUIModelElement uiComment : uiComments) {
						R4EComment comment = ((R4EUIComment) uiComment).getComment();
						String oldCommentId = ((R4EComment) oldComment).getId().getUserID()
								+ ((R4EComment) oldComment).getId().getSequenceID();
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
}
