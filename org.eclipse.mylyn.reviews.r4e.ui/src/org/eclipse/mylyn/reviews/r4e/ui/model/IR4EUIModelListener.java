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
 * This interface defines the listener methods used to report model change events
 * to the UI
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.model;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */

public interface IR4EUIModelListener {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method addEvent.
	 * 
	 * @param aEvent
	 *            ReviewModelEvent
	 */
	void addEvent(R4EUIModelEvent aEvent);

	/**
	 * Method removeEvent.
	 * 
	 * @param aEvent
	 *            ReviewModelEvent
	 */
	void removeEvent(R4EUIModelEvent aEvent);

	/**
	 * Method changedEvent.
	 * 
	 * @param aEvent
	 *            ReviewModelEvent
	 */
	void changedEvent(R4EUIModelEvent aEvent);
}
