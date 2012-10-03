// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the context-sensitive command to add an anomaly on 
 * a review item
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
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.AnomalyUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class NewAnomalyHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Adding Anomaly..."")
	 */
	private static final String COMMAND_MESSAGE = "Adding Anomaly..."; //$NON-NLS-1$

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

		final IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.getActiveEditor(); // $codepro.audit.disable methodChainLength

		final IEditorInput input;
		if (null != editorPart) {
			input = editorPart.getEditorInput(); // $codepro.audit.disable methodChainLength
		} else {
			input = null;
		}

		final Job job = new Job(COMMAND_MESSAGE) {
			public String familyName = R4EUIConstants.R4E_UI_JOB_FAMILY;

			@Override
			public boolean belongsTo(Object family) {
				return familyName.equals(family);
			}

			@Override
			public IStatus run(IProgressMonitor monitor) {

				//Act differently depending on the type of selection we get
				final ISelection selection = HandlerUtil.getCurrentSelection(event);
				R4EUIModelController.setJobInProgress(true);

				if (selection instanceof ITextSelection) {
					monitor.beginTask(COMMAND_MESSAGE, IProgressMonitor.UNKNOWN);
					AnomalyUtils.addAnomalyFromText((ITextSelection) selection, input, false);

				} else if (selection instanceof ITreeSelection) {

					//First remove any editor selection (if open) if we execute the command from the review navigator view
					if (null != editorPart && editorPart instanceof ITextEditor) {
						((ITextEditor) editorPart).getSelectionProvider().setSelection(null);
					}

					//Then iterate through all selections
					monitor.beginTask(COMMAND_MESSAGE, ((IStructuredSelection) selection).size());
					for (final Iterator<?> iterator = ((ITreeSelection) selection).iterator(); iterator.hasNext();) {
						AnomalyUtils.addAnomalyFromTree(iterator.next(), monitor, false);
						if (monitor.isCanceled()) {
							R4EUIModelController.setJobInProgress(false);
							return Status.CANCEL_STATUS;
						}
					}
				} else if (null == selection || selection.isEmpty()) {
					//Try to get the active editor highlighted range and set it as the editor's selection
					if (null != editorPart) {
						if (editorPart instanceof ITextEditor) {
							final IRegion region = ((ITextEditor) editorPart).getHighlightRange();
							final TextSelection selectedText = new TextSelection(
									((ITextEditor) editorPart).getDocumentProvider().getDocument(
											editorPart.getEditorInput()), region.getOffset(), region.getLength());
							//Make sure selection is set in the UI thread
							Display.getDefault().syncExec(new Runnable() {
								public void run() {
									((ITextEditor) editorPart).getSelectionProvider().setSelection(selectedText);
								}
							});
							AnomalyUtils.addAnomalyFromText(selectedText, input, false);
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
