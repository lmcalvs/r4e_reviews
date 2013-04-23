// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the Navigator View filter used to display the 
 * review elements assigned to selected participants
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
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContentsContainer;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class AssignParticipantFilter extends ViewerFilter {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fParticipant.
	 */
	private String fParticipant = ""; //$NON-NLS-1$

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
	 * Gets the participant to filter on
	 * 
	 * @return String
	 */
	public String getParticipant() {
		return fParticipant;
	}

	/**
	 * Method select.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aParentElement
	 *            Object
	 * @param aElement
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean select(Viewer aViewer, Object aParentElement, Object aElement) {

		//Only Review elements that are unassigned, or assigned to the participant
		//and anomalies created by the participant are shown
		if (aElement instanceof R4EUIReviewItem) {
			if (0 == ((R4EUIReviewItem) aElement).getItem().getAssignedTo().size()
					|| ((R4EUIReviewItem) aElement).getItem().getAssignedTo().contains(fParticipant)) {
				return true;
			} else {
				final List<R4EUIFileContext> files = ((R4EUIReviewItem) aElement).getFileContexts();
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
		} else if (aElement instanceof R4EUIFileContext) {
			if (aElement instanceof R4EUIPostponedFile) {
				return true;
			}
			if (0 == ((R4EUIFileContext) aElement).getFileContext().getAssignedTo().size()
					|| ((R4EUIFileContext) aElement).getFileContext().getAssignedTo().contains(fParticipant)) {
				return true;
			} else {
				final IR4EUIModelElement[] contents = ((R4EUIFileContext) aElement).getContentsContainerElement()
						.getChildren();
				for (IR4EUIModelElement content : contents) {
					if (((R4EUIContent) content).getContent().getAssignedTo().contains(fParticipant)) {
						return true;
					}
				}
				final IR4EUIModelElement[] anomalies = ((R4EUIFileContext) aElement).getAnomalyContainerElement()
						.getChildren();
				for (IR4EUIModelElement anomaly : anomalies) {
					if (((R4EUIAnomalyBasic) anomaly).getAnomaly().getAssignedTo().contains(fParticipant)) {
						return true;
					}
				}
			}
			return false;
		} else if (aElement instanceof R4EUIContent) {
			if (0 == ((R4EUIContent) aElement).getContent().getAssignedTo().size()
					|| ((R4EUIContent) aElement).getContent().getAssignedTo().contains(fParticipant)) {
				return true;
			}
			return false;
		} else if (aElement instanceof R4EUIAnomalyBasic) {
			if (0 == ((R4EUIAnomalyBasic) aElement).getAnomaly().getAssignedTo().size()
					|| ((R4EUIAnomalyBasic) aElement).getAnomaly().getAssignedTo().contains(fParticipant)
					|| ((R4EUIAnomalyBasic) aElement).getAnomaly().getUser().getId().equals(fParticipant)) {
				return true;
			}
			return false;
		} else if (aElement instanceof R4EUIContentsContainer) {
			for (IR4EUIModelElement child : ((R4EUIContentsContainer) aElement).getChildren()) {
				if (0 == ((R4EUIContent) child).getContent().getAssignedTo().size()
						|| ((R4EUIContent) child).getContent().getAssignedTo().contains(fParticipant)) {
					return true;
				}
			}
			return false;
		} else if (aElement instanceof R4EUIAnomalyContainer) {
			for (IR4EUIModelElement child : ((R4EUIAnomalyContainer) aElement).getChildren()) {
				if (0 == ((R4EUIAnomalyBasic) child).getAnomaly().getAssignedTo().size()
						|| ((R4EUIAnomalyBasic) child).getAnomaly().getAssignedTo().contains(fParticipant)
						|| ((R4EUIAnomalyBasic) child).getAnomaly().getUser().getId().equals(fParticipant)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}
}
