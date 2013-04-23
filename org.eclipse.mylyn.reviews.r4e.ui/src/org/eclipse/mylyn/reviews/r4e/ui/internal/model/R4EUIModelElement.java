// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity
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
 * This class implements the IR4EUIModelElement interface.  It is used a the base 
 * class for all UI model elements
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Jacques Bouthillier - Add method definition for Report 
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.ModelElementProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.r4e.upgrade.ui.R4EUpgradeController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author Sebastien Dubois
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
	public static final String REVIEWED_OVERLAY_ICON_FILE = "icons/ovr16/revovr_tsk.gif"; //$NON-NLS-1$

	/**
	 * Field DISABLED_OVERLAY_ICON_FILE.
	 */
	public static final String DISABLED_OVERLAY_ICON_FILE = "icons/ovr16/dsbldovr_tsk.gif"; //$NON-NLS-1$

	/**
	 * Field DISABLED_OVERLAY_ICON_FILE.
	 */
	public static final String BOLD_ICON_FILE = "icons/ovr16/bold_icon.gif"; //$NON-NLS-1$

	/**
	 * Field DISABLED_OVERLAY_ICON_FILE.
	 */
	public static final String ITALIC_ICON_FILE = "icons/ovr16/italics_icon.gif"; //$NON-NLS-1$

	/**
	 * Field DUE_DATE_PASSED_OVERLAY_ICON_FILE.
	 */
	public static final String DUE_DATE_PASSED_OVERLAY_ICON_FILE = "icons/ovr16/duedateovr_tsk.gif"; //$NON-NLS-1$

	/**
	 * Field DISABLED_OVERLAY_ICON_FILE.
	 */
	public static final String READONLY_OVERLAY_ICON_FILE = "icons/ovr16/readonlyovr_tsk.gif"; //$NON-NLS-1$

	/**
	 * Field UNRESOLVED_OVERLAY_ICON_FILE.
	 */
	public static final String UNRESOLVED_OVERLAY_ICON_FILE = "icons/ovr16/unresolvedovr_tsk.gif"; //$NON-NLS-1$

	/**
	 * Field SET_IMAGE_MESSAGE.
	 */
	public static final String SET_IMAGE_MESSAGE = "Setting Images"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fName.
	 */
	private String fName;

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
	 * Field fUserReviewed.
	 */
	protected boolean fUserReviewed = false;

	/**
	 * Field fOpen.
	 */
	protected boolean fOpen = true;

	/**
	 * Field fReadOnly.
	 */
	protected boolean fReadOnly = false;

	/**
	 * Field fResolved.
	 */
	protected boolean fResolved = true;

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
	 */
	protected R4EUIModelElement(IR4EUIModelElement aParent, String aName) {
		fName = aName;
		fParent = aParent;
		fOpen = true; // by default
		fImage = null;
		fDisabledImage = null;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getAdapter.
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) {
			return this;
		}
		if (IPropertySource.class.equals(adapter)) {
			return new ModelElementProperties(this);
		}
		return null;
	}

	// Attributes

	/**
	 * Method setName.
	 * 
	 * @param aName
	 *            String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setName(String)
	 */
	public void setName(String aName) {
		fName = aName;
	}

	/**
	 * Method getName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getName()
	 */
	public String getName() {
		return fName;
	}

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTip()
	 */
	public String getToolTip() {
		return null; //default implementation
	}

	/**
	 * Method getToolTipColor.
	 * 
	 * @return Color
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTipColor()
	 */
	public Color getToolTipColor() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK); //default implementation
	}

	/**
	 * Method getImage.
	 * 
	 * @param aLocation
	 *            - String
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getImage()
	 */
	public Image getImage(final String aLocation) {
		if (isEnabled()) {
			if (null == fImage) {
				setImage(aLocation);
			}
			return fImage;
		}
		if (null == fDisabledImage) {
			setDisabledImage(aLocation);
		}
		return fDisabledImage;
	}

	/**
	 * Method setImage.
	 * 
	 * @param aLocation
	 *            String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setImage(String)
	 */
	public final void setImage(final String aLocation) {
		fImage = UIUtils.loadIcon(aLocation);
	}

	/**
	 * Method setDisabledImage.
	 * 
	 * @param aLocation
	 *            String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setDisabledImage(String)
	 */
	public final void setDisabledImage(final String aLocation) {
		fDisabledImage = UIUtils.loadDisabledIcon(aLocation);
	}

	/**
	 * Method isUserReviewed.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isUserReviewed()
	 */
	public boolean isUserReviewed() {
		return fUserReviewed;
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	public boolean isEnabled() {
		return true; //default implementation
	}

	/**
	 * Method isDueDatePassed.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isDueDatePassed()
	 */
	public boolean isDueDatePassed() {
		return false; //default implementation
	}

	/**
	 * Method isReadOnly.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isReadOnly()
	 */
	public boolean isReadOnly() {
		return fReadOnly;
	}

	/**
	 * Checks if the corresponding model element is assigned to a user
	 * 
	 * @param aUserName
	 *            - the user name
	 * @param aCheckChildren
	 *            - a flag that determines whether we will also check the child elements
	 * @return true/false
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isAssigned(String, boolean)
	 */
	public boolean isAssigned(String aUserName, boolean aCheckChildren) {
		return false; //default implementation
	}

	/**
	 * Method isResolved.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isResolved()
	 */
	public boolean isResolved() {
		return fResolved;
	}

	/**
	 * Close the model element (i.e. disable it)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#close()
	 */
	public void close() {
		fOpen = false;
	}

	/**
	 * Open the model element (i.e. enable it)
	 * 
	 * @throws ResourceHandlingException
	 * @throws FileNotFoundException
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#open()
	 */
	public void open() throws CompatibilityException, ResourceHandlingException, FileNotFoundException { // $codepro.audit.disable unnecessaryExceptions
		fOpen = true;
	}

	/**
	 * Checks whether an element is open or close
	 * 
	 * @return true if open, false otherwise
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpen()
	 */
	public boolean isOpen() {
		return fOpen;
	}

	/**
	 * Method getUserReviewedImage.
	 * 
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getUserReviewedImage()
	 */
	public Image getUserReviewedImage() {
		return UIUtils.loadIcon(REVIEWED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getDisabledImage.
	 * 
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getDisabledImage()
	 */
	public Image getDisabledImage() {
		return UIUtils.loadIcon(DISABLED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getDueDatePassedImage.
	 * 
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getDueDatePassedImage()
	 */
	public Image getDueDatePassedImage() {
		return UIUtils.loadIcon(DUE_DATE_PASSED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getReadOnlyImage.
	 * 
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getReadOnlyImage()
	 */
	public Image getReadOnlyImage() {
		return UIUtils.loadIcon(READONLY_OVERLAY_ICON_FILE);
	}

	/**
	 * Method getUnresolvedImage.
	 * 
	 * @return Image
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getUnresolvedImage()
	 */
	public Image getUnresolvedImage() {
		return UIUtils.loadIcon(UNRESOLVED_OVERLAY_ICON_FILE);
	}

	/**
	 * Method setUserReviewed.
	 * 
	 * @param aReviewed
	 *            boolean
	 * @param aSetChildren
	 *            boolean
	 * @param aUpdateModel
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setUserReviewed(boolean, boolean,
	 *      boolean)
	 */
	public void setUserReviewed(boolean aReviewed, boolean aSetChildren, boolean aUpdateModel)
			throws OutOfSyncException, ResourceHandlingException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		//default implementation
	}

	/**
	 * Method setEnabled.
	 * 
	 * @param aEnabled
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setEnabled(boolean)
	 */
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException,
			CompatibilityException { // $codepro.audit.disable emptyMethod
		//default implementation
	}

	/**
	 * Method setReadOnly.
	 * 
	 * @param aReadOnly
	 *            boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setReadOnly(boolean)
	 */
	public void setReadOnly(boolean aReadOnly) {
		fReadOnly = aReadOnly;
	}

	/**
	 * Method addAssignees.
	 * 
	 * @param aParticipants
	 *            - List<R4EParticipant> aParticipants
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addAssignees(List<R4EParticipant>,
	 *      boolean)
	 */
	public void addAssignees(List<R4EParticipant> aParticipants) {
		//default implementation
	}

	/**
	 * Method removeAssignees.
	 * 
	 * @param aParticipants
	 *            - List<R4EParticipant> aParticipants
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeAssignees(List<R4EParticipant>)
	 */
	public void removeAssignees(List<R4EParticipant> aParticipants) {
		//default implementation
	}

	/**
	 * Method setChildUserReviewed.
	 * 
	 * @param aUpdateModel
	 *            - flag that is used to see whether we should also update the serialization model
	 * @param aReviewed
	 *            boolean
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setChildUserReviewed(boolean, boolean)
	 */
	public void setChildUserReviewed(boolean aReviewed, boolean aUpdateModel) throws ResourceHandlingException,
			OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		// default implementation
	}

	/**
	 * Method checkToSetUserReviewed.
	 * 
	 * @param aUpdateModel
	 *            - flag that is used to see whether we should also update the serialization model
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#checkToSetUserReviewed(boolean)
	 */
	public void checkToSetUserReviewed(boolean aUpdateModel) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Create a serialization model element object
	 * 
	 * @return the new serialization element object
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildModelDataElement()
	 */
	public List<ReviewComponent> createChildModelDataElement() {
		//default implementation
		return null;
	}

	/**
	 * Set serialization model data by copying it from the passed-in object
	 * 
	 * @param aModelComponent
	 *            - a serialization model element to copy information from
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setModelData(R4EReviewComponent)
	 */
	public void setModelData(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException { // $codepro.audit.disable emptyMethod, unnecessaryExceptions
		//default implementation
	}

	/**
	 * Method setInput.
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#setInput()
	 */
	public void setInput() { // $codepro.audit.disable emptyMethod
		//default empty implementation
	}

	// Hierarchy

	/**
	 * Method getParent.
	 * 
	 * @return IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getParent()
	 */
	public IR4EUIModelElement getParent() {
		return fParent;
	}

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	public IR4EUIModelElement[] getChildren() {
		return new IR4EUIModelElement[0]; // $codepro.audit.disable $codepro.audit.disable reusableImmutables
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	public boolean hasChildren() {
		return false;
	}

	/**
	 * Method createChildren.
	 * 
	 * @param aModelComponent
	 *            R4EReviewComponent
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws OutOfSyncException,
			CompatibilityException, ResourceHandlingException { // $codepro.audit.disable unnecessaryExceptions
		return null;
		// default implementation
	}

	/**
	 * Add a children to the current model element
	 * 
	 * @param aChildToAdd
	 *            - the child to add
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	public void addChildren(IR4EUIModelElement aChildToAdd) { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Method removeChildren.
	 * 
	 * @param aChildToRemove
	 *            IR4EUIModelElement
	 * @param aFileRemove
	 *            - also remove from file (hard remove)
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement,
	 *      boolean)
	 */
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove)
			throws ResourceHandlingException, OutOfSyncException, CompatibilityException { // $codepro.audit.disable emptyMethod
		// default implementation
	}

	/**
	 * Method restore.
	 * 
	 * @throws CompatibilityException
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	public void restore() throws ResourceHandlingException, OutOfSyncException, CompatibilityException {
		setEnabled(true); //default implementation
	}

	/**
	 * Method removeAllChildren.
	 * 
	 * @param aFileRemove
	 *            boolean
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException,
			CompatibilityException { // $codepro.audit.disable emptyMethod -->
		//default implementation
	}

	/**
	 * Check version compatibility between the element(s) to load and the current R4E application and do the conversion
	 * if necessary, based on user input
	 * 
	 * @param aUpgradeRoot
	 *            - URI
	 * @param aOldVersion
	 *            - String
	 * @param aNewVersion
	 *            - String
	 * @param aRecursive
	 *            - boolean
	 * @return boolean
	 */
	public boolean checkCompatibility(URI aUpgradeRoot, String aElementMsg, String aOldVersion, String aNewVersion,
			boolean aRecursive) {
		int checkUpgradeResult;
		try {
			checkUpgradeResult = R4EUpgradeController.upgradeCheck(aUpgradeRoot, aElementMsg, aOldVersion, aNewVersion,
					aRecursive);
		} catch (IOException e) {
			R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			return false;
		}

		switch (checkUpgradeResult) {
		case R4EUIConstants.OPEN_NORMAL:
			fReadOnly = false;
			return true;
		case R4EUIConstants.OPEN_READONLY:
			fReadOnly = true;
			return true;
		default:
			//Assume Cancel
			return false;
		}
	}

	//Commands

	/**
	 * Method isAddLinkedAnomalyCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isAddLinkedAnomalyCmd()
	 */
	public boolean isAddLinkedAnomalyCmd() {
		return false; //default implementation
	}

	/**
	 * Method isOpenEditorCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenEditorCmd()
	 */
	public boolean isOpenEditorCmd() {
		return false; //default implementation
	}

	/**
	 * Method isChangeUserReviewStateCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isChangeUserReviewStateCmd()
	 */
	public boolean isChangeUserReviewStateCmd() {
		return false; //default implementation
	}

	/**
	 * Method isAssignToCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isAssignToCmd()
	 */
	public boolean isAssignToCmd() {
		return false; //default implementation
	}

	/**
	 * Method isUnassignToCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isUnassignToCmd()
	 */
	public boolean isUnassignToCmd() {
		return false; //default implementation
	}

	/**
	 * Method isOpenElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	public boolean isOpenElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getOpenElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getOpenElementCmdName()
	 */
	public String getOpenElementCmdName() {
		return R4EUIConstants.OPEN_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getOpenElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getOpenElementCmdTooltip()
	 */
	public String getOpenElementCmdTooltip() {
		return R4EUIConstants.OPEN_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isReportElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isReportElementCmd()
	 */
	public boolean isReportElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getReportElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getReportElementCmdName()
	 */
	public String getReportElementCmdName() {
		return R4EUIConstants.REPORT_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getReportElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getReportElementCmdTooltip()
	 */
	public String getReportElementCmdTooltip() {
		return R4EUIConstants.REPORT_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isCloseElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isCloseElementCmd()
	 */
	public boolean isCloseElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getCloseElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getCloseElementCmdName()
	 */
	public String getCloseElementCmdName() {
		return R4EUIConstants.CLOSE_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getCloseElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getCloseElementCmdTooltip()
	 */
	public String getCloseElementCmdTooltip() {
		return R4EUIConstants.CLOSE_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isCopyElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isCopyElementCmd()
	 */
	public boolean isCopyElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getCopyElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getCopyElementCmdName()
	 */
	public String getCopyElementCmdName() {
		return R4EUIConstants.COPY_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getCopyElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getCopyElementCmdTooltip()
	 */
	public String getCopyElementCmdTooltip() {
		return R4EUIConstants.COPY_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isPasteElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isPasteElementCmd()
	 */
	public boolean isPasteElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getPasteElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getPasteElementCmdName()
	 */
	public String getPasteElementCmdName() {
		return R4EUIConstants.PASTE_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getPasteElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getPasteElementCmdTooltip()
	 */
	public String getPasteElementCmdTooltip() {
		return R4EUIConstants.PASTE_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isNextStateElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isNextStateElementCmd()
	 */
	public boolean isNextStateElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getNextStateElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNextStateElementCmdName()
	 */
	public String getNextStateElementCmdName() {
		return R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getNextStateElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNextStateElementCmdTooltip()
	 */
	public String getNextStateElementCmdTooltip() {
		return R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isPreviousStateElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isPreviousStateElementCmd()
	 */
	public boolean isPreviousStateElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getPreviousStateElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getPreviousStateElementCmdName()
	 */
	public String getPreviousStateElementCmdName() {
		return R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getPreviousStateElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getPreviousStateElementCmdTooltip()
	 */
	public String getPreviousStateElementCmdTooltip() {
		return R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isAddChildElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isNewChildElementCmd()
	 */
	public boolean isNewChildElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getNewChildElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNewChildElementCmdName()
	 */
	public String getNewChildElementCmdName() {
		return R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getNewChildElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNewChildElementCmdTooltip()
	 */
	public String getNewChildElementCmdTooltip() {
		return R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isRemoveElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	public boolean isRemoveElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getRemoveElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	public String getRemoveElementCmdName() {
		return R4EUIConstants.REMOVE_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getRemoveElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	public String getRemoveElementCmdTooltip() {
		return R4EUIConstants.REMOVE_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	public boolean isRestoreElementCmd() {
		return false; //default implementation
	}

	/**
	 * Method getRestoreElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdName()
	 */
	public String getRestoreElementCmdName() {
		return R4EUIConstants.RESTORE_ELEMENT_COMMAND_NAME; //default implementation
	}

	/**
	 * Method getRestoreElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdTooltip()
	 */
	public String getRestoreElementCmdTooltip() {
		return R4EUIConstants.RESTORE_ELEMENT_COMMAND_TOOLTIP; //default implementation
	}

	/**
	 * Method isSendEmailCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isSendEmailCmd()
	 */
	public boolean isSendEmailCmd() {
		return false; //default implementation
	}

	/**
	 * Method isImportPostponedCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isImportPostponedCmd()
	 */
	public boolean isImportPostponedCmd() {
		return false; //default implementation
	}

	/**
	 * Method isShowPropertiesCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isShowPropertiesCmd()
	 */
	public boolean isShowPropertiesCmd() {
		return true; //default implementation
	}
}
