/*******************************************************************************
 * Copyright (c) 2011 Ericsson Research Canada
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This interface is used to inject a dialog used to display and select a date in a calendar
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.dialogs;

import java.util.Date;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public interface ICalendarDialog {

	/**
	 * Method open.
	 * 
	 * @return int
	 */
	int open();

	/**
	 * Method getDate.
	 * 
	 * @return Date
	 */
	Date getDate();

}