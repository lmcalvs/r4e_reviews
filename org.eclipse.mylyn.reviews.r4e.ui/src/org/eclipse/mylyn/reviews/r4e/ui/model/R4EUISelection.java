// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
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
 * This class implements the Selection element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.properties.SelectionProperties;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUISelection extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fSelectionFile.
	 * (value is ""icons/selection.gif"")
	 */
	private static final String SELECTION_ICON_FILE = "icons/selection.gif";
	
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Selection"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Delete Selection";
	
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this selection from its parent file"")
     */
    private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Remove this selection from its parent file";


	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
    /**
     * Field fDelta.
     */
    private final R4EDelta fDelta;
	
	/**
	 * Field fPosition.
	 */
	private final IR4EUIPosition fPosition;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUISelection.
	 * @param aParent IR4EUIModelElement
	 * @param aDelta R4EDelta
	 * @param aPosition IR4EUIPosition
	 */
	public R4EUISelection(IR4EUIModelElement aParent, R4EDelta aDelta, IR4EUIPosition aPosition) {
		super(aParent, aPosition.toString(), "Added by: " + 
				((R4EItem)aDelta.eContainer().eContainer()).getAddedBy().getId()); // $codepro.audit.disable methodChainLength
		fDelta = aDelta;
		fPosition = aPosition;
		fImage = UIUtils.loadIcon(SELECTION_ICON_FILE);
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
		if (IPropertySource.class.equals(adapter)) return new SelectionProperties(this);
		return null;
	}
	
	//Attributes
	
	/**
	 * Method getSelection.
	 * @return R4EDelta
	 */
	public R4EDelta getSelection() {
		return fDelta;
	}
	
	/**
	 * Method getPosition.
	 * @return IR4EPosition
	 */
	public IR4EUIPosition getPosition() {
		return fPosition;
	}
	
	/**
	 * Method setReviewed.
	 * @param aReviewed boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setReviewed(boolean)
	 */
	@Override
	public void setReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException {
		if (fReviewed != aReviewed) {   //Reviewed state is changed
			fReviewed = aReviewed;
			if (fReviewed) {
				//Add delta to the reviewedContent for this user
				addContentReviewed();
				
				//Check to see if we should mark the parent reviewed as well
				getParent().getParent().checkToSetReviewed();
			} else {
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
				
				//Remove check on parent, since at least one children is not set anymore
				getParent().getParent().setReviewed(fReviewed);
			}
			fireReviewStateChanged(this);
		}
	}
	
	/**
	 * Method setChildrenReviewed.
	 * @param aReviewed boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setChildReviewed(boolean)
	 */
	@Override
	public void setChildReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException {
		if (fReviewed != aReviewed) {   //Reviewed state is changed
			fReviewed = aReviewed;
			if (aReviewed) {
				//Add delta to the reviewedContent for this user
				addContentReviewed();
			} else {
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
			}
			fReviewed = aReviewed;
			fireReviewStateChanged(this);
		}
	}
	
	
	/**
	 * Method addContentReviewed.
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addContentReviewed() throws ResourceHandlingException, OutOfSyncException {
		//First get the current user
		final R4EUIReview review = (R4EUIReview) getParent().getParent().getParent().getParent(); // $codepro.audit.disable methodChainLength
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), true);
		
		//Add this selection to the reviewed content for this user
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
		user.getReviewedContent().add(fDelta.getId());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}
	
	/**
	 * Method removeContentReviewed.
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void removeContentReviewed() throws ResourceHandlingException, OutOfSyncException {
		//First get the current user
		final R4EUIReview review = (R4EUIReview) getParent().getParent().getParent().getParent(); // $codepro.audit.disable methodChainLength
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);
		
		if (null != user) {
			//Remove this selection from the reviewed content for this user
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
			user.getReviewedContent().remove(fDelta.getId());
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}
	}
	
	/**
	 * Load the serialization model data for this element
	 */
	public void loadModelData() {
		try {
			final R4EUIReview review = (R4EUIReview) getParent().getParent().getParent().getParent(); // $codepro.audit.disable methodChainLength
			final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

			if (null != user) {
				//Check if the current selection is part of the reviewed content 
				if (user.getReviewedContent().contains(fDelta.getId())) {
					setReviewed(true);
				}			
			}
		} catch (ResourceHandlingException e) {
			UIUtils.displayResourceErrorDialog(e);

		} catch (OutOfSyncException e) {
			UIUtils.displaySyncErrorDialog(e);

		}
	}
	
	
	//Commands
	
	/**
	 * Method isAddLinkedAnomalyCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddLinkedAnomalyCmd()
	 */
	@Override
	public boolean isAddLinkedAnomalyCmd() {
		return true;
	}
	
	/**
	 * Method isOpenEditorCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	@Override
	public boolean isOpenEditorCmd() {
		return true;
	}
	
	/**
	 * Method isChangeReviewStateCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isChangeReviewStateCmd()
	 */
	@Override
	public boolean isChangeReviewStateCmd() {
		return true;
	}
	
	/**
	 * Method isRemoveElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		return true;
	}
	
	/**
	 * Method getRemoveElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	@Override
	public String getRemoveElementCmdName() {
		return REMOVE_ELEMENT_COMMAND_NAME;
	}
	
	/**
	 * Method getRemoveElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	@Override
	public String getRemoveElementCmdTooltip() {
		return REMOVE_ELEMENT_COMMAND_TOOLTIP;
	}
}
