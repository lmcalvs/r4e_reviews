/*******************************************************************************
 * Copyright (c) 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as an input class that feeds the eclipse compare
 * editor to compare 2 Review Items
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIReviewItem;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Sebastien Dubois
 */
public class R4EReviewItemCompareEditorInput extends R4ECompareEditorInput {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field R4E_REVIEW_ITEM_COMPARE_EDITOR_TITLE. (value is ""R4E Review Item Compare"")
	 */
	private static final String R4E_REVIEW_ITEM_COMPARE_EDITOR_TITLE = "R4E Compare Review Item "; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fBaseReviewItem
	 */
	private final R4EUIReviewItem fBaseReviewItem;

	/**
	 * Field fTargetReviewItem
	 */
	private final R4EUIReviewItem fTargetReviewItem;

	/**
	 * Field fRootNode
	 */
	private DiffNode fRootNode;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EReviewItemCompareEditorInput.
	 * 
	 * @param aTargetReviewItem
	 *            R4EUIReviewItem
	 * @param aBaseReviewItem
	 *            R4EUIReviewItem
	 */
	public R4EReviewItemCompareEditorInput(R4EUIReviewItem aTargetReviewItem, R4EUIReviewItem aBaseReviewItem) {
		super();
		fTargetReviewItem = aTargetReviewItem;
		fBaseReviewItem = aBaseReviewItem;
		initConfiguration();
		setTitle(R4E_REVIEW_ITEM_COMPARE_EDITOR_TITLE + aTargetReviewItem.getName() + " with "
				+ aBaseReviewItem.getName());
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method initConfiguration.
	 * 
	 * @see org.eclipse.mylyn.reviews.r4e.ui.internal.editors.R4ECompareEditorInput#initConfiguration()
	 */
	@Override
	protected void initConfiguration() {
		// Set the label values for the compare editor
		getCompareConfiguration().setLeftEditable(false);
		getCompareConfiguration().setRightEditable(false);
		getCompareConfiguration().setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.valueOf(true));
	}

	/**
	 * Method getBaseReviewItem.
	 * 
	 * @return R4EUIReviewItem
	 */
	public R4EUIReviewItem getBaseReviewItem() {
		return fBaseReviewItem;
	}

	/**
	 * Method getTargetReviewItem.
	 * 
	 * @return R4EUIReviewItem
	 */
	public R4EUIReviewItem getTargetReviewItem() {
		return fTargetReviewItem;
	}

	/**
	 * Method prepareInput
	 * 
	 * @param aMonitor
	 *            - IProgressMonitor
	 * @return Object
	 * @see org.eclipse.compare.CompareEditorInput#prepareInput(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected Object prepareInput(IProgressMonitor aMonitor) throws InvocationTargetException, InterruptedException {
		if (null != aMonitor) {
			aMonitor.beginTask("R4E Review Item Compare", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		}

		if (fRootNode != null) {
			return fRootNode;
		}
		fRootNode = new DiffNode(Differencer.NO_CHANGE);

		//Here we compare the target versions of the target review item with the target version of the base review item.
		//For each file in the target review item, we try to match it to the same file in the base item
		List<R4EUIFileContext> matchedBasefiles = new ArrayList<R4EUIFileContext>();
		for (final R4EUIFileContext targetFile : fTargetReviewItem.getFileContexts()) {
			if (null != targetFile.getFileContext().getTarget()) {
				R4EDiffNode node = null;
				boolean identicalFiles = false;
				for (R4EUIFileContext basefile : fBaseReviewItem.getFileContexts()) {
					String targetPath = null;
					if (null != basefile.getFileContext().getTarget()) {
						targetPath = targetFile.getFileContext().getTarget().getRepositoryPath();
					}
					String basePath = null;
					if (null != basefile.getFileContext().getTarget()) {
						basePath = basefile.getFileContext().getTarget().getRepositoryPath();
					}
					if (null != targetPath && null != basePath && targetPath.equals(basePath)) {
						//Ignore identical files
						if (!targetFile.getTargetFileVersion()
								.getVersionID()
								.equals(basefile.getTargetFileVersion().getVersionID())) {
							node = new R4EDiffNode(targetFile, basefile, true);
						} else {
							identicalFiles = true;
						}
						matchedBasefiles.add(basefile);
						break;
					}
				}
				if (null == node && !identicalFiles) {
					//No base found, this is a new file
					node = new R4EDiffNode(targetFile, null, true);
				}

				if (null != node) {
					DiffNode parent = findNode(fRootNode, node.getPath());
					parent.add(node);
				}
			}
		}

		//The files in the base Review Item that were not included are now included here
		for (final R4EUIFileContext basefile : fBaseReviewItem.getFileContexts()) {
			if (!matchedBasefiles.contains(basefile)) {
				if (null != basefile && null != basefile.getFileContext().getTarget()) {
					//No target found, this is a removed file
					R4EDiffNode node = new R4EDiffNode(null, basefile, true);
					DiffNode parent = findNode(fRootNode, node.getPath());
					parent.add(node);
				}
			}
		}

		for (IDiffElement child : fRootNode.getChildren()) {
			if (child instanceof R4EDiffNode) {
				flattenTree((R4EDiffNode) child);
			}
		}

		return fRootNode;
	}

	private void flattenTree(R4EDiffNode node) {
		mergeChild(node);
		for (IDiffElement child : node.getChildren()) {
			if (child instanceof R4EDiffNode) {
				flattenTree((R4EDiffNode) child);
			}
		}
	}

	public void mergeChild(R4EDiffNode node) {
		if (node.getChildren().length == 1 && isDirectory(node.getChildren()[0])) {
			R4EDiffNode child = (R4EDiffNode) node.getChildren()[0];
			node.setName(node.getName() + "/" + child.getName());
			node.remove(child);
			for (IDiffElement element : child.getChildren()) {
				node.add(element);
			}
			mergeChild(node);
		}
	}

	private boolean isDirectory(IDiffElement element) {
		return element instanceof R4EDiffNode && ((R4EDiffNode) element).getTargetFile() == null
				&& ((R4EDiffNode) element).getBaseFile() == null;
	}

	private DiffNode findNode(DiffNode root, IPath path) {
		if (path.segmentCount() == 1) {
			return root;
		}

		String name = path.segment(0);

		// try to find existing path segment
		IDiffElement child = root.findChild(name);
		if (child instanceof DiffNode) {
			return findNode((DiffNode) child, path.removeFirstSegments(1));
		}

		// create new path segment
		R4EDiffNode node = new R4EDiffNode(name);
		root.add(node);
		return findNode(node, path.removeFirstSegments(1));
	}

	/**
	 * Method getToolTipText.
	 * 
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		if ((null != fTargetReviewItem) && (null != fBaseReviewItem)) {
			String format = null;

			// Set the label values for the compare editor
			StringBuilder leftLabel = null;
			String targetItemId = ""; //$NON-NLS-1$
			if (null != fTargetReviewItem) {
				targetItemId = fTargetReviewItem.getItem().getRepositoryRef();
				if (null != targetItemId) {
					leftLabel = new StringBuilder("Target Review Item " + targetItemId); //$NON-NLS-1$
				} else {
					//Resource file in workspace
					leftLabel = new StringBuilder("Target Review Item " + "(Current Workspace)"); //$NON-NLS-1$
				}
			}
			StringBuilder rightLabel = null;
			String baseItemId = ""; //$NON-NLS-1$
			if (null != fBaseReviewItem) {
				baseItemId = fBaseReviewItem.getItem().getRepositoryRef();
				rightLabel = new StringBuilder("Base Review Item " + baseItemId); //$NON-NLS-1$
			}
			format = CompareUI.getResourceBundle().getString("ResourceCompare.twoWay.tooltip"); //$NON-NLS-1$
			return MessageFormat.format(format, new Object[] { leftLabel, rightLabel });
		}
		// fall back
		return super.getToolTipText();
	}

	/**
	 * Method findContentViewer.
	 * 
	 * @param aOldViewer
	 *            - Viewer
	 * @param aInput
	 *            - ICompareInput
	 * @param aParent
	 *            - Composite
	 * @return Viewer
	 * @see org.eclipse.compare.CompareEditorInput#findContentViewer(org.eclipse.jface.viewers.Viewer,
	 *      org.eclipse.compare.structuremergeviewer.ICompareInput, org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public Viewer findContentViewer(Viewer aOldViewer, ICompareInput aInput, Composite aParent) {
		Viewer contentViewer = super.findContentViewer(aOldViewer, aInput, aParent);
		if (aInput instanceof R4EDiffNode) {
			if (!((R4EDiffNode) aInput).getType().equals(ITypedElement.FOLDER_TYPE)) {
				//create/insert new Annotation model
				IReviewAnnotationSupport annotationSupport = UIUtils.getCompareAnnotationSupport(contentViewer,
						((R4EDiffNode) aInput).getTargetFile(), ((R4EDiffNode) aInput).getBaseFile());
				((R4EDiffNode) aInput).setAnnotationSupport(annotationSupport);
				UIUtils.insertAnnotationNavigationCommands(CompareViewerPane.getToolBarManager(aParent),
						annotationSupport);
				getCompareConfiguration().setLeftLabel(((R4EDiffNode) aInput).getTargetLabel());
				getCompareConfiguration().setRightLabel(((R4EDiffNode) aInput).getBaseLabel());
				fCurrentDiffNode = (R4EDiffNode) aInput;
				//NOTE:  This solves the problem described in bug 402060, but causes a bad listener leak in AbstractTextEditor so we remove it for now
				//updateViewerConfig(contentViewer, (R4EDiffNode) aInput);
			}
		}
		return contentViewer;
	}

	/**
	 * Method updateViewerConfig.
	 * 
	 * @param aContentViewer
	 *            - Viewer
	 * @param aInput
	 *            - R4EDiffNode
	 */
	protected void updateViewerConfig(Viewer aContentViewer, R4EDiffNode aInput) {
		//NOTE:  This is a temporary hack to work around the problem described in bug 402060.  It should be removed when the bug is fixed
		if (aContentViewer instanceof TextMergeViewer) {
			final TextMergeViewer textMergeViewer = (TextMergeViewer) aContentViewer;
			try {
				final Class<TextMergeViewer> clazz = TextMergeViewer.class;
				Field declaredField = clazz.getDeclaredField("isConfigured"); //$NON-NLS-1$
				declaredField.setAccessible(true);
				declaredField.setBoolean(textMergeViewer, false);
				Method declaredMethod2 = clazz.getDeclaredMethod("updateContent", Object.class, Object.class, //$NON-NLS-1$
						Object.class);
				declaredMethod2.setAccessible(true);
				declaredMethod2.invoke(textMergeViewer, null, aInput.getLeft(), aInput.getRight());
			} catch (Throwable t) {
				t.printStackTrace();
				//do nothing for now
			}
		}
	}
}
