/*******************************************************************************
 * Copyright (c) 2012 Ericsson Research Canada and others
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements the position interface to manage text positions
 * within a file under review.
 * 
 * Contributors:
 *   Cedric Notot - Obeo
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.compare.structuremergeviewer.DiffElement;
import org.eclipse.emf.compare.match.eobject.EObjectIndex.Side;
import org.eclipse.emf.compare.utils.DiffUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EModelPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;

/**
 * @author cnotot
 */
public class R4EUIModelPosition implements IR4EUIPosition {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------
	private R4EModelPosition fPosition;

	private final String fObjectID;

	private final String fDescription;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public R4EUIModelPosition(R4EModelPosition aModelPosition) {
		fPosition = aModelPosition;
		fObjectID = aModelPosition.getObjectID();
		fDescription = aModelPosition.getDescription();
	}

	public R4EUIModelPosition(DiffElement aDiff) {
		EObject obj = DiffUtil.getElement(aDiff, Side.LEFT, EObject.class);
		fObjectID = obj.eResource().getURIFragment(obj);
		fDescription = AdapterUtils.getItemProviderText(aDiff);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	// Attributes

	public String getObjectID() {
		return fObjectID;
	}

	/**
	 * Method setPositionInModel.
	 * 
	 * @param aModelPosition
	 *            R4EPosition
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition#setPositionInModel(R4EPosition)
	 */
	public void setPositionInModel(R4EPosition aModelPosition) throws ResourceHandlingException, OutOfSyncException {
		fPosition = (R4EModelPosition) aModelPosition;
		Long bookNum = R4EUIModelController.FResourceUpdater.checkOut(fPosition, R4EUIModelController.getReviewer());
		fPosition.setObjectID(fObjectID);
		fPosition.setDescription(fDescription);
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);

		final R4EContent content = (R4EContent) fPosition.eContainer();
		bookNum = R4EUIModelController.FResourceUpdater.checkOut(content, R4EUIModelController.getReviewer());
		content.setInfo(((R4EContent) fPosition.eContainer()).getInfo());
		R4EUIModelController.FResourceUpdater.checkIn(bookNum);
	}

	/**
	 * Method isSameAs.
	 * 
	 * @param aPosition
	 *            IR4EPosition
	 * @return boolean
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition#isSameAs(IR4EUIPosition)
	 */
	public boolean isSameAs(IR4EUIPosition aPosition) {
		if (!(aPosition instanceof R4EUIModelPosition)) {
			return false;
		}

		return fObjectID.equals(((R4EUIModelPosition) aPosition).getObjectID());
	}

	/**
	 * Method toString.
	 * 
	 * @return String
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition#toString()
	 */
	@Override
	public String toString() {
		return fDescription;
	}
}
