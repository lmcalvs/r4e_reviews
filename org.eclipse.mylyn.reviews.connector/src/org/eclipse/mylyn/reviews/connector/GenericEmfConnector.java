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
 *   Others (includes code modified from GerritConnector in org.eclipse.mylyn.gerit, see author annotations.)
 *******************************************************************************/
package org.eclipse.mylyn.reviews.connector;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * Generic support for connectors to common Emf model implementations. Connector implementors should override, providing
 * additional meta-data.
 * 
 * @author Miles Parker
 */
public abstract class GenericEmfConnector extends AbstractEmfConnector {

	@Override
	public String getNextTaskId(EObject taskContainer) {
		Object value = taskContainer.eGet(getTaskKeySequenceAttribute());
		if (value instanceof Integer) {
			Integer key = (Integer) value;
			key++;
			taskContainer.eSet(getTaskKeySequenceAttribute(), key);
			return key + "";
		}
		throw new IllegalArgumentException("Task key attribute is not an EInt: " //$NON-NLS-1$
				+ getTaskKeySequenceAttribute().getName());
	}

	/**
	 * Expected to be of type EInt.
	 * 
	 * @return
	 */
	public abstract EAttribute getTaskKeySequenceAttribute();
}
