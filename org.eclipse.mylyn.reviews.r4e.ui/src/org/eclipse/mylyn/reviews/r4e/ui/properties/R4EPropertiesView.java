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
 * This class extends the Eclipse properties view to provide an R4E-specific 
 * properties view
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.properties;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.PropertySheet;

/**
 * @author lmcdubo
 * @version $Revision: 1.0 $
 */
public class R4EPropertiesView extends PropertySheet {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	
	/**
	 * Method isImportant.
	 * @param part IWorkbenchPart
	 * @return boolean
	 */
	@Override
	 protected boolean isImportant(IWorkbenchPart part) {
	  if (part.getSite().getId().equals("org.eclipse.mylyn.reviews.r4e.ui.navigator.ReviewNavigatorView")) return true;
	  return false;
	 }
}
