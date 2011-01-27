// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the Anomaly Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.frame.core.model.Location;
import org.eclipse.mylyn.reviews.frame.core.model.Topic;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.actions.AddChildNodeAction;
import org.eclipse.mylyn.reviews.r4e.ui.dialogs.R4EAnomalyInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorContentProvider;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.utils.UIUtils;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;


/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIAnomalyContainer extends R4EUIModelElement {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field fAnomalyContainerFile.
	 * (value is ""icons/anomaly_container.gif"")
	 */
	private static final String ANOMALY_CONTAINER_ICON_FILE = "icons/anomaly_container.gif";
	  
	/**
	 * Field ADD_ELEMENT_ACTION_NAME.
	 * (value is ""Add Anomaly"")
	 */
	private static final String ADD_ELEMENT_ACTION_NAME = "Add Anomaly";
	
    /**
     * Field ADD_ELEMENT_ACTION_TOOLTIP.
     * (value is ""Add a new global anomaly to the current review item"")
     */
    private static final String ADD_ELEMENT_ACTION_TOOLTIP = "Add a new global anomaly to the current review item";

	/**
	 * Field ADD_ANOMALY_DIALOG_TITLE.
	 * (value is ""Enter Anomaly details"")
	 */
	private static final String ADD_ANOMALY_DIALOG_TITLE = "Enter Anomaly details";
	
	/**
	 * Field ADD_ANOMALY_DIALOG_VALUE.
	 * (value is ""Enter the Anomaly title:"")
	 */
	private static final String ADD_ANOMALY_DIALOG_VALUE = "Enter the Anomaly title:";
	
	/**
	 * Field ADD_COMMENT_DIALOG_VALUE.
	 * (value is ""Enter your comments for the new Anomaly:"")
	 */
	private static final String ADD_DESCRIPTION_DIALOG_VALUE = "Enter the Anomaly description:";
	
	
	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
    
	/**
	 * Field fAnomalies.
	 */
	private final List<R4EUIAnomaly> fAnomalies;
	
	/**
	 * Field fContextAddChildNodeAction.
	 */
	private static AddChildNodeAction FContextAddChildNodeAction = null;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for AnomalyContainerElement.
	 * @param aParent IR4EUIModelElement
	 * @param aName String
	 */
	public R4EUIAnomalyContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName, null);
		fAnomalies = new ArrayList<R4EUIAnomaly>();
		fImage = UIUtils.loadIcon(ANOMALY_CONTAINER_ICON_FILE);
	}

	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	//Attributes
	
	/**
	 * Create a serialization model element object
	 * @return the new serialization element object
	 */
	@Override
	public R4EReviewComponent createChildModelDataElement() {
		//Get comment from user and set it in model data
		R4EAnomaly tempAnomaly = null;
		R4EUIModelController.setDialogOpen(true);
		final R4EAnomalyInputDialog dialog = new R4EAnomalyInputDialog(R4EUIModelController.getNavigatorView(). // $codepro.audit.disable methodChainLength
				getSite().getWorkbenchWindow().getShell(), ADD_ANOMALY_DIALOG_TITLE, ADD_ANOMALY_DIALOG_VALUE, 
				ADD_DESCRIPTION_DIALOG_VALUE);
    	final int result = dialog.open();
    	if (result == Window.OK) {
    		tempAnomaly = RModelFactory.eINSTANCE.createR4EAnomaly();
    		tempAnomaly.setTitle(dialog.getAnomalyValue());
    		tempAnomaly.setDescription(dialog.getCommentValue());
    	}
    	// else Window.CANCEL
		R4EUIModelController.setDialogOpen(false);
    	return tempAnomaly;
	}
	
	
	//Hierarchy
	
	/**
	 * Method getChildren.
	 * @return IR4EUIModelElement[]
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#getChildren()
	 */
	@Override
	public IR4EUIModelElement[] getChildren() { // $codepro.audit.disable
		return fAnomalies.toArray(new R4EUIAnomaly[fAnomalies.size()]);
	}
	
	/**
	 * Method hasChildren.
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		if (fAnomalies.size() > 0) return true;
	    return false;
	}
	
	/**
	 * Close the model element (i.e. disable it)
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#close()
	 */
	@Override
	public void close() {
		//Remove all children references
		R4EUIAnomaly anomaly = null;
		final int anomaliesSize = fAnomalies.size();
		for (int i = 0; i < anomaliesSize; i++) {
			
			anomaly = fAnomalies.get(i);
			anomaly.close();
			//fireRemove(anomaly);
		}
		fAnomalies.clear();
		fOpen = false;
		removeListener();
	}
	
	/** // $codepro.audit.disable blockDepth
	 * Method loadModelData.
	 * 		Load the serialization model data into UI model
	 */
	@Override
	public void loadModelData() {

		R4EUIAnomaly uiAnomaly = null;
		final IR4EUIModelElement parentElement = getParent();
		if (parentElement instanceof R4EUIFileContext) {
			
			//get anomalies that are specific to a file
			final List<R4EAnomaly> anomalies = ((R4EUIFileContext)parentElement).getAnomalies();
			R4EUITextPosition position = null;
			final int anomaliesSize = anomalies.size();
			for (int i = 0; i < anomaliesSize; i++) {

				//Do not set position for global EList<E>lies
				position = null;
				EList<Location> locations = anomalies.get(i).getLocation(); // $codepro.audit.disable variableDeclaredInLoop
				if (null != locations) {
					if (null != locations.get(0)) {
						int locationsSize = locations.size(); // $codepro.audit.disable variableDeclaredInLoop
						for (int j = 0; j < locationsSize; j++) {
							position = new R4EUITextPosition(
									((R4EContent)anomalies.get(i).getLocation().get(j)).getLocation());  // $codepro.audit.disable methodChainLength
							uiAnomaly = new R4EUIAnomaly(this, anomalies.get(i), position, anomalies.get(i).getTitle());
							addChildren(uiAnomaly);
							uiAnomaly.loadModelData();
						}
					} else {
						uiAnomaly = new R4EUIAnomaly(this, anomalies.get(i), null, anomalies.get(i).getTitle());
						addChildren(uiAnomaly);
						uiAnomaly.loadModelData();
					}
				}
			}
		} else if (parentElement instanceof R4EUIReview) {
			
			//Get anomalies that do not have any location.  These are global anomalies
			
			final EList<Topic> anomalies =((R4EUIReview)parentElement).getReview().getTopics();
			if (null != anomalies) {
				final int anomaliesSize = anomalies.size();
				for (int i = 0; i < anomaliesSize; i++) {
					R4EAnomaly anomaly = (R4EAnomaly) anomalies.get(i); // $codepro.audit.disable variableDeclaredInLoop
					if (0 == anomaly.getLocation().size()) {
						uiAnomaly = new R4EUIAnomaly(this, anomaly, null, anomaly.getTitle());
						addChildren(uiAnomaly);
						uiAnomaly.loadModelData();
					}
				}
			}
			
		}
		fOpen = true;
	}
	
	/**
	 * Method addChildren.
	 * @param aChildToAdd IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addChildren(IR4EUIModelElement)
	 */
	@Override
	public void addChildren(IR4EUIModelElement aChildToAdd) {
		fAnomalies.add((R4EUIAnomaly) aChildToAdd);
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
	public IR4EUIModelElement createChildren(R4EReviewComponent aModelComponent) throws ResourceHandlingException, OutOfSyncException {
		final String user = R4EUIModelController.getReviewer();
		final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(((R4EUIReview)getParent()).getParticipant(user, true));
		final R4EUIAnomaly addedChild = new R4EUIAnomaly(this, anomaly, null, ((R4EAnomaly)aModelComponent).getTitle());
		addedChild.setModelData(aModelComponent);
		addChildren(addedChild);
		return addedChild;
	}
	
	/**
	 * Method removeChildren.
	 * @param aChildToRemove IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeChildren(IR4EUIModelElement)
	 */
	@Override
	public void removeChildren(IR4EUIModelElement aChildToRemove) {
		fAnomalies.remove(aChildToRemove);
		aChildToRemove.removeListener();
		fireRemove(aChildToRemove);
	}
	
	
	//Listeners
	
	/**
	 * Method addListener.
	 * @param aProvider ReviewNavigatorContentProvider
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#addListener(ReviewNavigatorContentProvider)
	 */
	@Override
	public void addListener(ReviewNavigatorContentProvider aProvider) {
		fListener = aProvider;
		if (null != fAnomalies) {
			R4EUIAnomaly element = null;
			for (final Iterator<R4EUIAnomaly> iterator = fAnomalies.iterator(); iterator.hasNext();) {
			    element = iterator.next();
				element.addListener(aProvider);
			}
		}
	}
	
	/**
	 * Method removeListener
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#removeListener()
	 */
	@Override
	public void removeListener() {
		fListener = null;
		if (null != fAnomalies) {
			R4EUIAnomaly element = null;
			for (final Iterator<R4EUIAnomaly> iterator = fAnomalies.iterator(); iterator.hasNext();) {
				element = iterator.next();
				element.removeListener();
			}
		}
	}
	
	
	//Actions
	
	/**
	 * Method createActions.
	 * @param aView ReviewNavigatorView
	 * @see org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement#createActions(ReviewNavigatorView)
	 */
	@Override
	public void createActions(ReviewNavigatorView aView) {
		FContextAddChildNodeAction = new AddChildNodeAction(aView, ADD_ELEMENT_ACTION_NAME, ADD_ELEMENT_ACTION_TOOLTIP,
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD), false);
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
		
		//Only provide add action if this container is for global anomalies
		if (!(R4EUIModelController.isDialogOpen()) && getParent() instanceof R4EUIReview) {
			if (null == FContextAddChildNodeAction) createActions(aView);
			actions.add(FContextAddChildNodeAction);
		}
		return actions;
	}
}
