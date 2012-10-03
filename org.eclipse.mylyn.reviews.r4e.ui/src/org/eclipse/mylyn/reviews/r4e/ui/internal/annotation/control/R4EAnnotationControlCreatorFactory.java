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
 * This class implements a factory class that is injected in the framework to create 
 * the R4E-specific information control
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationControlCreatorFactory;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationControlCreatorFactory implements IReviewAnnotationControlCreatorFactory {

	/**
	 * Method createInformationControlCreator.
	 * 
	 * @return IInformationControlCreator
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationControlCreatorFactory#createInformationControlCreator()
	 */
	public IInformationControlCreator createInformationControlCreator() {
		return new R4EAnnotationInformationControlCreator();
	}

}
