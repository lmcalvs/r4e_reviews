// $codepro.audit.disable com.instantiations.assist.eclipse.analysis.audit.rule.effectivejava.alwaysOverridetoString.alwaysOverrideToString, com.instantiations.assist.eclipse.analysis.deserializeabilitySecurity, com.instantiations.assist.eclipse.analysis.disallowReturnMutable, com.instantiations.assist.eclipse.analysis.enforceCloneableUsageSecurity, explicitThisUsage
/*******************************************************************************
 * Copyright (c) 2010, 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class is used as the input class that feeds the eclipse compare
 * editor for R4E element.  It needs to be subclassed for the each compare operation type
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.editors;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.internal.CompareContentViewerSwitchingPane;
import org.eclipse.compare.internal.CompareEditorInputNavigator;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotation;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIPosition;
import org.eclipse.mylyn.reviews.r4e.ui.internal.utils.UIUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.team.ui.synchronize.SaveableCompareEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class R4ECompareEditorInput extends SaveableCompareEditorInput {

	// ------------------------------------------------------------------------
	// Member variables
	// ------------------------------------------------------------------------

	/**
	 * Field fCurrentDiffNode
	 */
	protected R4EDiffNode fCurrentDiffNode;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4ECompareEditorInput.
	 */
	public R4ECompareEditorInput() {
		super(new CompareConfiguration(), null);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method initConfiguration.
	 */
	protected void initConfiguration() {
		// Set the label values for the compare editor
		getCompareConfiguration().setLeftEditable(false);
		getCompareConfiguration().setRightEditable(false);
		getCompareConfiguration().setProperty(CompareConfiguration.IGNORE_WHITESPACE, Boolean.valueOf(true));
	}

	/**
	 * Method getAncestorElement.
	 * 
	 * @return ITypedElement
	 */
	public ITypedElement getAncestorElement() {
		return null; //Not supported for now	
	}

	/**
	 * Method getCurrentDiffNode.
	 * 
	 * @return R4EDiffNode
	 */
	public R4EDiffNode getCurrentDiffNode() {
		return fCurrentDiffNode;
	}

	/**
	 * Method getToolTipText.
	 * 
	 * @return String
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		String targetTooltip = fCurrentDiffNode.getTargetLabel();
		String baseTooltip = fCurrentDiffNode.getBaseLabel();
		String format = CompareUI.getResourceBundle().getString("ResourceCompare.twoWay.tooltip"); //$NON-NLS-1$
		return MessageFormat.format(format, new Object[] { targetTooltip, baseTooltip });
	}

	/**
	 * Method fireInputChange.
	 */
	@Override
	protected void fireInputChange() { // $codepro.audit.disable emptyMethod
		// Not implemented for now
	}

	/**
	 * Method prepareCompareInput.
	 * 
	 * @param aMonitor
	 *            IProgressMonitor
	 * @return ICompareInput
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @see org.eclipse.team.ui.synchronize.SaveableCompareEditorInput#prepareCompareInput(IProgressMonitor)
	 */
	@Override
	protected ICompareInput prepareCompareInput(IProgressMonitor aMonitor) {
		if (null != aMonitor) {
			aMonitor.beginTask("R4E File Context Compare", IProgressMonitor.UNKNOWN); //$NON-NLS-1$
		}
		return fCurrentDiffNode;
	}

	/**
	 * Method createContents.
	 * 
	 * @param aParent
	 *            Composite
	 * @return Control
	 * @see org.eclipse.compare.CompareEditorInput#createContents(Composite)
	 */
	@Override
	public Control createContents(Composite aParent) {
		final Control control = super.createContents(aParent);

		//Go to the correct element in the compare editor
		UIUtils.selectElementInEditor(this);

		//TODO:  This is needed to show annotation highlighting when opening the compare editor.
		//		 It should not be needed so this should be investigated in the future.
		if (null != fCurrentDiffNode) {
			fCurrentDiffNode.refreshAnnotations();
		}
		return control;
	}

	/**
	 * Method getContentViewer.
	 * 
	 * @return Viewer
	 */
	public Viewer getContentViewer() {
		final ICompareNavigator navigator = getNavigator();
		if (navigator instanceof CompareEditorInputNavigator) {
			final Object[] panes = ((CompareEditorInputNavigator) navigator).getPanes();
			for (Object pane : panes) {
				if (pane instanceof CompareContentViewerSwitchingPane) {
					return ((CompareContentViewerSwitchingPane) pane).getViewer();
				}
			}
		}
		return null;
	}

	/**
	 * Method isAnnotationsAvailable.
	 * 
	 * @param aType
	 *            String
	 * @return boolean
	 */
	public boolean isAnnotationsAvailable(String aType) {
		if (null != fCurrentDiffNode.getAnnotationSupport()) {
			IReviewAnnotationModel model = fCurrentDiffNode.getAnnotationSupport().getAnnotationModel();
			if (null != model) {
				if (model.isAnnotationsAvailable(aType)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method gotoNextAnnotation.
	 * 
	 * @param aType
	 *            String
	 * @return R4EAnnotation
	 */
	public R4EAnnotation gotoNextAnnotation(String aType) {
		R4EAnnotation originalAnnotation = null;
		R4EAnnotation foundAnnotation = null;

		//We need to only consider the anomalies currently visible in the viewport
		if (null != fCurrentDiffNode.getAnnotationSupport()) {
			ITextEditor editor = fCurrentDiffNode.getAnnotationSupport().getEditor();
			if (null != editor) {
				IReviewAnnotationModel model = fCurrentDiffNode.getAnnotationSupport().getAnnotationModel();
				if (null != model) {
					ICompareNavigator navigator = ((R4ECompareEditorInput) editor.getEditorInput()).getNavigator();

					originalAnnotation = (R4EAnnotation) model.getNextAnnotation(aType);
					if (null != originalAnnotation) {
						R4EAnnotation annotation = originalAnnotation;
						do {
							final IR4EUIPosition annotationPositon = annotation.getR4EPosition();
							if (UIUtils.selectElementInEditorPane(navigator, annotationPositon, true)) {
								foundAnnotation = annotation;
								break;
							}
							annotation = (R4EAnnotation) model.getNextAnnotation(aType);
						} while (!originalAnnotation.getR4EPosition().isSameAs(annotation.getR4EPosition()));
					}
				}
			}
		}
		return foundAnnotation;
	}

	/**
	 * Method gotoPreviousAnnotation.
	 * 
	 * @param aType
	 *            String
	 * @return R4EAnnotation
	 */
	public R4EAnnotation gotoPreviousAnnotation(String aType) {
		R4EAnnotation originalAnnotation = null;
		R4EAnnotation foundAnnotation = null;

		//We need to only consider the anomalies currently visible in the viewport
		if (null != fCurrentDiffNode.getAnnotationSupport()) {
			ITextEditor editor = fCurrentDiffNode.getAnnotationSupport().getEditor();
			if (null != editor) {
				IReviewAnnotationModel model = fCurrentDiffNode.getAnnotationSupport().getAnnotationModel();
				if (null != model) {
					ICompareNavigator navigator = ((R4ECompareEditorInput) editor.getEditorInput()).getNavigator();

					originalAnnotation = (R4EAnnotation) model.getPreviousAnnotation(aType);
					if (null != originalAnnotation) {
						R4EAnnotation annotation = originalAnnotation;
						do {
							final IR4EUIPosition annotationPositon = annotation.getR4EPosition();
							if (UIUtils.selectElementInEditorPane(navigator, annotationPositon, true)) {
								foundAnnotation = annotation;
								break;
							}
							annotation = (R4EAnnotation) model.getPreviousAnnotation(aType);
						} while (!originalAnnotation.getR4EPosition().isSameAs(annotation.getR4EPosition()));
					}
				}
			}
		}
		return foundAnnotation;
	}

	//Test Methods

	/**
	 * Method getAnnotationModel.
	 * 
	 * @return R4EAnnotationModel
	 */
	public R4EAnnotationModel getAnnotationModel() {
		if (fCurrentDiffNode.getAnnotationSupport() == null) {
			return null;
		}
		return (R4EAnnotationModel) fCurrentDiffNode.getAnnotationSupport().getAnnotationModel();
	}
}
