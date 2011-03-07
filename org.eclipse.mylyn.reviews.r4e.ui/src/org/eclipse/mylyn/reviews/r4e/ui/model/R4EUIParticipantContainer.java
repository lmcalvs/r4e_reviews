// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the Participant Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.ParticipantInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIParticipantContainer extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fSelectionContainerFile.
	 * (value is ""icons/obj16/partcont_obj.png"")
	 */
	private static final String PARTICIPANT_CONTAINER_ICON_FILE = "icons/obj16/partcont_obj.png";
    
	/**
	 * Field ADD_ELEMENT_ACTION_NAME.
	 * (value is ""Add Participant"")
	 */
	private static final String ADD_CHILD_ELEMENT_COMMAND_NAME = "Add Participant";
	
    /**
     * Field ADD_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Add a New Participant to the Current Review"")
     */
    private static final String ADD_CHILD_ELEMENT_COMMAND_TOOLTIP = "Add a New Participant to the Current Review";

    
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fSelections.
	 */
	private final List<R4EUIParticipant> fParticipants;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUIParticipantContainer.
	 * @param aParent IR4EUIModelElement
	 * @param aName String
	 */
	public R4EUIParticipantContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName, null);
		fParticipants = new ArrayList<R4EUIParticipant>();
		setImage(PARTICIPANT_CONTAINER_ICON_FILE);
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 */
	@Override
	public R4EReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EParticipant tempParticipant = null;
		R4EUIModelController.setDialogOpen(true);
		final ParticipantInputDialog dialog = new ParticipantInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell());
    	final int result = dialog.open();
    	if (result == Window.OK) {
    		tempParticipant = RModelFactory.eINSTANCE.createR4EParticipant();
    		tempParticipant.setId(dialog.getParticipantIdValue());
    		tempParticipant.getRoles().addAll(dialog.getParticipantRolesValue());
    		tempParticipant.setFocusArea(dialog.getFocusAreaValue());
    	}
    	// else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
    	return tempParticipant;
	}
	
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fParticipants.toArray(new R4EUIParticipant[fParticipants.size()]);
	}
	
	/**
	 * Method getSelectionList.
	 * @return List<R4EUIParticipant>
	 */
	public List<R4EUIParticipant> getParticipantList() {
		return fParticipants;
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fParticipants.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIParticipant participant = null;
		final int participantsSize = fParticipants.size();
		for (int i = 0; i < participantsSize; i++) {
			
			participant = fParticipants.get(i);
			participant.close();
		}
		fParticipants.clear();
		fOpen = false;
		removeListener();
	}
	
	/**
	 * Method open.
	 */
	@Override
	public void open() {
		final List<R4EParticipant> participants = ((R4EUIReviewBasic)getParent()).getParticipants();
		if (null != participants) {
			final int participantsSize = participants.size();
			for (int i = 0; i < participantsSize; i++) {
				addChildren(new R4EUIParticipant(this, participants.get(i)));
			}
		}
		fOpen = true;
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
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fParticipants.add((R4EUIParticipant) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}

	/**
	 * Method createChildren
	 * @param aModelComponent - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(ReviewNavigatorContentProvider)
	 */
	@Override
	public IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		final R4EParticipant participant = R4EUIModelController.FModelExt.createR4EParticipant(
				((R4EUIReviewBasic)getParent()).getReview(), ((R4EParticipant)aModelComponent).getId(), ((R4EParticipant)aModelComponent).getRoles());
		final R4EUIParticipant addedChild = new R4EUIParticipant(this, participant);
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);
		return addedChild;
	}
	
	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @param aFileRemove - also remove from file (hard remove)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove) {
		fParticipants.remove(aChildToRemove);
		aChildToRemove.removeListener();
		fireRemove(aChildToRemove);
	}
	
	/**
	 * Method removeAllChildren.
	 * @param aFileRemove boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) {
		//Recursively remove all children
		for (R4EUIParticipant participant : fParticipants) {
			removeChildren(participant, aFileRemove);
		}
	}
	
	//Listeners

	/**
	 * Method addListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
		fListener = aProvider;
		if (null != fParticipants) {
			R4EUIParticipant element = null;
			for (final Iterator<R4EUIParticipant> iterator = fParticipants.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.addListener(aProvider);
			}
		}
	}

	/**
	 * Method removeListener.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener() {
		fListener = null;
		if (null != fParticipants) {
			R4EUIParticipant element = null;
			for (final Iterator<R4EUIParticipant> iterator = fParticipants.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener();
			}
		}
	}
	
	//Commands
	
	/**
	 * Method isAddChildElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddChildElementCmd()
	 */
	@Override
	public boolean isAddChildElementCmd() {
		if (getParent().isEnabled() && !(R4EUIModelController.getActiveReview().isReviewed())) return true;
		return false;
	}
	
	/**
	 * Method getAddChildElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdName()
	 */
	@Override
	public String getAddChildElementCmdName() {
		return ADD_CHILD_ELEMENT_COMMAND_NAME;
	}
	
	/**
	 * Method getAddChildElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdTooltip()
	 */
	@Override
	public String getAddChildElementCmdTooltip() {
		return ADD_CHILD_ELEMENT_COMMAND_TOOLTIP; 
	}
}
