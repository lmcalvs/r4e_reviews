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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.mylyn.reviews.internal.notes.model.entityData.Entity;
import org.eclipse.mylyn.reviews.internal.notes.model.entityData.util.EntityDataResourceFactoryImpl;

public class NotesStaging {

	private NoteMap fNotesMap = NoteMap.newEmptyMap();

	/**
	 * @param aReference
	 *            e.g. /refs/notes/07/7
	 * @param repo
	 * @throws AmbiguousObjectException
	 * @throws IOException
	 */
	public NotesStaging(String aReference, Repository repo) throws AmbiguousObjectException, IOException {
		//Need to open all the notes of a particular reference
		//We shall then resolve the commit based on the refs name e.g. refs/notes/07/7

		if (aReference != null && repo != null && !Repository.isValidRefName(aReference)) {
			throw new IllegalArgumentException();
		}

		Ref reference = repo.getRef(aReference);
		RevCommit commit = null;

		if (reference != null) {
			//reference exists, read head
			RevWalk rwalk = new RevWalk(repo);
			commit = rwalk.parseCommit(reference.getObjectId());
			fNotesMap = NoteMap.read(rwalk.getObjectReader(), commit);
		} else {
			throw new IllegalArgumentException("Unable to resolve reference: " + aReference);
		}
	}

	public void add(AnyObjectId noteOn, Entity aEntity) {
		//make sure the entity is properly built
		if (aEntity == null) {
			return;
		}

		//if the note already exist, we need to merge replace the contents
		//We need to consider collisions with the remote

		String tmpdir = System.getProperty("java.io.tmpdir"); //$NON-NLS-1$
		String pathSeparator = System.getProperty("file.separator"); //$NON-NLS-1$

		String timeStamp = Long.toString(new Date().getTime());
		String filePath = tmpdir + pathSeparator + "tmpEnityNote_" + timeStamp; //$NON-NLS-1$
		URI destFileUri = URI.createFileURI(filePath);

		EntityDataResourceFactoryImpl resfactory = new EntityDataResourceFactoryImpl();

		Resource dres = resfactory.createResource(destFileUri);

		try {
			dres.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		//register an object with the entity data in the git repo
		InputStream istream = null;
		try {
			istream = new BufferedInputStream(new FileInputStream(destFileUri.devicePath()));

			//read notes on the current noteOn object

			//if noteOn object already has a note and the note is of the Entity type call a merge facility
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public NoteMap getStaging() {
		return fNotesMap;
	}

}
