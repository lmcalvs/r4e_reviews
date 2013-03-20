/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
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
 *   Jacques Bouthillier - Add contribution element for Report
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class ModelContributionItems extends CompoundContributionItem {

	/**
	 * Method getContributionItems.
	 * 
	 * @return IContributionItem[]
	 */
	@Override
	protected IContributionItem[] getContributionItems() {
		if (null != R4EUIModelController.getNavigatorView()) {
			IStructuredSelection selection = (IStructuredSelection) R4EUIModelController.getNavigatorView()
					.getTreeViewer()
					.getSelection();
			return getContributionItemsParameters((IR4EUIModelElement) selection.getFirstElement());
		}
		return getContributionItemsParameters(null);
	}

	/**
	 * Method getContributionItems.
	 * 
	 * @param aElement
	 *            - IR4EUIModelElement
	 * @return IContributionItem[]
	 */
	public IContributionItem[] getContributionItems(IR4EUIModelElement aElement) {

		//We use a source provider to pass in the R4E UI model element the commands would be applied to
		UIUtils.clearCommandUIElements();
		List<IR4EUIModelElement> uiElements = new ArrayList<IR4EUIModelElement>(1);
		uiElements.add(aElement);
		UIUtils.setCommandUIElements(uiElements);

		//Get Command Parameters
		return getContributionItemsParameters(aElement);
	}

	/**
	 * Method getContributionItemsParameters.
	 * 
	 * @param aElement
	 *            - IR4EUIModelElement
	 * @return IContributionItem[]
	 */
	private IContributionItem[] getContributionItemsParameters(IR4EUIModelElement aElement) {
		final List<IContributionItem> list = new ArrayList<IContributionItem>();
		CommandContributionItemParameter params = null;

		if (null != aElement) {
			if (aElement.isOpenElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.OPEN_ELEMENT_COMMAND, R4EUIConstants.OPEN_ELEMENT_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.OPEN_ELEMENT_ICON_FILE)), null, null,
						aElement.getOpenElementCmdName(), R4EUIConstants.OPEN_ELEMENT_COMMAND_MNEMONIC,
						aElement.getOpenElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isCloseElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.CLOSE_ELEMENT_COMMAND, R4EUIConstants.CLOSE_ELEMENT_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.CLOSE_ELEMENT_ICON_FILE)), null, null,
						aElement.getCloseElementCmdName(), R4EUIConstants.CLOSE_ELEMENT_COMMAND_MNEMONIC,
						aElement.getCloseElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (R4EUIModelController.getNavigatorView().getTreeViewer().getSelection() instanceof IStructuredSelection) {
				Object[] selections = ((IStructuredSelection) R4EUIModelController.getNavigatorView()
						.getTreeViewer()
						.getSelection()).toArray();
				if (selections.length == 2 && selections[0] instanceof R4EUIReviewItem
						&& selections[1] instanceof R4EUIReviewItem && ((R4EUIReviewItem) selections[0]).isEnabled()
						&& ((R4EUIReviewItem) selections[1]).isEnabled()) {
					params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
							R4EUIConstants.COMPARE_ITEMS_COMMAND, R4EUIConstants.COMPARE_ITEMS_COMMAND, null,
							ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
									.getBundle()
									.getEntry(R4EUIConstants.COMPARE_ITEMS_ICON_FILE)), null, null,
							R4EUIConstants.COMPARE_ITEMS_COMMAND_NAME, R4EUIConstants.COMPARE_ITEMS_COMMAND_MNEMONIC,
							R4EUIConstants.COMPARE_ITEMS_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null,
							true);
					list.add(new CommandContributionItem(params));
				}
			}

			if (aElement.isCopyElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.COPY_ELEMENT_COMMAND, R4EUIConstants.COPY_ELEMENT_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.COPY_ELEMENT_ICON_FILE)), null, null,
						aElement.getCopyElementCmdName(), R4EUIConstants.COPY_ELEMENT_COMMAND_MNEMONIC,
						aElement.getCopyElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isPasteElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.PASTE_ELEMENT_COMMAND, R4EUIConstants.PASTE_ELEMENT_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.PASTE_ELEMENT_ICON_FILE)), null, null,
						aElement.getPasteElementCmdName(), R4EUIConstants.PASTE_ELEMENT_COMMAND_MNEMONIC,
						aElement.getPasteElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isNextStateElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND, R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.NEXT_STATE_ELEMENT_ICON_FILE)), null, null,
						aElement.getNextStateElementCmdName(), R4EUIConstants.NEXT_STATE_ELEMENT_COMMAND_MNEMONIC,
						aElement.getNextStateElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isPreviousStateElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND, R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND,
						null, ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.PREVIOUS_STATE_ELEMENT_ICON_FILE)), null, null,
						aElement.getPreviousStateElementCmdName(),
						R4EUIConstants.PREVIOUS_STATE_ELEMENT_COMMAND_MNEMONIC,
						aElement.getPreviousStateElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isNewChildElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND, R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND, null,
						PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD),
						null, null, aElement.getNewChildElementCmdName(),
						R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND_MNEMONIC, aElement.getNewChildElementCmdTooltip(),
						CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isRemoveElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.REMOVE_ELEMENT_COMMAND, R4EUIConstants.REMOVE_ELEMENT_COMMAND, null,
						PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_DELETE),
						null, null, aElement.getRemoveElementCmdName(), R4EUIConstants.REMOVE_ELEMENT_COMMAND_MNEMONIC,
						aElement.getRemoveElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isAddLinkedAnomalyCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.NEW_LINKED_ANOMALY_COMMAND, R4EUIConstants.NEW_LINKED_ANOMALY_COMMAND, null,
						PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD),
						null, null, R4EUIConstants.NEW_LINKED_ANOMALY_NAME, R4EUIConstants.NEW_LINKED_ANOMALY_MNEMONIC,
						R4EUIConstants.NEW_LINKED_ANOMALY_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isOpenEditorCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.OPEN_EDITOR_COMMAND, R4EUIConstants.OPEN_EDITOR_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.OPEN_EDITOR_ICON_FILE)), null, null,
						R4EUIConstants.OPEN_EDITOR_COMMAND_NAME, R4EUIConstants.OPEN_EDITOR_COMMAND_MNEMONIC,
						R4EUIConstants.OPEN_EDITOR_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isChangeUserReviewStateCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND, R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.CHANGE_REVIEW_STATE_ICON_FILE)), null, null,
						aElement.isUserReviewed()
								? R4EUIConstants.UNMARK_REVIEW_STATE_COMMAND_NAME
								: R4EUIConstants.MARK_REVIEW_STATE_COMMAND_NAME,
						R4EUIConstants.CHANGE_REVIEW_STATE_COMMAND_MNEMONIC, aElement.isUserReviewed()
								? R4EUIConstants.UNMARK_REVIEW_STATE_COMMAND_TOOLTIP
								: R4EUIConstants.MARK_REVIEW_STATE_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH,
						null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isRestoreElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.RESTORE_ELEMENT_COMMAND, R4EUIConstants.RESTORE_ELEMENT_COMMAND, null,
						PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_UNDO),
						null, null, aElement.getRestoreElementCmdName(),
						R4EUIConstants.RESTORE_ELEMENT_COMMAND_MNEMONIC, aElement.getRestoreElementCmdTooltip(),
						CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isSendEmailCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.SEND_EMAIL_COMMAND, R4EUIConstants.SEND_EMAIL_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.SEND_EMAIL_ICON_FILE)), null, null,
						R4EUIConstants.SEND_EMAIL_COMMAND_NAME, R4EUIConstants.SEND_EMAIL_COMMAND_MNEMONIC,
						R4EUIConstants.SEND_EMAIL_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isImportPostponedCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.IMPORT_POSTPONED_COMMAND, R4EUIConstants.IMPORT_POSTPONED_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.IMPORT_POSTPONED_ICON_FILE)), null, null,
						R4EUIConstants.IMPORT_POSTPONED_COMMAND_NAME, R4EUIConstants.IMPORT_POSTPONED_COMMAND_MNEMONIC,
						R4EUIConstants.IMPORT_POSTPONED_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isReportElementCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.REPORT_ELEMENT_COMMAND, R4EUIConstants.REPORT_ELEMENT_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.REPORT_ELEMENT_ICON_FILE)), null, null,
						aElement.getReportElementCmdName(), R4EUIConstants.REPORT_ELEMENT_COMMAND_MNEMONIC,
						aElement.getReportElementCmdTooltip(), CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isAssignToCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.ASSIGN_TO_COMMAND, R4EUIConstants.ASSIGN_TO_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.ASSIGN_TO_ICON_FILE)), null, null,
						R4EUIConstants.ASSIGN_TO_COMMAND_NAME, R4EUIConstants.ASSIGN_TO_COMMAND_MNEMONIC,
						R4EUIConstants.ASSIGN_TO_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isUnassignToCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.UNASSIGN_TO_COMMAND, R4EUIConstants.UNASSIGN_TO_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.UNASSIGN_TO_ICON_FILE)), null, null,
						R4EUIConstants.UNASSIGN_TO_COMMAND_NAME, R4EUIConstants.UNASSIGN_TO_COMMAND_MNEMONIC,
						R4EUIConstants.UNASSIGN_TO_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (null != R4EUIModelController.getNavigatorView() && aElement.isShowPropertiesCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.SHOW_PROPERTIES_COMMAND, R4EUIConstants.SHOW_PROPERTIES_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.SHOW_PROPERTIES_ICON_FILE)), null, null,
						R4EUIConstants.SHOW_PROPERTIES_COMMAND_NAME, R4EUIConstants.SHOW_PROPERTIES_COMMAND_MNEMONIC,
						R4EUIConstants.SHOW_PROPERTIES_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isListReviewsCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.GERRIT_LIST_REVIEWS_COMMAND, R4EUIConstants.GERRIT_LIST_REVIEWS_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.CLOSE_ELEMENT_ICON_FILE)), null, null,
						R4EUIConstants.GERRIT_LIST_REVIEWS_COMMAND_NAME, " ",
						R4EUIConstants.GERRIT_LIST_REVIEWS_COMMAND, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isPushReviewCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.GERRIT_PUSH_REVIEW_COMMAND, R4EUIConstants.GERRIT_PUSH_REVIEW_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.CLOSE_ELEMENT_ICON_FILE)), null, null,
						R4EUIConstants.GERRIT_PUSH_REVIEW_COMMAND_NAME, " ", R4EUIConstants.GERRIT_PUSH_REVIEW_COMMAND,
						CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isFetchReviewCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.GERRIT_FETCH_REVIEW_COMMAND, R4EUIConstants.GERRIT_FETCH_REVIEW_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.CLOSE_ELEMENT_ICON_FILE)), null, null,
						R4EUIConstants.GERRIT_FETCH_REVIEW_COMMAND_NAME, " ",
						R4EUIConstants.GERRIT_FETCH_REVIEW_COMMAND, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

			if (aElement.isDeleteReviewCmd()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.GERRIT_DELETE_REVIEW_COMMAND, R4EUIConstants.GERRIT_DELETE_REVIEW_COMMAND, null,
						ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
								.getBundle()
								.getEntry(R4EUIConstants.CLOSE_ELEMENT_ICON_FILE)), null, null,
						R4EUIConstants.GERRIT_DELETE_REVIEW_COMMAND_NAME, " ",
						R4EUIConstants.GERRIT_DELETE_REVIEW_COMMAND, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}

		} else {
			//When no element is selected, contribute add review group command
			if (R4EUIModelController.getNavigatorView().isDefaultDisplay()) {
				params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
						R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND, R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND, null,
						PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ADD),
						null, null, R4EUIConstants.NEW_REVIEW_GROUP_COMMAND_NAME,
						R4EUIConstants.NEW_CHILD_ELEMENT_COMMAND_MNEMONIC,
						R4EUIConstants.NEW_REVIEW_GROUP_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
				list.add(new CommandContributionItem(params));
			}
		}
		return list.toArray(new IContributionItem[list.size()]);
	}
}
