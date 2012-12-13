/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
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
package org.eclipse.mylyn.internal.reviews.example.emftasks.ui;

import org.eclipse.mylyn.internal.reviews.example.emftasks.core.EmfExampleCorePlugin;
import org.eclipse.mylyn.reviews.connector.AbstractEmfConnector;
import org.eclipse.mylyn.reviews.connector.ui.EmfUrlHandler;

/**
 * @author Miles Parker
 */
public class EmfExampleUrlHandler extends EmfUrlHandler {

	@Override
	protected AbstractEmfConnector getEmfConnector() {
		return EmfExampleCorePlugin.getDefault().getConnector();
	}
}
