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
 * This class implements generic support for Review Annotations
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import java.lang.reflect.Method;

import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewAnnotationSupport implements IReviewAnnotationSupport {

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
	 * Constructor for ReviewAnnotationSupport.
	 * 
	 * @param aSourceFile
	 *            Object
	 */
	public ReviewAnnotationSupport(Object aSourceFile) {
		this.fTargetAnnotationModel = createAnnotationModel(aSourceFile);
		this.fTargetAnnotationModel.setFile(aSourceFile);
	}

	// ------------------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------------------

	/**
	 * Method createAnnotationModel.
	 * 
	 * @param aSourceFile
	 *            Object
	 * @return IReviewAnnotationModel
	 */
	protected abstract IReviewAnnotationModel createAnnotationModel(Object aSourceFile);

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
	 * Method refreshAnnotations.
	 * 
	 * @param aElement
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#refreshAnnotations(Object)
	 */
	public void refreshAnnotations(Object aElement) {
		if (null != fTargetAnnotationModel) {
			fTargetAnnotationModel.setFile(aElement);
			fTargetAnnotationModel.refreshAnnotations();
		}
	}

	/**
	 * Method addAnnotation.
	 * 
	 * @param aElement
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#addAnnotation(Object)
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
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#updateAnnotation(Object)
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
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#removeAnnotation(Object)
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
		final ReviewAnnotationSupport other = (ReviewAnnotationSupport) aObject;
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

	/**
	 * Method getSourceViewer.
	 * 
	 * @param aMergeViewer
	 *            MergeSourceViewer
	 * @return SourceViewer
	 */
	public static SourceViewer getSourceViewer(MergeSourceViewer aMergeViewer) {
		if (SourceViewer.class.isInstance(aMergeViewer)) {
			return SourceViewer.class.cast(aMergeViewer);
		}

		final Object returnValue;
		try {
			final Method getSourceViewerRefl = MergeSourceViewer.class.getDeclaredMethod("getSourceViewer");
			getSourceViewerRefl.setAccessible(true);
			returnValue = getSourceViewerRefl.invoke(aMergeViewer);
			if (returnValue instanceof SourceViewer) {
				return (SourceViewer) returnValue;
			}
		} catch (Exception e) {
			// ignore
		}
		return null;
	}
}
