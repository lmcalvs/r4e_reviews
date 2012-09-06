/*******************************************************************************
 * Copyright (c) 2012 Ericsson AB and others.
 * 
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Description:
 * 
 * This class implements generic support for R4E Annotations
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Review R4E project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.control;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.mylyn.reviews.r4e.ui.internal.annotation.content.R4EAnnotationModel;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.IR4EUIModelElement;
import org.eclipse.mylyn.reviews.r4e.ui.internal.model.R4EUIFileContext;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class R4EAnnotationSupport implements IReviewAnnotationSupport {

	// ------------------------------------------------------------------------
	// Members
	// ------------------------------------------------------------------------

	/**
	 * Field fTargetAnnotationModel.
	 */
	protected final IReviewAnnotationModel fTargetAnnotationModel;

	/**
	 * Field fTargetViewerListener.
	 */
	protected IEditorInputListener fTargetViewerListener = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for R4EAnnotationSupport.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 * @param aFileContext
	 *            R4EUIFileContext
	 */
	public R4EAnnotationSupport(Viewer aViewer, R4EUIFileContext aFileContext) {
		this.fTargetAnnotationModel = new R4EAnnotationModel();
		this.fTargetAnnotationModel.setFile(aFileContext);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method install.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 */
	protected abstract void install(Object aSourceViewer);

	/**
	 * Method getTargetEditor.
	 * 
	 * @return ITextEditor
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#getTargetEditor()
	 */
	public ITextEditor getTargetEditor() {
		if (null != fTargetViewerListener) {
			return fTargetViewerListener.getEditor();
		}
		return null;
	}

	/**
	 * Method getTargetAnnotationModel.
	 * 
	 * @return IReviewAnnotationModel
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#getTargetAnnotationModel()
	 */
	public IReviewAnnotationModel getTargetAnnotationModel() {
		return fTargetAnnotationModel;
	}

	/**
	 * Method setAnnotationModelElement.
	 * 
	 * @param aElement
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#setAnnotationModelElement(Object)
	 */
	public void setAnnotationModelElement(Object aElement) {
		if (null != fTargetAnnotationModel) {
			fTargetAnnotationModel.setFile(aElement);
		}
	}

	/**
	 * Method addAnnotation.
	 * 
	 * @param aElement
	 *            IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#addAnnotation(IR4EUIModelElement)
	 */
	public void addAnnotation(Object aElement) {
		if (null != fTargetAnnotationModel) {
			fTargetAnnotationModel.addAnnotation(aElement);
		}
	}

	/**
	 * Method updateAnnotation.
	 * 
	 * @param aElement
	 *            IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#updateAnnotation(IR4EUIModelElement)
	 */
	public void updateAnnotation(Object aElement) {
		if (null != fTargetAnnotationModel) {
			fTargetAnnotationModel.updateAnnotation(aElement);
		}
	}

	/**
	 * Method removeAnnotation.
	 * 
	 * @param aElement
	 *            IR4EUIModelElement
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#removeAnnotation(IR4EUIModelElement)
	 */
	public void removeAnnotation(Object aElement) {
		if (null != fTargetAnnotationModel) {
			fTargetAnnotationModel.removeAnnotation(aElement);
		}
	}

	/**
	 * Method equals.
	 * 
	 * @param aObject
	 *            Object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object aObject) {
		if (this == aObject) {
			return true;
		}
		if (aObject == null) {
			return false;
		}
		if (!getClass().equals(aObject.getClass())) {
			return false;
		}
		final R4EAnnotationSupport other = (R4EAnnotationSupport) aObject;
		if (fTargetAnnotationModel == null) {
			if (other.fTargetAnnotationModel != null) {
				return false;
			}
		} else if (!fTargetAnnotationModel.equals(other.fTargetAnnotationModel)) {
			return false;
		}
		return true;
	}

	/**
	 * Method hashCode.
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((fTargetAnnotationModel == null) ? 0 : fTargetAnnotationModel.hashCode());
		return result;
	}
}
