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
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.properties.FileContextProperties;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.views.properties.IPropertySource;


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
	 * Field fAnomalies.
	 */
	private List<R4EAnomaly> fAnomalies = null;  //Used to cache anomalies for this file context (used at startup)
	
	
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
		setImage(FILE_CONTEXT_ICON_FILE);
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
		if (IPropertySource.class.equals(adapter)) return new FileContextProperties(this);
		return null;
	}
	
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
		if (fReviewed != aReviewed) {   //Reviewed state is changed
			fReviewed = aReviewed;
			if (fReviewed) {
				//Add delta to the reviewedContent for this user
				addContentReviewed();
				
				//Also set the children
				if (null != fSelectionContainer) {
					final int length = fSelectionContainer.getChildren().length;
					for (int i = 0; i < length; i++) {
						fSelectionContainer.getChildren()[i].setChildReviewed(aReviewed);
					}
				}
				
				//Check to see if we should mark the parent reviewed as well
				getParent().checkToSetReviewed();
			} else {
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
				
				//Remove check on parent, since at least one children is not set anymore
				getParent().setReviewed(fReviewed);
			}
			fireReviewStateChanged(this);
		}
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
		if (fReviewed != aReviewed) {   //Reviewed state is changed
			fReviewed = aReviewed;
			if (aReviewed) {
				//Add delta to the reviewedContent for this user
				addContentReviewed();
				
				//Also set the children
				if (null != fSelectionContainer) {
					final int length = fSelectionContainer.getChildren().length;
					for (int i = 0; i < length; i++) {
						fSelectionContainer.getChildren()[i].setChildReviewed(aReviewed);
					}
				}
			} else {
				//Remove delta from the reviewedContent for this user
				removeContentReviewed();
			}
			fReviewed = aReviewed;
			fireReviewStateChanged(this);
		}
	}
	
	/**
	 * Method checkToSetReviewed.
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#checkToSetReviewed()
	 */
	@Override
	public void checkToSetReviewed() throws ResourceHandlingException, OutOfSyncException {
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
			addContentReviewed();
			getParent().checkToSetReviewed();
			fireReviewStateChanged(this);
		}
	}
	
	/**
	 * Method addContentReviewed.
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void addContentReviewed() throws ResourceHandlingException, OutOfSyncException {
		//First get the current user
		final R4EUIReview review = (R4EUIReview) getParent().getParent();
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), true);
		
		//Add this selection to the reviewed content for this user
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
		user.getReviewedContent().add(fFile.getId());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}
	
	/**
	 * Method removeContentReviewed.
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	private void removeContentReviewed() throws ResourceHandlingException, OutOfSyncException {
		//First get the current user
		final R4EUIReview review = (R4EUIReview) getParent().getParent();
		final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);
		
		if (null != user) {
			//Remove this selection from the reviewed content for this user
			final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(user, user.getId());
			user.getReviewedContent().remove(fFile.getId());
			R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		}
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
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fFile, R4EUIModelController.getReviewer());
		fFile.setEnabled(true);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
	}
	
	/**
	 * Method isEnabled.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fFile.isEnabled();
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
	 * Method open.
	 */
	@Override
	public void open() {
		
		//Restore resource data in serialization model
		final R4EFileVersion baseFileVersion = fFile.getBase();
		if (null != baseFileVersion) {
			try {
				final IFile baseFile = ResourceUtils.toIFile(baseFileVersion.getPlatformURI());
				baseFileVersion.setResource(baseFile);
			} catch (FileNotFoundException e) {
				Activator.Ftracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
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
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				targetFileVersion.setResource(null);
			}
		}
		
		//Load child data
		if (fFile.getDeltas().size() > 0) {
			addChildren(new R4EUISelectionContainer(this, R4EUIConstants.SELECTIONS_LABEL_NAME));
			fSelectionContainer.open();
		}
		
		fAnomalies = R4EUIModelController.getAnomaliesForFile(fFile.getTarget().getPlatformURI());
		if (null != fAnomalies && fAnomalies.size() > 0) {
			addChildren(new R4EUIAnomalyContainer(this, R4EUIConstants.ANOMALIES_LABEL_NAME));
			fAnomalyContainer.open();
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
	 * @param aFileRemove - also remove from file (hard remove)
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		if (aChildToRemove instanceof R4EUISelectionContainer) {
			fSelectionContainer.removeAllChildren(aFileRemove);
			if (!(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
				fSelectionContainer = null;
				aChildToRemove.removeListener();
				fireRemove(aChildToRemove);
			} else {
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		}
		else if (aChildToRemove instanceof R4EUIAnomalyContainer) {
			fAnomalyContainer.removeAllChildren(aFileRemove);
			if (!(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
				fAnomalyContainer = null;
				aChildToRemove.removeListener();
				fireRemove(aChildToRemove);
			} else {
				R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			}
		}
	}	
	
	/**
	 * Method removeAllChildren.
	 * @param aFileRemove boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		removeChildren(fSelectionContainer, aFileRemove);
		removeChildren(fAnomalyContainer, aFileRemove);
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
	
	
	//Commands

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
	
	/**
	 * Method isOpenEditorCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	@Override
	public boolean isOpenEditorCmd() {
		if (isEnabled() && null != getTargetFile()) return true;
		return false;
	}
	
	/**
	 * Method isChangeReviewStateCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isChangeReviewStateCmd()
	 */
	@Override
	public boolean isChangeReviewStateCmd() {
		if (isEnabled()) return true;
		return false;
	}
}
