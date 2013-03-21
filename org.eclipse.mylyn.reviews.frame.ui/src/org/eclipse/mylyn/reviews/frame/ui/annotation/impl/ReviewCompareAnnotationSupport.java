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
 * This abstract class implements support for Review Annotations in Compare Editors
 * 
 * Contributors:
 *   Sebastien Dubois - Created for Mylyn Reviews project
 *   
 ******************************************************************************/

package org.eclipse.mylyn.reviews.frame.ui.annotation.impl;

import java.lang.reflect.Field;

import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.MergeSourceViewer;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IEditorInputListener;
import org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationModel;

/**
 * Manages annotation models for compare viewers.
 * 
 * @author Thomas Ehrnhoefer
 * @author Steffen Pingel
 * @author Sebastien Dubois
 * @version $Revision: 1.0 $
 */
public abstract class ReviewCompareAnnotationSupport extends ReviewAnnotationSupport {

	/**
	 * Field fBaseAnnotationModel.
	 */
	private final IReviewAnnotationModel fBaseAnnotationModel;

	/**
	 * Field fBaseViewerListener.
	 */
	private IEditorInputListener fBaseViewerListener = null;

	/**
	 * Constructor for ReviewCompareAnnotationSupport.
	 * 
	 * @param aViewer
	 *            Viewer
	 * @param aTargetFile
	 *            Object
	 * @param aBaseFile
	 *            Object
	 */
	public ReviewCompareAnnotationSupport(Viewer aViewer, Object aTargetFile, Object aBaseFile) {
		super(aTargetFile);
		if (null != aBaseFile) {
			fBaseAnnotationModel = createAnnotationModel(aBaseFile);
			fBaseAnnotationModel.setFile(aBaseFile);
		} else {
			fBaseAnnotationModel = null;
		}
		install(aViewer);
	}

	/**
	 * Method getBaseAnnotationModel.
	 * 
	 * @return IReviewAnnotationModel
	 */
	public IReviewAnnotationModel getBaseAnnotationModel() {
		return fBaseAnnotationModel;
	}

	/**
	 * Method setAnnotationModelBaseElement.
	 * 
	 * @param aElement
	 *            - Object
	 */
	public void setAnnotationModelBaseElement(Object aElement) {
		if (null != fBaseAnnotationModel) {
			fBaseAnnotationModel.setFile(aElement);
		}
	}

	/**
	 * Method refreshAnnotations.
	 * 
	 * @param aElement
	 *            Object
	 * @see org.eclipse.mylyn.reviews.frame.ui.annotation.IReviewAnnotationSupport#refreshAnnotations(Object)
	 */
	@Override
	public void refreshAnnotations(Object aElement) {
		super.refreshAnnotations(aElement);
		if (null != fBaseAnnotationModel) {
			if (null != aElement) {
				fBaseAnnotationModel.setFile(aElement); //Set new file if it has changed
			}
			fBaseAnnotationModel.refreshAnnotations();
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
	@Override
	public void addAnnotation(Object aElement, Object aFile) {
		super.addAnnotation(aElement, aFile);
		if (null != fBaseAnnotationModel && null != aFile && aFile.equals(fBaseAnnotationModel.getFile())) {
			fBaseAnnotationModel.addAnnotation(aElement);
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
	@Override
	public void updateAnnotation(Object aElement, Object aFile) {
		super.updateAnnotation(aElement, aFile);
		if (null != fBaseAnnotationModel && null != aFile && aFile.equals(fBaseAnnotationModel.getFile())) {
			fBaseAnnotationModel.updateAnnotation(aElement);
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
	@Override
	public void removeAnnotation(Object aElement, Object aFile) {
		super.addAnnotation(aElement, aFile);
		if (null != fBaseAnnotationModel && aFile.equals(fBaseAnnotationModel.getFile())) {
			fBaseAnnotationModel.removeAnnotation(aElement);
		}
	}

	/**
	 * Method createEditorInputListener.
	 * 
	 * @param aViewer
	 *            MergeSourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return IEditorInputListener
	 */
	public abstract IEditorInputListener createEditorInputListener(final MergeSourceViewer aViewer,
			final IReviewAnnotationModel aAnnotationModel);

	/**
	 * Method install.
	 * 
	 * @param aSourceViewer
	 *            ISourceViewer
	 */
	@Override
	protected void install(Object aSourceViewer) {
		if (aSourceViewer instanceof TextMergeViewer) {
			final TextMergeViewer textMergeViewer = (TextMergeViewer) aSourceViewer;
			try {
				final Class<TextMergeViewer> clazz = TextMergeViewer.class;
				Field declaredField = clazz.getDeclaredField("fLeft"); //$NON-NLS-1$
				declaredField.setAccessible(true);
				final MergeSourceViewer fLeft = (MergeSourceViewer) declaredField.get(textMergeViewer);

				declaredField = clazz.getDeclaredField("fRight"); //$NON-NLS-1$
				declaredField.setAccessible(true);
				final MergeSourceViewer fRight = (MergeSourceViewer) declaredField.get(textMergeViewer);

				fViewerListener = registerInputListener(fLeft, fAnnotationModel);
				fBaseViewerListener = registerInputListener(fRight, fBaseAnnotationModel);
			} catch (Throwable t) {
				//do nothing for now
				if (null != ReviewAnnotationConfigFactory.getPlugin()) {
					ReviewAnnotationConfigFactory.getPlugin()
							.getLog()
							.log(new Status(IStatus.ERROR, ReviewAnnotationConfigFactory.getPlugin()
									.getBundle()
									.getSymbolicName(), IStatus.OK, t.getMessage(), t));
				}
			}
		}
	}

	/**
	 * Method registerInputListener.
	 * 
	 * @param aViewer
	 *            MergeSourceViewer
	 * @param aAnnotationModel
	 *            IReviewAnnotationModel
	 * @return IEditorInputListener
	 */
	private IEditorInputListener registerInputListener(final MergeSourceViewer aViewer,
			final IReviewAnnotationModel aAnnotationModel) {
		IEditorInputListener listener = null;
		if (null != aAnnotationModel) {
			final ISourceViewer sourceViewer = ReviewAnnotationSupport.getSourceViewer(aViewer);
			listener = createEditorInputListener(aViewer, aAnnotationModel);
			if (sourceViewer != null) {
				sourceViewer.addTextInputListener(listener);
			}
		}
		return listener;
	}
}
