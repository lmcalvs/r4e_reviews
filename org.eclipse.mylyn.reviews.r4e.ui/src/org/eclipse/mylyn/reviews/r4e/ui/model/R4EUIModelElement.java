// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
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
 * This class implements the IR4EUIModelElement interface.  It is used a the base 
 * class for all UI model elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.ModelElementProperties;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public abstract class R4EUIModelElement implements IR4EUIModelElement, // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses
		IAdaptable {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field REVIEWED_OVERLAY_ICON_FILE.
	 */
	private static final String REVIEWED_OVERLAY_ICON_FILE = "icons/ovr16/revovr_tsk.gif";

	/**
	 * Field DISABLED_OVERLAY_ICON_FILE.
	 */
	private static final String DISABLED_OVERLAY_ICON_FILE = "icons/ovr16/dsbldovr_tsk.gif";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fName.
	 */
	private String fName;

	/**
	 * Field fTooltip.
	 */
	private String fTooltip;

	/**
	 * Field fImage.
	 */
	protected Image fImage;

	/**
	 * Field fDisabledImage.
	 */
	protected Image fDisabledImage;
	
	/**
	 * Field fParent.
	 */
	private final IR4EUIModelElement fParent;

	/**
	 * Field fListener.
	 */
	protected List<IR4EUIModelListener> fListeners = new ArrayList<IR4EUIModelListener>();

	/**
	 * Field fReviewed.
	 */
	protected boolean fReviewed = false;

	/**
	 * Field fOpen.
	 */
	protected boolean fOpen = true;

	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIModelElement.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aName
	 *            String
	 * @param aTooltip
	 *            String
	 */
	protected R4EUIModelElement(IR4EUIModelElement aParent, String aName, String aTooltip) {
		fName = aName;
		fTooltip = aTooltip;
		fParent = aParent;
		fOpen = true; // by default
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
	public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) return this;
		if (IPropertySource.class.equals(adapter)) return new ModelElementProperties(this);
		return null;
	}
	
	
	// Attributes

	/**
	 * Method setName.
	 * 
	 * @param aName String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setName(String)
	 */
	public void setName(String aName) {
		fName = aName;
	}

	/**
	 * Method getName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getName()
	 */
	public String getName() {
		return fName;
	}

	/**
	 * Method getToolTip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getToolTip()
	 */
	public String getToolTip() {
		return fTooltip;
	}
	
	/**
	 * Method setToolTip.
	 * @param aToolTip String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setToolTip(String)
	 */
	public void setToolTip(String aToolTip) {
		fTooltip = aToolTip;
	}

	/**
	 * Method getImage.
	 * @return Image 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getImage()
	 */
	public Image getImage() {
		if (isEnabled()) return fImage;
		return fDisabledImage;
	}
	
	/**
	 * Method setImage.
	 * @param aLocation String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setImage(String)
	 */
	public final void setImage(String aLocation) {
		fImage = UIUtils.loadIcon(aLocation);
		fDisabledImage = new Image(null, fImage, SWT.IMAGE_DISABLE);
	}
	
	/**
	 * Method isReviewed.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isReviewed()
	 */
	public boolean isReviewed() {
		return fReviewed;
	}

	/**
	 * Method isEnabled.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isEnabled()
	 */
	public boolean isEnabled() {
		return true;   //default implementation
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	public void close() {
		fOpen = false;
		removeListeners();
	}

	/**
	 * Open the model element (i.e. enable it)
	 * @throws ResourceHandlingException
	 * @throws ReviewVersionsException 
	 * @throws FileNotFoundException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#open()
	 */
	public void open() throws ResourceHandlingException, ReviewVersionsException, FileNotFoundException { // $codepro.audit.disable unnecessaryExceptions
		fOpen = true;
	}

	/**
	 * Checks whether an element is open or close
	 * @return true if open, false otherwise
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpen()
	 */
	public boolean isOpen() {
		return fOpen;
	}

	/**
	 * Method getReviewedImage.
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getReviewedImage()
	 */
	public Image getReviewedImage() {
		return UIUtils.loadIcon(REVIEWED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getDisabledImage.
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getDisabledImage()
	 */
	public Image getDisabledImage() {
		return UIUtils.loadIcon(DISABLED_OVERLAY_ICON_FILE);
	}
	
	
	/**
	 * Method setReviewed.
	 * @param aReviewed boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setReviewed(boolean)
	 */
	public void setReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		//default implementation
	}

	/**
	 * Method setEnabled.
	 * @param aEnabled boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setReviewed(boolean)
	 */
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod
		//default implementation
	}

	/**
	 * Method setChildrenReviewed.
	 * @param aReviewed boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setChildReviewed(boolean)
	 */
	public void setChildReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		// default implementation
	}

	/**
	 * Method checkToSetReviewed.
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#checkToSetReviewed()
	 */
	public void checkToSetReviewed() throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildModelDataElement()
	 */
    public ReviewComponent createChildModelDataElement() {
    	//default implementation
    	return null;
    }
    
	/**
	 * Set serialization model data by copying it from the passed-in object
	 * @param aModelComponent - a serialization model element to copy information from
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setModelData(R4EReviewComponent)
	 */
    public void setModelData(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
    	//default implementation
    }
    
	/**
	 * Method setInput.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setInput()
	 */
	public void setInput() { // $codepro.audit.disable emptyMethod
    	//default empty implementation
	}
    
	// Hierarchy

	/**
	 * Method getParent.
	 * @return IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getParent()
	 */
	public IR4EUIModelElement getParent() {
		return fParent;
	}

	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[] 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	public IR4EUIModelElement[] getChildren() {
		return new IR4EUIModelElement[0];   // $codepro.audit.disable $codepro.audit.disable reusableImmutables
	}

	/**
	 * Method hasChildren.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	public boolean hasChildren() {
		return false;
	}

	/**
	 * Method createChildren.
	 * @param aModelComponent R4EReviewComponent
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable unnecessaryExceptions
		return null;
		// default implementation
	}

	/**
	 * Add a children to the current model element
	 * @param aChildToAdd - the child to add
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	public void addChildren(IR4EUIModelElement aChildToAdd) { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @param aFileRemove - also remove from file (hard remove)
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement, boolean)
	 */
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Method removeAllChildren.
	 * @param aFileRemove boolean
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod -->
		//default implementation
	}
	
	// Listeners

	/**
	 * Method addListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	public void addListener(ReviewNavigatorContentProvider aProvider) {
		fListeners.add(aProvider);
	}

	/**
	 * Method removeListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	public void removeListener(ReviewNavigatorContentProvider aProvider) {
		fListeners.remove(aProvider);
	}

	/**
	 * Method removeListeners.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListeners()
	 */
	public void removeListeners() {
		fListeners.clear();
	}
	
	/**
	 * Method fireAdd.
	 * @param aAdded Object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#fireAdd(Object)
	 */
	public void fireAdd(Object aAdded) {
		for (IR4EUIModelListener listener : fListeners) {
			listener.addEvent(new R4EUIModelEvent(aAdded));
		}
	}

	/**
	 * Method fireRemove.
	 * @param aRemoved Object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#fireRemove(Object)
	 */
	public void fireRemove(Object aRemoved) {
		for (IR4EUIModelListener listener : fListeners) {
			listener.removeEvent(new R4EUIModelEvent(aRemoved));
		}
	}

	/**
	 * Method fireReviewStateChanged.
	 * @param aChanged Object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#fireReviewStateChanged(Object)
	 */
	public void fireReviewStateChanged(Object aChanged) {
		for (IR4EUIModelListener listener : fListeners) {
			listener.changedEvent(new R4EUIModelEvent(aChanged));
		}
	}

	
	//Commands
	
	/**
	 * Method isAddLinkedAnomalyCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddLinkedAnomalyCmd()
	 */
	public boolean isAddLinkedAnomalyCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method isOpenEditorCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	public boolean isOpenEditorCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method isChangeReviewStateCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isChangeReviewStateCmd()
	 */
	public boolean isChangeReviewStateCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method isOpenElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	public boolean isOpenElementCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method isCloseElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isCloseElementCmd()
	 */
	public boolean isCloseElementCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method isAddChildElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddChildElementCmd()
	 */
	public boolean isAddChildElementCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method getAddChildElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdName()
	 */
	public String getAddChildElementCmdName() {
		return R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_NAME;   //default implementation
	}
	
	/**
	 * Method getAddChildElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdTooltip()
	 */
	public String getAddChildElementCmdTooltip() {
		return R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_TOOLTIP;   //default implementation
	}
	
	/**
	 * Method isRemoveElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	public boolean isRemoveElementCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method isRestoreElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	public boolean isRestoreElementCmd() {
		return false;   //default implementation
	}
	
	/**
	 * Method getRemoveElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	public String getRemoveElementCmdName() {
		return R4EUIConstants.REMOVE_ELEMENT_COMMAND_NAME;   //default implementation
	}
	
	/**
	 * Method getRemoveElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	public String getRemoveElementCmdTooltip() {
		return R4EUIConstants.REMOVE_ELEMENT_COMMAND_TOOLTIP;   //default implementation
	}
	
	/**
	 * Method isSendEmailCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isSendEmailCmd()
	 */
	public boolean isSendEmailCmd() {
		return false;   //default implementation
	}
}
