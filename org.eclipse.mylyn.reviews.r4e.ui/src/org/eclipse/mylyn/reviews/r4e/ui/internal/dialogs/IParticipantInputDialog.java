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
 * This interface is used to inject the dialog used to fill-in the Participant element details.
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import java.util.List;

import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public interface IParticipantInputDialog {

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
	 * Method close.
	 * 
	 * @return boolean
	 */
	boolean close();

	/**
	 * Method getParticipants.
	 * 
	 * @return List<R4EParticipant>
	 */
	List<R4EParticipant> getParticipants();

	/**
	 * Method addParticipant.
	 * 
	 * @param aParticipant
	 *            - String
	 */
	void addParticipant(String aParticipant);
}