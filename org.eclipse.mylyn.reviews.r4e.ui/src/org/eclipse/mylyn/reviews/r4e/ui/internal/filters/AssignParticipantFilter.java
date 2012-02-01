// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Navigator View filter used to display the 
 * review(s) for the selected participant and its descendants
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.filters;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AssignParticipantFilter extends ViewerFilter {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fParticipant.
	 */
	private String fParticipant = "";

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Sets the current participant to filter on
	 * 
	 * @param aParticipant
	 */
	public void setParticipant(String aParticipant) {
		fParticipant = aParticipant;
	}

	/**
	 * Gets the currently selected particiant
	 * 
	 * @return String
	 */
	public String getParticipant() {
		return fParticipant;
	}

	/**
	 * Method select.
	 * 
	 * @param viewer
	 *            Viewer
	 * @param parentElement
	 *            Object
	 * @param element
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		//Only Review elements that are unassigned, or assigned to the participant
		//and anomalies created by the participant are shown
		if (element instanceof R4EUIReviewItem) {
			if (((R4EUIReviewItem) element).getItem().getAssignedTo().size() == 0
					|| ((R4EUIReviewItem) element).getItem().getAssignedTo().contains(fParticipant)) {
				return true;
			} else {
				List<R4EUIFileContext> files = ((R4EUIReviewItem) element).getFileContexts();
				for (R4EUIFileContext file : files) {
					if (file.getFileContext().getAssignedTo().contains(fParticipant)) {
						return true;
					} else {
						IR4EUIModelElement[] contents = file.getContentsContainerElement().getChildren();
						for (IR4EUIModelElement content : contents) {
							if (((R4EUIContent) content).getContent().getAssignedTo().contains(fParticipant)) {
								return true;
							}
						}
						IR4EUIModelElement[] anomalies = file.getAnomalyContainerElement().getChildren();
						for (IR4EUIModelElement anomaly : anomalies) {
							if (((R4EUIAnomalyBasic) anomaly).getAnomaly().getAssignedTo().contains(fParticipant)) {
								return true;
							}
						}
					}
				}
			}
			return false;
		} else if (element instanceof R4EUIFileContext) {
			if (element instanceof R4EUIPostponedFile) {
				return true;
			}
			if (((R4EUIFileContext) element).getFileContext().getAssignedTo().size() == 0
					|| ((R4EUIFileContext) element).getFileContext().getAssignedTo().contains(fParticipant)) {
				return true;
			} else {
				IR4EUIModelElement[] contents = ((R4EUIFileContext) element).getContentsContainerElement()
						.getChildren();
				for (IR4EUIModelElement content : contents) {
					if (((R4EUIContent) content).getContent().getAssignedTo().contains(fParticipant)) {
						return true;
					}
				}
				IR4EUIModelElement[] anomalies = ((R4EUIFileContext) element).getAnomalyContainerElement()
						.getChildren();
				for (IR4EUIModelElement anomaly : anomalies) {
					if (((R4EUIAnomalyBasic) anomaly).getAnomaly().getAssignedTo().contains(fParticipant)) {
						return true;
					}
				}
			}
			return false;
		} else if (element instanceof R4EUIContent) {
			if (((R4EUIContent) element).getContent().getAssignedTo().size() == 0
					|| ((R4EUIContent) element).getContent().getAssignedTo().contains(fParticipant)) {
				return true;
			}
			return false;
		} else if (element instanceof R4EUIAnomalyBasic) {
			if (((R4EUIAnomalyBasic) element).getAnomaly().getAssignedTo().size() == 0
					|| ((R4EUIAnomalyBasic) element).getAnomaly().getAssignedTo().contains(fParticipant)
					|| ((R4EUIAnomalyBasic) element).getAnomaly().getUser().getId().equals(fParticipant)) {
				return true;
			}
			return false;
		}
		return true;
	}
}
