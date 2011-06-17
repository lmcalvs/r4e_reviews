/*******************************************************************************
 * Copyright (c) 2011 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.internal.transform.impl;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IRWUserBasedRes.ResourceType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
import org.eclipse.mylyn.reviews.r4e.core.utils.ResourceUtils;
import org.eclipse.mylyn.reviews.r4e.internal.transform.Activator;
import org.eclipse.mylyn.reviews.r4e.internal.transform.ModelTransform;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResFactory;
import org.eclipse.mylyn.reviews.r4e.internal.transform.serial.impl.TResSerializeFactory;

/**
 * @author Alvaro Sanchez-Leon
 */
public class ModelTransformImpl implements ModelTransform {

//	private static final String REVIEWS_RES_NAME = "Merged";
//
//	private static final String ANOMALIES_RES_NAME = "Merged";
//
//	private static final String ITEMS_RES_NAME = "Merged";

	IModelWriter fWriter = TResSerializeFactory.getWriter();

	IModelReader fReader = TResSerializeFactory.getReader();

	RModelFactoryExt fModelFactory = SerializeFactory.getModelExtension();

	TransResFactory fModelResFactory = TransResFactory.eINSTANCE;

	/**
	 * @throws ResourceHandlingException
	 */
	public ReviewGroupRes createReviewGroupRes(URI aFolderPath, String aGroupName, String aFilePrefix)
			throws ResourceHandlingException {
		URI fileGroupURI = fWriter.createResourceURI(aFilePrefix, aFolderPath, ResourceType.GROUP);
		// create a new ResourceSet and resource for the given group
		Resource resource = fWriter.createResourceSetWithResource(fileGroupURI);
		ReviewGroupRes group = TransResFactory.eINSTANCE.createReviewGroupRes();
		resource.getContents().add(group);

		URI uri = group.eResource().getURI().trimSegments(1);
		// Update the resource
		group.setName(aGroupName);
		group.setFilesPrefix(aFilePrefix);
		group.setFolder(URI.decode(uri.devicePath().toString()));
		fWriter.saveResource(resource);

		return group;
	}

	/**
	 *
	 */
	public ReviewGroupRes openReviewGroupRes(URI aResourcePath) throws ResourceHandlingException {
		ReviewGroupRes group = fReader.deserializeTopElement(aResourcePath, ReviewGroupRes.class);

		// Load resources from all participants
		URI folder = ResourceUtils.getFolderPath(aResourcePath);

		// Build the mapping references to anomaly types
		EList<R4EAnomalyType> anomTypes = group.getAvailableAnomalyTypes();
		for (R4EAnomalyType r4eAnomalyType : anomTypes) {
			group.getAnomalyTypeKeyToReference().put(r4eAnomalyType.getType(), r4eAnomalyType);
		}

		URI resUri = group.eResource().getURI().trimSegments(1);

		// update the transient value of folder
		group.setFolder(URI.decode(resUri.devicePath().toString()));

		return group;
	}

	public String closeReviewGroupRes(ReviewGroupRes aReviewGroup) {
		StringBuilder sb = new StringBuilder();

		// Obtain all resources
		Resource resource = aReviewGroup.eResource();
		if (resource == null) {
			sb.append("Attempting to close a review group with no associated resource");
			Activator.fTracer.traceDebug(sb.toString());
			return sb.toString();
		}

		ResourceSet resSet = resource.getResourceSet();
		if (resSet == null) {
			sb.append("Attempting to close a review group with no associated resource set");
			Activator.fTracer.traceDebug(sb.toString());
			return sb.toString();
		}

		EList<Resource> resList = resSet.getResources();

		// unload then all
		for (Resource res : resList) {
			res.unload();
		}
		return null;
	}

	/**
	 * Transform the original review to concatenate a copy of the contents on a separate model with consolidated
	 * resources (i.e. one file per items, one for anomalies, one for all reviews, one for all users and comments
	 * 
	 * @throws ResourceHandlingException
	 */
	/**
	 *
	 */
	public ReviewRes transformReview(URI origReviewGroup, URI destReviewGroup, String origReviewName)
			throws ResourceHandlingException {
		//Open original model
		R4EReviewGroup origGroup = RModelFactoryExt.eINSTANCE.openR4EReviewGroup(origReviewGroup);

		//Open original review
		R4EReview origReview = RModelFactoryExt.eINSTANCE.openR4EReview(origGroup, origReviewName);
		ResourceSet origResSet = origReview.eResource().getResourceSet();

		//Open destination group
		ReviewGroupRes destGroup = openReviewGroupRes(destReviewGroup);
		ResourceSet destResSet = destGroup.eResource().getResourceSet();

		//Retrieve the file prefix, selected for the group
		String filePrefix = destGroup.getFilesPrefix();

		//Make sure a review with this name does not already exists
		EList<ReviewRes> existingReviews = destGroup.getReviewsRes();
		for (Object element : existingReviews) {
			ReviewRes reviewRes = (ReviewRes) element;
			if (reviewRes.getName().equals(origReviewName)) {
				StringBuilder sb = new StringBuilder("A review with this name already exists in destination Group: "
						+ origReviewName);
				throw new ResourceHandlingException(sb.toString());
			}
		}

		//Create the extended review instance
		ReviewRes destReview = fModelResFactory.createReviewRes();

		//Create Review resource if it does not exist yet
		Resource destReviewResource = null;
		if (existingReviews == null || existingReviews.size() == 0) {
			URI containerPath = destGroup.eResource().getURI().trimSegments(1);
			URI destReviewURI = fWriter.createResourceURI(filePrefix, containerPath, ResourceType.REVIEW);
			destReviewResource = destResSet.createResource(destReviewURI);
		} else {
			destReviewResource = existingReviews.get(0).eResource();
		}

		//Add the review to the ReviewGroup
		destGroup.getReviewsRes().add(destReview);

		//Make sure the new destination review is associated to a valid resource
		destReviewResource.getContents().add(destReview);
		adaptReview(origReview, destReview, destGroup);

		//save the review resource
		fWriter.saveResources(destGroup.eResource().getResourceSet());
		return destReview;
	}

	private void adaptReview(R4EReview origReview, ReviewRes destReview, ReviewGroupRes destGroup) {

		Collection<R4EUser> origUsersList = origReview.getUsersMap().values();
		ResourceSet resSet = destReview.eResource().getResourceSet();

		//copy review values
		EList<R4EUser> users = destReview.getUsersRes();
		copyReviewData(origReview, destReview);

		//Destination review folder
		String filePrefix = destGroup.getFilesPrefix();
		URI containerPath = destReview.eResource().getURI().trimSegments(1);
		URI destAnomaliesURI = fWriter.createResourceURI(filePrefix, containerPath, ResourceType.USER_COMMENT);
		URI destItemsURI = fWriter.createResourceURI(filePrefix, containerPath, ResourceType.USER_ITEM);

		Resource destAnomaliesResource = null;
		Resource destItemsResource = null;

		//Resolve the Anomalies resource 
		File file = new File(destAnomaliesURI.devicePath());
		if (file.exists()) {
			destAnomaliesResource = resSet.getResource(destAnomaliesURI, true);
		} else {
			destAnomaliesResource = resSet.createResource(destAnomaliesURI);
		}

		//Resolve the Items resource
		file = new File(destItemsURI.devicePath());
		if (file.exists()) {
			destItemsResource = resSet.getResource(destItemsURI, true);
		} else {
			destItemsResource = resSet.createResource(destItemsURI);
		}

		//Move all users to new destination resource, this will make sure that back reference from children to any user will point to the updated resource
		for (R4EUser user : origUsersList) {
			//move the user to the destination review
			users.add(user);
			user.setReviewInstance(destReview);
			//Move the user to a new destination serialisation resource
			destAnomaliesResource.getContents().add(user);
		}

		//First user pass: Move user's content to the destination resources
		for (R4EUser user : origUsersList) {
			//Move anomalies to a different resource
			EList<R4EComment> comments = user.getAddedComments();
			for (R4EComment comment : comments) {
				//Move the item to the destination resource
				switchResources(destAnomaliesResource, comment);
			}

			//Move Items to a different resource
			EList<R4EItem> items = user.getAddedItems();
			for (R4EItem item : items) {
				//Move the item to the destination resource
				switchResources(destItemsResource, item);
				item.setReview(destReview);
				//TODO: Investigate why this reference is not updated by EMF
				String addedBy = item.getAddedById();
				item.setAddedBy(origReview.getUsersMap().get(addedBy));
			}
		}

//		//Second user pass: Adjust reviewed content references which have just been moved in the item level for all users above
//		for (R4EUser user : origUsersList) {
//			if (user instanceof R4EParticipant) {
//				List<EObject> IdsCached = new ArrayList<EObject>();
//				EList<R4EID> reviewed = ((R4EParticipant) user).getReviewedContent();
//
//				//cach
//				for (R4EID r4eid : reviewed) {
//					IdsCached.add(r4eid);
//				}
//
//				//clear
//				reviewed.clear();
//
//				//refresh
//				for (Object element : IdsCached) {
//					R4EID r4eid = (R4EID) element;
//					reviewed.add(r4eid);
//				}
//			}
//		}
	}

	/**
	 * @param destResource
	 * @param eObject
	 */
	private void switchResources(Resource destResource, EObject eObject) {
		//Migrate the object itself
		destResource.getContents().add(eObject);
		//Migrate the direct contents as well
		for (Iterator iterator = EcoreUtil.getAllContents(eObject, true); iterator.hasNext();) {
			Object child = iterator.next();
			if (child instanceof EObject) {
				EObject eobject = (EObject) child;
				destResource.getContents().add(eobject);
			}
		}
	}

	/**
	 * @param origReview
	 * @param destReview
	 */
	private void copyReviewData(R4EReview origReview, ReviewRes destReview) {
//		Resource res = destReview.eResource();
//
//		//Move references 
//		if (origReview.getActiveMeeting() != null) {
//			switchResources(res, origReview.getActiveMeeting());
//		}
//
//		if (origReview.getAnomalyTemplate() != null) {
//			switchResources(res, origReview.getAnomalyTemplate());
//		}
//
//		if (origReview.getDecision() != null) {
//			switchResources(res, origReview.getDecision());
//		}
//
//		if (origReview.getReviewTask() != null) {
//			switchResources(res, origReview.getReviewTask());
//		}
//
//		if (origReview.getState() != null) {
//			switchResources(res, origReview.getState());
//		}

		//Update references and values in the destination review
		destReview.setActiveMeeting(origReview.getActiveMeeting());
		destReview.setAnomalyTemplate(origReview.getAnomalyTemplate());
		destReview.setCreatedBy(origReview.getCreatedBy());
		destReview.setDecision(origReview.getDecision());
		destReview.setEnabled(origReview.isEnabled());
		destReview.setEndDate(origReview.getEndDate());
		destReview.setEntryCriteria(origReview.getEntryCriteria());
		destReview.setExtraNotes(origReview.getExtraNotes());
		destReview.setName(origReview.getName());
		destReview.setObjectives(origReview.getObjectives());
		destReview.setProject(origReview.getProject());
		destReview.setReferenceMaterial(origReview.getReferenceMaterial());
		destReview.setReviewTask(origReview.getReviewTask());
		destReview.setStartDate(origReview.getStartDate());
		destReview.setState(origReview.getState());
		destReview.setType(origReview.getType());
		destReview.setXmlVersion(origReview.getXmlVersion());
	}
}
