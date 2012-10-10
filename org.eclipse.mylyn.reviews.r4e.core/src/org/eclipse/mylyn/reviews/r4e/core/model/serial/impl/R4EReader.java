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
 *   Alvaro Sanchez-Leon - Intial Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IRWUserBasedRes;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.RWCommon;

/**
 * @author Alvaro Sanchez-Leon
 */
public class R4EReader extends RWCommon implements IModelReader {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#deserializeTopElement(org.eclipse.emf.common.util.URI, java.lang.Class)
	 */
	public <T> T deserializeTopElement(URI aRootPath, Class<T> type) throws ResourceHandlingException {
		if (aRootPath == null) {
			return null;
		}

		EList<EObject> eObjects = getObjects(aRootPath);
		return findType(type, eObjects);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#deserializeTopElement(org.eclipse.emf.common.util.URI, org.eclipse.emf.ecore.resource.ResourceSet, java.lang.Class)
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

	// /**
	// * Loads resource at given URI and returns all top elements matching the specified type
	// *
	// * @param <T>
	// * @param aRootPath
	// * @param type
	// * @return
	// * @throws ResourceHandlingException
	// */
	// @SuppressWarnings("unchecked")
	// public <T> List<T> deserializeElements(URI aRootPath, Class<T> type) throws ResourceHandlingException {
	// if (aRootPath == null) {
	// return null;
	// }
	//
	// List<T> resElements = new ArrayList<T>();
	// EList<EObject> eObjects = getObjects(aRootPath);
	//
	// for (EObject eobject : eObjects) {
	// if (type.isAssignableFrom(eobject.getClass())) {
	// resElements.add((T) eobject);
	// }
	// }
	//
	// return resElements;
	// }

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
			Registry reg = EPackage.Registry.INSTANCE;
			resource.load(null);
		} catch (IOException e) {
			String message = new StringBuffer("Unable to load resource at URI: " + resourcePath.toString()).toString();
			ResourceHandlingException exception = new ResourceHandlingException(message, e);
			throw exception;
		}

		EList<EObject> eObjects = resource.getContents();
		return eObjects;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#reloadResource(org.eclipse.emf.ecore.resource.Resource)
	 */
	public void reloadResource(Resource resource) throws ResourceHandlingException {
		if (resource == null) {
			return;
		}

		try {
			resource.load(fOptions);
		} catch (IOException e) {
			StringBuilder sb = new StringBuilder("Unable to load resource");
			URI resURI = resource.getURI();
			if (resURI != null) {
				sb.append(": " + resource.getURI().toString());
			}
			throw new ResourceHandlingException(sb.toString(), e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#selectUsrReviewGroupRes(org.eclipse.emf.common.util.URI)
	 */
	public List<URI> selectUsrReviewGroupRes(URI aFolder) {
		return selectFiles(aFolder, IRWUserBasedRes.USER_GROUP_REVS_PATT);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#selectUsrCommentsRes(org.eclipse.emf.common.util.URI)
	 */
	public List<URI> selectUsrCommentsRes(URI aFolder) {
		return selectFiles(aFolder, IRWUserBasedRes.USER_COMMENTS_PATT);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#selectUsrItemsRes(org.eclipse.emf.common.util.URI)
	 */
	public List<URI> selectUsrItemsRes(URI aFolder) {
		return selectFiles(aFolder, IRWUserBasedRes.USER_ITEMS_PATT);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelReader#isGroupResourceUri(org.eclipse.emf.common.util.URI)
	 */
	public Boolean isGroupResourceUri(URI uri) {
		return uriMatch(uri, IRWUserBasedRes.ROOT_GROUP_PATT);
	}

	/**
	 * @param aFolder
	 * @param pattern
	 * @return
	 */
	private List<URI> selectFiles(URI aFolder, Pattern pattern) {
		Matcher matcher = null;
		List<URI> filesRes = new ArrayList<URI>();
		String path = URI.decode(aFolder.devicePath());
		File folder = new File(path);

		if (folder.exists()) {
			String[] files = folder.list();
			for (String file : files) {
				matcher = pattern.matcher(file);
				if (matcher.find()) {
					filesRes.add(aFolder.appendSegment(file));
				}
			}
		}

		return filesRes;
	}

	/**
	 * @param uri
	 * @param pattern
	 * @return
	 */
	private Boolean uriMatch(URI uri, Pattern pattern) {
		if (uri == null || pattern == null) {
			return null;
		}

		String uriStr = uri.toString();
		Matcher matcher = pattern.matcher(uriStr);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * @param aPathToResource
	 * @param resourceSet
	 * @return
	 */
	private Resource createResource(URI aPathToResource, ResourceSet resourceSet) {
		Resource groupResource = null;

		if (aPathToResource != null && resourceSet != null) {
			// Create the resource for given group
			groupResource = resourceSet.getResource(aPathToResource, true);
		}
		return groupResource;
	}

}
