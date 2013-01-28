// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, com.instantiations.assist.eclipse.analysis.instanceFieldSecurity, com.instantiations.assist.eclipse.analysis.mutabilityOfArrays
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
 * This class implements the Review Group element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Jacques Bouthillier - Add method to read the group path file for Report
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.core.model.IReview;
import org.eclipse.mylyn.reviews.core.model.IReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhase;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewState;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IReviewInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.general.ReviewGroupProperties;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.mylyn.reviews.r4e.upgrade.ui.R4EUpgradeController;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIReviewGroup extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field REVIEW_GROUP_ICON_FILE. (value is ""icons/obj16/revgrp_obj.gif"")
	 */
	public static final String REVIEW_GROUP_ICON_FILE = "icons/obj16/revgrp_obj.gif";

	/**
	 * Field REVIEW_GROUP_CLOSED_ICON_FILE. (value is ""icons/obj16/revgrpclsd_obj.gif"")
	 */
	public static final String REVIEW_GROUP_CLOSED_ICON_FILE = "icons/obj16/revgrpclsd_obj.gif";

	/**
	 * Field NEW_CHILD_ELEMENT_COMMAND_NAME. (value is ""New Review..."")
	 */
	private static final String NEW_CHILD_ELEMENT_COMMAND_NAME = "New Review...";

	/**
	 * Field NEW_CHILD_ELEMENT_COMMAND_TOOLTIP. (value is ""Add a new review to the current review group"")
	 */
	private static final String NEW_CHILD_ELEMENT_COMMAND_TOOLTIP = "Add a New Review to the Current Review Group";

	/**
	 * Field OPEN_ELEMENT_COMMAND_NAME. (value is ""Open Review Group"")
	 */
	private static final String OPEN_ELEMENT_COMMAND_NAME = "Open Review Group";

	/**
	 * Field OPEN_ELEMENT_COMMAND_TOOLTIP. (value is ""Open and Load Data for this Review Group"")
	 */
	private static final String OPEN_ELEMENT_COMMAND_TOOLTIP = "Open and Load Data for this Review Group";

	/**
	 * Field CLOSE_ELEMENT_COMMAND_NAME. (value is ""Close Review Group"")
	 */
	private static final String CLOSE_ELEMENT_COMMAND_NAME = "Close Review Group";

	/**
	 * Field CLOSE_ELEMENT_COMMAND_TOOLTIP. (value is ""Close and Unload Data for this Review Group"")
	 */
	private static final String CLOSE_ELEMENT_COMMAND_TOOLTIP = "Close and Unload Data for this Review Group";

	/**
	 * Field REMOVE_ELEMENT_ACTION_NAME. (value is ""Disable Review Group"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_NAME = "Disable Review Group";

	/**
	 * Field REMOVE_ELEMENT_ACTION_TOOLTIP. (value is ""Disable this Review Group"")
	 */
	private static final String REMOVE_ELEMENT_COMMAND_TOOLTIP = "Disable this Review Group";

	/**
	 * Field RESTORE_ELEMENT_COMMAND_NAME. (value is ""Restore Review Group"")
	 */
	private static final String RESTORE_ELEMENT_COMMAND_NAME = "Restore Review Group";

	/**
	 * Field RESTORE_ELEMENT_ACTION_TOOLTIP. (value is ""Restore this disabled Review Group"")
	 */
	private static final String RESTORE_ELEMENT_COMMAND_TOOLTIP = "Restore this disabled Review Group";

	/**
	 * Field UNKNOWN_REVIEW_GROUP_TYPE_TOOLTIP. (value is ""Review Group Version Data is not Compatible with the current
	 * R4E Version. Open Group to Upgrade."")
	 */
	private static final String UNKNOWN_REVIEW_GROUP_TYPE_TOOLTIP = "Review Group Version Data is not Compatible with the current R4E Version."
			+ R4EUIConstants.LINE_FEED + "Open Group to Upgrade.";

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fGroup.
	 */
	protected R4EReviewGroup fGroup;

	/**
	 * Field fGroupFileURI.
	 */
	private URI fGroupFileURI = null;

	/**
	 * Field fReviews.
	 */
	private final List<R4EUIReview> fReviews;

	/**
	 * Field fRuleSets.
	 */
	private final List<R4EUIRuleSet> fRuleSets;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIReviewGroup.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aGroup
	 *            R4EReviewGroup
	 * @param aOpen
	 *            boolean
	 */
	public R4EUIReviewGroup(IR4EUIModelElement aParent, String aGroupPath, R4EReviewGroup aGroup, boolean aOpen) {
		super(aParent, extractGroupName(aGroupPath));
		fReadOnly = false;
		fGroup = aGroup;
		fGroupFileURI = URI.createFileURI(aGroupPath);
		if (null == fGroup) {
			fResolved = false;
		}
		fReviews = new ArrayList<R4EUIReview>();
		fRuleSets = new ArrayList<R4EUIRuleSet>();
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
			return REVIEW_GROUP_ICON_FILE;
		}
		return REVIEW_GROUP_CLOSED_ICON_FILE;
	}

	/**
	 * Method getToolTip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getToolTip()
	 */
	@Override
	public String getToolTip() {
		if (fResolved) {
			return R4EUIConstants.FILE_LOCATION_LABEL + URI.decode(fGroupFileURI.devicePath());
		}
		return UNKNOWN_REVIEW_GROUP_TYPE_TOOLTIP;
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
			if (fResolved) {
				return new ReviewGroupProperties(this);
			}
		}
		return null;
	}

	/**
	 * Method extractGroupName.
	 * 
	 * @param aGroupPath
	 *            String
	 * @return String
	 */
	private static String extractGroupName(String aGroupPath) {
		String separator = System.getProperty("file.separator");
		String tokens[] = aGroupPath.split(separator.equals("\\") ? "\\" + separator : separator);
		return (tokens[tokens.length - 1]).replaceFirst(R4EUIConstants.GROUP_FILE_SUFFIX, "");
	}

	//Attributes

	/**
	 * Method getReviewGroup.
	 * 
	 * @return R4EReviewGroup
	 */
	public R4EReviewGroup getReviewGroup() {
		return fGroup;
	}

	/**
	 * Method getGroupFile.
	 * 
	 * @return R4EReviewGroup
	 */
	public String getGroupFile() {
		return fGroupFileURI.toFileString();
	}

	/**
	 * Method getRuleSets.
	 * 
	 * @return List<R4EUIRuleSet>
	 */
	public List<R4EUIRuleSet> getRuleSets() {
		return fRuleSets;
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
	public void setModelData(IReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		//Set data in model element
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fGroup, R4EUIModelController.getReviewer());
		fGroup.setDescription(((R4EReviewGroup) aModelComponent).getDescription());
		fGroup.getAvailableProjects().addAll(((R4EReviewGroup) aModelComponent).getAvailableProjects());
		fGroup.getAvailableComponents().addAll(((R4EReviewGroup) aModelComponent).getAvailableComponents());
		fGroup.setDefaultEntryCriteria(((R4EReviewGroup) aModelComponent).getDefaultEntryCriteria());
		fGroup.getDesignRuleLocations().addAll(((R4EReviewGroup) aModelComponent).getDesignRuleLocations());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Create a serialization model element object
	 * 
	 * @return the new serialization element object
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildModelDataElement()
	 */
	@Override
	public List<IReviewComponent> createChildModelDataElement() {
		//Get comment from user and set it in model data
		final List<IReviewComponent> tempReviews = new ArrayList<IReviewComponent>();

		final IReviewInputDialog dialog = R4EUIDialogFactory.getInstance().getReviewInputDialog(this);
		dialog.create();
		final int result = dialog.open();
		if (result == Window.OK) {
			R4EReview tempReview;

			//All reviews
			final R4EReviewType type = dialog.getReviewTypeValue();
			if (type.equals(R4EReviewType.FORMAL)) {
				tempReview = RModelFactory.eINSTANCE.createR4EFormalReview();
			} else {
				tempReview = RModelFactory.eINSTANCE.createR4EReview();
			}
			tempReview.setType(type);
			tempReview.setName(dialog.getReviewNameValue());
			tempReview.setExtraNotes(dialog.getReviewDescriptionValue());
			tempReview.setDueDate(dialog.getDueDate());
			tempReview.setProject(dialog.getProjectValue());
			for (String component : dialog.getComponentsValues()) {
				tempReview.getComponents().add(component);
			}
			tempReview.setEntryCriteria(dialog.getEntryCriteriaValue());
			tempReview.setObjectives(dialog.getObjectivesValue());
			tempReview.setReferenceMaterial(dialog.getReferenceMaterialValue());
			//Set default exit decision for INFORMAL review
			final R4EReviewDecision reviewDecision = RModelFactoryExt.eINSTANCE.createR4EReviewDecision();
			if (type.equals(R4EReviewType.INFORMAL)) {
				reviewDecision.setValue(R4EDecision.ACCEPTED);
			} else {
				reviewDecision.setValue(R4EDecision.NONE);
			}

			tempReview.setDecision(reviewDecision);
			tempReviews.add(tempReview);
		}
		return tempReviews;
	}

	/**
	 * Close the model element (i.e. disable it)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIReview review = null;
		final int reviewsSize = fReviews.size();
		for (int i = 0; i < reviewsSize; i++) {

			review = fReviews.get(i);
			if (!review.isOpen()) {
				continue; //skip reviews that are already closed
			}
			review.close();
		}
		fReviews.clear();
		fRuleSets.clear();
		fOpen = false;
		fReadOnly = false;
		if (null != fGroup) {
			R4EUIModelController.FModelExt.closeR4EReviewGroup(fGroup); //Notify model
		}
		fImage = UIUtils.loadIcon(REVIEW_GROUP_CLOSED_ICON_FILE);
	}

	/**
	 * Open the model element (i.e. enable it)
	 * 
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#open()
	 */
	@Override
	public void open() throws ResourceHandlingException, CompatibilityException {
		String newVersion = Persistence.Roots.GROUP.getVersion();
		String oldVersion;

		try {
			oldVersion = R4EUpgradeController.getVersionFromResourceFile(fGroupFileURI);
		} catch (IOException e1) {
			throw new ResourceHandlingException("Cannot find Review Group resource file " + fGroupFileURI, e1);
		}

		if (checkCompatibility(fGroupFileURI, R4EUIConstants.REVIEW_GROUP_LABEL + " "
				+ extractGroupName(getGroupFile()), oldVersion, newVersion, false)) {
			fGroup = R4EUIModelController.FModelExt.openR4EReviewGroup(fGroupFileURI);
			fResolved = true;

			//Stamp new version if an upgrade took place
			if (!oldVersion.equals(newVersion)) {
				try {
					R4EUIModelController.stampVersion(fGroup, R4EUIModelController.getReviewer(), newVersion);
				} catch (OutOfSyncException e) {
					R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					R4EUIModelController.FModelExt.closeR4EReviewGroup(fGroup);
					return;
				}
			}

			final List<IReview> reviews = fGroup.getReviews();
			if (null != reviews) {
				final int reviewsSize = reviews.size();
				R4EReview review = null;
				R4EUIReview uiReview = null;

				for (int i = 0; i < reviewsSize; i++) {
					review = (R4EReview) reviews.get(i);
					if (review.isEnabled()
							|| R4EUIPlugin.getDefault()
									.getPreferenceStore()
									.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
						uiReview = null;

						//Here we need to check if the review has a compatible version.  Otherwise, it will be displayed as an unknown type
						if (checkChildReviewCompatibility(review)) {
							if (review.getType().equals(R4EReviewType.FORMAL)) {
								uiReview = new R4EUIReviewExtended(this, review, review.getType(), false);
								((R4EUIReviewExtended) uiReview).setName(((R4EUIReviewExtended) uiReview).getPhaseString(((R4EReviewState) review.getState()).getState())
										+ ": " + uiReview.getName());
							} else {
								uiReview = new R4EUIReviewBasic(this, review, review.getType(), false);
							}
						} else {
							uiReview = new R4EUIReview(this, review, false);
						}
						addChildren(uiReview);
					}
				}
			}

			//Add Rule Sets
			final List<String> ruleSetlocations = fGroup.getDesignRuleLocations();
			for (String ruleSetlocation : ruleSetlocations) {
				for (R4EUIRuleSet ruleSet : ((R4EUIRootElement) getParent()).getRuleSets()) {
					String path = R4EUIRuleSet.extractRuleSetName(ruleSet.getRuleSetFile());
					if (path.equals(ruleSetlocation)) {
						fRuleSets.add(ruleSet);
						break;
					}
				}
			}
			fOpen = true;
			fImage = UIUtils.loadIcon(REVIEW_GROUP_ICON_FILE);
		} else {
			close();
		}
	}

	/**
	 * Method checkChildReviewCompatibility
	 * 
	 * @param aReview
	 *            - aReview
	 * @return boolean
	 * @throws ResourceHandlingException
	 */
	public boolean checkChildReviewCompatibility(R4EReview aReview) throws ResourceHandlingException {
		String newVersion = Persistence.Roots.REVIEW.getVersion();
		String validReviewName = ResourceUtils.toValidFileName(aReview.getName());
		URI upgradeRootUri = URI.createFileURI(getReviewGroup().getFolder() + R4EUIConstants.SEPARATOR
				+ validReviewName);
		URI reviewResourceUri = upgradeRootUri.appendSegment(validReviewName + R4EUIConstants.REVIEW_FILE_SUFFIX);
		String oldVersion;
		try {
			oldVersion = R4EUpgradeController.getVersionFromResourceFile(reviewResourceUri);
		} catch (IOException e1) {
			throw new ResourceHandlingException("Cannot find Review Group resource file " + reviewResourceUri, e1);
		}

		try {
			return R4EUpgradeController.upgradeCompatibilityCheck(reviewResourceUri, oldVersion, newVersion);
		} catch (IOException e) {
			R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			return false;
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
		fGroup = R4EUIModelController.FModelExt.openR4EReviewGroup(fGroupFileURI);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fGroup, R4EUIModelController.getReviewer());
		fGroup.setEnabled(aEnabled);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
		R4EUIModelController.FModelExt.closeR4EReviewGroup(fGroup);
	}

	/**
	 * Method isEnabled.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		if (fResolved) {
			return fGroup.isEnabled();
		}
		return true;
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
		return fReviews.toArray(new R4EUIReview[fReviews.size()]);
	}

	/**
	 * Method replaceReview.
	 * 
	 * @param aOldReview
	 *            - R4EUIReview
	 * @param aNewReview
	 *            - R4EUIReview
	 */
	public void replaceReview(R4EUIReview aOldReview, R4EUIReview aNewReview) {
		fReviews.set(fReviews.indexOf(aOldReview), aNewReview);
	}

	/**
	 * Method getReview.
	 * 
	 * @param aReviewName
	 *            - String
	 * @return R4EUIReview
	 */
	public R4EUIReview getReview(String aReviewName) {
		for (R4EUIReview review : fReviews) {
			if (null != review.getReview() && aReviewName.equals(review.getReview().getName())) {
				return review;
			}
		}
		return null;
	}

	/**
	 * Method hasChildren
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (isOpen()) {
			if (fReviews.size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method createChildren.
	 * 
	 * @param aModelComponent
	 *            - the serialization model component object
	 * @return IR4EUIModelElement
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#createChildren(R4EReviewComponent)
	 */
	@Override
	public IR4EUIModelElement createChildren(IReviewComponent aModelComponent) throws ResourceHandlingException,
			OutOfSyncException {

		final String reviewName = ((R4EReview) aModelComponent).getName();
		final R4EReviewType type = ((R4EReview) aModelComponent).getType();

		//Check if review already exists.  If so it cannot be recreated
		for (R4EUIReview review : fReviews) {
			if (review.getReview().getName().equals(reviewName)) {
				final ErrorDialog dialog = new ErrorDialog(null, R4EUIConstants.DIALOG_TITLE_ERROR,
						"Error while creating new review ", new Status(IStatus.ERROR, R4EUIPlugin.PLUGIN_ID, 0,
								"Review " + reviewName + " already exists", null), IStatus.ERROR);
				Display.getDefault().syncExec(new Runnable() {
					public void run() {
						dialog.open();
					}
				});
				return null;
			}
		}

		final R4EUIReviewBasic addedChild;
		if (type.equals(R4EReviewType.FORMAL)) {
			addedChild = new R4EUIReviewExtended(this, R4EUIModelController.FModelExt.createR4EFormalReview(
					getReviewGroup(), reviewName, R4EUIModelController.getReviewer()), type, true);
			((R4EUIReviewExtended) addedChild).updatePhase(R4EReviewPhase.STARTED);
		} else {
			addedChild = new R4EUIReviewBasic(this, R4EUIModelController.FModelExt.createR4EReview(getReviewGroup(),
					reviewName, R4EUIModelController.getReviewer()), type, true);
			addedChild.updatePhase(R4EReviewPhase.STARTED);
		}
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);
		return addedChild;
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
		fReviews.add((R4EUIReview) aChildToAdd);
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
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove, boolean aFileRemove)
			throws ResourceHandlingException, OutOfSyncException, CompatibilityException {
		//This was the current review, so tell the controller that no review is now active
		if (((R4EUIReviewBasic) aChildToRemove).isOpen()) {
			R4EUIModelController.setActiveReview(null);
		}

		final R4EUIReview removedElement = fReviews.get(fReviews.indexOf(aChildToRemove));

		//Also recursively remove all children 
		removedElement.removeAllChildren(aFileRemove);

		/* TODO uncomment when core model supports hard-removing of elements
		if (aFileRemove) removedElement.getReview().remove());
		else */
		removedElement.setEnabled(false);

		//Remove element from UI if the show disabled element option is off
		if (!(R4EUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_SHOW_DISABLED))) {
			fReviews.remove(removedElement);
		}
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
	@Override
	public void removeAllChildren(boolean aFileRemove) throws ResourceHandlingException, OutOfSyncException,
			CompatibilityException {
		//Recursively remove all children
		for (R4EUIReview review : fReviews) {
			removeChildren(review, aFileRemove);
		}
	}

	//Commands

	/**
	 * Method isOpenElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isOpenElementCmd()
	 */
	@Override
	public boolean isOpenElementCmd() {
		if (fResolved && (!isEnabled() || isOpen())) {
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
		if (fResolved && isEnabled() && isOpen()) {
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
		if (fResolved && isEnabled() && isOpen() && !isReadOnly()) {
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
		if (fResolved && !isOpen() && isEnabled() && !isReadOnly()) {
			return true;
		}
		return false;
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

	/**
	 * Method isRestoreElementCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#iisRestoreElementCmd()
	 */
	@Override
	public boolean isRestoreElementCmd() {
		if (fResolved && isOpen() || isEnabled() || isReadOnly()) {
			return false;
		}
		return true;
	}

	/**
	 * Method getRestoreElementCmdName.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdName()
	 */
	@Override
	public String getRestoreElementCmdName() {
		return RESTORE_ELEMENT_COMMAND_NAME;
	}

	/**
	 * Method getRestoreElementCmdTooltip.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getRestoreElementCmdTooltip()
	 */
	@Override
	public String getRestoreElementCmdTooltip() {
		return RESTORE_ELEMENT_COMMAND_TOOLTIP;
	}

	/**
	 * Method isShowPropertiesCmd.
	 * 
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#isShowPropertiesCmd()
	 */
	@Override
	public boolean isShowPropertiesCmd() {
		return fResolved;
	}
}
