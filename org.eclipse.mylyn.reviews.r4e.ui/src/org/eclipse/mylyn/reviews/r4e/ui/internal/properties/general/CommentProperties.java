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
 * This class encapsulates the properties for the Comment UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class CommentProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field COMMENT_AUTHOR_ID. (value is ""commentElement.author"")
	 */
	private static final String COMMENT_AUTHOR_ID = "commentElement.author";

	/**
	 * Field COMMENT_AUTHOR_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor COMMENT_AUTHOR_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			COMMENT_AUTHOR_ID, R4EUIConstants.AUTHOR_LABEL);

	/**
	 * Field COMMENT_CREATION_DATE_ID. (value is ""commentElement.creationDate"")
	 */
	private static final String COMMENT_CREATION_DATE_ID = "commentElement.creationDate";

	/**
	 * Field COMMENT_CREATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor COMMENT_CREATION_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			COMMENT_CREATION_DATE_ID, R4EUIConstants.CREATION_DATE_LABEL);

	/**
	 * Field COMMENT_DESCRIPTION_ID. (value is ""commentElement.description"")
	 */
	private static final String COMMENT_DESCRIPTION_ID = "commentElement.description";

	/**
	 * Field COMMENT_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor COMMENT_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			COMMENT_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { COMMENT_AUTHOR_PROPERTY_DESCRIPTOR,
			COMMENT_CREATION_DATE_PROPERTY_DESCRIPTOR, COMMENT_DESCRIPTION_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for CommentProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public CommentProperties(R4EUIModelElement aElement) {
		super(aElement);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getPropertyDescriptors.
	 * 
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}

	/**
	 * Method getPropertyValue.
	 * 
	 * @param aId
	 *            Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		if (COMMENT_AUTHOR_ID.equals(aId)) {
			return ((R4EUIComment) getElement()).getComment().getUser().getId();
		} else if (COMMENT_CREATION_DATE_ID.equals(aId)) {
			return ((R4EUIComment) getElement()).getComment().getCreatedOn().toString();
		} else if (COMMENT_DESCRIPTION_ID.equals(aId)) {
			return ((R4EUIComment) getElement()).getComment().getDescription();
		}
		return null;
	}
}
