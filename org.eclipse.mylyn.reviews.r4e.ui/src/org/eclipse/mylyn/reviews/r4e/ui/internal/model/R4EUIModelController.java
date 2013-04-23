// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the model controller.  It is used to control the UI 
 * model data
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   Jacques Bouthillier - Add method definition for Report package availability
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.mylyn.reviews.frame.core.model.Location;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewComponent;
import org.eclipse.mylyn.reviews.frame.core.model.SubModelRoot;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.ldap.LdapPlugin;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.drules.R4EDesignRuleCollection;
import org.eclipse.mylyn.reviews.r4e.core.model.impl.R4EItemImpl;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.commands.ReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorActionGroup;
import org.eclipse.mylyn.reviews.r4e.ui.internal.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.properties.tabbed.ModelElementTabPropertySection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IEvaluationService;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIModelController {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fRootElement.
	 */
	private static R4EUIRootElement FRootElement = null;

	/**
	 * Field FIsDialogOpen.
	 */
	private static boolean FIsDialogOpen = false;

	/**
	 * Field fActiveReview.
	 */
	private static R4EUIReviewBasic FActiveReview = null;

	/**
	 * Field FReviewSourceProvider.
	 */
	private static ReviewState FReviewSourceProvider = null;

	/**
	 * Field fView.
	 */
	private static ReviewNavigatorView FView = null;

	/**
	 * Field FReviewer.
	 */
	private static String FReviewer = null;

	/**
	 * Field modelExt.
	 */
	public static RModelFactoryExt FModelExt; //Reference to the EMF serialization model

	/**
	 * Field resourceUpdater.
	 */
	public static ResourceUpdater FResourceUpdater; //Reference to the EMF serialization model

	/**
	 * Field fFileAnomalyMap. File version to Anomaly List map
	 */
	private static final Map<String, List<R4EAnomaly>> FFileAnomalyMap = new HashMap<String, List<R4EAnomaly>>(32,
			0.75f); // $codepro.audit.disable constantNamingConvention

	/**
	 * Field fFocusElement.
	 */
	private static IR4EUIModelElement FFocusElement = null;

	/**
	 * Field FCurrentPropertySection.
	 */
	private static ModelElementTabPropertySection FCurrentPropertySection = null;

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method setNavigatorView.
	 * 
	 * @param aView
	 *            ReviewNavigatorView
	 */
	public static void setNavigatorView(ReviewNavigatorView aView) {
		FView = aView;
	}

	/**
	 * Method getNavigatorView.
	 * 
	 * @return ReviewNavigatorView
	 */
	public static ReviewNavigatorView getNavigatorView() {
		return FView;
	}

	/**
	 * Method setReviewCommandSourceProvider.
	 * 
	 * @param aSourceProvider
	 *            ReviewState
	 */
	public static void setReviewCommandSourceProvider(ReviewState aSourceProvider) {
		FReviewSourceProvider = aSourceProvider;
	}

	/**
	 * Get the model root element
	 * 
	 * @return the root R4E element
	 */
	public static R4EUIRootElement getRootElement() {
		return FRootElement;
	}

	/**
	 * Set the currently active review
	 * 
	 * @param aActiveReview
	 *            ReviewElement - the currently open ReviewElement
	 */
	public static void setActiveReview(R4EUIReviewBasic aActiveReview) {
		FActiveReview = aActiveReview;
		if (null != FReviewSourceProvider) {
			FReviewSourceProvider.setCurrentReview(FActiveReview);
		}
		//check to apply filters
		final ReviewNavigatorActionGroup actionGroup = (ReviewNavigatorActionGroup) FView.getActionSet();
		if (actionGroup.isMyReviewFilterSet()) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						actionGroup.runReviewsMyFilterCommand(true);
					} catch (ExecutionException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotDefinedException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotEnabledException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotHandledException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					}
				}
			});
		}
		if (actionGroup.isParticipantFilterSet()) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						actionGroup.runReviewsParticipantFilterCommand(actionGroup.getReviewFilterParticipant());
					} catch (ExecutionException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotDefinedException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotEnabledException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					} catch (NotHandledException e) {
						R4EUIPlugin.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
						R4EUIPlugin.getDefault().logError("Exception: " + e.toString(), e);
					}
				}
			});
		}
	}

	/**
	 * Set current dialog state
	 * 
	 * @param aIsDialogOpen
	 *            boolean
	 */
	public static void setJobInProgress(final boolean aIsDialogOpen) {
		FIsDialogOpen = aIsDialogOpen;
		final IEvaluationService service = (IEvaluationService) FView.getSite().getService(IEvaluationService.class);

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				service.requestEvaluation(R4EUIConstants.JOB_IN_PROGRESS_COMMAND);
				R4EUIModelController.getNavigatorView().refreshItems();
			}
		});
	}

	/**
	 * Get the currently active review
	 * 
	 * @return the currently open ReviewElement
	 */
	public static R4EUIReviewBasic getActiveReview() {
		return FActiveReview;
	}

	/**
	 * Get the current dialog state
	 * 
	 * @return the current dialog state
	 */
	public static boolean isJobInProgress() {
		return FIsDialogOpen;
	}

	/**
	 * Method peekReviewGroup.
	 * 
	 * @param aFilePath
	 *            String
	 * @return R4EReviewGroup
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	public static R4EReviewGroup peekReviewGroup(String aFilePath) throws ResourceHandlingException,
			CompatibilityException {
		FModelExt = SerializeFactory.getModelExtension();
		return FModelExt.openR4EReviewGroup(URI.createFileURI(aFilePath));
	}

	/**
	 * Method peekRuleSet.
	 * 
	 * @param aFilePath
	 *            String
	 * @return R4EDesignRuleCollection
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	public static R4EDesignRuleCollection peekRuleSet(String aFilePath) throws ResourceHandlingException,
			CompatibilityException {
		FModelExt = SerializeFactory.getModelExtension();
		return FModelExt.openR4EDesignRuleCollection(URI.createFileURI(aFilePath));
	}

	/**
	 * Method loadModel. Load data from Serialized Model
	 */
	public static void loadModel() {
		FModelExt = SerializeFactory.getModelExtension();
		FResourceUpdater = SerializeFactory.getResourceUpdater();

		//Set current user as reviewer
		setReviewer(R4EUIPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER_ID));

		//Add or Reset Root element in Review Navigator
		FRootElement = new R4EUIRootElement(null, "R4E");

		//Load Review Elements
		List<String> loadErrors = new ArrayList<String>();
		final IPreferenceStore preferenceStore = R4EUIPlugin.getDefault().getPreferenceStore();
		final String groupPaths = preferenceStore.getString(PreferenceConstants.P_GROUP_FILE_PATH);
		final List<String> groupPathsList = UIUtils.parseStringList(groupPaths);
		loadErrors = loadReviewGroups(groupPathsList, loadErrors);
		final String ruleSetPaths = preferenceStore.getString(PreferenceConstants.P_RULE_SET_FILE_PATH);
		final List<String> ruleSetPathsList = UIUtils.parseStringList(ruleSetPaths);
		loadErrors = loadRuleSets(ruleSetPathsList, loadErrors);

		//Report elements that failed to load
		if (loadErrors.size() > 0) {
			UIUtils.displayFailedLoadDialog(loadErrors);
		}
	}

	/**
	 * Method loadReviewGroups.
	 * 
	 * @param aGroupPaths
	 *            List<String>
	 * @param aErrors
	 *            List<String>
	 * @return List<String>
	 */
	public static List<String> loadReviewGroups(List<String> aGroupPaths, List<String> aErrors) {

		for (String groupPath : aGroupPaths) {
			//Open the review group file as entered in the preferences
			try {

				//Check if the file exists.  If not flag it here
				File groupFile = new File(groupPath);
				if (!groupFile.exists()) {
					aErrors.add("Invalid Group Path " + groupPath + R4EUIConstants.LINE_FEED);
				} else {
					FRootElement.loadReviewGroup(groupPath);
				}
			} catch (ResourceHandlingException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				aErrors.add(e.getMessage() + R4EUIConstants.LINE_FEED);
			} catch (CompatibilityException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				aErrors.add(e.getMessage() + R4EUIConstants.LINE_FEED);
			} catch (IOException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				aErrors.add(e.getMessage() + R4EUIConstants.LINE_FEED);
			}
		}
		return aErrors;
	}

	/**
	 * Method loadRuleSets.
	 * 
	 * @param aRuleSetPaths
	 *            List<String>
	 * @param aErrors
	 *            List<String>
	 * @return List<String>
	 */
	public static List<String> loadRuleSets(List<String> aRuleSetPaths, List<String> aErrors) {

		for (String ruleSetPath : aRuleSetPaths) {

			//Open the rule set file as entered in the preferences
			try {

				//Check if the file exists.  If not flag it here
				File ruleSetFile = new File(ruleSetPath);
				if (!ruleSetFile.exists()) {
					aErrors.add("Invalid Ruleset Path " + ruleSetPath + R4EUIConstants.LINE_FEED);
				} else {
					FRootElement.loadRuleSet(ruleSetPath);
				}
			} catch (ResourceHandlingException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				aErrors.add(e.getMessage() + R4EUIConstants.LINE_FEED);
			} catch (CompatibilityException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				aErrors.add(e.getMessage() + R4EUIConstants.LINE_FEED);
			} catch (IOException e) {
				R4EUIPlugin.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				aErrors.add(e.getMessage() + R4EUIConstants.LINE_FEED);
			}
		}
		return aErrors;
	}

	/**
	 * Method stampVersion.
	 * 
	 * @param aReviewComponent
	 *            IReviewComponent
	 * @param aUpdater
	 *            String
	 * @param aNewVersion
	 *            String
	 * @return R4EDesignRuleCollection
	 */
	public static void stampVersion(ReviewComponent aReviewComponent, String aUpdater, String aNewVersion)
			throws ResourceHandlingException, OutOfSyncException {
		if (aReviewComponent instanceof SubModelRoot) {
			Long bookNum;
			bookNum = SerializeFactory.getResourceUpdater().checkOut(aReviewComponent, aUpdater);
			((SubModelRoot) aReviewComponent).setFragmentVersion(aNewVersion);
			SerializeFactory.getResourceUpdater().checkIn(bookNum);
		}
	}

	/**
	 * Method mapAnomalies. Map anomalies to the files in which they are located
	 * 
	 * @param aReview
	 *            R4EReview
	 */
	public static void mapAnomalies(R4EReview aReview) {
		clearAnomalyMap(); //Start with a clean map 
		final EList<Topic> anomalies = aReview.getTopics();
		Topic anomaly = null;
		EList<Location> locations = null;
		String targetFileVersion = null;
		final int anomaliesSize = anomalies.size();
		for (int i = 0; i < anomaliesSize; i++) {
			anomaly = anomalies.get(i);

			locations = anomaly.getLocation();
			for (Location location : locations) {
				targetFileVersion = ((R4EAnomalyTextPosition) ((R4EContent) location).getLocation()).getFile()
						.getLocalVersionID();
				if (FFileAnomalyMap.containsKey(targetFileVersion)) {
					//Add anomaly for this file version if not already present
					List<R4EAnomaly> anomalyList = FFileAnomalyMap.get(targetFileVersion); // $codepro.audit.disable variableDeclaredInLoop
					if (!(anomalyList.contains(anomaly))) {
						anomalyList.add((R4EAnomaly) anomaly);
					}
				} else {
					List<R4EAnomaly> anomalyList = new ArrayList<R4EAnomaly>(); // $codepro.audit.disable variableDeclaredInLoop
					anomalyList.add((R4EAnomaly) anomaly);
					FFileAnomalyMap.put(targetFileVersion, anomalyList);
				}
			}

		}
	}

	/**
	 * Method clearAnomalyMap.
	 */
	public static void clearAnomalyMap() {
		final Set<String> keys = FFileAnomalyMap.keySet();
		for (String key : keys) {
			List<R4EAnomaly> anomaliesList = FFileAnomalyMap.get(key);
			anomaliesList.clear();
		}
		FFileAnomalyMap.clear();
	}

	/**
	 * Method getAnomaliesForFile. Get list of anomalies for a given file
	 * 
	 * @param aFilePath
	 *            String
	 * @return List<R4EAnomaly>
	 */
	public static List<R4EAnomaly> getAnomaliesForFile(String aFilePath) {
		return FFileAnomalyMap.get(aFilePath);
	}

	/**
	 * Method getAnomaliesForFileIfMostRecent. The goal of the method is to return anomalies to the given fileContext
	 * only when this context is associated to the most recent (latest) commit.
	 * 
	 * @param aFileSelected
	 *            R4EFileContext
	 * @param aSingleReviewItem
	 *            R4EUIReviewItem
	 * @return List<R4EAnomaly>
	 */
	public static List<R4EAnomaly> getAnomaliesForFileIfMostRecent(R4EFileContext aFileSelected,
			R4EUIReviewItem aSingleReviewItem) {
		//Adjust the selected file version
		String selectedFileVersionId = aFileSelected.getTarget().getLocalVersionID();
		R4EItemImpl reviewItem = (R4EItemImpl) aFileSelected.eContainer();
		Date dateSelected = reviewItem.getSubmitted();

		//Test if there  is a date in the commit
		//For the resource, the date submitted is currently null
		if (null == dateSelected) {
			return FFileAnomalyMap.get(selectedFileVersionId);
		}

		//Get the list for all review item and files
		R4EUIReviewBasic review = (R4EUIReviewBasic) aSingleReviewItem.getParent();
		List<R4EUIReviewItem> listReviewItems = review.getItems();
		int reviewItemsize = listReviewItems.size();
		String targetVersionId = null;
		R4EFileContext latestFileContext = null;

		//Test to see if the change file is within the latest review item
		for (int j = 0; j < reviewItemsize; j++) {
			EList<R4EFileContext> listFile = listReviewItems.get(j).getItem().getFileContextList();
			int size = listFile.size();
			//Test if the selected container is not before the current container
			Date testDate = listFile.get(0) != null
					? ((R4EItemImpl) listFile.get(0).eContainer()).getSubmitted()
					: null;

			//Same date of later, we look at the commit
			if (testDate != null) {
				if (testDate.after(dateSelected)) {
					for (int k = 0; k < size; k++) {
						R4EFileVersion targetFile = listFile.get(k).getTarget();
						if (null != targetFile) {
							// The file is not a deleted file
							targetVersionId = listFile.get(k).getTarget().getLocalVersionID();
							if (targetVersionId.equals(selectedFileVersionId)) {
								// A more recent commit found containing the target file
								// the anomalies are therefore not attached to this file context and will be attached 
								// to the most recent one when it's opened.
								return null;
							}
						}
					}
				} else if (testDate.equals(dateSelected)) {
					//Same date, so we initialize the context
					latestFileContext = listFile.get(0);
				}
			}

		}

		//Have found a context, verify to add only with the latest commit 
		if (latestFileContext != null) {
			R4EItemImpl selectContainer = (R4EItemImpl) latestFileContext.eContainer();
			Date datefound = selectContainer.getSubmitted();
			//A more recent review item containing this file version was not found. 
			//i.e. This file context belongs to  the most recent commit, therefore the anomalies shall be associated here
			if (datefound.equals(dateSelected)) {
				return FFileAnomalyMap.get(selectedFileVersionId);
			}
		}
		//This happen when there is no anomaly yet for a file or this file exist in a newer commit
		return null;
	}

	/**
	 * Method getReviewer.
	 * 
	 * @return R4EParticipant
	 */
	public static String getReviewer() {
		return FReviewer;
	}

	/**
	 * Method setReviewer.
	 * 
	 * @param aReviewer
	 *            R4EParticipant
	 */
	public static void setReviewer(String aReviewer) {
		FReviewer = aReviewer;
	}

	/**
	 * Method isUserQueryAvailable.
	 * 
	 * @return boolean\
	 */
	public static boolean isUserQueryAvailable() {
		//Verify if the LDAP bundle is available and if there is any LDAP host configured
		if (null != Platform.getBundle("org.eclipse.mylyn.reviews.ldap")
				&& LdapPlugin.getDefault()
						.getPreferenceStore()
						.getString(org.eclipse.mylyn.reviews.ldap.internal.preferences.PreferenceConstants.FP_HOST_ID)
						.length() > 0
				&& LdapPlugin.getDefault()
						.getPreferenceStore()
						.getString(org.eclipse.mylyn.reviews.ldap.internal.preferences.PreferenceConstants.FP_PORT_ID)
						.length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Method setCurrentFocusElement.
	 * 
	 * @param aElement
	 *            IR4EUIModelElement
	 */
	public static void setCurrentFocusElement(IR4EUIModelElement aElement) {
		FFocusElement = aElement;
	}

	/**
	 * Method getCurrentFocusElement.
	 * 
	 * @return IR4EUIModelElement
	 */
	public static IR4EUIModelElement getCurrentFocusElement() {
		return FFocusElement;
	}

	/**
	 * Method setCurrentPropertySection.
	 * 
	 * @param aSection
	 *            ModelElementTabPropertySection
	 */
	public static void setCurrentPropertySection(ModelElementTabPropertySection aSection) {
		FCurrentPropertySection = aSection;
	}

	/**
	 * Method getCurrentPropertySection.
	 * 
	 * @return ModelElementTabPropertySection
	 */
	public static ModelElementTabPropertySection getCurrentPropertySection() {
		return FCurrentPropertySection;
	}

	/**
	 * Stop model element serializations for the give resource
	 * 
	 * @param resource
	 */
	public static void stopSerialization(Resource aResource) {
		SerializeFactory.getResourceSerializationRegistry().addSerializationInactive(aResource);
	}

	/**
	 * Start a previously stopped serialization to the given resource
	 * 
	 * @param resource
	 */
	public static void startSerialization(Resource aResource) {
		SerializeFactory.getResourceSerializationRegistry().removeSerializationInactive(aResource);
	}

	/**
	 * Remove all previously stop serialization orders and serialize as default
	 */
	public static void resetToDefaultSerialization() {
		SerializeFactory.getResourceSerializationRegistry().clearSerializationInactive();
	}
}