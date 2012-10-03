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
 * This class implements a sorter that sorts R4E annotations by type
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIDelta;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUISelection;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationTypeSorter extends ViewerSorter {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method category.
	 * 
	 * @param aAnnotationElement
	 *            Object
	 * @return int
	 */
	@Override
	public int category(Object aAnnotationElement) {
		if (aAnnotationElement instanceof R4EAnnotation) {
			final IR4EUIModelElement sourceElement = ((R4EAnnotation) aAnnotationElement).getSourceElement();
			if (sourceElement instanceof R4EUIDelta) {
				return 0;
			} else if (sourceElement instanceof R4EUISelection) {
				return 1;
			} else if (sourceElement instanceof R4EUIAnomalyBasic) {
				return 2;
			}
		}
		return 3; //Should never happen
	}

	/**
	 * Method sort.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aElements
	 *            Object[]
	 */
	@Override
	public void sort(final Viewer aViewer, Object[] aElements) {
		//Prevent sorting elements alphabetically
	}
}
