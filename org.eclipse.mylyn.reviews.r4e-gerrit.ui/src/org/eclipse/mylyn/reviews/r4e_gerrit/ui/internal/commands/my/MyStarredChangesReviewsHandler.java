/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the R4E-Gerrit UI my starred changes reviews handler.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the plug-in handler
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.commands.my;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.mylyn.reviews.r4e_gerrit.ui.R4EGerritUi;
import org.eclipse.mylyn.reviews.r4egerrit.ui.views.R4EGerritTableView;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 *
 */

public class MyStarredChangesReviewsHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent aEvent) throws ExecutionException {
		R4EGerritUi.Ftracer.traceInfo("Search the Gerrit reviews for My Starred Changes  " ); //$NON-NLS-1$

		R4EGerritTableView reviewTableView = R4EGerritTableView
				.getActiveView();

		// see http://gerrit-documentation.googlecode.com/svn/Documentation/2.5.2/user-search.html
		//for My > Starred Changess--> is:starred
		R4EGerritUi.Ftracer.traceInfo("Execute command :   "  +  "My > Starred Changes"); //$NON-NLS-1$
		reviewTableView.processCommands("is:starred");

//		R4EGerritUi.Ftracer.traceInfo("Execute:   "  ); //$NON-NLS-1$
//		try {
//			UIUtils.notInplementedDialog(aEvent.getCommand().getName());
//		} catch (NotDefinedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return null;
	}

}
