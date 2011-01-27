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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.AddLinkedAnomalyAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.ChangeReviewedStateAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.OpenEditorAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.RemoveNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

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
	private static final String REMOVE_ELEMENT_ACTION_NAME = "Delete Selection";
	
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this selection from its parent file"")
     */
    private static final String REMOVE_ELEMENT_ACTION_TOOLTIP = "Remove this selection from its parent file";
    
    /**
     * Field CHANGE_REVIEW_STATE_ACTION_NAME.
     * (value is ""Mark/Unmark as completed"")
     */
    private static final String CHANGE_REVIEW_STATE_ACTION_NAME = "Mark/Unmark as completed";
    
    /**
     * Field CHANGE_REVIEW_STATE_ACTION_TOOLTIP.
     * (value is ""Mark/Unmark this selection as reviewed"")
     */
    private static final String CHANGE_REVIEW_STATE_ACTION_TOOLTIP = "Mark/Unmark this selection as reviewed";
    
	/**
	 * Field CHANGE_REVIEW_STATE_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String CHANGE_REVIEW_STATE_ACTION_ICON_FILE = "icons/done.gif";
	
    /**
     * Field OPEN_EDITOR_ACTION_NAME.
     * (value is ""Open parent file in editor"")
     */
    private static final String OPEN_EDITOR_ACTION_NAME = "Open parent file in editor";
    
    /**
     * Field OPEN_EDITOR_ACTION_TOOLTIP.
     * (value is ""Open the parent file with the matching editor and locate this selection"")
     */
    private static final String OPEN_EDITOR_ACTION_TOOLTIP = "Open the parent file with the matching editor and locate this selection";
    
	/**
	 * Field OPEN_EDITOR_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String OPEN_EDITOR_ACTION_ICON_FILE = "icons/open_file.gif";
	
	/**
	 * Field ADD_ANOMALY_ACTION_NAME.
	 * (value is ""Add linked anomaly"")
	 */
	private static final String ADD_ANOMALY_ACTION_NAME = "Add linked anomaly";
	
    /**
     * Field ADD_ANOMALY_ACTION_TOOLTIP.
     * (value is ""Add a new linked anomaly to the current selection"")
     */
    private static final String ADD_ANOMALY_ACTION_TOOLTIP = "Add a new linked anomaly to the current selection";
	
	/**
	 * Field SELECTION_POSITION_ID. (value is ""selectionElement.position"")
	 */
	private static final String SELECTION_POSITION_ID = "selectionElement.position";

	/**
	 * Field SELECTION_POSITION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor SELECTION_POSITION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			SELECTION_POSITION_ID, R4EUIConstants.POSITION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { SELECTION_POSITION_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
    /**
     * Field fDelta.
     */
    private final R4EDelta fDelta;
    
	/**
	 * Field fContextRemoveNodeAction.
	 */
	private static RemoveNodeAction FContextRemoveNodeAction = null;
	
	/**
	 * Field fChangeReviewedStateAction.
	 */
	private static ChangeReviewedStateAction FChangeReviewedStateAction = null;
	
	/**
	 * Field FContextOpenEditorAction.
	 */
	private static OpenEditorAction FContextOpenEditorAction = null;
	
	/**
	 * Field FAddLinkedAnomalyAction.
	 */
	private static AddLinkedAnomalyAction FAddLinkedAnomalyAction = null;
	
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
		if (aReviewed) {
			if (fReviewed != aReviewed) {   //Reviewed state is changed
				//Add delta to the reviewedContent for this user
				addContentReviewed();
				fReviewed = aReviewed;

				//Check to see if we should mark the parent reviewed as well
				getParent().getParent().checkToSetReviewed();
			}
		} else {
			if (fReviewed != aReviewed) {   //Reviewed state is changed
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
				fReviewed = aReviewed;

				//Remove check on parent, since at least one children is not set anymore
				getParent().getParent().setReviewed(aReviewed);
			}
		}
		fireReviewStateChanged(this);
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
		if (aReviewed) {
			if (fReviewed != aReviewed) {   //Reviewed state is changed
				//Add delta to the reviewedContent for this user
				addContentReviewed();
			}
		} else {
			if (fReviewed != aReviewed) {   //Reviewed state is changed
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
			}
		}
		fReviewed = aReviewed;
		fireReviewStateChanged(this);
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
	@Override
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
			Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logError("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while setting element as reviewed",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
		} catch (OutOfSyncException e) {
			Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected setting element as reviewed" +
					"Please close the reviewe and re-open to sync in content",
    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
			dialog.open();
			// TODO later we will want to do this automatically
		}
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
		if (SELECTION_POSITION_ID.equals(aId)) {
			return fPosition.toString();
		}
		return null;
	}
	
	
	//Actions
	
	/**
	 * Method createActions.
	 * @param aView ReviewNavigatorView
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createActions(ReviewNavigatorView)
	 */
	@Override
	public void createActions(ReviewNavigatorView aView) {
		FAddLinkedAnomalyAction = new AddLinkedAnomalyAction(aView, ADD_ANOMALY_ACTION_NAME, ADD_ANOMALY_ACTION_TOOLTIP,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD));
		FContextRemoveNodeAction = new RemoveNodeAction(aView, REMOVE_ELEMENT_ACTION_NAME, REMOVE_ELEMENT_ACTION_TOOLTIP,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		FChangeReviewedStateAction = new ChangeReviewedStateAction(aView, CHANGE_REVIEW_STATE_ACTION_NAME, CHANGE_REVIEW_STATE_ACTION_TOOLTIP, 
				ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(CHANGE_REVIEW_STATE_ACTION_ICON_FILE)));
		FContextOpenEditorAction = new OpenEditorAction(aView, OPEN_EDITOR_ACTION_NAME, OPEN_EDITOR_ACTION_TOOLTIP, 
				ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(OPEN_EDITOR_ACTION_ICON_FILE)));
	}

	/**
	 * Method getActions.
	 * @param aView ReviewNavigatorView
	 * @return List<Action>
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getActions(ReviewNavigatorView)
	 */
	@Override
	public List<IAction> getActions(ReviewNavigatorView aView) {
		if (null == FContextRemoveNodeAction) createActions(aView);
		final List<IAction> actions = new ArrayList<IAction>();
		if (!(R4EUIModelController.isDialogOpen()) && isOpen()) {
			actions.add(FAddLinkedAnomalyAction);
			actions.add(FContextRemoveNodeAction);
			actions.add(FChangeReviewedStateAction);
			if (null != ((R4EUIFileContext)getParent().getParent()).getTargetFile()) actions.add(FContextOpenEditorAction);	
		}
		return actions;
	}
}
