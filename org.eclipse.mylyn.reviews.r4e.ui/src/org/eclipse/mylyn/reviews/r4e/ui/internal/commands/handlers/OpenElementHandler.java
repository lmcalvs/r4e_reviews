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
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
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
					try {
						element = selectedElements.get(0);

						if (element instanceof R4EUIReviewBasic) {
							R4EUIPlugin.Ftracer.traceInfo("Opening element " + element.getName()); //$NON-NLS-1$
							final R4EUIReviewBasic activeReview = R4EUIModelController.getActiveReview();
							if (null != activeReview) {
								activeReview.close();
							}

							//Make sure serialization starts as default in all resources
							R4EUIModelController.resetToDefaultSerialization();
						}
						element.open();
						R4EUIModelController.setJobInProgress(false);
						UIUtils.setNavigatorViewFocus(element, 1);
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
}
