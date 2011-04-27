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
 * This class encapsulates the properties for the Rule Set UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIRuleSet;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class RuleSetProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field RULE_SET_VERSION. (value is ""ruleSetElement.version"")
	 */
	protected static final String RULE_SET_VERSION_ID = "ruleSetElement.version";

	/**
	 * Field RULE_SET_VERSION_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_SET_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			RULE_SET_VERSION_ID, R4EUIConstants.VERSION_LABEL);

	/**
	 * Field RULE_SET_NAME_ID. (value is ""ruleSetElement.name"")
	 */
	protected static final String RULE_SET_NAME_ID = "ruleSetElement.name";

	/**
	 * Field RULE_SET_NAME_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_SET_NAME_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			RULE_SET_NAME_ID, R4EUIConstants.NAME_LABEL);
	
	/**
	 * Field RULE_SET_FILEPATH. (value is ""ruleSetElement.filePath"")
	 */
	protected static final String RULE_SET_FILEPATH_ID = "ruleSetElement.filePath";

	/**
	 * Field RULE_SET_FILEPATH_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_SET_FILEPATH_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			RULE_SET_FILEPATH_ID, R4EUIConstants.FILE_LABEL);
		
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { RULE_SET_VERSION_PROPERTY_DESCRIPTOR,  
		RULE_SET_NAME_PROPERTY_DESCRIPTOR, RULE_SET_FILEPATH_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ReviewGroupProperties.
	 * @param aElement R4EUIModelElement
	 */
	public RuleSetProperties(R4EUIModelElement aElement) {
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
			if (RULE_SET_VERSION_ID.equals(aId)) { 
				return ((R4EUIRuleSet)getElement()).getRuleSet().getVersion();
			} else if (RULE_SET_NAME_ID.equals(aId)) {
				return ((R4EUIRuleSet)getElement()).getName();
			} else if (RULE_SET_FILEPATH_ID.equals(aId)) {
				if (((R4EUIRuleSet)getElement()).isOpen()) {
					return ((R4EUIRuleSet)getElement()).getRuleSet().eResource().getURI().toFileString();
				}
			}
		}
		return null;
	}
}
