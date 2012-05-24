/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon  - First API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.IResSerializationState;

/**
 * @author Alvaro Sanchez-Leon
 *
 */
public class ChangeResSetController extends ChangeResController {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	public ChangeResSetController(IResSerializationState aResState) {
		super(aResState);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ChangeResController#checkIn(java.lang.Long)
	 */
	public void checkIn(Long aBookingNumber) throws ResourceHandlingException {
		if (aBookingNumber.equals(INACTIVE_BOOKING)) {
			// Checked-out while resource serialisation was inactive i.e.nothing to do
			return;
		}

		UpdateContext context = checkedOutMap.remove(aBookingNumber);
		// Check if booking still on records
		if (context != null) {
			// if on record SAVE resource
			Resource resource = context.getResource();
			ResourceSet resourceSet = resource.getResourceSet();
			fWriter.saveResources(resourceSet);
			// remove lock
			unlockResource(resource);
		}
	}


}
	