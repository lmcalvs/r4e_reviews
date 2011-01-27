// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.constructorsOnlyInvokeFinalMethods, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the action used to add a linked anomaly to a given 
 * selection
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewComponent;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIF.FileVersionInfo;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewsVersionsIFFactory;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddLinkedAnomalyAction extends Action {
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Constructor for AddChildNodeAction.
	 * @param aView ReviewNavigatorView
	 * @param aActionName String
	 * @param aActionTooltip String
	 * @param aImage ImageDescriptor
	 */
	public AddLinkedAnomalyAction(ReviewNavigatorView aView, String aActionName, String aActionTooltip, ImageDescriptor aImage) {
		fView = aView;
		setText(aActionName);
		setToolTipText(aActionTooltip);
		setImageDescriptor(aImage);
	}
	
	/**
	 * Method run
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {	
		//Add a linked anomaly to this selection
		final IStructuredSelection selection = (IStructuredSelection) fView.getTreeViewer().getSelection();
		if (!selection.isEmpty()) {
			final IR4EUIModelElement element = ((IR4EUIModelElement)selection.getFirstElement());
			if (element instanceof R4EUISelection) {
				try {
					Activator.Tracer.traceInfo("Adding linked anomaly to element " + element.getName());
					addLinkedAnomaly((R4EUISelection)element);
					
				} catch (ResourceHandlingException e) {
					Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					Activator.getDefault().logError("Exception: " + e.toString(), e);
					final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while adding linked anomaly ",
		    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
					dialog.open();
				} catch (OutOfSyncException e) {				
					Activator.Tracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while adding linked anomaly.  " +
							"Please refresh the review navigator view and try the command again",
		    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
					dialog.open();
					// TODO later we will want to do this automatically
				} catch (ReviewVersionsException e) {
					Activator.Tracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					Activator.getDefault().logError("Exception: " + e.toString(), e);
					final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while adding review item ",
		    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
					dialog.open();
				}
			}
		}
	}
	
	/**
	 * Method addLinkedAnomaly.
	 * @param aElement R4EUISelection
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws ReviewVersionsException 
	 */
	private void addLinkedAnomaly(R4EUISelection aElement) throws ResourceHandlingException, 
			OutOfSyncException, ReviewVersionsException {
		
		final R4EUIFileContext fileContext = (R4EUIFileContext)aElement.getParent().getParent();
		R4EUIAnomalyContainer container = (R4EUIAnomalyContainer)(fileContext.getAnomalyContainerElement());
		final String user = R4EUIModelController.getReviewer();
		
		//Get core version interface
		final IFile targetFile = fileContext.getTargetFile();
		final IProject project = targetFile.getProject();
		ReviewsVersionsIF versionsIf = null;
		try {
			versionsIf = ReviewsVersionsIFFactory.instance.getVersionsIF(project);
		} catch (ReviewVersionsException e) {
			Activator.Tracer.traceInfo("Exception: " + e.toString() + " (" + e.getMessage() + ")");
			Activator.getDefault().logInfo("Exception: " + e.toString(), e);
			final ErrorDialog dialog = new ErrorDialog(null, "Info", 
					"Take note that the review item you are trying to add is not in source control.",
    				new Status(IStatus.INFO, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.INFO);
			dialog.open();
		}
		
		//Get data from user
		if (null == container) {
			container = new R4EUIAnomalyContainer(fileContext, R4EUIConstants.ANOMALIES_LABEL_NAME);
			fileContext.addChildren(container);
		}
		final R4EReviewComponent tempModelComponent = container.createChildModelDataElement();
		if (null == tempModelComponent) return;  //User cancelled the action
		Activator.Tracer.traceInfo("Adding child to linked element " + container.getName());

		//Create actual model elements
		final R4EParticipant participant = ((R4EUIReview)fileContext.getParent().getParent()).getParticipant(user, true); // $codepro.audit.disable methodChainLength
		final R4EAnomaly anomaly = R4EUIModelController.FModelExt.createR4EAnomaly(participant);
		final R4EAnomalyTextPosition position = R4EUIModelController.FModelExt.createR4EAnomalyTextPosition(
				R4EUIModelController.FModelExt.createR4ETextContent(anomaly));
		
		final R4EFileVersion anomalyFile = R4EUIModelController.FModelExt.createR4EFileVersion(position);
		final Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(anomalyFile, R4EUIModelController.getReviewer());
		anomalyFile.setResource(targetFile);
		anomalyFile.setPlatformURI(ResourceUtils.toPlatformURI(targetFile).toString());
		if (null != versionsIf) {
			//File is in a Git repository
			final FileVersionInfo versionInfo = versionsIf.getFileVersionInfo(targetFile);
			anomalyFile.setName(versionInfo.getName());
			anomalyFile.setRepositoryPath(versionInfo.getRepositoryPath());
			anomalyFile.setVersionID(versionInfo.getId());
		} else {
			//File is not version-controlled
			anomalyFile.setName(targetFile.getName());
			anomalyFile.setRepositoryPath(targetFile.getFullPath().toOSString());
			anomalyFile.setVersionID(R4EUIConstants.FILE_NOT_IN_VERSION_CONTROL_MSG);
		}
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		final R4EUITextPosition uiPosition = (R4EUITextPosition) aElement.getPosition();
		uiPosition.setPositionInModel(position);
		final R4EUIAnomaly uiAnomaly = new R4EUIAnomaly(container, anomaly, uiPosition, ((R4EAnomaly)tempModelComponent).getTitle());
		uiAnomaly.setModelData(tempModelComponent);   //Set Element dats in model
		container.addChildren(uiAnomaly);
		
		//Set focus to newly created anomaly comment
		fView.getTreeViewer().expandToLevel(uiAnomaly, AbstractTreeViewer.ALL_LEVELS);
		fView.getTreeViewer().setSelection(new StructuredSelection(uiAnomaly), true);
	}
}
