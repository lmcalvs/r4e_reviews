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
 * This class implements an Adapter used when dropping Anomalies on Contents 
 * for cloning purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.navigator;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIAnomalyBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIComment;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIContent;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.AnomalyUtils;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TransferData;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUIElementDropAdapter extends ViewerDropAdapter {

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIElementDragListener.
	 * 
	 * @param aViewer
	 *            - ReviewNavigatorTreeViewer
	 */
	public R4EUIElementDropAdapter(ReviewNavigatorTreeViewer viewer) {
		super(viewer);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method declared on ViewerDropAdapter
	 * 
	 * @param aData
	 *            - Object
	 */
	@Override
	public boolean performDrop(Object aData) {
		if (null != aData) {
			R4EUIModelElement target = (R4EUIModelElement) getCurrentTarget();
			if (target != null) {
				Object[] sourceElements = ((IStructuredSelection) aData).toArray();

				for (Object sourceElement : sourceElements) {
					if (target instanceof R4EUIContent && sourceElement instanceof R4EUIAnomalyBasic) {
						if (null == AnomalyUtils.isAnomalyExist((R4EUIFileContext) target.getParent().getParent(),
								((R4EUIContent) target).getPosition(), ((R4EUIAnomalyBasic) sourceElement).getAnomaly()
										.getDescription())) {
							try {
								//Dropping the anomaly into content creates a cloned linked anomaly
								AnomalyUtils.cloneLinkedAnomaly((R4EUIContent) target,
										(R4EUIAnomalyBasic) sourceElement);
							} catch (ResourceHandlingException e) {
								UIUtils.displayResourceErrorDialog(e);
							} catch (OutOfSyncException e) {
								UIUtils.displaySyncErrorDialog(e);
							}
						}
					} else if (target instanceof R4EUIAnomalyBasic && sourceElement instanceof R4EUIComment) {
						if (null == AnomalyUtils.isCommentExist((R4EUIAnomalyBasic) target,
								((R4EUIComment) sourceElement).getComment().getDescription())) {
							try {
								//Dropping the comment into the Anomaly copies it to this anomaly
								IR4EUIModelElement newUIComment = ((R4EUIAnomalyBasic) target).createChildren(((R4EUIComment) sourceElement).getComment());
								UIUtils.setNavigatorViewFocus(newUIComment, AbstractTreeViewer.ALL_LEVELS);
							} catch (ResourceHandlingException e) {
								UIUtils.displayResourceErrorDialog(e);
							} catch (OutOfSyncException e) {
								UIUtils.displaySyncErrorDialog(e);
							} catch (CompatibilityException e) {
								UIUtils.displayCompatibilityErrorDialog(e);
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Method declared on ViewerDropAdapter
	 * 
	 * @param aTarget
	 *            - Object
	 * @param aOperation
	 *            - int
	 * @param aTransferType
	 *            - TransferData
	 * @return boolean
	 */
	@Override
	public boolean validateDrop(Object aTarget, int aOperation, TransferData aTransferType) {
		IR4EUIModelElement elementSelected = (IR4EUIModelElement) ((IStructuredSelection) R4EUIModelController.getNavigatorView()
				.getTreeViewer()
				.getSelection()).getFirstElement();
		if ((aTarget instanceof R4EUIContent && elementSelected instanceof R4EUIAnomalyBasic)
				|| (aTarget instanceof R4EUIAnomalyBasic && elementSelected instanceof R4EUIComment)) {
			return LocalSelectionTransfer.getTransfer().isSupportedType(aTransferType);
		}
		return false;
	}

	/**
	 * Method declared on ViewerDropAdapter
	 * 
	 * @param aEvent
	 *            - DropTargetEvent
	 */
	@Override
	public void dragOver(DropTargetEvent aEvent) {
		super.dragOver(aEvent);
		aEvent.feedback = DND.FEEDBACK_INSERT_BEFORE;
	}
}
