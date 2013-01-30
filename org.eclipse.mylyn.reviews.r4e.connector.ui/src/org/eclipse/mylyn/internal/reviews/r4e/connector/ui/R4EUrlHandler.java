/*******************************************************************************
 * Copyright (c) 2013 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Miles Parker, Tasktop Technologies - Initial API and Implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.reviews.r4e.connector.ui;

import org.eclipse.mylyn.internal.reviews.r4e.connector.R4EConnectorPlugin;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.ui.EmfUrlHandler;

/**
 * @author Miles Parker
 */
public class R4EUrlHandler extends EmfUrlHandler {

	@Override
	protected AbstractEmfConnector getEmfConnector() {
		return R4EConnectorPlugin.getDefault().getConnector();
	}
}
