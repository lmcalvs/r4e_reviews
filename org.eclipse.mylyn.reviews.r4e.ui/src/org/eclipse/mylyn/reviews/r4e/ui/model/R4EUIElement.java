// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the root R4E element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.R4EReviewGroupInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIElement extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
    
	/**
	 * Field ADD_REVIEW_GROUP_DIALOG_TITLE.
	 * (value is ""Enter Review Group details"")
	 */
	private static final String ADD_REVIEW_GROUP_DIALOG_TITLE = "Enter Review Group details";
	
	/**
	 * Field ADD_REVIEW_GROUP_NAME_DIALOG_VALUE.
	 * (value is ""Enter the Review Group Name:"")
	 */
	private static final String ADD_REVIEW_GROUP_NAME_DIALOG_VALUE = "Enter the Review Group Name:";
	
	/**
	 * Field ADD_REVIEW_GROUP_FOLDER_DIALOG_VALUE.
	 * (value is ""Enter the Review Group Folder:"")
	 */
	private static final String ADD_REVIEW_GROUP_FOLDER_DIALOG_VALUE = "Enter the Review Group Folder:";
	
	/**
	 * Field ADD_REVIEW_GROUP_DESCRIPTION_DIALOG_VALUE.
	 * (value is ""Enter the Review Group Description:"")
	 */
	private static final String ADD_REVIEW_GROUP_DESCRIPTION_DIALOG_VALUE = "Enter the Review Group Description:";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fReviewGroups.
	 */
	private final List<R4EUIReviewGroup> fReviewGroups;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EElement.
	 * @param aParent IR4EUIModelElement
	 * @param aName String
	 */
	public R4EUIElement(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName, null);
		fReviewGroups = new ArrayList<R4EUIReviewGroup>();
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 */
	@Override
	public R4EReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EReviewGroup tempReviewGroup = null;
		R4EUIModelController.setDialogOpen(true);
		final R4EReviewGroupInputDialog dialog = new R4EReviewGroupInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell(), ADD_REVIEW_GROUP_DIALOG_TITLE, ADD_REVIEW_GROUP_NAME_DIALOG_VALUE, 
				ADD_REVIEW_GROUP_FOLDER_DIALOG_VALUE, ADD_REVIEW_GROUP_DESCRIPTION_DIALOG_VALUE);
    	final int result = dialog.open();
    	if (result == Window.OK) {
    		tempReviewGroup = RModelFactory.eINSTANCE.createR4EReviewGroup();
    		tempReviewGroup.setName(dialog.getGroupNameValue());
    		tempReviewGroup.setDescription(dialog.getGroupDescriptionValue());
    		tempReviewGroup.setFolder(dialog.getGroupFolderValue());
    	}
    	//else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
    	return tempReviewGroup;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIReviewGroup reviewGroup = null;
		final int reviewGroupsSize = fReviewGroups.size();
		for (int i = 0; i < reviewGroupsSize; i++) {
			
			reviewGroup = fReviewGroups.get(i);
			if (!reviewGroup.isOpen()) continue;  //skip reviews groups that are already closed
			reviewGroup.close();
			reviewGroup.removeListener();
			fireRemove(reviewGroup);
		}
		fReviewGroups.clear();
		fOpen = false;
	}
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fReviewGroups.toArray(new R4EUIReviewGroup[fReviewGroups.size()]);
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fReviewGroups.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Method addInitialChildren.
	 * @param aGroup R4EReviewGroup
	 * @throws ResourceHandlingException 
	 */
	public void loadReviewGroup(R4EReviewGroup aGroup) {
		final R4EUIReviewGroup addedChild = new R4EUIReviewGroup(this, aGroup, false);
		addChildren(addedChild);
	}
	
	/**
	 * Method addChildren.
	 * @param aModelComponent - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(ReviewNavigatorContentProvider)
	 */
	@Override
	public IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		
		final String groupName = ((R4EReviewGroup)aModelComponent).getName();
		
		//Check if group already exists.  If so it cannot be recreated
		for (R4EUIReviewGroup group : fReviewGroups) {
			if (group.getReviewGroup().getName().equals(groupName)) {
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while creating new review group ",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Review Group " +
	    						groupName + " already exists", null), IStatus.ERROR);
				dialog.open();
				return null;
			}
		}
		
		final R4EReviewGroup reviewGroup = R4EUIModelController.FModelExt.createR4EReviewGroup(URI.createFileURI(
				((R4EReviewGroup)aModelComponent).getFolder()), groupName);
		final R4EUIReviewGroup addedChild = new R4EUIReviewGroup(this, reviewGroup, true);
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);

		final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
    	preferenceStore.setValue(PreferenceConstants.P_FILE_PATH, 
    			preferenceStore.getString(PreferenceConstants.P_FILE_PATH) +
    			System.getProperty("line.separator") + reviewGroup.eResource().getURI().toFileString());
		return addedChild;
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fReviewGroups.add((R4EUIReviewGroup) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().
				getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}
	
	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove) {
		fReviewGroups.remove(aChildToRemove);
		aChildToRemove.removeListener();
		fireRemove(aChildToRemove);
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
		if (null != fReviewGroups) {
			R4EUIReviewGroup element = null;
			for (final Iterator<R4EUIReviewGroup> iterator = fReviewGroups.iterator(); iterator.hasNext();) {
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
		if (null != fReviewGroups) {
			R4EUIReviewGroup element = null;
			for (final Iterator<R4EUIReviewGroup> iterator = fReviewGroups.iterator(); iterator.hasNext();) {
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
		return true;
	}
	
	/**
	 * Method getAddChildElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdName()
	 */
	@Override
	public String getAddChildElementCmdName() {
		return R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_NAME;
	}
	
	/**
	 * Method getAddChildElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdTooltip()
	 */
	@Override
	public String getAddChildElementCmdTooltip() {
		return R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_TOOLTIP; 
	}
}
