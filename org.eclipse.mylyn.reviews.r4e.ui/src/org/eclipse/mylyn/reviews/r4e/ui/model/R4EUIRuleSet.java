// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
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

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.DRModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleArea;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.RuleAreaInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.properties.general.RuleSetProperties;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIRuleSet extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field RULE_SET_ICON_FILE.
	 * (value is ""icons/obj16/ruleset_obj.gif"")
	 */
	private static final String RULE_SET_ICON_FILE = "icons/obj16/ruleset_obj.gif";
	
	/**
	 * Field RULE_SET_CLOSED_ICON_FILE.
	 * (value is ""icons/obj16/rulesetclsd_obj.gif"")
	 */
	private static final String RULE_SET_CLOSED_ICON_FILE = "icons/obj16/rulesetclsd_obj.gif";
	
	/**
	 * Field ADD_CHILD_ELEMENT_COMMAND_NAME.
	 * (value is ""Add Rule Area"")
	 */
	private static final String ADD_CHILD_ELEMENT_COMMAND_NAME = "Add Rule Area";
	
    /**
     * Field ADD_CHILD_ELEMENT_COMMAND_TOOLTIP.
     * (value is ""Add a New Rule Area to the Current Rule Set"")
     */
    private static final String ADD_CHILD_ELEMENT_COMMAND_TOOLTIP = "Add a New Rule Area to the Current Rule Set";

	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME.
	 * (value is ""Disable Rule Set"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Rule Set";
	
    /**
     * Field REMOVE_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Disable (and Optionally Remove) this Rule Set"")
     */
    private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable (and Optionally Remove) this Rule Set";
    
    
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
	 * @param aParent IR4EUIModelElement
	 * @param aRuleSet R4EDesignRuleCollection
	 * @param aOpen boolean
	 */
	public R4EUIRuleSet(IR4EUIModelElement aParent, R4EDesignRuleCollection aRuleSet, boolean aOpen) {
		super(aParent, aRuleSet.getName(), R4EUIConstants.FILE_LOCATION_LABEL + aRuleSet.eResource().getURI().devicePath());
		fRuleSet = aRuleSet;
		fRuleSetFileURI = aRuleSet.eResource().getURI();
		fAreas = new ArrayList<R4EUIRuleArea>();
		if (aOpen) {
			setImage(RULE_SET_ICON_FILE);
			fOpen = true;
		} else {
			setImage(RULE_SET_CLOSED_ICON_FILE);
			fOpen = false;
		}
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
		if (IPropertySource.class.equals(adapter)) return new RuleSetProperties(this);
		return null;
	}
	
	//Attributes
	
	/**
	 * Method getRuleSet.
	 * @return R4EDesignRuleCollection
	 */
	public R4EDesignRuleCollection getRuleSet() {
		return fRuleSet;
	}
	
	/**
	 * Set serialization model data by copying it from the passed-in object
	 * @param aModelComponent - a serialization model element to copy information from
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#setModelData(R4EReviewComponent)
	 */
	@Override
	public void setModelData(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
    	//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRuleSet, R4EUIModelController.getReviewer());
		fRuleSet.setVersion(((R4EDesignRuleCollection)aModelComponent).getVersion());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
    }
	
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 */
	@Override
	public ReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EDesignRuleArea tempArea = null;
		R4EUIModelController.setDialogOpen(true);
		final RuleAreaInputDialog dialog = new RuleAreaInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell());
    	final int result = dialog.open();
    	if (result == Window.OK) {
    		tempArea = DRModelFactory.eINSTANCE.createR4EDesignRuleArea();
    		tempArea.setName(dialog.getNameValue());
    	}
    	// else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
    	return tempArea;
	}
	
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() {
		return fAreas.toArray(new R4EUIRuleArea[fAreas.size()]);
	}
	
	/**
	 * Method getAreaList.
	 * @return List<R4EUIRuleArea>
	 */
	public List<R4EUIRuleArea> getAreaList() {
		return fAreas;
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fAreas.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
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
		R4EUIModelController.FModelExt.closeR4EDesignRuleCollection(fRuleSet);
		fImage = UIUtils.loadIcon(RULE_SET_CLOSED_ICON_FILE);
		fireReviewStateChanged(this);
	}
	
	/**
	 * Method open.
	 * @throws ResourceHandlingException 
	 */
	@Override
	public void open() throws ResourceHandlingException {
		fRuleSet = R4EUIModelController.FModelExt.openR4EDesignRuleCollection(fRuleSetFileURI);
		final List<R4EDesignRuleArea> areas = fRuleSet.getAreas();
		if (null != areas) {
			R4EUIRuleArea uiArea = null;
			final int areaSize = areas.size();
			for (int i = 0; i < areaSize; i++) {
				uiArea = new R4EUIRuleArea(this, areas.get(i));
				addChildren(uiArea);
				if (uiArea.isEnabled()) uiArea.open();	
			}
		}
		fOpen = true;
		fImage = UIUtils.loadIcon(RULE_SET_ICON_FILE);
		fireReviewStateChanged(this);
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
		//NOTE we need to open the model element temporarly to be able to set the enabled state
		fRuleSet = R4EUIModelController.FModelExt.openR4EDesignRuleCollection(fRuleSetFileURI);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fRuleSet, R4EUIModelController.getReviewer());
		fRuleSet.setEnabled(aEnabled);
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
		return fRuleSet.isEnabled();
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fAreas.add((R4EUIRuleArea) aChildToAdd);
		aChildToAdd.addListener((ReviewNavigatorContentProvider) R4EUIModelController.getNavigatorView().getTreeViewer().getContentProvider());
		fireAdd(aChildToAdd);
	}

	/**
	 * Method createChildren
	 * @param aModelComponent - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createChildren(ReviewNavigatorContentProvider)
	 */
	@Override
	public IR4EUIModelElement createChildren(ReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		final R4EDesignRuleArea area = R4EUIModelController.FModelExt.createR4EDesignRuleArea(fRuleSet);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(area, R4EUIModelController.getReviewer());
		area.setName(((R4EDesignRuleArea)aModelComponent).getName());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		final R4EUIRuleArea addedChild = new R4EUIRuleArea(this, area);
		addChildren(addedChild);
		return addedChild;
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
		
		final R4EUIRuleArea removedElement = fAreas.get(fAreas.indexOf(aChildToRemove));
		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getArea().remove());
		else */
		final R4EDesignRuleArea modelArea = removedElement.getArea();
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(modelArea, R4EUIModelController.getReviewer());
		modelArea.setEnabled(false);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		
		//Remove element from UI if the show disabled element option is off
		if (!(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fAreas.remove(removedElement);
			aChildToRemove.removeListeners();
			fireRemove(aChildToRemove);
		} else {
			R4EUIModelController.getNavigatorView().getTreeViewer().refresh();
		}
	}
	
	/**
	 * Method removeAllChildren.
	 * @param aFileRemove boolean
	 * @throws OutOfSyncException 
	 * @throws ResourceHandlingException 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeAllChildren(boolean)
	 */
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException {
		//Recursively remove all children
		for (R4EUIRuleArea area : fAreas) {
			removeChildren(area, aFileRemove);
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
		super.addListener(aProvider);
		if (null != fAreas) {
			R4EUIRuleArea element = null;
			for (final Iterator<R4EUIRuleArea> iterator = fAreas.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.addListener(aProvider);
			}
		}
	}

	/**
	 * Method removeListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
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
	}
	
	//Commands
	
	/**
	 * Method isOpenElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	@Override
	public boolean isOpenElementCmd() {
		if (!isEnabled() || isOpen()) return false;
		return true;
	}
	
	/**
	 * Method isCloseElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isCloseElementCmd()
	 */
	@Override
	public boolean isCloseElementCmd() {
		if (isEnabled() && isOpen()) return true;
		return false;
	}
	
	/**
	 * Method isAddChildElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isAddChildElementCmd()
	 */
	@Override
	public boolean isAddChildElementCmd() {
		if (isEnabled() && isOpen()) return true;
		return false;
	}
	
	/**
	 * Method getAddChildElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdName()
	 */
	@Override
	public String getAddChildElementCmdName() {
		return ADD_CHILD_ELEMENT_COMMAND_NAME;
	}
	
	/**
	 * Method getAddChildElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getAddChildElementCmdTooltip()
	 */
	@Override
	public String getAddChildElementCmdTooltip() {
		return ADD_CHILD_ELEMENT_COMMAND_TOOLTIP; 
	}
	
	/**
	 * Method isRemoveElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#isRemoveElementCmd()
	 */
	@Override
	public boolean isRemoveElementCmd() {
		if (!isOpen() && isEnabled()) return true;
		return false;
	}
	
	/**
	 * Method isRestoreElementCmd.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (isEnabled()) return false;
		return true;
	}
	
	/**
	 * Method getRemoveElementCmdName.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getRemoveElementCmdName()
	 */
	@Override
	public String getRemoveElementCmdName() {
		return REMOVE_ELEMENT_COMMAND_NAME;
	}
	
	/**
	 * Method getRemoveElementCmdTooltip.
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getRemoveElementCmdTooltip()
	 */
	@Override
	public String getRemoveElementCmdTooltip() {
		return REMOVE_ELEMENT_COMMAND_TOOLTIP;
	}
}
