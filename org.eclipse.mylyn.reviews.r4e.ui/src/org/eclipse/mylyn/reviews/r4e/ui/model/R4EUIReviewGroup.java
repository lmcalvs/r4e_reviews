// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
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
 * This class implements the Review Group element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.R4EReviewInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.properties.ReviewGroupProperties;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIReviewGroup extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	 
	/**
	 * Field REVIEW_GROUP_FILE_PREFIX.
	 * (value is ""File location: "")
	 */
	private static final String REVIEW_GROUP_FILE_PREFIX = "File location: ";
	
	/**
	 * Field fReviewGroupFile.
	 * (value is ""icons/groups.gif"")
	 */
	private static final String REVIEW_GROUP_CLOSED_ICON_FILE = "icons/groups_closed.gif";
	
	/**
	 * Field ADD_ELEMENT_ACTION_NAME.
	 * (value is ""Add Review"")
	 */
	private static final String ADD_CHILD_ELEMENT_COMMAND_NAME = "Add Review";
	
    /**
     * Field ADD_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Add a new review to the current review group"")
     */
    private static final String ADD_CHILD_ELEMENT_COMMAND_TOOLTIP = "Add a new review to the current review group";
    
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Review Group"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Review Group";
    
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this review group"")
     */
    private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable (and optionally remove) this review group";

	
	/**
	 * Field ADD_REVIEW_DIALOG_TITLE.
	 * (value is ""Enter Review details"")
	 */
	private static final String ADD_REVIEW_DIALOG_TITLE = "Enter Review details";
	
	/**
	 * Field ADD_REVIEW_NAME_DIALOG_VALUE.
	 * (value is ""Enter the Review name"")
	 */
	private static final String ADD_REVIEW_NAME_DIALOG_VALUE = "Enter the Review name";
	
	/**
	 * Field ADD_REVIEW_DESCRIPTION_DIALOG_VALUE.
	 * (value is ""Enter the Review Description"")
	 */
	private static final String ADD_REVIEW_DESCRIPTION_DIALOG_VALUE = "Enter the Review Description";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fGroup.
	 */
	protected R4EReviewGroup fGroup;
	
	/**
	 * Field fGroupFileURI.
	 */
	private final URI fGroupFileURI;
	
	/**
	 * Field fReviews.
	 */
	private final List<R4EUIReview> fReviews;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for R4EUIReviewGroup.
	 * @param aParent IR4EUIModelElement
	 * @param aGroup R4EReviewGroup
	 * @param aOpen boolean
	 */
	public R4EUIReviewGroup(IR4EUIModelElement aParent, R4EReviewGroup aGroup, boolean aOpen) {
		super(aParent, aGroup.getName(), REVIEW_GROUP_FILE_PREFIX + aGroup.eResource().getURI().devicePath());
		fGroup = aGroup;
		fGroupFileURI = aGroup.eResource().getURI();
		fReviews = new ArrayList<R4EUIReview>();
		if (aOpen) {
			setImage(R4EUIConstants.REVIEW_GROUP_ICON_FILE);
			fOpen = true;
		} else {
			setImage(REVIEW_GROUP_CLOSED_ICON_FILE);
			fOpen = false;
		}
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
		if (IPropertySource.class.equals(adapter)) return new ReviewGroupProperties(this);
		return null;
	}
	
	//Attributes
	
	/**
	 * Method getGroup.
	 * @return R4EReviewGroup
	 */
	public R4EReviewGroup getGroup() {
		return fGroup;
	}

	/**
	 * Method getGroupURI.
	 * @return URI
	 */
	public URI getGroupURI() {
		return fGroupFileURI;
	}
	
	/**
	 * Set serialization model data by copying it from the passed-in object
	 * @param aModelComponent - a serialization model element to copy information from
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setModelData(R4EReviewComponent)
	 */
	@Override
	public void setModelData(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
    	//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fGroup, R4EUIModelController.getReviewer());
		fGroup.setDescription(((R4EReviewGroup)aModelComponent).getDescription());
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    }
	
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildModelDataElement()
	 */
	@Override
	public R4EReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EReview tempReview = null;
		R4EUIModelController.setDialogOpen(true);
		final R4EReviewInputDialog dialog = new R4EReviewInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell(), ADD_REVIEW_DIALOG_TITLE, ADD_REVIEW_NAME_DIALOG_VALUE, 
				ADD_REVIEW_DESCRIPTION_DIALOG_VALUE);
    	final int result = dialog.open();
    	if (result == Window.OK) {
    		tempReview = RModelFactory.eINSTANCE.createR4EReview();
    		tempReview.setName(dialog.getReviewNameValue());
    		tempReview.setExtraNotes(dialog.getReviewDescriptionValue());
    	}
    	//else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
    	return tempReview;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIReview review = null;
		final int reviewsSize = fReviews.size();
		for (int i = 0; i < reviewsSize; i++) {
			
			review = fReviews.get(i);
			if (!review.isOpen()) continue;  //skip reviews that are already closed
			review.close();
			review.removeListener();
			fireRemove(review);
		}
		
		fReviews.clear();
		fOpen = false;
		R4EUIModelController.FModelExt.closeR4EReviewGroup(fGroup);   //Notify model
		fImage = UIUtils.loadIcon(REVIEW_GROUP_CLOSED_ICON_FILE);
		fireReviewStateChanged(this);
	}
	
	/**
	 * Open the model element (i.e. enable it)
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#open()
	 */
	@Override
	public void open() throws ResourceHandlingException {
		//Load model information
		fGroup = R4EUIModelController.FModelExt.openR4EReviewGroup(fGroupFileURI);
		final EList<Review> reviews = fGroup.getReviews();
		if (null != reviews) {
			final int reviewsSize = reviews.size();
			R4EReview review = null;
			for (int i = 0; i < reviewsSize; i++) {
				review = (R4EReview)reviews.get(i);
				if (review.isEnabled() || Activator.getDefault().getPreferenceStore().
						getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					R4EUIReview uiReview = new R4EUIReview(this, review, false);
					addChildren(uiReview);
					
					//Check if this review is completed
					if (((R4EReviewState)review.getState()).getState() == R4EReviewPhase.R4E_REVIEW_PHASE_COMPLETED) {
						try {
							uiReview.setReviewed(true);
						} catch (OutOfSyncException e) {
							UIUtils.displaySyncErrorDialog(e);
						}
					}
				}
			}
		}
		fOpen = true;
		fImage = UIUtils.loadIcon(R4EUIConstants.REVIEW_GROUP_ICON_FILE);
		fireReviewStateChanged(this);
	}
	
	
	/**
	 * Method getReviewGroup.
	 * @return R4EReviewGroup
	 */
	public R4EReviewGroup getReviewGroup() {
		return fGroup;
	}
	
	
	/**
	 * Method setEnabled.
	 * @param aEnabled boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setReviewed(boolean)
	 */
	@Override
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException {
		//NOTE we need to oppen the model element temporarly to be able to set the enabled state
		R4EUIModelController.FModelExt.openR4EReviewGroup(getGroupURI());
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fGroup, R4EUIModelController.getReviewer());
		fGroup.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		R4EUIModelController.FModelExt.closeR4EReviewGroup(fGroup);
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}
	
	/**
	 * Method isEnabled.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fGroup.isEnabled();
	}
	
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fReviews.toArray(new R4EUIReview[fReviews.size()]);
	}
	
	/**
	 * Method hasChildren
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (isOpen()) {
			if (fReviews.size() > 0) return true;
		}
	    return false;
	}
	
	/**
	 * Method createChildren.
	 * @param aModelComponent - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	@Override
	public IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		
		final String reviewName = ((R4EReview)aModelComponent).getName();
		
		//Check if group already exists.  If so it cannot be recreated
		for (R4EUIReview review : fReviews) {
			if (review.getReview().getName().equals(reviewName)) {
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR, "Error while creating new review ",
	    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, "Review " +
	    						reviewName + " already exists", null), IStatus.ERROR);
				dialog.open();
				return null;
			}
		}
		
		final R4EUIReview addedChild = new R4EUIReview(this, 
				R4EUIModelController.FModelExt.createR4EReview(getReviewGroup(), reviewName, 
						R4EUIModelController.getReviewer()), true);
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);
		return addedChild;
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fReviews.add((R4EUIReview) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().
				getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}
	
	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @param aFileRemove - also remove from file (hard remove)
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		//This was the current review, so tell the controller that no review is now active
		if (((R4EUIReview)aChildToRemove).isOpen()) R4EUIModelController.setActiveReview(null);

		final R4EUIReview removedElement = fReviews.get(fReviews.indexOf(aChildToRemove));
		
		//Also recursively remove all children 
		removedElement.removeAllChildren(aFileRemove);
		
		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getReview().remove());
		else */
		
		//NOTE we need to oppen the model element temporarly to be able to set the enabled state
		removedElement.setEnabled(false);

		//Remove element from UI if the show disabled element option is off
		if (!(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fReviews.remove(removedElement);
			aChildToRemove.removeListener();
			fireRemove(aChildToRemove);
		} else {
			R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
		}
		
		aChildToRemove.removeListener();
		fireRemove(aChildToRemove);
	}
	
	/**
	 * Method removeAllChildren.
	 * @param aFileRemove boolean
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		//Recursively remove all children
		for (R4EUIReview review : fReviews) {
			removeChildren(review, aFileRemove);
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
		if (null != fReviews) {
			R4EUIReview element = null;
			for (final Iterator<R4EUIReview> iterator = fReviews.iterator(); iterator.hasNext();) {
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
		if (null != fReviews) {
			R4EUIReview element = null;
			for (final Iterator<R4EUIReview> iterator = fReviews.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener();
			}
		}
	}
	
	
	//Commands
	
	/**
	 * Method isOpenElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	@Override
	public boolean isOpenElementCmd() {
		if (!isEnabled() || isOpen()) return false;
		return true;
	}
	
	/**
	 * Method isCloseElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isCloseElementCmd()
	 */
	@Override
	public boolean isCloseElementCmd() {
		if (isEnabled() && isOpen()) return true;
		return false;
	}
	
	/**
	 * Method isAddChildElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddChildElementCmd()
	 */
	@Override
	public boolean isAddChildElementCmd() {
		if (isEnabled() && isOpen()) return true;
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
	
	/**
	 * Method isRemoveElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		if (!isOpen() && isEnabled()) return true;
		return false;
	}
	
	/**
	 * Method isRestoreElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (isOpen() || isEnabled()) return false;
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
