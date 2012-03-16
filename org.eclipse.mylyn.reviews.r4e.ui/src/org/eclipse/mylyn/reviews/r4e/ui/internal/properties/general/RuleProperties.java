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
 * This class encapsulates the properties for the Rule UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIRule;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class RuleProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field RULE_ID_ID. (value is ""ruleElement.id"")
	 */
	protected static final String RULE_ID_ID = "ruleElement.id";

	/**
	 * Field RULE_ID_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_ID_PROPERTY_DESCRIPTOR = new PropertyDescriptor(RULE_ID_ID,
			R4EUIConstants.ID_LABEL);

	/**
	 * Field RULE_TITLE_ID. (value is ""ruleElement.title"")
	 */
	protected static final String RULE_TITLE_ID = "ruleElement.title";

	/**
	 * Field RULE_TITLE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_TITLE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(RULE_TITLE_ID,
			R4EUIConstants.TITLE_LABEL);

	/**
	 * Field RULE_DESCRIPTION_ID. (value is ""ruleElement.description"")
	 */
	protected static final String RULE_DESCRIPTION_ID = "ruleElement.description";

	/**
	 * Field RULE_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_DESCRIPTION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			RULE_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);

	/**
	 * Field RULE_CLASS_ID. (value is ""ruleElement.class"")
	 */
	protected static final String RULE_CLASS_ID = "ruleElement.class";

	/**
	 * Field RULE_CLASS_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_CLASS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(RULE_CLASS_ID,
			R4EUIConstants.CLASS_LABEL);

	/**
	 * Field RULE_RANK_ID. (value is ""ruleElement.rank"")
	 */
	protected static final String RULE_RANK_ID = "ruleElement.rank";

	/**
	 * Field RULE_RANK_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor RULE_RANK_PROPERTY_DESCRIPTOR = new PropertyDescriptor(RULE_RANK_ID,
			R4EUIConstants.RANK_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { RULE_ID_PROPERTY_DESCRIPTOR,
			RULE_TITLE_PROPERTY_DESCRIPTOR, RULE_DESCRIPTION_PROPERTY_DESCRIPTOR, RULE_CLASS_PROPERTY_DESCRIPTOR,
			RULE_RANK_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewGroupProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public RuleProperties(R4EUIModelElement aElement) {
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
			if (RULE_ID_ID.equals(aId)) {
				return ((R4EUIRule) getElement()).getRule().getId();
			} else if (RULE_TITLE_ID.equals(aId)) {
				return ((R4EUIRule) getElement()).getRule().getTitle();
			} else if (RULE_DESCRIPTION_ID.equals(aId)) {
				return ((R4EUIRule) getElement()).getRule().getDescription();
			} else if (RULE_CLASS_ID.equals(aId)) {
				return UIUtils.getClasses()[Integer.valueOf(((R4EUIRule) getElement()).getRule().getClass_().getValue())
						.intValue()];
			} else if (RULE_RANK_ID.equals(aId)) {
				//Bug 368865:  Mapping needed for DEPRECATED value to MINOR
				final int rankValue = ((R4EUIRule) getElement()).getRule().getRank().getValue();
				final int intValue = Integer.valueOf(rankValue == R4EDesignRuleRank.R4E_RANK_DEPRECATED_VALUE
						? R4EDesignRuleRank.R4E_RANK_MINOR_VALUE
						: rankValue);
				return UIUtils.getRanks()[intValue];
			}
		}
		return null;
	}
}
