// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the context-sensitive command to add an anomaly on 
 * a review item
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.AnomalyUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class PasteElementHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Pasting Element(s)..."")
	 */
	private static final String COMMAND_MESSAGE = "Pasting Element(s)...";

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
				for (IR4EUIModelElement target : selectedElements) {
					if (target != null) {
						final Object[] sourceSelection = new Object[1];
						Display.getDefault().syncExec(new Runnable() {
							public void run() {
								sourceSelection[0] = R4EUIModelController.getNavigatorView().getClipboardContents();
							}
						});

						Object sourceElement = null;
						if (null != sourceSelection[0]) {
							for (final Iterator<?> iterator = ((IStructuredSelection) sourceSelection[0]).iterator(); iterator.hasNext();) {
								sourceElement = iterator.next();
								if (target instanceof R4EUIContent
										&& sourceElement instanceof R4EUIAnomalyBasic
										&& null == AnomalyUtils.isAnomalyExist((R4EUIFileContext) target.getParent()
												.getParent(), ((R4EUIContent) target).getPosition(),
												((R4EUIAnomalyBasic) sourceElement).getAnomaly().getDescription())) {
									try {
										//Pasting the Anomaly into content creates a cloned linked anomaly
										AnomalyUtils.cloneLinkedAnomaly((R4EUIContent) target,
												(R4EUIAnomalyBasic) sourceElement);
									} catch (ResourceHandlingException e) {
										UIUtils.displayResourceErrorDialog(e);
									} catch (OutOfSyncException e) {
										UIUtils.displaySyncErrorDialog(e);
									}
								} else if (target instanceof R4EUIAnomalyBasic && sourceElement instanceof R4EUIComment) {
									if (null == AnomalyUtils.isCommentExist((R4EUIAnomalyBasic) target,
											((R4EUIComment) sourceElement).getComment().getDescription())) {
										try {
											//Pasting the comment into the anomaly copies it to this anomaly
											IR4EUIModelElement newUIComment = ((R4EUIAnomalyBasic) target).createChildren(((R4EUIComment) sourceElement).getComment());
											UIUtils.setNavigatorViewFocus(newUIComment, AbstractTreeViewer.ALL_LEVELS);
										} catch (ResourceHandlingException e) {
											UIUtils.displayResourceErrorDialog(e);
										} catch (OutOfSyncException e) {
											UIUtils.displaySyncErrorDialog(e);
										} catch (CompatibilityException e) {
											UIUtils.displayCompatibilityErrorDialog(e);
										}
									}
								}
							}
						}
					}
				}
				R4EUIModelController.setJobInProgress(false);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(false);
		job.schedule();
		return null;
	}

}
