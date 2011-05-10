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
 * This class implements the view sorter that sorts anomalies by line number
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.navigator;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class LineViewerSorter extends ViewerSorter {

	/**
	 * Method getCommitTitleList.
	 * 
	 * @param viewer
	 *            Viewer
	 * @param e1
	 *            Object
	 * @param e2
	 *            Object
	 * @return int
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		if (e1 instanceof R4EUIAnomalyBasic && e2 instanceof R4EUIAnomalyBasic) {
			if (null != ((R4EUIAnomalyBasic) e1).getPosition()) {
				return ((R4EUITextPosition) ((R4EUIAnomalyBasic) e1).getPosition()).getStartLine()
						- ((R4EUITextPosition) ((R4EUIAnomalyBasic) e2).getPosition()).getStartLine();
			}
			//Global anomalies return alphabetically
			// use the comparator to compare the strings
			return getComparator().compare(((R4EUIAnomalyBasic) e1).getName(), ((R4EUIAnomalyBasic) e2).getName());
		}
		return 0;
	}
}
