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
 * This class encapsulates the generic properties for the R4E model elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ModelElementProperties implements IPropertySource {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fElement.
	 */
	protected final R4EUIModelElement fElement;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ModelElementProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public ModelElementProperties(R4EUIModelElement aElement) {
		fElement = aElement;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getElement.
	 * 
	 * @return R4EUIModelElement
	 */
	public R4EUIModelElement getElement() {
		return fElement;
	}

	/**
	 * Method getEditableValue.
	 * 
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	public Object getEditableValue() {
		return null; //default implementation
	}

	/**
	 * Method getPropertyDescriptors.
	 * 
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[0]; //default implementation <!-- // $codepro.audit.disable reusableImmutables -->
	}

	/**
	 * Method getPropertyValue.
	 * 
	 * @param aId
	 *            Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	public Object getPropertyValue(Object aId) {
		return null; //default implementation
	}

	/**
	 * Method resetPropertyValue.
	 * 
	 * @param id
	 *            Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(Object)
	 */
	public void resetPropertyValue(Object id) { // $codepro.audit.disable emptyMethod
		//default implementation, no properties are resettable
	}

	/**
	 * Method setPropertyValue.
	 * 
	 * @param id
	 *            Object
	 * @param value
	 *            Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	public void setPropertyValue(Object id, Object value) { // $codepro.audit.disable emptyMethod
		//default implementation
	}

	/**
	 * Method isPropertySet.
	 * 
	 * @param id
	 *            Object
	 * @return boolean
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
	 */
	public boolean isPropertySet(Object id) {
		return false; //   //default implementation, no propery has a default value
	}
}
