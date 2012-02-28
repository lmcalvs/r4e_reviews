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
 * This class encapsulates the properties for the Review Group UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ReviewGroupProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field GROUP_NAME_ID. (value is ""reviewGroupElement.name"")
	 */
	protected static final String GROUP_NAME_ID = "reviewGroupElement.name";

	/**
	 * Field GROUP_NAME_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor GROUP_NAME_PROPERTY_DESCRIPTOR = new PropertyDescriptor(GROUP_NAME_ID,
			R4EUIConstants.NAME_LABEL);

	/**
	 * Field GROUP_FOLDER_ID. (value is ""reviewGroupElement.folderName"")
	 */
	protected static final String GROUP_FOLDER_ID = "reviewGroupElement.folderName";

	/**
	 * Field GROUP_FOLDER_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor GROUP_FOLDER_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_FOLDER_ID, R4EUIConstants.FOLDER_LABEL);

	/**
	 * Field GROUP_DESCRIPTION_ID. (value is ""reviewGroupElement.description"")
	 */
	protected static final String GROUP_DESCRIPTION_ID = "reviewGroupElement.description";

	/**
	 * Field GROUP_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor GROUP_DESCRIPTION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);

	/**
	 * Field GROUP_AVAILABLE_PROJECTS_ID. (value is ""reviewGroupElement.availableProjects"")
	 */
	private static final String GROUP_AVAILABLE_PROJECTS_ID = "reviewGroupElement.availableProjects";

	/**
	 * Field GROUP_AVAILABLE_PROJECTS_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor GROUP_AVAILABLE_PROJECTS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_AVAILABLE_PROJECTS_ID, R4EUIConstants.AVAILABLE_PROJECTS_LABEL);

	/**
	 * Field GROUP_AVAILABLE_COMPONENTS_ID. (value is ""reviewGroupElement.availableComponents"")
	 */
	private static final String GROUP_AVAILABLE_COMPONENTS_ID = "reviewGroupElement.availableComponents";

	/**
	 * Field GROUP_AVAILABLE_COMPONENTS_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor GROUP_AVAILABLE_COMPONENTS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_AVAILABLE_COMPONENTS_ID, R4EUIConstants.AVAILABLE_COMPONENTS_LABEL);

	/**
	 * Field GROUP_DEFAULT_ENTRY_CRITERIA_ID. (value is ""reviewGroupElement.defaultEntryCriteria"")
	 */
	private static final String GROUP_DEFAULT_ENTRY_CRITERIA_ID = "reviewGroupElement.defaultEntryCriteria";

	/**
	 * Field GROUP_DEFAULT_ENTRY_CRITERIA_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor GROUP_DEFAULT_ENTRY_CRITERIA_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_DEFAULT_ENTRY_CRITERIA_ID, R4EUIConstants.DEFAULT_ENTRY_CRITERIA_LABEL);

	/**
	 * Field GROUP_APPLIED_RULE_SETS_ID. (value is ""reviewGroupElement.appliedRuleSets"")
	 */
	private static final String GROUP_APPLIED_RULE_SETS_ID = "reviewGroupElement.appliedRuleSets";

	/**
	 * Field GROUP_APPLIED_RULE_SETS_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor GROUP_APPLIED_RULE_SETS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_APPLIED_RULE_SETS_ID, R4EUIConstants.RULE_SETS_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { GROUP_NAME_PROPERTY_DESCRIPTOR,
			GROUP_FOLDER_PROPERTY_DESCRIPTOR, GROUP_DESCRIPTION_PROPERTY_DESCRIPTOR,
			GROUP_AVAILABLE_PROJECTS_PROPERTY_DESCRIPTOR, GROUP_AVAILABLE_COMPONENTS_PROPERTY_DESCRIPTOR,
			GROUP_DEFAULT_ENTRY_CRITERIA_PROPERTY_DESCRIPTOR, GROUP_APPLIED_RULE_SETS_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewGroupProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public ReviewGroupProperties(R4EUIModelElement aElement) {
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
		if (null != getElement()) {
			if (GROUP_NAME_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getName();
			} else if (GROUP_FOLDER_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getFolder();
			} else if (GROUP_DESCRIPTION_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getDescription();
			} else if (GROUP_AVAILABLE_PROJECTS_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getAvailableProjects().toString();
			} else if (GROUP_AVAILABLE_COMPONENTS_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getAvailableComponents().toString();
			} else if (GROUP_DEFAULT_ENTRY_CRITERIA_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getDefaultEntryCriteria();
			} else if (GROUP_APPLIED_RULE_SETS_ID.equals(aId)) {
				return ((R4EUIReviewGroup) getElement()).getReviewGroup().getDesignRuleLocations().toString();
			}
		}
		return null;
	}
}
