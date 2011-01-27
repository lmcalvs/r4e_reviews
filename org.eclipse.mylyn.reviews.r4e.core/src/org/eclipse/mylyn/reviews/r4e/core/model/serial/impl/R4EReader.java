/*******************************************************************************
 * Copyright (c) 2010 Ericsson
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * Contributors:
 *   Alvaro Sanchez-Leon - Intial Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * @author lmcalvs
 * 
 */
public class R4EReader extends Common {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Deserialize the resource at the given URI and look for the first element of the given type at the first level of
	 * the objects tree. A brand new ResrouceSet and Resource is created to hold the returned element
	 * 
	 * @param <T>
	 * @param aRootPath
	 * @param type
	 * @return
	 * @throws ResourceHandlingException
	 */
	public <T> T deserializeTopElement(URI aRootPath, Class<T> type) throws ResourceHandlingException {
		if (aRootPath == null) {
			return null;
		}

		EList<EObject> eObjects = getObjects(aRootPath);
		return findType(type, eObjects);
	}

	/**
	 * Deserialize the resource at the given URI and look for the first element of the given type at the first level of
	 * the objects tree. The received resourceSet is used to hold the new Resource which is associated to the returned
	 * element
	 * 
	 * @param <T>
	 * @param aRootPath
	 * @param resource
	 * @param type
	 * @return
	 * @throws ResourceHandlingException
	 */
	public <T> T deserializeTopElement(URI aRootPath, ResourceSet resSet, Class<T> type)
			throws ResourceHandlingException {
		if (aRootPath == null) {
			return null;
		}

		EList<EObject> eObjects = getObjects(aRootPath, resSet);
		return findType(type, eObjects);
	}

	/**
	 * @param <T>
	 * @param type
	 * @param eObjects
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T findType(Class<T> type, EList<EObject> eObjects) {
		T element = null;
		for (EObject eobject : eObjects) {
			if (type.isAssignableFrom(eobject.getClass())) {
				element = (T) eobject;
				break;
			}
		}
		return element;
	}

	/**
	 * Loads resource at given URI and returns all top elements matching the specified type
	 * 
	 * @param <T>
	 * @param aRootPath
	 * @param type
	 * @return
	 * @throws ResourceHandlingException
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> deserializeElements(URI aRootPath, Class<T> type) throws ResourceHandlingException {
		if (aRootPath == null) {
			return null;
		}

		List<T> resElements = new ArrayList<T>();
		EList<EObject> eObjects = getObjects(aRootPath);

		for (EObject eobject : eObjects) {
			if (type.isAssignableFrom(eobject.getClass())) {
				resElements.add((T) eobject);
			}
		}

		return resElements;
	}

	/**
	 * Load resource from given URI
	 * 
	 * @param resourcePath
	 * @return
	 * @throws ResourceHandlingException
	 */
	private EList<EObject> getObjects(URI resourcePath) throws ResourceHandlingException {
		// Create the resouce pointing to the specified URI
		Resource resource = createResourceSetWithResource(resourcePath);
		return getObjects(resourcePath, resource);
	}

	/**
	 * @param resourcePath
	 * @param resSet
	 * @return
	 * @throws ResourceHandlingException
	 */
	private EList<EObject> getObjects(URI resourcePath, ResourceSet resSet) throws ResourceHandlingException {
		Resource resource = createResource(resourcePath, resSet);
		return getObjects(resourcePath, resource);
	}

	/**
	 * @param resourcePath
	 * @param resource
	 *            - resource associated to one to be loaded
	 * @return
	 * @throws ResourceHandlingException
	 */
	private EList<EObject> getObjects(URI resourcePath, Resource resource) throws ResourceHandlingException {
		// Load resources
		try {
			resource.load(null);
		} catch (IOException e) {
			String message = new StringBuffer("Unable to load resource at URI: " + resourcePath.toString()).toString();
			ResourceHandlingException exception = new ResourceHandlingException(message, e);
			throw exception;
		}

		EList<EObject> eObjects = resource.getContents();
		return eObjects;
	}

}
	