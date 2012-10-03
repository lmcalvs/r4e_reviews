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
 * This class implements contribution item that provide annotation navigation
 *  commands to the Compare editor
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.commands;

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
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EAnnotationContributionItems extends CompoundContributionItem {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getContributionItems.
	 * 
	 * @return IContributionItem[]
	 */
	@Override
	protected IContributionItem[] getContributionItems() {

		final List<IContributionItem> list = new ArrayList<IContributionItem>();

		CommandContributionItemParameter params = new CommandContributionItemParameter(
				R4EUIModelController.getNavigatorView().getSite(), R4EUIConstants.NEXT_ANOMALY_ANNOTATION_COMMAND,
				R4EUIConstants.NEXT_ANOMALY_ANNOTATION_COMMAND, null,
				ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
						.getBundle()
						.getEntry(R4EUIConstants.NEXT_ANOMALY_ANNOTATION_ICON_FILE)), null, null,
				R4EUIConstants.NEXT_ANOMALY_ANNOTATION_COMMAND_NAME,
				R4EUIConstants.NEXT_ANOMALY_ANNOTATION_COMMAND_MNEMONIC,
				R4EUIConstants.NEXT_ANOMALY_ANNOTATION_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null, true);
		list.add(new CommandContributionItem(params));

		params = new CommandContributionItemParameter(R4EUIModelController.getNavigatorView().getSite(),
				R4EUIConstants.PREVIOUS_ANOMALY_ANNOTATION_COMMAND, R4EUIConstants.PREVIOUS_ANOMALY_ANNOTATION_COMMAND,
				null, ImageDescriptor.createFromURL(R4EUIPlugin.getDefault()
						.getBundle()
						.getEntry(R4EUIConstants.PREVIOUS_ANOMALY_ANNOTATION_ICON_FILE)), null, null,
				R4EUIConstants.PREVIOUS_ANOMALY_ANNOTATION_COMMAND_NAME,
				R4EUIConstants.PREVIOUS_ANOMALY_ANNOTATION_COMMAND_MNEMONIC,
				R4EUIConstants.PREVIOUS_ANOMALY_ANNOTATION_COMMAND_TOOLTIP, CommandContributionItem.STYLE_PUSH, null,
				true);
		list.add(new CommandContributionItem(params));

		return list.toArray(new IContributionItem[list.size()]);
	}

	/**
	 * Method getR4EContributionItems.
	 * 
	 * @return IContributionItem[]
	 */
	public IContributionItem[] getR4EContributionItems() {
		return getContributionItems();
	}
}
