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

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
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
import org.eclipse.mylyn.reviews.r4e.internal.transform.api.ModelTransform;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.TransResFactory;
import org.eclipse.mylyn.reviews.r4e.internal.transform.serial.impl.TResSerializeFactory;

/**
 * @author Alvaro Sanchez-Leon
 */
public class ModelTransformImpl implements ModelTransform {

	private static final String REVIEWS_RES_NAME = "reviews";

	private static final String ANOMALIES_RES_NAME = "anomalies";

	private static final String ITEMS_RES_NAME = "items";

	IModelWriter fWriter = TResSerializeFactory.getWriter();

	IModelReader fReader = TResSerializeFactory.getReader();

	RModelFactoryExt fModelFactory = SerializeFactory.getModelExtension();

	TransResFactory fModelResFactory = TransResFactory.eINSTANCE;

	/**
	 * @throws ResourceHandlingException
	 */
	public ReviewGroupRes createReviewGroupRes(URI aFolderPath, String aGroupName) throws ResourceHandlingException {
		URI fileGroupURI = fWriter.createResourceURI(aGroupName, aFolderPath, ResourceType.GROUP);
		// create a new ResourceSet and resource for the given group
		Resource resource = fWriter.createResourceSetWithResource(fileGroupURI);
		ReviewGroupRes group = TransResFactory.eINSTANCE.createReviewGroupRes();
		resource.getContents().add(group);

		URI uri = group.eResource().getURI().trimSegments(1);
		// Update the resource
		group.setName(aGroupName);
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
	public ReviewRes reviewTransform(URI origReviewGroup, URI destReviewGroup, String origReviewName)
			throws ResourceHandlingException {
		//Open original model
		R4EReviewGroup origGroup = RModelFactoryExt.eINSTANCE.openR4EReviewGroup(origReviewGroup);

		//Open original review
		R4EReview origReview = RModelFactoryExt.eINSTANCE.openR4EReview(origGroup, origReviewName);
		ResourceSet origResSet = origReview.eResource().getResourceSet();

		//Open destination group
		ReviewGroupRes destGroup = openReviewGroupRes(destReviewGroup);
		ResourceSet destResSet = destGroup.eResource().getResourceSet();

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
			URI destReviewURI = fWriter.createResourceURI(REVIEWS_RES_NAME, containerPath, ResourceType.REVIEW);
			destReviewResource = destResSet.createResource(destReviewURI);
		} else {
			destReviewResource = existingReviews.get(0).eResource();
		}

		//Make sure the new destination review is associated to a valid resource
		destReviewResource.getContents().add(destReview);
		adaptReview(origReview, destReview, destGroup);

		//save the review resource
		Resource reviewResource = destReview.eResource();
		fWriter.saveResource(reviewResource);

		return destReview;
	}

	private void adaptReview(R4EReview origReview, ReviewRes destReview, ReviewGroupRes destGroup) {
		//Adapt values and references
		Collection<R4EUser> origUsersList = origReview.getUsersMap().values();

		EList<R4EUser> users = destReview.getUsersRes();
		ResourceSet resSet = destReview.eResource().getResourceSet();

		//Destination review folder
		URI containerPath = destReview.eResource().getURI().trimSegments(1);
		//Create the user resource if it does not exist already
		Resource destAnomaliesResource = null;
		if (users == null || users.size() == 0) {
			URI destAnomaliesURI = fWriter.createResourceURI(ANOMALIES_RES_NAME, containerPath,
					ResourceType.USER_COMMENT);
			destAnomaliesResource = resSet.createResource(destAnomaliesURI);
		} else {
			//User resource exists already
			destAnomaliesResource = users.get(0).eResource();
		}

		//Move each user to the destination resources
		for (Object element : origUsersList) {
			R4EUser user = (R4EUser) element;
			//Move the user to a new destination serialisation resource
			destAnomaliesResource.getContents().add(user);
			//move the user to the destination review
			users.add(user);

			//Move Items to a different resource
			EList<R4EItem> items = user.getAddedItems();
			Resource destItemsResource = null;
			if (items == null || items.size() == 0) {
				URI destItemsURI = fWriter.createResourceURI(ITEMS_RES_NAME, containerPath, ResourceType.USER_ITEM);
				destItemsResource = resSet.createResource(destItemsURI);
			} else {
				destItemsResource = items.get(0).eResource();
			}

			for (R4EItem item : items) {
				//Move the item to the destination resource
				destItemsResource.getContents().add(item);
			}
		}
	}
}
