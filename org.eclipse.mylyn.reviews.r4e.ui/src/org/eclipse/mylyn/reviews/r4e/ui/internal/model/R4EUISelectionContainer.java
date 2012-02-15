// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
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
 * This class implements the Selection Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.ui.R4EUIPlugin;
import org.eclipse.mylyn.reviews.r4e.ui.internal.preferences.PreferenceConstants;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;

/**
 * @author lmcdubo
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
		setImage(SELECTION_CONTAINER_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

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
		EList<String> parentAssignedParticipants = ((R4EUIFileContext) getParent()).getFileContext().getAssignedTo();
		List<R4EParticipant> participantsToAssign = new ArrayList<R4EParticipant>();
		for (String parentAssignedParticipant : parentAssignedParticipants) {
			R4EParticipant participant = R4EUIModelController.getActiveReview().getParticipant(
					parentAssignedParticipant, false);
			if (null != participant) {
				participantsToAssign.add(participant);
			}
		}
		uiSelection.addAssignees(participantsToAssign);
		return uiSelection;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method open.
	 */
	@Override
	public void open() {
		final EList<R4EDelta> selections = ((R4EUIFileContext) getParent()).getFileContext().getDeltas();
		if (null != selections) {
			R4EUITextPosition position = null;
			R4EUISelection newSelection = null;
			final int selectionsSize = selections.size();
			R4EDelta selection = null;
			for (int i = 0; i < selectionsSize; i++) {
				selection = selections.get(i);
				if (selection.isEnabled()
						|| R4EUIPlugin.getDefault()
								.getPreferenceStore()
								.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					position = new R4EUITextPosition(selections.get(i).getTarget().getLocation());
					newSelection = new R4EUISelection(this, selections.get(i), position);
					addChildren(newSelection);
				}
			}

			try {
				final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent().getParent();
				final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

				if (null != user) {
					//Check if the file contexts are part of the reviewed content
					for (R4EUIContent uiSelection : fContents) {
						if (user.getReviewedContent().contains(uiSelection.getContent().getId())) {
							uiSelection.setUserReviewed(true, true);
						}
					}
				}
			} catch (ResourceHandlingException e) {
				UIUtils.displayResourceErrorDialog(e);

			} catch (OutOfSyncException e) {
				UIUtils.displaySyncErrorDialog(e);

			}
		}
		fOpen = true;
	}
}
