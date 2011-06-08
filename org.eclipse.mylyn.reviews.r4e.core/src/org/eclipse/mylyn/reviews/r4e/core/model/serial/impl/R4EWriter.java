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

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.FileSupportCommandFactory;
import org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.IFileSupportCommand;

/**
 * @author lmcalvs
 *
 */
/**
 * @author lmcalvs
 * 
 */
public class R4EWriter extends Common {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	

	/**
	 * @param resourceSet
	 */
	public void saveResources(ResourceSet resourceSet) throws ResourceHandlingException {
		// Indicate to save the schema location within the resource files
		// options = new HashMap<String, Boolean>();
		// options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

		EList<Resource> resources = resourceSet.getResources();
		if (resources != null) {
			for (Resource resource : resources) {
				saveResource(resource);
			}
		}
	}

	/**
	 * @param resource
	 * @throws ResourceHandlingException
	 */
	public void saveResource(Resource resource) throws ResourceHandlingException {
		// Indicate to save the schema location within the resource files
		// options = new HashMap<String, Boolean>();
		// options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		StringBuilder message = new StringBuilder();
		URI resUri = resource.getURI();
		if (resUri == null) {
			message.append("Not possible to save a Resource with URI= null");
			throw new ResourceHandlingException(message.toString());
		}
		
		// Mark new folder creation
		URI folderUri = getFolderPath(resUri);
		File folder = new File(folderUri.toString());
		boolean newFolder = !folder.exists();
		
		// Mark new file creation
		File file = new File(URI.decode(resUri.devicePath()));
		boolean newFile = !file.exists();

		// When change tracking is implemented, restrict save to resources marked as modified
		try {
			resource.save(fOptions);
		} catch (IOException e) {
			message.setLength(0);
			message.append("IOException while saving resource with URI: " + resource.getURI().toString());
			throw new ResourceHandlingException(message.toString(), e);
		}

		IFileSupportCommand permitionsUpdater = FileSupportCommandFactory.getInstance();
		// Change folder permissions for new resources
		if (newFolder && folder.exists()) {
			String absFolderStr = folder.getAbsolutePath();
			try {
				permitionsUpdater.grantWritePermission(absFolderStr);
			} catch (IOException e) {
				message.setLength(0);
				message.append("IOException while changing permissions to : " + absFolderStr);
				throw new ResourceHandlingException(message.toString(), e);
			}
		}

		// Change file permissions, if successfully created
		if (newFile && file.exists()) {
			String absFileStr = file.getAbsolutePath();
			try {
				permitionsUpdater.grantWritePermission(absFileStr);
			} catch (IOException e) {
				message.setLength(0);
				message.append("IOException while changing permissions to : " + absFileStr);
				throw new ResourceHandlingException(message.toString(), e);
			}
		}
	}
}
	