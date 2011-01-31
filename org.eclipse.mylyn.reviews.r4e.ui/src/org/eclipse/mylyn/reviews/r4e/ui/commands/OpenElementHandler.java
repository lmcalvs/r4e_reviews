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
 * This class implements the review navigator view toolbar command used 
 * to browse the view's treeviewer elements and open the ones that can be open
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands;
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
 * This class implements the context-sensitive command to open the
 * currently selected element and load data from the model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

import java.io.FileNotFoundException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.versions.ReviewVersionsException;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIReview;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class OpenElementHandler extends AbstractHandler {

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

		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty()) {
			try {
				final IR4EUIModelElement element = (IR4EUIModelElement)selection.getFirstElement();

				if (element instanceof R4EUIReview) {					
					Activator.Ftracer.traceInfo("Opening element " + element.getName());
					final R4EUIReview activeReview = R4EUIModelController.getActiveReview();
					if (null != activeReview ) activeReview.close();
					element.open();
					R4EUIModelController.setActiveReview((R4EUIReview)element);
				} else {
					element.open();	
				}

				//The action is only performed on the first element, so select it
				final StructuredSelection newSelection = new StructuredSelection(selection.getFirstElement());
				R4EUIModelController.getNavigatorView().getTreeViewer().setSelection(newSelection, true);
			} catch (ResourceHandlingException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Error while opening element",
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			} catch (ReviewVersionsException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "Version error detected while opening element",
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			} catch (FileNotFoundException e) {
				Activator.Ftracer.traceError("Exception: " + e.toString() + " (" + e.getMessage() + ")");
				Activator.getDefault().logError("Exception: " + e.toString(), e);
				final ErrorDialog dialog = new ErrorDialog(null, "Error", "File not found error detected while opening element",
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, 0, e.getMessage(), e), IStatus.ERROR);
				dialog.open();
			}
		}
		return null;
	}
}
