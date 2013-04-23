// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.disallowReturnMutable, packageJavadoc
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
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
 *   Jacques Bouthillier - Add method definition for Report
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.FileNotFoundException;
import java.util.List;

import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */

public interface IR4EUIModelElement {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	//Attributes

	/**
	 * Method setName.
	 * 
	 * @param aName
	 *            String
	 */
	void setName(String aName);

	/**
	 * Get the element name
	 * 
	 * @return - the element name
	 */
	String getName();

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 */
	String getToolTip();

	/**
	 * Method getToolTipColor.
	 * 
	 * @return String
	 */
	Color getToolTipColor();

	/**
	 * Get the element image path
	 * 
	 * @return String - the path to the image
	 */
	String getImageLocation();

	/**
	 * Get the element image
	 * 
	 * @param - the path to the image to get
	 * @return Image - the element image
	 */
	Image getImage(final String aLocation);

	/**
	 * Method setImage.
	 * 
	 * @param aLocation
	 *            String
	 */
	void setImage(String aLocation);

	/**
	 * Method setDisabledImage.
	 * 
	 * @param aLocation
	 *            String
	 */
	void setDisabledImage(String aLocation);

	/**
	 * Gets the overlay image that indicate that the element is reviewed
	 * 
	 * @return Image
	 */
	Image getUserReviewedImage();

	/**
	 * Gets the overlay image that indicate that the element is disabled
	 * 
	 * @return Image
	 */
	Image getDisabledImage();

	/**
	 * Gets the overlay image that indicate that the element is passed its dues date
	 * 
	 * @return Image
	 */
	Image getDueDatePassedImage();

	/**
	 * Gets the overlay image that indicate that the element is read-only
	 * 
	 * @return Image
	 */
	Image getReadOnlyImage();

	/**
	 * Gets the overlay image that indicate that the element is unresolved
	 * 
	 * @return Image
	 */
	Image getUnresolvedImage();

	/**
	 * Method isResolved.
	 * 
	 * @return boolean
	 */
	boolean isResolved();

	/**
	 * Gets the reviewed flag. Take note that this is not applicable to all model elements
	 * 
	 * @return true/false
	 */
	boolean isUserReviewed();

	/**
	 * Checks if the corresponding model element is enabled or disabled
	 * 
	 * @return true/false
	 */
	boolean isEnabled();

	/**
	 * Checks if the corresponding model element due date is passed (if applicable)
	 * 
	 * @return boolean
	 */
	boolean isDueDatePassed();

	/**
	 * Checks if the corresponding model element is read-only
	 * 
	 * @return boolean
	 */
	boolean isReadOnly();

	/**
	 * Checks if the corresponding model element is assigned to a user
	 * 
	 * @param aUserName
	 *            - the user name
	 * @param aCheckChildren
	 *            - a flag that determines whether we will also check the child elements
	 * @return true/false
	 */
	boolean isAssigned(String aUserName, boolean aCheckChildren);

	/**
	 * Sets the reviewed flag. Take note that this is not applicable to all model elements
	 * 
	 * @param aReviewed
	 *            - the reviewed flag (true/false)
	 * @param aSetChildren
	 *            - flag that is used to see whether we should also update child elements
	 * @param aUpdateModel
	 *            - flag that is used to see whether we should also update the serialization model
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	void setUserReviewed(boolean aReviewed, boolean aSetChildren, boolean aUpdateModel)
			throws ResourceHandlingException, OutOfSyncException;

	/**
	 * Sets the enabled flag. Take note that this is not applicable to all model elements
	 * 
	 * @param aEnabled
	 *            - the enable flag (true/false)
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws CompatibilityException
	 */
	void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException, CompatibilityException;

	/**
	 * Sets the read-only flag.
	 * 
	 * @param aReadOnly
	 *            - the read-only flag (true/false)
	 */
	void setReadOnly(boolean aReadOnly);

	/**
	 * Add assignees to review element. Take note that this is not applicable to all model elements
	 * 
	 * @param aParticipants
	 *            - the list of assigned participants to assign
	 */
	void addAssignees(List<R4EParticipant> aParticipants);

	/**
	 * Remove assignees from review element. Take note that this is not applicable to all model elements
	 * 
	 * @param aParticipants
	 *            - the list of assigned participants to unassign
	 */
	void removeAssignees(List<R4EParticipant> aParticipants);

	/**
	 * Set this child reviewed state
	 * 
	 * @param aReviewed
	 *            - the reviewed state
	 * @param aUpdateModel
	 *            - flag that is used to see whether we should also update the serialization model
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	void setChildUserReviewed(boolean aReviewed, boolean aUpdateModel) throws ResourceHandlingException,
			OutOfSyncException;

	/**
	 * Checks if all the children of this parent are set as reviewed
	 * 
	 * @param aUpdateModel
	 *            - flag that is used to see whether we should also update the serialization model
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	void checkToSetUserReviewed(boolean aUpdateModel) throws ResourceHandlingException, OutOfSyncException;

	/**
	 * Open the model element (i.e. enable it)
	 * 
	 * @throws ResourceHandlingException
	 * @throws FileNotFoundException
	 * @throws CompatibilityException
	 */
	void open() throws ResourceHandlingException, FileNotFoundException, CompatibilityException;

	/**
	 * Checks whether an element is open or close
	 * 
	 * @return true if open, false otherwise
	 */
	boolean isOpen();

	/**
	 * Close the model element (i.e. disable it)
	 */
	void close();

	/**
	 * Create a serialization model element object
	 * 
	 * @return the new serialization elements objects
	 * @throws ResourceHandlingException
	 */
	List<ReviewComponent> createChildModelDataElement() throws ResourceHandlingException;

	/**
	 * Set serialization model data by copying it from the passed-in object
	 * 
	 * @param aModelComponent
	 *            - a serialization model element to copy information from
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	void setModelData(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException;

	//Properties

	/**
	 * Method setInput.
	 */
	void setInput();

	//Hierarchy

	/**
	 * Get the element parent
	 * 
	 * @return - the parent
	 */
	IR4EUIModelElement getParent();

	/**
	 * Get the element children
	 * 
	 * @return - the children
	 */
	IR4EUIModelElement[] getChildren();

	/**
	 * Check whether the element has children
	 * 
	 * @return true/false
	 */
	boolean hasChildren();

	/**
	 * Add a new children to the current element
	 * 
	 * @param tempModelComponent
	 *            - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws CompatibilityException
	 */
	IR4EUIModelElement createChildren(ReviewComponent tempModelComponent) throws ResourceHandlingException,
			OutOfSyncException, CompatibilityException;

	/**
	 * Method addChildren.
	 * 
	 * @param aChildToAdd
	 *            IR4EUIModelElement
	 */
	void addChildren(IR4EUIModelElement aChildToAdd);

	/**
	 * Remove a child from the current element list
	 * 
	 * @param aChildToRemove
	 *            - the child to remove
	 * @param aFileRemove
	 *            - also remove from file (hard remove)
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove) throws ResourceHandlingException,
			OutOfSyncException, CompatibilityException;

	/**
	 * Method restore.
	 * 
	 * @throws CompatibilityException
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	void restore() throws ResourceHandlingException, OutOfSyncException, CompatibilityException;

	/**
	 * Method removeAllChildren.
	 * 
	 * @param aFileRemove
	 *            boolean
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException,
			CompatibilityException;

	//Commands

	/**
	 * Method isAddLinkedAnomalyCmd.
	 * 
	 * @return boolean
	 */
	boolean isAddLinkedAnomalyCmd();

	/**
	 * Method isOpenEditorCmd.
	 * 
	 * @return boolean
	 */
	boolean isOpenEditorCmd();

	/**
	 * Method isChangeReviewStateCmd.
	 * 
	 * @return boolean
	 */
	boolean isChangeUserReviewStateCmd();

	/**
	 * Method isAssignToCmd.
	 * 
	 * @return boolean
	 */
	boolean isAssignToCmd();

	/**
	 * Method isUnassignToCmd.
	 * 
	 * @return boolean
	 */
	boolean isUnassignToCmd();

	/**
	 * Method isOpenElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isOpenElementCmd();

	/**
	 * Method getOpenElementCmdName.
	 * 
	 * @return String
	 */
	String getOpenElementCmdName();

	/**
	 * Method getOpenElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getOpenElementCmdTooltip();

	/**
	 * Method isReportElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isReportElementCmd();

	/**
	 * Method getReportElementCmdName.
	 * 
	 * @return String
	 */
	String getReportElementCmdName();

	/**
	 * Method getReportElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getReportElementCmdTooltip();

	/**
	 * Method isCloseElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isCloseElementCmd();

	/**
	 * Method getCloseElementCmdName.
	 * 
	 * @return String
	 */
	String getCloseElementCmdName();

	/**
	 * Method getCloseElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getCloseElementCmdTooltip();

	/**
	 * Method isCopyElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isCopyElementCmd();

	/**
	 * Method getCopyElementCmdName.
	 * 
	 * @return String
	 */
	String getCopyElementCmdName();

	/**
	 * Method getCopyElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getCopyElementCmdTooltip();

	/**
	 * Method isPasteElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isPasteElementCmd();

	/**
	 * Method getPasteElementCmdName.
	 * 
	 * @return String
	 */
	String getPasteElementCmdName();

	/**
	 * Method getPasteElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getPasteElementCmdTooltip();

	/**
	 * Method isNextStateElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isNextStateElementCmd();

	/**
	 * Method getNextStateElementCmdName.
	 * 
	 * @return String
	 */
	String getNextStateElementCmdName();

	/**
	 * Method getNextStateElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getNextStateElementCmdTooltip();

	/**
	 * Method isPreviousStateElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isPreviousStateElementCmd();

	/**
	 * Method getPreviousStateElementCmdName.
	 * 
	 * @return String
	 */
	String getPreviousStateElementCmdName();

	/**
	 * Method getPreviousStateElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getPreviousStateElementCmdTooltip();

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isRestoreElementCmd();

	/**
	 * Method getRestoreElementCmdName.
	 * 
	 * @return String
	 */
	String getRestoreElementCmdName();

	/**
	 * Method getRestoreElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getRestoreElementCmdTooltip();

	/**
	 * Method isNewChildElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isNewChildElementCmd();

	/**
	 * Method getNewChildElementCmdName.
	 * 
	 * @return String
	 */
	String getNewChildElementCmdName();

	/**
	 * Method getNewChildElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getNewChildElementCmdTooltip();

	/**
	 * Method isRemoveElementCmd.
	 * 
	 * @return boolean
	 */
	boolean isRemoveElementCmd();

	/**
	 * Method getRemoveElementCmdName.
	 * 
	 * @return String
	 */
	String getRemoveElementCmdName();

	/**
	 * Method getRemoveElementCmdTooltip.
	 * 
	 * @return String
	 */
	String getRemoveElementCmdTooltip();

	/**
	 * Method isSendEmailCmd.
	 * 
	 * @return boolean
	 */
	boolean isSendEmailCmd();

	/**
	 * Method isImportPostponedCmd.
	 * 
	 * @return boolean
	 */
	boolean isImportPostponedCmd();

	/**
	 * Method isShowPropertiesCmd.
	 * 
	 * @return boolean
	 */
	boolean isShowPropertiesCmd();
}
