// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
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
 * This class implements the Rule element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRule;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.RuleProperties;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIRule extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field fSelectionFile. (value is ""icons/obj16/rule_obj.gif"")
	 */
	public static final String RULE_ICON_FILE = "icons/obj16/rule_obj.gif";

	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME. (value is ""Disable Rule"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Rule";

	/**
	 * Field REMOVE_ELEMENT_ACTION_TOOLTIP. (value is ""Disable (and Optionally Remove) this Rule from its parent Rule
	 * Violation"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable (and Optionally Remove) this Rule"
			+ " from its parent Rule Violation";

	/**
	 * Field RESTORE_ELEMENT_COMMAND_NAME. (value is ""Restore Rule"")
	 */
	private static final String RESTORE_ELEMENT_COMMAND_NAME = "Restore Rule";

	/**
	 * Field RESTORE_ELEMENT_ACTION_TOOLTIP. (value is ""Restore this disabled Rule"")
	 */
	private static final String RESTORE_ELEMENT_COMMAND_TOOLTIP = "Restore this disabled Rule";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fDelta.
	 */
	private final R4EDesignRule fRule;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIRule.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aRule
	 *            R4EDesignRule
	 */
	public R4EUIRule(IR4EUIModelElement aParent, R4EDesignRule aRule) {
		super(aParent, aRule.getId());
		fReadOnly = aParent.isReadOnly();
		fRule = aRule;
		setImage(RULE_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return fRule.getTitle();
	}

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
		if (IR4EUIModelElement.class.equals(adapter)) {
			return this;
		}
		if (IPropertySource.class.equals(adapter)) {
			return new RuleProperties(this);
		}
		return null;
	}

	//Attributes

	/**
	 * Method getSelection.
	 * 
	 * @return R4EDelta
	 */
	public R4EDesignRule getRule() {
		return fRule;
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
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRule, R4EUIModelController.getReviewer());
		fRule.setId(((R4EDesignRule) aModelComponent).getId());
		fRule.setTitle(((R4EDesignRule) aModelComponent).getTitle());
		fRule.setDescription(((R4EDesignRule) aModelComponent).getDescription());
		fRule.setClass(((R4EDesignRule) aModelComponent).getClass_());
		fRule.setRank(((R4EDesignRule) aModelComponent).getRank());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
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
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRule, R4EUIModelController.getReviewer());
		fRule.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fRule.isEnabled();
	}

	//Commands

	/**
	 * Method isRemoveElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		if (isEnabled() && !isReadOnly()) {
			return true;
		}
		return false;
	}

	/**
	 * Method getRemoveElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	@Override
	public String getRemoveElementCmdName() {
		return REMOVE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getRemoveElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	@Override
	public String getRemoveElementCmdTooltip() {
		return REMOVE_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (isEnabled() || isReadOnly()) {
			return false;
		}
		return true;
	}

	/**
	 * Method getRestoreElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdName()
	 */
	@Override
	public String getRestoreElementCmdName() {
		return RESTORE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getRestoreElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdTooltip()
	 */
	@Override
	public String getRestoreElementCmdTooltip() {
		return RESTORE_ELEMENT_COMMAND_TOOLTIP;
	}
}
