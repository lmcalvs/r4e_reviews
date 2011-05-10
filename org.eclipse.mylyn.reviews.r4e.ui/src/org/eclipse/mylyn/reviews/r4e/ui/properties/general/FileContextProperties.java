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
 * This class encapsulates the properties for the FileContext UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class FileContextProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field FILE_BASE_VERSION_ID. (value is ""fileContextElement.baseVersion"")
	 */
	private static final String FILE_BASE_VERSION_ID = "fileContextElement.baseVersion";

	/**
	 * Field FILE_BASE_VERSION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_BASE_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_BASE_VERSION_ID, "Base file");

	/**
	 * Field FILE_TARGET_VERSION_ID. (value is ""fileContextElement.targetVersion"")
	 */
	private static final String FILE_TARGET_VERSION_ID = "fileContextElement.targetVersion";

	/**
	 * Field FILE_TARGET_VERSION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_TARGET_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_TARGET_VERSION_ID, "Target file");

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { FILE_BASE_VERSION_PROPERTY_DESCRIPTOR,
			FILE_TARGET_VERSION_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for FileContextProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public FileContextProperties(R4EUIModelElement aElement) {
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
		if (FILE_TARGET_VERSION_ID.equals(aId)) {
			if (null != ((R4EUIFileContext) getElement()).getFileContext().getTarget()) {
				return new FileVersionSourceProperties(((R4EUIFileContext) getElement()).getFileContext().getTarget());
			}
			return R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE;
		} else if (FILE_BASE_VERSION_ID.equals(aId)) {
			if (null != ((R4EUIFileContext) getElement()).getFileContext().getBase()) {
				return new FileVersionSourceProperties(((R4EUIFileContext) getElement()).getFileContext().getBase());
			}
			return R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE;
		}
		return null;
	}
}
