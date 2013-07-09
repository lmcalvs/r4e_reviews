/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 	This class implements the implementation of the review table view information.
 * 
 * Contributors:
 *   Jacques Bouthillier - Initial Implementation of the table view information
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e_gerrit.ui.internal.model;

import java.util.ArrayList;

/**
 * @author Jacques Bouthillier
 * @version $Revision: 1.0 $
 * 
 */
// ------------------------------------------------------------------------
// Constants
// ------------------------------------------------------------------------
// Definition of the review table list {name, width of the column, Resizeable,
// Moveable}
public enum ReviewTableDefinition {
	// 			Name 			Width 	Resize Moveable
	   STARRED(	"", 			20, 	false,	true), 
	   ID(		"ID", 			70, 	false,	true), 
	   SUBJECT(	"Subject",	 	200, 	true,	true), 
	   OWNER(	"Owner", 		100, 	true, 	true), 
	   PROJECT(	"Project", 		200, 	true, 	true), 
	   BRANCH(	"Branch", 		100, 	true, 	true), 
	   UPDATED(	"Updated", 		65, 	true, 	true), 
	   CR(		"CR", 			28, 	false, 	true), 
	   IC(		"IC", 			25, 	false, 	true), 
	   VERIFY(	"V", 			25,		false, 	true);

	private String fHeader;
	private int fwidth;
	private Boolean fResize;
	private Boolean fMoveable;

	private ReviewTableDefinition(String aName, int aWidth, Boolean aResize,
			Boolean aMove) {
		fHeader = aName;
		fwidth = aWidth;
		fResize = aResize;
		fMoveable = aMove;
	}

	public String getName() {
		return fHeader;
	}

	public int getWidth() {
		return fwidth;
	}

	public Boolean getResize() {
		return fResize;
	}

	public Boolean getMoveable() {
		return fMoveable;
	}

	public static String[] getColumnName() {
		ArrayList<String> listName = new ArrayList<String>();
		for (ReviewTableDefinition st : ReviewTableDefinition.values()) {
			listName.add(st.getName());
		}
		return listName.toArray(new String[] {});
	}

	// public static int getColumnNumber (String st) {
	// R4EGerritPlugin.Ftracer.traceInfo("getColumnNumber(): " +
	// (ReviewTableDefinition.valueOf(st).ordinal() + 1));
	// //The ordinal starts at zero, so add 1
	// return ReviewTableDefinition.valueOf(st).ordinal();
	// }

}