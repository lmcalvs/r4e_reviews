/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements an Adapter used when dragging Anomalies for cloning purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIElementDragListener extends DragSourceAdapter {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIElementDragListener.
	 * 
	 * @param aViewer
	 *            - ReviewNavigatorTreeViewer
	 */
	public R4EUIElementDragListener(ReviewNavigatorTreeViewer aViewer) {
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method declared on DragSourceListener
	 * 
	 * @param aEvent
	 *            - DragSourceEvent
	 */
	@Override
	public void dragStart(DragSourceEvent aEvent) {
		//Only allow dragging of anomalies
		ISelection selection = R4EUIModelController.getNavigatorView().getTreeViewer().getSelection();
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			//Only allow dragging Local Anomalies (not global ones)
			if ((element instanceof R4EUIAnomalyBasic && ((R4EUIAnomalyBasic) element).getParent().getParent() instanceof R4EUIFileContext)
					|| element instanceof R4EUIComment) {
				aEvent.doit = true;
				return;
			}
		}
		aEvent.doit = false;
	}

	/**
	 * Method declared on DragSourceListener
	 * 
	 * @param aEvent
	 *            - DragSourceEvent
	 */
	@Override
	public void dragSetData(DragSourceEvent aEvent) {
		IStructuredSelection selection = (IStructuredSelection) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getSelection();
		IR4EUIModelElement[] elements = (IR4EUIModelElement[]) selection.toList().toArray(
				new IR4EUIModelElement[selection.size()]);
		aEvent.data = elements;
		LocalSelectionTransfer.getTransfer().setSelection(selection);
	}
}
