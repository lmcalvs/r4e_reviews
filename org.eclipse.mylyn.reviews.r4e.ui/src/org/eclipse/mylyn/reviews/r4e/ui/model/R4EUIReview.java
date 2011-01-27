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
 * This class implements the Review element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.frame.core.model.Item;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.CommitDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIFFactory;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.CloseReviewAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.OpenReviewAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.RemoveNodeAction;
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
public class R4EUIReview extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field REVIEW_ICON_FILE.
	 * (value is ""icons/review.gif"")
	 */
	private static final String REVIEW_ICON_FILE = "icons/review.gif";
	
	/**
	 * Field REVIEW_ICON_FILE.
	 * (value is ""icons/review_closed.gif"")
	 */
	private static final String REVIEW_CLOSED_ICON_FILE = "icons/review_closed.gif";
	
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Review"")
	 */
	private static final String REMOVE_ELEMENT_ACTION_NAME = "Delete Review";
	
	/**
	 * Field OPEN_ELEMENT_ACTION_NAME.
	 * (value is ""Open Review"")
	 */
	private static final String OPEN_ELEMENT_ACTION_NAME = "Open Review";
	
	/**
	 * Field CLOSE_ELEMENT_ACTION_NAME.
	 * (value is ""Close Review"")
	 */
	private static final String CLOSE_ELEMENT_ACTION_NAME = "Close Review";
        
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this review from its parent review group"")
     */
    private static final String REMOVE_ELEMENT_ACTION_TOOLTIP = "Remove this review from its parent review group";
    
    /**
     * Field OPEN_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Open this review"")
     */
    private static final String OPEN_ELEMENT_ACTION_TOOLTIP = "Open this review";
    
    /**
     * Field CLOSE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Close this review"")
     */
    private static final String CLOSE_ELEMENT_ACTION_TOOLTIP = "Close this review";
    
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
	 * Field REVIEW_NAME_ID. (value is ""reviewElement.name"")
	 */
	private static final String REVIEW_NAME_ID = "reviewElement.name";

	/**
	 * Field REVIEW_NAME_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor REVIEW_NAME_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_NAME_ID, R4EUIConstants.NAME_LABEL);

	/**
	 * Field REVIEW_CREATION_DATE_ID. (value is ""reviewElement.creationDate"")
	 */
	private static final String REVIEW_CREATION_DATE_ID = "reviewElement.creationDate";

	/**
	 * Field REVIEW_CREATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor REVIEW_CREATION_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			REVIEW_CREATION_DATE_ID, R4EUIConstants.CREATION_DATE_LABEL);
	
	/**
	 * Field REVIEW_DESCRIPTION_ID. (value is ""reviewElement.description"")
	 */
	private static final String REVIEW_DESCRIPTION_ID = "reviewElement.description";

	/**
	 * Field REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			REVIEW_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { REVIEW_NAME_PROPERTY_DESCRIPTOR,  
		REVIEW_CREATION_DATE_PROPERTY_DESCRIPTOR, REVIEW_DESCRIPTION_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fReview.
	 */
	protected R4EReview fReview;
	
	/**
	 * Field fReviewName.
	 */
	private final String fReviewName;
	
	/**
	 * Field fItems.
	 */
	private final List<R4EUIReviewItem> fItems;
	
	/**
	 * Field fParticipantsContainer.
	 */
	private R4EUIParticipantContainer fParticipantsContainer;
	
	/**
	 * Field fAnomalyContainer.
	 */
	private R4EUIAnomalyContainer fAnomalyContainer = null;
	
	
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
	 * Constructor for R4EUIReview.
	 * @param aParent R4EUIReviewGroup
	 * @param aReview R4EReview
	 * @param aOpen boolean
	 * @throws ResourceHandlingException 
	 */
	public R4EUIReview(R4EUIReviewGroup aParent, R4EReview aReview, boolean aOpen) throws ResourceHandlingException {
		super(aParent, aReview.getName(), aReview.getExtraNotes());
		fReview = aReview;
		fReviewName = aReview.getName();
		fParticipantsContainer = new R4EUIParticipantContainer(this, R4EUIConstants.PARTICIPANTS_LABEL_NAME);
		fAnomalyContainer = new R4EUIAnomalyContainer(this, R4EUIConstants.GLOBAL_ANOMALIES_LABEL_NAME);
		fItems = new ArrayList<R4EUIReviewItem>();
		if (aOpen) {
			//Open the new review and make itt the active one (close any other that is open)
			fImage = UIUtils.loadIcon(REVIEW_ICON_FILE);
			fOpen = true;
			final List<R4EUserRole> role = new ArrayList<R4EUserRole>(1);
			role.add(R4EUserRole.R4E_ROLE_LEAD);
			final R4EParticipant participant = R4EUIModelController.FModelExt.createR4EParticipant(fReview, R4EUIModelController.getReviewer(), role);
			fParticipantsContainer.addChildren(new R4EUIParticipant(fParticipantsContainer, participant));
			final R4EUIReview activeReview = R4EUIModelController.getActiveReview();
			if (null != activeReview ) activeReview.close();
			R4EUIModelController.setActiveReview(this);
		} else {
			fImage = UIUtils.loadIcon(REVIEW_CLOSED_ICON_FILE);
			fOpen = false;
		}
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
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
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fReview, 
				R4EUIModelController.getReviewer());
    	fReview.setExtraNotes(((R4EReview)aModelComponent).getExtraNotes());
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    }
	
	/**
	 * Method getReview.
	 * @return R4EReview
	 */
	public R4EReview getReview() {
		return fReview;
	}
	
	/**
	 * Add a new participant
	 * @param aParticipant - the new particpant name/ID
	 * @param aCreate boolean
	 * @return R4EParticipant
	 * @throws ResourceHandlingException
	 * @throws ResourceHandlingException
	 */
	public R4EParticipant getParticipant(String aParticipant, boolean aCreate) throws ResourceHandlingException {
		R4EParticipant participant = null;
		if (isParticipant(aParticipant)) {
			participant = (R4EParticipant)fReview.getUsersMap().get(aParticipant);
		} else {
			if (aCreate) {
				final List<R4EUserRole> role = new ArrayList<R4EUserRole>(1);
				role.add(R4EUserRole.R4E_ROLE_REVIEWER);
				participant = R4EUIModelController.FModelExt.createR4EParticipant(fReview, aParticipant, null);
				fParticipantsContainer.addChildren(new R4EUIParticipant(fParticipantsContainer, participant));
			}
		}
		return participant;
	}
	
	/**
	 * Checks whether the participant is in the participants list for this review
	 * @param aParticipant - the participant to look for
	 * @return true/false
	 */
	public boolean isParticipant(String aParticipant) {
		if ((null != fReview) && (null != ((R4EParticipant)fReview.getUsersMap().get(aParticipant)))) return true;
		return false;
	}
	
	/**
	 * Method getParticipants.
	 * @return List<R4EParticipant>
	 */
	public List<R4EParticipant> getParticipants() {
		final Object[] users = fReview.getUsersMap().values().toArray();
		
		//Cast list to R4EParticipants
		final List<R4EParticipant> participants = new ArrayList<R4EParticipant>();
		for (Object user : users) {
			participants.add((R4EParticipant) user);
		}
		return participants;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIReviewItem reviewItem = null;
		final int itemsSize = fItems.size();
		for (int i = 0; i < itemsSize; i++) {
			
			reviewItem = fItems.get(i);
			reviewItem.close();
		}
		fParticipantsContainer.close();
		fAnomalyContainer.close();
		
		fItems.clear();
		fOpen = false;
		R4EUIModelController.FModelExt.closeR4EReview(fReview);   //Notify model
		R4EUIModelController.clearAnomalyMap();
		fImage = UIUtils.loadIcon(REVIEW_CLOSED_ICON_FILE);
		fireReviewStateChanged(this);
	}
	
	/**
	 * Open the model element (i.e. enable it)
	 * @throws ResourceHandlingException
	 * @throws ReviewVersionsException 
	 * @throws FileNotFoundException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#open()
	 */
	@Override
	public void open() throws ResourceHandlingException, ReviewVersionsException, FileNotFoundException {
		fReview = R4EUIModelController.FModelExt.openR4EReview(((R4EUIReviewGroup)getParent()).getReviewGroup(), fReviewName);
		final EList<Item> items = fReview.getReviewItems();
		if (null != items) {
		
			R4EUIReviewItem uiItem = null;
			R4EUIModelController.mapAnomalies(fReview);
			final int itemsSize = items.size();
			R4EItem item = null;
			for (int i = 0; i < itemsSize; i++) {
				//TODO This is a temporary fix to be able to distinguish between various review item types
				item = (R4EItem)items.get(i);
				if (null == item.getFileContextList().get(0).getBase()) {
					//Assume resource
					uiItem = new R4EUIReviewItem(this, item, R4EUIConstants.REVIEW_ITEM_TYPE_RESOURCE, item);
				} else {
					//commit
					IProject project = ResourceUtils.toIProject(item.getProjectURIs().get(0));
					ReviewsVersionsIF versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(project);
					CommitDescriptor descriptor = versionsIf.getCommitInfo(project, item.getRepositoryRef());
					uiItem = new R4EUIReviewItem(this, item, R4EUIConstants.REVIEW_ITEM_TYPE_COMMIT, descriptor);
				}
				
				uiItem.loadModelData();
				addChildren(uiItem);
			}
		}
		
		fAnomalyContainer.loadModelData();
		fParticipantsContainer.loadModelData();
		
		fOpen = true;
		fImage = UIUtils.loadIcon(REVIEW_ICON_FILE);
		fireReviewStateChanged(this);
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
	 * 
	 * @param aId Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		if (null != fReview) {
			if (REVIEW_NAME_ID.equals(aId)) { 
				return fReview.getName();
			} else if (REVIEW_CREATION_DATE_ID.equals(aId)) {
				return fReview.getCreationDate().toString();
			} else if (REVIEW_DESCRIPTION_ID.equals(aId)) {
				return fReview.getExtraNotes();
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
				if (REVIEW_NAME_ID.equals(aId)) { 
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fReview, 
							R4EUIModelController.getReviewer());
					fReview.setName((String) aValue);
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				} else if (REVIEW_DESCRIPTION_ID.equals(aId)) {
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fReview, 
							R4EUIModelController.getReviewer());
					fReview.setExtraNotes((String) aValue);
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
	 * Method hasChildren
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (isOpen()) {
			if (fItems.size() > 0 || null != fAnomalyContainer || null != fParticipantsContainer) return true;
		}
	    return false;
	}
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		final List<IR4EUIModelElement> newList = new ArrayList<IR4EUIModelElement>();
		newList.addAll(fItems);
		newList.add(fAnomalyContainer);
		newList.add(fParticipantsContainer);
		return newList.toArray(new IR4EUIModelElement[newList.size()]);
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		if (aChildToAdd instanceof R4EUIReviewItem) {
			fItems.add((R4EUIReviewItem) aChildToAdd);
		} else if (aChildToAdd instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer = (R4EUIAnomalyContainer) aChildToAdd;
		} else if (aChildToAdd instanceof R4EUIParticipantContainer) {
			fParticipantsContainer = (R4EUIParticipantContainer) aChildToAdd;
		} 
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
		aChildToRemove.removeListener();
		fireRemove(aChildToRemove);
		if (aChildToRemove instanceof R4EUIReviewItem) {
			fItems.remove(aChildToRemove);
		} else if (aChildToRemove instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer = null;
		} else if (aChildToRemove instanceof R4EUIParticipantContainer) {
			fParticipantsContainer = null;
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
		if (null != fItems) {
			R4EUIReviewItem element = null;
			for (final Iterator<R4EUIReviewItem> iterator = fItems.iterator(); iterator.hasNext();) {
			    element = iterator.next();
				element.addListener(aProvider);
			}
		}
		if (null != fAnomalyContainer) fAnomalyContainer.addListener(aProvider);
		if (null != fParticipantsContainer) fParticipantsContainer.addListener(aProvider);	
	}
	
	/**
	 * Method removeListener.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener() {
		fListener = null;
		if (null != fItems) {
			R4EUIReviewItem element = null;
			for (final Iterator<R4EUIReviewItem> iterator = fItems.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener();
			}
		}
		if (null != fAnomalyContainer) fAnomalyContainer.removeListener();
		if (null != fParticipantsContainer) fParticipantsContainer.removeListener();
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
