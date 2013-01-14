// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the Selection Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Display;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EUISelectionContainer extends R4EUIContentsContainer {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field SELECTION_CONTAINER_ICON_FILE. (value is ""icons/obj16/selcont_obj.gif"")
	 */
	public static final String SELECTION_CONTAINER_ICON_FILE = "icons/obj16/selcont_obj.gif";

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for SelectionContainerElement.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aName
	 *            String
	 */
	public R4EUISelectionContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method getImageLocation.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement#getImageLocation()
	 */
	public String getImageLocation() {
		return SELECTION_CONTAINER_ICON_FILE;
	}

	/**
	 * Method createSelection
	 * 
	 * @param aUiPosition
	 *            - the position of the anomaly to create
	 * @return R4EUISelection
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUISelection createSelection(R4EUITextPosition aUiPosition) throws ResourceHandlingException,
			OutOfSyncException {

		//Create and set content model element
		final R4EDelta selection = R4EUIModelController.FModelExt.createR4EDelta(((R4EUIFileContext) getParent()).getFileContext());
		final R4ETextPosition position = R4EUIModelController.FModelExt.createR4ETextPosition(R4EUIModelController.FModelExt.createR4ETargetTextContent(selection));
		aUiPosition.setPositionInModel(position);

		//Create and set UI model element
		final R4EUISelection uiSelection = new R4EUISelection(this, selection, aUiPosition);
		addChildren(uiSelection);

		//If parent file is assigned, assign the Selection too
		final List<String> parentAssignedParticipants = ((R4EUIFileContext) getParent()).getFileContext()
				.getAssignedTo();
		final List<R4EParticipant> participantsToAssign = new ArrayList<R4EParticipant>();
		for (String parentAssignedParticipant : parentAssignedParticipants) {
			R4EParticipant participant = R4EUIModelController.getActiveReview().getParticipant(
					parentAssignedParticipant, false);
			if (null != participant) {
				participantsToAssign.add(participant);
			}
		}
		uiSelection.addAssignees(participantsToAssign);

		//Add selection to editor (if applicable)
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				UIUtils.addAnnotation(uiSelection, (R4EUIFileContext) getParent());
			}
		});
		return uiSelection;
	}

	/**
	 * Method open.
	 */
	@Override
	public void open() {
		final List<R4EDelta> selections = ((R4EUIFileContext) getParent()).getFileContext().getDeltas();
		if (null != selections) {
			IR4EUIPosition uiPosition = null;
			R4EUISelection newSelection = null;
			final int selectionsSize = selections.size();
			R4EDelta selection = null;
			for (int i = 0; i < selectionsSize; i++) {
				selection = selections.get(i);
				if (selection.isEnabled()
						|| R4EUIPlugin.getDefault()
								.getPreferenceStore()
								.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					R4EPosition pos = selections.get(i).getTarget().getLocation();
					if (pos instanceof R4ETextPosition) {
						uiPosition = new R4EUITextPosition(((R4ETextPosition) pos));
					} else if (pos instanceof R4EModelPosition) {
						uiPosition = new R4EUIModelPosition((R4EModelPosition) pos);
					}
					newSelection = new R4EUISelection(this, selections.get(i), uiPosition);
					addChildren(newSelection);
				}
			}
		}
		fOpen = true;
	}
}
