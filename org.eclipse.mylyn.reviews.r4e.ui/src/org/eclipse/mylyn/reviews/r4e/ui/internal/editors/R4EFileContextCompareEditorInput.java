// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010, 2013 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as the input class that feeds the eclipse compare
 * editor to compare the target and base version of a File Context element
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public class R4EFileContextCompareEditorInput extends R4ECompareEditorInput {

	// ------------------------------------------------------------------------
	// Constants
	// ------------------------------------------------------------------------

	/**
	 * Field R4E_FILE_COMPARE_EDITOR_TITLE. (value is ""R4E File Compare"")
	 */
	private static final String R4E_FILE_COMPARE_EDITOR_TITLE = "R4E File Compare"; //$NON-NLS-1$

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ECompareEditorInput.
	 * 
	 * @param aContext
	 *            R4EUIFileContext
	 */
	public R4EFileContextCompareEditorInput(R4EUIFileContext aContext) {
		super();
		fCurrentDiffNode = new R4EDiffNode(aContext, aContext, false);
		initConfiguration();
		setTitle(R4E_FILE_COMPARE_EDITOR_TITLE);
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
		super.initConfiguration();
		getCompareConfiguration().setLeftLabel(fCurrentDiffNode.getTargetLabel());
		getCompareConfiguration().setRightLabel(fCurrentDiffNode.getBaseLabel());
	}

	/**
	 * Method getAdapter.
	 * 
	 * @param aAdapter
	 *            Class
	 * @return Object
	 * @see org.eclipse.compare.CompareEditorInput#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes")
	Class aAdapter) {
		if (IFile.class.equals(aAdapter)) {
			if (null != getWorkspaceElement()) {
				return getWorkspaceElement().getResource();
			}
			return null;
		}
		return super.getAdapter(aAdapter);
	}

	/**
	 * Method getWorkspaceElement.
	 * 
	 * @return R4EFileTypedElement
	 */
	private R4EFileTypedElement getWorkspaceElement() {
		ITypedElement targetElement = fCurrentDiffNode.getTargetTypedElement();
		if (targetElement instanceof R4EFileTypedElement) {
			return (R4EFileTypedElement) targetElement;
		}
		return null;
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
		final Viewer contentViewer = super.findContentViewer(aOldViewer, aInput, aParent);
		if (aInput instanceof R4EDiffNode) {
			//Check if files should be editable
			if (((R4EDiffNode) aInput).getTargetTypedElement() instanceof R4EFileTypedElement) {
				getCompareConfiguration().setLeftEditable(true);
				//Right side (base) is always read-only
			}
			IReviewAnnotationSupport annotationSupport = UIUtils.getCompareAnnotationSupport(contentViewer,
					((R4EDiffNode) aInput).getTargetFile(), null); //No annotation model for the base is created/used
			((R4EDiffNode) aInput).setAnnotationSupport(annotationSupport);
			UIUtils.insertAnnotationNavigationCommands(CompareViewerPane.getToolBarManager(aParent), annotationSupport);
		}
		return contentViewer;
	}
}
