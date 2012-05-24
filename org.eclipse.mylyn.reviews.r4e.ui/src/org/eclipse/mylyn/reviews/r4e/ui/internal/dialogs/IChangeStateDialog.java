/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This interface is used to inject the dialog used to get the next review phase to transition to
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IChangeStateDialog {

	/**
	 * Method create.
	 */
	void create();

	/**
	 * Method open.
	 * 
	 * @return int
	 */
	int open();

	/**
	 * Returns new state.
	 * 
	 * @return the new state as a String
	 */
	String getState();

	/**
	 * Method setPhases.
	 * 
	 * @param aStates
	 *            String[]
	 */
	void setStates(String[] aStates);

}