// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, useForLoop, staticFieldSecurity, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the model controller.  It is used to control the UI 
 * model data
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.frame.core.model.Location;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.commands.ReviewState;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.IPropertyListener;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIModelController {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fRootElement.
	 */
	private static R4EUIElement FRootElement = null;
	
	/**
	 * Field FIsDialogOpen.
	 */
	private static boolean FIsDialogOpen = false;
	
	/**
	 * Field fActiveReview.
	 */
	private static R4EUIReview FActiveReview = null;
	
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
	
	//References to the EMF serialization model
	
	/**
	 * Field modelExt.
	 */
	public static RModelFactoryExt FModelExt;
	/**
	 * Field resourceUpdater.
	 */
	public static ResourceUpdater FResourceUpdater;
	
	/**
	 * Field fFileAnomalyMap.
	 * 		Filname to Anomaly List map
	 */
	private static final Map<String,List<R4EAnomaly>> FFileAnomalyMap = new HashMap<String,List<R4EAnomaly>>(32, 0.75f); // $codepro.audit.disable constantNamingConvention
	
	/**
	 * Field FDialogStateListenerList.
	 */
	private static final List<IPropertyListener> FDialogStateListenerList = new ArrayList<IPropertyListener>(); // $codepro.audit.disable constantNamingConvention
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method addDialogStateListener.
	 * @param aListener IPropertyListener
	 */
	public static void addDialogStateListener(IPropertyListener aListener) {
		FDialogStateListenerList.add(aListener);
	}
	
	/**
	 * Method removeDialogStateListener.
	 * @param aListener IPropertyListener
	 */
	public static void removeDialogStateListener(IPropertyListener aListener) {
		FDialogStateListenerList.remove(aListener);
	}
	
	/**
	 * Method setNavigatorView.
	 * @param aView ReviewNavigatorView
	 */
	public static void setNavigatorView(ReviewNavigatorView aView) {
		FView = aView;
	}
	
	/**
	 * Method getNavigatorView.
	 * @return ReviewNavigatorView
	 */
	public static ReviewNavigatorView getNavigatorView() {
		return FView;
	}
	
	/**
	 * Method setReviewCommandSourceProvider.
	 * @param aSourceProvider ReviewState
	 */
	public static void setReviewCommandSourceProvider(ReviewState aSourceProvider) {
		FReviewSourceProvider = aSourceProvider;
	}
	
	/**
	 * Get the model root element
	 * @return the root R4E element
	 */
	public static IR4EUIModelElement getRootElement() {
		return FRootElement;
	}
	
	/**
	 * Set the currently active review
	 * @param aActiveReview ReviewElement - the currently open ReviewElement
	 */
	public static void setActiveReview(R4EUIReview aActiveReview) {
		FActiveReview = aActiveReview;
		if (null != FReviewSourceProvider) {
			FReviewSourceProvider.setReview(FActiveReview);
		}
	}
	
	/**
	 * Set the currently dialog state (true if a dialog is currently in progress)
	 * @param aIsDialogOpen boolean
	 */
	public static void setDialogOpen(boolean aIsDialogOpen) {
		FIsDialogOpen = aIsDialogOpen;
		for (IPropertyListener listener : FDialogStateListenerList) {
			listener.propertyChanged(null, 0);
		}
	}
	
	/**
	 * Set the currently dialog state (true if a dialog is currently in progress)
	 * @param aSelection the selected element
	 */
	public static void selectionChanged(IStructuredSelection aSelection) {
		for (IPropertyListener listener : FDialogStateListenerList) {
			listener.propertyChanged(null, 0);  //TODO this is temporary and should be refined later
		}
	}
	
	
	/**
	 * Get the currently active review
	 * @return the currently open ReviewElement
	 */
	public static R4EUIReview getActiveReview() {
		return FActiveReview;
	}
	
	/**
	 * Get the current dialog state
	 * @return the current dialog state
	 */
	public static boolean isDialogOpen() {
		return FIsDialogOpen;
	}
	
	/**
	 * Method loadModel.
	 * 		Load data from Serialized Model
	 */
	public static void loadModel() {
		FModelExt = SerializeFactory.getModelExtension();
		FResourceUpdater = SerializeFactory.getResourceUpdater();
		
        //Set current user as reviewer
        setReviewer(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER_ID));
        
		//Add root for Review Navigator
		FRootElement = new R4EUIElement(null, "R4E");
		
		//Load Review Groups
		final IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		final String paths = preferenceStore.getString(PreferenceConstants.P_FILE_PATH);
		final String[] pathsArray = parseReviewGroups(paths);
		boolean changePrefsPaths = false;
		
		R4EReviewGroup reviewGroup = null;
        for (int i = 0; i < pathsArray.length; i++) {
        	
        	reviewGroup = null;
        	
        	//First try to open the review group file as entrered in the preferences
        	//If it fails, then create it
        	try {
        		reviewGroup = FModelExt.openR4EReviewGroup(URI.createFileURI(pathsArray[i]));
    			FRootElement.loadReviewGroup(reviewGroup);
        	} catch (ResourceHandlingException e) {
    			Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");

        		//Review Group not found, ask user for review group creation
        		MessageDialog dialog = new MessageDialog( // $codepro.audit.disable variableDeclaredInLoop
        				null, "Missing Review Group", null, "Review Group file at location " + pathsArray[i] + 
        					" not found, remove it from Preferences?",
        				MessageDialog.WARNING,
        				new String[] {"Yes", "No"},
        				R4EUIConstants.DIALOG_NO); // no is the default
        		int result = dialog.open(); // $codepro.audit.disable variableDeclaredInLoop

        		if (result == R4EUIConstants.DIALOG_YES) {
        			changePrefsPaths = true;
        			pathsArray[i] = null;
        		}      			
        	}
        }
        
        //Adjust review group paths in preferences
        if (changePrefsPaths) {
    		preferenceStore.setValue(PreferenceConstants.P_FILE_PATH, buildReviewGroupsStr(pathsArray));
        }
	}
	
	
	/**
	 * Method parseReviewGroups.
	 * @param aReviewGroupsStr String
	 * @return String[]
	 */
	private static String[] parseReviewGroups(String aReviewGroupsStr) {
        final StringTokenizer st = new StringTokenizer(aReviewGroupsStr, File.pathSeparator + 
        		System.getProperty("line.separator"));
        final ArrayList<String> stringArray = new ArrayList<String>();
        while (st.hasMoreElements()) {
        	stringArray.add((String) st.nextElement());
        }
        return stringArray.toArray(new String[stringArray.size()]);
	}
	
	/**
	 * Method buildReviewGroupsStr.
	 * @param aReviewGroups String[]
	 * @return String
	 */
	private static String buildReviewGroupsStr(String[] aReviewGroups) {
		final StringBuffer newPathsStr = new StringBuffer(R4EUIConstants.REVIEW_GROUP_PATHS_LENGTH);
        for (int i = 0; i < aReviewGroups.length; i++) {
        	if (null != aReviewGroups[i]) {
        		newPathsStr.append(aReviewGroups[i] + System.getProperty("line.separator"));
        	}
        }
        return newPathsStr.toString();
	}
	
	/** // $codepro.audit.disable blockDepth
	 * Method mapAnomalies.
	 * 		Map anomalies to the files in which they are located
	 * @param aReview R4EReview
	 */
	public static void mapAnomalies(R4EReview aReview) {
		final EList<Topic> anomalies = aReview.getTopics();
		Topic anomaly = null;
		EList<Location> locations = null;
		final int anomaliesSize = anomalies.size();
		for (int i = 0; i < anomaliesSize; i++) {
			anomaly = anomalies.get(i);
			
			locations = anomaly.getLocation();
			for (Location location : locations) {
				String targetFilePath = ((R4EAnomalyTextPosition)((R4EContent)location).getLocation()).getFile().getPlatformURI(); // $codepro.audit.disable variableDeclaredInLoop
				if (FFileAnomalyMap.containsKey(targetFilePath)) {
					//Add anomaly for this filename if not already present
					List<R4EAnomaly> anomalyList = FFileAnomalyMap.get(targetFilePath); // $codepro.audit.disable variableDeclaredInLoop
					if (!(anomalyList.contains(anomaly))) {
						anomalyList.add((R4EAnomaly)anomaly);
					}
				} else {
					List<R4EAnomaly> anomalyList = new ArrayList<R4EAnomaly>(); // $codepro.audit.disable variableDeclaredInLoop
					anomalyList.add((R4EAnomaly)anomaly);
					FFileAnomalyMap.put(targetFilePath, anomalyList);
				}
			}

		}
	}
	
	/**
	 * Method clearAnomalyMap.
	 */
	public static void clearAnomalyMap() {
		final Set<String> keys = FFileAnomalyMap.keySet();
		for (String key: keys) {
			List<R4EAnomaly> anomaliesList = FFileAnomalyMap.get(key);
			anomaliesList.clear();
		}
		FFileAnomalyMap.clear();
	}
	
	/**
	 * Method getAnomaliesForFile.
	 * 		Get list of anomalies for a given file
	 * @param aFilePath String
	 * @return List<R4EAnomaly>
	 */
	public static List<R4EAnomaly> getAnomaliesForFile(String aFilePath) {
		return FFileAnomalyMap.get(aFilePath);
	}
	
	/**
	 * Method getReviewer.
	 * @return R4EParticipant
	 */
	public static String getReviewer() {
		return FReviewer;
	}
	
	/**
	 * Method setReviewer.
	 * @param aReviewer R4EParticipant
	 */
	public static void setReviewer(String aReviewer) {
		FReviewer = aReviewer;
	}
}