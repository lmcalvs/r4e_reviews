// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Rule Set element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IRuleAreaInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.RuleSetProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIRuleSet extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field RULE_SET_ICON_FILE. (value is ""icons/obj16/ruleset_obj.gif"")
	 */
	public static final String RULE_SET_ICON_FILE = "icons/obj16/ruleset_obj.gif";

	/**
	 * Field RULE_SET_CLOSED_ICON_FILE. (value is ""icons/obj16/rulesetclsd_obj.gif"")
	 */
	public static final String RULE_SET_CLOSED_ICON_FILE = "icons/obj16/rulesetclsd_obj.gif";

	/**
	 * Field NEW_CHILD_ELEMENT_COMMAND_NAME. (value is ""New Rule Area..."")
	 */
	private static final String NEW_CHILD_ELEMENT_COMMAND_NAME = "New Rule Area...";

	/**
	 * Field NEW_CHILD_ELEMENT_COMMAND_TOOLTIP. (value is ""Add a New Rule Area to the Current Rule Set"")
	 */
	private static final String NEW_CHILD_ELEMENT_COMMAND_TOOLTIP = "Add a New Rule Area to the Current Rule Set";

	/**
	 * Field OPEN_ELEMENT_COMMAND_NAME. (value is ""Open Rule Set"")
	 */
	private static final String OPEN_ELEMENT_COMMAND_NAME = "Open Rule Set";

	/**
	 * Field OPEN_ELEMENT_COMMAND_TOOLTIP. (value is ""Open and Load Data for this Rule Set"")
	 */
	private static final String OPEN_ELEMENT_COMMAND_TOOLTIP = "Open and Load Data for this Rule Set";

	/**
	 * Field CLOSE_ELEMENT_COMMAND_NAME. (value is ""Close Rule Set"")
	 */
	private static final String CLOSE_ELEMENT_COMMAND_NAME = "Close Rule Set";

	/**
	 * Field CLOSE_ELEMENT_COMMAND_TOOLTIP. (value is ""Close and Unload Data for this Rule Set"")
	 */
	private static final String CLOSE_ELEMENT_COMMAND_TOOLTIP = "Close and Unload Data for this Rule Set";

	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME. (value is ""Disable Rule Set"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Rule Set";

	/**
	 * Field REMOVE_ELEMENT_ACTION_TOOLTIP. (value is ""Disable this Rule Set"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable this Rule Set";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fGroup.
	 */
	protected R4EDesignRuleCollection fRuleSet;

	/**
	 * Field fRulesSetFileURI.
	 */
	private final URI fRuleSetFileURI;

	/**
	 * Field fAreas.
	 */
	private final List<R4EUIRuleArea> fAreas;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIParticipantContainer.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aRuleSet
	 *            R4EDesignRuleCollection
	 * @param aOpen
	 *            boolean
	 */
	public R4EUIRuleSet(IR4EUIModelElement aParent, R4EDesignRuleCollection aRuleSet, boolean aOpen) {
		super(aParent, aRuleSet.getName());
		fReadOnly = false;
		fRuleSet = aRuleSet;
		fRuleSetFileURI = aRuleSet.eResource().getURI();
		fAreas = new ArrayList<R4EUIRuleArea>();
		fOpen = aOpen;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getImageLocation.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getImageLocation()
	 */
	public String getImageLocation() {
		if (isOpen()) {
			return RULE_SET_ICON_FILE;
		}
		return RULE_SET_CLOSED_ICON_FILE;
	}

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTip()
	 */
	@Override
	public String getToolTip() {
		return R4EUIConstants.FILE_LOCATION_LABEL + URI.decode(fRuleSetFileURI.devicePath());
	}

	/**
	 * Method getAdapter.
	 * 
	 * @param adapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class adapter) {
		if (IR4EUIModelElement.class.equals(adapter)) {
			return this;
		}
		if (IPropertySource.class.equals(adapter)) {
			return new RuleSetProperties(this);
		}
		return null;
	}

	//Attributes

	/**
	 * Method getRuleSet.
	 * 
	 * @return R4EDesignRuleCollection
	 */
	public R4EDesignRuleCollection getRuleSet() {
		return fRuleSet;
	}

	/**
	 * Method getRuleSetFileURI.
	 * 
	 * @return URI
	 */
	public String getRuleSetFile() {
		return fRuleSetFileURI.devicePath();
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
	@Override
	public void setModelData(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRuleSet,
				R4EUIModelController.getReviewer());
		fRuleSet.setVersion(((R4EDesignRuleCollection) aModelComponent).getVersion());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Create a serialization model element object
	 * 
	 * @return the new serialization element object
	 */
	@Override
	public List<ReviewComponent> createChildModelDataElement() {
		//Get Rule Area from user and set it in model data
		final List<ReviewComponent> tempAreas = new ArrayList<ReviewComponent>();

		final IRuleAreaInputDialog dialog = R4EUIDialogFactory.getInstance().getRuleAreaInputDialog();
		final int result = dialog.open();
		if (result == Window.OK) {
			final R4EDesignRuleArea tempArea = DRModelFactory.eINSTANCE.createR4EDesignRuleArea();
			tempArea.setName(dialog.getNameValue());
			tempAreas.add(tempArea);
		}
		return tempAreas;
	}

	//Hierarchy

	/**
	 * Method getChildren.
	 * 
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fAreas.toArray(new R4EUIRuleArea[fAreas.size()]);
	}

	/**
	 * Method getAreaList.
	 * 
	 * @return List<R4EUIRuleArea>
	 */
	public List<R4EUIRuleArea> getAreaList() {
		return fAreas;
	}

	/**
	 * Method hasChildren.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fAreas.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Close the model element
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIRuleArea area = null;
		final int areaSize = fAreas.size();
		for (int i = 0; i < areaSize; i++) {

			area = fAreas.get(i);
			area.close();
		}
		fAreas.clear();
		fOpen = false;
		fReadOnly = false;
		R4EUIModelController.FModelExt.closeR4EDesignRuleCollection(fRuleSet);
		fImage = UIUtils.loadIcon(RULE_SET_CLOSED_ICON_FILE);
	}

	/**
	 * Method open.
	 * 
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	@Override
	public void open() throws ResourceHandlingException, CompatibilityException {
		fRuleSet = R4EUIModelController.FModelExt.openR4EDesignRuleCollection(fRuleSetFileURI);
		if (checkCompatibility()) {
			final List<R4EDesignRuleArea> areas = fRuleSet.getAreas();
			if (null != areas) {
				R4EUIRuleArea uiArea = null;
				final int areaSize = areas.size();
				for (int i = 0; i < areaSize; i++) {
					if (areas.get(i).isEnabled()
							|| R4EUIPlugin.getDefault()
									.getPreferenceStore()
									.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
						uiArea = new R4EUIRuleArea(this, areas.get(i));
						addChildren(uiArea);
						if (uiArea.isEnabled()) {
							uiArea.open();
						}
					}
				}
			}
			fOpen = true;
			fImage = UIUtils.loadIcon(RULE_SET_ICON_FILE);
		} else {
			R4EUIModelController.FModelExt.closeR4EDesignRuleCollection(fRuleSet);
		}
	}

	/**
	 * Method openReadOnly.
	 * 
	 * @return boolean
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	public boolean openReadOnly() throws ResourceHandlingException, CompatibilityException {
		fRuleSet = R4EUIModelController.FModelExt.openR4EDesignRuleCollection(fRuleSetFileURI);
		final int checkResult = fRuleSet.getCompatibility();
		if (checkResult == R4EUIConstants.VERSION_APPLICATION_OLDER) {
			R4EUIModelController.FModelExt.closeR4EDesignRuleCollection(fRuleSet);
			return false;
		} else {
			fReadOnly = true;

			final List<R4EDesignRuleArea> areas = fRuleSet.getAreas();
			if (null != areas) {
				R4EUIRuleArea uiArea = null;
				final int areaSize = areas.size();
				for (int i = 0; i < areaSize; i++) {
					if (areas.get(i).isEnabled()
							|| R4EUIPlugin.getDefault()
									.getPreferenceStore()
									.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
						uiArea = new R4EUIRuleArea(this, areas.get(i));
						addChildren(uiArea);
						if (uiArea.isEnabled()) {
							uiArea.open();
						}
					}
				}
			}
			fOpen = true;
			fImage = UIUtils.loadIcon(RULE_SET_ICON_FILE);
			R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
			return true;
		}
	}

	/**
	 * Check version compatibility between the element(s) to load and the current R4E application
	 * 
	 * @return boolean
	 */
	private boolean checkCompatibility() {
		final int checkResult = fRuleSet.getCompatibility();
		switch (checkResult) {
		case R4EUIConstants.VERSION_APPLICATION_OLDER:
			UIUtils.displayCompatibilityErrorDialog();
			return false;
		case R4EUIConstants.VERSION_APPLICATION_NEWER:
			final int result = UIUtils.displayCompatibilityWarningDialog(fRuleSet.getFragmentVersion(),
					fRuleSet.getApplicationVersion());
			switch (result) {
			case R4EUIConstants.OPEN_NORMAL:
				//Upgrade version immediately
				try {
					final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRuleSet,
							R4EUIModelController.getReviewer());
					fRuleSet.setFragmentVersion(fRuleSet.getApplicationVersion());
					R4EUIModelController.FResourceUpdater.checkIn(bookNum);
				} catch (ResourceHandlingException e) {
					UIUtils.displayResourceErrorDialog(e);
					return false;
				} catch (OutOfSyncException e) {
					UIUtils.displaySyncErrorDialog(e);
					return false;
				}
				fReadOnly = false;
				return true;
			case R4EUIConstants.OPEN_READONLY:
				fReadOnly = true;
				return true;
			default:
				//Assume Cancel
				return false;
			}
		default:
			//Normal case, do nothing
			fReadOnly = false;
			return true;
		}
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
	@Override
	public void setEnabled(boolean aEnabled) throws ResourceHandlingException, OutOfSyncException,
			CompatibilityException {
		//NOTE we need to open the model element temporarly to be able to set the enabled state
		fRuleSet = R4EUIModelController.FModelExt.openR4EDesignRuleCollection(fRuleSetFileURI);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRuleSet,
				R4EUIModelController.getReviewer());
		fRuleSet.setEnabled(aEnabled);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return fRuleSet.isEnabled();
	}

	/**
	 * Method addChildren.
	 * 
	 * @param aChildToAdd
	 *            IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fAreas.add((R4EUIRuleArea) aChildToAdd);
	}

	/**
	 * Method createChildren
	 * 
	 * @param aModelComponent
	 *            - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildren(ReviewNavigatorContentProvider)
	 */
	@Override
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws ResourceHandlingException,
			OutOfSyncException {
		final R4EDesignRuleArea area = R4EUIModelController.FModelExt.createR4EDesignRuleArea(fRuleSet);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(area, R4EUIModelController.getReviewer());
		area.setName(((R4EDesignRuleArea) aModelComponent).getName());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		final R4EUIRuleArea addedChild = new R4EUIRuleArea(this, area);
		addChildren(addedChild);
		return addedChild;
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove)
			throws ResourceHandlingException, OutOfSyncException {

		final R4EUIRuleArea removedElement = fAreas.get(fAreas.indexOf(aChildToRemove));
		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getArea().remove());
		else */
		final R4EDesignRuleArea modelArea = removedElement.getArea();
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelArea,
				R4EUIModelController.getReviewer());
		modelArea.setEnabled(false);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		//Remove element from UI if the show disabled element option is off
		if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fAreas.remove(removedElement);
		}
	}

	/**
	 * Method removeAllChildren.
	 * 
	 * @param aFileRemove
	 *            boolean
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		//Recursively remove all children
		for (R4EUIRuleArea area : fAreas) {
			removeChildren(area, aFileRemove);
		}
	}

	//Listeners

/*	*//**
	 * Method addListener.
	 * 
	 * @param aProvider
	 *            ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	/*
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
	super.addListener(aProvider);
	if (null != fAreas) {
		R4EUIRuleArea element = null;
		for (final Iterator<R4EUIRuleArea> iterator = fAreas.iterator(); iterator.hasNext();) {
			element = iterator.next();
			element.addListener(aProvider);
		}
	}
	}

	*//**
	 * Method removeListener.
	 * 
	 * @param aProvider
	 *            ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeListener()
	 */
	/*
	@Override
	public void removeListener(ReviewNavigatorContentProvider aProvider) {
	super.removeListener(aProvider);
	if (null != fAreas) {
		R4EUIRuleArea element = null;
		for (final Iterator<R4EUIRuleArea> iterator = fAreas.iterator(); iterator.hasNext();) {
			element = iterator.next();
			element.removeListener(aProvider);
		}
	}
	}*/

	//Commands

	/**
	 * Method isOpenElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	@Override
	public boolean isOpenElementCmd() {
		if (!isEnabled() || isOpen()) {
			return false;
		}
		return true;
	}

	/**
	 * Method getOpenElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getOpenElementCmdName()
	 */
	@Override
	public String getOpenElementCmdName() {
		return OPEN_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getOpenElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getOpenElementCmdTooltip()
	 */
	@Override
	public String getOpenElementCmdTooltip() {
		return OPEN_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isCloseElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isCloseElementCmd()
	 */
	@Override
	public boolean isCloseElementCmd() {
		if (isEnabled() && isOpen()) {
			return true;
		}
		return false;
	}

	/**
	 * Method getCloseElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getCloseElementCmdName()
	 */
	@Override
	public String getCloseElementCmdName() {
		return CLOSE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getCloseElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getCloseElementCmdTooltip()
	 */
	@Override
	public String getCloseElementCmdTooltip() {
		return CLOSE_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isAddChildElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isNewChildElementCmd()
	 */
	@Override
	public boolean isNewChildElementCmd() {
		if (isEnabled() && isOpen() && !isReadOnly()) {
			return true;
		}
		return false;
	}

	/**
	 * Method getAddChildElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNewChildElementCmdName()
	 */
	@Override
	public String getNewChildElementCmdName() {
		return NEW_CHILD_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getAddChildElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getNewChildElementCmdTooltip()
	 */
	@Override
	public String getNewChildElementCmdTooltip() {
		return NEW_CHILD_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isRemoveElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		if (!isOpen() && isEnabled() && !isReadOnly()) {
			return true;
		}
		return false;
	}

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (isEnabled() || isReadOnly()) {
			return false;
		}
		return true;
	}

	/**
	 * Method getRemoveElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	@Override
	public String getRemoveElementCmdName() {
		return REMOVE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getRemoveElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	@Override
	public String getRemoveElementCmdTooltip() {
		return REMOVE_ELEMENT_COMMAND_TOOLTIP;
	}
}
