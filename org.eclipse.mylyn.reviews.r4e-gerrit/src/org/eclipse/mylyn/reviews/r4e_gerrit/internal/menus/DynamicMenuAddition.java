/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the Dynamic menu selection to pre-filled the list of gerrit
 * 	project locations.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the dynamic menu selection
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e_gerrit.internal.menus;

import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.mylyn.reviews.r4e_gerrit.R4EGerritPlugin;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EGerritServerUtility;
import org.eclipse.mylyn.reviews.r4e_gerrit.internal.utils.R4EUIConstants;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */
public class DynamicMenuAddition extends CompoundContributionItem implements IWorkbenchContribution {


	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	
	/**
	 * Field SELECT_PICTURE_FILE. (value is ""icons/select.png"")
	 */
//	private String SELECT_PICTURE_FILE = "icons/select.png";
	private String SELECT_PICTURE_FILE = "icons/select.gif";
	
	// ------------------------------------------------------------------------
	// Variables
	// ------------------------------------------------------------------------

	private IServiceLocator fServiceLocator;
	
	private R4EGerritServerUtility fServer = null;
	
	private Map<Repository, String> fMapServer = null;
	
	private ImageDescriptor fSelectPicture = null;
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	@Override
	protected IContributionItem[] getContributionItems() {
		
		R4EGerritPlugin.Ftracer.traceInfo("\t\t DynamicMenuAddition .getContributionItems()" );
		 CommandContributionItem[] contributionItems = new CommandContributionItem[0];
		 if (fServer != null) {
			 fMapServer = fServer.getGerritMapping();			 
		 }
		 
		if (fMapServer != null && !fMapServer.isEmpty()) {
				Set<Repository> mapSet = fMapServer.keySet();
				String lastSelected = fServer.getLastSavedGerritServer();
				R4EGerritPlugin.Ftracer.traceInfo("-------------------");
				int size = mapSet.size();
				contributionItems = new CommandContributionItem[size];

				int count = 0;
				for (Repository key: mapSet) {
					R4EGerritPlugin.Ftracer.traceInfo("Map Key: " + key.getWorkTree().getName() + "\t URL: " + fMapServer.get(key));
					CommandContributionItemParameter contributionParameter = new CommandContributionItemParameter(fServiceLocator, 
									fMapServer.get(key), R4EUIConstants.ADD_GERRIT_SITE_COMMAND_ID,  
									CommandContributionItem.STYLE_PUSH);  
					contributionParameter.label = key.getWorkTree().getName(); 
					contributionParameter.visibleEnabled = true; 
					if (lastSelected != null && lastSelected.equals(fMapServer.get(key)) ) {
						fSelectPicture = R4EGerritPlugin.getImageDescriptor(SELECT_PICTURE_FILE);

						contributionParameter.icon = fSelectPicture;
						
					}
					contributionItems[count++] = new CommandContributionItem(contributionParameter) ;
				}
			}

		return contributionItems;
	}

	@Override
	public void initialize(IServiceLocator aServiceLocator) {
		fServiceLocator = aServiceLocator;
		
		//Read the Gerrit potential servers
		fServer = new R4EGerritServerUtility();
		R4EGerritPlugin.Ftracer.traceInfo("\t\t DynamicMenuAddition .initialize()()" );
		
	}


}
