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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class PushReviewHandler extends AbstractHandler {

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
		if (element instanceof R4EUIReview) {
			String groupName = element.getParent().getName();
			String reviewName = ((R4EUIReview) element).getReviewName();
			try {
				R4ERemoteRepository.getInstance().pushReview(groupName, reviewName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
