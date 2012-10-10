/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Ericsson AB - First implementation and API
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;

/**
 * @author Alvaro Sanchez-Leon
 */
public class InactiveSerializationRegistry implements Persistence.IResSerializationRegistry {

	// ------------------------------------------------------------------------
	// Instance Variables
	// ------------------------------------------------------------------------
	public final Set<Resource> fInactiveSerResources = new HashSet<Resource>();

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	public boolean isSerializationInactive(Resource aResource) {
		return fInactiveSerResources.contains(aResource);
	}

	public void addSerializationInactive(Resource aResource) {
		if (aResource != null) {
			fInactiveSerResources.add(aResource);
		}
	}

	public void removeSerializationInactive(Resource aResource) {
		if (aResource != null) {
			fInactiveSerResources.remove(aResource);
		}
	}

	public void clearSerializationInactive() {
		fInactiveSerResources.clear();
	}

}
