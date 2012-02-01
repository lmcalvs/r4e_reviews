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
 * This class implements contribution item that provide the sorter commands
 * to the review navigator toolbar sort menu
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.commands.sorters;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.R4EUIConstants;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class SortersContributionItems extends CompoundContributionItem {

	/**
	 * Method getContributionItems.
	 * 
	 * @return IContributionItem[]
	 */
	@Override
	protected IContributionItem[] getContributionItems() {

		final List<IContributionItem> list = new ArrayList<IContributionItem>();

		if (R4EUIModelController.getNavigatorView().isDefaultDisplay()) {

			CommandContributionItemParameter params = new CommandContributionItemParameter(
					R4EUIModelController.getNavigatorView().getSite(), R4EUIConstants.ALPHA_SORTER_COMMAND,
					R4EUIConstants.ALPHA_SORTER_COMMAND, null, ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
							.getBundle()
							.getEntry(R4EUIConstants.ALPHA_SORTER_ICON_FILE)), null, null,
					R4EUIConstants.ALPHA_SORTER_NAME, R4EUIConstants.ALPHA_SORTER_MNEMONIC,
					R4EUIConstants.ALPHA_SORTER_TOOLTIP, CommandContributionItem.STYLE_CHECK, null, true);
			list.add(new CommandContributionItem(params));

			params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
					R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND, R4EUIConstants.REVIEW_TYPE_SORTER_COMMAND, null,
					ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
							.getBundle()
							.getEntry(R4EUIConstants.REVIEW_TYPE_SORTER_ICON_FILE)), null, null,
					R4EUIConstants.REVIEW_TYPE_SORTER_NAME, R4EUIConstants.REVIEW_TYPE_SORTER_MNEMONIC,
					R4EUIConstants.REVIEW_TYPE_SORTER_TOOLTIP, CommandContributionItem.STYLE_CHECK, null, true);
			list.add(new CommandContributionItem(params));
		}

		return list.toArray(new IContributionItem[list.size()]);
	}

}
