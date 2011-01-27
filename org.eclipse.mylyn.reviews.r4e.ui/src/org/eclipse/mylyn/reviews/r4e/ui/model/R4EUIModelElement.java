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

import org.eclipse.jface.action.IAction;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public abstract class R4EUIModelElement implements IR4EUIModelElement, // $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.preferInterfacesToAbstractClasses
		IPropertySource {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fReviewedOverlayFile.
	 */
	private static final String REVIEWED_OVERLAY_ICON_FILE = "icons/reviewedOverlay.gif";

	
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
	private final String fTooltip;

	/**
	 * Field fImage.
	 */
	protected Image fImage;

	/**
	 * Field fParent.
	 */
	private final IR4EUIModelElement fParent;

	/**
	 * Field fListener.
	 */
	protected IR4EUIModelListener fListener;

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
	protected R4EUIModelElement(IR4EUIModelElement aParent, String aName,
			String aTooltip) {
		fName = aName;
		fTooltip = aTooltip;
		fParent = aParent;
		fOpen = true; // by default
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	// Attributes

	/**
	 * Method setName.
	 * 
	 * @param aName
	 *            String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setName(String)
	 */
	@Override
	public void setName(String aName) {
		fName = aName;
	}

	/**
	 * Method getName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getName()
	 */
	@Override
	public String getName() {
		return fName;
	}

	/**
	 * Method getToolTip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return fTooltip;
	}

	/**
	 * Method getImage.
	 * @return Image 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement
	 */
	@Override
	public Image getImage() {
		return fImage;
	}

	/**
	 * Method isReviewed.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isReviewed()
	 */
	@Override
	public boolean isReviewed() {
		return fReviewed;
	}

	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		fOpen = false;
		removeListener();
	}

	/**
	 * Open the model element (i.e. enable it)
	 * @throws ResourceHandlingException
	 * @throws ReviewVersionsException 
	 * @throws FileNotFoundException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#open()
	 */
	@Override
	public void open() throws ResourceHandlingException, ReviewVersionsException, FileNotFoundException { // $codepro.audit.disable unnecessaryExceptions
		fOpen = true;
	}

	/**
	 * Checks whether an element is open or close
	 * @return true if open, false otherwise
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return fOpen;
	}

	/**
	 * Method getReviewedImage.
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getReviewedImage()
	 */
	@Override
	public Image getReviewedImage() {
		return UIUtils.loadIcon(REVIEWED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method setReviewed.
	 * 
	 * @param aReviewed boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setReviewed(boolean)
	 */
	@Override
	public void setReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		//default implementation
	}

	/**
	 * Method setChildrenReviewed.
	 * @param aReviewed boolean
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setChildReviewed(boolean)
	 */
	@Override
	public void setChildReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		// default implementation
	}

	/**
	 * Method checkToSetReviewed.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#checkToSetReviewed()
	 */
	@Override
	public void checkToSetReviewed() { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildModelDataElement()
	 */
    @Override
	public R4EReviewComponent createChildModelDataElement() throws ResourceHandlingException {
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
    @Override
	public void setModelData(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
    	//default implementation
    }
    
	// Properties

	/**
	 * Method getEditableValue.
	 * @return Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	@Override
	public Object getEditableValue() {
		return null;  //default implementation
	}

	/**
	 * Method getPropertyDescriptors.
	 * @return IPropertyDescriptor[]
	 * @see org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[0];  //default implementation <!-- // $codepro.audit.disable reusableImmutables -->
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
		return null;  //default implementation
	}

	/**
	 * Method resetPropertyValue.
	 * @param id Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(Object) 
	 */
	@Override
	public void resetPropertyValue(Object id) { // $codepro.audit.disable emptyMethod
			//default implementation, no properties are resettable
	}

	/**
	 * Method setPropertyValue.
	 * @param id Object
	 * @param value Object
	 * @see org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(Object, Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) { // $codepro.audit.disable emptyMethod
		  //default implementation
	}

	/**
	 * Method isPropertySet.
	 * 
	 * @param id Object
	 * @return boolean 
	 * @see org.eclipse.ui.views.properties.IPropertySource#isPropertySet(Object)
	 */
	@Override
	public boolean isPropertySet(Object id) {
		return false; //   //default implementation, no propery has a default value
	}
    
	/**
	 * Method setInput.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setInput()
	 */
	@Override
	public void setInput() { // $codepro.audit.disable emptyMethod
    	//default empty implementation
	}
    
	// Hierarchy

	/**
	 * Method getParent.
	 * @return IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getParent()
	 */
	@Override
	public IR4EUIModelElement getParent() {
		return fParent;
	}

	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[] 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return new IR4EUIModelElement[0];   // $codepro.audit.disable $codepro.audit.disable reusableImmutables
	}

	/**
	 * Method hasChildren.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
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
	@Override
	public IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable unnecessaryExceptions
		return null;
		// default implementation
	}

	/**
	 * Load the serialization model data for this element
	 */
	public void loadModelData() { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Add a children to the current model element
	 * @param aChildToAdd - the child to add
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove) { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	// Listeners

	/**
	 * Method addListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
		fListener = aProvider;
	}

	/**
	 * Method removeListener.
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener() {
		fListener = null;
	}

	/**
	 * Method fireAdd.
	 * @param aAdded Object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#fireAdd(Object)
	 */
	@Override
	public void fireAdd(Object aAdded) {
		if (null != fListener) {
			fListener.addEvent(new R4EUIModelEvent(aAdded));
		}
	}

	/**
	 * Method fireRemove.
	 * @param aRemoved Object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#fireRemove(Object)
	 */
	@Override
	public void fireRemove(Object aRemoved) {
		if (null != fListener) {
			fListener.removeEvent(new R4EUIModelEvent(aRemoved));
		}
	}

	/**
	 * Method fireReviewStateChanged.
	 * @param aChanged Object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#fireReviewStateChanged(Object)
	 */
	@Override
	public void fireReviewStateChanged(Object aChanged) {
		if (null != fListener) {
			fListener.changedEvent(new R4EUIModelEvent(aChanged));
		}
	}

	// Actions

	/**
	 * Method createActions.
	 * @param aView ReviewNavigatorView
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createActions(ReviewNavigatorView)
	 */
	@Override
	public void createActions(ReviewNavigatorView aView) { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Method getActions.
	 * @param aView ReviewNavigatorView
	 * @return List<Action>
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getActions(ReviewNavigatorView)
	 */
	@Override
	public List<IAction> getActions(ReviewNavigatorView aView) {
		final List<IAction> actions = new ArrayList<IAction>();
		return actions;
	}

}
