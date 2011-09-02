/*******************************************************************************
 * Copyright (c) 2011 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ericsson AB - initial API
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.internal.transform;

import org.eclipse.emf.common.util.URI;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;
import org.eclipse.mylyn.reviews.r4e.internal.transform.impl.ModelTransformImpl;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewGroupRes;
import org.eclipse.mylyn.reviews.r4e.internal.transform.resources.ReviewRes;

/**
 * @author Alvaro Sanchez-Leon
 */
public interface ModelTransform {

	public static ModelTransform instance = new ModelTransformImpl();

	/**
	 * Creates an EMF ResourceSet a ReviewGroupRes and its EMF Resource at the specified URI folder location
	 * 
	 * @param aFolderPath
	 *            - folder URI location where the resource group file shall be created e.g. file://c:\folder
	 * @param aGroupName
	 *            - The name of the group
	 * @param aFilePrefix
	 *            - a common file prefix used in all generated serialisation resource files
	 * @return
	 * @throws ResourceHandlingException
	 */
	public ReviewGroupRes createReviewGroupRes(URI aFolderPath, String aGroupName, String aFilePrefix)
			throws ResourceHandlingException;

	/**
	 * @param aResourcePath
	 *            - path to the group root file
	 * @return
	 * @throws ResourceHandlingException
	 */
	public ReviewGroupRes openReviewGroupRes(URI aResourcePath) throws ResourceHandlingException;

	/**
	 * Close the file Resources associated to the given review group
	 * 
	 * @param aReviewGroup
	 * @return - Message for unexpected cases, e.g. nothing to close, etc..
	 */
	public String closeReviewGroupRes(ReviewGroupRes aReviewGroup);

	/**
	 * Transform a review from the R4E core model resource structure to resource serialisation per type ans serialise it
	 * in the given location.
	 * 
	 * @param origReviewGroup
	 *            - Group instance of R4EReviewGroup
	 * @param destReviewGroup
	 *            - Group instance of ReviewGroupRes
	 * @param origReviewName
	 *            - R4EReview name in origReviewGroup
	 * @return - Resulting transformed review or null if not able to transform
	 * @throws ResourceHandlingException
	 */
	public ReviewRes transformReview(URI origReviewGroup, URI destReviewGroup, String origReviewName)
			throws ResourceHandlingException;
}
