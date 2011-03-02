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
 * This class encapsulates the properties for the Review UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties;

import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field REVIEW_NAME_ID. (value is ""reviewElement.name"")
	 */
	private static final String REVIEW_NAME_ID = "reviewElement.name";

	/**
	 * Field REVIEW_NAME_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor REVIEW_NAME_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_NAME_ID, R4EUIConstants.NAME_LABEL);

	/**
	 * Field REVIEW_CREATION_DATE_ID. (value is ""reviewElement.creationDate"")
	 */
	private static final String					REVIEW_CREATION_DATE_ID						= "reviewElement.creationDate";

	/**
	 * Field REVIEW_CREATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor		REVIEW_CREATION_DATE_PROPERTY_DESCRIPTOR	= new PropertyDescriptor(
																									REVIEW_CREATION_DATE_ID,
																									R4EUIConstants.CREATION_DATE_LABEL);
	
	/**
	 * Field REVIEW_DESCRIPTION_ID. (value is ""reviewElement.description"")
	 */
	private static final String REVIEW_DESCRIPTION_ID = "reviewElement.description";

	/**
	 * Field REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[]	DESCRIPTORS									= {
			REVIEW_NAME_PROPERTY_DESCRIPTOR, REVIEW_CREATION_DATE_PROPERTY_DESCRIPTOR,
			REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR											};
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ReviewProperties.
	 * @param aElement R4EUIModelElement
	 */
	public ReviewProperties(R4EUIModelElement aElement) {
		super(aElement);
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getPropertyDescriptors.
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return DESCRIPTORS;
	}
	
	/**
	 * Method getPropertyValue.
	 * @param aId Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		if (null != getElement()) {
			if (REVIEW_NAME_ID.equals(aId)) { 
				return ((R4EUIReview)getElement()).getReview().getName();
			} else if (REVIEW_CREATION_DATE_ID.equals(aId)) {
				return ((R4EUIReview) getElement()).getReview().getStartDate().toString();
			} else if (REVIEW_DESCRIPTION_ID.equals(aId)) {
				return ((R4EUIReview)getElement()).getReview().getExtraNotes();
			}
		}
		return null;
	}

	/**
	 * Method setPropertyValue.
	 * @param aId Object
	 * @param aValue Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	@Override
	public void setPropertyValue(Object aId, Object aValue) { // $codepro.audit.disable emptyMethod
		if (!(R4EUIModelController.isDialogOpen()) && getElement().isOpen()) {
			try {
				if (REVIEW_NAME_ID.equals(aId)) { 
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(((R4EUIReview)getElement()).getReview(), 
							R4EUIModelController.getReviewer());
					((R4EUIReview)getElement()).getReview().setName((String) aValue);
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				} else if (REVIEW_DESCRIPTION_ID.equals(aId)) {
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(((R4EUIReview)getElement()).getReview(), 
							R4EUIModelController.getReviewer());
					((R4EUIReview)getElement()).getReview().setExtraNotes((String) aValue);
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				}
			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);
			} catch (OutOfSyncException e) {
				UIUtils.displaySyncErrorDialog(e);
			}
		}
	}
}
