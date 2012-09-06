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
 * This factory class creates R4E Annotation Controls
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.EditorsUI;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationInformationControlCreator implements IInformationControlCreator {

	/**
	 * Method createInformationControl.
	 * 
	 * @param parent
	 *            Shell
	 * @return IInformationControl
	 * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(Shell)
	 */
	public IInformationControl createInformationControl(Shell aParent) {
		return new R4EAnnotationInformationControl(aParent, EditorsUI.getTooltipAffordanceString());
	}
}
