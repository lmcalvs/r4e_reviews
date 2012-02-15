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
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.mylyn.reviews.frame.core.model.ReviewState;
import org.eclipse.mylyn.reviews.frame.core.model.TaskReference;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyType;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFormalReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EMeetingData;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewDecision;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewPhaseInfo;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUser;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IRWUserBasedRes.ResourceType;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.CompatibilityException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.SerializeFactory;
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

	public ReviewGroupRes openReviewGroupRes(URI aResourcePath) throws ResourceHandlingException {
		ReviewGroupRes group = fReader.deserializeTopElement(aResourcePath, ReviewGroupRes.class);

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
			sb.append("Attempting to close a review group with no associated resource"); //$NON-NLS-1$
			Activator.fTracer.traceDebug(sb.toString());
			return sb.toString();
		}

		ResourceSet resSet = resource.getResourceSet();
		if (resSet == null) {
			sb.append("Attempting to close a review group with no associated resource set"); //$NON-NLS-1$
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
	 * Transform the original review to concatenate a copy of the contents on a separate model within a consolidated
	 * resource
	 * 
	 * @throws ResourceHandlingException
	 * @throws CompatibilityException
	 */
	public ReviewRes transformReview(URI aOigReviewGroup, URI aDstReviewGroup, String aOigReviewName)
			throws ResourceHandlingException, CompatibilityException {
		//Open original model
		R4EReviewGroup origGroup = RModelFactoryExt.eINSTANCE.openR4EReviewGroup(aOigReviewGroup);

		//Open original review
		R4EReview origReview = RModelFactoryExt.eINSTANCE.openR4EReview(origGroup, aOigReviewName);

		//Open destination group
		ReviewGroupRes destGroup = openReviewGroupRes(aDstReviewGroup);
		ResourceSet destResSet = destGroup.eResource().getResourceSet();

		//Retrieve the file prefix, selected for the group
		String filePrefix = destGroup.getFilesPrefix();

		//Make sure a review with this name does not already exists
		EList<ReviewRes> existingReviews = destGroup.getReviewsRes();
		for (Object element : existingReviews) {
			ReviewRes reviewRes = (ReviewRes) element;
			if (reviewRes.getName().equals(aOigReviewName)) {
				StringBuilder sb = new StringBuilder("A review with this name already exists in destination Group: " //$NON-NLS-1$
						+ aOigReviewName);
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
		Copier copier = new Copier();
		Collection<R4EUser> origUsersList = origReview.getUsersMap().values();

		//copy review data from original element to the extended review element
		copyReviewData(origReview, destReview, copier);

		Resource destResource = destReview.eResource();
		R4EUser createdBy = origReview.getCreatedBy();
		EList<R4EUser> users = destReview.getUsersRes();
		//clone all users to new destination resource, this will make sure that back reference from children to any user will point to the updated resource
		for (R4EUser oUser : origUsersList) {
			R4EUser dUser = (R4EUser) copyToResource(destResource, oUser, copier);
			//move the user to the destination review
			users.add(dUser);
			//Associate the user to a new destination serialisation resource
			destResource.getContents().add(dUser);
			//find created by user and update the reference in the destination review
			if (oUser == createdBy) {
				destReview.setCreatedBy(dUser);
			}
		}

		//Update all references in the copied elements
		copier.copyReferences();

		//The review is a new element extended from original review.
		//Refresh all review references in the underneath structure to
		//point to the extended one.
		EList<R4EUser> destUsers = destReview.getUsersRes();
		for (R4EUser user : destUsers) {
			//update it at the user 
			user.setReviewInstance(destReview);
			EList<R4EComment> comments = user.getAddedComments();
			for (R4EComment comment : comments) {
				if (comment instanceof R4EAnomaly) {
					R4EAnomaly anomaly = (R4EAnomaly) comment;
					//Update of Review instance at the anomaly level
					anomaly.setReview(destReview);
				}
			}

			EList<R4EItem> items = user.getAddedItems();
			for (R4EItem item : items) {
				//update of review instance at review item level
				item.setReview(destReview);
			}
		}
	}

	/**
	 * @param destResource
	 * @param eObject
	 */
	private void associateToResource(Resource destResource, EObject eObject) {
		//Migrate the object itself
		destResource.getContents().add(eObject);
		//Migrate the direct contents as well
		for (Iterator<Object> iterator = EcoreUtil.getAllContents(eObject, true); iterator.hasNext();) {
			Object child = iterator.next();
			if (child instanceof EObject) {
				EObject eobject = (EObject) child;
				destResource.getContents().add(eobject);
			}
		}
	}

	private EObject copyToResource(Resource resource, EObject source, Copier copier) {
		EObject copyOfObject = copier.copy(source);
		associateToResource(resource, copyOfObject);
		return copyOfObject;
	}

	/**
	 * @param origReview
	 * @param destReview
	 * @param copier
	 */
	private void copyReviewData(R4EReview origReview, ReviewRes destReview, Copier copier) {
		Resource res = destReview.eResource();

		//copy EObject references 
		if (origReview.getActiveMeeting() != null) {
			R4EMeetingData meetingData = (R4EMeetingData) copyToResource(res, origReview.getActiveMeeting(), copier);
			destReview.setActiveMeeting(meetingData);
		}

		if (origReview.getAnomalyTemplate() != null) {
			R4EAnomaly anomalyTemplate = (R4EAnomaly) copyToResource(res, origReview.getAnomalyTemplate(), copier);
			destReview.setAnomalyTemplate(anomalyTemplate);
		}

		if (origReview.getDecision() != null) {
			R4EReviewDecision decision = (R4EReviewDecision) copyToResource(res, origReview.getDecision(), copier);
			destReview.setDecision(decision);
		}

		if (origReview.getReviewTask() != null) {
			TaskReference taskRef = (TaskReference) copyToResource(res, origReview.getReviewTask(), copier);
			destReview.setReviewTask(taskRef);
		}

		if (origReview.getState() != null) {
			ReviewState state = (ReviewState) copyToResource(res, origReview.getState(), copier);
			destReview.setState(state);
		}

		//associate java types
		destReview.setType(origReview.getType());
		destReview.setEnabled(origReview.isEnabled());
		destReview.setEndDate(origReview.getEndDate());
		destReview.setEntryCriteria(origReview.getEntryCriteria());
		destReview.setExtraNotes(origReview.getExtraNotes());
		destReview.setName(origReview.getName());
		destReview.setObjectives(origReview.getObjectives());
		destReview.setProject(origReview.getProject());
		destReview.setReferenceMaterial(origReview.getReferenceMaterial());
		destReview.setStartDate(origReview.getStartDate());
		destReview.setFragmentVersion(origReview.getFragmentVersion());

		//copy review components
		EList<String> components = origReview.getComponents();
		for (Object element : components) {
			String component = (String) element;
			destReview.getComponents().add(component);
		}

		if (origReview instanceof R4EFormalReview) {
			R4EFormalReview formalRevOrig = (R4EFormalReview) origReview;
			EList<R4EReviewPhaseInfo> phases = formalRevOrig.getPhases();
			if (phases != null) {
				R4EReviewPhaseInfo currentPhase = formalRevOrig.getCurrent();
				R4EReviewPhaseInfo[] movingPhases = phases.toArray(new R4EReviewPhaseInfo[0]);
				for (R4EReviewPhaseInfo phaseInfo : movingPhases) {
					R4EReviewPhaseInfo phaseInfoCopy = (R4EReviewPhaseInfo) copyToResource(res, phaseInfo, copier);
					destReview.getPhases().add(phaseInfoCopy);
					//Current phase found
					if (phaseInfo == currentPhase) {
						destReview.setCurrent(phaseInfoCopy);
					}
				}
			}

		}
	}
}
