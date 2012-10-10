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
 * This class defines a factory class interface that is injected in the framework to create 
 * the a specific information control
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation;

import org.eclipse.jface.text.IInformationControlCreator;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IReviewAnnotationControlCreatorFactory {
	/**
	 * Method createInformationControlCreator.
	 * 
	 * @return IInformationControlCreator
	 */
	public IInformationControlCreator createInformationControlCreator();
}
