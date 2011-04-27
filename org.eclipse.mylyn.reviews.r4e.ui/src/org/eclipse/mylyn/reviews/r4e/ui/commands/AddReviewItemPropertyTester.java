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
 * This class implements a property tester that is used to see if a review item can
 * be added
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.AbstractSet;
import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4EFileRevisionEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddReviewItemPropertyTester extends PropertyTester {

	/**
	 * Method test.
	 * @param receiver Object
	 * @param property String
	 * @param args Object[]
	 * @param expectedValue Object
	 * @return boolean
	 * @see org.eclipse.core.expressions.IPropertyTester#test(Object, String, Object[], Object)
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {

		//Command is disabled if the review is completed
		final R4EUIReviewBasic activeReview = R4EUIModelController.getActiveReview();
		if (null == activeReview || activeReview.isReviewed()) return false;

		//If the file opened is an R4E File that does not have a valid target version, the command is disabled
		if (receiver instanceof AbstractSet) {
			final Iterator<?> iterator = ((AbstractSet<?>)receiver).iterator();
			if (iterator.next() instanceof TextSelection) {
				IEditorInput editorInput = null;
				final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				if (null != window) {
					final IWorkbenchPage page = window.getActivePage();
					if (null != page) {
						editorInput = page.getActiveEditor().getEditorInput();
						if (editorInput instanceof R4EFileRevisionEditorInput) {
							if (null == ((R4EFileRevisionEditorInput)editorInput).getFileVersion().getResource()) {
								return false;
							}
						}
					}
				}
			}
		}
		
		//For formal reviews, review items can only be added by reveiwers in the planning and preparation phase
		if (activeReview.getReview().getType().equals(R4EReviewType.R4E_REVIEW_TYPE_FORMAL)) {
			R4EParticipant reviewer = null;
			try {
				reviewer = activeReview.getParticipant(R4EUIModelController.getReviewer(), false);
			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);
				return false;
			}
			if (null == reviewer) return false;

			if (((R4EReviewState)activeReview.getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_STARTED)) {
				if (!(reviewer.getRoles().contains(R4EUserRole.R4E_ROLE_LEAD)) || (reviewer.getRoles().contains(R4EUserRole.R4E_ROLE_AUTHOR))) {
					return false;
				}
			} else if (!((R4EReviewState)activeReview.getReview().getState()).getState().equals(R4EReviewPhase.R4E_REVIEW_PHASE_PREPARATION)) {
				return false;
			}
		}
		return true;
	}
}
