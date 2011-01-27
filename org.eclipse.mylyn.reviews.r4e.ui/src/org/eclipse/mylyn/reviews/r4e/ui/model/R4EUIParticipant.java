// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Participant element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIParticipant extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fSelectionFile.
	 * (value is ""icons/selection.gif"")
	 */
	private static final String PARTICIPANT_ICON_FILE = "icons/participant.png";
	
	/**
	 * Field PARTICIPANT_ID. (value is ""participantElement.id"")
	 */
	private static final String PARTICIPANT_ID = "participantElement.id";

	/**
	 * Field PARTICIPANT_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_ID, R4EUIConstants.ID_LABEL);
	
	/**
	 * Field PARTICIPANT_NUM_COMMENTS_ID. (value is ""participantElement.numComments"")
	 */
	private static final String PARTICIPANT_NUM_COMMENTS_ID = "participantElement.numComments";

	/**
	 * Field PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			PARTICIPANT_NUM_COMMENTS_ID, R4EUIConstants.NUM_COMMENTS_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { PARTICIPANT_PROPERTY_DESCRIPTOR,  
		PARTICIPANT_NUM_COMMENTS_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
    /**
     * Field fParticipant.
     */
    private final R4EParticipant fParticipant;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUIParticipant.
	 * @param aParent IR4EUIModelElement
	 * @param aParticipant R4EParticipant
	 */
	public R4EUIParticipant(IR4EUIModelElement aParent, R4EParticipant aParticipant) {
		super(aParent, aParticipant.getId(), null);  //TODO add email adress as tooltip later
		fParticipant = aParticipant;
		fImage = UIUtils.loadIcon(PARTICIPANT_ICON_FILE);
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	/**
	 * Method getParticipant.
	 * @return R4EParticipant
	 */
	public R4EParticipant getParticipant() {
		return fParticipant;
	}
	
	// Properties
	
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
		if (PARTICIPANT_ID.equals(aId)) { 
			return fParticipant.getId();
		} else if (PARTICIPANT_NUM_COMMENTS_ID.equals(aId)) {
			return Integer.valueOf(fParticipant.getAddedComments().size());
		}
		return null;
	}
}
