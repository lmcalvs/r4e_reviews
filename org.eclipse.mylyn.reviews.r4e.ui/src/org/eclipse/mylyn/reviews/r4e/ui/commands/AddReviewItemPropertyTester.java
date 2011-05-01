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

import java.util.AbstractList;
import java.util.AbstractSet;
import java.util.Iterator;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4ECompareEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4EFileEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4EFileRevisionEditorInput;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4EFileRevisionTypedElement;
import org.eclipse.mylyn.reviews.r4e.ui.editors.R4EFileTypedElement;
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

		//Check if command is enabled based on input
		if (receiver instanceof AbstractSet) {
			final Iterator<?> iterator = ((AbstractSet<?>)receiver).iterator();
			if (iterator.next() instanceof TextSelection) {
				if (!(isR4EEditorInputAvailable())) return false;
			}
		}
		//This happens when the command is selected from the outline view on an external or workspace file
		if (receiver instanceof AbstractList) {
			final Iterator<?> iterator = ((AbstractList<?>)receiver).iterator();
			if (!iterator.hasNext()) {
				if (!(isR4EEditorInputAvailable())) return false;
			} else {
				final Object obj = iterator.next();
				if (obj instanceof org.eclipse.jdt.core.ISourceReference || 
						obj instanceof org.eclipse.cdt.core.model.ISourceReference) {
					if (!(isR4EEditorInputAvailable())) return false;
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
	
	/**
	 * Method isR4EEditorInputAvailable.
	 * @return boolean
	 */
	private boolean isR4EEditorInputAvailable() {
		IEditorInput editorInput = null;
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (null != window) {
			final IWorkbenchPage page = window.getActivePage();
			if (null != page && null != page.getActiveEditor()) {
				editorInput = page.getActiveEditor().getEditorInput();
				//Single editor
				if (editorInput instanceof R4EFileRevisionEditorInput) {
					if (null == ((R4EFileRevisionEditorInput)editorInput).getFileVersion().getResource()) {
						return false;   //No valid target
					}
					final R4EItem parentItem = 
						((R4EItem)((R4EFileRevisionEditorInput)editorInput).getFileVersion().eContainer().eContainer());
					if (null != parentItem.getRepositoryRef()) {
						return false;  //Cannot add review item on a commit
					}
				} else if (editorInput instanceof R4EFileEditorInput) {
					final R4EItem parentItem = 
						((R4EItem)((R4EFileEditorInput)editorInput).getFileVersion().eContainer().eContainer());
					if (null != parentItem.getRepositoryRef()) {
						return false;  //Cannot add review item on a commit
					}
					//Compare editor
				} else if (editorInput instanceof R4ECompareEditorInput) {
					final ITypedElement targetElement = ((R4ECompareEditorInput)editorInput).getLeftElement();
					if (null != targetElement) {
						if (targetElement instanceof R4EFileRevisionTypedElement) {
							final R4EItem parentItem = 
								((R4EItem)((R4EFileRevisionTypedElement)targetElement).getFileVersion().eContainer().eContainer());
							if (null != parentItem.getRepositoryRef()) {
								return false;  //Cannot add review item on a commit
							}
						} else if (targetElement instanceof R4EFileTypedElement) {
							final R4EItem parentItem = 
								((R4EItem)((R4EFileTypedElement)targetElement).getFileVersion().eContainer().eContainer());
							if (null != parentItem.getRepositoryRef()) {
								return false;  //Cannot add review item on a commit
							}
						} 
					} else {
						return false;  
					}
				}
			}
		}
		return true;
	}
}