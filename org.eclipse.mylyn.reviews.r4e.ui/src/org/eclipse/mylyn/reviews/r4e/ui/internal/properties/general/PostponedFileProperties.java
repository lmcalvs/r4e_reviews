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
 * This class encapsulates the properties for the PostponedFile UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIPostponedFile;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class PostponedFileProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field POSTPONED_FILE_VERSION_ID. (value is ""postponedFileElement.version"")
	 */
	private static final String POSTPONED_FILE_VERSION_ID = "postponedFileElement.version";

	/**
	 * Field POSTPONED_FILE_VERSION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor POSTPONED_FILE_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			POSTPONED_FILE_VERSION_ID, "Original File");

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { POSTPONED_FILE_VERSION_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for FileContextProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public PostponedFileProperties(R4EUIModelElement aElement) {
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
		if (POSTPONED_FILE_VERSION_ID.equals(aId)) {
			if (null != ((R4EUIPostponedFile) getElement()).getFileContext().getTarget()) {
				return new FileVersionSourceProperties(((R4EUIPostponedFile) getElement()).getFileContext().getTarget());
			}
			return R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE;
			//TODO:  Add later review item description, this is not trivial...
		}
		return null;
	}
}
