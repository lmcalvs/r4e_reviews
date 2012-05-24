/*******************************************************************************
 * Copyright (c) 2011, 2012 Ericsson
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

package org.eclipse.mylyn.reviews.r4e.internal.transform.serial.impl;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelReader;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.IModelWriter;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.RModelFactoryExt;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.Persistence.ResourceUpdater;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ChangeResController;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.ChangeResSetController;
import org.eclipse.mylyn.reviews.r4e.core.model.serial.impl.RModelFactoryExtImpl;

/**
 * @author Alvaro Sanchez-Leon
 */
public class TResSerializeFactory {

	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	private static TResReader reader = null;

	private static TResWriter writer = null;

	private static RModelFactoryExt fFactoryExtension = null;

	private static ResourceUpdater fResUpdater = null;

	private static ResourceUpdater fResSetUpdater = null;

	private static final Persistence.IResSerializationState fResSerializationState = new Persistence.IResSerializationState() {
		//Serialisation is always active for model transformations
		public boolean isSerializationInactive(Resource aResource) {
			return false;
		}
	};

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @return
	 */
	public static IModelReader getReader() {
		if (reader == null) {
			reader = new TResReader();
		}
		return reader;
	}

	/**
	 * @return
	 */
	public static IModelWriter getWriter() {
		if (writer == null) {
			writer = new TResWriter(fResSerializationState);
		}
		return writer;
	}

	/**
	 * @return
	 */
	public static RModelFactoryExt getModelExtension() {
		if (fFactoryExtension == null) {
			fFactoryExtension = new RModelFactoryExtImpl();
		}
		return fFactoryExtension;
	}

	/**
	 * Model updater at the ResourceSet level, used for initial creation and configuration of Reviews and Review Groups,
	 * i.e. The instance is not yet published to other users One lock for all associated resources. One save for all
	 * resources within the same ResourceSet
	 * 
	 * @return
	 */
	public static ResourceUpdater getResourceSetUpdater() {
		if (fResSetUpdater == null) {
			fResSetUpdater = new ChangeResSetController(fResSerializationState);
		}
		return fResSetUpdater;
	}

	/**
	 * Model updater, one resource or element at a time, to be used after initial cration and configuration. Keeping
	 * locks for the minimum time needed since the resource has been already published for other users
	 * 
	 * @return
	 */
	public static ResourceUpdater getResourceUpdater() {
		if (fResUpdater == null) {
			fResUpdater = new ChangeResController(fResSerializationState);
		}
		return fResUpdater;
	}
}
