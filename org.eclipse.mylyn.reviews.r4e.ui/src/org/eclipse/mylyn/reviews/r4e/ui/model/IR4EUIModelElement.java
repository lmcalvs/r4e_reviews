// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.disallowReturnMutable, packageJavadoc
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
 * This interface defines the methods used to access and control the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.io.FileNotFoundException;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */

public interface IR4EUIModelElement {
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Method setName.
	 * @param aName String
	 */
	void setName(String aName);
	
	/**
	 * Get the element name
	 * @return - the element name
	 */
	String getName();
	
	/**
	 * Method getToolTip.
	 * @return String
	 */
	String getToolTip();
	
	/**
	 * Method setToolTip.
	 * @param aToolTip String
	 */
	void setToolTip(String aToolTip);
	
	/**
	 * Get the element image
	 * @return - the element image
	 */
	Image getImage();
	
	/**
	 * Gets the overlay image that indicate that the element is reviewed
	 * @return Image
	 */
	Image getReviewedImage();
	
	/**
	 * Gets the reviewed flag.  Take note that this is not applicable to all model elements
	 * @return true/false
	 */
	boolean isReviewed();
	
	/**
	 * Sets the reviewed flag.  Take note that this is not applicable to all model elements
	 * @param aReviewed - the reviewed flag (true/false)
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException
	 */
	void setReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException;
	
	/**
	 * Set this child reviewed state
	 * @param aReviewed - the reviewed state
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException
	 */
	void setChildReviewed(boolean aReviewed) throws ResourceHandlingException, OutOfSyncException ;
	
	/**
	 * Checks if all the children of this parent are set as reviewed
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 */
	void checkToSetReviewed() throws ResourceHandlingException, OutOfSyncException;
	
	/**
	 * Open the model element (i.e. enable it)
	 * @throws ResourceHandlingException
	 * @throws ReviewVersionsException 
	 * @throws FileNotFoundException 
	 */
	void open() throws ResourceHandlingException, ReviewVersionsException, FileNotFoundException;
	
	/**
	 * Checks whether an element is open or close
	 * @return true if open, false otherwise
	 */
	boolean isOpen();
	
	/**
	 * Close the model element (i.e. disable it)
	 */
    void close();
    
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 * @throws ResourceHandlingException
	 */
    R4EReviewComponent createChildModelDataElement() throws ResourceHandlingException;
    
	/**
	 * Set serialization model data by copying it from the passed-in object
	 * @param aModelComponent - a serialization model element to copy information from
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
    void setModelData(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException;
	
    
    //Properties
    
	/**
	 * Method setInput.
	 */
	void setInput();
    
    
	//Hierarchy
	
	/**
	 * Get the element parent
	 * @return - the parent
	 */
	IR4EUIModelElement getParent();
	
	/**
	 * Get the element children
	 * @return - the children
	 */
	IR4EUIModelElement[] getChildren();
	
	/**
	 * Check whether the element has children
	 * @return true/false
	 */
	boolean hasChildren();
	
	/**
	 * Add a new children to the current element
	 * @param aModelComponent - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException 
	 * @throws OutOfSyncException
	 */
	IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException;
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 */
	void addChildren(IR4EUIModelElement aChildToAdd);

	/**
	 * Remove a child from the current element list
	 * @param aChildToRemove - the child to remove
	 */
	void removeChildren(IR4EUIModelElement aChildToRemove);


	//Listeners
	
	/**
	 * Add a listener to the current element
	 * @param aProvider - the treeviewer content provider
	 */
	void addListener(ReviewNavigatorContentProvider aProvider);
	
	/**
	 * Remove the listener from the current element
	 */
	void removeListener();
	
	/**
	 * Fire an add event to notify the UI of the model change
	 * @param aAdded - the added object 
	 */
	void fireAdd(Object aAdded);
	
	/**
	 * Fire a remove event to notify the UI of the model change
	 * @param aRemoved - the removed object 
	 */
	void fireRemove(Object aRemoved);
	
	/**
	 * Fire a review state changed event to notify the UI of the model change
	 * @param aChanged - the changed object
	 */
	void fireReviewStateChanged(Object aChanged);
	
	
	 //Commands
	
	/**
	 * Method isAddLinkedAnomalyCmd.
	 * @return boolean
	 */
	 boolean isAddLinkedAnomalyCmd();
	 
	/**
	 * Method isOpenEditorCmd.
	 * @return boolean
	 */
	 boolean isOpenEditorCmd();
	 
	/**
	 * Method isChangeReviewStateCmd.
	 * @return boolean
	 */	 
	 boolean isChangeReviewStateCmd();
	 
	 /**
	  * Method isOpenElementCmd.
	  * @return boolean
	  */
	 boolean isOpenElementCmd();
	 
	/**
	 * Method isCloseElementCmd.
	 * @return boolean
	 */
	 boolean isCloseElementCmd();
	 
	/**
	 * Method isAddChildElementCmd.
	 * @return boolean
	 */
	 boolean isAddChildElementCmd();
	 
	/**
	 * Method getAddChildElementCmdName.
	 * @return String
	 */
	 String getAddChildElementCmdName();
	 
	/**
	 * Method getAddChildElementCmdTooltip.
	 * @return String
	 */
	 String getAddChildElementCmdTooltip();
	 
	/**
	 * Method isRemoveElementCmd.
	 * @return boolean
	 */
	 boolean isRemoveElementCmd();
	 
	/**
	 * Method getRemoveElementCmdName.
	 * @return String
	 */
	 String getRemoveElementCmdName();
	 
	/**
	 * Method getRemoveElementCmdTooltip.
	 * @return String
	 */
	 String getRemoveElementCmdTooltip();
}
