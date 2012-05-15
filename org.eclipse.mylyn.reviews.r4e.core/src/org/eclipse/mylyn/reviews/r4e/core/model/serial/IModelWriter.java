/**
 * Copyright (c) 2012 Ericsson AB and others.
 *  
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ericsson AB
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IRWUserBasedRes.ResourceType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;

/**
 * @author Alvaro Sanchez-Leon
 *
 */
public interface IModelWriter {

	/**
	 * @param resourceSet
	 */
	public abstract void saveResources(ResourceSet resourceSet) throws ResourceHandlingException;

	/**
	 * @param resource
	 * @throws ResourceHandlingException
	 */
	public abstract void saveResource(Resource resource) throws ResourceHandlingException;

	/**
	 * Convert the given folder URI to a review file path with folder, file name and extension e.g. for a review
	 * groupPath "file://c:/folder shall become file://c:/folder/validReviewName/validReviewName_review.xrer
	 * 
	 * @param review
	 * @param containerPath
	 * @return
	 */
	public abstract URI createResourceURI(String name, URI containerPath, ResourceType resourceType);

	/**
	 * Convert String to a valid file name by replacing invalid characters by '_'
	 * 
	 * @param stValue
	 * @return
	 */
	public abstract String toValidFileName(String stValue);

	/**
	 * Create ResourceSet with a new Resource at the specified URI
	 * 
	 * @param aPathToResource
	 * @return
	 */
	public Resource createResourceSetWithResource(URI aPathToResource);
}