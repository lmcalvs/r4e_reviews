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

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.ParticipantProperties;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIParticipant extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	//TODO get different icons depending on the role
	
	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE.
	 * (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_REVIEWER_ICON_FILE = "icons/obj16/part_obj.png";
	
	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE.
	 * (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_LEAD_ICON_FILE = "icons/obj16/part_obj.png";
	
	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE.
	 * (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_AUTHOR_ICON_FILE = "icons/obj16/part_obj.png";
	
	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE.
	 * (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_ORGANIZER_ICON_FILE = "icons/obj16/part_obj.png";
	
	
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
		
		final EList<R4EUserRole> roles = fParticipant.getRoles();
		for (R4EUserRole role : roles) {
			if (role == R4EUserRole.R4E_ROLE_LEAD) {
				setImage(PARTICIPANT_LEAD_ICON_FILE);
				return;
			} else if (role == R4EUserRole.R4E_ROLE_AUTHOR) {
				setImage(PARTICIPANT_AUTHOR_ICON_FILE);
				return;
			} else if (role == R4EUserRole.R4E_ROLE_ORGANIZER) {
				setImage(PARTICIPANT_ORGANIZER_ICON_FILE);
				return;
			}
		}
		setImage(PARTICIPANT_REVIEWER_ICON_FILE);
		return;
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method getAdapter.
	 * @param adapter Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) return this;
		if (IPropertySource.class.equals(adapter)) return new ParticipantProperties(this);
		return null;
	}
	
	/**
	 * Method getParticipant.
	 * @return R4EParticipant
	 */
	public R4EParticipant getParticipant() {
		return fParticipant;
	}
	
	/**
	 * Method isEnabled.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return getParent().isEnabled();
	}
}
