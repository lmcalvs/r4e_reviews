/*******************************************************************************
 * Copyright (c) 2008, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the context-sensitive command to open the
 * currently selected element and load data from the model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.r4e.upgrade.ui.R4EUpgradeController;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class OpenElementHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Opening Element..."")
	 */
	private static final String COMMAND_MESSAGE = "Opening Element...";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method execute.
	 * 
	 * @param aEvent
	 *            ExecutionEvent
	 * @return Object
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent aEvent) {

		final List<IR4EUIModelElement> selectedElements = UIUtils.getCommandUIElements();

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object aFamily) {
				return familyName.equals(aFamily);
			}

			@Override
			public IStatus run(IProgressMonitor aMonitor) {
				R4EUIModelController.setJobInProgress(true);
				aMonitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);

				if (!selectedElements.isEmpty()) {
					IR4EUIModelElement element = null;
					IR4EUIModelElement upgradedElement = null;
					try {
						element = selectedElements.get(0);

						if (element instanceof R4EUIReview) {
							R4EUIPlugin.Ftracer.traceInfo("Opening element " + element.getName()); //$NON-NLS-1$
							upgradedElement = checkReviewCompatibility((R4EUIReview) element);
							if (null == upgradedElement) {
								R4EUIModelController.setJobInProgress(false);
								aMonitor.done();
								return Status.CANCEL_STATUS;
							}
						} else {
							upgradedElement = element;
						}
						upgradedElement.open();
						R4EUIModelController.setJobInProgress(false);
						UIUtils.setNavigatorViewFocus(upgradedElement, 1);
					} catch (ResourceHandlingException e) {
						UIUtils.displayResourceErrorDialog(e);
						//make sure the element is released from memory
						if (null != element && element instanceof R4EUIReviewBasic) {
							element.close();
						}
					} catch (CompatibilityException e) {
						UIUtils.displayCompatibilityErrorDialog(e);
						//make sure the element is released from memory
						if (null != element && element instanceof R4EUIReviewBasic) {
							element.close();
						}
					} catch (FileNotFoundException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
						final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
								"File not found error detected while opening element", new Status(IStatus.ERROR, //$NON-NLS-1$
										R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								dialog.open();
							}
						});
					}
				}
				R4EUIModelController.setJobInProgress(false);
				aMonitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Method checkReviewCompatibility.
	 * 
	 * @param aCurrentUiReview
	 *            R4EUIReview
	 * @return R4EUIReview
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	private R4EUIReview checkReviewCompatibility(R4EUIReview aUiReview) throws ResourceHandlingException,
			CompatibilityException {
		R4EUIReview originalUiReview = aUiReview;
		R4EUIReview newUiReview = null;

		//If a review is currently open, close it first
		final R4EUIReviewBasic activeReview = R4EUIModelController.getActiveReview();
		if (null != activeReview) {
			activeReview.close();
		}

		//Make sure serialization starts as default in all resources
		R4EUIModelController.resetToDefaultSerialization();

		//If the Review needs to be upgraded prior to opening it, we do it here
		if (!(originalUiReview instanceof R4EUIReviewBasic)) {
			String reviewName = ((R4EUIReview) originalUiReview).getReview().getName();
			R4EUIReviewGroup parentGroup = (R4EUIReviewGroup) originalUiReview.getParent();

			String newVersion = Persistence.Roots.REVIEW.getVersion();
			String validReviewName = ResourceUtils.toValidFileName(reviewName);
			URI upgradeRootUri = URI.createFileURI(parentGroup.getReviewGroup().getFolder() + R4EUIConstants.SEPARATOR
					+ validReviewName);
			URI reviewResourceUri = upgradeRootUri.appendSegment(validReviewName + R4EUIConstants.REVIEW_FILE_SUFFIX);
			String oldVersion;
			try {
				oldVersion = R4EUpgradeController.getVersionFromResourceFile(reviewResourceUri);
			} catch (IOException e1) {
				throw new ResourceHandlingException("Cannot find Review Group resource file " + reviewResourceUri, e1);
			}

			if (((R4EUIModelElement) originalUiReview).checkCompatibility(upgradeRootUri, R4EUIConstants.REVIEW_LABEL
					+ " " + reviewName, oldVersion, newVersion, true)) {

				//If the review is upgraded we need to do this update the Review references
				parentGroup.close();
				parentGroup.open();
				R4EUIReview upgradedUiReview = parentGroup.getReview(reviewName);
				R4EReview upgradedReview = upgradedUiReview.getReview();

				if (upgradedReview.getType().equals(R4EReviewType.FORMAL)) {
					newUiReview = new R4EUIReviewExtended(parentGroup, upgradedReview, upgradedReview.getType(), false);
					((R4EUIReviewExtended) newUiReview).setName(((R4EUIReviewExtended) newUiReview).getPhaseString(((R4EReviewState) upgradedReview.getState()).getState())
							+ ": " + upgradedReview.getName());
				} else {
					newUiReview = new R4EUIReviewBasic(parentGroup, upgradedReview, upgradedReview.getType(), false);
				}

				//Replace current review element in UI model content provider with the new upgraded one
				parentGroup.replaceReview((R4EUIReview) upgradedUiReview, newUiReview);

				//Stamp new version if an upgrade took place
				if (!oldVersion.equals(newVersion)) {
					try {
						R4EUIModelController.stampVersion(upgradedReview, R4EUIModelController.getReviewer(),
								newVersion);
					} catch (OutOfSyncException e) {
						R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIModelController.FModelExt.closeR4EReview(upgradedReview);
						R4EUIModelController.setJobInProgress(false);
						return null;
					}
				}
			} else {
				R4EUIModelController.setJobInProgress(false);
				return null;
			}
		} else {
			newUiReview = originalUiReview;
		}
		return newUiReview;
	}
}
