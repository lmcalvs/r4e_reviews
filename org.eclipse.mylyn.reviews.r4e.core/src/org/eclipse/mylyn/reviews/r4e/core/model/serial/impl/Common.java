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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelPackage;
import org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelPackageImpl;

/**
 * @author lmcalvs
 *
 */
public class Common {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	public enum ResourceType {
		GROUP, USER_GROUP, REVIEW, USER_COMMENT, USER_ITEM
	}
	
	protected final static String			EXTENSION				= "xrer";
	private final static String				REVIEW_RES_TAG			= "_review";
	private final static String				REVIEW_UCOMMENT_TAG		= "_comments";
	private final static String				REVIEW_UITEM_TAG		= "_items";
	private final static String				GROUP_ROOT_TAG			= "_group_root";
	private final static String				GROUP_UREVIEW_TAG		= "_group_reviews";

	// Patterns
	private final static String				END						= "\\z";
	private final static Pattern			USER_GROUP_REVS_PATT	= Pattern.compile(GROUP_UREVIEW_TAG + "."
																			+ EXTENSION + END);
	private final static Pattern			USER_ITEMS_PATT			= Pattern.compile(REVIEW_UITEM_TAG + "."
																			+ EXTENSION + END);
	private final static Pattern			USER_COMMENTS_PATT		= Pattern.compile(REVIEW_UCOMMENT_TAG + "."
																			+ EXTENSION + END);
	private final static Pattern			ROOT_GROUP_PATT			= Pattern.compile(GROUP_ROOT_TAG + "." + EXTENSION
																			+ END);

	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	protected final RModelPackage	fintPackage;
	protected Map<String, Boolean>	fOptions		= null;
	private final Map<ResourceType, String>	fresTypeToTag			= new HashMap<ResourceType, String>();

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	public Common() {
		// This statement is needed for save and Load of resources.
		// If init is not executed at loading, the package name will no be
		// resolved causing the loaded object to be represented as
		// DynamicEObjImpl object rather that its corresponding class
		fintPackage = RModelPackageImpl.init();

		// Build a lookup table to facilitate the selection of the proper resource tag
		fresTypeToTag.put(ResourceType.USER_COMMENT, REVIEW_UCOMMENT_TAG);
		fresTypeToTag.put(ResourceType.USER_ITEM, REVIEW_UITEM_TAG);
		fresTypeToTag.put(ResourceType.REVIEW, REVIEW_RES_TAG);
		fresTypeToTag.put(ResourceType.USER_GROUP, GROUP_UREVIEW_TAG);
		fresTypeToTag.put(ResourceType.GROUP, GROUP_ROOT_TAG);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Initialise the resource Set for reading or writing
	 * 
	 * @return
	 */
	public ResourceSet createResourceSet() {
		// create the container resource set
		ResourceSet resourceSet = new ResourceSetImpl();
		// Register the appropriate resource factory to handle all file
		// extensions.
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl() {
					public Resource createResource(URI uri) {
						return new XMIResourceImpl(uri) {
							protected boolean useUUIDs() {
								return true;
							}
						};
					}
				});
		
		Activator.fTracer.traceDebug("new Resourceset created: " + resourceSet);
		return resourceSet;
	}

	// /**
	// * Crate resource previously serialised as per provided root path and group name
	// *
	// * @param aGroupPath
	// * directory path ending with "/"
	// * @param aGroupName
	// * file name with no extension. ".xml" is default
	// * @return
	// */
	// protected Resource createGroupResourceSet(String aGroupPath, String aGroupName) {
	// Resource groupResource = null;
	//
	// if (aGroupPath != null && aGroupName != null) {
	// ResourceSet resourceSet = createResourceSet();
	// URI reviewGroupURI = URI.createFileURI(aGroupPath + aGroupName + _EXTENSION);
	//
	// // Create the resource for given group
	// groupResource = resourceSet.createResource(reviewGroupURI);
	// }
	// return groupResource;
	// }

	/**
	 * Create ResourceSet with a new Resource at the specified URI
	 * 
	 * @param aPathToResource
	 * @return
	 */
	public Resource createResourceSetWithResource(URI aPathToResource) {
		Resource groupResource = null;

		if (aPathToResource != null) {
			ResourceSet resourceSet = createResourceSet();

			// Create the resource for given group
			groupResource = resourceSet.createResource(aPathToResource);
		}
		return groupResource;
	}

	/**
	 * @param aPathToResource
	 * @param resourceSet
	 * @return
	 */
	protected Resource createResource(URI aPathToResource, ResourceSet resourceSet) {
		Resource groupResource = null;

		if (aPathToResource != null && resourceSet != null) {
			// Create the resource for given group
			groupResource = resourceSet.getResource(aPathToResource, true);
		}
		return groupResource;
	}

	/**
	 * @param resource
	 * @throws ResourceHandlingException
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
	
	/**
	 * From a file path e.g. file://c:/dir/demo.xml return the folder path i.e. file://c:/dir
	 * 
	 * @param uri
	 * @return
	 */
	public URI getFolderPath(URI uri) {
		URI retURI = null;
		if (uri != null) {
			retURI = uri.trimSegments(1);
		}
		return retURI;
	}

	/**
	 * Convert String to a valid file name by replacing invalid characters by '_'
	 * 
	 * @param stValue
	 * @return
	 */
	public String toValidFileName(String stValue) {
		String result = null;
		StringBuilder sb = new StringBuilder();

		if (stValue != null) {
			int size = stValue.length();
			for (int i = 0; i < size; i++) {
				char c = stValue.charAt(i);
				if (!Character.isLetterOrDigit(c) && c != '-' && c != '_') {
					sb.append('_');
				} else {
					sb.append(c);
				}
			}
			result = sb.toString();
		}
	
		return result;
	}

	/**
	 * Convert the given folder URI to a review file path with folder, file name and extension e.g. for a review
	 * groupPath "file://c:/folder shall become file://c:/folder/validReviewName/validReviewName_review.xrer
	 * 
	 * @param review
	 * @param containerPath
	 * @return
	 */
	public URI createResourceURI(String name, URI containerPath, ResourceType resourceType) {
		URI resURI = null;
		String reviewFolderSegment = null;
		String fileName = null;
		if (name != null) {
			reviewFolderSegment = toValidFileName(name);
			// convert name to a valid file name
			fileName = reviewFolderSegment + fresTypeToTag.get(resourceType);
			if (resourceType == ResourceType.REVIEW) {
				resURI = containerPath.appendSegment(reviewFolderSegment).appendSegment(fileName);
			} else {
				resURI = containerPath.appendSegment(fileName);
			}
			resURI = resURI.appendFileExtension(EXTENSION);
		}

		return resURI;
	}

	/**
	 * @param aFolder
	 * @return
	 */
	public List<URI> selectUsrReviewGroupRes(URI aFolder) {
		return selectFiles(aFolder, USER_GROUP_REVS_PATT);
	}

	/**
	 * @param aFolder
	 * @return
	 */
	public List<URI> selectUsrCommentsRes(URI aFolder) {
		return selectFiles(aFolder, USER_COMMENTS_PATT);
	}

	/**
	 * @param aFolder
	 * @return
	 */
	public List<URI> selectUsrItemsRes(URI aFolder) {
		return selectFiles(aFolder, USER_ITEMS_PATT);
	}

	/**
	 * @param uri
	 * @return
	 */
	public Boolean isGroupResourceUri(URI uri) {
		return uriMatch(uri, ROOT_GROUP_PATT);
	}

	/**
	 * @param aFolder
	 * @param pattern
	 * @return
	 */
	private List<URI> selectFiles(URI aFolder, Pattern pattern) {
		Matcher matcher = null;
		List<URI> filesRes = new ArrayList<URI>();
		String path = aFolder.devicePath();
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

}
	