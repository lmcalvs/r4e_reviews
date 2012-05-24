// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the command to progress the element (Review/Anomaly)
 * to its next state
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IChangeStateDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class NextStateHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Progressing element to its Next State..."")
	 */
	private static final String COMMAND_MESSAGE = "Progressing element to its Next State...";

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

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				final ISelection selection = HandlerUtil.getCurrentSelection(aEvent);
				if (selection instanceof IStructuredSelection) {
					if (!selection.isEmpty()) {
						monitor.beginTask(COMMAND_MESSAGE, ((IStructuredSelection) selection).size());
						R4EUIModelController.setJobInProgress(true);

						IR4EUIModelElement element = null;

						for (final Iterator<?> iterator = ((IStructuredSelection) selection).iterator(); iterator.hasNext();) {
							element = (IR4EUIModelElement) iterator.next();
							R4EUIPlugin.Ftracer.traceInfo("Progressing state for element " + element.getName()); //$NON-NLS-1$

							if (element instanceof R4EUIReviewExtended) {
								progressExtendedReview((R4EUIReviewExtended) element);

							} else if (element instanceof R4EUIReviewBasic) {
								progressBasicReview((R4EUIReviewBasic) element);

							} else if (element instanceof R4EUIPostponedAnomaly) {
								try {
									if (((R4EUIPostponedAnomaly) element).checkCompatibility()) {
										progressAnomaly((R4EUIPostponedAnomaly) element);
									}
								} catch (ResourceHandlingException e) {
									UIUtils.displayResourceErrorDialog(e);
								} catch (CompatibilityException e) {
									UIUtils.displayCompatibilityErrorDialog(e);
								}
							} else if (element instanceof R4EUIAnomalyExtended) {
								progressAnomaly((R4EUIAnomalyExtended) element);
							}
							monitor.worked(1);
							if (monitor.isCanceled()) {
								R4EUIModelController.setJobInProgress(false);
								UIUtils.setNavigatorViewFocus(element, 0);
								return Status.CANCEL_STATUS;
							}
						}
						R4EUIModelController.setJobInProgress(false);
						UIUtils.setNavigatorViewFocus(element, 0);
					}
				}
				R4EUIModelController.setJobInProgress(false);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	/**
	 * Method progressExtendedReview.
	 * 
	 * @param aReview
	 *            R4EUIReviewExtended
	 */
	private void progressExtendedReview(R4EUIReviewExtended aReview) {

		final String[] newPhasesStr = aReview.getNextAvailablePhases();
		if (newPhasesStr.length > 1) {
			//Get next phase from user
			final IChangeStateDialog dialog = R4EUIDialogFactory.getInstance().getChangeStateDialog(
					R4EUIReviewBasic.class);
			final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					dialog.create();
					dialog.setStates(newPhasesStr);
					result[0] = dialog.open();
				}
			});
			if (result[0] == Window.OK) {
				final R4EReviewPhase newPhase = aReview.getPhaseFromString(dialog.getState());
				UIUtils.changeReviewPhase(aReview, newPhase);
			}
		} else {
			//Only 1 possible value to use
			final R4EReviewPhase newPhase = aReview.getPhaseFromString(newPhasesStr[0]);
			UIUtils.changeReviewPhase(aReview, newPhase);
		}
	}

	/**
	 * Method progressBasicReview.
	 * 
	 * @param aReview
	 *            aAnomaly
	 */
	private void progressBasicReview(R4EUIReviewBasic aReview) {
		final R4EReviewPhase newPhase = aReview.getPhaseFromString(R4EUIConstants.REVIEW_PHASE_COMPLETED);
		UIUtils.changeReviewPhase(aReview, newPhase);
	}

	/**
	 * Method progressAnomaly.
	 * 
	 * @param aAnomaly
	 *            R4EUIAnomalyExtended
	 */
	private void progressAnomaly(R4EUIAnomalyExtended aAnomaly) {
		final String[] newStatesStr = aAnomaly.getNextAvailableStates();
		if (newStatesStr.length > 1) {
			final IChangeStateDialog dialog = R4EUIDialogFactory.getInstance().getChangeStateDialog(
					R4EUIAnomalyBasic.class);
			final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					dialog.create();
					dialog.setStates(newStatesStr);
					result[0] = dialog.open();
				}
			});
			if (result[0] == Window.OK) {
				final R4EAnomalyState newState = R4EUIAnomalyExtended.getStateFromString(dialog.getState());
				if (newState.equals(R4EAnomalyState.R4E_ANOMALY_STATE_REJECTED)) {
					final boolean commentResult = aAnomaly.createComment(true);
					if (commentResult) {
						UIUtils.changeAnomalyState(aAnomaly, newState);
					} else {
						final ErrorDialog commentErrorDialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
								"Cannot change Anomaly State", new Status(IStatus.ERROR, //$NON-NLS-1$
										R4EUIPlugin.PLUGIN_ID, 0, "Please enter a reason for rejecting this anomaly", //$NON-NLS-1$
										null), IStatus.ERROR);
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								commentErrorDialog.open();
							}
						});
					}
				} else {
					UIUtils.changeAnomalyState(aAnomaly, newState);
				}
			}
		} else {
			//Only 1 possible value to use
			final R4EAnomalyState newState = R4EUIAnomalyExtended.getStateFromString(newStatesStr[0]);
			UIUtils.changeAnomalyState(aAnomaly, newState);
		}
	}
}
