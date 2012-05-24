/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
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

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author Sebastien Dubois
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
	 * Field REVIEW_ITEM_AUTHOR_REP_ID. (value is ""reviewItemElement.authorRep"")
	 */
	private static final String REVIEW_ITEM_AUTHOR_REP_ID = "reviewItemElement.authorRep";

	/**
	 * Field REVIEW_ITEM_AUTHOR_REP_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_AUTHOR_REP_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_AUTHOR_REP_ID, R4EUIConstants.EMAIL_LABEL);

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
	 * Field REVIEW_ITEM_CHANGE_ID. (value is ""reviewItemElement.ChangeId"")
	 */
	private static final String REVIEW_ITEM_CHANGE_ID = "reviewItemElement.ChangeId";

	/**
	 * Field REVIEW_ITEM_CHANGE_ID_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_CHANGE_ID_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_CHANGE_ID, R4EUIConstants.CHANGE_ID_LABEL);

	/**
	 * Field REVIEW_ITEM_DATE_SUBMITTED_ID. (value is ""reviewItemElement.dateSubmitted"")
	 */
	private static final String REVIEW_ITEM_DATE_SUBMITTED_ID = "reviewItemElement.dateSubmitted";

	/**
	 * Field REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_DATE_SUBMITTED_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_DATE_SUBMITTED_ID, R4EUIConstants.DATE_SUBMITTED_LABEL);

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
	 * Field REVIEW_ITEM_ASSIGNED_TO_ID. (value is ""reviewItemElement.assignedTo"")
	 */
	private static final String REVIEW_ITEM_ASSIGNED_TO_ID = "reviewItemElement.assignedTo";

	/**
	 * Field REVIEW_ITEM_ASSIGNED_TO_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor REVIEW_ITEM_ASSIGNED_TO_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_ASSIGNED_TO_ID, R4EUIConstants.ASSIGNED_TO_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR,
			REVIEW_ITEM_AUTHOR_REP_PROPERTY_DESCRIPTOR, REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR,
			REVIEW_ITEM_CHANGE_ID_PROPERTY_DESCRIPTOR, REVIEW_ITEM_DATE_SUBMITTED_PROPERTY_DESCRIPTOR,
			REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR };

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
		} else if (REVIEW_ITEM_AUTHOR_REP_ID.equals(aId)) {
			final R4EItem modelItem = ((R4EUIReviewItem) getElement()).getItem();
			if (null != modelItem.getAuthorRep()) {
				return modelItem.getAuthorRep();
			} else {
				try {
					final R4EParticipant participant = R4EUIModelController.getActiveReview().getParticipant(
							modelItem.getAddedById(), false);
					if (null != participant) {
						return participant.getEmail();
					} else {
						return "";
					}
				} catch (ResourceHandlingException e) {
					R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					return "";
				}
			}

		} else if (REVIEW_ITEM_PROJECT_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getProjectURIs();
		} else if (REVIEW_ITEM_CHANGE_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getRepositoryRef();
		} else if (REVIEW_ITEM_DATE_SUBMITTED_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getSubmitted();
		} else if (REVIEW_ITEM_DESCRIPTION_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getDescription();
		} else if (REVIEW_ITEM_ASSIGNED_TO_ID.equals(aId)) {
			return ((R4EUIReviewItem) getElement()).getItem().getAssignedTo();
		}
		return null;
	}
}
