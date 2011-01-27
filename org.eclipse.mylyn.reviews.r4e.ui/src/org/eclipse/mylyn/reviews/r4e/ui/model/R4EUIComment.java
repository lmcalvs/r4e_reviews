// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays, explicitThisUsage
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
 * This class implements the Comment element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.frame.core.model.Comment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.OpenEditorAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.RemoveNodeAction;
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
public class R4EUIComment extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fCommentFile.
	 * (value is ""icons/comment.gif"")
	 */
	private static final String COMMENT_ICON_FILE = "icons/comment.gif";
	
	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Delete Comment"")
	 */
	private static final String REMOVE_ELEMENT_ACTION_NAME = "Delete Comment";
	
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Remove this comment from its parent anomaly"")
     */
    private static final String REMOVE_ELEMENT_ACTION_TOOLTIP = "Remove this comment from its parent anomaly";
    
    /**
     * Field OPEN_EDITOR_ACTION_NAME.
     * (value is ""Open parent file in editor"")
     */
    private static final String OPEN_EDITOR_ACTION_NAME = "Open parent file in editor";
    
    /**
     * Field OPEN_EDITOR_ACTION_TOOLTIP.
     * (value is ""Open the parent file with the matching editor and locate the parent anomaly"")
     */
    private static final String OPEN_EDITOR_ACTION_TOOLTIP = "Open the parent file with the matching editor and locate the parent anomaly";
    
	/**
	 * Field OPEN_EDITOR_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String OPEN_EDITOR_ACTION_ICON_FILE = "icons/open_file.gif";
	
	/**
	 * Field START_STRING_INDEX.
	 * (value is 0)
	 */
	private static final int START_STRING_INDEX = 0;
	/**
	 * Field END_STRING_NAME_INDEX.
	 * (value is 10)
	 */
	private static final int END_STRING_NAME_INDEX = 10;
	
	/**
	 * Field COMMENT_AUTHOR_ID. (value is ""commentElement.author"")
	 */
	private static final String COMMENT_AUTHOR_ID = "commentElement.author";

	/**
	 * Field COMMENT_AUTHOR_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor COMMENT_AUTHOR_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			COMMENT_AUTHOR_ID, R4EUIConstants.AUTHOR_LABEL);

	/**
	 * Field COMMENT_CREATION_DATE_ID. (value is ""commentElement.creationDate"")
	 */
	private static final String COMMENT_CREATION_DATE_ID = "commentElement.creationDate";

	/**
	 * Field COMMENT_CREATION_DATE_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor COMMENT_CREATION_DATE_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			COMMENT_CREATION_DATE_ID, R4EUIConstants.CREATION_DATE_LABEL);
	
	/**
	 * Field COMMENT_DESCRIPTION_ID. (value is ""commentElement.description"")
	 */
	private static final String COMMENT_DESCRIPTION_ID = "commentElement.description";

	/**
	 * Field COMMENT_DESCRIPTION_PROPERTY_DESCRIPTOR.
	 */
	private static final TextPropertyDescriptor COMMENT_DESCRIPTION_PROPERTY_DESCRIPTOR = new TextPropertyDescriptor(
			COMMENT_DESCRIPTION_ID, R4EUIConstants.DESCRIPTION_LABEL);
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { COMMENT_AUTHOR_PROPERTY_DESCRIPTOR,  
		COMMENT_CREATION_DATE_PROPERTY_DESCRIPTOR, COMMENT_DESCRIPTION_PROPERTY_DESCRIPTOR };
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fComment.
	 */
	private final R4EComment fComment;
	
	/**
	 * Field fContextRemoveNodeAction.
	 */
	private static RemoveNodeAction FContextRemoveNodeAction = null;
	
	/**
	 * Field FContextOpenEditorAction.
	 */
	private static OpenEditorAction FContextOpenEditorAction = null;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIComment.
	 * @param aParent IR4EUIModelElement
	 * @param aComment R4EComment
	 * @param aDescription String
	 */
	public R4EUIComment(IR4EUIModelElement aParent, R4EComment aComment, String aDescription) {
		super(aParent, (aDescription.length() < END_STRING_NAME_INDEX) ? 
				new String(aDescription).substring(START_STRING_INDEX, aDescription.length()) + "..." :
					new String(aDescription).substring(START_STRING_INDEX, END_STRING_NAME_INDEX) + "...", 
					aComment.getUser().getId() + ": " + aDescription);
		fComment = aComment;
		fImage = UIUtils.loadIcon(COMMENT_ICON_FILE);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Method getComment.
	 * @return R4EComment
	 */
	public R4EComment getComment() {
		return fComment;
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
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fComment, 
				R4EUIModelController.getReviewer());
    	fComment.setDescription(((Comment)aModelComponent).getDescription());
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
		if (COMMENT_AUTHOR_ID.equals(aId)) { 
			return fComment.getUser().getId();
		} else if (COMMENT_CREATION_DATE_ID.equals(aId)) {
			return fComment.getCreatedOn().toString();
		} else if (COMMENT_DESCRIPTION_ID.equals(aId)) {
			return fComment.getDescription();
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
				if (COMMENT_DESCRIPTION_ID.equals(aId)) {
					final String currentUser = R4EUIModelController.getReviewer();
					if (fComment.getUser().getId().equals(currentUser)) {
						final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fComment, currentUser);
						fComment.setDescription((String) aValue);
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
			actions.add(FContextRemoveNodeAction);
			if (getParent().getParent().getParent() instanceof R4EUIFileContext &&
					null != ((R4EUIFileContext)getParent().getParent().getParent()).getTargetFile()) actions.add(FContextOpenEditorAction);	 // $codepro.audit.disable methodChainLength
		}
		return actions;
	}
}
