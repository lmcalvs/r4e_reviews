// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.sorters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUITextPosition;

/**
 * This class extends the default viewer comparator to compare the TextPosition elements and sort them incrementally by
 * their occurence in a Text file
 * 
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class LinePositionComparator extends ViewerComparator {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

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

		final int cat1 = category(e1);
		final int cat2 = category(e2);
		if (cat1 != cat2) {
			return cat1 - cat2;
		}

		IR4EUIPosition position1 = null;
		IR4EUIPosition position2 = null;

		//Only sort Selection and Anomaly elements
		if (e1 instanceof R4EUIContent) {
			position1 = ((R4EUIContent) e1).getPosition();
			position2 = ((R4EUIContent) e2).getPosition();
		} else if (e1 instanceof R4EUIAnomalyBasic) {
			position1 = ((R4EUIAnomalyBasic) e1).getPosition();
			position2 = ((R4EUIAnomalyBasic) e2).getPosition();
		} else {
			return 0;
		}

		//For now we only support TextPositions comparisons
		if (position1 instanceof R4EUITextPosition && position2 instanceof R4EUITextPosition) {
			return ((R4EUITextPosition) position1).getOffset() - ((R4EUITextPosition) position2).getOffset();
		}
		return 0;
	}
}
