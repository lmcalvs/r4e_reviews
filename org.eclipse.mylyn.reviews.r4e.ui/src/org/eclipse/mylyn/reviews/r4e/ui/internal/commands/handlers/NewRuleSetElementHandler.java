// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the context-sensitive command to add a child element
 * to the navigator view/model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRootElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.progress.UIJob;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class NewRuleSetElementHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Adding New Rule Set..."")
	 */
	private static final String COMMAND_MESSAGE = "Adding New Rule Set...";

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

		final UIJob job = new UIJob(COMMAND_MESSAGE) {
			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);

				final IR4EUIModelElement element = R4EUIModelController.getRootElement();

				//Get data from user
				final ReviewComponent tempModelComponent = ((R4EUIRootElement) element).createRuleSetElement();
				if (null != tempModelComponent) {
					R4EUIPlugin.Ftracer.traceInfo("Adding Rule Set to the root element "); //$NON-NLS-1$

					//Create actual model element
					final Job job = new Job(COMMAND_MESSAGE) {
						@Override
						public IStatus run(IProgressMonitor monitor) {
							monitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);
							R4EUIModelController.setJobInProgress(true);

							IR4EUIModelElement newElement = null;
							try {
								newElement = element.createChildren(tempModelComponent);
							} catch (ResourceHandlingException e) {
								UIUtils.displayResourceErrorDialog(e);

								//Remove object if partially created
								try {
									element.removeChildren(newElement, true);
								} catch (ResourceHandlingException e1) {
									UIUtils.displayResourceErrorDialog(e1);
								} catch (OutOfSyncException e1) {
									UIUtils.displaySyncErrorDialog(e1);
								} catch (CompatibilityException e1) {
									UIUtils.displayCompatibilityErrorDialog(e1);
								}

							} catch (CompatibilityException e) {
								UIUtils.displayCompatibilityErrorDialog(e);

								//Remove object if partially created
								try {
									element.removeChildren(newElement, true);
								} catch (ResourceHandlingException e1) {
									UIUtils.displayResourceErrorDialog(e1);
								} catch (OutOfSyncException e1) {
									UIUtils.displaySyncErrorDialog(e1);
								} catch (CompatibilityException e1) {
									UIUtils.displayCompatibilityErrorDialog(e1);
								}

							} catch (OutOfSyncException e) {
								UIUtils.displaySyncErrorDialog(e);

								//Remove object if partially created
								try {
									element.removeChildren(newElement, true);
								} catch (ResourceHandlingException e1) {
									UIUtils.displayResourceErrorDialog(e1);
								} catch (OutOfSyncException e1) {
									UIUtils.displaySyncErrorDialog(e1);
								} catch (CompatibilityException e1) {
									UIUtils.displayCompatibilityErrorDialog(e1);
								}
							}

							R4EUIModelController.setJobInProgress(false);
							UIUtils.setNavigatorViewFocus(newElement, 0);
							monitor.done();
							return Status.OK_STATUS;
						}
					};
					job.setUser(true);
					job.schedule();
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
