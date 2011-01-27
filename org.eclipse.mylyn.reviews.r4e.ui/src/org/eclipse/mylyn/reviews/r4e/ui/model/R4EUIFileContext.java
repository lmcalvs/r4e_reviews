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
 * This class implements the File Context element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.actions.ChangeReviewedStateAction;
import org.eclipse.mylyn.reviews.r4e.ui.actions.OpenEditorAction;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIFileContext extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field FILE_CONTEXT_ICON_FILE.
	 * (value is ""icons/file.gif"")
	 */
	private static final String FILE_CONTEXT_ICON_FILE = "icons/file.gif";
	
    /**
     * Field CHANGE_REVIEW_STATE_ACTION_NAME.
     * (value is ""Mark/Unmark as completed"")
     */
    private static final String CHANGE_REVIEW_STATE_ACTION_NAME = "Mark/Unmark as completed";
    
    /**
     * Field CHANGE_REVIEW_STATE_ACTION_TOOLTIP.
     * (value is ""Mark/Unmark this file as reviewed"")
     */
    private static final String CHANGE_REVIEW_STATE_ACTION_TOOLTIP = "Mark/Unmark this file as reviewed";
	
	/**
	 * Field CHANGE_REVIEW_STATE_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String CHANGE_REVIEW_STATE_ACTION_ICON_FILE = "icons/done.gif";
	
    /**
     * Field OPEN_EDITOR_ACTION_NAME.
     * (value is ""Open file in editor"")
     */
    private static final String OPEN_EDITOR_ACTION_NAME = "Open file in editor";
    
    /**
     * Field OPEN_EDITOR_ACTION_TOOLTIP.
     * (value is ""Open the current file with the matching editor"")
     */
    private static final String OPEN_EDITOR_ACTION_TOOLTIP = "Open the current file with the matching editor";
    
	/**
	 * Field OPEN_EDITOR_ACTION_ICON_FILE.
	 * (value is ""icons/done.gif"")
	 */
	private static final String OPEN_EDITOR_ACTION_ICON_FILE = "icons/open_file.gif";
	
	/**
	 * Field FILE_BASE_VERSION_ID. (value is ""fileContextElement.baseVersion"")
	 */
	private static final String FILE_BASE_VERSION_ID = "fileContextElement.baseVersion";

	/**
	 * Field FILE_BASE_VERSION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_BASE_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_BASE_VERSION_ID, "Base file");
	
	/**
	 * Field FILE_TARGET_VERSION_ID. (value is ""fileContextElement.targetVersion"")
	 */
	private static final String FILE_TARGET_VERSION_ID = "fileContextElement.targetVersion";

	/**
	 * Field FILE_TARGET_VERSION_PROPERTY_DESCRIPTOR.
	 */
	private static final PropertyDescriptor FILE_TARGET_VERSION_PROPERTY_DESCRIPTOR = new PropertyDescriptor(
			FILE_TARGET_VERSION_ID, "Target file");
	
	/**
	 * Field DESCRIPTORS.
	 */
	private static final IPropertyDescriptor[] DESCRIPTORS = { FILE_BASE_VERSION_PROPERTY_DESCRIPTOR, 
		FILE_TARGET_VERSION_PROPERTY_DESCRIPTOR};
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fFile.
	 */
	private final R4EFileContext fFile;
	
	/**
	 * Field fSelectionContainer.
	 */
	private R4EUISelectionContainer fSelectionContainer = null;
	
	/**
	 * Field fAnomalyContainer.
	 */
	private R4EUIAnomalyContainer fAnomalyContainer = null;
	
	/**
	 * Field fChangeReviewedStateAction.
	 */
	private static ChangeReviewedStateAction FChangeReviewedStateAction = null;
	
	/**
	 * Field FContextOpenEditorAction.
	 */
	private static OpenEditorAction FContextOpenEditorAction = null;
	
	//Use to cache anomalies for this file context (used at startup)
	/**
	 * Field fAnomalies.
	 */
	private List<R4EAnomaly> fAnomalies = null;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIFileContext.
	 * @param aParent IR4EUIModelElement
	 * @param aFile R4EFileContext
	 */
	public R4EUIFileContext(IR4EUIModelElement aParent, R4EFileContext aFile) {
		super(aParent, aFile.getTarget().getName(), 
				getNavigatorTooltip(aFile.getTarget(), aFile.getBase()));
		fFile = aFile;
		fImage = UIUtils.loadIcon(FILE_CONTEXT_ICON_FILE);
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Method getAnomalies.
	 * @return List<R4EAnomaly>
	 */
	public List<R4EAnomaly> getAnomalies() {
		return fAnomalies;
	}
	
	/**
	 * Method getBaseFilePath.
	 * @return String
	 */
	public IFile getBaseFile() {
		if (null != fFile.getBase()) {
			return (IFile) fFile.getBase().getResource();
		}
		return null;
	}
	
	/**
	 * Method getTargetFilePath.
	 * @return String
	 */
	public IFile getTargetFile() {
		if (null != fFile.getTarget()) {
			return (IFile) fFile.getTarget().getResource();
		}
		return null;
	}
	
	/**
	 * Method getFileContext.
	 * @return R4EFileContext
	 */
	public R4EFileContext getFileContext() {
		return fFile;
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
			if (null != fSelectionContainer) {
				final int length = fSelectionContainer.getChildren().length;
				for (int i = 0; i < length; i++) {
					fSelectionContainer.getChildren()[i].setChildReviewed(aReviewed);
				}
			}
			getParent().checkToSetReviewed();
		} else {
			//Remove check on parent, since at least one children is not set anymore
			getParent().setReviewed(aReviewed);
		}

		fireReviewStateChanged(this);
	}
	
    /**
     * Method getNavigatorTooltip.
     * @param aTarget R4EFileVersion
     * @param aBase R4EFileVersion
     * @return String
	 */
    public static String getNavigatorTooltip(R4EFileVersion aTarget, R4EFileVersion aBase) {
		
    	//The tooltip shows the path relative to the workspace
    	final String targetResourceStr = aTarget.getVersionID();
		
    	if (null != aBase) {
    		final String baseResourceStr = aBase.getVersionID();	
			return "Base Version: " + baseResourceStr + System.getProperty("line.separator") + "Target Version: " 
				+ targetResourceStr;
		}
		return "Target Version: " + targetResourceStr;
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
		if (null != fSelectionContainer) {
			final int length = fSelectionContainer.getChildren().length;
			for (int i = 0; i < length; i++) {
				fSelectionContainer.getChildren()[i].setChildReviewed(aReviewed);
			}
		}
		fReviewed = aReviewed;
		fireReviewStateChanged(this);
	}
	
	/**
	 * Method checkToSetReviewed.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#checkToSetReviewed()
	 */
	@Override
	public void checkToSetReviewed() {
		boolean allChildrenReviewed = true;
		if (null != fSelectionContainer) {
			final int length = fSelectionContainer.getChildren().length;
			for (int i = 0; i < length; i++) {
				if (!(fSelectionContainer.getChildren()[i].isReviewed())) allChildrenReviewed = false;
			}
		}
		//If all children are reviewed, mark the parent as reviewed as well
		if (allChildrenReviewed) {
			fReviewed = true;
			getParent().checkToSetReviewed();
			fireReviewStateChanged(this);
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
	 * 
	 * @param aId
	 *            Object
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(Object)
	 */
	@Override
	public Object getPropertyValue(Object aId) {
		if (FILE_TARGET_VERSION_ID.equals(aId)) {
			return new R4EUIFileVersionSource(fFile.getTarget());
		} else if (FILE_BASE_VERSION_ID.equals(aId)) {
			if (null != fFile.getBase()) {
				return new R4EUIFileVersionSource(fFile.getBase());
			}
			return R4EUIConstants.NO_VERSION_PROPERTY_MESSAGE;
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
		final List<IR4EUIModelElement> newList = new ArrayList<IR4EUIModelElement>();
		if (null != fSelectionContainer) newList.add(fSelectionContainer);
		if (null != fAnomalyContainer) newList.add(fAnomalyContainer);
		return newList.toArray(new IR4EUIModelElement[newList.size()]);
	}
	
	/**
	 * Method getSelectionContainerElement.
	 * @return IR4EUIModelElement
	 */
	public IR4EUIModelElement getSelectionContainerElement() {
		return fSelectionContainer;
	}
	
	/**
	 * Method getAnomalyContainerElement.
	 * @return IR4EUIModelElement
	 */
	public IR4EUIModelElement getAnomalyContainerElement() {
		return fAnomalyContainer;
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (null == fSelectionContainer && null == fAnomalyContainer) {
			return false;
		}
		return true;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		if (null != fSelectionContainer) fSelectionContainer.close();
		fSelectionContainer = null;
		if (null != fAnomalyContainer)  fAnomalyContainer.close();
		fAnomalyContainer = null;
		fOpen = false;
		removeListener();
	}
	
	/**
	 * Method loadModelData.
	 */
	@Override
	public void loadModelData() {
		
		//Restore resource data in serialization model
		final R4EFileVersion baseFileVersion = fFile.getBase();
		if (null != baseFileVersion) {
			try {
				final IFile baseFile = ResourceUtils.toIFile(baseFileVersion.getPlatformURI());
				baseFileVersion.setResource(baseFile);
			} catch (FileNotFoundException e) {
				Activator.Tracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logInfo("Exception: " + e.toString(), e);
				baseFileVersion.setResource(null);
			}
		}
		
		final R4EFileVersion targetFileVersion = fFile.getTarget();
		if (null != targetFileVersion) {
			try {
				final IFile targetFile = ResourceUtils.toIFile(targetFileVersion.getPlatformURI());
				targetFileVersion.setResource(targetFile);
			} catch (FileNotFoundException e) {
				Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				targetFileVersion.setResource(null);
			}
		}
		
		//Load child data
		if (fFile.getDeltas().size() > 0) {
			addChildren(new R4EUISelectionContainer(this, R4EUIConstants.SELECTIONS_LABEL_NAME));
			fSelectionContainer.loadModelData();
		}
		
		fAnomalies = R4EUIModelController.getAnomaliesForFile(fFile.getTarget().getPlatformURI());
		if (null != fAnomalies && fAnomalies.size() > 0) {
			addChildren(new R4EUIAnomalyContainer(this, R4EUIConstants.ANOMALIES_LABEL_NAME));
			fAnomalyContainer.loadModelData();
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
		if (aChildToAdd instanceof R4EUISelectionContainer) {
			fSelectionContainer = (R4EUISelectionContainer) aChildToAdd;
		}
		else if (aChildToAdd instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer = (R4EUIAnomalyContainer) aChildToAdd;
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
		if (aChildToRemove instanceof R4EUISelectionContainer) {
			fSelectionContainer = null;
		}
		else if (aChildToRemove instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer = null;
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
		if (null != fSelectionContainer) fSelectionContainer.addListener(aProvider);
		if (null != fAnomalyContainer) fAnomalyContainer.addListener(aProvider);
	}
	
	/**
	 * Method removeListener.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener() {
		fListener = null;
		if (null != fSelectionContainer) fSelectionContainer.removeListener();
		if (null != fAnomalyContainer) fAnomalyContainer.removeListener();
	}
	
	
	//Actions
	
	/**
	 * Method createActions.
	 * @param aView ReviewNavigatorView
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createActions(ReviewNavigatorView)
	 */
	@Override
	public void createActions(ReviewNavigatorView aView) {
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
		if (null == FChangeReviewedStateAction) createActions(aView);
		final List<IAction> actions = new ArrayList<IAction>();
		if (!(R4EUIModelController.isDialogOpen()) && isOpen()) {
			actions.add(FChangeReviewedStateAction);
			if (null != getTargetFile()) actions.add(FContextOpenEditorAction);		
		}
		return actions;
	}

	/**
	 * Return true if the associated target file version can be compared with the associated base version
	 * @return true/false
	 */
	public boolean isFileVersionsComparable() {
		if (null != fFile.getBase()) {
			return true;
		}
		return false;
	}
}
