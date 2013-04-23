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
 * This class implements an annotation that wraps an R4E Anomaly
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EID;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnomalyAnnotation extends R4EAnnotation {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fDescriptionLines.
	 */
	private final List<String> fDescriptionLines = new ArrayList<String>();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EAnomalyAnnotation.
	 * 
	 * @param aSourceAnomaly
	 *            R4EUIAnomalyBasic
	 */
	public R4EAnomalyAnnotation(R4EUIAnomalyBasic aSourceAnomaly) {
		super(aSourceAnomaly, getAnnotationType(aSourceAnomaly), aSourceAnomaly.getName());
		final StringTokenizer st = new StringTokenizer(aSourceAnomaly.getAnomaly().getDescription(),
				R4EUIConstants.LINE_FEED);
		while (st.hasMoreElements()) {
			fDescriptionLines.add((String) st.nextElement());
		}
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getR4EPosition.
	 * 
	 * @return IR4EUIPosition
	 */
	@Override
	public IR4EUIPosition getR4EPosition() {
		return ((R4EUIAnomalyBasic) fSourceElement).getPosition();
	}

	/**
	 * Method getId.
	 * 
	 * @return R4EID
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getId()
	 */
	@Override
	public R4EID getId() {
		return ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getId();
	}

	/**
	 * Method getAnnotationType.
	 * 
	 * @param aSourceAnomaly
	 *            R4EUIAnomalyBasic
	 * @return String
	 */
	private static String getAnnotationType(R4EUIAnomalyBasic aSourceAnomaly) {
		if (aSourceAnomaly.isEnabled()) {
			if (aSourceAnomaly.isTerminalState()) {
				return R4EUIConstants.ANOMALY_CLOSED_ANNOTATION_ID;
			} else {
				return R4EUIConstants.ANOMALY_OPEN_ANNOTATION_ID;
			}
		}
		return R4EUIConstants.ANOMALY_DISABLED_ANNOTATION_ID;
	}

	/**
	 * Method getParent.
	 * 
	 * @return Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getParent()
	 */
	public Object getParent() {
		return getParentInput();
	}

	/**
	 * Method getChildren.
	 * 
	 * @return Object[]
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getChildren()
	 */
	public Object[] getChildren() {
		final List<Object> values = new ArrayList<Object>();

		//Description
		values.add(new R4EAnnotationText(this, R4EUIConstants.DESCRIPTION_LABEL, fDescriptionLines));

		//Details
		final List<String> detailsValues = new ArrayList<String>();
		detailsValues.add(R4EUIConstants.AUTHOR_LABEL
				+ ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getUser().getId());
		final R4ECommentType commentType = (R4ECommentType) ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getType();
		if (null != commentType) {
			detailsValues.add(R4EUIConstants.CLASS_LABEL + (UIUtils.getClassStr(commentType.getType())));
		}
		final R4EDesignRuleRank rank = ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getRank();
		if ((null != rank) && !rank.equals(R4EDesignRuleRank.R4E_RANK_NONE)) {
			detailsValues.add(R4EUIConstants.RANK_LABEL + UIUtils.getRankStr(rank));
		}
		final String ruleId = ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getRuleID();
		if ((null != ruleId) && !ruleId.equals("")) { //$NON-NLS-1$
			detailsValues.add(R4EUIConstants.RULE_ID_LABEL + ruleId);
		}
		final EList<String> assignees = ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getAssignedTo();
		if (((null != assignees) && (assignees.size() > 0)) && !assignees.get(0).equals("")) { //$NON-NLS-1$
			detailsValues.add(R4EUIConstants.ASSIGNED_TO_LABEL + assignees.get(0));
		}
		final SimpleDateFormat dateFormat = new SimpleDateFormat(R4EUIConstants.SIMPLE_DATE_FORMAT);
		final Date dueDate = ((R4EUIAnomalyBasic) fSourceElement).getAnomaly().getDueDate();
		if (null != dueDate) {
			detailsValues.add(R4EUIConstants.DUE_DATE_LABEL + dateFormat.format(dueDate));
		}
		values.add(new R4EAnnotationText(this, "Details: ", detailsValues));

		final IR4EUIModelElement[] comments = ((R4EUIAnomalyBasic) fSourceElement).getChildren();
		if ((null != comments) && (comments.length > 0)) {
			for (IR4EUIModelElement comment : comments) {
				values.add(new R4ECommentAnnotation((R4EUIComment) comment, this));
			}
		}
		return values.toArray(new Object[values.size()]);
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#hasChildren()
	 */
	public boolean hasChildren() {
		return true;
	}
}
