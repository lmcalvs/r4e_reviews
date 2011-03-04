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
 * This class defines a listener that is used to notify users that 
 * an EditableListWidget they use was updated
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.ui.utils;

import org.eclipse.swt.widgets.Item;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public interface IEditableListListener {
	
	/**
	 * Method itemAdded.
	 * @param aItems Item[]
	 * @param aInstanceId int
	 */
	void itemsUpdated(Item[] aItems, int aInstanceId);
}
