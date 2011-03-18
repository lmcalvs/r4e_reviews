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
 * This class implements the navigator view toolbar command to apply the 
 * default alphabetical sorter
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands.sorters;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorActionGroup;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class AlphaSortHandler extends AbstractHandler {

	/**
	 * Method execute.
	 * @param event ExecutionEvent
	 * @return Object
	 * @throws ExecutionException
	 * @see org.eclipse.core.commands.IHandler#execute(ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		//We need to preserve the expansion state and restore it afterwards
	    final TreeViewer viewer = R4EUIModelController.getNavigatorView().getTreeViewer();
	    final ViewerComparator sorter = 
	    	((ReviewNavigatorActionGroup) R4EUIModelController.getNavigatorView().getActionSet()).getAlphaSorter();
		
	    final Object[] elements =  viewer.getExpandedElements();
	    boolean oldValue = HandlerUtil.toggleCommandState(event.getCommand());
	    
		if (!oldValue) {
			Activator.Ftracer.traceInfo("Apply alpha sorter to ReviewNavigator");
			viewer.setComparator(sorter);
		} else {
			Activator.Ftracer.traceInfo("Remove alpha sorter from ReviewNavigator");
			viewer.setComparator(null);
		}
		R4EUIModelController.getNavigatorView().getTreeViewer().setExpandedElements(elements);
		return null;
	}
}
