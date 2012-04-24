/*******************************************************************************
 * Copyright (c) 2011 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Ericsson AB - Intial Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.internal.transform.serial.impl;

import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.R4EWriter;

/**
 * @author Alvaro Sanchez-Leon
 */
public class TResWriter extends R4EWriter implements IModelWriter {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	//From parent
//	private final Map<ResourceType, String> fresTypeToTag = new HashMap<ResourceType, String>();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public TResWriter(Persistence.IResSerializationState aResState) {
		super(aResState);
		//TODO: Solution changed and there is no variation with default writer at the moment, 
		//the instance is left to allow a variation of implementation at a later point.
		
		//From parent
		// Build a lookup table to facilitate the selection of the proper resource tag
//		fresTypeToTag.put(ResourceType.USER_COMMENT, IRWUserBasedRes.REVIEW_UCOMMENT_TAG);
//		fresTypeToTag.put(ResourceType.USER_ITEM, IRWUserBasedRes.REVIEW_UITEM_TAG);
//		fresTypeToTag.put(ResourceType.REVIEW, IRWUserBasedRes.REVIEW_RES_TAG);
//		fresTypeToTag.put(ResourceType.USER_GROUP, IRWUserBasedRes.GROUP_UREVIEW_TAG);
//		fresTypeToTag.put(ResourceType.GROUP, IRWUserBasedRes.GROUP_ROOT_TAG);
//		fresTypeToTag.put(ResourceType.DRULE_SET, IRWUserBasedRes.DRULE_SET_TAG);
	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelWriter#saveResources(org.eclipse.emf.ecore.resource.ResourceSet)
//	 */
//	@Override
//	public void saveResources(ResourceSet resourceSet) throws ResourceHandlingException {
//		// Indicate to save the schema location within the resource files
//		// options = new HashMap<String, Boolean>();
//		// options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
//
//		EList<Resource> resources = resourceSet.getResources();
//		if (resources != null) {
//			for (Resource resource : resources) {
//				saveResource(resource);
//			}
//		}
//	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelWriter#saveResource(org.eclipse.emf.ecore.resource.Resource)
//	 */
//	@Override
//	public void saveResource(Resource resource) throws ResourceHandlingException {
//		// Indicate to save the schema location within the resource files
//		// options = new HashMap<String, Boolean>();
//		// options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
//		StringBuilder message = new StringBuilder();
//		URI resUri = resource.getURI();
//		if (resUri == null) {
//			message.append("Not possible to save a Resource with URI= null");
//			throw new ResourceHandlingException(message.toString());
//		}
//
//		// Mark new folder creation
//		URI folderUri = ResourceUtils.getFolderPath(resUri);
//		File folder = new File(folderUri.toString());
//		boolean newFolder = !folder.exists();
//
//		// Mark new file creation
//		File file = new File(URI.decode(resUri.devicePath()));
//		boolean newFile = !file.exists();
//
//		// When change tracking is implemented, restrict save to resources marked as modified
//		try {
//			resource.save(fOptions);
//		} catch (IOException e) {
//			message.setLength(0);
//			message.append("IOException while saving resource with URI: " + resource.getURI().toString());
//			throw new ResourceHandlingException(message.toString(), e);
//		}
//
//		IFileSupportCommand permitionsUpdater = FileSupportCommandFactory.getInstance();
//		// Change folder permissions for new resources
//		if (newFolder && folder.exists()) {
//			String absFolderStr = folder.getAbsolutePath();
//			try {
//				permitionsUpdater.grantWritePermission(absFolderStr);
//			} catch (IOException e) {
//				message.setLength(0);
//				message.append("IOException while changing permissions to : " + absFolderStr);
//				throw new ResourceHandlingException(message.toString(), e);
//			}
//		}
//
//		// Change file permissions, if successfully created
//		if (newFile && file.exists()) {
//			String absFileStr = file.getAbsolutePath();
//			try {
//				permitionsUpdater.grantWritePermission(absFileStr);
//			} catch (IOException e) {
//				message.setLength(0);
//				message.append("IOException while changing permissions to : " + absFileStr);
//				throw new ResourceHandlingException(message.toString(), e);
//			}
//		}
//	}

	/* (non-Javadoc)
	//	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IModelWriter#createResourceURI(java.lang.String, org.eclipse.emf.common.util.URI, org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.IRWUserBasedRes.ResourceType)
	//	 */
//	@Override
//	public URI createResourceURI(String name, URI containerPath, ResourceType resourceType) {
//		URI resURI = null;
//		String reviewFolderSegment = null;
//		String fileName = null;
//		if (name != null) {
//			reviewFolderSegment = toValidFileName(name);
//			// convert name to a valid file name
//			fileName = reviewFolderSegment + fresTypeToTag.get(resourceType);
//			if (resourceType == ResourceType.REVIEW) {
//				resURI = containerPath.appendSegment(reviewFolderSegment).appendSegment(fileName);
//			} else {
//				resURI = containerPath.appendSegment(fileName);
//			}
//			resURI = resURI.appendFileExtension(IRWUserBasedRes.EXTENSION);
//		}
//
//		return resURI;
//	}
}
