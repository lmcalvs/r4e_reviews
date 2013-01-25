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
 * This class implements the command to regress the element (Review/Anomaly)
 * to its previous state
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewExtended;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class PreviousStateHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Regressing element to its Previous State..."")
	 */
	private static final String COMMAND_MESSAGE = "Regressing element to its Previous State...";

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
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (!selectedElements.isEmpty()) {
					R4EUIModelController.setJobInProgress(true);
					monitor.beginTask(COMMAND_MESSAGE, selectedElements.size());

					for (IR4EUIModelElement element : selectedElements) {
						R4EUIPlugin.Ftracer.traceInfo("Regressing State for element " + element.getName()); //$NON-NLS-1$

						if (element instanceof R4EUIReviewExtended) {
							R4EReviewPhase newPhase = ((R4EUIReviewExtended) element).getPreviousPhase();
							UIUtils.changeReviewPhase(element, newPhase);

						} else if (element instanceof R4EUIReviewBasic) {
							R4EReviewPhase newPhase = ((R4EUIReviewBasic) element).getPhaseFromString(R4EUIConstants.REVIEW_PHASE_STARTED);
							UIUtils.changeReviewPhase(element, newPhase);
						} else if (element instanceof R4EUIPostponedAnomaly) {
							try {
								if (((R4EUIPostponedAnomaly) element).checkOrigReviewCompatibility()) {
									R4EAnomalyState newState = ((R4EUIPostponedAnomaly) element).getPreviousState();
									UIUtils.changeAnomalyState(element, newState);
								}
							} catch (ResourceHandlingException e) {
								UIUtils.displayResourceErrorDialog(e);
							} catch (CompatibilityException e) {
								UIUtils.displayCompatibilityErrorDialog(e);
							}
						} else if (element instanceof R4EUIAnomalyExtended) {
							R4EAnomalyState newState = ((R4EUIAnomalyExtended) element).getPreviousState();
							UIUtils.changeAnomalyState(element, newState);
						}
						monitor.worked(1);
						if (monitor.isCanceled()) {
							R4EUIModelController.setJobInProgress(false);
							UIUtils.setNavigatorViewFocus(element, 0);
							return Status.CANCEL_STATUS;
						}
					}
					R4EUIModelController.setJobInProgress(false);
					UIUtils.setNavigatorViewFocus(selectedElements.get(0), 0);
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
}
