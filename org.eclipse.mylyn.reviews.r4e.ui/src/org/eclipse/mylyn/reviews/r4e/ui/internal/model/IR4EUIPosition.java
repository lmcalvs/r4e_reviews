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
 * This interface is used to access a generic position for a review item/
 * anomaly
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.model;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public interface IR4EUIPosition {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method toString. Returns the position as a readable string
	 * 
	 * @return String
	 */
	@Override
	String toString();

	/**
	 * Method isSameAs. Compares 2 positions to see if they are the same (value-wise)
	 * 
	 * @param aPosition
	 *            IR4EPosition
	 * @return boolean
	 */
	boolean isSameAs(IR4EUIPosition aPosition);

	/**
	 * Method setPositionInModel. Set the position in serialization model
	 * 
	 * @param aModelPosition
	 *            R4EPosition
	 * @throws OutOfSyncException
	 * @throws ResourceHandlingException
	 */
	void setPositionInModel(R4EPosition aModelPosition) throws ResourceHandlingException, OutOfSyncException;
}
