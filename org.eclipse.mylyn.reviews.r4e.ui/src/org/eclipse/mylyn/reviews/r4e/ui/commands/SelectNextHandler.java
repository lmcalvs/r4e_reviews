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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.editors.EditorProxy;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIAnomaly;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUISelection;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorTreeViewer;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class SelectNextHandler extends AbstractHandler {

	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object 
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) {

		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty()) {
			final ReviewNavigatorView view = R4EUIModelController.getNavigatorView();
			
			//Get the previous element
			final IR4EUIModelElement nextElement = getNextElement((ReviewNavigatorTreeViewer) view.getTreeViewer());
		    
			//If there is one, select it
			if (null != nextElement) {
			    Activator.Tracer.traceInfo("Select next element " + nextElement.getName());
				final ISelection nextSelection = new StructuredSelection(nextElement);
				view.getTreeViewer().setSelection(nextSelection);
				
			    //Open the editor on FileContexts, selections amd anomalies
				if (nextElement instanceof R4EUIFileContext ||
						nextElement instanceof R4EUISelection ||
						nextElement instanceof R4EUIAnomaly) {
					EditorProxy.openEditor(view.getSite().getPage(), nextSelection, false);
				}
			}			
		}
		return null;
	}
	
	/**
	 * Method getNextElement.
	 * @param aTreeViewer ReviewNavigatorTreeViewer
	 * @return IR4EUIModelElement
	 */
	private IR4EUIModelElement getNextElement(ReviewNavigatorTreeViewer aTreeViewer) {
		final TreeItem item = aTreeViewer.getTree().getSelection()[0];
		final TreeItem nextItem = aTreeViewer.getNext(item);
		return (IR4EUIModelElement) nextItem.getData();
	}
}
