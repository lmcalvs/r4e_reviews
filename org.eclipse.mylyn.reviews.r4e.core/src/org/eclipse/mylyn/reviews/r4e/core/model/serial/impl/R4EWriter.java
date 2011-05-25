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
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.frame.core.model.CommentType;
import org.eclipse.mylyn.reviews.frame.core.model.Review;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
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
	

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * @param aGroup
	 * @throws ResourceHandlingException
	 */
	public void serializeGroup(R4EReviewGroup aGroup, URI aFileLocation) throws ResourceHandlingException {
		// rebuild resourceset reference
		ResourceSet resourceSet = createResourceSet();
		// GROUP resource
		Resource resource = resourceSet.createResource(aFileLocation);
		// Add name to events map to its own resource
		resource.getContents().add(aGroup);

		// PACKAGE resource
		// uri = URI.createFileURI(groupPath + _PACKAGE_RESOURCE_NAME);
		// resource = resourceSet.createResource(uri);

		// Add the information to the resource package to be serialised
		// resource.getContents().add(fintPackage);

		// REVIEWS
		// Create one resource per review
		URI groupPath = getFolderPath(aFileLocation);

		EList<Review> reviews = aGroup.getReviews();
		if (reviews != null) {
			for (Review review : reviews) {
				// If any review resource does not exist yet, create it
				if (review.eResource() == null) {
					// create to a valid review file path and name with extension
					URI uri = createResourceURI(((R4EReview) review).getName(), groupPath, ResourceType.REVIEW);

					resource = resourceSet.createResource(uri);
					resource.getContents().add(review);
				}
			}
		}

		// Save modified resources
		try {
			saveResources(resourceSet);
		} catch (ResourceHandlingException e) {
			throw new ResourceHandlingException("Exception while saving the ResourceSet");
		}
	}

	/**
	 * @param aReview
	 */
	public void serializeReview(R4EReview aReview) {

		R4EReviewGroup group = (R4EReviewGroup) aReview.eContainer();
		// FIXME: outdated to be fixed later
		String groupPath = group.eResource().getURI().trimSegments(1).toString();
		// rebuild resourceset reference
		ResourceSet resourceSet = createResourceSet();

		// REVIEW
		String reviewName = aReview.getName();
		URI uri = URI.createFileURI(groupPath + reviewName + "/" + reviewName + EXTENSION);
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(aReview);

		// The reference to type is not transient and shall be serialized automatically
		// EList<Topic> anomalies = aReview.getTopics();
		// if (anomalies != null && anomalies.size() > 0) {
		// CommentType type = anomalies.get(0).getType();
		// if (type != null) {
		// resource.getContents().add(type);
		// }
		// }

		try {
			saveResources(resourceSet);
		} catch (ResourceHandlingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param aReview
	 */
	public void serializeParticipants(R4EReview aReview) {
		Collection<R4EUser> participants = aReview.getUsersMap().values();
		for (Iterator<R4EUser> iterator = participants.iterator(); iterator.hasNext();) {
			R4EParticipant participant = (R4EParticipant) iterator.next();
			serializeUser(participant, aReview);
		}
	}

	/**
	 * @param aParticipant
	 * @param aReview
	 *            needed to resolve the resource path
	 */
	public void serializeUser(R4EUser aParticipant, R4EReview aReview) {
		// aParticipant.getCreatedAnomalies();

		R4EReviewGroup group = (R4EReviewGroup) aReview.eContainer();

		String groupPath = group.eResource().getURI().trimSegments(1).toString();

		ResourceSet resourceSet = createResourceSet();
		// REVIEW
		String reviewName = aReview.getName();
		String userName = aParticipant.getId();

		// user resource
		URI uri = URI.createFileURI(groupPath + reviewName + "/" + userName + EXTENSION);
		Resource userResource = resourceSet.createResource(uri);
		userResource.getContents().add(aParticipant);

		// serialize comment types
		EList<R4EComment> comments = aParticipant.getAddedComments();
		for (int i = 0; i < comments.size(); i++) {
			R4EComment comment = comments.get(i);
			CommentType commentType = comment.getType();
			if (commentType != null) {
				userResource.getContents().add(commentType);
			}
		}

		try {
			saveResources(resourceSet);
		} catch (ResourceHandlingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	