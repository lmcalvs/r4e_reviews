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
 * 	 Based on internal org.eclipse.egit.ui.internal.history.FileDiff
 *   Alvaro Sanchez-Leon - Adapted for R4E
 *******************************************************************************/
package org.eclipse.mylyn.reviews.r4e.core.versions.git.diff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.GitCompareFileRevisionEditorInput;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.DiffContext.DiffContextSettable;
import org.eclipse.mylyn.reviews.r4e.core.versions.git.diff.internal.GitDiffFactory;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class GitDiffUtils {

	/**
	 * A copy of the non-accessible preference constant IPreferenceIds.REUSE_OPEN_COMPARE_EDITOR from the team ui plug
	 * in
	 */
	private static final String	REUSE_COMPARE_EDITOR_PREFID	= "org.eclipse.team.ui.reuse_open_compare_editors"; //$NON-NLS-1$

	/** The team ui plugin ID which is not accessible */
	private static final String	TEAM_UI_PLUGIN				= "org.eclipse.team.ui";							//$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * @param aRepo
	 * @param resource
	 * @param aCommitId
	 * @return
	 * @throws MissingObjectException
	 * @throws IncorrectObjectTypeException
	 * @throws CorruptObjectException
	 * @throws IOException
	 */
	public static DiffContext[] getChangeSet(Repository aRepo, IResource resource, ObjectId aCommitId)
			throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {

		if (aRepo == null || aCommitId == null) {
			return null;
		}
		RevWalk commitWalk = new RevWalk(aRepo);
		RevCommit commit = commitWalk.parseCommit(aCommitId);
		return getChangeSet(aRepo, resource, commit, commitWalk);
	}

	/**
	 * @param aRepo
	 * @param resource
	 * @param aCommitId
	 * @return
	 * @throws MissingObjectException
	 * @throws IncorrectObjectTypeException
	 * @throws CorruptObjectException
	 * @throws IOException
	 */
	public static DiffContext[] getChangeSet(Repository aRepo, IResource resource, RevCommit aCommitId)
			throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {
		if (aRepo == null || aCommitId == null) {
			return null;
		}

		RevWalk commitWalk = new RevWalk(aRepo);
		return getChangeSet(aRepo, resource, aCommitId, commitWalk);
	}

	// TODO: EGit API discussion required
	/**
	 * @param aRepo
	 * @param resource
	 * @param aCommitId
	 * @param commitWalk
	 * @return
	 * @throws MissingObjectException
	 * @throws IncorrectObjectTypeException
	 * @throws CorruptObjectException
	 * @throws IOException
	 */
	private static DiffContext[] getChangeSet(Repository aRepo, IResource resource, RevCommit aCommitId,
			RevWalk commitWalk)
			throws MissingObjectException,
			IncorrectObjectTypeException, CorruptObjectException, IOException {

		TreeWalk treeWalk = new TreeWalk(aRepo);
		treeWalk.setRecursive(true);

		Collection<String> paths = getPaths(aRepo, resource);

		TreeFilter filter = AndTreeFilter.create(PathFilterGroup.createFromStrings(paths), TreeFilter.ANY_DIFF);
		treeWalk.setFilter(filter);

		final ArrayList<DiffContext> changeSetList = new ArrayList<DiffContext>();

		if (aCommitId.getParentCount() > 0)
			treeWalk.reset(trees(aCommitId, commitWalk));
		else {
			treeWalk.reset();
			treeWalk.addTree(new EmptyTreeIterator());
			treeWalk.addTree(aCommitId.getTree());
		}

		if (treeWalk.getTreeCount() <= 2) {
			List<DiffEntry> entries = DiffEntry.scan(treeWalk);
			for (DiffEntry entry : entries) {
				final DiffContext d = GitDiffFactory.createGitDiffContext(aCommitId, entry);
				changeSetList.add(d);
			}
		} else { // DiffEntry does not support walks with more than two trees
			final int nTree = treeWalk.getTreeCount();
			final int myTree = nTree - 1;
			while (treeWalk.next()) {
				if (matchAnyParent(treeWalk, myTree))
					continue;

				final DiffContextSettable mergedDiff = GitDiffFactory.createGitDiffContextMerged(aCommitId);
				mergedDiff.setPath(treeWalk.getPathString());
				int m0 = 0;
				for (int i = 0; i < myTree; i++)
					m0 |= treeWalk.getRawMode(i);
				final int m1 = treeWalk.getRawMode(myTree);
				mergedDiff.setChange(ChangeType.MODIFY);
				if (m0 == 0 && m1 != 0)
					mergedDiff.setChange(ChangeType.ADD);
				else if (m0 != 0 && m1 == 0)
					mergedDiff.setChange(ChangeType.DELETE);
				else if (m0 != m1 && treeWalk.idEqual(0, myTree))
					mergedDiff.setChange(ChangeType.MODIFY); // there is no ChangeType.TypeChanged
				ObjectId[] blobs = new ObjectId[nTree];
				FileMode[] modes = new FileMode[nTree];
				for (int i = 0; i < nTree; i++) {
					blobs[i] = treeWalk.getObjectId(i);
					modes[i] = treeWalk.getFileMode(i);
				}
				mergedDiff.setBlobs(blobs);
				mergedDiff.setModes(modes);
				changeSetList.add(mergedDiff);
			}
		}

		DiffContext[] tmp = new DiffContext[changeSetList.size()];
		changeSetList.toArray(tmp);
		return tmp;
	}

	/**
	 * @param aRepo
	 * @param resource
	 * @return
	 */
	private static Collection<String> getPaths(Repository aRepo, IResource resource) {
		Collection<String> paths = new ArrayList<String>();
		final RepositoryMapping map = RepositoryMapping.getMapping(resource);
		paths.add(map.getRepoRelativePath(resource));
		return paths;
	}

	/**
	 * @param aCommit
	 * @param commitWalk
	 * @return
	 * @throws MissingObjectException
	 * @throws IncorrectObjectTypeException
	 * @throws IOException
	 */
	private static ObjectId[] trees(final RevCommit aCommit, RevWalk commitWalk) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		RevCommit parent = null;
		final ObjectId[] parentIds = new ObjectId[aCommit.getParentCount() + 1];
		for (int i = 0; i < parentIds.length - 1; i++) {
			commitWalk.reset();
			parent = commitWalk.parseCommit(aCommit.getParent(i).getId());
			parentIds[i] = parent.getTree().getId();
		}

		parentIds[parentIds.length - 1] = aCommit.getTree().getId();
		return parentIds;
	}

	/**
	 * @param walk
	 * @param myTree
	 * @return
	 */
	private static boolean matchAnyParent(final TreeWalk walk, final int myTree) {
		final int m = walk.getRawMode(myTree);
		for (int i = 0; i < myTree; i++)
			if (walk.getRawMode(i) == m && walk.idEqual(i, myTree))
				return true;
		return false;
	}

	/**
	 * TODO: Egit API discussion required Based on CommitFileDiffViewer showTwoWayFileDiff
	 * 
	 * @param d
	 */
	public static void showTwoWayFileDiff(final DiffContext d, Repository db) {
		final GitCompareFileRevisionEditorInput in;

		final String p = d.getPath();
		final RevCommit c = d.getCommit();
		final ITypedElement base;
		final ITypedElement next;

		if (d.getBlobs().length == 2 && !d.getChange().equals(ChangeType.ADD))
			base = CompareUtils.getFileRevisionTypedElement(p, c.getParent(0), db, d.getBlobs()[0]);
		else
			// Initial import
			base = new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$

		if (d.getChange().equals(ChangeType.DELETE))
			next = new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$
		else
			next = CompareUtils.getFileRevisionTypedElement(p, c, db, d.getBlobs()[1]);

		in = new GitCompareFileRevisionEditorInput(next, base, null);

		openInCompare(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(), in);

	}

	/**
	 * @param workBenchPage
	 * @param input
	 */
	public static void openInCompare(IWorkbenchPage workBenchPage, CompareEditorInput input) {
		IEditorPart editor = findReusableCompareEditor(input, workBenchPage);
		if (editor != null) {
			IEditorInput otherInput = editor.getEditorInput();
			if (otherInput.equals(input)) {
				// simply provide focus to editor
				if (OpenStrategy.activateOnOpen())
					workBenchPage.activate(editor);
				else
					workBenchPage.bringToTop(editor);
			} else {
				// if editor is currently not open on that input either re-use
				// existing
				CompareUI.reuseCompareEditor(input, (IReusableEditor) editor);
				if (OpenStrategy.activateOnOpen())
					workBenchPage.activate(editor);
				else
					workBenchPage.bringToTop(editor);
			}
		} else {
			CompareUI.openCompareEditor(input);
		}
	}

	private static IEditorPart findReusableCompareEditor(CompareEditorInput input, IWorkbenchPage page) {
		IEditorReference[] editorRefs = page.getEditorReferences();
		// first loop looking for an editor with the same input
		for (int i = 0; i < editorRefs.length; i++) {
			IEditorPart part = editorRefs[i].getEditor(false);
			if (part != null && (part.getEditorInput() instanceof GitCompareFileRevisionEditorInput)
					&& part instanceof IReusableEditor && part.getEditorInput().equals(input)) {
				return part;
			}
		}
		// if none found and "Reuse open compare editors" preference is on use
		// a non-dirty editor
		if (isReuseOpenEditor()) {
			for (int i = 0; i < editorRefs.length; i++) {
				IEditorPart part = editorRefs[i].getEditor(false);
				if (part != null && (part.getEditorInput() instanceof SaveableCompareEditorInput)
						&& part instanceof IReusableEditor && !part.isDirty()) {
					return part;
				}
			}
		}
		// no re-usable editor found
		return null;
	}

	private static boolean isReuseOpenEditor() {
		boolean defaultReuse = new DefaultScope().getNode(TEAM_UI_PLUGIN)
				.getBoolean(REUSE_COMPARE_EDITOR_PREFID, false);
		return new InstanceScope().getNode(TEAM_UI_PLUGIN).getBoolean(REUSE_COMPARE_EDITOR_PREFID, defaultReuse);
	}
}
