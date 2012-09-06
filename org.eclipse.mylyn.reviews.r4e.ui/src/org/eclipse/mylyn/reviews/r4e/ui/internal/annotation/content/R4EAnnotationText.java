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
 * This class implements a simple text annotation wrapper
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationText {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fParent.
	 */
	private final Object fParent;

	/**
	 * Field fText.
	 */
	private final String fText;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EAnnotationText.
	 * 
	 * @param aParent
	 *            IReviewAnnotation
	 * @param aText
	 *            String
	 */
	R4EAnnotationText(Object aParent, String aText) {
		fParent = aParent;
		fText = aText;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getParent.
	 * 
	 * @return Object
	 */
	public Object getParent() {
		return fParent;
	}

	/**
	 * Method getText.
	 * 
	 * @return String
	 */
	public String getText() {
		return fText;
	}
}
