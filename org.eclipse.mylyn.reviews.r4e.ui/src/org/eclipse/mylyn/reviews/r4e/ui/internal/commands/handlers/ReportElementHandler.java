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
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.report.impl.IR4EReport;
import org.eclipse.mylyn.reviews.r4e.report.impl.R4EReportFactory;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;

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

		final ISelection selection = R4EUIModelController.getNavigatorView().getTreeViewer().getSelection();

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

				if (selection instanceof IStructuredSelection) {
					if (!selection.isEmpty()) {
						IR4EUIModelElement element = null;
						final IStructuredSelection structSelection = (IStructuredSelection) selection;
						final Iterator<?> iter = structSelection.iterator();
						String groupFile = null;

						//Build list of reviews to generate reports for
						final ArrayList<File> listSelectedReviews = new ArrayList<File>();
						while (iter.hasNext()) {
							element = (IR4EUIModelElement) iter.next();
							if (element instanceof R4EUIReviewBasic) {
								R4EUIReviewBasic extentElement = (R4EUIReviewBasic) element;
								if (null == groupFile) {
									groupFile = ((R4EUIReviewGroup) extentElement.getParent()).getGroupFile();
									R4EUIPlugin.Ftracer.traceInfo("Info: " + "Group file: " + groupFile); //$NON-NLS-1$//$NON-NLS-2$
								}
								R4EUIPlugin.Ftracer.traceInfo("Review name element " //$NON-NLS-1$
										+ extentElement.getReview().getName());
								listSelectedReviews.add(new File(extentElement.getReview().getName()));
							}
						}
						final IR4EReport reportGen = R4EReportFactory.getInstance();
						reportGen.setReviewListSelection(listSelectedReviews.toArray(new File[listSelectedReviews.size()]));
						reportGen.handleReportGeneration(groupFile, monitor);
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
