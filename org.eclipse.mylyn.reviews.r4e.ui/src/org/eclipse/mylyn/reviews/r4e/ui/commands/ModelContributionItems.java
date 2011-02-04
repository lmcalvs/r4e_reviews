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
 * This class implements contribution item that provide the model commands
 * to the review navigator context-sensitive menus
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.utils.R4EUIConstants;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ModelContributionItems extends CompoundContributionItem {

	/**
	 * Method getContributionItems.
	 * @return IContributionItem[]
	 */
	@Override
	protected IContributionItem[] getContributionItems() {

		final List<IContributionItem> list = new ArrayList<IContributionItem>();
		CommandContributionItemParameter params = null;
		final IR4EUIModelElement element = 
			(IR4EUIModelElement) ((IStructuredSelection)R4EUIModelController.getNavigatorView().getTreeViewer().getSelection()).getFirstElement();

		if (null != element) {
			if (element.isOpenElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.OPEN_ELEMENT_COMMAND,
						R4EUIConstants.OPEN_ELEMENT_COMMAND,
						null, ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(R4EUIConstants.OPEN_ELEMENT_ICON_FILE)),
						null, null, R4EUIConstants.OPEN_ELEMENT_COMMAND_NAME, R4EUIConstants.OPEN_ELEMENT_COMMAND_MNEMONIC,
						R4EUIConstants.OPEN_ELEMENT_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (element.isCloseElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.CLOSE_ELEMENT_COMMAND,
						R4EUIConstants.CLOSE_ELEMENT_COMMAND,
						null, ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(R4EUIConstants.CLOSE_ELEMENT_ICON_FILE)),
						null, null, R4EUIConstants.CLOSE_ELEMENT_COMMAND_NAME, R4EUIConstants.CLOSE_ELEMENT_COMMAND_MNEMONIC,
						R4EUIConstants.CLOSE_ELEMENT_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}
			
			if (element.isAddChildElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND,
						R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND,
						null, PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD),
						null, null, element.getAddChildElementCmdName(), R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_MNEMONIC,
						element.getAddChildElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (element.isRemoveElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.REMOVE_ELEMENT_COMMAND,
						R4EUIConstants.REMOVE_ELEMENT_COMMAND,
						null, PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE),
						null, null, element.getRemoveElementCmdName(), R4EUIConstants.REMOVE_ELEMENT_COMMAND_MNEMONIC,
						element.getRemoveElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}
			
			if (element.isAddLinkedAnomalyCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.ADD_LINKED_ANOMALY_COMMAND,
						R4EUIConstants.ADD_LINKED_ANOMALY_COMMAND,
						null, PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD),
						null, null, R4EUIConstants.ADD_LINKED_ANOMALY_NAME, R4EUIConstants.ADD_LINKED_ANOMALY_MNEMONIC,
						R4EUIConstants.ADD_LINKED_ANOMALY_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}
			if (element.isOpenEditorCmd() && R4EUIModelController.getNavigatorView().isEditorLinked()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.OPEN_EDITOR_COMMAND,
						R4EUIConstants.OPEN_EDITOR_COMMAND,
						null, ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(R4EUIConstants.OPEN_EDITOR_ICON_FILE)),
						null, null, R4EUIConstants.OPEN_EDITOR_COMMAND_NAME, R4EUIConstants.OPEN_EDITOR_COMMAND_MNEMONIC,
						R4EUIConstants.OPEN_EDITOR_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (element.isChangeReviewStateCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND,
						R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND,
						null, ImageDescriptor.createFromURL(Activator.getDefault().getBundle().getEntry(R4EUIConstants.CHANGE_REVIEW_STATE_ICON_FILE)),
						null, null, R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND_NAME, R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND_MNEMONIC,
						R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}
			
			if (element.isRestoreElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.RESTORE_ELEMENT_COMMAND,
						R4EUIConstants.RESTORE_ELEMENT_COMMAND,
						null, PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_UNDO),
						null, null, R4EUIConstants.RESTORE_ELEMENT_COMMAND_NAME, R4EUIConstants.RESTORE_ELEMENT_COMMAND_MNEMONIC,
						R4EUIConstants.RESTORE_ELEMENT_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}
		} else {
			//When no element is selected, contribute add review group command
			params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
					R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND,
					R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND,
					null, PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD),
					null, null, R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_NAME, R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_MNEMONIC,
					R4EUIConstants.ADD_CHILD_ELEMENT_COMMAND_NAME, CommandContributionItem.STYLE_PUSH, null, true);
			list.add(new CommandContributionItem(params));
		}
		return list.toArray(new IContributionItem[list.size()]);
	}
}
