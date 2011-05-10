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
 * This class encapsulates the properties for the Review Item UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewItemProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_ITEM_AUTHOR_ID. (value is ""reviewItemElement.author"")
	 */
	private static final String REVIEW_ITEM_AUTHOR_ID = "reviewItemElement.author";

	/**
	 * Field REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_AUTHOR_ID, R4EUIConstants.AUTHOR_LABEL);

	/**
	 * Field REVIEW_ITEM_PROJECT_ID. (value is ""reviewItemElement.project"")
	 */
	private static final String REVIEW_ITEM_PROJECT_ID = "reviewItemElement.project";

	/**
	 * Field REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_PROJECT_ID, R4EUIConstants.PROJECT_LABEL);

	/**
	 * Field REVIEW_ITEM_DESCRIPTION_ID. (value is ""reviewItemElement.description"")
	 */
	private static final String REVIEW_ITEM_DESCRIPTION_ID = "reviewItemElement.description";

	/**
	 * Field REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR,
			REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR, REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewItemProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public ReviewItemProperties(R4EUIModelElement aElement) {
		super(aElement);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------s

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
		if (REVIEW_ITEM_AUTHOR_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getAddedById();
		} else if (REVIEW_ITEM_PROJECT_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getProjectURIs();
		} else if (REVIEW_ITEM_DESCRIPTION_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getDescription();
		}
		return null;
	}
}
