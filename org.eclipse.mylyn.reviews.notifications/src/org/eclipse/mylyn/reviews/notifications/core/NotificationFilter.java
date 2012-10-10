/**
 * Copyright (c) 2011, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Ericsson AB - Initial API and implementation
 * 
 */
package org.eclipse.mylyn.reviews.notifications.core;

import java.util.ArrayList;

/**
 * @author Alvaro Sanchez-Leon
 */
public abstract class NotificationFilter {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * @param criteria
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] filter(Object criteria, Object[] elements) {
		int size = elements.length;
		@SuppressWarnings("rawtypes")
		ArrayList filtered = new ArrayList(size);
		for (int i = 0; i < size; ++i) {
			Object element = elements[i];
			if (select(criteria, element)) {
				filtered.add(element);
			}
		}

		return filtered.toArray();
	}

	/**
	 * @param aCriteria
	 * @param aElement
	 * @return - true for accepted
	 */
	public abstract boolean select(Object aCriteria, Object aElement);
}
