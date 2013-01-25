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
 * This class implements the review navigator view toolbar command used 
 * to refresh (i.e. sychronize with serialization model) the review/review group data 
 * 
 * Contributors:
 *   Jacques Bouthillier - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 */
public class ReportElementHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Generating Report for Elements..."")
	 */
	private static final String COMMAND_MESSAGE = "Generating Report for Elements...";

	/**
	 * Field COMMAND_MESSAGE. (value is ""Generating Report Warning"")
	 */
	private static final String GENERATE_REPORT_DIALOG_TITLE = "Generating Report Warning";

	/**
	 * Field GENERATE_REPORT_DIALOG_MESSAGE. (value is ""Some Uncompatible Reviews cannot be included in Report"")
	 */
	private static final String GENERATE_REPORT_DIALOG_MESSAGE = "Some Uncompatible Reviews cannot be included in Report"
			+ R4EUIConstants.LINE_FEED
			+ "Do you want to continue?"
			+ R4EUIConstants.LINE_FEED
			+ R4EUIConstants.LINE_FEED;

	/**
	 * Field GENERATE_REPORT_DIALOG_BUTTONS.
	 */
	private static final String[] GENERATE_REPORT_DIALOG_BUTTONS = { "Continue", "Cancel" };

	/**
	 * Field GENERATE_REPORT_CONTINUE_INDEX.
	 */
	private static final int GENERATE_REPORT_CONTINUE_INDEX = 0;

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
	public Object execute(final ExecutionEvent event) {

		final List<IR4EUIModelElement> selectedElements = UIUtils.getCommandUIElements();

		final Job job = new Job(COMMAND_MESSAGE) {

			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {
				R4EUIModelController.setJobInProgress(true);
				monitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);

				if (!selectedElements.isEmpty()) {
					String groupFile = null;

					//Build list of reviews to generate reports for
					final ArrayList<File> listSelectedReviews = new ArrayList<File>();
					final ArrayList<String> listUnknownReviews = new ArrayList<String>();

					for (IR4EUIModelElement element : selectedElements) {
						if (element instanceof R4EUIReviewBasic) {
							R4EUIReviewBasic extentElement = (R4EUIReviewBasic) element;
							if (null == groupFile) {
								groupFile = ((R4EUIReviewGroup) extentElement.getParent()).getGroupFile();
								R4EUIPlugin.Ftracer.traceInfo("Info: " + "Group file: " + groupFile); //$NON-NLS-1$//$NON-NLS-2$
							}
							R4EUIPlugin.Ftracer.traceInfo("Review name element " //$NON-NLS-1$
									+ extentElement.getReview().getName());
							listSelectedReviews.add(new File(extentElement.getReview().getName()));
						} else if (element instanceof R4EUIReview) {
							listUnknownReviews.add(((R4EUIReview) element).getReviewName());
						}
					}
					if (R4EUIPlugin.isUserReportAvailable()) {
						final int[] result = new int[1]; //We need this to be able to pass the result value outside.  This is safe as we are using SyncExec
						result[0] = GENERATE_REPORT_CONTINUE_INDEX;
						if (listUnknownReviews.size() > 0) {
							//If some selected reviews are not compatible, ask user to confirm
							final StringBuffer buffer = new StringBuffer();
							int numReviewErrorsAppened = 0;
							for (String unresolvedReview : listUnknownReviews) {
								if (numReviewErrorsAppened < 5) {
									buffer.append(unresolvedReview + R4EUIConstants.LINE_FEED);
								} else if (MAX_REVIEW_ERRORS == numReviewErrorsAppened) {
									buffer.append("...");
								}
								++numReviewErrorsAppened;
							}
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									final MessageDialog dialog = new MessageDialog(null, GENERATE_REPORT_DIALOG_TITLE,
											null, GENERATE_REPORT_DIALOG_MESSAGE + "Unresolved Reviews: "
													+ R4EUIConstants.LINE_FEED + buffer.toString(),
											MessageDialog.QUESTION_WITH_CANCEL, GENERATE_REPORT_DIALOG_BUTTONS, 0);
									result[0] = dialog.open();

								}
							});
						}
						if (result[0] == GENERATE_REPORT_CONTINUE_INDEX) {
							final org.eclipse.mylyn.reviews.r4e.report.impl.IR4EReport reportGen = org.eclipse.mylyn.reviews.r4e.report.impl.R4EReportFactory.getInstance();
							reportGen.setReviewListSelection(listSelectedReviews.toArray(new File[listSelectedReviews.size()]));
							reportGen.handleReportGeneration(groupFile, monitor);
							R4EUIPlugin.Ftracer.traceInfo("Report element AVAILABLE");//$NON-NLS-1$
						}
					} else {
						R4EUIPlugin.Ftracer.traceWarning("Report element Not available" //$NON-NLS-1$
						);
					}
				}
				monitor.done();
				R4EUIModelController.setJobInProgress(false);
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
