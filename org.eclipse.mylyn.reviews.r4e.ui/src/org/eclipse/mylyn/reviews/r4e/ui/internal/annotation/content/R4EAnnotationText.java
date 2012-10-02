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

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Field fChildText.
	 */
	private final List<String> fChildText;

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
	 * @param aChildText
	 *            List<String>
	 */
	R4EAnnotationText(Object aParent, String aText, List<String> aChildText) {
		fParent = aParent;
		fText = aText;
		fChildText = aChildText;
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

	/**
	 * Method getChildren.
	 * 
	 * @return Object[]
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotation#getChildren()
	 */
	public Object[] getChildren() {
		final List<R4EAnnotationText> values = new ArrayList<R4EAnnotationText>();
		if (null != fChildText) {
			for (String contentLine : fChildText) {
				values.add(new R4EAnnotationText(this, contentLine, null));
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
		if (null != fChildText) {
			return fChildText.size() > 0;
		}
		return false;
	}
}
