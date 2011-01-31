// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity
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
 * This class implements the context-sensitive command to add a new anomaly
 * that is linked to the currently selected element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomalyContainer;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUITextPosition;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AddLinkedAnomalyHandler extends AbstractHandler {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object 
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) {
		//Add a linked anomaly to this selection
		final IStructuredSelection selection = 
			(IStructuredSelection) R4EUIModelController.getNavigatorView().getTreeViewer().getSelection();
		if (!selection.isEmpty()) {
			final IR4EUIModelElement element = ((IR4EUIModelElement)selection.getFirstElement());
			if (element instanceof R4EUISelection) {
				try {
					Activator.Ftracer.traceInfo("Adding linked anomaly to element " + element.getName());
					addLinkedAnomaly((R4EUISelection)element);
					
				} catch (ResourceHandlingException e) {
					Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					Activator.getDefault().logError("Exception: " + e.toString(), e);
					final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while adding linked anomaly ",
		    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
					dialog.open();
				} catch (OutOfSyncException e) {				
					Activator.Ftracer.traceWarning("Exception: " + e.toString() + " (" + e.getMessage() + ")");
					final ErrorDialog dialog = new ErrorDialog(null, "Error", "Synchronization error detected while adding linked anomaly.  " +
							"Please refresh the review navigator view and try the command again",
		    				new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
					dialog.open();
					// TODO later we will want to do this automatically
				}
			}
		}
		return null;
	}
	
	/**
	 * Method addLinkedAnomaly.
	 * @param aElement R4EUISelection
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 * @throws ReviewVersionsException 
	 */
	private void addLinkedAnomaly(R4EUISelection aElement) throws ResourceHandlingException, 
			OutOfSyncException {
		
		final R4EUIFileContext fileContext = (R4EUIFileContext)aElement.getParent().getParent();
		R4EUIAnomalyContainer container = (R4EUIAnomalyContainer)(fileContext.getAnomalyContainerElement());
		//Get data from user
		if (null == container) {
			container = new R4EUIAnomalyContainer(fileContext, R4EUIConstants.ANOMALIES_LABEL_NAME);
			fileContext.addChildren(container);
		}
		
		final R4EUIAnomaly uiAnomaly = container.createAnomaly((R4EUITextPosition) aElement.getPosition());
		if (null != uiAnomaly) {
			//Set focus to newly created anomaly comment
			R4EUIModelController.getNavigatorView().getTreeViewer().expandToLevel(uiAnomaly, AbstractTreeViewer.ALL_LEVELS);
			R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(new StructuredSelection(uiAnomaly), true);
		}	
	}
}
