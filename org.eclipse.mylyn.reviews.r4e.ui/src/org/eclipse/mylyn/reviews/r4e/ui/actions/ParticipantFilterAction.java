// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the action used to filter the displayed element based 
 * on a participant
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.ui.Activator;
import org.eclipse.mylyn.reviews.r4e.ui.filters.ReviewParticipantFilter;
import org.eclipse.mylyn.reviews.r4e.ui.model.R4EUIModelController;
import org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView;
import org.eclipse.mylyn.reviews.r4e.ui.preferences.PreferenceConstants;
import org.eclipse.swt.widgets.Display;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class ParticipantFilterAction extends Action {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	
	/**
	 * Field fView.
	 */
	private final ReviewNavigatorView fView;
	
	/**
	 * Field fFilter.
	 */
	private final ReviewParticipantFilter fFilter;
	
	/**
	 * Field fFilterApplied.
	 */
	private boolean fFilterApplied;
	
	/**
	 * Field fFilterName.
	 */
	private final String fFilterName;
	
	
	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	
	/**
	 * Constructor for ParticipantFilterAction.
	 * @param aView ReviewNavigatorView
	 * @param aFilter ReviewParticipantFilter
	 * @param aFilterName String
	 */
	public ParticipantFilterAction(ReviewNavigatorView aView, ReviewParticipantFilter aFilter, String aFilterName) {
		super(aFilterName, IAction.AS_CHECK_BOX);
	    fFilterName = aFilterName;
		fView = aView;
		fFilter = aFilter;
		fFilterApplied = false;
	}
	
	
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method run.
	 * @see org.eclipse.jface.action.IAction#run()
	 */
	@Override
	public void run() {
		//We need to preserve the expansion state and restore it afterwards
		final Object[] elements =  fView.getTreeViewer().getExpandedElements();
		String participant = null;
		if (!fFilterApplied) {
			
			if (!fFilter.getParticipant().equals(R4EUIModelController.getReviewer())) {
				//Show dialog to get participant name
			    participant = getParticipantDialog();
				fFilter.setParticipant(participant);
				super.setText(fFilterName + " (" + fFilter.getParticipant() + ")");
			} else {
				participant = PreferenceConstants.P_USER_ID;
			}
			Activator.Tracer.traceInfo("Apply participant filter for " + participant);
			fView.getTreeViewer().addFilter(fFilter);
			this.setChecked(true);
			fFilterApplied = true;
		} else {
			Activator.Tracer.traceInfo("Remove participant filter");
			super.setText(fFilterName);
			fView.getTreeViewer().removeFilter(fFilter);
			this.setChecked(false);
			fFilterApplied = false;
		}
		fView.getTreeViewer().setExpandedElements(elements);	
	}
	
	/**
	 * Display the dialog used by the user to enter the participant to use as filter criteria
	 * @return String
	 */
	public String getParticipantDialog() {
			final InputDialog dlg = new InputDialog(Display.getCurrent().getActiveShell(),
					"Set user name", "Enter user name to filter on", null, null);
			if (dlg.open() == Window.OK) {
				return dlg.getValue();
			}
			return "";
	}
	
	/**
	 * Reset the filter status
	 */
	public void reset() {
		this.setChecked(false);
		fFilterApplied = false;
	}
}





