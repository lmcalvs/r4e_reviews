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
 * This class implements the Anomaly element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.Comment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.AddChildNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.OpenEditorAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.RemoveNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.R4ECommentInputDialog;
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
public class R4EUIAnomaly extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fAnomalyFile.
	 * (value is ""icons/anomaly.gif"")
	 */
	private static final String ANOMALY_ICON_FILE = "icons/anomaly.gif";
	
	/**
	 * Field ADD_ELEMENT_ACTION_NAME.
	 * (value is ""Add Comment"")
	 */
	private static final String ADD_ELEMENT_ACTION_NAME = "Add Comment";
	
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Anomaly"")
	 */
	private static final String REMOVE_ELEMENT_ACTION_NAME = "Delete Anomaly";
	
    /**
     * Field ADD_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Add a new comment to the current anomaly"")
     */
    private static final String ADD_ELEMENT_ACTION_TOOLTIP = "Add a new comment to the current anomaly";
    
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this anomaly from its parent file or review item"")
     */
    private static final String REMOVE_ELEMENT_ACTION_TOOLTIP = "Remove this anomaly from its parent file or review item";
    
    /**
     * Field OPEN_EDITOR_ACTION_NAME.
     * (value is ""Open parent file in editor"")
     */
    private static final String OPEN_EDITOR_ACTION_NAME = "Open parent file in editor";
    
    /**
     * Field OPEN_EDITOR_ACTION_TOOLTIP.
     * (value is ""Open the parent file with the matching editor and locate this anomaly"")
     */
    private static final String OPEN_EDITOR_ACTION_TOOLTIP = "Open the parent file with the matching editor and locate this anomaly";
    
	/**
	 * Field OPEN_EDITOR_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String OPEN_EDITOR_ACTION_ICON_FILE = "icons/open_file.gif";
	
	/**
	 * Field ADD_COMMENT_DIALOG_TITLE.
	 * (value is ""Enter Comment details"")
	 */
	private static final String ADD_COMMENT_DIALOG_TITLE = "Enter Comment details";
	
	/**
	 * Field ADD_COMMENT_DIALOG_VALUE.
	 * (value is ""Enter your new Comments for this Anomaly:"")
	 */
	private static final String ADD_COMMENT_DIALOG_VALUE = "Enter your new Comments for this Anomaly:";
	
	/**
	 * Field ANOMALY_TITLE_ID. (value is ""anomalyElement.title"")
	 */
	private static final String ANOMALY_TITLE_ID = "anomalyElement.title";

	/**
	 * Field ANOMALY_TITLE_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor ANOMALY_TITLE_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			ANOMALY_TITLE_ID, R4EUIConstants.TITLE_LABEL);
	
	/**
	 * Field ANOMALY_POSITION_ID. (value is ""anomalyElement.position"")
	 */
	private static final String ANOMALY_POSITION_ID = "anomalyElement.position";

	/**
	 * Field ANOMALY_POSITION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor ANOMALY_POSITION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_POSITION_ID, R4EUIConstants.POSITION_LABEL);
	
	/**
	 * Field ANOMALY_AUTHOR_ID. (value is ""anomalyElement.author"")
	 */
	private static final String ANOMALY_AUTHOR_ID = "anomalyElement.author";

	/**
	 * Field ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_AUTHOR_ID, R4EUIConstants.AUTHOR_LABEL);

	/**
	 * Field ANOMALY_CREATION_DATE_ID. (value is ""anomalyElement.creationDate"")
	 */
	private static final String ANOMALY_CREATION_DATE_ID = "anomalyElement.creationDate";

	/**
	 * Field ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			ANOMALY_CREATION_DATE_ID, R4EUIConstants.CREATION_DATE_LABEL);
	
	/**
	 * Field ANOMALY_DESCRIPTION_ID. (value is ""anomalyElement.description"")
	 */
	private static final String ANOMALY_DESCRIPTION_ID = "anomalyElement.description";

	/**
	 * Field ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			ANOMALY_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { ANOMALY_TITLE_PROPERTY_DESCRIPTOR,
		ANOMALY_POSITION_PROPERTY_DESCRIPTOR, ANOMALY_AUTHOR_PROPERTY_DESCRIPTOR,  
		ANOMALY_CREATION_DATE_PROPERTY_DESCRIPTOR, ANOMALY_DESCRIPTION_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fAnomaly.
	 */
	private final R4EAnomaly fAnomaly;
	
	/**
	 * Field fComments.
	 */
	private final List<R4EUIComment> fComments;

	/**
	 * Field fContextAddChildNodeAction.
	 */
	private static AddChildNodeAction FContextAddChildNodeAction = null;
	
	/**
	 * Field fContextRemoveNodeAction.
	 */
	private static RemoveNodeAction FContextRemoveNodeAction = null;
	
	/**
	 * Field FContextOpenEditorAction.
	 */
	private static OpenEditorAction FContextOpenEditorAction = null;
	
	/**
	 * Field fPosition.
	 */
	private final IR4EUIPosition fPosition;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIAnomaly.
	 * @param aParent IR4EUIModelElement
	 * @param aAnomaly R4EAnomaly
	 * @param aPosition IR4EUIPosition
	 * @param aTitle String
	 */
	public R4EUIAnomaly(IR4EUIModelElement aParent, R4EAnomaly aAnomaly, IR4EUIPosition aPosition, String aTitle) {
		super(aParent, ((null == aPosition) ?  aTitle :
			aPosition.toString()), aAnomaly.getUser().getId() + ": " + aAnomaly.getDescription());
		fAnomaly = aAnomaly;
		fComments = new ArrayList<R4EUIComment>();
		fImage = UIUtils.loadIcon(ANOMALY_ICON_FILE);
		fPosition = aPosition;
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Method getAnomaly.
	 * @return R4EAnomaly
	 */
	public R4EAnomaly getAnomaly() {
		return fAnomaly;
	}
	
	/**
	 * Method getPosition.
	 * @return IR4EPosition
	 */
	public IR4EUIPosition getPosition() {
		return fPosition;
	}
    
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildModelDataElement()
	 */
	@Override
	public R4EReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EComment tempComment = null;
		R4EUIModelController.setDialogOpen(true);
		final R4ECommentInputDialog dialog = new R4ECommentInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell(), ADD_COMMENT_DIALOG_TITLE, ADD_COMMENT_DIALOG_VALUE);
    	final int result = dialog.open();
    	if (result == Window.OK) {
    		tempComment = RModelFactory.eINSTANCE.createR4EComment();
    		tempComment.setDescription(dialog.getCommentValue());
    	}
    	// else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
    	return tempComment;
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
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fAnomaly, 
				R4EUIModelController.getReviewer());
		fAnomaly.setTitle(((R4EAnomaly)aModelComponent).getTitle());
		fAnomaly.setDescription(((R4EAnomaly)aModelComponent).getDescription());
    	R4EUIModelController.FResourceUpdater.checkIn(bookNum);
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
	 * @param aId
	 *            Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		if (ANOMALY_TITLE_ID.equals(aId)) { 
			return fAnomaly.getTitle();
		} else if (ANOMALY_POSITION_ID.equals(aId)) {
			if (null == fPosition ) {
				return R4EUIConstants.GLOBAL_ANOMALY_PROPERTY_VALUE;
			}
			return fPosition.toString();
		} else if (ANOMALY_AUTHOR_ID.equals(aId)) { 
			return fAnomaly.getUser().getId();
		} else if (ANOMALY_CREATION_DATE_ID.equals(aId)) {
			return fAnomaly.getCreatedOn().toString();
		} else if (ANOMALY_DESCRIPTION_ID.equals(aId)) {
			return fAnomaly.getDescription();
		}
		return null;
	}
	
	/** // $codepro.audit.disable blockDepth
	 * Method setPropertyValue.
	 * @param aId Object
	 * @param aValue Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	@Override
	public void setPropertyValue(Object aId, Object aValue) { // $codepro.audit.disable emptyMethod
		if (!(R4EUIModelController.isDialogOpen())) {
			try {
				final String currentUser = R4EUIModelController.getReviewer();
				if (fAnomaly.getUser().getId().equals(currentUser)) {
					if (ANOMALY_TITLE_ID.equals(aId)) {
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fAnomaly, currentUser);
						fAnomaly.setTitle((String) aValue);
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);
					} else if (ANOMALY_DESCRIPTION_ID.equals(aId)) { 
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fAnomaly, currentUser);
						fAnomaly.setDescription((String) aValue);
						R4EUIModelController.FResourceUpdater.checkIn(bookNum);	
					}
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
		return fComments.toArray(new R4EUIComment[fComments.size()]);
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fComments.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIComment comment = null;
		final int commentsSize = fComments.size();
		for (int i = 0; i < commentsSize; i++) {
			
			comment = fComments.get(i);
			comment.close();
		}
		fComments.clear();
		fOpen = false;
		removeListener();
	}
	
	/**
	 * Method loadModelData.
	 * 		Load the serialization model data into UI model
	 */
	@Override
	public void loadModelData() {
		final List<Comment> comments = fAnomaly.getComments();
		if (null != comments) {
			R4EComment r4eComment = null;
			final int commentsSize = comments.size();
			for (int i = 0; i < commentsSize; i++) {
				r4eComment = (R4EComment)comments.get(i);
				addChildren(new R4EUIComment(this, r4eComment, r4eComment.getDescription()));
			}
		}
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fComments.add((R4EUIComment) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}

	/**
	 * Method addChildren.
	 * @param aModelComponent - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	@Override
	public IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		final String user = R4EUIModelController.getReviewer();
		R4EParticipant participant = null;
		if (getParent().getParent().getParent().getParent() instanceof R4EUIReview) { // $codepro.audit.disable methodChainLength
			participant = ((R4EUIReview)getParent().getParent().getParent().getParent()).getParticipant(user, true); // $codepro.audit.disable methodChainLength
		} else {
			//Global anomaly
			participant = ((R4EUIReview)getParent().getParent()).getParticipant(user, true);
		}
		final R4EComment comment = R4EUIModelController.FModelExt.createR4EComment(participant, fAnomaly);
		final R4EUIComment addedChild = new R4EUIComment(this, comment, ((R4EComment)aModelComponent).getDescription()); 
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);
		return addedChild;
	}

	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove) {
		fComments.remove(aChildToRemove);
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
		if (null != fComments) {
			R4EUIComment element = null;
			for (final Iterator<R4EUIComment> iterator = fComments.iterator(); iterator.hasNext();) {
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
		if (null != fComments) {
			R4EUIComment element = null;
			for (final Iterator<R4EUIComment> iterator = fComments.iterator(); iterator.hasNext();) {
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
		if (null == FContextAddChildNodeAction) createActions(aView);
		final List<IAction> actions = new ArrayList<IAction>();
		if (!(R4EUIModelController.isDialogOpen()) && isOpen()) {
			actions.add(FContextAddChildNodeAction);
			actions.add(FContextRemoveNodeAction);
			final IR4EUIModelElement element = getParent().getParent();
			if (element instanceof R4EUIFileContext && null != ((R4EUIFileContext)element).getTargetFile()) {
				actions.add(FContextOpenEditorAction);	
			}
		}
		return actions;
	}
}
