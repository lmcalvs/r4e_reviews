package org.eclipse.mylyn.reviews.r4e.core.model.serial;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ResourceHandlingException;

public interface IModelReader {

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/**
	 * Deserialize the resource at the given URI and look for the first element of the given type at the first level of
	 * the objects tree. A brand new ResrouceSet and Resource is created to hold the returned element
	 * 
	 * @param <T>
	 * @param aRootPath
	 * @param type
	 * @return
	 * @throws ResourceHandlingException
	 */
	public abstract <T> T deserializeTopElement(URI aRootPath, Class<T> type) throws ResourceHandlingException;

	/**
	 * Deserialize the resource at the given URI and look for the first element of the given type at the first level of
	 * the objects tree. The received resourceSet is used to hold the new Resource which is associated to the returned
	 * element
	 * 
	 * @param <T>
	 * @param aRootPath
	 * @param resource
	 * @param type
	 * @return
	 * @throws ResourceHandlingException
	 */
	public abstract <T> T deserializeTopElement(URI aRootPath, ResourceSet resSet, Class<T> type)
			throws ResourceHandlingException;

	/**
	 * @param resource
	 * @throws ResourceHandlingException
	 */
	public abstract void reloadResource(Resource resource) throws ResourceHandlingException;

	/**
	 * @param aFolder
	 * @return
	 */
	public abstract List<URI> selectUsrReviewGroupRes(URI aFolder);

	/**
	 * @param aFolder
	 * @return
	 */
	public abstract List<URI> selectUsrCommentsRes(URI aFolder);

	/**
	 * @param aFolder
	 * @return
	 */
	public abstract List<URI> selectUsrItemsRes(URI aFolder);

	/**
	 * @param uri
	 * @return
	 */
	public abstract Boolean isGroupResourceUri(URI uri);

}