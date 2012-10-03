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
 * This class extends the default viewer comparator to compare
 * Review Navigator Tree table column elements for a specific column
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.sorters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.SWT;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class TreeTableComparator extends ViewerComparator {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field DESCENDING. (value is 1)
	 */
	private static final int DESCENDING = 1;

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fColumnName.
	 */
	private String fColumnName = null;

	/**
	 * Field fDirection.
	 */
	private int fDirection = DESCENDING;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for TreeTableComparator.
	 */
	public TreeTableComparator() {
		fDirection = DESCENDING;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getDirection.
	 * 
	 * @return int
	 */
	public int getDirection() {
		return 1 == fDirection ? SWT.DOWN : SWT.UP;
	}

	/**
	 * Method setColumnName.
	 * 
	 * @param aColumnName
	 *            String
	 */
	public void setColumnName(String aColumnName) {
		if (null != fColumnName && aColumnName.equals(fColumnName)) {
			// Same column as last sort; toggle the direction
			fDirection = 1 - fDirection;
		} else {
			// New column; do an ascending sort
			fColumnName = aColumnName;
			fDirection = DESCENDING;
		}
	}

	/**
	 * Method compare.
	 * 
	 * @param viewer
	 *            Viewer
	 * @param e1
	 *            Object
	 * @param e2
	 *            Object
	 * @return int
	 */
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		//For all columns, sort first by category
		final int cat1 = category(e1);
		final int cat2 = category(e2);
		if (cat1 != cat2) {
			return cat1 - cat2;
		}

		//Next sort each column based on its contents
		int compareResult = 0;
		if (R4EUIConstants.ELEMENTS_LABEL_NAME.equals(fColumnName)) {
			//Sort by element name
			if (e1 instanceof IR4EUIModelElement && e2 instanceof IR4EUIModelElement) {
				final String str1 = ((IR4EUIModelElement) e1).getName();
				final String str2 = ((IR4EUIModelElement) e2).getName();
				compareResult = str1.compareTo(str2);
			} else {
				compareResult = 0;
			}
		} else if (R4EUIConstants.PATH_LABEL.equals(fColumnName)) {
			//Sort by path
			if (e1 instanceof R4EUIFileContext && e2 instanceof R4EUIFileContext) {
				String str1 = "";
				String str2 = "";
				//First try target file version
				R4EFileVersion fileVersion = ((R4EUIFileContext) e1).getTargetFileVersion();
				if (null != fileVersion) {
					str1 = UIUtils.getProjectPath(fileVersion);
				} else {
					//Try base file version
					fileVersion = ((R4EUIFileContext) e1).getBaseFileVersion();
					if (null != fileVersion) {
						str1 = UIUtils.getProjectPath(fileVersion);
					}
				}

				fileVersion = ((R4EUIFileContext) e2).getTargetFileVersion();
				if (null != fileVersion) {
					str2 = UIUtils.getProjectPath(fileVersion);
				} else {
					//Try base file version
					fileVersion = ((R4EUIFileContext) e2).getBaseFileVersion();
					if (null != fileVersion) {
						str2 = UIUtils.getProjectPath(fileVersion);
					}
				}
				compareResult = str1.compareTo(str2);
			} else {
				compareResult = 0;
			}
		} else if (R4EUIConstants.ASSIGNED_TO_LABEL2.equals(fColumnName)) {
			//Sort by assignees
			if (e1 instanceof R4EUIReviewItem && e2 instanceof R4EUIReviewItem) {
				final String str1 = UIUtils.formatAssignedParticipants(((R4EUIReviewItem) e1).getItem().getAssignedTo());
				final String str2 = UIUtils.formatAssignedParticipants(((R4EUIReviewItem) e2).getItem().getAssignedTo());
				compareResult = str1.compareTo(str2);
			} else if (e1 instanceof R4EUIFileContext && e2 instanceof R4EUIFileContext) {
				final String str1 = UIUtils.formatAssignedParticipants(((R4EUIFileContext) e1).getFileContext()
						.getAssignedTo());
				final String str2 = UIUtils.formatAssignedParticipants(((R4EUIFileContext) e2).getFileContext()
						.getAssignedTo());
				compareResult = str1.compareTo(str2);
			} else {
				compareResult = 0; //should never happen
			}
		} else if (R4EUIConstants.CHANGES_LABEL.equals(fColumnName)) {
			//Sort by number of changes
			if (e1 instanceof R4EUIReviewItem && e2 instanceof R4EUIReviewItem) {
				final int num1 = ((R4EUIReviewItem) e1).getNumChanges();
				final int num2 = ((R4EUIReviewItem) e2).getNumChanges();
				compareResult = num1 - num2;
			} else if (e1 instanceof R4EUIFileContext && e2 instanceof R4EUIFileContext) {
				final int num1 = ((R4EUIFileContext) e1).getNumChanges();
				final int num2 = ((R4EUIFileContext) e2).getNumChanges();
				compareResult = num1 - num2;
			} else {
				compareResult = 0; //should never happen
			}
		} else if (R4EUIConstants.ANOMALIES_LABEL.equals(fColumnName)) {
			//Sort by number of anomalies
			if (e1 instanceof R4EUIReviewItem && e2 instanceof R4EUIReviewItem) {
				final int num1 = ((R4EUIReviewItem) e1).getNumAnomalies();
				final int num2 = ((R4EUIReviewItem) e2).getNumAnomalies();
				compareResult = num1 - num2;
			} else if (e1 instanceof R4EUIFileContext && e2 instanceof R4EUIFileContext) {
				final int num1 = ((R4EUIFileContext) e1).getNumAnomalies();
				final int num2 = ((R4EUIFileContext) e2).getNumAnomalies();
				compareResult = num1 - num2;
			} else {
				compareResult = 0; //should never happen
			}
		}

		// If descending order, flip the direction
		if (fDirection == DESCENDING) {
			compareResult = -compareResult;
		}
		return compareResult;
	}
}
