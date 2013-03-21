/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 ******************************************************************************/


//This is a test file used by the R4E UI Test framework

package org.eclipse.mylyn.reviews.r4e.ui;

public class extModCommitFile {
	
	private int someIntMod;
	
	public extModCommitFile {
	}
	
	public setSomeInt(int someInt) {
		this.someIntMod = someInt;
	}
	
	public addSomeIntModified() {
		this.someIntMod += this.someIntMod;
	}
}