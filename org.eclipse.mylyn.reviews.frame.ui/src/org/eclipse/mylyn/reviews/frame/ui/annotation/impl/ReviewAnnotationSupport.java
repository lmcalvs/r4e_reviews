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
	 * Field fAnnotationModel.
	 */
	protected IReviewAnnotationModel fAnnotationModel = null;

	/**
	 * Field fViewerListener.
	 */
	protected IEditorInputListener fViewerListener = null;

	// ------------------------------------------------------------------------
	// Constructors
	// ------------------------------------------------------------------------

	/**
	 * Constructor for ReviewAnnotationSupport.
	 * 
	 * @param aFileVersion
	 *            Object
	 */
	public ReviewAnnotationSupport(Object aFileVersion) {
		if (null != aFileVersion) {
			this.fAnnotationModel = createAnnotationModel(aFileVersion);
			this.fAnnotationModel.setFile(aFileVersion);
		}
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
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#getEditor()
	 */
	public ITextEditor getEditor() {
		if (null != fViewerListener) {
			return fViewerListener.getEditor();
		}
		return null;
	}

	/**
	 * Method getAnnotationModel.
	 * 
	 * @return IReviewAnnotationModel
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#getAnnotationModel()
	 */
	public IReviewAnnotationModel getAnnotationModel() {
		return fAnnotationModel;
	}

	/**
	 * Method setAnnotationModelElement.
	 * 
	 * @param aElement
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#setAnnotationModelElement(Object)
	 */
	public void setAnnotationModelElement(Object aElement) {
		if (null != fAnnotationModel) {
			fAnnotationModel.setFile(aElement);
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
		if (null != fAnnotationModel) {
			if (null != aElement) {
				fAnnotationModel.setFile(aElement); //Set new file if it has changed
			}
			fAnnotationModel.refreshAnnotations();
		}
	}

	/**
	 * Method addAnnotation.
	 * 
	 * @param aElement
	 *            Object
	 * @param aFile
	 *            Object      
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#addAnnotation(Object)
	 */
	public void addAnnotation(Object aElement, Object aFile) {
		if (null != fAnnotationModel && aFile.equals(fAnnotationModel.getFile())) {
			fAnnotationModel.addAnnotation(aElement);
		}
	}

	/**
	 * Method updateAnnotation.
	 * 
	 * @param aElement
	 *            Object
	 * @param aFile
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#updateAnnotation(Object)
	 */
	public void updateAnnotation(Object aElement, Object aFile) {
		if (null != fAnnotationModel && aFile.equals(fAnnotationModel.getFile())) {
			fAnnotationModel.updateAnnotation(aElement);
		}
	}

	/**
	 * Method removeAnnotation.
	 * 
	 * @param aElement
	 *            Object
	 * @param aFile
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#removeAnnotation(Object)
	 */
	public void removeAnnotation(Object aElement, Object aFile) {
		if (null != fAnnotationModel && aFile.equals(fAnnotationModel.getFile())) {
			fAnnotationModel.removeAnnotation(aElement);
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
		if (fAnnotationModel == null) {
			if (other.fAnnotationModel != null) {
				return false;
			}
		} else if (!fAnnotationModel.equals(other.fAnnotationModel)) {
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
		result = (prime * result) + ((fAnnotationModel == null) ? 0 : fAnnotationModel.hashCode());
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
