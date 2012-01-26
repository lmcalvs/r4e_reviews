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
 * This class encapsulates the properties for the Anomaly UI model element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general;

import org.eclipse.mylyn.reviews.r4e.core.model.R4ECommentType;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleRank;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AnomalyBasicProperties extends ModelElementProperties {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field ANOMALY_TITLE_ID. (value is ""anomalyElement.title"")
	 */
	private static final String ANOMALY_TITLE_ID = "anomalyElement.title";

	/**
	 * Field ANOMALY_TITLE_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor ANOMALY_TITLE_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			ANOMALY_TITLE_ID, R4EUIConstants.TITLE_LABEL);

	/**
	 * Field ANOMALY_POSITION_ID. (value is ""anomalyElement.position"")
	 */
	private static final String ANOMALY_POSITION_ID = "anomalyElement.position";

	/**
	 * Field ANOMALY_POSITION_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor ANOMALY_POSITION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_POSITION_ID, R4EUIConstants.POSITION_LABEL);

	/**
	 * Field ANOMALY_AUTHOR_ID. (value is ""anomalyElement.author"")
	 */
	private static final String ANOMALY_AUTHOR_ID = "anomalyElement.author";

	/**
	 * Field ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_AUTHOR_ID, R4EUIConstants.AUTHOR_LABEL);

	/**
	 * Field ANOMALY_CREATION_DATE_ID. (value is ""anomalyElement.creationDate"")
	 */
	private static final String ANOMALY_CREATION_DATE_ID = "anomalyElement.creationDate";

	/**
	 * Field ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_CREATION_DATE_ID, R4EUIConstants.CREATION_DATE_LABEL);

	/**
	 * Field ANOMALY_DESCRIPTION_ID. (value is ""anomalyElement.description"")
	 */
	private static final String ANOMALY_DESCRIPTION_ID = "anomalyElement.description";

	/**
	 * Field ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	protected static final TextPropertyDescriptor ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			ANOMALY_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);

	/**
	 * Field ANOMALY_DUE_DATE_ID. (value is ""anomalyElement.dueDate"")
	 */
	private static final String ANOMALY_DUE_DATE_ID = "anomalyElement.dueDate";

	/**
	 * Field ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_DUE_DATE_ID, R4EUIConstants.DUE_DATE_LABEL);

	/**
	 * Field ANOMALY_CLASS_ID. (value is ""anomalyElement.class"")
	 */
	private static final String ANOMALY_CLASS_ID = "anomalyElement.class";

	/**
	 * Field ANOMALY_CLASS_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_CLASS_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_CLASS_ID, R4EUIConstants.CLASS_LABEL, UIUtils.getClasses());

	/**
	 * Field ANOMALY_RANK_ID. (value is ""anomalyElement.rank"")
	 */
	private static final String ANOMALY_RANK_ID = "anomalyElement.rank";

	/**
	 * Field ANOMALY_RANK_PROPERTY_DESCRIPTOR.
	 */
	protected static final ComboBoxPropertyDescriptor ANOMALY_RANK_PROPERTY_DESCRIPTOR = new ComboBoxPropertyDescriptor(
			ANOMALY_RANK_ID, R4EUIConstants.RANK_LABEL, UIUtils.getRanks());

	/**
	 * Field ANOMALY_RULE_ID_ID. (value is ""anomalyElement.ruleId"")
	 */
	private static final String ANOMALY_RULE_ID_ID = "anomalyElement.ruleId";

	/**
	 * Field ANOMALY_RULE_ID_PROPERTY_DESCRIPTOR.
	 */
	protected static final PropertyDescriptor ANOMALY_RULE_ID_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_RULE_ID_ID, R4EUIConstants.RULE_ID_LABEL);

	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { ANOMALY_TITLE_PROPERTY_DESCRIPTOR,
			ANOMALY_POSITION_PROPERTY_DESCRIPTOR, ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR,
			ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR, ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR,
			ANOMALY_DUE_DATE_PROPERTY_DESCRIPTOR, ANOMALY_CLASS_PROPERTY_DESCRIPTOR, ANOMALY_RANK_PROPERTY_DESCRIPTOR,
			ANOMALY_RULE_ID_PROPERTY_DESCRIPTOR };

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for AnomalyProperties.
	 * 
	 * @param aElement
	 *            R4EUIModelElement
	 */
	public AnomalyBasicProperties(R4EUIModelElement aElement) {
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
		if (ANOMALY_TITLE_ID.equals(aId)) {
			return ((R4EUIAnomalyBasic) getElement()).getAnomaly().getTitle();
		} else if (ANOMALY_POSITION_ID.equals(aId)) {
			if (null == ((R4EUIAnomalyBasic) getElement()).getPosition()) {
				return R4EUIConstants.GLOBAL_ANOMALY_PROPERTY_VALUE;
			}
			return ((R4EUIAnomalyBasic) getElement()).getPosition().toString();
		} else if (ANOMALY_AUTHOR_ID.equals(aId)) {
			return ((R4EUIAnomalyBasic) getElement()).getAnomaly().getUser().getId();
		} else if (ANOMALY_CREATION_DATE_ID.equals(aId)) {
			return ((R4EUIAnomalyBasic) getElement()).getAnomaly().getCreatedOn().toString();
		} else if (ANOMALY_DESCRIPTION_ID.equals(aId)) {
			return ((R4EUIAnomalyBasic) getElement()).getAnomaly().getDescription();
		} else if (ANOMALY_DUE_DATE_ID.equals(aId)) {
			if (null != ((R4EUIAnomalyBasic) getElement()).getAnomaly().getDueDate()) {
				return ((R4EUIAnomalyBasic) getElement()).getAnomaly().getDueDate().toString();
			}
		} else if (ANOMALY_CLASS_ID.equals(aId)) {
			final R4ECommentType type = (R4ECommentType) ((R4EUIAnomalyBasic) getElement()).getAnomaly().getType();
			if (null != type) {
				return Integer.valueOf(type.getType().getValue());
			}
		} else if (ANOMALY_RANK_ID.equals(aId)) {
			//Bug 368865:  Mapping needed for DEPRECATED value to MINOR
			int rankValue = ((R4EUIAnomalyBasic) getElement()).getAnomaly().getRank().getValue();
			return Integer.valueOf(rankValue == R4EDesignRuleRank.R4E_RANK_DEPRECATED_VALUE
					? R4EDesignRuleRank.R4E_RANK_MINOR_VALUE
					: rankValue);
		} else if (ANOMALY_RULE_ID_ID.equals(aId)) {
			return ((R4EUIAnomalyBasic) getElement()).getAnomaly().getRuleID();
		}
		return null;
	}
}
