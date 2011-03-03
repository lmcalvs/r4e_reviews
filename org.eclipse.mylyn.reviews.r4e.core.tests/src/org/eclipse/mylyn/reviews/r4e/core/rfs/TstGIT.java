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
 *   Alvaro Sanchez-Leon - Initial Implementation
 *******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.core.rfs;


import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.GitProvider;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.project.GitProjectData;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.GitDiffUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.team.core.RepositoryProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TstGIT {
	// ------------------------------------------------------------------------
	// Fields
	// ------------------------------------------------------------------------
	RepositoryCache	repoCache		= org.eclipse.mylyn.reviews.r4e.core.Activator.getDefault().getGitRepositoryCache();
	File			testRepoFile	= new File("C:/git_repos/jgit/.git");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	// Comment: Converting user selected object to resource,
	// use org.eclipse.team.internal.ui.Utils.getResouurce

	// ------------------------------------------------------------------------
	// Tests
	// ------------------------------------------------------------------------
	@Test
	public void findLastCommit() {
		IProject[] projects = getProjects();

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			Repository repo = getRepository(project);
			try {
				System.out.println("\n\nLast commit for project: " + project.getName());
				printLastCommit(repo);
			} catch (AmbiguousObjectException e) {
				e.printStackTrace();
				fail("Exception");
			} catch (IOException e) {
				e.printStackTrace();
				fail("Exception");
			}
		}
	}

	@Test
	public void selectAValidCommit() {
		// pick and old commit in different commit lane than last commit
		// ObjectId commitID = ObjectId.fromString("12b635043506211fe9c873f14fc8e03546d6081d");
		ObjectId commitID = ObjectId.fromString("faa0747cce9826137b3841661a5b745030fb55fe");
		IProject project = getProject("org.eclipse.jgit");
		Repository repo = getRepository(project);
		RevWalk commitWalk = new RevWalk(repo);
		try {
			RevCommit commit = commitWalk.parseCommit(commitID);
			printCommit(commit);
			System.out.println("\n\n");
			printCommit(commitWalk.parseCommit(commit.getParent(0).getId()));
		} catch (MissingObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (AmbiguousObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void selectAnInValidCommit() {
		// pick and invalid commit.
		ObjectId commitID = ObjectId.fromString("faa0747cce9826137b3841661a5b745030000000");
		IProject project = getProject("org.eclipse.jgit");
		Repository repo = getRepository(project);
		RevWalk commitWalk = new RevWalk(repo);
		try {
			RevCommit commit = commitWalk.parseCommit(commitID);
			printCommit(commit);
		} catch (MissingObjectException e) {
			System.out.println("MissingObjectException received and expected for test case");
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (AmbiguousObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	/**
	 * Obtain the change set for a serialized commit id
	 */
	@Test
	public void findChangeSet() {
		// pick a valid commit.
		ObjectId commitID = ObjectId.fromString("faa0747cce9826137b3841661a5b745030fb55fe");
		IProject project = getProject("org.eclipse.jgit");
		Repository repo = getRepository(project);
		// Merged, Two parents - Nov 22 - 2010
		// ObjectId commitID = ObjectId.fromString("34962b4700940221e07371e0a965f02b88b84711");

		try {
			DiffContext[] changeSet = GitDiffUtils.getChangeSet(repo, project, commitID);
			printCommit(new RevWalk(repo).parseCommit(commitID));
			for (int i = 0; i < changeSet.length; i++) {
				DiffContext aDiffContext = changeSet[i];
				System.out.println("context " + i + " : " + aDiffContext.getChange() + ", " + aDiffContext.getPath());
			}

		} catch (MissingObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (CorruptObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}

	}

	/**
	 * Launcher option settings shall be set to<br>
	 * Run in UI thread shall be UNCHECKED<br>
	 * Keep Junit running when in Debug shalL be CHECKED
	 */
	@Test
	public void triggerEclipseCompare() {
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		// pick a valid commit.
		ObjectId commitID = ObjectId.fromString("faa0747cce9826137b3841661a5b745030fb55fe");
		IProject project = getProject("org.eclipse.jgit");
		final Repository repo = getRepository(project);
		// Merged, Two parents - Nov 22 - 2010
		// ObjectId commitID = ObjectId.fromString("34962b4700940221e07371e0a965f02b88b84711");

		try {
			final DiffContext[] changeSet = GitDiffUtils.getChangeSet(repo, project, commitID);
			printCommit(new RevWalk(repo).parseCommit(commitID));
			if (changeSet.length > 0) {
				// Let a new thread display the action
				Thread thread = new Thread(new Runnable() {
					public void run() {
						Display.getDefault().asyncExec(new Runnable() {
							public void run() {
								GitDiffUtils.showTwoWayFileDiff(changeSet[0], repo);
								System.out.println("executing");
							}
						});
					}
				});

				thread.start();
				thread.setPriority(Thread.MAX_PRIORITY);

				try {
					// Wait for the other thread to execute
					Thread.yield();
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					fail("Interrupted while waiting");
				}
			} else {
				fail("changeSet is empty");
			}

		} catch (MissingObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IncorrectObjectTypeException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (CorruptObjectException e) {
			e.printStackTrace();
			fail("Exception");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Exception");
		}
	}

	@Test
	public void testResources() {
		String persURIStr = "platform:/resource/org.eclipse.jgit/src/org/eclipse/jgit/JGitText.java";
		URI uri = URI.createURI(persURIStr);
		IFile iFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));

		// Be sure to encode the path when creating a URI for it, i.e.,
		URI genURI = URI.createPlatformResourceURI(iFile.getFullPath().toString(), true);

		System.out.println("Resource resolved: " + genURI.toString());
	}

	@Test
	public void createRepository() {

	}

	@Test
	public void insertObject() {

	}

	@Test
	public void retrieveObject() {

	}

	// ------------------------------------------------------------------------
	// Private methods
	// ------------------------------------------------------------------------

	private IProject[] getProjects() {
		IWorkspace root = ResourcesPlugin.getWorkspace();
		IProject[] projects = root.getRoot().getProjects();
		return projects;
	}

	private IProject getProject(String name) {
		IWorkspace root = ResourcesPlugin.getWorkspace();
		IProject[] projects = root.getRoot().getProjects();
		IProject project = null;
		for (int i = 0; i < projects.length; i++) {
			project = projects[i];
			if (project.getName().equals(name)) {
				return project;
			}
		}
	
		return project;
	}

	private Repository getRepository(IResource resource) {
		if (resource == null || resource.getProject() == null) {
			return null;
		}

		Repository repo = null;
		RepositoryProvider teamProvider = RepositoryProvider.getProvider(resource.getProject());
		// check if the provider is for git
		if ((teamProvider instanceof GitProvider)) {
			// a project under a git repository found
			GitProvider provider = (GitProvider) teamProvider;
			// Resolve the repository
			GitProjectData projData = provider.getData();
			RepositoryMapping repMap = projData.getRepositoryMapping(resource);
			repo = repMap.getRepository();
		}
	
		return repo;
	}

	private void printLastCommit(Repository repo) throws AmbiguousObjectException, IOException {
		RevWalk commitWalk = new RevWalk(repo);
		RevCommit lastCommit = commitWalk.parseCommit(repo.resolve(HEAD));
		// commitWalk.markStart(lastCommit);

		System.out.println("Current branch: " + repo.getBranch());
		printCommit(lastCommit);
	}
	
	private void printCommit(RevCommit commit) {
		System.out.println("Name: " + commit.getName());
		System.out.println("Author: " + commit.getAuthorIdent());
		System.out.println("Time: " + commit.getCommitTime());
		System.out.println("Message: " + commit.getFullMessage());
	}

}


		
