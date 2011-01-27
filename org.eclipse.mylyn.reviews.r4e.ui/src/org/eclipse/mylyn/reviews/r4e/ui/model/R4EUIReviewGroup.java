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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.AddChildNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.CloseReviewAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.OpenReviewAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.RemoveNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.R4EReviewInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;


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
	private static final String ADD_ELEMENT_ACTION_NAME = "Add Review";
	
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Review Group"")
	 */
	private static final String REMOVE_ELEMENT_ACTION_NAME = "Delete Review Group";
	
	/**
	 * Field OPEN_ELEMENT_ACTION_NAME.
	 * (value is ""Open Review Group"")
	 */
	private static final String OPEN_ELEMENT_ACTION_NAME = "Open Review Group";
	
	/**
	 * Field CLOSE_ELEMENT_ACTION_NAME.
	 * (value is ""Close Review Group"")
	 */
	private static final String CLOSE_ELEMENT_ACTION_NAME = "Close Review Group";
	
    /**
     * Field ADD_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Add a new review to the current review group"")
     */
    private static final String ADD_ELEMENT_ACTION_TOOLTIP = "Add a new review to the current review group";
    
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this review group"")
     */
    private static final String REMOVE_ELEMENT_ACTION_TOOLTIP = "Remove this review group";
    
    /**
     * Field OPEN_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Open this review group"")
     */
    private static final String OPEN_ELEMENT_ACTION_TOOLTIP = "Open this review group";
    
    /**
     * Field CLOSE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Close this review group"")
     */
    private static final String CLOSE_ELEMENT_ACTION_TOOLTIP = "Close this review group";
    
	/**
	 * Field REVIEW_OPEN_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String REVIEW_OPEN_ACTION_ICON_FILE = "icons/open.gif";
	
	/**
	 * Field REVIEW_OPEN_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String REVIEW_CLOSE_ACTION_ICON_FILE = "icons/close.gif";
	
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
	
	/**
	 * Field GROUP_NAME_ID. (value is ""reviewGroupElement.name"")
	 */
	private static final String GROUP_NAME_ID = "reviewGroupElement.name";

	/**
	 * Field GROUP_NAME_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor GROUP_NAME_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			GROUP_NAME_ID, R4EUIConstants.NAME_LABEL);

	/**
	 * Field GROUP_FOLDER_ID. (value is ""reviewGroupElement.folderName"")
	 */
	private static final String GROUP_FOLDER_ID = "reviewGroupElement.folderName";

	/**
	 * Field GROUP_FOLDER_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor GROUP_FOLDER_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			GROUP_FOLDER_ID, R4EUIConstants.FOLDER_LABEL);
	
	/**
	 * Field GROUP_DESCRIPTION_ID. (value is ""reviewGroupElement.description"")
	 */
	private static final String GROUP_DESCRIPTION_ID = "reviewGroupElement.description";

	/**
	 * Field GROUP_NAME_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor GROUP_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			GROUP_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { GROUP_NAME_PROPERTY_DESCRIPTOR,  
		GROUP_FOLDER_PROPERTY_DESCRIPTOR, GROUP_DESCRIPTION_PROPERTY_DESCRIPTOR };
	
	
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

	/**
	 * Field fContextAddChildNodeAction.
	 */
	private static AddChildNodeAction FContextAddChildNodeAction = null;
	
	/**
	 * Field fContextRemoveNodeAction.
	 */
	private static RemoveNodeAction FContextRemoveNodeAction = null;
	
	/**
	 * Field fContextOpenReviewAction.
	 */
	private static OpenReviewAction FContextOpenReviewAction = null;
	
	/**
	 * Field fContextCloseReviewAction.
	 */
	private static CloseReviewAction FContextCloseReviewAction = null;
	
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
			fImage = UIUtils.loadIcon(R4EUIConstants.REVIEW_GROUP_ICON_FILE);
			fOpen = true;
		} else {
			fImage = UIUtils.loadIcon(REVIEW_GROUP_CLOSED_ICON_FILE);
			fOpen = false;
		}
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Method getGroup.
	 * @return R4EReviewGroup
	 */
	public R4EReviewGroup getGroup() {
		return fGroup;
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
			for (int i = 0; i < reviewsSize; i++) {
				addChildren(new R4EUIReview(this, (R4EReview)reviews.get(i), false));
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
		if (null != fGroup) {
			if (GROUP_NAME_ID.equals(aId)) { 
				return fGroup.getName();
			} else if (GROUP_FOLDER_ID.equals(aId)) {
				return fGroup.getFolder();
			} else if (GROUP_DESCRIPTION_ID.equals(aId)) {
				return fGroup.getDescription();
			}
		}
		return null;
	}
	
	/**
	 * Method setPropertyValue.
	 * @param aId Object
	 * @param aValue Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	@Override
	public void setPropertyValue(Object aId, Object aValue) { // $codepro.audit.disable emptyMethod
		if (!(R4EUIModelController.isDialogOpen()) && isOpen()) {
			try {
				if (GROUP_NAME_ID.equals(aId)) { 
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fGroup, 
							R4EUIModelController.getReviewer());
					fGroup.setName((String) aValue);
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				} else if (GROUP_DESCRIPTION_ID.equals(aId)) {
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fGroup, 
							R4EUIModelController.getReviewer());
					fGroup.setDescription((String) aValue);
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				}
			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);
			} catch (OutOfSyncException e) {
				UIUtils.displaySyncErrorDialog(e);
			}
		}
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
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while creating new review ",
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove) {
		//This was the current review, so tell the controller that no review is now active
		if (((R4EUIReview)aChildToRemove).isOpen()) R4EUIModelController.setActiveReview(null);
		
		fReviews.remove(aChildToRemove);
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
	
	
	//Actions
	
	/**
	 * Method createActions.
	 * @param aView ReviewNavigatorView
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createActions(ReviewNavigatorView)
	 */
	@Override
	public void createActions(ReviewNavigatorView aView) {
		FContextAddChildNodeAction = new AddChildNodeAction(aView, ADD_ELEMENT_ACTION_NAME, ADD_ELEMENT_ACTION_TOOLTIP,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD), false);
		FContextRemoveNodeAction = new RemoveNodeAction(aView, REMOVE_ELEMENT_ACTION_NAME, REMOVE_ELEMENT_ACTION_TOOLTIP,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
		FContextOpenReviewAction = new OpenReviewAction(aView, OPEN_ELEMENT_ACTION_NAME, OPEN_ELEMENT_ACTION_TOOLTIP,
				ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(REVIEW_OPEN_ACTION_ICON_FILE)));
		FContextCloseReviewAction = new CloseReviewAction(aView, CLOSE_ELEMENT_ACTION_NAME, CLOSE_ELEMENT_ACTION_TOOLTIP,
				ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(REVIEW_CLOSE_ACTION_ICON_FILE)));
	}

	/**
	 * Method getActions.
	 * @param aView ReviewNavigatorView
	 * @return List<Action>
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getActions(ReviewNavigatorView)
	 */
	@Override
	public List<IAction> getActions(ReviewNavigatorView aView) {
		if (null == FContextCloseReviewAction) createActions(aView);
		final List<IAction> actions = new ArrayList<IAction>();
		if (!(R4EUIModelController.isDialogOpen())) {
			if (isOpen()) {
				actions.add(FContextCloseReviewAction);
				actions.add(FContextAddChildNodeAction);
			} else {
				actions.add(FContextOpenReviewAction);
			}
			actions.add(FContextRemoveNodeAction);
		}
		return actions;
	}
	
	/**
	 * Method getOpenReviewAction.
	 * @return IAction
	 */
	public IAction getOpenReviewAction() {
		if (null == FContextOpenReviewAction) createActions(R4EUIModelController.getNavigatorView());
		return FContextOpenReviewAction;
	}
	
	/**
	 * Method getCloseReviewAction.
	 * @return IAction
	 */
	public IAction getCloseReviewAction() {
		if (null == FContextCloseReviewAction) createActions(R4EUIModelController.getNavigatorView());
		return FContextCloseReviewAction;
	}
}
