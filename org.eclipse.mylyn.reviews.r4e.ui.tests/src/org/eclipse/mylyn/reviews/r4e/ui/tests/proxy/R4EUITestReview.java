/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements a Proxy class used to access/control the UI Review element
 * programatically for test purposes
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.tests.proxy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.eclipse.jface.window.Window;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewType;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.IReviewInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.R4EUIDialogFactory;
import org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs.ReviewInputDialog;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewBasic;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewGroup;

@SuppressWarnings("restriction")
public class R4EUITestReview extends R4EUITestElement {

	public R4EUITestReview(R4EUITestMain aR4EUITestProxy) {
		super(aR4EUITestProxy);
	}

	/**
	 * Create a new Review Group
	 * 
	 * @param aGroup
	 * @param aType
	 * @param aName
	 * @param aDescription
	 * @param aProject
	 * @param aComponents
	 * @param aEntryCriteria
	 * @param aObjectives
	 * @param aRefMat
	 * @return R4EUIReviewBasic
	 */
	public R4EUIReviewBasic createReview(R4EUIReviewGroup aGroup, R4EReviewType aType, String aName,
			String aDescription, String aProject, String[] aComponents, String aEntryCriteria, String aObjectives,
			String aRefMat) {

		//Inject mockup dialog for New Review
		IReviewInputDialog mockReviewDialog = mock(ReviewInputDialog.class);
		R4EUIDialogFactory.getInstance().setReviewInputDialog(mockReviewDialog);

		//Here we need to stub the ReviewInputDialog get methods to return what we want
		when(mockReviewDialog.getReviewTypeValue()).thenReturn(aType);
		when(mockReviewDialog.getReviewNameValue()).thenReturn(aName);
		when(mockReviewDialog.getReviewDescriptionValue()).thenReturn(aDescription);
		when(mockReviewDialog.getProjectValue()).thenReturn(aProject);
		when(mockReviewDialog.getComponentsValues()).thenReturn(aComponents);
		when(mockReviewDialog.getEntryCriteriaValue()).thenReturn(aEntryCriteria);
		when(mockReviewDialog.getObjectivesValue()).thenReturn(aObjectives);
		when(mockReviewDialog.getReferenceMaterialValue()).thenReturn(aRefMat);
		when(mockReviewDialog.open()).thenReturn(Window.OK);

		return (R4EUIReviewBasic) fParentProxy.getCommandProxy().runNewChildElement(aGroup);
	}

	/**
	 * Progress Review
	 * 
	 * @param aReview
	 *            - R4EUIReviewBasic
	 */
	public void progressReview(R4EUIReviewBasic aReview) {
		fParentProxy.getCommandProxy().progressElement(aReview, null);
	}
}
