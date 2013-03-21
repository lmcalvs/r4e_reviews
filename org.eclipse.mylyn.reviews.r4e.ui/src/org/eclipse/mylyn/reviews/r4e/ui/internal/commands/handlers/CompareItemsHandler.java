// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the context-sensitive command used 
 * compare two selected Review Items from the Review Navigator
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.handlers;

import java.util.Date;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.mylyn.reviews.r4e.ui.internal.editors.EditorProxy;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.progress.UIJob;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class CompareItemsHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMAND_MESSAGE. (value is ""Comparing Review Items..."")
	 */
	private static final String COMMAND_MESSAGE = "Comparing Review Items...";

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

		final UIJob job = new UIJob(COMMAND_MESSAGE) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				monitor.beginTask(COMMAND_MESSAGE, 1);

				if (!selectedElements.isEmpty()) {
					if (selectedElements.size() == 2 && selectedElements.get(0) instanceof R4EUIReviewItem
							&& selectedElements.get(1) instanceof R4EUIReviewItem) {
						R4EUIReviewItem item1 = (R4EUIReviewItem) selectedElements.get(0);
						R4EUIReviewItem item2 = (R4EUIReviewItem) selectedElements.get(1);
						//If there's no date submitted, it is a resource review item, use current date
						Date item1SubmitDate = item1.getItem().getSubmitted();
						Date item2SubmitDate = item2.getItem().getSubmitted();
						if (null == item1SubmitDate) {
							item1SubmitDate = new Date();
						}
						if (null == item2SubmitDate) {
							item2SubmitDate = new Date();
						}

						//The most recently submitted Review Item is always the target
						if (item1SubmitDate.after(item2SubmitDate)) {
							EditorProxy.openReviewItemCompareEditor(R4EUIModelController.getNavigatorView()
									.getSite()
									.getPage(), item1, item2);
						} else {
							EditorProxy.openReviewItemCompareEditor(R4EUIModelController.getNavigatorView()
									.getSite()
									.getPage(), item2, item1);
						}
					}
				}
				monitor.worked(1);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}
}
