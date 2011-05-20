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

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.ParticipantProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.userSearch.query.IQueryUser;
import org.eclipse.mylyn.reviews.userSearch.query.QueryUserFactory;
import org.eclipse.mylyn.reviews.userSearch.userInfo.IUserInfo;
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
	 * Field PARTICIPANT_REVIEWER_ICON_FILE. (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_REVIEWER_ICON_FILE = "icons/obj16/part_obj.png";

	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE. (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_LEAD_ICON_FILE = "icons/obj16/part_obj.png";

	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE. (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_AUTHOR_ICON_FILE = "icons/obj16/part_obj.png";

	/**
	 * Field PARTICIPANT_REVIEWER_ICON_FILE. (value is ""icons/obj16/part_obj.png"")
	 */
	private static final String PARTICIPANT_ORGANIZER_ICON_FILE = "icons/obj16/part_obj.png";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fParticipant.
	 */
	private final R4EParticipant fParticipant;

	/**
	 * Field fParticipantDetails.
	 */
	private String fParticipantDetails = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIParticipant.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aParticipant
	 *            R4EParticipant
	 */
	public R4EUIParticipant(IR4EUIModelElement aParent, R4EParticipant aParticipant) {
		super(aParent, aParticipant.getId(), aParticipant.getEmail());
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
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter))
			return this;
		if (IPropertySource.class.equals(adapter))
			return new ParticipantProperties(this);
		return null;
	}

	/**
	 * Method getParticipant.
	 * 
	 * @return R4EParticipant
	 */
	public R4EParticipant getParticipant() {
		return fParticipant;
	}

	/**
	 * Method getRoles.
	 * 
	 * @param aRoles
	 *            EList<R4EUserRole>
	 * @return String[]
	 */
	public String[] getRoles(EList<R4EUserRole> aRoles) {
		final List<String> roles = new ArrayList<String>();
		for (R4EUserRole role : aRoles) {
			if (role.getValue() == R4EUserRole.R4E_ROLE_ORGANIZER_VALUE) {
				roles.add(R4EUIConstants.USER_ROLE_ORGANIZER);
			} else if (role.getValue() == R4EUserRole.R4E_ROLE_LEAD_VALUE) {
				roles.add(R4EUIConstants.USER_ROLE_LEAD);
			} else if (role.getValue() == R4EUserRole.R4E_ROLE_AUTHOR_VALUE) {
				roles.add(R4EUIConstants.USER_ROLE_AUTHOR);
			} else if (role.getValue() == R4EUserRole.R4E_ROLE_REVIEWER_VALUE) {
				roles.add(R4EUIConstants.USER_ROLE_REVIEWER);
			}
		}
		return roles.toArray(new String[roles.size()]);
	}

	/**
	 * Method mapStringToRole.
	 * 
	 * @param aRoleStr
	 *            String
	 * @return R4EUserRole
	 */
	public R4EUserRole mapStringToRole(String aRoleStr) {
		if (aRoleStr.equals(R4EUIConstants.USER_ROLE_ORGANIZER)) {
			return R4EUserRole.R4E_ROLE_ORGANIZER;
		} else if (aRoleStr.equals(R4EUIConstants.USER_ROLE_LEAD)) {
			return R4EUserRole.R4E_ROLE_LEAD;
		}
		if (aRoleStr.equals(R4EUIConstants.USER_ROLE_AUTHOR)) {
			return R4EUserRole.R4E_ROLE_AUTHOR;
		} else if (aRoleStr.equals(R4EUIConstants.USER_ROLE_REVIEWER)) {
			return R4EUserRole.R4E_ROLE_REVIEWER;
		}
		return null;
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fParticipant.isEnabled();
	}

	/**
	 * Method setEnabled.
	 * 
	 * @param aEnabled
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setUserReviewed(boolean)
	 */
	@Override
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException {
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fParticipant,
				R4EUIModelController.getReviewer());
		fParticipant.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}

	/**
	 * Set serialization model data by copying it from the passed-in object
	 * 
	 * @param aModelComponent
	 *            - a serialization model element to copy information from
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setModelData(R4EReviewComponent)
	 */
	@Override
	public void setModelData(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fParticipant,
				R4EUIModelController.getReviewer());
		fParticipant.setId(((R4EParticipant) aModelComponent).getId());
		fParticipant.setEmail(((R4EParticipant) aModelComponent).getEmail());
		fParticipant.getRoles().addAll(((R4EParticipant) aModelComponent).getRoles());
		fParticipant.setFocusArea(((R4EParticipant) aModelComponent).getFocusArea());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Method getParticipantDetails.
	 * 
	 * @return String
	 */
	public String getParticipantDetails() {
		return fParticipantDetails;
	}

	/**
	 * Method setParticipantDetails.
	 */
	public void setParticipantDetails() {
		if (R4EUIModelController.isUserQueryAvailable()) {
			try {
				//Get detailed info from DB if available
				final IQueryUser query = new QueryUserFactory().getInstance();
				final List<IUserInfo> info = query.searchByUserId(fParticipant.getId());
				if (info.size() > 0) {
					final IUserInfo userInfo = info.get(0);
					fParticipantDetails = UIUtils.buildUserDetailsString(userInfo);
					if (null == fParticipant.getEmail()) {
						fParticipant.setEmail(userInfo.getEmail());
					}
				}
			} catch (NamingException e) {
				Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			} catch (IOException e) {
				Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			}
		}
	}

	/**
	 * Method isRemoveElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		if (isEnabled())
			return true;
		return false;
	}

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (!(getParent().getParent().isEnabled()))
			return false;
		if (isEnabled())
			return false;
		return true;
	}

}
