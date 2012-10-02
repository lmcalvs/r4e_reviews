// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the review navigator view toolbar command used 
 * to refresh (i.e. sychronize with serialization model) the review/review group data 
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class RefreshHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Refreshing Review Navigator View..."")
	 */
	private static final String COMMAND_MESSAGE = "Refreshing Review Navigator View...";

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
				monitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);
				R4EUIModelController.setJobInProgress(true);

				try {
					if (R4EUIModelController.getNavigatorView().isDefaultDisplay()) {
						if (!selectedElements.isEmpty()) {

							final IR4EUIModelElement element = selectedElements.get(0);
							if (element instanceof R4EUIReviewGroup) {
								//Refresh whole Review Group
								((R4EUIReviewGroup) element).close();
								((R4EUIReviewGroup) element).open();
							} else {
								//Refresh Review
								refreshReview(element);
							}
							R4EUIModelController.setJobInProgress(false);
							UIUtils.setNavigatorViewFocus(element, 0);
						} else {
							//No selection refresh all open review groups
							final IR4EUIModelElement[] groups = R4EUIModelController.getRootElement().getChildren();
							for (IR4EUIModelElement group : groups) {
								if (group.isOpen()) {
									group.close();
									group.open();
									R4EUIModelController.setJobInProgress(false);
									UIUtils.setNavigatorViewFocus(group, 0);
								}
							}
						}
					} else {
						//For the Tree table, only refresh the parent review
						final R4EUIReviewBasic review = R4EUIModelController.getActiveReview();
						if (null != review) {
							review.close();
							review.open();
							R4EUIModelController.setJobInProgress(false);
							UIUtils.setNavigatorViewFocus(review, 0);
						}
					}
				} catch (ResourceHandlingException e) {
					UIUtils.displayResourceErrorDialog(e);
				} catch (FileNotFoundException e) {
					R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
				} catch (CompatibilityException e1) {
					UIUtils.displayCompatibilityErrorDialog(e1);
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
	 * Method refreshReview.
	 * 
	 * @param element
	 *            IR4EUIModelElement
	 */
	private void refreshReview(IR4EUIModelElement element) {
		IR4EUIModelElement refreshElement = element;

		try {
			while (null != refreshElement && !(refreshElement instanceof R4EUIReviewBasic)) {
				refreshElement = refreshElement.getParent();
			}

			if (null != refreshElement && refreshElement.isOpen()) {
				refreshElement.close();
				refreshElement.open();
			}
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);

		} catch (FileNotFoundException e) {
			R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e); //$NON-NLS-1$
			final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
					"File not found error detected while refreshing review item ", new Status(IStatus.ERROR, //$NON-NLS-1$
							R4EUIPlugin.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					dialog.open();
				}
			});
		} catch (CompatibilityException e) {
			UIUtils.displayCompatibilityErrorDialog(e);
		}
	}
}
