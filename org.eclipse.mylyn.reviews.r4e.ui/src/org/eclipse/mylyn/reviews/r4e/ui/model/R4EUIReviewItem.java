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
 * This class implements the Review Item element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.ChangeReviewedStateAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.RemoveNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
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
public class R4EUIReviewItem extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fReviewItemFile.
	 * (value is ""icons/review_item.gif"")
	 */
	private static final String REVIEW_ITEM_ICON_FILE = "icons/review_item.gif";
	
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Review Item"")
	 */
	private static final String REMOVE_ELEMENT_ACTION_NAME = "Delete Review Item";
	
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this review item from its parent review"")
     */
    private static final String REMOVE_ELEMENT_ACTION_TOOLTIP = "Remove this review item from its parent review";
    
    /**
     * Field CHANGE_REVIEW_STATE_ACTION_NAME.
     * (value is ""Mark/Unmark as completed"")
     */
    private static final String CHANGE_REVIEW_STATE_ACTION_NAME = "Mark/Unmark as completed";
    
    /**
     * Field CHANGE_REVIEW_STATE_ACTION_TOOLTIP.
     * (value is ""Mark/Unmark this review item as reviewed"")
     */
    private static final String CHANGE_REVIEW_STATE_ACTION_TOOLTIP = "Mark/Unmark this review item as reviewed";
    
	/**
	 * Field CHANGE_REVIEW_STATE_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String CHANGE_REVIEW_STATE_ACTION_ICON_FILE = "icons/done.gif";
	
	/**
	 * Field REVIEW_ITEM_AUTHOR_ID. (value is ""reviewItemElement.author"")
	 */
	private static final String REVIEW_ITEM_AUTHOR_ID = "reviewItemElement.author";

	/**
	 * Field REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_AUTHOR_ID, R4EUIConstants.AUTHOR_LABEL);
	
	/**
	 * Field REVIEW_ITEM_PROJECT_ID. (value is ""reviewItemElement.project"")
	 */
	private static final String REVIEW_ITEM_PROJECT_ID = "reviewItemElement.project";

	/**
	 * Field REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_PROJECT_ID, R4EUIConstants.PROJECT_ID_LABEL);
	
	/**
	 * Field REVIEW_ITEM_DESCRIPTION_ID. (value is ""reviewItemElement.description"")
	 */
	private static final String REVIEW_ITEM_DESCRIPTION_ID = "reviewItemElement.description";

	/**
	 * Field REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_ITEM_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { REVIEW_ITEM_AUTHOR_PROPERTY_DESCRIPTOR,
		REVIEW_ITEM_PROJECT_PROPERTY_DESCRIPTOR, REVIEW_ITEM_DESCRIPTION_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fItem.
	 */
	private final R4EItem fItem;
	
	/**
	 * Field fType.
	 */
	private final int fType;
	
	/**
	 * Field fFileContexts.
	 */
	private final List<R4EUIFileContext> fFileContexts;
	
	/**
	 * Field fContextRemoveNodeAction.
	 */
	private static RemoveNodeAction FContextRemoveNodeAction = null;
	
	/**
	 * Field fChangeReviewedStateAction.
	 */
	private static ChangeReviewedStateAction FChangeReviewedStateAction = null;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIReviewItem.
	 * @param aParent IR4EUIModelElement
	 * @param aItem R4EItem
	 * @param aType int
	 * @param aItemInfo Object
	 */
	public R4EUIReviewItem(IR4EUIModelElement aParent, R4EItem aItem, int aType, Object aItemInfo) {
		super(aParent, getItemDisplayName(aType, aItemInfo), getItemDisplayTooltip(aType, aItemInfo));
		fItem = aItem;
		fType = aType;
		fFileContexts = new ArrayList<R4EUIFileContext>();
		fImage = UIUtils.loadIcon(REVIEW_ITEM_ICON_FILE);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Method getType.
	 * @return int
	 */
	public int getType() {
		return fType;
	}
	
	/**
	 * Method getItemDisplayName.
	 * @param aType int
	 * @param aItemInfo Object
	 * @return String
	 */
	private static String getItemDisplayName(int aType, Object aItemInfo) {
		switch (aType) {
			case R4EUIConstants.REVIEW_ITEM_TYPE_RESOURCE:
			{
				return "Resource: " + ((R4EItem)aItemInfo).getFileContextList().get(0).getTarget().getName();
			}
			
			case R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT:
			{
				return "Commit: " + ((CommitDescriptor)aItemInfo).getTitle();
			}
			
			default:
				return "";   //should never happen
		}
	}

	/**
	 * Method getItemDisplayTooltip.
	 * @param aType int
	 * @param aItemInfo Object
	 * @return String
	 */
	private static String getItemDisplayTooltip(int aType, Object aItemInfo) {
		switch (aType) {
			case R4EUIConstants.REVIEW_ITEM_TYPE_RESOURCE:
			{
				return "Description: " + ((R4EItem)aItemInfo).getDescription();
			}
			
			case R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT:
			{
				return "Description: " + ((CommitDescriptor)aItemInfo).getMessage();
			}
			
			default:
				return "";   //should never happen
		}
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
		fReviewed = aReviewed;
		if (fReviewed) {
			//Also set the children
			final int length = fFileContexts.size();
			for (int i = 0; i < length; i++) {
				fFileContexts.get(i).setChildReviewed(aReviewed);
			}
		}
		fireReviewStateChanged(this);
	}
	
	/**
	 * Method checkToSetReviewed.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#checkToSetReviewed()
	 */
	@Override
	public void checkToSetReviewed() {
		boolean allChildrenReviewed = true;
		final int length = fFileContexts.size();
		for (int i = 0; i < length; i++) {
			if (!(fFileContexts.get(i).isReviewed())) allChildrenReviewed = false;
		}
		//If all children are reviewed, mark the parent as reviewed as well
		if (allChildrenReviewed) {
			fReviewed = true;   
			fireReviewStateChanged(this);
		}
	}
	
	/**
	 * Method getItem.
	 * @return R4EItem
	 */
	public R4EItem getItem() {
		return fItem;
		
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
		if (REVIEW_ITEM_AUTHOR_ID.equals(aId)) { 
			return fItem.getAddedById();
		} else if (REVIEW_ITEM_PROJECT_ID.equals(aId)) {
			return fItem.getProjectURIs();
		} else if (REVIEW_ITEM_DESCRIPTION_ID.equals(aId)) {
			return fItem.getDescription();
		}
		return null;
	}
	
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fFileContexts.toArray(new R4EUIFileContext[fFileContexts.size()]);
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fFileContexts.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIFileContext fileContext = null;
		final int fileContextsSize = fFileContexts.size();
		for (int i = 0; i < fileContextsSize; i++) {
			
			fileContext = fFileContexts.get(i);
			fileContext.close();
		}
		fFileContexts.clear();
		fOpen = false;
		removeListener();
	}
	
	/**
	 * Method loadModelData.
	 */
	@Override
	public void loadModelData() {
		final EList<R4EFileContext> files = fItem.getFileContextList();
		if (null != files) {	
			R4EUIFileContext newFileContext = null;
			final int filesSize = files.size();
			for (int i = 0; i < filesSize; i++) {
				newFileContext = new R4EUIFileContext(this, files.get(i));
				addChildren(newFileContext);
				newFileContext.loadModelData();
			}
		}
		fOpen = true;
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fFileContexts.add((R4EUIFileContext) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}

	
	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove) {
		fFileContexts.remove(aChildToRemove);
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
		if (null != fFileContexts) {
			R4EUIFileContext element = null;
			for (final Iterator<R4EUIFileContext> iterator = fFileContexts.iterator(); iterator.hasNext();) {
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
		if (null != fFileContexts) {
			R4EUIFileContext element = null;
			for (final Iterator<R4EUIFileContext> iterator = fFileContexts.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener();
			}
		}
	}
	
	
	//Actions
	
	/**
	 * Method createActions.
	 * @param aView ReviewNavigatorView
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createActions(ReviewNavigatorView)
	 */
	@Override
	public void createActions(ReviewNavigatorView aView) {
		FContextRemoveNodeAction = new RemoveNodeAction(aView, REMOVE_ELEMENT_ACTION_NAME, REMOVE_ELEMENT_ACTION_TOOLTIP,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		FChangeReviewedStateAction = new ChangeReviewedStateAction(aView, CHANGE_REVIEW_STATE_ACTION_NAME, CHANGE_REVIEW_STATE_ACTION_TOOLTIP, 
				ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(CHANGE_REVIEW_STATE_ACTION_ICON_FILE)));
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
			actions.add(FContextRemoveNodeAction);
			actions.add(FChangeReviewedStateAction);
		}
		return actions;
	}
}