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
 * This class implements the Delta Container element of the UI model
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.emf.common.util.EList;
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

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EUIDeltaContainer extends R4EUIContentsContainer {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field DELTA_CONTAINER_ICON_FILE. (value is ""icons/obj16/selcont_obj.gif"")
	 */
	public static final String DELTA_CONTAINER_ICON_FILE = "icons/obj16/deltacont_obj.gif";

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EUIDeltaContainer.
	 * 
	 * @param aParent
	 *            IR4EUIModelElement
	 * @param aName
	 *            String
	 */
	public R4EUIDeltaContainer(IR4EUIModelElement aParent, String aName) {
		super(aParent, aName);
		setImage(DELTA_CONTAINER_ICON_FILE);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method createDelta
	 * 
	 * @param aUiPosition
	 *            - the position of the anomaly to create
	 * @return R4EUIDelta
	 * @throws ResourceHandlingException
	 * @throws OutOfSyncException
	 */
	public R4EUIDelta createDelta(IR4EUIPosition aUiPosition) throws ResourceHandlingException, OutOfSyncException {

		//Create and set content model element
		final R4EDelta delta = R4EUIModelController.FModelExt.createR4EDelta(((R4EUIFileContext) getParent()).getFileContext());

		R4EPosition position = null;
		if (aUiPosition instanceof R4EUITextPosition) {
			position = R4EUIModelController.FModelExt.createR4ETextPosition(R4EUIModelController.FModelExt.createR4ETargetTextContent(delta));
			aUiPosition.setPositionInModel(position);
		} else if (aUiPosition instanceof R4EUIModelPosition) {
			position = R4EUIModelController.FModelExt.createR4EModelPosition(R4EUIModelController.FModelExt.createR4ETargetTextContent(delta));
			aUiPosition.setPositionInModel(position);
		}

		//Create and set UI model element
		final R4EUIDelta uiDelta = new R4EUIDelta(this, delta, aUiPosition);
		addChildren(uiDelta);
		return uiDelta;
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method open.
	 */
	@Override
	public void open() {
		final EList<R4EDelta> deltas = ((R4EUIFileContext) getParent()).getFileContext().getDeltas();
		if (null != deltas) {
			IR4EUIPosition uiPosition = null;
			R4EUIDelta newDelta = null;
			final int deltaSize = deltas.size();
			R4EDelta delta = null;
			for (int i = 0; i < deltaSize; i++) {
				delta = deltas.get(i);
				if (delta.isEnabled()
						|| R4EUIPlugin.getDefault()
								.getPreferenceStore()
								.getBoolean(PreferenceConstants.P_SHOW_DISABLED)) {
					R4EPosition position = deltas.get(i).getTarget().getLocation();
					if (position instanceof R4EModelPosition) {
						uiPosition = new R4EUIModelPosition((R4EModelPosition) position);
					} else if (position instanceof R4ETextPosition) {
						uiPosition = new R4EUITextPosition((R4ETextPosition) position);
					}
					newDelta = new R4EUIDelta(this, deltas.get(i), uiPosition);
					addChildren(newDelta);
				}
			}

			try {
				final R4EUIReviewBasic review = (R4EUIReviewBasic) getParent().getParent().getParent();
				final R4EParticipant user = review.getParticipant(R4EUIModelController.getReviewer(), false);

				if (null != user) {
					//Check if the file contexts are part of the reviewed content
					for (R4EUIContent uiDelta : fContents) {
						if (user.getReviewedContent().contains(uiDelta.getContent().getId())) {
							uiDelta.setUserReviewed(true, true);
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
