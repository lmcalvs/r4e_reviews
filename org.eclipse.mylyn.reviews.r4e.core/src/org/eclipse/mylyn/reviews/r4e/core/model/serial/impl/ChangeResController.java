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
 *   Alvaro Sanchez-Leon  - First API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.mylyn.reviews.r4e.core.model.serial.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.mylyn.reviews.r4e.core.Activator;
import org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.utils.filePermission.FileSupportCommandFactory;

/**
 * @author lmcalvs
 *
 * @version $Revision: 1.0 $
 */
public class ChangeResController implements Persistence.ResourceUpdater {
	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------
	private static final String					LOCK_EXT		= ".lck";
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	/**
	 * Field checkedOutMap.
	 */
	protected final Map<Long, UpdateContext>	checkedOutMap	= new HashMap<Long, UpdateContext>();
	/**
	 * Field fcount.
	 */
	protected Long								fcount			= 0L;
	// private R4EReader fReader = SerializeFactory.getReader();
	/**
	 * Field fWriter.
	 */
	protected IModelWriter							fWriter			= SerializeFactory.getWriter();

	// protected Persistence fPersistence = SerializeFactory.get

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------
	

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater#checkOut(org.eclipse.emf.ecore.EObject
	 * , org.eclipse.mylyn.reviews.r4e.core.model.R4EParticipant)
	 */
	/**
	 * Method checkOut.
	 * @param aEObject EObject
	 * @param participant R4EParticipant
	 * @return Long
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence$ResourceUpdater#checkOut(EObject, R4EParticipant)
	 */
	public Long checkOut(EObject aEObject, String usrLoginID) throws ResourceHandlingException {
		UpdateContext newContext = new UpdateContext(aEObject, usrLoginID);
		// if resource is already checkedout, return existing booking number
		Collection<UpdateContext> checkedOutContextList = checkedOutMap.values();
		for (Iterator<UpdateContext> iterator = checkedOutContextList.iterator(); iterator.hasNext();) {
			UpdateContext updateContext = iterator.next();
			if (updateContext.equals(newContext)) {
				// already checkedout
				return updateContext.getBookingNum();
			}
		}

		// TODO: Poll for directory changes, if directory is out of synch report it to UI via a new OutOfSynchException

		// if new checkout,
		lockResource(aEObject.eResource(), usrLoginID);

		// get new booking number and register checkout
		fcount++;
		newContext.setBookingNum(fcount);
		checkedOutMap.put(fcount, newContext);

		Activator.fTracer.traceDebug("Checkout Num: " + fcount + ", Object: " + aEObject.toString());
		
		// return booking number
		return fcount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater#undoCheckOut(java.lang.Long)
	 */
	/**
	 * Method undoCheckOut.
	 * 
	 * @param aBookingNumber
	 *            Long
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence$ResourceUpdater#undoCheckOut(Long)
	 */
	public void undoCheckOut(Long aBookingNumber) throws ResourceHandlingException {
		// Check if part of booking list
		UpdateContext context = checkedOutMap.remove(aBookingNumber);
		// if present on record
		if (context != null) {
			unlockResource(context.getResource());
		} else {
			Activator.fTracer.traceError(new StringBuilder("UndoCheckOut failed: booking number is not active: "
					+ aBookingNumber).toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater#checkIn(java.lang.Long)
	 */
	/**
	 * Method checkIn.
	 * @param aBookingNumber Long
	 * @throws ResourceHandlingException
	 * @see org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence$ResourceUpdater#checkIn(Long)
	 */
	public void checkIn(Long aBookingNumber) throws ResourceHandlingException {
		if (aBookingNumber == null) {
			Activator.fTracer.traceDebug("CheckIn with booking number set to null");
			return;
		}

		UpdateContext context = checkedOutMap.remove(aBookingNumber);
		// Check if booking still on records
		if (context != null) {
			// if on record SAVE resource
			Resource resource = context.getResource();
			fWriter.saveResource(resource);
			// remove lock
			unlockResource(resource);
			Activator.fTracer.traceDebug("CheckIn: " + aBookingNumber);
		} else {
			Activator.fTracer.traceError(new StringBuilder("CheckIn failed: booking number is not active: "
					+ aBookingNumber).toString());
		}
	}

	/**
	 * @param aResource
	 * @return
	 * @throws ResourceHandlingException
	 */
	protected void lockResource(Resource aResource, String usrLoginID) throws ResourceHandlingException {
		if (aResource == null || aResource.getURI() == null) {
			return;
		}

		URI resUri = aResource.getURI();
		File file = new File(URI.decode(resUri.devicePath()));
		
		if (!file.exists()) {
			throw new ResourceHandlingException("Not able to lock file: " + file.getAbsolutePath().toString()
					+ ". file does not exist");
		}

		file = new File(resUri.toFileString() + LOCK_EXT);
		// check if the file exist and if what is the user locking it
		if (file.exists()) {
			// The resource is already locked
			try {
				// TODO: Preference.
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Activator.fTracer.traceDebug(new StringBuilder("Interrupter exception while waiting to lock resource")
						.toString());
			}

			// Try a second time, if lock file does not longer exists continue
			if (file.exists()) {
				FileReader reader;
				try {
					reader = new FileReader(file);
					BufferedReader breader = new BufferedReader(reader);
					StringBuilder sb = new StringBuilder();
					try {
						String line = breader.readLine();
						while (line != null) {
							sb.append(line + "\n");
							line = breader.readLine();
						}
					} catch (IOException e) {
						Activator.fTracer
								.traceDebug(new StringBuilder("IOException while reading lock file").toString());
					}
					finally {
						if (breader != null) {
							try {
								breader.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}

					throw new ResourceHandlingException("The Resource is locked: " + file.getAbsolutePath().toString()
							+ "\nLock Information:\n" + sb.toString());
				} catch (FileNotFoundException e) {
					// Quite unlikely but
					// file is now gone, assume good timing and proceed
				}
			}
		}

		// create the lock file
		try {
			file.createNewFile();
			// TODO: Preference. grant it write permissions to everyone should be an option
			FileSupportCommandFactory.getInstance().grantWritePermission(file.getAbsolutePath());
		} catch (IOException e) {
			throw new ResourceHandlingException(e);
		}

		// Write lock tracking info
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(file);
			writer.println("user: " + usrLoginID);
			writer.println("Date: " + new Date().toString());

		} catch (FileNotFoundException e) {
			throw new ResourceHandlingException(e);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		
		Activator.fTracer.traceDebug("Checkout lock created " + file.getAbsolutePath());
	}

	// /**
	// * @param aResource
	//
	// * @throws ResourceHandlingException */
	// protected void reloadResource(EObject aEObject) throws ResourceHandlingException {
	// if (aEObject instanceof R4EReviewGroup) {
	// // if resource is group - simple resource reload
	// Resource aResource = aEObject.eResource();
	// fReader.reloadResource(aResource);
	// return;
	// }
	//
	// RModelFactoryExt resFactory = SerializeFactory.getModelExtension();
	//
	// // if review object, close and re-open the review, to build new references
	// if (aEObject instanceof R4EReview) {
	// R4EReview review = (R4EReview) aEObject;
	// String reviewName = review.getName();
	// R4EReviewGroup group = (R4EReviewGroup) review.eContainer();
	// resFactory.closeR4EReview((R4EReview) aEObject);
	// resFactory.openR4EReview(group, reviewName);
	// }
	//
	//
	// URI resUri = aResource.getURI();
	// ResourceSet resSet = aResource.getResourceSet();
	// Resource reviewResource = null;
	// EList<Resource> resources = resSet.getResources();
	// for (Iterator<Resource> iterator = resources.iterator(); iterator.hasNext();) {
	// Resource resource = iterator.next();
	// resUri = resource.getURI();
	// if (fReader.isReviewResourceUri(resUri)) {
	// reviewResource = resource;
	// break;
	// }
	// }
	// if (reviewResource != null) {
	// TreeIterator<EObject> iterator = EcoreUtil.getAllContents(reviewResource, false);
	// }
	//
	// }

	/**
	 * @param resource
	 * @throws ResourceHandlingException
	 */
	protected void unlockResource(Resource aResource) throws ResourceHandlingException {
		if (aResource == null || aResource.getURI() == null) {
			return;
		}

		URI resUri = aResource.getURI();
		File file = new File(URI.decode(resUri.devicePath()));
		if (!file.exists()) {
			throw new ResourceHandlingException("Not able to unlock Resource file: "
					+ file.getAbsolutePath().toString() + ". File does not exist");
		}

		String fileStr = resUri.toFileString() + LOCK_EXT;
		file = new File(fileStr);
		// check if the file to delete exists
		if (file.exists()) {
			// remove the file
			file.delete();
			Activator.fTracer.traceDebug("CheckIn, Lock file removed: " + fileStr);
		} else {
			StringBuilder message = new StringBuilder("Lock to remove does not exist");
			String absPath = file.getAbsolutePath();
			if (absPath != null) {
				message.append(": " + absPath);
			}
			Activator.fTracer.traceError(message.toString());
		}
	}

	/**
	 */
	static class UpdateContext {
		private String	fUser;
		private EObject		fEObject;
		private Long		fBookingNum	= null;

		/**
		 * Constructor for UpdateContext.
		 * @param aEObject EObject
		 * @param aUser R4EUser
		 */
		UpdateContext(EObject aEObject, String aUser) {
			fEObject = aEObject;
			fUser = aUser;
		}

		/**
		 * Method getResource.
		 * @return Resource
		 */
		public Resource getResource() {
			return fEObject.eResource();
		}

		/**
		 * Method getUser.
		 * @return R4EUser
		 */
		public String getUser() {
			return fUser;
		}

		/**
		 * Method getBookingNum.
		 * @return Long
		 */
		public Long getBookingNum() {
			return fBookingNum;
		}

		/**
		 * Method setBookingNum.
		 * @param aBookingNum Long
		 */
		public void setBookingNum(Long aBookingNum) {
			fBookingNum = aBookingNum;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		/**
		 * Method equals.
		 * @param o Object
		 * @return boolean
		 */
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if (o instanceof UpdateContext) {
				UpdateContext otherContext = (UpdateContext) o;
				Resource otherResource = otherContext.getResource();
				if (otherResource != fEObject.eResource()) {
					// resource references are different, check the contents to make sure
					URI otherURI = otherResource.getURI();
					URI tURI = fEObject.eResource().getURI();
					if (!(otherURI.equals(tURI))) {
						return false;
					}
				}

				// Compare user IDs
				String oUserId = otherContext.getUser();
				if (oUserId.equals(fUser)) {
					return true;
				}
			}
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		/**
		 * Method hashCode.
		 * @return int
		 */
		public int hashCode() {
			String toHash = fEObject.eResource().getURI().toString() + fUser;
			return toHash.hashCode();
		}
	}
}
	