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
 *   Alvaro Sanchez-Leon - First implementation and API
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomaly;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EAnomalyTextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EComment;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EDelta;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileContext;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EFileVersion;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EItem;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReview;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EReviewGroup;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextContent;
import org.eclipse.mylyn.reviews.r4e.core.model.R4ETextPosition;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EUserRole;
import org.eclipse.mylyn.reviews.r4e.core.model.RModelFactory;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.OutOfSyncException;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;

/**
 * @author lmcalvs
 *
 */
public interface Persistence {
	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Perform read, write permissions on the specified folder uri
	 * 
	 * @param aLocation
	 * @return - true if successful writing
	 */
	public boolean testWritePermissions(URI aLocation) throws ResourceHandlingException;

	/**
	 * Service method to poll for changes at the directory of associated resource.
	 * 
	 * @param atElementLoc
	 * @return - null provided when the resources are upto date with directory <br>
	 *         when out of synch - the return list is populated with the out of sync Resources found
	 */
	public List<Resource> pollDirUpdates(EObject atElementLoc);

	public interface GroupResFactory {

		/**
		 * Creates an EMF ResourceSet an R4EReviewGroup and its EMF Resource at the specified URI folder location
		 * 
		 * @param aFolderPath
		 *            - folder URI location where the resource group file shall be created e.g. file://c:\folder
		 * @param aGroupName
		 *            - The name of the group
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EReviewGroup createR4EReviewGroup(URI aFolderPath, String aGroupName) throws ResourceHandlingException;

		/**
		 * Loads an R4EReviewGroup from specified URI
		 * 
		 * @param aRresourcePath
		 * @return R4EReviewGroup or null if loading fails
		 * @throws ResourceHandlingException
		 */
		public R4EReviewGroup openR4EReviewGroup(URI aResourcePath) throws ResourceHandlingException;

		/**
		 * The review group structure shall unload the associated resources and remove any references, the application is
		 * responsible to remove any other references to this review group structure <br>
		 * 
		 * @param aReviewGroup
		 * @return
		 */
		public String closeR4EReviewGroup(R4EReviewGroup aReviewGroup);

	}

	public interface ReviewResFactory {

		/**
		 * Creates an R4EReview and its EMF Resource associated to the same ResourceSet as the one in the reviewGroup Uses a
		 * locking mechanism before updating the EMF Resource associated to the R4EReviewGroup adminState of Review -> Open<br>
		 * It also creates the first participant with the default role and its associated resource.
		 * 
		 * @param aReviewGroup
		 * @param aReviewName
		 * @param aCreatedByUser
		 * @return - null for invalid arguments
		 * @throws ResourceHandlingException
		 *             - i.e. review name already used, writing permissions.
		 */
		public R4EReview createR4EReview(R4EReviewGroup aRviewGroup, String aReviewName, String aCreatedByUser)
				throws ResourceHandlingException;

		/**
		 * Although opening the actual review shall done automatically via proxy resolution from the elements in the
		 * reviewGroup, it's necessary to build the references for all subelements in the review. So all the different
		 * associated resources (e.g. Items and Anomalies) must be loaded and the transient references must be built.
		 * loadState of Review in workspace -> Opened
		 * 
		 * @param aReviewGroup
		 * @param aReviewName
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EReview openR4EReview(R4EReviewGroup aRreviewGroup, String aReviewName) throws ResourceHandlingException;

		/**
		 * The review structure shall unload the associated resources and remove any references, the application is
		 * responsible to remove any other references to this review structure <br>
		 * loadState of Review in workspace -> Closed
		 * 
		 * @param aReview
		 * @return null if successful, error message if unsuccessful
		 */
		public String closeR4EReview(R4EReview aReview);

		/**
		 * <b>Delete process Example</b> <list><li>Close the review</li><li>Mark the review as disabled</li> <li>Remove the
		 * review entry from the EMF Resource associated to the R4EReviewGroup</li><br>
		 * 
		 * <br>
		 * <b>if aDeleteOnDisk = true</b> <list> <li>The R4EGroup resource shall be updated and saved without the reference
		 * to this review</li> <li>The Resources associated to this review shall be removed from the disk</li> </list>
		 * 
		 * @param aReview
		 * @param aDeleteOnDisk
		 * @return null if successful, error message if unsuccessful
		 */
		public String deleteR4EReview(R4EReview aReview, boolean aDeleteOnDisk) throws ResourceHandlingException;

	}

	public interface UserItemResFactory {

		/**
		 * Creates a new ReviewItem entry for the given user, the user is expected to be already associated to a Review <br>
		 * It updates the serialization resource of the the given participant <br>
		 * It updates references to the associated review<br>
		 * It associates a unique id for the item
		 * 
		 * @param aParticipant
		 *            - associated to a review
		 * @return
		 */
		public R4EItem createR4EItem(R4EParticipant aParticipant) throws ResourceHandlingException;

		/**
		 * Soft delete a previously created review item,
		 * 
		 * @param aItem
		 * @param aDeleteOnDisk
		 *            - if true, delete references, remove from file resources
		 */
		public void deleteR4EItem(R4EItem aItem, boolean aDeleteOnDisk) throws ResourceHandlingException;

		/**
		 * @param item
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EFileContext createR4EFileContext(R4EItem item) throws ResourceHandlingException;

		/**
		 * @param context
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EFileVersion createR4EBaseFileVersion(R4EFileContext context) throws ResourceHandlingException;

		/**
		 * @param context
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EFileVersion createR4ETargetFileVersion(R4EFileContext context) throws ResourceHandlingException;

		/**
		 * @param context
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EDelta createR4EDelta(R4EFileContext context) throws ResourceHandlingException;

		/**
		 * @param delta
		 * @throws ResourceHandlingException
		 */
		public void deleteR4EDelta(R4EDelta delta) throws ResourceHandlingException;

		/**
		 * @param delta
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4ETextContent createR4EBaseTextContent(R4EDelta delta) throws ResourceHandlingException;

		/**
		 * @param delta
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4ETextContent createR4ETargetTextContent(R4EDelta delta) throws ResourceHandlingException;

		/**
		 * @param content
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4ETextPosition createR4ETextPosition(R4ETextContent content) throws ResourceHandlingException;

	}

	public interface UserCommentResFactory {
		/**
		 * TODO: Creating an R4EUser has to support the following scenarios <list><li>The need to transform from a user
		 * to a participant</li> <li>Combinations with deletion</li> </list>
		 * 
		 * @param aReview
		 * @param userId
		 * @return
		 * @throws ResourceHandlingException
		 */
		// public R4EUser createR4EUser(R4EReview aReview, String userId) throws ResourceHandlingException;

		/**
		 * Crates the instance and its serialization resource, update references as needed e.g. R4EReview
		 * 
		 * @param aReview
		 * @param participantId
		 * @param aRoles
		 */
		public R4EParticipant createR4EParticipant(R4EReview aReview, String participantId, List<R4EUserRole> aRoles)
				throws ResourceHandlingException;

		/**
		 * Creates the associated content (entry to location) and associates it the the Anomaly (Topic)
		 * 
		 * @param anomaly
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4ETextContent createR4ETextContent(R4EAnomaly anomaly) throws ResourceHandlingException;

		/**
		 * Creates a new ReviewAnomaly entry for the given participant, the user is expected to be already associated to
		 * a Review <br>
		 * It updates the serialization resource of the the given participant <br>
		 * It updates references to the associated review Creates a unique id for the comment<br>
		 * Associates the anomalyid with the comment
		 * 
		 * @param aParticipant
		 *            - associated to a review
		 * @return
		 */
		public R4EAnomaly createR4EAnomaly(R4EParticipant aParticipant) throws ResourceHandlingException;


		/**
		 * Creates a new ReviewComment entry for the given participant, the user is expected to be already associated to
		 * a Review <br>
		 * It updates the serialization resource of the the given participant <br>
		 * It updates references to the associated review Creates a unique id for the comment<br>
		 * Associates the anomalyid with the comment
		 * 
		 * @param aParticipant
		 *            - associated to a review
		 * @return
		 */
		public R4EComment createR4EComment(R4EParticipant aParticipant, R4EAnomaly aAnomaly)
				throws ResourceHandlingException;

		/**
		 * @param content
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EAnomalyTextPosition createR4EAnomalyTextPosition(R4EContent content)
				throws ResourceHandlingException;

		/**
		 * @param txtPosition
		 * @return
		 * @throws ResourceHandlingException
		 */
		public R4EFileVersion createR4EFileVersion(R4EAnomalyTextPosition txtPosition)
				throws ResourceHandlingException;

		/**
		 * Soft delete a previously created review comment,
		 * 
		 * @param aComment
		 * @param aDeleteOnDisk
		 *            - if true, delete references, remove from file resources
		 */
		public void deleteR4EComment(R4EComment aComment, boolean aDeleteOnDisk) throws ResourceHandlingException;

		/**
		 * Soft delete a previously created anomaly
		 * 
		 * @param aAnomaly
		 * @param aDeleteOnDisk
		 */
		public void deleteR4EAnomaly(R4EAnomaly aAnomaly, boolean aDeleteOnDisk) throws ResourceHandlingException;

	}
	
	public interface ResourceUpdater {
		/**
		 * Reserve Resource, Reload and update new references<br>
		 * <b>Note:</b>The resource will be locked for the duration of the check out, the update is controlled by the
		 * user application and shall be as quick as possible to avoid locking out other users from the resource
		 * 
		 * @param aEObject
		 * @param usrLoginID
		 * @return - bookingNumber, used to check in
		 * @throws ResourceHandlingException
		 */
		public Long checkOut(EObject aEObject, String usrLoginID) throws ResourceHandlingException, OutOfSyncException;

		/**
		 * Revert changes performed in the associated Resource since checkout e.g. using EMF ChangeRecorder. <br>
		 * Unreserve the file<br>
		 * Releases the booking number
		 * 
		 * @param aBookingNumber
		 * @throws ResourceHandlingException
		 */
		public void undoCheckOut(Long aBookingNumber) throws ResourceHandlingException;

		/**
		 * Save resource (if updated) and unreserve it
		 * 
		 * @param aBookingNumber
		 *            - Obtained during check out
		 * @throws ResourceHandlingException
		 */
		public void checkIn(Long aBookingNumber) throws ResourceHandlingException;

	}

	/**
	 * Limit visibility to the methods related to construction for Persistence
	 * 
	 * @author lmcalvs
	 * 
	 */
	public interface RModelFactoryExt extends Persistence, Persistence.GroupResFactory, Persistence.ReviewResFactory,
			Persistence.UserItemResFactory, Persistence.UserCommentResFactory {
		RModelFactory	eINSTANCE	= org.eclipse.mylyn.reviews.r4e.core.model.impl.RModelFactoryImpl.init();
	}

}
	