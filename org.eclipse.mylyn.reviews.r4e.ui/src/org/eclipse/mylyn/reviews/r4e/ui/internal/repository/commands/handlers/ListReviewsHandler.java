/*******************************************************************************
 * Copyright (c) 2013 Ericsson and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Francois Chouinard - Initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.repository.commands.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.mylyn.reviews.r4e.core.rfs.repository.R4ERemoteRepository;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class ListReviewsHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// IHandler
	// ------------------------------------------------------------------------

	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Check if we are closing down
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}

		// Fire the command after validation
		final List<IR4EUIModelElement> selectedElements = UIUtils.getCommandUIElements();
		IR4EUIModelElement element = selectedElements.get(0);
		if (element instanceof R4EUIReviewGroup) {
			String groupName = element.getName();
			List<String> reviews = R4ERemoteRepository.getInstance().listReviews(groupName);
			showReviews(window, reviews);
		}

		return null;
	}

	// Display available reviews in a message box
	private void showReviews(IWorkbenchWindow parent, List<String> reviews) {

		StringBuilder message = new StringBuilder();
		for (int i = 0; i < reviews.size(); i++) {
			message.append(i + 1).append(".\t").append(reviews.get(i)).append('\n'); //$NON-NLS-1$
		}

		// Fire the command
		Shell shell = parent.getShell();
		MessageBox mb = new MessageBox(shell);
		mb.setText("List of reviews"); //$NON-NLS-1$
		mb.setMessage(message.toString());
		mb.open();
	}

}
