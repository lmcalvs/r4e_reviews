/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Ericsson AB - Initial API and Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.internal.notes.serialize.impl;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity;

public class NotesIO {

	private String test;

	public void commit(Repository repo, String branch, NoteMap notes) {
		ObjectReader reader = repo.newObjectReader();
		ObjectInserter inserter = repo.newObjectInserter();

//		notes.se

	}

	public void update(Entity aEntity) {
		//Make sure the entity belongs to a valid Resource

	}

	public List<Entity> open(URI uri) {
		//Implement it 
		return null;
	}

}
